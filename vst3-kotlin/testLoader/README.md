# testLoader

A demo program to show VST3 plugin's info.

This program depends on [VST3 wrapper for C](../vst3cw) as a static library.
And this program (loader.c) is written in plain **C** !

## Demo
- for Linux
    ```shell
    cd ..
    ./gradlew :vst3cw:createReleaseLinux
    cd testLoader/linux
    make run # or ./loader path/to/vst3plugin.so
    ```
    expect:
    ```
    ./loader ../../vst3sdk/build/vst3sdk/VST3/Release/panner.vst3/Contents/x86_64-linux/panner.so
    (loader) Success: open ../../vst3sdk/build/vst3sdk/VST3/Release/panner.vst3/Contents/x86_64-linux/panner.so
    (loader) Result: 0
    (loader) vendor=Steinberg Media Technologies, url=http://www.steinberg.net, email=mailto:info@steinberg.de, flags=16
    (loader) Classes: 2
    (loader) Class[0]: name=Panner, category=Audio Module Class, cardinality=2147483647
    (loader) Class[1]: name=PannerController, category=Component Controller Class, cardinality=2147483647
    (loader) Closed: result=0
    (loader) Success: Closed handle
    ```