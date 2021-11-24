package net.minecraft.world.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LargeFireball extends Fireball {
   private int f_37197_ = 1;

   public LargeFireball(EntityType<? extends LargeFireball> p_37199_, Level p_37200_) {
      super(p_37199_, p_37200_);
   }

   public LargeFireball(Level p_181151_, LivingEntity p_181152_, double p_181153_, double p_181154_, double p_181155_, int p_181156_) {
      super(EntityType.f_20463_, p_181152_, p_181153_, p_181154_, p_181155_, p_181151_);
      this.f_37197_ = p_181156_;
   }

   protected void m_6532_(HitResult p_37218_) {
      super.m_6532_(p_37218_);
      if (!this.f_19853_.f_46443_) {
         boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this.m_37282_());
         this.f_19853_.m_46518_((Entity)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)this.f_37197_, flag, flag ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE);
         this.m_146870_();
      }

   }

   protected void m_5790_(EntityHitResult p_37216_) {
      super.m_5790_(p_37216_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37216_.m_82443_();
         Entity entity1 = this.m_37282_();
         entity.m_6469_(DamageSource.m_19349_(this, entity1), 6.0F);
         if (entity1 instanceof LivingEntity) {
            this.m_19970_((LivingEntity)entity1, entity);
         }

      }
   }

   public void m_7380_(CompoundTag p_37222_) {
      super.m_7380_(p_37222_);
      p_37222_.m_128344_("ExplosionPower", (byte)this.f_37197_);
   }

   public void m_7378_(CompoundTag p_37220_) {
      super.m_7378_(p_37220_);
      if (p_37220_.m_128425_("ExplosionPower", 99)) {
         this.f_37197_ = p_37220_.m_128445_("ExplosionPower");
      }

   }
}
