package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
   private static final Component f_169039_ = new TranslatableComponent("narration.selection.usage");
   private boolean f_94440_;

   public ObjectSelectionList(Minecraft p_94442_, int p_94443_, int p_94444_, int p_94445_, int p_94446_, int p_94447_) {
      super(p_94442_, p_94443_, p_94444_, p_94445_, p_94446_, p_94447_);
   }

   public boolean m_5755_(boolean p_94449_) {
      if (!this.f_94440_ && this.m_5773_() == 0) {
         return false;
      } else {
         this.f_94440_ = !this.f_94440_;
         if (this.f_94440_ && this.m_93511_() == null && this.m_5773_() > 0) {
            this.m_6778_(AbstractSelectionList.SelectionDirection.DOWN);
         } else if (this.f_94440_ && this.m_93511_() != null) {
            this.m_93519_();
         }

         return this.f_94440_;
      }
   }

   public void m_142291_(NarrationElementOutput p_169042_) {
      E e = this.m_168795_();
      if (e != null) {
         this.m_168790_(p_169042_.m_142047_(), e);
         e.m_142291_(p_169042_);
      } else {
         E e1 = this.m_93511_();
         if (e1 != null) {
            this.m_168790_(p_169042_.m_142047_(), e1);
            e1.m_142291_(p_169042_);
         }
      }

      if (this.m_5694_()) {
         p_169042_.m_169146_(NarratedElementType.USAGE, f_169039_);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public abstract static class Entry<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList.Entry<E> implements NarrationSupplier {
      public boolean m_5755_(boolean p_94452_) {
         return false;
      }

      public abstract Component m_142172_();

      public void m_142291_(NarrationElementOutput p_169044_) {
         p_169044_.m_169146_(NarratedElementType.TITLE, this.m_142172_());
      }
   }
}