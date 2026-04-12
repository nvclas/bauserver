package de.theniclas.bauplugin.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Slab;
import MultipleFacing;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.theniclas.bauplugin.utils.Vars;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        // Guard: only owners, trusted players and admins may interact
        if (!p.hasPermission("bs.admin")
                && !Vars.isTrusted(p, p.getWorld().getName())
                && !Vars.isOwner(p, p.getWorld().getName())) {
            e.setCancelled(true);
            return;
        }

        // Special block placement via held item
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            ItemStack held = p.getInventory().getItemInHand();
            if (held != null && held.hasItemMeta()) {
                ItemMeta meta = held.getItemMeta();
                if (meta.hasDisplayName()) {
                    handleSpecialBlock(e, p, meta.getDisplayName());
                    if (e.isCancelled()) return;
                }
            }

            // Iron door / iron trapdoor toggling (left open by default in vanilla)
            handleIronDoorToggle(e, p);
        }
    }

    // -------------------------------------------------------------------------
    // Special-block placement
    // -------------------------------------------------------------------------

    private void handleSpecialBlock(PlayerInteractEvent e, Player p, String name) {
        switch (name) {
            case "§fRoter Pilzblock"     -> placeBlock(e, p, Material.RED_MUSHROOM_BLOCK,   Sound.BLOCK_WOOD_BREAK);
            case "§fBrauner Pilzblock"   -> placeBlock(e, p, Material.BROWN_MUSHROOM_BLOCK, Sound.BLOCK_WOOD_BREAK);
            case "§fPilzsporenblock"     -> placePoreBlock(e, p);
            case "§fPilzstielblock"      -> placeBlock(e, p, Material.MUSHROOM_STEM,         Sound.BLOCK_WOOD_BREAK);
            case "§fVolle Steinstufe"    -> placeDoubleSlab(e, p, Material.SMOOTH_STONE_SLAB);
            case "§fVolle Sandsteinstufe"    -> placeDoubleSlab(e, p, Material.SANDSTONE_SLAB);
            case "§fVolle Rote Sandsteinstufe" -> placeDoubleSlab(e, p, Material.RED_SANDSTONE_SLAB);
            default -> { /* not a special block */ }
        }
    }

    private void placeBlock(PlayerInteractEvent e, Player p, Material mat, Sound sound) {
        e.setCancelled(true);
        Block target = e.getClickedBlock().getRelative(e.getBlockFace());
        if (isOccupied(target)) return;
        target.setType(mat);
        p.playSound(target.getLocation(), sound, 1f, 0.8f);
    }

    /** Places a §fPilzsporenblock§r: a brown mushroom block where all six faces show pores. */
    private void placePoreBlock(PlayerInteractEvent e, Player p) {
        e.setCancelled(true);
        Block target = e.getClickedBlock().getRelative(e.getBlockFace());
        if (isOccupied(target)) return;
        target.setType(Material.BROWN_MUSHROOM_BLOCK);
        MultipleFacing pore =
                (MultipleFacing) target.getBlockData();
        for (BlockFace face : pore.getAllowedFaces()) pore.setFace(face, false);
        target.setBlockData(pore);
        p.playSound(target.getLocation(), Sound.BLOCK_WOOD_BREAK, 1f, 0.8f);
    }

    /** Places a double-slab block of the given slab material. */
    private void placeDoubleSlab(PlayerInteractEvent e, Player p, Material slabMat) {
        e.setCancelled(true);
        Block target = e.getClickedBlock().getRelative(e.getBlockFace());
        if (isOccupied(target)) return;
        target.setType(slabMat);
        Slab slab = (Slab) target.getBlockData();
        slab.setType(Slab.Type.DOUBLE);
        target.setBlockData(slab);
        p.playSound(target.getLocation(), Sound.BLOCK_STONE_BREAK, 1f, 0.8f);
    }

    /** Returns {@code true} when the block position is already occupied by a non-item entity. */
    private boolean isOccupied(Block block) {
        Location centre = block.getLocation().add(0.5, 0, 0.5);
        return !block.getWorld().getNearbyEntities(centre, 0.5, 1, 0.5)
                     .stream().allMatch(en -> en instanceof Item);
    }

    // -------------------------------------------------------------------------
    // Iron door / trapdoor toggle
    // -------------------------------------------------------------------------

    private void handleIronDoorToggle(PlayerInteractEvent e, Player p) {
        Block clicked = e.getClickedBlock();
        if (clicked == null) return;
        Material type = clicked.getType();

        if (type == Material.IRON_TRAPDOOR) {
            e.setCancelled(true);
            TrapDoor td = (TrapDoor) clicked.getBlockData();
            td.setOpen(!td.isOpen());
            clicked.setBlockData(td);
            Sound sound = td.isOpen() ? Sound.BLOCK_IRON_TRAPDOOR_OPEN : Sound.BLOCK_IRON_TRAPDOOR_CLOSE;
            p.playSound(clicked.getLocation(), sound, 1f, 1f);

        } else if (type == Material.IRON_DOOR) {
            e.setCancelled(true);
            // Normalise to the lower half
            Block lower = clicked;
            BlockData data = clicked.getBlockData();
            if (data instanceof Bisected bisected && bisected.getHalf() == Bisected.Half.TOP) {
                lower = clicked.getRelative(BlockFace.DOWN);
            }

            Openable door = (Openable) lower.getBlockData();
            door.setOpen(!door.isOpen());
            lower.setBlockData((BlockData) door);

            // Sync the upper half
            Block upper = lower.getRelative(BlockFace.UP);
            if (upper.getType() == Material.IRON_DOOR) {
                Openable upperDoor = (Openable) upper.getBlockData();
                upperDoor.setOpen(door.isOpen());
                upper.setBlockData((BlockData) upperDoor);
            }

            Sound sound = door.isOpen() ? Sound.BLOCK_IRON_DOOR_OPEN : Sound.BLOCK_IRON_DOOR_CLOSE;
            p.playSound(lower.getLocation(), sound, 1f, 1f);
        }
    }
}
