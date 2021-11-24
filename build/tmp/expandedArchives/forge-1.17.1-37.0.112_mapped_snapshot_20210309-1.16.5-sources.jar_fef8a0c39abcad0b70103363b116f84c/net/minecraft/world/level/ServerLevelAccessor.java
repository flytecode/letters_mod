package net.minecraft.world.level;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public interface ServerLevelAccessor extends LevelAccessor {
   ServerLevel m_6018_();

   default void m_47205_(Entity p_47206_) {
      p_47206_.m_142428_().forEach(this::m_7967_);
   }
}