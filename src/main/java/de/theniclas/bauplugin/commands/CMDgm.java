package de.theniclas.bauplugin.commands;

import de.theniclas.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDgm implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gm") && sender instanceof Player p) {
                if (p.hasPermission("bs.worlds")) {
                    if (args.length == 1) {
                        switch (args[0]) {
                            case "0":
                                p.setGameMode(GameMode.SURVIVAL);
                                p.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eSurvival");
                                break;
                            case "1":
                                p.setGameMode(GameMode.CREATIVE);
                                p.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eCreative");
                                break;
                            case "2":
                                p.setGameMode(GameMode.ADVENTURE);
                                p.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eAdventure");
                                break;
                            case "3":
                                p.setGameMode(GameMode.SPECTATOR);
                                p.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eSpectator");
                                break;
                            default:
                                p.sendMessage(Vars.pr + "§cAber diesen Spielmodus gibt's gar nicht");
                        }
                    } else if (args.length >= 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if ((Vars.isOwner(p,
                                    p.getWorld().getName()) && target.getWorld() == p.getWorld()) || p.hasPermission(
                                    "bs.admin")) {
                                switch (args[0]) {
                                    case "0":
                                        p.sendMessage(Vars.pr + "§e" + target.getName() + " §aist nun im Spielmodus §eSurvival");
                                        target.setGameMode(GameMode.SURVIVAL);
                                        target.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eSurvival");
                                        break;
                                    case "1":
                                        p.sendMessage(Vars.pr + "§e" + target.getName() + " §aist nun im Spielmodus §eCreative");
                                        target.setGameMode(GameMode.CREATIVE);
                                        target.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eCreative");
                                        break;
                                    case "2":
                                        p.sendMessage(Vars.pr + "§e" + target.getName() + " §aist nun im Spielmodus §eAdventure");
                                        target.setGameMode(GameMode.ADVENTURE);
                                        target.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eAdventure");
                                        break;
                                    case "3":
                                        p.sendMessage(Vars.pr + "§e" + target.getName() + " §aist nun im Spielmodus §eSpectator");
                                        target.setGameMode(GameMode.SPECTATOR);
                                        target.sendMessage(Vars.pr + "§aDu bist nun im Spielmodus §eSpectator");
                                        break;
                                    default:
                                        p.sendMessage(Vars.pr + "§cAber diesen Spielmodus gibt's gar nicht");
                                }
                            } else {
                                if (!Vars.isOwner(p, p.getWorld().getName())) {
                                    p.sendMessage(Vars.pr + "§cDu musst der Besitzer dieser Welt sein");
                                } else if (target.getWorld() != p.getWorld()) {
                                    p.sendMessage(Vars.pr + "§cDas Ziel muss sich in deiner Welt befinden");
                                }
                            }
                        } else {
                            p.sendMessage(Vars.pr + "§cDieser Spieler ist nicht online");
                        }
                    } else {
                        p.sendMessage(Vars.pr + "§cUps, fehlt da etwa eine Zahl?");
                    }
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }

        return false;
    }

}
