package de.theniclas.bauplugin.commands;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.Bauserver;
import de.theniclas.bauplugin.utils.Vars;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CMDtpa implements CommandExecutor {

    private final Bauserver plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (args.length < 1) { p.sendMessage(Vars.PR + "§cWem willst du eine Anfrage schicken?"); return true; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage(Vars.PR + "§e" + args[0] + " §cist nicht online"); return true; }
        if (target.getName().equals(p.getName())) { p.sendMessage(Vars.PR + "§cDu bist doch schon bei dir"); return true; }
        if (Vars.tpa.containsKey(target.getUniqueId().toString())
                && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
            p.sendMessage(Vars.PR + "§cDu hast §e" + target.getName() + " §cbereits eine Anfrage gesendet");
            return true;
        }
        Vars.tpa.put(target.getUniqueId().toString(), p.getUniqueId().toString());
        p.sendMessage(Vars.PR + "§6Du hast §e" + target.getName() + " §6eine Anfrage gesendet");
        target.sendMessage(Vars.PR + "§e" + p.getName() + " §6möchte sich zu dir teleportieren");
        target.sendMessage(Vars.PR + "§6Die Anfrage ist §e30 Sekunden §6lang gültig");
        target.sendMessage(Component.text(Vars.PR + "Klicke hier: ")
                .append(Component.text("[ANNEHMEN]")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/tpaccept " + p.getName()))));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (Vars.tpa.containsKey(target.getUniqueId().toString())
                    && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
                Vars.tpa.remove(target.getUniqueId().toString());
                target.sendMessage(Vars.PR + "§cDie Anfrage von §e" + p.getName() + " §cist abgelaufen");
                if (p.isOnline()) p.sendMessage(Vars.PR + "§cDeine Anfrage an §e" + target.getName() + " §cist abgelaufen");
            }
        }, (long) 20 * 30);
        return true;
    }
}
