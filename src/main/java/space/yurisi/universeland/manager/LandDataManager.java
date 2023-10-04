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
            //FIXME: UUIDが取得できない！
        }
        return data;
    }

    public LandData getOverlapLandData(int x, int z) throws LandNotFoundException {
        for(LandData land : getLandsData()){
            //BoundingBox bb =
            //if(land.getMinX() <= x && x >= land.getMaxX() && land.getMinZ() <= z && z >= land.getMaxZ()) return land;
        }

        return null;
    }
}
