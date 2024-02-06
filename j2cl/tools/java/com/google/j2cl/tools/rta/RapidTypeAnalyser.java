/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.j2cl.tools.rta;

import com.google.j2cl.transpiler.backend.libraryinfo.LibraryInfo;
import java.util.Collection;
import java.util.List;

final class RapidTypeAnalyser {

  static RtaResult analyse(List<LibraryInfo> libraryInfos, boolean keepJsTypeInterfaces) {
    Collection<Type> types = TypeGraphBuilder.build(libraryInfos);

    if (keepJsTypeInterfaces) {
      types.stream().filter(Type::isJsTypeInterface).forEach(RapidTypeAnalyser::markTypeLive);
    }

    // Go over the entry points to start the traversal.
    types.stream()
        .flatMap(t -> t.getMembers().stream())
        .filter(Member::isJsAccessible)
        .forEach(m -> onMemberReference(m));

    return RtaResult.build(types);
  }

  private static void onMemberReference(Member member) {
    if (member.isPolymorphic()) {
      traversePolymorphicReference(member.getDeclaringType(), member.getName());
    } else {
      markTypeLive(member.getDeclaringType());
      markMemberLive(member.getDeclaringType().getMemberByName("$clinit"));
      markMemberLive(member);
    }
  }

  private static void markMemberLive(Member member) {
    if (member.isLive()) {
      return;
    }

    member.markLive();

    Type declaringType = member.getDeclaringType();
    if (!declaringType.isInstantiated() && member.isConstructor()) {
      declaringType.instantiate();
      declaringType.getPotentiallyLiveMembers().forEach(RapidTypeAnalyser::markMemberLive);
    }

    member.getReferencedMembers().forEach(RapidTypeAnalyser::onMemberReference);
    member.getReferencedTypes().forEach(RapidTypeAnalyser::markTypeLive);
  }

  private static void traversePolymorphicReference(Type type, String memberName) {
    Member member = type.getMemberByName(memberName);
    if (member == null) {
      // No member found in this class. In this case we need to mark the supertype method as
      // potentially live since it might be an accidental override.
      markOverriddenMembersPotentiallyLive(type, memberName);
    } else if (member.isPolymorphic()) {
      if (member.isFullyTraversed()) {
        return;
      }
      member.markFullyTraversed();

      markMemberPotentiallyLive(member);
    }

    // Recursively unfold the overriding chain.
    type.getImmediateSubtypes()
        .forEach(subtype -> traversePolymorphicReference(subtype, memberName));
  }

  private static void markOverriddenMembersPotentiallyLive(Type type, String memberName) {
    while ((type = type.getSuperClass()) != null) {
      Member member = type.getMemberByName(memberName);
      if (member != null && member.isPolymorphic()) {
        markMemberPotentiallyLive(member);
        return;
      }
    }
  }

  private static void markMemberPotentiallyLive(Member member) {
    Type declaringType = member.getDeclaringType();
    if (declaringType.isInstantiated()) {
      markMemberLive(member);
    } else {
      // Type is not instantiated, defer making it live until the type is instantiated.
      declaringType.addPotentiallyLiveMember(member);
    }
  }

  private static void markTypeLive(Type type) {
    if (type.isLive()) {
      return;
    }

    type.markLive();

    // When a type is marked as live, we need to explicitly mark the super interfaces as live since
    // we need markImplementor call (which are not tracked in AST).
    type.getSuperInterfaces().forEach(RapidTypeAnalyser::markTypeLive);
  }

  private RapidTypeAnalyser() {}
}
