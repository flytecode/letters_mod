package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractTickableSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
   private boolean f_119604_;

   protected AbstractTickableSoundInstance(SoundEvent p_119606_, SoundSource p_119607_) {
      super(p_119606_, p_119607_);
   }

   public boolean m_7801_() {
      return this.f_119604_;
   }

   protected final void m_119609_() {
      this.f_119604_ = true;
      this.f_119578_ = false;
   }
}