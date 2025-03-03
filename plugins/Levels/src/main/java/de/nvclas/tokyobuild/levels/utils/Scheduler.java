package de.nvclas.tokyobuild.levels.utils;

import de.nvclas.tokyobuild.levels.config.Data;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

    private final Map<Player, Integer> afkTimer;
    private final List<Player> afkPlayers;
    private final JavaPlugin plugin;
    
    public Scheduler(JavaPlugin plugin) {
        this.afkTimer = new HashMap<>();
        this.afkPlayers = new ArrayList<>();
        this.plugin = plugin;
    }

    public void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!afkPlayers.contains(all)) {
                    //AFK TIMER
                    if (afkTimer.containsKey(all)) {
                        afkTimer.put(all, afkTimer.get(all) + 1);
                    } else {
                        afkTimer.put(all, 0);
                    }
                    if (afkTimer.get(all) >= 5) {
                        afkPlayers.add(all);
                        all.sendMessage(Vars.pr + "�eDu bist nun AFK");
                    }

                    //XP TIMER
                    if (Data.getConfig().get("Levels." + all.getUniqueId()) != null) {
                        Methods.addXp(all, 1);
                    }
                }
            }
        }, (long) 20 * 60, (long) 20 * 60);
    }
}
