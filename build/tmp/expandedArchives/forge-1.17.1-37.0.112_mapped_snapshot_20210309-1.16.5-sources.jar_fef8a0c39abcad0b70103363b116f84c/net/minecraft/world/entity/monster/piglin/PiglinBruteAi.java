package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.InteractWithDoor;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.schedule.Activity;

public class PiglinBruteAi {
   private static final int f_149977_ = 600;
   private static final int f_149978_ = 20;
   private static final double f_149979_ = 0.0125D;
   private static final int f_149980_ = 8;
   private static final int f_149981_ = 8;
   private static final double f_149982_ = 12.0D;
   private static final float f_149983_ = 0.6F;
   private static final int f_149984_ = 2;
   private static final int f_149985_ = 100;
   private static final int f_149986_ = 5;

   protected static Brain<?> m_35099_(PiglinBrute p_35100_, Brain<PiglinBrute> p_35101_) {
      m_35111_(p_35100_, p_35101_);
      m_35119_(p_35100_, p_35101_);
      m_35124_(p_35100_, p_35101_);
      p_35101_.m_21930_(ImmutableSet.of(Activity.f_37978_));
      p_35101_.m_21944_(Activity.f_37979_);
      p_35101_.m_21962_();
      return p_35101_;
   }

   protected static void m_35094_(PiglinBrute p_35095_) {
      GlobalPos globalpos = GlobalPos.m_122643_(p_35095_.f_19853_.m_46472_(), p_35095_.m_142538_());
      p_35095_.m_6274_().m_21879_(MemoryModuleType.f_26359_, globalpos);
   }

   private static void m_35111_(PiglinBrute p_35112_, Brain<PiglinBrute> p_35113_) {
      p_35113_.m_21891_(Activity.f_37978_, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), new InteractWithDoor(), new StopBeingAngryIfTargetDead<>()));
   }

   private static void m_35119_(PiglinBrute p_35120_, Brain<PiglinBrute> p_35121_) {
      p_35121_.m_21891_(Activity.f_37979_, 10, ImmutableList.of(new StartAttacking<>(PiglinBruteAi::m_35086_), m_35080_(), m_35105_(), new SetLookAndInteract(EntityType.f_20532_, 4)));
   }

   private static void m_35124_(PiglinBrute p_35125_, Brain<PiglinBrute> p_35126_) {
      p_35126_.m_21895_(Activity.f_37988_, 10, ImmutableList.of(new StopAttackingIfTargetInvalid<>((p_35118_) -> {
         return !m_35088_(p_35125_, p_35118_);
      }), new SetWalkTargetFromAttackTargetIfTargetOutOfReach(1.0F), new MeleeAttack(20)), MemoryModuleType.f_26372_);
   }

   private static RunOne<PiglinBrute> m_35080_() {
      return new RunOne<>(ImmutableList.of(Pair.of(new SetEntityLookTarget(EntityType.f_20532_, 8.0F), 1), Pair.of(new SetEntityLookTarget(EntityType.f_20511_, 8.0F), 1), Pair.of(new SetEntityLookTarget(EntityType.f_20512_, 8.0F), 1), Pair.of(new SetEntityLookTarget(8.0F), 1), Pair.of(new DoNothing(30, 60), 1)));
   }

   private static RunOne<PiglinBrute> m_35105_() {
      return new RunOne<>(ImmutableList.of(Pair.of(new RandomStroll(0.6F), 2), Pair.of(InteractWith.m_23260_(EntityType.f_20511_, 8, MemoryModuleType.f_26374_, 0.6F, 2), 2), Pair.of(InteractWith.m_23260_(EntityType.f_20512_, 8, MemoryModuleType.f_26374_, 0.6F, 2), 2), Pair.of(new StrollToPoi(MemoryModuleType.f_26359_, 0.6F, 2, 100), 2), Pair.of(new StrollAroundPoi(MemoryModuleType.f_26359_, 0.6F, 5), 2), Pair.of(new DoNothing(30, 60), 1)));
   }

   protected static void m_35109_(PiglinBrute p_35110_) {
      Brain<PiglinBrute> brain = p_35110_.m_6274_();
      Activity activity = brain.m_21968_().orElse((Activity)null);
      brain.m_21926_(ImmutableList.of(Activity.f_37988_, Activity.f_37979_));
      Activity activity1 = brain.m_21968_().orElse((Activity)null);
      if (activity != activity1) {
         m_35122_(p_35110_);
      }

      p_35110_.m_21561_(brain.m_21874_(MemoryModuleType.f_26372_));
   }

   private static boolean m_35088_(AbstractPiglin p_35089_, LivingEntity p_35090_) {
      return m_35086_(p_35089_).filter((p_35085_) -> {
         return p_35085_ == p_35090_;
      }).isPresent();
   }

   private static Optional<? extends LivingEntity> m_35086_(AbstractPiglin p_35087_) {
      Optional<LivingEntity> optional = BehaviorUtils.m_22610_(p_35087_, MemoryModuleType.f_26334_);
      if (optional.isPresent() && Sensor.m_182377_(p_35087_, optional.get())) {
         return optional;
      } else {
         Optional<? extends LivingEntity> optional1 = m_35091_(p_35087_, MemoryModuleType.f_148206_);
         return optional1.isPresent() ? optional1 : p_35087_.m_6274_().m_21952_(MemoryModuleType.f_26333_);
      }
   }

   private static Optional<? extends LivingEntity> m_35091_(AbstractPiglin p_35092_, MemoryModuleType<? extends LivingEntity> p_35093_) {
      return p_35092_.m_6274_().m_21952_(p_35093_).filter((p_35108_) -> {
         return p_35108_.m_19950_(p_35092_, 12.0D);
      });
   }

   protected static void m_35096_(PiglinBrute p_35097_, LivingEntity p_35098_) {
      if (!(p_35098_ instanceof AbstractPiglin)) {
         PiglinAi.m_34826_(p_35097_, p_35098_);
      }
   }

   protected static void m_149988_(PiglinBrute p_149989_, LivingEntity p_149990_) {
      p_149989_.m_6274_().m_21936_(MemoryModuleType.f_26326_);
      p_149989_.m_6274_().m_21882_(MemoryModuleType.f_26334_, p_149990_.m_142081_(), 600L);
   }

   protected static void m_35114_(PiglinBrute p_35115_) {
      if ((double)p_35115_.f_19853_.f_46441_.nextFloat() < 0.0125D) {
         m_35122_(p_35115_);
      }

   }

   private static void m_35122_(PiglinBrute p_35123_) {
      p_35123_.m_6274_().m_21968_().ifPresent((p_35104_) -> {
         if (p_35104_ == Activity.f_37988_) {
            p_35123_.m_35076_();
         }

      });
   }
}