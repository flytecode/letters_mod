package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class EmptyLootItem extends LootPoolSingletonContainer {
   EmptyLootItem(int p_79519_, int p_79520_, LootItemCondition[] p_79521_, LootItemFunction[] p_79522_) {
      super(p_79519_, p_79520_, p_79521_, p_79522_);
   }

   public LootPoolEntryType m_6751_() {
      return LootPoolEntries.f_79619_;
   }

   public void m_6948_(Consumer<ItemStack> p_79531_, LootContext p_79532_) {
   }

   public static LootPoolSingletonContainer.Builder<?> m_79533_() {
      return m_79687_(EmptyLootItem::new);
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<EmptyLootItem> {
      public EmptyLootItem m_7267_(JsonObject p_79536_, JsonDeserializationContext p_79537_, int p_79538_, int p_79539_, LootItemCondition[] p_79540_, LootItemFunction[] p_79541_) {
         return new EmptyLootItem(p_79538_, p_79539_, p_79540_, p_79541_);
      }
   }
}