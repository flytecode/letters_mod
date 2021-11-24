package net.minecraft.client.gui.components;

import net.minecraft.client.Options;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractOptionSliderButton extends AbstractSliderButton {
   protected final Options f_93377_;

   protected AbstractOptionSliderButton(Options p_93379_, int p_93380_, int p_93381_, int p_93382_, int p_93383_, double p_93384_) {
      super(p_93380_, p_93381_, p_93382_, p_93383_, TextComponent.f_131282_, p_93384_);
      this.f_93377_ = p_93379_;
   }
}