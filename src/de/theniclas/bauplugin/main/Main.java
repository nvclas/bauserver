package de.theniclas.bauplugin.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.theniclas.bauplugin.commands.CMDaddspawn;
import de.theniclas.bauplugin.commands.CMDblocks;
import de.theniclas.bauplugin.commands.CMDfly;
import de.theniclas.bauplugin.commands.CMDglobalspawn;
import de.theniclas.bauplugin.commands.CMDgm;
import de.theniclas.bauplugin.commands.CMDping;
import de.theniclas.bauplugin.commands.CMDprepare;
import de.theniclas.bauplugin.commands.CMDsetowner;
import de.theniclas.bauplugin.commands.CMDspawn;
import de.theniclas.bauplugin.commands.CMDspeed;
import de.theniclas.bauplugin.commands.CMDtools;
import de.theniclas.bauplugin.commands.CMDtpa;
import de.theniclas.bauplugin.commands.CMDtpaccept;
import de.theniclas.bauplugin.commands.CMDtrust;
import de.theniclas.bauplugin.commands.CMDtrusted;
import de.theniclas.bauplugin.commands.CMDuntrust;
import de.theniclas.bauplugin.commands.CMDvisibility;
import de.theniclas.bauplugin.commands.CMDwkick;
import de.theniclas.bauplugin.commands.CMDworldlock;
import de.theniclas.bauplugin.commands.CMDworlds;
import de.theniclas.bauplugin.events.AsyncPlayerChat;
import de.theniclas.bauplugin.events.BlockBreak;
import de.theniclas.bauplugin.events.BlockPlace;
import de.theniclas.bauplugin.events.EntityDamageByEntity;
import de.theniclas.bauplugin.events.FoodLevelChange;
import de.theniclas.bauplugin.events.HangingBreakByEntity;
import de.theniclas.bauplugin.events.InventoryClick;
import de.theniclas.bauplugin.events.PlayerChangedWorld;
import de.theniclas.bauplugin.events.PlayerCommandPreprocess;
import de.theniclas.bauplugin.events.PlayerDeath;
import de.theniclas.bauplugin.events.PlayerDropItem;
import de.theniclas.bauplugin.events.PlayerInteract;
import de.theniclas.bauplugin.events.PlayerInteractAtEntity;
import de.theniclas.bauplugin.events.PlayerJoin;
import de.theniclas.bauplugin.events.PlayerPickupItem;
import de.theniclas.bauplugin.events.PlayerQuit;
import de.theniclas.bauplugin.events.PlayerRespawn;
import de.theniclas.bauplugin.events.WeatherChange;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		this.getCommand("gm").setExecutor(new CMDgm());
		this.getCommand("speed").setExecutor(new CMDspeed());
		this.getCommand("blocks").setExecutor(new CMDblocks());
		this.getCommand("tools").setExecutor(new CMDtools());
		this.getCommand("fly").setExecutor(new CMDfly());
		this.getCommand("worlds").setExecutor(new CMDworlds());
		this.getCommand("trust").setExecutor(new CMDtrust());
		this.getCommand("untrust").setExecutor(new CMDuntrust());
		this.getCommand("tpa").setExecutor(new CMDtpa());
		this.getCommand("tpaccept").setExecutor(new CMDtpaccept());
		this.getCommand("ping").setExecutor(new CMDping());
		this.getCommand("addspawn").setExecutor(new CMDaddspawn());
		this.getCommand("globalspawn").setExecutor(new CMDglobalspawn());
		this.getCommand("spawn").setExecutor(new CMDspawn());
		this.getCommand("visibility").setExecutor(new CMDvisibility());
		this.getCommand("prepare").setExecutor(new CMDprepare());
		this.getCommand("worldlock").setExecutor(new CMDworldlock());
		this.getCommand("wkick").setExecutor(new CMDwkick());
		this.getCommand("trusted").setExecutor(new CMDtrusted());
		this.getCommand("setowner").setExecutor(new CMDsetowner());
		
		getServer().getPluginManager().registerEvents(new InventoryClick(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
		getServer().getPluginManager().registerEvents(new PlayerPickupItem(), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractAtEntity(), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);
		getServer().getPluginManager().registerEvents(new HangingBreakByEntity(), this);
		getServer().getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new WeatherChange(), this);
		getServer().getPluginManager().registerEvents(new PlayerChangedWorld(), this);
		getServer().getPluginManager().registerEvents(new FoodLevelChange(), this);
		
		Configs.loadConfiguration();
		Vars.loadGlobalSpawnWorld();
			
		System.out.println("Bauserver-Plugin gestartet");
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
}
