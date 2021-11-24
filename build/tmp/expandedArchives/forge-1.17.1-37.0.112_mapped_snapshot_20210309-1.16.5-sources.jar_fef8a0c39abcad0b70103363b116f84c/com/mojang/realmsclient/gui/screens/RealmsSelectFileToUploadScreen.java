package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsSelectFileToUploadScreen extends RealmsScreen {
   private static final Logger f_89482_ = LogManager.getLogger();
   static final Component f_89483_ = new TranslatableComponent("selectWorld.world");
   static final Component f_89484_ = new TranslatableComponent("selectWorld.conversion");
   static final Component f_89485_ = (new TranslatableComponent("mco.upload.hardcore")).m_130940_(ChatFormatting.DARK_RED);
   static final Component f_89486_ = new TranslatableComponent("selectWorld.cheats");
   private static final DateFormat f_89487_ = new SimpleDateFormat();
   private final RealmsResetWorldScreen f_89488_;
   private final long f_89489_;
   private final int f_89490_;
   Button f_89491_;
   List<LevelSummary> f_89492_ = Lists.newArrayList();
   int f_89493_ = -1;
   RealmsSelectFileToUploadScreen.WorldSelectionList f_89494_;
   private final Runnable f_89481_;

   public RealmsSelectFileToUploadScreen(long p_89498_, int p_89499_, RealmsResetWorldScreen p_89500_, Runnable p_89501_) {
      super(new TranslatableComponent("mco.upload.select.world.title"));
      this.f_89488_ = p_89500_;
      this.f_89489_ = p_89498_;
      this.f_89490_ = p_89499_;
      this.f_89481_ = p_89501_;
   }

   private void m_89551_() throws Exception {
      this.f_89492_ = this.f_96541_.m_91392_().m_78244_().stream().sorted((p_89512_, p_89513_) -> {
         if (p_89512_.m_78366_() < p_89513_.m_78366_()) {
            return 1;
         } else {
            return p_89512_.m_78366_() > p_89513_.m_78366_() ? -1 : p_89512_.m_78358_().compareTo(p_89513_.m_78358_());
         }
      }).collect(Collectors.toList());

      for(LevelSummary levelsummary : this.f_89492_) {
         this.f_89494_.m_89587_(levelsummary);
      }

   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_89494_ = new RealmsSelectFileToUploadScreen.WorldSelectionList();

      try {
         this.m_89551_();
      } catch (Exception exception) {
         f_89482_.error("Couldn't load level list", (Throwable)exception);
         this.f_96541_.m_91152_(new RealmsGenericErrorScreen(new TextComponent("Unable to load worlds"), Component.m_130674_(exception.getMessage()), this.f_89488_));
         return;
      }

      this.m_7787_(this.f_89494_);
      this.f_89491_ = this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 32, 153, 20, new TranslatableComponent("mco.upload.button.name"), (p_89532_) -> {
         this.m_89552_();
      }));
      this.f_89491_.f_93623_ = this.f_89493_ >= 0 && this.f_89493_ < this.f_89492_.size();
      this.m_142416_(new Button(this.f_96543_ / 2 + 6, this.f_96544_ - 32, 153, 20, CommonComponents.f_130660_, (p_89525_) -> {
         this.f_96541_.m_91152_(this.f_89488_);
      }));
      this.m_175073_(new RealmsLabel(new TranslatableComponent("mco.upload.select.world.subtitle"), this.f_96543_ / 2, m_120774_(-1), 10526880));
      if (this.f_89492_.isEmpty()) {
         this.m_175073_(new RealmsLabel(new TranslatableComponent("mco.upload.select.world.none"), this.f_96543_ / 2, this.f_96544_ / 2 - 20, 16777215));
      }

   }

   public Component m_142562_() {
      return CommonComponents.m_178398_(this.m_96636_(), this.m_175075_());
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_89552_() {
      if (this.f_89493_ != -1 && !this.f_89492_.get(this.f_89493_).m_78368_()) {
         LevelSummary levelsummary = this.f_89492_.get(this.f_89493_);
         this.f_96541_.m_91152_(new RealmsUploadScreen(this.f_89489_, this.f_89490_, this.f_89488_, levelsummary, this.f_89481_));
      }

   }

   public void m_6305_(PoseStack p_89515_, int p_89516_, int p_89517_, float p_89518_) {
      this.m_7333_(p_89515_);
      this.f_89494_.m_6305_(p_89515_, p_89516_, p_89517_, p_89518_);
      m_93215_(p_89515_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 13, 16777215);
      super.m_6305_(p_89515_, p_89516_, p_89517_, p_89518_);
   }

   public boolean m_7933_(int p_89506_, int p_89507_, int p_89508_) {
      if (p_89506_ == 256) {
         this.f_96541_.m_91152_(this.f_89488_);
         return true;
      } else {
         return super.m_7933_(p_89506_, p_89507_, p_89508_);
      }
   }

   static Component m_89534_(LevelSummary p_89535_) {
      return p_89535_.m_78367_().m_151499_();
   }

   static String m_89538_(LevelSummary p_89539_) {
      return f_89487_.format(new Date(p_89539_.m_78366_()));
   }

   @OnlyIn(Dist.CLIENT)
   class Entry extends ObjectSelectionList.Entry<RealmsSelectFileToUploadScreen.Entry> {
      private final LevelSummary f_89554_;
      private final String f_89555_;
      private final String f_89556_;
      private final Component f_89557_;

      public Entry(LevelSummary p_89560_) {
         this.f_89554_ = p_89560_;
         this.f_89555_ = p_89560_.m_78361_();
         this.f_89556_ = p_89560_.m_78358_() + " (" + RealmsSelectFileToUploadScreen.m_89538_(p_89560_) + ")";
         if (p_89560_.m_78365_()) {
            this.f_89557_ = RealmsSelectFileToUploadScreen.f_89484_;
         } else {
            Component component;
            if (p_89560_.m_78368_()) {
               component = RealmsSelectFileToUploadScreen.f_89485_;
            } else {
               component = RealmsSelectFileToUploadScreen.m_89534_(p_89560_);
            }

            if (p_89560_.m_78369_()) {
               component = component.m_6881_().m_130946_(", ").m_7220_(RealmsSelectFileToUploadScreen.f_89486_);
            }

            this.f_89557_ = component;
         }

      }

      public void m_6311_(PoseStack p_89566_, int p_89567_, int p_89568_, int p_89569_, int p_89570_, int p_89571_, int p_89572_, int p_89573_, boolean p_89574_, float p_89575_) {
         this.m_167474_(p_89566_, p_89567_, p_89569_, p_89568_);
      }

      public boolean m_6375_(double p_89562_, double p_89563_, int p_89564_) {
         RealmsSelectFileToUploadScreen.this.f_89494_.m_7109_(RealmsSelectFileToUploadScreen.this.f_89492_.indexOf(this.f_89554_));
         return true;
      }

      protected void m_167474_(PoseStack p_167475_, int p_167476_, int p_167477_, int p_167478_) {
         String s;
         if (this.f_89555_.isEmpty()) {
            s = RealmsSelectFileToUploadScreen.f_89483_ + " " + (p_167476_ + 1);
         } else {
            s = this.f_89555_;
         }

         RealmsSelectFileToUploadScreen.this.f_96547_.m_92883_(p_167475_, s, (float)(p_167477_ + 2), (float)(p_167478_ + 1), 16777215);
         RealmsSelectFileToUploadScreen.this.f_96547_.m_92883_(p_167475_, this.f_89556_, (float)(p_167477_ + 2), (float)(p_167478_ + 12), 8421504);
         RealmsSelectFileToUploadScreen.this.f_96547_.m_92889_(p_167475_, this.f_89557_, (float)(p_167477_ + 2), (float)(p_167478_ + 12 + 10), 8421504);
      }

      public Component m_142172_() {
         Component component = CommonComponents.m_178396_(new TextComponent(this.f_89554_.m_78361_()), new TextComponent(RealmsSelectFileToUploadScreen.m_89538_(this.f_89554_)), RealmsSelectFileToUploadScreen.m_89534_(this.f_89554_));
         return new TranslatableComponent("narrator.select", component);
      }
   }

   @OnlyIn(Dist.CLIENT)
   class WorldSelectionList extends RealmsObjectSelectionList<RealmsSelectFileToUploadScreen.Entry> {
      public WorldSelectionList() {
         super(RealmsSelectFileToUploadScreen.this.f_96543_, RealmsSelectFileToUploadScreen.this.f_96544_, RealmsSelectFileToUploadScreen.m_120774_(0), RealmsSelectFileToUploadScreen.this.f_96544_ - 40, 36);
      }

      public void m_89587_(LevelSummary p_89588_) {
         this.m_7085_(RealmsSelectFileToUploadScreen.this.new Entry(p_89588_));
      }

      public int m_5775_() {
         return RealmsSelectFileToUploadScreen.this.f_89492_.size() * 36;
      }

      public boolean m_5694_() {
         return RealmsSelectFileToUploadScreen.this.m_7222_() == this;
      }

      public void m_7733_(PoseStack p_89590_) {
         RealmsSelectFileToUploadScreen.this.m_7333_(p_89590_);
      }

      public void m_6987_(@Nullable RealmsSelectFileToUploadScreen.Entry p_89592_) {
         super.m_6987_(p_89592_);
         RealmsSelectFileToUploadScreen.this.f_89493_ = this.m_6702_().indexOf(p_89592_);
         RealmsSelectFileToUploadScreen.this.f_89491_.f_93623_ = RealmsSelectFileToUploadScreen.this.f_89493_ >= 0 && RealmsSelectFileToUploadScreen.this.f_89493_ < this.m_5773_() && !RealmsSelectFileToUploadScreen.this.f_89492_.get(RealmsSelectFileToUploadScreen.this.f_89493_).m_78368_();
      }
   }
}