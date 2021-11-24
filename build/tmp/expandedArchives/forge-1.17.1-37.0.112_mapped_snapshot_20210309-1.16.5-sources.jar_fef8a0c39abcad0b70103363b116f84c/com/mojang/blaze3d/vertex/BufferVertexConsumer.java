package com.mojang.blaze3d.vertex;

import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface BufferVertexConsumer extends VertexConsumer {
   VertexFormatElement m_6297_();

   void m_5751_();

   void m_5672_(int p_85779_, byte p_85780_);

   void m_5586_(int p_85791_, short p_85792_);

   void m_5832_(int p_85781_, float p_85782_);

   default VertexConsumer m_5483_(double p_85771_, double p_85772_, double p_85773_) {
      if (this.m_6297_().m_86048_() != VertexFormatElement.Usage.POSITION) {
         return this;
      } else if (this.m_6297_().m_86041_() == VertexFormatElement.Type.FLOAT && this.m_6297_().m_166969_() == 3) {
         this.m_5832_(0, (float)p_85771_);
         this.m_5832_(4, (float)p_85772_);
         this.m_5832_(8, (float)p_85773_);
         this.m_5751_();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   default VertexConsumer m_6122_(int p_85787_, int p_85788_, int p_85789_, int p_85790_) {
      VertexFormatElement vertexformatelement = this.m_6297_();
      if (vertexformatelement.m_86048_() != VertexFormatElement.Usage.COLOR) {
         return this;
      } else if (vertexformatelement.m_86041_() == VertexFormatElement.Type.UBYTE && vertexformatelement.m_166969_() == 4) {
         this.m_5672_(0, (byte)p_85787_);
         this.m_5672_(1, (byte)p_85788_);
         this.m_5672_(2, (byte)p_85789_);
         this.m_5672_(3, (byte)p_85790_);
         this.m_5751_();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   default VertexConsumer m_7421_(float p_85777_, float p_85778_) {
      VertexFormatElement vertexformatelement = this.m_6297_();
      if (vertexformatelement.m_86048_() == VertexFormatElement.Usage.UV && vertexformatelement.m_86049_() == 0) {
         if (vertexformatelement.m_86041_() == VertexFormatElement.Type.FLOAT && vertexformatelement.m_166969_() == 2) {
            this.m_5832_(0, p_85777_);
            this.m_5832_(4, p_85778_);
            this.m_5751_();
            return this;
         } else {
            throw new IllegalStateException();
         }
      } else {
         return this;
      }
   }

   default VertexConsumer m_7122_(int p_85784_, int p_85785_) {
      return this.m_85793_((short)p_85784_, (short)p_85785_, 1);
   }

   default VertexConsumer m_7120_(int p_85802_, int p_85803_) {
      return this.m_85793_((short)p_85802_, (short)p_85803_, 2);
   }

   default VertexConsumer m_85793_(short p_85794_, short p_85795_, int p_85796_) {
      VertexFormatElement vertexformatelement = this.m_6297_();
      if (vertexformatelement.m_86048_() == VertexFormatElement.Usage.UV && vertexformatelement.m_86049_() == p_85796_) {
         if (vertexformatelement.m_86041_() == VertexFormatElement.Type.SHORT && vertexformatelement.m_166969_() == 2) {
            this.m_5586_(0, p_85794_);
            this.m_5586_(2, p_85795_);
            this.m_5751_();
            return this;
         } else {
            throw new IllegalStateException();
         }
      } else {
         return this;
      }
   }

   default VertexConsumer m_5601_(float p_85798_, float p_85799_, float p_85800_) {
      VertexFormatElement vertexformatelement = this.m_6297_();
      if (vertexformatelement.m_86048_() != VertexFormatElement.Usage.NORMAL) {
         return this;
      } else if (vertexformatelement.m_86041_() == VertexFormatElement.Type.BYTE && vertexformatelement.m_166969_() == 3) {
         this.m_5672_(0, m_85774_(p_85798_));
         this.m_5672_(1, m_85774_(p_85799_));
         this.m_5672_(2, m_85774_(p_85800_));
         this.m_5751_();
         return this;
      } else {
         throw new IllegalStateException();
      }
   }

   static byte m_85774_(float p_85775_) {
      return (byte)((int)(Mth.m_14036_(p_85775_, -1.0F, 1.0F) * 127.0F) & 255);
   }
}