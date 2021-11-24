package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class RenameBiomesFix extends DataFix {
   private final String f_16834_;
   private final Map<String, String> f_16835_;

   public RenameBiomesFix(Schema p_16837_, boolean p_16838_, String p_16839_, Map<String, String> p_16840_) {
      super(p_16837_, p_16838_);
      this.f_16835_ = p_16840_;
      this.f_16834_ = p_16839_;
   }

   protected TypeRewriteRule makeRule() {
      Type<Pair<String, String>> type = DSL.named(References.f_16794_.typeName(), NamespacedSchema.m_17310_());
      if (!Objects.equals(type, this.getInputSchema().getType(References.f_16794_))) {
         throw new IllegalStateException("Biome type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.f_16834_, type, (p_16844_) -> {
            return (p_145634_) -> {
               return p_145634_.mapSecond((p_145636_) -> {
                  return this.f_16835_.getOrDefault(p_145636_, p_145636_);
               });
            };
         });
      }
   }
}