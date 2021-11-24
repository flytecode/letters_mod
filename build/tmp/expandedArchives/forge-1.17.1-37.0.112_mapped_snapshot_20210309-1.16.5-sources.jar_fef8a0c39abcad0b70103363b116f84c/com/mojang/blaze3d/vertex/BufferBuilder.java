package com.mojang.blaze3d.vertex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BufferBuilder extends DefaultedVertexConsumer implements BufferVertexConsumer {
   private static final int f_166763_ = 2097152;
   private static final Logger f_85647_ = LogManager.getLogger();
   private ByteBuffer f_85648_;
   private final List<BufferBuilder.DrawState> f_166764_ = Lists.newArrayList();
   private int f_166765_;
   private int f_85651_;
   private int f_85652_;
   private int f_85653_;
   private int f_85654_;
   @Nullable
   private VertexFormatElement f_85655_;
   private int f_85656_;
   private VertexFormat f_85658_;
   private VertexFormat.Mode f_85657_;
   private boolean f_85659_;
   private boolean f_85660_;
   private boolean f_85661_;
   @Nullable
   private Vector3f[] f_166766_;
   private float f_166767_ = Float.NaN;
   private float f_166768_ = Float.NaN;
   private float f_166769_ = Float.NaN;
   private boolean f_166762_;

   public BufferBuilder(int p_85664_) {
      this.f_85648_ = MemoryTracker.m_182527_(p_85664_ * 6);
   }

   private void m_85665_() {
      this.m_85722_(this.f_85658_.m_86020_());
   }

   private void m_85722_(int p_85723_) {
      if (this.f_85652_ + p_85723_ > this.f_85648_.capacity()) {
         int i = this.f_85648_.capacity();
         int j = i + m_85725_(p_85723_);
         f_85647_.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", i, j);
         ByteBuffer bytebuffer = MemoryTracker.m_182529_(this.f_85648_, j);
         bytebuffer.rewind();
         this.f_85648_ = bytebuffer;
      }
   }

   private static int m_85725_(int p_85726_) {
      int i = 2097152;
      if (p_85726_ == 0) {
         return i;
      } else {
         if (p_85726_ < 0) {
            i *= -1;
         }

         int j = p_85726_ % i;
         return j == 0 ? p_85726_ : p_85726_ + i - j;
      }
   }

   public void m_166771_(float p_166772_, float p_166773_, float p_166774_) {
      if (this.f_85657_ == VertexFormat.Mode.QUADS) {
         if (this.f_166767_ != p_166772_ || this.f_166768_ != p_166773_ || this.f_166769_ != p_166774_) {
            this.f_166767_ = p_166772_;
            this.f_166768_ = p_166773_;
            this.f_166769_ = p_166774_;
            if (this.f_166766_ == null) {
               this.f_166766_ = this.m_166794_();
            }
         }

      }
   }

   public BufferBuilder.SortState m_166770_() {
      return new BufferBuilder.SortState(this.f_85657_, this.f_85654_, this.f_166766_, this.f_166767_, this.f_166768_, this.f_166769_);
   }

   public void m_166775_(BufferBuilder.SortState p_166776_) {
      this.f_85648_.clear();
      this.f_85657_ = p_166776_.f_166817_;
      this.f_85654_ = p_166776_.f_166818_;
      this.f_85652_ = this.f_85651_;
      this.f_166766_ = p_166776_.f_166819_;
      this.f_166767_ = p_166776_.f_166820_;
      this.f_166768_ = p_166776_.f_166821_;
      this.f_166769_ = p_166776_.f_166822_;
      this.f_166762_ = true;
   }

   public void m_166779_(VertexFormat.Mode p_166780_, VertexFormat p_166781_) {
      if (this.f_85661_) {
         throw new IllegalStateException("Already building!");
      } else {
         this.f_85661_ = true;
         this.f_85657_ = p_166780_;
         this.m_85704_(p_166781_);
         this.f_85655_ = p_166781_.m_86023_().get(0);
         this.f_85656_ = 0;
         this.f_85648_.clear();
      }
   }

   private void m_85704_(VertexFormat p_85705_) {
      if (this.f_85658_ != p_85705_) {
         this.f_85658_ = p_85705_;
         boolean flag = p_85705_ == DefaultVertexFormat.f_85812_;
         boolean flag1 = p_85705_ == DefaultVertexFormat.f_85811_;
         this.f_85659_ = flag || flag1;
         this.f_85660_ = flag;
      }
   }

   private IntConsumer m_166777_(VertexFormat.IndexType p_166778_) {
      switch(p_166778_) {
      case BYTE:
         return (p_166793_) -> {
            this.f_85648_.put((byte)p_166793_);
         };
      case SHORT:
         return (p_166791_) -> {
            this.f_85648_.putShort((short)p_166791_);
         };
      case INT:
      default:
         return (p_166789_) -> {
            this.f_85648_.putInt(p_166789_);
         };
      }
   }

   private Vector3f[] m_166794_() {
      FloatBuffer floatbuffer = this.f_85648_.asFloatBuffer();
      int i = this.f_85651_ / 4;
      int j = this.f_85658_.m_86017_();
      int k = j * this.f_85657_.f_166948_;
      int l = this.f_85654_ / this.f_85657_.f_166948_;
      Vector3f[] avector3f = new Vector3f[l];

      for(int i1 = 0; i1 < l; ++i1) {
         float f = floatbuffer.get(i + i1 * k + 0);
         float f1 = floatbuffer.get(i + i1 * k + 1);
         float f2 = floatbuffer.get(i + i1 * k + 2);
         float f3 = floatbuffer.get(i + i1 * k + j * 2 + 0);
         float f4 = floatbuffer.get(i + i1 * k + j * 2 + 1);
         float f5 = floatbuffer.get(i + i1 * k + j * 2 + 2);
         float f6 = (f + f3) / 2.0F;
         float f7 = (f1 + f4) / 2.0F;
         float f8 = (f2 + f5) / 2.0F;
         avector3f[i1] = new Vector3f(f6, f7, f8);
      }

      return avector3f;
   }

   private void m_166786_(VertexFormat.IndexType p_166787_) {
      float[] afloat = new float[this.f_166766_.length];
      int[] aint = new int[this.f_166766_.length];

      for(int i = 0; i < this.f_166766_.length; aint[i] = i++) {
         float f = this.f_166766_[i].m_122239_() - this.f_166767_;
         float f1 = this.f_166766_[i].m_122260_() - this.f_166768_;
         float f2 = this.f_166766_[i].m_122269_() - this.f_166769_;
         afloat[i] = f * f + f1 * f1 + f2 * f2;
      }

      IntArrays.mergeSort(aint, (p_166784_, p_166785_) -> {
         return Floats.compare(afloat[p_166785_], afloat[p_166784_]);
      });
      IntConsumer intconsumer = this.m_166777_(p_166787_);
      this.f_85648_.position(this.f_85652_);

      for(int j : aint) {
         intconsumer.accept(j * this.f_85657_.f_166948_ + 0);
         intconsumer.accept(j * this.f_85657_.f_166948_ + 1);
         intconsumer.accept(j * this.f_85657_.f_166948_ + 2);
         intconsumer.accept(j * this.f_85657_.f_166948_ + 2);
         intconsumer.accept(j * this.f_85657_.f_166948_ + 3);
         intconsumer.accept(j * this.f_85657_.f_166948_ + 0);
      }

   }

   public void m_85721_() {
      if (!this.f_85661_) {
         throw new IllegalStateException("Not building!");
      } else {
         int i = this.f_85657_.m_166958_(this.f_85654_);
         VertexFormat.IndexType vertexformat$indextype = VertexFormat.IndexType.m_166933_(i);
         boolean flag;
         if (this.f_166766_ != null) {
            int j = Mth.m_144941_(i * vertexformat$indextype.f_166924_, 4);
            this.m_85722_(j);
            this.m_166786_(vertexformat$indextype);
            flag = false;
            this.f_85652_ += j;
            this.f_85651_ += this.f_85654_ * this.f_85658_.m_86020_() + j;
         } else {
            flag = true;
            this.f_85651_ += this.f_85654_ * this.f_85658_.m_86020_();
         }

         this.f_85661_ = false;
         this.f_166764_.add(new BufferBuilder.DrawState(this.f_85658_, this.f_85654_, i, this.f_85657_, vertexformat$indextype, this.f_166762_, flag));
         this.f_85654_ = 0;
         this.f_85655_ = null;
         this.f_85656_ = 0;
         this.f_166766_ = null;
         this.f_166767_ = Float.NaN;
         this.f_166768_ = Float.NaN;
         this.f_166769_ = Float.NaN;
         this.f_166762_ = false;
      }
   }

   public void m_5672_(int p_85686_, byte p_85687_) {
      this.f_85648_.put(this.f_85652_ + p_85686_, p_85687_);
   }

   public void m_5586_(int p_85700_, short p_85701_) {
      this.f_85648_.putShort(this.f_85652_ + p_85700_, p_85701_);
   }

   public void m_5832_(int p_85689_, float p_85690_) {
      this.f_85648_.putFloat(this.f_85652_ + p_85689_, p_85690_);
   }

   public void m_5752_() {
      if (this.f_85656_ != 0) {
         throw new IllegalStateException("Not filled all elements of the vertex");
      } else {
         ++this.f_85654_;
         this.m_85665_();
         if (this.f_85657_ == VertexFormat.Mode.LINES || this.f_85657_ == VertexFormat.Mode.LINE_STRIP) {
            int i = this.f_85658_.m_86020_();
            this.f_85648_.position(this.f_85652_);
            ByteBuffer bytebuffer = this.f_85648_.duplicate();
            bytebuffer.position(this.f_85652_ - i).limit(this.f_85652_);
            this.f_85648_.put(bytebuffer);
            this.f_85652_ += i;
            ++this.f_85654_;
            this.m_85665_();
         }

      }
   }

   public void m_5751_() {
      ImmutableList<VertexFormatElement> immutablelist = this.f_85658_.m_86023_();
      this.f_85656_ = (this.f_85656_ + 1) % immutablelist.size();
      this.f_85652_ += this.f_85655_.m_86050_();
      VertexFormatElement vertexformatelement = immutablelist.get(this.f_85656_);
      this.f_85655_ = vertexformatelement;
      if (vertexformatelement.m_86048_() == VertexFormatElement.Usage.PADDING) {
         this.m_5751_();
      }

      if (this.f_85824_ && this.f_85655_.m_86048_() == VertexFormatElement.Usage.COLOR) {
         BufferVertexConsumer.super.m_6122_(this.f_85825_, this.f_85826_, this.f_85827_, this.f_85828_);
      }

   }

   public VertexConsumer m_6122_(int p_85692_, int p_85693_, int p_85694_, int p_85695_) {
      if (this.f_85824_) {
         throw new IllegalStateException();
      } else {
         return BufferVertexConsumer.super.m_6122_(p_85692_, p_85693_, p_85694_, p_85695_);
      }
   }

   public void m_5954_(float p_85671_, float p_85672_, float p_85673_, float p_85674_, float p_85675_, float p_85676_, float p_85677_, float p_85678_, float p_85679_, int p_85680_, int p_85681_, float p_85682_, float p_85683_, float p_85684_) {
      if (this.f_85824_) {
         throw new IllegalStateException();
      } else if (this.f_85659_) {
         this.m_5832_(0, p_85671_);
         this.m_5832_(4, p_85672_);
         this.m_5832_(8, p_85673_);
         this.m_5672_(12, (byte)((int)(p_85674_ * 255.0F)));
         this.m_5672_(13, (byte)((int)(p_85675_ * 255.0F)));
         this.m_5672_(14, (byte)((int)(p_85676_ * 255.0F)));
         this.m_5672_(15, (byte)((int)(p_85677_ * 255.0F)));
         this.m_5832_(16, p_85678_);
         this.m_5832_(20, p_85679_);
         int i;
         if (this.f_85660_) {
            this.m_5586_(24, (short)(p_85680_ & '\uffff'));
            this.m_5586_(26, (short)(p_85680_ >> 16 & '\uffff'));
            i = 28;
         } else {
            i = 24;
         }

         this.m_5586_(i + 0, (short)(p_85681_ & '\uffff'));
         this.m_5586_(i + 2, (short)(p_85681_ >> 16 & '\uffff'));
         this.m_5672_(i + 4, BufferVertexConsumer.m_85774_(p_85682_));
         this.m_5672_(i + 5, BufferVertexConsumer.m_85774_(p_85683_));
         this.m_5672_(i + 6, BufferVertexConsumer.m_85774_(p_85684_));
         this.f_85652_ += i + 8;
         this.m_5752_();
      } else {
         super.m_5954_(p_85671_, p_85672_, p_85673_, p_85674_, p_85675_, p_85676_, p_85677_, p_85678_, p_85679_, p_85680_, p_85681_, p_85682_, p_85683_, p_85684_);
      }
   }

   public Pair<BufferBuilder.DrawState, ByteBuffer> m_85728_() {
      BufferBuilder.DrawState bufferbuilder$drawstate = this.f_166764_.get(this.f_166765_++);
      this.f_85648_.position(this.f_85653_);
      this.f_85653_ += Mth.m_144941_(bufferbuilder$drawstate.m_166813_(), 4);
      this.f_85648_.limit(this.f_85653_);
      if (this.f_166765_ == this.f_166764_.size() && this.f_85654_ == 0) {
         this.m_85729_();
      }

      ByteBuffer bytebuffer = this.f_85648_.slice();
      bytebuffer.order(this.f_85648_.order()); // FORGE: Fix incorrect byte order
      this.f_85648_.clear();
      return Pair.of(bufferbuilder$drawstate, bytebuffer);
   }

   public void m_85729_() {
      if (this.f_85651_ != this.f_85653_) {
         f_85647_.warn("Bytes mismatch {} {}", this.f_85651_, this.f_85653_);
      }

      this.m_85730_();
   }

   public void m_85730_() {
      this.f_85651_ = 0;
      this.f_85653_ = 0;
      this.f_85652_ = 0;
      this.f_166764_.clear();
      this.f_166765_ = 0;
   }

   public VertexFormatElement m_6297_() {
      if (this.f_85655_ == null) {
         throw new IllegalStateException("BufferBuilder not started");
      } else {
         return this.f_85655_;
      }
   }

   public boolean m_85732_() {
      return this.f_85661_;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class DrawState {
      private final VertexFormat f_85733_;
      private final int f_85734_;
      private final int f_166797_;
      private final VertexFormat.Mode f_85735_;
      private final VertexFormat.IndexType f_166798_;
      private final boolean f_166799_;
      private final boolean f_166800_;

      DrawState(VertexFormat p_166802_, int p_166803_, int p_166804_, VertexFormat.Mode p_166805_, VertexFormat.IndexType p_166806_, boolean p_166807_, boolean p_166808_) {
         this.f_85733_ = p_166802_;
         this.f_85734_ = p_166803_;
         this.f_166797_ = p_166804_;
         this.f_85735_ = p_166805_;
         this.f_166798_ = p_166806_;
         this.f_166799_ = p_166807_;
         this.f_166800_ = p_166808_;
      }

      public VertexFormat m_85745_() {
         return this.f_85733_;
      }

      public int m_85746_() {
         return this.f_85734_;
      }

      public int m_166809_() {
         return this.f_166797_;
      }

      public VertexFormat.Mode m_166810_() {
         return this.f_85735_;
      }

      public VertexFormat.IndexType m_166811_() {
         return this.f_166798_;
      }

      public int m_166812_() {
         return this.f_85734_ * this.f_85733_.m_86020_();
      }

      private int m_166816_() {
         return this.f_166800_ ? 0 : this.f_166797_ * this.f_166798_.f_166924_;
      }

      public int m_166813_() {
         return this.m_166812_() + this.m_166816_();
      }

      public boolean m_166814_() {
         return this.f_166799_;
      }

      public boolean m_166815_() {
         return this.f_166800_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class SortState {
      final VertexFormat.Mode f_166817_;
      final int f_166818_;
      @Nullable
      final Vector3f[] f_166819_;
      final float f_166820_;
      final float f_166821_;
      final float f_166822_;

      SortState(VertexFormat.Mode p_166824_, int p_166825_, @Nullable Vector3f[] p_166826_, float p_166827_, float p_166828_, float p_166829_) {
         this.f_166817_ = p_166824_;
         this.f_166818_ = p_166825_;
         this.f_166819_ = p_166826_;
         this.f_166820_ = p_166827_;
         this.f_166821_ = p_166828_;
         this.f_166822_ = p_166829_;
      }
   }

   // Forge start
   public void putBulkData(ByteBuffer buffer) {
      m_85722_(buffer.limit() + this.f_85658_.m_86020_());
      this.f_85648_.position(this.f_85654_ * this.f_85658_.m_86020_());
      this.f_85648_.put(buffer);
      this.f_85654_ += buffer.limit() / this.f_85658_.m_86020_();
      this.f_85652_ += buffer.limit();
   }

   public VertexFormat getVertexFormat() { return this.f_85658_; }
}
