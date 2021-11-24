package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class NearestBedSensor extends Sensor<Mob> {
   private static final int f_148279_ = 40;
   private static final int f_148280_ = 5;
   private static final int f_148281_ = 20;
   private final Long2LongMap f_26676_ = new Long2LongOpenHashMap();
   private int f_26677_;
   private long f_26678_;

   public NearestBedSensor() {
      super(20);
   }

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26380_);
   }

   protected void m_5578_(ServerLevel p_26685_, Mob p_26686_) {
      if (p_26686_.m_6162_()) {
         this.f_26677_ = 0;
         this.f_26678_ = p_26685_.m_46467_() + (long)p_26685_.m_5822_().nextInt(20);
         PoiManager poimanager = p_26685_.m_8904_();
         Predicate<BlockPos> predicate = (p_26688_) -> {
            long i = p_26688_.m_121878_();
            if (this.f_26676_.containsKey(i)) {
               return false;
            } else if (++this.f_26677_ >= 5) {
               return false;
            } else {
               this.f_26676_.put(i, this.f_26678_ + 40L);
               return true;
            }
         };
         Stream<BlockPos> stream = poimanager.m_27138_(PoiType.f_27346_.m_27392_(), predicate, p_26686_.m_142538_(), 48, PoiManager.Occupancy.ANY);
         Path path = p_26686_.m_21573_().m_26556_(stream, PoiType.f_27346_.m_27397_());
         if (path != null && path.m_77403_()) {
            BlockPos blockpos = path.m_77406_();
            Optional<PoiType> optional = poimanager.m_27177_(blockpos);
            if (optional.isPresent()) {
               p_26686_.m_6274_().m_21879_(MemoryModuleType.f_26380_, blockpos);
            }
         } else if (this.f_26677_ < 5) {
            this.f_26676_.long2LongEntrySet().removeIf((p_26690_) -> {
               return p_26690_.getLongValue() < this.f_26678_;
            });
         }

      }
   }
}