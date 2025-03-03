package de.nvclas.tokyobuild.levels.utils;

import java.util.ArrayList;

import de.nvclas.tokyobuild.levels.config.Data;
import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class Methods {
	
	public static int getLevel(Player p) {
		return Data.getConfig().getInt("Levels." + p.getUniqueId() + ".Level");
	}
	
	public static int getCurrentXp(Player p) {
		return Data.getConfig().getInt("Levels." + p.getUniqueId() + ".Xp");
	}
	
	public static int getNextLevelXp(Player p) {
		return Data.getConfig().getInt("Levels." + p.getUniqueId() + ".NextLevelXp");
	}
	
	public static void addXp(Player p, int amount) {
		int currentXp = getCurrentXp(p);
		int level = getLevel(p);
		int nextLevelXp = getNextLevelXp(p);
		currentXp = currentXp + amount;
		Data.getConfig().set("Levels." + p.getUniqueId() + ".Xp", currentXp);
		
		if(currentXp >= nextLevelXp) {
			for(; currentXp >= nextLevelXp; level++) {
				currentXp = currentXp - nextLevelXp;
				nextLevelXp = nextLevelXp + 10;
				p.sendMessage(Vars.pr + "�aGl�ckwunsch, du bist nun �bLevel " + Integer.valueOf(level + 1));
				Data.getConfig().set("Levels." + p.getUniqueId() + ".Level", level + 1);
				Data.getConfig().set("Levels." + p.getUniqueId() + ".NextLevelXp", nextLevelXp);
				Data.getConfig().set("Levels." + p.getUniqueId() + ".Xp", currentXp);
			}
			Data.getConfig().set("Levels." + p.getUniqueId() + ".Xp", currentXp);
			levelUp(p);
		}
		Data.saveConfiguration();
	}
	
	public static void addAchievement(Player p, String achievement, int xp) {
		ArrayList<String> unlockedAchievements = new ArrayList<>();
		if(Data.getConfig().getStringList("Levels." + p.getUniqueId() + ".Achievements").contains(achievement)) {
			return;
		}
		if(Data.getConfig().get("Levels." + p.getUniqueId() + ".Achievements") == null) {
			unlockedAchievements.add(achievement);
		} else {
            unlockedAchievements.addAll(Data.getConfig().getStringList("Levels." + p.getUniqueId() + ".Achievements"));
			unlockedAchievements.add(achievement);
		}
		p.sendMessage(Vars.pr + "�7�k11111111111111111111111111111111");
		p.sendMessage(Vars.pr);
		p.sendMessage(Vars.pr + "�aErfolg freigeschaltet�8: �e" + achievement);
		p.sendMessage(Vars.pr + "�b+" + xp + "XP");
		p.sendMessage(Vars.pr);
		p.sendMessage(Vars.pr + "�7�k11111111111111111111111111111111");
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
		addXp(p, xp);
		Data.getConfig().set("Levels." + p.getUniqueId() + ".Achievements", unlockedAchievements);
		Data.saveConfiguration();
		unlockedAchievements.clear();
	}
	
	public static void levelUp(Player p) {
		PermissionUser user = PermissionsEx.getUser(p);
		if(getLevel(p) == 10) {
			user.addGroup("deshi");
			user.removeGroup("shinjin");
			p.sendMessage(Vars.pr + "�aDu hast den Rang �eDeshi �aerreicht!");
		} else if(getLevel(p) == 20) {
			user.addGroup("puro");
			user.removeGroup("deshi");
			p.sendMessage(Vars.pr + "�aDu hast den Rang �6Puro �aerreicht!");
		} else if(getLevel(p) == 30) {
			user.addGroup("senpai");
			user.removeGroup("puro");
			p.sendMessage(Vars.pr + "�aDu hast den Rang �9Senpai �aerreicht!");
		} else if(getLevel(p) == 50) {
			user.addGroup("sensei");
			user.removeGroup("senpai");
			p.sendMessage(Vars.pr + "�aDu hast den Rang �cSensei �aerreicht!");
		}
	}
	
	public static boolean isUnlocked(Player p) {
		if(Data.getConfig().getConfigurationSection("Levels." + p.getUniqueId()) == null) {
			return false;
		}
		return true;
	}
	
}
