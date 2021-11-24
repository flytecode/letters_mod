package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

public abstract class PathfinderMob extends Mob {
   protected PathfinderMob(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
      super(p_21683_, p_21684_);
   }

   public float m_21692_(BlockPos p_21693_) {
      return this.m_5610_(p_21693_, this.f_19853_);
   }

   public float m_5610_(BlockPos p_21688_, LevelReader p_21689_) {
      return 0.0F;
   }

   public boolean m_5545_(LevelAccessor p_21686_, MobSpawnType p_21687_) {
      return this.m_5610_(this.m_142538_(), p_21686_) >= 0.0F;
   }

   public boolean m_21691_() {
      return !this.m_21573_().m_26571_();
   }

   protected void m_6119_() {
      super.m_6119_();
      Entity entity = this.m_21524_();
      if (entity != null && entity.f_19853_ == this.f_19853_) {
         this.m_21446_(entity.m_142538_(), 5);
         float f = this.m_20270_(entity);
         if (this instanceof TamableAnimal && ((TamableAnimal)this).m_21825_()) {
            if (f > 10.0F) {
               this.m_21455_(true, true);
            }

            return;
         }

         this.m_7880_(f);
         if (f > 10.0F) {
            this.m_21455_(true, true);
            this.f_21345_.m_25355_(Goal.Flag.MOVE);
         } else if (f > 6.0F) {
            double d0 = (entity.m_20185_() - this.m_20185_()) / (double)f;
            double d1 = (entity.m_20186_() - this.m_20186_()) / (double)f;
            double d2 = (entity.m_20189_() - this.m_20189_()) / (double)f;
            this.m_20256_(this.m_20184_().m_82520_(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
         } else {
            this.f_21345_.m_25374_(Goal.Flag.MOVE);
            float f1 = 2.0F;
            Vec3 vec3 = (new Vec3(entity.m_20185_() - this.m_20185_(), entity.m_20186_() - this.m_20186_(), entity.m_20189_() - this.m_20189_())).m_82541_().m_82490_((double)Math.max(f - 2.0F, 0.0F));
            this.m_21573_().m_26519_(this.m_20185_() + vec3.f_82479_, this.m_20186_() + vec3.f_82480_, this.m_20189_() + vec3.f_82481_, this.m_5823_());
         }
      }

   }

   protected double m_5823_() {
      return 1.0D;
   }

   protected void m_7880_(float p_21694_) {
   }
}