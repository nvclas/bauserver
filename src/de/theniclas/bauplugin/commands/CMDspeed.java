package de.theniclas.bauplugin.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;

public class CMDspeed implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("speed")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("bs.speed")) {
					if(args.length >= 1) {
						if(NumberUtils.isNumber(args[0])) {
							if(Float.parseFloat(args[0]) >= 1 && Float.parseFloat(args[0]) <= 10) {
								if(p.isFlying()) {
									p.setFlySpeed(Float.parseFloat(args[0]) / 10);
									p.sendMessage(Vars.pr + "�aDein Fluggeschwindigkeit wurde auf �e" + args[0] + " �agesetzt");
								} else {
									p.setWalkSpeed(Float.parseFloat(args[0]) / 10);
									p.sendMessage(Vars.pr + "�aDein Laufgeschwindigkeit wurde auf �e" + args[0] + " �agesetzt");
								}
							} else {
								p.sendMessage(Vars.pr + "�c�hm, ich denke zwischen 1 und 10 sollte reichen");
							}
						} else {
							p.sendMessage(Vars.pr + "�cZahlen w�ren praktisch");
						}
					} else {
						if(p.isFlying()) {
							p.sendMessage(Vars.pr + "�aDeine Fluggeschwindigkeit betr�gt derzeit �e" + p.getFlySpeed() * 10 + " �8(Standard: 1)");
						} else {
							p.sendMessage(Vars.pr + "�aDeine Laufgeschwindigkeit betr�gt derzeit �e" + p.getWalkSpeed() * 10 + " �8(Standard: 2)");
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
