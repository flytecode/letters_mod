package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;

public class EntityZombieVillagerTypeFix extends NamedEntityFix {
   private static final int f_145350_ = 6;
   private static final Random f_15803_ = new Random();

   public EntityZombieVillagerTypeFix(Schema p_15806_, boolean p_15807_) {
      super(p_15806_, p_15807_, "EntityZombieVillagerTypeFix", References.f_16786_, "Zombie");
   }

   public Dynamic<?> m_15812_(Dynamic<?> p_15813_) {
      if (p_15813_.get("IsVillager").asBoolean(false)) {
         if (!p_15813_.get("ZombieType").result().isPresent()) {
            int i = this.m_15808_(p_15813_.get("VillagerProfession").asInt(-1));
            if (i == -1) {
               i = this.m_15808_(f_15803_.nextInt(6));
            }

            p_15813_ = p_15813_.set("ZombieType", p_15813_.createInt(i));
         }

         p_15813_ = p_15813_.remove("IsVillager");
      }

      return p_15813_;
   }

   private int m_15808_(int p_15809_) {
      return p_15809_ >= 0 && p_15809_ < 6 ? p_15809_ : -1;
   }

   protected Typed<?> m_7504_(Typed<?> p_15811_) {
      return p_15811_.update(DSL.remainderFinder(), this::m_15812_);
   }
}