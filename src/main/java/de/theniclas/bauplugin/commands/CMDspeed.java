package de.theniclas.bauplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDspeed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.speed")) { p.sendMessage(Vars.NOPERM); return false; }
        if (args.length < 1) {
            if (p.isFlying()) {
                p.sendMessage(Vars.prefixed("<green>Deine Fluggeschwindigkeit beträgt derzeit <yellow>"
                        + (p.getFlySpeed() * 10) + " <dark_gray>(Standard: 1)"));
            } else {
                p.sendMessage(Vars.prefixed("<green>Deine Laufgeschwindigkeit beträgt derzeit <yellow>"
                        + (p.getWalkSpeed() * 10) + " <dark_gray>(Standard: 2)"));
            }
            return false;
        }
        float value;
        try {
            value = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(Vars.prefixed("<red>Zahlen wären praktisch")); return false;
        }
        if (value < 1 || value > 10) {
            p.sendMessage(Vars.prefixed("<red>Ähm, ich denke zwischen 1 und 10 sollte reichen")); return false;
        }
        if (p.isFlying()) {
            p.setFlySpeed(value / 10);
            p.sendMessage(Vars.prefixed("<green>Deine Fluggeschwindigkeit wurde auf <yellow>" + value + " <green>gesetzt"));
        } else {
            p.setWalkSpeed(value / 10);
            p.sendMessage(Vars.prefixed("<green>Deine Laufgeschwindigkeit wurde auf <yellow>" + value + " <green>gesetzt"));
        }
        return false;
    }
}
