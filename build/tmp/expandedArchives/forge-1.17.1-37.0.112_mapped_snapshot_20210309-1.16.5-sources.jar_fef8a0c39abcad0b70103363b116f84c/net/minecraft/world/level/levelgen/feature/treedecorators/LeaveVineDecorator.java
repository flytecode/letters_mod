package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.Feature;

public class LeaveVineDecorator extends TreeDecorator {
   public static final Codec<LeaveVineDecorator> f_69996_;
   public static final LeaveVineDecorator f_69997_ = new LeaveVineDecorator();

   protected TreeDecoratorType<?> m_6663_() {
      return TreeDecoratorType.f_70043_;
   }

   public void m_142741_(LevelSimulatedReader p_161735_, BiConsumer<BlockPos, BlockState> p_161736_, Random p_161737_, List<BlockPos> p_161738_, List<BlockPos> p_161739_) {
      p_161739_.forEach((p_161744_) -> {
         if (p_161737_.nextInt(4) == 0) {
            BlockPos blockpos = p_161744_.m_142125_();
            if (Feature.m_65810_(p_161735_, blockpos)) {
               m_161729_(p_161735_, blockpos, VineBlock.f_57835_, p_161736_);
            }
         }

         if (p_161737_.nextInt(4) == 0) {
            BlockPos blockpos1 = p_161744_.m_142126_();
            if (Feature.m_65810_(p_161735_, blockpos1)) {
               m_161729_(p_161735_, blockpos1, VineBlock.f_57837_, p_161736_);
            }
         }

         if (p_161737_.nextInt(4) == 0) {
            BlockPos blockpos2 = p_161744_.m_142127_();
            if (Feature.m_65810_(p_161735_, blockpos2)) {
               m_161729_(p_161735_, blockpos2, VineBlock.f_57836_, p_161736_);
            }
         }

         if (p_161737_.nextInt(4) == 0) {
            BlockPos blockpos3 = p_161744_.m_142128_();
            if (Feature.m_65810_(p_161735_, blockpos3)) {
               m_161729_(p_161735_, blockpos3, VineBlock.f_57834_, p_161736_);
            }
         }

      });
   }

   private static void m_161729_(LevelSimulatedReader p_161730_, BlockPos p_161731_, BooleanProperty p_161732_, BiConsumer<BlockPos, BlockState> p_161733_) {
      m_161750_(p_161733_, p_161731_, p_161732_);
      int i = 4;

      for(BlockPos blockpos = p_161731_.m_7495_(); Feature.m_65810_(p_161730_, blockpos) && i > 0; --i) {
         m_161750_(p_161733_, blockpos, p_161732_);
         blockpos = blockpos.m_7495_();
      }

   }

   static {
      f_69996_ = Codec.unit(() -> {
         return f_69997_;
      });
   }
}