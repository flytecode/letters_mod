package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StopAttackingIfTargetInvalid<E extends Mob> extends Behavior<E> {
   private static final int f_147978_ = 200;
   private final Predicate<LivingEntity> f_24233_;
   private final Consumer<E> f_147979_;

   public StopAttackingIfTargetInvalid(Predicate<LivingEntity> p_147983_, Consumer<E> p_147984_) {
      super(ImmutableMap.of(MemoryModuleType.f_26372_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26326_, MemoryStatus.REGISTERED));
      this.f_24233_ = p_147983_;
      this.f_147979_ = p_147984_;
   }

   public StopAttackingIfTargetInvalid(Predicate<LivingEntity> p_24236_) {
      this(p_24236_, (p_147992_) -> {
      });
   }

   public StopAttackingIfTargetInvalid(Consumer<E> p_147981_) {
      this((p_147988_) -> {
         return false;
      }, p_147981_);
   }

   public StopAttackingIfTargetInvalid() {
      this((p_147986_) -> {
         return false;
      }, (p_147990_) -> {
      });
   }

   protected void m_6735_(ServerLevel p_24242_, E p_24243_, long p_24244_) {
      LivingEntity livingentity = this.m_24251_(p_24243_);
      if (!p_24243_.m_6779_(livingentity)) {
         this.m_24255_(p_24243_);
      } else if (m_24245_(p_24243_)) {
         this.m_24255_(p_24243_);
      } else if (this.m_24253_(p_24243_)) {
         this.m_24255_(p_24243_);
      } else if (this.m_24247_(p_24243_)) {
         this.m_24255_(p_24243_);
      } else if (this.f_24233_.test(this.m_24251_(p_24243_))) {
         this.m_24255_(p_24243_);
      }
   }

   private boolean m_24247_(E p_24248_) {
      return this.m_24251_(p_24248_).f_19853_ != p_24248_.f_19853_;
   }

   private LivingEntity m_24251_(E p_24252_) {
      return p_24252_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
   }

   private static <E extends LivingEntity> boolean m_24245_(E p_24246_) {
      Optional<Long> optional = p_24246_.m_6274_().m_21952_(MemoryModuleType.f_26326_);
      return optional.isPresent() && p_24246_.f_19853_.m_46467_() - optional.get() > 200L;
   }

   private boolean m_24253_(E p_24254_) {
      Optional<LivingEntity> optional = p_24254_.m_6274_().m_21952_(MemoryModuleType.f_26372_);
      return optional.isPresent() && !optional.get().m_6084_();
   }

   protected void m_24255_(E p_24256_) {
      this.f_147979_.accept(p_24256_);
      p_24256_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
   }
}