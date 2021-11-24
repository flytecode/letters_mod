package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;

public class GeodeFeature extends Feature<GeodeConfiguration> {
   private static final Direction[] f_159831_ = Direction.values();

   public GeodeFeature(Codec<GeodeConfiguration> p_159834_) {
      super(p_159834_);
   }

   public boolean m_142674_(FeaturePlaceContext<GeodeConfiguration> p_159836_) {
      GeodeConfiguration geodeconfiguration = p_159836_.m_159778_();
      Random random = p_159836_.m_159776_();
      BlockPos blockpos = p_159836_.m_159777_();
      WorldGenLevel worldgenlevel = p_159836_.m_159774_();
      int i = geodeconfiguration.f_160822_;
      int j = geodeconfiguration.f_160823_;
      List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
      int k = geodeconfiguration.f_160820_.m_142270_(random);
      WorldgenRandom worldgenrandom = new WorldgenRandom(worldgenlevel.m_7328_());
      NormalNoise normalnoise = NormalNoise.m_164354_(worldgenrandom, -4, 1.0D);
      List<BlockPos> list1 = Lists.newLinkedList();
      double d0 = (double)k / (double)geodeconfiguration.f_160819_.m_142737_();
      GeodeLayerSettings geodelayersettings = geodeconfiguration.f_160814_;
      GeodeBlockSettings geodeblocksettings = geodeconfiguration.f_160813_;
      GeodeCrackSettings geodecracksettings = geodeconfiguration.f_160815_;
      double d1 = 1.0D / Math.sqrt(geodelayersettings.f_158342_);
      double d2 = 1.0D / Math.sqrt(geodelayersettings.f_158343_ + d0);
      double d3 = 1.0D / Math.sqrt(geodelayersettings.f_158344_ + d0);
      double d4 = 1.0D / Math.sqrt(geodelayersettings.f_158345_ + d0);
      double d5 = 1.0D / Math.sqrt(geodecracksettings.f_158326_ + random.nextDouble() / 2.0D + (k > 3 ? d0 : 0.0D));
      boolean flag = (double)random.nextFloat() < geodecracksettings.f_158325_;
      int l = 0;

      for(int i1 = 0; i1 < k; ++i1) {
         int j1 = geodeconfiguration.f_160819_.m_142270_(random);
         int k1 = geodeconfiguration.f_160819_.m_142270_(random);
         int l1 = geodeconfiguration.f_160819_.m_142270_(random);
         BlockPos blockpos1 = blockpos.m_142082_(j1, k1, l1);
         BlockState blockstate = worldgenlevel.m_8055_(blockpos1);
         if (blockstate.m_60795_() || blockstate.m_60620_(BlockTags.f_144289_)) {
            ++l;
            if (l > geodeconfiguration.f_160825_) {
               return false;
            }
         }

         list.add(Pair.of(blockpos1, geodeconfiguration.f_160821_.m_142270_(random)));
      }

      if (flag) {
         int i2 = random.nextInt(4);
         int j2 = k * 2 + 1;
         if (i2 == 0) {
            list1.add(blockpos.m_142082_(j2, 7, 0));
            list1.add(blockpos.m_142082_(j2, 5, 0));
            list1.add(blockpos.m_142082_(j2, 1, 0));
         } else if (i2 == 1) {
            list1.add(blockpos.m_142082_(0, 7, j2));
            list1.add(blockpos.m_142082_(0, 5, j2));
            list1.add(blockpos.m_142082_(0, 1, j2));
         } else if (i2 == 2) {
            list1.add(blockpos.m_142082_(j2, 7, j2));
            list1.add(blockpos.m_142082_(j2, 5, j2));
            list1.add(blockpos.m_142082_(j2, 1, j2));
         } else {
            list1.add(blockpos.m_142082_(0, 7, 0));
            list1.add(blockpos.m_142082_(0, 5, 0));
            list1.add(blockpos.m_142082_(0, 1, 0));
         }
      }

      List<BlockPos> list2 = Lists.newArrayList();
      Predicate<BlockState> predicate = m_159757_(geodeconfiguration.f_160813_.f_158293_);

      for(BlockPos blockpos3 : BlockPos.m_121940_(blockpos.m_142082_(i, i, i), blockpos.m_142082_(j, j, j))) {
         double d8 = normalnoise.m_75380_((double)blockpos3.m_123341_(), (double)blockpos3.m_123342_(), (double)blockpos3.m_123343_()) * geodeconfiguration.f_160824_;
         double d6 = 0.0D;
         double d7 = 0.0D;

         for(Pair<BlockPos, Integer> pair : list) {
            d6 += Mth.m_14193_(blockpos3.m_123331_(pair.getFirst()) + (double)pair.getSecond().intValue()) + d8;
         }

         for(BlockPos blockpos6 : list1) {
            d7 += Mth.m_14193_(blockpos3.m_123331_(blockpos6) + (double)geodecracksettings.f_158327_) + d8;
         }

         if (!(d6 < d4)) {
            if (flag && d7 >= d5 && d6 < d1) {
               this.m_159742_(worldgenlevel, blockpos3, Blocks.f_50016_.m_49966_(), predicate);

               for(Direction direction1 : f_159831_) {
                  BlockPos blockpos2 = blockpos3.m_142300_(direction1);
                  FluidState fluidstate = worldgenlevel.m_6425_(blockpos2);
                  if (!fluidstate.m_76178_()) {
                     worldgenlevel.m_6217_().m_5945_(blockpos2, fluidstate.m_76152_(), 0);
                  }
               }
            } else if (d6 >= d1) {
               this.m_159742_(worldgenlevel, blockpos3, geodeblocksettings.f_158287_.m_7112_(random, blockpos3), predicate);
            } else if (d6 >= d2) {
               boolean flag1 = (double)random.nextFloat() < geodeconfiguration.f_160817_;
               if (flag1) {
                  this.m_159742_(worldgenlevel, blockpos3, geodeblocksettings.f_158289_.m_7112_(random, blockpos3), predicate);
               } else {
                  this.m_159742_(worldgenlevel, blockpos3, geodeblocksettings.f_158288_.m_7112_(random, blockpos3), predicate);
               }

               if ((!geodeconfiguration.f_160818_ || flag1) && (double)random.nextFloat() < geodeconfiguration.f_160816_) {
                  list2.add(blockpos3.m_7949_());
               }
            } else if (d6 >= d3) {
               this.m_159742_(worldgenlevel, blockpos3, geodeblocksettings.f_158290_.m_7112_(random, blockpos3), predicate);
            } else if (d6 >= d4) {
               this.m_159742_(worldgenlevel, blockpos3, geodeblocksettings.f_158291_.m_7112_(random, blockpos3), predicate);
            }
         }
      }

      List<BlockState> list3 = geodeblocksettings.f_158292_;

      for(BlockPos blockpos4 : list2) {
         BlockState blockstate1 = Util.m_143804_(list3, random);

         for(Direction direction : f_159831_) {
            if (blockstate1.m_61138_(BlockStateProperties.f_61372_)) {
               blockstate1 = blockstate1.m_61124_(BlockStateProperties.f_61372_, direction);
            }

            BlockPos blockpos5 = blockpos4.m_142300_(direction);
            BlockState blockstate2 = worldgenlevel.m_8055_(blockpos5);
            if (blockstate1.m_61138_(BlockStateProperties.f_61362_)) {
               blockstate1 = blockstate1.m_61124_(BlockStateProperties.f_61362_, Boolean.valueOf(blockstate2.m_60819_().m_76170_()));
            }

            if (BuddingAmethystBlock.m_152734_(blockstate2)) {
               this.m_159742_(worldgenlevel, blockpos5, blockstate1, predicate);
               break;
            }
         }
      }

      return true;
   }
}