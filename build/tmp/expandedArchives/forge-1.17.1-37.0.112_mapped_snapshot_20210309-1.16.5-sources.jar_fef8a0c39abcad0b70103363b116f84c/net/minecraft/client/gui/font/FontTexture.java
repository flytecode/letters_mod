package net.minecraft.client.gui.font;

import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FontTexture extends AbstractTexture {
   private static final int f_169092_ = 256;
   private final ResourceLocation f_95091_;
   private final RenderType f_95092_;
   private final RenderType f_95093_;
   private final RenderType f_181373_;
   private final boolean f_95094_;
   private final FontTexture.Node f_95095_;

   public FontTexture(ResourceLocation p_95097_, boolean p_95098_) {
      this.f_95091_ = p_95097_;
      this.f_95094_ = p_95098_;
      this.f_95095_ = new FontTexture.Node(0, 0, 256, 256);
      TextureUtil.m_85292_(p_95098_ ? NativeImage.InternalGlFormat.RGBA : NativeImage.InternalGlFormat.RED, this.m_117963_(), 256, 256);
      this.f_95092_ = p_95098_ ? RenderType.m_110497_(p_95097_) : RenderType.m_173237_(p_95097_);
      this.f_95093_ = p_95098_ ? RenderType.m_110500_(p_95097_) : RenderType.m_173240_(p_95097_);
      this.f_181373_ = p_95098_ ? RenderType.m_181444_(p_95097_) : RenderType.m_181446_(p_95097_);
   }

   public void m_6704_(ResourceManager p_95101_) {
   }

   public void close() {
      this.m_117964_();
   }

   @Nullable
   public BakedGlyph m_95102_(RawGlyph p_95103_) {
      if (p_95103_.m_5633_() != this.f_95094_) {
         return null;
      } else {
         FontTexture.Node fonttexture$node = this.f_95095_.m_95123_(p_95103_);
         if (fonttexture$node != null) {
            this.m_117966_();
            p_95103_.m_6238_(fonttexture$node.f_95105_, fonttexture$node.f_95106_);
            float f = 256.0F;
            float f1 = 256.0F;
            float f2 = 0.01F;
            return new BakedGlyph(this.f_95092_, this.f_95093_, this.f_181373_, ((float)fonttexture$node.f_95105_ + 0.01F) / 256.0F, ((float)fonttexture$node.f_95105_ - 0.01F + (float)p_95103_.m_5631_()) / 256.0F, ((float)fonttexture$node.f_95106_ + 0.01F) / 256.0F, ((float)fonttexture$node.f_95106_ - 0.01F + (float)p_95103_.m_5629_()) / 256.0F, p_95103_.m_83833_(), p_95103_.m_83834_(), p_95103_.m_83835_(), p_95103_.m_83836_());
         } else {
            return null;
         }
      }
   }

   public ResourceLocation m_95099_() {
      return this.f_95091_;
   }

   @OnlyIn(Dist.CLIENT)
   static class Node {
      final int f_95105_;
      final int f_95106_;
      private final int f_95107_;
      private final int f_95108_;
      private FontTexture.Node f_95109_;
      private FontTexture.Node f_95110_;
      private boolean f_95111_;

      Node(int p_95113_, int p_95114_, int p_95115_, int p_95116_) {
         this.f_95105_ = p_95113_;
         this.f_95106_ = p_95114_;
         this.f_95107_ = p_95115_;
         this.f_95108_ = p_95116_;
      }

      @Nullable
      FontTexture.Node m_95123_(RawGlyph p_95124_) {
         if (this.f_95109_ != null && this.f_95110_ != null) {
            FontTexture.Node fonttexture$node = this.f_95109_.m_95123_(p_95124_);
            if (fonttexture$node == null) {
               fonttexture$node = this.f_95110_.m_95123_(p_95124_);
            }

            return fonttexture$node;
         } else if (this.f_95111_) {
            return null;
         } else {
            int i = p_95124_.m_5631_();
            int j = p_95124_.m_5629_();
            if (i <= this.f_95107_ && j <= this.f_95108_) {
               if (i == this.f_95107_ && j == this.f_95108_) {
                  this.f_95111_ = true;
                  return this;
               } else {
                  int k = this.f_95107_ - i;
                  int l = this.f_95108_ - j;
                  if (k > l) {
                     this.f_95109_ = new FontTexture.Node(this.f_95105_, this.f_95106_, i, this.f_95108_);
                     this.f_95110_ = new FontTexture.Node(this.f_95105_ + i + 1, this.f_95106_, this.f_95107_ - i - 1, this.f_95108_);
                  } else {
                     this.f_95109_ = new FontTexture.Node(this.f_95105_, this.f_95106_, this.f_95107_, j);
                     this.f_95110_ = new FontTexture.Node(this.f_95105_, this.f_95106_ + j + 1, this.f_95107_, this.f_95108_ - j - 1);
                  }

                  return this.f_95109_.m_95123_(p_95124_);
               }
            } else {
               return null;
            }
         }
      }
   }
}