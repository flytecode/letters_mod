package net.minecraft.world.entity.ai.behavior;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class BehaviorUtils {
   public static void m_22602_(LivingEntity p_22603_, LivingEntity p_22604_, float p_22605_) {
      m_22670_(p_22603_, p_22604_);
      m_22660_(p_22603_, p_22604_, p_22605_);
   }

   public static boolean m_22636_(Brain<?> p_22637_, LivingEntity p_22638_) {
      return p_22637_.m_21952_(MemoryModuleType.f_148205_).filter((p_22624_) -> {
         return p_22624_.contains(p_22638_);
      }).isPresent();
   }

   public static boolean m_22639_(Brain<?> p_22640_, MemoryModuleType<? extends LivingEntity> p_22641_, EntityType<?> p_22642_) {
      return m_22643_(p_22640_, p_22641_, (p_22587_) -> {
         return p_22587_.m_6095_() == p_22642_;
      });
   }

   private static boolean m_22643_(Brain<?> p_22644_, MemoryModuleType<? extends LivingEntity> p_22645_, Predicate<LivingEntity> p_22646_) {
      return p_22644_.m_21952_(p_22645_).filter(p_22646_).filter(LivingEntity::m_6084_).filter((p_22666_) -> {
         return m_22636_(p_22644_, p_22666_);
      }).isPresent();
   }

   private static void m_22670_(LivingEntity p_22671_, LivingEntity p_22672_) {
      m_22595_(p_22671_, p_22672_);
      m_22595_(p_22672_, p_22671_);
   }

   public static void m_22595_(LivingEntity p_22596_, LivingEntity p_22597_) {
      p_22596_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_22597_, true));
   }

   private static void m_22660_(LivingEntity p_22661_, LivingEntity p_22662_, float p_22663_) {
      int i = 2;
      m_22590_(p_22661_, p_22662_, p_22663_, 2);
      m_22590_(p_22662_, p_22661_, p_22663_, 2);
   }

   public static void m_22590_(LivingEntity p_22591_, Entity p_22592_, float p_22593_, int p_22594_) {
      WalkTarget walktarget = new WalkTarget(new EntityTracker(p_22592_, false), p_22593_, p_22594_);
      p_22591_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_22592_, true));
      p_22591_.m_6274_().m_21879_(MemoryModuleType.f_26370_, walktarget);
   }

   public static void m_22617_(LivingEntity p_22618_, BlockPos p_22619_, float p_22620_, int p_22621_) {
      WalkTarget walktarget = new WalkTarget(new BlockPosTracker(p_22619_), p_22620_, p_22621_);
      p_22618_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(p_22619_));
      p_22618_.m_6274_().m_21879_(MemoryModuleType.f_26370_, walktarget);
   }

   public static void m_22613_(LivingEntity p_22614_, ItemStack p_22615_, Vec3 p_22616_) {
      double d0 = p_22614_.m_20188_() - (double)0.3F;
      ItemEntity itementity = new ItemEntity(p_22614_.f_19853_, p_22614_.m_20185_(), d0, p_22614_.m_20189_(), p_22615_);
      float f = 0.3F;
      Vec3 vec3 = p_22616_.m_82546_(p_22614_.m_20182_());
      vec3 = vec3.m_82541_().m_82490_((double)0.3F);
      itementity.m_20256_(vec3);
      itementity.m_32060_();
      p_22614_.f_19853_.m_7967_(itementity);
   }

   public static SectionPos m_22581_(ServerLevel p_22582_, SectionPos p_22583_, int p_22584_) {
      int i = p_22582_.m_8828_(p_22583_);
      return SectionPos.m_123201_(p_22583_, p_22584_).filter((p_22580_) -> {
         return p_22582_.m_8828_(p_22580_) < i;
      }).min(Comparator.comparingInt(p_22582_::m_8828_)).orElse(p_22583_);
   }

   public static boolean m_22632_(Mob p_22633_, LivingEntity p_22634_, int p_22635_) {
      Item item = p_22633_.m_21205_().m_41720_();
      if (item instanceof ProjectileWeaponItem && p_22633_.m_5886_((ProjectileWeaponItem)item)) {
         int i = ((ProjectileWeaponItem)item).m_6615_() - p_22635_;
         return p_22633_.m_19950_(p_22634_, (double)i);
      } else {
         return m_147441_(p_22633_, p_22634_);
      }
   }

   public static boolean m_147441_(Mob p_147442_, LivingEntity p_147443_) {
      double d0 = p_147442_.m_20275_(p_147443_.m_20185_(), p_147443_.m_20186_(), p_147443_.m_20189_());
      return d0 <= p_147442_.m_142593_(p_147443_);
   }

   public static boolean m_22598_(LivingEntity p_22599_, LivingEntity p_22600_, double p_22601_) {
      Optional<LivingEntity> optional = p_22599_.m_6274_().m_21952_(MemoryModuleType.f_26372_);
      if (!optional.isPresent()) {
         return false;
      } else {
         double d0 = p_22599_.m_20238_(optional.get().m_20182_());
         double d1 = p_22599_.m_20238_(p_22600_.m_20182_());
         return d1 > d0 + p_22601_ * p_22601_;
      }
   }

   public static boolean m_22667_(LivingEntity p_22668_, LivingEntity p_22669_) {
      Brain<?> brain = p_22668_.m_6274_();
      return !brain.m_21874_(MemoryModuleType.f_148205_) ? false : brain.m_21952_(MemoryModuleType.f_148205_).get().contains(p_22669_);
   }

   public static LivingEntity m_22625_(LivingEntity p_22626_, Optional<LivingEntity> p_22627_, LivingEntity p_22628_) {
      return !p_22627_.isPresent() ? p_22628_ : m_22606_(p_22626_, p_22627_.get(), p_22628_);
   }

   public static LivingEntity m_22606_(LivingEntity p_22607_, LivingEntity p_22608_, LivingEntity p_22609_) {
      Vec3 vec3 = p_22608_.m_20182_();
      Vec3 vec31 = p_22609_.m_20182_();
      return p_22607_.m_20238_(vec3) < p_22607_.m_20238_(vec31) ? p_22608_ : p_22609_;
   }

   public static Optional<LivingEntity> m_22610_(LivingEntity p_22611_, MemoryModuleType<UUID> p_22612_) {
      Optional<UUID> optional = p_22611_.m_6274_().m_21952_(p_22612_);
      return optional.map((p_147440_) -> {
         return ((ServerLevel)p_22611_.f_19853_).m_8791_(p_147440_);
      }).map((p_147435_) -> {
         return p_147435_ instanceof LivingEntity ? (LivingEntity)p_147435_ : null;
      });
   }

   public static Stream<Villager> m_22650_(Villager p_22651_, Predicate<Villager> p_22652_) {
      return p_22651_.m_6274_().m_21952_(MemoryModuleType.f_148204_).map((p_147454_) -> {
         return p_147454_.stream().filter((p_147450_) -> {
            return p_147450_ instanceof Villager && p_147450_ != p_22651_;
         }).map((p_147437_) -> {
            return (Villager)p_147437_;
         }).filter(LivingEntity::m_6084_).filter(p_22652_);
      }).orElseGet(Stream::empty);
   }

   @Nullable
   public static Vec3 m_147444_(PathfinderMob p_147445_, int p_147446_, int p_147447_) {
      Vec3 vec3 = DefaultRandomPos.m_148403_(p_147445_, p_147446_, p_147447_);

      for(int i = 0; vec3 != null && !p_147445_.f_19853_.m_8055_(new BlockPos(vec3)).m_60647_(p_147445_.f_19853_, new BlockPos(vec3), PathComputationType.WATER) && i++ < 10; vec3 = DefaultRandomPos.m_148403_(p_147445_, p_147446_, p_147447_)) {
      }

      return vec3;
   }
}