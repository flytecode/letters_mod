package net.minecraft.world.level.block.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class StateHolder<O, S> {
   public static final String f_155962_ = "Name";
   public static final String f_155963_ = "Properties";
   private static final Function<Entry<Property<?>, Comparable<?>>, String> f_61110_ = new Function<Entry<Property<?>, Comparable<?>>, String>() {
      public String apply(@Nullable Entry<Property<?>, Comparable<?>> p_61155_) {
         if (p_61155_ == null) {
            return "<NULL>";
         } else {
            Property<?> property = p_61155_.getKey();
            return property.m_61708_() + "=" + this.m_61151_(property, p_61155_.getValue());
         }
      }

      private <T extends Comparable<T>> String m_61151_(Property<T> p_61152_, Comparable<?> p_61153_) {
         return p_61152_.m_6940_((T)p_61153_);
      }
   };
   protected final O f_61112_;
   private final ImmutableMap<Property<?>, Comparable<?>> f_61111_;
   private Table<Property<?>, Comparable<?>, S> f_61114_;
   protected final MapCodec<S> f_61113_;

   protected StateHolder(O p_61117_, ImmutableMap<Property<?>, Comparable<?>> p_61118_, MapCodec<S> p_61119_) {
      this.f_61112_ = p_61117_;
      this.f_61111_ = p_61118_;
      this.f_61113_ = p_61119_;
   }

   public <T extends Comparable<T>> S m_61122_(Property<T> p_61123_) {
      return this.m_61124_(p_61123_, m_61130_(p_61123_.m_6908_(), this.m_61143_(p_61123_)));
   }

   protected static <T> T m_61130_(Collection<T> p_61131_, T p_61132_) {
      Iterator<T> iterator = p_61131_.iterator();

      while(iterator.hasNext()) {
         if (iterator.next().equals(p_61132_)) {
            if (iterator.hasNext()) {
               return iterator.next();
            }

            return p_61131_.iterator().next();
         }
      }

      return iterator.next();
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(this.f_61112_);
      if (!this.m_61148_().isEmpty()) {
         stringbuilder.append('[');
         stringbuilder.append(this.m_61148_().entrySet().stream().map(f_61110_).collect(Collectors.joining(",")));
         stringbuilder.append(']');
      }

      return stringbuilder.toString();
   }

   public Collection<Property<?>> m_61147_() {
      return Collections.unmodifiableCollection(this.f_61111_.keySet());
   }

   public <T extends Comparable<T>> boolean m_61138_(Property<T> p_61139_) {
      return this.f_61111_.containsKey(p_61139_);
   }

   public <T extends Comparable<T>> T m_61143_(Property<T> p_61144_) {
      Comparable<?> comparable = this.f_61111_.get(p_61144_);
      if (comparable == null) {
         throw new IllegalArgumentException("Cannot get property " + p_61144_ + " as it does not exist in " + this.f_61112_);
      } else {
         return p_61144_.m_61709_().cast(comparable);
      }
   }

   public <T extends Comparable<T>> Optional<T> m_61145_(Property<T> p_61146_) {
      Comparable<?> comparable = this.f_61111_.get(p_61146_);
      return comparable == null ? Optional.empty() : Optional.of(p_61146_.m_61709_().cast(comparable));
   }

   public <T extends Comparable<T>, V extends T> S m_61124_(Property<T> p_61125_, V p_61126_) {
      Comparable<?> comparable = this.f_61111_.get(p_61125_);
      if (comparable == null) {
         throw new IllegalArgumentException("Cannot set property " + p_61125_ + " as it does not exist in " + this.f_61112_);
      } else if (comparable == p_61126_) {
         return (S)this;
      } else {
         S s = this.f_61114_.get(p_61125_, p_61126_);
         if (s == null) {
            throw new IllegalArgumentException("Cannot set property " + p_61125_ + " to " + p_61126_ + " on " + this.f_61112_ + ", it is not an allowed value");
         } else {
            return s;
         }
      }
   }

   public void m_61133_(Map<Map<Property<?>, Comparable<?>>, S> p_61134_) {
      if (this.f_61114_ != null) {
         throw new IllegalStateException();
      } else {
         Table<Property<?>, Comparable<?>, S> table = HashBasedTable.create();

         for(Entry<Property<?>, Comparable<?>> entry : this.f_61111_.entrySet()) {
            Property<?> property = entry.getKey();

            for(Comparable<?> comparable : property.m_6908_()) {
               if (comparable != entry.getValue()) {
                  table.put(property, comparable, p_61134_.get(this.m_61140_(property, comparable)));
               }
            }
         }

         this.f_61114_ = (Table<Property<?>, Comparable<?>, S>)(table.isEmpty() ? table : ArrayTable.create(table));
      }
   }

   private Map<Property<?>, Comparable<?>> m_61140_(Property<?> p_61141_, Comparable<?> p_61142_) {
      Map<Property<?>, Comparable<?>> map = Maps.newHashMap(this.f_61111_);
      map.put(p_61141_, p_61142_);
      return map;
   }

   public ImmutableMap<Property<?>, Comparable<?>> m_61148_() {
      return this.f_61111_;
   }

   protected static <O, S extends StateHolder<O, S>> Codec<S> m_61127_(Codec<O> p_61128_, Function<O, S> p_61129_) {
      return p_61128_.dispatch("Name", (p_61121_) -> {
         return p_61121_.f_61112_;
      }, (p_61137_) -> {
         S s = p_61129_.apply(p_61137_);
         return s.m_61148_().isEmpty() ? Codec.unit(s) : s.f_61113_.fieldOf("Properties").codec();
      });
   }
}