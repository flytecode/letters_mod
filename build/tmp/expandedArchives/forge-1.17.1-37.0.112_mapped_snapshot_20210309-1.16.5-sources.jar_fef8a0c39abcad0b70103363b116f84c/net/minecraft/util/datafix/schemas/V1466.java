package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1466 extends NamespacedSchema {
   public V1466(int p_17685_, Schema p_17686_) {
      super(p_17685_, p_17686_);
   }

   public void registerTypes(Schema p_17694_, Map<String, Supplier<TypeTemplate>> p_17695_, Map<String, Supplier<TypeTemplate>> p_17696_) {
      super.registerTypes(p_17694_, p_17695_, p_17696_);
      p_17694_.registerType(false, References.f_16773_, () -> {
         return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.f_16785_.in(p_17694_)), "TileEntities", DSL.list(References.f_16781_.in(p_17694_)), "TileTicks", DSL.list(DSL.fields("i", References.f_16787_.in(p_17694_))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(References.f_16783_.in(p_17694_)))), "Structures", DSL.optionalFields("Starts", DSL.compoundList(References.f_16790_.in(p_17694_)))));
      });
      p_17694_.registerType(false, References.f_16790_, () -> {
         return DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", References.f_16783_.in(p_17694_), "CB", References.f_16783_.in(p_17694_), "CC", References.f_16783_.in(p_17694_), "CD", References.f_16783_.in(p_17694_))), "biome", References.f_16794_.in(p_17694_));
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_17692_) {
      Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(p_17692_);
      map.put("DUMMY", DSL::remainder);
      return map;
   }
}