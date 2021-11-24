package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PreloadedTexture extends SimpleTexture {
   @Nullable
   private CompletableFuture<SimpleTexture.TextureImage> f_118100_;

   public PreloadedTexture(ResourceManager p_118102_, ResourceLocation p_118103_, Executor p_118104_) {
      super(p_118103_);
      this.f_118100_ = CompletableFuture.supplyAsync(() -> {
         return SimpleTexture.TextureImage.m_118155_(p_118102_, p_118103_);
      }, p_118104_);
   }

   protected SimpleTexture.TextureImage m_6335_(ResourceManager p_118126_) {
      if (this.f_118100_ != null) {
         SimpleTexture.TextureImage simpletexture$textureimage = this.f_118100_.join();
         this.f_118100_ = null;
         return simpletexture$textureimage;
      } else {
         return SimpleTexture.TextureImage.m_118155_(p_118126_, this.f_118129_);
      }
   }

   public CompletableFuture<Void> m_118105_() {
      return this.f_118100_ == null ? CompletableFuture.completedFuture((Void)null) : this.f_118100_.thenApply((p_118110_) -> {
         return null;
      });
   }

   public void m_6479_(TextureManager p_118114_, ResourceManager p_118115_, ResourceLocation p_118116_, Executor p_118117_) {
      this.f_118100_ = CompletableFuture.supplyAsync(() -> {
         return SimpleTexture.TextureImage.m_118155_(p_118115_, this.f_118129_);
      }, Util.m_137578_());
      this.f_118100_.thenRunAsync(() -> {
         p_118114_.m_118495_(this.f_118129_, this);
      }, m_118120_(p_118117_));
   }

   private static Executor m_118120_(Executor p_118121_) {
      return (p_118124_) -> {
         p_118121_.execute(() -> {
            RenderSystem.m_69879_(p_118124_::run);
         });
      };
   }
}