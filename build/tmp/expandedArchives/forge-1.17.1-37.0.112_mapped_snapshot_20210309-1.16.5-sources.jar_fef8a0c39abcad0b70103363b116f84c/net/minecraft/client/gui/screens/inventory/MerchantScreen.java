package net.minecraft.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MerchantScreen extends AbstractContainerScreen<MerchantMenu> {
   private static final ResourceLocation f_99113_ = new ResourceLocation("textures/gui/container/villager2.png");
   private static final int f_169785_ = 512;
   private static final int f_169786_ = 256;
   private static final int f_169787_ = 99;
   private static final int f_169788_ = 136;
   private static final int f_169789_ = 16;
   private static final int f_169790_ = 5;
   private static final int f_169791_ = 35;
   private static final int f_169792_ = 68;
   private static final int f_169793_ = 6;
   private static final int f_169794_ = 7;
   private static final int f_169795_ = 5;
   private static final int f_169796_ = 20;
   private static final int f_169797_ = 89;
   private static final int f_169798_ = 27;
   private static final int f_169799_ = 6;
   private static final int f_169800_ = 139;
   private static final int f_169801_ = 18;
   private static final int f_169802_ = 94;
   private static final Component f_99114_ = new TranslatableComponent("merchant.trades");
   private static final Component f_99115_ = new TextComponent(" - ");
   private static final Component f_99116_ = new TranslatableComponent("merchant.deprecated");
   private int f_99117_;
   private final MerchantScreen.TradeOfferButton[] f_99118_ = new MerchantScreen.TradeOfferButton[7];
   int f_99119_;
   private boolean f_99120_;

   public MerchantScreen(MerchantMenu p_99123_, Inventory p_99124_, Component p_99125_) {
      super(p_99123_, p_99124_, p_99125_);
      this.f_97726_ = 276;
      this.f_97730_ = 107;
   }

   private void m_99200_() {
      this.f_97732_.m_40063_(this.f_99117_);
      this.f_97732_.m_40072_(this.f_99117_);
      this.f_96541_.m_91403_().m_104955_(new ServerboundSelectTradePacket(this.f_99117_));
   }

   protected void m_7856_() {
      super.m_7856_();
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      int k = j + 16 + 2;

      for(int l = 0; l < 7; ++l) {
         this.f_99118_[l] = this.m_142416_(new MerchantScreen.TradeOfferButton(i + 5, k, l, (p_99174_) -> {
            if (p_99174_ instanceof MerchantScreen.TradeOfferButton) {
               this.f_99117_ = ((MerchantScreen.TradeOfferButton)p_99174_).m_99209_() + this.f_99119_;
               this.m_99200_();
            }

         }));
         k += 20;
      }

   }

   protected void m_7027_(PoseStack p_99185_, int p_99186_, int p_99187_) {
      int i = this.f_97732_.m_40071_();
      if (i > 0 && i <= 5 && this.f_97732_.m_40076_()) {
         Component component = this.f_96539_.m_6881_().m_7220_(f_99115_).m_7220_(new TranslatableComponent("merchant.level." + i));
         int j = this.f_96547_.m_92852_(component);
         int k = 49 + this.f_97726_ / 2 - j / 2;
         this.f_96547_.m_92889_(p_99185_, component, (float)k, 6.0F, 4210752);
      } else {
         this.f_96547_.m_92889_(p_99185_, this.f_96539_, (float)(49 + this.f_97726_ / 2 - this.f_96547_.m_92852_(this.f_96539_) / 2), 6.0F, 4210752);
      }

      this.f_96547_.m_92889_(p_99185_, this.f_169604_, (float)this.f_97730_, (float)this.f_97731_, 4210752);
      int l = this.f_96547_.m_92852_(f_99114_);
      this.f_96547_.m_92889_(p_99185_, f_99114_, (float)(5 - l / 2 + 48), 6.0F, 4210752);
   }

   protected void m_7286_(PoseStack p_99143_, float p_99144_, int p_99145_, int p_99146_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, f_99113_);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      m_93143_(p_99143_, i, j, this.m_93252_(), 0.0F, 0.0F, this.f_97726_, this.f_97727_, 256, 512);
      MerchantOffers merchantoffers = this.f_97732_.m_40075_();
      if (!merchantoffers.isEmpty()) {
         int k = this.f_99117_;
         if (k < 0 || k >= merchantoffers.size()) {
            return;
         }

         MerchantOffer merchantoffer = merchantoffers.get(k);
         if (merchantoffer.m_45380_()) {
            RenderSystem.m_157456_(0, f_99113_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            m_93143_(p_99143_, this.f_97735_ + 83 + 99, this.f_97736_ + 35, this.m_93252_(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }

   }

   private void m_99152_(PoseStack p_99153_, int p_99154_, int p_99155_, MerchantOffer p_99156_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_99113_);
      int i = this.f_97732_.m_40071_();
      int j = this.f_97732_.m_40065_();
      if (i < 5) {
         m_93143_(p_99153_, p_99154_ + 136, p_99155_ + 16, this.m_93252_(), 0.0F, 186.0F, 102, 5, 256, 512);
         int k = VillagerData.m_35572_(i);
         if (j >= k && VillagerData.m_35582_(i)) {
            int l = 100;
            float f = 100.0F / (float)(VillagerData.m_35577_(i) - k);
            int i1 = Math.min(Mth.m_14143_(f * (float)(j - k)), 100);
            m_93143_(p_99153_, p_99154_ + 136, p_99155_ + 16, this.m_93252_(), 0.0F, 191.0F, i1 + 1, 5, 256, 512);
            int j1 = this.f_97732_.m_40068_();
            if (j1 > 0) {
               int k1 = Math.min(Mth.m_14143_((float)j1 * f), 100 - i1);
               m_93143_(p_99153_, p_99154_ + 136 + i1 + 1, p_99155_ + 16 + 1, this.m_93252_(), 2.0F, 182.0F, k1, 3, 256, 512);
            }

         }
      }
   }

   private void m_99157_(PoseStack p_99158_, int p_99159_, int p_99160_, MerchantOffers p_99161_) {
      int i = p_99161_.size() + 1 - 7;
      if (i > 1) {
         int j = 139 - (27 + (i - 1) * 139 / i);
         int k = 1 + j / i + 139 / i;
         int l = 113;
         int i1 = Math.min(113, this.f_99119_ * k);
         if (this.f_99119_ == i - 1) {
            i1 = 113;
         }

         m_93143_(p_99158_, p_99159_ + 94, p_99160_ + 18 + i1, this.m_93252_(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         m_93143_(p_99158_, p_99159_ + 94, p_99160_ + 18, this.m_93252_(), 6.0F, 199.0F, 6, 27, 256, 512);
      }

   }

   public void m_6305_(PoseStack p_99148_, int p_99149_, int p_99150_, float p_99151_) {
      this.m_7333_(p_99148_);
      super.m_6305_(p_99148_, p_99149_, p_99150_, p_99151_);
      MerchantOffers merchantoffers = this.f_97732_.m_40075_();
      if (!merchantoffers.isEmpty()) {
         int i = (this.f_96543_ - this.f_97726_) / 2;
         int j = (this.f_96544_ - this.f_97727_) / 2;
         int k = j + 16 + 1;
         int l = i + 5 + 5;
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_99113_);
         this.m_99157_(p_99148_, i, j, merchantoffers);
         int i1 = 0;

         for(MerchantOffer merchantoffer : merchantoffers) {
            if (this.m_99140_(merchantoffers.size()) && (i1 < this.f_99119_ || i1 >= 7 + this.f_99119_)) {
               ++i1;
            } else {
               ItemStack itemstack = merchantoffer.m_45352_();
               ItemStack itemstack1 = merchantoffer.m_45358_();
               ItemStack itemstack2 = merchantoffer.m_45364_();
               ItemStack itemstack3 = merchantoffer.m_45368_();
               this.f_96542_.f_115093_ = 100.0F;
               int j1 = k + 2;
               this.m_99162_(p_99148_, itemstack1, itemstack, l, j1);
               if (!itemstack2.m_41619_()) {
                  this.f_96542_.m_115218_(itemstack2, i + 5 + 35, j1);
                  this.f_96542_.m_115169_(this.f_96547_, itemstack2, i + 5 + 35, j1);
               }

               this.m_99168_(p_99148_, merchantoffer, i, j1);
               this.f_96542_.m_115218_(itemstack3, i + 5 + 68, j1);
               this.f_96542_.m_115169_(this.f_96547_, itemstack3, i + 5 + 68, j1);
               this.f_96542_.f_115093_ = 0.0F;
               k += 20;
               ++i1;
            }
         }

         int k1 = this.f_99117_;
         MerchantOffer merchantoffer1 = merchantoffers.get(k1);
         if (this.f_97732_.m_40076_()) {
            this.m_99152_(p_99148_, i, j, merchantoffer1);
         }

         if (merchantoffer1.m_45380_() && this.m_6774_(186, 35, 22, 21, (double)p_99149_, (double)p_99150_) && this.f_97732_.m_40074_()) {
            this.m_96602_(p_99148_, f_99116_, p_99149_, p_99150_);
         }

         for(MerchantScreen.TradeOfferButton merchantscreen$tradeofferbutton : this.f_99118_) {
            if (merchantscreen$tradeofferbutton.m_5702_()) {
               merchantscreen$tradeofferbutton.m_7428_(p_99148_, p_99149_, p_99150_);
            }

            merchantscreen$tradeofferbutton.f_93624_ = merchantscreen$tradeofferbutton.f_99201_ < this.f_97732_.m_40075_().size();
         }

         RenderSystem.m_69482_();
      }

      this.m_7025_(p_99148_, p_99149_, p_99150_);
   }

   private void m_99168_(PoseStack p_99169_, MerchantOffer p_99170_, int p_99171_, int p_99172_) {
      RenderSystem.m_69478_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_99113_);
      if (p_99170_.m_45380_()) {
         m_93143_(p_99169_, p_99171_ + 5 + 35 + 20, p_99172_ + 3, this.m_93252_(), 25.0F, 171.0F, 10, 9, 256, 512);
      } else {
         m_93143_(p_99169_, p_99171_ + 5 + 35 + 20, p_99172_ + 3, this.m_93252_(), 15.0F, 171.0F, 10, 9, 256, 512);
      }

   }

   private void m_99162_(PoseStack p_99163_, ItemStack p_99164_, ItemStack p_99165_, int p_99166_, int p_99167_) {
      this.f_96542_.m_115218_(p_99164_, p_99166_, p_99167_);
      if (p_99165_.m_41613_() == p_99164_.m_41613_()) {
         this.f_96542_.m_115169_(this.f_96547_, p_99164_, p_99166_, p_99167_);
      } else {
         this.f_96542_.m_115174_(this.f_96547_, p_99165_, p_99166_, p_99167_, p_99165_.m_41613_() == 1 ? "1" : null);
         this.f_96542_.m_115174_(this.f_96547_, p_99164_, p_99166_ + 14, p_99167_, p_99164_.m_41613_() == 1 ? "1" : null);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, f_99113_);
         this.m_93250_(this.m_93252_() + 300);
         m_93143_(p_99163_, p_99166_ + 7, p_99167_ + 12, this.m_93252_(), 0.0F, 176.0F, 9, 2, 256, 512);
         this.m_93250_(this.m_93252_() - 300);
      }

   }

   private boolean m_99140_(int p_99141_) {
      return p_99141_ > 7;
   }

   public boolean m_6050_(double p_99127_, double p_99128_, double p_99129_) {
      int i = this.f_97732_.m_40075_().size();
      if (this.m_99140_(i)) {
         int j = i - 7;
         this.f_99119_ = (int)((double)this.f_99119_ - p_99129_);
         this.f_99119_ = Mth.m_14045_(this.f_99119_, 0, j);
      }

      return true;
   }

   public boolean m_7979_(double p_99135_, double p_99136_, int p_99137_, double p_99138_, double p_99139_) {
      int i = this.f_97732_.m_40075_().size();
      if (this.f_99120_) {
         int j = this.f_97736_ + 18;
         int k = j + 139;
         int l = i - 7;
         float f = ((float)p_99136_ - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
         f = f * (float)l + 0.5F;
         this.f_99119_ = Mth.m_14045_((int)f, 0, l);
         return true;
      } else {
         return super.m_7979_(p_99135_, p_99136_, p_99137_, p_99138_, p_99139_);
      }
   }

   public boolean m_6375_(double p_99131_, double p_99132_, int p_99133_) {
      this.f_99120_ = false;
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      if (this.m_99140_(this.f_97732_.m_40075_().size()) && p_99131_ > (double)(i + 94) && p_99131_ < (double)(i + 94 + 6) && p_99132_ > (double)(j + 18) && p_99132_ <= (double)(j + 18 + 139 + 1)) {
         this.f_99120_ = true;
      }

      return super.m_6375_(p_99131_, p_99132_, p_99133_);
   }

   @OnlyIn(Dist.CLIENT)
   class TradeOfferButton extends Button {
      final int f_99201_;

      public TradeOfferButton(int p_99205_, int p_99206_, int p_99207_, Button.OnPress p_99208_) {
         super(p_99205_, p_99206_, 89, 20, TextComponent.f_131282_, p_99208_);
         this.f_99201_ = p_99207_;
         this.f_93624_ = false;
      }

      public int m_99209_() {
         return this.f_99201_;
      }

      public void m_7428_(PoseStack p_99211_, int p_99212_, int p_99213_) {
         if (this.f_93622_ && MerchantScreen.this.f_97732_.m_40075_().size() > this.f_99201_ + MerchantScreen.this.f_99119_) {
            if (p_99212_ < this.f_93620_ + 20) {
               ItemStack itemstack = MerchantScreen.this.f_97732_.m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_).m_45358_();
               MerchantScreen.this.m_6057_(p_99211_, itemstack, p_99212_, p_99213_);
            } else if (p_99212_ < this.f_93620_ + 50 && p_99212_ > this.f_93620_ + 30) {
               ItemStack itemstack2 = MerchantScreen.this.f_97732_.m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_).m_45364_();
               if (!itemstack2.m_41619_()) {
                  MerchantScreen.this.m_6057_(p_99211_, itemstack2, p_99212_, p_99213_);
               }
            } else if (p_99212_ > this.f_93620_ + 65) {
               ItemStack itemstack1 = MerchantScreen.this.f_97732_.m_40075_().get(this.f_99201_ + MerchantScreen.this.f_99119_).m_45368_();
               MerchantScreen.this.m_6057_(p_99211_, itemstack1, p_99212_, p_99213_);
            }
         }

      }
   }
}