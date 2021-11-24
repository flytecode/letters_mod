package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkinManager {
   public static final String f_174841_ = "textures";
   private final TextureManager f_118807_;
   private final File f_118808_;
   private final MinecraftSessionService f_118809_;
   private final LoadingCache<String, Map<Type, MinecraftProfileTexture>> f_118810_;

   public SkinManager(TextureManager p_118812_, File p_118813_, final MinecraftSessionService p_118814_) {
      this.f_118807_ = p_118812_;
      this.f_118808_ = p_118813_;
      this.f_118809_ = p_118814_;
      this.f_118810_ = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<String, Map<Type, MinecraftProfileTexture>>() {
         public Map<Type, MinecraftProfileTexture> load(String p_118853_) {
            GameProfile gameprofile = new GameProfile((UUID)null, "dummy_mcdummyface");
            gameprofile.getProperties().put("textures", new Property("textures", p_118853_, ""));

            try {
               return p_118814_.getTextures(gameprofile, false);
            } catch (Throwable throwable) {
               return ImmutableMap.of();
            }
         }
      });
   }

   public ResourceLocation m_118825_(MinecraftProfileTexture p_118826_, Type p_118827_) {
      return this.m_118828_(p_118826_, p_118827_, (SkinManager.SkinTextureCallback)null);
   }

   private ResourceLocation m_118828_(MinecraftProfileTexture p_118829_, Type p_118830_, @Nullable SkinManager.SkinTextureCallback p_118831_) {
      String s = Hashing.sha1().hashUnencodedChars(p_118829_.getHash()).toString();
      ResourceLocation resourcelocation = new ResourceLocation("skins/" + s);
      AbstractTexture abstracttexture = this.f_118807_.m_174786_(resourcelocation, MissingTextureAtlasSprite.m_118080_());
      if (abstracttexture == MissingTextureAtlasSprite.m_118080_()) {
         File file1 = new File(this.f_118808_, s.length() > 2 ? s.substring(0, 2) : "xx");
         File file2 = new File(file1, s);
         HttpTexture httptexture = new HttpTexture(file2, p_118829_.getUrl(), DefaultPlayerSkin.m_118626_(), p_118830_ == Type.SKIN, () -> {
            if (p_118831_ != null) {
               p_118831_.m_118856_(p_118830_, resourcelocation, p_118829_);
            }

         });
         this.f_118807_.m_118495_(resourcelocation, httptexture);
      } else if (p_118831_ != null) {
         p_118831_.m_118856_(p_118830_, resourcelocation, p_118829_);
      }

      return resourcelocation;
   }

   public void m_118817_(GameProfile p_118818_, SkinManager.SkinTextureCallback p_118819_, boolean p_118820_) {
      Runnable runnable = () -> {
         Map<Type, MinecraftProfileTexture> map = Maps.newHashMap();

         try {
            map.putAll(this.f_118809_.getTextures(p_118818_, p_118820_));
         } catch (InsecureTextureException insecuretextureexception1) {
         }

         if (map.isEmpty()) {
            p_118818_.getProperties().clear();
            if (p_118818_.getId().equals(Minecraft.m_91087_().m_91094_().m_92548_().getId())) {
               p_118818_.getProperties().putAll(Minecraft.m_91087_().m_91095_());
               map.putAll(this.f_118809_.getTextures(p_118818_, false));
            } else {
               this.f_118809_.fillProfileProperties(p_118818_, p_118820_);

               try {
                  map.putAll(this.f_118809_.getTextures(p_118818_, p_118820_));
               } catch (InsecureTextureException insecuretextureexception) {
               }
            }
         }

         Minecraft.m_91087_().execute(() -> {
            RenderSystem.m_69879_(() -> {
               ImmutableList.of(Type.SKIN, Type.CAPE).forEach((p_174848_) -> {
                  if (map.containsKey(p_174848_)) {
                     this.m_118828_(map.get(p_174848_), p_174848_, p_118819_);
                  }

               });
            });
         });
      };
      Util.m_137578_().execute(runnable);
   }

   public Map<Type, MinecraftProfileTexture> m_118815_(GameProfile p_118816_) {
      Property property = Iterables.getFirst(p_118816_.getProperties().get("textures"), (Property)null);
      return (Map<Type, MinecraftProfileTexture>)(property == null ? ImmutableMap.of() : this.f_118810_.getUnchecked(property.getValue()));
   }

   @OnlyIn(Dist.CLIENT)
   public interface SkinTextureCallback {
      void m_118856_(Type p_118857_, ResourceLocation p_118858_, MinecraftProfileTexture p_118859_);
   }
}