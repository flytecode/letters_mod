package net.minecraft.client.gui.components;

import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SliderButton extends AbstractOptionSliderButton implements TooltipAccessor {
   private final ProgressOption f_94569_;
   private final List<FormattedCharSequence> f_169058_;

   public SliderButton(Options p_169060_, int p_169061_, int p_169062_, int p_169063_, int p_169064_, ProgressOption p_169065_, List<FormattedCharSequence> p_169066_) {
      super(p_169060_, p_169061_, p_169062_, p_169063_, p_169064_, (double)((float)p_169065_.m_6917_(p_169065_.m_92221_(p_169060_))));
      this.f_94569_ = p_169065_;
      this.f_169058_ = p_169066_;
      this.m_5695_();
   }

   protected void m_5697_() {
      this.f_94569_.m_92223_(this.f_93377_, this.f_94569_.m_6912_(this.f_93577_));
      this.f_93377_.m_92169_();
   }

   protected void m_5695_() {
      this.m_93666_(this.f_94569_.m_92233_(this.f_93377_));
   }

   public List<FormattedCharSequence> m_141932_() {
      return this.f_169058_;
   }
}