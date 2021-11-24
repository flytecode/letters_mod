package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class GoToCelebrateLocation<E extends Mob> extends Behavior<E> {
   private final int f_23054_;
   private final float f_23055_;

   public GoToCelebrateLocation(int p_23057_, float p_23058_) {
      super(ImmutableMap.of(MemoryModuleType.f_26341_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26372_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED));
      this.f_23054_ = p_23057_;
      this.f_23055_ = p_23058_;
   }

   protected void m_6735_(ServerLevel p_23064_, Mob p_23065_, long p_23066_) {
      BlockPos blockpos = m_23067_(p_23065_);
      boolean flag = blockpos.m_123314_(p_23065_.m_142538_(), (double)this.f_23054_);
      if (!flag) {
         BehaviorUtils.m_22617_(p_23065_, m_23069_(p_23065_, blockpos), this.f_23055_, this.f_23054_);
      }

   }

   private static BlockPos m_23069_(Mob p_23070_, BlockPos p_23071_) {
      Random random = p_23070_.f_19853_.f_46441_;
      return p_23071_.m_142082_(m_23072_(random), 0, m_23072_(random));
   }

   private static int m_23072_(Random p_23073_) {
      return p_23073_.nextInt(3) - 1;
   }

   private static BlockPos m_23067_(Mob p_23068_) {
      return p_23068_.m_6274_().m_21952_(MemoryModuleType.f_26341_).get();
   }
}