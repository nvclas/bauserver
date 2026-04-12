package de.theniclas.bauplugin.events;

import de.theniclas.bauplugin.utils.Vars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.block.data.MultipleFacing;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public class PlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("bs.admin") && !Vars.isTrusted(p, p.getWorld().getName())
                && !Vars.isOwner(p, p.getWorld().getName())) {
            e.setCancelled(true);
            return;
        }

        if (e.getHand() != EquipmentSlot.HAND || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getClickedBlock() == null) {
            return;
        }

        String itemName = getDisplayName(p.getInventory().getItemInMainHand());
        if (itemName != null && handleSpecialBlockPlacement(e, p, itemName)) {
                return;
            }


        Block clicked = e.getClickedBlock();
        Material type = clicked.getType();
        if (type == Material.IRON_TRAPDOOR || type == Material.IRON_DOOR) {
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setCancelled(true);

            Block target = clicked;
            BlockData data = target.getBlockData();
            if (type == Material.IRON_DOOR && data instanceof Bisected bisected && bisected.getHalf() == Bisected.Half.TOP) {
                target = clicked.getRelative(BlockFace.DOWN);
                data = target.getBlockData();
            }

            if (!(data instanceof Openable openable)) {
                return;
            }

            boolean opening = !openable.isOpen();
            openable.setOpen(opening);
            target.setBlockData(openable, true);
            p.playSound(clicked.getLocation(), opening ? Sound.BLOCK_IRON_DOOR_OPEN : Sound.BLOCK_IRON_DOOR_CLOSE, 1f, 1f);
        }
    }

    private boolean handleSpecialBlockPlacement(PlayerInteractEvent e, Player p, String itemName) {
        Material placeType;
        Sound placeSound;
        boolean setSporeData = false;
        boolean setStemData = false;

        switch (itemName) {
            case "roter pilzblock" -> {
                placeType = Material.RED_MUSHROOM_BLOCK;
                placeSound = Sound.BLOCK_WOOD_PLACE;
            }
            case "brauner pilzblock" -> {
                placeType = Material.BROWN_MUSHROOM_BLOCK;
                placeSound = Sound.BLOCK_WOOD_PLACE;
            }
            case "pilzsporenblock" -> {
                placeType = Material.BROWN_MUSHROOM_BLOCK;
                placeSound = Sound.BLOCK_WOOD_PLACE;
                setSporeData = true;
            }
            case "pilzstielblock" -> {
                placeType = Material.MUSHROOM_STEM;
                placeSound = Sound.BLOCK_WOOD_PLACE;
                setStemData = true;
            }
            default -> {
                return false;
            }
        }

        Block targetBlock = e.getClickedBlock().getRelative(e.getBlockFace());
        if (!canPlaceSpecialBlock(targetBlock)) {
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setCancelled(true);
            return true;
        }

        e.setUseInteractedBlock(Event.Result.DENY);
        e.setCancelled(true);

        targetBlock.setType(placeType, false);
        if (setSporeData || setStemData) {
            BlockData blockData = targetBlock.getBlockData();
            if (blockData instanceof MultipleFacing faces) {
                for (BlockFace face : faces.getAllowedFaces()) {
                    faces.setFace(face, setStemData);
                }
                targetBlock.setBlockData(faces, false);
            }
        }

        p.playSound(e.getClickedBlock().getLocation(), placeSound, 1f, 0.8f);
        return true;
    }

    private boolean canPlaceSpecialBlock(Block targetBlock) {
        Location location = targetBlock.getLocation().add(0.5, 0, 0.5);
        return targetBlock.getWorld().getNearbyEntities(location, 0.5, 1, 0.5)
                .stream()
                .allMatch(Item.class::isInstance);
    }

    private @Nullable String getDisplayName(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return null;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }

        Component componentName = itemMeta.displayName();
        if (componentName == null) {
            return null;
        }

        String name = PlainTextComponentSerializer.plainText().serialize(componentName);
        if (name.isBlank()) {
            return null;
        }
        return name.toLowerCase(Locale.ROOT).trim();
    }
}
