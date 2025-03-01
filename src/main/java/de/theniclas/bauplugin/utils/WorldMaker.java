package de.theniclas.bauplugin.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorldMaker {

    public static void createNormalWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.normalWorldName.remove(p);
            p.sendMessage(Vars.pr + "§eErstelle normale Welt...");
            WorldCreator wc = new WorldCreator("worlds/" + name);
            wc.type(WorldType.NORMAL);
            World world = wc.createWorld();
            if (world != null) {
                Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Type", "Normal");
                Configs.saveConfiguration();
                System.out.println("Preparing spawn area for " + name + ", done");
                world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
                p.teleport(world.getSpawnLocation());
                p.sendMessage(Vars.pr + "§aDeine normale Welt wurde mit dem Namen §e" + name + " §aerstellt");
                p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
            }
        } else {
            p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
            Vars.voidWorldName.remove(p);
            Vars.flatWorldName.remove(p);
            Vars.normalWorldName.remove(p);
        }
    }

    public static void createFlatWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.flatWorldName.remove(p);
            p.sendMessage(Vars.pr + "§eErstelle Flat-Welt...");
            WorldCreator wc = new WorldCreator("worlds/" + name);
            wc.type(WorldType.FLAT);
            World world = wc.createWorld();
            if (world != null) {
                Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Type", "Flat");
                Configs.saveConfiguration();
                System.out.println("Preparing spawn area for " + name + ", done");
                world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
                p.teleport(world.getSpawnLocation());
                p.sendMessage(Vars.pr + "§aDeine Flat-Welt wurde mit dem Namen §e" + name + " §aerstellt");
                p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
            }
        } else {
            p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
            Vars.voidWorldName.remove(p);
            Vars.flatWorldName.remove(p);
            Vars.normalWorldName.remove(p);
        }
    }

    public static void createVoidWorld(String name, Player p, ItemStack icon) {
        if (Configs.worldsConfig.getBoolean("Visibility") || Vars.getWorldAmount(p) < Vars.getMaxWorldAmount(p)) {
            Vars.voidWorldName.remove(p);
            p.sendMessage(Vars.pr + "§eErstelle Void-Welt...");
            WorldCreator wc = new WorldCreator("worlds/" + name);
            wc.type(WorldType.FLAT);
            wc.generatorSettings("{\"layers\":[],\"biome\":\"the_void\"}"); // Void generator settings
            World world = wc.createWorld();
            if (world != null) {
                Configs.worldsConfig.set("Worlds." + name + ".Owner", p.getUniqueId().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Symbol", icon.getType().toString());
                Configs.worldsConfig.set("Worlds." + name + ".Type", "Void");
                Configs.saveConfiguration();
                System.out.println("Preparing spawn area for " + name + ", done");
                world.setSpawnLocation(0, 100, 0);
                Location bedrockLocation = new Location(world, 0, 99, 0);
                world.getBlockAt(bedrockLocation).setType(Material.BEDROCK);
                p.teleport(world.getSpawnLocation());
                p.sendMessage(Vars.pr + "§aDeine Void-Welt wurde mit dem Namen §e" + name + " §aerstellt");
                p.sendMessage(Vars.pr + "§aNutze §e/prepare§a, um Mobs, Wetter, Blockphysiken und andere Einflüsse auszustellen");
            }
        } else {
            p.sendMessage(Vars.pr + "§cDu kannst keine weiteren Welten erstellen");
            Vars.voidWorldName.remove(p);
            Vars.flatWorldName.remove(p);
            Vars.normalWorldName.remove(p);
        }
    }
}