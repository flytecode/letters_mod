package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;

public class WorkAtPoi extends Behavior<Villager> {
   private static final int f_148046_ = 300;
   private static final double f_148047_ = 1.73D;
   private long f_24804_;

   public WorkAtPoi() {
      super(ImmutableMap.of(MemoryModuleType.f_26360_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED));
   }

   protected boolean m_6114_(ServerLevel p_24827_, Villager p_24828_) {
      if (p_24827_.m_46467_() - this.f_24804_ < 300L) {
         return false;
      } else if (p_24827_.f_46441_.nextInt(2) != 0) {
         return false;
      } else {
         this.f_24804_ = p_24827_.m_46467_();
         GlobalPos globalpos = p_24828_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get();
         return globalpos.m_122640_() == p_24827_.m_46472_() && globalpos.m_122646_().m_123306_(p_24828_.m_20182_(), 1.73D);
      }
   }

   protected void m_6735_(ServerLevel p_24816_, Villager p_24817_, long p_24818_) {
      Brain<Villager> brain = p_24817_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_26330_, p_24818_);
      brain.m_21952_(MemoryModuleType.f_26360_).ifPresent((p_24821_) -> {
         brain.m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(p_24821_.m_122646_()));
      });
      p_24817_.m_35512_();
      this.m_5628_(p_24816_, p_24817_);
      if (p_24817_.m_35511_()) {
         p_24817_.m_35510_();
      }

   }

   protected void m_5628_(ServerLevel p_24813_, Villager p_24814_) {
   }

   protected boolean m_6737_(ServerLevel p_24830_, Villager p_24831_, long p_24832_) {
      Optional<GlobalPos> optional = p_24831_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
      if (!optional.isPresent()) {
         return false;
      } else {
         GlobalPos globalpos = optional.get();
         return globalpos.m_122640_() == p_24830_.m_46472_() && globalpos.m_122646_().m_123306_(p_24831_.m_20182_(), 1.73D);
      }
   }
}