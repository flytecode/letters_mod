package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class BlockEntityUUIDFix extends AbstractUUIDFix {
   public BlockEntityUUIDFix(Schema p_14883_) {
      super(p_14883_, References.f_16781_);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.f_14569_), (p_14885_) -> {
         p_14885_ = this.m_14574_(p_14885_, "minecraft:conduit", this::m_14891_);
         return this.m_14574_(p_14885_, "minecraft:skull", this::m_14889_);
      });
   }

   private Dynamic<?> m_14889_(Dynamic<?> p_14890_) {
      return p_14890_.get("Owner").get().map((p_14894_) -> {
         return m_14590_(p_14894_, "Id", "Id").orElse(p_14894_);
      }).<Dynamic<?>>map((p_14888_) -> {
         return p_14890_.remove("Owner").set("SkullOwner", p_14888_);
      }).result().orElse(p_14890_);
   }

   private Dynamic<?> m_14891_(Dynamic<?> p_14892_) {
      return m_14608_(p_14892_, "target_uuid", "Target").orElse(p_14892_);
   }
}