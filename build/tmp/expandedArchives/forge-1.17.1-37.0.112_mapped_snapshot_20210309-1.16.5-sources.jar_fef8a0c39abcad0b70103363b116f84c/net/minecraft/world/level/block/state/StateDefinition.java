package net.minecraft.world.level.block.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.Property;

public class StateDefinition<O, S extends StateHolder<O, S>> {
   static final Pattern f_61046_ = Pattern.compile("^[a-z0-9_]+$");
   private final O f_61047_;
   private final ImmutableSortedMap<String, Property<?>> f_61048_;
   private final ImmutableList<S> f_61049_;

   protected StateDefinition(Function<O, S> p_61052_, O p_61053_, StateDefinition.Factory<O, S> p_61054_, Map<String, Property<?>> p_61055_) {
      this.f_61047_ = p_61053_;
      this.f_61048_ = ImmutableSortedMap.copyOf(p_61055_);
      Supplier<S> supplier = () -> {
         return p_61052_.apply(p_61053_);
      };
      MapCodec<S> mapcodec = MapCodec.of(Encoder.empty(), Decoder.unit(supplier));

      for(Entry<String, Property<?>> entry : this.f_61048_.entrySet()) {
         mapcodec = m_61076_(mapcodec, supplier, entry.getKey(), entry.getValue());
      }

      MapCodec<S> mapcodec1 = mapcodec;
      Map<Map<Property<?>, Comparable<?>>, S> map = Maps.newLinkedHashMap();
      List<S> list = Lists.newArrayList();
      Stream<List<Pair<Property<?>, Comparable<?>>>> stream = Stream.of(Collections.emptyList());

      for(Property<?> property : this.f_61048_.values()) {
         stream = stream.flatMap((p_61072_) -> {
            return property.m_6908_().stream().map((p_155961_) -> {
               List<Pair<Property<?>, Comparable<?>>> list1 = Lists.newArrayList(p_61072_);
               list1.add(Pair.of(property, p_155961_));
               return list1;
            });
         });
      }

      stream.forEach((p_61063_) -> {
         ImmutableMap<Property<?>, Comparable<?>> immutablemap = p_61063_.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
         S s1 = p_61054_.m_61106_(p_61053_, immutablemap, mapcodec1);
         map.put(immutablemap, s1);
         list.add(s1);
      });

      for(S s : list) {
         s.m_61133_(map);
      }

      this.f_61049_ = ImmutableList.copyOf(list);
   }

   private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> m_61076_(MapCodec<S> p_61077_, Supplier<S> p_61078_, String p_61079_, Property<T> p_61080_) {
      return Codec.mapPair(p_61077_, p_61080_.m_61705_().fieldOf(p_61079_).setPartial(() -> {
         return p_61080_.m_61694_(p_61078_.get());
      })).xmap((p_61069_) -> {
         return p_61069_.getFirst().m_61124_(p_61080_, p_61069_.getSecond().m_61722_());
      }, (p_61066_) -> {
         return Pair.of(p_61066_, p_61080_.m_61694_(p_61066_));
      });
   }

   public ImmutableList<S> m_61056_() {
      return this.f_61049_;
   }

   public S m_61090_() {
      return this.f_61049_.get(0);
   }

   public O m_61091_() {
      return this.f_61047_;
   }

   public Collection<Property<?>> m_61092_() {
      return this.f_61048_.values();
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("block", this.f_61047_).add("properties", this.f_61048_.values().stream().map(Property::m_61708_).collect(Collectors.toList())).toString();
   }

   @Nullable
   public Property<?> m_61081_(String p_61082_) {
      return this.f_61048_.get(p_61082_);
   }

   public static class Builder<O, S extends StateHolder<O, S>> {
      private final O f_61095_;
      private final Map<String, Property<?>> f_61096_ = Maps.newHashMap();

      public Builder(O p_61098_) {
         this.f_61095_ = p_61098_;
      }

      public StateDefinition.Builder<O, S> m_61104_(Property<?>... p_61105_) {
         for(Property<?> property : p_61105_) {
            this.m_61099_(property);
            this.f_61096_.put(property.m_61708_(), property);
         }

         return this;
      }

      private <T extends Comparable<T>> void m_61099_(Property<T> p_61100_) {
         String s = p_61100_.m_61708_();
         if (!StateDefinition.f_61046_.matcher(s).matches()) {
            throw new IllegalArgumentException(this.f_61095_ + " has invalidly named property: " + s);
         } else {
            Collection<T> collection = p_61100_.m_6908_();
            if (collection.size() <= 1) {
               throw new IllegalArgumentException(this.f_61095_ + " attempted use property " + s + " with <= 1 possible values");
            } else {
               for(T t : collection) {
                  String s1 = p_61100_.m_6940_(t);
                  if (!StateDefinition.f_61046_.matcher(s1).matches()) {
                     throw new IllegalArgumentException(this.f_61095_ + " has property: " + s + " with invalidly named value: " + s1);
                  }
               }

               if (this.f_61096_.containsKey(s)) {
                  throw new IllegalArgumentException(this.f_61095_ + " has duplicate property: " + s);
               }
            }
         }
      }

      public StateDefinition<O, S> m_61101_(Function<O, S> p_61102_, StateDefinition.Factory<O, S> p_61103_) {
         return new StateDefinition<>(p_61102_, this.f_61095_, p_61103_, this.f_61096_);
      }
   }

   public interface Factory<O, S> {
      S m_61106_(O p_61107_, ImmutableMap<Property<?>, Comparable<?>> p_61108_, MapCodec<S> p_61109_);
   }
}