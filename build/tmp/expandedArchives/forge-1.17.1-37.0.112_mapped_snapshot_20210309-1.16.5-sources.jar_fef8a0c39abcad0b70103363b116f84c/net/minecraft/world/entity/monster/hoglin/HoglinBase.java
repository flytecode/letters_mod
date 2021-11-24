package net.minecraft.world.entity.monster.hoglin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public interface HoglinBase {
   int f_149916_ = 10;

   int m_7575_();

   static boolean m_34642_(LivingEntity p_34643_, LivingEntity p_34644_) {
      float f1 = (float)p_34643_.m_21133_(Attributes.f_22281_);
      float f;
      if (!p_34643_.m_6162_() && (int)f1 > 0) {
         f = f1 / 2.0F + (float)p_34643_.f_19853_.f_46441_.nextInt((int)f1);
      } else {
         f = f1;
      }

      boolean flag = p_34644_.m_6469_(DamageSource.m_19370_(p_34643_), f);
      if (flag) {
         p_34643_.m_19970_(p_34643_, p_34644_);
         if (!p_34643_.m_6162_()) {
            m_34645_(p_34643_, p_34644_);
         }
      }

      return flag;
   }

   static void m_34645_(LivingEntity p_34646_, LivingEntity p_34647_) {
      double d0 = p_34646_.m_21133_(Attributes.f_22282_);
      double d1 = p_34647_.m_21133_(Attributes.f_22278_);
      double d2 = d0 - d1;
      if (!(d2 <= 0.0D)) {
         double d3 = p_34647_.m_20185_() - p_34646_.m_20185_();
         double d4 = p_34647_.m_20189_() - p_34646_.m_20189_();
         float f = (float)(p_34646_.f_19853_.f_46441_.nextInt(21) - 10);
         double d5 = d2 * (double)(p_34646_.f_19853_.f_46441_.nextFloat() * 0.5F + 0.2F);
         Vec3 vec3 = (new Vec3(d3, 0.0D, d4)).m_82541_().m_82490_(d5).m_82524_(f);
         double d6 = d2 * (double)p_34646_.f_19853_.f_46441_.nextFloat() * 0.5D;
         p_34647_.m_5997_(vec3.f_82479_, d6, vec3.f_82481_);
         p_34647_.f_19864_ = true;
      }
   }
}