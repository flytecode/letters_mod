package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

public interface Palette<T> {
   int m_6796_(T p_63061_);

   boolean m_6419_(Predicate<T> p_63062_);

   @Nullable
   T m_5795_(int p_63060_);

   void m_5680_(FriendlyByteBuf p_63064_);

   void m_5678_(FriendlyByteBuf p_63065_);

   int m_6429_();

   int m_142067_();

   void m_7385_(ListTag p_63063_);
}