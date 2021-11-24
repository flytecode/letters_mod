package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GameTestDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final float f_173886_ = 0.02F;
   private final Map<BlockPos, GameTestDebugRenderer.Marker> f_113512_ = Maps.newHashMap();

   public void m_113524_(BlockPos p_113525_, int p_113526_, String p_113527_, int p_113528_) {
      this.f_113512_.put(p_113525_, new GameTestDebugRenderer.Marker(p_113526_, p_113527_, Util.m_137550_() + (long)p_113528_));
   }

   public void m_5630_() {
      this.f_113512_.clear();
   }

   public void m_7790_(PoseStack p_113519_, MultiBufferSource p_113520_, double p_113521_, double p_113522_, double p_113523_) {
      long i = Util.m_137550_();
      this.f_113512_.entrySet().removeIf((p_113517_) -> {
         return i > (p_113517_.getValue()).f_113534_;
      });
      this.f_113512_.forEach(this::m_113529_);
   }

   private void m_113529_(BlockPos p_113530_, GameTestDebugRenderer.Marker p_113531_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      RenderSystem.m_157429_(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.m_69472_();
      DebugRenderer.m_113463_(p_113530_, 0.02F, p_113531_.m_113539_(), p_113531_.m_113540_(), p_113531_.m_113541_(), p_113531_.m_113542_());
      if (!p_113531_.f_113533_.isEmpty()) {
         double d0 = (double)p_113530_.m_123341_() + 0.5D;
         double d1 = (double)p_113530_.m_123342_() + 1.2D;
         double d2 = (double)p_113530_.m_123343_() + 0.5D;
         DebugRenderer.m_113490_(p_113531_.f_113533_, d0, d1, d2, -1, 0.01F, true, 0.0F, true);
      }

      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   @OnlyIn(Dist.CLIENT)
   static class Marker {
      public int f_113532_;
      public String f_113533_;
      public long f_113534_;

      public Marker(int p_113536_, String p_113537_, long p_113538_) {
         this.f_113532_ = p_113536_;
         this.f_113533_ = p_113537_;
         this.f_113534_ = p_113538_;
      }

      public float m_113539_() {
         return (float)(this.f_113532_ >> 16 & 255) / 255.0F;
      }

      public float m_113540_() {
         return (float)(this.f_113532_ >> 8 & 255) / 255.0F;
      }

      public float m_113541_() {
         return (float)(this.f_113532_ & 255) / 255.0F;
      }

      public float m_113542_() {
         return (float)(this.f_113532_ >> 24 & 255) / 255.0F;
      }
   }
}