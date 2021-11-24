package net.minecraft.world.level.chunk;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.util.ThreadingDetector;

public class PalettedContainer<T> implements PaletteResize<T> {
   private static final int f_156462_ = 4096;
   public static final int f_156460_ = 9;
   public static final int f_156461_ = 4;
   private final Palette<T> f_63069_;
   private final PaletteResize<T> f_63070_ = (p_63139_, p_63140_) -> {
      return 0;
   };
   private final IdMapper<T> f_63071_;
   private final Function<CompoundTag, T> f_63072_;
   private final Function<T, CompoundTag> f_63073_;
   private final T f_63074_;
   protected BitStorage f_63068_;
   private Palette<T> f_63075_;
   private int f_63076_;
   private final Semaphore f_63077_ = new Semaphore(1);
   @Nullable
   private final DebugBuffer<Pair<Thread, StackTraceElement[]>> f_156463_ = null;

   public void m_63084_() {
      if (this.f_156463_ != null) {
         Thread thread = Thread.currentThread();
         this.f_156463_.m_144625_(Pair.of(thread, thread.getStackTrace()));
      }

      ThreadingDetector.m_145012_(this.f_63077_, this.f_156463_, "PalettedContainer");
   }

   public void m_63120_() {
      this.f_63077_.release();
   }

   public PalettedContainer(Palette<T> p_63079_, IdMapper<T> p_63080_, Function<CompoundTag, T> p_63081_, Function<T, CompoundTag> p_63082_, T p_63083_) {
      this.f_63069_ = p_63079_;
      this.f_63071_ = p_63080_;
      this.f_63072_ = p_63081_;
      this.f_63073_ = p_63082_;
      this.f_63074_ = p_63083_;
      this.m_63121_(4);
   }

   private static int m_63123_(int p_63124_, int p_63125_, int p_63126_) {
      return p_63125_ << 8 | p_63126_ << 4 | p_63124_;
   }

   private void m_63121_(int p_63122_) {
      setBits(p_63122_, false);
   }
   private void setBits(int p_63122_, boolean forceBits) {
      if (p_63122_ != this.f_63076_) {
         this.f_63076_ = p_63122_;
         if (this.f_63076_ <= 4) {
            this.f_63076_ = 4;
            this.f_63075_ = new LinearPalette<>(this.f_63071_, this.f_63076_, this, this.f_63072_);
         } else if (this.f_63076_ < 9) {
            this.f_63075_ = new HashMapPalette<>(this.f_63071_, this.f_63076_, this, this.f_63072_, this.f_63073_);
         } else {
            this.f_63075_ = this.f_63069_;
            this.f_63076_ = Mth.m_14163_(this.f_63071_.m_122659_());
            if (forceBits)
               this.f_63076_ = p_63122_;
         }

         this.f_63075_.m_6796_(this.f_63074_);
         this.f_63068_ = new BitStorage(this.f_63076_, 4096);
      }
   }

   public int m_7248_(int p_63142_, T p_63143_) {
      BitStorage bitstorage = this.f_63068_;
      Palette<T> palette = this.f_63075_;
      this.m_63121_(p_63142_);

      for(int i = 0; i < bitstorage.m_13521_(); ++i) {
         T t = palette.m_5795_(bitstorage.m_13514_(i));
         if (t != null) {
            this.m_63132_(i, t);
         }
      }

      return this.f_63075_.m_6796_(p_63143_);
   }

   public T m_63091_(int p_63092_, int p_63093_, int p_63094_, T p_63095_) {
      Object object;
      try {
         this.m_63084_();
         T t = this.m_63096_(m_63123_(p_63092_, p_63093_, p_63094_), p_63095_);
         object = t;
      } finally {
         this.m_63120_();
      }

      return (T)object;
   }

   public T m_63127_(int p_63128_, int p_63129_, int p_63130_, T p_63131_) {
      return this.m_63096_(m_63123_(p_63128_, p_63129_, p_63130_), p_63131_);
   }

   private T m_63096_(int p_63097_, T p_63098_) {
      int i = this.f_63075_.m_6796_(p_63098_);
      int j = this.f_63068_.m_13516_(p_63097_, i);
      T t = this.f_63075_.m_5795_(j);
      return (T)(t == null ? this.f_63074_ : t);
   }

   public void m_156470_(int p_156471_, int p_156472_, int p_156473_, T p_156474_) {
      try {
         this.m_63084_();
         this.m_63132_(m_63123_(p_156471_, p_156472_, p_156473_), p_156474_);
      } finally {
         this.m_63120_();
      }

   }

   private void m_63132_(int p_63133_, T p_63134_) {
      int i = this.f_63075_.m_6796_(p_63134_);
      this.f_63068_.m_13524_(p_63133_, i);
   }

   public T m_63087_(int p_63088_, int p_63089_, int p_63090_) {
      return this.m_63085_(m_63123_(p_63088_, p_63089_, p_63090_));
   }

