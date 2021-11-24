package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class FancyTrunkPlacer extends TrunkPlacer {
   public static final Codec<FancyTrunkPlacer> f_70091_ = RecordCodecBuilder.create((p_70136_) -> {
      return m_70305_(p_70136_).apply(p_70136_, FancyTrunkPlacer::new);
   });
   private static final double f_161796_ = 0.618D;
   private static final double f_161797_ = 1.382D;
   private static final double f_161798_ = 0.381D;
   private static final double f_161799_ = 0.328D;

   public FancyTrunkPlacer(int p_70094_, int p_70095_, int p_70096_) {
      super(p_70094_, p_70095_, p_70096_);
   }

   protected TrunkPlacerType<?> m_7362_() {
      return TrunkPlacerType.f_70320_;
   }

   public List<FoliagePlacer.FoliageAttachment> m_142625_(LevelSimulatedReader p_161801_, BiConsumer<BlockPos, BlockState> p_161802_, Random p_161803_, int p_161804_, BlockPos p_161805_, TreeConfiguration p_161806_) {
      int i = 5;
      int j = p_161804_ + 2;
      int k = Mth.m_14107_((double)j * 0.618D);
      m_161880_(p_161801_, p_161802_, p_161803_, p_161805_.m_7495_(), p_161806_);
      double d0 = 1.0D;
      int l = Math.min(1, Mth.m_14107_(1.382D + Math.pow(1.0D * (double)j / 13.0D, 2.0D)));
      int i1 = p_161805_.m_123342_() + k;
      int j1 = j - 5;
      List<FancyTrunkPlacer.FoliageCoords> list = Lists.newArrayList();
      list.add(new FancyTrunkPlacer.FoliageCoords(p_161805_.m_6630_(j1), i1));

      for(; j1 >= 0; --j1) {
         float f = m_70132_(j, j1);
         if (!(f < 0.0F)) {
            for(int k1 = 0; k1 < l; ++k1) {
               double d1 = 1.0D;
               double d2 = 1.0D * (double)f * ((double)p_161803_.nextFloat() + 0.328D);
               double d3 = (double)(p_161803_.nextFloat() * 2.0F) * Math.PI;
               double d4 = d2 * Math.sin(d3) + 0.5D;
               double d5 = d2 * Math.cos(d3) + 0.5D;
               BlockPos blockpos = p_161805_.m_142022_(d4, (double)(j1 - 1), d5);
               BlockPos blockpos1 = blockpos.m_6630_(5);
               if (this.m_161815_(p_161801_, p_161802_, p_161803_, blockpos, blockpos1, false, p_161806_)) {
                  int l1 = p_161805_.m_123341_() - blockpos.m_123341_();
                  int i2 = p_161805_.m_123343_() - blockpos.m_123343_();
                  double d6 = (double)blockpos.m_123342_() - Math.sqrt((double)(l1 * l1 + i2 * i2)) * 0.381D;
                  int j2 = d6 > (double)i1 ? i1 : (int)d6;
                  BlockPos blockpos2 = new BlockPos(p_161805_.m_123341_(), j2, p_161805_.m_123343_());
                  if (this.m_161815_(p_161801_, p_161802_, p_161803_, blockpos2, blockpos, false, p_161806_)) {
                     list.add(new FancyTrunkPlacer.FoliageCoords(blockpos, blockpos2.m_123342_()));
                  }
               }
            }
         }
      }

      this.m_161815_(p_161801_, p_161802_, p_161803_, p_161805_, p_161805_.m_6630_(k), true, p_161806_);
      this.m_161807_(p_161801_, p_161802_, p_161803_, j, p_161805_, list, p_161806_);
      List<FoliagePlacer.FoliageAttachment> list1 = Lists.newArrayList();

      for(FancyTrunkPlacer.FoliageCoords fancytrunkplacer$foliagecoords : list) {
         if (this.m_70098_(j, fancytrunkplacer$foliagecoords.m_70142_() - p_161805_.m_123342_())) {
            list1.add(fancytrunkplacer$foliagecoords.f_70137_);
         }
      }

      return list1;
   }

   private boolean m_161815_(LevelSimulatedReader p_161816_, BiConsumer<BlockPos, BlockState> p_161817_, Random p_161818_, BlockPos p_161819_, BlockPos p_161820_, boolean p_161821_, TreeConfiguration p_161822_) {
      if (!p_161821_ && Objects.equals(p_161819_, p_161820_)) {
         return true;
      } else {
         BlockPos blockpos = p_161820_.m_142082_(-p_161819_.m_123341_(), -p_161819_.m_123342_(), -p_161819_.m_123343_());
         int i = this.m_70127_(blockpos);
         float f = (float)blockpos.m_123341_() / (float)i;
         float f1 = (float)blockpos.m_123342_() / (float)i;
         float f2 = (float)blockpos.m_123343_() / (float)i;

         for(int j = 0; j <= i; ++j) {
            BlockPos blockpos1 = p_161819_.m_142022_((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * f1), (double)(0.5F + (float)j * f2));
            if (p_161821_) {
               TrunkPlacer.m_161886_(p_161816_, p_161817_, p_161818_, blockpos1, p_161822_, (p_161826_) -> {
                  return p_161826_.m_61124_(RotatedPillarBlock.f_55923_, this.m_70129_(p_161819_, blockpos1));
               });
            } else if (!TreeFeature.m_67262_(p_161816_, blockpos1)) {
               return false;
            }
         }

         return true;
      }
   }

   private int m_70127_(BlockPos p_70128_) {
      int i = Mth.m_14040_(p_70128_.m_123341_());
      int j = Mth.m_14040_(p_70128_.m_123342_());
      int k = Mth.m_14040_(p_70128_.m_123343_());
      return Math.max(i, Math.max(j, k));
   }

   private Direction.Axis m_70129_(BlockPos p_70130_, BlockPos p_70131_) {
      Direction.Axis direction$axis = Direction.Axis.Y;
      int i = Math.abs(p_70131_.m_123341_() - p_70130_.m_123341_());
      int j = Math.abs(p_70131_.m_123343_() - p_70130_.m_123343_());
      int k = Math.max(i, j);
      if (k > 0) {
         if (i == k) {
            direction$axis = Direction.Axis.X;
         } else {
            direction$axis = Direction.Axis.Z;
         }
      }

      return direction$axis;
   }

   private boolean m_70098_(int p_70099_, int p_70100_) {
      return (double)p_70100_ >= (double)p_70099_ * 0.2D;
   }

   private void m_161807_(LevelSimulatedReader p_161808_, BiConsumer<BlockPos, BlockState> p_161809_, Random p_161810_, int p_161811_, BlockPos p_161812_, List<FancyTrunkPlacer.FoliageCoords> p_161813_, TreeConfiguration p_161814_) {
      for(FancyTrunkPlacer.FoliageCoords fancytrunkplacer$foliagecoords : p_161813_) {
         int i = fancytrunkplacer$foliagecoords.m_70142_();
         BlockPos blockpos = new BlockPos(p_161812_.m_123341_(), i, p_161812_.m_123343_());
         if (!blockpos.equals(fancytrunkplacer$foliagecoords.f_70137_.m_161451_()) && this.m_70098_(p_161811_, i - p_161812_.m_123342_())) {
            this.m_161815_(p_161808_, p_161809_, p_161810_, blockpos, fancytrunkplacer$foliagecoords.f_70137_.m_161451_(), true, p_161814_);
         }
      }

   }

   private static float m_70132_(int p_70133_, int p_70134_) {
      if ((float)p_70134_ < (float)p_70133_ * 0.3F) {
         return -1.0F;
      } else {
         float f = (float)p_70133_ / 2.0F;
         float f1 = f - (float)p_70134_;
         float f2 = Mth.m_14116_(f * f - f1 * f1);
         if (f1 == 0.0F) {
            f2 = f;
         } else if (Math.abs(f1) >= f) {
            return 0.0F;
         }

         return f2 * 0.5F;
      }
   }

   static class FoliageCoords {
      final FoliagePlacer.FoliageAttachment f_70137_;
      private final int f_70138_;

      public FoliageCoords(BlockPos p_70140_, int p_70141_) {
         this.f_70137_ = new FoliagePlacer.FoliageAttachment(p_70140_, 0, false);
         this.f_70138_ = p_70141_;
      }

      public int m_70142_() {
         return this.f_70138_;
      }
   }
}