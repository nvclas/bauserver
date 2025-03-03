package de.nvclas.tokyobuild.levels.main;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.nvclas.tokyobuild.levels.achievements.CreateWorld;
import de.nvclas.tokyobuild.levels.achievements.EatGoldenApple;
import de.nvclas.tokyobuild.levels.achievements.EnterNetherPortal;
import de.nvclas.tokyobuild.levels.achievements.FirstBlock;
import de.nvclas.tokyobuild.levels.achievements.PlaceBlocks;
import de.nvclas.tokyobuild.levels.achievements.SummonWither;
import de.nvclas.tokyobuild.levels.achievements.VoidDeath;
import de.nvclas.tokyobuild.levels.commands.CMDaddxp;
import de.nvclas.tokyobuild.levels.commands.CMDlevel;
import de.nvclas.tokyobuild.levels.commands.CMDlock;
import de.nvclas.tokyobuild.levels.commands.CMDunlock;
import de.nvclas.tokyobuild.levels.listeners.PlayerMoveListener;
import de.nvclas.tokyobuild.levels.utils.Scheduler;

import java.util.Objects;
import java.util.logging.Level;

@Getter
public class Main extends JavaPlugin {

	private final Scheduler scheduler = new Scheduler(this);
	
	@Override
	public void onEnable() {
		
		//Commands
		Objects.requireNonNull(this.getCommand("level")).setExecutor(new CMDlevel());
		Objects.requireNonNull(this.getCommand("unlock")).setExecutor(new CMDunlock());
		Objects.requireNonNull(this.getCommand("lock")).setExecutor(new CMDlock());
		Objects.requireNonNull(this.getCommand("addxp")).setExecutor(new CMDaddxp());

		//Achievements
		Bukkit.getPluginManager().registerEvents(new FirstBlock(), this);
		Bukkit.getPluginManager().registerEvents(new VoidDeath(), this);
		Bukkit.getPluginManager().registerEvents(new CreateWorld(), this);
		Bukkit.getPluginManager().registerEvents(new PlaceBlocks(), this);
		Bukkit.getPluginManager().registerEvents(new EatGoldenApple(), this);
		Bukkit.getPluginManager().registerEvents(new SummonWither(), this);
		Bukkit.getPluginManager().registerEvents(new EnterNetherPortal(), this);
		
		//Events
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
		
		//Miscellaneous
		scheduler.startScheduler();
		
		getLogger().log(Level.INFO, "Levels geladen");
	}
}
