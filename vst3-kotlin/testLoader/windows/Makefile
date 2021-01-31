#CC=clang	# Compilers such as clang, gcc will be OK.
PROGRAM=loader

VST3SDK=../../vst3sdk/build
VST3=$(VST3SDK)/vst3sdk/VST3
SDK_HEADERS=$(VST3SDK)/VST_SDK/VST3_SDK

WRAPPER=../../vst3cw
WRAPPER_HEADERS=$(WRAPPER)/src/main/public
WRAPPER_LIB=$(WRAPPER)/build/lib/main/release/windows

OPT_HEADERS=-I$(SDK_HEADERS) -I$(WRAPPER_HEADERS)
OPT_LIBS=-L$(WRAPPER_LIB) -lvst3cw

.PHONY: all run clean

all: $(PROGRAM)

run: $(PROGRAM)
	./loader $(VST3)/Release/panner.vst3/Contents/x86_64-win/panner.vst3

loader: loader.c
	$(CC) $(OPT_HEADERS) $^ $(OPT_LIBS) -o $@

clean:
	rm -f $(PROGRAM)
