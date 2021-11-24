package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public abstract class NearestVisibleLivingEntitySensor extends Sensor<LivingEntity> {
   protected abstract boolean m_142628_(LivingEntity p_148292_, LivingEntity p_148293_);

   protected abstract MemoryModuleType<LivingEntity> m_142149_();

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(this.m_142149_());
   }

   protected void m_5578_(ServerLevel p_148288_, LivingEntity p_148289_) {
      p_148289_.m_6274_().m_21886_(this.m_142149_(), this.m_148297_(p_148289_));
   }

   private Optional<LivingEntity> m_148297_(LivingEntity p_148298_) {
      return this.m_148290_(p_148298_).flatMap((p_148296_) -> {
         return p_148296_.stream().filter((p_148301_) -> {
            return this.m_142628_(p_148298_, p_148301_);
         }).min(Comparator.comparingDouble(p_148298_::m_20280_));
      });
   }

   protected Optional<List<LivingEntity>> m_148290_(LivingEntity p_148291_) {
      return p_148291_.m_6274_().m_21952_(MemoryModuleType.f_148205_);
   }
}