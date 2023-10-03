package space.yurisi.universeland.utils;

public class BoundingBox {
    private int minX;
    private int minZ;

    private int maxX;
    private int maxZ;

    public BoundingBox(int x1, int z1, int x2, int z2) {
        this.minX = x1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxZ = z2;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public boolean isOverlapping(BoundingBox other) {
        return (minX <= other.maxX && maxX >= other.minX) && (minZ <= other.maxZ && maxZ >= other.minZ);
    }

    public int getSize() {
        return maxX - minX + maxZ - minZ;
    }
}