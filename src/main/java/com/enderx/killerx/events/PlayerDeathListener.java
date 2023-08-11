package com.enderx.killerx.events;

import com.enderx.killerx.database.DatabaseManager;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private DatabaseManager dbManager;

    public PlayerDeathListener(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @EventHandler
    @SneakyThrows
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {
            dbManager.incrementKillCount(killer.getUniqueId().toString());
        }

        dbManager.incrementDeathCount(player.getUniqueId().toString());

        if (killer != null) {
            dbManager.incrementKillStreak(killer.getUniqueId().toString());
        }

        dbManager.resetKillStreak(player.getUniqueId().toString());
    }
}
