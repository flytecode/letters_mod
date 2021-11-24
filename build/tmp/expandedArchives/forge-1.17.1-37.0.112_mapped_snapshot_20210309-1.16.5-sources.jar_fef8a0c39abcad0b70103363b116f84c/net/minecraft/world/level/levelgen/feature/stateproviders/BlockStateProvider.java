package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockStateProvider {
   public static final Codec<BlockStateProvider> f_68747_ = Registry.f_122856_.dispatch(BlockStateProvider::m_5923_, BlockStateProviderType::m_68761_);

   protected abstract BlockStateProviderType<?> m_5923_();

   public abstract BlockState m_7112_(Random p_68750_, BlockPos p_68751_);
}