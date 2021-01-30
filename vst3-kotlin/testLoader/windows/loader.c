#include <windows.h>
#include <stdio.h>

#include <IPluginFactory.h>

int main(int argc, char const *argv[]) {
	if (argc < 2) {
		printf("Usage: %s plugin\n", argv[0]);
		return 1;
	}
	const char *pluginName = argv[1];    // plugin name

	/*
	 * Open [plugin].
	 */
    HINSTANCE plugin = LoadLibrary(TEXT(pluginName));
	if (plugin == NULL) {
		printf("(loader) Failed: open %s\n", pluginName);
		return 1;
	}
	printf("(loader) Success: open %s\n", pluginName);

	/*
	 * Get the function symbol.
	 */
	IPluginFactoryGetter proc = (IPluginFactoryGetter) GetProcAddress(hinstLib, "GetPluginFactory");
    if (proc == NULL) {
    	printf("(loader) Failed: get GetPluginFactory\n");
    } else {
		/*
		 * Get IPluginFactory
		 */
		IPluginFactory *factory = proc();
		PFactoryInfo fInfo;
		tresult result = IPluginFactory_getFactoryInfo(factory, &fInfo);
		printf("(loader) Result: %d\n", result);

		/*
		 * Show plugin info
		 */
		printf("(loader) vendor=%s, url=%s, email=%s, flags=%d\n", fInfo.vendor, fInfo.url, fInfo.email, fInfo.flags);
		int32 nClasses = IPluginFactory_countClasses(factory);
		printf("(loader) Classes: %d\n", nClasses);
		for (int32 i = 0; i < nClasses; i++) {
			PClassInfo cInfo;
			IPluginFactory_getClassInfo(factory, i, &cInfo);
			printf("(loader) Class[%d]: name=%s, category=%s, cardinality=%d\n", i, cInfo.name, cInfo.category, cInfo.cardinality);
		}
		/**
		 * Close the IPluginFactory.
		 */
		result = IPluginFactory_release(factory);
		printf("(loader) Closed: result=%d\n", result);
    }

    /**
     * Close the plugin.
     */
    FreeLibrary(plugin);
	printf("(loader) Success: Closed handle\n");
    return 0;
}