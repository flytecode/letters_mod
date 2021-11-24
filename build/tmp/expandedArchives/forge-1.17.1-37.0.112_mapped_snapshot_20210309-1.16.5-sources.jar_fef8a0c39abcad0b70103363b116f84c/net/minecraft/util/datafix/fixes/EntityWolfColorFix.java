package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class EntityWolfColorFix extends NamedEntityFix {
   public EntityWolfColorFix(Schema p_15789_, boolean p_15790_) {
      super(p_15789_, p_15790_, "EntityWolfColorFix", References.f_16786_, "minecraft:wolf");
   }

   public Dynamic<?> m_15793_(Dynamic<?> p_15794_) {
      return p_15794_.update("CollarColor", (p_15796_) -> {
         return p_15796_.createByte((byte)(15 - p_15796_.asInt(0)));
      });
   }

   protected Typed<?> m_7504_(Typed<?> p_15792_) {
      return p_15792_.update(DSL.remainderFinder(), this::m_15793_);
   }
}