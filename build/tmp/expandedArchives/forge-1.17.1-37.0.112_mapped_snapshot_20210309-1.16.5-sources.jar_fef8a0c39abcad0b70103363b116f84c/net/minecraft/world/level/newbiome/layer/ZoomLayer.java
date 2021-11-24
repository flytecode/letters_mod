package net.minecraft.world.level.newbiome.layer;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

public enum ZoomLayer implements AreaTransformer1 {
   NORMAL,
   FUZZY {
      protected int m_7577_(BigContext<?> p_76979_, int p_76980_, int p_76981_, int p_76982_, int p_76983_) {
         return p_76979_.m_76503_(p_76980_, p_76981_, p_76982_, p_76983_);
      }
   };

   private static final int f_164637_ = 1;
   private static final int f_164638_ = 1;

   public int m_6320_(int p_76959_) {
      return p_76959_ >> 1;
   }

   public int m_6317_(int p_76971_) {
      return p_76971_ >> 1;
   }

   public int m_7591_(BigContext<?> p_76966_, Area p_76967_, int p_76968_, int p_76969_) {
      int i = p_76967_.m_7929_(this.m_6320_(p_76968_), this.m_6317_(p_76969_));
      p_76966_.m_6687_((long)(p_76968_ >> 1 << 1), (long)(p_76969_ >> 1 << 1));
      int j = p_76968_ & 1;
      int k = p_76969_ & 1;
      if (j == 0 && k == 0) {
         return i;
      } else {
         int l = p_76967_.m_7929_(this.m_6320_(p_76968_), this.m_6317_(p_76969_ + 1));
         int i1 = p_76966_.m_76500_(i, l);
         if (j == 0 && k == 1) {
            return i1;
         } else {
            int j1 = p_76967_.m_7929_(this.m_6320_(p_76968_ + 1), this.m_6317_(p_76969_));
            int k1 = p_76966_.m_76500_(i, j1);
            if (j == 1 && k == 0) {
               return k1;
            } else {
               int l1 = p_76967_.m_7929_(this.m_6320_(p_76968_ + 1), this.m_6317_(p_76969_ + 1));
               return this.m_7577_(p_76966_, i, j1, l, l1);
            }
         }
      }
   }

   protected int m_7577_(BigContext<?> p_76960_, int p_76961_, int p_76962_, int p_76963_, int p_76964_) {
      if (p_76962_ == p_76963_ && p_76963_ == p_76964_) {
         return p_76962_;
      } else if (p_76961_ == p_76962_ && p_76961_ == p_76963_) {
         return p_76961_;
      } else if (p_76961_ == p_76962_ && p_76961_ == p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76963_ && p_76961_ == p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76962_ && p_76963_ != p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76963_ && p_76962_ != p_76964_) {
         return p_76961_;
      } else if (p_76961_ == p_76964_ && p_76962_ != p_76963_) {
         return p_76961_;
      } else if (p_76962_ == p_76963_ && p_76961_ != p_76964_) {
         return p_76962_;
      } else if (p_76962_ == p_76964_ && p_76961_ != p_76963_) {
         return p_76962_;
      } else {
         return p_76963_ == p_76964_ && p_76961_ != p_76962_ ? p_76963_ : p_76960_.m_76503_(p_76961_, p_76962_, p_76963_, p_76964_);
      }
   }
}