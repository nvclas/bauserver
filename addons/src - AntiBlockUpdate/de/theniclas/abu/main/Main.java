package de.theniclas.abu.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.abu.commands.CMDabu;
import de.theniclas.abu.events.BlockFromTo;
import de.theniclas.abu.events.BlockPhysics;
import de.theniclas.abu.events.BlockPlace;
import de.theniclas.abu.utils.Vars;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		this.getCommand("abu").setExecutor(new CMDabu());
		
		Bukkit.getPluginManager().registerEvents(new BlockPhysics(), this);
		Bukkit.getPluginManager().registerEvents(new BlockPlace(), this);
		Bukkit.getPluginManager().registerEvents(new BlockFromTo(), this);
		
		Vars.loadConfiguration();
		
		System.out.println("AntiBlockUpdate geladen");
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
}
