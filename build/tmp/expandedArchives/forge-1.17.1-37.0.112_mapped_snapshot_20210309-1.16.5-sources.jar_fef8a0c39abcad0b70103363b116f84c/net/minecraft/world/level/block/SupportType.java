package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum SupportType {
   FULL {
      public boolean m_5588_(BlockState p_57220_, BlockGetter p_57221_, BlockPos p_57222_, Direction p_57223_) {
         return Block.m_49918_(p_57220_.m_60816_(p_57221_, p_57222_), p_57223_);
      }
   },
   CENTER {
      private final int f_57224_ = 1;
      private final VoxelShape f_57225_ = Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 10.0D, 9.0D);

      public boolean m_5588_(BlockState p_57230_, BlockGetter p_57231_, BlockPos p_57232_, Direction p_57233_) {
         return !Shapes.m_83157_(p_57230_.m_60816_(p_57231_, p_57232_).m_83263_(p_57233_), this.f_57225_, BooleanOp.f_82683_);
      }
   },
   RIGID {
      private final int f_57234_ = 2;
      private final VoxelShape f_57235_ = Shapes.m_83113_(Shapes.m_83144_(), Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.f_82685_);

      public boolean m_5588_(BlockState p_57240_, BlockGetter p_57241_, BlockPos p_57242_, Direction p_57243_) {
         return !Shapes.m_83157_(p_57240_.m_60816_(p_57241_, p_57242_).m_83263_(p_57243_), this.f_57235_, BooleanOp.f_82683_);
      }
   };

   public abstract boolean m_5588_(BlockState p_57209_, BlockGetter p_57210_, BlockPos p_57211_, Direction p_57212_);
}