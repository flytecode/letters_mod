package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class SetLookAndInteract extends Behavior<LivingEntity> {
   private final EntityType<?> f_23937_;
   private final int f_23938_;
   private final Predicate<LivingEntity> f_23939_;
   private final Predicate<LivingEntity> f_23940_;

   public SetLookAndInteract(EntityType<?> p_23945_, int p_23946_, Predicate<LivingEntity> p_23947_, Predicate<LivingEntity> p_23948_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26374_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT));
      this.f_23937_ = p_23945_;
      this.f_23938_ = p_23946_ * p_23946_;
      this.f_23939_ = p_23948_;
      this.f_23940_ = p_23947_;
   }

   public SetLookAndInteract(EntityType<?> p_23942_, int p_23943_) {
      this(p_23942_, p_23943_, (p_23973_) -> {
         return true;
      }, (p_23971_) -> {
         return true;
      });
   }

   public boolean m_6114_(ServerLevel p_23950_, LivingEntity p_23951_) {
      return this.f_23940_.test(p_23951_) && this.m_23968_(p_23951_).stream().anyMatch(this::m_23956_);
   }

   public void m_6735_(ServerLevel p_23953_, LivingEntity p_23954_, long p_23955_) {
      super.m_6735_(p_23953_, p_23954_, p_23955_);
      Brain<?> brain = p_23954_.m_6274_();
      brain.m_21952_(MemoryModuleType.f_148205_).ifPresent((p_23964_) -> {
         p_23964_.stream().filter((p_147899_) -> {
            return p_147899_.m_20280_(p_23954_) <= (double)this.f_23938_;
         }).filter(this::m_23956_).findFirst().ifPresent((p_147902_) -> {
            brain.m_21879_(MemoryModuleType.f_26374_, p_147902_);
            brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_147902_, true));
         });
      });
   }

   private boolean m_23956_(LivingEntity p_23957_) {
      return this.f_23937_.equals(p_23957_.m_6095_()) && this.f_23939_.test(p_23957_);
   }

   private List<LivingEntity> m_23968_(LivingEntity p_23969_) {
      return p_23969_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get();
   }
}