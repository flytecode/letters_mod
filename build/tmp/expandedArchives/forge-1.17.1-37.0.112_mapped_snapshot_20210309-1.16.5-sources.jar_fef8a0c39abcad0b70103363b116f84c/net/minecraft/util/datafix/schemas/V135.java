package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V135 extends Schema {
   public V135(int p_17404_, Schema p_17405_) {
      super(p_17404_, p_17405_);
   }

   public void registerTypes(Schema p_17411_, Map<String, Supplier<TypeTemplate>> p_17412_, Map<String, Supplier<TypeTemplate>> p_17413_) {
      super.registerTypes(p_17411_, p_17412_, p_17413_);
      p_17411_.registerType(false, References.f_16772_, () -> {
         return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.f_16785_.in(p_17411_)), "Inventory", DSL.list(References.f_16782_.in(p_17411_)), "EnderItems", DSL.list(References.f_16782_.in(p_17411_)));
      });
      p_17411_.registerType(true, References.f_16785_, () -> {
         return DSL.optionalFields("Passengers", DSL.list(References.f_16785_.in(p_17411_)), References.f_16786_.in(p_17411_));
      });
   }
}