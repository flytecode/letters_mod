package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

public class BuddingAmethystBlock extends AmethystBlock {
   public static final int f_152722_ = 5;
   private static final Direction[] f_152723_ = Direction.values();

   public BuddingAmethystBlock(BlockBehaviour.Properties p_152726_) {
      super(p_152726_);
   }

   public PushReaction m_5537_(BlockState p_152733_) {
      return PushReaction.DESTROY;
   }

   public void m_7455_(BlockState p_152728_, ServerLevel p_152729_, BlockPos p_152730_, Random p_152731_) {
      if (p_152731_.nextInt(5) == 0) {
         Direction direction = f_152723_[p_152731_.nextInt(f_152723_.length)];
         BlockPos blockpos = p_152730_.m_142300_(direction);
         BlockState blockstate = p_152729_.m_8055_(blockpos);
         Block block = null;
         if (m_152734_(blockstate)) {
            block = Blocks.f_152495_;
         } else if (blockstate.m_60713_(Blocks.f_152495_) && blockstate.m_61143_(AmethystClusterBlock.f_152006_) == direction) {
            block = Blocks.f_152494_;
         } else if (blockstate.m_60713_(Blocks.f_152494_) && blockstate.m_61143_(AmethystClusterBlock.f_152006_) == direction) {
            block = Blocks.f_152493_;
         } else if (blockstate.m_60713_(Blocks.f_152493_) && blockstate.m_61143_(AmethystClusterBlock.f_152006_) == direction) {
            block = Blocks.f_152492_;
         }

         if (block != null) {
            BlockState blockstate1 = block.m_49966_().m_61124_(AmethystClusterBlock.f_152006_, direction).m_61124_(AmethystClusterBlock.f_152005_, Boolean.valueOf(blockstate.m_60819_().m_76152_() == Fluids.f_76193_));
            p_152729_.m_46597_(blockpos, blockstate1);
         }

      }
   }

   public static boolean m_152734_(BlockState p_152735_) {
      return p_152735_.m_60795_() || p_152735_.m_60713_(Blocks.f_49990_) && p_152735_.m_60819_().m_76186_() == 8;
   }
}