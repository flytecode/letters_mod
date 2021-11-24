package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface BishopTransformer extends AreaTransformer1, DimensionOffset1Transformer {
   int m_6939_(Context p_77034_, int p_77035_, int p_77036_, int p_77037_, int p_77038_, int p_77039_);

   default int m_7591_(BigContext<?> p_77030_, Area p_77031_, int p_77032_, int p_77033_) {
      return this.m_6939_(p_77030_, p_77031_.m_7929_(this.m_6320_(p_77032_ + 0), this.m_6317_(p_77033_ + 2)), p_77031_.m_7929_(this.m_6320_(p_77032_ + 2), this.m_6317_(p_77033_ + 2)), p_77031_.m_7929_(this.m_6320_(p_77032_ + 2), this.m_6317_(p_77033_ + 0)), p_77031_.m_7929_(this.m_6320_(p_77032_ + 0), this.m_6317_(p_77033_ + 0)), p_77031_.m_7929_(this.m_6320_(p_77032_ + 1), this.m_6317_(p_77033_ + 1)));
   }
}