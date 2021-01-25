
plugins {
    kotlin("multiplatform") version "1.4.21"
}

group = "io.github.durun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val plugin = project(":plugin")
kotlin {
    linuxX64 {
        compilations.getByName("main") {
            cinterops {
                create("plugin") {
                    compilerOpts("-I${plugin.buildDir.resolve("bin/linuxX64/releaseShared")}")
                }
            }
        }
        binaries {
            executable()
        }
    }

    macosX64 {
        binaries {
            executable()
        }
    }

    mingwX64("windowsX64") {
        binaries {
            executable()
        }
    }
}

evaluationDependsOn(plugin.path)
tasks["cinteropPluginLinuxX64"].dependsOn(plugin.tasks["linkReleaseSharedLinuxX64"])

tasks.withType<Exec> {
    val os = org.gradle.internal.os.OperatingSystem.current()
    val dir = when {
        os.isLinux -> "linuxX64"
        os.isMacOsX -> "macosX64"
        os.isWindows -> "windowsX64"
        else -> throw NotImplementedError("Not available in ${os.familyName}")
    }
    val lib = plugin.buildDir.resolve("bin/$dir/releaseShared/libplugin.so")
    args(lib, "libplugin_symbols")
}

tasks.withType<Wrapper> {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}