package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Items;

public class StopHoldingItemIfNoLongerAdmiring<E extends Piglin> extends Behavior<E> {
   public StopHoldingItemIfNoLongerAdmiring() {
      super(ImmutableMap.of(MemoryModuleType.f_26336_, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean m_6114_(ServerLevel p_35255_, E p_35256_) {
      return !p_35256_.m_21206_().m_41619_() && !p_35256_.m_21206_().canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK);
   }

   protected void m_6735_(ServerLevel p_35258_, E p_35259_, long p_35260_) {
      PiglinAi.m_34867_(p_35259_, true);
   }
}
