package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1022 extends Schema {
   public V1022(int p_17365_, Schema p_17366_) {
      super(p_17365_, p_17366_);
   }

   public void registerTypes(Schema p_17373_, Map<String, Supplier<TypeTemplate>> p_17374_, Map<String, Supplier<TypeTemplate>> p_17375_) {
      super.registerTypes(p_17373_, p_17374_, p_17375_);
      p_17373_.registerType(false, References.f_16793_, () -> {
         return DSL.constType(NamespacedSchema.m_17310_());
      });
      p_17373_.registerType(false, References.f_16772_, () -> {
         return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.f_16785_.in(p_17373_)), "Inventory", DSL.list(References.f_16782_.in(p_17373_)), "EnderItems", DSL.list(References.f_16782_.in(p_17373_)), DSL.optionalFields("ShoulderEntityLeft", References.f_16785_.in(p_17373_), "ShoulderEntityRight", References.f_16785_.in(p_17373_), "recipeBook", DSL.optionalFields("recipes", DSL.list(References.f_16793_.in(p_17373_)), "toBeDisplayed", DSL.list(References.f_16793_.in(p_17373_)))));
      });
      p_17373_.registerType(false, References.f_16774_, () -> {
         return DSL.compoundList(DSL.list(References.f_16782_.in(p_17373_)));
      });
   }
}