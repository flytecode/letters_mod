package net.minecraft.client.gui.narration;

import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface NarrationElementOutput {
   default void m_169146_(NarratedElementType p_169147_, Component p_169148_) {
      this.m_142549_(p_169147_, NarrationThunk.m_169160_(p_169148_.getString()));
   }

   default void m_169143_(NarratedElementType p_169144_, String p_169145_) {
      this.m_142549_(p_169144_, NarrationThunk.m_169160_(p_169145_));
   }

   default void m_169149_(NarratedElementType p_169150_, Component... p_169151_) {
      this.m_142549_(p_169150_, NarrationThunk.m_169162_(ImmutableList.copyOf(p_169151_)));
   }

   void m_142549_(NarratedElementType p_169141_, NarrationThunk<?> p_169142_);

   NarrationElementOutput m_142047_();
}