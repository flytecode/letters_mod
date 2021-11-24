package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombieHorse extends AbstractHorse {
   public ZombieHorse(EntityType<? extends ZombieHorse> p_30994_, Level p_30995_) {
      super(p_30994_, p_30995_);
   }

   public static AttributeSupplier.Builder m_31008_() {
      return m_30627_().m_22268_(Attributes.f_22276_, 15.0D).m_22268_(Attributes.f_22279_, (double)0.2F);
   }

   protected void m_7505_() {
      this.m_21051_(Attributes.f_22288_).m_22100_(this.m_30630_());
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected SoundEvent m_7515_() {
      super.m_7515_();
      return SoundEvents.f_12605_;
   }

   protected SoundEvent m_5592_() {
      super.m_5592_();
      return SoundEvents.f_12606_;
   }

   protected SoundEvent m_7975_(DamageSource p_31006_) {
      super.m_7975_(p_31006_);
      return SoundEvents.f_12607_;
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel p_149561_, AgeableMob p_149562_) {
      return EntityType.f_20502_.m_20615_(p_149561_);
   }

   public InteractionResult m_6071_(Player p_31001_, InteractionHand p_31002_) {
      ItemStack itemstack = p_31001_.m_21120_(p_31002_);
      if (!this.m_30614_()) {
         return InteractionResult.PASS;
      } else if (this.m_6162_()) {
         return super.m_6071_(p_31001_, p_31002_);
      } else if (p_31001_.m_36341_()) {
         this.m_30620_(p_31001_);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else if (this.m_20160_()) {
         return super.m_6071_(p_31001_, p_31002_);
      } else {
         if (!itemstack.m_41619_()) {
            if (itemstack.m_150930_(Items.f_42450_) && !this.m_6254_()) {
               this.m_30620_(p_31001_);
               return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }

            InteractionResult interactionresult = itemstack.m_41647_(p_31001_, this, p_31002_);
            if (interactionresult.m_19077_()) {
               return interactionresult;
            }
         }

         this.m_6835_(p_31001_);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      }
   }

   protected void m_7509_() {
   }
}