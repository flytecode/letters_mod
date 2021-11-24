package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PauseScreen extends Screen {
   private static final String f_169332_ = "https://aka.ms/snapshotfeedback?ref=game";
   private static final String f_169333_ = "https://aka.ms/javafeedback?ref=game";
   private static final String f_169334_ = "https://aka.ms/snapshotbugs?ref=game";
   private final boolean f_96306_;

   public PauseScreen(boolean p_96308_) {
      super(p_96308_ ? new TranslatableComponent("menu.game") : new TranslatableComponent("menu.paused"));
      this.f_96306_ = p_96308_;
   }

   protected void m_7856_() {
      if (this.f_96306_) {
         this.m_96338_();
      }

   }

   private void m_96338_() {
      int i = -16;
      int j = 98;
      this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 24 + -16, 204, 20, new TranslatableComponent("menu.returnToGame"), (p_96337_) -> {
         this.f_96541_.m_91152_((Screen)null);
         this.f_96541_.f_91067_.m_91601_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 48 + -16, 98, 20, new TranslatableComponent("gui.advancements"), (p_96335_) -> {
         this.f_96541_.m_91152_(new AdvancementsScreen(this.f_96541_.f_91074_.f_108617_.m_105145_()));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 48 + -16, 98, 20, new TranslatableComponent("gui.stats"), (p_96333_) -> {
         this.f_96541_.m_91152_(new StatsScreen(this, this.f_96541_.f_91074_.m_108630_()));
      }));
      String s = SharedConstants.m_136187_().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 72 + -16, 98, 20, new TranslatableComponent("menu.sendFeedback"), (p_96318_) -> {
         this.f_96541_.m_91152_(new ConfirmLinkScreen((p_169337_) -> {
            if (p_169337_) {
               Util.m_137581_().m_137646_(s);
            }

            this.f_96541_.m_91152_(this);
         }, s, true));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 72 + -16, 98, 20, new TranslatableComponent("menu.reportBugs"), (p_96331_) -> {
         this.f_96541_.m_91152_(new ConfirmLinkScreen((p_169339_) -> {
            if (p_169339_) {
               Util.m_137581_().m_137646_("https://aka.ms/snapshotbugs?ref=game");
            }

            this.f_96541_.m_91152_(this);
         }, "https://aka.ms/snapshotbugs?ref=game", true));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 96 + -16, 98, 20, new TranslatableComponent("menu.options"), (p_96323_) -> {
         this.f_96541_.m_91152_(new OptionsScreen(this, this.f_96541_.f_91066_));
      }));
      Button button = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ / 4 + 96 + -16, 98, 20, new TranslatableComponent("menu.shareToLan"), (p_96321_) -> {
         this.f_96541_.m_91152_(new ShareToLanScreen(this));
      }));
      button.f_93623_ = this.f_96541_.m_91091_() && !this.f_96541_.m_91092_().m_6992_();
      Component component = this.f_96541_.m_91090_() ? new TranslatableComponent("menu.returnToMenu") : new TranslatableComponent("menu.disconnect");
      this.m_142416_(new Button(this.f_96543_ / 2 - 102, this.f_96544_ / 4 + 120 + -16, 204, 20, component, (p_96315_) -> {
         boolean flag = this.f_96541_.m_91090_();
         boolean flag1 = this.f_96541_.m_91294_();
         p_96315_.f_93623_ = false;
         this.f_96541_.f_91073_.m_7462_();
         if (flag) {
            this.f_96541_.m_91320_(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
         } else {
            this.f_96541_.m_91399_();
         }

         TitleScreen titlescreen = new TitleScreen();
         if (flag) {
            this.f_96541_.m_91152_(titlescreen);
         } else if (flag1) {
            this.f_96541_.m_91152_(new RealmsMainScreen(titlescreen));
         } else {
            this.f_96541_.m_91152_(new JoinMultiplayerScreen(titlescreen));
         }

      }));
   }

   public void m_96624_() {
      super.m_96624_();
   }

   public void m_6305_(PoseStack p_96310_, int p_96311_, int p_96312_, float p_96313_) {
      if (this.f_96306_) {
         this.m_7333_(p_96310_);
         m_93215_(p_96310_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 40, 16777215);
      } else {
         m_93215_(p_96310_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 16777215);
      }

      super.m_6305_(p_96310_, p_96311_, p_96312_, p_96313_);
   }
}