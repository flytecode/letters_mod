package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootItemBlockStatePropertyCondition implements LootItemCondition {
   final Block f_81759_;
   final StatePropertiesPredicate f_81760_;

   LootItemBlockStatePropertyCondition(Block p_81762_, StatePropertiesPredicate p_81763_) {
      this.f_81759_ = p_81762_;
      this.f_81760_ = p_81763_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81818_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81461_);
   }

   public boolean test(LootContext p_81772_) {
      BlockState blockstate = p_81772_.m_78953_(LootContextParams.f_81461_);
      return blockstate != null && blockstate.m_60713_(this.f_81759_) && this.f_81760_.m_67667_(blockstate);
   }

   public static LootItemBlockStatePropertyCondition.Builder m_81769_(Block p_81770_) {
      return new LootItemBlockStatePropertyCondition.Builder(p_81770_);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final Block f_81780_;
      private StatePropertiesPredicate f_81781_ = StatePropertiesPredicate.f_67658_;

      public Builder(Block p_81783_) {
         this.f_81780_ = p_81783_;
      }

      public LootItemBlockStatePropertyCondition.Builder m_81784_(StatePropertiesPredicate.Builder p_81785_) {
         this.f_81781_ = p_81785_.m_67706_();
         return this;
      }

      public LootItemCondition m_6409_() {
         return new LootItemBlockStatePropertyCondition(this.f_81780_, this.f_81781_);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemBlockStatePropertyCondition> {
      public void m_6170_(JsonObject p_81795_, LootItemBlockStatePropertyCondition p_81796_, JsonSerializationContext p_81797_) {
         p_81795_.addProperty("block", Registry.f_122824_.m_7981_(p_81796_.f_81759_).toString());
         p_81795_.add("properties", p_81796_.f_81760_.m_67666_());
      }

      public LootItemBlockStatePropertyCondition m_7561_(JsonObject p_81805_, JsonDeserializationContext p_81806_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_81805_, "block"));
         Block block = Registry.f_122824_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new IllegalArgumentException("Can't find block " + resourcelocation);
         });
         StatePropertiesPredicate statepropertiespredicate = StatePropertiesPredicate.m_67679_(p_81805_.get("properties"));
         statepropertiespredicate.m_67672_(block.m_49965_(), (p_81790_) -> {
            throw new JsonSyntaxException("Block " + block + " has no property " + p_81790_);
         });
         return new LootItemBlockStatePropertyCondition(block, statepropertiespredicate);
      }
   }
}