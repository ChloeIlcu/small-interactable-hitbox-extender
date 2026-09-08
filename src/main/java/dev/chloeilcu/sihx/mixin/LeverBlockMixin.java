package dev.chloeilcu.sihx.mixin;

import dev.chloeilcu.sihx.util.ShapeHelper;

import dev.chloeilcu.sihx.config.HitboxConfig;
import dev.chloeilcu.sihx.config.HitboxMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;	
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.block.LeverBlock", remap = false)
public abstract class LeverBlockMixin {
    @Inject(method = "getShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true, remap = false)
    private void buttonhitbox$changeLeverShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        HitboxMode mode = HitboxConfig.leverMode();
        if (mode == HitboxMode.VANILLA) return;
        if (mode == HitboxMode.FULL_BLOCK) {
            cir.setReturnValue(Shapes.block());
        } else {
            cir.setReturnValue(ShapeHelper.flatLever(state));
        }
    }
}
