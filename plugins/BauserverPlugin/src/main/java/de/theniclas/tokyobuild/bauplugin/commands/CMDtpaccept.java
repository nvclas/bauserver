package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDtpaccept implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (sender instanceof Player p) {
                if (args.length >= 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (Vars.tpa.containsKey(p.getUniqueId().toString())) {
                            if (Vars.tpa.containsValue(target.getUniqueId().toString()) && Vars.tpa.get(p.getUniqueId()
                                    .toString()).equals(target.getUniqueId().toString())) {
                                target.teleport(p);
                                target.sendMessage(Vars.pr + "§aDeine Anfrage wurde angenommen");
                                p.sendMessage(Vars.pr + "§aDu hast die Anfrage angenommen");
                                Vars.tpa.remove(p.getUniqueId().toString(), target.getUniqueId().toString());
                            } else {
                                p.sendMessage(Vars.pr + "§e" + target.getName() + " §chat dir keine Anfrage gesendet, dafür aber jemand anderes");
                            }
                        } else {
                            p.sendMessage(Vars.pr + "§cNiemand will sich zu dir teleportieren :(");
                        }
                    } else {
                        p.sendMessage(Vars.pr + "§cUps, der ist wohl schon offline gegangen");
                    }
                } else {
                    p.sendMessage(Vars.pr + "§cWessen Anfrage soll denn angenommen werden?");
                }
            }
        }
        return false;
    }

}
