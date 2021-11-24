package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEgg extends ThrowableItemProjectile {
   public ThrownEgg(EntityType<? extends ThrownEgg> p_37473_, Level p_37474_) {
      super(p_37473_, p_37474_);
   }

   public ThrownEgg(Level p_37481_, LivingEntity p_37482_) {
      super(EntityType.f_20483_, p_37482_, p_37481_);
   }

   public ThrownEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
      super(EntityType.f_20483_, p_37477_, p_37478_, p_37479_, p_37476_);
   }

   public void m_7822_(byte p_37484_) {
      if (p_37484_ == 3) {
         double d0 = 0.08D;

         for(int i = 0; i < 8; ++i) {
            this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, this.m_7846_()), this.m_20185_(), this.m_20186_(), this.m_20189_(), ((double)this.f_19796_.nextFloat() - 0.5D) * 0.08D, ((double)this.f_19796_.nextFloat() - 0.5D) * 0.08D, ((double)this.f_19796_.nextFloat() - 0.5D) * 0.08D);
         }
      }

   }

   protected void m_5790_(EntityHitResult p_37486_) {
      super.m_5790_(p_37486_);
      p_37486_.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0F);
   }

   protected void m_6532_(HitResult p_37488_) {
      super.m_6532_(p_37488_);
      if (!this.f_19853_.f_46443_) {
         if (this.f_19796_.nextInt(8) == 0) {
            int i = 1;
            if (this.f_19796_.nextInt(32) == 0) {
               i = 4;
            }

            for(int j = 0; j < i; ++j) {
               Chicken chicken = EntityType.f_20555_.m_20615_(this.f_19853_);
               chicken.m_146762_(-24000);
               chicken.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
               this.f_19853_.m_7967_(chicken);
            }
         }

         this.f_19853_.m_7605_(this, (byte)3);
         this.m_146870_();
      }

   }

   protected Item m_7881_() {
      return Items.f_42521_;
   }
}