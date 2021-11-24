package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V501 extends Schema {
   public V501(int p_17974_, Schema p_17975_) {
      super(p_17974_, p_17975_);
   }

   protected static void m_17978_(Schema p_17979_, Map<String, Supplier<TypeTemplate>> p_17980_, String p_17981_) {
      p_17979_.register(p_17980_, p_17981_, () -> {
         return V100.m_17330_(p_17979_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17983_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerEntities(p_17983_);
      m_17978_(p_17983_, map, "PolarBear");
      return map;
   }
}