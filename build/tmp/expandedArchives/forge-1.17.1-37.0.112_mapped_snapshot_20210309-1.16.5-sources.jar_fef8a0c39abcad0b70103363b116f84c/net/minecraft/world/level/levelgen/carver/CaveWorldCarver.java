package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class CaveWorldCarver extends WorldCarver<CaveCarverConfiguration> {
   public CaveWorldCarver(Codec<CaveCarverConfiguration> p_159194_) {
      super(p_159194_);
   }

   public boolean m_142512_(CaveCarverConfiguration p_159263_, Random p_159264_) {
      return p_159264_.nextFloat() <= p_159263_.f_67859_;
   }

   public boolean m_142404_(CarvingContext p_159254_, CaveCarverConfiguration p_159255_, ChunkAccess p_159256_, Function<BlockPos, Biome> p_159257_, Random p_159258_, Aquifer p_159259_, ChunkPos p_159260_, BitSet p_159261_) {
      int i = SectionPos.m_123223_(this.m_65073_() * 2 - 1);
      int j = p_159258_.nextInt(p_159258_.nextInt(p_159258_.nextInt(this.m_6208_()) + 1) + 1);

      for(int k = 0; k < j; ++k) {
         double d0 = (double)p_159260_.m_151382_(p_159258_.nextInt(16));
         double d1 = (double)p_159255_.f_159088_.m_142233_(p_159258_, p_159254_);
         double d2 = (double)p_159260_.m_151391_(p_159258_.nextInt(16));
         double d3 = (double)p_159255_.f_159155_.m_142269_(p_159258_);
         double d4 = (double)p_159255_.f_159156_.m_142269_(p_159258_);
         double d5 = (double)p_159255_.f_159157_.m_142269_(p_159258_);
         WorldCarver.CarveSkipChecker worldcarver$carveskipchecker = (p_159202_, p_159203_, p_159204_, p_159205_, p_159206_) -> {
            return m_159195_(p_159203_, p_159204_, p_159205_, d5);
         };
         int l = 1;
         if (p_159258_.nextInt(4) == 0) {
            double d6 = (double)p_159255_.f_159089_.m_142269_(p_159258_);
            float f1 = 1.0F + p_159258_.nextFloat() * 6.0F;
            this.m_159239_(p_159254_, p_159255_, p_159256_, p_159257_, p_159258_.nextLong(), p_159259_, d0, d1, d2, f1, d6, p_159261_, worldcarver$carveskipchecker);
            l += p_159258_.nextInt(4);
         }

         for(int k1 = 0; k1 < l; ++k1) {
            float f = p_159258_.nextFloat() * ((float)Math.PI * 2F);
            float f3 = (p_159258_.nextFloat() - 0.5F) / 4.0F;
            float f2 = this.m_5710_(p_159258_);
            int i1 = i - p_159258_.nextInt(i / 4);
            int j1 = 0;
            this.m_159219_(p_159254_, p_159255_, p_159256_, p_159257_, p_159258_.nextLong(), p_159259_, d0, d1, d2, d3, d4, f2, f, f3, 0, i1, this.m_6203_(), p_159261_, worldcarver$carveskipchecker);
         }
      }

      return true;
   }

   protected int m_6208_() {
      return 15;
   }

   protected float m_5710_(Random p_64834_) {
      float f = p_64834_.nextFloat() * 2.0F + p_64834_.nextFloat();
      if (p_64834_.nextInt(10) == 0) {
         f *= p_64834_.nextFloat() * p_64834_.nextFloat() * 3.0F + 1.0F;
      }

      return f;
   }

   protected double m_6203_() {
      return 1.0D;
   }

   protected void m_159239_(CarvingContext p_159240_, CaveCarverConfiguration p_159241_, ChunkAccess p_159242_, Function<BlockPos, Biome> p_159243_, long p_159244_, Aquifer p_159245_, double p_159246_, double p_159247_, double p_159248_, float p_159249_, double p_159250_, BitSet p_159251_, WorldCarver.CarveSkipChecker p_159252_) {
      double d0 = 1.5D + (double)(Mth.m_14031_(((float)Math.PI / 2F)) * p_159249_);
      double d1 = d0 * p_159250_;
      this.m_159386_(p_159240_, p_159241_, p_159242_, p_159243_, p_159244_, p_159245_, p_159246_ + 1.0D, p_159247_, p_159248_, d0, d1, p_159251_, p_159252_);
   }

   protected void m_159219_(CarvingContext p_159220_, CaveCarverConfiguration p_159221_, ChunkAccess p_159222_, Function<BlockPos, Biome> p_159223_, long p_159224_, Aquifer p_159225_, double p_159226_, double p_159227_, double p_159228_, double p_159229_, double p_159230_, float p_159231_, float p_159232_, float p_159233_, int p_159234_, int p_159235_, double p_159236_, BitSet p_159237_, WorldCarver.CarveSkipChecker p_159238_) {
      Random random = new Random(p_159224_);
      int i = random.nextInt(p_159235_ / 2) + p_159235_ / 4;
      boolean flag = random.nextInt(6) == 0;
      float f = 0.0F;
      float f1 = 0.0F;

      for(int j = p_159234_; j < p_159235_; ++j) {
         double d0 = 1.5D + (double)(Mth.m_14031_((float)Math.PI * (float)j / (float)p_159235_) * p_159231_);
         double d1 = d0 * p_159236_;
         float f2 = Mth.m_14089_(p_159233_);
         p_159226_ += (double)(Mth.m_14089_(p_159232_) * f2);
         p_159227_ += (double)Mth.m_14031_(p_159233_);
         p_159228_ += (double)(Mth.m_14031_(p_159232_) * f2);
         p_159233_ = p_159233_ * (flag ? 0.92F : 0.7F);
         p_159233_ = p_159233_ + f1 * 0.1F;
         p_159232_ += f * 0.1F;
         f1 = f1 * 0.9F;
         f = f * 0.75F;
         f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
         f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
         if (j == i && p_159231_ > 1.0F) {
            this.m_159219_(p_159220_, p_159221_, p_159222_, p_159223_, random.nextLong(), p_159225_, p_159226_, p_159227_, p_159228_, p_159229_, p_159230_, random.nextFloat() * 0.5F + 0.5F, p_159232_ - ((float)Math.PI / 2F), p_159233_ / 3.0F, j, p_159235_, 1.0D, p_159237_, p_159238_);
            this.m_159219_(p_159220_, p_159221_, p_159222_, p_159223_, random.nextLong(), p_159225_, p_159226_, p_159227_, p_159228_, p_159229_, p_159230_, random.nextFloat() * 0.5F + 0.5F, p_159232_ + ((float)Math.PI / 2F), p_159233_ / 3.0F, j, p_159235_, 1.0D, p_159237_, p_159238_);
            return;
         }

         if (random.nextInt(4) != 0) {
            if (!m_159367_(p_159222_.m_7697_(), p_159226_, p_159228_, j, p_159235_, p_159231_)) {
               return;
            }

            this.m_159386_(p_159220_, p_159221_, p_159222_, p_159223_, p_159224_, p_159225_, p_159226_, p_159227_, p_159228_, d0 * p_159229_, d1 * p_159230_, p_159237_, p_159238_);
         }
      }

   }

   private static boolean m_159195_(double p_159196_, double p_159197_, double p_159198_, double p_159199_) {
      if (p_159197_ <= p_159199_) {
         return true;
      } else {
         return p_159196_ * p_159196_ + p_159197_ * p_159197_ + p_159198_ * p_159198_ >= 1.0D;
      }
   }
}