package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonsterRoomFeature extends Feature<NoneFeatureConfiguration> {
   private static final Logger f_66340_ = LogManager.getLogger();
   private static final EntityType<?>[] f_66341_ = new EntityType[]{EntityType.f_20524_, EntityType.f_20501_, EntityType.f_20501_, EntityType.f_20479_};
   private static final BlockState f_66342_ = Blocks.f_50627_.m_49966_();

   public MonsterRoomFeature(Codec<NoneFeatureConfiguration> p_66345_) {
      super(p_66345_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160066_) {
      Predicate<BlockState> predicate = Feature.m_159757_(BlockTags.f_144287_.m_6979_());
      BlockPos blockpos = p_160066_.m_159777_();
      Random random = p_160066_.m_159776_();
      WorldGenLevel worldgenlevel = p_160066_.m_159774_();
      int i = 3;
      int j = random.nextInt(2) + 2;
      int k = -j - 1;
      int l = j + 1;
      int i1 = -1;
      int j1 = 4;
      int k1 = random.nextInt(2) + 2;
      int l1 = -k1 - 1;
      int i2 = k1 + 1;
      int j2 = 0;

      for(int k2 = k; k2 <= l; ++k2) {
         for(int l2 = -1; l2 <= 4; ++l2) {
            for(int i3 = l1; i3 <= i2; ++i3) {
               BlockPos blockpos1 = blockpos.m_142082_(k2, l2, i3);
               Material material = worldgenlevel.m_8055_(blockpos1).m_60767_();
               boolean flag = material.m_76333_();
               if (l2 == -1 && !flag) {
                  return false;
               }

               if (l2 == 4 && !flag) {
                  return false;
               }

               if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && worldgenlevel.m_46859_(blockpos1) && worldgenlevel.m_46859_(blockpos1.m_7494_())) {
                  ++j2;
               }
            }
         }
      }

      if (j2 >= 1 && j2 <= 5) {
         for(int k3 = k; k3 <= l; ++k3) {
            for(int i4 = 3; i4 >= -1; --i4) {
               for(int k4 = l1; k4 <= i2; ++k4) {
                  BlockPos blockpos2 = blockpos.m_142082_(k3, i4, k4);
                  BlockState blockstate = worldgenlevel.m_8055_(blockpos2);
                  if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2) {
                     if (!blockstate.m_60713_(Blocks.f_50087_) && !blockstate.m_60713_(Blocks.f_50085_)) {
                        this.m_159742_(worldgenlevel, blockpos2, f_66342_, predicate);
                     }
                  } else if (blockpos2.m_123342_() >= worldgenlevel.m_141937_() && !worldgenlevel.m_8055_(blockpos2.m_7495_()).m_60767_().m_76333_()) {
                     worldgenlevel.m_7731_(blockpos2, f_66342_, 2);
                  } else if (blockstate.m_60767_().m_76333_() && !blockstate.m_60713_(Blocks.f_50087_)) {
                     if (i4 == -1 && random.nextInt(4) != 0) {
                        this.m_159742_(worldgenlevel, blockpos2, Blocks.f_50079_.m_49966_(), predicate);
                     } else {
                        this.m_159742_(worldgenlevel, blockpos2, Blocks.f_50652_.m_49966_(), predicate);
                     }
                  }
               }
            }
         }

         for(int l3 = 0; l3 < 2; ++l3) {
            for(int j4 = 0; j4 < 3; ++j4) {
               int l4 = blockpos.m_123341_() + random.nextInt(j * 2 + 1) - j;
               int i5 = blockpos.m_123342_();
               int j5 = blockpos.m_123343_() + random.nextInt(k1 * 2 + 1) - k1;
               BlockPos blockpos3 = new BlockPos(l4, i5, j5);
               if (worldgenlevel.m_46859_(blockpos3)) {
                  int j3 = 0;

                  for(Direction direction : Direction.Plane.HORIZONTAL) {
                     if (worldgenlevel.m_8055_(blockpos3.m_142300_(direction)).m_60767_().m_76333_()) {
                        ++j3;
                     }
                  }

                  if (j3 == 1) {
                     this.m_159742_(worldgenlevel, blockpos3, StructurePiece.m_73407_(worldgenlevel, blockpos3, Blocks.f_50087_.m_49966_()), predicate);
                     RandomizableContainerBlockEntity.m_59620_(worldgenlevel, random, blockpos3, BuiltInLootTables.f_78742_);
                     break;
                  }
               }
            }
         }

         this.m_159742_(worldgenlevel, blockpos, Blocks.f_50085_.m_49966_(), predicate);
         BlockEntity blockentity = worldgenlevel.m_7702_(blockpos);
         if (blockentity instanceof SpawnerBlockEntity) {
            ((SpawnerBlockEntity)blockentity).m_59801_().m_45462_(this.m_66358_(random));
         } else {
            f_66340_.error("Failed to fetch mob spawner entity at ({}, {}, {})", blockpos.m_123341_(), blockpos.m_123342_(), blockpos.m_123343_());
         }

         return true;
      } else {
         return false;
      }
   }

   private EntityType<?> m_66358_(Random p_66359_) {
      return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(p_66359_);
   }
}
