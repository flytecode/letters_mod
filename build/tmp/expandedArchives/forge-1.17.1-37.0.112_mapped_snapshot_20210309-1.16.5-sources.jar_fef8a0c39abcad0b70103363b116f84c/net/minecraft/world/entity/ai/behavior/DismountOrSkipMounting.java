package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class DismountOrSkipMounting<E extends LivingEntity, T extends Entity> extends Behavior<E> {
   private final int f_22824_;
   private final BiPredicate<E, Entity> f_22825_;

   public DismountOrSkipMounting(int p_22827_, BiPredicate<E, Entity> p_22828_) {
      super(ImmutableMap.of(MemoryModuleType.f_26376_, MemoryStatus.REGISTERED));
      this.f_22824_ = p_22827_;
      this.f_22825_ = p_22828_;
   }

   protected boolean m_6114_(ServerLevel p_22830_, E p_22831_) {
      Entity entity = p_22831_.m_20202_();
      Entity entity1 = p_22831_.m_6274_().m_21952_(MemoryModuleType.f_26376_).orElse((Entity)null);
      if (entity == null && entity1 == null) {
         return false;
      } else {
         Entity entity2 = entity == null ? entity1 : entity;
         return !this.m_22836_(p_22831_, entity2) || this.f_22825_.test(p_22831_, entity2);
      }
   }

   private boolean m_22836_(E p_22837_, Entity p_22838_) {
      return p_22838_.m_6084_() && p_22838_.m_19950_(p_22837_, (double)this.f_22824_) && p_22838_.f_19853_ == p_22837_.f_19853_;
   }

   protected void m_6735_(ServerLevel p_22833_, E p_22834_, long p_22835_) {
      p_22834_.m_8127_();
      p_22834_.m_6274_().m_21936_(MemoryModuleType.f_26376_);
   }
}