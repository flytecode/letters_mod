package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AlertScreen extends Screen {
   private final Runnable f_95515_;
   protected final Component f_95513_;
   private MultiLineLabel f_95516_ = MultiLineLabel.f_94331_;
   protected final Component f_95514_;

   public AlertScreen(Runnable p_95519_, Component p_95520_, Component p_95521_) {
      this(p_95519_, p_95520_, p_95521_, CommonComponents.f_130660_);
   }

   public AlertScreen(Runnable p_95523_, Component p_95524_, Component p_95525_, Component p_95526_) {
      super(p_95524_);
      this.f_95515_ = p_95523_;
      this.f_95513_ = p_95525_;
      this.f_95514_ = p_95526_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 6 + 168, 200, 20, this.f_95514_, (p_95533_) -> {
         this.f_95515_.run();
      }));
      this.f_95516_ = MultiLineLabel.m_94341_(this.f_96547_, this.f_95513_, this.f_96543_ - 50);
   }

   public void m_6305_(PoseStack p_95528_, int p_95529_, int p_95530_, float p_95531_) {
      this.m_7333_(p_95528_);
      m_93215_(p_95528_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 70, 16777215);
      this.f_95516_.m_6276_(p_95528_, this.f_96543_ / 2, 90);
      super.m_6305_(p_95528_, p_95529_, p_95530_, p_95531_);
   }
}