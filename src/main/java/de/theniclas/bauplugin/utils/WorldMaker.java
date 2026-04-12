package de.theniclas.bauplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

public class WorldMaker {

    public static void createNormalWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.normalWorldName.remove(p);
            p.sendMessage(Vars.PR + "\u00a7eErstelle normale Welt...");
            new WorldCreator("worlds/" + name).type(WorldType.NORMAL).createWorld();
            saveWorldMeta(name, p, icon, "Normal");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0,
                    Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(Vars.PR + "\u00a7aDeine normale Welt wurde mit dem Namen \u00a7e" + name + " \u00a7aerstellt");
        } else {
            sendWorldLimitMessage(p);
        }
    }

    public static void createFlatWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.flatWorldName.remove(p);
            p.sendMessage(Vars.PR + "\u00a7eErstelle Flat-Welt...");
            new WorldCreator("worlds/" + name).type(WorldType.FLAT).createWorld();
            saveWorldMeta(name, p, icon, "Flat");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0,
                    Bukkit.getWorld("worlds/" + name).getHighestBlockYAt(0, 0), 0);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(Vars.PR + "\u00a7aDeine Flat-Welt wurde mit dem Namen \u00a7e" + name + " \u00a7aerstellt");
        } else {
            sendWorldLimitMessage(p);
        }
    }

    public static void createVoidWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.voidWorldName.remove(p);
            p.sendMessage(Vars.PR + "\u00a7eErstelle Void-Welt...");
            new WorldCreator("worlds/" + name)
                    .type(WorldType.FLAT)
                    .generator(new ChunkGenerator() {})
                    .createWorld();
            saveWorldMeta(name, p, icon, "Void");
            Bukkit.getWorld("worlds/" + name).setSpawnLocation(0, 100, 0);
            Bukkit.getWorld("worlds/" + name)
                    .getBlockAt(new Location(Bukkit.getWorld("worlds/" + name), 0, 99, 0))
                    .setType(Material.BEDROCK);
            p.teleport(Bukkit.getWorld("worlds/" + name).getSpawnLocation());
            p.sendMessage(Vars.PR + "\u00a7aDeine Void-Welt wurde mit dem Namen \u00a7e" + name + " \u00a7aerstellt");
        } else {
            sendWorldLimitMessage(p);
        }
    }

    private static void saveWorldMeta(String name, Player p, ItemStack icon, String type) {
        Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
        Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().name());
        Configs.worldsConfig.set("Worlds." + name + ".Type", type);
        Configs.saveConfiguration();
    }

    private static void sendWorldLimitMessage(Player p) {
        p.sendMessage(Vars.PR + "\u00a7cDu kannst keine weiteren Welten erstellen");
        Vars.voidWorldName.remove(p);
        Vars.flatWorldName.remove(p);
        Vars.normalWorldName.remove(p);
    }
}
