package space.yurisi.universeland.event.block;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import space.yurisi.universecore.exception.LandNotFoundException;
import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;
import space.yurisi.universeland.utils.BoundingBox;

public class FromToEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFromTo(BlockFromToEvent event) throws LandNotFoundException {
        Block block = event.getBlock();
        Block to = event.getToBlock();

        LandData data = LandDataManager.getInstance().getOverlapLandData(new BoundingBox(to.getX(), to.getZ(), to.getX(), to.getZ(), to.getWorld().getName()));

        if (data != null) event.setCancelled(true);
    }
}
