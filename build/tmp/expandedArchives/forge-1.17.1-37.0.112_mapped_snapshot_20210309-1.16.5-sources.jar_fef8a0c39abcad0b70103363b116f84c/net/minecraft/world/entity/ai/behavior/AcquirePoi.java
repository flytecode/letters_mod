package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class AcquirePoi extends Behavior<PathfinderMob> {
   private static final int f_147364_ = 5;
   private static final int f_147365_ = 20;
   public static final int f_147363_ = 48;
   private final PoiType f_22316_;
   private final MemoryModuleType<GlobalPos> f_22317_;
   private final boolean f_22318_;
   private final Optional<Byte> f_22319_;
   private long f_22320_;
   private final Long2ObjectMap<AcquirePoi.JitteredLinearRetry> f_22321_ = new Long2ObjectOpenHashMap<>();

   public AcquirePoi(PoiType p_22323_, MemoryModuleType<GlobalPos> p_22324_, MemoryModuleType<GlobalPos> p_22325_, boolean p_22326_, Optional<Byte> p_22327_) {
      super(m_22361_(p_22324_, p_22325_));
      this.f_22316_ = p_22323_;
      this.f_22317_ = p_22325_;
      this.f_22318_ = p_22326_;
      this.f_22319_ = p_22327_;
   }

   public AcquirePoi(PoiType p_22329_, MemoryModuleType<GlobalPos> p_22330_, boolean p_22331_, Optional<Byte> p_22332_) {
      this(p_22329_, p_22330_, p_22330_, p_22331_, p_22332_);
   }

   private static ImmutableMap<MemoryModuleType<?>, MemoryStatus> m_22361_(MemoryModuleType<GlobalPos> p_22362_, MemoryModuleType<GlobalPos> p_22363_) {
      Builder<MemoryModuleType<?>, MemoryStatus> builder = ImmutableMap.builder();
      builder.put(p_22362_, MemoryStatus.VALUE_ABSENT);
      if (p_22363_ != p_22362_) {
         builder.put(p_22363_, MemoryStatus.VALUE_ABSENT);
      }

      return builder.build();
   }

   protected boolean m_6114_(ServerLevel p_22347_, PathfinderMob p_22348_) {
      if (this.f_22318_ && p_22348_.m_6162_()) {
         return false;
      } else if (this.f_22320_ == 0L) {
         this.f_22320_ = p_22348_.f_19853_.m_46467_() + (long)p_22347_.f_46441_.nextInt(20);
         return false;
      } else {
         return p_22347_.m_46467_() >= this.f_22320_;
      }
   }

   protected void m_6735_(ServerLevel p_22350_, PathfinderMob p_22351_, long p_22352_) {
      this.f_22320_ = p_22352_ + 20L + (long)p_22350_.m_5822_().nextInt(20);
      PoiManager poimanager = p_22350_.m_8904_();
      this.f_22321_.long2ObjectEntrySet().removeIf((p_22338_) -> {
         return !p_22338_.getValue().m_22382_(p_22352_);
      });
      Predicate<BlockPos> predicate = (p_22335_) -> {
         AcquirePoi.JitteredLinearRetry acquirepoi$jitteredlinearretry = this.f_22321_.get(p_22335_.m_121878_());
         if (acquirepoi$jitteredlinearretry == null) {
            return true;
         } else if (!acquirepoi$jitteredlinearretry.m_22384_(p_22352_)) {
            return false;
         } else {
            acquirepoi$jitteredlinearretry.m_22380_(p_22352_);
            return true;
         }
      };
      Set<BlockPos> set = poimanager.m_27171_(this.f_22316_.m_27392_(), predicate, p_22351_.m_142538_(), 48, PoiManager.Occupancy.HAS_SPACE).limit(5L).collect(Collectors.toSet());
      Path path = p_22351_.m_21573_().m_26548_(set, this.f_22316_.m_27397_());
      if (path != null && path.m_77403_()) {
         BlockPos blockpos1 = path.m_77406_();
         poimanager.m_27177_(blockpos1).ifPresent((p_22369_) -> {
            poimanager.m_27133_(this.f_22316_.m_27392_(), (p_147372_) -> {
               return p_147372_.equals(blockpos1);
            }, blockpos1, 1);
            p_22351_.m_6274_().m_21879_(this.f_22317_, GlobalPos.m_122643_(p_22350_.m_46472_(), blockpos1));
            this.f_22319_.ifPresent((p_147369_) -> {
               p_22350_.m_7605_(p_22351_, p_147369_);
            });
            this.f_22321_.clear();
            DebugPackets.m_133719_(p_22350_, blockpos1);
         });
      } else {
         for(BlockPos blockpos : set) {
            this.f_22321_.computeIfAbsent(blockpos.m_121878_(), (p_22360_) -> {
               return new AcquirePoi.JitteredLinearRetry(p_22351_.f_19853_.f_46441_, p_22352_);
            });
         }
      }

   }

   static class JitteredLinearRetry {
      private static final int f_147373_ = 40;
      private static final int f_147374_ = 80;
      private static final int f_147375_ = 400;
      private final Random f_22373_;
      private long f_22374_;
      private long f_22375_;
      private int f_22376_;

      JitteredLinearRetry(Random p_22378_, long p_22379_) {
         this.f_22373_ = p_22378_;
         this.m_22380_(p_22379_);
      }

      public void m_22380_(long p_22381_) {
         this.f_22374_ = p_22381_;
         int i = this.f_22376_ + this.f_22373_.nextInt(40) + 40;
         this.f_22376_ = Math.min(i, 400);
         this.f_22375_ = p_22381_ + (long)this.f_22376_;
      }

      public boolean m_22382_(long p_22383_) {
         return p_22383_ - this.f_22374_ < 400L;
      }

      public boolean m_22384_(long p_22385_) {
         return p_22385_ >= this.f_22375_;
      }

      public String toString() {
         return "RetryMarker{, previousAttemptAt=" + this.f_22374_ + ", nextScheduledAttemptAt=" + this.f_22375_ + ", currentDelay=" + this.f_22376_ + "}";
      }
   }
}