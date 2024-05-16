package net.enderx.killerx.listener;

import net.enderx.killerx.KillerX;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final KillerX plugin;

    public PlayerListener(KillerX plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        UUID playerUUID = event.getPlayer().getUniqueId();
        plugin.getDatabase().registerPlayer(playerUUID);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            Player killer = player.getKiller();
            UUID playerUUID = player.getUniqueId();

            plugin.getDatabase().addDeath(playerUUID);
            plugin.getDatabase().setKillStreak(playerUUID, 0);

            if (killer == null) {
                return;
            }

            UUID killerUUID = killer.getUniqueId();

            plugin.getDatabase().addKill(killerUUID);
            plugin.getDatabase().addKillStreak(killerUUID);
        }
    }
}
