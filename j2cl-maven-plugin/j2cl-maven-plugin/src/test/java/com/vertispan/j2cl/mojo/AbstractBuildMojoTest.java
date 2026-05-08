/*
 * Copyright © 2024 j2cl-maven-plugin authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vertispan.j2cl.mojo;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.DefaultArtifactHandler;
import org.apache.maven.artifact.versioning.VersionRange;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for AbstractBuildMojo, focusing on SNAPSHOT dependency resolution behavior.
 */
public class AbstractBuildMojoTest {

    /**
     * Helper to invoke the private static key() method via reflection.
     */
    private String invokeKeyMethod(Artifact artifact) throws Exception {
        Method keyMethod = AbstractBuildMojo.class.getDeclaredMethod("key", Artifact.class);
        keyMethod.setAccessible(true);
        return (String) keyMethod.invoke(null, artifact);
    }

    @Test
    public void testKeyForReleaseArtifact() throws Exception {
        Artifact artifact = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0.0"),
                "compile",
                "jar",
                null,
                new DefaultArtifactHandler("jar")
        );
        artifact.setVersion("1.0.0");

        String key = invokeKeyMethod(artifact);
        assertEquals("com.example:my-artifact:1.0.0", key);
    }

    @Test
    public void testKeyForReleaseArtifactWithClassifier() throws Exception {
        Artifact artifact = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0.0"),
                "compile",
                "jar",
                "sources",
                new DefaultArtifactHandler("jar")
        );
        artifact.setVersion("1.0.0");

        String key = invokeKeyMethod(artifact);
        assertEquals("com.example:my-artifact:1.0.0:sources", key);
    }

    @Test
    public void testKeyForSnapshotWithoutResolvedVersion() throws Exception {
        // Simulates a SNAPSHOT that hasn't been resolved yet (e.g., reactor project)
        Artifact artifact = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0-SNAPSHOT"),
                "compile",
                "jar",
                null,
                new DefaultArtifactHandler("jar")
        );
        artifact.setVersion("1.0-SNAPSHOT");

        String key = invokeKeyMethod(artifact);
        // Should use base version when resolved version equals base version
        assertEquals("com.example:my-artifact:1.0-SNAPSHOT", key);
    }

    @Test
    public void testKeyForSnapshotWithResolvedVersion() throws Exception {
        // Simulates a SNAPSHOT that has been resolved to a timestamped version
        Artifact artifact = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0-SNAPSHOT"),
                "compile",
                "jar",
                null,
                new DefaultArtifactHandler("jar")
        );
        artifact.setVersion("1.0-20260507.070000-1");

        String key = invokeKeyMethod(artifact);
        // Should use resolved timestamped version for unique identification
        assertEquals("com.example:my-artifact:1.0-20260507.070000-1", key);
    }

    @Test
    public void testKeyForDifferentSnapshotBuilds() throws Exception {
        // Create two artifacts representing different builds of the same SNAPSHOT
        Artifact snapshot1 = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0-SNAPSHOT"),
                "compile",
                "jar",
                null,
                new DefaultArtifactHandler("jar")
        );
        snapshot1.setVersion("1.0-20260507.070000-1");

        Artifact snapshot2 = new DefaultArtifact(
                "com.example",
                "my-artifact",
                VersionRange.createFromVersion("1.0-SNAPSHOT"),
                "compile",
                "jar",
                null,
                new DefaultArtifactHandler("jar")
        );
        snapshot2.setVersion("1.0-20260507.080000-2");

        String key1 = invokeKeyMethod(snapshot1);
        String key2 = invokeKeyMethod(snapshot2);

        // Different SNAPSHOT builds should have different keys
        assertNotEquals("Different SNAPSHOT builds should have different keys", key1, key2);
        assertEquals("com.example:my-artifact:1.0-20260507.070000-1", key1);
        assertEquals("com.example:my-artifact:1.0-20260507.080000-2", key2);
    }
}
