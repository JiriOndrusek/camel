/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.dsl.jbang.core.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.apache.camel.dsl.jbang.core.common.RuntimeType;
import org.apache.camel.util.FileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExportCustomizePropertiesTest {

    private Path workingDir;

    @BeforeEach
    public void setup() throws IOException {
        workingDir = Files.createTempDirectory("camel-export-props");
    }

    @AfterEach
    public void end() throws IOException {
        FileUtil.removeDir(workingDir.toFile());
    }

    private static Stream<Arguments> runtimeProvider() {
        return Stream.of(
                Arguments.of(RuntimeType.quarkus),
                Arguments.of(RuntimeType.springBoot),
                Arguments.of(RuntimeType.main));
    }

    @ParameterizedTest
    @MethodSource("runtimeProvider")
    public void hookCalledAfterPropertiesWritten(RuntimeType rt) throws Exception {
        AtomicReference<Path> capturedBuildDir = new AtomicReference<>();
        AtomicReference<RuntimeType> capturedRuntime = new AtomicReference<>();

        // buildDir/src/main/resources is the targetDir passed to copySettingsAndProfile
        Path buildDir = workingDir.resolve("build");
        Path targetDir = buildDir.resolve("src/main/resources");

        // create a minimal settings file
        Path settings = workingDir.resolve("settings.properties");
        Files.writeString(settings, "camel.main.name=test\n", StandardCharsets.UTF_8);

        TestableExport cmd = new TestableExport(rt, (dir, runtime) -> {
            capturedBuildDir.set(dir);
            capturedRuntime.set(runtime);
        });

        cmd.copySettingsAndProfile(settings, workingDir.resolve("profile.properties"), targetDir, null);

        Path appProps = targetDir.resolve("application.properties");
        assertThat(appProps).exists();
        assertThat(capturedBuildDir.get()).isEqualTo(buildDir);
        assertThat(capturedRuntime.get()).isEqualTo(rt);
    }

    @Test
    public void hookSkippedWhenPluginsDisabled() throws Exception {
        AtomicReference<Path> capturedBuildDir = new AtomicReference<>();

        Path buildDir = workingDir.resolve("build");
        Path targetDir = buildDir.resolve("src/main/resources");
        Path settings = workingDir.resolve("settings.properties");
        Files.writeString(settings, "camel.main.name=test\n", StandardCharsets.UTF_8);

        TestableExport cmd = new TestableExport(RuntimeType.quarkus, (dir, runtime) -> {
            capturedBuildDir.set(dir);
        });
        cmd.skipPlugins = true;

        cmd.copySettingsAndProfile(settings, workingDir.resolve("profile.properties"), targetDir, null);

        assertThat(capturedBuildDir.get()).isNull();
    }

    @Test
    public void hookSkippedWhenRuntimeNull() throws Exception {
        AtomicReference<Path> capturedBuildDir = new AtomicReference<>();

        Path buildDir = workingDir.resolve("build");
        Path targetDir = buildDir.resolve("src/main/resources");
        Path settings = workingDir.resolve("settings.properties");
        Files.writeString(settings, "camel.main.name=test\n", StandardCharsets.UTF_8);

        TestableExport cmd = new TestableExport(null, (dir, runtime) -> {
            capturedBuildDir.set(dir);
        });

        cmd.copySettingsAndProfile(settings, workingDir.resolve("profile.properties"), targetDir, null);

        assertThat(capturedBuildDir.get()).isNull();
    }

    @FunctionalInterface
    interface HookCallback {
        void onHook(Path buildDir, RuntimeType runtime);
    }

    static class TestableExport extends ExportBaseCommand {

        private final HookCallback callback;

        TestableExport(RuntimeType rt, HookCallback callback) {
            super(new CamelJBangMain());
            this.runtime = rt;
            this.callback = callback;
        }

        @Override
        protected Integer export() {
            return 0;
        }

        @Override
        protected void invokeCustomizeExportedProperties(Path buildDir) {
            if (!skipPlugins && runtime != null) {
                callback.onHook(buildDir, runtime);
            }
        }
    }
}
