package de.theniclas.bauplugin.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import de.theniclas.bauplugin.main.Main;

public class Vars {

    /** Prefix shown before plugin messages. */
    public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + ChatColor.BOLD + "Tokyo-Build" + ChatColor.DARK_GRAY + "] ";
    public static final String NO_PERM = PREFIX + ChatColor.RED + "Dafür hast du keine Rechte";

    // Inventory title constants – used when creating inventories AND when identifying them in click events.
    public static final String INV_BLOCKS       = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Spezialblöcke";
    public static final String INV_TOOLS        = ChatColor.GOLD + "" + ChatColor.BOLD + "Tools";
    public static final String INV_WORLDS       = ChatColor.DARK_AQUA + "Welten";
    public static final String INV_CREATE_WORLD = ChatColor.DARK_AQUA + "Welt erstellen";

    /** Players currently being prompted for a new world name. */
    public static final ArrayList<Player> voidWorldName   = new ArrayList<>();
    public static final ArrayList<Player> flatWorldName   = new ArrayList<>();
    public static final ArrayList<Player> normalWorldName = new ArrayList<>();

    /** Pending TPA requests: targetUUID → requesterUUID. */
    public static final HashMap<String, String> tpa = new HashMap<>();

    /** Per-player PermissionAttachment used to grant build-tool permissions in owned/trusted worlds. */
    public static final Map<UUID, PermissionAttachment> toolPermissions = new HashMap<>();

    // -------------------------------------------------------------------------
    // Skull helper
    // -------------------------------------------------------------------------

    /**
     * Creates a player-head item with a custom skin texture URL.
     *
     * @param url         URL of the texture (e.g. {@code http://textures.minecraft.net/texture/…})
     * @param displayName Display name for the item
     * @return the configured {@link ItemStack}
     */
    public static ItemStack getSkull(String url, String displayName) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) return item;

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(displayName);

        try {
            PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "");
            PlayerTextures textures = profile.getTextures();
            textures.setSkin(URI.create(url).toURL());
            profile.setTextures(textures);
            meta.setOwnerProfile(profile);
        } catch (MalformedURLException e) {
            Main.getPlugin().getLogger().warning("Invalid skull texture URL: " + url);
        }

        item.setItemMeta(meta);
        return item;
    }

    // -------------------------------------------------------------------------
    // World helpers
    // -------------------------------------------------------------------------

    /** Returns {@code true} when {@code p} is the registered owner of {@code worldName}. */
    public static boolean isOwner(Player p, String worldName) {
        String owner = Configs.worldsConfig.getString("Worlds." + stripWorldsPrefix(worldName) + ".Owner");
        return owner != null && owner.equals(p.getUniqueId().toString());
    }

    /** Returns {@code true} when {@code p} is in the trusted list of {@code worldName}. */
    public static boolean isTrusted(OfflinePlayer p, String worldName) {
        var trusted = Configs.worldsConfig.getStringList("Worlds." + stripWorldsPrefix(worldName) + ".Trusted");
        return trusted != null && trusted.contains(p.getUniqueId().toString());
    }

    /** Removes the leading {@code "worlds/"} prefix from a world name if present. */
    public static String stripWorldsPrefix(String worldName) {
        return worldName.startsWith("worlds/") ? worldName.substring(7) : worldName;
    }

    /** Loads and registers the global spawn world on plugin startup. */
    public static void loadGlobalSpawnWorld() {
        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (spawnWorld != null) {
            Bukkit.createWorld(new WorldCreator(spawnWorld));
        }
    }

    /** Returns the number of worlds owned by {@code p}. */
    public static int getWorldAmount(Player p) {
        var worldsSection = Configs.worldsConfig.getConfigurationSection("Worlds");
        if (worldsSection == null) return 0;
        int amount = 0;
        for (String world : worldsSection.getKeys(false)) {
            if (p.getUniqueId().toString().equals(Configs.worldsConfig.getString("Worlds." + world + ".Owner"))) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Returns the maximum number of worlds {@code p} is allowed to own.
     * Players with {@code bs.worlds.infinite} have no limit.
     * Otherwise the highest {@code bs.worlds.<n>} permission (1–30) wins.
     */
    public static int getMaxWorldAmount(Player p) {
        if (p.hasPermission("bs.worlds.infinite")) {
            return Integer.MAX_VALUE;
        }
        for (int i = 1; i <= 30; i++) {
            if (p.hasPermission("bs.worlds." + i)) {
                return i;
            }
        }
        return 0;
    }
}
