package de.nvclas.tokyobuild.abu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockFromToListener implements Listener {

    private final JavaPlugin plugin;

    public BlockFromToListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        if (plugin.getConfig().getStringList("Worlds").contains(e.getBlock().getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
