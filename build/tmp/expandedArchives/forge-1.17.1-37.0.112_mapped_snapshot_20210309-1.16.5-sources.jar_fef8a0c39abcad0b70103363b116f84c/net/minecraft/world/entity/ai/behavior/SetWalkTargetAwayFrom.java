package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class SetWalkTargetAwayFrom<T> extends Behavior<PathfinderMob> {
   private final MemoryModuleType<T> f_23982_;
   private final float f_23983_;
   private final int f_23984_;
   private final Function<T, Vec3> f_23985_;

   public SetWalkTargetAwayFrom(MemoryModuleType<T> p_23987_, float p_23988_, int p_23989_, boolean p_23990_, Function<T, Vec3> p_23991_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, p_23990_ ? MemoryStatus.REGISTERED : MemoryStatus.VALUE_ABSENT, p_23987_, MemoryStatus.VALUE_PRESENT));
      this.f_23982_ = p_23987_;
      this.f_23983_ = p_23988_;
      this.f_23984_ = p_23989_;
      this.f_23985_ = p_23991_;
   }

   public static SetWalkTargetAwayFrom<BlockPos> m_24012_(MemoryModuleType<BlockPos> p_24013_, float p_24014_, int p_24015_, boolean p_24016_) {
      return new SetWalkTargetAwayFrom<>(p_24013_, p_24014_, p_24015_, p_24016_, Vec3::m_82539_);
   }

   public static SetWalkTargetAwayFrom<? extends Entity> m_24019_(MemoryModuleType<? extends Entity> p_24020_, float p_24021_, int p_24022_, boolean p_24023_) {
      return new SetWalkTargetAwayFrom<>(p_24020_, p_24021_, p_24022_, p_24023_, Entity::m_20182_);
   }

   protected boolean m_6114_(ServerLevel p_24000_, PathfinderMob p_24001_) {
      return this.m_24017_(p_24001_) ? false : p_24001_.m_20182_().m_82509_(this.m_24006_(p_24001_), (double)this.f_23984_);
   }

   private Vec3 m_24006_(PathfinderMob p_24007_) {
      return this.f_23985_.apply(p_24007_.m_6274_().m_21952_(this.f_23982_).get());
   }

   private boolean m_24017_(PathfinderMob p_24018_) {
      if (!p_24018_.m_6274_().m_21874_(MemoryModuleType.f_26370_)) {
         return false;
      } else {
         WalkTarget walktarget = p_24018_.m_6274_().m_21952_(MemoryModuleType.f_26370_).get();
         if (walktarget.m_26421_() != this.f_23983_) {
            return false;
         } else {
            Vec3 vec3 = walktarget.m_26420_().m_7024_().m_82546_(p_24018_.m_20182_());
            Vec3 vec31 = this.m_24006_(p_24018_).m_82546_(p_24018_.m_20182_());
            return vec3.m_82526_(vec31) < 0.0D;
         }
      }
   }

   protected void m_6735_(ServerLevel p_24003_, PathfinderMob p_24004_, long p_24005_) {
      m_24008_(p_24004_, this.m_24006_(p_24004_), this.f_23983_);
   }

   private static void m_24008_(PathfinderMob p_24009_, Vec3 p_24010_, float p_24011_) {
      for(int i = 0; i < 10; ++i) {
         Vec3 vec3 = LandRandomPos.m_148521_(p_24009_, 16, 7, p_24010_);
         if (vec3 != null) {
            p_24009_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(vec3, p_24011_, 0));
            return;
         }
      }

   }
}