package net.minecraft.world.level.newbiome.layer;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.level.newbiome.context.Context;
import net.minecraft.world.level.newbiome.layer.traits.CastleTransformer;

public enum ShoreLayer implements CastleTransformer {
   INSTANCE;

   private static final IntSet f_76910_ = new IntOpenHashSet(new int[]{26, 11, 12, 13, 140, 30, 31, 158, 10});
   private static final IntSet f_76911_ = new IntOpenHashSet(new int[]{168, 169, 21, 22, 23, 149, 151});

   public int m_6949_(Context p_76918_, int p_76919_, int p_76920_, int p_76921_, int p_76922_, int p_76923_) {
      if (p_76923_ == 14) {
         if (Layers.m_76751_(p_76919_) || Layers.m_76751_(p_76920_) || Layers.m_76751_(p_76921_) || Layers.m_76751_(p_76922_)) {
            return 15;
         }
      } else if (f_76911_.contains(p_76923_)) {
         if (!m_76924_(p_76919_) || !m_76924_(p_76920_) || !m_76924_(p_76921_) || !m_76924_(p_76922_)) {
            return 23;
         }

         if (Layers.m_76721_(p_76919_) || Layers.m_76721_(p_76920_) || Layers.m_76721_(p_76921_) || Layers.m_76721_(p_76922_)) {
            return 16;
         }
      } else if (p_76923_ != 3 && p_76923_ != 34 && p_76923_ != 20) {
         if (f_76910_.contains(p_76923_)) {
            if (!Layers.m_76721_(p_76923_) && (Layers.m_76721_(p_76919_) || Layers.m_76721_(p_76920_) || Layers.m_76721_(p_76921_) || Layers.m_76721_(p_76922_))) {
               return 26;
            }
         } else if (p_76923_ != 37 && p_76923_ != 38) {
            if (!Layers.m_76721_(p_76923_) && p_76923_ != 7 && p_76923_ != 6 && (Layers.m_76721_(p_76919_) || Layers.m_76721_(p_76920_) || Layers.m_76721_(p_76921_) || Layers.m_76721_(p_76922_))) {
               return 16;
            }
         } else if (!Layers.m_76721_(p_76919_) && !Layers.m_76721_(p_76920_) && !Layers.m_76721_(p_76921_) && !Layers.m_76721_(p_76922_) && (!this.m_76926_(p_76919_) || !this.m_76926_(p_76920_) || !this.m_76926_(p_76921_) || !this.m_76926_(p_76922_))) {
            return 2;
         }
      } else if (!Layers.m_76721_(p_76923_) && (Layers.m_76721_(p_76919_) || Layers.m_76721_(p_76920_) || Layers.m_76721_(p_76921_) || Layers.m_76721_(p_76922_))) {
         return 25;
      }

      return p_76923_;
   }

   private static boolean m_76924_(int p_76925_) {
      return f_76911_.contains(p_76925_) || p_76925_ == 4 || p_76925_ == 5 || Layers.m_76721_(p_76925_);
   }

   private boolean m_76926_(int p_76927_) {
      return p_76927_ == 37 || p_76927_ == 38 || p_76927_ == 39 || p_76927_ == 165 || p_76927_ == 166 || p_76927_ == 167;
   }
}