
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
    compilations.getByName("main") {
      cinterops {
        create("myplugin")
      }
    }
    /**
     * Required files:
     *  /usr/lib/libtinfo.so.5
     *      arch-linux -> install "ncurses5-compat-libs" from AUR
     */
    binaries {
      executable()
    }
  }

  macosX64 {
    compilations.getByName("main") {
      cinterops {
        create("myplugin")
      }
    }
    binaries {
      executable()
    }
  }

  mingwX64("windowsX64") {
    compilations.getByName("main") {
      cinterops {
        create("myplugin")
      }
    }
    binaries {
      executable()
    }
  }
}
tasks.findByName("cinteropMypluginLinuxX64")?.apply {
  tasks.findByPath("myplugin:createReleaseLinux")?.let { dependsOn(it) }
}
tasks.findByName("cinteropMypluginMacosX64")?.apply {
  tasks.findByPath("myplugin:createReleaseMacos")?.let { dependsOn(it) }
}
tasks.findByName("cinteropMypluginWindowsX64")?.apply {
  tasks.findByPath("myplugin:createReleaseWindows")?.let { dependsOn(it) }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.8.1"
  distributionType = Wrapper.DistributionType.ALL
}