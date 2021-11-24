package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianAttackSoundInstance extends AbstractTickableSoundInstance {
   private static final float f_174927_ = 0.0F;
   private static final float f_174928_ = 1.0F;
   private static final float f_174929_ = 0.7F;
   private static final float f_174930_ = 0.5F;
   private final Guardian f_119688_;

   public GuardianAttackSoundInstance(Guardian p_119690_) {
      super(SoundEvents.f_12001_, SoundSource.HOSTILE);
      this.f_119688_ = p_119690_;
      this.f_119580_ = SoundInstance.Attenuation.NONE;
      this.f_119578_ = true;
      this.f_119579_ = 0;
   }

   public boolean m_7767_() {
      return !this.f_119688_.m_20067_();
   }

   public void m_7788_() {
      if (!this.f_119688_.m_146910_() && this.f_119688_.m_5448_() == null) {
         this.f_119575_ = (double)((float)this.f_119688_.m_20185_());
         this.f_119576_ = (double)((float)this.f_119688_.m_20186_());
         this.f_119577_ = (double)((float)this.f_119688_.m_20189_());
         float f = this.f_119688_.m_32812_(0.0F);
         this.f_119573_ = 0.0F + 1.0F * f * f;
         this.f_119574_ = 0.7F + 0.5F * f;
      } else {
         this.m_119609_();
      }
   }
}