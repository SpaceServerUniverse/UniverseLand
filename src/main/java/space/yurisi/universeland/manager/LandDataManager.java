package space.yurisi.universeland.manager;

import space.yurisi.universeland.store.LandData;

import java.util.LinkedHashMap;
import java.util.UUID;

public class LandDataManager {

    private static LandDataManager instance;

    private final LinkedHashMap<UUID, LandData> data = new LinkedHashMap<>();

    public LandDataManager() {
        instance = this;
    }

    public static LandDataManager getInstance() {
        return instance;
    }

    public LandData getLandData(UUID uuid) {
        return data.get(uuid);
    }

    public void setLandData(UUID uuid) {
        data.put(uuid, new LandData());
    }
}
