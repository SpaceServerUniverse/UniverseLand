package space.yurisi.universeland.utils;

public class BoundingBox {
    private double minX;
    private double minZ;

    private double maxX;
    private double maxZ;

    public BoundingBox(double x1, double z1, double x2, double z2){
        double tmp;

        if(x2 < x1){
            tmp = x2;
            x2 = x1;
            x1 = tmp;
        }

        if(z2 < z1){
            tmp = z2;
            z2 = z1;
            z1 = tmp;
        }

        this.minX = x1;
        this.minZ = z1;
        this.maxX = x2;
        this.maxZ = z2;
    }

    public boolean isOverlapping(BoundingBox other){
        return (minX <= other.maxX && maxX >= other.minX) && (minZ <= other.maxZ && maxZ >= other.minZ);
    }
}