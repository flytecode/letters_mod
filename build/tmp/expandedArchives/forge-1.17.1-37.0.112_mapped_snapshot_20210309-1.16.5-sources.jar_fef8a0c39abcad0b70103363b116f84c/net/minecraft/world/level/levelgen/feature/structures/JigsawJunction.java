package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

public class JigsawJunction {
   private final int f_68919_;
   private final int f_68920_;
   private final int f_68921_;
   private final int f_68922_;
   private final StructureTemplatePool.Projection f_68923_;

   public JigsawJunction(int p_68925_, int p_68926_, int p_68927_, int p_68928_, StructureTemplatePool.Projection p_68929_) {
      this.f_68919_ = p_68925_;
      this.f_68920_ = p_68926_;
      this.f_68921_ = p_68927_;
      this.f_68922_ = p_68928_;
      this.f_68923_ = p_68929_;
   }

   public int m_68930_() {
      return this.f_68919_;
   }

   public int m_68935_() {
      return this.f_68920_;
   }

   public int m_68936_() {
      return this.f_68921_;
   }

   public int m_161609_() {
      return this.f_68922_;
   }

   public StructureTemplatePool.Projection m_161610_() {
      return this.f_68923_;
   }

   public <T> Dynamic<T> m_68933_(DynamicOps<T> p_68934_) {
      Builder<T, T> builder = ImmutableMap.builder();
      builder.put(p_68934_.createString("source_x"), p_68934_.createInt(this.f_68919_)).put(p_68934_.createString("source_ground_y"), p_68934_.createInt(this.f_68920_)).put(p_68934_.createString("source_z"), p_68934_.createInt(this.f_68921_)).put(p_68934_.createString("delta_y"), p_68934_.createInt(this.f_68922_)).put(p_68934_.createString("dest_proj"), p_68934_.createString(this.f_68923_.m_69297_()));
      return new Dynamic<>(p_68934_, p_68934_.createMap(builder.build()));
   }

   public static <T> JigsawJunction m_68931_(Dynamic<T> p_68932_) {
      return new JigsawJunction(p_68932_.get("source_x").asInt(0), p_68932_.get("source_ground_y").asInt(0), p_68932_.get("source_z").asInt(0), p_68932_.get("delta_y").asInt(0), StructureTemplatePool.Projection.m_69295_(p_68932_.get("dest_proj").asString("")));
   }

   public boolean equals(Object p_68938_) {
      if (this == p_68938_) {
         return true;
      } else if (p_68938_ != null && this.getClass() == p_68938_.getClass()) {
         JigsawJunction jigsawjunction = (JigsawJunction)p_68938_;
         if (this.f_68919_ != jigsawjunction.f_68919_) {
            return false;
         } else if (this.f_68921_ != jigsawjunction.f_68921_) {
            return false;
         } else if (this.f_68922_ != jigsawjunction.f_68922_) {
            return false;
         } else {
            return this.f_68923_ == jigsawjunction.f_68923_;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.f_68919_;
      i = 31 * i + this.f_68920_;
      i = 31 * i + this.f_68921_;
      i = 31 * i + this.f_68922_;
      return 31 * i + this.f_68923_.hashCode();
   }

   public String toString() {
      return "JigsawJunction{sourceX=" + this.f_68919_ + ", sourceGroundY=" + this.f_68920_ + ", sourceZ=" + this.f_68921_ + ", deltaY=" + this.f_68922_ + ", destProjection=" + this.f_68923_ + "}";
   }
}