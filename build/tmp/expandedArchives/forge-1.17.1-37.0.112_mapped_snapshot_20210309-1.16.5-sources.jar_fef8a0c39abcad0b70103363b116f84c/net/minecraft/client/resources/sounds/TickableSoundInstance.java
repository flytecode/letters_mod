package net.minecraft.client.resources.sounds;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface TickableSoundInstance extends SoundInstance {
   boolean m_7801_();

   void m_7788_();
}