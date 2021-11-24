package net.minecraft.core;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class IdMapper<T> implements IdMap<T> {
   public static final int f_175379_ = -1;
   protected int f_122653_;
   protected final IdentityHashMap<T, Integer> f_122654_;
   protected final List<T> f_122655_;

   public IdMapper() {
      this(512);
   }

   public IdMapper(int p_122658_) {
      this.f_122655_ = Lists.newArrayListWithExpectedSize(p_122658_);
      this.f_122654_ = new IdentityHashMap<>(p_122658_);
   }

   public void m_122664_(T p_122665_, int p_122666_) {
      this.f_122654_.put(p_122665_, p_122666_);

      while(this.f_122655_.size() <= p_122666_) {
         this.f_122655_.add((T)null);
      }

      this.f_122655_.set(p_122666_, p_122665_);
      if (this.f_122653_ <= p_122666_) {
         this.f_122653_ = p_122666_ + 1;
      }

   }

   public void m_122667_(T p_122668_) {
      this.m_122664_(p_122668_, this.f_122653_);
   }

   public int m_7447_(T p_122663_) {
      Integer integer = this.f_122654_.get(p_122663_);
      return integer == null ? -1 : integer;
   }

   @Nullable
   public final T m_7942_(int p_122661_) {
      return (T)(p_122661_ >= 0 && p_122661_ < this.f_122655_.size() ? this.f_122655_.get(p_122661_) : null);
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.f_122655_.iterator(), Predicates.notNull());
   }

   public boolean m_175380_(int p_175381_) {
      return this.m_7942_(p_175381_) != null;
   }

   public int m_122659_() {
      return this.f_122654_.size();
   }
}