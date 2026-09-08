package dev.chloeilcu.sihx.util;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ShapeHelper {
    private static final double DEPTH = 2.0 / 16.0;

    private ShapeHelper() {}

    /*
     * Used by buttons.
     *
     * Buttons have simple enough vanilla shapes that we can determine
     * their attached surface from the shape bounds.
     */
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

    /*
     * Used by levers.
     *
     * Lever shapes are asymmetric because of the handle, so instead of
     * guessing the attached surface from the vanilla shape, use the
     * lever's actual FACE and FACING block-state properties.
     */
    public static VoxelShape flatLever(BlockState state) {
        AttachFace face = state.getValue(LeverBlock.FACE);

        if (face == AttachFace.FLOOR) {
            return Shapes.box(
                    0.0, 0.0, 0.0,
                    1.0, DEPTH, 1.0
            );
        }

        if (face == AttachFace.CEILING) {
            return Shapes.box(
                    0.0, 1.0 - DEPTH, 0.0,
                    1.0, 1.0, 1.0
            );
        }

        Direction facing = state.getValue(LeverBlock.FACING);

        return switch (facing) {
            case NORTH -> Shapes.box(
                    0.0, 0.0, 1.0 - DEPTH,
                    1.0, 1.0, 1.0
            );

            case SOUTH -> Shapes.box(
                    0.0, 0.0, 0.0,
                    1.0, 1.0, DEPTH
            );

            case WEST -> Shapes.box(
                    1.0 - DEPTH, 0.0, 0.0,
                    1.0, 1.0, 1.0
            );

            case EAST -> Shapes.box(
                    0.0, 0.0, 0.0,
                    DEPTH, 1.0, 1.0
            );

            default -> Shapes.block();
        };
    }
}						