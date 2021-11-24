package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1470 extends NamespacedSchema {
   public V1470(int p_17698_, Schema p_17699_) {
      super(p_17698_, p_17699_);
   }

   protected static void m_17705_(Schema p_17706_, Map<String, Supplier<TypeTemplate>> p_17707_, String p_17708_) {
      p_17706_.register(p_17707_, p_17708_, () -> {
         return V100.m_17330_(p_17706_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_17710_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerEntities(p_17710_);
      m_17705_(p_17710_, map, "minecraft:turtle");
      m_17705_(p_17710_, map, "minecraft:cod_mob");
      m_17705_(p_17710_, map, "minecraft:tropical_fish");
      m_17705_(p_17710_, map, "minecraft:salmon_mob");
      m_17705_(p_17710_, map, "minecraft:puffer_fish");
      m_17705_(p_17710_, map, "minecraft:phantom");
      m_17705_(p_17710_, map, "minecraft:dolphin");
      m_17705_(p_17710_, map, "minecraft:drowned");
      p_17710_.register(map, "minecraft:trident", (p_17704_) -> {
         return DSL.optionalFields("inBlockState", References.f_16783_.in(p_17710_));
      });
      return map;
   }
}