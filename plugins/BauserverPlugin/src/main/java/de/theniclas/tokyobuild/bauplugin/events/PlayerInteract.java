package de.theniclas.tokyobuild.bauplugin.events;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (!p.hasPermission("bs.admin")) {
            if (!Vars.isTrusted(p, p.getWorld().getName())) {
                if (!Vars.isOwner(p, p.getWorld().getName())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        if (p.getInventory().getItemInMainHand().getType() != Material.AIR && p.getInventory()
                .getItemInMainHand()
                .hasItemMeta()) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                String displayName = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
                Location bloc = new Location(targetBlock.getWorld(),
                        targetBlock.getX() + 0.5,
                        targetBlock.getY(),
                        targetBlock.getZ() + 0.5);

                if (targetBlock.getWorld()
                        .getNearbyEntities(bloc, 0.5, 1, 0.5)
                        .stream()
                        .allMatch(entity -> entity instanceof Item)) {
                    switch (displayName) {
                        case "§fRoter Pilzblock" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.RED_MUSHROOM_BLOCK);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_WOOD_PLACE, 1f, 0.8f);
                        }
                        case "§fBrauner Pilzblock", "§fPilzsporenblock" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.BROWN_MUSHROOM_BLOCK);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_WOOD_PLACE, 1f, 0.8f);
                        }
                        case "§fPilzstielblock" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.MUSHROOM_STEM);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_WOOD_PLACE, 1f, 0.8f);
                        }
                        case "§fVolle Steinstufe" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.STONE_SLAB);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_STONE_PLACE, 1f, 0.8f);
                        }
                        case "§fVolle Sandsteinstufe" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.SANDSTONE_SLAB);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_STONE_PLACE, 1f, 0.8f);
                        }
                        case "§fVolle Rote Sandsteinstufe" -> {
                            e.setCancelled(true);
                            targetBlock.setType(Material.RED_SANDSTONE_SLAB);
                            p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_STONE_PLACE, 1f, 0.8f);
                        }
                    }
                }
            }
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.IRON_TRAPDOOR || e.getClickedBlock()
                    .getType() == Material.IRON_DOOR) {
                BlockState blockState = e.getClickedBlock().getState();
                Openable openable = (Openable) blockState.getBlockData();
                if (openable.isOpen()) {
                    openable.setOpen(false);
                    p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1f, 1f);
                } else {
                    openable.setOpen(true);
                    p.playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1f, 1f);
                }
                blockState.setBlockData(openable);
                blockState.update();
            }
        }
    }
}