package net.minecraft.world.level.gameevent;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface GameEventListener {
   PositionSource m_142460_();

   int m_142078_();

   boolean m_142721_(Level p_157846_, GameEvent p_157847_, @Nullable Entity p_157848_, BlockPos p_157849_);
}