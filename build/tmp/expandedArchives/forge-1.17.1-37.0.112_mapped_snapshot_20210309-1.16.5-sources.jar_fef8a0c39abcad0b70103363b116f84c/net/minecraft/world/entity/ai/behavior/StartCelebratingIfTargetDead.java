package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.GameRules;

public class StartCelebratingIfTargetDead extends Behavior<LivingEntity> {
   private final int f_24219_;
   private final BiPredicate<LivingEntity, LivingEntity> f_24220_;

   public StartCelebratingIfTargetDead(int p_24222_, BiPredicate<LivingEntity, LivingEntity> p_24223_) {
      super(ImmutableMap.of(MemoryModuleType.f_26372_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26334_, MemoryStatus.REGISTERED, MemoryModuleType.f_26341_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26342_, MemoryStatus.REGISTERED));
      this.f_24219_ = p_24222_;
      this.f_24220_ = p_24223_;
   }

   protected boolean m_6114_(ServerLevel p_24225_, LivingEntity p_24226_) {
      return this.m_24231_(p_24226_).m_21224_();
   }

   protected void m_6735_(ServerLevel p_24228_, LivingEntity p_24229_, long p_24230_) {
      LivingEntity livingentity = this.m_24231_(p_24229_);
      if (this.f_24220_.test(p_24229_, livingentity)) {
         p_24229_.m_6274_().m_21882_(MemoryModuleType.f_26342_, true, (long)this.f_24219_);
      }

      p_24229_.m_6274_().m_21882_(MemoryModuleType.f_26341_, livingentity.m_142538_(), (long)this.f_24219_);
      if (livingentity.m_6095_() != EntityType.f_20532_ || p_24228_.m_46469_().m_46207_(GameRules.f_46126_)) {
         p_24229_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
         p_24229_.m_6274_().m_21936_(MemoryModuleType.f_26334_);
      }

   }

   private LivingEntity m_24231_(LivingEntity p_24232_) {
      return p_24232_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
   }
}