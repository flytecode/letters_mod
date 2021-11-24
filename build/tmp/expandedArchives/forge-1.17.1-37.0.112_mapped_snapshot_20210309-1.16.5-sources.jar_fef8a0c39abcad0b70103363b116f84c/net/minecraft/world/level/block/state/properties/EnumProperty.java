package net.minecraft.world.level.block.state.properties;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.StringRepresentable;

public class EnumProperty<T extends Enum<T> & StringRepresentable> extends Property<T> {
   private final ImmutableSet<T> f_61576_;
   private final Map<String, T> f_61577_ = Maps.newHashMap();

   protected EnumProperty(String p_61579_, Class<T> p_61580_, Collection<T> p_61581_) {
      super(p_61579_, p_61580_);
      this.f_61576_ = ImmutableSet.copyOf(p_61581_);

      for(T t : p_61581_) {
         String s = t.m_7912_();
         if (this.f_61577_.containsKey(s)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + s + "'");
         }

         this.f_61577_.put(s, t);
      }

   }

   public Collection<T> m_6908_() {
      return this.f_61576_;
   }

   public Optional<T> m_6215_(String p_61604_) {
      return Optional.ofNullable(this.f_61577_.get(p_61604_));
   }

   public String m_6940_(T p_61586_) {
      return p_61586_.m_7912_();
   }

   public boolean equals(Object p_61606_) {
      if (this == p_61606_) {
         return true;
      } else if (p_61606_ instanceof EnumProperty && super.equals(p_61606_)) {
         EnumProperty<?> enumproperty = (EnumProperty)p_61606_;
         return this.f_61576_.equals(enumproperty.f_61576_) && this.f_61577_.equals(enumproperty.f_61577_);
      } else {
         return false;
      }
   }

   public int m_6310_() {
      int i = super.m_6310_();
      i = 31 * i + this.f_61576_.hashCode();
      return 31 * i + this.f_61577_.hashCode();
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> m_61587_(String p_61588_, Class<T> p_61589_) {
      return m_61594_(p_61588_, p_61589_, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> m_61594_(String p_61595_, Class<T> p_61596_, Predicate<T> p_61597_) {
      return m_61590_(p_61595_, p_61596_, Arrays.<T>stream(p_61596_.getEnumConstants()).filter(p_61597_).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> m_61598_(String p_61599_, Class<T> p_61600_, T... p_61601_) {
      return m_61590_(p_61599_, p_61600_, Lists.newArrayList(p_61601_));
   }

   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> m_61590_(String p_61591_, Class<T> p_61592_, Collection<T> p_61593_) {
      return new EnumProperty<>(p_61591_, p_61592_, p_61593_);
   }
}