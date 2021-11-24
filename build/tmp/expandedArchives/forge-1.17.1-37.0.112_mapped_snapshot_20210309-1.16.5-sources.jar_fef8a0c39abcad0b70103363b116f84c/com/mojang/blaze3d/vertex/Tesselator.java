package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Tesselator {
   private static final int f_166857_ = 8388608;
   private static final int f_166858_ = 2097152;
   private final BufferBuilder f_85907_;
   private static final Tesselator f_85908_ = new Tesselator();

   public static Tesselator m_85913_() {
      RenderSystem.m_69393_(RenderSystem::m_69585_);
      return f_85908_;
   }

   public Tesselator(int p_85912_) {
      this.f_85907_ = new BufferBuilder(p_85912_);
   }

   public Tesselator() {
      this(2097152);
   }

   public void m_85914_() {
      this.f_85907_.m_85721_();
      BufferUploader.m_85761_(this.f_85907_);
   }

   public BufferBuilder m_85915_() {
      return this.f_85907_;
   }
}