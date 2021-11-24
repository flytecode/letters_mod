package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public class EntityPaintingMotiveFix extends NamedEntityFix {
   private static final Map<String, String> f_15522_ = DataFixUtils.make(Maps.newHashMap(), (p_15532_) -> {
      p_15532_.put("donkeykong", "donkey_kong");
      p_15532_.put("burningskull", "burning_skull");
      p_15532_.put("skullandroses", "skull_and_roses");
   });

   public EntityPaintingMotiveFix(Schema p_15525_, boolean p_15526_) {
      super(p_15525_, p_15526_, "EntityPaintingMotiveFix", References.f_16786_, "minecraft:painting");
   }

   public Dynamic<?> m_15529_(Dynamic<?> p_15530_) {
      Optional<String> optional = p_15530_.get("Motive").asString().result();
      if (optional.isPresent()) {
         String s = optional.get().toLowerCase(Locale.ROOT);
         return p_15530_.set("Motive", p_15530_.createString((new ResourceLocation(f_15522_.getOrDefault(s, s))).toString()));
      } else {
         return p_15530_;
      }
   }

   protected Typed<?> m_7504_(Typed<?> p_15528_) {
      return p_15528_.update(DSL.remainderFinder(), this::m_15529_);
   }
}