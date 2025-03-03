package de.nvclas.tokyobuild.levels.achievements;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.nvclas.tokyobuild.levels.utils.Methods;

public class PlaceBlocks implements Listener {
	
	public static final HashMap<Player, Integer> placedBlocks = new HashMap<>();
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(!e.isCancelled()) {
			if(!placedBlocks.containsKey(p)) {
				placedBlocks.put(p, 1);
			}
			int amount = placedBlocks.get(p);
			placedBlocks.put(p, amount + 1);
			if(amount >= 500) {
				Methods.addAchievement(p, "G�nn dir mal 'ne Pause", 75);
			}
        }
	}
}