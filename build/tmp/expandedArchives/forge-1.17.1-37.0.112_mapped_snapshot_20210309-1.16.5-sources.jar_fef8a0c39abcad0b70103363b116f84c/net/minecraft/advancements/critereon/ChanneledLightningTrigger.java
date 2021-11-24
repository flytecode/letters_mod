package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class ChanneledLightningTrigger extends SimpleCriterionTrigger<ChanneledLightningTrigger.TriggerInstance> {
   static final ResourceLocation f_21714_ = new ResourceLocation("channeled_lightning");

   public ResourceLocation m_7295_() {
      return f_21714_;
   }

   public ChanneledLightningTrigger.TriggerInstance m_7214_(JsonObject p_21725_, EntityPredicate.Composite p_21726_, DeserializationContext p_21727_) {
      EntityPredicate.Composite[] aentitypredicate$composite = EntityPredicate.Composite.m_36692_(p_21725_, "victims", p_21727_);
      return new ChanneledLightningTrigger.TriggerInstance(p_21726_, aentitypredicate$composite);
   }

   public void m_21721_(ServerPlayer p_21722_, Collection<? extends Entity> p_21723_) {
      List<LootContext> list = p_21723_.stream().map((p_21720_) -> {
         return EntityPredicate.m_36616_(p_21722_, p_21720_);
      }).collect(Collectors.toList());
      this.m_66234_(p_21722_, (p_21730_) -> {
         return p_21730_.m_21744_(list);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite[] f_21736_;

      public TriggerInstance(EntityPredicate.Composite p_21738_, EntityPredicate.Composite[] p_21739_) {
         super(ChanneledLightningTrigger.f_21714_, p_21738_);
         this.f_21736_ = p_21739_;
      }

      public static ChanneledLightningTrigger.TriggerInstance m_21746_(EntityPredicate... p_21747_) {
         return new ChanneledLightningTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, Stream.of(p_21747_).map(EntityPredicate.Composite::m_36673_).toArray((p_21741_) -> {
            return new EntityPredicate.Composite[p_21741_];
         }));
      }

      public boolean m_21744_(Collection<? extends LootContext> p_21745_) {
         for(EntityPredicate.Composite entitypredicate$composite : this.f_21736_) {
            boolean flag = false;

            for(LootContext lootcontext : p_21745_) {
               if (entitypredicate$composite.m_36681_(lootcontext)) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               return false;
            }
         }

         return true;
      }

      public JsonObject m_7683_(SerializationContext p_21743_) {
         JsonObject jsonobject = super.m_7683_(p_21743_);
         jsonobject.add("victims", EntityPredicate.Composite.m_36687_(this.f_21736_, p_21743_));
         return jsonobject;
      }
   }
}