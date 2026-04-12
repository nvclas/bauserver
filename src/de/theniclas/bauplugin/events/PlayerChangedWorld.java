package de.theniclas.bauplugin.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.permissions.PermissionAttachment;

import de.theniclas.bauplugin.main.Main;
import de.theniclas.bauplugin.utils.Configs;
import de.theniclas.bauplugin.utils.Vars;

public class PlayerChangedWorld implements Listener {

    /**
     * Permissions granted to world owners / trusted players for WorldEdit and
     * companion build tools.  These mirror the soft-depend plugins declared in
     * plugin.yml.
     */
    private static final List<String> TOOL_PERMISSIONS = List.of(
            "worldedit.history.undo", "worldedit.history.redo",
            "worldedit.navigation.jumpto.cmd", "worldedit.navigation.thru.cmd",
            "worldedit.navigation.up",
            "worldedit.wand",
            "worldedit.selection.pos", "worldedit.selection.chunk",
            "worldedit.selection.contract", "worldedit.selection.expand",
            "worldedit.selection.outset", "worldedit.selection.inset",
            "worldedit.selection.trim", "worldedit.selection.count",
            "worldedit.analysis.count", "worldedit.analysis.distr",
            "worldedit.region.set", "worldedit.region.replace",
            "worldedit.region.overlay", "worldedit.region.walls",
            "worldedit.region.faces", "worldedit.region.smooth",
            "worldedit.region.move", "worldedit.region.stack",
            "worldedit.region.naturalize", "worldedit.region.line",
            "worldedit.region.curve", "worldedit.region.cylinder",
            "worldedit.region.hollow", "worldedit.region.fill",
            "worldedit.region.fillr", "worldedit.region.drain",
            "worldedit.region.fixliquid", "worldedit.region.center",
            "worldedit.generation.cylinder", "worldedit.generation.sphere",
            "worldedit.generation.pyramid", "worldedit.generation.forest",
            "worldedit.generation.pumpkins", "worldedit.generation.shape",
            "worldedit.clipboard.copy", "worldedit.clipboard.cut",
            "worldedit.clipboard.paste", "worldedit.clipboard.rotate",
            "worldedit.clipboard.flip", "worldedit.clipboard.load",
            "worldedit.clipboard.save", "worldedit.clipboard.clear",
            "worldedit.tool.replacer", "worldedit.tool.data-cycler",
            "worldedit.tool.floodfill", "worldedit.tool.deltree",
            "worldedit.tool.farwand", "worldedit.tool.lrbuild",
            "worldedit.brush.sphere", "worldedit.brush.cylinder",
            "worldedit.brush.smooth", "worldedit.brush.gravity",
            "worldedit.brush.clipboard", "worldedit.brush.forest",
            "worldedit.brush.raise", "worldedit.brush.lower",
            "worldedit.biome.set", "worldedit.biome.info",
            "gobrush.use", "gopaint.use",
            "voxelsniper.sniper", "voxelsniper.brush",
            "astools.use"
    );

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        // Notify players in the affected worlds
        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.getWorld() == e.getFrom()) {
                other.sendMessage("§e" + p.getName() + " §7hat die Welt verlassen");
            } else if (other.getWorld() == p.getWorld()) {
                other.sendMessage("§e" + p.getName() + " §7hat die Welt betreten");
            }
        }

        // Update build-tool permissions for non-admins
        if (!p.hasPermission("bs.admin")) {
            if (Vars.isOwner(p, p.getWorld().getName()) || Vars.isTrusted(p, p.getWorld().getName())) {
                grantToolPermissions(p);
            } else {
                revokeToolPermissions(p);
            }
        }

        // Unload the previous world when it is empty and is not the spawn world
        String spawnWorld = Configs.worldsConfig.getString("Spawn.World");
        if (e.getFrom().getPlayers().isEmpty()
                && !e.getFrom().getName().equals(spawnWorld)) {
            Bukkit.unloadWorld(e.getFrom(), true);
        }
    }

    private static void grantToolPermissions(Player p) {
        revokeToolPermissions(p); // remove any stale attachment first
        PermissionAttachment attachment = p.addAttachment(Main.getPlugin());
        for (String perm : TOOL_PERMISSIONS) {
            attachment.setPermission(perm, true);
        }
        Vars.toolPermissions.put(p.getUniqueId(), attachment);
    }

    private static void revokeToolPermissions(Player p) {
        PermissionAttachment old = Vars.toolPermissions.remove(p.getUniqueId());
        if (old != null) {
            p.removeAttachment(old);
        }
    }
}
