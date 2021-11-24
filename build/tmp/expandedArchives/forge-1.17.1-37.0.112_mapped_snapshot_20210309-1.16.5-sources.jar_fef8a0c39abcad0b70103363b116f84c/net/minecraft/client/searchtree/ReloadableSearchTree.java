package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
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
public class ReloadableSearchTree<T> extends ReloadableIdSearchTree<T> {
   protected SuffixArray<T> f_119920_ = new SuffixArray<>();
   private final Function<T, Stream<String>> f_119921_;

   public ReloadableSearchTree(Function<T, Stream<String>> p_119923_, Function<T, Stream<ResourceLocation>> p_119924_) {
      super(p_119924_);
      this.f_119921_ = p_119923_;
   }

   public void m_7729_() {
      this.f_119920_ = new SuffixArray<>();
      super.m_7729_();
      this.f_119920_.m_119967_();
   }

   protected void m_8074_(T p_119932_) {
      super.m_8074_(p_119932_);
      this.f_119921_.apply(p_119932_).forEach((p_119927_) -> {
         this.f_119920_.m_119970_(p_119932_, p_119927_.toLowerCase(Locale.ROOT));
      });
   }

   public List<T> m_6293_(String p_119929_) {
      int i = p_119929_.indexOf(58);
      if (i < 0) {
         return this.f_119920_.m_119973_(p_119929_);
      } else {
         List<T> list = this.f_119870_.m_119973_(p_119929_.substring(0, i).trim());
         String s = p_119929_.substring(i + 1).trim();
         List<T> list1 = this.f_119871_.m_119973_(s);
         List<T> list2 = this.f_119920_.m_119973_(s);
         return Lists.newArrayList(new ReloadableIdSearchTree.IntersectionIterator<>(list.iterator(), new ReloadableSearchTree.MergingUniqueIterator<>(list1.iterator(), list2.iterator(), this::m_119880_), this::m_119880_));
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class MergingUniqueIterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> f_119933_;
      private final PeekingIterator<T> f_119934_;
      private final Comparator<T> f_119935_;

      public MergingUniqueIterator(Iterator<T> p_119937_, Iterator<T> p_119938_, Comparator<T> p_119939_) {
         this.f_119933_ = Iterators.peekingIterator(p_119937_);
         this.f_119934_ = Iterators.peekingIterator(p_119938_);
         this.f_119935_ = p_119939_;
      }

      protected T computeNext() {
         boolean flag = !this.f_119933_.hasNext();
         boolean flag1 = !this.f_119934_.hasNext();
         if (flag && flag1) {
            return this.endOfData();
         } else if (flag) {
            return this.f_119934_.next();
         } else if (flag1) {
            return this.f_119933_.next();
         } else {
            int i = this.f_119935_.compare(this.f_119933_.peek(), this.f_119934_.peek());
            if (i == 0) {
               this.f_119934_.next();
            }

            return (T)(i <= 0 ? this.f_119933_.next() : this.f_119934_.next());
         }
      }
   }
}