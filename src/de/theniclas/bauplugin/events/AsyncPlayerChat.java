package de.theniclas.bauplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.bauplugin.utils.WorldMaker;

public class AsyncPlayerChat implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		if(Vars.voidWorldName.contains(p) || Vars.flatWorldName.contains(p) || Vars.normalWorldName.contains(p)) {
			e.setCancelled(true);
			String message = e.getMessage();
			ItemStack icon = p.getInventory().getItemInHand();
			if(message.matches("[^a-zA-Z0-9]") || message.contains(" ") || message.contains("%") || message.contains("/") || message.length() > 16) {
				p.sendMessage(Vars.pr + "§cDer Weltenname darf maximal 16 Zeichen besitzen und keine Leerzeichen oder unerlaubte Symbole enthalten");
			} else if(message.equalsIgnoreCase("abbrechen") || message.equalsIgnoreCase("abbruch") || message.equalsIgnoreCase("stop") || message.equalsIgnoreCase("stopp")) {
				Vars.voidWorldName.remove(p);
				Vars.flatWorldName.remove(p);
				Vars.normalWorldName.remove(p);
				p.sendMessage(Vars.pr + "§aWeltenerstellung abgebrochen");
			} else if(Configs.worldsConfig.getConfigurationSection("Worlds") != null && Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false).contains(e.getMessage())) {
				p.sendMessage(Vars.pr + "§cEs existiert bereits eine Welt mit diesem Namen");
			} else {
				if(p.getItemInHand() != null && p.getItemInHand().getType() != null && p.getItemInHand().getType() != Material.AIR) {
					if(Vars.voidWorldName.contains(p)) {
						Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
							public void run() {
								WorldMaker.createVoidWorld(message, p, icon);
							}
						});
					} else if(Vars.flatWorldName.contains(p)) {
						Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
							public void run() {
								WorldMaker.createFlatWorld(message, p, icon);
							}
						});
					} else if(Vars.normalWorldName.contains(p)) {
						Bukkit.getScheduler().runTask(Main.getPlugin(), new Runnable() {
							public void run() {
								WorldMaker.createNormalWorld(message, p, icon);
							}
						});
					}
				} else {
					p.sendMessage(Vars.pr + "§cBitte halte ein Item für das Welticon in der Hand");
				}
			}
		}
	}
}
