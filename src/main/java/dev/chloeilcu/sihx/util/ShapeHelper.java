package dev.chloeilcu.sihx.util;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ShapeHelper {
    private static final double DEPTH = 2.0 / 16.0;

    private ShapeHelper() {}

    public static VoxelShape flatFrom(VoxelShape original) {
        AABB bounds = original.bounds();
        double sizeX = bounds.maxX - bounds.minX;
        double sizeY = bounds.maxY - bounds.minY;
        double sizeZ = bounds.maxZ - bounds.minZ;

        if (sizeY <= sizeX && sizeY <= sizeZ) {
            return bounds.minY < 0.5
                    ? Shapes.box(0.0, 0.0, 0.0, 1.0, DEPTH, 1.0)
                    : Shapes.box(0.0, 1.0 - DEPTH, 0.0, 1.0, 1.0, 1.0);
        } else if (sizeX <= sizeY && sizeX <= sizeZ) {
            return bounds.minX < 0.5
                    ? Shapes.box(0.0, 0.0, 0.0, DEPTH, 1.0, 1.0)
                    : Shapes.box(1.0 - DEPTH, 0.0, 0.0, 1.0, 1.0, 1.0);
        } else {
            return bounds.minZ < 0.5
                    ? Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, DEPTH)
                    : Shapes.box(0.0, 0.0, 1.0 - DEPTH, 1.0, 1.0, 1.0);
        }
    }
}
