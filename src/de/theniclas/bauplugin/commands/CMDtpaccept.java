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
		if(command.getName().equalsIgnoreCase("tpaccept")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length >= 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(Vars.tpa.containsKey(p.getUniqueId().toString())) {
							if(Vars.tpa.containsValue(target.getUniqueId().toString()) && Vars.tpa.get(p.getUniqueId().toString()).equals(target.getUniqueId().toString())) {
								target.teleport(p);
								target.sendMessage(Vars.pr + "�aDeine Anfrage wurde angenommen");
								p.sendMessage(Vars.pr + "�aDu hast die Anfrage angenommen");
								Vars.tpa.remove(p.getUniqueId().toString(), target.getUniqueId().toString());
							} else {
								p.sendMessage(Vars.pr + "�e" + target.getName() + " �chat dir keine Anfrage gesendet, daf�r aber jemand anderes");
							}
						} else {
							p.sendMessage(Vars.pr + "�cNiemand will sich zu dir teleportieren :(");
						}
					} else {
						p.sendMessage(Vars.pr + "�cUps, der ist wohl schon offline gegangen");
					}
				} else {
					p.sendMessage(Vars.pr + "�cWessen Anfrage soll denn angenommen werden?");
				}
			}
		}
		return false;
	}

}
