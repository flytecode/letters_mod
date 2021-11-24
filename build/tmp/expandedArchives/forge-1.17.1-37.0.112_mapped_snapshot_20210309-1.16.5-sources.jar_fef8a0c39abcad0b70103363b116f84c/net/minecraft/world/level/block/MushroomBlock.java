package net.minecraft.world.level.block;

import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MushroomBlock extends BushBlock implements BonemealableBlock {
   protected static final float f_153980_ = 3.0F;
   protected static final VoxelShape f_54855_ = Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
   private final Supplier<ConfiguredFeature<?, ?>> f_153981_;

   public MushroomBlock(BlockBehaviour.Properties p_153983_, Supplier<ConfiguredFeature<?, ?>> p_153984_) {
      super(p_153983_);
      this.f_153981_ = p_153984_;
   }

   public VoxelShape m_5940_(BlockState p_54889_, BlockGetter p_54890_, BlockPos p_54891_, CollisionContext p_54892_) {
      return f_54855_;
   }

   public void m_7455_(BlockState p_54884_, ServerLevel p_54885_, BlockPos p_54886_, Random p_54887_) {
      if (p_54887_.nextInt(25) == 0) {
         int i = 5;
         int j = 4;

         for(BlockPos blockpos : BlockPos.m_121940_(p_54886_.m_142082_(-4, -1, -4), p_54886_.m_142082_(4, 1, 4))) {
            if (p_54885_.m_8055_(blockpos).m_60713_(this)) {
               --i;
               if (i <= 0) {
                  return;
               }
            }
         }

         BlockPos blockpos1 = p_54886_.m_142082_(p_54887_.nextInt(3) - 1, p_54887_.nextInt(2) - p_54887_.nextInt(2), p_54887_.nextInt(3) - 1);

         for(int k = 0; k < 4; ++k) {
            if (p_54885_.m_46859_(blockpos1) && p_54884_.m_60710_(p_54885_, blockpos1)) {
               p_54886_ = blockpos1;
            }

            blockpos1 = p_54886_.m_142082_(p_54887_.nextInt(3) - 1, p_54887_.nextInt(2) - p_54887_.nextInt(2), p_54887_.nextInt(3) - 1);
         }

         if (p_54885_.m_46859_(blockpos1) && p_54884_.m_60710_(p_54885_, blockpos1)) {
            p_54885_.m_7731_(blockpos1, p_54884_, 2);
         }
      }

   }

   protected boolean m_6266_(BlockState p_54894_, BlockGetter p_54895_, BlockPos p_54896_) {
      return p_54894_.m_60804_(p_54895_, p_54896_);
   }

   public boolean m_7898_(BlockState p_54880_, LevelReader p_54881_, BlockPos p_54882_) {
      BlockPos blockpos = p_54882_.m_7495_();
      BlockState blockstate = p_54881_.m_8055_(blockpos);
      if (blockstate.m_60620_(BlockTags.f_13057_)) {
         return true;
      } else {
         return p_54881_.m_45524_(p_54882_, 0) < 13 && blockstate.canSustainPlant(p_54881_, blockpos, net.minecraft.core.Direction.UP, this);
      }
   }

   public boolean m_54859_(ServerLevel p_54860_, BlockPos p_54861_, BlockState p_54862_, Random p_54863_) {
      p_54860_.m_7471_(p_54861_, false);
      if (this.f_153981_.get().m_65385_(p_54860_, p_54860_.m_7726_().m_8481_(), p_54863_, p_54861_)) {
         return true;
      } else {
         p_54860_.m_7731_(p_54861_, p_54862_, 3);
         return false;
      }
   }

   public boolean m_7370_(BlockGetter p_54870_, BlockPos p_54871_, BlockState p_54872_, boolean p_54873_) {
      return true;
   }

   public boolean m_5491_(Level p_54875_, Random p_54876_, BlockPos p_54877_, BlockState p_54878_) {
      return (double)p_54876_.nextFloat() < 0.4D;
   }

   public void m_7719_(ServerLevel p_54865_, Random p_54866_, BlockPos p_54867_, BlockState p_54868_) {
      this.m_54859_(p_54865_, p_54867_, p_54868_, p_54866_);
   }
}
