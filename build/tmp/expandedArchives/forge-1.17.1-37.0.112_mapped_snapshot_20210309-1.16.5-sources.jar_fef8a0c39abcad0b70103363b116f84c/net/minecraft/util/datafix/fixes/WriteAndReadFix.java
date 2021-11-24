package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public class WriteAndReadFix extends DataFix {
   private final String f_17290_;
   private final TypeReference f_17291_;

   public WriteAndReadFix(Schema p_17293_, String p_17294_, TypeReference p_17295_) {
      super(p_17293_, true);
      this.f_17290_ = p_17294_;
      this.f_17291_ = p_17295_;
   }

   protected TypeRewriteRule makeRule() {
      return this.writeAndRead(this.f_17290_, this.getInputSchema().getType(this.f_17291_), this.getOutputSchema().getType(this.f_17291_));
   }
}