package space.yurisi.universeland.manager;

import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.event.player.TouchEvent;

public class EventManager {

    public static void init(UniverseLand plugin) {
        plugin.getServer().getPluginManager().registerEvents(new TouchEvent(), plugin);
    }
}
