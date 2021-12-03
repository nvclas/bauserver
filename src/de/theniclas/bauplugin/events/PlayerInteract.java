package de.theniclas.bauplugin.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import de.theniclas.bauplugin.utils.Vars;

public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		if(!p.hasPermission("bs.admin")) {
			if(!Vars.isTrusted(p, p.getWorld().getName())) {
				if(!Vars.isOwner(p, p.getWorld().getName())) {
					e.setCancelled(true);
					return;
				}
			}
		}
		if(p.getItemInHand() != null && p.getItemInHand().getType() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName() != null) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fRoter Pilzblock")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.HUGE_MUSHROOM_2);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_WOOD, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fBrauner Pilzblock")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.HUGE_MUSHROOM_1);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_WOOD, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fPilzsporenblock")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.HUGE_MUSHROOM_1);
						e.getClickedBlock().getRelative(e.getBlockFace()).setData((byte) 0);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_WOOD, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fPilzstielblock")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.HUGE_MUSHROOM_1);
						e.getClickedBlock().getRelative(e.getBlockFace()).setData((byte) 15);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_WOOD, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fVolle Steinstufe")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.DOUBLE_STEP);
						e.getClickedBlock().getRelative(e.getBlockFace()).setData((byte) 8);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_STONE, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fVolle Sandsteinstufe")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.DOUBLE_STEP);
						e.getClickedBlock().getRelative(e.getBlockFace()).setData((byte) 9);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_STONE, 1f, 0.8f);
					}
					
				} else if(p.getItemInHand().getItemMeta().getDisplayName().equals("§fVolle Rote Sandsteinstufe")) {
					e.setCancelled(true);
					Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
					Location bloc = new Location(targetBlock.getWorld(), targetBlock.getX() + 0.5, targetBlock.getY(), targetBlock.getZ() + 0.5);
					if(targetBlock.getWorld().getNearbyEntities(bloc, 0.5, 1, 0.5).stream().noneMatch(entity -> !(entity instanceof Item))) {
						e.getClickedBlock().getRelative(e.getBlockFace()).setType(Material.DOUBLE_STONE_SLAB2);
						e.getClickedBlock().getRelative(e.getBlockFace()).setData((byte) 8);
						p.playSound(e.getClickedBlock().getLocation(), Sound.DIG_STONE, 1f, 0.8f);
					}
				}
			}
		}
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.IRON_TRAPDOOR || e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
				BlockState blockState = e.getClickedBlock().getState();
				if(e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
					if(((Door) blockState.getData()).isTopHalf()){
					    blockState = e.getClickedBlock().getRelative(BlockFace.DOWN).getState();
					}
				}
				Openable openable = (Openable) blockState.getData();
				if(openable.isOpen()) {
					openable.setOpen(false);
					p.playSound(e.getClickedBlock().getLocation(), Sound.DOOR_CLOSE, 1f, 1f);
				} else {
					openable.setOpen(true);
					p.playSound(e.getClickedBlock().getLocation(), Sound.DOOR_OPEN, 1f, 1f);
				}
				blockState.setData((MaterialData) openable);	
				blockState.update();
			}
		}
	}
}