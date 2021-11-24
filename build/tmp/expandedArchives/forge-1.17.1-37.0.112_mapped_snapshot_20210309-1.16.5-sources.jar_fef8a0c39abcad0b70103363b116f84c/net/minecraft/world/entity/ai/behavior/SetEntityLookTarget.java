package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class SetEntityLookTarget extends Behavior<LivingEntity> {
   private final Predicate<LivingEntity> f_23889_;
   private final float f_23890_;

   public SetEntityLookTarget(Tag<EntityType<?>> p_147885_, float p_147886_) {
      this((p_147889_) -> {
         return p_147889_.m_6095_().m_20609_(p_147885_);
      }, p_147886_);
   }

   public SetEntityLookTarget(MobCategory p_23897_, float p_23898_) {
      this((p_23923_) -> {
         return p_23897_.equals(p_23923_.m_6095_().m_20674_());
      }, p_23898_);
   }

   public SetEntityLookTarget(EntityType<?> p_23894_, float p_23895_) {
      this((p_23911_) -> {
         return p_23894_.equals(p_23911_.m_6095_());
      }, p_23895_);
   }

   public SetEntityLookTarget(float p_23892_) {
      this((p_23913_) -> {
         return true;
      }, p_23892_);
   }

   public SetEntityLookTarget(Predicate<LivingEntity> p_23900_, float p_23901_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT));
      this.f_23889_ = p_23900_;
      this.f_23890_ = p_23901_ * p_23901_;
   }

   protected boolean m_6114_(ServerLevel p_23903_, LivingEntity p_23904_) {
      return p_23904_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get().stream().anyMatch(this.f_23889_);
   }

   protected void m_6735_(ServerLevel p_23906_, LivingEntity p_23907_, long p_23908_) {
      Brain<?> brain = p_23907_.m_6274_();
      brain.m_21952_(MemoryModuleType.f_148205_).ifPresent((p_23920_) -> {
         p_23920_.stream().filter(this.f_23889_).filter((p_147892_) -> {
            return p_147892_.m_20280_(p_23907_) <= (double)this.f_23890_;
         }).findFirst().ifPresent((p_147895_) -> {
            brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_147895_, true));
         });
      });
   }
}