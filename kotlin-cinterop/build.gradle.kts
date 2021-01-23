
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
    /**
     * Required files:
     *  /usr/lib/libtinfo.so.5
     *      arch-linux -> install "ncurses5-compat-libs" from AUR
     */
    binaries.executable()
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.8.1"
  distributionType = Wrapper.DistributionType.ALL
}