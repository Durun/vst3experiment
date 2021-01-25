
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
        compilations.getByName("main") {
            cinterops {
                create("plugin") {
                    compilerOpts("-I${plugin.buildDir.resolve("bin/macosX64/releaseShared")}")
                }
            }
        }
        binaries {
            executable()
        }
    }

    mingwX64("windowsX64") {
        compilations.getByName("main") {
            cinterops {
                create("plugin") {
                    compilerOpts("-I${plugin.buildDir.resolve("bin/windowsX64/releaseShared")}")
                }
            }
        }
        binaries {
            executable()
        }
    }
}

evaluationDependsOn(plugin.path)
tasks.findByName("cinteropPluginLinuxX64")?.apply {
    plugin.tasks.findByName("linkReleaseSharedLinuxX64")?.let { dependsOn(it) }
}
tasks.findByName("cinteropPluginMacosX64")?.apply {
    plugin.tasks.findByName("linkReleaseSharedMacosX64")?.let { dependsOn(it) }
}
tasks.findByName("cinteropPluginWindowsX64")?.apply {
    plugin.tasks.findByName("linkReleaseSharedWindowsX64")?.let { dependsOn(it) }
}

tasks.withType<Exec> {
    val os = org.gradle.internal.os.OperatingSystem.current()
    val dir = when {
        os.isLinux -> "linuxX64"
        os.isMacOsX -> "macosX64"
        os.isWindows -> "windowsX64"
        else -> throw NotImplementedError("Not available in ${os.familyName}")
    }
    val file = when {
        os.isLinux -> "libplugin.so"
        os.isMacOsX -> "libplugin.dylib"
        os.isWindows -> "plugin.dll" // and "plugin.def"
        else -> throw NotImplementedError("Not available in ${os.familyName}")
    }
    val lib = plugin.buildDir.resolve("bin/$dir/releaseShared/$file")
    args(lib, "libplugin_symbols")
}

tasks.withType<Wrapper> {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}