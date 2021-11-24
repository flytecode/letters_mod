package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class VillageBoundRandomStroll extends Behavior<PathfinderMob> {
   private static final int f_148037_ = 10;
   private static final int f_148038_ = 7;
   private final float f_24542_;
   private final int f_24543_;
   private final int f_24544_;

   public VillageBoundRandomStroll(float p_24546_) {
      this(p_24546_, 10, 7);
   }

   public VillageBoundRandomStroll(float p_24548_, int p_24549_, int p_24550_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
      this.f_24542_ = p_24548_;
      this.f_24543_ = p_24549_;
      this.f_24544_ = p_24550_;
   }

   protected void m_6735_(ServerLevel p_24556_, PathfinderMob p_24557_, long p_24558_) {
      BlockPos blockpos = p_24557_.m_142538_();
      if (p_24556_.m_8802_(blockpos)) {
         this.m_24559_(p_24557_);
      } else {
         SectionPos sectionpos = SectionPos.m_123199_(blockpos);
         SectionPos sectionpos1 = BehaviorUtils.m_22581_(p_24556_, sectionpos, 2);
         if (sectionpos1 != sectionpos) {
            this.m_24561_(p_24557_, sectionpos1);
         } else {
            this.m_24559_(p_24557_);
         }
      }

   }

   private void m_24561_(PathfinderMob p_24562_, SectionPos p_24563_) {
      Optional<Vec3> optional = Optional.ofNullable(DefaultRandomPos.m_148412_(p_24562_, this.f_24543_, this.f_24544_, Vec3.m_82539_(p_24563_.m_123250_()), (double)((float)Math.PI / 2F)));
      p_24562_.m_6274_().m_21886_(MemoryModuleType.f_26370_, optional.map((p_24567_) -> {
         return new WalkTarget(p_24567_, this.f_24542_, 0);
      }));
   }

   private void m_24559_(PathfinderMob p_24560_) {
      Optional<Vec3> optional = Optional.ofNullable(LandRandomPos.m_148488_(p_24560_, this.f_24543_, this.f_24544_));
      p_24560_.m_6274_().m_21886_(MemoryModuleType.f_26370_, optional.map((p_24565_) -> {
         return new WalkTarget(p_24565_, this.f_24542_, 0);
      }));
   }
}