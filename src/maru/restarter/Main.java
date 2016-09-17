package maru.restarter;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

public class Main extends PluginBase {
	private Config config;
	
	@SuppressWarnings("serial")
	@Override
	public void onEnable() {
		getDataFolder().mkdirs();
		config = new Config(getDataFolder() + "/config.yml", Config.YAML, new ConfigSection() {
			{
				put("restart-message", "Start server restarting cause memory leak!");
			}
		});
		
		getServer().getScheduler().scheduleAsyncTask(new AsyncTask() {

			@Override
			public void onRun() {
				while (true) {
					if (Runtime.getRuntime().totalMemory() / 1024 / 1024 > (Runtime.getRuntime().maxMemory() / 1024 / 1024) - 256) {
						getLogger().critical(config.getString("restart-message"));
						getServer().getOnlinePlayers().forEach((uuid, player) -> {
							player.kick(config.getString("restart-message"), false);
						});
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
