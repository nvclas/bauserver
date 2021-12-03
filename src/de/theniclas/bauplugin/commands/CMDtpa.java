package de.theniclas.bauplugin.commands;



import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Vars;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class CMDtpa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("tpa")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length >= 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null) {
						if(!target.getName().equals(p.getName())) {
							if(!Vars.tpa.containsKey(target.getUniqueId().toString()) || !Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
								Vars.tpa.put(target.getUniqueId().toString(), p.getUniqueId().toString());
								p.sendMessage(Vars.pr + "§6Du hast §e" + target.getName() + " §6eine Anfrage gesendet");
								target.sendMessage(Vars.pr + "§e" + p.getName() + " §6möchte sich zu dir teleportieren");
								target.sendMessage(Vars.pr + "§6Die Anfrage ist §e30 Sekunden §6lang gültig");
								IChatBaseComponent comp = ChatSerializer.a("[{\"text\":\"" + Vars.pr +"§7Klicke hier zum Annehmen§8: \"},{\"text\":\"§a[ANNEHMEN]\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpaccept " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aKlicken zum Annehmen\"}}]");
								PacketPlayOutChat chat = new PacketPlayOutChat(comp);
								((CraftPlayer)target).getHandle().playerConnection.sendPacket(chat);
								Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
									public void run() {
										if(Vars.tpa.containsKey(target.getUniqueId().toString()) && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
											Vars.tpa.remove(target.getUniqueId().toString(), p.getUniqueId().toString());
											target.sendMessage(Vars.pr + "§cDie Anfrage von §e" + p.getName() + " §cist abgelaufen");
											p.sendMessage(Vars.pr + "§cDeine Anfrage an §e" + target.getName() + " §cist abgelaufen");
										}
									}
								}, 20*30);
							} else {
								p.sendMessage(Vars.pr + "§cDu hast §e" + target.getName() + " §cbereits eine Anfrage gesendet");
							}
						} else {
							p.sendMessage(Vars.pr + "§cDu bist doch schon bei dir");
						}
					} else {
						p.sendMessage(Vars.pr + "§e" + args[0] + " §cist nicht online");
					}
				} else {
					p.sendMessage(Vars.pr + "§cWem willst du eine Anfrage schicken?");
				}
			}
		}
		return false;
	}
}
