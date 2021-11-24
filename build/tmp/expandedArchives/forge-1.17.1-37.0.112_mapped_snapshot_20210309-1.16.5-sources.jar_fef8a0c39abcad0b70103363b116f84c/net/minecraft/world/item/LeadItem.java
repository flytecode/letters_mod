package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LeadItem extends Item {
   public LeadItem(Item.Properties p_42828_) {
      super(p_42828_);
   }

   public InteractionResult m_6225_(UseOnContext p_42834_) {
      Level level = p_42834_.m_43725_();
      BlockPos blockpos = p_42834_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (blockstate.m_60620_(BlockTags.f_13039_)) {
         Player player = p_42834_.m_43723_();
         if (!level.f_46443_ && player != null) {
            m_42829_(player, level, blockpos);
         }

         return InteractionResult.m_19078_(level.f_46443_);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static InteractionResult m_42829_(Player p_42830_, Level p_42831_, BlockPos p_42832_) {
      LeashFenceKnotEntity leashfenceknotentity = null;
      boolean flag = false;
      double d0 = 7.0D;
      int i = p_42832_.m_123341_();
      int j = p_42832_.m_123342_();
      int k = p_42832_.m_123343_();

      for(Mob mob : p_42831_.m_45976_(Mob.class, new AABB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
         if (mob.m_21524_() == p_42830_) {
            if (leashfenceknotentity == null) {
               leashfenceknotentity = LeashFenceKnotEntity.m_31844_(p_42831_, p_42832_);
               leashfenceknotentity.m_7084_();
            }

            mob.m_21463_(leashfenceknotentity, true);
            flag = true;
         }
      }

      return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
   }
}