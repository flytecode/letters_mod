package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class HangingEntityItem extends Item {
   private final EntityType<? extends HangingEntity> f_41322_;

   public HangingEntityItem(EntityType<? extends HangingEntity> p_41324_, Item.Properties p_41325_) {
      super(p_41325_);
      this.f_41322_ = p_41324_;
   }

   public InteractionResult m_6225_(UseOnContext p_41331_) {
      BlockPos blockpos = p_41331_.m_8083_();
      Direction direction = p_41331_.m_43719_();
      BlockPos blockpos1 = blockpos.m_142300_(direction);
      Player player = p_41331_.m_43723_();
      ItemStack itemstack = p_41331_.m_43722_();
      if (player != null && !this.m_5595_(player, direction, itemstack, blockpos1)) {
         return InteractionResult.FAIL;
      } else {
         Level level = p_41331_.m_43725_();
         HangingEntity hangingentity;
         if (this.f_41322_ == EntityType.f_20506_) {
            hangingentity = new Painting(level, blockpos1, direction);
         } else if (this.f_41322_ == EntityType.f_20462_) {
            hangingentity = new ItemFrame(level, blockpos1, direction);
         } else {
            if (this.f_41322_ != EntityType.f_147033_) {
               return InteractionResult.m_19078_(level.f_46443_);
            }

            hangingentity = new GlowItemFrame(level, blockpos1, direction);
         }

         CompoundTag compoundtag = itemstack.m_41783_();
         if (compoundtag != null) {
            EntityType.m_20620_(level, player, hangingentity, compoundtag);
         }

         if (hangingentity.m_7088_()) {
            if (!level.f_46443_) {
               hangingentity.m_7084_();
               level.m_142346_(player, GameEvent.f_157810_, blockpos);
               level.m_7967_(hangingentity);
            }

            itemstack.m_41774_(1);
            return InteractionResult.m_19078_(level.f_46443_);
         } else {
            return InteractionResult.CONSUME;
         }
      }
   }

   protected boolean m_5595_(Player p_41326_, Direction p_41327_, ItemStack p_41328_, BlockPos p_41329_) {
      return !p_41327_.m_122434_().m_122478_() && p_41326_.m_36204_(p_41329_, p_41327_, p_41328_);
   }
}