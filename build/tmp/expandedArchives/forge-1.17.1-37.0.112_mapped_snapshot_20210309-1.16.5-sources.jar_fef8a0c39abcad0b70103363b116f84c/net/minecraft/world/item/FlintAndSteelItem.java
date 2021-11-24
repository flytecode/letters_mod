package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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

public class FlintAndSteelItem extends Item {
   public FlintAndSteelItem(Item.Properties p_41295_) {
      super(p_41295_);
   }

   public InteractionResult m_6225_(UseOnContext p_41297_) {
      Player player = p_41297_.m_43723_();
      Level level = p_41297_.m_43725_();
      BlockPos blockpos = p_41297_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (!CampfireBlock.m_51321_(blockstate) && !CandleBlock.m_152845_(blockstate) && !CandleCakeBlock.m_152910_(blockstate)) {
         BlockPos blockpos1 = blockpos.m_142300_(p_41297_.m_43719_());
         if (BaseFireBlock.m_49255_(level, blockpos1, p_41297_.m_8125_())) {
            level.m_5594_(player, blockpos1, SoundEvents.f_11942_, SoundSource.BLOCKS, 1.0F, level.m_5822_().nextFloat() * 0.4F + 0.8F);
            BlockState blockstate1 = BaseFireBlock.m_49245_(level, blockpos1);
            level.m_7731_(blockpos1, blockstate1, 11);
            level.m_142346_(player, GameEvent.f_157797_, blockpos);
            ItemStack itemstack = p_41297_.m_43722_();
            if (player instanceof ServerPlayer) {
               CriteriaTriggers.f_10591_.m_59469_((ServerPlayer)player, blockpos1, itemstack);
               itemstack.m_41622_(1, player, (p_41300_) -> {
                  p_41300_.m_21190_(p_41297_.m_43724_());
               });
            }

            return InteractionResult.m_19078_(level.m_5776_());
         } else {
            return InteractionResult.FAIL;
         }
      } else {
         level.m_5594_(player, blockpos, SoundEvents.f_11942_, SoundSource.BLOCKS, 1.0F, level.m_5822_().nextFloat() * 0.4F + 0.8F);
         level.m_7731_(blockpos, blockstate.m_61124_(BlockStateProperties.f_61443_, Boolean.valueOf(true)), 11);
         level.m_142346_(player, GameEvent.f_157797_, blockpos);
         if (player != null) {
            p_41297_.m_43722_().m_41622_(1, player, (p_41303_) -> {
               p_41303_.m_21190_(p_41297_.m_43724_());
            });
         }

         return InteractionResult.m_19078_(level.m_5776_());
      }
   }
}