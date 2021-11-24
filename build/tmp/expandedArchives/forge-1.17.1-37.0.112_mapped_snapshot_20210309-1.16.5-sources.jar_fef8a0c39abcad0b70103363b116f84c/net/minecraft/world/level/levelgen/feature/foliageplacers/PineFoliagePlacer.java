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

public class PineFoliagePlacer extends FoliagePlacer {
   public static final Codec<PineFoliagePlacer> f_68676_ = RecordCodecBuilder.create((p_68698_) -> {
      return m_68573_(p_68698_).and(IntProvider.m_146545_(0, 24).fieldOf("height").forGetter((p_161500_) -> {
         return p_161500_.f_68677_;
      })).apply(p_68698_, PineFoliagePlacer::new);
   });
   private final IntProvider f_68677_;

   public PineFoliagePlacer(IntProvider p_161486_, IntProvider p_161487_, IntProvider p_161488_) {
      super(p_161486_, p_161487_);
      this.f_68677_ = p_161488_;
   }

   protected FoliagePlacerType<?> m_5897_() {
      return FoliagePlacerType.f_68593_;
   }

   protected void m_142539_(LevelSimulatedReader p_161490_, BiConsumer<BlockPos, BlockState> p_161491_, Random p_161492_, TreeConfiguration p_161493_, int p_161494_, FoliagePlacer.FoliageAttachment p_161495_, int p_161496_, int p_161497_, int p_161498_) {
      int i = 0;

      for(int j = p_161498_; j >= p_161498_ - p_161496_; --j) {
         this.m_161437_(p_161490_, p_161491_, p_161492_, p_161493_, p_161495_.m_161451_(), i, j, p_161495_.m_68590_());
         if (i >= 1 && j == p_161498_ - p_161496_ + 1) {
            --i;
         } else if (i < p_161497_ + p_161495_.m_68589_()) {
            ++i;
         }
      }

   }

   public int m_5937_(Random p_68700_, int p_68701_) {
      return super.m_5937_(p_68700_, p_68701_) + p_68700_.nextInt(Math.max(p_68701_ + 1, 1));
   }

   public int m_5969_(Random p_68710_, int p_68711_, TreeConfiguration p_68712_) {
      return this.f_68677_.m_142270_(p_68710_);
   }

   protected boolean m_7394_(Random p_68703_, int p_68704_, int p_68705_, int p_68706_, int p_68707_, boolean p_68708_) {
      return p_68704_ == p_68707_ && p_68706_ == p_68707_ && p_68707_ > 0;
   }
}