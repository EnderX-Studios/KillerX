package net.enderx.killerx.commands.arguments;

import net.enderx.killerx.KillerX;
import net.enderx.killerx.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class setStreak implements SubCommand {

    private final KillerX plugin;

    public setStreak(KillerX plugin){
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.not_value")));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        if (!plugin.getImanager().isRegistered(target)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.player_not_found")));
            return;
        }

        try {
            int value = Integer.parseInt(args[1]);

            plugin.getImanager().setKillStreak(target.getUniqueId(), value);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.commands.setStreak").replace("%player%", target.getName()).replace("%value%", String.valueOf(value))));

        } catch (NumberFormatException x) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.not_value")));
        }
    }
}
