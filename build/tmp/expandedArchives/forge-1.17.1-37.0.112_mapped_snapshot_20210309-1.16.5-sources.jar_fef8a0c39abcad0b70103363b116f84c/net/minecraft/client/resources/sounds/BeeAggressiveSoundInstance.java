package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeAggressiveSoundInstance extends BeeSoundInstance {
   public BeeAggressiveSoundInstance(Bee p_119611_) {
      super(p_119611_, SoundEvents.f_11690_, SoundSource.NEUTRAL);
      this.f_119579_ = 0;
   }

   protected AbstractTickableSoundInstance m_5958_() {
      return new BeeFlyingSoundInstance(this.f_119618_);
   }

   protected boolean m_7774_() {
      return !this.f_119618_.m_21660_();
   }
}