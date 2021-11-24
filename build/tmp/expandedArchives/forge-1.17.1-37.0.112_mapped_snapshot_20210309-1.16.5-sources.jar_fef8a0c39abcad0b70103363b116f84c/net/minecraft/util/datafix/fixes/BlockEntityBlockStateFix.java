package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class BlockEntityBlockStateFix extends NamedEntityFix {
   public BlockEntityBlockStateFix(Schema p_14810_, boolean p_14811_) {
      super(p_14810_, p_14811_, "BlockEntityBlockStateFix", References.f_16781_, "minecraft:piston");
   }

   protected Typed<?> m_7504_(Typed<?> p_14814_) {
      Type<?> type = this.getOutputSchema().getChoiceType(References.f_16781_, "minecraft:piston");
      Type<?> type1 = type.findFieldType("blockState");
      OpticFinder<?> opticfinder = DSL.fieldFinder("blockState", type1);
      Dynamic<?> dynamic = p_14814_.get(DSL.remainderFinder());
      int i = dynamic.get("blockId").asInt(0);
      dynamic = dynamic.remove("blockId");
      int j = dynamic.get("blockData").asInt(0) & 15;
      dynamic = dynamic.remove("blockData");
      Dynamic<?> dynamic1 = BlockStateData.m_14952_(i << 4 | j);
      Typed<?> typed = type.pointTyped(p_14814_.getOps()).orElseThrow(() -> {
         return new IllegalStateException("Could not create new piston block entity.");
      });
      return typed.set(DSL.remainderFinder(), dynamic).set(opticfinder, type1.readTyped(dynamic1).result().orElseThrow(() -> {
         return new IllegalStateException("Could not parse newly created block state tag.");
      }).getFirst());
   }
}