#!/bin/bash

# Deployment script modeled roughly after upstream's js deployment wiring,
# this file runs the bazel command to produce bundles, then immediately
# unbundles the zip and deploys each artifact. This script differs slightly
# in that it uses gpg:sign-and-deploy-file to handle attaching signatures,
# and only uploads the artifacts that Vertispan is presently interested in.

set -e

DEFAULT_DEPLOY_URL=https://repo.vertispan.com/j2cl/
DEFAULT_DEPLOY_REPO_ID=vertispan-snapshots

VERSION=${1:-HEAD-SNAPSHOT}
DEPLOY_URL=${2:-$DEFAULT_DEPLOY_URL}
DEPLOY_REPO_ID=${3:-$DEFAULT_DEPLOY_REPO_ID}

#TODO Find another way to pass in gpg passphrase - apparently
#     this is the only way bazel can work with parameters passed
#     in? Also, it is unnecessary, since we have to call sign-and-deploy-file
#     anyway to let maven upload without manually pushing a
#     bundle, so it is unnecessary.
#     It might be easier to modify the .bzl instead of supporting
#     this, and just have bazel produce the jars+poms.
bazel build //:sonatype_bundles --define=COMPILER_VERSION=$VERSION --define=CLEARTEXT_GPG_PASSPHRASE=$CLEARTEXT_GPG_PASSPHRASE


if [ -z "$CLEARTEXT_GPG_PASSPHRASE" ]; then
  GOAL="deploy:deploy-file"
else
  GOAL="gpg:sign-and-deploy-file"
fi

mkdir tmp
cd tmp

mkdir parent
unzip -n ../bazel-bin/closure-compiler-parent_bundle.jar -d parent
mvn $GOAL -Dfile=parent/pom.xml -DpomFile=parent/pom.xml -Durl=$DEPLOY_URL -DrepositoryId=$DEPLOY_REPO_ID


mkdir main
unzip -n ../bazel-bin/closure-compiler-main_bundle.jar  -d main
mvn $GOAL -Dfile=main/pom.xml -DpomFile=main/pom.xml -Durl=$DEPLOY_URL -DrepositoryId=$DEPLOY_REPO_ID


mkdir unshaded
unzip -n ../bazel-bin/closure-compiler-unshaded_bundle.jar  -d unshaded
mvn $GOAL -Dfile=unshaded/closure-compiler-unshaded-${VERSION}.jar -Djavadoc=unshaded/closure-compiler-unshaded-${VERSION}-javadoc.jar -Dsources=unshaded/closure-compiler-unshaded-${VERSION}-sources.jar -DpomFile=unshaded/pom.xml -Durl=$DEPLOY_URL -DrepositoryId=$DEPLOY_REPO_ID


cd -
rm -rf tmp