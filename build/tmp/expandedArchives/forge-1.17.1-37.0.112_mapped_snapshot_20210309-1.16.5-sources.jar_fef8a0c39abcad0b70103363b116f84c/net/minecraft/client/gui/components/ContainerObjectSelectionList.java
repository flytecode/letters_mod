package net.minecraft.client.gui.components;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ContainerObjectSelectionList<E extends ContainerObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
   private boolean f_168849_;

   public ContainerObjectSelectionList(Minecraft p_94010_, int p_94011_, int p_94012_, int p_94013_, int p_94014_, int p_94015_) {
      super(p_94010_, p_94011_, p_94012_, p_94013_, p_94014_, p_94015_);
   }

   public boolean m_5755_(boolean p_94017_) {
      this.f_168849_ = super.m_5755_(p_94017_);
      if (this.f_168849_) {
         this.m_93498_(this.m_7222_());
      }

      return this.f_168849_;
   }

   public NarratableEntry.NarrationPriority m_142684_() {
      return this.f_168849_ ? NarratableEntry.NarrationPriority.FOCUSED : super.m_142684_();
   }

   protected boolean m_7987_(int p_94019_) {
      return false;
   }

   public void m_142291_(NarrationElementOutput p_168851_) {
      E e = this.m_168795_();
      if (e != null) {
         e.m_168854_(p_168851_.m_142047_());
         this.m_168790_(p_168851_, e);
      } else {
         E e1 = this.m_7222_();
         if (e1 != null) {
            e1.m_168854_(p_168851_.m_142047_());
            this.m_168790_(p_168851_, e1);
         }
      }

      p_168851_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry<E extends ContainerObjectSelectionList.Entry<E>> extends AbstractSelectionList.Entry<E> implements ContainerEventHandler {
      @Nullable
      private GuiEventListener f_94020_;
      @Nullable
      private NarratableEntry f_168853_;
      private boolean f_94021_;

      public boolean m_7282_() {
         return this.f_94021_;
      }

      public void m_7897_(boolean p_94028_) {
         this.f_94021_ = p_94028_;
      }

      public void m_7522_(@Nullable GuiEventListener p_94024_) {
         this.f_94020_ = p_94024_;
      }

      @Nullable
      public GuiEventListener m_7222_() {
         return this.f_94020_;
      }

      public abstract List<? extends NarratableEntry> m_142437_();

      void m_168854_(NarrationElementOutput p_168855_) {
         List<? extends NarratableEntry> list = this.m_142437_();
         Screen.NarratableSearchResult screen$narratablesearchresult = Screen.m_169400_(list, this.f_168853_);
         if (screen$narratablesearchresult != null) {
            if (screen$narratablesearchresult.f_169422_.m_169123_()) {
               this.f_168853_ = screen$narratablesearchresult.f_169420_;
            }

            if (list.size() > 1) {
               p_168855_.m_169146_(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.object_list", screen$narratablesearchresult.f_169421_ + 1, list.size()));
               if (screen$narratablesearchresult.f_169422_ == NarratableEntry.NarrationPriority.FOCUSED) {
                  p_168855_.m_169146_(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
               }
            }

            screen$narratablesearchresult.f_169420_.m_142291_(p_168855_.m_142047_());
         }

      }
   }
}