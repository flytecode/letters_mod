package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum RemoveTooMuchOceanLayer implements CastleTransformer {
   INSTANCE;

   public int m_6949_(Context p_76855_, int p_76856_, int p_76857_, int p_76858_, int p_76859_, int p_76860_) {
      return Layers.m_76751_(p_76860_) && Layers.m_76751_(p_76856_) && Layers.m_76751_(p_76857_) && Layers.m_76751_(p_76859_) && Layers.m_76751_(p_76858_) && p_76855_.m_5826_(2) == 0 ? 1 : p_76860_;
   }
}