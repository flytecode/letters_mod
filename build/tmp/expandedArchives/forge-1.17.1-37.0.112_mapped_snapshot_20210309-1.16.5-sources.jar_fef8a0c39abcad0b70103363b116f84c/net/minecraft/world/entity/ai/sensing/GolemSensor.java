package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class GolemSensor extends Sensor<LivingEntity> {
   private static final int f_148277_ = 200;
   private static final int f_148278_ = 600;

   public GolemSensor() {
      this(200);
   }

   public GolemSensor(int p_26642_) {
      super(p_26642_);
   }

   protected void m_5578_(ServerLevel p_26645_, LivingEntity p_26646_) {
      m_26647_(p_26646_);
   }

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148204_);
   }

   public static void m_26647_(LivingEntity p_26648_) {
      Optional<List<LivingEntity>> optional = p_26648_.m_6274_().m_21952_(MemoryModuleType.f_148204_);
      if (optional.isPresent()) {
         boolean flag = optional.get().stream().anyMatch((p_26652_) -> {
            return p_26652_.m_6095_().equals(EntityType.f_20460_);
         });
         if (flag) {
            m_26649_(p_26648_);
         }

      }
   }

   public static void m_26649_(LivingEntity p_26650_) {
      p_26650_.m_6274_().m_21882_(MemoryModuleType.f_26327_, true, 600L);
   }
}