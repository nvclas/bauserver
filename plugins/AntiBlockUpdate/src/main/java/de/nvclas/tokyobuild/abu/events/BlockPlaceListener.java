package de.nvclas.tokyobuild.abu.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class BlockPlaceListener implements Listener {

    private static final List<Material> SUPPORTED_MATERIALS = Arrays.asList(Material.SAND, Material.GRAVEL);
    private static final int TIMER_DELAY = 3;

    private final JavaPlugin plugin;

    public BlockPlaceListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!isWorldConfigured(event.getBlock().getWorld())) {
            return;
        }

        Block blockBelow = getBlockBelow(event.getBlock());

        if (SUPPORTED_MATERIALS.contains(event.getBlock().getType()) && blockBelow.getType() == Material.AIR) {
            blockBelow.setType(Material.BARRIER);

            // Schedule task to reset the block's type after the delay
            Bukkit.getScheduler()
                    .runTaskLater(plugin, () -> blockBelow.setType(Material.AIR), TIMER_DELAY);
        }
    }

    private boolean isWorldConfigured(World world) {
        return plugin.getConfig().getStringList("Worlds").contains(world.getName());
    }

    private Block getBlockBelow(Block block) {
        return block.getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ());
    }
}