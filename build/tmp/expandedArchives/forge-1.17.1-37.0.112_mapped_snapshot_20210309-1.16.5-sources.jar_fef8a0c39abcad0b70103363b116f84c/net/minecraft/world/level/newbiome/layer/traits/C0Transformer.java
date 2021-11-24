package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface C0Transformer extends AreaTransformer1, DimensionOffset0Transformer {
   int m_8006_(Context p_77045_, int p_77046_);

   default int m_7591_(BigContext<?> p_77041_, Area p_77042_, int p_77043_, int p_77044_) {
      return this.m_8006_(p_77041_, p_77042_.m_7929_(this.m_6320_(p_77043_), this.m_6317_(p_77044_)));
   }
}