package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RaidDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int f_173901_ = 160;
   private static final float f_173902_ = 0.04F;
   private final Minecraft f_113647_;
   private Collection<BlockPos> f_113648_ = Lists.newArrayList();

   public RaidDebugRenderer(Minecraft p_113650_) {
      this.f_113647_ = p_113650_;
   }

   public void m_113663_(Collection<BlockPos> p_113664_) {
      this.f_113648_ = p_113664_;
   }

   public void m_7790_(PoseStack p_113652_, MultiBufferSource p_113653_, double p_113654_, double p_113655_, double p_113656_) {
      BlockPos blockpos = this.m_113665_().m_90588_();

      for(BlockPos blockpos1 : this.f_113648_) {
         if (blockpos.m_123314_(blockpos1, 160.0D)) {
            m_113657_(blockpos1);
         }
      }

   }

   private static void m_113657_(BlockPos p_113658_) {
      DebugRenderer.m_113470_(p_113658_.m_142022_(-0.5D, -0.5D, -0.5D), p_113658_.m_142022_(1.5D, 1.5D, 1.5D), 1.0F, 0.0F, 0.0F, 0.15F);
      int i = -65536;
      m_113659_("Raid center", p_113658_, -65536);
   }

   private static void m_113659_(String p_113660_, BlockPos p_113661_, int p_113662_) {
      double d0 = (double)p_113661_.m_123341_() + 0.5D;
      double d1 = (double)p_113661_.m_123342_() + 1.3D;
      double d2 = (double)p_113661_.m_123343_() + 0.5D;
      DebugRenderer.m_113490_(p_113660_, d0, d1, d2, p_113662_, 0.04F, true, 0.0F, true);
   }

   private Camera m_113665_() {
      return this.f_113647_.f_91063_.m_109153_();
   }
}