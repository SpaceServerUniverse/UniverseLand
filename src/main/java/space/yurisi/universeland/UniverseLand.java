package space.yurisi.universeland;

import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universeland.command.LandCommand;
import space.yurisi.universeland.manager.EventManager;

import java.util.Objects;

public final class UniverseLand extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("land")).setExecutor(new LandCommand());
        EventManager.init(this);
        getLogger().info("UniverseLandを読み込みました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
