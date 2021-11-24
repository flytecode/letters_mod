package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.phys.Vec3;

public class LargeDripstoneFeature extends Feature<LargeDripstoneConfiguration> {
   public LargeDripstoneFeature(Codec<LargeDripstoneConfiguration> p_159960_) {
      super(p_159960_);
   }

   public boolean m_142674_(FeaturePlaceContext<LargeDripstoneConfiguration> p_159967_) {
      WorldGenLevel worldgenlevel = p_159967_.m_159774_();
      BlockPos blockpos = p_159967_.m_159777_();
      LargeDripstoneConfiguration largedripstoneconfiguration = p_159967_.m_159778_();
      Random random = p_159967_.m_159776_();
      if (!DripstoneUtils.m_159628_(worldgenlevel, blockpos)) {
         return false;
      } else {
         Optional<Column> optional = Column.m_158175_(worldgenlevel, blockpos, largedripstoneconfiguration.f_160945_, DripstoneUtils::m_159664_, DripstoneUtils::m_159649_);
         if (optional.isPresent() && optional.get() instanceof Column.Range) {
            Column.Range column$range = (Column.Range)optional.get();
            if (column$range.m_158214_() < 4) {
               return false;
            } else {
               int i = (int)((float)column$range.m_158214_() * largedripstoneconfiguration.f_160948_);
               int j = Mth.m_14045_(i, largedripstoneconfiguration.f_160946_.m_142739_(), largedripstoneconfiguration.f_160946_.m_142737_());
               int k = Mth.m_144928_(random, largedripstoneconfiguration.f_160946_.m_142739_(), j);
               LargeDripstoneFeature.LargeDripstone largedripstonefeature$largedripstone = m_159968_(blockpos.m_175288_(column$range.m_158212_() - 1), false, random, k, largedripstoneconfiguration.f_160949_, largedripstoneconfiguration.f_160947_);
               LargeDripstoneFeature.LargeDripstone largedripstonefeature$largedripstone1 = m_159968_(blockpos.m_175288_(column$range.m_158213_() + 1), true, random, k, largedripstoneconfiguration.f_160950_, largedripstoneconfiguration.f_160947_);
               LargeDripstoneFeature.WindOffsetter largedripstonefeature$windoffsetter;
               if (largedripstonefeature$largedripstone.m_159996_(largedripstoneconfiguration) && largedripstonefeature$largedripstone1.m_159996_(largedripstoneconfiguration)) {
                  largedripstonefeature$windoffsetter = new LargeDripstoneFeature.WindOffsetter(blockpos.m_123342_(), random, largedripstoneconfiguration.f_160951_);
               } else {
                  largedripstonefeature$windoffsetter = LargeDripstoneFeature.WindOffsetter.m_160007_();
               }

               boolean flag = largedripstonefeature$largedripstone.m_159989_(worldgenlevel, largedripstonefeature$windoffsetter);
               boolean flag1 = largedripstonefeature$largedripstone1.m_159989_(worldgenlevel, largedripstonefeature$windoffsetter);
               if (flag) {
                  largedripstonefeature$largedripstone.m_159992_(worldgenlevel, random, largedripstonefeature$windoffsetter);
               }

               if (flag1) {
                  largedripstonefeature$largedripstone1.m_159992_(worldgenlevel, random, largedripstonefeature$windoffsetter);
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   private static LargeDripstoneFeature.LargeDripstone m_159968_(BlockPos p_159969_, boolean p_159970_, Random p_159971_, int p_159972_, FloatProvider p_159973_, FloatProvider p_159974_) {
      return new LargeDripstoneFeature.LargeDripstone(p_159969_, p_159970_, p_159972_, (double)p_159973_.m_142269_(p_159971_), (double)p_159974_.m_142269_(p_159971_));
   }

   private void m_159961_(WorldGenLevel p_159962_, BlockPos p_159963_, Column.Range p_159964_, LargeDripstoneFeature.WindOffsetter p_159965_) {
      p_159962_.m_7731_(p_159965_.m_160008_(p_159963_.m_175288_(p_159964_.m_158212_() - 1)), Blocks.f_50090_.m_49966_(), 2);
      p_159962_.m_7731_(p_159965_.m_160008_(p_159963_.m_175288_(p_159964_.m_158213_() + 1)), Blocks.f_50074_.m_49966_(), 2);

      for(BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159963_.m_175288_(p_159964_.m_158213_() + 2).m_122032_(); blockpos$mutableblockpos.m_123342_() < p_159964_.m_158212_() - 1; blockpos$mutableblockpos.m_122173_(Direction.UP)) {
         BlockPos blockpos = p_159965_.m_160008_(blockpos$mutableblockpos);
         if (DripstoneUtils.m_159628_(p_159962_, blockpos) || p_159962_.m_8055_(blockpos).m_60713_(Blocks.f_152537_)) {
            p_159962_.m_7731_(blockpos, Blocks.f_50318_.m_49966_(), 2);
         }
      }

   }

   static final class LargeDripstone {
      private BlockPos f_159975_;
      private final boolean f_159976_;
      private int f_159977_;
      private final double f_159978_;
      private final double f_159979_;

      LargeDripstone(BlockPos p_159981_, boolean p_159982_, int p_159983_, double p_159984_, double p_159985_) {
         this.f_159975_ = p_159981_;
         this.f_159976_ = p_159982_;
         this.f_159977_ = p_159983_;
         this.f_159978_ = p_159984_;
         this.f_159979_ = p_159985_;
      }

      private int m_159986_() {
         return this.m_159987_(0.0F);
      }

      private int m_159998_() {
         return this.f_159976_ ? this.f_159975_.m_123342_() : this.f_159975_.m_123342_() - this.m_159986_();
      }

      private int m_159999_() {
         return !this.f_159976_ ? this.f_159975_.m_123342_() : this.f_159975_.m_123342_() + this.m_159986_();
      }

      boolean m_159989_(WorldGenLevel p_159990_, LargeDripstoneFeature.WindOffsetter p_159991_) {
         while(this.f_159977_ > 1) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = this.f_159975_.m_122032_();
            int i = Math.min(10, this.m_159986_());

            for(int j = 0; j < i; ++j) {
               if (p_159990_.m_8055_(blockpos$mutableblockpos).m_60713_(Blocks.f_49991_)) {
                  return false;
               }

               if (DripstoneUtils.m_159639_(p_159990_, p_159991_.m_160008_(blockpos$mutableblockpos), this.f_159977_)) {
                  this.f_159975_ = blockpos$mutableblockpos;
                  return true;
               }

               blockpos$mutableblockpos.m_122173_(this.f_159976_ ? Direction.DOWN : Direction.UP);
            }

            this.f_159977_ /= 2;
         }

         return false;
      }

      private int m_159987_(float p_159988_) {
         return (int)DripstoneUtils.m_159623_((double)p_159988_, (double)this.f_159977_, this.f_159979_, this.f_159978_);
      }

      void m_159992_(WorldGenLevel p_159993_, Random p_159994_, LargeDripstoneFeature.WindOffsetter p_159995_) {
         for(int i = -this.f_159977_; i <= this.f_159977_; ++i) {
            for(int j = -this.f_159977_; j <= this.f_159977_; ++j) {
               float f = Mth.m_14116_((float)(i * i + j * j));
               if (!(f > (float)this.f_159977_)) {
                  int k = this.m_159987_(f);
                  if (k > 0) {
                     if ((double)p_159994_.nextFloat() < 0.2D) {
                        k = (int)((float)k * Mth.m_144924_(p_159994_, 0.8F, 1.0F));
                     }

                     BlockPos.MutableBlockPos blockpos$mutableblockpos = this.f_159975_.m_142082_(i, 0, j).m_122032_();
                     boolean flag = false;

                     for(int l = 0; l < k; ++l) {
                        BlockPos blockpos = p_159995_.m_160008_(blockpos$mutableblockpos);
                        if (DripstoneUtils.m_159659_(p_159993_, blockpos)) {
                           flag = true;
                           Block block = Blocks.f_152537_;
                           p_159993_.m_7731_(blockpos, block.m_49966_(), 2);
                        } else if (flag && p_159993_.m_8055_(blockpos).m_60620_(BlockTags.f_13061_)) {
                           break;
                        }

                        blockpos$mutableblockpos.m_122173_(this.f_159976_ ? Direction.UP : Direction.DOWN);
                     }
                  }
               }
            }
         }

      }

      boolean m_159996_(LargeDripstoneConfiguration p_159997_) {
         return this.f_159977_ >= p_159997_.f_160952_ && this.f_159978_ >= (double)p_159997_.f_160953_;
      }
   }

   static final class WindOffsetter {
      private final int f_160000_;
      @Nullable
      private final Vec3 f_160001_;

      WindOffsetter(int p_160004_, Random p_160005_, FloatProvider p_160006_) {
         this.f_160000_ = p_160004_;
         float f = p_160006_.m_142269_(p_160005_);
         float f1 = Mth.m_144924_(p_160005_, 0.0F, (float)Math.PI);
         this.f_160001_ = new Vec3((double)(Mth.m_14089_(f1) * f), 0.0D, (double)(Mth.m_14031_(f1) * f));
      }

      private WindOffsetter() {
         this.f_160000_ = 0;
         this.f_160001_ = null;
      }

      static LargeDripstoneFeature.WindOffsetter m_160007_() {
         return new LargeDripstoneFeature.WindOffsetter();
      }

      BlockPos m_160008_(BlockPos p_160009_) {
         if (this.f_160001_ == null) {
            return p_160009_;
         } else {
            int i = this.f_160000_ - p_160009_.m_123342_();
            Vec3 vec3 = this.f_160001_.m_82490_((double)i);
            return p_160009_.m_142022_(vec3.f_82479_, 0.0D, vec3.f_82481_);
         }
      }
   }
}