package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ClassInstanceMultiMap<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> f_13527_ = Maps.newHashMap();
   private final Class<T> f_13528_;
   private final List<T> f_13529_ = Lists.newArrayList();

   public ClassInstanceMultiMap(Class<T> p_13531_) {
      this.f_13528_ = p_13531_;
      this.f_13527_.put(p_13531_, this.f_13529_);
   }

   public boolean add(T p_13536_) {
      boolean flag = false;

      for(Entry<Class<?>, List<T>> entry : this.f_13527_.entrySet()) {
         if (entry.getKey().isInstance(p_13536_)) {
            flag |= entry.getValue().add(p_13536_);
         }
      }

      return flag;
   }

   public boolean remove(Object p_13543_) {
      boolean flag = false;

      for(Entry<Class<?>, List<T>> entry : this.f_13527_.entrySet()) {
         if (entry.getKey().isInstance(p_13543_)) {
            List<T> list = entry.getValue();
            flag |= list.remove(p_13543_);
         }
      }

      return flag;
   }

   public boolean contains(Object p_13540_) {
      return this.m_13533_(p_13540_.getClass()).contains(p_13540_);
   }

   public <S> Collection<S> m_13533_(Class<S> p_13534_) {
      if (!this.f_13528_.isAssignableFrom(p_13534_)) {
         throw new IllegalArgumentException("Don't know how to search for " + p_13534_);
      } else {
         List<? extends T> list = this.f_13527_.computeIfAbsent(p_13534_, (p_13538_) -> {
            return this.f_13529_.stream().filter(p_13538_::isInstance).collect(Collectors.toList());
         });
         return (Collection<S>)Collections.unmodifiableCollection(list);
      }
   }

   public Iterator<T> iterator() {
      return (Iterator<T>)(this.f_13529_.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.f_13529_.iterator()));
   }

   public List<T> m_13532_() {
      return ImmutableList.copyOf(this.f_13529_);
   }

   public int size() {
      return this.f_13529_.size();
   }
}