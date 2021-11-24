package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Animal;

public class AnimalMakeLove extends Behavior<Animal> {
   private static final int f_147376_ = 3;
   private static final int f_147377_ = 60;
   private static final int f_147378_ = 110;
   private final EntityType<? extends Animal> f_22387_;
   private final float f_22388_;
   private long f_22389_;

   public AnimalMakeLove(EntityType<? extends Animal> p_22391_, float p_22392_) {
      super(ImmutableMap.of(MemoryModuleType.f_148205_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26375_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26370_, MemoryStatus.REGISTERED, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED), 110);
      this.f_22387_ = p_22391_;
      this.f_22388_ = p_22392_;
   }

   protected boolean m_6114_(ServerLevel p_22401_, Animal p_22402_) {
      return p_22402_.m_27593_() && this.m_22431_(p_22402_).isPresent();
   }

   protected void m_6735_(ServerLevel p_22404_, Animal p_22405_, long p_22406_) {
      Animal animal = this.m_22431_(p_22405_).get();
      p_22405_.m_6274_().m_21879_(MemoryModuleType.f_26375_, animal);
      animal.m_6274_().m_21879_(MemoryModuleType.f_26375_, p_22405_);
      BehaviorUtils.m_22602_(p_22405_, animal, this.f_22388_);
      int i = 60 + p_22405_.m_21187_().nextInt(50);
      this.f_22389_ = p_22406_ + (long)i;
   }

   protected boolean m_6737_(ServerLevel p_22416_, Animal p_22417_, long p_22418_) {
      if (!this.m_22421_(p_22417_)) {
         return false;
      } else {
         Animal animal = this.m_22409_(p_22417_);
         return animal.m_6084_() && p_22417_.m_7848_(animal) && BehaviorUtils.m_22636_(p_22417_.m_6274_(), animal) && p_22418_ <= this.f_22389_;
      }
   }

   protected void m_6725_(ServerLevel p_22428_, Animal p_22429_, long p_22430_) {
      Animal animal = this.m_22409_(p_22429_);
      BehaviorUtils.m_22602_(p_22429_, animal, this.f_22388_);
      if (p_22429_.m_19950_(animal, 3.0D)) {
         if (p_22430_ >= this.f_22389_) {
            p_22429_.m_27563_(p_22428_, animal);
            p_22429_.m_6274_().m_21936_(MemoryModuleType.f_26375_);
            animal.m_6274_().m_21936_(MemoryModuleType.f_26375_);
         }

      }
   }

   protected void m_6732_(ServerLevel p_22438_, Animal p_22439_, long p_22440_) {
      p_22439_.m_6274_().m_21936_(MemoryModuleType.f_26375_);
      p_22439_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
      p_22439_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
      this.f_22389_ = 0L;
   }

   private Animal m_22409_(Animal p_22410_) {
      return (Animal)p_22410_.m_6274_().m_21952_(MemoryModuleType.f_26375_).get();
   }

   private boolean m_22421_(Animal p_22422_) {
      Brain<?> brain = p_22422_.m_6274_();
      return brain.m_21874_(MemoryModuleType.f_26375_) && brain.m_21952_(MemoryModuleType.f_26375_).get().m_6095_() == this.f_22387_;
   }

   private Optional<? extends Animal> m_22431_(Animal p_22432_) {
      return p_22432_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get().stream().filter((p_22420_) -> {
         return p_22420_.m_6095_() == this.f_22387_;
      }).map((p_22408_) -> {
         return (Animal)p_22408_;
      }).filter(p_22432_::m_7848_).findFirst();
   }
}