package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface AreaTransformer0 {
   default <R extends Area> AreaFactory<R> m_76984_(BigContext<R> p_76985_) {
      return () -> {
         return p_76985_.m_7851_((p_164642_, p_164643_) -> {
            p_76985_.m_6687_((long)p_164642_, (long)p_164643_);
            return this.m_7215_(p_76985_, p_164642_, p_164643_);
         });
      };
   }

   int m_7215_(Context p_76990_, int p_76991_, int p_76992_);
}