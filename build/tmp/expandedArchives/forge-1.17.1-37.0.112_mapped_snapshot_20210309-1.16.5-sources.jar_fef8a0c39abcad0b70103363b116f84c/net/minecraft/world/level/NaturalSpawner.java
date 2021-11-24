package net.minecraft.world.level;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.NearestNeighborBiomeZoomer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: ForgeEventFactory.getPotentialSpawns, ForgeHooks.canEntitySpawn
public final class NaturalSpawner {
   private static final Logger f_46977_ = LogManager.getLogger();
   private static final int f_151589_ = 24;
   public static final int f_151587_ = 8;
   public static final int f_151588_ = 128;
   static final int f_46978_ = (int)Math.pow(17.0D, 2.0D);
   private static final MobCategory[] f_46979_ = Stream.of(MobCategory.values()).filter((p_47037_) -> {
      return p_47037_ != MobCategory.MISC;
   }).toArray((p_46983_) -> {
      return new MobCategory[p_46983_];
   });

   private NaturalSpawner() {
   }

   public static NaturalSpawner.SpawnState m_46984_(int p_46985_, Iterable<Entity> p_46986_, NaturalSpawner.ChunkGetter p_46987_) {
      PotentialCalculator potentialcalculator = new PotentialCalculator();
      Object2IntOpenHashMap<MobCategory> object2intopenhashmap = new Object2IntOpenHashMap<>();
      Iterator iterator = p_46986_.iterator();

      while(true) {
         Entity entity;
         Mob mob;
         do {
            if (!iterator.hasNext()) {
               return new NaturalSpawner.SpawnState(p_46985_, object2intopenhashmap, potentialcalculator);
            }

            entity = (Entity)iterator.next();
            if (!(entity instanceof Mob)) {
               break;
            }

            mob = (Mob)entity;
         } while(mob.m_21532_() || mob.m_8023_());

         MobCategory mobcategory = entity.getClassification(true);
         if (mobcategory != MobCategory.MISC) {
            Entity finalEntity = entity;
            BlockPos blockpos = entity.m_142538_();
            long i = ChunkPos.m_45589_(SectionPos.m_123171_(blockpos.m_123341_()), SectionPos.m_123171_(blockpos.m_123343_()));
            p_46987_.m_47103_(i, (p_47091_) -> {
               MobSpawnSettings.MobSpawnCost mobspawnsettings$mobspawncost = m_47095_(blockpos, p_47091_).m_47518_().m_48345_(finalEntity.m_6095_());
               if (mobspawnsettings$mobspawncost != null) {
                  potentialcalculator.m_47192_(finalEntity.m_142538_(), mobspawnsettings$mobspawncost.m_48400_());
               }

               object2intopenhashmap.addTo(mobcategory, 1);
            });
         }
      }
   }

   static Biome m_47095_(BlockPos p_47096_, ChunkAccess p_47097_) {
      return NearestNeighborBiomeZoomer.INSTANCE.m_7782_(0L, p_47096_.m_123341_(), p_47096_.m_123342_(), p_47096_.m_123343_(), p_47097_.m_6221_());
   }

   public static void m_47029_(ServerLevel p_47030_, LevelChunk p_47031_, NaturalSpawner.SpawnState p_47032_, boolean p_47033_, boolean p_47034_, boolean p_47035_) {
      p_47030_.m_46473_().m_6180_("spawner");

      for(MobCategory mobcategory : f_46979_) {
         if ((p_47033_ || !mobcategory.m_21609_()) && (p_47034_ || mobcategory.m_21609_()) && (p_47035_ || !mobcategory.m_21610_()) && p_47032_.m_47134_(mobcategory)) {
            m_47045_(mobcategory, p_47030_, p_47031_, p_47032_::m_47127_, p_47032_::m_47131_);
         }
      }

      p_47030_.m_46473_().m_7238_();
   }

   public static void m_47045_(MobCategory p_47046_, ServerLevel p_47047_, LevelChunk p_47048_, NaturalSpawner.SpawnPredicate p_47049_, NaturalSpawner.AfterSpawnCallback p_47050_) {
      BlockPos blockpos = m_47062_(p_47047_, p_47048_);
      if (blockpos.m_123342_() >= p_47047_.m_141937_() + 1) {
         m_47038_(p_47046_, p_47047_, p_47048_, blockpos, p_47049_, p_47050_);
      }
   }

