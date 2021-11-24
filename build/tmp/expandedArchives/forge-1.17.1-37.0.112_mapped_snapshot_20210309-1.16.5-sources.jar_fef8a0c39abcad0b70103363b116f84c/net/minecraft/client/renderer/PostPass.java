package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PostPass implements AutoCloseable {
   private final EffectInstance f_110054_;
   public final RenderTarget f_110052_;
   public final RenderTarget f_110053_;
   private final List<IntSupplier> f_110055_ = Lists.newArrayList();
   private final List<String> f_110056_ = Lists.newArrayList();
   private final List<Integer> f_110057_ = Lists.newArrayList();
   private final List<Integer> f_110058_ = Lists.newArrayList();
   private Matrix4f f_110059_;

   public PostPass(ResourceManager p_110061_, String p_110062_, RenderTarget p_110063_, RenderTarget p_110064_) throws IOException {
      this.f_110054_ = new EffectInstance(p_110061_, p_110062_);
      this.f_110052_ = p_110063_;
      this.f_110053_ = p_110064_;
   }

   public void close() {
      this.f_110054_.close();
   }

   public final String m_173046_() {
      return this.f_110054_.m_172571_();
   }

   public void m_110069_(String p_110070_, IntSupplier p_110071_, int p_110072_, int p_110073_) {
      this.f_110056_.add(this.f_110056_.size(), p_110070_);
      this.f_110055_.add(this.f_110055_.size(), p_110071_);
      this.f_110057_.add(this.f_110057_.size(), p_110072_);
      this.f_110058_.add(this.f_110058_.size(), p_110073_);
   }

   public void m_110067_(Matrix4f p_110068_) {
      this.f_110059_ = p_110068_;
   }

   public void m_110065_(float p_110066_) {
      this.f_110052_.m_83970_();
      float f = (float)this.f_110053_.f_83915_;
      float f1 = (float)this.f_110053_.f_83916_;
      RenderSystem.m_69949_(0, 0, (int)f, (int)f1);
      this.f_110054_.m_108954_("DiffuseSampler", this.f_110052_::m_83975_);

      for(int i = 0; i < this.f_110055_.size(); ++i) {
         this.f_110054_.m_108954_(this.f_110056_.get(i), this.f_110055_.get(i));
         this.f_110054_.m_108960_("AuxSize" + i).m_7971_((float)this.f_110057_.get(i).intValue(), (float)this.f_110058_.get(i).intValue());
      }

      this.f_110054_.m_108960_("ProjMat").m_5679_(this.f_110059_);
      this.f_110054_.m_108960_("InSize").m_7971_((float)this.f_110052_.f_83915_, (float)this.f_110052_.f_83916_);
      this.f_110054_.m_108960_("OutSize").m_7971_(f, f1);
      this.f_110054_.m_108960_("Time").m_5985_(p_110066_);
      Minecraft minecraft = Minecraft.m_91087_();
      this.f_110054_.m_108960_("ScreenSize").m_7971_((float)minecraft.m_91268_().m_85441_(), (float)minecraft.m_91268_().m_85442_());
      this.f_110054_.m_108966_();
      this.f_110053_.m_83954_(Minecraft.f_91002_);
      this.f_110053_.m_83947_(false);
      RenderSystem.m_69456_(519);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
      bufferbuilder.m_5483_(0.0D, 0.0D, 500.0D).m_5752_();
      bufferbuilder.m_5483_((double)f, 0.0D, 500.0D).m_5752_();
      bufferbuilder.m_5483_((double)f, (double)f1, 500.0D).m_5752_();
      bufferbuilder.m_5483_(0.0D, (double)f1, 500.0D).m_5752_();
      bufferbuilder.m_85721_();
      BufferUploader.m_166847_(bufferbuilder);
      RenderSystem.m_69456_(515);
      this.f_110054_.m_108965_();
      this.f_110053_.m_83970_();
      this.f_110052_.m_83963_();

      for(Object object : this.f_110055_) {
         if (object instanceof RenderTarget) {
            ((RenderTarget)object).m_83963_();
         }
      }

   }

   public EffectInstance m_110074_() {
      return this.f_110054_;
   }
}