package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer2;
import net.minecraft.world.level.newbiome.layer.traits.DimensionOffset0Transformer;

public enum OceanMixerLayer implements AreaTransformer2, DimensionOffset0Transformer {
   INSTANCE;

   public int m_5924_(Context p_76797_, Area p_76798_, Area p_76799_, int p_76800_, int p_76801_) {
      int i = p_76798_.m_7929_(this.m_6320_(p_76800_), this.m_6317_(p_76801_));
      int j = p_76799_.m_7929_(this.m_6320_(p_76800_), this.m_6317_(p_76801_));
      if (!Layers.m_76721_(i)) {
         return i;
      } else {
         int k = 8;
         int l = 4;

         for(int i1 = -8; i1 <= 8; i1 += 4) {
            for(int j1 = -8; j1 <= 8; j1 += 4) {
               int k1 = p_76798_.m_7929_(this.m_6320_(p_76800_ + i1), this.m_6317_(p_76801_ + j1));
               if (!Layers.m_76721_(k1)) {
                  if (j == 44) {
                     return 45;
                  }

                  if (j == 10) {
                     return 46;
                  }
               }
            }
         }

         if (i == 24) {
            if (j == 45) {
               return 48;
            }

            if (j == 0) {
               return 24;
            }

            if (j == 46) {
               return 49;
            }

            if (j == 10) {
               return 50;
            }
         }

         return j;
      }
   }
}