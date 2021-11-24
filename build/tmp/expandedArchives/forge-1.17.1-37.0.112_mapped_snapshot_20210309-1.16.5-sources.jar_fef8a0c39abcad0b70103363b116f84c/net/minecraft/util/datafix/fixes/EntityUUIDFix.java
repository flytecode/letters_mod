package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class EntityUUIDFix extends AbstractUUIDFix {
   private static final Set<String> f_15715_ = Sets.newHashSet();
   private static final Set<String> f_15716_ = Sets.newHashSet();
   private static final Set<String> f_15717_ = Sets.newHashSet();
   private static final Set<String> f_15718_ = Sets.newHashSet();
   private static final Set<String> f_15719_ = Sets.newHashSet();
   private static final Set<String> f_15720_ = Sets.newHashSet();

   public EntityUUIDFix(Schema p_15723_) {
      super(p_15723_, References.f_16786_);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.f_14569_), (p_15725_) -> {
         p_15725_ = p_15725_.update(DSL.remainderFinder(), EntityUUIDFix::m_15734_);

         for(String s : f_15715_) {
            p_15725_ = this.m_14574_(p_15725_, s, EntityUUIDFix::m_15761_);
         }

         for(String s1 : f_15716_) {
            p_15725_ = this.m_14574_(p_15725_, s1, EntityUUIDFix::m_15761_);
         }

         for(String s2 : f_15717_) {
            p_15725_ = this.m_14574_(p_15725_, s2, EntityUUIDFix::m_15763_);
         }

         for(String s3 : f_15718_) {
            p_15725_ = this.m_14574_(p_15725_, s3, EntityUUIDFix::m_15766_);
         }

         for(String s4 : f_15719_) {
            p_15725_ = this.m_14574_(p_15725_, s4, EntityUUIDFix::m_15729_);
         }

         for(String s5 : f_15720_) {
            p_15725_ = this.m_14574_(p_15725_, s5, EntityUUIDFix::m_15768_);
         }

         p_15725_ = this.m_14574_(p_15725_, "minecraft:bee", EntityUUIDFix::m_15759_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:zombified_piglin", EntityUUIDFix::m_15759_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:fox", EntityUUIDFix::m_15757_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:item", EntityUUIDFix::m_15755_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:shulker_bullet", EntityUUIDFix::m_15753_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:area_effect_cloud", EntityUUIDFix::m_15751_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:zombie_villager", EntityUUIDFix::m_15749_);
         p_15725_ = this.m_14574_(p_15725_, "minecraft:evoker_fangs", EntityUUIDFix::m_15744_);
         return this.m_14574_(p_15725_, "minecraft:piglin", EntityUUIDFix::m_15739_);
      });
   }

   private static Dynamic<?> m_15739_(Dynamic<?> p_15740_) {
      return p_15740_.update("Brain", (p_15781_) -> {
         return p_15781_.update("memories", (p_145345_) -> {
            return p_145345_.update("minecraft:angry_at", (p_145347_) -> {
               return m_14590_(p_145347_, "value", "value").orElseGet(() -> {
                  f_14568_.warn("angry_at has no value.");
                  return p_145347_;
               });
            });
         });
      });
   }

   private static Dynamic<?> m_15744_(Dynamic<?> p_15745_) {
      return m_14617_(p_15745_, "OwnerUUID", "Owner").orElse(p_15745_);
   }

   private static Dynamic<?> m_15749_(Dynamic<?> p_15750_) {
      return m_14617_(p_15750_, "ConversionPlayer", "ConversionPlayer").orElse(p_15750_);
   }

   private static Dynamic<?> m_15751_(Dynamic<?> p_15752_) {
      return m_14617_(p_15752_, "OwnerUUID", "Owner").orElse(p_15752_);
   }

   private static Dynamic<?> m_15753_(Dynamic<?> p_15754_) {
      p_15754_ = m_14608_(p_15754_, "Owner", "Owner").orElse(p_15754_);
      return m_14608_(p_15754_, "Target", "Target").orElse(p_15754_);
   }

   private static Dynamic<?> m_15755_(Dynamic<?> p_15756_) {
      p_15756_ = m_14608_(p_15756_, "Owner", "Owner").orElse(p_15756_);
      return m_14608_(p_15756_, "Thrower", "Thrower").orElse(p_15756_);
   }

   private static Dynamic<?> m_15757_(Dynamic<?> p_15758_) {
      Optional<Dynamic<?>> optional = p_15758_.get("TrustedUUIDs").result().map((p_15748_) -> {
         return p_15758_.createList(p_15748_.asStream().map((p_145341_) -> {
            return m_14578_(p_145341_).orElseGet(() -> {
               f_14568_.warn("Trusted contained invalid data.");
               return p_145341_;
            });
         }));
      });
      return DataFixUtils.orElse(optional.map((p_15743_) -> {
         return p_15758_.remove("TrustedUUIDs").set("Trusted", p_15743_);
      }), p_15758_);
   }

   private static Dynamic<?> m_15759_(Dynamic<?> p_15760_) {
      return m_14590_(p_15760_, "HurtBy", "HurtBy").orElse(p_15760_);
   }

   private static Dynamic<?> m_15761_(Dynamic<?> p_15762_) {
      Dynamic<?> dynamic = m_15763_(p_15762_);
      return m_14590_(dynamic, "OwnerUUID", "Owner").orElse(dynamic);
   }

   private static Dynamic<?> m_15763_(Dynamic<?> p_15764_) {
      Dynamic<?> dynamic = m_15766_(p_15764_);
      return m_14617_(dynamic, "LoveCause", "LoveCause").orElse(dynamic);
   }

   private static Dynamic<?> m_15766_(Dynamic<?> p_15767_) {
      return m_15729_(p_15767_).update("Leash", (p_15775_) -> {
         return m_14617_(p_15775_, "UUID", "UUID").orElse(p_15775_);
      });
   }

   public static Dynamic<?> m_15729_(Dynamic<?> p_15730_) {
      return p_15730_.update("Attributes", (p_15733_) -> {
         return p_15730_.createList(p_15733_.asStream().map((p_145337_) -> {
            return p_145337_.update("Modifiers", (p_145335_) -> {
               return p_145337_.createList(p_145335_.asStream().map((p_145339_) -> {
                  return m_14617_(p_145339_, "UUID", "UUID").orElse(p_145339_);
               }));
            });
         }));
      });
   }

   private static Dynamic<?> m_15768_(Dynamic<?> p_15769_) {
      return DataFixUtils.orElse(p_15769_.get("OwnerUUID").result().map((p_15728_) -> {
         return p_15769_.remove("OwnerUUID").set("Owner", p_15728_);
      }), p_15769_);
   }

   public static Dynamic<?> m_15734_(Dynamic<?> p_15735_) {
      return m_14617_(p_15735_, "UUID", "UUID").orElse(p_15735_);
   }

   static {
      f_15715_.add("minecraft:donkey");
      f_15715_.add("minecraft:horse");
      f_15715_.add("minecraft:llama");
      f_15715_.add("minecraft:mule");
      f_15715_.add("minecraft:skeleton_horse");
      f_15715_.add("minecraft:trader_llama");
      f_15715_.add("minecraft:zombie_horse");
      f_15716_.add("minecraft:cat");
      f_15716_.add("minecraft:parrot");
      f_15716_.add("minecraft:wolf");
      f_15717_.add("minecraft:bee");
      f_15717_.add("minecraft:chicken");
      f_15717_.add("minecraft:cow");
      f_15717_.add("minecraft:fox");
      f_15717_.add("minecraft:mooshroom");
      f_15717_.add("minecraft:ocelot");
      f_15717_.add("minecraft:panda");
      f_15717_.add("minecraft:pig");
      f_15717_.add("minecraft:polar_bear");
      f_15717_.add("minecraft:rabbit");
      f_15717_.add("minecraft:sheep");
      f_15717_.add("minecraft:turtle");
      f_15717_.add("minecraft:hoglin");
      f_15718_.add("minecraft:bat");
      f_15718_.add("minecraft:blaze");
      f_15718_.add("minecraft:cave_spider");
      f_15718_.add("minecraft:cod");
      f_15718_.add("minecraft:creeper");
      f_15718_.add("minecraft:dolphin");
      f_15718_.add("minecraft:drowned");
      f_15718_.add("minecraft:elder_guardian");
      f_15718_.add("minecraft:ender_dragon");
      f_15718_.add("minecraft:enderman");
      f_15718_.add("minecraft:endermite");
      f_15718_.add("minecraft:evoker");
      f_15718_.add("minecraft:ghast");
      f_15718_.add("minecraft:giant");
      f_15718_.add("minecraft:guardian");
      f_15718_.add("minecraft:husk");
      f_15718_.add("minecraft:illusioner");
      f_15718_.add("minecraft:magma_cube");
      f_15718_.add("minecraft:pufferfish");
      f_15718_.add("minecraft:zombified_piglin");
      f_15718_.add("minecraft:salmon");
      f_15718_.add("minecraft:shulker");
      f_15718_.add("minecraft:silverfish");
      f_15718_.add("minecraft:skeleton");
      f_15718_.add("minecraft:slime");
      f_15718_.add("minecraft:snow_golem");
      f_15718_.add("minecraft:spider");
      f_15718_.add("minecraft:squid");
      f_15718_.add("minecraft:stray");
      f_15718_.add("minecraft:tropical_fish");
      f_15718_.add("minecraft:vex");
      f_15718_.add("minecraft:villager");
      f_15718_.add("minecraft:iron_golem");
      f_15718_.add("minecraft:vindicator");
      f_15718_.add("minecraft:pillager");
      f_15718_.add("minecraft:wandering_trader");
      f_15718_.add("minecraft:witch");
      f_15718_.add("minecraft:wither");
      f_15718_.add("minecraft:wither_skeleton");
      f_15718_.add("minecraft:zombie");
      f_15718_.add("minecraft:zombie_villager");
      f_15718_.add("minecraft:phantom");
      f_15718_.add("minecraft:ravager");
      f_15718_.add("minecraft:piglin");
      f_15719_.add("minecraft:armor_stand");
      f_15720_.add("minecraft:arrow");
      f_15720_.add("minecraft:dragon_fireball");
      f_15720_.add("minecraft:firework_rocket");
      f_15720_.add("minecraft:fireball");
      f_15720_.add("minecraft:llama_spit");
      f_15720_.add("minecraft:small_fireball");
      f_15720_.add("minecraft:snowball");
      f_15720_.add("minecraft:spectral_arrow");
      f_15720_.add("minecraft:egg");
      f_15720_.add("minecraft:ender_pearl");
      f_15720_.add("minecraft:experience_bottle");
      f_15720_.add("minecraft:potion");
      f_15720_.add("minecraft:trident");
      f_15720_.add("minecraft:wither_skull");
   }
}