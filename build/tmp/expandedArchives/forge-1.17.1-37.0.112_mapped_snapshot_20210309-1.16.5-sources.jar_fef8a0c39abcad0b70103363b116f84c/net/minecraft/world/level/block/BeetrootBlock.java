package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BeetrootBlock extends CropBlock {
   public static final int f_152186_ = 3;
   public static final IntegerProperty f_49657_ = BlockStateProperties.f_61407_;
   private static final VoxelShape[] f_49658_ = new VoxelShape[]{Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)};

   public BeetrootBlock(BlockBehaviour.Properties p_49661_) {
      super(p_49661_);
   }

   public IntegerProperty m_7959_() {
      return f_49657_;
   }

   public int m_7419_() {
      return 3;
   }

   protected ItemLike m_6404_() {
      return Items.f_42733_;
   }

   public void m_7455_(BlockState p_49667_, ServerLevel p_49668_, BlockPos p_49669_, Random p_49670_) {
      if (p_49670_.nextInt(3) != 0) {
         super.m_7455_(p_49667_, p_49668_, p_49669_, p_49670_);
      }

   }

   protected int m_7125_(Level p_49663_) {
      return super.m_7125_(p_49663_) / 3;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49665_) {
      p_49665_.m_61104_(f_49657_);
   }

   public VoxelShape m_5940_(BlockState p_49672_, BlockGetter p_49673_, BlockPos p_49674_, CollisionContext p_49675_) {
      return f_49658_[p_49672_.m_61143_(this.m_7959_())];
   }
}