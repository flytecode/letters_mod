package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public interface LootContextUser {
   default Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of();
   }

   default void m_6169_(ValidationContext p_79022_) {
      p_79022_.m_79353_(this);
   }
}