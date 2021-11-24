package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;

public class SleepInBed extends Behavior<LivingEntity> {
   public static final int f_147968_ = 100;
   private long f_24149_;

   public SleepInBed() {
      super(ImmutableMap.of(MemoryModuleType.f_26359_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26329_, MemoryStatus.REGISTERED));
   }

   protected boolean m_6114_(ServerLevel p_24154_, LivingEntity p_24155_) {
      if (p_24155_.m_20159_()) {
         return false;
      } else {
         Brain<?> brain = p_24155_.m_6274_();
         GlobalPos globalpos = brain.m_21952_(MemoryModuleType.f_26359_).get();
         if (p_24154_.m_46472_() != globalpos.m_122640_()) {
            return false;
         } else {
            Optional<Long> optional = brain.m_21952_(MemoryModuleType.f_26329_);
            if (optional.isPresent()) {
               long i = p_24154_.m_46467_() - optional.get();
               if (i > 0L && i < 100L) {
                  return false;
               }
            }

            BlockState blockstate = p_24154_.m_8055_(globalpos.m_122646_());
            return globalpos.m_122646_().m_123306_(p_24155_.m_20182_(), 2.0D) && blockstate.m_60620_(BlockTags.f_13038_) && !blockstate.m_61143_(BedBlock.f_49441_);
         }
      }
   }

   protected boolean m_6737_(ServerLevel p_24161_, LivingEntity p_24162_, long p_24163_) {
      Optional<GlobalPos> optional = p_24162_.m_6274_().m_21952_(MemoryModuleType.f_26359_);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos blockpos = optional.get().m_122646_();
         return p_24162_.m_6274_().m_21954_(Activity.f_37982_) && p_24162_.m_20186_() > (double)blockpos.m_123342_() + 0.4D && blockpos.m_123306_(p_24162_.m_20182_(), 1.14D);
      }
   }

   protected void m_6735_(ServerLevel p_24157_, LivingEntity p_24158_, long p_24159_) {
      if (p_24159_ > this.f_24149_) {
         InteractWithDoor.m_23298_(p_24157_, p_24158_, (Node)null, (Node)null);
         p_24158_.m_5802_(p_24158_.m_6274_().m_21952_(MemoryModuleType.f_26359_).get().m_122646_());
      }

   }

   protected boolean m_7773_(long p_24152_) {
      return false;
   }

   protected void m_6732_(ServerLevel p_24165_, LivingEntity p_24166_, long p_24167_) {
      if (p_24166_.m_5803_()) {
         p_24166_.m_5796_();
         this.f_24149_ = p_24167_ + 40L;
      }

   }
}