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

    private final LandDataManager landDataManager = new LandDataManager();

    @EventHandler(ignoreCancelled = true)
    public void onTouch(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

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
                landData.setWorldName(player.getWorld().getWorldFolder().getName());
                player.sendMessage(Component.text("StartPositionを設定しました (X: " + x + ", Z: " + z + ")"));
            } else {
                landData.setEndPosition(new Vector2(x, z));
                landData.setSelectLand(false);

                int size = landData.getLand().getSize();

                if (size <= 0) {
                    player.sendMessage(Component.text("保護する範囲は、2マス以上にしてください"));
                    landData.resetLandData();
                    return;
                } else if (!player.getWorld().getWorldFolder().getName().equals(landData.getWorldName())) {
                    player.sendMessage(Component.text("同じワールドで範囲を指定してください"));
                    landData.resetLandData();
                    return;
                }

                player.sendMessage(Component.text("EndPositionを設定しました (X: " + x + ", Z: " + z + ")"));
                player.sendMessage(Component.text("サイズ: " + size + "ブロック (値段: " + landData.getPrice()));
                player.sendMessage(Component.text("指定した範囲の土地を購入する際は、/land buyを実行してください"));
            }
        }
    }

    private void initLandData(Player player) {
        landDataManager.setLandData(player.getUniqueId());
    }
}
