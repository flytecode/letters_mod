package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StartAttacking<E extends Mob> extends Behavior<E> {
   private final Predicate<E> f_24190_;
   private final Function<E, Optional<? extends LivingEntity>> f_24191_;

   public StartAttacking(Predicate<E> p_24195_, Function<E, Optional<? extends LivingEntity>> p_24196_) {
      super(ImmutableMap.of(MemoryModuleType.f_26372_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26326_, MemoryStatus.REGISTERED));
      this.f_24190_ = p_24195_;
      this.f_24191_ = p_24196_;
   }

   public StartAttacking(Function<E, Optional<? extends LivingEntity>> p_24193_) {
      this((p_24212_) -> {
         return true;
      }, p_24193_);
   }

   protected boolean m_6114_(ServerLevel p_24205_, E p_24206_) {
      if (!this.f_24190_.test(p_24206_)) {
         return false;
      } else {
         Optional<? extends LivingEntity> optional = this.f_24191_.apply(p_24206_);
         return optional.isPresent() ? p_24206_.m_6779_(optional.get()) : false;
      }
   }

   protected void m_6735_(ServerLevel p_24208_, E p_24209_, long p_24210_) {
      this.f_24191_.apply(p_24209_).ifPresent((p_24218_) -> {
         this.m_24213_(p_24209_, p_24218_);
      });
   }

   private void m_24213_(E p_24214_, LivingEntity p_24215_) {
      p_24214_.m_6274_().m_21879_(MemoryModuleType.f_26372_, p_24215_);
      p_24214_.m_6274_().m_21936_(MemoryModuleType.f_26326_);
   }
}