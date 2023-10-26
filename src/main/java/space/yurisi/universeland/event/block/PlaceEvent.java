package space.yurisi.universeland.event.block;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import space.yurisi.universecore.exception.LandNotFoundException;
import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;
import space.yurisi.universeland.utils.BoundingBox;

import java.util.UUID;

public class PlaceEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) throws LandNotFoundException {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        LandDataManager landDataManager = LandDataManager.getInstance();
        BoundingBox bb = new BoundingBox(block.getX(), block.getZ(), block.getX(), block.getZ(), block.getWorld().getName());

        if(!landDataManager.canAccess(player, bb)){
            event.setCancelled(true);

            LandData data = landDataManager.getOverlapLandData(bb);

            OfflinePlayer p = UniverseLand.getInstance().getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
        }
    }
}
