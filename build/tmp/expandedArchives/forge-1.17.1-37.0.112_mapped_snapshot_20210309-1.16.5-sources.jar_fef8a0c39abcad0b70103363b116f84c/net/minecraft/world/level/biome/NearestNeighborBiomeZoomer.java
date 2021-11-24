package net.minecraft.world.level.biome;

import net.minecraft.core.QuartPos;

public enum NearestNeighborBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   public Biome m_7782_(long p_48573_, int p_48574_, int p_48575_, int p_48576_, BiomeManager.NoiseBiomeSource p_48577_) {
      return p_48577_.m_7158_(QuartPos.m_175400_(p_48574_), QuartPos.m_175400_(p_48575_), QuartPos.m_175400_(p_48576_));
   }
}