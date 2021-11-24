package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum RiverLayer implements CastleTransformer {
   INSTANCE;

   public int m_6949_(Context p_76883_, int p_76884_, int p_76885_, int p_76886_, int p_76887_, int p_76888_) {
      int i = m_76889_(p_76888_);
      return i == m_76889_(p_76887_) && i == m_76889_(p_76884_) && i == m_76889_(p_76885_) && i == m_76889_(p_76886_) ? -1 : 7;
   }

   private static int m_76889_(int p_76890_) {
      return p_76890_ >= 2 ? 2 + (p_76890_ & 1) : p_76890_;
   }
}