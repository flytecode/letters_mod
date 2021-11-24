package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class TreeFeature extends Feature<TreeConfiguration> {
   private static final int f_160509_ = 19;

   public TreeFeature(Codec<TreeConfiguration> p_67201_) {
      super(p_67201_);
   }

   public static boolean m_67262_(LevelSimulatedReader p_67263_, BlockPos p_67264_) {
      return m_67272_(p_67263_, p_67264_) || p_67263_.m_7433_(p_67264_, (p_67281_) -> {
         return p_67281_.m_60620_(BlockTags.f_13106_);
      });
   }

   private static boolean m_67277_(LevelSimulatedReader p_67278_, BlockPos p_67279_) {
      return p_67278_.m_7433_(p_67279_, (p_67276_) -> {
         return p_67276_.m_60713_(Blocks.f_50191_);
      });
   }

   private static boolean m_67282_(LevelSimulatedReader p_67283_, BlockPos p_67284_) {
      return p_67283_.m_7433_(p_67284_, (p_67271_) -> {
         return p_67271_.m_60713_(Blocks.f_49990_);
      });
   }

   public static boolean m_67267_(LevelSimulatedReader p_67268_, BlockPos p_67269_) {
      return p_67268_.m_7433_(p_67269_, (p_67266_) -> {
         return p_67266_.m_60795_() || p_67266_.m_60620_(BlockTags.f_13035_);
      });
   }

   private static boolean m_67288_(LevelSimulatedReader p_67289_, BlockPos p_67290_) {
      return p_67289_.m_7433_(p_67290_, (p_160551_) -> {
         Material material = p_160551_.m_60767_();
         return material == Material.f_76302_;
      });
   }

   private static void m_67256_(LevelWriter p_67257_, BlockPos p_67258_, BlockState p_67259_) {
      p_67257_.m_7731_(p_67258_, p_67259_, 19);
   }

   public static boolean m_67272_(LevelSimulatedReader p_67273_, BlockPos p_67274_) {
      return m_67267_(p_67273_, p_67274_) || m_67288_(p_67273_, p_67274_) || m_67282_(p_67273_, p_67274_);
   }

   private boolean m_160510_(WorldGenLevel p_160511_, Random p_160512_, BlockPos p_160513_, BiConsumer<BlockPos, BlockState> p_160514_, BiConsumer<BlockPos, BlockState> p_160515_, TreeConfiguration p_160516_) {
      int i = p_160516_.f_68190_.m_70309_(p_160512_);
      int j = p_160516_.f_68189_.m_5969_(p_160512_, i, p_160516_);
      int k = i - j;
      int l = p_160516_.f_68189_.m_5937_(p_160512_, k);
      if (p_160513_.m_123342_() >= p_160511_.m_141937_() + 1 && p_160513_.m_123342_() + i + 1 <= p_160511_.m_151558_()) {
         if (!p_160516_.f_161214_.m_7112_(p_160512_, p_160513_).m_60710_(p_160511_, p_160513_)) {
            return false;
         } else {
            OptionalInt optionalint = p_160516_.f_68191_.m_68295_();
            int i1 = this.m_67215_(p_160511_, i, p_160513_, p_160516_);
            if (i1 >= i || optionalint.isPresent() && i1 >= optionalint.getAsInt()) {
               List<FoliagePlacer.FoliageAttachment> list = p_160516_.f_68190_.m_142625_(p_160511_, p_160514_, p_160512_, i1, p_160513_, p_160516_);
               list.forEach((p_160539_) -> {
                  p_160516_.f_68189_.m_161413_(p_160511_, p_160515_, p_160512_, p_160516_, i1, p_160539_, j, l);
               });
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private int m_67215_(LevelSimulatedReader p_67216_, int p_67217_, BlockPos p_67218_, TreeConfiguration p_67219_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i <= p_67217_ + 1; ++i) {
         int j = p_67219_.f_68191_.m_6133_(p_67217_, i);

         for(int k = -j; k <= j; ++k) {
            for(int l = -j; l <= j; ++l) {
               blockpos$mutableblockpos.m_122154_(p_67218_, k, i, l);
               if (!m_67262_(p_67216_, blockpos$mutableblockpos) || !p_67219_.f_68193_ && m_67277_(p_67216_, blockpos$mutableblockpos)) {
                  return i - 2;
               }
            }
         }
      }

      return p_67217_;
   }

   protected void m_5974_(LevelWriter p_67221_, BlockPos p_67222_, BlockState p_67223_) {
      m_67256_(p_67221_, p_67222_, p_67223_);
   }

   public final boolean m_142674_(FeaturePlaceContext<TreeConfiguration> p_160530_) {
      WorldGenLevel worldgenlevel = p_160530_.m_159774_();
      Random random = p_160530_.m_159776_();
      BlockPos blockpos = p_160530_.m_159777_();
      TreeConfiguration treeconfiguration = p_160530_.m_159778_();
      Set<BlockPos> set = Sets.newHashSet();
      Set<BlockPos> set1 = Sets.newHashSet();
      Set<BlockPos> set2 = Sets.newHashSet();
      BiConsumer<BlockPos, BlockState> biconsumer = (p_160555_, p_160556_) -> {
         set.add(p_160555_.m_7949_());
         worldgenlevel.m_7731_(p_160555_, p_160556_, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer1 = (p_160548_, p_160549_) -> {
         set1.add(p_160548_.m_7949_());
         worldgenlevel.m_7731_(p_160548_, p_160549_, 19);
      };
      BiConsumer<BlockPos, BlockState> biconsumer2 = (p_160543_, p_160544_) -> {
         set2.add(p_160543_.m_7949_());
         worldgenlevel.m_7731_(p_160543_, p_160544_, 19);
      };
      boolean flag = this.m_160510_(worldgenlevel, random, blockpos, biconsumer, biconsumer1, treeconfiguration);
      if (flag && (!set.isEmpty() || !set1.isEmpty())) {
         if (!treeconfiguration.f_68187_.isEmpty()) {
            List<BlockPos> list = Lists.newArrayList(set);
            List<BlockPos> list1 = Lists.newArrayList(set1);
            list.sort(Comparator.comparingInt(Vec3i::m_123342_));
            list1.sort(Comparator.comparingInt(Vec3i::m_123342_));
            treeconfiguration.f_68187_.forEach((p_160528_) -> {
               p_160528_.m_142741_(worldgenlevel, biconsumer2, random, list, list1);
            });
         }

         return BoundingBox.m_162378_(Iterables.concat(set, set1, set2)).map((p_160521_) -> {
            DiscreteVoxelShape discretevoxelshape = m_67202_(worldgenlevel, p_160521_, set, set2);
            StructureTemplate.m_74510_(worldgenlevel, 3, discretevoxelshape, p_160521_.m_162395_(), p_160521_.m_162396_(), p_160521_.m_162398_());
            return true;
         }).orElse(false);
      } else {
         return false;
      }
   }

   private static DiscreteVoxelShape m_67202_(LevelAccessor p_67203_, BoundingBox p_67204_, Set<BlockPos> p_67205_, Set<BlockPos> p_67206_) {
      List<Set<BlockPos>> list = Lists.newArrayList();
      DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(p_67204_.m_71056_(), p_67204_.m_71057_(), p_67204_.m_71058_());
      int i = 6;

      for(int j = 0; j < 6; ++j) {
         list.add(Sets.newHashSet());
      }

      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(BlockPos blockpos : Lists.newArrayList(p_67206_)) {
         if (p_67204_.m_71051_(blockpos)) {
            discretevoxelshape.m_142703_(blockpos.m_123341_() - p_67204_.m_162395_(), blockpos.m_123342_() - p_67204_.m_162396_(), blockpos.m_123343_() - p_67204_.m_162398_());
         }
      }

      for(BlockPos blockpos1 : Lists.newArrayList(p_67205_)) {
         if (p_67204_.m_71051_(blockpos1)) {
            discretevoxelshape.m_142703_(blockpos1.m_123341_() - p_67204_.m_162395_(), blockpos1.m_123342_() - p_67204_.m_162396_(), blockpos1.m_123343_() - p_67204_.m_162398_());
         }

         for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.m_122159_(blockpos1, direction);
            if (!p_67205_.contains(blockpos$mutableblockpos)) {
               BlockState blockstate = p_67203_.m_8055_(blockpos$mutableblockpos);
               if (blockstate.m_61138_(BlockStateProperties.f_61414_)) {
                  list.get(0).add(blockpos$mutableblockpos.m_7949_());
                  m_67256_(p_67203_, blockpos$mutableblockpos, blockstate.m_61124_(BlockStateProperties.f_61414_, Integer.valueOf(1)));
                  if (p_67204_.m_71051_(blockpos$mutableblockpos)) {
                     discretevoxelshape.m_142703_(blockpos$mutableblockpos.m_123341_() - p_67204_.m_162395_(), blockpos$mutableblockpos.m_123342_() - p_67204_.m_162396_(), blockpos$mutableblockpos.m_123343_() - p_67204_.m_162398_());
                  }
               }
            }
         }
      }

      for(int l = 1; l < 6; ++l) {
         Set<BlockPos> set = list.get(l - 1);
         Set<BlockPos> set1 = list.get(l);

         for(BlockPos blockpos2 : set) {
            if (p_67204_.m_71051_(blockpos2)) {
               discretevoxelshape.m_142703_(blockpos2.m_123341_() - p_67204_.m_162395_(), blockpos2.m_123342_() - p_67204_.m_162396_(), blockpos2.m_123343_() - p_67204_.m_162398_());
            }

            for(Direction direction1 : Direction.values()) {
               blockpos$mutableblockpos.m_122159_(blockpos2, direction1);
               if (!set.contains(blockpos$mutableblockpos) && !set1.contains(blockpos$mutableblockpos)) {
                  BlockState blockstate1 = p_67203_.m_8055_(blockpos$mutableblockpos);
                  if (blockstate1.m_61138_(BlockStateProperties.f_61414_)) {
                     int k = blockstate1.m_61143_(BlockStateProperties.f_61414_);
                     if (k > l + 1) {
                        BlockState blockstate2 = blockstate1.m_61124_(BlockStateProperties.f_61414_, Integer.valueOf(l + 1));
                        m_67256_(p_67203_, blockpos$mutableblockpos, blockstate2);
                        if (p_67204_.m_71051_(blockpos$mutableblockpos)) {
                           discretevoxelshape.m_142703_(blockpos$mutableblockpos.m_123341_() - p_67204_.m_162395_(), blockpos$mutableblockpos.m_123342_() - p_67204_.m_162396_(), blockpos$mutableblockpos.m_123343_() - p_67204_.m_162398_());
                        }

                        set1.add(blockpos$mutableblockpos.m_7949_());
                     }
                  }
               }
            }
         }
      }

      return discretevoxelshape;
   }
}