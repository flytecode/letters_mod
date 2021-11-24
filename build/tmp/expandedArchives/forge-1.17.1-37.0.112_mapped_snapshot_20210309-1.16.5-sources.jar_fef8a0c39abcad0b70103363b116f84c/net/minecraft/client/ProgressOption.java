package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ProgressOption extends Option {
   protected final float f_92204_;
   protected final double f_92205_;
   protected double f_92206_;
   private final Function<Options, Double> f_92207_;
   private final BiConsumer<Options, Double> f_92208_;
   private final BiFunction<Options, ProgressOption, Component> f_92209_;
   private final Function<Minecraft, List<FormattedCharSequence>> f_168538_;

   public ProgressOption(String p_168540_, double p_168541_, double p_168542_, float p_168543_, Function<Options, Double> p_168544_, BiConsumer<Options, Double> p_168545_, BiFunction<Options, ProgressOption, Component> p_168546_, Function<Minecraft, List<FormattedCharSequence>> p_168547_) {
      super(p_168540_);
      this.f_92205_ = p_168541_;
      this.f_92206_ = p_168542_;
      this.f_92204_ = p_168543_;
      this.f_92207_ = p_168544_;
      this.f_92208_ = p_168545_;
      this.f_92209_ = p_168546_;
      this.f_168538_ = p_168547_;
   }

   public ProgressOption(String p_92211_, double p_92212_, double p_92213_, float p_92214_, Function<Options, Double> p_92215_, BiConsumer<Options, Double> p_92216_, BiFunction<Options, ProgressOption, Component> p_92217_) {
      this(p_92211_, p_92212_, p_92213_, p_92214_, p_92215_, p_92216_, p_92217_, (p_168549_) -> {
         return ImmutableList.of();
      });
   }

   public AbstractWidget m_7496_(Options p_92227_, int p_92228_, int p_92229_, int p_92230_) {
      List<FormattedCharSequence> list = this.f_168538_.apply(Minecraft.m_91087_());
      return new SliderButton(p_92227_, p_92228_, p_92229_, p_92230_, 20, this, list);
   }

   public double m_6917_(double p_92218_) {
      return Mth.m_14008_((this.m_92236_(p_92218_) - this.f_92205_) / (this.f_92206_ - this.f_92205_), 0.0D, 1.0D);
   }

   public double m_6912_(double p_92231_) {
      return this.m_92236_(Mth.m_14139_(Mth.m_14008_(p_92231_, 0.0D, 1.0D), this.f_92205_, this.f_92206_));
   }

   private double m_92236_(double p_92237_) {
      if (this.f_92204_ > 0.0F) {
         p_92237_ = (double)(this.f_92204_ * (float)Math.round(p_92237_ / (double)this.f_92204_));
      }

      return Mth.m_14008_(p_92237_, this.f_92205_, this.f_92206_);
   }

   public double m_92232_() {
      return this.f_92205_;
   }

   public double m_92235_() {
      return this.f_92206_;
   }

   public void m_92219_(float p_92220_) {
      this.f_92206_ = (double)p_92220_;
   }

   public void m_92223_(Options p_92224_, double p_92225_) {
      this.f_92208_.accept(p_92224_, p_92225_);
   }

   public double m_92221_(Options p_92222_) {
      return this.f_92207_.apply(p_92222_);
   }

   public Component m_92233_(Options p_92234_) {
      return this.f_92209_.apply(p_92234_, this);
   }
}