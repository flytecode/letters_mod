package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexFormat {
   private final ImmutableList<VertexFormatElement> f_86012_;
   private final ImmutableMap<String, VertexFormatElement> f_166905_;
   private final IntList f_86013_ = new IntArrayList();
   private final int f_86014_;
   private int f_166906_;
   private int f_166907_;
   private int f_166908_;

   public VertexFormat(ImmutableMap<String, VertexFormatElement> p_166910_) {
      this.f_166905_ = p_166910_;
      this.f_86012_ = p_166910_.values().asList();
      int i = 0;

      for(VertexFormatElement vertexformatelement : p_166910_.values()) {
         this.f_86013_.add(i);
         i += vertexformatelement.m_86050_();
      }

      this.f_86014_ = i;
   }

   public String toString() {
      return "format: " + this.f_166905_.size() + " elements: " + (String)this.f_166905_.entrySet().stream().map(Object::toString).collect(Collectors.joining(" "));
   }

   public int m_86017_() {
      return this.m_86020_() / 4;
   }

   public int m_86020_() {
      return this.f_86014_;
   }

   public ImmutableList<VertexFormatElement> m_86023_() {
      return this.f_86012_;
   }

   public ImmutableList<String> m_166911_() {
      return this.f_166905_.keySet().asList();
   }

   public boolean equals(Object p_86026_) {
      if (this == p_86026_) {
         return true;
      } else if (p_86026_ != null && this.getClass() == p_86026_.getClass()) {
         VertexFormat vertexformat = (VertexFormat)p_86026_;
         return this.f_86014_ != vertexformat.f_86014_ ? false : this.f_166905_.equals(vertexformat.f_166905_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_166905_.hashCode();
   }

   public void m_166912_() {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(this::m_166916_);
      } else {
         this.m_166916_();
      }
   }

   private void m_166916_() {
      int i = this.m_86020_();
      List<VertexFormatElement> list = this.m_86023_();

      for(int j = 0; j < list.size(); ++j) {
         list.get(j).m_166965_(j, (long)this.f_86013_.getInt(j), i);
      }

   }

   public void m_86024_() {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(this::m_166917_);
      } else {
         this.m_166917_();
      }
   }

   private void m_166917_() {
      ImmutableList<VertexFormatElement> immutablelist = this.m_86023_();

      for(int i = 0; i < immutablelist.size(); ++i) {
         VertexFormatElement vertexformatelement = immutablelist.get(i);
         vertexformatelement.m_166963_(i);
      }

   }

   public int m_166913_() {
      if (this.f_166906_ == 0) {
         this.f_166906_ = GlStateManager.m_157089_();
      }

      return this.f_166906_;
   }

   public int m_166914_() {
      if (this.f_166907_ == 0) {
         this.f_166907_ = GlStateManager.m_84537_();
      }

      return this.f_166907_;
   }

   public int m_166915_() {
      if (this.f_166908_ == 0) {
         this.f_166908_ = GlStateManager.m_84537_();
      }

      return this.f_166908_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum IndexType {
      BYTE(5121, 1),
      SHORT(5123, 2),
      INT(5125, 4);

      public final int f_166923_;
      public final int f_166924_;

      private IndexType(int p_166930_, int p_166931_) {
         this.f_166923_ = p_166930_;
         this.f_166924_ = p_166931_;
      }

      public static VertexFormat.IndexType m_166933_(int p_166934_) {
         if ((p_166934_ & -65536) != 0) {
            return INT;
         } else {
            return (p_166934_ & '\uff00') != 0 ? SHORT : BYTE;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Mode {
      LINES(4, 2, 2),
      LINE_STRIP(5, 2, 1),
      DEBUG_LINES(1, 2, 2),
      DEBUG_LINE_STRIP(3, 2, 1),
      TRIANGLES(4, 3, 3),
      TRIANGLE_STRIP(5, 3, 1),
      TRIANGLE_FAN(6, 3, 1),
      QUADS(4, 4, 4);

      public final int f_166946_;
      public final int f_166947_;
      public final int f_166948_;

      private Mode(int p_166954_, int p_166955_, int p_166956_) {
         this.f_166946_ = p_166954_;
         this.f_166947_ = p_166955_;
         this.f_166948_ = p_166956_;
      }

      public int m_166958_(int p_166959_) {
         int i;
         switch(this) {
         case LINE_STRIP:
         case DEBUG_LINES:
         case DEBUG_LINE_STRIP:
         case TRIANGLES:
         case TRIANGLE_STRIP:
         case TRIANGLE_FAN:
            i = p_166959_;
            break;
         case LINES:
         case QUADS:
            i = p_166959_ / 4 * 6;
            break;
         default:
            i = 0;
         }

         return i;
      }
   }

   public ImmutableMap<String, VertexFormatElement> getElementMapping() { return f_166905_; }
   public int getOffset(int index) { return f_86013_.getInt(index); }
   public boolean hasPosition() { return f_86012_.stream().anyMatch(e -> e.m_86048_() == VertexFormatElement.Usage.POSITION); }
   public boolean hasNormal() { return f_86012_.stream().anyMatch(e -> e.m_86048_() == VertexFormatElement.Usage.NORMAL); }
   public boolean hasColor() { return f_86012_.stream().anyMatch(e -> e.m_86048_() == VertexFormatElement.Usage.COLOR); }
   public boolean hasUV(int which) { return f_86012_.stream().anyMatch(e -> e.m_86048_() == VertexFormatElement.Usage.UV && e.m_86049_() == which); }
}
