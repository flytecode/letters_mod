package net.minecraft.world.level.levelgen.feature.treedecorators;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class TreeDecorator {
   public static final Codec<TreeDecorator> f_70021_ = Registry.f_122860_.dispatch(TreeDecorator::m_6663_, TreeDecoratorType::m_70051_);

   protected abstract TreeDecoratorType<?> m_6663_();

   public abstract void m_142741_(LevelSimulatedReader p_161745_, BiConsumer<BlockPos, BlockState> p_161746_, Random p_161747_, List<BlockPos> p_161748_, List<BlockPos> p_161749_);

   protected static void m_161750_(BiConsumer<BlockPos, BlockState> p_161751_, BlockPos p_161752_, BooleanProperty p_161753_) {
      p_161751_.accept(p_161752_, Blocks.f_50191_.m_49966_().m_61124_(p_161753_, Boolean.valueOf(true)));
   }
}