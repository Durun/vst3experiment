# Kotlin C Interop demo

## Requirement
- x86_64 architecture
- Linux or MacOS

## Demo
- on Linux
    ```shell
    ./gradlew runReleaseExecutableLinuxX64
    ```
- on MacOS
    ```shell
    ./gradlew runReleaseExecutableMacosX64
    ```

## Project overview
- **[cinterop](build.gradle.kts)**
  builds *native executable (`build/bin`)*
  from [Kotlin source](src/linuxX64Main), 
  [library definition](src/nativeInterop/cinterop),
  *native library (`myplugin/build/lib/main/release`)*
- **[myplugin](myplugin/build.gradle.kts)** 
  builds *native library (`myplugin/build/lib/main/release`)*
  from [C++ source](myplugin/src/main/cpp),
  [C header](myplugin/src/main/public)