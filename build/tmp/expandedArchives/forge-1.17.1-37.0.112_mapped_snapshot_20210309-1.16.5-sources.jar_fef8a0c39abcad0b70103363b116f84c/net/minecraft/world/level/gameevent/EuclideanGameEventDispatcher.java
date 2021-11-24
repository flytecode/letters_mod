package net.minecraft.world.level.gameevent;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EuclideanGameEventDispatcher implements GameEventDispatcher {
   private final List<GameEventListener> f_157750_ = Lists.newArrayList();
   private final Level f_157751_;

   public EuclideanGameEventDispatcher(Level p_157753_) {
      this.f_157751_ = p_157753_;
   }

   public boolean m_142086_() {
      return this.f_157750_.isEmpty();
   }

   public void m_142501_(GameEventListener p_157766_) {
      this.f_157750_.add(p_157766_);
      DebugPackets.m_179507_(this.f_157751_, p_157766_);
   }

   public void m_142500_(GameEventListener p_157768_) {
      this.f_157750_.remove(p_157768_);
   }

   public void m_142666_(GameEvent p_157762_, @Nullable Entity p_157763_, BlockPos p_157764_) {
      boolean flag = false;

      for(GameEventListener gameeventlistener : this.f_157750_) {
         if (this.m_157755_(this.f_157751_, p_157762_, p_157763_, p_157764_, gameeventlistener)) {
            flag = true;
         }
      }

      if (flag) {
         DebugPackets.m_179503_(this.f_157751_, p_157762_, p_157764_);
      }

   }

   private boolean m_157755_(Level p_157756_, GameEvent p_157757_, @Nullable Entity p_157758_, BlockPos p_157759_, GameEventListener p_157760_) {
      Optional<BlockPos> optional = p_157760_.m_142460_().m_142502_(p_157756_);
      if (!optional.isPresent()) {
         return false;
      } else {
         double d0 = optional.get().m_175582_(p_157759_, false);
         int i = p_157760_.m_142078_() * p_157760_.m_142078_();
         return d0 <= (double)i && p_157760_.m_142721_(p_157756_, p_157757_, p_157758_, p_157759_);
      }
   }
}