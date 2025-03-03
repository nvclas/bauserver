package de.nvclas.tokyobuild.levels.commands;

import de.nvclas.tokyobuild.bauplugin.utils.Vars;
import de.nvclas.tokyobuild.levels.config.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDunlock implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("unlock")) {
            if (sender.hasPermission("bs.admin")) {
                if (args.length >= 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target.hasPlayedBefore()) {
                        PermissionUser user = PermissionsEx.getUser(target.getUniqueId().toString());
                        if (Data.getConfig().get("Levels." + target.getUniqueId() + ".Level") == null) {
                            Data.getConfig().set("Levels." + target.getUniqueId() + ".Level", 1);
                            Data.getConfig().set("Levels." + target.getUniqueId() + ".Xp", 0);
                            Data.getConfig().set("Levels." + target.getUniqueId() + ".NextLevelXp", 20);
                            Data.saveConfiguration();
                            sender.sendMessage(Vars.pr + "�aDu hast �6" + target.getName() + " �afreigeschaltet");
                            user.addGroup("shinjin");
                            user.removeGroup("gast");
                            if (target.isOnline()) {
                                ((Player) target).sendMessage(Vars.pr + "�7�k1111111111111111111111111111");
                                ((Player) target).sendMessage(Vars.pr);
                                ((Player) target).sendMessage(Vars.pr + "�eDu wurdest freigeschaltet!");
                                ((Player) target).sendMessage(Vars.pr);
                                ((Player) target).sendMessage(Vars.pr + "�7�k1111111111111111111111111111");
                                ((Player) target).playSound(((Player) target).getLocation(),
                                        Sound.LEVEL_UP,
                                        1.0f,
                                        1.0f);
                            }
                        } else {
                            sender.sendMessage(Vars.pr + "�cDieser Spieler ist bereits freigeschaltet");
                        }
                    } else {
                        sender.sendMessage(Vars.pr + "�cDieser Spieler war nie online");
                    }
                } else {
                    sender.sendMessage(Vars.pr + "�cWer soll freigeschalten werden?");
                }
            } else {
                sender.sendMessage(Vars.noperm);
            }
        }
        return false;
    }

}
