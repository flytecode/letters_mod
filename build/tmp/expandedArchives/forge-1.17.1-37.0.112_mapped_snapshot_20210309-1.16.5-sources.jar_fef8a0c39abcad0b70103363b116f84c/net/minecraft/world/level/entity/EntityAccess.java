package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public interface EntityAccess {
   int m_142049_();

   UUID m_142081_();

   BlockPos m_142538_();

   AABB m_142469_();

   void m_141960_(EntityInLevelCallback p_156797_);

   Stream<? extends EntityAccess> m_142428_();

   Stream<? extends EntityAccess> m_142429_();

   void m_142467_(Entity.RemovalReason p_156798_);

   boolean m_142391_();

   boolean m_142389_();
}