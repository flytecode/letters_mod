package net.minecraft.commands.synchronization.brigadier;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;

public class BrigadierArgumentSerializers {
   private static final byte f_175214_ = 1;
   private static final byte f_175215_ = 2;

   public static void m_121685_() {
      ArgumentTypes.m_121601_("brigadier:bool", BoolArgumentType.class, new EmptyArgumentSerializer<>(BoolArgumentType::bool));
      ArgumentTypes.m_121601_("brigadier:float", FloatArgumentType.class, new FloatArgumentSerializer());
      ArgumentTypes.m_121601_("brigadier:double", DoubleArgumentType.class, new DoubleArgumentSerializer());
      ArgumentTypes.m_121601_("brigadier:integer", IntegerArgumentType.class, new IntegerArgumentSerializer());
      ArgumentTypes.m_121601_("brigadier:long", LongArgumentType.class, new LongArgumentSerializer());
      ArgumentTypes.m_121601_("brigadier:string", StringArgumentType.class, new StringArgumentSerializer());
   }

   public static byte m_121688_(boolean p_121689_, boolean p_121690_) {
      byte b0 = 0;
      if (p_121689_) {
         b0 = (byte)(b0 | 1);
      }

      if (p_121690_) {
         b0 = (byte)(b0 | 2);
      }

      return b0;
   }

   public static boolean m_121686_(byte p_121687_) {
      return (p_121687_ & 1) != 0;
   }

   public static boolean m_121691_(byte p_121692_) {
      return (p_121692_ & 2) != 0;
   }
}