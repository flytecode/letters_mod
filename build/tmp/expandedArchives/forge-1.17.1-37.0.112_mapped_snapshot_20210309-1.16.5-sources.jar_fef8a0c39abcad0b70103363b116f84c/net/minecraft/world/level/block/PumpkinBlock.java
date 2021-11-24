package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class PumpkinBlock extends StemGrownBlock {
   public PumpkinBlock(BlockBehaviour.Properties p_55284_) {
      super(p_55284_);
   }

   public InteractionResult m_6227_(BlockState p_55289_, Level p_55290_, BlockPos p_55291_, Player p_55292_, InteractionHand p_55293_, BlockHitResult p_55294_) {
      ItemStack itemstack = p_55292_.m_21120_(p_55293_);
      if (itemstack.canPerformAction(net.minecraftforge.common.ToolActions.SHEARS_CARVE)) {
         if (!p_55290_.f_46443_) {
            Direction direction = p_55294_.m_82434_();
            Direction direction1 = direction.m_122434_() == Direction.Axis.Y ? p_55292_.m_6350_().m_122424_() : direction;
            p_55290_.m_5594_((Player)null, p_55291_, SoundEvents.f_12296_, SoundSource.BLOCKS, 1.0F, 1.0F);
            p_55290_.m_7731_(p_55291_, Blocks.f_50143_.m_49966_().m_61124_(CarvedPumpkinBlock.f_51367_, direction1), 11);
            ItemEntity itementity = new ItemEntity(p_55290_, (double)p_55291_.m_123341_() + 0.5D + (double)direction1.m_122429_() * 0.65D, (double)p_55291_.m_123342_() + 0.1D, (double)p_55291_.m_123343_() + 0.5D + (double)direction1.m_122431_() * 0.65D, new ItemStack(Items.f_42577_, 4));
            itementity.m_20334_(0.05D * (double)direction1.m_122429_() + p_55290_.f_46441_.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.m_122431_() + p_55290_.f_46441_.nextDouble() * 0.02D);
            p_55290_.m_7967_(itementity);
            itemstack.m_41622_(1, p_55292_, (p_55287_) -> {
               p_55287_.m_21190_(p_55293_);
            });
            p_55290_.m_142346_(p_55292_, GameEvent.f_157781_, p_55291_);
            p_55292_.m_36246_(Stats.f_12982_.m_12902_(Items.f_42574_));
         }

         return InteractionResult.m_19078_(p_55290_.f_46443_);
      } else {
         return super.m_6227_(p_55289_, p_55290_, p_55291_, p_55292_, p_55293_, p_55294_);
      }
   }

   public StemBlock m_7161_() {
      return (StemBlock)Blocks.f_50189_;
   }

   public AttachedStemBlock m_7810_() {
      return (AttachedStemBlock)Blocks.f_50187_;
   }
}
