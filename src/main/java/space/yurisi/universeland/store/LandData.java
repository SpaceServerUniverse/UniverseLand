package space.yurisi.universeland.store;

import space.yurisi.universeland.utils.BoundingBox;

import java.util.List;
import java.util.UUID;

public class LandData {

    private final UUID ownerUUID;
    private final BoundingBox boundingBox;
    private final List<UUID> allowedList;

    public LandData(UUID ownerUUID, BoundingBox boundingBox, List<UUID> allowedList) {
        this.ownerUUID = ownerUUID;
        this.boundingBox = boundingBox;
        this.allowedList = allowedList;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public List<UUID> getAllowedList() {
        return allowedList;
    }
}
