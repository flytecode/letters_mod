package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlacedBlockTrigger extends SimpleCriterionTrigger<PlacedBlockTrigger.TriggerInstance> {
   static final ResourceLocation f_59465_ = new ResourceLocation("placed_block");

   public ResourceLocation m_7295_() {
      return f_59465_;
   }

   public PlacedBlockTrigger.TriggerInstance m_7214_(JsonObject p_59485_, EntityPredicate.Composite p_59486_, DeserializationContext p_59487_) {
      Block block = m_59482_(p_59485_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(p_59485_.get("state"));
      if (block != null) {
         statepropertiespredicate.m_67672_(block.m_49965_(), (p_59475_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_59475_ + ":");
         });
      }

      LocationPredicate locationpredicate = LocationPredicate.m_52629_(p_59485_.get("location"));
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_59485_.get("item"));
      return new PlacedBlockTrigger.TriggerInstance(p_59486_, block, statepropertiespredicate, locationpredicate, itempredicate);
   }

   @Nullable
   private static Block m_59482_(JsonObject p_59483_) {
      if (p_59483_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_59483_, "block"));
         return Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void m_59469_(ServerPlayer p_59470_, BlockPos p_59471_, ItemStack p_59472_) {
      BlockState blockstate = p_59470_.m_9236_().m_8055_(p_59471_);
      this.m_66234_(p_59470_, (p_59481_) -> {
         return p_59481_.m_59507_(blockstate, p_59471_, p_59470_.m_9236_(), p_59472_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Block f_59495_;
      private final StatePropertiesPredicate f_59496_;
      private final LocationPredicate f_59497_;
      private final ItemPredicate f_59498_;

      public TriggerInstance(EntityPredicate.Composite p_59500_, @Nullable Block p_59501_, StatePropertiesPredicate p_59502_, LocationPredicate p_59503_, ItemPredicate p_59504_) {
         super(PlacedBlockTrigger.f_59465_, p_59500_);
         this.f_59495_ = p_59501_;
         this.f_59496_ = p_59502_;
         this.f_59497_ = p_59503_;
         this.f_59498_ = p_59504_;
      }

      public static PlacedBlockTrigger.TriggerInstance m_59505_(Block p_59506_) {
         return new PlacedBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_59506_, StatePropertiesPredicate.f_67658_, LocationPredicate.f_52592_, ItemPredicate.f_45028_);
      }

      public boolean m_59507_(BlockState p_59508_, BlockPos p_59509_, ServerLevel p_59510_, ItemStack p_59511_) {
         if (this.f_59495_ != null && !p_59508_.m_60713_(this.f_59495_)) {
            return false;
         } else if (!this.f_59496_.m_67667_(p_59508_)) {
            return false;
         } else if (!this.f_59497_.m_52617_(p_59510_, (double)p_59509_.m_123341_(), (double)p_59509_.m_123342_(), (double)p_59509_.m_123343_())) {
            return false;
         } else {
            return this.f_59498_.m_45049_(p_59511_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_59513_) {
         JsonObject jsonobject = super.m_7683_(p_59513_);
         if (this.f_59495_ != null) {
            jsonobject.addProperty("block", Registry.f_122824_.m_7981_(this.f_59495_).toString());
         }

         jsonobject.add("state", this.f_59496_.m_67666_());
         jsonobject.add("location", this.f_59497_.m_52616_());
         jsonobject.add("item", this.f_59498_.m_45048_());
         return jsonobject;
      }
   }
}