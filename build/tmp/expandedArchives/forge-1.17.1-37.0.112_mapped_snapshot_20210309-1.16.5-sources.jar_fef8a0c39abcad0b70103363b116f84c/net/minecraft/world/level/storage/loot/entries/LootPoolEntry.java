package net.minecraft.world.level.storage.loot.entries;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public interface LootPoolEntry {
   int m_7067_(float p_79632_);

   void m_6941_(Consumer<ItemStack> p_79633_, LootContext p_79634_);
}