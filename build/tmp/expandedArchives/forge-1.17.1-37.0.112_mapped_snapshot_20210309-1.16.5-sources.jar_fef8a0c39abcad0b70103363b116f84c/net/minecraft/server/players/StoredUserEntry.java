package net.minecraft.server.players;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public abstract class StoredUserEntry<T> {
   @Nullable
   private final T f_11369_;

   public StoredUserEntry(@Nullable T p_11371_) {
      this.f_11369_ = p_11371_;
   }

   @Nullable
   T m_11373_() {
      return this.f_11369_;
   }

   boolean m_7524_() {
      return false;
   }

   protected abstract void m_6009_(JsonObject p_11372_);
}