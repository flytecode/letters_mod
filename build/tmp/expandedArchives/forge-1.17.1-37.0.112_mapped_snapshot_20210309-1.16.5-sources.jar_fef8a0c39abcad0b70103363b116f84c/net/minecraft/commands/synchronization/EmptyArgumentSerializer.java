package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;

public class EmptyArgumentSerializer<T extends ArgumentType<?>> implements ArgumentSerializer<T> {
   private final Supplier<T> f_121630_;

   public EmptyArgumentSerializer(Supplier<T> p_121632_) {
      this.f_121630_ = p_121632_;
   }

   public void m_6017_(T p_121637_, FriendlyByteBuf p_121638_) {
   }

   public T m_7813_(FriendlyByteBuf p_121640_) {
      return this.f_121630_.get();
   }

   public void m_6964_(T p_121634_, JsonObject p_121635_) {
   }
}