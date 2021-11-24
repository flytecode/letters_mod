package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

public class DripstoneClusterFeature extends Feature<DripstoneClusterConfiguration> {
   public DripstoneClusterFeature(Codec<DripstoneClusterConfiguration> p_159575_) {
      super(p_159575_);
   }

   public boolean m_142674_(FeaturePlaceContext<DripstoneClusterConfiguration> p_159605_) {
      WorldGenLevel worldgenlevel = p_159605_.m_159774_();
      BlockPos blockpos = p_159605_.m_159777_();
      DripstoneClusterConfiguration dripstoneclusterconfiguration = p_159605_.m_159778_();
      Random random = p_159605_.m_159776_();
      if (!DripstoneUtils.m_159628_(worldgenlevel, blockpos)) {
         return false;
      } else {
         int i = dripstoneclusterconfiguration.f_160760_.m_142270_(random);
         float f = dripstoneclusterconfiguration.f_160766_.m_142269_(random);
         float f1 = dripstoneclusterconfiguration.f_160765_.m_142269_(random);
         int j = dripstoneclusterconfiguration.f_160761_.m_142270_(random);
         int k = dripstoneclusterconfiguration.f_160761_.m_142270_(random);

         for(int l = -j; l <= j; ++l) {
            for(int i1 = -k; i1 <= k; ++i1) {
               double d0 = this.m_159576_(j, k, l, i1, dripstoneclusterconfiguration);
               BlockPos blockpos1 = blockpos.m_142082_(l, 0, i1);
               this.m_159593_(worldgenlevel, random, blockpos1, l, i1, f, d0, i, f1, dripstoneclusterconfiguration);
            }
         }

         return true;
      }
   }

   private void m_159593_(WorldGenLevel p_159594_, Random p_159595_, BlockPos p_159596_, int p_159597_, int p_159598_, float p_159599_, double p_159600_, int p_159601_, float p_159602_, DripstoneClusterConfiguration p_159603_) {
      Optional<Column> optional = Column.m_158175_(p_159594_, p_159596_, p_159603_.f_160759_, DripstoneUtils::m_159664_, DripstoneUtils::m_159649_);
      if (optional.isPresent()) {
         OptionalInt optionalint = optional.get().m_142011_();
         OptionalInt optionalint1 = optional.get().m_142009_();
         if (optionalint.isPresent() || optionalint1.isPresent()) {
            boolean flag = p_159595_.nextFloat() < p_159599_;
            Column column;
            if (flag && optionalint1.isPresent() && this.m_159619_(p_159594_, p_159596_.m_175288_(optionalint1.getAsInt()))) {
               int i = optionalint1.getAsInt();
               column = optional.get().m_158181_(OptionalInt.of(i - 1));
               p_159594_.m_7731_(p_159596_.m_175288_(i), Blocks.f_49990_.m_49966_(), 2);
            } else {
               column = optional.get();
            }

            OptionalInt optionalint2 = column.m_142009_();
            boolean flag1 = p_159595_.nextDouble() < p_159600_;
            int j;
            if (optionalint.isPresent() && flag1 && !this.m_159585_(p_159594_, p_159596_.m_175288_(optionalint.getAsInt()))) {
               int k = p_159603_.f_160764_.m_142270_(p_159595_);
               this.m_159588_(p_159594_, p_159596_.m_175288_(optionalint.getAsInt()), k, Direction.UP);
               int l;
               if (optionalint2.isPresent()) {
                  l = Math.min(p_159601_, optionalint.getAsInt() - optionalint2.getAsInt());
               } else {
                  l = p_159601_;
               }

               j = this.m_159612_(p_159595_, p_159597_, p_159598_, p_159602_, l, p_159603_);
            } else {
               j = 0;
            }

            boolean flag2 = p_159595_.nextDouble() < p_159600_;
            int i3;
            if (optionalint2.isPresent() && flag2 && !this.m_159585_(p_159594_, p_159596_.m_175288_(optionalint2.getAsInt()))) {
               int i1 = p_159603_.f_160764_.m_142270_(p_159595_);
               this.m_159588_(p_159594_, p_159596_.m_175288_(optionalint2.getAsInt()), i1, Direction.DOWN);
               i3 = Math.max(0, j + Mth.m_144928_(p_159595_, -p_159603_.f_160762_, p_159603_.f_160762_));
            } else {
               i3 = 0;
            }

            int j1;
            int j3;
            if (optionalint.isPresent() && optionalint2.isPresent() && optionalint.getAsInt() - j <= optionalint2.getAsInt() + i3) {
               int k1 = optionalint2.getAsInt();
               int l1 = optionalint.getAsInt();
               int i2 = Math.max(l1 - j, k1 + 1);
               int j2 = Math.min(k1 + i3, l1 - 1);
               int k2 = Mth.m_144928_(p_159595_, i2, j2 + 1);
               int l2 = k2 - 1;
               j3 = l1 - k2;
               j1 = l2 - k1;
            } else {
               j3 = j;
               j1 = i3;
            }

            boolean flag3 = p_159595_.nextBoolean() && j3 > 0 && j1 > 0 && column.m_142030_().isPresent() && j3 + j1 == column.m_142030_().getAsInt();
            if (optionalint.isPresent()) {
               DripstoneUtils.m_159643_(p_159594_, p_159596_.m_175288_(optionalint.getAsInt() - 1), Direction.DOWN, j3, flag3);
            }

            if (optionalint2.isPresent()) {
               DripstoneUtils.m_159643_(p_159594_, p_159596_.m_175288_(optionalint2.getAsInt() + 1), Direction.UP, j1, flag3);
            }

         }
      }
   }

