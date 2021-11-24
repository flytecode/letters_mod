package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public class OptionsRenameFieldFix extends DataFix {
   private final String f_16666_;
   private final String f_16667_;
   private final String f_16668_;

   public OptionsRenameFieldFix(Schema p_16670_, boolean p_16671_, String p_16672_, String p_16673_, String p_16674_) {
      super(p_16670_, p_16671_);
      this.f_16666_ = p_16672_;
      this.f_16667_ = p_16673_;
      this.f_16668_ = p_16674_;
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(this.f_16666_, this.getInputSchema().getType(References.f_16775_), (p_16676_) -> {
         return p_16676_.update(DSL.remainderFinder(), (p_145592_) -> {
            return DataFixUtils.orElse(p_145592_.get(this.f_16667_).result().map((p_145595_) -> {
               return p_145592_.set(this.f_16668_, p_145595_).remove(this.f_16667_);
            }), p_145592_);
         });
      });
   }
}