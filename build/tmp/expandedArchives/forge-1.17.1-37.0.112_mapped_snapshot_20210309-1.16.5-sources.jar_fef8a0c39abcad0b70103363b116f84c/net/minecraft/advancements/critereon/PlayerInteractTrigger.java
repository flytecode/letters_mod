package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerInteractTrigger extends SimpleCriterionTrigger<PlayerInteractTrigger.TriggerInstance> {
   static final ResourceLocation f_61490_ = new ResourceLocation("player_interacted_with_entity");

   public ResourceLocation m_7295_() {
      return f_61490_;
   }

   protected PlayerInteractTrigger.TriggerInstance m_7214_(JsonObject p_61503_, EntityPredicate.Composite p_61504_, DeserializationContext p_61505_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_61503_.get("item"));
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_61503_, "entity", p_61505_);
      return new PlayerInteractTrigger.TriggerInstance(p_61504_, itempredicate, entitypredicate$composite);
   }

   public void m_61494_(ServerPlayer p_61495_, ItemStack p_61496_, Entity p_61497_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_61495_, p_61497_);
      this.m_66234_(p_61495_, (p_61501_) -> {
         return p_61501_.m_61521_(p_61496_, lootcontext);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_61511_;
      private final EntityPredicate.Composite f_61512_;

      public TriggerInstance(EntityPredicate.Composite p_61514_, ItemPredicate p_61515_, EntityPredicate.Composite p_61516_) {
         super(PlayerInteractTrigger.f_61490_, p_61514_);
         this.f_61511_ = p_61515_;
         this.f_61512_ = p_61516_;
      }

      public static PlayerInteractTrigger.TriggerInstance m_61517_(EntityPredicate.Composite p_61518_, ItemPredicate.Builder p_61519_, EntityPredicate.Composite p_61520_) {
         return new PlayerInteractTrigger.TriggerInstance(p_61518_, p_61519_.m_45077_(), p_61520_);
      }

      public boolean m_61521_(ItemStack p_61522_, LootContext p_61523_) {
         return !this.f_61511_.m_45049_(p_61522_) ? false : this.f_61512_.m_36681_(p_61523_);
      }

      public JsonObject m_7683_(SerializationContext p_61525_) {
         JsonObject jsonobject = super.m_7683_(p_61525_);
         jsonobject.add("item", this.f_61511_.m_45048_());
         jsonobject.add("entity", this.f_61512_.m_36675_(p_61525_));
         return jsonobject;
      }
   }
}