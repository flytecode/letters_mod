package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;

public class GlowLichenFeature extends Feature<GlowLichenConfiguration> {
   public GlowLichenFeature(Codec<GlowLichenConfiguration> p_159838_) {
      super(p_159838_);
   }

   public boolean m_142674_(FeaturePlaceContext<GlowLichenConfiguration> p_159847_) {
      WorldGenLevel worldgenlevel = p_159847_.m_159774_();
      BlockPos blockpos = p_159847_.m_159777_();
      Random random = p_159847_.m_159776_();
      GlowLichenConfiguration glowlichenconfiguration = p_159847_.m_159778_();
      if (!m_159858_(worldgenlevel.m_8055_(blockpos))) {
         return false;
      } else {
         List<Direction> list = m_159848_(glowlichenconfiguration, random);
         if (m_159839_(worldgenlevel, blockpos, worldgenlevel.m_8055_(blockpos), glowlichenconfiguration, random, list)) {
            return true;
         } else {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();

            for(Direction direction : list) {
               blockpos$mutableblockpos.m_122190_(blockpos);
               List<Direction> list1 = m_159851_(glowlichenconfiguration, random, direction.m_122424_());

               for(int i = 0; i < glowlichenconfiguration.f_160870_; ++i) {
                  blockpos$mutableblockpos.m_122159_(blockpos, direction);
                  BlockState blockstate = worldgenlevel.m_8055_(blockpos$mutableblockpos);
                  if (!m_159858_(blockstate) && !blockstate.m_60713_(Blocks.f_152475_)) {
                     break;
                  }

                  if (m_159839_(worldgenlevel, blockpos$mutableblockpos, blockstate, glowlichenconfiguration, random, list1)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public static boolean m_159839_(WorldGenLevel p_159840_, BlockPos p_159841_, BlockState p_159842_, GlowLichenConfiguration p_159843_, Random p_159844_, List<Direction> p_159845_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159841_.m_122032_();

      for(Direction direction : p_159845_) {
         BlockState blockstate = p_159840_.m_8055_(blockpos$mutableblockpos.m_122159_(p_159841_, direction));
         if (p_159843_.m_160885_(blockstate.m_60734_())) {
            GlowLichenBlock glowlichenblock = (GlowLichenBlock)Blocks.f_152475_;
            BlockState blockstate1 = glowlichenblock.m_153940_(p_159842_, p_159840_, p_159841_, direction);
            if (blockstate1 == null) {
               return false;
            }

            p_159840_.m_7731_(p_159841_, blockstate1, 3);
            p_159840_.m_46865_(p_159841_).m_8113_(p_159841_);
            if (p_159844_.nextFloat() < p_159843_.f_160874_) {
               glowlichenblock.m_153873_(blockstate1, p_159840_, p_159841_, direction, p_159844_, true);
            }

            return true;
         }
      }

      return false;
   }

   public static List<Direction> m_159848_(GlowLichenConfiguration p_159849_, Random p_159850_) {
      List<Direction> list = Lists.newArrayList(p_159849_.f_160876_);
      Collections.shuffle(list, p_159850_);
      return list;
   }

   public static List<Direction> m_159851_(GlowLichenConfiguration p_159852_, Random p_159853_, Direction p_159854_) {
      List<Direction> list = p_159852_.f_160876_.stream().filter((p_159857_) -> {
         return p_159857_ != p_159854_;
      }).collect(Collectors.toList());
      Collections.shuffle(list, p_159853_);
      return list;
   }

   private static boolean m_159858_(BlockState p_159859_) {
      return p_159859_.m_60795_() || p_159859_.m_60713_(Blocks.f_49990_);
   }
}