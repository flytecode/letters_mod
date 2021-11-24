package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class ItemPickedUpByEntityTrigger extends SimpleCriterionTrigger<ItemPickedUpByEntityTrigger.TriggerInstance> {
   static final ResourceLocation f_44359_ = new ResourceLocation("thrown_item_picked_up_by_entity");

   public ResourceLocation m_7295_() {
      return f_44359_;
   }

   protected ItemPickedUpByEntityTrigger.TriggerInstance m_7214_(JsonObject p_44373_, EntityPredicate.Composite p_44374_, DeserializationContext p_44375_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_44373_.get("item"));
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_44373_, "entity", p_44375_);
      return new ItemPickedUpByEntityTrigger.TriggerInstance(p_44374_, itempredicate, entitypredicate$composite);
   }

   public void m_44363_(ServerPlayer p_44364_, ItemStack p_44365_, Entity p_44366_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_44364_, p_44366_);
      this.m_66234_(p_44364_, (p_44371_) -> {
         return p_44371_.m_44387_(p_44364_, p_44365_, lootcontext);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_44381_;
      private final EntityPredicate.Composite f_44382_;

      public TriggerInstance(EntityPredicate.Composite p_44384_, ItemPredicate p_44385_, EntityPredicate.Composite p_44386_) {
         super(ItemPickedUpByEntityTrigger.f_44359_, p_44384_);
         this.f_44381_ = p_44385_;
         this.f_44382_ = p_44386_;
      }

      public static ItemPickedUpByEntityTrigger.TriggerInstance m_44391_(EntityPredicate.Composite p_44392_, ItemPredicate.Builder p_44393_, EntityPredicate.Composite p_44394_) {
         return new ItemPickedUpByEntityTrigger.TriggerInstance(p_44392_, p_44393_.m_45077_(), p_44394_);
      }

      public boolean m_44387_(ServerPlayer p_44388_, ItemStack p_44389_, LootContext p_44390_) {
         if (!this.f_44381_.m_45049_(p_44389_)) {
            return false;
         } else {
            return this.f_44382_.m_36681_(p_44390_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_44396_) {
         JsonObject jsonobject = super.m_7683_(p_44396_);
         jsonobject.add("item", this.f_44381_.m_45048_());
         jsonobject.add("entity", this.f_44382_.m_36675_(p_44396_));
         return jsonobject;
      }
   }
}