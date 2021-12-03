package de.theniclas.levels.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.theniclas.bauplugin.utils.Vars;
import de.theniclas.levels.main.Main;

public class Scheduler {
	
	public static HashMap<Player, Integer> afk = new HashMap<>();
	public static ArrayList<Player> afkPlayers = new ArrayList<>();
	
	public static void startScheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			public void run() {
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(!afkPlayers.contains(all)) {
						//AFK TIMER
						if(afk.containsKey(all)) {
							afk.put(all, afk.get(all) + 1);
						} else {
							afk.put(all, 0);
						}
						if(afk.get(all) >= 5) {
							afkPlayers.add(all);
							all.sendMessage(Vars.pr + "§eDu bist nun AFK");
						}
						
						//XP TIMER
						if(Data.getConfig().get("Levels." + all.getUniqueId().toString()) != null) {
							Methods.addXp(all, 1);
						}
					}
				}
			}
		}, 20*60, 20*60);
	}
}
