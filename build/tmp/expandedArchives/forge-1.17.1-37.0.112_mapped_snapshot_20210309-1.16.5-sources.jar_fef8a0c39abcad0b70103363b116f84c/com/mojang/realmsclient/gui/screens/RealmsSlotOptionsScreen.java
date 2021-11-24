package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import java.util.List;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsSlotOptionsScreen extends RealmsScreen {
   private static final int f_167511_ = 2;
   public static final List<Difficulty> f_89870_ = ImmutableList.of(Difficulty.PEACEFUL, Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD);
   private static final int f_167512_ = 0;
   public static final List<GameType> f_89871_ = ImmutableList.of(GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE);
   private static final Component f_89876_ = new TranslatableComponent("mco.configure.world.edit.slot.name");
   static final Component f_167513_ = new TranslatableComponent("mco.configure.world.spawnProtection");
   private EditBox f_89877_;
   protected final RealmsConfigureWorldScreen f_89872_;
   private int f_89878_;
   private int f_89879_;
   private final RealmsWorldOptions f_89881_;
   private final RealmsServer.WorldType f_89882_;
   private final int f_89883_;
   private Difficulty f_89852_;
   private GameType f_89853_;
   private boolean f_89854_;
   private boolean f_89855_;
   private boolean f_89856_;
   private boolean f_89857_;
   int f_89858_;
   private boolean f_89859_;
   private boolean f_89860_;
   RealmsSlotOptionsScreen.SettingsSlider f_89865_;

   public RealmsSlotOptionsScreen(RealmsConfigureWorldScreen p_89886_, RealmsWorldOptions p_89887_, RealmsServer.WorldType p_89888_, int p_89889_) {
      super(new TranslatableComponent("mco.configure.world.buttons.options"));
      this.f_89872_ = p_89886_;
      this.f_89881_ = p_89887_;
      this.f_89882_ = p_89888_;
      this.f_89883_ = p_89889_;
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   public void m_96624_() {
      this.f_89877_.m_94120_();
   }

   public boolean m_7933_(int p_89891_, int p_89892_, int p_89893_) {
      if (p_89891_ == 256) {
         this.f_96541_.m_91152_(this.f_89872_);
         return true;
      } else {
         return super.m_7933_(p_89891_, p_89892_, p_89893_);
      }
   }

   private static <T> T m_167524_(List<T> p_167525_, int p_167526_, int p_167527_) {
      try {
         return p_167525_.get(p_167526_);
      } catch (IndexOutOfBoundsException indexoutofboundsexception) {
         return p_167525_.get(p_167527_);
      }
   }

   private static <T> int m_167528_(List<T> p_167529_, T p_167530_, int p_167531_) {
      int i = p_167529_.indexOf(p_167530_);
      return i == -1 ? p_167531_ : i;
   }

   public void m_7856_() {
      this.f_89879_ = 170;
      this.f_89878_ = this.f_96543_ / 2 - this.f_89879_;
      int i = this.f_96543_ / 2 + 10;
      this.f_89852_ = m_167524_(f_89870_, this.f_89881_.f_87605_, 2);
      this.f_89853_ = m_167524_(f_89871_, this.f_89881_.f_87606_, 0);
      if (this.f_89882_ == RealmsServer.WorldType.NORMAL) {
         this.f_89854_ = this.f_89881_.f_87598_;
         this.f_89858_ = this.f_89881_.f_87602_;
         this.f_89860_ = this.f_89881_.f_87604_;
         this.f_89856_ = this.f_89881_.f_87599_;
         this.f_89857_ = this.f_89881_.f_87600_;
         this.f_89855_ = this.f_89881_.f_87601_;
         this.f_89859_ = this.f_89881_.f_87603_;
      } else {
         Component component;
         if (this.f_89882_ == RealmsServer.WorldType.ADVENTUREMAP) {
            component = new TranslatableComponent("mco.configure.world.edit.subscreen.adventuremap");
         } else if (this.f_89882_ == RealmsServer.WorldType.INSPIRATION) {
            component = new TranslatableComponent("mco.configure.world.edit.subscreen.inspiration");
         } else {
            component = new TranslatableComponent("mco.configure.world.edit.subscreen.experience");
         }

         this.m_175073_(new RealmsLabel(component, this.f_96543_ / 2, 26, 16711680));
         this.f_89854_ = true;
         this.f_89858_ = 0;
         this.f_89860_ = false;
         this.f_89856_ = true;
         this.f_89857_ = true;
         this.f_89855_ = true;
         this.f_89859_ = true;
      }

      this.f_89877_ = new EditBox(this.f_96541_.f_91062_, this.f_89878_ + 2, m_120774_(1), this.f_89879_ - 4, 20, (EditBox)null, new TranslatableComponent("mco.configure.world.edit.slot.name"));
      this.f_89877_.m_94199_(10);
      this.f_89877_.m_94144_(this.f_89881_.m_87626_(this.f_89883_));
      this.m_94725_(this.f_89877_);
      CycleButton<Boolean> cyclebutton5 = this.m_142416_(CycleButton.m_168916_(this.f_89854_).m_168936_(i, m_120774_(1), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.pvp"), (p_167546_, p_167547_) -> {
         this.f_89854_ = p_167547_;
      }));
      this.m_142416_(CycleButton.m_168894_(GameType::m_151500_).m_168950_(f_89871_).m_168948_(this.f_89853_).m_168936_(this.f_89878_, m_120774_(3), this.f_89879_, 20, new TranslatableComponent("selectWorld.gameMode"), (p_167515_, p_167516_) -> {
         this.f_89853_ = p_167516_;
      }));
      CycleButton<Boolean> cyclebutton = this.m_142416_(CycleButton.m_168916_(this.f_89856_).m_168936_(i, m_120774_(3), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.spawnAnimals"), (p_167543_, p_167544_) -> {
         this.f_89856_ = p_167544_;
      }));
      CycleButton<Boolean> cyclebutton1 = CycleButton.m_168916_(this.f_89852_ != Difficulty.PEACEFUL && this.f_89857_).m_168936_(i, m_120774_(5), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.spawnMonsters"), (p_167540_, p_167541_) -> {
         this.f_89857_ = p_167541_;
      });
      this.m_142416_(CycleButton.m_168894_(Difficulty::m_19033_).m_168950_(f_89870_).m_168948_(this.f_89852_).m_168936_(this.f_89878_, m_120774_(5), this.f_89879_, 20, new TranslatableComponent("options.difficulty"), (p_167519_, p_167520_) -> {
         this.f_89852_ = p_167520_;
         if (this.f_89882_ == RealmsServer.WorldType.NORMAL) {
            boolean flag = this.f_89852_ != Difficulty.PEACEFUL;
            cyclebutton1.f_93623_ = flag;
            cyclebutton1.m_168892_(flag && this.f_89857_);
         }

      }));
      this.m_142416_(cyclebutton1);
      this.f_89865_ = this.m_142416_(new RealmsSlotOptionsScreen.SettingsSlider(this.f_89878_, m_120774_(7), this.f_89879_, this.f_89858_, 0.0F, 16.0F));
      CycleButton<Boolean> cyclebutton2 = this.m_142416_(CycleButton.m_168916_(this.f_89855_).m_168936_(i, m_120774_(7), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.spawnNPCs"), (p_167537_, p_167538_) -> {
         this.f_89855_ = p_167538_;
      }));
      CycleButton<Boolean> cyclebutton3 = this.m_142416_(CycleButton.m_168916_(this.f_89860_).m_168936_(this.f_89878_, m_120774_(9), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.forceGameMode"), (p_167534_, p_167535_) -> {
         this.f_89860_ = p_167535_;
      }));
      CycleButton<Boolean> cyclebutton4 = this.m_142416_(CycleButton.m_168916_(this.f_89859_).m_168936_(i, m_120774_(9), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.commandBlocks"), (p_167522_, p_167523_) -> {
         this.f_89859_ = p_167523_;
      }));
      if (this.f_89882_ != RealmsServer.WorldType.NORMAL) {
         cyclebutton5.f_93623_ = false;
         cyclebutton.f_93623_ = false;
         cyclebutton2.f_93623_ = false;
         cyclebutton1.f_93623_ = false;
         this.f_89865_.f_93623_ = false;
         cyclebutton4.f_93623_ = false;
         cyclebutton3.f_93623_ = false;
      }

      if (this.f_89852_ == Difficulty.PEACEFUL) {
         cyclebutton1.f_93623_ = false;
      }

      this.m_142416_(new Button(this.f_89878_, m_120774_(13), this.f_89879_, 20, new TranslatableComponent("mco.configure.world.buttons.done"), (p_89910_) -> {
         this.m_89940_();
      }));
      this.m_142416_(new Button(i, m_120774_(13), this.f_89879_, 20, CommonComponents.f_130656_, (p_89905_) -> {
         this.f_96541_.m_91152_(this.f_89872_);
      }));
      this.m_7787_(this.f_89877_);
   }

   public Component m_142562_() {
      return CommonComponents.m_178398_(this.m_96636_(), this.m_175075_());
   }

   public void m_6305_(PoseStack p_89895_, int p_89896_, int p_89897_, float p_89898_) {
      this.m_7333_(p_89895_);
      m_93215_(p_89895_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 17, 16777215);
      this.f_96547_.m_92889_(p_89895_, f_89876_, (float)(this.f_89878_ + this.f_89879_ / 2 - this.f_96547_.m_92852_(f_89876_) / 2), (float)(m_120774_(0) - 5), 16777215);
      this.f_89877_.m_6305_(p_89895_, p_89896_, p_89897_, p_89898_);
      super.m_6305_(p_89895_, p_89896_, p_89897_, p_89898_);
   }

   private String m_89939_() {
      return this.f_89877_.m_94155_().equals(this.f_89881_.m_87633_(this.f_89883_)) ? "" : this.f_89877_.m_94155_();
   }

   private void m_89940_() {
      int i = m_167528_(f_89870_, this.f_89852_, 2);
      int j = m_167528_(f_89871_, this.f_89853_, 0);
      if (this.f_89882_ != RealmsServer.WorldType.ADVENTUREMAP && this.f_89882_ != RealmsServer.WorldType.EXPERIENCE && this.f_89882_ != RealmsServer.WorldType.INSPIRATION) {
         this.f_89872_.m_88444_(new RealmsWorldOptions(this.f_89854_, this.f_89856_, this.f_89857_, this.f_89855_, this.f_89858_, this.f_89859_, i, j, this.f_89860_, this.m_89939_()));
      } else {
         this.f_89872_.m_88444_(new RealmsWorldOptions(this.f_89881_.f_87598_, this.f_89881_.f_87599_, this.f_89881_.f_87600_, this.f_89881_.f_87601_, this.f_89881_.f_87602_, this.f_89881_.f_87603_, i, j, this.f_89881_.f_87604_, this.m_89939_()));
      }

   }

   @OnlyIn(Dist.CLIENT)
   class SettingsSlider extends AbstractSliderButton {
      private final double f_89942_;
      private final double f_89943_;

      public SettingsSlider(int p_89946_, int p_89947_, int p_89948_, int p_89949_, float p_89950_, float p_89951_) {
         super(p_89946_, p_89947_, p_89948_, 20, TextComponent.f_131282_, 0.0D);
         this.f_89942_ = (double)p_89950_;
         this.f_89943_ = (double)p_89951_;
         this.f_93577_ = (double)((Mth.m_14036_((float)p_89949_, p_89950_, p_89951_) - p_89950_) / (p_89951_ - p_89950_));
         this.m_5695_();
      }

      public void m_5697_() {
         if (RealmsSlotOptionsScreen.this.f_89865_.f_93623_) {
            RealmsSlotOptionsScreen.this.f_89858_ = (int)Mth.m_14139_(Mth.m_14008_(this.f_93577_, 0.0D, 1.0D), this.f_89942_, this.f_89943_);
         }
      }

      protected void m_5695_() {
         this.m_93666_(CommonComponents.m_178393_(RealmsSlotOptionsScreen.f_167513_, (Component)(RealmsSlotOptionsScreen.this.f_89858_ == 0 ? CommonComponents.f_130654_ : new TextComponent(String.valueOf(RealmsSlotOptionsScreen.this.f_89858_)))));
      }

      public void m_5716_(double p_89954_, double p_89955_) {
      }

      public void m_7691_(double p_89957_, double p_89958_) {
      }
   }
}