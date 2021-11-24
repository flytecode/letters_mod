package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AlterGroundDecorator extends TreeDecorator {
   public static final Codec<AlterGroundDecorator> f_69302_ = BlockStateProvider.f_68747_.fieldOf("provider").xmap(AlterGroundDecorator::new, (p_69327_) -> {
      return p_69327_.f_69303_;
   }).codec();
   private final BlockStateProvider f_69303_;

   public AlterGroundDecorator(BlockStateProvider p_69306_) {
      this.f_69303_ = p_69306_;
   }

   protected TreeDecoratorType<?> m_6663_() {
      return TreeDecoratorType.f_70046_;
   }

   public void m_142741_(LevelSimulatedReader p_161694_, BiConsumer<BlockPos, BlockState> p_161695_, Random p_161696_, List<BlockPos> p_161697_, List<BlockPos> p_161698_) {
      if (!p_161697_.isEmpty()) {
         int i = p_161697_.get(0).m_123342_();
         p_161697_.stream().filter((p_69310_) -> {
            return p_69310_.m_123342_() == i;
         }).forEach((p_161708_) -> {
            this.m_161688_(p_161694_, p_161695_, p_161696_, p_161708_.m_142125_().m_142127_());
            this.m_161688_(p_161694_, p_161695_, p_161696_, p_161708_.m_142385_(2).m_142127_());
            this.m_161688_(p_161694_, p_161695_, p_161696_, p_161708_.m_142125_().m_142383_(2));
            this.m_161688_(p_161694_, p_161695_, p_161696_, p_161708_.m_142385_(2).m_142383_(2));

            for(int j = 0; j < 5; ++j) {
               int k = p_161696_.nextInt(64);
               int l = k % 8;
               int i1 = k / 8;
               if (l == 0 || l == 7 || i1 == 0 || i1 == 7) {
                  this.m_161688_(p_161694_, p_161695_, p_161696_, p_161708_.m_142082_(-3 + l, 0, -3 + i1));
               }
            }

         });
      }
   }

   private void m_161688_(LevelSimulatedReader p_161689_, BiConsumer<BlockPos, BlockState> p_161690_, Random p_161691_, BlockPos p_161692_) {
      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            if (Math.abs(i) != 2 || Math.abs(j) != 2) {
               this.m_161699_(p_161689_, p_161690_, p_161691_, p_161692_.m_142082_(i, 0, j));
            }
         }
      }

   }

   private void m_161699_(LevelSimulatedReader p_161700_, BiConsumer<BlockPos, BlockState> p_161701_, Random p_161702_, BlockPos p_161703_) {
      for(int i = 2; i >= -3; --i) {
         BlockPos blockpos = p_161703_.m_6630_(i);
         if (Feature.m_65788_(p_161700_, blockpos)) {
            p_161701_.accept(blockpos, this.f_69303_.m_7112_(p_161702_, p_161703_));
            break;
         }

         if (!Feature.m_65810_(p_161700_, blockpos) && i < 0) {
            break;
         }
      }

   }
}