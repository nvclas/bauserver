package de.theniclas.levels.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.levels.achievements.CreateWorld;
import de.theniclas.levels.achievements.EatGoldenApple;
import de.theniclas.levels.achievements.EnterNetherPortal;
import de.theniclas.levels.achievements.FirstBlock;
import de.theniclas.levels.achievements.PlaceBlocks;
import de.theniclas.levels.achievements.SummonWither;
import de.theniclas.levels.achievements.VoidDeath;
import de.theniclas.levels.commands.CMDaddxp;
import de.theniclas.levels.commands.CMDlevel;
import de.theniclas.levels.commands.CMDlock;
import de.theniclas.levels.commands.CMDunlock;
import de.theniclas.levels.events.PlayerMove;
import de.theniclas.levels.utils.Scheduler;

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
