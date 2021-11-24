package net.minecraft.world.level.storage.loot.providers.score;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public interface ScoreboardNameProvider {
   @Nullable
   String m_142600_(LootContext p_165867_);

   LootScoreProviderType m_142680_();

   Set<LootContextParam<?>> m_142636_();
}