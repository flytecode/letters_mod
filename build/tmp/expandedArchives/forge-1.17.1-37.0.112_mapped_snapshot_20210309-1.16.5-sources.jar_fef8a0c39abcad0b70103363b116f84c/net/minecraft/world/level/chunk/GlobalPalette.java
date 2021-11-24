package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

public class GlobalPalette<T> implements Palette<T> {
   private final IdMapper<T> f_62639_;
   private final T f_62640_;

   public GlobalPalette(IdMapper<T> p_62642_, T p_62643_) {
      this.f_62639_ = p_62642_;
      this.f_62640_ = p_62643_;
   }

   public int m_6796_(T p_62648_) {
      int i = this.f_62639_.m_7447_(p_62648_);
      return i == -1 ? 0 : i;
   }

   public boolean m_6419_(Predicate<T> p_62650_) {
      return true;
   }

   public T m_5795_(int p_62646_) {
      T t = this.f_62639_.m_7942_(p_62646_);
      return (T)(t == null ? this.f_62640_ : t);
   }

   public void m_5680_(FriendlyByteBuf p_62654_) {
   }

   public void m_5678_(FriendlyByteBuf p_62656_) {
   }

   public int m_6429_() {
      return FriendlyByteBuf.m_130053_(0);
   }

   public int m_142067_() {
      return this.f_62639_.m_122659_();
   }

   public void m_7385_(ListTag p_62652_) {
   }
}