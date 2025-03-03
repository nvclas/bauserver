package de.nvclas.tokyobuild.abu.commands;

import de.nvclas.tokyobuild.abu.AntiBlockUpdate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CMDabu implements CommandExecutor {
    private static final String CONFIGURED_WORLDS_KEY = "Worlds";
    private final AntiBlockUpdate plugin;

    public CMDabu(AntiBlockUpdate plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("abu") || !(sender instanceof Player player)) {
            return false;
        }

        if (!player.hasPermission("bs.abu")) {
            player.sendMessage(AntiBlockUpdate.PREFIX + "§cDafür hast du keine Rechte");
            return true;
        }

        String currentWorldName = player.getWorld().getName();
        List<String> configuredWorlds = plugin.getConfig().getStringList(CONFIGURED_WORLDS_KEY);

        if (configuredWorlds.contains(currentWorldName)) {
            deactivateWorld(currentWorldName, configuredWorlds);
        } else {
            activateWorld(currentWorldName, configuredWorlds);
        }

        plugin.getConfig().set(CONFIGURED_WORLDS_KEY, configuredWorlds);
        plugin.getWorldConfig().saveConfiguration();
        return true;
    }

    private void activateWorld(String worldName, List<String> configuredWorlds) {
        configuredWorlds.add(worldName);
        notifyPlayersInWorld(worldName, AntiBlockUpdate.PREFIX + "§aBlockphysiken wurden für diese Welt §edeaktiviert");
    }

    private void deactivateWorld(String worldName, List<String> configuredWorlds) {
        configuredWorlds.remove(worldName);
        notifyPlayersInWorld(worldName, AntiBlockUpdate.PREFIX + "§aBlockphysiken wurden für diese Welt §eaktiviert");
    }

    private void notifyPlayersInWorld(String worldName, String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().getName().equals(worldName))
                .forEach(player -> player.sendMessage(message));
    }
}