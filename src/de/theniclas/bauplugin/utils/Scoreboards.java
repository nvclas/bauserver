package de.theniclas.bauplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Scoreboards {

    private static Scoreboard sb;

    /** (Re)creates the scoreboard teams and assigns all online players. */
    public static void setScoreboard() {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        sb.registerNewTeam("0000Admin");
        sb.registerNewTeam("0001BuilderPlus");
        sb.registerNewTeam("0005Builder");
        sb.registerNewTeam("9999Gast");

        for (Player player : Bukkit.getOnlinePlayers()) {
            assignTeam(player);
        }
    }

    /** Assigns {@code p} to the appropriate team on the shared scoreboard. */
    public static void assignTeam(Player p) {
        String teamName;
        if (p.hasPermission("bs.admin")) {
            teamName = "0000Admin";
        } else if (p.hasPermission("bs.builderplus")) {
            teamName = "0001BuilderPlus";
        } else if (p.hasPermission("bs.builder")) {
            teamName = "0005Builder";
        } else {
            teamName = "9999Gast";
        }

        Team team = sb.getTeam(teamName);
        if (team != null) {
            team.addEntry(p.getName());
        }
        p.setScoreboard(sb);
    }
}
