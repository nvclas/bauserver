package de.nvclas.tokyobuild.levels.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.nvclas.tokyobuild.levels.achievements.CreateWorld;
import de.nvclas.tokyobuild.levels.achievements.EatGoldenApple;
import de.nvclas.tokyobuild.levels.achievements.EnterNetherPortal;
import de.nvclas.tokyobuild.levels.achievements.FirstBlock;
import de.nvclas.tokyobuild.levels.achievements.PlaceBlocks;
import de.nvclas.tokyobuild.levels.achievements.SummonWither;
import de.nvclas.tokyobuild.levels.achievements.VoidDeath;
import de.nvclas.tokyobuild.levels.commands.CMDaddxp;
import de.nvclas.tokyobuild.levels.commands.CMDlevel;
import de.nvclas.tokyobuild.levels.commands.CMDlock;
import de.nvclas.tokyobuild.levels.commands.CMDunlock;
import de.nvclas.tokyobuild.levels.events.PlayerMove;
import de.nvclas.tokyobuild.levels.utils.Scheduler;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		//Commands
		this.getCommand("level").setExecutor(new CMDlevel());
		this.getCommand("unlock").setExecutor(new CMDunlock());
		this.getCommand("lock").setExecutor(new CMDlock());
		this.getCommand("addxp").setExecutor(new CMDaddxp());

		//Achievements
		Bukkit.getPluginManager().registerEvents(new FirstBlock(), this);
		Bukkit.getPluginManager().registerEvents(new VoidDeath(), this);
		Bukkit.getPluginManager().registerEvents(new CreateWorld(), this);
		Bukkit.getPluginManager().registerEvents(new PlaceBlocks(), this);
		Bukkit.getPluginManager().registerEvents(new EatGoldenApple(), this);
		Bukkit.getPluginManager().registerEvents(new SummonWither(), this);
		Bukkit.getPluginManager().registerEvents(new EnterNetherPortal(), this);
		
		//Events
		Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
		
		//Miscellaneous
		Scheduler.startScheduler();
		
		System.out.println("Levelplugin geladen");
	}

	public static Main getPlugin() {
		
		return plugin;
		
	}
}
