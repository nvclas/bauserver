package de.theniclas.bauplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.theniclas.bauplugin.utils.InvHolder;
import de.theniclas.bauplugin.utils.Vars;

public class CMDblocks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return false;
        if (!p.hasPermission("bs.blocks")) { p.sendMessage(Vars.NOPERM); return false; }
        Inventory inv = Bukkit.createInventory(new InvHolder("blocks"), 9 * 5,
                Vars.mini("<dark_purple><bold>Spezialblöcke"));
        inv.addItem(Vars.getSkull("http://textures.minecraft.net/texture/732dbd6612e9d3f42947b5ca8785bfb334258f3ceb83ad69a5cdeebea4cd65", "<white>Roter Pilzblock"));
        inv.addItem(Vars.getSkull("http://textures.minecraft.net/texture/fa49eca0369d1e158e539d78149acb1572949b88ba921d9ee694fea4c726b3", "<white>Brauner Pilzblock"));
        inv.addItem(Vars.getSkull("http://textures.minecraft.net/texture/3fa39ccf4788d9179a8795e6b72382d49297b39217146eda68ae78384355b13", "<white>Pilzsporenblock"));
        inv.addItem(Vars.getSkull("http://textures.minecraft.net/texture/f55fa642d5ebcba2c5246fe6499b1c4f6803c10f14f5299c8e59819d5dc", "<white>Pilzstielblock"));
        p.openInventory(inv);
        return false;
    }
}
