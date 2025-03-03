package de.theniclas.tokyobuild.bauplugin.events;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class PlayerChangedWorld implements Listener {
    private static final List<String> WORLDEDIT_PERMISSIONS = List.of(
            "worldedit.history.undo", "worldedit.history.redo", "worldedit.navigation.*",
            "worldedit.wand.*", "worldedit.selection.*", "worldedit.analysis.*",
            "worldedit.region.*", "worldedit.generation.*", "worldedit.clipboard.*",
            "worldedit.tool.*", "worldedit.brush.*", "worldedit.biome.*",
            "gobrush.use", "gopaint.use", "voxelsniper.sniper",
            "voxelsniper.brush", "astools.use"
    );
    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;

    public PlayerChangedWorld(JavaPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World previousWorld = event.getFrom();
        World newWorld = player.getWorld();

        // 📢 Spieler über Weltwechsel informieren
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld().equals(previousWorld)) {
                onlinePlayer.sendMessage(Component.text(player.getName() + " hat die Welt verlassen",
                        NamedTextColor.YELLOW));
            } else if (onlinePlayer.getWorld().equals(newWorld)) {
                onlinePlayer.sendMessage(Component.text(player.getName() + " hat die Welt betreten",
                        NamedTextColor.YELLOW));
            }
        }

        // 🔐 Berechtigungen setzen
        updatePermissions(player, newWorld);

        // 🌍 Welt entladen, falls leer
        unloadEmptyWorld(previousWorld);
    }

    private void updatePermissions(Player player, World world) {
        if (player.hasPermission("bs.admin")) return;

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return;

        boolean shouldHavePermissions = Vars.isOwner(player, world.getName()) || Vars.isTrusted(player,
                world.getName());

        WORLDEDIT_PERMISSIONS.forEach(permission -> {
            if (shouldHavePermissions) {
                user.data().add(Node.builder(permission).build());
            } else {
                user.data().remove(Node.builder(permission).build());
            }
        });

        luckPerms.getUserManager().saveUser(user);
    }

    private void unloadEmptyWorld(World world) {
        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (!world.getName().equals(spawnWorld) && world.getPlayers().isEmpty()) {
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.unloadWorld(world, true));
        }
    }
}