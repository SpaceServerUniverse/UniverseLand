package space.yurisi.universeland.utils;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universeland.UniverseLand;

import java.util.List;

public class Config {

    private final UniverseLand main;

    private FileConfiguration config = null;

    public Config(UniverseLand main) {
        this.main = main;
        init();
    }

    private void init() {
        main.saveDefaultConfig();
        if (config != null) {
            main.reloadConfig();
        }
        this.config = main.getConfig();
    }

    public Long getLandPrice() {
        return this.config.getLong("land-price");
    }

    public List<String> getDenyWorlds() {
        return this.config.getStringList("deny-worlds");
    }
}