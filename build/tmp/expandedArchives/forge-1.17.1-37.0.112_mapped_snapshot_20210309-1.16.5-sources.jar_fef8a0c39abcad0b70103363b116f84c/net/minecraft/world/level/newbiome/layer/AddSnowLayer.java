package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.C1Transformer;

public enum AddSnowLayer implements C1Transformer {
   INSTANCE;

   public int m_8007_(Context p_76652_, int p_76653_) {
      if (Layers.m_76751_(p_76653_)) {
         return p_76653_;
      } else {
         int i = p_76652_.m_5826_(6);
         if (i == 0) {
            return 4;
         } else {
            return i == 1 ? 3 : 1;
         }
      }
   }
}