package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;

public class MinecartItem extends Item {
   private static final DispenseItemBehavior f_42934_ = new DefaultDispenseItemBehavior() {
      private final DefaultDispenseItemBehavior f_42944_ = new DefaultDispenseItemBehavior();

      public ItemStack m_7498_(BlockSource p_42949_, ItemStack p_42950_) {
         Direction direction = p_42949_.m_6414_().m_61143_(DispenserBlock.f_52659_);
         Level level = p_42949_.m_7727_();
         double d0 = p_42949_.m_7096_() + (double)direction.m_122429_() * 1.125D;
         double d1 = Math.floor(p_42949_.m_7098_()) + (double)direction.m_122430_();
         double d2 = p_42949_.m_7094_() + (double)direction.m_122431_() * 1.125D;
         BlockPos blockpos = p_42949_.m_7961_().m_142300_(direction);
         BlockState blockstate = level.m_8055_(blockpos);
         RailShape railshape = blockstate.m_60734_() instanceof BaseRailBlock ? ((BaseRailBlock)blockstate.m_60734_()).getRailDirection(blockstate, level, blockpos, null) : RailShape.NORTH_SOUTH;
         double d3;
         if (blockstate.m_60620_(BlockTags.f_13034_)) {
            if (railshape.m_61745_()) {
               d3 = 0.6D;
            } else {
               d3 = 0.1D;
            }
         } else {
            if (!blockstate.m_60795_() || !level.m_8055_(blockpos.m_7495_()).m_60620_(BlockTags.f_13034_)) {
               return this.f_42944_.m_6115_(p_42949_, p_42950_);
            }

            BlockState blockstate1 = level.m_8055_(blockpos.m_7495_());
            RailShape railshape1 = blockstate1.m_60734_() instanceof BaseRailBlock ? blockstate1.m_61143_(((BaseRailBlock)blockstate1.m_60734_()).m_7978_()) : RailShape.NORTH_SOUTH;
            if (direction != Direction.DOWN && railshape1.m_61745_()) {
               d3 = -0.4D;
            } else {
               d3 = -0.9D;
            }
         }

         AbstractMinecart abstractminecart = AbstractMinecart.m_38119_(level, d0, d1 + d3, d2, ((MinecartItem)p_42950_.m_41720_()).f_42935_);
         if (p_42950_.m_41788_()) {
            abstractminecart.m_6593_(p_42950_.m_41786_());
         }

         level.m_7967_(abstractminecart);
         p_42950_.m_41774_(1);
         return p_42950_;
      }

      protected void m_6823_(BlockSource p_42947_) {
         p_42947_.m_7727_().m_46796_(1000, p_42947_.m_7961_(), 0);
      }
   };
   final AbstractMinecart.Type f_42935_;

   public MinecartItem(AbstractMinecart.Type p_42938_, Item.Properties p_42939_) {
      super(p_42939_);
      this.f_42935_ = p_42938_;
      DispenserBlock.m_52672_(this, f_42934_);
   }

   public InteractionResult m_6225_(UseOnContext p_42943_) {
      Level level = p_42943_.m_43725_();
      BlockPos blockpos = p_42943_.m_8083_();
      BlockState blockstate = level.m_8055_(blockpos);
      if (!blockstate.m_60620_(BlockTags.f_13034_)) {
         return InteractionResult.FAIL;
      } else {
         ItemStack itemstack = p_42943_.m_43722_();
         if (!level.f_46443_) {
            RailShape railshape = blockstate.m_60734_() instanceof BaseRailBlock ? ((BaseRailBlock)blockstate.m_60734_()).getRailDirection(blockstate, level, blockpos, null) : RailShape.NORTH_SOUTH;
            double d0 = 0.0D;
            if (railshape.m_61745_()) {
               d0 = 0.5D;
            }

            AbstractMinecart abstractminecart = AbstractMinecart.m_38119_(level, (double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.0625D + d0, (double)blockpos.m_123343_() + 0.5D, this.f_42935_);
            if (itemstack.m_41788_()) {
               abstractminecart.m_6593_(itemstack.m_41786_());
            }

            level.m_7967_(abstractminecart);
            level.m_142346_(p_42943_.m_43723_(), GameEvent.f_157810_, blockpos);
         }

         itemstack.m_41774_(1);
         return InteractionResult.m_19078_(level.f_46443_);
      }
   }
}
