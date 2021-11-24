package net.minecraft.world.level.storage.loot.providers.number;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

public interface NumberProvider extends LootContextUser {
   float m_142688_(LootContext p_165730_);

   default int m_142683_(LootContext p_165729_) {
      return Math.round(this.m_142688_(p_165729_));
   }

   LootNumberProviderType m_142587_();
}