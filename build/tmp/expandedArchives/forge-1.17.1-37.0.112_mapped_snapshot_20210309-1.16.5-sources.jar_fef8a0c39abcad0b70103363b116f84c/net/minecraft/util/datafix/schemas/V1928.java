package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1928 extends NamespacedSchema {
   public V1928(int p_17798_, Schema p_17799_) {
      super(p_17798_, p_17799_);
   }

   protected static TypeTemplate m_17800_(Schema p_17801_) {
      return DSL.optionalFields("ArmorItems", DSL.list(References.f_16782_.in(p_17801_)), "HandItems", DSL.list(References.f_16782_.in(p_17801_)));
   }

   protected static void m_17802_(Schema p_17803_, Map<String, Supplier<TypeTemplate>> p_17804_, String p_17805_) {
      p_17803_.register(p_17804_, p_17805_, () -> {
         return m_17800_(p_17803_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17809_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerEntities(p_17809_);
      map.remove("minecraft:illager_beast");
      m_17802_(p_17809_, map, "minecraft:ravager");
      return map;
   }
}