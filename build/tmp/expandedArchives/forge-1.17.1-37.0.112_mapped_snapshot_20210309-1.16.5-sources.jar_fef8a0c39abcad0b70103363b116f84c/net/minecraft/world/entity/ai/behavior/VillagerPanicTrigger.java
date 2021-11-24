package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

public class VillagerPanicTrigger extends Behavior<Villager> {
   public VillagerPanicTrigger() {
      super(ImmutableMap.of());
   }

   protected boolean m_6737_(ServerLevel p_24684_, Villager p_24685_, long p_24686_) {
      return m_24697_(p_24685_) || m_24687_(p_24685_);
   }

   protected void m_6735_(ServerLevel p_24694_, Villager p_24695_, long p_24696_) {
      if (m_24697_(p_24695_) || m_24687_(p_24695_)) {
         Brain<?> brain = p_24695_.m_6274_();
         if (!brain.m_21954_(Activity.f_37984_)) {
            brain.m_21936_(MemoryModuleType.f_26377_);
            brain.m_21936_(MemoryModuleType.f_26370_);
            brain.m_21936_(MemoryModuleType.f_26371_);
            brain.m_21936_(MemoryModuleType.f_26375_);
            brain.m_21936_(MemoryModuleType.f_26374_);
         }

         brain.m_21889_(Activity.f_37984_);
      }

   }

   protected void m_6725_(ServerLevel p_24700_, Villager p_24701_, long p_24702_) {
      if (p_24702_ % 100L == 0L) {
         p_24701_.m_35397_(p_24700_, p_24702_, 3);
      }

   }

   public static boolean m_24687_(LivingEntity p_24688_) {
      return p_24688_.m_6274_().m_21874_(MemoryModuleType.f_26323_);
   }

   public static boolean m_24697_(LivingEntity p_24698_) {
      return p_24698_.m_6274_().m_21874_(MemoryModuleType.f_26381_);
   }
}