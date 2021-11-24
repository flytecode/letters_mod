package net.minecraft.network.syncher;

import net.minecraft.network.FriendlyByteBuf;

public interface EntityDataSerializer<T> {
   void m_6856_(FriendlyByteBuf p_135025_, T p_135026_);

   T m_6709_(FriendlyByteBuf p_135024_);

   default EntityDataAccessor<T> m_135021_(int p_135022_) {
      return new EntityDataAccessor<>(p_135022_, this);
   }

   T m_7020_(T p_135023_);
}