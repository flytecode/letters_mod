package net.minecraft.world.level.gameevent;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public interface GameEventDispatcher {
   GameEventDispatcher f_157829_ = new GameEventDispatcher() {
      public boolean m_142086_() {
         return true;
      }

      public void m_142501_(GameEventListener p_157843_) {
      }

      public void m_142500_(GameEventListener p_157845_) {
      }

      public void m_142666_(GameEvent p_157839_, @Nullable Entity p_157840_, BlockPos p_157841_) {
      }
   };

   boolean m_142086_();

   void m_142501_(GameEventListener p_157834_);

   void m_142500_(GameEventListener p_157835_);

   void m_142666_(GameEvent p_157831_, @Nullable Entity p_157832_, BlockPos p_157833_);
}