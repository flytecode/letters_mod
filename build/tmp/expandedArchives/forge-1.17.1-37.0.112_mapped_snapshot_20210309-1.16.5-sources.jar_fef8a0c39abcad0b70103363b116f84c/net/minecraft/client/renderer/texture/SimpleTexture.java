package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SimpleTexture extends AbstractTexture {
   static final Logger f_118130_ = LogManager.getLogger();
   protected final ResourceLocation f_118129_;

   public SimpleTexture(ResourceLocation p_118133_) {
      this.f_118129_ = p_118133_;
   }

   public void m_6704_(ResourceManager p_118135_) throws IOException {
      SimpleTexture.TextureImage simpletexture$textureimage = this.m_6335_(p_118135_);
      simpletexture$textureimage.m_118159_();
      TextureMetadataSection texturemetadatasection = simpletexture$textureimage.m_118154_();
      boolean flag;
      boolean flag1;
      if (texturemetadatasection != null) {
         flag = texturemetadatasection.m_119115_();
         flag1 = texturemetadatasection.m_119116_();
      } else {
         flag = false;
         flag1 = false;
      }

      NativeImage nativeimage = simpletexture$textureimage.m_118158_();
      if (!RenderSystem.m_69587_()) {
         RenderSystem.m_69879_(() -> {
            this.m_118136_(nativeimage, flag, flag1);
         });
      } else {
         this.m_118136_(nativeimage, flag, flag1);
      }

   }

   private void m_118136_(NativeImage p_118137_, boolean p_118138_, boolean p_118139_) {
      TextureUtil.m_85287_(this.m_117963_(), 0, p_118137_.m_84982_(), p_118137_.m_85084_());
      p_118137_.m_85013_(0, 0, 0, 0, 0, p_118137_.m_84982_(), p_118137_.m_85084_(), p_118138_, p_118139_, false, true);
   }

   protected SimpleTexture.TextureImage m_6335_(ResourceManager p_118140_) {
      return SimpleTexture.TextureImage.m_118155_(p_118140_, this.f_118129_);
   }

   @OnlyIn(Dist.CLIENT)
   protected static class TextureImage implements Closeable {
      @Nullable
      private final TextureMetadataSection f_118146_;
      @Nullable
      private final NativeImage f_118147_;
      @Nullable
      private final IOException f_118148_;

      public TextureImage(IOException p_118153_) {
         this.f_118148_ = p_118153_;
         this.f_118146_ = null;
         this.f_118147_ = null;
      }

      public TextureImage(@Nullable TextureMetadataSection p_118150_, NativeImage p_118151_) {
         this.f_118148_ = null;
         this.f_118146_ = p_118150_;
         this.f_118147_ = p_118151_;
      }

      public static SimpleTexture.TextureImage m_118155_(ResourceManager p_118156_, ResourceLocation p_118157_) {
         try {
            Resource resource = p_118156_.m_142591_(p_118157_);

            SimpleTexture.TextureImage simpletexture$textureimage;
            try {
               NativeImage nativeimage = NativeImage.m_85058_(resource.m_6679_());
               TextureMetadataSection texturemetadatasection = null;

               try {
                  texturemetadatasection = resource.m_5507_(TextureMetadataSection.f_119108_);
               } catch (RuntimeException runtimeexception) {
                  SimpleTexture.f_118130_.warn("Failed reading metadata of: {}", p_118157_, runtimeexception);
               }

               simpletexture$textureimage = new SimpleTexture.TextureImage(texturemetadatasection, nativeimage);
            } catch (Throwable throwable1) {
               if (resource != null) {
                  try {
                     resource.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (resource != null) {
               resource.close();
            }

            return simpletexture$textureimage;
         } catch (IOException ioexception) {
            return new SimpleTexture.TextureImage(ioexception);
         }
      }

      @Nullable
      public TextureMetadataSection m_118154_() {
         return this.f_118146_;
      }

      public NativeImage m_118158_() throws IOException {
         if (this.f_118148_ != null) {
            throw this.f_118148_;
         } else {
            return this.f_118147_;
         }
      }

      public void close() {
         if (this.f_118147_ != null) {
            this.f_118147_.close();
         }

      }

      public void m_118159_() throws IOException {
         if (this.f_118148_ != null) {
            throw this.f_118148_;
         }
      }
   }
}