package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class ItemRenameFix extends DataFix {
   private final String f_15999_;

   public ItemRenameFix(Schema p_16001_, String p_16002_) {
      super(p_16001_, false);
      this.f_15999_ = p_16002_;
   }

   public TypeRewriteRule makeRule() {
      Type<Pair<String, String>> type = DSL.named(References.f_16788_.typeName(), NamespacedSchema.m_17310_());
      if (!Objects.equals(this.getInputSchema().getType(References.f_16788_), type)) {
         throw new IllegalStateException("item name type is not what was expected.");
      } else {
         return this.fixTypeEverywhere(this.f_15999_, type, (p_16010_) -> {
            return (p_145402_) -> {
               return p_145402_.mapSecond(this::m_7348_);
            };
         });
      }
   }

   protected abstract String m_7348_(String p_16011_);

   public static DataFix m_16003_(Schema p_16004_, String p_16005_, final Function<String, String> p_16006_) {
      return new ItemRenameFix(p_16004_, p_16005_) {
         protected String m_7348_(String p_16019_) {
            return p_16006_.apply(p_16019_);
         }
      };
   }
}