package net.minecraft.world.level.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;

public class HashMapPalette<T> implements Palette<T> {
   private final IdMapper<T> f_62657_;
   private final CrudeIncrementalIntIdentityHashBiMap<T> f_62658_;
   private final PaletteResize<T> f_62659_;
   private final Function<CompoundTag, T> f_62660_;
   private final Function<T, CompoundTag> f_62661_;
   private final int f_62662_;

   public HashMapPalette(IdMapper<T> p_62664_, int p_62665_, PaletteResize<T> p_62666_, Function<CompoundTag, T> p_62667_, Function<T, CompoundTag> p_62668_) {
      this.f_62657_ = p_62664_;
      this.f_62662_ = p_62665_;
      this.f_62659_ = p_62666_;
      this.f_62660_ = p_62667_;
      this.f_62661_ = p_62668_;
      this.f_62658_ = new CrudeIncrementalIntIdentityHashBiMap<>(1 << p_62665_);
   }

   public int m_6796_(T p_62673_) {
      int i = this.f_62658_.m_7447_(p_62673_);
      if (i == -1) {
         i = this.f_62658_.m_13569_(p_62673_);
         if (i >= 1 << this.f_62662_) {
            i = this.f_62659_.m_7248_(this.f_62662_ + 1, p_62673_);
         }
      }

      return i;
   }

   public boolean m_6419_(Predicate<T> p_62675_) {
      for(int i = 0; i < this.m_142067_(); ++i) {
         if (p_62675_.test(this.f_62658_.m_7942_(i))) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public T m_5795_(int p_62671_) {
      return this.f_62658_.m_7942_(p_62671_);
   }

   public void m_5680_(FriendlyByteBuf p_62679_) {
      this.f_62658_.m_13554_();
      int i = p_62679_.m_130242_();

      for(int j = 0; j < i; ++j) {
         this.f_62658_.m_13569_(this.f_62657_.m_7942_(p_62679_.m_130242_()));
      }

   }

   public void m_5678_(FriendlyByteBuf p_62684_) {
      int i = this.m_142067_();
      p_62684_.m_130130_(i);

      for(int j = 0; j < i; ++j) {
         p_62684_.m_130130_(this.f_62657_.m_7447_(this.f_62658_.m_7942_(j)));
      }

   }

   public int m_6429_() {
      int i = FriendlyByteBuf.m_130053_(this.m_142067_());

      for(int j = 0; j < this.m_142067_(); ++j) {
         i += FriendlyByteBuf.m_130053_(this.f_62657_.m_7447_(this.f_62658_.m_7942_(j)));
      }

      return i;
   }

   public int m_142067_() {
      return this.f_62658_.m_13562_();
   }

   public void m_7385_(ListTag p_62677_) {
      this.f_62658_.m_13554_();

      for(int i = 0; i < p_62677_.size(); ++i) {
         this.f_62658_.m_13569_(this.f_62660_.apply(p_62677_.m_128728_(i)));
      }

   }

   public void m_62681_(ListTag p_62682_) {
      for(int i = 0; i < this.m_142067_(); ++i) {
         p_62682_.add(this.f_62661_.apply(this.f_62658_.m_7942_(i)));
      }

   }
}