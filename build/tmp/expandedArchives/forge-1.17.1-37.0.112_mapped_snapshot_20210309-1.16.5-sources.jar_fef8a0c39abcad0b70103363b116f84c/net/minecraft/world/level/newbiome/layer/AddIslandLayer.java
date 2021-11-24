package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.BishopTransformer;

public enum AddIslandLayer implements BishopTransformer {
   INSTANCE;

   public int m_6939_(Context p_76620_, int p_76621_, int p_76622_, int p_76623_, int p_76624_, int p_76625_) {
      if (!Layers.m_76751_(p_76625_) || Layers.m_76751_(p_76624_) && Layers.m_76751_(p_76623_) && Layers.m_76751_(p_76621_) && Layers.m_76751_(p_76622_)) {
         if (!Layers.m_76751_(p_76625_) && (Layers.m_76751_(p_76624_) || Layers.m_76751_(p_76621_) || Layers.m_76751_(p_76623_) || Layers.m_76751_(p_76622_)) && p_76620_.m_5826_(5) == 0) {
            if (Layers.m_76751_(p_76624_)) {
               return p_76625_ == 4 ? 4 : p_76624_;
            }

            if (Layers.m_76751_(p_76621_)) {
               return p_76625_ == 4 ? 4 : p_76621_;
            }

            if (Layers.m_76751_(p_76623_)) {
               return p_76625_ == 4 ? 4 : p_76623_;
            }

            if (Layers.m_76751_(p_76622_)) {
               return p_76625_ == 4 ? 4 : p_76622_;
            }
         }

         return p_76625_;
      } else {
         int i = 1;
         int j = 1;
         if (!Layers.m_76751_(p_76624_) && p_76620_.m_5826_(i++) == 0) {
            j = p_76624_;
         }

         if (!Layers.m_76751_(p_76623_) && p_76620_.m_5826_(i++) == 0) {
            j = p_76623_;
         }

         if (!Layers.m_76751_(p_76621_) && p_76620_.m_5826_(i++) == 0) {
            j = p_76621_;
         }

         if (!Layers.m_76751_(p_76622_) && p_76620_.m_5826_(i++) == 0) {
            j = p_76622_;
         }

         if (p_76620_.m_5826_(3) == 0) {
            return j;
         } else {
            return j == 4 ? 4 : p_76625_;
         }
      }
   }
}