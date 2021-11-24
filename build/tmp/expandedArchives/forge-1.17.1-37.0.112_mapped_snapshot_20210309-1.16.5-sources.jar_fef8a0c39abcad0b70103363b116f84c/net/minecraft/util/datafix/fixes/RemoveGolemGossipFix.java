package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class RemoveGolemGossipFix extends NamedEntityFix {
   public RemoveGolemGossipFix(Schema p_16823_, boolean p_16824_) {
      super(p_16823_, p_16824_, "Remove Golem Gossip Fix", References.f_16786_, "minecraft:villager");
   }

   protected Typed<?> m_7504_(Typed<?> p_16826_) {
      return p_16826_.update(DSL.remainderFinder(), RemoveGolemGossipFix::m_16827_);
   }

   private static Dynamic<?> m_16827_(Dynamic<?> p_16828_) {
      return p_16828_.update("Gossips", (p_16831_) -> {
         return p_16828_.createList(p_16831_.asStream().filter((p_145632_) -> {
            return !p_145632_.get("Type").asString("").equals("golem");
         }));
      });
   }
}