package net.minecraft.core.particles;

import com.mojang.serialization.Codec;

public abstract class ParticleType<T extends ParticleOptions>  extends net.minecraftforge.registries.ForgeRegistryEntry<ParticleType<?>> {
   private final boolean f_123737_;
   private final ParticleOptions.Deserializer<T> f_123738_;

   public ParticleType(boolean p_123740_, ParticleOptions.Deserializer<T> p_123741_) {
      this.f_123737_ = p_123740_;
      this.f_123738_ = p_123741_;
   }

   public boolean m_123742_() {
      return this.f_123737_;
   }

   public ParticleOptions.Deserializer<T> m_123743_() {
      return this.f_123738_;
   }

   public abstract Codec<T> m_7652_();
}
