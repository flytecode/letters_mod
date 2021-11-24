package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

interface IndexMerger {
   DoubleList m_6241_();

   boolean m_6200_(IndexMerger.IndexConsumer p_82907_);

   int size();

   public interface IndexConsumer {
      boolean m_82908_(int p_82909_, int p_82910_, int p_82911_);
   }
}