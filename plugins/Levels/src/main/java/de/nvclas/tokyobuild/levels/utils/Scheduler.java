package de.nvclas.tokyobuild.levels.utils;

import de.nvclas.tokyobuild.bauplugin.utils.Vars;
import de.nvclas.tokyobuild.levels.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

    public static Map<Player, Integer> afk = new HashMap<>();
    public static List<Player> afkPlayers = new ArrayList<>();

    public static void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> {

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!afkPlayers.contains(all)) {
                    //AFK TIMER
                    if (afk.containsKey(all)) {
                        afk.put(all, afk.get(all) + 1);
                    } else {
                        afk.put(all, 0);
                    }
                    if (afk.get(all) >= 5) {
                        afkPlayers.add(all);
                        all.sendMessage(AntiBlockUpdate.PREFIX + "�eDu bist nun AFK");
                    }

                    //XP TIMER
                    if (Data.getConfig().get("Levels." + all.getUniqueId().toString()) != null) {
                        Methods.addXp(all, 1);
                    }
                }
            }
        }, 20 * 60, 20 * 60);
    }
}
