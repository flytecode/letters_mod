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
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InteractWith<E extends LivingEntity, T extends LivingEntity> extends Behavior<E> {
   private final int f_23238_;
   private final float f_23239_;
   private final EntityType<? extends T> f_23240_;
   private final int f_23241_;
   private final Predicate<T> f_23242_;
   private final Predicate<E> f_23243_;
   private final MemoryModuleType<T> f_23244_;

   public InteractWith(EntityType<? extends T> p_23246_, int p_23247_, Predicate<E> p_23248_, Predicate<T> p_23249_, MemoryModuleType<T> p_23250_, float p_23251_, int p_23252_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.REGISTERED, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT));
      this.f_23240_ = p_23246_;
      this.f_23239_ = p_23251_;
      this.f_23241_ = p_23247_ * p_23247_;
      this.f_23238_ = p_23252_;
      this.f_23242_ = p_23249_;
      this.f_23243_ = p_23248_;
      this.f_23244_ = p_23250_;
   }

   public static <T extends LivingEntity> InteractWith<LivingEntity, T> m_23260_(EntityType<? extends T> p_23261_, int p_23262_, MemoryModuleType<T> p_23263_, float p_23264_, int p_23265_) {
      return new InteractWith<>(p_23261_, p_23262_, (p_23287_) -> {
         return true;
      }, (p_23285_) -> {
         return true;
      }, p_23263_, p_23264_, p_23265_);
   }

   public static <T extends LivingEntity> InteractWith<LivingEntity, T> m_147566_(EntityType<? extends T> p_147567_, int p_147568_, Predicate<T> p_147569_, MemoryModuleType<T> p_147570_, float p_147571_, int p_147572_) {
      return new InteractWith<>(p_147567_, p_147568_, (p_147584_) -> {
         return true;
      }, p_147569_, p_147570_, p_147571_, p_147572_);
   }

   protected boolean m_6114_(ServerLevel p_23254_, E p_23255_) {
      return this.f_23243_.test(p_23255_) && this.m_23266_(p_23255_);
   }

   private boolean m_23266_(E p_23267_) {
      List<LivingEntity> list = p_23267_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get();
      return list.stream().anyMatch(this::m_23278_);
   }

   private boolean m_23278_(LivingEntity p_23279_) {
      return this.f_23240_.equals(p_23279_.m_6095_()) && this.f_23242_.test((T)p_23279_);
   }

   protected void m_6735_(ServerLevel p_23257_, E p_23258_, long p_23259_) {
      Brain<?> brain = p_23258_.m_6274_();
      brain.m_21952_(MemoryModuleType.f_148205_).ifPresent((p_23274_) -> {
         p_23274_.stream().filter((p_147582_) -> {
            return this.f_23240_.equals(p_147582_.m_6095_());
         }).map((p_147580_) -> {
            return (T) p_147580_;
         }).filter((p_147575_) -> {
            return p_147575_.m_20280_(p_23258_) <= (double)this.f_23241_;
         }).filter(this.f_23242_).findFirst().ifPresent((p_147578_) -> {
            brain.m_21879_(this.f_23244_, (T)p_147578_);
            brain.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_147578_, true));
            brain.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker(p_147578_, false), this.f_23239_, this.f_23238_));
         });
      });
   }
}