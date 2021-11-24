package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Locale;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PathfindingRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Map<Integer, Path> f_113607_ = Maps.newHashMap();
   private final Map<Integer, Float> f_113608_ = Maps.newHashMap();
   private final Map<Integer, Long> f_113609_ = Maps.newHashMap();
   private static final long f_173893_ = 5000L;
   private static final float f_173894_ = 80.0F;
   private static final boolean f_173895_ = true;
   private static final boolean f_173896_ = false;
   private static final boolean f_173897_ = false;
   private static final boolean f_173898_ = true;
   private static final boolean f_173899_ = true;
   private static final float f_173900_ = 0.02F;

   public void m_113611_(int p_113612_, Path p_113613_, float p_113614_) {
      this.f_113607_.put(p_113612_, p_113613_);
      this.f_113609_.put(p_113612_, Util.m_137550_());
      this.f_113608_.put(p_113612_, p_113614_);
   }

   public void m_7790_(PoseStack p_113629_, MultiBufferSource p_113630_, double p_113631_, double p_113632_, double p_113633_) {
      if (!this.f_113607_.isEmpty()) {
         long i = Util.m_137550_();

         for(Integer integer : this.f_113607_.keySet()) {
            Path path = this.f_113607_.get(integer);
            float f = this.f_113608_.get(integer);
            m_113620_(path, f, true, true, p_113631_, p_113632_, p_113633_);
         }

         for(Integer integer1 : this.f_113609_.keySet().toArray(new Integer[0])) {
            if (i - this.f_113609_.get(integer1) > 5000L) {
               this.f_113607_.remove(integer1);
               this.f_113609_.remove(integer1);
            }
         }

      }
   }

   public static void m_113620_(Path p_113621_, float p_113622_, boolean p_113623_, boolean p_113624_, double p_113625_, double p_113626_, double p_113627_) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157429_(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.m_69472_();
      RenderSystem.m_69832_(6.0F);
      m_113639_(p_113621_, p_113622_, p_113623_, p_113624_, p_113625_, p_113626_, p_113627_);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   private static void m_113639_(Path p_113640_, float p_113641_, boolean p_113642_, boolean p_113643_, double p_113644_, double p_113645_, double p_113646_) {
      m_113615_(p_113640_, p_113644_, p_113645_, p_113646_);
      BlockPos blockpos = p_113640_.m_77406_();
      if (m_113634_(blockpos, p_113644_, p_113645_, p_113646_) <= 80.0F) {
         DebugRenderer.m_113451_((new AABB((double)((float)blockpos.m_123341_() + 0.25F), (double)((float)blockpos.m_123342_() + 0.25F), (double)blockpos.m_123343_() + 0.25D, (double)((float)blockpos.m_123341_() + 0.75F), (double)((float)blockpos.m_123342_() + 0.75F), (double)((float)blockpos.m_123343_() + 0.75F))).m_82386_(-p_113644_, -p_113645_, -p_113646_), 0.0F, 1.0F, 0.0F, 0.5F);

         for(int i = 0; i < p_113640_.m_77398_(); ++i) {
            Node node = p_113640_.m_77375_(i);
            if (m_113634_(node.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0F) {
               float f = i == p_113640_.m_77399_() ? 1.0F : 0.0F;
               float f1 = i == p_113640_.m_77399_() ? 0.0F : 1.0F;
               DebugRenderer.m_113451_((new AABB((double)((float)node.f_77271_ + 0.5F - p_113641_), (double)((float)node.f_77272_ + 0.01F * (float)i), (double)((float)node.f_77273_ + 0.5F - p_113641_), (double)((float)node.f_77271_ + 0.5F + p_113641_), (double)((float)node.f_77272_ + 0.25F + 0.01F * (float)i), (double)((float)node.f_77273_ + 0.5F + p_113641_))).m_82386_(-p_113644_, -p_113645_, -p_113646_), f, 0.0F, f1, 0.5F);
            }
         }
      }

      if (p_113642_) {
         for(Node node2 : p_113640_.m_77405_()) {
            if (m_113634_(node2.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0F) {
               DebugRenderer.m_113451_((new AABB((double)((float)node2.f_77271_ + 0.5F - p_113641_ / 2.0F), (double)((float)node2.f_77272_ + 0.01F), (double)((float)node2.f_77273_ + 0.5F - p_113641_ / 2.0F), (double)((float)node2.f_77271_ + 0.5F + p_113641_ / 2.0F), (double)node2.f_77272_ + 0.1D, (double)((float)node2.f_77273_ + 0.5F + p_113641_ / 2.0F))).m_82386_(-p_113644_, -p_113645_, -p_113646_), 1.0F, 0.8F, 0.8F, 0.5F);
            }
         }

         for(Node node3 : p_113640_.m_77404_()) {
            if (m_113634_(node3.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0F) {
               DebugRenderer.m_113451_((new AABB((double)((float)node3.f_77271_ + 0.5F - p_113641_ / 2.0F), (double)((float)node3.f_77272_ + 0.01F), (double)((float)node3.f_77273_ + 0.5F - p_113641_ / 2.0F), (double)((float)node3.f_77271_ + 0.5F + p_113641_ / 2.0F), (double)node3.f_77272_ + 0.1D, (double)((float)node3.f_77273_ + 0.5F + p_113641_ / 2.0F))).m_82386_(-p_113644_, -p_113645_, -p_113646_), 0.8F, 1.0F, 1.0F, 0.5F);
            }
         }
      }

      if (p_113643_) {
         for(int j = 0; j < p_113640_.m_77398_(); ++j) {
            Node node1 = p_113640_.m_77375_(j);
            if (m_113634_(node1.m_77288_(), p_113644_, p_113645_, p_113646_) <= 80.0F) {
               DebugRenderer.m_113490_(String.format("%s", node1.f_77282_), (double)node1.f_77271_ + 0.5D, (double)node1.f_77272_ + 0.75D, (double)node1.f_77273_ + 0.5D, -1, 0.02F, true, 0.0F, true);
               DebugRenderer.m_113490_(String.format(Locale.ROOT, "%.2f", node1.f_77281_), (double)node1.f_77271_ + 0.5D, (double)node1.f_77272_ + 0.25D, (double)node1.f_77273_ + 0.5D, -1, 0.02F, true, 0.0F, true);
            }
         }
      }

   }

   public static void m_113615_(Path p_113616_, double p_113617_, double p_113618_, double p_113619_) {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      bufferbuilder.m_166779_(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.f_85815_);

      for(int i = 0; i < p_113616_.m_77398_(); ++i) {
         Node node = p_113616_.m_77375_(i);
         if (!(m_113634_(node.m_77288_(), p_113617_, p_113618_, p_113619_) > 80.0F)) {
            float f = (float)i / (float)p_113616_.m_77398_() * 0.33F;
            int j = i == 0 ? 0 : Mth.m_14169_(f, 0.9F, 0.9F);
            int k = j >> 16 & 255;
            int l = j >> 8 & 255;
            int i1 = j & 255;
            bufferbuilder.m_5483_((double)node.f_77271_ - p_113617_ + 0.5D, (double)node.f_77272_ - p_113618_ + 0.5D, (double)node.f_77273_ - p_113619_ + 0.5D).m_6122_(k, l, i1, 255).m_5752_();
         }
      }

      tesselator.m_85914_();
   }

   private static float m_113634_(BlockPos p_113635_, double p_113636_, double p_113637_, double p_113638_) {
      return (float)(Math.abs((double)p_113635_.m_123341_() - p_113636_) + Math.abs((double)p_113635_.m_123342_() - p_113637_) + Math.abs((double)p_113635_.m_123343_() - p_113638_));
   }
}