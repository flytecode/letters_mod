package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorldGenAttemptRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final List<BlockPos> f_113724_ = Lists.newArrayList();
   private final List<Float> f_113725_ = Lists.newArrayList();
   private final List<Float> f_113726_ = Lists.newArrayList();
   private final List<Float> f_113727_ = Lists.newArrayList();
   private final List<Float> f_113728_ = Lists.newArrayList();
   private final List<Float> f_113729_ = Lists.newArrayList();

   public void m_113737_(BlockPos p_113738_, float p_113739_, float p_113740_, float p_113741_, float p_113742_, float p_113743_) {
      this.f_113724_.add(p_113738_);
      this.f_113725_.add(p_113739_);
      this.f_113726_.add(p_113743_);
      this.f_113727_.add(p_113740_);
      this.f_113728_.add(p_113741_);
      this.f_113729_.add(p_113742_);
   }

   public void m_7790_(PoseStack p_113732_, MultiBufferSource p_113733_, double p_113734_, double p_113735_, double p_113736_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.f_85815_);

      for(int i = 0; i < this.f_113724_.size(); ++i) {
         BlockPos blockpos = this.f_113724_.get(i);
         Float f = this.f_113725_.get(i);
         float f1 = f / 2.0F;
         LevelRenderer.m_109556_(bufferbuilder, (double)((float)blockpos.m_123341_() + 0.5F - f1) - p_113734_, (double)((float)blockpos.m_123342_() + 0.5F - f1) - p_113735_, (double)((float)blockpos.m_123343_() + 0.5F - f1) - p_113736_, (double)((float)blockpos.m_123341_() + 0.5F + f1) - p_113734_, (double)((float)blockpos.m_123342_() + 0.5F + f1) - p_113735_, (double)((float)blockpos.m_123343_() + 0.5F + f1) - p_113736_, this.f_113727_.get(i), this.f_113728_.get(i), this.f_113729_.get(i), this.f_113726_.get(i));
      }

      tesselator.m_85914_();
      RenderSystem.m_69493_();
   }
}