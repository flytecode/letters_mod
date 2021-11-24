package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WinScreen extends Screen {
   private static final Logger f_96863_ = LogManager.getLogger();
   private static final ResourceLocation f_96864_ = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation f_96865_ = new ResourceLocation("textures/gui/title/edition.png");
   private static final ResourceLocation f_96866_ = new ResourceLocation("textures/misc/vignette.png");
   private static final Component f_169463_ = (new TextComponent("============")).m_130940_(ChatFormatting.WHITE);
   private static final String f_169464_ = "           ";
   private static final String f_96867_ = "" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + ChatFormatting.GREEN + ChatFormatting.AQUA;
   private static final int f_169465_ = 274;
   private static final float f_169466_ = 5.0F;
   private static final float f_181393_ = 15.0F;
   private final boolean f_96868_;
   private final Runnable f_96869_;
   private float f_169467_;
   private List<FormattedCharSequence> f_96871_;
   private IntSet f_96872_;
   private int f_96873_;
   private boolean f_181391_;
   private final IntSet f_181392_ = new IntOpenHashSet();
   private float f_96874_;
   private final float f_169462_;

   public WinScreen(boolean p_96877_, Runnable p_96878_) {
      super(NarratorChatListener.f_93310_);
      this.f_96868_ = p_96877_;
      this.f_96869_ = p_96878_;
      if (!p_96877_) {
         this.f_169462_ = 0.75F;
      } else {
         this.f_169462_ = 0.5F;
      }

      this.f_96874_ = this.f_169462_;
   }

   private float m_181399_() {
      return this.f_181391_ ? this.f_169462_ * (5.0F + (float)this.f_181392_.size() * 15.0F) : this.f_169462_;
   }

   public void m_96624_() {
      this.f_96541_.m_91397_().m_120183_();
      this.f_96541_.m_91106_().m_120389_(false);
      float f = (float)(this.f_96873_ + this.f_96544_ + this.f_96544_ + 24);
      if (this.f_169467_ > f) {
         this.m_96895_();
      }

   }

   public boolean m_7933_(int p_169469_, int p_169470_, int p_169471_) {
      if (p_169469_ != 341 && p_169469_ != 345) {
         if (p_169469_ == 32) {
            this.f_181391_ = true;
         }
      } else {
         this.f_181392_.add(p_169469_);
      }

      this.f_96874_ = this.m_181399_();
      return super.m_7933_(p_169469_, p_169470_, p_169471_);
   }

   public boolean m_7920_(int p_169476_, int p_169477_, int p_169478_) {
      if (p_169476_ == 32) {
         this.f_181391_ = false;
      } else if (p_169476_ == 341 || p_169476_ == 345) {
         this.f_181392_.remove(p_169476_);
      }

      this.f_96874_ = this.m_181399_();
      return super.m_7920_(p_169476_, p_169477_, p_169478_);
   }

   public void m_7379_() {
      this.m_96895_();
   }

   private void m_96895_() {
      this.f_96869_.run();
      this.f_96541_.m_91152_((Screen)null);
   }

   protected void m_7856_() {
      if (this.f_96871_ == null) {
         this.f_96871_ = Lists.newArrayList();
         this.f_96872_ = new IntOpenHashSet();
         Resource resource = null;

         try {
            if (this.f_96868_) {
               resource = this.f_96541_.m_91098_().m_142591_(new ResourceLocation("texts/end.txt"));
               InputStream inputstream = resource.m_6679_();
               BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
               Random random = new Random(8124371L);

               String s;
               while((s = bufferedreader.readLine()) != null) {
                  int i;
                  String s1;
                  String s2;
                  for(s = s.replaceAll("PLAYERNAME", this.f_96541_.m_91094_().m_92546_()); (i = s.indexOf(f_96867_)) != -1; s = s1 + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s2) {
                     s1 = s.substring(0, i);
                     s2 = s.substring(i + f_96867_.length());
                  }

                  this.m_181397_(s);
                  this.m_169482_();
               }

               inputstream.close();

               for(int j = 0; j < 8; ++j) {
                  this.m_169482_();
               }
            }

            resource = this.f_96541_.m_91098_().m_142591_(new ResourceLocation("texts/credits.json"));
            JsonArray jsonarray1 = GsonHelper.m_144765_(new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8));

            for(JsonElement jsonelement2 : jsonarray1.getAsJsonArray()) {
               JsonObject jsonobject1 = jsonelement2.getAsJsonObject();
               String s5 = jsonobject1.get("section").getAsString();
               this.m_169472_(f_169463_, true);
               this.m_169472_((new TextComponent(s5)).m_130940_(ChatFormatting.YELLOW), true);
               this.m_169472_(f_169463_, true);
               this.m_169482_();
               this.m_169482_();

               for(JsonElement jsonelement : jsonobject1.getAsJsonArray("titles")) {
                  JsonObject jsonobject = jsonelement.getAsJsonObject();
                  String s3 = jsonobject.get("title").getAsString();
                  JsonArray jsonarray = jsonobject.getAsJsonArray("names");
                  this.m_169472_((new TextComponent(s3)).m_130940_(ChatFormatting.GRAY), false);

                  for(JsonElement jsonelement1 : jsonarray) {
                     String s4 = jsonelement1.getAsString();
                     this.m_169472_((new TextComponent("           ")).m_130946_(s4).m_130940_(ChatFormatting.WHITE), false);
                  }

                  this.m_169482_();
                  this.m_169482_();
               }
            }

            this.f_96873_ = this.f_96871_.size() * 12;
         } catch (Exception exception) {
            f_96863_.error("Couldn't load credits", (Throwable)exception);
         } finally {
            IOUtils.closeQuietly((Closeable)resource);
         }

      }
   }

   private void m_169482_() {
      this.f_96871_.add(FormattedCharSequence.f_13691_);
   }

   private void m_181397_(String p_181398_) {
      this.f_96871_.addAll(this.f_96541_.f_91062_.m_92923_(new TextComponent(p_181398_), 274));
   }

   private void m_169472_(Component p_169473_, boolean p_169474_) {
      if (p_169474_) {
         this.f_96872_.add(this.f_96871_.size());
      }

      this.f_96871_.add(p_169473_.m_7532_());
   }

   private void m_169483_() {
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157456_(0, GuiComponent.f_93096_);
      int i = this.f_96543_;
      float f = -this.f_169467_ * 0.5F;
      float f1 = (float)this.f_96544_ - 0.5F * this.f_169467_;
      float f2 = 0.015625F;
      float f3 = this.f_169467_ / this.f_169462_;
      float f4 = f3 * 0.02F;
      float f5 = (float)(this.f_96873_ + this.f_96544_ + this.f_96544_ + 24) / this.f_169462_;
      float f6 = (f5 - 20.0F - f3) * 0.005F;
      if (f6 < f4) {
         f4 = f6;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      f4 = f4 * f4;
      f4 = f4 * 96.0F / 255.0F;
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_5483_(0.0D, (double)this.f_96544_, (double)this.m_93252_()).m_7421_(0.0F, f * 0.015625F).m_85950_(f4, f4, f4, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)i, (double)this.f_96544_, (double)this.m_93252_()).m_7421_((float)i * 0.015625F, f * 0.015625F).m_85950_(f4, f4, f4, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)i, 0.0D, (double)this.m_93252_()).m_7421_((float)i * 0.015625F, f1 * 0.015625F).m_85950_(f4, f4, f4, 1.0F).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, (double)this.m_93252_()).m_7421_(0.0F, f1 * 0.015625F).m_85950_(f4, f4, f4, 1.0F).m_5752_();
      tesselator.m_85914_();
   }

   public void m_6305_(PoseStack p_96884_, int p_96885_, int p_96886_, float p_96887_) {
      this.f_169467_ += p_96887_ * this.f_96874_;
      this.m_169483_();
      int i = this.f_96543_ / 2 - 137;
      int j = this.f_96544_ + 50;
      float f = -this.f_169467_;
      p_96884_.m_85836_();
      p_96884_.m_85837_(0.0D, (double)f, 0.0D);
      RenderSystem.m_157456_(0, f_96864_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      this.m_93101_(i, j, (p_96890_, p_96891_) -> {
         this.m_93228_(p_96884_, p_96890_ + 0, p_96891_, 0, 0, 155, 44);
         this.m_93228_(p_96884_, p_96890_ + 155, p_96891_, 0, 45, 155, 44);
      });
      RenderSystem.m_69461_();
      RenderSystem.m_157456_(0, f_96865_);
      m_93133_(p_96884_, i + 88, j + 37, 0.0F, 0.0F, 98, 14, 128, 16);
      int k = j + 100;

      for(int l = 0; l < this.f_96871_.size(); ++l) {
         if (l == this.f_96871_.size() - 1) {
            float f1 = (float)k + f - (float)(this.f_96544_ / 2 - 6);
            if (f1 < 0.0F) {
               p_96884_.m_85837_(0.0D, (double)(-f1), 0.0D);
            }
         }

         if ((float)k + f + 12.0F + 8.0F > 0.0F && (float)k + f < (float)this.f_96544_) {
            FormattedCharSequence formattedcharsequence = this.f_96871_.get(l);
            if (this.f_96872_.contains(l)) {
               this.f_96547_.m_92744_(p_96884_, formattedcharsequence, (float)(i + (274 - this.f_96547_.m_92724_(formattedcharsequence)) / 2), (float)k, 16777215);
            } else {
               this.f_96547_.m_92744_(p_96884_, formattedcharsequence, (float)i, (float)k, 16777215);
            }
         }

         k += 12;
      }

      p_96884_.m_85849_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157456_(0, f_96866_);
      RenderSystem.m_69478_();
      RenderSystem.m_69408_(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
      int i1 = this.f_96543_;
      int j1 = this.f_96544_;
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_5483_(0.0D, (double)j1, (double)this.m_93252_()).m_7421_(0.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)i1, (double)j1, (double)this.m_93252_()).m_7421_(1.0F, 1.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_((double)i1, 0.0D, (double)this.m_93252_()).m_7421_(1.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_5752_();
      bufferbuilder.m_5483_(0.0D, 0.0D, (double)this.m_93252_()).m_7421_(0.0F, 0.0F).m_85950_(1.0F, 1.0F, 1.0F, 1.0F).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69461_();
      super.m_6305_(p_96884_, p_96885_, p_96886_, p_96887_);
   }
}