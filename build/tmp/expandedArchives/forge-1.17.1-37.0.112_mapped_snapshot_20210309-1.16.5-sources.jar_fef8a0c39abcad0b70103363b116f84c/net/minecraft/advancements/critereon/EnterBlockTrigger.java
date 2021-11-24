package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EnterBlockTrigger extends SimpleCriterionTrigger<EnterBlockTrigger.TriggerInstance> {
   static final ResourceLocation f_31265_ = new ResourceLocation("enter_block");

   public ResourceLocation m_7295_() {
      return f_31265_;
   }

   public EnterBlockTrigger.TriggerInstance m_7214_(JsonObject p_31281_, EntityPredicate.Composite p_31282_, DeserializationContext p_31283_) {
      Block block = m_31278_(p_31281_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(p_31281_.get("state"));
      if (block != null) {
         statepropertiespredicate.m_67672_(block.m_49965_(), (p_31274_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_31274_);
         });
      }

      return new EnterBlockTrigger.TriggerInstance(p_31282_, block, statepropertiespredicate);
   }

   @Nullable
   private static Block m_31278_(JsonObject p_31279_) {
      if (p_31279_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_31279_, "block"));
         return Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void m_31269_(ServerPlayer p_31270_, BlockState p_31271_) {
      this.m_66234_(p_31270_, (p_31277_) -> {
         return p_31277_.m_31299_(p_31271_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Block f_31291_;
      private final StatePropertiesPredicate f_31292_;

      public TriggerInstance(EntityPredicate.Composite p_31294_, @Nullable Block p_31295_, StatePropertiesPredicate p_31296_) {
         super(EnterBlockTrigger.f_31265_, p_31294_);
         this.f_31291_ = p_31295_;
         this.f_31292_ = p_31296_;
      }

      public static EnterBlockTrigger.TriggerInstance m_31297_(Block p_31298_) {
         return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_31298_, StatePropertiesPredicate.f_67658_);
      }

      public JsonObject m_7683_(SerializationContext p_31302_) {
         JsonObject jsonobject = super.m_7683_(p_31302_);
         if (this.f_31291_ != null) {
            jsonobject.addProperty("block", Registry.f_122824_.m_7981_(this.f_31291_).toString());
         }

         jsonobject.add("state", this.f_31292_.m_67666_());
         return jsonobject;
      }

      public boolean m_31299_(BlockState p_31300_) {
         if (this.f_31291_ != null && !p_31300_.m_60713_(this.f_31291_)) {
            return false;
         } else {
            return this.f_31292_.m_67667_(p_31300_);
         }
      }
   }
}