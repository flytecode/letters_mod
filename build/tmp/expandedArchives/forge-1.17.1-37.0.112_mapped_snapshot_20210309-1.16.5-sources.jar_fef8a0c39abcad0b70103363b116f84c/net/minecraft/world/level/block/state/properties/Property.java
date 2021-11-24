package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.level.block.state.StateHolder;

public abstract class Property<T extends Comparable<T>> {
   private final Class<T> f_61686_;
   private final String f_61687_;
   private Integer f_61688_;
   private final Codec<T> f_61689_ = Codec.STRING.comapFlatMap((p_61698_) -> {
      return this.m_6215_(p_61698_).map(DataResult::success).orElseGet(() -> {
         return DataResult.error("Unable to read property: " + this + " with value: " + p_61698_);
      });
   }, this::m_6940_);
   private final Codec<Property.Value<T>> f_61690_ = this.f_61689_.xmap(this::m_61699_, Property.Value::m_61722_);

   protected Property(String p_61692_, Class<T> p_61693_) {
      this.f_61686_ = p_61693_;
      this.f_61687_ = p_61692_;
   }

   public Property.Value<T> m_61699_(T p_61700_) {
      return new Property.Value<>(this, p_61700_);
   }

   public Property.Value<T> m_61694_(StateHolder<?, ?> p_61695_) {
      return new Property.Value<>(this, p_61695_.m_61143_(this));
   }

   public Stream<Property.Value<T>> m_61702_() {
      return this.m_6908_().stream().map(this::m_61699_);
   }

   public Codec<T> m_156037_() {
      return this.f_61689_;
   }

   public Codec<Property.Value<T>> m_61705_() {
      return this.f_61690_;
   }

   public String m_61708_() {
      return this.f_61687_;
   }

   public Class<T> m_61709_() {
      return this.f_61686_;
   }

   public abstract Collection<T> m_6908_();

   public abstract String m_6940_(T p_61696_);

   public abstract Optional<T> m_6215_(String p_61701_);

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.f_61687_).add("clazz", this.f_61686_).add("values", this.m_6908_()).toString();
   }

   public boolean equals(Object p_61707_) {
      if (this == p_61707_) {
         return true;
      } else if (!(p_61707_ instanceof Property)) {
         return false;
      } else {
         Property<?> property = (Property)p_61707_;
         return this.f_61686_.equals(property.f_61686_) && this.f_61687_.equals(property.f_61687_);
      }
   }

   public final int hashCode() {
      if (this.f_61688_ == null) {
         this.f_61688_ = this.m_6310_();
      }

      return this.f_61688_;
   }

   public int m_6310_() {
      return 31 * this.f_61686_.hashCode() + this.f_61687_.hashCode();
   }

   public <U, S extends StateHolder<?, S>> DataResult<S> m_156031_(DynamicOps<U> p_156032_, S p_156033_, U p_156034_) {
      DataResult<T> dataresult = this.f_61689_.parse(p_156032_, p_156034_);
      return dataresult.map((p_156030_) -> {
         return p_156033_.m_61124_(this, p_156030_);
      }).setPartial(p_156033_);
   }

   public static final class Value<T extends Comparable<T>> {
      private final Property<T> f_61712_;
      private final T f_61713_;

      Value(Property<T> p_61715_, T p_61716_) {
         if (!p_61715_.m_6908_().contains(p_61716_)) {
            throw new IllegalArgumentException("Value " + p_61716_ + " does not belong to property " + p_61715_);
         } else {
            this.f_61712_ = p_61715_;
            this.f_61713_ = p_61716_;
         }
      }

      public Property<T> m_61721_() {
         return this.f_61712_;
      }

      public T m_61722_() {
         return this.f_61713_;
      }

      public String toString() {
         return this.f_61712_.m_61708_() + "=" + this.f_61712_.m_6940_(this.f_61713_);
      }

      public boolean equals(Object p_61724_) {
         if (this == p_61724_) {
            return true;
         } else if (!(p_61724_ instanceof Property.Value)) {
            return false;
         } else {
            Property.Value<?> value = (Property.Value)p_61724_;
            return this.f_61712_ == value.f_61712_ && this.f_61713_.equals(value.f_61713_);
         }
      }

      public int hashCode() {
         int i = this.f_61712_.hashCode();
         return 31 * i + this.f_61713_.hashCode();
      }
   }
}