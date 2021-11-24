package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Collection;

public class GameTestTicker {
   public static final GameTestTicker f_177648_ = new GameTestTicker();
   private final Collection<GameTestInfo> f_127784_ = Lists.newCopyOnWriteArrayList();

   public void m_127788_(GameTestInfo p_127789_) {
      this.f_127784_.add(p_127789_);
   }

   public void m_127787_() {
      this.f_127784_.clear();
   }

   public void m_127790_() {
      this.f_127784_.forEach(GameTestInfo::m_127628_);
      this.f_127784_.removeIf(GameTestInfo::m_127641_);
   }
}