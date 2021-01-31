# testLoader

A demo program to show VST3 plugin's info.

This program depends on [VST3 wrapper for C](../vst3cw) as a static library.
And this program (loader.c) is written in plain **C** !

## Requirements
- x86_64 architecture
- Linux or MacOS
- cmake (>=3.5)
- make
- C++ compiler (such as clang)
- (on Ubuntu) `"libstdc++6" libx11-xcb-dev libxcb-util-dev libxcb-cursor-dev libxcb-xkb-dev libxkbcommon-dev libxkbcommon-x11-dev libfontconfig1-dev libcairo2-dev libgtkmm-3.0-dev libsqlite3-dev libxcb-keysyms1-dev`
  - https://steinbergmedia.github.io/vst3_doc/vstinterfaces/linuxSetup.html
## Demo
- for Linux
    ```shell
    cd ..
    ./gradlew :vst3cw:createReleaseLinux
    cd testLoader/linux
    make run # or ./loader path/to/vst3plugin.so
    ```
    <details>
    <summary>expect:</summary>
      <pre><code>
      ./loader ../../vst3sdk/build/vst3sdk/VST3/Release/helloworldWithVSTGUI.vst3/Contents/x86_64-linux/helloworldWithVSTGUI.so
      (loader) Success: open ../../vst3sdk/build/vst3sdk/VST3/Release/helloworldWithVSTGUI.vst3/Contents/x86_64-linux/helloworldWithVSTGUI.so
      (loader) Result: 0
      (loader) vendor=Steinberg Media Technologies, url=http://www.steinberg.net, email=mailto:info@steinberg.de, flags=16
      (loader) Classes: 2
      (loader) Class[0]: name=Hello World With UI, category=Audio Module Class, cardinality=2147483647
      (loader) Class[1]: name=Hello World With UIController, category=Component Controller Class, cardinality=2147483647
      (loader) Closed: result=0
      (loader) Success: Closed handle
      </code></pre>
    </details>
- for MacOS
    ```shell
    cd ..
    ./gradlew :vst3cw:createReleaseMacos
    cd testLoader/macos
    make run # or ./loader path/to/vst3plugin.dylib
    ```
    <details>
    <summary>expect:</summary>
      <pre><code>
      ./loader ../../vst3sdk/build/vst3sdk/VST3/Release/helloworldWithVSTGUI.vst3/Contents/MacOS/helloworldWithVSTGUI
      (loader) Success: open ../../vst3sdk/build/vst3sdk/VST3/Release/helloworldWithVSTGUI.vst3/Contents/MacOS/helloworldWithVSTGUI
      (loader) Result: 0
      (loader) vendor=Steinberg Media Technologies, url=http://www.steinberg.net, email=mailto:info@steinberg.de, flags=16
      (loader) Classes: 2
      (loader) Class[0]: name=Hello World With UI, category=Audio Module Class, cardinality=2147483647
      (loader) Class[1]: name=Hello World With UIController, category=Component Controller Class, cardinality=2147483647
      (loader) Closed: result=0
      (loader) Success: Closed handle
      </code></pre>
    </details>