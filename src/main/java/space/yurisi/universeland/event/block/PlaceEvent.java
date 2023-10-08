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

public class PlaceEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) throws LandNotFoundException {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        LandData data = LandDataManager.getInstance().getOverlapLandData(new BoundingBox(block.getX(), block.getZ(), block.getX(), block.getZ(), block.getWorld().getName()));

        if (data != null && !data.isOwner(player) && !data.canAccess(player)) {
            event.setCancelled(true);

            OfflinePlayer p = UniverseLand.getInstance().getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
        }
    }
}
