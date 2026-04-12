package de.theniclas.bauplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorldMaker {

    private WorldMaker() {}

    public static void createNormalWorld(String name, Player p, ItemStack icon) {
        if (!canCreateWorld(p)) {
            sendLimitMessage(p);
            return;
        }
        Vars.normalWorldName.remove(p);
        p.sendMessage(Vars.PREFIX + "§eErstelle normale Welt...");

        WorldCreator wc = new WorldCreator("worlds/" + name);
        wc.type(WorldType.NORMAL);
        World world = wc.createWorld();
        if (world == null) return;

        world.save();
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
        saveWorldEntry(name, p, icon, "Normal");

        p.teleport(world.getSpawnLocation());
        p.sendMessage(Vars.PREFIX + "§aDeine normale Welt wurde mit dem Namen §e" + name + " §aerstellt");
        p.sendMessage(Vars.PREFIX + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
    }

    public static void createFlatWorld(String name, Player p, ItemStack icon) {
        if (!canCreateWorld(p)) {
            sendLimitMessage(p);
            return;
        }
        Vars.flatWorldName.remove(p);
        p.sendMessage(Vars.PREFIX + "§eErstelle Flat-Welt...");

        WorldCreator wc = new WorldCreator("worlds/" + name);
        wc.type(WorldType.FLAT);
        World world = wc.createWorld();
        if (world == null) return;

        world.save();
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
        saveWorldEntry(name, p, icon, "Flat");

        p.teleport(world.getSpawnLocation());
        p.sendMessage(Vars.PREFIX + "§aDeine Flat-Welt wurde mit dem Namen §e" + name + " §aerstellt");
        p.sendMessage(Vars.PREFIX + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
    }

    public static void createVoidWorld(String name, Player p, ItemStack icon) {
        if (!canCreateWorld(p)) {
            sendLimitMessage(p);
            return;
        }
        Vars.voidWorldName.remove(p);
        p.sendMessage(Vars.PREFIX + "§eErstelle Void-Welt...");

        WorldCreator wc = new WorldCreator("worlds/" + name);
        wc.type(WorldType.FLAT);
        // Empty flat world (void) using the modern 1.21 generator-settings JSON format.
        wc.generatorSettings("{\"biome\":\"minecraft:the_void\",\"layers\":[{\"block\":\"minecraft:air\",\"height\":1}],\"structures\":{\"structures\":{}},\"features\":false,\"lakes\":false}");
        World world = wc.createWorld();
        if (world == null) return;

        world.save();
        world.setSpawnLocation(0, 100, 0);
        world.getBlockAt(new Location(world, 0, 99, 0)).setType(Material.BEDROCK);
        saveWorldEntry(name, p, icon, "Void");

        p.teleport(world.getSpawnLocation());
        p.sendMessage(Vars.PREFIX + "§aDeine Void-Welt wurde mit dem Namen §e" + name + " §aerstellt");
        p.sendMessage(Vars.PREFIX + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
    }

    // -------------------------------------------------------------------------

    private static boolean canCreateWorld(Player p) {
        return Configs.worldsConfig.getBoolean("Visibility")
                || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p);
    }

    private static void sendLimitMessage(Player p) {
        p.sendMessage(Vars.PREFIX + "§cDu kannst keine weiteren Welten erstellen");
        Vars.voidWorldName.remove(p);
        Vars.flatWorldName.remove(p);
        Vars.normalWorldName.remove(p);
    }

    /**
     * Persists a new world entry in worlds.yml.
     * The icon is stored as the material name (e.g. {@code "GRASS_BLOCK"}).
     */
    private static void saveWorldEntry(String name, Player p, ItemStack icon, String type) {
        Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
        Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().name());
        Configs.worldsConfig.set("Worlds." + name + ".Type", type);
        Configs.saveConfiguration();
    }
}
