package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset0Transformer;

public enum RiverMixerLayer implements AreaTransformer2, DimensionOffset0Transformer {
   INSTANCE;

   public int m_5924_(Context p_76901_, Area p_76902_, Area p_76903_, int p_76904_, int p_76905_) {
      int i = p_76902_.m_7929_(this.m_6320_(p_76904_), this.m_6317_(p_76905_));
      int j = p_76903_.m_7929_(this.m_6320_(p_76904_), this.m_6317_(p_76905_));
      if (Layers.m_76721_(i)) {
         return i;
      } else if (j == 7) {
         if (i == 12) {
            return 11;
         } else {
            return i != 14 && i != 15 ? j & 255 : 15;
         }
      } else {
         return i;
      }
   }
}