package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V102 extends Schema {
   public V102(int p_17356_, Schema p_17357_) {
      super(p_17356_, p_17357_);
   }

   public void registerTypes(Schema p_17361_, Map<String, Supplier<TypeTemplate>> p_17362_, Map<String, Supplier<TypeTemplate>> p_17363_) {
      super.registerTypes(p_17361_, p_17362_, p_17363_);
      p_17361_.registerType(true, References.f_16782_, () -> {
         return DSL.hook(DSL.optionalFields("id", References.f_16788_.in(p_17361_), "tag", DSL.optionalFields("EntityTag", References.f_16785_.in(p_17361_), "BlockEntityTag", References.f_16781_.in(p_17361_), "CanDestroy", DSL.list(References.f_16787_.in(p_17361_)), "CanPlaceOn", DSL.list(References.f_16787_.in(p_17361_)), "Items", DSL.list(References.f_16782_.in(p_17361_)))), V99.f_18180_, HookFunction.IDENTITY);
      });
   }
}