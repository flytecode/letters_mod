package net.minecraft.world.level.storage.loot.providers.nbt;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public interface NbtProvider {
   @Nullable
   Tag m_142301_(LootContext p_165622_);

   Set<LootContextParam<?>> m_142677_();

   LootNbtProviderType m_142624_();
}