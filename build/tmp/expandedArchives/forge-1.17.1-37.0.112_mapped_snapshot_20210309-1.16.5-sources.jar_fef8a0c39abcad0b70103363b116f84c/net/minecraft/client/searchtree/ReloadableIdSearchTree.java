package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReloadableIdSearchTree<T> implements MutableSearchTree<T> {
   protected SuffixArray<T> f_119870_ = new SuffixArray<>();
   protected SuffixArray<T> f_119871_ = new SuffixArray<>();
   private final Function<T, Stream<ResourceLocation>> f_119872_;
   private final List<T> f_119873_ = Lists.newArrayList();
   private final Object2IntMap<T> f_119874_ = new Object2IntOpenHashMap<>();

   public ReloadableIdSearchTree(Function<T, Stream<ResourceLocation>> p_119876_) {
      this.f_119872_ = p_119876_;
   }

   public void m_7729_() {
      this.f_119870_ = new SuffixArray<>();
      this.f_119871_ = new SuffixArray<>();

      for(T t : this.f_119873_) {
         this.m_8074_(t);
      }

      this.f_119870_.m_119967_();
      this.f_119871_.m_119967_();
   }

   public void m_8080_(T p_119879_) {
      this.f_119874_.put(p_119879_, this.f_119873_.size());
      this.f_119873_.add(p_119879_);
      this.m_8074_(p_119879_);
   }

   public void m_7716_() {
      this.f_119873_.clear();
      this.f_119874_.clear();
   }

   protected void m_8074_(T p_119889_) {
      this.f_119872_.apply(p_119889_).forEach((p_119885_) -> {
         this.f_119870_.m_119970_(p_119889_, p_119885_.m_135827_().toLowerCase(Locale.ROOT));
         this.f_119871_.m_119970_(p_119889_, p_119885_.m_135815_().toLowerCase(Locale.ROOT));
      });
   }

   protected int m_119880_(T p_119881_, T p_119882_) {
      return Integer.compare(this.f_119874_.getInt(p_119881_), this.f_119874_.getInt(p_119882_));
   }

   public List<T> m_6293_(String p_119887_) {
      int i = p_119887_.indexOf(58);
      if (i == -1) {
         return this.f_119871_.m_119973_(p_119887_);
      } else {
         List<T> list = this.f_119870_.m_119973_(p_119887_.substring(0, i).trim());
         String s = p_119887_.substring(i + 1).trim();
         List<T> list1 = this.f_119871_.m_119973_(s);
         return Lists.newArrayList(new ReloadableIdSearchTree.IntersectionIterator<>(list.iterator(), list1.iterator(), this::m_119880_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   protected static class IntersectionIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> f_119890_;
      private final PeekingIterator<T> f_119891_;
      private final Comparator<T> f_119892_;

      public IntersectionIterator(Iterator<T> p_119894_, Iterator<T> p_119895_, Comparator<T> p_119896_) {
         this.f_119890_ = Iterators.peekingIterator(p_119894_);
         this.f_119891_ = Iterators.peekingIterator(p_119895_);
         this.f_119892_ = p_119896_;
      }

      protected T computeNext() {
         while(this.f_119890_.hasNext() && this.f_119891_.hasNext()) {
            int i = this.f_119892_.compare(this.f_119890_.peek(), this.f_119891_.peek());
            if (i == 0) {
               this.f_119891_.next();
               return this.f_119890_.next();
            }

            if (i < 0) {
               this.f_119890_.next();
            } else {
               this.f_119891_.next();
            }
         }

         return this.endOfData();
      }
   }
}