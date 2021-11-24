package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Map;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class StatsRenameFix extends DataFix {
   private final String f_145702_;
   private final Map<String, String> f_145703_;

   public StatsRenameFix(Schema p_145705_, String p_145706_, Map<String, String> p_145707_) {
      super(p_145705_, false);
      this.f_145702_ = p_145706_;
      this.f_145703_ = p_145707_;
   }

   protected TypeRewriteRule makeRule() {
      return TypeRewriteRule.seq(this.m_181057_(), this.m_181042_());
   }

   private TypeRewriteRule m_181042_() {
      Type<?> type = this.getOutputSchema().getType(References.f_16791_);
      Type<?> type1 = this.getInputSchema().getType(References.f_16791_);
      OpticFinder<?> opticfinder = type1.findField("CriteriaType");
      TaggedChoiceType<?> taggedchoicetype = opticfinder.type().findChoiceType("type", -1).orElseThrow(() -> {
         return new IllegalStateException("Can't find choice type for criteria");
      });
      Type<?> type2 = taggedchoicetype.types().get("minecraft:custom");
      if (type2 == null) {
         throw new IllegalStateException("Failed to find custom criterion type variant");
      } else {
         OpticFinder<?> opticfinder1 = DSL.namedChoice("minecraft:custom", type2);
         OpticFinder<String> opticfinder2 = DSL.fieldFinder("id", NamespacedSchema.m_17310_());
         return this.fixTypeEverywhereTyped(this.f_145702_, type1, type, (p_181062_) -> {
            return p_181062_.updateTyped(opticfinder, (p_181066_) -> {
               return p_181066_.updateTyped(opticfinder1, (p_181069_) -> {
                  return p_181069_.update(opticfinder2, (p_181071_) -> {
                     return this.f_145703_.getOrDefault(p_181071_, p_181071_);
                  });
               });
            });
         });
      }
   }

   private TypeRewriteRule m_181057_() {
      Type<?> type = this.getOutputSchema().getType(References.f_16777_);
      Type<?> type1 = this.getInputSchema().getType(References.f_16777_);
      OpticFinder<?> opticfinder = type1.findField("stats");
      OpticFinder<?> opticfinder1 = opticfinder.type().findField("minecraft:custom");
      OpticFinder<String> opticfinder2 = NamespacedSchema.m_17310_().finder();
      return this.fixTypeEverywhereTyped(this.f_145702_, type1, type, (p_145712_) -> {
         return p_145712_.updateTyped(opticfinder, (p_145716_) -> {
            return p_145716_.updateTyped(opticfinder1, (p_145719_) -> {
               return p_145719_.update(opticfinder2, (p_145721_) -> {
                  return this.f_145703_.getOrDefault(p_145721_, p_145721_);
               });
            });
         });
      });
   }
}