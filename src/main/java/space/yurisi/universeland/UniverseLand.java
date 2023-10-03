package space.yurisi.universeland;

import org.bukkit.plugin.java.JavaPlugin;

public final class UniverseLand extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("UniverseLandを読み込みました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
