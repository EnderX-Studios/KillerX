package net.enderx.killerx.commands;

import lombok.SneakyThrows;
import net.enderx.killerx.KillerX;
import net.enderx.killerx.commands.arguments.setDeaths;
import net.enderx.killerx.commands.arguments.setKills;
import net.enderx.killerx.commands.arguments.setStreak;
import net.enderx.killerx.managers.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillerXCommand implements CommandExecutor {

    private final KillerX plugin;
    private final DatabaseManager manager;
    private final setKills setKills;
    private final setDeaths setDeaths;
    private final setStreak setStreak;

    public KillerXCommand(KillerX plugin, DatabaseManager manager){
        this.plugin = plugin;
        this.manager = manager;

        this.setKills = new setKills(this.plugin);
        this.setDeaths = new setDeaths(this.plugin);
        this.setStreak = new setStreak(this.plugin);
    }

    @Override
    @SneakyThrows
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("killerx"))
            return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.console_sender")));
            return true;
        }

        if (!(sender.hasPermission("killerx.use"))) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.errors.player_not_permession")));
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("§c=== §4KillerX §c===");
            sender.sendMessage("§c> §7/killerx §esetkills <playerName> <value>");
            sender.sendMessage("§c> §7/killerx §esetdeaths <playerName> <value>");
            sender.sendMessage("§c> §7/killerx §esetstreak <playerName> <value>");
            sender.sendMessage("§c=== §4KillerX §c===");
            return true;
        }

        String subCommand = args[0];
        String[] subCommandArgs = new String[args.length - 1];

        System.arraycopy(args, 1, subCommandArgs, 0, args.length - 1);

        if (subCommand.equalsIgnoreCase("setkills")) {
            setKills.execute(sender, subCommandArgs);
        } else if (subCommand.equalsIgnoreCase("setdeaths")) {
            setDeaths.execute(sender, subCommandArgs);
        } else if (subCommand.equalsIgnoreCase("setstreak")) {
            setStreak.execute(sender, subCommandArgs);
        } else {
            player.sendMessage(ChatColor.RED + "Unknown sub-command. Available sub-commands: setkills, setdeaths, setstreak");
        }
        return true;
    }
}
