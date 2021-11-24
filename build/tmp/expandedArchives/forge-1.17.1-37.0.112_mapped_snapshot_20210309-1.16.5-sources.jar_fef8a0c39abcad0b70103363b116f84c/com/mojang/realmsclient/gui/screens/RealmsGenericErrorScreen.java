package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsGenericErrorScreen extends RealmsScreen {
   private final Screen f_88665_;
   private Component f_88666_;
   private Component f_88667_;

   public RealmsGenericErrorScreen(RealmsServiceException p_88669_, Screen p_88670_) {
      super(NarratorChatListener.f_93310_);
      this.f_88665_ = p_88670_;
      this.m_88683_(p_88669_);
   }

   public RealmsGenericErrorScreen(Component p_88672_, Screen p_88673_) {
      super(NarratorChatListener.f_93310_);
      this.f_88665_ = p_88673_;
      this.m_88687_(p_88672_);
   }

   public RealmsGenericErrorScreen(Component p_88675_, Component p_88676_, Screen p_88677_) {
      super(NarratorChatListener.f_93310_);
      this.f_88665_ = p_88677_;
      this.m_88689_(p_88675_, p_88676_);
   }

   private void m_88683_(RealmsServiceException p_88684_) {
      if (p_88684_.f_87775_ == -1) {
         this.f_88666_ = new TextComponent("An error occurred (" + p_88684_.f_87773_ + "):");
         this.f_88667_ = new TextComponent(p_88684_.f_87774_);
      } else {
         this.f_88666_ = new TextComponent("Realms (" + p_88684_.f_87775_ + "):");
         String s = "mco.errorMessage." + p_88684_.f_87775_;
         this.f_88667_ = (Component)(I18n.m_118936_(s) ? new TranslatableComponent(s) : Component.m_130674_(p_88684_.f_87776_));
      }

   }

   private void m_88687_(Component p_88688_) {
      this.f_88666_ = new TextComponent("An error occurred: ");
      this.f_88667_ = p_88688_;
   }

   private void m_88689_(Component p_88690_, Component p_88691_) {
      this.f_88666_ = p_88690_;
      this.f_88667_ = p_88691_;
   }

   public void m_7856_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 52, 200, 20, new TextComponent("Ok"), (p_88686_) -> {
         this.f_96541_.m_91152_(this.f_88665_);
      }));
   }

   public Component m_142562_() {
      return (new TextComponent("")).m_7220_(this.f_88666_).m_130946_(": ").m_7220_(this.f_88667_);
   }

    @Override
    public boolean m_7933_(int key, int scanCode, int modifiers) {
       if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE) {
          f_96541_.m_91152_(this.f_88665_);
          return true;
       }
       return super.m_7933_(key, scanCode, modifiers);
    }

   public void m_6305_(PoseStack p_88679_, int p_88680_, int p_88681_, float p_88682_) {
      this.m_7333_(p_88679_);
      m_93215_(p_88679_, this.f_96547_, this.f_88666_, this.f_96543_ / 2, 80, 16777215);
      m_93215_(p_88679_, this.f_96547_, this.f_88667_, this.f_96543_ / 2, 100, 16711680);
      super.m_6305_(p_88679_, p_88680_, p_88681_, p_88682_);
   }
}
