package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmeltItemFunction extends LootItemConditionalFunction {
   private static final Logger f_81260_ = LogManager.getLogger();

   SmeltItemFunction(LootItemCondition[] p_81263_) {
      super(p_81263_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80740_;
   }

   public ItemStack m_7372_(ItemStack p_81268_, LootContext p_81269_) {
      if (p_81268_.m_41619_()) {
         return p_81268_;
      } else {
         Optional<SmeltingRecipe> optional = p_81269_.m_78952_().m_7465_().m_44015_(RecipeType.f_44108_, new SimpleContainer(p_81268_), p_81269_.m_78952_());
         if (optional.isPresent()) {
            ItemStack itemstack = optional.get().m_8043_();
            if (!itemstack.m_41619_()) {
               ItemStack itemstack1 = itemstack.m_41777_();
               itemstack1.m_41764_(p_81268_.m_41613_() * itemstack.m_41613_()); //Forge: Support smelting returning multiple
               return itemstack1;
            }
         }

         f_81260_.warn("Couldn't smelt {} because there is no smelting recipe", (Object)p_81268_);
         return p_81268_;
      }
   }

   public static LootItemConditionalFunction.Builder<?> m_81271_() {
      return m_80683_(SmeltItemFunction::new);
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SmeltItemFunction> {
      public SmeltItemFunction m_6821_(JsonObject p_81274_, JsonDeserializationContext p_81275_, LootItemCondition[] p_81276_) {
         return new SmeltItemFunction(p_81276_);
      }
   }
}
