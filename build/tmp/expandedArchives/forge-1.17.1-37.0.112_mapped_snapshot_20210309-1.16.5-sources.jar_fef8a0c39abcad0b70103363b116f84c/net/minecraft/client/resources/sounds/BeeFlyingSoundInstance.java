package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeFlyingSoundInstance extends BeeSoundInstance {
   public BeeFlyingSoundInstance(Bee p_119615_) {
      super(p_119615_, SoundEvents.f_11691_, SoundSource.NEUTRAL);
   }

   protected AbstractTickableSoundInstance m_5958_() {
      return new BeeAggressiveSoundInstance(this.f_119618_);
   }

   protected boolean m_7774_() {
      return this.f_119618_.m_21660_();
   }
}