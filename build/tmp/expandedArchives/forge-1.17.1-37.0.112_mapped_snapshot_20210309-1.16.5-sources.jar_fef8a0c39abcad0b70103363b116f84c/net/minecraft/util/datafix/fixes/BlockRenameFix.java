package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class BlockRenameFix extends DataFix {
   private final String f_14908_;

   public BlockRenameFix(Schema p_14910_, String p_14911_) {
      super(p_14910_, false);
      this.f_14908_ = p_14911_;
   }

   public TypeRewriteRule makeRule() {
      Type<?> type = this.getInputSchema().getType(References.f_16787_);
      Type<Pair<String, String>> type1 = DSL.named(References.f_16787_.typeName(), NamespacedSchema.m_17310_());
      if (!Objects.equals(type, type1)) {
         throw new IllegalStateException("block type is not what was expected.");
      } else {
         TypeRewriteRule typerewriterule = this.fixTypeEverywhere(this.f_14908_ + " for block", type1, (p_14923_) -> {
            return (p_145145_) -> {
               return p_145145_.mapSecond(this::m_7384_);
            };
         });
         TypeRewriteRule typerewriterule1 = this.fixTypeEverywhereTyped(this.f_14908_ + " for block_state", this.getInputSchema().getType(References.f_16783_), (p_14913_) -> {
            return p_14913_.update(DSL.remainderFinder(), (p_145147_) -> {
               Optional<String> optional = p_145147_.get("Name").asString().result();
               return optional.isPresent() ? p_145147_.set("Name", p_145147_.createString(this.m_7384_(optional.get()))) : p_145147_;
            });
         });
         return TypeRewriteRule.seq(typerewriterule, typerewriterule1);
      }
   }

   protected abstract String m_7384_(String p_14924_);

   public static DataFix m_14914_(Schema p_14915_, String p_14916_, final Function<String, String> p_14917_) {
      return new BlockRenameFix(p_14915_, p_14916_) {
         protected String m_7384_(String p_14932_) {
            return p_14917_.apply(p_14932_);
         }
      };
   }
}