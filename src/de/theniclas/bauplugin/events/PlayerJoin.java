package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class PlayerJoin implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e)  {
		
		Player p = e.getPlayer();
		
		e.setJoinMessage("§9" + p.getName() + " §7hat den Server betreten");
		
		if(!p.hasPlayedBefore()) {
			e.setJoinMessage("§9" + p.getName() + " §7ist neu beigetreten");
			p.sendMessage(Vars.pr + "§aJoine auf unseren Discord, um dich freizuschalten!");
			p.sendMessage(Vars.pr + "§aKlicke hier: @ehttps://discord.gg/FjHUFJe6ny");
		}
		p.setFoodLevel(20);
		p.setHealth(20);
		
		World w = Bukkit.getWorld(Configs.worldsConfig.getString("Spawn.World"));
		Double x = Configs.worldsConfig.getDouble("Spawn.X");
		Double y = Configs.worldsConfig.getDouble("Spawn.Y");
		Double z = Configs.worldsConfig.getDouble("Spawn.Z");
		
		Location loc = new Location(w, x, y, z);
		p.teleport(loc);
	}
}
