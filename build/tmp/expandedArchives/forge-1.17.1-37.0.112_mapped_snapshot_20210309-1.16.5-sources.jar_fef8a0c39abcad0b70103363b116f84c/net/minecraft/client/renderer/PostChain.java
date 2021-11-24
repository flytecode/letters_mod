package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;

@OnlyIn(Dist.CLIENT)
public class PostChain implements AutoCloseable {
   private static final String f_173045_ = "minecraft:main";
   private final RenderTarget f_110006_;
   private final ResourceManager f_110007_;
   private final String f_110008_;
   private final List<PostPass> f_110009_ = Lists.newArrayList();
   private final Map<String, RenderTarget> f_110010_ = Maps.newHashMap();
   private final List<RenderTarget> f_110011_ = Lists.newArrayList();
   private Matrix4f f_110012_;
   private int f_110013_;
   private int f_110014_;
   private float f_110015_;
   private float f_110016_;

   public PostChain(TextureManager p_110018_, ResourceManager p_110019_, RenderTarget p_110020_, ResourceLocation p_110021_) throws IOException, JsonSyntaxException {
      this.f_110007_ = p_110019_;
      this.f_110006_ = p_110020_;
      this.f_110015_ = 0.0F;
      this.f_110016_ = 0.0F;
      this.f_110013_ = p_110020_.f_83917_;
      this.f_110014_ = p_110020_.f_83918_;
      this.f_110008_ = p_110021_.toString();
      this.m_110046_();
      this.m_110033_(p_110018_, p_110021_);
   }

