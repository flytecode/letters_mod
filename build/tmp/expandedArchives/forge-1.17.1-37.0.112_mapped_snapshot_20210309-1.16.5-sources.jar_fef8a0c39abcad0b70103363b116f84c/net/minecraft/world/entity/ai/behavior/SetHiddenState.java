package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class SetHiddenState extends Behavior<LivingEntity> {
   private static final int f_147896_ = 300;
   private final int f_23927_;
   private final int f_23928_;
   private int f_23929_;

   public SetHiddenState(int p_23931_, int p_23932_) {
      super(ImmutableMap.of(MemoryModuleType.f_26324_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26325_, MemoryStatus.VALUE_PRESENT));
      this.f_23928_ = p_23931_ * 20;
      this.f_23929_ = 0;
      this.f_23927_ = p_23932_;
   }

   protected void m_6735_(ServerLevel p_23934_, LivingEntity p_23935_, long p_23936_) {
      Brain<?> brain = p_23935_.m_6274_();
      Optional<Long> optional = brain.m_21952_(MemoryModuleType.f_26325_);
      boolean flag = optional.get() + 300L <= p_23936_;
      if (this.f_23929_ <= this.f_23928_ && !flag) {
         BlockPos blockpos = brain.m_21952_(MemoryModuleType.f_26324_).get().m_122646_();
         if (blockpos.m_123314_(p_23935_.m_142538_(), (double)this.f_23927_)) {
            ++this.f_23929_;
         }

      } else {
         brain.m_21936_(MemoryModuleType.f_26325_);
         brain.m_21936_(MemoryModuleType.f_26324_);
         brain.m_21862_(p_23934_.m_46468_(), p_23934_.m_46467_());
         this.f_23929_ = 0;
      }
   }
}