package maru.restarter;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;

public class Main extends PluginBase {

	@Override
	public void onEnable() {
		getServer().getScheduler().scheduleAsyncTask(new AsyncTask() {

			@Override
			public void onRun() {
				while (true) {
					if (Runtime.getRuntime().totalMemory() / 1024 / 1024 > (Runtime.getRuntime().maxMemory() / 1024 / 1024) - 256) {
						getLogger().critical("Start server restarting cause memory leak!");
						getServer().forceShutdown();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// ignore
					}
				}
			}
		});
	}
}
