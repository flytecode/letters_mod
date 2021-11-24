package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class EndCrystalItem extends Item {
   public EndCrystalItem(Item.Properties p_41174_) {
      super(p_41174_);
   }

   public InteractionResult m_6225_(UseOnContext p_41176_) {
      Level level = p_41176_.m_43725_();
      BlockPos blockpos = p_41176_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (!blockstate.m_60713_(Blocks.f_50080_) && !blockstate.m_60713_(Blocks.f_50752_)) {
         return InteractionResult.FAIL;
      } else {
         BlockPos blockpos1 = blockpos.m_7494_();
         if (!level.m_46859_(blockpos1)) {
            return InteractionResult.FAIL;
         } else {
            double d0 = (double)blockpos1.m_123341_();
            double d1 = (double)blockpos1.m_123342_();
            double d2 = (double)blockpos1.m_123343_();
            List<Entity> list = level.m_45933_((Entity)null, new AABB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
            if (!list.isEmpty()) {
               return InteractionResult.FAIL;
            } else {
               if (level instanceof ServerLevel) {
                  EndCrystal endcrystal = new EndCrystal(level, d0 + 0.5D, d1, d2 + 0.5D);
                  endcrystal.m_31056_(false);
                  level.m_7967_(endcrystal);
                  level.m_142346_(p_41176_.m_43723_(), GameEvent.f_157810_, blockpos1);
                  EndDragonFight enddragonfight = ((ServerLevel)level).m_8586_();
                  if (enddragonfight != null) {
                     enddragonfight.m_64100_();
                  }
               }

               p_41176_.m_43722_().m_41774_(1);
               return InteractionResult.m_19078_(level.f_46443_);
            }
         }
      }
   }

   public boolean m_5812_(ItemStack p_41178_) {
      return true;
   }
}