package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.function.Function;

public class AdvancementsRenameFix extends DataFix {
   private final String f_14649_;
   private final Function<String, String> f_14650_;

   public AdvancementsRenameFix(Schema p_14652_, boolean p_14653_, String p_14654_, Function<String, String> p_14655_) {
      super(p_14652_, p_14653_);
      this.f_14649_ = p_14654_;
      this.f_14650_ = p_14655_;
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(this.f_14649_, this.getInputSchema().getType(References.f_16779_), (p_14657_) -> {
         return p_14657_.update(DSL.remainderFinder(), (p_145063_) -> {
            return p_145063_.updateMapValues((p_145066_) -> {
               String s = p_145066_.getFirst().asString("");
               return p_145066_.mapFirst((p_145070_) -> {
                  return p_145063_.createString(this.f_14650_.apply(s));
               });
            });
         });
      });
   }
}