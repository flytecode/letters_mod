package net.minecraft.realms;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DisconnectedRealmsScreen extends RealmsScreen {
   private final Component f_120648_;
   private MultiLineLabel f_120649_ = MultiLineLabel.f_94331_;
   private final Screen f_120650_;
   private int f_120651_;

   public DisconnectedRealmsScreen(Screen p_120653_, Component p_120654_, Component p_120655_) {
      super(p_120654_);
      this.f_120650_ = p_120653_;
      this.f_120648_ = p_120655_;
   }

   public void m_7856_() {
      Minecraft minecraft = Minecraft.m_91087_();
      minecraft.m_91372_(false);
      minecraft.m_91100_().m_118586_();
      this.f_120649_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_120648_, this.f_96543_ - 50);
      this.f_120651_ = this.f_120649_.m_5770_() * 9;
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 2 + this.f_120651_ / 2 + 9, 200, 20, CommonComponents.f_130660_, (p_120663_) -> {
         minecraft.m_91152_(this.f_120650_);
      }));
   }

   public Component m_142562_() {
      return (new TextComponent("")).m_7220_(this.f_96539_).m_130946_(": ").m_7220_(this.f_120648_);
   }

   public void m_7379_() {
      Minecraft.m_91087_().m_91152_(this.f_120650_);
   }

   public void m_6305_(PoseStack p_120657_, int p_120658_, int p_120659_, float p_120660_) {
      this.m_7333_(p_120657_);
      m_93215_(p_120657_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_120651_ / 2 - 9 * 2, 11184810);
      this.f_120649_.m_6276_(p_120657_, this.f_96543_ / 2, this.f_96544_ / 2 - this.f_120651_ / 2);
      super.m_6305_(p_120657_, p_120658_, p_120659_, p_120660_);
   }
}