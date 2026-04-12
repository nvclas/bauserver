package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDtpaccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (args.length < 1) { p.sendMessage(Vars.prefixed("<red>Wessen Anfrage soll angenommen werden?")); return false; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage(Vars.prefixed("<red>Der Spieler ist nicht online")); return false; }
        if (!Vars.tpa.containsKey(p.getUniqueId().toString())) {
            p.sendMessage(Vars.prefixed("<red>Niemand will sich zu dir teleportieren :(")); return false;
        }
        if (!Vars.tpa.get(p.getUniqueId().toString()).equals(target.getUniqueId().toString())) {
            p.sendMessage(Vars.prefixed("<yellow>" + target.getName() + " <red>hat dir keine Anfrage gesendet")); return false;
        }
        target.teleport(p);
        target.sendMessage(Vars.prefixed("<green>Deine Anfrage wurde angenommen"));
        p.sendMessage(Vars.prefixed("<green>Du hast die Anfrage angenommen"));
        Vars.tpa.remove(p.getUniqueId().toString());
        return false;
    }
}
