package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.pathfinder.Path;

public class YieldJobSite extends Behavior<Villager> {
   private final float f_24833_;

   public YieldJobSite(float p_24835_) {
      super(ImmutableMap.of(MemoryModuleType.f_26361_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26360_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148204_, MemoryStatus.VALUE_PRESENT));
      this.f_24833_ = p_24835_;
   }

   protected boolean m_6114_(ServerLevel p_24844_, Villager p_24845_) {
      if (p_24845_.m_6162_()) {
         return false;
      } else {
         return p_24845_.m_7141_().m_35571_() == VillagerProfession.f_35585_;
      }
   }

   protected void m_6735_(ServerLevel p_24847_, Villager p_24848_, long p_24849_) {
      BlockPos blockpos = p_24848_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get().m_122646_();
      Optional<PoiType> optional = p_24847_.m_8904_().m_27177_(blockpos);
      if (optional.isPresent()) {
         BehaviorUtils.m_22650_(p_24848_, (p_24874_) -> {
            return this.m_24861_(optional.get(), p_24874_, blockpos);
         }).findFirst().ifPresent((p_24860_) -> {
            this.m_24850_(p_24847_, p_24848_, p_24860_, blockpos, p_24860_.m_6274_().m_21952_(MemoryModuleType.f_26360_).isPresent());
         });
      }
   }

   private boolean m_24861_(PoiType p_24862_, Villager p_24863_, BlockPos p_24864_) {
      boolean flag = p_24863_.m_6274_().m_21952_(MemoryModuleType.f_26361_).isPresent();
      if (flag) {
         return false;
      } else {
         Optional<GlobalPos> optional = p_24863_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
         VillagerProfession villagerprofession = p_24863_.m_7141_().m_35571_();
         if (p_24863_.m_7141_().m_35571_() != VillagerProfession.f_35585_ && villagerprofession.m_35622_().m_27392_().test(p_24862_)) {
            return !optional.isPresent() ? this.m_24867_(p_24863_, p_24864_, p_24862_) : optional.get().m_122646_().equals(p_24864_);
         } else {
            return false;
         }
      }
   }

   private void m_24850_(ServerLevel p_24851_, Villager p_24852_, Villager p_24853_, BlockPos p_24854_, boolean p_24855_) {
      this.m_24865_(p_24852_);
      if (!p_24855_) {
         BehaviorUtils.m_22617_(p_24853_, p_24854_, this.f_24833_, 1);
         p_24853_.m_6274_().m_21879_(MemoryModuleType.f_26361_, GlobalPos.m_122643_(p_24851_.m_46472_(), p_24854_));
         DebugPackets.m_133719_(p_24851_, p_24854_);
      }

   }

   private boolean m_24867_(Villager p_24868_, BlockPos p_24869_, PoiType p_24870_) {
      Path path = p_24868_.m_21573_().m_7864_(p_24869_, p_24870_.m_27397_());
      return path != null && path.m_77403_();
   }

   private void m_24865_(Villager p_24866_) {
      p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
      p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
      p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26361_);
   }
}