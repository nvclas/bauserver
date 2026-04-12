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
        if (!p.hasPermission("bs.speed")) { p.sendMessage(Vars.NO_PERM); return false; }

        if (args.length < 1) {
            if (p.isFlying()) {
                p.sendMessage(Vars.PREFIX + "§aDeine Fluggeschwindigkeit beträgt derzeit §e" + (p.getFlySpeed() * 10) + " §8(Standard: 1)");
            } else {
                p.sendMessage(Vars.PREFIX + "§aDeine Laufgeschwindigkeit beträgt derzeit §e" + (p.getWalkSpeed() * 10) + " §8(Standard: 2)");
            }
            return false;
        }

        float value;
        try {
            value = Float.parseFloat(args[0]);
        } catch (NumberFormatException ex) {
            p.sendMessage(Vars.PREFIX + "§cZahlen wären praktisch");
            return false;
        }

        if (value < 1 || value > 10) {
            p.sendMessage(Vars.PREFIX + "§cÄhm, ich denke zwischen 1 und 10 sollte reichen");
            return false;
        }

        if (p.isFlying()) {
            p.setFlySpeed(value / 10f);
            p.sendMessage(Vars.PREFIX + "§aDeine Fluggeschwindigkeit wurde auf §e" + args[0] + " §agesetzt");
        } else {
            p.setWalkSpeed(value / 10f);
            p.sendMessage(Vars.PREFIX + "§aDeine Laufgeschwindigkeit wurde auf §e" + args[0] + " §agesetzt");
        }
        return false;
    }
}
