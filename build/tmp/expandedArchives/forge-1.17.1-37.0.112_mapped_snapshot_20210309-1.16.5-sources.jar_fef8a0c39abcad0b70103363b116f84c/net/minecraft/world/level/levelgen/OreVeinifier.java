package net.minecraft.world.level.levelgen;

import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class OreVeinifier {
   private static final float f_158783_ = 1.0F;
   private static final float f_158784_ = 4.0F;
   private static final float f_158785_ = 0.08F;
   private static final float f_158786_ = 0.5F;
   private static final double f_158787_ = 1.5D;
   private static final int f_158788_ = 20;
   private static final double f_158789_ = 0.2D;
   private static final float f_158790_ = 0.7F;
   private static final float f_158791_ = 0.1F;
   private static final float f_158792_ = 0.3F;
   private static final float f_158793_ = 0.6F;
   private static final float f_158794_ = 0.02F;
   private static final float f_158795_ = -0.3F;
   private final int f_158796_;
   private final int f_158797_;
   private final BlockState f_158798_;
   private final NormalNoise f_158799_;
   private final NormalNoise f_158800_;
   private final NormalNoise f_158801_;
   private final NormalNoise f_158802_;
   private final int f_158803_;
   private final int f_158804_;

   public OreVeinifier(long p_158806_, BlockState p_158807_, int p_158808_, int p_158809_, int p_158810_) {
      Random random = new Random(p_158806_);
      this.f_158798_ = p_158807_;
      this.f_158799_ = NormalNoise.m_164354_(new SimpleRandomSource(random.nextLong()), -8, 1.0D);
      this.f_158800_ = NormalNoise.m_164354_(new SimpleRandomSource(random.nextLong()), -7, 1.0D);
      this.f_158801_ = NormalNoise.m_164354_(new SimpleRandomSource(random.nextLong()), -7, 1.0D);
      this.f_158802_ = NormalNoise.m_164354_(new SimpleRandomSource(0L), -5, 1.0D);
      this.f_158803_ = p_158808_;
      this.f_158804_ = p_158809_;
      this.f_158796_ = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_158842_) -> {
         return p_158842_.f_158861_;
      }).max().orElse(p_158810_);
      this.f_158797_ = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_158818_) -> {
         return p_158818_.f_158860_;
      }).min().orElse(p_158810_);
   }

   public void m_158827_(double[] p_158828_, int p_158829_, int p_158830_, int p_158831_, int p_158832_) {
      this.m_158833_(p_158828_, p_158829_, p_158830_, this.f_158799_, 1.5D, p_158831_, p_158832_);
   }

   public void m_158843_(double[] p_158844_, int p_158845_, int p_158846_, int p_158847_, int p_158848_) {
      this.m_158833_(p_158844_, p_158845_, p_158846_, this.f_158800_, 4.0D, p_158847_, p_158848_);
   }

   public void m_158849_(double[] p_158850_, int p_158851_, int p_158852_, int p_158853_, int p_158854_) {
      this.m_158833_(p_158850_, p_158851_, p_158852_, this.f_158801_, 4.0D, p_158853_, p_158854_);
   }

   public void m_158833_(double[] p_158834_, int p_158835_, int p_158836_, NormalNoise p_158837_, double p_158838_, int p_158839_, int p_158840_) {
      for(int i = 0; i < p_158840_; ++i) {
         int j = i + p_158839_;
         int k = p_158835_ * this.f_158803_;
         int l = j * this.f_158804_;
         int i1 = p_158836_ * this.f_158803_;
         double d0;
         if (l >= this.f_158797_ && l <= this.f_158796_) {
            d0 = p_158837_.m_75380_((double)k * p_158838_, (double)l * p_158838_, (double)i1 * p_158838_);
         } else {
            d0 = 0.0D;
         }

         p_158834_[i] = d0;
      }

   }

   public BlockState m_158819_(RandomSource p_158820_, int p_158821_, int p_158822_, int p_158823_, double p_158824_, double p_158825_, double p_158826_) {
      BlockState blockstate = this.f_158798_;
      OreVeinifier.VeinType oreveinifier$veintype = this.m_158814_(p_158824_, p_158822_);
      if (oreveinifier$veintype == null) {
         return blockstate;
      } else if (p_158820_.nextFloat() > 0.7F) {
         return blockstate;
      } else if (this.m_158811_(p_158825_, p_158826_)) {
         double d0 = Mth.m_144851_(Math.abs(p_158824_), 0.5D, (double)0.6F, (double)0.1F, (double)0.3F);
         if ((double)p_158820_.nextFloat() < d0 && this.f_158802_.m_75380_((double)p_158821_, (double)p_158822_, (double)p_158823_) > (double)-0.3F) {
            return p_158820_.nextFloat() < 0.02F ? oreveinifier$veintype.f_158858_ : oreveinifier$veintype.f_158857_;
         } else {
            return oreveinifier$veintype.f_158859_;
         }
      } else {
         return blockstate;
      }
   }

   private boolean m_158811_(double p_158812_, double p_158813_) {
      double d0 = Math.abs(1.0D * p_158812_) - (double)0.08F;
      double d1 = Math.abs(1.0D * p_158813_) - (double)0.08F;
      return Math.max(d0, d1) < 0.0D;
   }

   @Nullable
   private OreVeinifier.VeinType m_158814_(double p_158815_, int p_158816_) {
      OreVeinifier.VeinType oreveinifier$veintype = p_158815_ > 0.0D ? OreVeinifier.VeinType.COPPER : OreVeinifier.VeinType.IRON;
      int i = oreveinifier$veintype.f_158861_ - p_158816_;
      int j = p_158816_ - oreveinifier$veintype.f_158860_;
      if (j >= 0 && i >= 0) {
         int k = Math.min(i, j);
         double d0 = Mth.m_144851_((double)k, 0.0D, 20.0D, -0.2D, 0.0D);
         return Math.abs(p_158815_) + d0 < 0.5D ? null : oreveinifier$veintype;
      } else {
         return null;
      }
   }

   static enum VeinType {
      COPPER(Blocks.f_152505_.m_49966_(), Blocks.f_152599_.m_49966_(), Blocks.f_50122_.m_49966_(), 0, 50),
      IRON(Blocks.f_152468_.m_49966_(), Blocks.f_152598_.m_49966_(), Blocks.f_152496_.m_49966_(), -60, -8);

      final BlockState f_158857_;
      final BlockState f_158858_;
      final BlockState f_158859_;
      final int f_158860_;
      final int f_158861_;

      private VeinType(BlockState p_158867_, BlockState p_158868_, BlockState p_158869_, int p_158870_, int p_158871_) {
         this.f_158857_ = p_158867_;
         this.f_158858_ = p_158868_;
         this.f_158859_ = p_158869_;
         this.f_158860_ = p_158870_;
         this.f_158861_ = p_158871_;
      }
   }
}