package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsParentalConsentScreen extends RealmsScreen {
   private static final Component f_88856_ = new TranslatableComponent("mco.account.privacyinfo");
   private final Screen f_88857_;
   private MultiLineLabel f_88858_ = MultiLineLabel.f_94331_;

   public RealmsParentalConsentScreen(Screen p_88861_) {
      super(NarratorChatListener.f_93310_);
      this.f_88857_ = p_88861_;
   }

   public void m_7856_() {
      Component component = new TranslatableComponent("mco.account.update");
      Component component1 = CommonComponents.f_130660_;
      int i = Math.max(this.f_96547_.m_92852_(component), this.f_96547_.m_92852_(component1)) + 30;
      Component component2 = new TranslatableComponent("mco.account.privacy.info");
      int j = (int)((double)this.f_96547_.m_92852_(component2) * 1.2D);
      this.m_142416_(new Button(this.f_96543_ / 2 - j / 2, m_120774_(11), j, 20, component2, (p_88873_) -> {
         Util.m_137581_().m_137646_("https://aka.ms/MinecraftGDPR");
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - (i + 5), m_120774_(13), i, 20, component, (p_88871_) -> {
         Util.m_137581_().m_137646_("https://aka.ms/UpdateMojangAccount");
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, m_120774_(13), i, 20, component1, (p_88868_) -> {
         this.f_96541_.m_91152_(this.f_88857_);
      }));
      this.f_88858_ = MultiLineLabel.m_94341_(this.f_96547_, f_88856_, (int)Math.round((double)this.f_96543_ * 0.9D));
   }

   public Component m_142562_() {
      return f_88856_;
   }

   public void m_6305_(PoseStack p_88863_, int p_88864_, int p_88865_, float p_88866_) {
      this.m_7333_(p_88863_);
      this.f_88858_.m_6514_(p_88863_, this.f_96543_ / 2, 15, 15, 16777215);
      super.m_6305_(p_88863_, p_88864_, p_88865_, p_88866_);
   }
}