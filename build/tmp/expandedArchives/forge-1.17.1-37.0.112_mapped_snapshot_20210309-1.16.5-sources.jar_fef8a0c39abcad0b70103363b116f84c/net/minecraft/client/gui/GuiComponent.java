package net.minecraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GuiComponent {
   public static final ResourceLocation f_93096_ = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation f_93097_ = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation f_93098_ = new ResourceLocation("textures/gui/icons.png");
   private int f_93095_;

   protected void m_93154_(PoseStack p_93155_, int p_93156_, int p_93157_, int p_93158_, int p_93159_) {
      if (p_93157_ < p_93156_) {
         int i = p_93156_;
         p_93156_ = p_93157_;
         p_93157_ = i;
      }

      m_93172_(p_93155_, p_93156_, p_93158_, p_93157_ + 1, p_93158_ + 1, p_93159_);
   }

   protected void m_93222_(PoseStack p_93223_, int p_93224_, int p_93225_, int p_93226_, int p_93227_) {
      if (p_93226_ < p_93225_) {
         int i = p_93225_;
         p_93225_ = p_93226_;
         p_93226_ = i;
      }

      m_93172_(p_93223_, p_93224_, p_93225_ + 1, p_93224_ + 1, p_93226_, p_93227_);
   }

   public static void m_93172_(PoseStack p_93173_, int p_93174_, int p_93175_, int p_93176_, int p_93177_, int p_93178_) {
      m_93105_(p_93173_.m_85850_().m_85861_(), p_93174_, p_93175_, p_93176_, p_93177_, p_93178_);
   }

   private static void m_93105_(Matrix4f p_93106_, int p_93107_, int p_93108_, int p_93109_, int p_93110_, int p_93111_) {
      if (p_93107_ < p_93109_) {
         int i = p_93107_;
         p_93107_ = p_93109_;
         p_93109_ = i;
      }

      if (p_93108_ < p_93110_) {
         int j = p_93108_;
         p_93108_ = p_93110_;
         p_93110_ = j;
      }

      float f3 = (float)(p_93111_ >> 24 & 255) / 255.0F;
      float f = (float)(p_93111_ >> 16 & 255) / 255.0F;
      float f1 = (float)(p_93111_ >> 8 & 255) / 255.0F;
      float f2 = (float)(p_93111_ & 255) / 255.0F;
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      RenderSystem.m_69478_();
      RenderSystem.m_69472_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      bufferbuilder.m_85982_(p_93106_, (float)p_93107_, (float)p_93110_, 0.0F).m_85950_(f, f1, f2, f3).m_5752_();
      bufferbuilder.m_85982_(p_93106_, (float)p_93109_, (float)p_93110_, 0.0F).m_85950_(f, f1, f2, f3).m_5752_();
      bufferbuilder.m_85982_(p_93106_, (float)p_93109_, (float)p_93108_, 0.0F).m_85950_(f, f1, f2, f3).m_5752_();
      bufferbuilder.m_85982_(p_93106_, (float)p_93107_, (float)p_93108_, 0.0F).m_85950_(f, f1, f2, f3).m_5752_();
      bufferbuilder.m_85721_();
      BufferUploader.m_85761_(bufferbuilder);
      RenderSystem.m_69493_();
      RenderSystem.m_69461_();
   }

   protected void m_93179_(PoseStack p_93180_, int p_93181_, int p_93182_, int p_93183_, int p_93184_, int p_93185_, int p_93186_) {
      m_168740_(p_93180_, p_93181_, p_93182_, p_93183_, p_93184_, p_93185_, p_93186_, this.f_93095_);
   }

   protected static void m_168740_(PoseStack p_168741_, int p_168742_, int p_168743_, int p_168744_, int p_168745_, int p_168746_, int p_168747_, int p_168748_) {
      RenderSystem.m_69472_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      m_93123_(p_168741_.m_85850_().m_85861_(), bufferbuilder, p_168742_, p_168743_, p_168744_, p_168745_, p_168748_, p_168746_, p_168747_);
      tesselator.m_85914_();
      RenderSystem.m_69461_();
      RenderSystem.m_69493_();
   }

   protected static void m_93123_(Matrix4f p_93124_, BufferBuilder p_93125_, int p_93126_, int p_93127_, int p_93128_, int p_93129_, int p_93130_, int p_93131_, int p_93132_) {
      float f = (float)(p_93131_ >> 24 & 255) / 255.0F;
      float f1 = (float)(p_93131_ >> 16 & 255) / 255.0F;
      float f2 = (float)(p_93131_ >> 8 & 255) / 255.0F;
      float f3 = (float)(p_93131_ & 255) / 255.0F;
      float f4 = (float)(p_93132_ >> 24 & 255) / 255.0F;
      float f5 = (float)(p_93132_ >> 16 & 255) / 255.0F;
      float f6 = (float)(p_93132_ >> 8 & 255) / 255.0F;
      float f7 = (float)(p_93132_ & 255) / 255.0F;
      p_93125_.m_85982_(p_93124_, (float)p_93128_, (float)p_93127_, (float)p_93130_).m_85950_(f1, f2, f3, f).m_5752_();
      p_93125_.m_85982_(p_93124_, (float)p_93126_, (float)p_93127_, (float)p_93130_).m_85950_(f1, f2, f3, f).m_5752_();
      p_93125_.m_85982_(p_93124_, (float)p_93126_, (float)p_93129_, (float)p_93130_).m_85950_(f5, f6, f7, f4).m_5752_();
      p_93125_.m_85982_(p_93124_, (float)p_93128_, (float)p_93129_, (float)p_93130_).m_85950_(f5, f6, f7, f4).m_5752_();
   }

   public static void m_93208_(PoseStack p_93209_, Font p_93210_, String p_93211_, int p_93212_, int p_93213_, int p_93214_) {
      p_93210_.m_92750_(p_93209_, p_93211_, (float)(p_93212_ - p_93210_.m_92895_(p_93211_) / 2), (float)p_93213_, p_93214_);
   }

   public static void m_93215_(PoseStack p_93216_, Font p_93217_, Component p_93218_, int p_93219_, int p_93220_, int p_93221_) {
      FormattedCharSequence formattedcharsequence = p_93218_.m_7532_();
      p_93217_.m_92744_(p_93216_, formattedcharsequence, (float)(p_93219_ - p_93217_.m_92724_(formattedcharsequence) / 2), (float)p_93220_, p_93221_);
   }

   public static void m_168749_(PoseStack p_168750_, Font p_168751_, FormattedCharSequence p_168752_, int p_168753_, int p_168754_, int p_168755_) {
      p_168751_.m_92744_(p_168750_, p_168752_, (float)(p_168753_ - p_168751_.m_92724_(p_168752_) / 2), (float)p_168754_, p_168755_);
   }

   public static void m_93236_(PoseStack p_93237_, Font p_93238_, String p_93239_, int p_93240_, int p_93241_, int p_93242_) {
      p_93238_.m_92750_(p_93237_, p_93239_, (float)p_93240_, (float)p_93241_, p_93242_);
   }

   public static void m_168756_(PoseStack p_168757_, Font p_168758_, FormattedCharSequence p_168759_, int p_168760_, int p_168761_, int p_168762_) {
      p_168758_.m_92744_(p_168757_, p_168759_, (float)p_168760_, (float)p_168761_, p_168762_);
   }

   public static void m_93243_(PoseStack p_93244_, Font p_93245_, Component p_93246_, int p_93247_, int p_93248_, int p_93249_) {
      p_93245_.m_92763_(p_93244_, p_93246_, (float)p_93247_, (float)p_93248_, p_93249_);
   }

   public void m_93101_(int p_93102_, int p_93103_, BiConsumer<Integer, Integer> p_93104_) {
      RenderSystem.m_69416_(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_93104_.accept(p_93102_ + 1, p_93103_);
      p_93104_.accept(p_93102_ - 1, p_93103_);
      p_93104_.accept(p_93102_, p_93103_ + 1);
      p_93104_.accept(p_93102_, p_93103_ - 1);
      RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      p_93104_.accept(p_93102_, p_93103_);
   }

   public static void m_93200_(PoseStack p_93201_, int p_93202_, int p_93203_, int p_93204_, int p_93205_, int p_93206_, TextureAtlasSprite p_93207_) {
      m_93112_(p_93201_.m_85850_().m_85861_(), p_93202_, p_93202_ + p_93205_, p_93203_, p_93203_ + p_93206_, p_93204_, p_93207_.m_118409_(), p_93207_.m_118410_(), p_93207_.m_118411_(), p_93207_.m_118412_());
   }

   public void m_93228_(PoseStack p_93229_, int p_93230_, int p_93231_, int p_93232_, int p_93233_, int p_93234_, int p_93235_) {
      m_93143_(p_93229_, p_93230_, p_93231_, this.f_93095_, (float)p_93232_, (float)p_93233_, p_93234_, p_93235_, 256, 256);
   }

   public static void m_93143_(PoseStack p_93144_, int p_93145_, int p_93146_, int p_93147_, float p_93148_, float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_) {
      m_93187_(p_93144_, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_, p_93148_, p_93149_, p_93153_, p_93152_);
   }

   public static void m_93160_(PoseStack p_93161_, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_, float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_) {
      m_93187_(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_);
   }

   public static void m_93133_(PoseStack p_93134_, int p_93135_, int p_93136_, float p_93137_, float p_93138_, int p_93139_, int p_93140_, int p_93141_, int p_93142_) {
      m_93160_(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_);
   }

   private static void m_93187_(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
      m_93112_(p_93188_.m_85850_().m_85861_(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_);
   }

   private static void m_93112_(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
      bufferbuilder.m_85982_(p_93113_, (float)p_93114_, (float)p_93117_, (float)p_93118_).m_7421_(p_93119_, p_93122_).m_5752_();
      bufferbuilder.m_85982_(p_93113_, (float)p_93115_, (float)p_93117_, (float)p_93118_).m_7421_(p_93120_, p_93122_).m_5752_();
      bufferbuilder.m_85982_(p_93113_, (float)p_93115_, (float)p_93116_, (float)p_93118_).m_7421_(p_93120_, p_93121_).m_5752_();
      bufferbuilder.m_85982_(p_93113_, (float)p_93114_, (float)p_93116_, (float)p_93118_).m_7421_(p_93119_, p_93121_).m_5752_();
      bufferbuilder.m_85721_();
      BufferUploader.m_85761_(bufferbuilder);
   }

   public int m_93252_() {
      return this.f_93095_;
   }

   public void m_93250_(int p_93251_) {
      this.f_93095_ = p_93251_;
   }
}