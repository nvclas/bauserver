package de.theniclas.tokyobuild.bauplugin.commands;

import de.theniclas.tokyobuild.bauplugin.utils.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class CMDblocks implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("blocks") && sender instanceof Player p) {
                if (p.hasPermission("bs.blocks")) {
                    Inventory inv = Bukkit.createInventory(null, 9 * 5, "§5§lSpezialblöcke");
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/732dbd6612e9d3f42947b5ca8785bfb334258f3ceb83ad69a5cdeebea4cd65",
                            "§fRoter Pilzblock"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/fa49eca0369d1e158e539d78149acb1572949b88ba921d9ee694fea4c726b3",
                            "§fBrauner Pilzblock"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/3fa39ccf4788d9179a8795e6b72382d49297b39217146eda68ae78384355b13",
                            "§fPilzsporenblock"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/f55fa642d5ebcba2c5246fe6499b1c4f6803c10f14f5299c8e59819d5dc",
                            "§fPilzstielblock"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/8dd0cd158c2bb6618650e3954b2d29237f5b4c0ddc7d258e17380ab6979f071",
                            "§fVolle Steinstufe"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/d36e9862832a6fcc4855fa79c1aae5e73b9197ec6bbd38f6312efac680c63285",
                            "§fVolle Sandsteinstufe"));
                    inv.addItem(Vars.getSkull(
                            "http://textures.minecraft.net/texture/a2da7aa1ae6cc9d6c36c18a460d2398162edc2207fdfc9e28a7bf84d7441b8a2",
                            "§fVolle Rote Sandsteinstufe"));

                    p.openInventory(inv);
                    p.updateInventory();
                } else {
                    p.sendMessage(Vars.noperm);
                }
            }

        return false;
    }
}
