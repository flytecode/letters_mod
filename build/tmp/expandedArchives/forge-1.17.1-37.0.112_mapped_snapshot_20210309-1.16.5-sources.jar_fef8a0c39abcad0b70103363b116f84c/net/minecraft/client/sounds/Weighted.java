package net.minecraft.client.sounds;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface Weighted<T> {
   int m_7789_();

   T m_6776_();

   void m_8054_(SoundEngine p_120456_);
}