   private void m_110033_(TextureManager p_110034_, ResourceLocation p_110035_) throws IOException, JsonSyntaxException {
      Resource resource = null;

      try {
         resource = this.f_110007_.m_142591_(p_110035_);
         JsonObject jsonobject = GsonHelper.m_13859_(new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8));
         if (GsonHelper.m_13885_(jsonobject, "targets")) {
            JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  this.m_110028_(jsonelement);
               } catch (Exception exception1) {
                  ChainedJsonException chainedjsonexception1 = ChainedJsonException.m_135906_(exception1);
                  chainedjsonexception1.m_135908_("targets[" + i + "]");
                  throw chainedjsonexception1;
               }

               ++i;
            }
         }

         if (GsonHelper.m_13885_(jsonobject, "passes")) {
            JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
            int j = 0;

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.m_110030_(p_110034_, jsonelement1);
               } catch (Exception exception) {
                  ChainedJsonException chainedjsonexception2 = ChainedJsonException.m_135906_(exception);
                  chainedjsonexception2.m_135908_("passes[" + j + "]");
                  throw chainedjsonexception2;
               }

               ++j;
            }
         }
      } catch (Exception exception2) {
         String s;
         if (resource != null) {
            s = " (" + resource.m_7816_() + ")";
         } else {
            s = "";
         }

         ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(exception2);
         chainedjsonexception.m_135910_(p_110035_.m_135815_() + s);
         throw chainedjsonexception;
      } finally {
         IOUtils.closeQuietly((Closeable)resource);
      }

   }

   private void m_110028_(JsonElement p_110029_) throws ChainedJsonException {
      if (GsonHelper.m_13803_(p_110029_)) {
         this.m_110038_(p_110029_.getAsString(), this.f_110013_, this.f_110014_);
      } else {
         JsonObject jsonobject = GsonHelper.m_13918_(p_110029_, "target");
         String s = GsonHelper.m_13906_(jsonobject, "name");
         int i = GsonHelper.m_13824_(jsonobject, "width", this.f_110013_);
         int j = GsonHelper.m_13824_(jsonobject, "height", this.f_110014_);
         if (this.f_110010_.containsKey(s)) {
            throw new ChainedJsonException(s + " is already defined");
         }

         this.m_110038_(s, i, j);
      }

   }

   private void m_110030_(TextureManager p_110031_, JsonElement p_110032_) throws IOException {
      JsonObject jsonobject = GsonHelper.m_13918_(p_110032_, "pass");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      String s1 = GsonHelper.m_13906_(jsonobject, "intarget");
      String s2 = GsonHelper.m_13906_(jsonobject, "outtarget");
      RenderTarget rendertarget = this.m_110049_(s1);
      RenderTarget rendertarget1 = this.m_110049_(s2);
      if (rendertarget == null) {
         throw new ChainedJsonException("Input target '" + s1 + "' does not exist");
      } else if (rendertarget1 == null) {
         throw new ChainedJsonException("Output target '" + s2 + "' does not exist");
      } else {
         PostPass postpass = this.m_110042_(s, rendertarget, rendertarget1);
         JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "auxtargets", (JsonArray)null);
         if (jsonarray != null) {
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  JsonObject jsonobject1 = GsonHelper.m_13918_(jsonelement, "auxtarget");
                  String s5 = GsonHelper.m_13906_(jsonobject1, "name");
                  String s3 = GsonHelper.m_13906_(jsonobject1, "id");
                  boolean flag;
                  String s4;
                  if (s3.endsWith(":depth")) {
                     flag = true;
                     s4 = s3.substring(0, s3.lastIndexOf(58));
                  } else {
                     flag = false;
                     s4 = s3;
                  }

                  RenderTarget rendertarget2 = this.m_110049_(s4);
                  if (rendertarget2 == null) {
                     if (flag) {
                        throw new ChainedJsonException("Render target '" + s4 + "' can't be used as depth buffer");
                     }

                     ResourceLocation rl = ResourceLocation.m_135820_(s4);
                     ResourceLocation resourcelocation = new ResourceLocation(rl.m_135827_(), "textures/effect/" + rl.m_135815_() + ".png");
                     Resource resource = null;

                     try {
                        resource = this.f_110007_.m_142591_(resourcelocation);
                     } catch (FileNotFoundException filenotfoundexception) {
                        throw new ChainedJsonException("Render target or texture '" + s4 + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly((Closeable)resource);
                     }

                     RenderSystem.m_157456_(0, resourcelocation);
                     p_110031_.m_174784_(resourcelocation);
                     AbstractTexture abstracttexture = p_110031_.m_118506_(resourcelocation);
                     int j = GsonHelper.m_13927_(jsonobject1, "width");
                     int k = GsonHelper.m_13927_(jsonobject1, "height");
                     boolean flag1 = GsonHelper.m_13912_(jsonobject1, "bilinear");
                     if (flag1) {
                        RenderSystem.m_69937_(3553, 10241, 9729);
                        RenderSystem.m_69937_(3553, 10240, 9729);
                     } else {
                        RenderSystem.m_69937_(3553, 10241, 9728);
                        RenderSystem.m_69937_(3553, 10240, 9728);
                     }

                     postpass.m_110069_(s5, abstracttexture::m_117963_, j, k);
                  } else if (flag) {
                     postpass.m_110069_(s5, rendertarget2::m_83980_, rendertarget2.f_83915_, rendertarget2.f_83916_);
                  } else {
                     postpass.m_110069_(s5, rendertarget2::m_83975_, rendertarget2.f_83915_, rendertarget2.f_83916_);
                  }
               } catch (Exception exception1) {
                  ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(exception1);
                  chainedjsonexception.m_135908_("auxtargets[" + i + "]");
                  throw chainedjsonexception;
               }

               ++i;
            }
         }

         JsonArray jsonarray1 = GsonHelper.m_13832_(jsonobject, "uniforms", (JsonArray)null);
         if (jsonarray1 != null) {
            int l = 0;

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.m_110047_(jsonelement1);
               } catch (Exception exception) {
                  ChainedJsonException chainedjsonexception1 = ChainedJsonException.m_135906_(exception);
                  chainedjsonexception1.m_135908_("uniforms[" + l + "]");
                  throw chainedjsonexception1;
               }

               ++l;
            }
         }

      }
   }

   private void m_110047_(JsonElement p_110048_) throws ChainedJsonException {
      JsonObject jsonobject = GsonHelper.m_13918_(p_110048_, "uniform");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      Uniform uniform = this.f_110009_.get(this.f_110009_.size() - 1).m_110074_().m_108952_(s);
      if (uniform == null) {
         throw new ChainedJsonException("Uniform '" + s + "' does not exist");
      } else {
         float[] afloat = new float[4];
         int i = 0;

         for(JsonElement jsonelement : GsonHelper.m_13933_(jsonobject, "values")) {
            try {
               afloat[i] = GsonHelper.m_13888_(jsonelement, "value");
            } catch (Exception exception) {
               ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(exception);
               chainedjsonexception.m_135908_("values[" + i + "]");
               throw chainedjsonexception;
            }

            ++i;
         }

         switch(i) {
         case 0:
         default:
            break;
         case 1:
            uniform.m_5985_(afloat[0]);
            break;
         case 2:
            uniform.m_7971_(afloat[0], afloat[1]);
            break;
         case 3:
            uniform.m_5889_(afloat[0], afloat[1], afloat[2]);
            break;
         case 4:
            uniform.m_5805_(afloat[0], afloat[1], afloat[2], afloat[3]);
         }

      }
   }

   public RenderTarget m_110036_(String p_110037_) {
      return this.f_110010_.get(p_110037_);
   }

   public void m_110038_(String p_110039_, int p_110040_, int p_110041_) {
      RenderTarget rendertarget = new TextureTarget(p_110040_, p_110041_, true, Minecraft.f_91002_);
      rendertarget.m_83931_(0.0F, 0.0F, 0.0F, 0.0F);
      if (f_110006_.isStencilEnabled()) { rendertarget.enableStencil(); }
      this.f_110010_.put(p_110039_, rendertarget);
      if (p_110040_ == this.f_110013_ && p_110041_ == this.f_110014_) {
         this.f_110011_.add(rendertarget);
      }

   }

   public void close() {
      for(RenderTarget rendertarget : this.f_110010_.values()) {
         rendertarget.m_83930_();
      }

      for(PostPass postpass : this.f_110009_) {
         postpass.close();
      }

      this.f_110009_.clear();
   }

   public PostPass m_110042_(String p_110043_, RenderTarget p_110044_, RenderTarget p_110045_) throws IOException {
      PostPass postpass = new PostPass(this.f_110007_, p_110043_, p_110044_, p_110045_);
      this.f_110009_.add(this.f_110009_.size(), postpass);
      return postpass;
   }

   private void m_110046_() {
      this.f_110012_ = Matrix4f.m_162203_(0.0F, (float)this.f_110006_.f_83915_, (float)this.f_110006_.f_83916_, 0.0F, 0.1F, 1000.0F);
   }

   public void m_110025_(int p_110026_, int p_110027_) {
      this.f_110013_ = this.f_110006_.f_83915_;
      this.f_110014_ = this.f_110006_.f_83916_;
      this.m_110046_();

      for(PostPass postpass : this.f_110009_) {
         postpass.m_110067_(this.f_110012_);
      }

      for(RenderTarget rendertarget : this.f_110011_) {
         rendertarget.m_83941_(p_110026_, p_110027_, Minecraft.f_91002_);
      }

   }

   public void m_110023_(float p_110024_) {
      if (p_110024_ < this.f_110016_) {
         this.f_110015_ += 1.0F - this.f_110016_;
         this.f_110015_ += p_110024_;
      } else {
         this.f_110015_ += p_110024_ - this.f_110016_;
      }

      for(this.f_110016_ = p_110024_; this.f_110015_ > 20.0F; this.f_110015_ -= 20.0F) {
      }

      for(PostPass postpass : this.f_110009_) {
         postpass.m_110065_(this.f_110015_ / 20.0F);
      }

   }

   public final String m_110022_() {
      return this.f_110008_;
   }

   private RenderTarget m_110049_(String p_110050_) {
      if (p_110050_ == null) {
         return null;
      } else {
         return p_110050_.equals("minecraft:main") ? this.f_110006_ : this.f_110010_.get(p_110050_);
      }
   }
}
