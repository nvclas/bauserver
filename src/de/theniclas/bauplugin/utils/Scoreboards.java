package de.theniclas.bauplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class Scoreboards {
	
	private static Scoreboard sb;

    public static void setScoreboard() {
       
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
       
        sb.registerNewTeam("0000Admin");
        sb.registerNewTeam("0001BuilderPlus");
        sb.registerNewTeam("0005Builder");
        sb.registerNewTeam("9999Gast");
       
        for(Player all : Bukkit.getOnlinePlayers()) {
            setTeams(all);
        }
       
    }
   
    @SuppressWarnings("deprecation")
    private static void setTeams(Player p) {
        String team = "";
        if(p.hasPermission("bs.admin")) {
            team = "0000Admin";
        } else if(p.hasPermission("bs.builderplus")) {
        	team = "0001BuilderPlus";
        } else if(p.hasPermission("bs.builder")) {
            team = "0005Builder";
        } else {
            team = "9999Gast";
        }
       
        sb.getTeam(team).addPlayer(p);
        p.setScoreboard(sb);
       
    }
}
