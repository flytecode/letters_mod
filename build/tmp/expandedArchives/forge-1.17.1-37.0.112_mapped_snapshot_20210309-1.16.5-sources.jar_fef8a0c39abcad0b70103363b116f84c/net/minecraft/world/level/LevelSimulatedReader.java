package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;

public interface LevelSimulatedReader {
   boolean m_7433_(BlockPos p_46938_, Predicate<BlockState> p_46939_);

   boolean m_142433_(BlockPos p_151584_, Predicate<FluidState> p_151585_);

   <T extends BlockEntity> Optional<T> m_141902_(BlockPos p_151582_, BlockEntityType<T> p_151583_);

   BlockPos m_5452_(Heightmap.Types p_46936_, BlockPos p_46937_);
}