package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class AnimalPanic extends Behavior<PathfinderMob> {
   private static final int f_147379_ = 100;
   private static final int f_147380_ = 120;
   private static final int f_147381_ = 5;
   private static final int f_147382_ = 4;
   private final float f_147383_;

   public AnimalPanic(float p_147385_) {
      super(ImmutableMap.of(MemoryModuleType.f_26381_, MemoryStatus.VALUE_PRESENT), 100, 120);
      this.f_147383_ = p_147385_;
   }

   protected boolean m_6737_(ServerLevel p_147391_, PathfinderMob p_147392_, long p_147393_) {
      return true;
   }

   protected void m_6735_(ServerLevel p_147399_, PathfinderMob p_147400_, long p_147401_) {
      p_147400_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
   }

   protected void m_6725_(ServerLevel p_147403_, PathfinderMob p_147404_, long p_147405_) {
      if (p_147404_.m_21573_().m_26571_()) {
         Vec3 vec3 = LandRandomPos.m_148488_(p_147404_, 5, 4);
         if (vec3 != null) {
            p_147404_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(vec3, this.f_147383_, 0));
         }
      }

   }
}