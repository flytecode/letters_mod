package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CycleButton<T> extends AbstractButton implements TooltipAccessor {
   static final BooleanSupplier f_168856_ = Screen::m_96639_;
   private static final List<Boolean> f_168857_ = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
   private final Component f_168858_;
   private int f_168859_;
   private T f_168860_;
   private final CycleButton.ValueListSupplier<T> f_168861_;
   private final Function<T, Component> f_168862_;
   private final Function<CycleButton<T>, MutableComponent> f_168863_;
   private final CycleButton.OnValueChange<T> f_168864_;
   private final CycleButton.TooltipSupplier<T> f_168865_;
   private final boolean f_168866_;

   CycleButton(int p_168869_, int p_168870_, int p_168871_, int p_168872_, Component p_168873_, Component p_168874_, int p_168875_, T p_168876_, CycleButton.ValueListSupplier<T> p_168877_, Function<T, Component> p_168878_, Function<CycleButton<T>, MutableComponent> p_168879_, CycleButton.OnValueChange<T> p_168880_, CycleButton.TooltipSupplier<T> p_168881_, boolean p_168882_) {
      super(p_168869_, p_168870_, p_168871_, p_168872_, p_168873_);
      this.f_168858_ = p_168874_;
      this.f_168859_ = p_168875_;
      this.f_168860_ = p_168876_;
      this.f_168861_ = p_168877_;
      this.f_168862_ = p_168878_;
      this.f_168863_ = p_168879_;
      this.f_168864_ = p_168880_;
      this.f_168865_ = p_168881_;
      this.f_168866_ = p_168882_;
   }

   public void m_5691_() {
      if (Screen.m_96638_()) {
         this.m_168908_(-1);
      } else {
         this.m_168908_(1);
      }

   }

   private void m_168908_(int p_168909_) {
      List<T> list = this.f_168861_.m_142477_();
      this.f_168859_ = Mth.m_14100_(this.f_168859_ + p_168909_, list.size());
      T t = list.get(this.f_168859_);
      this.m_168905_(t);
      this.f_168864_.m_168965_(this, t);
   }

   private T m_168914_(int p_168915_) {
      List<T> list = this.f_168861_.m_142477_();
      return list.get(Mth.m_14100_(this.f_168859_ + p_168915_, list.size()));
   }

   public boolean m_6050_(double p_168885_, double p_168886_, double p_168887_) {
      if (p_168887_ > 0.0D) {
         this.m_168908_(-1);
      } else if (p_168887_ < 0.0D) {
         this.m_168908_(1);
      }

      return true;
   }

   public void m_168892_(T p_168893_) {
      List<T> list = this.f_168861_.m_142477_();
      int i = list.indexOf(p_168893_);
      if (i != -1) {
         this.f_168859_ = i;
      }

      this.m_168905_(p_168893_);
   }

   private void m_168905_(T p_168906_) {
      Component component = this.m_168910_(p_168906_);
      this.m_93666_(component);
      this.f_168860_ = p_168906_;
   }

   private Component m_168910_(T p_168911_) {
      return (Component)(this.f_168866_ ? this.f_168862_.apply(p_168911_) : this.m_168912_(p_168911_));
   }

   private MutableComponent m_168912_(T p_168913_) {
      return CommonComponents.m_178393_(this.f_168858_, this.f_168862_.apply(p_168913_));
   }

   public T m_168883_() {
      return this.f_168860_;
   }

   protected MutableComponent m_5646_() {
      return this.f_168863_.apply(this);
   }

   public void m_142291_(NarrationElementOutput p_168889_) {
      p_168889_.m_169146_(NarratedElementType.TITLE, this.m_5646_());
      if (this.f_93623_) {
         T t = this.m_168914_(1);
         Component component = this.m_168910_(t);
         if (this.m_93696_()) {
            p_168889_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.cycle_button.usage.focused", component));
         } else {
            p_168889_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.cycle_button.usage.hovered", component));
         }
      }

   }

   public MutableComponent m_168904_() {
      return m_168799_((Component)(this.f_168866_ ? this.m_168912_(this.f_168860_) : this.m_6035_()));
   }

   public List<FormattedCharSequence> m_141932_() {
      return this.f_168865_.apply(this.f_168860_);
   }

   public static <T> CycleButton.Builder<T> m_168894_(Function<T, Component> p_168895_) {
      return new CycleButton.Builder<>(p_168895_);
   }

   public static CycleButton.Builder<Boolean> m_168896_(Component p_168897_, Component p_168898_) {
      return (new CycleButton.Builder<Boolean>((p_168902_) -> {
         return p_168902_ ? p_168897_ : p_168898_;
      })).m_168950_(f_168857_);
   }

   public static CycleButton.Builder<Boolean> m_168919_() {
      return (new CycleButton.Builder<Boolean>((p_168891_) -> {
         return p_168891_ ? CommonComponents.f_130653_ : CommonComponents.f_130654_;
      })).m_168950_(f_168857_);
   }

   public static CycleButton.Builder<Boolean> m_168916_(boolean p_168917_) {
      return m_168919_().m_168948_(p_168917_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder<T> {
      private int f_168920_;
      @Nullable
      private T f_168921_;
      private final Function<T, Component> f_168922_;
      private CycleButton.TooltipSupplier<T> f_168923_ = (p_168964_) -> {
         return ImmutableList.of();
      };
      private Function<CycleButton<T>, MutableComponent> f_168924_ = CycleButton::m_168904_;
      private CycleButton.ValueListSupplier<T> f_168925_ = CycleButton.ValueListSupplier.m_168968_(ImmutableList.of());
      private boolean f_168926_;

      public Builder(Function<T, Component> p_168928_) {
         this.f_168922_ = p_168928_;
      }

      public CycleButton.Builder<T> m_168950_(List<T> p_168951_) {
         this.f_168925_ = CycleButton.ValueListSupplier.m_168968_(p_168951_);
         return this;
      }

      @SafeVarargs
      public final CycleButton.Builder<T> m_168961_(T... p_168962_) {
         return this.m_168950_(ImmutableList.copyOf(p_168962_));
      }

      public CycleButton.Builder<T> m_168952_(List<T> p_168953_, List<T> p_168954_) {
         this.f_168925_ = CycleButton.ValueListSupplier.m_168970_(CycleButton.f_168856_, p_168953_, p_168954_);
         return this;
      }

      public CycleButton.Builder<T> m_168955_(BooleanSupplier p_168956_, List<T> p_168957_, List<T> p_168958_) {
         this.f_168925_ = CycleButton.ValueListSupplier.m_168970_(p_168956_, p_168957_, p_168958_);
         return this;
      }

      public CycleButton.Builder<T> m_168943_(CycleButton.TooltipSupplier<T> p_168944_) {
         this.f_168923_ = p_168944_;
         return this;
      }

      public CycleButton.Builder<T> m_168948_(T p_168949_) {
         this.f_168921_ = p_168949_;
         int i = this.f_168925_.m_142478_().indexOf(p_168949_);
         if (i != -1) {
            this.f_168920_ = i;
         }

         return this;
      }

      public CycleButton.Builder<T> m_168959_(Function<CycleButton<T>, MutableComponent> p_168960_) {
         this.f_168924_ = p_168960_;
         return this;
      }

      public CycleButton.Builder<T> m_168929_() {
         this.f_168926_ = true;
         return this;
      }

      public CycleButton<T> m_168930_(int p_168931_, int p_168932_, int p_168933_, int p_168934_, Component p_168935_) {
         return this.m_168936_(p_168931_, p_168932_, p_168933_, p_168934_, p_168935_, (p_168946_, p_168947_) -> {
         });
      }

      public CycleButton<T> m_168936_(int p_168937_, int p_168938_, int p_168939_, int p_168940_, Component p_168941_, CycleButton.OnValueChange<T> p_168942_) {
         List<T> list = this.f_168925_.m_142478_();
         if (list.isEmpty()) {
            throw new IllegalStateException("No values for cycle button");
         } else {
            T t = (T)(this.f_168921_ != null ? this.f_168921_ : list.get(this.f_168920_));
            Component component = this.f_168922_.apply(t);
            Component component1 = (Component)(this.f_168926_ ? component : CommonComponents.m_178393_(p_168941_, component));
            return new CycleButton<>(p_168937_, p_168938_, p_168939_, p_168940_, component1, p_168941_, this.f_168920_, t, this.f_168925_, this.f_168922_, this.f_168924_, p_168942_, this.f_168923_, this.f_168926_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public interface OnValueChange<T> {
      void m_168965_(CycleButton p_168966_, T p_168967_);
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   public interface TooltipSupplier<T> extends Function<T, List<FormattedCharSequence>> {
   }

   @OnlyIn(Dist.CLIENT)
   interface ValueListSupplier<T> {
      List<T> m_142477_();

      List<T> m_142478_();

      static <T> CycleButton.ValueListSupplier<T> m_168968_(List<T> p_168969_) {
         final List<T> list = ImmutableList.copyOf(p_168969_);
         return new CycleButton.ValueListSupplier<T>() {
            public List<T> m_142477_() {
               return list;
            }

            public List<T> m_142478_() {
               return list;
            }
         };
      }

      static <T> CycleButton.ValueListSupplier<T> m_168970_(final BooleanSupplier p_168971_, List<T> p_168972_, List<T> p_168973_) {
         final List<T> list = ImmutableList.copyOf(p_168972_);
         final List<T> list1 = ImmutableList.copyOf(p_168973_);
         return new CycleButton.ValueListSupplier<T>() {
            public List<T> m_142477_() {
               return p_168971_.getAsBoolean() ? list1 : list;
            }

            public List<T> m_142478_() {
               return list;
            }
         };
      }
   }
}