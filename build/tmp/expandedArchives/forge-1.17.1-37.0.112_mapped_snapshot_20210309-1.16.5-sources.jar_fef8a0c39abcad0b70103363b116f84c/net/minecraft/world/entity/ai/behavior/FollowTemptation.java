package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

public class FollowTemptation extends Behavior<PathfinderMob> {
   public static final int f_147482_ = 100;
   public static final double f_147483_ = 2.5D;
   private final Function<LivingEntity, Float> f_147484_;

   public FollowTemptation(Function<LivingEntity, Float> p_147486_) {
      super(Util.m_137537_(() -> {
         Builder<MemoryModuleType<?>, MemoryStatus> builder = ImmutableMap.builder();
         builder.put(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED);
         builder.put(MemoryModuleType.f_26370_, MemoryStatus.REGISTERED);
         builder.put(MemoryModuleType.f_148197_, MemoryStatus.VALUE_ABSENT);
         builder.put(MemoryModuleType.f_148198_, MemoryStatus.REGISTERED);
         builder.put(MemoryModuleType.f_148196_, MemoryStatus.VALUE_PRESENT);
         builder.put(MemoryModuleType.f_26375_, MemoryStatus.VALUE_ABSENT);
         return builder.build();
      }));
      this.f_147484_ = p_147486_;
   }

   protected float m_147497_(PathfinderMob p_147498_) {
      return this.f_147484_.apply(p_147498_);
   }

   private Optional<Player> m_147508_(PathfinderMob p_147509_) {
      return p_147509_.m_6274_().m_21952_(MemoryModuleType.f_148196_);
   }

   protected boolean m_7773_(long p_147488_) {
      return false;
   }

   protected boolean m_6737_(ServerLevel p_147494_, PathfinderMob p_147495_, long p_147496_) {
      return this.m_147508_(p_147495_).isPresent() && !p_147495_.m_6274_().m_21874_(MemoryModuleType.f_26375_);
   }

   protected void m_6735_(ServerLevel p_147505_, PathfinderMob p_147506_, long p_147507_) {
      p_147506_.m_6274_().m_21879_(MemoryModuleType.f_148198_, true);
   }

   protected void m_6732_(ServerLevel p_147515_, PathfinderMob p_147516_, long p_147517_) {
      Brain<?> brain = p_147516_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_148197_, 100);
      brain.m_21879_(MemoryModuleType.f_148198_, false);
      brain.m_21936_(MemoryModuleType.f_26370_);
      brain.m_21936_(MemoryModuleType.f_26371_);
   }

   protected void m_6725_(ServerLevel p_147523_, PathfinderMob p_147524_, long p_147525_) {
      Player player = this.m_147508_(p_147524_).get();
      Brain<?> brain = p_147524_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(player, true));
      if (p_147524_.m_20280_(player) < 6.25D) {
         brain.m_21936_(MemoryModuleType.f_26370_);
      } else {
         brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker(player, false), this.m_147497_(p_147524_), 2));
      }

   }
}