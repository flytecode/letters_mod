package net.minecraft.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class PropertyDispatch {
   private final Map<Selector, List<Variant>> f_125291_ = Maps.newHashMap();

   protected void m_125319_(Selector p_125320_, List<Variant> p_125321_) {
      List<Variant> list = this.f_125291_.put(p_125320_, p_125321_);
      if (list != null) {
         throw new IllegalStateException("Value " + p_125320_ + " is already defined");
      }
   }

   Map<Selector, List<Variant>> m_125293_() {
      this.m_125322_();
      return ImmutableMap.copyOf(this.f_125291_);
   }

   private void m_125322_() {
      List<Property<?>> list = this.m_7336_();
      Stream<Selector> stream = Stream.of(Selector.m_125485_());

      for(Property<?> property : list) {
         stream = stream.flatMap((p_125316_) -> {
            return property.m_61702_().map(p_125316_::m_125486_);
         });
      }

      List<Selector> list1 = stream.filter((p_125318_) -> {
         return !this.f_125291_.containsKey(p_125318_);
      }).collect(Collectors.toList());
      if (!list1.isEmpty()) {
         throw new IllegalStateException("Missing definition for properties: " + list1);
      }
   }

   abstract List<Property<?>> m_7336_();

   public static <T1 extends Comparable<T1>> PropertyDispatch.C1<T1> m_125294_(Property<T1> p_125295_) {
      return new PropertyDispatch.C1<>(p_125295_);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> PropertyDispatch.C2<T1, T2> m_125296_(Property<T1> p_125297_, Property<T2> p_125298_) {
      return new PropertyDispatch.C2<>(p_125297_, p_125298_);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> PropertyDispatch.C3<T1, T2, T3> m_125299_(Property<T1> p_125300_, Property<T2> p_125301_, Property<T3> p_125302_) {
      return new PropertyDispatch.C3<>(p_125300_, p_125301_, p_125302_);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> PropertyDispatch.C4<T1, T2, T3, T4> m_125303_(Property<T1> p_125304_, Property<T2> p_125305_, Property<T3> p_125306_, Property<T4> p_125307_) {
      return new PropertyDispatch.C4<>(p_125304_, p_125305_, p_125306_, p_125307_);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> PropertyDispatch.C5<T1, T2, T3, T4, T5> m_125308_(Property<T1> p_125309_, Property<T2> p_125310_, Property<T3> p_125311_, Property<T4> p_125312_, Property<T5> p_125313_) {
      return new PropertyDispatch.C5<>(p_125309_, p_125310_, p_125311_, p_125312_, p_125313_);
   }

   public static class C1<T1 extends Comparable<T1>> extends PropertyDispatch {
      private final Property<T1> f_125323_;

      C1(Property<T1> p_125325_) {
         this.f_125323_ = p_125325_;
      }

      public List<Property<?>> m_7336_() {
         return ImmutableList.of(this.f_125323_);
      }

      public PropertyDispatch.C1<T1> m_125332_(T1 p_125333_, List<Variant> p_125334_) {
         Selector selector = Selector.m_125490_(this.f_125323_.m_61699_(p_125333_));
         this.m_125319_(selector, p_125334_);
         return this;
      }

      public PropertyDispatch.C1<T1> m_125329_(T1 p_125330_, Variant p_125331_) {
         return this.m_125332_(p_125330_, Collections.singletonList(p_125331_));
      }

      public PropertyDispatch m_125335_(Function<T1, Variant> p_125336_) {
         this.f_125323_.m_6908_().forEach((p_125340_) -> {
            this.m_125329_(p_125340_, p_125336_.apply(p_125340_));
         });
         return this;
      }

      public PropertyDispatch m_176313_(Function<T1, List<Variant>> p_176314_) {
         this.f_125323_.m_6908_().forEach((p_176312_) -> {
            this.m_125332_(p_176312_, p_176314_.apply(p_176312_));
         });
         return this;
      }
   }

   public static class C2<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends PropertyDispatch {
      private final Property<T1> f_125341_;
      private final Property<T2> f_125342_;

      C2(Property<T1> p_125344_, Property<T2> p_125345_) {
         this.f_125341_ = p_125344_;
         this.f_125342_ = p_125345_;
      }

      public List<Property<?>> m_7336_() {
         return ImmutableList.of(this.f_125341_, this.f_125342_);
      }

      public PropertyDispatch.C2<T1, T2> m_125354_(T1 p_125355_, T2 p_125356_, List<Variant> p_125357_) {
         Selector selector = Selector.m_125490_(this.f_125341_.m_61699_(p_125355_), this.f_125342_.m_61699_(p_125356_));
         this.m_125319_(selector, p_125357_);
         return this;
      }

      public PropertyDispatch.C2<T1, T2> m_125350_(T1 p_125351_, T2 p_125352_, Variant p_125353_) {
         return this.m_125354_(p_125351_, p_125352_, Collections.singletonList(p_125353_));
      }

      public PropertyDispatch m_125362_(BiFunction<T1, T2, Variant> p_125363_) {
         this.f_125341_.m_6908_().forEach((p_125376_) -> {
            this.f_125342_.m_6908_().forEach((p_176322_) -> {
               this.m_125350_((T1)p_125376_, p_176322_, p_125363_.apply((T1)p_125376_, p_176322_));
            });
         });
         return this;
      }

      public PropertyDispatch m_125372_(BiFunction<T1, T2, List<Variant>> p_125373_) {
         this.f_125341_.m_6908_().forEach((p_125366_) -> {
            this.f_125342_.m_6908_().forEach((p_176318_) -> {
               this.m_125354_((T1)p_125366_, p_176318_, p_125373_.apply((T1)p_125366_, p_176318_));
            });
         });
         return this;
      }
   }

   public static class C3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends PropertyDispatch {
      private final Property<T1> f_125377_;
      private final Property<T2> f_125378_;
      private final Property<T3> f_125379_;

      C3(Property<T1> p_125381_, Property<T2> p_125382_, Property<T3> p_125383_) {
         this.f_125377_ = p_125381_;
         this.f_125378_ = p_125382_;
         this.f_125379_ = p_125383_;
      }

      public List<Property<?>> m_7336_() {
         return ImmutableList.of(this.f_125377_, this.f_125378_, this.f_125379_);
      }

      public PropertyDispatch.C3<T1, T2, T3> m_125396_(T1 p_125397_, T2 p_125398_, T3 p_125399_, List<Variant> p_125400_) {
         Selector selector = Selector.m_125490_(this.f_125377_.m_61699_(p_125397_), this.f_125378_.m_61699_(p_125398_), this.f_125379_.m_61699_(p_125399_));
         this.m_125319_(selector, p_125400_);
         return this;
      }

      public PropertyDispatch.C3<T1, T2, T3> m_125391_(T1 p_125392_, T2 p_125393_, T3 p_125394_, Variant p_125395_) {
         return this.m_125396_(p_125392_, p_125393_, p_125394_, Collections.singletonList(p_125395_));
      }

      public PropertyDispatch m_125389_(PropertyDispatch.TriFunction<T1, T2, T3, Variant> p_125390_) {
         this.f_125377_.m_6908_().forEach((p_125404_) -> {
            this.f_125378_.m_6908_().forEach((p_176343_) -> {
               this.f_125379_.m_6908_().forEach((p_176339_) -> {
                  this.m_125391_((T1)p_125404_, (T2)p_176343_, p_176339_, p_125390_.m_125475_((T1)p_125404_, (T2)p_176343_, p_176339_));
               });
            });
         });
         return this;
      }

      public PropertyDispatch m_176344_(PropertyDispatch.TriFunction<T1, T2, T3, List<Variant>> p_176345_) {
         this.f_125377_.m_6908_().forEach((p_176334_) -> {
            this.f_125378_.m_6908_().forEach((p_176331_) -> {
               this.f_125379_.m_6908_().forEach((p_176327_) -> {
                  this.m_125396_((T1)p_176334_, (T2)p_176331_, p_176327_, p_176345_.m_125475_((T1)p_176334_, (T2)p_176331_, p_176327_));
               });
            });
         });
         return this;
      }
   }

   public static class C4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> extends PropertyDispatch {
      private final Property<T1> f_125414_;
      private final Property<T2> f_125415_;
      private final Property<T3> f_125416_;
      private final Property<T4> f_125417_;

      C4(Property<T1> p_125419_, Property<T2> p_125420_, Property<T3> p_125421_, Property<T4> p_125422_) {
         this.f_125414_ = p_125419_;
         this.f_125415_ = p_125420_;
         this.f_125416_ = p_125421_;
         this.f_125417_ = p_125422_;
      }

      public List<Property<?>> m_7336_() {
         return ImmutableList.of(this.f_125414_, this.f_125415_, this.f_125416_, this.f_125417_);
      }

      public PropertyDispatch.C4<T1, T2, T3, T4> m_125435_(T1 p_125436_, T2 p_125437_, T3 p_125438_, T4 p_125439_, List<Variant> p_125440_) {
         Selector selector = Selector.m_125490_(this.f_125414_.m_61699_(p_125436_), this.f_125415_.m_61699_(p_125437_), this.f_125416_.m_61699_(p_125438_), this.f_125417_.m_61699_(p_125439_));
         this.m_125319_(selector, p_125440_);
         return this;
      }

      public PropertyDispatch.C4<T1, T2, T3, T4> m_125429_(T1 p_125430_, T2 p_125431_, T3 p_125432_, T4 p_125433_, Variant p_125434_) {
         return this.m_125435_(p_125430_, p_125431_, p_125432_, p_125433_, Collections.singletonList(p_125434_));
      }

      public PropertyDispatch m_176361_(PropertyDispatch.QuadFunction<T1, T2, T3, T4, Variant> p_176362_) {
         this.f_125414_.m_6908_().forEach((p_176385_) -> {
            this.f_125415_.m_6908_().forEach((p_176380_) -> {
               this.f_125416_.m_6908_().forEach((p_176376_) -> {
                  this.f_125417_.m_6908_().forEach((p_176371_) -> {
                     this.m_125429_((T1)p_176385_, (T2)p_176380_, (T3)p_176376_, p_176371_, p_176362_.m_176446_((T1)p_176385_, (T2)p_176380_, (T3)p_176376_, p_176371_));
                  });
               });
            });
         });
         return this;
      }

      public PropertyDispatch m_176381_(PropertyDispatch.QuadFunction<T1, T2, T3, T4, List<Variant>> p_176382_) {
         this.f_125414_.m_6908_().forEach((p_176365_) -> {
            this.f_125415_.m_6908_().forEach((p_176360_) -> {
               this.f_125416_.m_6908_().forEach((p_176356_) -> {
                  this.f_125417_.m_6908_().forEach((p_176351_) -> {
                     this.m_125435_((T1)p_176365_, (T2)p_176360_, (T3)p_176356_, p_176351_, p_176382_.m_176446_((T1)p_176365_, (T2)p_176360_, (T3)p_176356_, p_176351_));
                  });
               });
            });
         });
         return this;
      }
   }

   public static class C5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> extends PropertyDispatch {
      private final Property<T1> f_125442_;
      private final Property<T2> f_125443_;
      private final Property<T3> f_125444_;
      private final Property<T4> f_125445_;
      private final Property<T5> f_125446_;

      C5(Property<T1> p_125448_, Property<T2> p_125449_, Property<T3> p_125450_, Property<T4> p_125451_, Property<T5> p_125452_) {
         this.f_125442_ = p_125448_;
         this.f_125443_ = p_125449_;
         this.f_125444_ = p_125450_;
         this.f_125445_ = p_125451_;
         this.f_125446_ = p_125452_;
      }

      public List<Property<?>> m_7336_() {
         return ImmutableList.of(this.f_125442_, this.f_125443_, this.f_125444_, this.f_125445_, this.f_125446_);
      }

      public PropertyDispatch.C5<T1, T2, T3, T4, T5> m_125467_(T1 p_125468_, T2 p_125469_, T3 p_125470_, T4 p_125471_, T5 p_125472_, List<Variant> p_125473_) {
         Selector selector = Selector.m_125490_(this.f_125442_.m_61699_(p_125468_), this.f_125443_.m_61699_(p_125469_), this.f_125444_.m_61699_(p_125470_), this.f_125445_.m_61699_(p_125471_), this.f_125446_.m_61699_(p_125472_));
         this.m_125319_(selector, p_125473_);
         return this;
      }

      public PropertyDispatch.C5<T1, T2, T3, T4, T5> m_125460_(T1 p_125461_, T2 p_125462_, T3 p_125463_, T4 p_125464_, T5 p_125465_, Variant p_125466_) {
         return this.m_125467_(p_125461_, p_125462_, p_125463_, p_125464_, p_125465_, Collections.singletonList(p_125466_));
      }

      public PropertyDispatch m_176408_(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, Variant> p_176409_) {
         this.f_125442_.m_6908_().forEach((p_176439_) -> {
            this.f_125443_.m_6908_().forEach((p_176434_) -> {
               this.f_125444_.m_6908_().forEach((p_176430_) -> {
                  this.f_125445_.m_6908_().forEach((p_176425_) -> {
                     this.f_125446_.m_6908_().forEach((p_176419_) -> {
                        this.m_125460_((T1)p_176439_, (T2)p_176434_, (T3)p_176430_, (T4)p_176425_, p_176419_, p_176409_.m_176440_((T1)p_176439_, (T2)p_176434_, (T3)p_176430_, (T4)p_176425_, p_176419_));
                     });
                  });
               });
            });
         });
         return this;
      }

      public PropertyDispatch m_176435_(PropertyDispatch.PentaFunction<T1, T2, T3, T4, T5, List<Variant>> p_176436_) {
         this.f_125442_.m_6908_().forEach((p_176412_) -> {
            this.f_125443_.m_6908_().forEach((p_176407_) -> {
               this.f_125444_.m_6908_().forEach((p_176403_) -> {
                  this.f_125445_.m_6908_().forEach((p_176398_) -> {
                     this.f_125446_.m_6908_().forEach((p_176392_) -> {
                        this.m_125467_((T1)p_176412_, (T2)p_176407_, (T3)p_176403_, (T4)p_176398_, p_176392_, p_176436_.m_176440_((T1)p_176412_, (T2)p_176407_, (T3)p_176403_, (T4)p_176398_, p_176392_));
                     });
                  });
               });
            });
         });
         return this;
      }
   }

   @FunctionalInterface
   public interface PentaFunction<P1, P2, P3, P4, P5, R> {
      R m_176440_(P1 p_176441_, P2 p_176442_, P3 p_176443_, P4 p_176444_, P5 p_176445_);
   }

   @FunctionalInterface
   public interface QuadFunction<P1, P2, P3, P4, R> {
      R m_176446_(P1 p_176447_, P2 p_176448_, P3 p_176449_, P4 p_176450_);
   }

   @FunctionalInterface
   public interface TriFunction<P1, P2, P3, R> {
      R m_125475_(P1 p_125476_, P2 p_125477_, P3 p_125478_);
   }
}