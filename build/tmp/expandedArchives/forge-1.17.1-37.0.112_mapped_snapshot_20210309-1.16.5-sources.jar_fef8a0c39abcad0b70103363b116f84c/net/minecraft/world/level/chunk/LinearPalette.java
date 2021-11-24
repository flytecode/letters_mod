package net.minecraft.world.level.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

public class LinearPalette<T> implements Palette<T> {
   private final IdMapper<T> f_63025_;
   private final T[] f_63026_;
   private final PaletteResize<T> f_63027_;
   private final Function<CompoundTag, T> f_63028_;
   private final int f_63029_;
   private int f_63030_;

   public LinearPalette(IdMapper<T> p_63032_, int p_63033_, PaletteResize<T> p_63034_, Function<CompoundTag, T> p_63035_) {
      this.f_63025_ = p_63032_;
      this.f_63026_ = (T[])(new Object[1 << p_63033_]);
      this.f_63029_ = p_63033_;
      this.f_63027_ = p_63034_;
      this.f_63028_ = p_63035_;
   }

   public int m_6796_(T p_63040_) {
      for(int i = 0; i < this.f_63030_; ++i) {
         if (this.f_63026_[i] == p_63040_) {
            return i;
         }
      }

      int j = this.f_63030_;
      if (j < this.f_63026_.length) {
         this.f_63026_[j] = p_63040_;
         ++this.f_63030_;
         return j;
      } else {
         return this.f_63027_.m_7248_(this.f_63029_ + 1, p_63040_);
      }
   }

   public boolean m_6419_(Predicate<T> p_63042_) {
      for(int i = 0; i < this.f_63030_; ++i) {
         if (p_63042_.test(this.f_63026_[i])) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public T m_5795_(int p_63038_) {
      return (T)(p_63038_ >= 0 && p_63038_ < this.f_63030_ ? this.f_63026_[p_63038_] : null);
   }

   public void m_5680_(FriendlyByteBuf p_63046_) {
      this.f_63030_ = p_63046_.m_130242_();

      for(int i = 0; i < this.f_63030_; ++i) {
         this.f_63026_[i] = this.f_63025_.m_7942_(p_63046_.m_130242_());
      }

   }

   public void m_5678_(FriendlyByteBuf p_63049_) {
      p_63049_.m_130130_(this.f_63030_);

      for(int i = 0; i < this.f_63030_; ++i) {
         p_63049_.m_130130_(this.f_63025_.m_7447_(this.f_63026_[i]));
      }

   }

   public int m_6429_() {
      int i = FriendlyByteBuf.m_130053_(this.m_142067_());

      for(int j = 0; j < this.m_142067_(); ++j) {
         i += FriendlyByteBuf.m_130053_(this.f_63025_.m_7447_(this.f_63026_[j]));
      }

      return i;
   }

   public int m_142067_() {
      return this.f_63030_;
   }

   public void m_7385_(ListTag p_63044_) {
      for(int i = 0; i < p_63044_.size(); ++i) {
         this.f_63026_[i] = this.f_63028_.apply(p_63044_.m_128728_(i));
      }

      this.f_63030_ = p_63044_.size();
   }
}