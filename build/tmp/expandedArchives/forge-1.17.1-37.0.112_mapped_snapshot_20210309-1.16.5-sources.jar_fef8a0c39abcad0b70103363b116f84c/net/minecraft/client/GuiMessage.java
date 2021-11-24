package net.minecraft.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiMessage<T> {
   private final int f_90786_;
   private final T f_90787_;
   private final int f_90788_;

   public GuiMessage(int p_90790_, T p_90791_, int p_90792_) {
      this.f_90787_ = p_90791_;
      this.f_90786_ = p_90790_;
      this.f_90788_ = p_90792_;
   }

   public T m_90793_() {
      return this.f_90787_;
   }

   public int m_90794_() {
      return this.f_90786_;
   }

   public int m_90795_() {
      return this.f_90788_;
   }
}