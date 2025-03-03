package de.nvclas.tokyobuild.levels.commands;

import de.nvclas.tokyobuild.bauplugin.utils.Vars;
import de.nvclas.tokyobuild.levels.utils.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDunlock implements CommandExecutor {

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("unlock")) {
            if (sender.hasPermission("bs.admin")) {
                if (args.length >= 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (target.hasPlayedBefore()) {
                        PermissionUser user = PermissionsEx.getUser(target.getUniqueId().toString());
                        if (Data.getConfig().get("Levels." + target.getUniqueId().toString() + ".Level") == null) {
                            Data.getConfig().set("Levels." + target.getUniqueId().toString() + ".Level", 1);
                            Data.getConfig().set("Levels." + target.getUniqueId().toString() + ".Xp", 0);
                            Data.getConfig().set("Levels." + target.getUniqueId().toString() + ".NextLevelXp", 20);
                            Data.saveConfiguration();
                            sender.sendMessage(AntiBlockUpdate.PREFIX + "�aDu hast �6" + target.getName() + " �afreigeschaltet");
                            user.addGroup("shinjin");
                            user.removeGroup("gast");
                            if (target.isOnline()) {
                                ((Player) target).sendMessage(AntiBlockUpdate.PREFIX + "�7�k1111111111111111111111111111");
                                ((Player) target).sendMessage(AntiBlockUpdate.PREFIX);
                                ((Player) target).sendMessage(AntiBlockUpdate.PREFIX + "�eDu wurdest freigeschaltet!");
                                ((Player) target).sendMessage(AntiBlockUpdate.PREFIX);
                                ((Player) target).sendMessage(AntiBlockUpdate.PREFIX + "�7�k1111111111111111111111111111");
                                ((Player) target).playSound(((Player) target).getLocation(),
                                        Sound.LEVEL_UP,
                                        1.0f,
                                        1.0f);
                            }
                        } else {
                            sender.sendMessage(AntiBlockUpdate.PREFIX + "�cDieser Spieler ist bereits freigeschaltet");
                        }
                    } else {
                        sender.sendMessage(AntiBlockUpdate.PREFIX + "�cDieser Spieler war nie online");
                    }
                } else {
                    sender.sendMessage(AntiBlockUpdate.PREFIX + "�cWer soll freigeschalten werden?");
                }
            } else {
                sender.sendMessage(Vars.noperm);
            }
        }
        return false;
    }

}
