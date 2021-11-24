package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CycleOption<T> extends Option {
   private final CycleOption.OptionSetter<T> f_90678_;
   private final Function<Options, T> f_167713_;
   private final Supplier<CycleButton.Builder<T>> f_167714_;
   private Function<Minecraft, CycleButton.TooltipSupplier<T>> f_167715_ = (p_167722_) -> {
      return (p_167728_) -> {
         return ImmutableList.of();
      };
   };

   private CycleOption(String p_167717_, Function<Options, T> p_167718_, CycleOption.OptionSetter<T> p_167719_, Supplier<CycleButton.Builder<T>> p_167720_) {
      super(p_167717_);
      this.f_167713_ = p_167718_;
      this.f_90678_ = p_167719_;
      this.f_167714_ = p_167720_;
   }

   public static <T> CycleOption<T> m_167737_(String p_167738_, List<T> p_167739_, Function<T, Component> p_167740_, Function<Options, T> p_167741_, CycleOption.OptionSetter<T> p_167742_) {
      return new CycleOption<>(p_167738_, p_167741_, p_167742_, () -> {
         return CycleButton.m_168894_(p_167740_).m_168950_(p_167739_);
      });
   }

   public static <T> CycleOption<T> m_167747_(String p_167748_, Supplier<List<T>> p_167749_, Function<T, Component> p_167750_, Function<Options, T> p_167751_, CycleOption.OptionSetter<T> p_167752_) {
      return new CycleOption<>(p_167748_, p_167751_, p_167752_, () -> {
         return CycleButton.m_168894_(p_167750_).m_168950_(p_167749_.get());
      });
   }

   public static <T> CycleOption<T> m_167729_(String p_167730_, List<T> p_167731_, List<T> p_167732_, BooleanSupplier p_167733_, Function<T, Component> p_167734_, Function<Options, T> p_167735_, CycleOption.OptionSetter<T> p_167736_) {
      return new CycleOption<>(p_167730_, p_167735_, p_167736_, () -> {
         return CycleButton.m_168894_(p_167734_).m_168955_(p_167733_, p_167731_, p_167732_);
      });
   }

   public static <T> CycleOption<T> m_167764_(String p_167765_, T[] p_167766_, Function<T, Component> p_167767_, Function<Options, T> p_167768_, CycleOption.OptionSetter<T> p_167769_) {
      return new CycleOption<>(p_167765_, p_167768_, p_167769_, () -> {
         return CycleButton.m_168894_(p_167767_).m_168961_(p_167766_);
      });
   }

   public static CycleOption<Boolean> m_167758_(String p_167759_, Component p_167760_, Component p_167761_, Function<Options, Boolean> p_167762_, CycleOption.OptionSetter<Boolean> p_167763_) {
      return new CycleOption<>(p_167759_, p_167762_, p_167763_, () -> {
         return CycleButton.m_168896_(p_167760_, p_167761_);
      });
   }

   public static CycleOption<Boolean> m_167743_(String p_167744_, Function<Options, Boolean> p_167745_, CycleOption.OptionSetter<Boolean> p_167746_) {
      return new CycleOption<>(p_167744_, p_167745_, p_167746_, CycleButton::m_168919_);
   }

   public static CycleOption<Boolean> m_167753_(String p_167754_, Component p_167755_, Function<Options, Boolean> p_167756_, CycleOption.OptionSetter<Boolean> p_167757_) {
      return m_167743_(p_167754_, p_167756_, p_167757_).m_167773_((p_167791_) -> {
         List<FormattedCharSequence> list = p_167791_.f_91062_.m_92923_(p_167755_, 200);
         return (p_167772_) -> {
            return list;
         };
      });
   }

   public CycleOption<T> m_167773_(Function<Minecraft, CycleButton.TooltipSupplier<T>> p_167774_) {
      this.f_167715_ = p_167774_;
      return this;
   }

   public AbstractWidget m_7496_(Options p_90688_, int p_90689_, int p_90690_, int p_90691_) {
      CycleButton.TooltipSupplier<T> tooltipsupplier = this.f_167715_.apply(Minecraft.m_91087_());
      return this.f_167714_.get().m_168943_(tooltipsupplier).m_168948_(this.f_167713_.apply(p_90688_)).m_168936_(p_90689_, p_90690_, p_90691_, 20, this.m_91714_(), (p_167725_, p_167726_) -> {
         this.f_90678_.m_167795_(p_90688_, this, p_167726_);
         p_90688_.m_92169_();
      });
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface OptionSetter<T> {
      void m_167795_(Options p_167796_, Option p_167797_, T p_167798_);
   }
}