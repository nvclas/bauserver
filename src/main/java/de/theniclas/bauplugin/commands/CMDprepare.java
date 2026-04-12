package de.theniclas.bauplugin.commands;

import org.bukkit.GameRule;
import org.bukkit.GameRules;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDprepare implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.worlds")) { p.sendMessage(Vars.NOPERM); return false; }
        if (!Vars.isOwner(p, p.getWorld().getName()) && !p.hasPermission("bs.admin")) {
            p.sendMessage(Vars.prefixed("<red>Du bist nicht der Ersteller dieser Welt")); return false;
        }
        p.getWorld().setTime(6000);
        p.getWorld().setGameRule(GameRules.SPAWN_MOBS, false);
        p.sendMessage(Vars.prefixed("<green>Mobspawning <yellow>deaktiviert"));
        p.getWorld().setGameRule(GameRules.MOB_GRIEFING, false);
        p.sendMessage(Vars.prefixed("<green>Mobgriefing <yellow>deaktiviert"));
        p.getWorld().setGameRule(GameRules.FIRE_SPREAD_RADIUS_AROUND_PLAYER, 0);
        p.sendMessage(Vars.prefixed("<green>Feuerausbreitung <yellow>deaktiviert"));
        p.getWorld().setGameRule(GameRules.RANDOM_TICK_SPEED, 0);
        p.sendMessage(Vars.prefixed("<green>Zufällige Blockupdates <yellow>deaktiviert"));
        p.getWorld().setStorm(false);
        p.getWorld().setThundering(false);
        p.sendMessage(Vars.prefixed("<green>Wetter <yellow>deaktiviert"));
        p.getWorld().setGameRule(GameRules.ADVANCE_TIME, false);
        p.sendMessage(Vars.prefixed("<green>Tag-/Nachtzyklus <yellow>deaktiviert"));
        return false;
    }
}
