package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class MoveToSkySeeingSpot extends Behavior<LivingEntity> {
   private final float f_23548_;

   public MoveToSkySeeingSpot(float p_23550_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
      this.f_23548_ = p_23550_;
   }

   protected void m_6735_(ServerLevel p_23555_, LivingEntity p_23556_, long p_23557_) {
      Optional<Vec3> optional = Optional.ofNullable(this.m_23564_(p_23555_, p_23556_));
      if (optional.isPresent()) {
         p_23556_.m_6274_().m_21886_(MemoryModuleType.f_26370_, optional.map((p_23563_) -> {
            return new WalkTarget(p_23563_, this.f_23548_, 0);
         }));
      }

   }

   protected boolean m_6114_(ServerLevel p_23552_, LivingEntity p_23553_) {
      return !p_23552_.m_45527_(p_23553_.m_142538_());
   }

   @Nullable
   private Vec3 m_23564_(ServerLevel p_23565_, LivingEntity p_23566_) {
      Random random = p_23566_.m_21187_();
      BlockPos blockpos = p_23566_.m_142538_();

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.m_142082_(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
         if (m_23558_(p_23565_, p_23566_, blockpos1)) {
            return Vec3.m_82539_(blockpos1);
         }
      }

      return null;
   }

   public static boolean m_23558_(ServerLevel p_23559_, LivingEntity p_23560_, BlockPos p_23561_) {
      return p_23559_.m_45527_(p_23561_) && (double)p_23559_.m_5452_(Heightmap.Types.MOTION_BLOCKING, p_23561_).m_123342_() <= p_23560_.m_20186_();
   }
}