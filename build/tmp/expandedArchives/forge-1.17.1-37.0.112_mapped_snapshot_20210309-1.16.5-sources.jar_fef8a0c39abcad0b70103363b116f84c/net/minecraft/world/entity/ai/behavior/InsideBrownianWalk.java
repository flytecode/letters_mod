package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InsideBrownianWalk extends Behavior<PathfinderMob> {
   private final float f_23207_;

   public InsideBrownianWalk(float p_23209_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
      this.f_23207_ = p_23209_;
   }

   protected boolean m_6114_(ServerLevel p_23218_, PathfinderMob p_23219_) {
      return !p_23218_.m_45527_(p_23219_.m_142538_());
   }

   protected void m_6735_(ServerLevel p_23221_, PathfinderMob p_23222_, long p_23223_) {
      BlockPos blockpos = p_23222_.m_142538_();
      List<BlockPos> list = BlockPos.m_121990_(blockpos.m_142082_(-1, -1, -1), blockpos.m_142082_(1, 1, 1)).map(BlockPos::m_7949_).collect(Collectors.toList());
      Collections.shuffle(list);
      Optional<BlockPos> optional = list.stream().filter((p_23230_) -> {
         return !p_23221_.m_45527_(p_23230_);
      }).filter((p_23237_) -> {
         return p_23221_.m_46575_(p_23237_, p_23222_);
      }).filter((p_23227_) -> {
         return p_23221_.m_45786_(p_23222_);
      }).findFirst();
      optional.ifPresent((p_23233_) -> {
         p_23222_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(p_23233_, this.f_23207_, 0));
      });
   }
}