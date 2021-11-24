package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CarrotBlock extends CropBlock {
   private static final VoxelShape[] f_51325_ = new VoxelShape[]{Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)};

   public CarrotBlock(BlockBehaviour.Properties p_51328_) {
      super(p_51328_);
   }

   protected ItemLike m_6404_() {
      return Items.f_42619_;
   }

   public VoxelShape m_5940_(BlockState p_51330_, BlockGetter p_51331_, BlockPos p_51332_, CollisionContext p_51333_) {
      return f_51325_[p_51330_.m_61143_(this.m_7959_())];
   }
}