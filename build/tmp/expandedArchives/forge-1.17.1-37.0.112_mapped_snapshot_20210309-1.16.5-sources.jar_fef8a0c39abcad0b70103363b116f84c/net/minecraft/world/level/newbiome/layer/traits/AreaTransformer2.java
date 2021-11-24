package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface AreaTransformer2 extends DimensionTransformer {
   default <R extends Area> AreaFactory<R> m_77020_(BigContext<R> p_77021_, AreaFactory<R> p_77022_, AreaFactory<R> p_77023_) {
      return () -> {
         R r = p_77022_.m_76488_();
         R r1 = p_77023_.m_76488_();
         return p_77021_.m_6887_((p_164653_, p_164654_) -> {
            p_77021_.m_6687_((long)p_164653_, (long)p_164654_);
            return this.m_5924_(p_77021_, r, r1, p_164653_, p_164654_);
         }, r, r1);
      };
   }

   int m_5924_(Context p_77024_, Area p_77025_, Area p_77026_, int p_77027_, int p_77028_);
}