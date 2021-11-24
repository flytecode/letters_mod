package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class RandomSpreadFoliagePlacer extends FoliagePlacer {
   public static final Codec<RandomSpreadFoliagePlacer> f_161501_ = RecordCodecBuilder.create((p_161522_) -> {
      return m_68573_(p_161522_).and(p_161522_.group(IntProvider.m_146545_(1, 512).fieldOf("foliage_height").forGetter((p_161537_) -> {
         return p_161537_.f_161502_;
      }), Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter((p_161524_) -> {
         return p_161524_.f_161503_;
      }))).apply(p_161522_, RandomSpreadFoliagePlacer::new);
   });
   private final IntProvider f_161502_;
   private final int f_161503_;

   public RandomSpreadFoliagePlacer(IntProvider p_161506_, IntProvider p_161507_, IntProvider p_161508_, int p_161509_) {
      super(p_161506_, p_161507_);
      this.f_161502_ = p_161508_;
      this.f_161503_ = p_161509_;
   }

   protected FoliagePlacerType<?> m_5897_() {
      return FoliagePlacerType.f_161452_;
   }

   protected void m_142539_(LevelSimulatedReader p_161512_, BiConsumer<BlockPos, BlockState> p_161513_, Random p_161514_, TreeConfiguration p_161515_, int p_161516_, FoliagePlacer.FoliageAttachment p_161517_, int p_161518_, int p_161519_, int p_161520_) {
      BlockPos blockpos = p_161517_.m_161451_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();

      for(int i = 0; i < this.f_161503_; ++i) {
         blockpos$mutableblockpos.m_122154_(blockpos, p_161514_.nextInt(p_161519_) - p_161514_.nextInt(p_161519_), p_161514_.nextInt(p_161518_) - p_161514_.nextInt(p_161518_), p_161514_.nextInt(p_161519_) - p_161514_.nextInt(p_161519_));
         m_161431_(p_161512_, p_161513_, p_161514_, p_161515_, blockpos$mutableblockpos);
      }

   }

   public int m_5969_(Random p_161533_, int p_161534_, TreeConfiguration p_161535_) {
      return this.f_161502_.m_142270_(p_161533_);
   }

   protected boolean m_7394_(Random p_161526_, int p_161527_, int p_161528_, int p_161529_, int p_161530_, boolean p_161531_) {
      return false;
   }
}