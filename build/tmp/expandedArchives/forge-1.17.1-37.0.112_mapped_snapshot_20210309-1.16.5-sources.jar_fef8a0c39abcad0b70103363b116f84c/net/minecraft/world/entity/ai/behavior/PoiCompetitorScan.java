package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class PoiCompetitorScan extends Behavior<Villager> {
   final VillagerProfession f_23708_;

   public PoiCompetitorScan(VillagerProfession p_23710_) {
      super(ImmutableMap.of(MemoryModuleType.f_26360_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148204_, MemoryStatus.VALUE_PRESENT));
      this.f_23708_ = p_23710_;
   }

   protected void m_6735_(ServerLevel p_23716_, Villager p_23717_, long p_23718_) {
      GlobalPos globalpos = p_23717_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get();
      p_23716_.m_8904_().m_27177_(globalpos.m_122646_()).ifPresent((p_23730_) -> {
         BehaviorUtils.m_22650_(p_23717_, (p_147712_) -> {
            return this.m_23731_(globalpos, p_23730_, p_147712_);
         }).reduce(p_23717_, PoiCompetitorScan::m_23724_);
      });
   }

   private static Villager m_23724_(Villager p_23725_, Villager p_23726_) {
      Villager villager;
      Villager villager1;
      if (p_23725_.m_7809_() > p_23726_.m_7809_()) {
         villager = p_23725_;
         villager1 = p_23726_;
      } else {
         villager = p_23726_;
         villager1 = p_23725_;
      }

      villager1.m_6274_().m_21936_(MemoryModuleType.f_26360_);
      return villager;
   }

   private boolean m_23731_(GlobalPos p_23732_, PoiType p_23733_, Villager p_23734_) {
      return this.m_23722_(p_23734_) && p_23732_.equals(p_23734_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get()) && this.m_23719_(p_23733_, p_23734_.m_7141_().m_35571_());
   }

   private boolean m_23719_(PoiType p_23720_, VillagerProfession p_23721_) {
      return p_23721_.m_35622_().m_27392_().test(p_23720_);
   }

   private boolean m_23722_(Villager p_23723_) {
      return p_23723_.m_6274_().m_21952_(MemoryModuleType.f_26360_).isPresent();
   }
}