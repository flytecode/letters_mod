package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class BlockEntityBannerColorFix extends NamedEntityFix {
   public BlockEntityBannerColorFix(Schema p_14793_, boolean p_14794_) {
      super(p_14793_, p_14794_, "BlockEntityBannerColorFix", References.f_16781_, "minecraft:banner");
   }

   public Dynamic<?> m_14797_(Dynamic<?> p_14798_) {
      p_14798_ = p_14798_.update("Base", (p_14808_) -> {
         return p_14808_.createInt(15 - p_14808_.asInt(0));
      });
      return p_14798_.update("Patterns", (p_14802_) -> {
         return DataFixUtils.orElse(p_14802_.asStreamOpt().map((p_145125_) -> {
            return p_145125_.map((p_145127_) -> {
               return p_145127_.update("Color", (p_145129_) -> {
                  return p_145129_.createInt(15 - p_145129_.asInt(0));
               });
            });
         }).map(p_14802_::createList).result(), p_14802_);
      });
   }

   protected Typed<?> m_7504_(Typed<?> p_14796_) {
      return p_14796_.update(DSL.remainderFinder(), this::m_14797_);
   }
}