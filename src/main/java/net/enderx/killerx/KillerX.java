package net.enderx.killerx;

import lombok.Getter;
import lombok.SneakyThrows;
import net.enderx.killerx.commands.KillerXCommand;
import net.enderx.killerx.commands.StatsCommand;
import net.enderx.killerx.managers.DatabaseManager;
import net.enderx.killerx.managers.IDataManager;
import net.enderx.killerx.managers.YMLManager;
import net.enderx.killerx.listener.PlayerListener;
import net.enderx.killerx.placeholder.PlaceholderHook;
import net.enderx.killerx.utils.MySQL;
import net.enderx.killerx.utils.YMLStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillerX extends JavaPlugin {

    @Getter private static KillerX instance;
    private MySQL mySQL;
    @Getter private DatabaseManager manager;
    @Getter private YMLManager ymlManager;
    @Getter private YMLStorage ymlStorage;
    @Getter private IDataManager Imanager;

    @Override
    public void onEnable() {

        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        storageManager();
        registerCommands();


        new PlaceholderHook(this).register();


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
                "github: https://github.com/EnderX-Studios");

    }


    private void registerCommands(){
        getCommand("killerx").setExecutor(new KillerXCommand(this, manager));
        getCommand("stats").setExecutor(new StatsCommand(this, manager));
    }

    @SneakyThrows
    private void storageManager() {
        if (isMySQL()) {
            this.mySQL = new MySQL(this);
            this.mySQL.connect();
            if (!this.mySQL.isConnected()) {
                getLogger().severe("Could not connect to MySQL database! Disabling plugin.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            this.manager = new DatabaseManager(this);
            this.Imanager = this.manager;
        } else if (isYML()) {
            this.ymlStorage = new YMLStorage(this, "player_stats.yml", getDataFolder().getPath());
            this.ymlManager = new YMLManager(ymlStorage);
            this.Imanager = this.ymlManager;
        } else {
            throw new IllegalStateException("Unsupported storage method type: " + getDatabaseType());
        }
    }

    public void onDisable(){
        if (mySQL != null) {
            mySQL.disconnect();
        }

    }




    public MySQL getMySQL() {
        return mySQL;
    }

    public DatabaseManager getDatabase() {
        return manager;
    }

    public String getDatabaseType() {
        return getConfig().getString("Storage.type").toLowerCase();
    }

    public boolean isMySQL() {
        return getDatabaseType().equals("mysql");
    }

    public boolean isSQLite() {
        return getDatabaseType().equals("sqlite");
    }

    public boolean isYML() {
        return getDatabaseType().equals("yml");
    }

    public boolean isSQL() {
        String dbType = getDatabaseType();
        return isMySQL() || isSQLite();
    }



}
