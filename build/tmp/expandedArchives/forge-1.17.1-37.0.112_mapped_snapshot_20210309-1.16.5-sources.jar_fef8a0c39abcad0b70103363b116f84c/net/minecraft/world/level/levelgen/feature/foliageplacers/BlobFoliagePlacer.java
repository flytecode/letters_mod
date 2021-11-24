package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class BlobFoliagePlacer extends FoliagePlacer {
   public static final Codec<BlobFoliagePlacer> f_68392_ = RecordCodecBuilder.create((p_68427_) -> {
      return m_68413_(p_68427_).apply(p_68427_, BlobFoliagePlacer::new);
   });
   protected final int f_68393_;

   protected static <P extends BlobFoliagePlacer> P3<Mu<P>, IntProvider, IntProvider, Integer> m_68413_(Instance<P> p_68414_) {
      return m_68573_(p_68414_).and(Codec.intRange(0, 16).fieldOf("height").forGetter((p_68412_) -> {
         return p_68412_.f_68393_;
      }));
   }

   public BlobFoliagePlacer(IntProvider p_161356_, IntProvider p_161357_, int p_161358_) {
      super(p_161356_, p_161357_);
      this.f_68393_ = p_161358_;
   }

   protected FoliagePlacerType<?> m_5897_() {
      return FoliagePlacerType.f_68591_;
   }

   protected void m_142539_(LevelSimulatedReader p_161360_, BiConsumer<BlockPos, BlockState> p_161361_, Random p_161362_, TreeConfiguration p_161363_, int p_161364_, FoliagePlacer.FoliageAttachment p_161365_, int p_161366_, int p_161367_, int p_161368_) {
      for(int i = p_161368_; i >= p_161368_ - p_161366_; --i) {
         int j = Math.max(p_161367_ + p_161365_.m_68589_() - 1 - i / 2, 0);
         this.m_161437_(p_161360_, p_161361_, p_161362_, p_161363_, p_161365_.m_161451_(), j, i, p_161365_.m_68590_());
      }

   }

   public int m_5969_(Random p_68423_, int p_68424_, TreeConfiguration p_68425_) {
      return this.f_68393_;
   }

   protected boolean m_7394_(Random p_68416_, int p_68417_, int p_68418_, int p_68419_, int p_68420_, boolean p_68421_) {
      return p_68417_ == p_68420_ && p_68419_ == p_68420_ && (p_68416_.nextInt(2) == 0 || p_68418_ == 0);
   }
}