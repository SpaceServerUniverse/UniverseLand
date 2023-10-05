package space.yurisi.universeland.manager;

import space.yurisi.universecore.database.models.Land;
import space.yurisi.universecore.exception.LandNotFoundException;
import space.yurisi.universeland.UniverseLand;
import space.yurisi.universeland.store.LandData;
import space.yurisi.universeland.store.LandStore;
import space.yurisi.universeland.utils.BoundingBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class LandDataManager {

    private static LandDataManager instance;

    private final LinkedHashMap<UUID, LandStore> data = new LinkedHashMap<>();

    public LandDataManager() {
        instance = this;
    }

    public static LandDataManager getInstance() {
        return instance;
    }

    public LandStore getLandData(UUID uuid) {
        return data.get(uuid);
    }

    public void setLandData(UUID uuid) {
        data.put(uuid, new LandStore());
    }

    public List<LandData> getLandsData() throws LandNotFoundException {
        List<LandData> data = new ArrayList<>();
        List<Land> lands = UniverseLand.getInstance().getDatabaseManager().getLandRepository().getLands();
        for(Land land : lands){
            data.add(new LandData(UUID.fromString(land.getUUID()), new BoundingBox(land.getStart_x(), land.getStart_z(), land.getEnd_x(), land.getEnd_z(), land.getWorld_name()), new ArrayList<>()));
        }
        return data;
    }

    public LandData getOverlapLandData(int x, int z) throws LandNotFoundException {
        for(LandData land : getLandsData()){
            BoundingBox bb = land.getBoundingBox();
            if(x <= bb.getMinX() && x >= bb.getMaxX() && bb.getMinZ() <= z && z >= bb.getMaxZ()) return land;
        }

        return null;
    }

    public LandData getOverlapLandData(BoundingBox other) throws LandNotFoundException {
        for(LandData land : getLandsData()){
            BoundingBox bb = land.getBoundingBox();
            if(bb.isOverlapping(other)) return land;
        }

        return null;
    }
}
