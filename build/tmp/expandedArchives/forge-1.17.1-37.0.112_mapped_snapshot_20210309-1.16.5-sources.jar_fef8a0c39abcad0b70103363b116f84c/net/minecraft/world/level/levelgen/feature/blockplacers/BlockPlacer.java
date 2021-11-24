package net.minecraft.world.level.levelgen.feature.blockplacers;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockPlacer {
   public static final Codec<BlockPlacer> f_67480_ = Registry.f_122857_.dispatch(BlockPlacer::m_7070_, BlockPlacerType::m_67494_);

   public abstract void m_7275_(LevelAccessor p_67483_, BlockPos p_67484_, BlockState p_67485_, Random p_67486_);

   protected abstract BlockPlacerType<?> m_7070_();
}