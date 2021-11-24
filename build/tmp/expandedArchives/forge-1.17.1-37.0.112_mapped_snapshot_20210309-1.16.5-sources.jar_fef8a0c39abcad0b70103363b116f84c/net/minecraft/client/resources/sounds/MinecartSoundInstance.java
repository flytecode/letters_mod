package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartSoundInstance extends AbstractTickableSoundInstance {
   private static final float f_174931_ = 0.0F;
   private static final float f_174932_ = 0.7F;
   private static final float f_174933_ = 0.0F;
   private static final float f_174934_ = 1.0F;
   private static final float f_174935_ = 0.0025F;
   private final AbstractMinecart f_119693_;
   private float f_119694_ = 0.0F;

   public MinecartSoundInstance(AbstractMinecart p_119696_) {
      super(SoundEvents.f_12070_, SoundSource.NEUTRAL);
      this.f_119693_ = p_119696_;
      this.f_119578_ = true;
      this.f_119579_ = 0;
      this.f_119573_ = 0.0F;
      this.f_119575_ = (double)((float)p_119696_.m_20185_());
      this.f_119576_ = (double)((float)p_119696_.m_20186_());
      this.f_119577_ = (double)((float)p_119696_.m_20189_());
   }

   public boolean m_7767_() {
      return !this.f_119693_.m_20067_();
   }

   public boolean m_7784_() {
      return true;
   }

   public void m_7788_() {
      if (this.f_119693_.m_146910_()) {
         this.m_119609_();
      } else {
         this.f_119575_ = (double)((float)this.f_119693_.m_20185_());
         this.f_119576_ = (double)((float)this.f_119693_.m_20186_());
         this.f_119577_ = (double)((float)this.f_119693_.m_20189_());
         float f = (float)this.f_119693_.m_20184_().m_165924_();
         if (f >= 0.01F) {
            this.f_119694_ = Mth.m_14036_(this.f_119694_ + 0.0025F, 0.0F, 1.0F);
            this.f_119573_ = Mth.m_14179_(Mth.m_14036_(f, 0.0F, 0.5F), 0.0F, 0.7F);
         } else {
            this.f_119694_ = 0.0F;
            this.f_119573_ = 0.0F;
         }

      }
   }
}