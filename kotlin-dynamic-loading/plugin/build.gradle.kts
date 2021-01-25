
plugins {
    kotlin("multiplatform") version "1.4.21"
}

group = "io.github.durun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    linuxX64 {
        binaries {
            sharedLib {
                baseName = project.name
            }
        }
    }
    macosX64 {
        binaries {
            sharedLib {
                baseName = project.name
            }
        }
    }
    mingwX64("windowsX64") {
        binaries {
            sharedLib {
                baseName = project.name
            }
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}