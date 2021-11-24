package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.LecternMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LecternScreen extends BookViewScreen implements MenuAccess<LecternMenu> {
   private final LecternMenu f_99017_;
   private final ContainerListener f_99018_ = new ContainerListener() {
      public void m_7934_(AbstractContainerMenu p_99054_, int p_99055_, ItemStack p_99056_) {
         LecternScreen.this.m_99044_();
      }

      public void m_142153_(AbstractContainerMenu p_169772_, int p_169773_, int p_169774_) {
         if (p_169773_ == 0) {
            LecternScreen.this.m_99045_();
         }

      }
   };

   public LecternScreen(LecternMenu p_99020_, Inventory p_99021_, Component p_99022_) {
      this.f_99017_ = p_99020_;
   }

   public LecternMenu m_6262_() {
      return this.f_99017_;
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_99017_.m_38893_(this.f_99018_);
   }

   public void m_7379_() {
      this.f_96541_.f_91074_.m_6915_();
      super.m_7379_();
   }

   public void m_7861_() {
      super.m_7861_();
      this.f_99017_.m_38943_(this.f_99018_);
   }

   protected void m_7829_() {
      if (this.f_96541_.f_91074_.m_36326_()) {
         this.m_142416_(new Button(this.f_96543_ / 2 - 100, 196, 98, 20, CommonComponents.f_130655_, (p_99033_) -> {
            this.m_7379_();
         }));
         this.m_142416_(new Button(this.f_96543_ / 2 + 2, 196, 98, 20, new TranslatableComponent("lectern.take_book"), (p_99024_) -> {
            this.m_99036_(3);
         }));
      } else {
         super.m_7829_();
      }

   }

   protected void m_7811_() {
      this.m_99036_(1);
   }

   protected void m_7815_() {
      this.m_99036_(2);
   }

   protected boolean m_7735_(int p_99031_) {
      if (p_99031_ != this.f_99017_.m_39836_()) {
         this.m_99036_(100 + p_99031_);
         return true;
      } else {
         return false;
      }
   }

   private void m_99036_(int p_99037_) {
      this.f_96541_.f_91072_.m_105208_(this.f_99017_.f_38840_, p_99037_);
   }

   public boolean m_7043_() {
      return false;
   }

   void m_99044_() {
      ItemStack itemstack = this.f_99017_.m_39835_();
      this.m_98288_(BookViewScreen.BookAccess.m_98308_(itemstack));
   }

   void m_99045_() {
      this.m_98275_(this.f_99017_.m_39836_());
   }

   protected void m_141919_() {
      this.f_96541_.f_91074_.m_6915_();
   }
}