package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherWartBlock extends BushBlock {
   public static final int f_153989_ = 3;
   public static final IntegerProperty f_54967_ = BlockStateProperties.f_61407_;
   private static final VoxelShape[] f_54968_ = new VoxelShape[]{Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)};

   public NetherWartBlock(BlockBehaviour.Properties p_54971_) {
      super(p_54971_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54967_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_54986_, BlockGetter p_54987_, BlockPos p_54988_, CollisionContext p_54989_) {
      return f_54968_[p_54986_.m_61143_(f_54967_)];
   }

   protected boolean m_6266_(BlockState p_54991_, BlockGetter p_54992_, BlockPos p_54993_) {
      return p_54991_.m_60713_(Blocks.f_50135_);
   }

   public boolean m_6724_(BlockState p_54979_) {
      return p_54979_.m_61143_(f_54967_) < 3;
   }

   public void m_7455_(BlockState p_54981_, ServerLevel p_54982_, BlockPos p_54983_, Random p_54984_) {
      int i = p_54981_.m_61143_(f_54967_);
      if (i < 3 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_54982_, p_54983_, p_54981_, p_54984_.nextInt(10) == 0)) {
         p_54981_ = p_54981_.m_61124_(f_54967_, Integer.valueOf(i + 1));
         p_54982_.m_7731_(p_54983_, p_54981_, 2);
         net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_54982_, p_54983_, p_54981_);
      }

   }

   public ItemStack m_7397_(BlockGetter p_54973_, BlockPos p_54974_, BlockState p_54975_) {
      return new ItemStack(Items.f_42588_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54977_) {
      p_54977_.m_61104_(f_54967_);
   }
}
