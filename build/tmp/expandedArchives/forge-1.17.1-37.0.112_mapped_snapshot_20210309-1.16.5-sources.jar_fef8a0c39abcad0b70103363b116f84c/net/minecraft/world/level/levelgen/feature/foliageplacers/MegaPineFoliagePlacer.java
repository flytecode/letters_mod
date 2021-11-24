package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class MegaPineFoliagePlacer extends FoliagePlacer {
   public static final Codec<MegaPineFoliagePlacer> f_68642_ = RecordCodecBuilder.create((p_68664_) -> {
      return m_68573_(p_68664_).and(IntProvider.m_146545_(0, 24).fieldOf("crown_height").forGetter((p_161484_) -> {
         return p_161484_.f_68643_;
      })).apply(p_68664_, MegaPineFoliagePlacer::new);
   });
   private final IntProvider f_68643_;

   public MegaPineFoliagePlacer(IntProvider p_161470_, IntProvider p_161471_, IntProvider p_161472_) {
      super(p_161470_, p_161471_);
      this.f_68643_ = p_161472_;
   }

   protected FoliagePlacerType<?> m_5897_() {
      return FoliagePlacerType.f_68598_;
   }

   protected void m_142539_(LevelSimulatedReader p_161474_, BiConsumer<BlockPos, BlockState> p_161475_, Random p_161476_, TreeConfiguration p_161477_, int p_161478_, FoliagePlacer.FoliageAttachment p_161479_, int p_161480_, int p_161481_, int p_161482_) {
      BlockPos blockpos = p_161479_.m_161451_();
      int i = 0;

      for(int j = blockpos.m_123342_() - p_161480_ + p_161482_; j <= blockpos.m_123342_() + p_161482_; ++j) {
         int k = blockpos.m_123342_() - j;
         int l = p_161481_ + p_161479_.m_68589_() + Mth.m_14143_((float)k / (float)p_161480_ * 3.5F);
         int i1;
         if (k > 0 && l == i && (j & 1) == 0) {
            i1 = l + 1;
         } else {
            i1 = l;
         }

         this.m_161437_(p_161474_, p_161475_, p_161476_, p_161477_, new BlockPos(blockpos.m_123341_(), j, blockpos.m_123343_()), i1, 0, p_161479_.m_68590_());
         i = l;
      }

   }

   public int m_5969_(Random p_68673_, int p_68674_, TreeConfiguration p_68675_) {
      return this.f_68643_.m_142270_(p_68673_);
   }

   protected boolean m_7394_(Random p_68666_, int p_68667_, int p_68668_, int p_68669_, int p_68670_, boolean p_68671_) {
      if (p_68667_ + p_68669_ >= 7) {
         return true;
      } else {
         return p_68667_ * p_68667_ + p_68669_ * p_68669_ > p_68670_ * p_68670_;
      }
   }
}