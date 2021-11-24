package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class HurtBySensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26381_, MemoryModuleType.f_26382_);
   }

   protected void m_5578_(ServerLevel p_26670_, LivingEntity p_26671_) {
      Brain<?> brain = p_26671_.m_6274_();
      DamageSource damagesource = p_26671_.m_21225_();
      if (damagesource != null) {
         brain.m_21879_(MemoryModuleType.f_26381_, p_26671_.m_21225_());
         Entity entity = damagesource.m_7639_();
         if (entity instanceof LivingEntity) {
            brain.m_21879_(MemoryModuleType.f_26382_, (LivingEntity)entity);
         }
      } else {
         brain.m_21936_(MemoryModuleType.f_26381_);
      }

      brain.m_21952_(MemoryModuleType.f_26382_).ifPresent((p_26675_) -> {
         if (!p_26675_.m_6084_() || p_26675_.f_19853_ != p_26670_) {
            brain.m_21936_(MemoryModuleType.f_26382_);
         }

      });
   }
}