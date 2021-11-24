package net.minecraft.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MapRenderer implements AutoCloseable {
   private static final ResourceLocation f_93253_ = new ResourceLocation("textures/map/map_icons.png");
   static final RenderType f_93254_ = RenderType.m_110497_(f_93253_);
   private static final int f_168763_ = 128;
   private static final int f_168764_ = 128;
   final TextureManager f_93255_;
   private final Int2ObjectMap<MapRenderer.MapInstance> f_93256_ = new Int2ObjectOpenHashMap<>();

   public MapRenderer(TextureManager p_93259_) {
      this.f_93255_ = p_93259_;
   }

   public void m_168765_(int p_168766_, MapItemSavedData p_168767_) {
      this.m_168778_(p_168766_, p_168767_).m_182566_();
   }

   public void m_168771_(PoseStack p_168772_, MultiBufferSource p_168773_, int p_168774_, MapItemSavedData p_168775_, boolean p_168776_, int p_168777_) {
      this.m_168778_(p_168774_, p_168775_).m_93291_(p_168772_, p_168773_, p_168776_, p_168777_);
   }

   private MapRenderer.MapInstance m_168778_(int p_168779_, MapItemSavedData p_168780_) {
      return this.f_93256_.compute(p_168779_, (p_182563_, p_182564_) -> {
         if (p_182564_ == null) {
            return new MapRenderer.MapInstance(p_182563_, p_168780_);
         } else {
            p_182564_.m_182567_(p_168780_);
            return p_182564_;
         }
      });
   }

   public void m_93260_() {
      for(MapRenderer.MapInstance maprenderer$mapinstance : this.f_93256_.values()) {
         maprenderer$mapinstance.close();
      }

      this.f_93256_.clear();
   }

   public void close() {
      this.m_93260_();
   }

   @OnlyIn(Dist.CLIENT)
   class MapInstance implements AutoCloseable {
      private MapItemSavedData f_93280_;
      private final DynamicTexture f_93281_;
      private final RenderType f_93282_;
      private boolean f_182565_ = true;

      MapInstance(int p_168783_, MapItemSavedData p_168784_) {
         this.f_93280_ = p_168784_;
         this.f_93281_ = new DynamicTexture(128, 128, true);
         ResourceLocation resourcelocation = MapRenderer.this.f_93255_.m_118490_("map/" + p_168783_, this.f_93281_);
         this.f_93282_ = RenderType.m_110497_(resourcelocation);
      }

      void m_182567_(MapItemSavedData p_182568_) {
         boolean flag = this.f_93280_ != p_182568_;
         this.f_93280_ = p_182568_;
         this.f_182565_ |= flag;
      }

      public void m_182566_() {
         this.f_182565_ = true;
      }

      private void m_93290_() {
         for(int i = 0; i < 128; ++i) {
            for(int j = 0; j < 128; ++j) {
               int k = j + i * 128;
               int l = this.f_93280_.f_77891_[k] & 255;
               if (l / 4 == 0) {
                  this.f_93281_.m_117991_().m_84988_(j, i, 0);
               } else {
                  this.f_93281_.m_117991_().m_84988_(j, i, MaterialColor.f_76387_[l / 4].m_76427_(l & 3));
               }
            }
         }

         this.f_93281_.m_117985_();
      }

      void m_93291_(PoseStack p_93292_, MultiBufferSource p_93293_, boolean p_93294_, int p_93295_) {
         if (this.f_182565_) {
            this.m_93290_();
            this.f_182565_ = false;
         }

         int i = 0;
         int j = 0;
         float f = 0.0F;
         Matrix4f matrix4f = p_93292_.m_85850_().m_85861_();
         VertexConsumer vertexconsumer = p_93293_.m_6299_(this.f_93282_);
         vertexconsumer.m_85982_(matrix4f, 0.0F, 128.0F, -0.01F).m_6122_(255, 255, 255, 255).m_7421_(0.0F, 1.0F).m_85969_(p_93295_).m_5752_();
         vertexconsumer.m_85982_(matrix4f, 128.0F, 128.0F, -0.01F).m_6122_(255, 255, 255, 255).m_7421_(1.0F, 1.0F).m_85969_(p_93295_).m_5752_();
         vertexconsumer.m_85982_(matrix4f, 128.0F, 0.0F, -0.01F).m_6122_(255, 255, 255, 255).m_7421_(1.0F, 0.0F).m_85969_(p_93295_).m_5752_();
         vertexconsumer.m_85982_(matrix4f, 0.0F, 0.0F, -0.01F).m_6122_(255, 255, 255, 255).m_7421_(0.0F, 0.0F).m_85969_(p_93295_).m_5752_();
         int k = 0;

         for(MapDecoration mapdecoration : this.f_93280_.m_164811_()) {
            if (!p_93294_ || mapdecoration.m_77809_()) {
               if (mapdecoration.render(k)) { k++; continue; }
               p_93292_.m_85836_();
               p_93292_.m_85837_((double)(0.0F + (float)mapdecoration.m_77804_() / 2.0F + 64.0F), (double)(0.0F + (float)mapdecoration.m_77805_() / 2.0F + 64.0F), (double)-0.02F);
               p_93292_.m_85845_(Vector3f.f_122227_.m_122240_((float)(mapdecoration.m_77806_() * 360) / 16.0F));
               p_93292_.m_85841_(4.0F, 4.0F, 3.0F);
               p_93292_.m_85837_(-0.125D, 0.125D, 0.0D);
               byte b0 = mapdecoration.m_77802_();
               float f1 = (float)(b0 % 16 + 0) / 16.0F;
               float f2 = (float)(b0 / 16 + 0) / 16.0F;
               float f3 = (float)(b0 % 16 + 1) / 16.0F;
               float f4 = (float)(b0 / 16 + 1) / 16.0F;
               Matrix4f matrix4f1 = p_93292_.m_85850_().m_85861_();
               float f5 = -0.001F;
               VertexConsumer vertexconsumer1 = p_93293_.m_6299_(MapRenderer.f_93254_);
               vertexconsumer1.m_85982_(matrix4f1, -1.0F, 1.0F, (float)k * -0.001F).m_6122_(255, 255, 255, 255).m_7421_(f1, f2).m_85969_(p_93295_).m_5752_();
               vertexconsumer1.m_85982_(matrix4f1, 1.0F, 1.0F, (float)k * -0.001F).m_6122_(255, 255, 255, 255).m_7421_(f3, f2).m_85969_(p_93295_).m_5752_();
               vertexconsumer1.m_85982_(matrix4f1, 1.0F, -1.0F, (float)k * -0.001F).m_6122_(255, 255, 255, 255).m_7421_(f3, f4).m_85969_(p_93295_).m_5752_();
               vertexconsumer1.m_85982_(matrix4f1, -1.0F, -1.0F, (float)k * -0.001F).m_6122_(255, 255, 255, 255).m_7421_(f1, f4).m_85969_(p_93295_).m_5752_();
               p_93292_.m_85849_();
               if (mapdecoration.m_77810_() != null) {
                  Font font = Minecraft.m_91087_().f_91062_;
                  Component component = mapdecoration.m_77810_();
                  float f6 = (float)font.m_92852_(component);
                  float f7 = Mth.m_14036_(25.0F / f6, 0.0F, 6.0F / 9.0F);
                  p_93292_.m_85836_();
                  p_93292_.m_85837_((double)(0.0F + (float)mapdecoration.m_77804_() / 2.0F + 64.0F - f6 * f7 / 2.0F), (double)(0.0F + (float)mapdecoration.m_77805_() / 2.0F + 64.0F + 4.0F), (double)-0.025F);
                  p_93292_.m_85841_(f7, f7, 1.0F);
                  p_93292_.m_85837_(0.0D, 0.0D, (double)-0.1F);
                  font.m_92841_(component, 0.0F, 0.0F, -1, false, p_93292_.m_85850_().m_85861_(), p_93293_, false, Integer.MIN_VALUE, p_93295_);
                  p_93292_.m_85849_();
               }

               ++k;
            }
         }

      }

      public void close() {
         this.f_93281_.close();
      }
   }
}
