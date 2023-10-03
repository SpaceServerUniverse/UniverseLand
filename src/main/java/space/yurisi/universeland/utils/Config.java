package space.yurisi.universeland.utils;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecore.UniverseCore;

import java.util.List;

public class Config {

    private final UniverseCore main;

    private FileConfiguration config = null;

    public Config(UniverseCore main){
        this.main = main;
        init();
    }

    private void init(){
        main.saveDefaultConfig();
        if (config != null) {
            main.reloadConfig();
        }
        this.config = main.getConfig();
    }

    public int getLandPrice(){
        return this.config.getInt("land-price");
    }

    public List<String> getDenyWorlds(){
        return this.config.getStringList("deny-worlds");
    }
}