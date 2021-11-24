package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromLookTarget extends Behavior<LivingEntity> {
   private final Function<LivingEntity, Float> f_24081_;
   private final int f_24082_;
   private final Predicate<LivingEntity> f_182357_;

   public SetWalkTargetFromLookTarget(float p_24084_, int p_24085_) {
      this((p_182369_) -> {
         return true;
      }, (p_182364_) -> {
         return p_24084_;
      }, p_24085_);
   }

   public SetWalkTargetFromLookTarget(Predicate<LivingEntity> p_182359_, Function<LivingEntity, Float> p_182360_, int p_182361_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26371_, MemoryStatus.VALUE_PRESENT));
      this.f_24081_ = p_182360_;
      this.f_24082_ = p_182361_;
      this.f_182357_ = p_182359_;
   }

   protected boolean m_6114_(ServerLevel p_182366_, LivingEntity p_182367_) {
      return this.f_182357_.test(p_182367_);
   }

   protected void m_6735_(ServerLevel p_24087_, LivingEntity p_24088_, long p_24089_) {
      Brain<?> brain = p_24088_.m_6274_();
      PositionTracker positiontracker = brain.m_21952_(MemoryModuleType.f_26371_).get();
      brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(positiontracker, this.f_24081_.apply(p_24088_), this.f_24082_));
   }
}