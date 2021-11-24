package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.network.FriendlyByteBuf;

public interface ArgumentSerializer<T extends ArgumentType<?>> {
   void m_6017_(T p_121579_, FriendlyByteBuf p_121580_);

   T m_7813_(FriendlyByteBuf p_121581_);

   void m_6964_(T p_121577_, JsonObject p_121578_);
}