   @VisibleForDebug
   public static void m_151612_(MobCategory p_151613_, ServerLevel p_151614_, BlockPos p_151615_) {
      m_47038_(p_151613_, p_151614_, p_151614_.m_46865_(p_151615_), p_151615_, (p_151606_, p_151607_, p_151608_) -> {
         return true;
      }, (p_151610_, p_151611_) -> {
      });
   }

   public static void m_47038_(MobCategory p_47039_, ServerLevel p_47040_, ChunkAccess p_47041_, BlockPos p_47042_, NaturalSpawner.SpawnPredicate p_47043_, NaturalSpawner.AfterSpawnCallback p_47044_) {
      StructureFeatureManager structurefeaturemanager = p_47040_.m_8595_();
      ChunkGenerator chunkgenerator = p_47040_.m_7726_().m_8481_();
      int i = p_47042_.m_123342_();
      BlockState blockstate = p_47041_.m_8055_(p_47042_);
      if (!blockstate.m_60796_(p_47041_, p_47042_)) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         int j = 0;

         for(int k = 0; k < 3; ++k) {
            int l = p_47042_.m_123341_();
            int i1 = p_47042_.m_123343_();
            int j1 = 6;
            MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = null;
            SpawnGroupData spawngroupdata = null;
            int k1 = Mth.m_14167_(p_47040_.f_46441_.nextFloat() * 4.0F);
            int l1 = 0;

            for(int i2 = 0; i2 < k1; ++i2) {
               l += p_47040_.f_46441_.nextInt(6) - p_47040_.f_46441_.nextInt(6);
               i1 += p_47040_.f_46441_.nextInt(6) - p_47040_.f_46441_.nextInt(6);
               blockpos$mutableblockpos.m_122178_(l, i, i1);
               double d0 = (double)l + 0.5D;
               double d1 = (double)i1 + 0.5D;
               Player player = p_47040_.m_45924_(d0, (double)i, d1, -1.0D, false);
               if (player != null) {
                  double d2 = player.m_20275_(d0, (double)i, d1);
                  if (m_47024_(p_47040_, p_47041_, blockpos$mutableblockpos, d2)) {
                     if (mobspawnsettings$spawnerdata == null) {
                        Optional<MobSpawnSettings.SpawnerData> optional = m_151598_(p_47040_, structurefeaturemanager, chunkgenerator, p_47039_, p_47040_.f_46441_, blockpos$mutableblockpos);
                        if (!optional.isPresent()) {
                           break;
                        }

                        mobspawnsettings$spawnerdata = optional.get();
                        k1 = mobspawnsettings$spawnerdata.f_48405_ + p_47040_.f_46441_.nextInt(1 + mobspawnsettings$spawnerdata.f_48406_ - mobspawnsettings$spawnerdata.f_48405_);
                     }

                     if (m_46995_(p_47040_, p_47039_, structurefeaturemanager, chunkgenerator, mobspawnsettings$spawnerdata, blockpos$mutableblockpos, d2) && p_47043_.m_47106_(mobspawnsettings$spawnerdata.f_48404_, blockpos$mutableblockpos, p_47041_)) {
                        Mob mob = m_46988_(p_47040_, mobspawnsettings$spawnerdata.f_48404_);
                        if (mob == null) {
                           return;
                        }

                        mob.m_7678_(d0, (double)i, d1, p_47040_.f_46441_.nextFloat() * 360.0F, 0.0F);
                        int canSpawn = net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, p_47040_, d0, i, d1, null, MobSpawnType.NATURAL);
                        if (canSpawn != -1 && (canSpawn == 1 || m_46991_(p_47040_, mob, d2))) {
                           if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(mob, p_47040_, (float)d0, (float)i, (float)d1, null, MobSpawnType.NATURAL))
                           spawngroupdata = mob.m_6518_(p_47040_, p_47040_.m_6436_(mob.m_142538_()), MobSpawnType.NATURAL, spawngroupdata, (CompoundTag)null);
                           ++j;
                           ++l1;
                           p_47040_.m_47205_(mob);
                           p_47044_.m_47100_(mob, p_47041_);
                           if (j >= net.minecraftforge.event.ForgeEventFactory.getMaxSpawnPackSize(mob)) {
                              return;
                           }

                           if (mob.m_7296_(l1)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private static boolean m_47024_(ServerLevel p_47025_, ChunkAccess p_47026_, BlockPos.MutableBlockPos p_47027_, double p_47028_) {
      if (p_47028_ <= 576.0D) {
         return false;
      } else if (p_47025_.m_8900_().m_123306_(new Vec3((double)p_47027_.m_123341_() + 0.5D, (double)p_47027_.m_123342_(), (double)p_47027_.m_123343_() + 0.5D), 24.0D)) {
         return false;
      } else {
         return Objects.equals(new ChunkPos(p_47027_), p_47026_.m_7697_()) || p_47025_.m_143340_(p_47027_);
      }
   }

   private static boolean m_46995_(ServerLevel p_46996_, MobCategory p_46997_, StructureFeatureManager p_46998_, ChunkGenerator p_46999_, MobSpawnSettings.SpawnerData p_47000_, BlockPos.MutableBlockPos p_47001_, double p_47002_) {
      EntityType<?> entitytype = p_47000_.f_48404_;
      if (entitytype.m_20674_() == MobCategory.MISC) {
         return false;
      } else if (!entitytype.m_20673_() && p_47002_ > (double)(entitytype.m_20674_().m_21611_() * entitytype.m_20674_().m_21611_())) {
         return false;
      } else if (entitytype.m_20654_() && m_47003_(p_46996_, p_46998_, p_46999_, p_46997_, p_47000_, p_47001_)) {
         SpawnPlacements.Type spawnplacements$type = SpawnPlacements.m_21752_(entitytype);
         if (!m_47051_(spawnplacements$type, p_46996_, p_47001_, entitytype)) {
            return false;
         } else if (!SpawnPlacements.m_21759_(entitytype, p_46996_, MobSpawnType.NATURAL, p_47001_, p_46996_.f_46441_)) {
            return false;
         } else {
            return p_46996_.m_45772_(entitytype.m_20585_((double)p_47001_.m_123341_() + 0.5D, (double)p_47001_.m_123342_(), (double)p_47001_.m_123343_() + 0.5D));
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static Mob m_46988_(ServerLevel p_46989_, EntityType<?> p_46990_) {
      try {
         Entity entity = p_46990_.m_20615_(p_46989_);
         if (!(entity instanceof Mob)) {
            throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.f_122826_.m_7981_(p_46990_));
         } else {
            return (Mob)entity;
         }
      } catch (Exception exception) {
         f_46977_.warn("Failed to create mob", (Throwable)exception);
         return null;
      }
   }

   private static boolean m_46991_(ServerLevel p_46992_, Mob p_46993_, double p_46994_) {
      if (p_46994_ > (double)(p_46993_.m_6095_().m_20674_().m_21611_() * p_46993_.m_6095_().m_20674_().m_21611_()) && p_46993_.m_6785_(p_46994_)) {
         return false;
      } else {
         return p_46993_.m_5545_(p_46992_, MobSpawnType.NATURAL) && p_46993_.m_6914_(p_46992_);
      }
   }

   private static Optional<MobSpawnSettings.SpawnerData> m_151598_(ServerLevel p_151599_, StructureFeatureManager p_151600_, ChunkGenerator p_151601_, MobCategory p_151602_, Random p_151603_, BlockPos p_151604_) {
      Biome biome = p_151599_.m_46857_(p_151604_);
      return p_151602_ == MobCategory.WATER_AMBIENT && biome.m_47567_() == Biome.BiomeCategory.RIVER && p_151603_.nextFloat() < 0.98F ? Optional.empty() : m_151591_(p_151599_, p_151600_, p_151601_, p_151602_, p_151604_, biome).m_146335_(p_151603_);
   }

   private static boolean m_47003_(ServerLevel p_47004_, StructureFeatureManager p_47005_, ChunkGenerator p_47006_, MobCategory p_47007_, MobSpawnSettings.SpawnerData p_47008_, BlockPos p_47009_) {
      return m_151591_(p_47004_, p_47005_, p_47006_, p_47007_, p_47009_, (Biome)null).m_146338_().contains(p_47008_);
   }

   private static WeightedRandomList<MobSpawnSettings.SpawnerData> m_151591_(ServerLevel p_151592_, StructureFeatureManager p_151593_, ChunkGenerator p_151594_, MobCategory p_151595_, BlockPos p_151596_, @Nullable Biome p_151597_) {
      return p_151595_ == MobCategory.MONSTER && p_151592_.m_8055_(p_151596_.m_7495_()).m_60713_(Blocks.f_50197_) && p_151593_.m_47285_(p_151596_, false, StructureFeature.f_67025_).m_73603_() ? StructureFeature.f_67025_.m_142494_() : p_151594_.m_142184_(p_151597_ != null ? p_151597_ : p_151592_.m_46857_(p_151596_), p_151593_, p_151595_, p_151596_);
   }

   private static BlockPos m_47062_(Level p_47063_, LevelChunk p_47064_) {
      ChunkPos chunkpos = p_47064_.m_7697_();
      int i = chunkpos.m_45604_() + p_47063_.f_46441_.nextInt(16);
      int j = chunkpos.m_45605_() + p_47063_.f_46441_.nextInt(16);
      int k = p_47064_.m_5885_(Heightmap.Types.WORLD_SURFACE, i, j) + 1;
      int l = Mth.m_144928_(p_47063_.f_46441_, p_47063_.m_141937_(), k);
      return new BlockPos(i, l, j);
   }

   public static boolean m_47056_(BlockGetter p_47057_, BlockPos p_47058_, BlockState p_47059_, FluidState p_47060_, EntityType<?> p_47061_) {
      if (p_47059_.m_60838_(p_47057_, p_47058_)) {
         return false;
      } else if (p_47059_.m_60803_()) {
         return false;
      } else if (!p_47060_.m_76178_()) {
         return false;
      } else if (p_47059_.m_60620_(BlockTags.f_13054_)) {
         return false;
      } else {
         return !p_47061_.m_20630_(p_47059_);
      }
   }

   public static boolean m_47051_(SpawnPlacements.Type p_47052_, LevelReader p_47053_, BlockPos p_47054_, @Nullable EntityType<?> p_47055_) {
      if (p_47052_ == SpawnPlacements.Type.NO_RESTRICTIONS) {
         return true;
      } else if (p_47055_ != null && p_47053_.m_6857_().m_61937_(p_47054_)) {
         return p_47052_.canSpawnAt(p_47053_, p_47054_, p_47055_);
      }
      return false;
   }

   public static boolean canSpawnAtBody(SpawnPlacements.Type p_47052_, LevelReader p_47053_, BlockPos p_47054_, @Nullable EntityType<?> p_47055_) {
      {
         BlockState blockstate = p_47053_.m_8055_(p_47054_);
         FluidState fluidstate = p_47053_.m_6425_(p_47054_);
         BlockPos blockpos = p_47054_.m_7494_();
         BlockPos blockpos1 = p_47054_.m_7495_();
         switch(p_47052_) {
         case IN_WATER:
            return fluidstate.m_76153_(FluidTags.f_13131_) && p_47053_.m_6425_(blockpos1).m_76153_(FluidTags.f_13131_) && !p_47053_.m_8055_(blockpos).m_60796_(p_47053_, blockpos);
         case IN_LAVA:
            return fluidstate.m_76153_(FluidTags.f_13132_);
         case ON_GROUND:
         default:
            BlockState blockstate1 = p_47053_.m_8055_(blockpos1);
            if (!blockstate1.canCreatureSpawn(p_47053_, blockpos1, p_47052_, p_47055_)) {
               return false;
            } else {
               return m_47056_(p_47053_, p_47054_, blockstate, fluidstate, p_47055_) && m_47056_(p_47053_, blockpos, p_47053_.m_8055_(blockpos), p_47053_.m_6425_(blockpos), p_47055_);
            }
         }
      }
   }

   public static void m_151616_(ServerLevelAccessor p_151617_, Biome p_151618_, ChunkPos p_151619_, Random p_151620_) {
      MobSpawnSettings mobspawnsettings = p_151618_.m_47518_();
      WeightedRandomList<MobSpawnSettings.SpawnerData> weightedrandomlist = mobspawnsettings.m_151798_(MobCategory.CREATURE);
      if (!weightedrandomlist.m_146337_()) {
         int i = p_151619_.m_45604_();
         int j = p_151619_.m_45605_();

         while(p_151620_.nextFloat() < mobspawnsettings.m_48344_()) {
            Optional<MobSpawnSettings.SpawnerData> optional = weightedrandomlist.m_146335_(p_151620_);
            if (optional.isPresent()) {
               MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = optional.get();
               int k = mobspawnsettings$spawnerdata.f_48405_ + p_151620_.nextInt(1 + mobspawnsettings$spawnerdata.f_48406_ - mobspawnsettings$spawnerdata.f_48405_);
               SpawnGroupData spawngroupdata = null;
               int l = i + p_151620_.nextInt(16);
               int i1 = j + p_151620_.nextInt(16);
               int j1 = l;
               int k1 = i1;

               for(int l1 = 0; l1 < k; ++l1) {
                  boolean flag = false;

                  for(int i2 = 0; !flag && i2 < 4; ++i2) {
                     BlockPos blockpos = m_47065_(p_151617_, mobspawnsettings$spawnerdata.f_48404_, l, i1);
                     if (mobspawnsettings$spawnerdata.f_48404_.m_20654_() && m_47051_(SpawnPlacements.m_21752_(mobspawnsettings$spawnerdata.f_48404_), p_151617_, blockpos, mobspawnsettings$spawnerdata.f_48404_)) {
                        float f = mobspawnsettings$spawnerdata.f_48404_.m_20678_();
                        double d0 = Mth.m_14008_((double)l, (double)i + (double)f, (double)i + 16.0D - (double)f);
                        double d1 = Mth.m_14008_((double)i1, (double)j + (double)f, (double)j + 16.0D - (double)f);
                        if (!p_151617_.m_45772_(mobspawnsettings$spawnerdata.f_48404_.m_20585_(d0, (double)blockpos.m_123342_(), d1)) || !SpawnPlacements.m_21759_(mobspawnsettings$spawnerdata.f_48404_, p_151617_, MobSpawnType.CHUNK_GENERATION, new BlockPos(d0, (double)blockpos.m_123342_(), d1), p_151617_.m_5822_())) {
                           continue;
                        }

                        Entity entity;
                        try {
                           entity = mobspawnsettings$spawnerdata.f_48404_.m_20615_(p_151617_.m_6018_());
                        } catch (Exception exception) {
                           f_46977_.warn("Failed to create mob", (Throwable)exception);
                           continue;
                        }

                        entity.m_7678_(d0, (double)blockpos.m_123342_(), d1, p_151620_.nextFloat() * 360.0F, 0.0F);
                        if (entity instanceof Mob) {
                           Mob mob = (Mob)entity;
                           if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, p_151617_, d0, blockpos.m_123342_(), d1, null, MobSpawnType.CHUNK_GENERATION) == -1) continue;
                           if (mob.m_5545_(p_151617_, MobSpawnType.CHUNK_GENERATION) && mob.m_6914_(p_151617_)) {
                              spawngroupdata = mob.m_6518_(p_151617_, p_151617_.m_6436_(mob.m_142538_()), MobSpawnType.CHUNK_GENERATION, spawngroupdata, (CompoundTag)null);
                              p_151617_.m_47205_(mob);
                              flag = true;
                           }
                        }
                     }

                     l += p_151620_.nextInt(5) - p_151620_.nextInt(5);

                     for(i1 += p_151620_.nextInt(5) - p_151620_.nextInt(5); l < i || l >= i + 16 || i1 < j || i1 >= j + 16; i1 = k1 + p_151620_.nextInt(5) - p_151620_.nextInt(5)) {
                        l = j1 + p_151620_.nextInt(5) - p_151620_.nextInt(5);
                     }
                  }
               }
            }
         }

      }
   }

   private static BlockPos m_47065_(LevelReader p_47066_, EntityType<?> p_47067_, int p_47068_, int p_47069_) {
      int i = p_47066_.m_6924_(SpawnPlacements.m_21765_(p_47067_), p_47068_, p_47069_);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_47068_, i, p_47069_);
      if (p_47066_.m_6042_().m_63946_()) {
         do {
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         } while(!p_47066_.m_8055_(blockpos$mutableblockpos).m_60795_());

         do {
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         } while(p_47066_.m_8055_(blockpos$mutableblockpos).m_60795_() && blockpos$mutableblockpos.m_123342_() > p_47066_.m_141937_());
      }

      if (SpawnPlacements.m_21752_(p_47067_) == SpawnPlacements.Type.ON_GROUND) {
         BlockPos blockpos = blockpos$mutableblockpos.m_7495_();
         if (p_47066_.m_8055_(blockpos).m_60647_(p_47066_, blockpos, PathComputationType.LAND)) {
            return blockpos;
         }
      }

      return blockpos$mutableblockpos.m_7949_();
   }

   @FunctionalInterface
   public interface AfterSpawnCallback {
      void m_47100_(Mob p_47101_, ChunkAccess p_47102_);
   }

   @FunctionalInterface
   public interface ChunkGetter {
      void m_47103_(long p_47104_, Consumer<LevelChunk> p_47105_);
   }

   @FunctionalInterface
   public interface SpawnPredicate {
      boolean m_47106_(EntityType<?> p_47107_, BlockPos p_47108_, ChunkAccess p_47109_);
   }

   public static class SpawnState {
      private final int f_47110_;
      private final Object2IntOpenHashMap<MobCategory> f_47111_;
      private final PotentialCalculator f_47112_;
      private final Object2IntMap<MobCategory> f_47113_;
      @Nullable
      private BlockPos f_47114_;
      @Nullable
      private EntityType<?> f_47115_;
      private double f_47116_;

      SpawnState(int p_47118_, Object2IntOpenHashMap<MobCategory> p_47119_, PotentialCalculator p_47120_) {
         this.f_47110_ = p_47118_;
         this.f_47111_ = p_47119_;
         this.f_47112_ = p_47120_;
         this.f_47113_ = Object2IntMaps.unmodifiable(p_47119_);
      }

      private boolean m_47127_(EntityType<?> p_47128_, BlockPos p_47129_, ChunkAccess p_47130_) {
         this.f_47114_ = p_47129_;
         this.f_47115_ = p_47128_;
         MobSpawnSettings.MobSpawnCost mobspawnsettings$mobspawncost = NaturalSpawner.m_47095_(p_47129_, p_47130_).m_47518_().m_48345_(p_47128_);
         if (mobspawnsettings$mobspawncost == null) {
            this.f_47116_ = 0.0D;
            return true;
         } else {
            double d0 = mobspawnsettings$mobspawncost.m_48400_();
            this.f_47116_ = d0;
            double d1 = this.f_47112_.m_47195_(p_47129_, d0);
            return d1 <= mobspawnsettings$mobspawncost.m_48395_();
         }
      }

      private void m_47131_(Mob p_47132_, ChunkAccess p_47133_) {
         EntityType<?> entitytype = p_47132_.m_6095_();
         BlockPos blockpos = p_47132_.m_142538_();
         double d0;
         if (blockpos.equals(this.f_47114_) && entitytype == this.f_47115_) {
            d0 = this.f_47116_;
         } else {
            MobSpawnSettings.MobSpawnCost mobspawnsettings$mobspawncost = NaturalSpawner.m_47095_(blockpos, p_47133_).m_47518_().m_48345_(entitytype);
            if (mobspawnsettings$mobspawncost != null) {
               d0 = mobspawnsettings$mobspawncost.m_48400_();
            } else {
               d0 = 0.0D;
            }
         }

         this.f_47112_.m_47192_(blockpos, d0);
         this.f_47111_.addTo(entitytype.m_20674_(), 1);
      }

      public int m_47126_() {
         return this.f_47110_;
      }

      public Object2IntMap<MobCategory> m_47148_() {
         return this.f_47113_;
      }

      boolean m_47134_(MobCategory p_47135_) {
         int i = p_47135_.m_21608_() * this.f_47110_ / NaturalSpawner.f_46978_;
         return this.f_47111_.getInt(p_47135_) < i;
      }
   }
}
