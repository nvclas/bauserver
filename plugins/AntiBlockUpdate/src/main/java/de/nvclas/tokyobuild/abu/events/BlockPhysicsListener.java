package de.nvclas.tokyobuild.abu.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockPhysicsListener implements Listener {

    private final JavaPlugin plugin;

    public BlockPhysicsListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (plugin.getConfig().getStringList("Worlds").contains(e.getBlock().getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
