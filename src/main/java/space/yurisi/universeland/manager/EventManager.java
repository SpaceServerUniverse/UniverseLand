package space.yurisi.universeland.manager;

import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.event.block.BreakEvent;
import space.yurisi.universeland.event.block.FromToEvent;
import space.yurisi.universeland.event.block.PlaceEvent;
import space.yurisi.universeland.event.player.TouchEvent;

public class EventManager {

    public static void init(UniverseLand plugin) {
        plugin.getServer().getPluginManager().registerEvents(new BreakEvent(), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new FromToEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TouchEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlaceEvent(), plugin);
    }
}
