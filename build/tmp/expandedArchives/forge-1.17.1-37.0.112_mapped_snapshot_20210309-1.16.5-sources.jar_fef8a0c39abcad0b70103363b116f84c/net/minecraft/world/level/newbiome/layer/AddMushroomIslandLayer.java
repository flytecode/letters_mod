package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.BishopTransformer;

public enum AddMushroomIslandLayer implements BishopTransformer {
   INSTANCE;

   public int m_6939_(Context p_76636_, int p_76637_, int p_76638_, int p_76639_, int p_76640_, int p_76641_) {
      return Layers.m_76751_(p_76641_) && Layers.m_76751_(p_76640_) && Layers.m_76751_(p_76637_) && Layers.m_76751_(p_76639_) && Layers.m_76751_(p_76638_) && p_76636_.m_5826_(100) == 0 ? 14 : p_76641_;
   }
}