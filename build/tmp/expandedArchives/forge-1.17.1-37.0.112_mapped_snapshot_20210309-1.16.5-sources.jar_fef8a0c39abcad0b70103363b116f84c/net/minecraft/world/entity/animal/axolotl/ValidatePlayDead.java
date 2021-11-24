package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ValidatePlayDead extends Behavior<Axolotl> {
   public ValidatePlayDead() {
      super(ImmutableMap.of(MemoryModuleType.f_148195_, MemoryStatus.VALUE_PRESENT));
   }

   protected void m_6735_(ServerLevel p_149339_, Axolotl p_149340_, long p_149341_) {
      Brain<Axolotl> brain = p_149340_.m_6274_();
      int i = brain.m_21952_(MemoryModuleType.f_148195_).get();
      if (i <= 0) {
         brain.m_21936_(MemoryModuleType.f_148195_);
         brain.m_21936_(MemoryModuleType.f_26382_);
         brain.m_21962_();
      } else {
         brain.m_21879_(MemoryModuleType.f_148195_, i - 1);
      }

   }
}