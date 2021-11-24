package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class BeehiveDecorator extends TreeDecorator {
   public static final Codec<BeehiveDecorator> f_69954_ = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BeehiveDecorator::new, (p_69971_) -> {
      return p_69971_.f_69955_;
   }).codec();
   private final float f_69955_;

   public BeehiveDecorator(float p_69958_) {
      this.f_69955_ = p_69958_;
   }

   protected TreeDecoratorType<?> m_6663_() {
      return TreeDecoratorType.f_70045_;
   }

   public void m_142741_(LevelSimulatedReader p_161710_, BiConsumer<BlockPos, BlockState> p_161711_, Random p_161712_, List<BlockPos> p_161713_, List<BlockPos> p_161714_) {
      if (!(p_161712_.nextFloat() >= this.f_69955_)) {
         Direction direction = BeehiveBlock.m_49647_(p_161712_);
         int i = !p_161714_.isEmpty() ? Math.max(p_161714_.get(0).m_123342_() - 1, p_161713_.get(0).m_123342_()) : Math.min(p_161713_.get(0).m_123342_() + 1 + p_161712_.nextInt(3), p_161713_.get(p_161713_.size() - 1).m_123342_());
         List<BlockPos> list = p_161713_.stream().filter((p_69962_) -> {
            return p_69962_.m_123342_() == i;
         }).collect(Collectors.toList());
         if (!list.isEmpty()) {
            BlockPos blockpos = list.get(p_161712_.nextInt(list.size()));
            BlockPos blockpos1 = blockpos.m_142300_(direction);
            if (Feature.m_65810_(p_161710_, blockpos1) && Feature.m_65810_(p_161710_, blockpos1.m_142300_(Direction.SOUTH))) {
               p_161711_.accept(blockpos1, Blocks.f_50717_.m_49966_().m_61124_(BeehiveBlock.f_49563_, Direction.SOUTH));
               p_161710_.m_141902_(blockpos1, BlockEntityType.f_58912_).ifPresent((p_161717_) -> {
                  int j = 2 + p_161712_.nextInt(2);

                  for(int k = 0; k < j; ++k) {
                     CompoundTag compoundtag = new CompoundTag();
                     compoundtag.m_128359_("id", Registry.f_122826_.m_7981_(EntityType.f_20550_).toString());
                     p_161717_.m_155157_(compoundtag, p_161712_.nextInt(599), false);
                  }

               });
            }
         }
      }
   }
}