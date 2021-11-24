package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class SetWalkTargetFromBlockMemory extends Behavior<Villager> {
   private final MemoryModuleType<GlobalPos> f_24040_;
   private final float f_24041_;
   private final int f_24042_;
   private final int f_24043_;
   private final int f_24044_;

   public SetWalkTargetFromBlockMemory(MemoryModuleType<GlobalPos> p_24046_, float p_24047_, int p_24048_, int p_24049_, int p_24050_) {
      super(ImmutableMap.of(MemoryModuleType.f_26326_, MemoryStatus.REGISTERED, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, p_24046_, MemoryStatus.VALUE_PRESENT));
      this.f_24040_ = p_24046_;
      this.f_24041_ = p_24047_;
      this.f_24042_ = p_24048_;
      this.f_24043_ = p_24049_;
      this.f_24044_ = p_24050_;
   }

   private void m_24075_(Villager p_24076_, long p_24077_) {
      Brain<?> brain = p_24076_.m_6274_();
      p_24076_.m_35428_(this.f_24040_);
      brain.m_21936_(this.f_24040_);
      brain.m_21879_(MemoryModuleType.f_26326_, p_24077_);
   }

   protected void m_6735_(ServerLevel p_24059_, Villager p_24060_, long p_24061_) {
      Brain<?> brain = p_24060_.m_6274_();
      brain.m_21952_(this.f_24040_).ifPresent((p_24067_) -> {
         if (!this.m_24072_(p_24059_, p_24067_) && !this.m_24055_(p_24059_, p_24060_)) {
            if (this.m_24078_(p_24060_, p_24067_)) {
               Vec3 vec3 = null;
               int i = 0;

               for(int j = 1000; i < 1000 && (vec3 == null || this.m_24078_(p_24060_, GlobalPos.m_122643_(p_24059_.m_46472_(), new BlockPos(vec3)))); ++i) {
                  vec3 = DefaultRandomPos.m_148412_(p_24060_, 15, 7, Vec3.m_82539_(p_24067_.m_122646_()), (double)((float)Math.PI / 2F));
               }

               if (i == 1000) {
                  this.m_24075_(p_24060_, p_24061_);
                  return;
               }

               brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(vec3, this.f_24041_, this.f_24042_));
            } else if (!this.m_24068_(p_24059_, p_24060_, p_24067_)) {
               brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(p_24067_.m_122646_(), this.f_24041_, this.f_24042_));
            }
         } else {
            this.m_24075_(p_24060_, p_24061_);
         }

      });
   }

   private boolean m_24055_(ServerLevel p_24056_, Villager p_24057_) {
      Optional<Long> optional = p_24057_.m_6274_().m_21952_(MemoryModuleType.f_26326_);
      if (optional.isPresent()) {
         return p_24056_.m_46467_() - optional.get() > (long)this.f_24044_;
      } else {
         return false;
      }
   }

   private boolean m_24078_(Villager p_24079_, GlobalPos p_24080_) {
      return p_24080_.m_122646_().m_123333_(p_24079_.m_142538_()) > this.f_24043_;
   }

   private boolean m_24072_(ServerLevel p_24073_, GlobalPos p_24074_) {
      return p_24074_.m_122640_() != p_24073_.m_46472_();
   }

   private boolean m_24068_(ServerLevel p_24069_, Villager p_24070_, GlobalPos p_24071_) {
      return p_24071_.m_122640_() == p_24069_.m_46472_() && p_24071_.m_122646_().m_123333_(p_24070_.m_142538_()) <= this.f_24042_;
   }
}