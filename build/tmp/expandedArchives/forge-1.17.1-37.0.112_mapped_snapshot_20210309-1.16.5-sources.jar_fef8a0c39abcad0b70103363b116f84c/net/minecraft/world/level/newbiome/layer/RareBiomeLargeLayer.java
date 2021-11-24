package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C1Transformer;

public enum RareBiomeLargeLayer implements C1Transformer {
   INSTANCE;

   public int m_8007_(Context p_76812_, int p_76813_) {
      return p_76812_.m_5826_(10) == 0 && p_76813_ == 21 ? 168 : p_76813_;
   }
}