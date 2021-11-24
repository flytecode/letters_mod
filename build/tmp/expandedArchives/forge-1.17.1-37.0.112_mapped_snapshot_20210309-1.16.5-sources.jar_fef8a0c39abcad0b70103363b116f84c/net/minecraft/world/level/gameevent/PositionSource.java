package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

public interface PositionSource {
   Codec<PositionSource> f_157868_ = Registry.f_175420_.dispatch(PositionSource::m_142510_, PositionSourceType::m_142341_);

   Optional<BlockPos> m_142502_(Level p_157870_);

   PositionSourceType<?> m_142510_();
}