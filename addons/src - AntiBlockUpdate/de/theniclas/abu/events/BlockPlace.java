package de.theniclas.abu.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.theniclas.abu.main.Main;
import de.theniclas.abu.utils.Vars;

public class BlockPlace implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(Vars.config.getStringList("Worlds") != null && Vars.config.getStringList("Worlds").contains(e.getBlock().getWorld().getName())) {
			if(e.getBlock().getType() == Material.SAND || e.getBlock().getType() == Material.GRAVEL) {
				if(e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY() - 1, e.getBlock().getLocation().getBlockZ()).getType() == Material.AIR) {
					e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY() - 1, e.getBlock().getLocation().getBlockZ()).setType(Material.BARRIER);
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
						public void run() {
							e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY() - 1, e.getBlock().getLocation().getBlockZ()).setType(Material.AIR);
						}
					}, 3);
				}
			}
		}
	}
}
