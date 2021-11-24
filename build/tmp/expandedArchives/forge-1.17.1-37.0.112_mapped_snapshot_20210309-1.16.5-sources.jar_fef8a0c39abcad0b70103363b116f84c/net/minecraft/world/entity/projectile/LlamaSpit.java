package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LlamaSpit extends Projectile {
   public LlamaSpit(EntityType<? extends LlamaSpit> p_37224_, Level p_37225_) {
      super(p_37224_, p_37225_);
   }

   public LlamaSpit(Level p_37235_, Llama p_37236_) {
      this(EntityType.f_20467_, p_37235_);
      this.m_5602_(p_37236_);
      this.m_6034_(p_37236_.m_20185_() - (double)(p_37236_.m_20205_() + 1.0F) * 0.5D * (double)Mth.m_14031_(p_37236_.f_20883_ * ((float)Math.PI / 180F)), p_37236_.m_20188_() - (double)0.1F, p_37236_.m_20189_() + (double)(p_37236_.m_20205_() + 1.0F) * 0.5D * (double)Mth.m_14089_(p_37236_.f_20883_ * ((float)Math.PI / 180F)));
   }

   public void m_8119_() {
      super.m_8119_();
      Vec3 vec3 = this.m_20184_();
      HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
      if (hitresult.m_6662_() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult))
      this.m_6532_(hitresult);
      double d0 = this.m_20185_() + vec3.f_82479_;
      double d1 = this.m_20186_() + vec3.f_82480_;
      double d2 = this.m_20189_() + vec3.f_82481_;
      this.m_37283_();
      float f = 0.99F;
      float f1 = 0.06F;
      if (this.f_19853_.m_45556_(this.m_142469_()).noneMatch(BlockBehaviour.BlockStateBase::m_60795_)) {
         this.m_146870_();
      } else if (this.m_20072_()) {
         this.m_146870_();
      } else {
         this.m_20256_(vec3.m_82490_((double)0.99F));
         if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, (double)-0.06F, 0.0D));
         }

         this.m_6034_(d0, d1, d2);
      }
   }

   protected void m_5790_(EntityHitResult p_37241_) {
      super.m_5790_(p_37241_);
      Entity entity = this.m_37282_();
      if (entity instanceof LivingEntity) {
         p_37241_.m_82443_().m_6469_(DamageSource.m_19340_(this, (LivingEntity)entity).m_19366_(), 1.0F);
      }

   }

   protected void m_8060_(BlockHitResult p_37239_) {
      super.m_8060_(p_37239_);
      if (!this.f_19853_.f_46443_) {
         this.m_146870_();
      }

   }

   protected void m_8097_() {
   }

   public void m_141965_(ClientboundAddEntityPacket p_150162_) {
      super.m_141965_(p_150162_);
      double d0 = p_150162_.m_131503_();
      double d1 = p_150162_.m_131504_();
      double d2 = p_150162_.m_131505_();

      for(int i = 0; i < 7; ++i) {
         double d3 = 0.4D + 0.1D * (double)i;
         this.f_19853_.m_7106_(ParticleTypes.f_123764_, this.m_20185_(), this.m_20186_(), this.m_20189_(), d0 * d3, d1, d2 * d3);
      }

      this.m_20334_(d0, d1, d2);
   }
}
