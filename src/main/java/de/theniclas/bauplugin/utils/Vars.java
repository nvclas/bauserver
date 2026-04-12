package de.theniclas.bauplugin.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.theniclas.bauplugin.Bauserver;
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

    private final Bauserver plugin;
    private static Vars instance;

    public Vars(Bauserver plugin) {
        this.plugin = plugin;
    }

    public static Vars inject(Bauserver plugin) {
        instance = new Vars(plugin);
        return instance;
    }

    private static Vars getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Vars has not been injected yet");
        }
        return instance;
    }
    public static final String PR = "§8[§9§lTokyo-Build§8] ";
    public static final String NOPERM = PR + "§cDafür hast du keine Rechte";

    public static final List<Player> voidWorldName = new ArrayList<>();
    public static final List<Player> flatWorldName = new ArrayList<>();
    public static final List<Player> normalWorldName = new ArrayList<>();

    public static final Map<String, String> tpa = new HashMap<>();

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
        String owner = getInstance().plugin.getBauserverConfig().getWorldsConfig().getString(key);
        return owner != null && owner.equals(p.getUniqueId().toString());
    }

    public static boolean isTrusted(OfflinePlayer p, String worldName) {
        List<String> trusted = getInstance().plugin.getBauserverConfig().getWorldsConfig().getStringList(
                "Worlds." + worldName.replace("worlds/", "") + ".Trusted");
        return trusted.contains(p.getUniqueId().toString());
    }

    public static void loadGlobalSpawnWorld() {
        if (getInstance().plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World") != null) {
            Bukkit.createWorld(new WorldCreator(getInstance().plugin.getBauserverConfig().getWorldsConfig().getString("Spawn.World")));
        }
    }

    public static int getWorldAmount(Player p) {
        int amount = 0;
        if (getInstance().plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds") == null) return 0;
        for (String worlds : getInstance().plugin.getBauserverConfig().getWorldsConfig().getConfigurationSection("Worlds").getKeys(false)) {
            if (p.getUniqueId().toString().equals(
                    getInstance().plugin.getBauserverConfig().getWorldsConfig().getString("Worlds." + worlds + ".Owner"))) {
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
