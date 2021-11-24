package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LoomScreen extends AbstractContainerScreen<LoomMenu> {
   private static final ResourceLocation f_99060_ = new ResourceLocation("textures/gui/container/loom.png");
   private static final int f_169775_ = 1;
   private static final int f_169776_ = 4;
   private static final int f_169777_ = 4;
   private static final int f_99061_ = (BannerPattern.f_58526_ - BannerPattern.f_58527_ - 1 + 4 - 1) / 4;
   private static final int f_169778_ = 12;
   private static final int f_169779_ = 15;
   private static final int f_169780_ = 14;
   private static final int f_169781_ = 56;
   private static final int f_169782_ = 60;
   private static final int f_169783_ = 13;
   private ModelPart f_99062_;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> f_99063_;
   private ItemStack f_99064_ = ItemStack.f_41583_;
   private ItemStack f_99065_ = ItemStack.f_41583_;
   private ItemStack f_99066_ = ItemStack.f_41583_;
   private boolean f_99067_;
   private boolean f_99068_;
   private boolean f_99069_;
   private float f_99070_;
   private boolean f_99071_;
   private int f_99072_ = 1;

   public LoomScreen(LoomMenu p_99075_, Inventory p_99076_, Component p_99077_) {
      super(p_99075_, p_99076_, p_99077_);
      p_99075_.m_39878_(this::m_99112_);
      this.f_97729_ -= 2;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_99062_ = this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171264_).m_171324_("flag");
   }

   public void m_6305_(PoseStack p_99104_, int p_99105_, int p_99106_, float p_99107_) {
      super.m_6305_(p_99104_, p_99105_, p_99106_, p_99107_);
      this.m_7025_(p_99104_, p_99105_, p_99106_);
   }

   protected void m_7286_(PoseStack p_99099_, float p_99100_, int p_99101_, int p_99102_) {
      this.m_7333_(p_99099_);
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_99060_);
      int i = this.f_97735_;
      int j = this.f_97736_;
      this.m_93228_(p_99099_, i, j, 0, 0, this.f_97726_, this.f_97727_);
      Slot slot = this.f_97732_.m_39894_();
      Slot slot1 = this.f_97732_.m_39895_();
      Slot slot2 = this.f_97732_.m_39896_();
      Slot slot3 = this.f_97732_.m_39897_();
      if (!slot.m_6657_()) {
         this.m_93228_(p_99099_, i + slot.f_40220_, j + slot.f_40221_, this.f_97726_, 0, 16, 16);
      }

      if (!slot1.m_6657_()) {
         this.m_93228_(p_99099_, i + slot1.f_40220_, j + slot1.f_40221_, this.f_97726_ + 16, 0, 16, 16);
      }

      if (!slot2.m_6657_()) {
         this.m_93228_(p_99099_, i + slot2.f_40220_, j + slot2.f_40221_, this.f_97726_ + 32, 0, 16, 16);
      }

      int k = (int)(41.0F * this.f_99070_);
      this.m_93228_(p_99099_, i + 119, j + 13 + k, 232 + (this.f_99067_ ? 0 : 12), 0, 12, 15);
      Lighting.m_84930_();
      if (this.f_99063_ != null && !this.f_99069_) {
         MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_96541_.m_91269_().m_110104_();
         p_99099_.m_85836_();
         p_99099_.m_85837_((double)(i + 139), (double)(j + 52), 0.0D);
         p_99099_.m_85841_(24.0F, -24.0F, 1.0F);
         p_99099_.m_85837_(0.5D, 0.5D, 0.5D);
         float f = 0.6666667F;
         p_99099_.m_85841_(0.6666667F, -0.6666667F, -0.6666667F);
         this.f_99062_.f_104203_ = 0.0F;
         this.f_99062_.f_104201_ = -32.0F;
         BannerRenderer.m_112065_(p_99099_, multibuffersource$buffersource, 15728880, OverlayTexture.f_118083_, this.f_99062_, ModelBakery.f_119224_, true, this.f_99063_);
         p_99099_.m_85849_();
         multibuffersource$buffersource.m_109911_();
      } else if (this.f_99069_) {
         this.m_93228_(p_99099_, i + slot3.f_40220_ - 2, j + slot3.f_40221_ - 2, this.f_97726_, 17, 17, 16);
      }

      if (this.f_99067_) {
         int j2 = i + 60;
         int l2 = j + 13;
         int l = this.f_99072_ + 16;

         for(int i1 = this.f_99072_; i1 < l && i1 < BannerPattern.f_58526_ - BannerPattern.f_58527_; ++i1) {
            int j1 = i1 - this.f_99072_;
            int k1 = j2 + j1 % 4 * 14;
            int l1 = l2 + j1 / 4 * 14;
            RenderSystem.m_157456_(0, f_99060_);
            int i2 = this.f_97727_;
            if (i1 == this.f_97732_.m_39891_()) {
               i2 += 14;
            } else if (p_99101_ >= k1 && p_99102_ >= l1 && p_99101_ < k1 + 14 && p_99102_ < l1 + 14) {
               i2 += 28;
            }

            this.m_93228_(p_99099_, k1, l1, 0, i2, 14, 14);
            this.m_99108_(i1, k1, l1);
         }
      } else if (this.f_99068_) {
         int k2 = i + 60;
         int i3 = j + 13;
         RenderSystem.m_157456_(0, f_99060_);
         this.m_93228_(p_99099_, k2, i3, 0, this.f_97727_, 14, 14);
         int j3 = this.f_97732_.m_39891_();
         this.m_99108_(j3, k2, i3);
      }

      Lighting.m_84931_();
   }

   private void m_99108_(int p_99109_, int p_99110_, int p_99111_) {
      ItemStack itemstack = new ItemStack(Items.f_42667_);
      CompoundTag compoundtag = itemstack.m_41698_("BlockEntityTag");
      ListTag listtag = (new BannerPattern.Builder()).m_58588_(BannerPattern.BASE, DyeColor.GRAY).m_58588_(BannerPattern.values()[p_99109_], DyeColor.WHITE).m_58587_();
      compoundtag.m_128365_("Patterns", listtag);
      PoseStack posestack = new PoseStack();
      posestack.m_85836_();
      posestack.m_85837_((double)((float)p_99110_ + 0.5F), (double)(p_99111_ + 16), 0.0D);
      posestack.m_85841_(6.0F, -6.0F, 1.0F);
      posestack.m_85837_(0.5D, 0.5D, 0.0D);
      posestack.m_85837_(0.5D, 0.5D, 0.5D);
      float f = 0.6666667F;
      posestack.m_85841_(0.6666667F, -0.6666667F, -0.6666667F);
      MultiBufferSource.BufferSource multibuffersource$buffersource = this.f_96541_.m_91269_().m_110104_();
      this.f_99062_.f_104203_ = 0.0F;
      this.f_99062_.f_104201_ = -32.0F;
      List<Pair<BannerPattern, DyeColor>> list = BannerBlockEntity.m_58484_(DyeColor.GRAY, BannerBlockEntity.m_58487_(itemstack));
      BannerRenderer.m_112065_(posestack, multibuffersource$buffersource, 15728880, OverlayTexture.f_118083_, this.f_99062_, ModelBakery.f_119224_, true, list);
      posestack.m_85849_();
      multibuffersource$buffersource.m_109911_();
   }

   public boolean m_6375_(double p_99083_, double p_99084_, int p_99085_) {
      this.f_99071_ = false;
      if (this.f_99067_) {
         int i = this.f_97735_ + 60;
         int j = this.f_97736_ + 13;
         int k = this.f_99072_ + 16;

         for(int l = this.f_99072_; l < k; ++l) {
            int i1 = l - this.f_99072_;
            double d0 = p_99083_ - (double)(i + i1 % 4 * 14);
            double d1 = p_99084_ - (double)(j + i1 / 4 * 14);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 14.0D && d1 < 14.0D && this.f_97732_.m_6366_(this.f_96541_.f_91074_, l)) {
               Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12491_, 1.0F));
               this.f_96541_.f_91072_.m_105208_((this.f_97732_).f_38840_, l);
               return true;
            }
         }

         i = this.f_97735_ + 119;
         j = this.f_97736_ + 9;
         if (p_99083_ >= (double)i && p_99083_ < (double)(i + 12) && p_99084_ >= (double)j && p_99084_ < (double)(j + 56)) {
            this.f_99071_ = true;
         }
      }

      return super.m_6375_(p_99083_, p_99084_, p_99085_);
   }

   public boolean m_7979_(double p_99087_, double p_99088_, int p_99089_, double p_99090_, double p_99091_) {
      if (this.f_99071_ && this.f_99067_) {
         int i = this.f_97736_ + 13;
         int j = i + 56;
         this.f_99070_ = ((float)p_99088_ - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.f_99070_ = Mth.m_14036_(this.f_99070_, 0.0F, 1.0F);
         int k = f_99061_ - 4;
         int l = (int)((double)(this.f_99070_ * (float)k) + 0.5D);
         if (l < 0) {
            l = 0;
         }

         this.f_99072_ = 1 + l * 4;
         return true;
      } else {
         return super.m_7979_(p_99087_, p_99088_, p_99089_, p_99090_, p_99091_);
      }
   }

   public boolean m_6050_(double p_99079_, double p_99080_, double p_99081_) {
      if (this.f_99067_) {
         int i = f_99061_ - 4;
         this.f_99070_ = (float)((double)this.f_99070_ - p_99081_ / (double)i);
         this.f_99070_ = Mth.m_14036_(this.f_99070_, 0.0F, 1.0F);
         this.f_99072_ = 1 + (int)((double)(this.f_99070_ * (float)i) + 0.5D) * 4;
      }

      return true;
   }

   protected boolean m_7467_(double p_99093_, double p_99094_, int p_99095_, int p_99096_, int p_99097_) {
      return p_99093_ < (double)p_99095_ || p_99094_ < (double)p_99096_ || p_99093_ >= (double)(p_99095_ + this.f_97726_) || p_99094_ >= (double)(p_99096_ + this.f_97727_);
   }

   private void m_99112_() {
      ItemStack itemstack = this.f_97732_.m_39897_().m_7993_();
      if (itemstack.m_41619_()) {
         this.f_99063_ = null;
      } else {
         this.f_99063_ = BannerBlockEntity.m_58484_(((BannerItem)itemstack.m_41720_()).m_40545_(), BannerBlockEntity.m_58487_(itemstack));
      }

      ItemStack itemstack1 = this.f_97732_.m_39894_().m_7993_();
      ItemStack itemstack2 = this.f_97732_.m_39895_().m_7993_();
      ItemStack itemstack3 = this.f_97732_.m_39896_().m_7993_();
      CompoundTag compoundtag = itemstack1.m_41698_("BlockEntityTag");
      this.f_99069_ = compoundtag.m_128425_("Patterns", 9) && !itemstack1.m_41619_() && compoundtag.m_128437_("Patterns", 10).size() >= 6;
      if (this.f_99069_) {
         this.f_99063_ = null;
      }

      if (!ItemStack.m_41728_(itemstack1, this.f_99064_) || !ItemStack.m_41728_(itemstack2, this.f_99065_) || !ItemStack.m_41728_(itemstack3, this.f_99066_)) {
         this.f_99067_ = !itemstack1.m_41619_() && !itemstack2.m_41619_() && itemstack3.m_41619_() && !this.f_99069_;
         this.f_99068_ = !this.f_99069_ && !itemstack3.m_41619_() && !itemstack1.m_41619_() && !itemstack2.m_41619_();
      }

      this.f_99064_ = itemstack1.m_41777_();
      this.f_99065_ = itemstack2.m_41777_();
      this.f_99066_ = itemstack3.m_41777_();
   }
}