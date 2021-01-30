group = "io.github.durun"
version = "1.0-SNAPSHOT"

plugins {
	// Apply the cpp-library plugin to add support for building C++ libraries
	`cpp-library`

	// Apply the cpp-unit-test plugin to add support for building and running C++ test executables
	`cpp-unit-test`
}

val vst3sdk = project(":vst3sdk")
evaluationDependsOn(vst3sdk.path)
library {
	dependencies {
		api(files(vst3sdk.ext["includeDir"]))
		implementation(files(vst3sdk.ext["libraryDir"]))
	}
	// Set the target operating system and architecture for this library
	targetMachines.add(machines.linux.x86_64)
	targetMachines.add(machines.macOS.x86_64)
	targetMachines.add(machines.windows.x86_64)
	linkage.set(listOf(Linkage.STATIC))
}

// Publish ext["includeDir"], ext["libraryDir"]
ext {
	val os = org.gradle.internal.os.OperatingSystem.current()
	val dir = when {
		os.isLinux -> "linux"
		os.isMacOsX -> "macos"
		os.isWindows -> "windows"
		else -> throw NotImplementedError("Not available in ${os.familyName}")
	}
	val lib = when {
		os.isLinux -> "libvst3cw.a"
		os.isMacOsX -> "libvst3cw.a"
		os.isWindows -> "vst3cw.lib"
		else -> throw NotImplementedError("Not available in ${os.familyName}")
	}
	set("includeDir", projectDir.resolve("src/main/public").absolutePath)
	set("libraryDir", buildDir.resolve("lib/main/release/$dir").absolutePath)
	set("staticLib", lib)
}

tasks.withType<CppCompile> {
	dependsOn(vst3sdk.tasks["cmakeBuild"])
}

tasks.withType<Wrapper> {
	gradleVersion = "6.8.1"
	distributionType = Wrapper.DistributionType.ALL
}