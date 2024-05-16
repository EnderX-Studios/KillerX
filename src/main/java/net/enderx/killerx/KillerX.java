package net.enderx.killerx;

import lombok.Getter;
import net.enderx.killerx.commands.KillerXCommand;
import net.enderx.killerx.database.DatabaseManager;
import net.enderx.killerx.listener.PlayerListener;
import net.enderx.killerx.placeholder.PlaceholderHook;
import net.enderx.killerx.utils.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class KillerX extends JavaPlugin {

    private MySQL mySQL;
    @Getter private DatabaseManager manager;

    @Override
    public void onEnable() {
        this.mySQL = new MySQL(this);
        this.getMySQL().connect();
        new PlaceholderHook(this).register();

        this.manager = new DatabaseManager(this);
        saveDefaultConfig();

        this.getLogger().info("\n§c" +
                "#    _  ___ _ _            __   __\n" +
                "#   | |/ (_| | |           \\ \\ / /\n" +
                "#   | ' / _| | | ___ _ __   \\ V / \n" +
                "#   |  < | | | |/ _ | '__|   > <  \n" +
                "#   | . \\| | | |  __| |     / . \\ \n" +
                "#   |_|\\_|_|_|_|\\___|_|    /_/ \\_\\\n" +
                "#                                 \n" +
                "#                                 \n" +
                "§bby Akari_my and TricolorAsp1209\n" +
                "github: https://github.com/Akari-my");
        Objects.requireNonNull(getCommand("killerx")).setExecutor(new KillerXCommand(this, manager));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public DatabaseManager getDatabase() {
        return manager;
    }
}
