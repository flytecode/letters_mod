package net.minecraft.client.gui.screens.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancementsScreen extends Screen implements ClientAdvancements.Listener {
   private static final ResourceLocation f_97329_ = new ResourceLocation("textures/gui/advancements/window.png");
   private static final ResourceLocation f_97330_ = new ResourceLocation("textures/gui/advancements/tabs.png");
   public static final int f_169556_ = 252;
   public static final int f_169557_ = 140;
   private static final int f_169564_ = 9;
   private static final int f_169565_ = 18;
   public static final int f_169558_ = 234;
   public static final int f_169559_ = 113;
   private static final int f_169566_ = 8;
   private static final int f_169567_ = 6;
   public static final int f_169560_ = 16;
   public static final int f_169561_ = 16;
   public static final int f_169562_ = 14;
   public static final int f_169563_ = 7;
   private static final Component f_97331_ = new TranslatableComponent("advancements.sad_label");
   private static final Component f_97332_ = new TranslatableComponent("advancements.empty");
   private static final Component f_97333_ = new TranslatableComponent("gui.advancements");
   private final ClientAdvancements f_97334_;
   private final Map<Advancement, AdvancementTab> f_97335_ = Maps.newLinkedHashMap();
   private AdvancementTab f_97336_;
   private boolean f_97337_;
   private static int tabPage, maxPages;

   public AdvancementsScreen(ClientAdvancements p_97340_) {
      super(NarratorChatListener.f_93310_);
      this.f_97334_ = p_97340_;
   }

   protected void m_7856_() {
      this.f_97335_.clear();
      this.f_97336_ = null;
      this.f_97334_.m_104397_(this);
      if (this.f_97336_ == null && !this.f_97335_.isEmpty()) {
         this.f_97334_.m_104401_(this.f_97335_.values().iterator().next().m_97182_(), true);
      } else {
         this.f_97334_.m_104401_(this.f_97336_ == null ? null : this.f_97336_.m_97182_(), true);
      }
      if (this.f_97335_.size() > AdvancementTabType.MAX_TABS) {
          int guiLeft = (this.f_96543_ - 252) / 2;
          int guiTop = (this.f_96544_ - 140) / 2;
          m_142416_(new net.minecraft.client.gui.components.Button(guiLeft,            guiTop - 50, 20, 20, new net.minecraft.network.chat.TextComponent("<"), b -> tabPage = Math.max(tabPage - 1, 0       )));
          m_142416_(new net.minecraft.client.gui.components.Button(guiLeft + 252 - 20, guiTop - 50, 20, 20, new net.minecraft.network.chat.TextComponent(">"), b -> tabPage = Math.min(tabPage + 1, maxPages)));
          maxPages = this.f_97335_.size() / AdvancementTabType.MAX_TABS;
      }
   }

   public void m_7861_() {
      this.f_97334_.m_104397_((ClientAdvancements.Listener)null);
      ClientPacketListener clientpacketlistener = this.f_96541_.m_91403_();
      if (clientpacketlistener != null) {
         clientpacketlistener.m_104955_(ServerboundSeenAdvancementsPacket.m_134444_());
      }

   }

   public boolean m_6375_(double p_97343_, double p_97344_, int p_97345_) {
      if (p_97345_ == 0) {
         int i = (this.f_96543_ - 252) / 2;
         int j = (this.f_96544_ - 140) / 2;

         for(AdvancementTab advancementtab : this.f_97335_.values()) {
            if (advancementtab.getPage() == tabPage && advancementtab.m_97154_(i, j, p_97343_, p_97344_)) {
               this.f_97334_.m_104401_(advancementtab.m_97182_(), true);
               break;
            }
         }
      }

      return super.m_6375_(p_97343_, p_97344_, p_97345_);
   }

   public boolean m_7933_(int p_97353_, int p_97354_, int p_97355_) {
      if (this.f_96541_.f_91066_.f_92055_.m_90832_(p_97353_, p_97354_)) {
         this.f_96541_.m_91152_((Screen)null);
         this.f_96541_.f_91067_.m_91601_();
         return true;
      } else {
         return super.m_7933_(p_97353_, p_97354_, p_97355_);
      }
   }

   public void m_6305_(PoseStack p_97361_, int p_97362_, int p_97363_, float p_97364_) {
      int i = (this.f_96543_ - 252) / 2;
      int j = (this.f_96544_ - 140) / 2;
      this.m_7333_(p_97361_);
      if (maxPages != 0) {
         net.minecraft.network.chat.Component page = new net.minecraft.network.chat.TextComponent(String.format("%d / %d", tabPage + 1, maxPages + 1));
         int width = this.f_96547_.m_92852_(page);
         this.f_96547_.m_92744_(p_97361_, page.m_7532_(), i + (252 / 2) - (width / 2), j - 44, -1);
      }
      this.m_97373_(p_97361_, p_97362_, p_97363_, i, j);
      this.m_97356_(p_97361_, i, j);
      this.m_97381_(p_97361_, p_97362_, p_97363_, i, j);
   }

   public boolean m_7979_(double p_97347_, double p_97348_, int p_97349_, double p_97350_, double p_97351_) {
      if (p_97349_ != 0) {
         this.f_97337_ = false;
         return false;
      } else {
         if (!this.f_97337_) {
            this.f_97337_ = true;
         } else if (this.f_97336_ != null) {
            this.f_97336_.m_97151_(p_97350_, p_97351_);
         }

         return true;
      }
   }

   private void m_97373_(PoseStack p_97374_, int p_97375_, int p_97376_, int p_97377_, int p_97378_) {
      AdvancementTab advancementtab = this.f_97336_;
      if (advancementtab == null) {
         m_93172_(p_97374_, p_97377_ + 9, p_97378_ + 18, p_97377_ + 9 + 234, p_97378_ + 18 + 113, -16777216);
         int i = p_97377_ + 9 + 117;
         m_93215_(p_97374_, this.f_96547_, f_97332_, i, p_97378_ + 18 + 56 - 9 / 2, -1);
         m_93215_(p_97374_, this.f_96547_, f_97331_, i, p_97378_ + 18 + 113 - 9, -1);
      } else {
         PoseStack posestack = RenderSystem.m_157191_();
         posestack.m_85836_();
         posestack.m_85837_((double)(p_97377_ + 9), (double)(p_97378_ + 18), 0.0D);
         RenderSystem.m_157182_();
         advancementtab.m_97163_(p_97374_);
         posestack.m_85849_();
         RenderSystem.m_157182_();
         RenderSystem.m_69456_(515);
         RenderSystem.m_69465_();
      }
   }

   public void m_97356_(PoseStack p_97357_, int p_97358_, int p_97359_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_97329_);
      this.m_93228_(p_97357_, p_97358_, p_97359_, 0, 0, 252, 140);
      if (this.f_97335_.size() > 1) {
         RenderSystem.m_157456_(0, f_97330_);

         for(AdvancementTab advancementtab : this.f_97335_.values()) {
            if (advancementtab.getPage() == tabPage)
            advancementtab.m_97165_(p_97357_, p_97358_, p_97359_, advancementtab == this.f_97336_);
         }

         RenderSystem.m_69453_();

         for(AdvancementTab advancementtab1 : this.f_97335_.values()) {
            if (advancementtab1.getPage() == tabPage)
            advancementtab1.m_97159_(p_97358_, p_97359_, this.f_96542_);
         }

         RenderSystem.m_69461_();
      }

      this.f_96547_.m_92889_(p_97357_, f_97333_, (float)(p_97358_ + 8), (float)(p_97359_ + 6), 4210752);
   }

   private void m_97381_(PoseStack p_97382_, int p_97383_, int p_97384_, int p_97385_, int p_97386_) {
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.f_97336_ != null) {
         PoseStack posestack = RenderSystem.m_157191_();
         posestack.m_85836_();
         posestack.m_85837_((double)(p_97385_ + 9), (double)(p_97386_ + 18), 400.0D);
         RenderSystem.m_157182_();
         RenderSystem.m_69482_();
         this.f_97336_.m_97183_(p_97382_, p_97383_ - p_97385_ - 9, p_97384_ - p_97386_ - 18, p_97385_, p_97386_);
         RenderSystem.m_69465_();
         posestack.m_85849_();
         RenderSystem.m_157182_();
      }

      if (this.f_97335_.size() > 1) {
         for(AdvancementTab advancementtab : this.f_97335_.values()) {
            if (advancementtab.getPage() == tabPage && advancementtab.m_97154_(p_97385_, p_97386_, (double)p_97383_, (double)p_97384_)) {
               this.m_96602_(p_97382_, advancementtab.m_97189_(), p_97383_, p_97384_);
            }
         }
      }

   }

   public void m_5513_(Advancement p_97366_) {
      AdvancementTab advancementtab = AdvancementTab.m_97170_(this.f_96541_, this, this.f_97335_.size(), p_97366_);
      if (advancementtab != null) {
         this.f_97335_.put(p_97366_, advancementtab);
      }
   }

   public void m_5504_(Advancement p_97372_) {
   }

   public void m_5505_(Advancement p_97380_) {
      AdvancementTab advancementtab = this.m_97394_(p_97380_);
      if (advancementtab != null) {
         advancementtab.m_97178_(p_97380_);
      }

   }

   public void m_5516_(Advancement p_97388_) {
   }

   public void m_7922_(Advancement p_97368_, AdvancementProgress p_97369_) {
      AdvancementWidget advancementwidget = this.m_97392_(p_97368_);
      if (advancementwidget != null) {
         advancementwidget.m_97264_(p_97369_);
      }

   }

   public void m_6896_(@Nullable Advancement p_97391_) {
      this.f_97336_ = this.f_97335_.get(p_97391_);
   }

   public void m_7204_() {
      this.f_97335_.clear();
      this.f_97336_ = null;
   }

   @Nullable
   public AdvancementWidget m_97392_(Advancement p_97393_) {
      AdvancementTab advancementtab = this.m_97394_(p_97393_);
      return advancementtab == null ? null : advancementtab.m_97180_(p_97393_);
   }

   @Nullable
   private AdvancementTab m_97394_(Advancement p_97395_) {
      while(p_97395_.m_138319_() != null) {
         p_97395_ = p_97395_.m_138319_();
      }

      return this.f_97335_.get(p_97395_);
   }
}
