package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class VillagerHostilesSensor extends NearestVisibleLivingEntitySensor {
   private static final ImmutableMap<EntityType<?>, Float> f_26842_ = ImmutableMap.<EntityType<?>, Float>builder().put(EntityType.f_20562_, 8.0F).put(EntityType.f_20568_, 12.0F).put(EntityType.f_20458_, 8.0F).put(EntityType.f_20459_, 12.0F).put(EntityType.f_20513_, 15.0F).put(EntityType.f_20518_, 12.0F).put(EntityType.f_20491_, 8.0F).put(EntityType.f_20493_, 10.0F).put(EntityType.f_20500_, 10.0F).put(EntityType.f_20501_, 8.0F).put(EntityType.f_20530_, 8.0F).build();

   protected boolean m_142628_(LivingEntity p_148344_, LivingEntity p_148345_) {
      return this.m_26867_(p_148345_) && this.m_26860_(p_148344_, p_148345_);
   }

   private boolean m_26860_(LivingEntity p_26861_, LivingEntity p_26862_) {
      float f = f_26842_.get(p_26862_.m_6095_());
      return p_26862_.m_20280_(p_26861_) <= (double)(f * f);
   }

   protected MemoryModuleType<LivingEntity> m_142149_() {
      return MemoryModuleType.f_26323_;
   }

   private boolean m_26867_(LivingEntity p_26868_) {
      return f_26842_.containsKey(p_26868_.m_6095_());
   }
}