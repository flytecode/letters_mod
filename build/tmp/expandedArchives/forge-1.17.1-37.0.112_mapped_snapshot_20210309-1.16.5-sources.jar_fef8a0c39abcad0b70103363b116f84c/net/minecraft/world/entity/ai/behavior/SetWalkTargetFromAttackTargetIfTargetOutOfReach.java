package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromAttackTargetIfTargetOutOfReach extends Behavior<Mob> {
   private static final int f_147903_ = 1;
   private final Function<LivingEntity, Float> f_24024_;

   public SetWalkTargetFromAttackTargetIfTargetOutOfReach(float p_24026_) {
      this((p_147908_) -> {
         return p_24026_;
      });
   }

   public SetWalkTargetFromAttackTargetIfTargetOutOfReach(Function<LivingEntity, Float> p_147905_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26372_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148205_, MemoryStatus.REGISTERED));
      this.f_24024_ = p_147905_;
   }

   protected void m_6735_(ServerLevel p_24032_, Mob p_24033_, long p_24034_) {
      LivingEntity livingentity = p_24033_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
      if (BehaviorUtils.m_22667_(p_24033_, livingentity) && BehaviorUtils.m_22632_(p_24033_, livingentity, 1)) {
         this.m_24035_(p_24033_);
      } else {
         this.m_24037_(p_24033_, livingentity);
      }

   }

   private void m_24037_(LivingEntity p_24038_, LivingEntity p_24039_) {
      Brain<?> brain = p_24038_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_24039_, true));
      WalkTarget walktarget = new WalkTarget(new EntityTracker(p_24039_, false), this.f_24024_.apply(p_24038_), 0);
      brain.m_21879_(MemoryModuleType.f_26370_, walktarget);
   }

   private void m_24035_(LivingEntity p_24036_) {
      p_24036_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
   }
}