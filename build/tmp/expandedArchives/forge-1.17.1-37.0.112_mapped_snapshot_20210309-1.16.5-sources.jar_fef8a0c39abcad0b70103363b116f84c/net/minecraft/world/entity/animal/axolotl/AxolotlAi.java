package net.minecraft.world.entity.animal.axolotl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RandomSwim;
import net.minecraft.world.entity.ai.behavior.RunIf;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.TryFindWater;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AxolotlAi {
   private static final UniformInt f_149279_ = UniformInt.m_146622_(5, 16);
   private static final float f_149280_ = 0.2F;
   private static final float f_149281_ = 0.15F;
   private static final float f_149282_ = 0.5F;
   private static final float f_149283_ = 0.6F;
   private static final float f_149284_ = 0.6F;

   protected static Brain<?> m_149290_(Brain<Axolotl> p_149291_) {
      m_149306_(p_149291_);
      m_149308_(p_149291_);
      m_149302_(p_149291_);
      m_149296_(p_149291_);
      p_149291_.m_21930_(ImmutableSet.of(Activity.f_37978_));
      p_149291_.m_21944_(Activity.f_37979_);
      p_149291_.m_21962_();
      return p_149291_;
   }

   private static void m_149296_(Brain<Axolotl> p_149297_) {
      p_149297_.m_21907_(Activity.f_150238_, ImmutableList.of(Pair.of(0, new PlayDead()), Pair.of(1, new EraseMemoryIf<>(AxolotlAi::m_149304_, MemoryModuleType.f_148195_))), ImmutableSet.of(Pair.of(MemoryModuleType.f_148195_, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of(MemoryModuleType.f_148195_));
   }

   private static void m_149302_(Brain<Axolotl> p_149303_) {
      p_149303_.m_21895_(Activity.f_37988_, 0, ImmutableList.of(new StopAttackingIfTargetInvalid<>(Axolotl::m_149119_), new SetWalkTargetFromAttackTargetIfTargetOutOfReach(AxolotlAi::m_149288_), new MeleeAttack(20), new EraseMemoryIf<Axolotl>(AxolotlAi::m_149304_, MemoryModuleType.f_26372_)), MemoryModuleType.f_26372_);
   }

   private static void m_149306_(Brain<Axolotl> p_149307_) {
      p_149307_.m_21891_(Activity.f_37978_, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), new ValidatePlayDead(), new CountDownCooldownTicks(MemoryModuleType.f_148197_)));
   }

   private static void m_149308_(Brain<Axolotl> p_149309_) {
      p_149309_.m_21900_(Activity.f_37979_, ImmutableList.of(Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.f_20532_, 6.0F), UniformInt.m_146622_(30, 60))), Pair.of(1, new AnimalMakeLove(EntityType.f_147039_, 0.2F)), Pair.of(2, new RunOne<>(ImmutableList.of(Pair.of(new FollowTemptation(AxolotlAi::m_149300_), 1), Pair.of(new BabyFollowAdult<>(f_149279_, AxolotlAi::m_149294_), 1)))), Pair.of(3, new StartAttacking<>(AxolotlAi::m_149298_)), Pair.of(3, new TryFindWater(6, 0.15F)), Pair.of(4, new GateBehavior<>(ImmutableMap.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(), GateBehavior.OrderPolicy.ORDERED, GateBehavior.RunningPolicy.TRY_ALL, ImmutableList.of(Pair.of(new RandomSwim(0.5F), 2), Pair.of(new RandomStroll(0.15F, false), 2), Pair.of(new SetWalkTargetFromLookTarget(AxolotlAi::m_182380_, AxolotlAi::m_149300_, 3), 3), Pair.of(new RunIf<>(Entity::m_20072_, new DoNothing(30, 60)), 5), Pair.of(new RunIf<>(Entity::m_20096_, new DoNothing(200, 400)), 5))))));
   }

   private static boolean m_182380_(LivingEntity p_182381_) {
      Level level = p_182381_.f_19853_;
      Optional<PositionTracker> optional = p_182381_.m_6274_().m_21952_(MemoryModuleType.f_26371_);
      if (optional.isPresent()) {
         BlockPos blockpos = optional.get().m_6675_();
         return level.m_46801_(blockpos) == p_182381_.m_20072_();
      } else {
         return false;
      }
   }

   public static void m_149292_(Axolotl p_149293_) {
      Brain<Axolotl> brain = p_149293_.m_6274_();
      Activity activity = brain.m_21968_().orElse((Activity)null);
      if (activity != Activity.f_150238_) {
         brain.m_21926_(ImmutableList.of(Activity.f_150238_, Activity.f_37988_, Activity.f_37979_));
         if (activity == Activity.f_37988_ && brain.m_21968_().orElse((Activity)null) != Activity.f_37988_) {
            brain.m_21882_(MemoryModuleType.f_148201_, true, 2400L);
         }
      }

   }

   private static float m_149288_(LivingEntity p_149289_) {
      return p_149289_.m_20072_() ? 0.6F : 0.15F;
   }

   private static float m_149294_(LivingEntity p_149295_) {
      return p_149295_.m_20072_() ? 0.6F : 0.15F;
   }

   private static float m_149300_(LivingEntity p_149301_) {
      return p_149301_.m_20072_() ? 0.5F : 0.15F;
   }

   private static Optional<? extends LivingEntity> m_149298_(Axolotl p_149299_) {
      return m_149304_(p_149299_) ? Optional.empty() : p_149299_.m_6274_().m_21952_(MemoryModuleType.f_148194_);
   }

   private static boolean m_149304_(Axolotl p_149305_) {
      return p_149305_.m_6274_().m_21874_(MemoryModuleType.f_26375_);
   }

   public static Ingredient m_149287_() {
      return Ingredient.m_43911_(ItemTags.f_144321_);
   }
}