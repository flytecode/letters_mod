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

public class SlideDownBlockTrigger extends SimpleCriterionTrigger<SlideDownBlockTrigger.TriggerInstance> {
   static final ResourceLocation f_66974_ = new ResourceLocation("slide_down_block");

   public ResourceLocation m_7295_() {
      return f_66974_;
   }

   public SlideDownBlockTrigger.TriggerInstance m_7214_(JsonObject p_66990_, EntityPredicate.Composite p_66991_, DeserializationContext p_66992_) {
      Block block = m_66987_(p_66990_);
      StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(p_66990_.get("state"));
      if (block != null) {
         statepropertiespredicate.m_67672_(block.m_49965_(), (p_66983_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_66983_);
         });
      }

      return new SlideDownBlockTrigger.TriggerInstance(p_66991_, block, statepropertiespredicate);
   }

   @Nullable
   private static Block m_66987_(JsonObject p_66988_) {
      if (p_66988_.has("block")) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_66988_, "block"));
         return Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown block type '" + resourcelocation + "'");
         });
      } else {
         return null;
      }
   }

   public void m_66978_(ServerPlayer p_66979_, BlockState p_66980_) {
      this.m_66234_(p_66979_, (p_66986_) -> {
         return p_66986_.m_67008_(p_66980_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Block f_67000_;
      private final StatePropertiesPredicate f_67001_;

      public TriggerInstance(EntityPredicate.Composite p_67003_, @Nullable Block p_67004_, StatePropertiesPredicate p_67005_) {
         super(SlideDownBlockTrigger.f_66974_, p_67003_);
         this.f_67000_ = p_67004_;
         this.f_67001_ = p_67005_;
      }

      public static SlideDownBlockTrigger.TriggerInstance m_67006_(Block p_67007_) {
         return new SlideDownBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_67007_, StatePropertiesPredicate.f_67658_);
      }

      public JsonObject m_7683_(SerializationContext p_67011_) {
         JsonObject jsonobject = super.m_7683_(p_67011_);
         if (this.f_67000_ != null) {
            jsonobject.addProperty("block", Registry.f_122824_.m_7981_(this.f_67000_).toString());
         }

         jsonobject.add("state", this.f_67001_.m_67666_());
         return jsonobject;
      }

      public boolean m_67008_(BlockState p_67009_) {
         if (this.f_67000_ != null && !p_67009_.m_60713_(this.f_67000_)) {
            return false;
         } else {
            return this.f_67001_.m_67667_(p_67009_);
         }
      }
   }
}