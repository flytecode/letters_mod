package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class AbstractGolem extends PathfinderMob {
   protected AbstractGolem(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
      super(p_27508_, p_27509_);
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return null;
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_27517_) {
      return null;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return null;
   }

   public int m_8100_() {
      return 120;
   }

   public boolean m_6785_(double p_27519_) {
      return false;
   }
}