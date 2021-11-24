package net.minecraft.client.gui.screens;

import net.minecraft.Util;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AccessibilityOptionsScreen extends SimpleOptionsSubScreen {
   private static final Option[] f_95501_ = new Option[]{Option.f_91627_, Option.f_91644_, Option.f_91678_, Option.f_91630_, Option.f_91662_, Option.f_91665_, Option.f_91666_, Option.f_91631_, Option.f_91646_, Option.f_91647_, Option.f_91669_, Option.f_91668_, Option.f_168105_};
   private static final String f_169230_ = "https://aka.ms/MinecraftJavaAccessibility";

   public AccessibilityOptionsScreen(Screen p_95504_, Options p_95505_) {
      super(p_95504_, p_95505_, new TranslatableComponent("options.accessibility.title"), f_95501_);
   }

   protected void m_7853_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 155, this.f_96544_ - 27, 150, 20, new TranslatableComponent("options.accessibility.link"), (p_95509_) -> {
         this.f_96541_.m_91152_(new ConfirmLinkScreen((p_169232_) -> {
            if (p_169232_) {
               Util.m_137581_().m_137646_("https://aka.ms/MinecraftJavaAccessibility");
            }

            this.f_96541_.m_91152_(this);
         }, "https://aka.ms/MinecraftJavaAccessibility", true));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 5, this.f_96544_ - 27, 150, 20, CommonComponents.f_130655_, (p_95507_) -> {
         this.f_96541_.m_91152_(this.f_96281_);
      }));
   }
}