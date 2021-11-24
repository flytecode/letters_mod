package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class IntegerArgumentSerializer implements ArgumentSerializer<IntegerArgumentType> {
   public void m_6017_(IntegerArgumentType p_121738_, FriendlyByteBuf p_121739_) {
      boolean flag = p_121738_.getMinimum() != Integer.MIN_VALUE;
      boolean flag1 = p_121738_.getMaximum() != Integer.MAX_VALUE;
      p_121739_.writeByte(BrigadierArgumentSerializers.m_121688_(flag, flag1));
      if (flag) {
         p_121739_.writeInt(p_121738_.getMinimum());
      }

      if (flag1) {
         p_121739_.writeInt(p_121738_.getMaximum());
      }

   }

   public IntegerArgumentType m_7813_(FriendlyByteBuf p_121741_) {
      byte b0 = p_121741_.readByte();
      int i = BrigadierArgumentSerializers.m_121686_(b0) ? p_121741_.readInt() : Integer.MIN_VALUE;
      int j = BrigadierArgumentSerializers.m_121691_(b0) ? p_121741_.readInt() : Integer.MAX_VALUE;
      return IntegerArgumentType.integer(i, j);
   }

   public void m_6964_(IntegerArgumentType p_121735_, JsonObject p_121736_) {
      if (p_121735_.getMinimum() != Integer.MIN_VALUE) {
         p_121736_.addProperty("min", p_121735_.getMinimum());
      }

      if (p_121735_.getMaximum() != Integer.MAX_VALUE) {
         p_121736_.addProperty("max", p_121735_.getMaximum());
      }

   }
}