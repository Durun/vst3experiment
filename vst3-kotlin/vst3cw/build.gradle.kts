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
ext["includeDir"] = projectDir.resolve("src/main/public").absolutePath
ext["libraryDir"] = buildDir.resolve("lib/main/release/linux").absolutePath
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

tasks.withType<CppCompile> {
	dependsOn(vst3sdk.tasks["cmakeBuild"])
}

tasks.withType<Wrapper> {
	gradleVersion = "6.8.1"
	distributionType = Wrapper.DistributionType.ALL
}