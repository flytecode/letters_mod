package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class LocateHidingPlace extends Behavior<LivingEntity> {
   private final float f_23403_;
   private final int f_23404_;
   private final int f_23405_;
   private Optional<BlockPos> f_23406_ = Optional.empty();

   public LocateHidingPlace(int p_23408_, float p_23409_, int p_23410_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26359_, MemoryStatus.REGISTERED, MemoryModuleType.f_26324_, MemoryStatus.REGISTERED));
      this.f_23404_ = p_23408_;
      this.f_23403_ = p_23409_;
      this.f_23405_ = p_23410_;
   }

   protected boolean m_6114_(ServerLevel p_23412_, LivingEntity p_23413_) {
      Optional<BlockPos> optional = p_23412_.m_8904_().m_27186_((p_23423_) -> {
         return p_23423_ == PoiType.f_27346_;
      }, (p_23425_) -> {
         return true;
      }, p_23413_.m_142538_(), this.f_23405_ + 1, PoiManager.Occupancy.ANY);
      if (optional.isPresent() && optional.get().m_123306_(p_23413_.m_20182_(), (double)this.f_23405_)) {
         this.f_23406_ = optional;
      } else {
         this.f_23406_ = Optional.empty();
      }

      return true;
   }

   protected void m_6735_(ServerLevel p_23415_, LivingEntity p_23416_, long p_23417_) {
      Brain<?> brain = p_23416_.m_6274_();
      Optional<BlockPos> optional = this.f_23406_;
      if (!optional.isPresent()) {
         optional = p_23415_.m_8904_().m_27126_((p_23419_) -> {
            return p_23419_ == PoiType.f_27346_;
         }, (p_23421_) -> {
            return true;
         }, PoiManager.Occupancy.ANY, p_23416_.m_142538_(), this.f_23404_, p_23416_.m_21187_());
         if (!optional.isPresent()) {
            Optional<GlobalPos> optional1 = brain.m_21952_(MemoryModuleType.f_26359_);
            if (optional1.isPresent()) {
               optional = Optional.of(optional1.get().m_122646_());
            }
         }
      }

      if (optional.isPresent()) {
         brain.m_21936_(MemoryModuleType.f_26377_);
         brain.m_21936_(MemoryModuleType.f_26371_);
         brain.m_21936_(MemoryModuleType.f_26375_);
         brain.m_21936_(MemoryModuleType.f_26374_);
         brain.m_21879_(MemoryModuleType.f_26324_, GlobalPos.m_122643_(p_23415_.m_46472_(), optional.get()));
         if (!optional.get().m_123306_(p_23416_.m_20182_(), (double)this.f_23405_)) {
            brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(optional.get(), this.f_23403_, this.f_23405_));
         }
      }

   }
}