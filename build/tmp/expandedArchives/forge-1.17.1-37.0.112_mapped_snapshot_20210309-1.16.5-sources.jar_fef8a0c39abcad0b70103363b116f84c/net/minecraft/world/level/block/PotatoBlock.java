package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PotatoBlock extends CropBlock {
   private static final VoxelShape[] f_55195_ = new VoxelShape[]{Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)};

   public PotatoBlock(BlockBehaviour.Properties p_55198_) {
      super(p_55198_);
   }

   protected ItemLike m_6404_() {
      return Items.f_42620_;
   }

   public VoxelShape m_5940_(BlockState p_55200_, BlockGetter p_55201_, BlockPos p_55202_, CollisionContext p_55203_) {
      return f_55195_[p_55200_.m_61143_(this.m_7959_())];
   }
}