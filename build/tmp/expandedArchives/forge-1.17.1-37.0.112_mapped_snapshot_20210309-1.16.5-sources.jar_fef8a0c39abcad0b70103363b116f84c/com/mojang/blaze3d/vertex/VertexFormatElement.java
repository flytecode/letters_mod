package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexFormatElement {
   private final VertexFormatElement.Type f_86030_;
   private final VertexFormatElement.Usage f_86031_;
   private final int f_86032_;
   private final int f_86033_;
   private final int f_86034_;

   public VertexFormatElement(int p_86037_, VertexFormatElement.Type p_86038_, VertexFormatElement.Usage p_86039_, int p_86040_) {
      if (this.m_86042_(p_86037_, p_86039_)) {
         this.f_86031_ = p_86039_;
         this.f_86030_ = p_86038_;
         this.f_86032_ = p_86037_;
         this.f_86033_ = p_86040_;
         this.f_86034_ = p_86038_.m_86074_() * this.f_86033_;
      } else {
         throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
      }
   }

   private boolean m_86042_(int p_86043_, VertexFormatElement.Usage p_86044_) {
      return p_86043_ == 0 || p_86044_ == VertexFormatElement.Usage.UV;
   }

   public final VertexFormatElement.Type m_86041_() {
      return this.f_86030_;
   }

   public final VertexFormatElement.Usage m_86048_() {
      return this.f_86031_;
   }

   public final int m_166969_() {
      return this.f_86033_;
   }

   public final int m_86049_() {
      return this.f_86032_;
   }

   public String toString() {
      return this.f_86033_ + "," + this.f_86031_.m_86097_() + "," + this.f_86030_.m_86075_();
   }

   public final int m_86050_() {
      return this.f_86034_;
   }

   public final boolean m_166970_() {
      return this.f_86031_ == VertexFormatElement.Usage.POSITION;
   }

   public boolean equals(Object p_86053_) {
      if (this == p_86053_) {
         return true;
      } else if (p_86053_ != null && this.getClass() == p_86053_.getClass()) {
         VertexFormatElement vertexformatelement = (VertexFormatElement)p_86053_;
         if (this.f_86033_ != vertexformatelement.f_86033_) {
            return false;
         } else if (this.f_86032_ != vertexformatelement.f_86032_) {
            return false;
         } else if (this.f_86030_ != vertexformatelement.f_86030_) {
            return false;
         } else {
            return this.f_86031_ == vertexformatelement.f_86031_;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.f_86030_.hashCode();
      i = 31 * i + this.f_86031_.hashCode();
      i = 31 * i + this.f_86032_;
      return 31 * i + this.f_86033_;
   }

   public void m_166965_(int p_166966_, long p_166967_, int p_166968_) {
      this.f_86031_.m_166981_(this.f_86033_, this.f_86030_.m_86076_(), p_166968_, p_166967_, this.f_86032_, p_166966_);
   }

   public void m_166963_(int p_166964_) {
      this.f_86031_.m_166978_(this.f_86032_, p_166964_);
   }

    public int getElementCount() {
       return f_86033_;
    }

   @OnlyIn(Dist.CLIENT)
   public static enum Type {
      FLOAT(4, "Float", 5126),
      UBYTE(1, "Unsigned Byte", 5121),
      BYTE(1, "Byte", 5120),
      USHORT(2, "Unsigned Short", 5123),
      SHORT(2, "Short", 5122),
      UINT(4, "Unsigned Int", 5125),
      INT(4, "Int", 5124);

      private final int f_86063_;
      private final String f_86064_;
      private final int f_86065_;

      private Type(int p_86071_, String p_86072_, int p_86073_) {
         this.f_86063_ = p_86071_;
         this.f_86064_ = p_86072_;
         this.f_86065_ = p_86073_;
      }

      public int m_86074_() {
         return this.f_86063_;
      }

      public String m_86075_() {
         return this.f_86064_;
      }

      public int m_86076_() {
         return this.f_86065_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Usage {
      POSITION("Position", (p_167043_, p_167044_, p_167045_, p_167046_, p_167047_, p_167048_) -> {
         GlStateManager.m_84565_(p_167048_);
         GlStateManager.m_84238_(p_167048_, p_167043_, p_167044_, false, p_167045_, p_167046_);
      }, (p_167040_, p_167041_) -> {
         GlStateManager.m_84086_(p_167041_);
      }),
      NORMAL("Normal", (p_167033_, p_167034_, p_167035_, p_167036_, p_167037_, p_167038_) -> {
         GlStateManager.m_84565_(p_167038_);
         GlStateManager.m_84238_(p_167038_, p_167033_, p_167034_, true, p_167035_, p_167036_);
      }, (p_167030_, p_167031_) -> {
         GlStateManager.m_84086_(p_167031_);
      }),
      COLOR("Vertex Color", (p_167023_, p_167024_, p_167025_, p_167026_, p_167027_, p_167028_) -> {
         GlStateManager.m_84565_(p_167028_);
         GlStateManager.m_84238_(p_167028_, p_167023_, p_167024_, true, p_167025_, p_167026_);
      }, (p_167020_, p_167021_) -> {
         GlStateManager.m_84086_(p_167021_);
      }),
      UV("UV", (p_167013_, p_167014_, p_167015_, p_167016_, p_167017_, p_167018_) -> {
         GlStateManager.m_84565_(p_167018_);
         if (p_167014_ == 5126) {
            GlStateManager.m_84238_(p_167018_, p_167013_, p_167014_, false, p_167015_, p_167016_);
         } else {
            GlStateManager.m_157108_(p_167018_, p_167013_, p_167014_, p_167015_, p_167016_);
         }

      }, (p_167010_, p_167011_) -> {
         GlStateManager.m_84086_(p_167011_);
      }),
      PADDING("Padding", (p_167003_, p_167004_, p_167005_, p_167006_, p_167007_, p_167008_) -> {
      }, (p_167000_, p_167001_) -> {
      }),
      GENERIC("Generic", (p_166993_, p_166994_, p_166995_, p_166996_, p_166997_, p_166998_) -> {
         GlStateManager.m_84565_(p_166998_);
         GlStateManager.m_84238_(p_166998_, p_166993_, p_166994_, false, p_166995_, p_166996_);
      }, (p_166990_, p_166991_) -> {
         GlStateManager.m_84086_(p_166991_);
      });

      private final String f_86086_;
      private final VertexFormatElement.Usage.SetupState f_86087_;
      private final VertexFormatElement.Usage.ClearState f_86088_;

      private Usage(String p_166975_, VertexFormatElement.Usage.SetupState p_166976_, VertexFormatElement.Usage.ClearState p_166977_) {
         this.f_86086_ = p_166975_;
         this.f_86087_ = p_166976_;
         this.f_86088_ = p_166977_;
      }

      void m_166981_(int p_166982_, int p_166983_, int p_166984_, long p_166985_, int p_166986_, int p_166987_) {
         this.f_86087_.m_167052_(p_166982_, p_166983_, p_166984_, p_166985_, p_166986_, p_166987_);
      }

      public void m_166978_(int p_166979_, int p_166980_) {
         this.f_86088_.m_167049_(p_166979_, p_166980_);
      }

      public String m_86097_() {
         return this.f_86086_;
      }

      @FunctionalInterface
      @OnlyIn(Dist.CLIENT)
      interface ClearState {
         void m_167049_(int p_167050_, int p_167051_);
      }

      @FunctionalInterface
      @OnlyIn(Dist.CLIENT)
      interface SetupState {
         void m_167052_(int p_167053_, int p_167054_, int p_167055_, long p_167056_, int p_167057_, int p_167058_);
      }
   }
}
