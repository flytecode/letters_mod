package net.minecraft.world.level.block.state.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockPatternBuilder {
   private static final Joiner f_61236_ = Joiner.on(",");
   private final List<String[]> f_61237_ = Lists.newArrayList();
   private final Map<Character, Predicate<BlockInWorld>> f_61238_ = Maps.newHashMap();
   private int f_61239_;
   private int f_61240_;

   private BlockPatternBuilder() {
      this.f_61238_.put(' ', Predicates.alwaysTrue());
   }

   public BlockPatternBuilder m_61247_(String... p_61248_) {
      if (!ArrayUtils.isEmpty((Object[])p_61248_) && !StringUtils.isEmpty(p_61248_[0])) {
         if (this.f_61237_.isEmpty()) {
            this.f_61239_ = p_61248_.length;
            this.f_61240_ = p_61248_[0].length();
         }

         if (p_61248_.length != this.f_61239_) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.f_61239_ + ", but was given one with a height of " + p_61248_.length + ")");
         } else {
            for(String s : p_61248_) {
               if (s.length() != this.f_61240_) {
                  throw new IllegalArgumentException("Not all rows in the given aisle are the correct width (expected " + this.f_61240_ + ", found one with " + s.length() + ")");
               }

               for(char c0 : s.toCharArray()) {
                  if (!this.f_61238_.containsKey(c0)) {
                     this.f_61238_.put(c0, (Predicate<BlockInWorld>)null);
                  }
               }
            }

            this.f_61237_.add(p_61248_);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static BlockPatternBuilder m_61243_() {
      return new BlockPatternBuilder();
   }

   public BlockPatternBuilder m_61244_(char p_61245_, Predicate<BlockInWorld> p_61246_) {
      this.f_61238_.put(p_61245_, p_61246_);
      return this;
   }

   public BlockPattern m_61249_() {
      return new BlockPattern(this.m_61250_());
   }

   private Predicate<BlockInWorld>[][][] m_61250_() {
      this.m_61251_();
      Predicate<BlockInWorld>[][][] predicate = (Predicate[][][])Array.newInstance(Predicate.class, this.f_61237_.size(), this.f_61239_, this.f_61240_);

      for(int i = 0; i < this.f_61237_.size(); ++i) {
         for(int j = 0; j < this.f_61239_; ++j) {
            for(int k = 0; k < this.f_61240_; ++k) {
               predicate[i][j][k] = this.f_61238_.get((this.f_61237_.get(i))[j].charAt(k));
            }
         }
      }

      return predicate;
   }

   private void m_61251_() {
      List<Character> list = Lists.newArrayList();

      for(Entry<Character, Predicate<BlockInWorld>> entry : this.f_61238_.entrySet()) {
         if (entry.getValue() == null) {
            list.add(entry.getKey());
         }
      }

      if (!list.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + f_61236_.join(list) + " are missing");
      }
   }
}