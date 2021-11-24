package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum CaveSurface implements StringRepresentable {
   CEILING(Direction.UP, 1, "ceiling"),
   FLOOR(Direction.DOWN, -1, "floor");

   public static final Codec<CaveSurface> f_162094_ = StringRepresentable.m_14350_(CaveSurface::values, CaveSurface::m_162108_);
   private final Direction f_162095_;
   private final int f_162096_;
   private final String f_162097_;
   private static final CaveSurface[] f_162098_ = values();

   private CaveSurface(Direction p_162104_, int p_162105_, String p_162106_) {
      this.f_162095_ = p_162104_;
      this.f_162096_ = p_162105_;
      this.f_162097_ = p_162106_;
   }

   public Direction m_162107_() {
      return this.f_162095_;
   }

   public int m_162110_() {
      return this.f_162096_;
   }

   public static CaveSurface m_162108_(String p_162109_) {
      for(CaveSurface cavesurface : f_162098_) {
         if (cavesurface.m_7912_().equals(p_162109_)) {
            return cavesurface;
         }
      }

      throw new IllegalArgumentException("Unknown Surface type: " + p_162109_);
   }

   public String m_7912_() {
      return this.f_162097_;
   }
}