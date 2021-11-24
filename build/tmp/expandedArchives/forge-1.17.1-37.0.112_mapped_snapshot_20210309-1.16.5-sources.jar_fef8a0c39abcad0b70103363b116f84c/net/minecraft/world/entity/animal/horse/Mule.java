package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class Mule extends AbstractChestedHorse {
   public Mule(EntityType<? extends Mule> p_30878_, Level p_30879_) {
      super(p_30878_, p_30879_);
   }

   protected SoundEvent m_7515_() {
      super.m_7515_();
      return SoundEvents.f_12076_;
   }

   protected SoundEvent m_7871_() {
      super.m_7871_();
      return SoundEvents.f_12077_;
   }

   protected SoundEvent m_5592_() {
      super.m_5592_();
      return SoundEvents.f_12079_;
   }

   @Nullable
   protected SoundEvent m_7872_() {
      return SoundEvents.f_12080_;
   }

   protected SoundEvent m_7975_(DamageSource p_30886_) {
      super.m_7975_(p_30886_);
      return SoundEvents.f_12081_;
   }

   protected void m_7609_() {
      this.m_5496_(SoundEvents.f_12078_, 1.0F, (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
   }

   public AgeableMob m_142606_(ServerLevel p_149549_, AgeableMob p_149550_) {
      return EntityType.f_20503_.m_20615_(p_149549_);
   }
}