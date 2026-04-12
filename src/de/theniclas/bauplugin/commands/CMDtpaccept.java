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

        if (args.length < 1) {
            p.sendMessage(Vars.PREFIX + "§cWessen Anfrage soll denn angenommen werden?");
            return false;
        }

        Player requester = Bukkit.getPlayer(args[0]);
        if (requester == null) {
            p.sendMessage(Vars.PREFIX + "§cUps, der ist wohl schon offline gegangen");
            return false;
        }

        String pId         = p.getUniqueId().toString();
        String requesterId = requester.getUniqueId().toString();

        if (!Vars.tpa.containsKey(pId)) {
            p.sendMessage(Vars.PREFIX + "§cNiemand will sich zu dir teleportieren :(");
            return false;
        }

        if (!requesterId.equals(Vars.tpa.get(pId))) {
            p.sendMessage(Vars.PREFIX + "§e" + requester.getName() + " §chat dir keine Anfrage gesendet, dafür aber jemand anderes");
            return false;
        }

        Vars.tpa.remove(pId);
        requester.teleport(p);
        requester.sendMessage(Vars.PREFIX + "§aDeine Anfrage wurde angenommen");
        p.sendMessage(Vars.PREFIX + "§aDu hast die Anfrage angenommen");
        return false;
    }
}
