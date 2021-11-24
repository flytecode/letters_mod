package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum GraphicsStatus {
   FAST(0, "options.graphics.fast"),
   FANCY(1, "options.graphics.fancy"),
   FABULOUS(2, "options.graphics.fabulous");

   private static final GraphicsStatus[] f_90763_ = Arrays.stream(values()).sorted(Comparator.comparingInt(GraphicsStatus::m_90773_)).toArray((p_90778_) -> {
      return new GraphicsStatus[p_90778_];
   });
   private final int f_90764_;
   private final String f_90765_;

   private GraphicsStatus(int p_90771_, String p_90772_) {
      this.f_90764_ = p_90771_;
      this.f_90765_ = p_90772_;
   }

   public int m_90773_() {
      return this.f_90764_;
   }

   public String m_90776_() {
      return this.f_90765_;
   }

   public String toString() {
      switch(this) {
      case FAST:
         return "fast";
      case FANCY:
         return "fancy";
      case FABULOUS:
         return "fabulous";
      default:
         throw new IllegalArgumentException();
      }
   }

   public static GraphicsStatus m_90774_(int p_90775_) {
      return f_90763_[Mth.m_14100_(p_90775_, f_90763_.length)];
   }
}