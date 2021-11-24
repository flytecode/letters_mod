package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class TrunkVineDecorator extends TreeDecorator {
   public static final Codec<TrunkVineDecorator> f_70055_;
   public static final TrunkVineDecorator f_70056_ = new TrunkVineDecorator();

   protected TreeDecoratorType<?> m_6663_() {
      return TreeDecoratorType.f_70042_;
   }

   public void m_142741_(LevelSimulatedReader p_161755_, BiConsumer<BlockPos, BlockState> p_161756_, Random p_161757_, List<BlockPos> p_161758_, List<BlockPos> p_161759_) {
      p_161758_.forEach((p_161764_) -> {
         if (p_161757_.nextInt(3) > 0) {
            BlockPos blockpos = p_161764_.m_142125_();
            if (Feature.m_65810_(p_161755_, blockpos)) {
               m_161750_(p_161756_, blockpos, VineBlock.f_57835_);
            }
         }

         if (p_161757_.nextInt(3) > 0) {
            BlockPos blockpos1 = p_161764_.m_142126_();
            if (Feature.m_65810_(p_161755_, blockpos1)) {
               m_161750_(p_161756_, blockpos1, VineBlock.f_57837_);
            }
         }

         if (p_161757_.nextInt(3) > 0) {
            BlockPos blockpos2 = p_161764_.m_142127_();
            if (Feature.m_65810_(p_161755_, blockpos2)) {
               m_161750_(p_161756_, blockpos2, VineBlock.f_57836_);
            }
         }

         if (p_161757_.nextInt(3) > 0) {
            BlockPos blockpos3 = p_161764_.m_142128_();
            if (Feature.m_65810_(p_161755_, blockpos3)) {
               m_161750_(p_161756_, blockpos3, VineBlock.f_57834_);
            }
         }

      });
   }

   static {
      f_70055_ = Codec.unit(() -> {
         return f_70056_;
      });
   }
}