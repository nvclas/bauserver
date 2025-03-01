package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.main.BauserverPlugin;
import de.theniclas.bauplugin.utils.Vars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDtpa implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (sender instanceof Player p) {
                if (args.length >= 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (!target.getName().equals(p.getName())) {
                            if (!Vars.tpa.containsKey(target.getUniqueId()
                                    .toString()) || !Vars.tpa.get(target.getUniqueId().toString())
                                    .equals(p.getUniqueId().toString())) {
                                Vars.tpa.put(target.getUniqueId().toString(), p.getUniqueId().toString());
                                p.sendMessage(Vars.pr + "§6Du hast §e" + target.getName() + " §6eine Anfrage gesendet");
                                target.sendMessage(Vars.pr + "§e" + p.getName() + " §6möchte sich zu dir teleportieren");
                                target.sendMessage(Vars.pr + "§6Die Anfrage ist §e30 Sekunden §6lang gültig");
                                Component message = Component.text()
                                        .append(Component.text("[" + Vars.pr + "] ", NamedTextColor.GRAY))
                                        .append(Component.text("Klicke hier zum Annehmen: ", NamedTextColor.DARK_GRAY))
                                        .append(Component.text("[ANNEHMEN]", NamedTextColor.GREEN)
                                                .clickEvent(ClickEvent.runCommand("/tpaccept " + sender.getName()))
                                                .hoverEvent(HoverEvent.showText(Component.text("Klicken zum Annehmen",
                                                        NamedTextColor.GREEN))))
                                        .build();
                                target.sendMessage(message);
                                Bukkit.getScheduler().runTaskLater(BauserverPlugin.getPlugin(), () -> {
                                    if (Vars.tpa.containsKey(target.getUniqueId()
                                            .toString()) && Vars.tpa.get(target.getUniqueId().toString())
                                            .equals(p.getUniqueId().toString())) {
                                        Vars.tpa.remove(target.getUniqueId().toString(),
                                                p.getUniqueId().toString());
                                        target.sendMessage(Vars.pr + "§cDie Anfrage von §e" + p.getName() + " §cist abgelaufen");
                                        p.sendMessage(Vars.pr + "§cDeine Anfrage an §e" + target.getName() + " §cist abgelaufen");
                                    }
                                }, 20 * 30);
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
