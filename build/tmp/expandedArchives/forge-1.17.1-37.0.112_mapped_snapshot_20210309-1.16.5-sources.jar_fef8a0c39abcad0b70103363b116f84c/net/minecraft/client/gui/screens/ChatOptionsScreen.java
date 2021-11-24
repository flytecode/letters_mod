package net.minecraft.client.gui.screens;

import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatOptionsScreen extends SimpleOptionsSubScreen {
   private static final Option[] f_95568_ = new Option[]{Option.f_91681_, Option.f_91634_, Option.f_91635_, Option.f_91636_, Option.f_91662_, Option.f_91678_, Option.f_91663_, Option.f_91665_, Option.f_91666_, Option.f_91664_, Option.f_91660_, Option.f_91661_, Option.f_91627_, Option.f_91632_, Option.f_91633_, Option.f_91643_};

   public ChatOptionsScreen(Screen p_95571_, Options p_95572_) {
      super(p_95571_, p_95572_, new TranslatableComponent("options.chat.title"), f_95568_);
   }
}