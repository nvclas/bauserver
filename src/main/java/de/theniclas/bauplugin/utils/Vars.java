package de.theniclas.bauplugin.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class Vars {

    public static final String pr = "§8[§9§lTokyo-Build§8] ";
    public static final String noperm = pr + "§cDafür hast du keine Rechte";

    public static final ArrayList<Player> voidWorldName = new ArrayList<>();
    public static final ArrayList<Player> flatWorldName = new ArrayList<>();
    public static final ArrayList<Player> normalWorldName = new ArrayList<>();

    public static HashMap<Player, Integer> placedBlocks = new HashMap<>();
    public static final HashMap<String, String> tpa = new HashMap<>();

    public static ItemStack getSkull(String url, String displayName) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) return item;
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        GameProfile profile = new GameProfile(UUID.randomUUID(), "name");
        byte[] encodedData = Base64.getEncoder()
                .encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public static boolean isOwner(Player p, String worldName) {
        String owner = Configs.worldsConfig.getString("Worlds." + worldName.replace("worlds/", "") + ".Owner");
        return owner != null && owner.equals(p.getUniqueId().toString());
    }

    public static boolean isTrusted(OfflinePlayer p, String worldName) {
        var trustedList = Configs.worldsConfig.getStringList("Worlds." + worldName.replace("worlds/",
                "") + ".Trusted");
        return trustedList.contains(p.getUniqueId().toString());
    }

    public static void loadGlobalSpawnWorld() {
        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (spawnWorld != null) {
            Bukkit.createWorld(new WorldCreator(spawnWorld));
        }
    }

    public static int getWorldAmount(Player p) {
        int amount = 0;
        var worldsSection = Configs.worldsConfig.getConfigurationSection("Worlds");
        if (worldsSection != null) {
            for (String worlds : worldsSection.getKeys(false)) {
                String owner = Configs.worldsConfig.getString("Worlds." + worlds + ".Owner");
                if (owner != null && owner.equals(p.getUniqueId().toString())) {
                    amount++;
                }
            }
        }
        return amount;
    }

    public static int getMaxWorldAmount(Player p) {
        if (!p.hasPermission("bs.worlds.infinite")) {
            for (int i = 0; i <= 30; i++) {
                if (p.hasPermission("bs.worlds." + i)) {
                    return i;
                }
            }
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

}