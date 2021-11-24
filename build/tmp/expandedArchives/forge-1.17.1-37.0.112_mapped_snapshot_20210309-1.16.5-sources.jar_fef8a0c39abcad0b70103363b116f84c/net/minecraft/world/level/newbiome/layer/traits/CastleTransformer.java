package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface CastleTransformer extends AreaTransformer1, DimensionOffset1Transformer {
   int m_6949_(Context p_77059_, int p_77060_, int p_77061_, int p_77062_, int p_77063_, int p_77064_);

   default int m_7591_(BigContext<?> p_77055_, Area p_77056_, int p_77057_, int p_77058_) {
      return this.m_6949_(p_77055_, p_77056_.m_7929_(this.m_6320_(p_77057_ + 1), this.m_6317_(p_77058_ + 0)), p_77056_.m_7929_(this.m_6320_(p_77057_ + 2), this.m_6317_(p_77058_ + 1)), p_77056_.m_7929_(this.m_6320_(p_77057_ + 1), this.m_6317_(p_77058_ + 2)), p_77056_.m_7929_(this.m_6320_(p_77057_ + 0), this.m_6317_(p_77058_ + 1)), p_77056_.m_7929_(this.m_6320_(p_77057_ + 1), this.m_6317_(p_77058_ + 1)));
   }
}