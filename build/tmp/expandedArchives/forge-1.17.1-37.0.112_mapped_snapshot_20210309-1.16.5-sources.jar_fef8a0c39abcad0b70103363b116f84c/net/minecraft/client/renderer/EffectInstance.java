package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.BlendMode;
import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.EffectProgram;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class EffectInstance implements Effect, AutoCloseable {
   private static final String f_172564_ = "shaders/program/";
   private static final Logger f_108921_ = LogManager.getLogger();
   private static final AbstractUniform f_108922_ = new AbstractUniform();
   private static final boolean f_172565_ = true;
   private static EffectInstance f_108923_;
   private static int f_108924_ = -1;
   private final Map<String, IntSupplier> f_108925_ = Maps.newHashMap();
   private final List<String> f_108926_ = Lists.newArrayList();
   private final List<Integer> f_108927_ = Lists.newArrayList();
   private final List<Uniform> f_108928_ = Lists.newArrayList();
   private final List<Integer> f_108929_ = Lists.newArrayList();
   private final Map<String, Uniform> f_108930_ = Maps.newHashMap();
   private final int f_108931_;
   private final String f_108932_;
   private boolean f_108933_;
   private final BlendMode f_108934_;
   private final List<Integer> f_108935_;
   private final List<String> f_108936_;
   private final EffectProgram f_108937_;
   private final EffectProgram f_108938_;

   public EffectInstance(ResourceManager p_108941_, String p_108942_) throws IOException {
      ResourceLocation rl = ResourceLocation.m_135820_(p_108942_);
      ResourceLocation resourcelocation = new ResourceLocation(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + ".json");
      this.f_108932_ = p_108942_;
      Resource resource = null;

      try {
         resource = p_108941_.m_142591_(resourcelocation);
         JsonObject jsonobject = GsonHelper.m_13859_(new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8));
         String s = GsonHelper.m_13906_(jsonobject, "vertex");
         String s2 = GsonHelper.m_13906_(jsonobject, "fragment");
         JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "samplers", (JsonArray)null);
         if (jsonarray != null) {
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               try {
                  this.m_108948_(jsonelement);
               } catch (Exception exception2) {
                  ChainedJsonException chainedjsonexception1 = ChainedJsonException.m_135906_(exception2);
                  chainedjsonexception1.m_135908_("samplers[" + i + "]");
                  throw chainedjsonexception1;
               }

               ++i;
            }
         }

         JsonArray jsonarray1 = GsonHelper.m_13832_(jsonobject, "attributes", (JsonArray)null);
         if (jsonarray1 != null) {
            int j = 0;
            this.f_108935_ = Lists.newArrayListWithCapacity(jsonarray1.size());
            this.f_108936_ = Lists.newArrayListWithCapacity(jsonarray1.size());

            for(JsonElement jsonelement1 : jsonarray1) {
               try {
                  this.f_108936_.add(GsonHelper.m_13805_(jsonelement1, "attribute"));
               } catch (Exception exception1) {
                  ChainedJsonException chainedjsonexception2 = ChainedJsonException.m_135906_(exception1);
                  chainedjsonexception2.m_135908_("attributes[" + j + "]");
                  throw chainedjsonexception2;
               }

               ++j;
            }
         } else {
            this.f_108935_ = null;
            this.f_108936_ = null;
         }

         JsonArray jsonarray2 = GsonHelper.m_13832_(jsonobject, "uniforms", (JsonArray)null);
         if (jsonarray2 != null) {
            int k = 0;

            for(JsonElement jsonelement2 : jsonarray2) {
               try {
                  this.m_108958_(jsonelement2);
               } catch (Exception exception) {
                  ChainedJsonException chainedjsonexception3 = ChainedJsonException.m_135906_(exception);
                  chainedjsonexception3.m_135908_("uniforms[" + k + "]");
                  throw chainedjsonexception3;
               }

               ++k;
            }
         }

         this.f_108934_ = m_108950_(GsonHelper.m_13841_(jsonobject, "blend", (JsonObject)null));
         this.f_108937_ = m_172566_(p_108941_, Program.Type.VERTEX, s);
         this.f_108938_ = m_172566_(p_108941_, Program.Type.FRAGMENT, s2);
         this.f_108931_ = ProgramManager.m_85577_();
         ProgramManager.m_166623_(this);
         this.m_108967_();
         if (this.f_108936_ != null) {
            for(String s3 : this.f_108936_) {
               int l = Uniform.m_85639_(this.f_108931_, s3);
               this.f_108935_.add(l);
            }
         }
      } catch (Exception exception3) {
         String s1;
         if (resource != null) {
            s1 = " (" + resource.m_7816_() + ")";
         } else {
            s1 = "";
         }

         ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(exception3);
         chainedjsonexception.m_135910_(resourcelocation.m_135815_() + s1);
         throw chainedjsonexception;
      } finally {
         IOUtils.closeQuietly((Closeable)resource);
      }

      this.m_142660_();
   }

   public static EffectProgram m_172566_(ResourceManager p_172567_, Program.Type p_172568_, String p_172569_) throws IOException {
      Program program = p_172568_.m_85570_().get(p_172569_);
      if (program != null && !(program instanceof EffectProgram)) {
         throw new InvalidClassException("Program is not of type EffectProgram");
      } else {
         EffectProgram effectprogram;
         if (program == null) {
            ResourceLocation rl = ResourceLocation.m_135820_(p_172569_);
            ResourceLocation resourcelocation = new ResourceLocation(rl.m_135827_(), "shaders/program/" + rl.m_135815_() + p_172568_.m_85569_());
            Resource resource = p_172567_.m_142591_(resourcelocation);

            try {
               effectprogram = EffectProgram.m_166588_(p_172568_, p_172569_, resource.m_6679_(), resource.m_7816_());
            } finally {
               IOUtils.closeQuietly((Closeable)resource);
            }
         } else {
            effectprogram = (EffectProgram)program;
         }

         return effectprogram;
      }
   }

   public static BlendMode m_108950_(JsonObject p_108951_) {
      if (p_108951_ == null) {
         return new BlendMode();
      } else {
         int i = 32774;
         int j = 1;
         int k = 0;
         int l = 1;
         int i1 = 0;
         boolean flag = true;
         boolean flag1 = false;
         if (GsonHelper.m_13813_(p_108951_, "func")) {
            i = BlendMode.m_85527_(p_108951_.get("func").getAsString());
            if (i != 32774) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(p_108951_, "srcrgb")) {
            j = BlendMode.m_85530_(p_108951_.get("srcrgb").getAsString());
            if (j != 1) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(p_108951_, "dstrgb")) {
            k = BlendMode.m_85530_(p_108951_.get("dstrgb").getAsString());
            if (k != 0) {
               flag = false;
            }
         }

         if (GsonHelper.m_13813_(p_108951_, "srcalpha")) {
            l = BlendMode.m_85530_(p_108951_.get("srcalpha").getAsString());
            if (l != 1) {
               flag = false;
            }

            flag1 = true;
         }

         if (GsonHelper.m_13813_(p_108951_, "dstalpha")) {
            i1 = BlendMode.m_85530_(p_108951_.get("dstalpha").getAsString());
            if (i1 != 0) {
               flag = false;
            }

            flag1 = true;
         }

         if (flag) {
            return new BlendMode();
         } else {
            return flag1 ? new BlendMode(j, k, l, i1, i) : new BlendMode(j, k, i);
         }
      }
   }

   public void close() {
      for(Uniform uniform : this.f_108928_) {
         uniform.close();
      }

      ProgramManager.m_166621_(this);
   }

   public void m_108965_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      ProgramManager.m_85578_(0);
      f_108924_ = -1;
      f_108923_ = null;

      for(int i = 0; i < this.f_108927_.size(); ++i) {
         if (this.f_108925_.get(this.f_108926_.get(i)) != null) {
            GlStateManager.m_84538_('\u84c0' + i);
            GlStateManager.m_84110_();
            GlStateManager.m_84544_(0);
         }
      }

   }

   public void m_108966_() {
      RenderSystem.m_69393_(RenderSystem::m_69584_);
      this.f_108933_ = false;
      f_108923_ = this;
      this.f_108934_.m_85526_();
      if (this.f_108931_ != f_108924_) {
         ProgramManager.m_85578_(this.f_108931_);
         f_108924_ = this.f_108931_;
      }

      for(int i = 0; i < this.f_108927_.size(); ++i) {
         String s = this.f_108926_.get(i);
         IntSupplier intsupplier = this.f_108925_.get(s);
         if (intsupplier != null) {
            RenderSystem.m_69388_('\u84c0' + i);
            RenderSystem.m_69493_();
            int j = intsupplier.getAsInt();
            if (j != -1) {
               RenderSystem.m_69396_(j);
               Uniform.m_85616_(this.f_108927_.get(i), i);
            }
         }
      }

      for(Uniform uniform : this.f_108928_) {
         uniform.m_85633_();
      }

   }

   public void m_142660_() {
      this.f_108933_ = true;
   }

   @Nullable
   public Uniform m_108952_(String p_108953_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      return this.f_108930_.get(p_108953_);
   }

   public AbstractUniform m_108960_(String p_108961_) {
      RenderSystem.m_69393_(RenderSystem::m_69584_);
      Uniform uniform = this.m_108952_(p_108961_);
      return (AbstractUniform)(uniform == null ? f_108922_ : uniform);
   }

   private void m_108967_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      IntList intlist = new IntArrayList();

      for(int i = 0; i < this.f_108926_.size(); ++i) {
         String s = this.f_108926_.get(i);
         int j = Uniform.m_85624_(this.f_108931_, s);
         if (j == -1) {
            f_108921_.warn("Shader {} could not find sampler named {} in the specified shader program.", this.f_108932_, s);
            this.f_108925_.remove(s);
            intlist.add(i);
         } else {
            this.f_108927_.add(j);
         }
      }

      for(int l = intlist.size() - 1; l >= 0; --l) {
         this.f_108926_.remove(intlist.getInt(l));
      }

      for(Uniform uniform : this.f_108928_) {
         String s1 = uniform.m_85599_();
         int k = Uniform.m_85624_(this.f_108931_, s1);
         if (k == -1) {
            f_108921_.warn("Shader {} could not find uniform named {} in the specified shader program.", this.f_108932_, s1);
         } else {
            this.f_108929_.add(k);
            uniform.m_85614_(k);
            this.f_108930_.put(s1, uniform);
         }
      }

   }

   private void m_108948_(JsonElement p_108949_) {
      JsonObject jsonobject = GsonHelper.m_13918_(p_108949_, "sampler");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      if (!GsonHelper.m_13813_(jsonobject, "file")) {
         this.f_108925_.put(s, (IntSupplier)null);
         this.f_108926_.add(s);
      } else {
         this.f_108926_.add(s);
      }
   }

   public void m_108954_(String p_108955_, IntSupplier p_108956_) {
      if (this.f_108925_.containsKey(p_108955_)) {
         this.f_108925_.remove(p_108955_);
      }

      this.f_108925_.put(p_108955_, p_108956_);
      this.m_142660_();
   }

   private void m_108958_(JsonElement p_108959_) throws ChainedJsonException {
      JsonObject jsonobject = GsonHelper.m_13918_(p_108959_, "uniform");
      String s = GsonHelper.m_13906_(jsonobject, "name");
      int i = Uniform.m_85629_(GsonHelper.m_13906_(jsonobject, "type"));
      int j = GsonHelper.m_13927_(jsonobject, "count");
      float[] afloat = new float[Math.max(j, 16)];
      JsonArray jsonarray = GsonHelper.m_13933_(jsonobject, "values");
      if (jsonarray.size() != j && jsonarray.size() > 1) {
         throw new ChainedJsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
      } else {
         int k = 0;

         for(JsonElement jsonelement : jsonarray) {
            try {
               afloat[k] = GsonHelper.m_13888_(jsonelement, "value");
            } catch (Exception exception) {
               ChainedJsonException chainedjsonexception = ChainedJsonException.m_135906_(exception);
               chainedjsonexception.m_135908_("values[" + k + "]");
               throw chainedjsonexception;
            }

            ++k;
         }

         if (j > 1 && jsonarray.size() == 1) {
            while(k < j) {
               afloat[k] = afloat[0];
               ++k;
            }
         }

         int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
         Uniform uniform = new Uniform(s, i + l, j, this);
         if (i <= 3) {
            uniform.m_7401_((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
         } else if (i <= 7) {
            uniform.m_5808_(afloat[0], afloat[1], afloat[2], afloat[3]);
         } else {
            uniform.m_5941_(afloat);
         }

         this.f_108928_.add(uniform);
      }
   }

   public Program m_142733_() {
      return this.f_108937_;
   }

   public Program m_142736_() {
      return this.f_108938_;
   }

   public void m_142662_() {
      this.f_108938_.m_166586_(this);
      this.f_108937_.m_166586_(this);
   }

   public String m_172571_() {
      return this.f_108932_;
   }

   public int m_142658_() {
      return this.f_108931_;
   }
}
