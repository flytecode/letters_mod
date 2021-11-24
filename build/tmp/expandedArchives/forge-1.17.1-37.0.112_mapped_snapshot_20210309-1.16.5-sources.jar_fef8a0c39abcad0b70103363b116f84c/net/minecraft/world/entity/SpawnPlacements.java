package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnPlacements {
   private static final Map<EntityType<?>, SpawnPlacements.Data> f_21750_ = Maps.newHashMap();

   public static <T extends Mob> void m_21754_(EntityType<T> p_21755_, SpawnPlacements.Type p_21756_, Heightmap.Types p_21757_, SpawnPlacements.SpawnPredicate<T> p_21758_) {
      SpawnPlacements.Data spawnplacements$data = f_21750_.put(p_21755_, new SpawnPlacements.Data(p_21757_, p_21756_, p_21758_));
      if (spawnplacements$data != null) {
         throw new IllegalStateException("Duplicate registration for type " + Registry.f_122826_.m_7981_(p_21755_));
      }
   }

   public static SpawnPlacements.Type m_21752_(EntityType<?> p_21753_) {
      SpawnPlacements.Data spawnplacements$data = f_21750_.get(p_21753_);
      return spawnplacements$data == null ? SpawnPlacements.Type.NO_RESTRICTIONS : spawnplacements$data.f_21768_;
   }

   public static Heightmap.Types m_21765_(@Nullable EntityType<?> p_21766_) {
      SpawnPlacements.Data spawnplacements$data = f_21750_.get(p_21766_);
      return spawnplacements$data == null ? Heightmap.Types.MOTION_BLOCKING_NO_LEAVES : spawnplacements$data.f_21767_;
   }

   public static <T extends Entity> boolean m_21759_(EntityType<T> p_21760_, ServerLevelAccessor p_21761_, MobSpawnType p_21762_, BlockPos p_21763_, Random p_21764_) {
      SpawnPlacements.Data spawnplacements$data = f_21750_.get(p_21760_);
      return spawnplacements$data == null || spawnplacements$data.f_21769_.m_21780_((EntityType)p_21760_, p_21761_, p_21762_, p_21763_, p_21764_);
   }

   static {
      m_21754_(EntityType.f_147039_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_149076_);
      m_21754_(EntityType.f_20556_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::m_27467_);
      m_21754_(EntityType.f_20559_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dolphin::m_28345_);
      m_21754_(EntityType.f_20562_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Drowned::m_32349_);
      m_21754_(EntityType.f_20455_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::m_32836_);
      m_21754_(EntityType.f_20516_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::m_27467_);
      m_21754_(EntityType.f_20519_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::m_27467_);
      m_21754_(EntityType.f_20480_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Squid::m_29968_);
      m_21754_(EntityType.f_20489_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractFish::m_27467_);
      m_21754_(EntityType.f_20549_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bat::m_27433_);
      m_21754_(EntityType.f_20551_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33023_);
      m_21754_(EntityType.f_20554_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20555_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20557_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20558_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20560_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20566_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20567_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Endermite::m_32597_);
      m_21754_(EntityType.f_20565_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20453_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ghast::m_32734_);
      m_21754_(EntityType.f_20454_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_147034_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::m_149076_);
      m_21754_(EntityType.f_147035_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20457_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20458_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Husk::m_32895_);
      m_21754_(EntityType.f_20460_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20466_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20468_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaCube::m_32980_);
      m_21754_(EntityType.f_20504_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomCow::m_28948_);
      m_21754_(EntityType.f_20503_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20505_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Ocelot::m_29025_);
      m_21754_(EntityType.f_20508_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Parrot::m_29423_);
      m_21754_(EntityType.f_20510_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20456_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hoglin::m_34533_);
      m_21754_(EntityType.f_20511_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Piglin::m_34733_);
      m_21754_(EntityType.f_20513_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::m_33056_);
      m_21754_(EntityType.f_20514_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PolarBear::m_29549_);
      m_21754_(EntityType.f_20517_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rabbit::m_29698_);
      m_21754_(EntityType.f_20520_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20523_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Silverfish::m_33533_);
      m_21754_(EntityType.f_20524_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20525_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20526_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slime::m_33620_);
      m_21754_(EntityType.f_20528_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20479_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20481_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stray::m_33839_);
      m_21754_(EntityType.f_20482_, SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Strider::m_33921_);
      m_21754_(EntityType.f_20490_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Turtle::m_30178_);
      m_21754_(EntityType.f_20492_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20495_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20496_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20497_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20499_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20501_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20502_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20531_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglin::m_34449_);
      m_21754_(EntityType.f_20530_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20553_, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20563_, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::m_32836_);
      m_21754_(EntityType.f_20568_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20452_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20459_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20507_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20509_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20518_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20521_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
      m_21754_(EntityType.f_20488_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::m_27577_);
      m_21754_(EntityType.f_20491_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20493_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::m_33017_);
      m_21754_(EntityType.f_20494_, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_21400_);
   }

   static class Data {
      final Heightmap.Types f_21767_;
      final SpawnPlacements.Type f_21768_;
      final SpawnPlacements.SpawnPredicate<?> f_21769_;

      public Data(Heightmap.Types p_21771_, SpawnPlacements.Type p_21772_, SpawnPlacements.SpawnPredicate<?> p_21773_) {
         this.f_21767_ = p_21771_;
         this.f_21768_ = p_21772_;
         this.f_21769_ = p_21773_;
      }
   }

   @FunctionalInterface
   public interface SpawnPredicate<T extends Entity> {
      boolean m_21780_(EntityType<T> p_21781_, ServerLevelAccessor p_21782_, MobSpawnType p_21783_, BlockPos p_21784_, Random p_21785_);
   }

   public static enum Type implements net.minecraftforge.common.IExtensibleEnum {
      ON_GROUND,
      IN_WATER,
      NO_RESTRICTIONS,
      IN_LAVA;

      public static Type create(String name, net.minecraftforge.common.util.TriPredicate<net.minecraft.world.level.LevelReader, BlockPos, EntityType<? extends Mob>> predicate) {
          throw new IllegalStateException("Enum not extended");
      }

      private net.minecraftforge.common.util.TriPredicate<net.minecraft.world.level.LevelReader, BlockPos, EntityType<?>> predicate;
      private Type() { this(null); }
      private Type(net.minecraftforge.common.util.TriPredicate<net.minecraft.world.level.LevelReader, BlockPos, EntityType<?>> predicate) {
          this.predicate = predicate;
      }

      public boolean canSpawnAt(net.minecraft.world.level.LevelReader world, BlockPos pos, EntityType<?> type) {
          if (this == NO_RESTRICTIONS) return true;
          if (predicate == null) return net.minecraft.world.level.NaturalSpawner.canSpawnAtBody(this, world, pos, type);
          return predicate.test(world, pos, type);
      }
   }
}
