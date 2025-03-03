package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDspeed implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("speed")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("bs.speed")) {
                    if (args.length >= 1) {
                        if (NumberUtils.isNumber(args[0])) {
                            if (Float.parseFloat(args[0]) >= 1 && Float.parseFloat(args[0]) <= 10) {
                                if (p.isFlying()) {
                                    p.setFlySpeed(Float.parseFloat(args[0]) / 10);
                                    p.sendMessage(Vars.pr + "§aDein Fluggeschwindigkeit wurde auf §e" + args[0] + " §agesetzt");
                                } else {
                                    p.setWalkSpeed(Float.parseFloat(args[0]) / 10);
                                    p.sendMessage(Vars.pr + "§aDein Laufgeschwindigkeit wurde auf §e" + args[0] + " §agesetzt");
                                }
                            } else {
                                p.sendMessage(Vars.pr + "§c§hm, ich denke zwischen 1 und 10 sollte reichen");
                            }
                        } else {
                            p.sendMessage(Vars.pr + "§cZahlen wären praktisch");
                        }
                    } else {
                        if (p.isFlying()) {
                            p.sendMessage(Vars.pr + "§aDeine Fluggeschwindigkeit beträgt derzeit §e" + p.getFlySpeed() * 10 + " §8(Standard: 1)");
                        } else {
                            p.sendMessage(Vars.pr + "§aDeine Laufgeschwindigkeit beträgt derzeit §e" + p.getWalkSpeed() * 10 + " §8(Standard: 2)");
                        }
                    }
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }
        }
        return false;
    }

}
