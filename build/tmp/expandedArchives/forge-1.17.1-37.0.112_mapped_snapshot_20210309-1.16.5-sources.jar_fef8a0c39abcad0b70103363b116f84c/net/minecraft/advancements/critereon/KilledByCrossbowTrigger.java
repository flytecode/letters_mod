package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;

public class KilledByCrossbowTrigger extends SimpleCriterionTrigger<KilledByCrossbowTrigger.TriggerInstance> {
   static final ResourceLocation f_46867_ = new ResourceLocation("killed_by_crossbow");

   public ResourceLocation m_7295_() {
      return f_46867_;
   }

   public KilledByCrossbowTrigger.TriggerInstance m_7214_(JsonObject p_46875_, EntityPredicate.Composite p_46876_, DeserializationContext p_46877_) {
      EntityPredicate.Composite[] aentitypredicate$composite = EntityPredicate.Composite.m_36692_(p_46875_, "victims", p_46877_);
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_46875_.get("unique_entity_types"));
      return new KilledByCrossbowTrigger.TriggerInstance(p_46876_, aentitypredicate$composite, minmaxbounds$ints);
   }

   public void m_46871_(ServerPlayer p_46872_, Collection<Entity> p_46873_) {
      List<LootContext> list = Lists.newArrayList();
      Set<EntityType<?>> set = Sets.newHashSet();

      for(Entity entity : p_46873_) {
         set.add(entity.m_6095_());
         list.add(EntityPredicate.m_36616_(p_46872_, entity));
      }

      this.m_66234_(p_46872_, (p_46881_) -> {
         return p_46881_.m_46897_(list, set.size());
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite[] f_46887_;
      private final MinMaxBounds.Ints f_46888_;

      public TriggerInstance(EntityPredicate.Composite p_46890_, EntityPredicate.Composite[] p_46891_, MinMaxBounds.Ints p_46892_) {
         super(KilledByCrossbowTrigger.f_46867_, p_46890_);
         this.f_46887_ = p_46891_;
         this.f_46888_ = p_46892_;
      }

      public static KilledByCrossbowTrigger.TriggerInstance m_46900_(EntityPredicate.Builder... p_46901_) {
         EntityPredicate.Composite[] aentitypredicate$composite = new EntityPredicate.Composite[p_46901_.length];

         for(int i = 0; i < p_46901_.length; ++i) {
            EntityPredicate.Builder entitypredicate$builder = p_46901_[i];
            aentitypredicate$composite[i] = EntityPredicate.Composite.m_36673_(entitypredicate$builder.m_36662_());
         }

         return new KilledByCrossbowTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, aentitypredicate$composite, MinMaxBounds.Ints.f_55364_);
      }

      public static KilledByCrossbowTrigger.TriggerInstance m_46893_(MinMaxBounds.Ints p_46894_) {
         EntityPredicate.Composite[] aentitypredicate$composite = new EntityPredicate.Composite[0];
         return new KilledByCrossbowTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, aentitypredicate$composite, p_46894_);
      }

      public boolean m_46897_(Collection<LootContext> p_46898_, int p_46899_) {
         if (this.f_46887_.length > 0) {
            List<LootContext> list = Lists.newArrayList(p_46898_);

            for(EntityPredicate.Composite entitypredicate$composite : this.f_46887_) {
               boolean flag = false;
               Iterator<LootContext> iterator = list.iterator();

               while(iterator.hasNext()) {
                  LootContext lootcontext = iterator.next();
                  if (entitypredicate$composite.m_36681_(lootcontext)) {
                     iterator.remove();
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  return false;
               }
            }
         }

         return this.f_46888_.m_55390_(p_46899_);
      }

      public JsonObject m_7683_(SerializationContext p_46896_) {
         JsonObject jsonobject = super.m_7683_(p_46896_);
         jsonobject.add("victims", EntityPredicate.Composite.m_36687_(this.f_46887_, p_46896_));
         jsonobject.add("unique_entity_types", this.f_46888_.m_55328_());
         return jsonobject;
      }
   }
}