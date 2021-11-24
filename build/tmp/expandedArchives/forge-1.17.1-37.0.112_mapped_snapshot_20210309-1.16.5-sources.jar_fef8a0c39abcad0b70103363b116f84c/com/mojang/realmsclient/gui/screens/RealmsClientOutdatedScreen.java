package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsClientOutdatedScreen extends RealmsScreen {
   private static final Component f_88358_ = new TranslatableComponent("mco.client.outdated.title");
   private static final Component[] f_88359_ = new Component[]{new TranslatableComponent("mco.client.outdated.msg.line1"), new TranslatableComponent("mco.client.outdated.msg.line2")};
   private static final Component f_88360_ = new TranslatableComponent("mco.client.incompatible.title");
   private static final Component[] f_88361_ = new Component[]{new TranslatableComponent("mco.client.incompatible.msg.line1"), new TranslatableComponent("mco.client.incompatible.msg.line2"), new TranslatableComponent("mco.client.incompatible.msg.line3")};
   private final Screen f_88362_;
   private final boolean f_88363_;

   public RealmsClientOutdatedScreen(Screen p_88366_, boolean p_88367_) {
      super(p_88367_ ? f_88358_ : f_88360_);
      this.f_88362_ = p_88366_;
      this.f_88363_ = p_88367_;
   }

   public void m_7856_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, m_120774_(12), 200, 20, CommonComponents.f_130660_, (p_88378_) -> {
         this.f_96541_.m_91152_(this.f_88362_);
      }));
   }

   public void m_6305_(PoseStack p_88373_, int p_88374_, int p_88375_, float p_88376_) {
      this.m_7333_(p_88373_);
      m_93215_(p_88373_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, m_120774_(3), 16711680);
      Component[] acomponent = this.f_88363_ ? f_88361_ : f_88359_;

      for(int i = 0; i < acomponent.length; ++i) {
         m_93215_(p_88373_, this.f_96547_, acomponent[i], this.f_96543_ / 2, m_120774_(5) + i * 12, 16777215);
      }

      super.m_6305_(p_88373_, p_88374_, p_88375_, p_88376_);
   }

   public boolean m_7933_(int p_88369_, int p_88370_, int p_88371_) {
      if (p_88369_ != 257 && p_88369_ != 335 && p_88369_ != 256) {
         return super.m_7933_(p_88369_, p_88370_, p_88371_);
      } else {
         this.f_96541_.m_91152_(this.f_88362_);
         return true;
      }
   }
}