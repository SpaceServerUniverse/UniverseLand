package space.yurisi.universeland.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import space.yurisi.universeland.manager.LandDataManager;
import space.yurisi.universeland.store.LandData;
import space.yurisi.universeland.utils.Vector2;

import java.util.UUID;

public class TouchEvent implements Listener {

    private LandDataManager landDataManager = new LandDataManager();

    @EventHandler(ignoreCancelled = true)
    public void onTouch(PlayerInteractEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (landDataManager.getLandData(uuid) == null) {
            initLandData(player);
        }

        LandData landData = landDataManager.getLandData(uuid);

        if (landData.isSelectLand()) {
            Block block = event.getClickedBlock();

            int x = block != null ? block.getX() : 0;
            int z = block != null ? block.getZ() : 0;

            if (landData.getStartPosition() == null) {
                landData.setStartPosition(new Vector2(x, z));
                player.sendMessage(Component.text("StartPositionを設定しました (X: " + x + ", Z: " + z + ")"));
            } else {
                landData.setEndPosition(new Vector2(x, z));
                landData.setSelectLand(false);
                player.sendMessage(Component.text("EndPositionを設定しました (X: " + x + ", Z: " + z + ") (サイズ: " + landData.getLand().getSize() + "ブロック)"));
            }
        }
    }

    private void initLandData(Player player) {
        landDataManager.setLandData(player.getUniqueId());
    }
}
