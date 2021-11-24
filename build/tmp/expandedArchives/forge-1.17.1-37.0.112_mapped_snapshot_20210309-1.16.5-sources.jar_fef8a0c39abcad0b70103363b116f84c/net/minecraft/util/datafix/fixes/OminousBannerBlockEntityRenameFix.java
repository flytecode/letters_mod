package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class OminousBannerBlockEntityRenameFix extends NamedEntityFix {
   public OminousBannerBlockEntityRenameFix(Schema p_16548_, boolean p_16549_) {
      super(p_16548_, p_16549_, "OminousBannerBlockEntityRenameFix", References.f_16781_, "minecraft:banner");
   }

   protected Typed<?> m_7504_(Typed<?> p_16551_) {
      return p_16551_.update(DSL.remainderFinder(), this::m_16552_);
   }

   private Dynamic<?> m_16552_(Dynamic<?> p_16553_) {
      Optional<String> optional = p_16553_.get("CustomName").asString().result();
      if (optional.isPresent()) {
         String s = optional.get();
         s = s.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
         return p_16553_.set("CustomName", p_16553_.createString(s));
      } else {
         return p_16553_;
      }
   }
}