package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class FlyingMob extends Mob {
   protected FlyingMob(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
      super(p_20806_, p_20807_);
   }

   public boolean m_142535_(float p_147105_, float p_147106_, DamageSource p_147107_) {
      return false;
   }

   protected void m_7840_(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
   }

   public void m_7023_(Vec3 p_20818_) {
      if (this.m_20069_()) {
         this.m_19920_(0.02F, p_20818_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_((double)0.8F));
      } else if (this.m_20077_()) {
         this.m_19920_(0.02F, p_20818_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.5D));
      } else {
         BlockPos ground = new BlockPos(this.m_20185_(), this.m_20186_() - 1.0D, this.m_20189_());
         float f = 0.91F;
         if (this.f_19861_) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         float f1 = 0.16277137F / (f * f * f);
         f = 0.91F;
         if (this.f_19861_) {
            f = this.f_19853_.m_8055_(ground).getFriction(this.f_19853_, ground, this) * 0.91F;
         }

         this.m_19920_(this.f_19861_ ? 0.1F * f1 : 0.02F, p_20818_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_((double)f));
      }

      this.m_21043_(this, false);
   }

   public boolean m_6147_() {
      return false;
   }
}
