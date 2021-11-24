package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.pathfinder.Path;

public class VillagerMakeLove extends Behavior<Villager> {
   private static final int f_148042_ = 5;
   private static final float f_148043_ = 0.5F;
   private long f_24613_;

   public VillagerMakeLove() {
      super(ImmutableMap.of(MemoryModuleType.f_26375_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT), 350, 350);
   }

   protected boolean m_6114_(ServerLevel p_24623_, Villager p_24624_) {
      return this.m_24639_(p_24624_);
   }

   protected boolean m_6737_(ServerLevel p_24626_, Villager p_24627_, long p_24628_) {
      return p_24628_ <= this.f_24613_ && this.m_24639_(p_24627_);
   }

   protected void m_6735_(ServerLevel p_24652_, Villager p_24653_, long p_24654_) {
      AgeableMob ageablemob = p_24653_.m_6274_().m_21952_(MemoryModuleType.f_26375_).get();
      BehaviorUtils.m_22602_(p_24653_, ageablemob, 0.5F);
      p_24652_.m_7605_(ageablemob, (byte)18);
      p_24652_.m_7605_(p_24653_, (byte)18);
      int i = 275 + p_24653_.m_21187_().nextInt(50);
      this.f_24613_ = p_24654_ + (long)i;
   }

   protected void m_6725_(ServerLevel p_24667_, Villager p_24668_, long p_24669_) {
      Villager villager = (Villager)p_24668_.m_6274_().m_21952_(MemoryModuleType.f_26375_).get();
      if (!(p_24668_.m_20280_(villager) > 5.0D)) {
         BehaviorUtils.m_22602_(p_24668_, villager, 0.5F);
         if (p_24669_ >= this.f_24613_) {
            p_24668_.m_35513_();
            villager.m_35513_();
            this.m_24629_(p_24667_, p_24668_, villager);
         } else if (p_24668_.m_21187_().nextInt(35) == 0) {
            p_24667_.m_7605_(villager, (byte)12);
            p_24667_.m_7605_(p_24668_, (byte)12);
         }

      }
   }

   private void m_24629_(ServerLevel p_24630_, Villager p_24631_, Villager p_24632_) {
      Optional<BlockPos> optional = this.m_24648_(p_24630_, p_24631_);
      if (!optional.isPresent()) {
         p_24630_.m_7605_(p_24632_, (byte)13);
         p_24630_.m_7605_(p_24631_, (byte)13);
      } else {
         Optional<Villager> optional1 = this.m_24655_(p_24630_, p_24631_, p_24632_);
         if (optional1.isPresent()) {
            this.m_24633_(p_24630_, optional1.get(), optional.get());
         } else {
            p_24630_.m_8904_().m_27154_(optional.get());
            DebugPackets.m_133719_(p_24630_, optional.get());
         }
      }

   }

   protected void m_6732_(ServerLevel p_24675_, Villager p_24676_, long p_24677_) {
      p_24676_.m_6274_().m_21936_(MemoryModuleType.f_26375_);
   }

   private boolean m_24639_(Villager p_24640_) {
      Brain<Villager> brain = p_24640_.m_6274_();
      Optional<AgeableMob> optional = brain.m_21952_(MemoryModuleType.f_26375_).filter((p_148045_) -> {
         return p_148045_.m_6095_() == EntityType.f_20492_;
      });
      if (!optional.isPresent()) {
         return false;
      } else {
         return BehaviorUtils.m_22639_(brain, MemoryModuleType.f_26375_, EntityType.f_20492_) && p_24640_.m_142668_() && optional.get().m_142668_();
      }
   }

   private Optional<BlockPos> m_24648_(ServerLevel p_24649_, Villager p_24650_) {
      return p_24649_.m_8904_().m_27133_(PoiType.f_27346_.m_27392_(), (p_24661_) -> {
         return this.m_24641_(p_24650_, p_24661_);
      }, p_24650_.m_142538_(), 48);
   }

   private boolean m_24641_(Villager p_24642_, BlockPos p_24643_) {
      Path path = p_24642_.m_21573_().m_7864_(p_24643_, PoiType.f_27346_.m_27397_());
      return path != null && path.m_77403_();
   }

   private Optional<Villager> m_24655_(ServerLevel p_24656_, Villager p_24657_, Villager p_24658_) {
      Villager villager = p_24657_.m_142606_(p_24656_, p_24658_);
      if (villager == null) {
         return Optional.empty();
      } else {
         p_24657_.m_146762_(6000);
         p_24658_.m_146762_(6000);
         villager.m_146762_(-24000);
         villager.m_7678_(p_24657_.m_20185_(), p_24657_.m_20186_(), p_24657_.m_20189_(), 0.0F, 0.0F);
         p_24656_.m_47205_(villager);
         p_24656_.m_7605_(villager, (byte)12);
         return Optional.of(villager);
      }
   }

   private void m_24633_(ServerLevel p_24634_, Villager p_24635_, BlockPos p_24636_) {
      GlobalPos globalpos = GlobalPos.m_122643_(p_24634_.m_46472_(), p_24636_);
      p_24635_.m_6274_().m_21879_(MemoryModuleType.f_26359_, globalpos);
   }
}