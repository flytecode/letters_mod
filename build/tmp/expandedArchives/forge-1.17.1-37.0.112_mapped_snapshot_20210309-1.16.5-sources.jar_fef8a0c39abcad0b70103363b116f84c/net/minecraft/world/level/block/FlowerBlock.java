package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowerBlock extends BushBlock {
   protected static final float f_153265_ = 3.0F;
   protected static final VoxelShape f_53507_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
   private final MobEffect f_53508_;
   private final int f_53509_;

   public FlowerBlock(MobEffect p_53512_, int p_53513_, BlockBehaviour.Properties p_53514_) {
      super(p_53514_);
      this.f_53508_ = p_53512_;
      if (p_53512_.m_8093_()) {
         this.f_53509_ = p_53513_;
      } else {
         this.f_53509_ = p_53513_ * 20;
      }

   }

   public VoxelShape m_5940_(BlockState p_53517_, BlockGetter p_53518_, BlockPos p_53519_, CollisionContext p_53520_) {
      Vec3 vec3 = p_53517_.m_60824_(p_53518_, p_53519_);
      return f_53507_.m_83216_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }

   public MobEffect m_53521_() {
      return this.f_53508_;
   }

   public int m_53522_() {
      return this.f_53509_;
   }
}