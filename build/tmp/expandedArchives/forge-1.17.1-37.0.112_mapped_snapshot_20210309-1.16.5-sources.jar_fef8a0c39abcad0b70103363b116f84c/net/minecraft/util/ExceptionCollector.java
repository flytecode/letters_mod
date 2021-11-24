package net.minecraft.util;

import javax.annotation.Nullable;

public class ExceptionCollector<T extends Throwable> {
   @Nullable
   private T f_13650_;

   public void m_13653_(T p_13654_) {
      if (this.f_13650_ == null) {
         this.f_13650_ = p_13654_;
      } else {
         this.f_13650_.addSuppressed(p_13654_);
      }

   }

   public void m_13652_() throws T {
      if (this.f_13650_ != null) {
         throw this.f_13650_;
      }
   }
}