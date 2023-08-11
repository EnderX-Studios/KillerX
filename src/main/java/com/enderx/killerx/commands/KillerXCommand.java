package com.enderx.killerx.commands;

import com.enderx.killerx.KillerX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillerXCommand implements CommandExecutor {

    private KillerX plugin;

    public KillerXCommand(KillerX plugin) {this.plugin = plugin;}


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("§5Running KillerX by EnderX Studios ");
            player.sendMessage("§5for support https://discord.gg/RkUWEDadMD");
        }

        return false;
    }
}
