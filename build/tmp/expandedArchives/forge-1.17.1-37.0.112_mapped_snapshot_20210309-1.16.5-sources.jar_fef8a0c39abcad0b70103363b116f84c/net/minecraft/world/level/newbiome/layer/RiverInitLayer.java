package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C0Transformer;

public enum RiverInitLayer implements C0Transformer {
   INSTANCE;

   public int m_8006_(Context p_76871_, int p_76872_) {
      return Layers.m_76751_(p_76872_) ? p_76872_ : p_76871_.m_5826_(299999) + 2;
   }
}