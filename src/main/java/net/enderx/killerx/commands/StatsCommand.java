package net.enderx.killerx.commands;

import net.enderx.killerx.KillerX;
import net.enderx.killerx.database.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class StatsCommand implements CommandExecutor {

    private KillerX plugin;
    private final DatabaseManager manager;

    public StatsCommand(KillerX plugin, DatabaseManager manager){
        this.plugin = plugin;
        this.manager = manager;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.console_sender")));
            return true;
        }

        Player player = (Player) sender;


        player.sendMessage("§7Statistics of §c" + player.getName());
        player.sendMessage("");
        player.sendMessage("§7Kills: §c" + plugin.getDatabase().getKills(player.getUniqueId()));
        player.sendMessage("§7Deaths: §c" + plugin.getDatabase().getDeaths(player.getUniqueId()));
        player.sendMessage("§7Streak: §c" + plugin.getDatabase().getKillStreak(player.getUniqueId()));

        return false;
    }
}