   protected T m_63085_(int p_63086_) {
      T t = this.f_63075_.m_5795_(this.f_63068_.m_13514_(p_63086_));
      return (T)(t == null ? this.f_63074_ : t);
   }

   public void m_63118_(FriendlyByteBuf p_63119_) {
      try {
         this.m_63084_();
         int i = p_63119_.readByte();
         if (this.f_63076_ != i) {
            this.setBits(i, true); //Forge, Force bit density to fix network issues, resize below if needed.
         }

         this.f_63075_.m_5680_(p_63119_);
         p_63119_.m_130105_(this.f_63068_.m_13513_());
      } finally {
         this.m_63120_();
      }

      int regSize = Mth.m_14163_(this.f_63071_.m_122659_());
      if (this.f_63075_ == f_63069_ && this.f_63076_ != regSize) // Resize bits to fit registry.
         this.m_7248_(regSize, f_63074_);
   }

   public void m_63135_(FriendlyByteBuf p_63136_) {
      try {
         this.m_63084_();
         p_63136_.writeByte(this.f_63076_);
         this.f_63075_.m_5678_(p_63136_);
         p_63136_.m_130091_(this.f_63068_.m_13513_());
      } finally {
         this.m_63120_();
      }

   }

   public void m_63115_(ListTag p_63116_, long[] p_63117_) {
      try {
         this.m_63084_();
         int i = Math.max(4, Mth.m_14163_(p_63116_.size()));
         if (i != this.f_63076_) {
            this.m_63121_(i);
         }

         this.f_63075_.m_7385_(p_63116_);
         int j = p_63117_.length * 64 / 4096;
         if (this.f_63075_ == this.f_63069_) {
            Palette<T> palette = new HashMapPalette<>(this.f_63071_, i, this.f_63070_, this.f_63072_, this.f_63073_);
            palette.m_7385_(p_63116_);
            BitStorage bitstorage = new BitStorage(i, 4096, p_63117_);

            for(int k = 0; k < 4096; ++k) {
               this.f_63068_.m_13524_(k, this.f_63069_.m_6796_(palette.m_5795_(bitstorage.m_13514_(k))));
            }
         } else if (j == this.f_63076_) {
            System.arraycopy(p_63117_, 0, this.f_63068_.m_13513_(), 0, p_63117_.length);
         } else {
            BitStorage bitstorage1 = new BitStorage(j, 4096, p_63117_);

            for(int l = 0; l < 4096; ++l) {
               this.f_63068_.m_13524_(l, bitstorage1.m_13514_(l));
            }
         }
      } finally {
         this.m_63120_();
      }

   }

   public void m_63111_(CompoundTag p_63112_, String p_63113_, String p_63114_) {
      try {
         this.m_63084_();
         HashMapPalette<T> hashmappalette = new HashMapPalette<>(this.f_63071_, this.f_63076_, this.f_63070_, this.f_63072_, this.f_63073_);
         T t = this.f_63074_;
         int i = hashmappalette.m_6796_(this.f_63074_);
         int[] aint = new int[4096];

         for(int j = 0; j < 4096; ++j) {
            T t1 = this.m_63085_(j);
            if (t1 != t) {
               t = t1;
               i = hashmappalette.m_6796_(t1);
            }

            aint[j] = i;
         }

         ListTag listtag = new ListTag();
         hashmappalette.m_62681_(listtag);
         p_63112_.m_128365_(p_63113_, listtag);
         int l = Math.max(4, Mth.m_14163_(listtag.size()));
         BitStorage bitstorage = new BitStorage(l, 4096);

         for(int k = 0; k < aint.length; ++k) {
            bitstorage.m_13524_(k, aint[k]);
         }

         p_63112_.m_128388_(p_63114_, bitstorage.m_13513_());
      } finally {
         this.m_63120_();
      }

   }

   public int m_63137_() {
      return 1 + this.f_63075_.m_6429_() + FriendlyByteBuf.m_130053_(this.f_63068_.m_13521_()) + this.f_63068_.m_13513_().length * 8;
   }

   public boolean m_63109_(Predicate<T> p_63110_) {
      return this.f_63075_.m_6419_(p_63110_);
   }

   public void m_63099_(PalettedContainer.CountConsumer<T> p_63100_) {
      Int2IntMap int2intmap = new Int2IntOpenHashMap();
      this.f_63068_.m_13519_((p_156469_) -> {
         int2intmap.put(p_156469_, int2intmap.get(p_156469_) + 1);
      });
      int2intmap.int2IntEntrySet().forEach((p_156466_) -> {
         p_63100_.m_63144_(this.f_63075_.m_5795_(p_156466_.getIntKey()), p_156466_.getIntValue());
      });
   }

   @FunctionalInterface
   public interface CountConsumer<T> {
      void m_63144_(T p_63145_, int p_63146_);
   }
}
