package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ResetProfession extends Behavior<Villager> {
   public ResetProfession() {
      super(ImmutableMap.of(MemoryModuleType.f_26360_, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean m_6114_(ServerLevel p_23773_, Villager p_23774_) {
      VillagerData villagerdata = p_23774_.m_7141_();
      return villagerdata.m_35571_() != VillagerProfession.f_35585_ && villagerdata.m_35571_() != VillagerProfession.f_35596_ && p_23774_.m_7809_() == 0 && villagerdata.m_35576_() <= 1;
   }

   protected void m_6735_(ServerLevel p_23776_, Villager p_23777_, long p_23778_) {
      p_23777_.m_141967_(p_23777_.m_7141_().m_35565_(VillagerProfession.f_35585_));
      p_23777_.m_35483_(p_23776_);
   }
}