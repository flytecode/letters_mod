package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class CocoaDecorator extends TreeDecorator {
   public static final Codec<CocoaDecorator> f_69972_ = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(CocoaDecorator::new, (p_69989_) -> {
      return p_69989_.f_69973_;
   }).codec();
   private final float f_69973_;

   public CocoaDecorator(float p_69976_) {
      this.f_69973_ = p_69976_;
   }

   protected TreeDecoratorType<?> m_6663_() {
      return TreeDecoratorType.f_70044_;
   }

   public void m_142741_(LevelSimulatedReader p_161719_, BiConsumer<BlockPos, BlockState> p_161720_, Random p_161721_, List<BlockPos> p_161722_, List<BlockPos> p_161723_) {
      if (!(p_161721_.nextFloat() >= this.f_69973_)) {
         int i = p_161722_.get(0).m_123342_();
         p_161722_.stream().filter((p_69980_) -> {
            return p_69980_.m_123342_() - i <= 2;
         }).forEach((p_161728_) -> {
            for(Direction direction : Direction.Plane.HORIZONTAL) {
               if (p_161721_.nextFloat() <= 0.25F) {
                  Direction direction1 = direction.m_122424_();
                  BlockPos blockpos = p_161728_.m_142082_(direction1.m_122429_(), 0, direction1.m_122431_());
                  if (Feature.m_65810_(p_161719_, blockpos)) {
                     p_161720_.accept(blockpos, Blocks.f_50262_.m_49966_().m_61124_(CocoaBlock.f_51736_, Integer.valueOf(p_161721_.nextInt(3))).m_61124_(CocoaBlock.f_54117_, direction));
                  }
               }
            }

         });
      }
   }
}