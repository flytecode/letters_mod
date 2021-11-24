package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EntityBoundSoundInstance extends AbstractTickableSoundInstance {
   private final Entity f_119675_;

   public EntityBoundSoundInstance(SoundEvent p_119677_, SoundSource p_119678_, float p_119679_, float p_119680_, Entity p_119681_) {
      super(p_119677_, p_119678_);
      this.f_119573_ = p_119679_;
      this.f_119574_ = p_119680_;
      this.f_119675_ = p_119681_;
      this.f_119575_ = (double)((float)this.f_119675_.m_20185_());
      this.f_119576_ = (double)((float)this.f_119675_.m_20186_());
      this.f_119577_ = (double)((float)this.f_119675_.m_20189_());
   }

   public boolean m_7767_() {
      return !this.f_119675_.m_20067_();
   }

   public void m_7788_() {
      if (this.f_119675_.m_146910_()) {
         this.m_119609_();
      } else {
         this.f_119575_ = (double)((float)this.f_119675_.m_20185_());
         this.f_119576_ = (double)((float)this.f_119675_.m_20186_());
         this.f_119577_ = (double)((float)this.f_119675_.m_20189_());
      }
   }
}