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

public class DarkOakFoliagePlacer extends FoliagePlacer {
   public static final Codec<DarkOakFoliagePlacer> f_68455_ = RecordCodecBuilder.create((p_68473_) -> {
      return m_68573_(p_68473_).apply(p_68473_, DarkOakFoliagePlacer::new);
   });

   public DarkOakFoliagePlacer(IntProvider p_161384_, IntProvider p_161385_) {
      super(p_161384_, p_161385_);
   }

   protected FoliagePlacerType<?> m_5897_() {
      return FoliagePlacerType.f_68599_;
   }

   protected void m_142539_(LevelSimulatedReader p_161387_, BiConsumer<BlockPos, BlockState> p_161388_, Random p_161389_, TreeConfiguration p_161390_, int p_161391_, FoliagePlacer.FoliageAttachment p_161392_, int p_161393_, int p_161394_, int p_161395_) {
      BlockPos blockpos = p_161392_.m_161451_().m_6630_(p_161395_);
      boolean flag = p_161392_.m_68590_();
      if (flag) {
         this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_ + 2, -1, flag);
         this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_ + 3, 0, flag);
         this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_ + 2, 1, flag);
         if (p_161389_.nextBoolean()) {
            this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_, 2, flag);
         }
      } else {
         this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_ + 2, -1, flag);
         this.m_161437_(p_161387_, p_161388_, p_161389_, p_161390_, blockpos, p_161394_ + 1, 0, flag);
      }

   }

   public int m_5969_(Random p_68482_, int p_68483_, TreeConfiguration p_68484_) {
      return 4;
   }

   protected boolean m_7395_(Random p_68486_, int p_68487_, int p_68488_, int p_68489_, int p_68490_, boolean p_68491_) {
      return p_68488_ != 0 || !p_68491_ || p_68487_ != -p_68490_ && p_68487_ < p_68490_ || p_68489_ != -p_68490_ && p_68489_ < p_68490_ ? super.m_7395_(p_68486_, p_68487_, p_68488_, p_68489_, p_68490_, p_68491_) : true;
   }

   protected boolean m_7394_(Random p_68475_, int p_68476_, int p_68477_, int p_68478_, int p_68479_, boolean p_68480_) {
      if (p_68477_ == -1 && !p_68480_) {
         return p_68476_ == p_68479_ && p_68478_ == p_68479_;
      } else if (p_68477_ == 1) {
         return p_68476_ + p_68478_ > p_68479_ * 2 - 2;
      } else {
         return false;
      }
   }
}