package space.yurisi.universeland.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecore.exception.LandNotFoundException;
import space.yurisi.universeland.manager.LandDataManager;

public class BreakEvent implements Listener {

    public void onBreak(BlockBreakEvent event) throws LandNotFoundException {
        Block block = event.getBlock();

        if(LandDataManager.getInstance().getOverlapLandData(block.getX(), block.getZ())){

        }
    }
}
