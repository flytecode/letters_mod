package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class FishingRodHookedTrigger extends SimpleCriterionTrigger<FishingRodHookedTrigger.TriggerInstance> {
   static final ResourceLocation f_40412_ = new ResourceLocation("fishing_rod_hooked");

   public ResourceLocation m_7295_() {
      return f_40412_;
   }

   public FishingRodHookedTrigger.TriggerInstance m_7214_(JsonObject p_40427_, EntityPredicate.Composite p_40428_, DeserializationContext p_40429_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_40427_.get("rod"));
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_40427_, "entity", p_40429_);
      ItemPredicate itempredicate1 = ItemPredicate.m_45051_(p_40427_.get("item"));
      return new FishingRodHookedTrigger.TriggerInstance(p_40428_, itempredicate, entitypredicate$composite, itempredicate1);
   }

   public void m_40416_(ServerPlayer p_40417_, ItemStack p_40418_, FishingHook p_40419_, Collection<ItemStack> p_40420_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_40417_, (Entity)(p_40419_.m_37170_() != null ? p_40419_.m_37170_() : p_40419_));
      this.m_66234_(p_40417_, (p_40425_) -> {
         return p_40425_.m_40443_(p_40418_, lootcontext, p_40420_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_40435_;
      private final EntityPredicate.Composite f_40436_;
      private final ItemPredicate f_40437_;

      public TriggerInstance(EntityPredicate.Composite p_40439_, ItemPredicate p_40440_, EntityPredicate.Composite p_40441_, ItemPredicate p_40442_) {
         super(FishingRodHookedTrigger.f_40412_, p_40439_);
         this.f_40435_ = p_40440_;
         this.f_40436_ = p_40441_;
         this.f_40437_ = p_40442_;
      }

      public static FishingRodHookedTrigger.TriggerInstance m_40447_(ItemPredicate p_40448_, EntityPredicate p_40449_, ItemPredicate p_40450_) {
         return new FishingRodHookedTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_40448_, EntityPredicate.Composite.m_36673_(p_40449_), p_40450_);
      }

      public boolean m_40443_(ItemStack p_40444_, LootContext p_40445_, Collection<ItemStack> p_40446_) {
         if (!this.f_40435_.m_45049_(p_40444_)) {
            return false;
         } else if (!this.f_40436_.m_36681_(p_40445_)) {
            return false;
         } else {
            if (this.f_40437_ != ItemPredicate.f_45028_) {
               boolean flag = false;
               Entity entity = p_40445_.m_78953_(LootContextParams.f_81455_);
               if (entity instanceof ItemEntity) {
                  ItemEntity itementity = (ItemEntity)entity;
                  if (this.f_40437_.m_45049_(itementity.m_32055_())) {
                     flag = true;
                  }
               }

               for(ItemStack itemstack : p_40446_) {
                  if (this.f_40437_.m_45049_(itemstack)) {
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
      }

      public JsonObject m_7683_(SerializationContext p_40452_) {
         JsonObject jsonobject = super.m_7683_(p_40452_);
         jsonobject.add("rod", this.f_40435_.m_45048_());
         jsonobject.add("entity", this.f_40436_.m_36675_(p_40452_));
         jsonobject.add("item", this.f_40437_.m_45048_());
         return jsonobject;
      }
   }
}