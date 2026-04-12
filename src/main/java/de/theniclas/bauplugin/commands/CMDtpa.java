package de.theniclas.bauplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Vars;

public class CMDtpa implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (args.length < 1) { p.sendMessage(Vars.PR + "\u00a7cWem willst du eine Anfrage schicken?"); return false; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage(Vars.PR + "\u00a7e" + args[0] + " \u00a7cist nicht online"); return false; }
        if (target.getName().equals(p.getName())) { p.sendMessage(Vars.PR + "\u00a7cDu bist doch schon bei dir"); return false; }
        if (Vars.tpa.containsKey(target.getUniqueId().toString())
                && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
            p.sendMessage(Vars.PR + "\u00a7cDu hast \u00a7e" + target.getName() + " \u00a7cbereits eine Anfrage gesendet");
            return false;
        }
        Vars.tpa.put(target.getUniqueId().toString(), p.getUniqueId().toString());
        p.sendMessage(Vars.PR + "\u00a76Du hast \u00a7e" + target.getName() + " \u00a76eine Anfrage gesendet");
        target.sendMessage(Vars.PR + "\u00a7e" + p.getName() + " \u00a76m\u00f6chte sich zu dir teleportieren");
        target.sendMessage(Vars.PR + "\u00a76Die Anfrage ist \u00a7e30 Sekunden \u00a76lang g\u00fcltig");
        target.sendMessage(Component.text(Vars.PR + "Klicke hier: ")
                .append(Component.text("[ANNEHMEN]")
                        .color(NamedTextColor.GREEN)
                        .clickEvent(ClickEvent.runCommand("/tpaccept " + p.getName()))));
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
            if (Vars.tpa.containsKey(target.getUniqueId().toString())
                    && Vars.tpa.get(target.getUniqueId().toString()).equals(p.getUniqueId().toString())) {
                Vars.tpa.remove(target.getUniqueId().toString());
                target.sendMessage(Vars.PR + "\u00a7cDie Anfrage von \u00a7e" + p.getName() + " \u00a7cist abgelaufen");
                if (p.isOnline()) p.sendMessage(Vars.PR + "\u00a7cDeine Anfrage an \u00a7e" + target.getName() + " \u00a7cist abgelaufen");
            }
        }, 20 * 30);
        return false;
    }
}
