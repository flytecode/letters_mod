package net.minecraft.client.renderer.entity;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemRenderer implements ResourceManagerReloadListener {
   public static final ResourceLocation f_115092_ = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private static final Set<Item> f_115094_ = Sets.newHashSet(Items.f_41852_);
   private static final int f_174221_ = 8;
   private static final int f_174222_ = 8;
   public static final int f_174218_ = 200;
   public static final float f_174219_ = 0.5F;
   public static final float f_174220_ = 0.75F;
   public float f_115093_;
   private final ItemModelShaper f_115095_;
   private final TextureManager f_115096_;
   private final ItemColors f_115097_;
   private final BlockEntityWithoutLevelRenderer f_174223_;

   public ItemRenderer(TextureManager p_174225_, ModelManager p_174226_, ItemColors p_174227_, BlockEntityWithoutLevelRenderer p_174228_) {
      this.f_115096_ = p_174225_;
      this.f_174223_ = p_174228_;
      this.f_115095_ = new net.minecraftforge.client.ItemModelMesherForge(p_174226_);

      for(Item item : Registry.f_122827_) {
         if (!f_115094_.contains(item)) {
            this.f_115095_.m_109396_(item, new ModelResourceLocation(Registry.f_122827_.m_7981_(item), "inventory"));
         }
      }

      this.f_115097_ = p_174227_;
   }

   public ItemModelShaper m_115103_() {
      return this.f_115095_;
   }

   public void m_115189_(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
      Random random = new Random();
      long i = 42L;

      for(Direction direction : Direction.values()) {
         random.setSeed(42L);
         this.m_115162_(p_115194_, p_115195_, p_115190_.m_6840_((BlockState)null, direction, random), p_115191_, p_115192_, p_115193_);
      }

      random.setSeed(42L);
      this.m_115162_(p_115194_, p_115195_, p_115190_.m_6840_((BlockState)null, (Direction)null, random), p_115191_, p_115192_, p_115193_);
   }

   public void m_115143_(ItemStack p_115144_, ItemTransforms.TransformType p_115145_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_) {
      if (!p_115144_.m_41619_()) {
         p_115147_.m_85836_();
         boolean flag = p_115145_ == ItemTransforms.TransformType.GUI || p_115145_ == ItemTransforms.TransformType.GROUND || p_115145_ == ItemTransforms.TransformType.FIXED;
         if (flag) {
            if (p_115144_.m_150930_(Items.f_42713_)) {
               p_115151_ = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:trident#inventory"));
            } else if (p_115144_.m_150930_(Items.f_151059_)) {
               p_115151_ = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:spyglass#inventory"));
            }
         }

         p_115151_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(p_115147_, p_115151_, p_115145_, p_115146_);
         p_115147_.m_85837_(-0.5D, -0.5D, -0.5D);
         if (!p_115151_.m_7521_() && (!p_115144_.m_150930_(Items.f_42713_) || flag)) {
            boolean flag1;
            if (p_115145_ != ItemTransforms.TransformType.GUI && !p_115145_.m_111841_() && p_115144_.m_41720_() instanceof BlockItem) {
               Block block = ((BlockItem)p_115144_.m_41720_()).m_40614_();
               flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
            } else {
               flag1 = true;
            }
            if (p_115151_.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(this, p_115151_, p_115144_, p_115147_, p_115148_, p_115149_, p_115150_, flag1); }
            else {
            RenderType rendertype = ItemBlockRenderTypes.m_109279_(p_115144_, flag1);
            VertexConsumer vertexconsumer;
            if (p_115144_.m_150930_(Items.f_42522_) && p_115144_.m_41790_()) {
               p_115147_.m_85836_();
               PoseStack.Pose posestack$pose = p_115147_.m_85850_();
               if (p_115145_ == ItemTransforms.TransformType.GUI) {
                  posestack$pose.m_85861_().m_27630_(0.5F);
               } else if (p_115145_.m_111841_()) {
                  posestack$pose.m_85861_().m_27630_(0.75F);
               }

               if (flag1) {
                  vertexconsumer = m_115207_(p_115148_, rendertype, posestack$pose);
               } else {
                  vertexconsumer = m_115180_(p_115148_, rendertype, posestack$pose);
               }

               p_115147_.m_85849_();
            } else if (flag1) {
               vertexconsumer = m_115222_(p_115148_, rendertype, true, p_115144_.m_41790_());
            } else {
               vertexconsumer = m_115211_(p_115148_, rendertype, true, p_115144_.m_41790_());
            }

            this.m_115189_(p_115151_, p_115144_, p_115149_, p_115150_, p_115147_, vertexconsumer);
            }
         } else {
            net.minecraftforge.client.RenderProperties.get(p_115144_).getItemStackRenderer().m_108829_(p_115144_, p_115145_, p_115147_, p_115148_, p_115149_, p_115150_);
         }

         p_115147_.m_85849_();
      }
   }

   public static VertexConsumer m_115184_(MultiBufferSource p_115185_, RenderType p_115186_, boolean p_115187_, boolean p_115188_) {
      return p_115188_ ? VertexMultiConsumer.m_86168_(p_115185_.m_6299_(p_115187_ ? RenderType.m_110481_() : RenderType.m_110484_()), p_115185_.m_6299_(p_115186_)) : p_115185_.m_6299_(p_115186_);
   }

   public static VertexConsumer m_115180_(MultiBufferSource p_115181_, RenderType p_115182_, PoseStack.Pose p_115183_) {
      return VertexMultiConsumer.m_86168_(new SheetedDecalTextureGenerator(p_115181_.m_6299_(RenderType.m_110490_()), p_115183_.m_85861_(), p_115183_.m_85864_()), p_115181_.m_6299_(p_115182_));
   }

   public static VertexConsumer m_115207_(MultiBufferSource p_115208_, RenderType p_115209_, PoseStack.Pose p_115210_) {
      return VertexMultiConsumer.m_86168_(new SheetedDecalTextureGenerator(p_115208_.m_6299_(RenderType.m_110493_()), p_115210_.m_85861_(), p_115210_.m_85864_()), p_115208_.m_6299_(p_115209_));
   }

   public static VertexConsumer m_115211_(MultiBufferSource p_115212_, RenderType p_115213_, boolean p_115214_, boolean p_115215_) {
      if (p_115215_) {
         return Minecraft.m_91085_() && p_115213_ == Sheets.m_110791_() ? VertexMultiConsumer.m_86168_(p_115212_.m_6299_(RenderType.m_110487_()), p_115212_.m_6299_(p_115213_)) : VertexMultiConsumer.m_86168_(p_115212_.m_6299_(p_115214_ ? RenderType.m_110490_() : RenderType.m_110496_()), p_115212_.m_6299_(p_115213_));
      } else {
         return p_115212_.m_6299_(p_115213_);
      }
   }

   public static VertexConsumer m_115222_(MultiBufferSource p_115223_, RenderType p_115224_, boolean p_115225_, boolean p_115226_) {
      return p_115226_ ? VertexMultiConsumer.m_86168_(p_115223_.m_6299_(p_115225_ ? RenderType.m_110493_() : RenderType.m_110499_()), p_115223_.m_6299_(p_115224_)) : p_115223_.m_6299_(p_115224_);
   }

   public void m_115162_(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, ItemStack p_115166_, int p_115167_, int p_115168_) {
      boolean flag = !p_115166_.m_41619_();
      PoseStack.Pose posestack$pose = p_115163_.m_85850_();

      for(BakedQuad bakedquad : p_115165_) {
         int i = -1;
         if (flag && bakedquad.m_111304_()) {
            i = this.f_115097_.m_92676_(p_115166_, bakedquad.m_111305_());
         }

         float f = (float)(i >> 16 & 255) / 255.0F;
         float f1 = (float)(i >> 8 & 255) / 255.0F;
         float f2 = (float)(i & 255) / 255.0F;
         p_115164_.putBulkData(posestack$pose, bakedquad, f, f1, f2, p_115167_, p_115168_, true);
      }

   }

   public BakedModel m_174264_(ItemStack p_174265_, @Nullable Level p_174266_, @Nullable LivingEntity p_174267_, int p_174268_) {
      BakedModel bakedmodel;
      if (p_174265_.m_150930_(Items.f_42713_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      } else if (p_174265_.m_150930_(Items.f_151059_)) {
         bakedmodel = this.f_115095_.m_109393_().m_119422_(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
      } else {
         bakedmodel = this.f_115095_.m_109406_(p_174265_);
      }

      ClientLevel clientlevel = p_174266_ instanceof ClientLevel ? (ClientLevel)p_174266_ : null;
      BakedModel bakedmodel1 = bakedmodel.m_7343_().m_173464_(bakedmodel, p_174265_, clientlevel, p_174267_, p_174268_);
      return bakedmodel1 == null ? this.f_115095_.m_109393_().m_119409_() : bakedmodel1;
   }

   public void m_174269_(ItemStack p_174270_, ItemTransforms.TransformType p_174271_, int p_174272_, int p_174273_, PoseStack p_174274_, MultiBufferSource p_174275_, int p_174276_) {
      this.m_174242_((LivingEntity)null, p_174270_, p_174271_, false, p_174274_, p_174275_, (Level)null, p_174272_, p_174273_, p_174276_);
   }

   public void m_174242_(@Nullable LivingEntity p_174243_, ItemStack p_174244_, ItemTransforms.TransformType p_174245_, boolean p_174246_, PoseStack p_174247_, MultiBufferSource p_174248_, @Nullable Level p_174249_, int p_174250_, int p_174251_, int p_174252_) {
      if (!p_174244_.m_41619_()) {
         BakedModel bakedmodel = this.m_174264_(p_174244_, p_174249_, p_174243_, p_174252_);
         this.m_115143_(p_174244_, p_174245_, p_174246_, p_174247_, p_174248_, p_174250_, p_174251_, bakedmodel);
      }
   }

   public void m_115123_(ItemStack p_115124_, int p_115125_, int p_115126_) {
      this.m_115127_(p_115124_, p_115125_, p_115126_, this.m_174264_(p_115124_, (Level)null, (LivingEntity)null, 0));
   }

   protected void m_115127_(ItemStack p_115128_, int p_115129_, int p_115130_, BakedModel p_115131_) {
      this.f_115096_.m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
      RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
      RenderSystem.m_69478_();
      RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_85837_((double)p_115129_, (double)p_115130_, (double)(100.0F + this.f_115093_));
      posestack.m_85837_(8.0D, 8.0D, 0.0D);
      posestack.m_85841_(1.0F, -1.0F, 1.0F);
      posestack.m_85841_(16.0F, 16.0F, 16.0F);
      RenderSystem.m_157182_();
      PoseStack posestack1 = new PoseStack();
      MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      boolean flag = !p_115131_.m_7547_();
      if (flag) {
         Lighting.m_84930_();
      }

      this.m_115143_(p_115128_, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.f_118083_, p_115131_);
      multibuffersource$buffersource.m_109911_();
      RenderSystem.m_69482_();
      if (flag) {
         Lighting.m_84931_();
      }

      posestack.m_85849_();
      RenderSystem.m_157182_();
   }

   public void m_115203_(ItemStack p_115204_, int p_115205_, int p_115206_) {
      this.m_174277_(Minecraft.m_91087_().f_91074_, p_115204_, p_115205_, p_115206_, 0);
   }

   public void m_174253_(ItemStack p_174254_, int p_174255_, int p_174256_, int p_174257_) {
      this.m_174277_(Minecraft.m_91087_().f_91074_, p_174254_, p_174255_, p_174256_, p_174257_);
   }

   public void m_174258_(ItemStack p_174259_, int p_174260_, int p_174261_, int p_174262_, int p_174263_) {
      this.m_174235_(Minecraft.m_91087_().f_91074_, p_174259_, p_174260_, p_174261_, p_174262_, p_174263_);
   }

   public void m_115218_(ItemStack p_115219_, int p_115220_, int p_115221_) {
      this.m_174277_((LivingEntity)null, p_115219_, p_115220_, p_115221_, 0);
   }

   public void m_174229_(LivingEntity p_174230_, ItemStack p_174231_, int p_174232_, int p_174233_, int p_174234_) {
      this.m_174277_(p_174230_, p_174231_, p_174232_, p_174233_, p_174234_);
   }

   private void m_174277_(@Nullable LivingEntity p_174278_, ItemStack p_174279_, int p_174280_, int p_174281_, int p_174282_) {
      this.m_174235_(p_174278_, p_174279_, p_174280_, p_174281_, p_174282_, 0);
   }

   private void m_174235_(@Nullable LivingEntity p_174236_, ItemStack p_174237_, int p_174238_, int p_174239_, int p_174240_, int p_174241_) {
      if (!p_174237_.m_41619_()) {
         BakedModel bakedmodel = this.m_174264_(p_174237_, (Level)null, p_174236_, p_174240_);
         this.f_115093_ = bakedmodel.m_7539_() ? this.f_115093_ + 50.0F + (float)p_174241_ : this.f_115093_ + 50.0F;

         try {
            this.m_115127_(p_174237_, p_174238_, p_174239_, bakedmodel);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Rendering item");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Item being rendered");
            crashreportcategory.m_128165_("Item Type", () -> {
               return String.valueOf((Object)p_174237_.m_41720_());
            });
            crashreportcategory.m_128165_("Registry Name", () -> String.valueOf(p_174237_.m_41720_().getRegistryName()));
            crashreportcategory.m_128165_("Item Damage", () -> {
               return String.valueOf(p_174237_.m_41773_());
            });
            crashreportcategory.m_128165_("Item NBT", () -> {
               return String.valueOf((Object)p_174237_.m_41783_());
            });
            crashreportcategory.m_128165_("Item Foil", () -> {
               return String.valueOf(p_174237_.m_41790_());
            });
            throw new ReportedException(crashreport);
         }

         this.f_115093_ = bakedmodel.m_7539_() ? this.f_115093_ - 50.0F - (float)p_174241_ : this.f_115093_ - 50.0F;
      }
   }

   public void m_115169_(Font p_115170_, ItemStack p_115171_, int p_115172_, int p_115173_) {
      this.m_115174_(p_115170_, p_115171_, p_115172_, p_115173_, (String)null);
   }

   public void m_115174_(Font p_115175_, ItemStack p_115176_, int p_115177_, int p_115178_, @Nullable String p_115179_) {
      if (!p_115176_.m_41619_()) {
         PoseStack posestack = new PoseStack();
         if (p_115176_.m_41613_() != 1 || p_115179_ != null) {
            String s = p_115179_ == null ? String.valueOf(p_115176_.m_41613_()) : p_115179_;
            posestack.m_85837_(0.0D, 0.0D, (double)(this.f_115093_ + 200.0F));
            MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
            p_115175_.m_92811_(s, (float)(p_115177_ + 19 - 2 - p_115175_.m_92895_(s)), (float)(p_115178_ + 6 + 3), 16777215, true, posestack.m_85850_().m_85861_(), multibuffersource$buffersource, false, 0, 15728880);
            multibuffersource$buffersource.m_109911_();
         }

         if (p_115176_.m_41720_().showDurabilityBar(p_115176_)) {
            RenderSystem.m_69465_();
            RenderSystem.m_69472_();
            RenderSystem.m_69461_();
            Tesselator tesselator = Tesselator.m_85913_();
            BufferBuilder bufferbuilder = tesselator.m_85915_();
            double health = p_115176_.m_41720_().getDurabilityForDisplay(p_115176_);
            int i = Math.round(13.0F - (float)health * 13.0F);
            int j = p_115176_.m_41720_().getRGBDurabilityForDisplay(p_115176_);
            this.m_115152_(bufferbuilder, p_115177_ + 2, p_115178_ + 13, 13, 2, 0, 0, 0, 255);
            this.m_115152_(bufferbuilder, p_115177_ + 2, p_115178_ + 13, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            RenderSystem.m_69478_();
            RenderSystem.m_69493_();
            RenderSystem.m_69482_();
         }

         LocalPlayer localplayer = Minecraft.m_91087_().f_91074_;
         float f = localplayer == null ? 0.0F : localplayer.m_36335_().m_41521_(p_115176_.m_41720_(), Minecraft.m_91087_().m_91296_());
         if (f > 0.0F) {
            RenderSystem.m_69465_();
            RenderSystem.m_69472_();
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            Tesselator tesselator1 = Tesselator.m_85913_();
            BufferBuilder bufferbuilder1 = tesselator1.m_85915_();
            this.m_115152_(bufferbuilder1, p_115177_, p_115178_ + Mth.m_14143_(16.0F * (1.0F - f)), 16, Mth.m_14167_(16.0F * f), 255, 255, 255, 127);
            RenderSystem.m_69493_();
            RenderSystem.m_69482_();
         }

      }
   }

   private void m_115152_(BufferBuilder p_115153_, int p_115154_, int p_115155_, int p_115156_, int p_115157_, int p_115158_, int p_115159_, int p_115160_, int p_115161_) {
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      p_115153_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      p_115153_.m_5483_((double)(p_115154_ + 0), (double)(p_115155_ + 0), 0.0D).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
      p_115153_.m_5483_((double)(p_115154_ + 0), (double)(p_115155_ + p_115157_), 0.0D).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
      p_115153_.m_5483_((double)(p_115154_ + p_115156_), (double)(p_115155_ + p_115157_), 0.0D).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
      p_115153_.m_5483_((double)(p_115154_ + p_115156_), (double)(p_115155_ + 0), 0.0D).m_6122_(p_115158_, p_115159_, p_115160_, p_115161_).m_5752_();
      p_115153_.m_85721_();
      BufferUploader.m_85761_(p_115153_);
   }

   public void m_6213_(ResourceManager p_115105_) {
      this.f_115095_.m_109403_();
   }

   public BlockEntityWithoutLevelRenderer getBlockEntityRenderer() {
       return f_174223_;
   }
}
