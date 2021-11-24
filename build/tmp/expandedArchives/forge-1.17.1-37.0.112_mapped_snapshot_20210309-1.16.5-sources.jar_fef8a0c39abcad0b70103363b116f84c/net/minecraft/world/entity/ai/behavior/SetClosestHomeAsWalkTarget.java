package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class SetClosestHomeAsWalkTarget extends Behavior<LivingEntity> {
   private static final int f_147880_ = 40;
   private static final int f_147881_ = 5;
   private static final int f_147882_ = 20;
   private static final int f_147883_ = 4;
   private final float f_23872_;
   private final Long2LongMap f_23873_ = new Long2LongOpenHashMap();
   private int f_23874_;
   private long f_23875_;

   public SetClosestHomeAsWalkTarget(float p_23877_) {
      super(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26359_, MemoryStatus.VALUE_ABSENT));
      this.f_23872_ = p_23877_;
   }

   protected boolean m_6114_(ServerLevel p_23879_, LivingEntity p_23880_) {
      if (p_23879_.m_46467_() - this.f_23875_ < 20L) {
         return false;
      } else {
         PathfinderMob pathfindermob = (PathfinderMob)p_23880_;
         PoiManager poimanager = p_23879_.m_8904_();
         Optional<BlockPos> optional = poimanager.m_27192_(PoiType.f_27346_.m_27392_(), p_23880_.m_142538_(), 48, PoiManager.Occupancy.ANY);
         return optional.isPresent() && !(optional.get().m_123331_(pathfindermob.m_142538_()) <= 4.0D);
      }
   }

   protected void m_6735_(ServerLevel p_23882_, LivingEntity p_23883_, long p_23884_) {
      this.f_23874_ = 0;
      this.f_23875_ = p_23882_.m_46467_() + (long)p_23882_.m_5822_().nextInt(20);
      PathfinderMob pathfindermob = (PathfinderMob)p_23883_;
      PoiManager poimanager = p_23882_.m_8904_();
      Predicate<BlockPos> predicate = (p_23886_) -> {
         long i = p_23886_.m_121878_();
         if (this.f_23873_.containsKey(i)) {
            return false;
         } else if (++this.f_23874_ >= 5) {
            return false;
         } else {
            this.f_23873_.put(i, this.f_23875_ + 40L);
            return true;
         }
      };
      Stream<BlockPos> stream = poimanager.m_27138_(PoiType.f_27346_.m_27392_(), predicate, p_23883_.m_142538_(), 48, PoiManager.Occupancy.ANY);
      Path path = pathfindermob.m_21573_().m_26556_(stream, PoiType.f_27346_.m_27397_());
      if (path != null && path.m_77403_()) {
         BlockPos blockpos = path.m_77406_();
         Optional<PoiType> optional = poimanager.m_27177_(blockpos);
         if (optional.isPresent()) {
            p_23883_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget(blockpos, this.f_23872_, 1));
            DebugPackets.m_133719_(p_23882_, blockpos);
         }
      } else if (this.f_23874_ < 5) {
         this.f_23873_.long2LongEntrySet().removeIf((p_23888_) -> {
            return p_23888_.getLongValue() < this.f_23875_;
         });
      }

   }
}