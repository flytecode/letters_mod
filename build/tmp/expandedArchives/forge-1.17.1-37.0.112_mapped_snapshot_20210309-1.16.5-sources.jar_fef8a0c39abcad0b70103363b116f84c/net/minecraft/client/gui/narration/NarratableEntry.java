package net.minecraft.client.gui.narration;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface NarratableEntry extends NarrationSupplier {
   NarratableEntry.NarrationPriority m_142684_();

   default boolean m_142518_() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum NarrationPriority {
      NONE,
      HOVERED,
      FOCUSED;

      public boolean m_169123_() {
         return this == FOCUSED;
      }
   }
}