package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class BlockNameFlatteningFix extends DataFix {
   public BlockNameFlatteningFix(Schema p_14897_, boolean p_14898_) {
      super(p_14897_, p_14898_);
   }

   public TypeRewriteRule makeRule() {
      Type<?> type = this.getInputSchema().getType(References.f_16787_);
      Type<?> type1 = this.getOutputSchema().getType(References.f_16787_);
      Type<Pair<String, Either<Integer, String>>> type2 = DSL.named(References.f_16787_.typeName(), DSL.or(DSL.intType(), NamespacedSchema.m_17310_()));
      Type<Pair<String, String>> type3 = DSL.named(References.f_16787_.typeName(), NamespacedSchema.m_17310_());
      if (Objects.equals(type, type2) && Objects.equals(type1, type3)) {
         return this.fixTypeEverywhere("BlockNameFlatteningFix", type2, type3, (p_14904_) -> {
            return (p_145141_) -> {
               return p_145141_.mapSecond((p_145139_) -> {
                  return p_145139_.map(BlockStateData::m_14940_, (p_145143_) -> {
                     return BlockStateData.m_14950_(NamespacedSchema.m_17311_(p_145143_));
                  });
               });
            };
         });
      } else {
         throw new IllegalStateException("Expected and actual types don't match.");
      }
   }
}