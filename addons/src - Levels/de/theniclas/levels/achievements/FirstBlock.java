package de.theniclas.levels.achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.theniclas.levels.utils.Methods;

public class FirstBlock implements Listener {
	@EventHandler
	public void onFirstBlock(BlockPlaceEvent e)  {
		Player p = e.getPlayer();
		if(!e.isCancelled()) {
			if(Methods.isUnlocked(p)) {
				Methods.addAchievement(p, "Du weiﬂt wie es geht!", 10);
			}				
		}
	}
}