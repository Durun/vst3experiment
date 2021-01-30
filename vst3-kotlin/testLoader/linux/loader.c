#include <dlfcn.h>
#include <stdio.h>

#include <IPluginFactory.h>

int main(int argc, char const *argv[]) {
	if (argc < 2) {
		printf("Usage: %s plugin\n", argv[0]);
		return 1;
	}
	const char *pluginName = argv[1];    // plugin name

	/*
	 * Open [plugin] with RTLD_LAZY mode.
	 * If [plugin] contains "/", [plugin] is interpreted as filepath.
	 * see also: man dlopen(3)
	 */
	void *plugin = dlopen(pluginName, RTLD_LAZY);  // not const
	if (plugin == NULL) {
		const char *message = dlerror();
		printf("(loader) Failed: open %s: %s\n", pluginName, message);
		return 1;
	}
	printf("(loader) Success: open %s\n", pluginName);

	/*
	 * Get the function symbol with plugin [handle] and [funcName].
	 * First, clean error message by calling dlerror()
	 * because dlsym() returns error message via dlerror().
	 * see also: man dlopen(3)
	 */
	dlerror(); // clean error message
	IPluginFactoryGetter proc = (IPluginFactoryGetter) dlsym(plugin, "GetPluginFactory");
	{
		const char *message = dlerror();
		if (message) {
			printf("(loader) Failed: get GetPluginFactory: %s\n", message);
		}
	}

	/**
	 * Get IPluginFactory
	 */
	IPluginFactory *factory = proc();
	PFactoryInfo fInfo;
	tresult result = IPluginFactory_getFactoryInfo(factory, &fInfo);
	printf("(loader) Result: %d\n", result);
	printf("(loader) vendor=%s, url=%s, email=%s, flags=%d\n", fInfo.vendor, fInfo.url, fInfo.email, fInfo.flags);
		int32 nClasses = IPluginFactory_countClasses(factory);
		printf("(loader) Classes: %d\n", nClasses);
		for (int32 i = 0; i < nClasses; i++) {
		PClassInfo cInfo;
		IPluginFactory_getClassInfo(factory, i, &cInfo);
		printf("(loader) Class[%d]: name=%s, category=%s, cardinality=%d\n", i, cInfo.name, cInfo.category, cInfo.cardinality);
	}


	/**
	 * Close the plugin.
	 */
	result = IPluginFactory_release(factory);
	printf("(loader) Closed: result=%d\n", result);
	dlclose(plugin);
	printf("(loader) Success: Closed handle\n");
	return 0;
}
