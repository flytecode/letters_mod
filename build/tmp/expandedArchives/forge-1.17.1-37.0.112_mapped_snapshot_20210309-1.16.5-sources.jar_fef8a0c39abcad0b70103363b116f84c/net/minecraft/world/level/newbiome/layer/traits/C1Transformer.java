package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface C1Transformer extends AreaTransformer1, DimensionOffset1Transformer {
   int m_8007_(Context p_77052_, int p_77053_);

   default int m_7591_(BigContext<?> p_77048_, Area p_77049_, int p_77050_, int p_77051_) {
      int i = p_77049_.m_7929_(this.m_6320_(p_77050_ + 1), this.m_6317_(p_77051_ + 1));
      return this.m_8007_(p_77048_, i);
   }
}