package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class StrollToPoi extends Behavior<PathfinderMob> {
   private final MemoryModuleType<GlobalPos> f_24327_;
   private final int f_24328_;
   private final int f_24329_;
   private final float f_24330_;
   private long f_24331_;

   public StrollToPoi(MemoryModuleType<GlobalPos> p_24333_, float p_24334_, int p_24335_, int p_24336_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, p_24333_, MemoryStatus.VALUE_PRESENT));
      this.f_24327_ = p_24333_;
      this.f_24330_ = p_24334_;
      this.f_24328_ = p_24335_;
      this.f_24329_ = p_24336_;
   }

   protected boolean m_6114_(ServerLevel p_24345_, PathfinderMob p_24346_) {
      Optional<GlobalPos> optional = p_24346_.m_6274_().m_21952_(this.f_24327_);
      return optional.isPresent() && p_24345_.m_46472_() == optional.get().m_122640_() && optional.get().m_122646_().m_123306_(p_24346_.m_20182_(), (double)this.f_24329_);
   }

   protected void m_6735_(ServerLevel p_24348_, PathfinderMob p_24349_, long p_24350_) {
      if (p_24350_ > this.f_24331_) {
         Brain<?> brain = p_24349_.m_6274_();
         Optional<GlobalPos> optional = brain.m_21952_(this.f_24327_);
         optional.ifPresent((p_24353_) -> {
            brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(p_24353_.m_122646_(), this.f_24330_, this.f_24328_));
         });
         this.f_24331_ = p_24350_ + 80L;
      }

   }
}