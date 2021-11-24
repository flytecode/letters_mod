package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113583_;
   private static final int f_173892_ = 10;

   public LightDebugRenderer(Minecraft p_113585_) {
      this.f_113583_ = p_113585_;
   }

   public void m_7790_(PoseStack p_113587_, MultiBufferSource p_113588_, double p_113589_, double p_113590_, double p_113591_) {
      Level level = this.f_113583_.f_91073_;
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69472_();
      BlockPos blockpos = new BlockPos(p_113589_, p_113590_, p_113591_);
      LongSet longset = new LongOpenHashSet();

      for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-10, -10, -10), blockpos.m_142082_(10, 10, 10))) {
         int i = level.m_45517_(LightLayer.SKY, blockpos1);
         float f = (float)(15 - i) / 15.0F * 0.5F + 0.16F;
         int j = Mth.m_14169_(f, 0.9F, 0.9F);
         long k = SectionPos.m_123235_(blockpos1.m_121878_());
         if (longset.add(k)) {
            DebugRenderer.m_113483_(level.m_7726_().m_7827_().m_75816_(LightLayer.SKY, SectionPos.m_123184_(k)), (double)SectionPos.m_175554_(SectionPos.m_123213_(k), 8), (double)SectionPos.m_175554_(SectionPos.m_123225_(k), 8), (double)SectionPos.m_175554_(SectionPos.m_123230_(k), 8), 16711680, 0.3F);
         }

         if (i != 15) {
            DebugRenderer.m_113477_(String.valueOf(i), (double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.25D, (double)blockpos1.m_123343_() + 0.5D, j);
         }
      }

      RenderSystem.m_69493_();
   }
}