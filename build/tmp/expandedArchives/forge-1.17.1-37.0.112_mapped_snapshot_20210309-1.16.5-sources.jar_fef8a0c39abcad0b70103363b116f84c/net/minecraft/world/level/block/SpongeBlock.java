package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.Queue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

public class SpongeBlock extends Block {
   public static final int f_154689_ = 6;
   public static final int f_154690_ = 64;

   public SpongeBlock(BlockBehaviour.Properties p_56796_) {
      super(p_56796_);
   }

   public void m_6807_(BlockState p_56811_, Level p_56812_, BlockPos p_56813_, BlockState p_56814_, boolean p_56815_) {
      if (!p_56814_.m_60713_(p_56811_.m_60734_())) {
         this.m_56797_(p_56812_, p_56813_);
      }
   }

   public void m_6861_(BlockState p_56801_, Level p_56802_, BlockPos p_56803_, Block p_56804_, BlockPos p_56805_, boolean p_56806_) {
      this.m_56797_(p_56802_, p_56803_);
      super.m_6861_(p_56801_, p_56802_, p_56803_, p_56804_, p_56805_, p_56806_);
   }

   protected void m_56797_(Level p_56798_, BlockPos p_56799_) {
      if (this.m_56807_(p_56798_, p_56799_)) {
         p_56798_.m_7731_(p_56799_, Blocks.f_50057_.m_49966_(), 2);
         p_56798_.m_46796_(2001, p_56799_, Block.m_49956_(Blocks.f_49990_.m_49966_()));
      }

   }

   private boolean m_56807_(Level p_56808_, BlockPos p_56809_) {
      Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
      queue.add(new Tuple<>(p_56809_, 0));
      int i = 0;

      while(!queue.isEmpty()) {
         Tuple<BlockPos, Integer> tuple = queue.poll();
         BlockPos blockpos = tuple.m_14418_();
         int j = tuple.m_14419_();

         for(Direction direction : Direction.values()) {
            BlockPos blockpos1 = blockpos.m_142300_(direction);
            BlockState blockstate = p_56808_.m_8055_(blockpos1);
            FluidState fluidstate = p_56808_.m_6425_(blockpos1);
            Material material = blockstate.m_60767_();
            if (fluidstate.m_76153_(FluidTags.f_13131_)) {
               if (blockstate.m_60734_() instanceof BucketPickup && !((BucketPickup)blockstate.m_60734_()).m_142598_(p_56808_, blockpos1, blockstate).m_41619_()) {
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockpos1, j + 1));
                  }
               } else if (blockstate.m_60734_() instanceof LiquidBlock) {
                  p_56808_.m_7731_(blockpos1, Blocks.f_50016_.m_49966_(), 3);
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockpos1, j + 1));
                  }
               } else if (material == Material.f_76301_ || material == Material.f_76304_) {
                  BlockEntity blockentity = blockstate.m_155947_() ? p_56808_.m_7702_(blockpos1) : null;
                  m_49892_(blockstate, p_56808_, blockpos1, blockentity);
                  p_56808_.m_7731_(blockpos1, Blocks.f_50016_.m_49966_(), 3);
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockpos1, j + 1));
                  }
               }
            }
         }

         if (i > 64) {
            break;
         }
      }

      return i > 0;
   }
}