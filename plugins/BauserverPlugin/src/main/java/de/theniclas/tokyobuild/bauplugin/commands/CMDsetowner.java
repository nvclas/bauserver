package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Configs;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDsetowner implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setowner")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.admin")) {
                    if (args.length >= 1) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                        if (target.hasPlayedBefore() || target.isOnline()) {
                            if (!target.getName()
                                    .equals(Configs.worldsConfig.get("Worlds." + p.getWorld()
                                            .getName()
                                            .replace("worlds/", "") + ".Owner"))) {
                                Configs.worldsConfig.set("Worlds." + p.getWorld()
                                        .getName()
                                        .replace("worlds/", "") + ".Owner", target.getUniqueId().toString());
                                p.sendMessage(Vars.pr + "§aDer neue Besitzer dieser Welt ist nun §e" + args[0]);
                                Configs.saveConfiguration();
                            } else p.sendMessage(Vars.pr + "§cDieser Spieler ist bereits der Besitzer dieser Welt");
                        } else p.sendMessage(Vars.pr + "§cDieser Spieler hat noch nie hier gespielt");
                    } else p.sendMessage(Vars.pr + "§cWer soll der neue Besitzer werden?");
                } else p.sendMessage(Vars.noperm);
            }
        }
        return false;
    }

}
