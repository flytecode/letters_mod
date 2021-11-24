package net.minecraft.client.gui.components.events;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ContainerEventHandler extends GuiEventListener {
   List<? extends GuiEventListener> m_6702_();

   default Optional<GuiEventListener> m_94729_(double p_94730_, double p_94731_) {
      for(GuiEventListener guieventlistener : this.m_6702_()) {
         if (guieventlistener.m_5953_(p_94730_, p_94731_)) {
            return Optional.of(guieventlistener);
         }
      }

      return Optional.empty();
   }

   default boolean m_6375_(double p_94695_, double p_94696_, int p_94697_) {
      for(GuiEventListener guieventlistener : this.m_6702_()) {
         if (guieventlistener.m_6375_(p_94695_, p_94696_, p_94697_)) {
            this.m_7522_(guieventlistener);
            if (p_94697_ == 0) {
               this.m_7897_(true);
            }

            return true;
         }
      }

      return false;
   }

   default boolean m_6348_(double p_94722_, double p_94723_, int p_94724_) {
      this.m_7897_(false);
      return this.m_94729_(p_94722_, p_94723_).filter((p_94708_) -> {
         return p_94708_.m_6348_(p_94722_, p_94723_, p_94724_);
      }).isPresent();
   }

   default boolean m_7979_(double p_94699_, double p_94700_, int p_94701_, double p_94702_, double p_94703_) {
      return this.m_7222_() != null && this.m_7282_() && p_94701_ == 0 ? this.m_7222_().m_7979_(p_94699_, p_94700_, p_94701_, p_94702_, p_94703_) : false;
   }

   boolean m_7282_();

   void m_7897_(boolean p_94720_);

   default boolean m_6050_(double p_94686_, double p_94687_, double p_94688_) {
      return this.m_94729_(p_94686_, p_94687_).filter((p_94693_) -> {
         return p_94693_.m_6050_(p_94686_, p_94687_, p_94688_);
      }).isPresent();
   }

   default boolean m_7933_(int p_94710_, int p_94711_, int p_94712_) {
      return this.m_7222_() != null && this.m_7222_().m_7933_(p_94710_, p_94711_, p_94712_);
   }

   default boolean m_7920_(int p_94715_, int p_94716_, int p_94717_) {
      return this.m_7222_() != null && this.m_7222_().m_7920_(p_94715_, p_94716_, p_94717_);
   }

   default boolean m_5534_(char p_94683_, int p_94684_) {
      return this.m_7222_() != null && this.m_7222_().m_5534_(p_94683_, p_94684_);
   }

   @Nullable
   GuiEventListener m_7222_();

   void m_7522_(@Nullable GuiEventListener p_94713_);

   default void m_94718_(@Nullable GuiEventListener p_94719_) {
      this.m_7522_(p_94719_);
      p_94719_.m_5755_(true);
   }

   default void m_94725_(@Nullable GuiEventListener p_94726_) {
      this.m_7522_(p_94726_);
   }

   default boolean m_5755_(boolean p_94728_) {
      GuiEventListener guieventlistener = this.m_7222_();
      boolean flag = guieventlistener != null;
      if (flag && guieventlistener.m_5755_(p_94728_)) {
         return true;
      } else {
         List<? extends GuiEventListener> list = this.m_6702_();
         int j = list.indexOf(guieventlistener);
         int i;
         if (flag && j >= 0) {
            i = j + (p_94728_ ? 1 : 0);
         } else if (p_94728_) {
            i = 0;
         } else {
            i = list.size();
         }

         ListIterator<? extends GuiEventListener> listiterator = list.listIterator(i);
         BooleanSupplier booleansupplier = p_94728_ ? listiterator::hasNext : listiterator::hasPrevious;
         Supplier<? extends GuiEventListener> supplier = p_94728_ ? listiterator::next : listiterator::previous;

         while(booleansupplier.getAsBoolean()) {
            GuiEventListener guieventlistener1 = supplier.get();
            if (guieventlistener1.m_5755_(p_94728_)) {
               this.m_7522_(guieventlistener1);
               return true;
            }
         }

         this.m_7522_((GuiEventListener)null);
         return false;
      }
   }
}