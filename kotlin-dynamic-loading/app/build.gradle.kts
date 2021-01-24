
plugins {
    kotlin("multiplatform") version "1.4.21"
}

group = "io.github.durun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    commonMainImplementation(kotlin("stdlib-common"))
}

kotlin {
    linuxX64 {
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

tasks.withType<Wrapper> {
    gradleVersion = "6.8.1"
    distributionType = Wrapper.DistributionType.ALL
}