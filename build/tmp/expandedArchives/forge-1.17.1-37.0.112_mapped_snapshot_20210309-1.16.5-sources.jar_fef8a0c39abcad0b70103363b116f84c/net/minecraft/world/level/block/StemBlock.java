package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StemBlock extends BushBlock implements BonemealableBlock {
   public static final int f_154724_ = 7;
   public static final IntegerProperty f_57013_ = BlockStateProperties.f_61409_;
   protected static final float f_154725_ = 1.0F;
   protected static final VoxelShape[] f_57014_ = new VoxelShape[]{Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 2.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 4.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 6.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 8.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 10.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 12.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D), Block.m_49796_(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D)};
   private final StemGrownBlock f_57015_;
   private final Supplier<Item> f_154726_;

   public StemBlock(StemGrownBlock p_154728_, Supplier<Item> p_154729_, BlockBehaviour.Properties p_154730_) {
      super(p_154730_);
      this.f_57015_ = p_154728_;
      this.f_154726_ = p_154729_;
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_57013_, Integer.valueOf(0)));
   }

   public VoxelShape m_5940_(BlockState p_57047_, BlockGetter p_57048_, BlockPos p_57049_, CollisionContext p_57050_) {
      return f_57014_[p_57047_.m_61143_(f_57013_)];
   }

   protected boolean m_6266_(BlockState p_57053_, BlockGetter p_57054_, BlockPos p_57055_) {
      return p_57053_.m_60713_(Blocks.f_50093_);
   }

   public void m_7455_(BlockState p_57042_, ServerLevel p_57043_, BlockPos p_57044_, Random p_57045_) {
      if (!p_57043_.isAreaLoaded(p_57044_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
      if (p_57043_.m_45524_(p_57044_, 0) >= 9) {
         float f = CropBlock.m_52272_(this, p_57043_, p_57044_);
         if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57043_, p_57044_, p_57042_, p_57045_.nextInt((int)(25.0F / f) + 1) == 0)) {
            int i = p_57042_.m_61143_(f_57013_);
            if (i < 7) {
               p_57043_.m_7731_(p_57044_, p_57042_.m_61124_(f_57013_, Integer.valueOf(i + 1)), 2);
            } else {
               Direction direction = Direction.Plane.HORIZONTAL.m_122560_(p_57045_);
               BlockPos blockpos = p_57044_.m_142300_(direction);
               BlockState blockstate = p_57043_.m_8055_(blockpos.m_7495_());
               Block block = blockstate.m_60734_();
               if (p_57043_.m_46859_(blockpos) && (blockstate.canSustainPlant(p_57043_, blockpos.m_7495_(), Direction.UP, this) || block == Blocks.f_50093_ || block == Blocks.f_50493_ || block == Blocks.f_50546_ || block == Blocks.f_50599_ || block == Blocks.f_50440_)) {
                  p_57043_.m_46597_(blockpos, this.f_57015_.m_49966_());
                  p_57043_.m_46597_(p_57044_, this.f_57015_.m_7810_().m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, direction));
               }
            }
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_57043_, p_57044_, p_57042_);
         }

      }
   }

   public ItemStack m_7397_(BlockGetter p_57026_, BlockPos p_57027_, BlockState p_57028_) {
      return new ItemStack(this.f_154726_.get());
   }

   public boolean m_7370_(BlockGetter p_57030_, BlockPos p_57031_, BlockState p_57032_, boolean p_57033_) {
      return p_57032_.m_61143_(f_57013_) != 7;
   }

   public boolean m_5491_(Level p_57035_, Random p_57036_, BlockPos p_57037_, BlockState p_57038_) {
      return true;
   }

   public void m_7719_(ServerLevel p_57021_, Random p_57022_, BlockPos p_57023_, BlockState p_57024_) {
      int i = Math.min(7, p_57024_.m_61143_(f_57013_) + Mth.m_14072_(p_57021_.f_46441_, 2, 5));
      BlockState blockstate = p_57024_.m_61124_(f_57013_, Integer.valueOf(i));
      p_57021_.m_7731_(p_57023_, blockstate, 2);
      if (i == 7) {
         blockstate.m_60735_(p_57021_, p_57023_, p_57021_.f_46441_);
      }

   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57040_) {
      p_57040_.m_61104_(f_57013_);
   }

   public StemGrownBlock m_57056_() {
      return this.f_57015_;
   }

   //FORGE START
   @Override
   public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return net.minecraftforge.common.PlantType.CROP;
   }
}
