package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PopupScreen extends Screen {
   private static final int f_169340_ = 20;
   private static final int f_169341_ = 5;
   private static final int f_169342_ = 20;
   private final Component f_169343_;
   private final FormattedText f_96339_;
   private final ImmutableList<PopupScreen.ButtonOption> f_96340_;
   private MultiLineLabel f_96341_ = MultiLineLabel.f_94331_;
   private int f_96342_;
   private int f_96343_;

   protected PopupScreen(Component p_96345_, List<Component> p_96346_, ImmutableList<PopupScreen.ButtonOption> p_96347_) {
      super(p_96345_);
      this.f_96339_ = FormattedText.m_130768_(p_96346_);
      this.f_169343_ = CommonComponents.m_178398_(p_96345_, ComponentUtils.m_178433_(p_96346_, TextComponent.f_131282_));
      this.f_96340_ = p_96347_;
   }

   public Component m_142562_() {
      return this.f_169343_;
   }

   public void m_7856_() {
      for(PopupScreen.ButtonOption popupscreen$buttonoption : this.f_96340_) {
         this.f_96343_ = Math.max(this.f_96343_, 20 + this.f_96547_.m_92852_(popupscreen$buttonoption.f_96359_) + 20);
      }

      int l = 5 + this.f_96343_ + 5;
      int i1 = l * this.f_96340_.size();
      this.f_96341_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_96339_, i1);
      int i = this.f_96341_.m_5770_() * 9;
      this.f_96342_ = (int)((double)this.f_96544_ / 2.0D - (double)i / 2.0D);
      int j = this.f_96342_ + i + 9 * 2;
      int k = (int)((double)this.f_96543_ / 2.0D - (double)i1 / 2.0D);

      for(PopupScreen.ButtonOption popupscreen$buttonoption1 : this.f_96340_) {
         this.m_142416_(new Button(k, j, this.f_96343_, 20, popupscreen$buttonoption1.f_96359_, popupscreen$buttonoption1.f_96360_));
         k += l;
      }

   }

   public void m_6305_(PoseStack p_96349_, int p_96350_, int p_96351_, float p_96352_) {
      this.m_96626_(0);
      m_93215_(p_96349_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96342_ - 9 * 2, -1);
      this.f_96341_.m_6276_(p_96349_, this.f_96543_ / 2, this.f_96342_);
      super.m_6305_(p_96349_, p_96350_, p_96351_, p_96352_);
   }

   public boolean m_6913_() {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class ButtonOption {
      final Component f_96359_;
      final Button.OnPress f_96360_;

      public ButtonOption(Component p_96362_, Button.OnPress p_96363_) {
         this.f_96359_ = p_96362_;
         this.f_96360_ = p_96363_;
      }
   }
}