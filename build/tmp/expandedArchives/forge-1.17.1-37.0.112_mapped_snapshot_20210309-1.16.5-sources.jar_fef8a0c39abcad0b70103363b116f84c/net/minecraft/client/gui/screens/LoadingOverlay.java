package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LoadingOverlay extends Overlay {
   static final ResourceLocation f_96160_ = new ResourceLocation("textures/gui/title/mojangstudios.png");
   private static final int f_169316_ = FastColor.ARGB32.m_13660_(255, 239, 50, 61);
   private static final int f_169317_ = FastColor.ARGB32.m_13660_(255, 0, 0, 0);
   private static final IntSupplier f_96161_ = () -> {
      return Minecraft.m_91087_().f_91066_.f_168413_ ? f_169317_ : f_169316_;
   };
   private static final int f_169318_ = 240;
   private static final float f_169319_ = 60.0F;
   private static final int f_169320_ = 60;
   private static final int f_169321_ = 120;
   private static final float f_169322_ = 0.0625F;
   private static final float f_169323_ = 0.95F;
   public static final long f_169314_ = 1000L;
   public static final long f_169315_ = 500L;
   private final Minecraft f_96163_;
   private final ReloadInstance f_96164_;
   private final Consumer<Optional<Throwable>> f_96165_;
   private final boolean f_96166_;
   private float f_96167_;
   private long f_96168_ = -1L;
   private long f_96169_ = -1L;

   public LoadingOverlay(Minecraft p_96172_, ReloadInstance p_96173_, Consumer<Optional<Throwable>> p_96174_, boolean p_96175_) {
      this.f_96163_ = p_96172_;
      this.f_96164_ = p_96173_;
      this.f_96165_ = p_96174_;
      this.f_96166_ = p_96175_;
   }

   public static void m_96189_(Minecraft p_96190_) {
      p_96190_.m_91097_().m_118495_(f_96160_, new LoadingOverlay.LogoTexture());
   }

   private static int m_169324_(int p_169325_, int p_169326_) {
      return p_169325_ & 16777215 | p_169326_ << 24;
   }

   public void m_6305_(PoseStack p_96178_, int p_96179_, int p_96180_, float p_96181_) {
      int i = this.f_96163_.m_91268_().m_85445_();
      int j = this.f_96163_.m_91268_().m_85446_();
      long k = Util.m_137550_();
      if (this.f_96166_ && this.f_96169_ == -1L) {
         this.f_96169_ = k;
      }

      float f = this.f_96168_ > -1L ? (float)(k - this.f_96168_) / 1000.0F : -1.0F;
      float f1 = this.f_96169_ > -1L ? (float)(k - this.f_96169_) / 500.0F : -1.0F;
      float f2;
      if (f >= 1.0F) {
         if (this.f_96163_.f_91080_ != null) {
            this.f_96163_.f_91080_.m_6305_(p_96178_, 0, 0, p_96181_);
         }

         int l = Mth.m_14167_((1.0F - Mth.m_14036_(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         m_93172_(p_96178_, 0, 0, i, j, m_169324_(f_96161_.getAsInt(), l));
         f2 = 1.0F - Mth.m_14036_(f - 1.0F, 0.0F, 1.0F);
      } else if (this.f_96166_) {
         if (this.f_96163_.f_91080_ != null && f1 < 1.0F) {
            this.f_96163_.f_91080_.m_6305_(p_96178_, p_96179_, p_96180_, p_96181_);
         }

         int l1 = Mth.m_14165_(Mth.m_14008_((double)f1, 0.15D, 1.0D) * 255.0D);
         m_93172_(p_96178_, 0, 0, i, j, m_169324_(f_96161_.getAsInt(), l1));
         f2 = Mth.m_14036_(f1, 0.0F, 1.0F);
      } else {
         int i2 = f_96161_.getAsInt();
         float f3 = (float)(i2 >> 16 & 255) / 255.0F;
         float f4 = (float)(i2 >> 8 & 255) / 255.0F;
         float f5 = (float)(i2 & 255) / 255.0F;
         GlStateManager.m_84318_(f3, f4, f5, 1.0F);
         GlStateManager.m_84266_(16384, Minecraft.f_91002_);
         f2 = 1.0F;
      }

      int j2 = (int)((double)this.f_96163_.m_91268_().m_85445_() * 0.5D);
      int k2 = (int)((double)this.f_96163_.m_91268_().m_85446_() * 0.5D);
      double d1 = Math.min((double)this.f_96163_.m_91268_().m_85445_() * 0.75D, (double)this.f_96163_.m_91268_().m_85446_()) * 0.25D;
      int i1 = (int)(d1 * 0.5D);
      double d0 = d1 * 4.0D;
      int j1 = (int)(d0 * 0.5D);
      RenderSystem.m_157456_(0, f_96160_);
      RenderSystem.m_69478_();
      RenderSystem.m_69403_(32774);
      RenderSystem.m_69405_(770, 1);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, f2);
      m_93160_(p_96178_, j2 - j1, k2 - i1, j1, (int)d1, -0.0625F, 0.0F, 120, 60, 120, 120);
      m_93160_(p_96178_, j2, k2 - i1, j1, (int)d1, 0.0625F, 60.0F, 120, 60, 120, 120);
      RenderSystem.m_69453_();
      RenderSystem.m_69461_();
      int k1 = (int)((double)this.f_96163_.m_91268_().m_85446_() * 0.8325D);
      float f6 = this.f_96164_.m_7750_();
      this.f_96167_ = Mth.m_14036_(this.f_96167_ * 0.95F + f6 * 0.050000012F, 0.0F, 1.0F);
      net.minecraftforge.fmlclient.ClientModLoader.renderProgressText();
      if (f < 1.0F) {
         this.m_96182_(p_96178_, i / 2 - j1, k1 - 5, i / 2 + j1, k1 + 5, 1.0F - Mth.m_14036_(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.f_96163_.m_91150_((Overlay)null);
      }

      if (this.f_96168_ == -1L && this.f_96164_.m_7746_() && (!this.f_96166_ || f1 >= 2.0F)) {
         this.f_96168_ = Util.m_137550_(); // Moved up to guard against inf loops caused by callback
         try {
            this.f_96164_.m_7748_();
            this.f_96165_.accept(Optional.empty());
         } catch (Throwable throwable) {
            this.f_96165_.accept(Optional.of(throwable));
         }

         if (this.f_96163_.f_91080_ != null) {
            this.f_96163_.f_91080_.m_6575_(this.f_96163_, this.f_96163_.m_91268_().m_85445_(), this.f_96163_.m_91268_().m_85446_());
         }
      }

   }

   private void m_96182_(PoseStack p_96183_, int p_96184_, int p_96185_, int p_96186_, int p_96187_, float p_96188_) {
      int i = Mth.m_14167_((float)(p_96186_ - p_96184_ - 2) * this.f_96167_);
      int j = Math.round(p_96188_ * 255.0F);
      int k = FastColor.ARGB32.m_13660_(j, 255, 255, 255);
      m_93172_(p_96183_, p_96184_ + 2, p_96185_ + 2, p_96184_ + i, p_96187_ - 2, k);
      m_93172_(p_96183_, p_96184_ + 1, p_96185_, p_96186_ - 1, p_96185_ + 1, k);
      m_93172_(p_96183_, p_96184_ + 1, p_96187_, p_96186_ - 1, p_96187_ - 1, k);
      m_93172_(p_96183_, p_96184_, p_96185_, p_96184_ + 1, p_96187_, k);
      m_93172_(p_96183_, p_96186_, p_96185_, p_96186_ - 1, p_96187_, k);
   }

   public boolean m_7859_() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   static class LogoTexture extends SimpleTexture {
      public LogoTexture() {
         super(LoadingOverlay.f_96160_);
      }

      protected SimpleTexture.TextureImage m_6335_(ResourceManager p_96194_) {
         Minecraft minecraft = Minecraft.m_91087_();
         VanillaPackResources vanillapackresources = minecraft.m_91100_().m_118555_();

         try {
            InputStream inputstream = vanillapackresources.m_8031_(PackType.CLIENT_RESOURCES, LoadingOverlay.f_96160_);

            SimpleTexture.TextureImage simpletexture$textureimage;
            try {
               simpletexture$textureimage = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.m_85058_(inputstream));
            } catch (Throwable throwable1) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            return simpletexture$textureimage;
         } catch (IOException ioexception) {
            return new SimpleTexture.TextureImage(ioexception);
         }
      }
   }
}
