package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;

public interface AreaTransformer1 extends DimensionTransformer {
   default <R extends Area> AreaFactory<R> m_77002_(BigContext<R> p_77003_, AreaFactory<R> p_77004_) {
      return () -> {
         R r = p_77004_.m_76488_();
         return p_77003_.m_6678_((p_164647_, p_164648_) -> {
            p_77003_.m_6687_((long)p_164647_, (long)p_164648_);
            return this.m_7591_(p_77003_, r, p_164647_, p_164648_);
         }, r);
      };
   }

   int m_7591_(BigContext<?> p_76998_, Area p_76999_, int p_77000_, int p_77001_);
}