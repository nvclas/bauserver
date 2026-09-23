package de.theniclas.bauplugin.utils;

import de.theniclas.bauplugin.Bauserver;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class WorldMaker {

    private final Bauserver plugin;

    public void createNormalWorld(String name, Player p, ItemStack icon) {
        if (plugin.getBauserverConfig().getWorldsConfig().getBoolean("Visibility")
                || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.normalWorldName.remove(p);
            p.sendMessage(Vars.prefixed("<yellow>Erstelle normale Welt..."));
            new WorldCreator("worlds/" + name).type(WorldType.NORMAL).createWorld();
            saveWorldMeta(name, p, icon, "Normal");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0,
                    Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(Vars.prefixed(
                    "<green>Deine normale Welt wurde mit dem Namen <yellow>" + name + " <green>erstellt"));
        } else {
            sendWorldLimitMessage(p);
        }
    }

    public void createFlatWorld(String name, Player p, ItemStack icon) {
        if (plugin.getBauserverConfig().getWorldsConfig().getBoolean("Visibility")
                || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.flatWorldName.remove(p);
            p.sendMessage(Vars.prefixed("<yellow>Erstelle Flat-Welt..."));
            new WorldCreator("worlds/" + name).type(WorldType.FLAT).createWorld();
            saveWorldMeta(name, p, icon, "Flat");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0,
                    Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(
                    Vars.prefixed("<green>Deine Flat-Welt wurde mit dem Namen <yellow>" + name + " <green>erstellt"));
        } else {
            sendWorldLimitMessage(p);
        }
    }

    public void createVoidWorld(String name, Player p, ItemStack icon) {
        if (plugin.getBauserverConfig().getWorldsConfig().getBoolean("Visibility")
                || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.voidWorldName.remove(p);
            p.sendMessage(Vars.prefixed("<yellow>Erstelle Void-Welt..."));
            new WorldCreator("worlds/" + name)
                    .type(WorldType.FLAT)
                    .generator(new ChunkGenerator() {
                    })
                    .createWorld();
            saveWorldMeta(name, p, icon, "Void");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0, 100, 0);
            Bukkit.getWorld("worlds/" + name)
                    .getBlockAt(new Location(Bukkit.getWorld("worlds/" + name), 0, 99, 0))
                    .setType(Material.BEDROCK);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(
                    Vars.prefixed("<green>Deine Void-Welt wurde mit dem Namen <yellow>" + name + " <green>erstellt"));
        } else {
            sendWorldLimitMessage(p);
        }
    }

    private void saveWorldMeta(String name, Player p, ItemStack icon, String type) {
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + name + ".Owner", p.getUniqueId().toString());
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + name + ".Symbol", icon.getType().name());
        plugin.getBauserverConfig().getWorldsConfig().set("Worlds." + name + ".Type", type);
        plugin.getBauserverConfig().saveConfiguration();
    }

    private void sendWorldLimitMessage(Player p) {
        p.sendMessage(Vars.prefixed("<red>Du kannst keine weiteren Welten erstellen"));
        Vars.voidWorldName.remove(p);
        Vars.flatWorldName.remove(p);
        Vars.normalWorldName.remove(p);
    }
}