   private boolean m_159585_(LevelReader p_159586_, BlockPos p_159587_) {
      return p_159586_.m_8055_(p_159587_).m_60713_(Blocks.f_49991_);
   }

   private int m_159612_(Random p_159613_, int p_159614_, int p_159615_, float p_159616_, int p_159617_, DripstoneClusterConfiguration p_159618_) {
      if (p_159613_.nextFloat() > p_159616_) {
         return 0;
      } else {
         int i = Math.abs(p_159614_) + Math.abs(p_159615_);
         float f = (float)Mth.m_144851_((double)i, 0.0D, (double)p_159618_.f_160769_, (double)p_159617_ / 2.0D, 0.0D);
         return (int)m_159606_(p_159613_, 0.0F, (float)p_159617_, f, (float)p_159618_.f_160763_);
      }
   }

   private boolean m_159619_(WorldGenLevel p_159620_, BlockPos p_159621_) {
      BlockState blockstate = p_159620_.m_8055_(p_159621_);
      if (!blockstate.m_60713_(Blocks.f_49990_) && !blockstate.m_60713_(Blocks.f_152537_) && !blockstate.m_60713_(Blocks.f_152588_)) {
         for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!this.m_159582_(p_159620_, p_159621_.m_142300_(direction))) {
               return false;
            }
         }

         return this.m_159582_(p_159620_, p_159621_.m_7495_());
      } else {
         return false;
      }
   }

   private boolean m_159582_(LevelAccessor p_159583_, BlockPos p_159584_) {
      BlockState blockstate = p_159583_.m_8055_(p_159584_);
      return blockstate.m_60620_(BlockTags.f_13061_) || blockstate.m_60819_().m_76153_(FluidTags.f_13131_);
   }

   private void m_159588_(WorldGenLevel p_159589_, BlockPos p_159590_, int p_159591_, Direction p_159592_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159590_.m_122032_();

      for(int i = 0; i < p_159591_; ++i) {
         if (!DripstoneUtils.m_159636_(p_159589_, blockpos$mutableblockpos)) {
            return;
         }

         blockpos$mutableblockpos.m_122173_(p_159592_);
      }

   }

   private double m_159576_(int p_159577_, int p_159578_, int p_159579_, int p_159580_, DripstoneClusterConfiguration p_159581_) {
      int i = p_159577_ - Math.abs(p_159579_);
      int j = p_159578_ - Math.abs(p_159580_);
      int k = Math.min(i, j);
      return Mth.m_144851_((double)k, 0.0D, (double)p_159581_.f_160768_, (double)p_159581_.f_160767_, 1.0D);
   }

   private static float m_159606_(Random p_159607_, float p_159608_, float p_159609_, float p_159610_, float p_159611_) {
      return ClampedNormalFloat.m_146434_(p_159607_, p_159610_, p_159611_, p_159608_, p_159609_);
   }
}