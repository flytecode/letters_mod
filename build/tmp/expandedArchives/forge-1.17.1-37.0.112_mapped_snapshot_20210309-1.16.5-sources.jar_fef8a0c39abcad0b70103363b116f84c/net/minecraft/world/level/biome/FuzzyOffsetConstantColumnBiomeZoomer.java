package net.minecraft.world.level.biome;

public enum FuzzyOffsetConstantColumnBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   public Biome m_7782_(long p_48317_, int p_48318_, int p_48319_, int p_48320_, BiomeManager.NoiseBiomeSource p_48321_) {
      return FuzzyOffsetBiomeZoomer.INSTANCE.m_7782_(p_48317_, p_48318_, 0, p_48320_, p_48321_);
   }
}