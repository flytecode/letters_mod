package net.minecraft;

import java.util.Objects;

@FunctionalInterface
public interface CharPredicate {
   boolean m_125854_(char p_125855_);

   default CharPredicate m_178286_(CharPredicate p_178287_) {
      Objects.requireNonNull(p_178287_);
      return (p_178295_) -> {
         return this.m_125854_(p_178295_) && p_178287_.m_125854_(p_178295_);
      };
   }

   default CharPredicate m_178283_() {
      return (p_178285_) -> {
         return !this.m_125854_(p_178285_);
      };
   }

   default CharPredicate m_178291_(CharPredicate p_178292_) {
      Objects.requireNonNull(p_178292_);
      return (p_178290_) -> {
         return this.m_125854_(p_178290_) || p_178292_.m_125854_(p_178290_);
      };
   }
}