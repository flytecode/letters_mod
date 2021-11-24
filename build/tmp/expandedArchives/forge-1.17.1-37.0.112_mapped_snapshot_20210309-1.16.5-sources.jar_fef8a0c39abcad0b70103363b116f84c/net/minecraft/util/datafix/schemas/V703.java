package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V703 extends Schema {
   public V703(int p_18018_, Schema p_18019_) {
      super(p_18018_, p_18019_);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_18031_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerEntities(p_18031_);
      map.remove("EntityHorse");
      p_18031_.register(map, "Horse", () -> {
         return DSL.optionalFields("ArmorItem", References.f_16782_.in(p_18031_), "SaddleItem", References.f_16782_.in(p_18031_), V100.m_17330_(p_18031_));
      });
      p_18031_.register(map, "Donkey", () -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18031_)), "SaddleItem", References.f_16782_.in(p_18031_), V100.m_17330_(p_18031_));
      });
      p_18031_.register(map, "Mule", () -> {
         return DSL.optionalFields("Items", DSL.list(References.f_16782_.in(p_18031_)), "SaddleItem", References.f_16782_.in(p_18031_), V100.m_17330_(p_18031_));
      });
      p_18031_.register(map, "ZombieHorse", () -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_18031_), V100.m_17330_(p_18031_));
      });
      p_18031_.register(map, "SkeletonHorse", () -> {
         return DSL.optionalFields("SaddleItem", References.f_16782_.in(p_18031_), V100.m_17330_(p_18031_));
      });
      return map;
   }
}