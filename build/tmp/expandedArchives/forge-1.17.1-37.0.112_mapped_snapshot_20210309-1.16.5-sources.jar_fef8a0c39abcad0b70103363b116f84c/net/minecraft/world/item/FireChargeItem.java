package net.minecraft.world.item;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FireChargeItem extends Item {
   public FireChargeItem(Item.Properties p_41202_) {
      super(p_41202_);
   }

   public InteractionResult m_6225_(UseOnContext p_41204_) {
      Level level = p_41204_.m_43725_();
      BlockPos blockpos = p_41204_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      boolean flag = false;
      if (!CampfireBlock.m_51321_(blockstate) && !CandleBlock.m_152845_(blockstate) && !CandleCakeBlock.m_152910_(blockstate)) {
         blockpos = blockpos.m_142300_(p_41204_.m_43719_());
         if (BaseFireBlock.m_49255_(level, blockpos, p_41204_.m_8125_())) {
            this.m_41205_(level, blockpos);
            level.m_46597_(blockpos, BaseFireBlock.m_49245_(level, blockpos));
            level.m_142346_(p_41204_.m_43723_(), GameEvent.f_157797_, blockpos);
            flag = true;
         }
      } else {
         this.m_41205_(level, blockpos);
         level.m_46597_(blockpos, blockstate.m_61124_(BlockStateProperties.f_61443_, Boolean.valueOf(true)));
         level.m_142346_(p_41204_.m_43723_(), GameEvent.f_157797_, blockpos);
         flag = true;
      }

      if (flag) {
         p_41204_.m_43722_().m_41774_(1);
         return InteractionResult.m_19078_(level.f_46443_);
      } else {
         return InteractionResult.FAIL;
      }
   }

   private void m_41205_(Level p_41206_, BlockPos p_41207_) {
      Random random = p_41206_.m_5822_();
      p_41206_.m_5594_((Player)null, p_41207_, SoundEvents.f_11874_, SoundSource.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
   }
}