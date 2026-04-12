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
                p.sendMessage(Vars.PR + "\u00a7aDeine Fluggeschwindigkeit betr\u00e4gt derzeit \u00a7e"
                        + (p.getFlySpeed() * 10) + " \u00a78(Standard: 1)");
            } else {
                p.sendMessage(Vars.PR + "\u00a7aDeine Laufgeschwindigkeit betr\u00e4gt derzeit \u00a7e"
                        + (p.getWalkSpeed() * 10) + " \u00a78(Standard: 2)");
            }
            return false;
        }
        float value;
        try {
            value = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(Vars.PR + "\u00a7cZahlen w\u00e4ren praktisch"); return false;
        }
        if (value < 1 || value > 10) {
            p.sendMessage(Vars.PR + "\u00a7c\u00dcm, ich denke zwischen 1 und 10 sollte reichen"); return false;
        }
        if (p.isFlying()) {
            p.setFlySpeed(value / 10);
            p.sendMessage(Vars.PR + "\u00a7aDeine Fluggeschwindigkeit wurde auf \u00a7e" + value + " \u00a7agesetzt");
        } else {
            p.setWalkSpeed(value / 10);
            p.sendMessage(Vars.PR + "\u00a7aDeine Laufgeschwindigkeit wurde auf \u00a7e" + value + " \u00a7agesetzt");
        }
        return false;
    }
}
