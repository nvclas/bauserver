package de.theniclas.bauplugin.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.kyori.adventure.text.Component;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Vars {

    public static final String PR = "\u00a78[\u00a79\u00a7lTokyo-Build\u00a78] ";
    public static final String NOPERM = PR + "\u00a7cDaf\u00fcr hast du keine Rechte";

    public static ArrayList<Player> voidWorldName = new ArrayList<>();
    public static ArrayList<Player> flatWorldName = new ArrayList<>();
    public static ArrayList<Player> normalWorldName = new ArrayList<>();

    public static HashMap<String, String> tpa = new HashMap<>();

    public static ItemStack getSkull(String url, String displayName) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) return item;
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(url));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        meta.displayName(Component.text(displayName));
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isOwner(Player p, String worldName) {
        String key = "Worlds." + worldName.replace("worlds/", "") + ".Owner";
        String owner = Configs.worldsConfig.getString(key);
        return owner != null && owner.equals(p.getUniqueId().toString());
    }

    public static boolean isTrusted(OfflinePlayer p, String worldName) {
        java.util.List<String> trusted = Configs.worldsConfig.getStringList(
                "Worlds." + worldName.replace("worlds/", "") + ".Trusted");
        return trusted != null && trusted.contains(p.getUniqueId().toString());
    }

    public static void loadGlobalSpawnWorld() {
        if (Configs.worldsConfig.getString("Spawn.World") != null) {
            Bukkit.createWorld(new WorldCreator(Configs.worldsConfig.getString("Spawn.World")));
        }
    }

    public static int getWorldAmount(Player p) {
        int amount = 0;
        if (Configs.worldsConfig.getConfigurationSection("Worlds") == null) return 0;
        for (String worlds : Configs.worldsConfig.getConfigurationSection("Worlds").getKeys(false)) {
            if (p.getUniqueId().toString().equals(
                    Configs.worldsConfig.getString("Worlds." + worlds + ".Owner"))) {
                amount++;
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
        }
        return Integer.MAX_VALUE;
    }
}
