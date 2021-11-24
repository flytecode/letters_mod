package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.FriendlyByteBuf;

public interface ParticleOptions {
   ParticleType<?> m_6012_();

   void m_7711_(FriendlyByteBuf p_123732_);

   String m_5942_();

   @Deprecated
   public interface Deserializer<T extends ParticleOptions> {
      T m_5739_(ParticleType<T> p_123733_, StringReader p_123734_) throws CommandSyntaxException;

      T m_6507_(ParticleType<T> p_123735_, FriendlyByteBuf p_123736_);
   }
}