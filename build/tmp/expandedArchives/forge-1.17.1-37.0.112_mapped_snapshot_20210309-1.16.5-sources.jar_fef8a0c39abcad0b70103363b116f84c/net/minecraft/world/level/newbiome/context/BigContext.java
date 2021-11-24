package net.minecraft.world.level.newbiome.context;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public interface BigContext<R extends Area> extends Context {
   void m_6687_(long p_76508_, long p_76509_);

   R m_7851_(PixelTransformer p_76510_);

   default R m_6678_(PixelTransformer p_76511_, R p_76512_) {
      return this.m_7851_(p_76511_);
   }

   default R m_6887_(PixelTransformer p_76513_, R p_76514_, R p_76515_) {
      return this.m_7851_(p_76513_);
   }

   default int m_76500_(int p_76501_, int p_76502_) {
      return this.m_5826_(2) == 0 ? p_76501_ : p_76502_;
   }

   default int m_76503_(int p_76504_, int p_76505_, int p_76506_, int p_76507_) {
      int i = this.m_5826_(4);
      if (i == 0) {
         return p_76504_;
      } else if (i == 1) {
         return p_76505_;
      } else {
         return i == 2 ? p_76506_ : p_76507_;
      }
   }
}