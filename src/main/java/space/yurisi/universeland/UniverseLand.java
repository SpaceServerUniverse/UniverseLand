package space.yurisi.universeland;

import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecore.UniverseCoreAPI;
import space.yurisi.universecore.database.DatabaseManager;
import space.yurisi.universeland.command.LandCommand;
import space.yurisi.universeland.manager.EventManager;
import space.yurisi.universeland.utils.Config;

import java.util.Objects;

public final class UniverseLand extends JavaPlugin {

    private static UniverseLand instance;
    private DatabaseManager databaseManager;
    private final Config config = new Config(this);

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("land")).setExecutor(new LandCommand());
        EventManager.init(this);

        databaseManager = UniverseCoreAPI.getInstance().getDatabaseManager();

        getLogger().info("UniverseLandを読み込みました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static UniverseLand getInstance() {
        return instance;
    }

    public Config getPluginConfig() {
        return config;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
