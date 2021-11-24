package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.categories.SpectatorPage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpectatorMenu {
   private static final SpectatorMenuItem f_101772_ = new SpectatorMenu.CloseSpectatorItem();
   private static final SpectatorMenuItem f_101773_ = new SpectatorMenu.ScrollMenuItem(-1, true);
   private static final SpectatorMenuItem f_101774_ = new SpectatorMenu.ScrollMenuItem(1, true);
   private static final SpectatorMenuItem f_101775_ = new SpectatorMenu.ScrollMenuItem(1, false);
   private static final int f_170328_ = 8;
   static final Component f_101776_ = new TranslatableComponent("spectatorMenu.close");
   static final Component f_101777_ = new TranslatableComponent("spectatorMenu.previous_page");
   static final Component f_101778_ = new TranslatableComponent("spectatorMenu.next_page");
   public static final SpectatorMenuItem f_101771_ = new SpectatorMenuItem() {
      public void m_7608_(SpectatorMenu p_101812_) {
      }

      public Component m_7869_() {
         return TextComponent.f_131282_;
      }

      public void m_6252_(PoseStack p_101808_, float p_101809_, int p_101810_) {
      }

      public boolean m_7304_() {
         return false;
      }
   };
   private final SpectatorMenuListener f_101779_;
   private SpectatorMenuCategory f_101780_;
   private int f_101781_ = -1;
   int f_101782_;

   public SpectatorMenu(SpectatorMenuListener p_101785_) {
      this.f_101780_ = new RootSpectatorMenuCategory();
      this.f_101779_ = p_101785_;
   }

   public SpectatorMenuItem m_101787_(int p_101788_) {
      int i = p_101788_ + this.f_101782_ * 6;
      if (this.f_101782_ > 0 && p_101788_ == 0) {
         return f_101773_;
      } else if (p_101788_ == 7) {
         return i < this.f_101780_.m_5919_().size() ? f_101774_ : f_101775_;
      } else if (p_101788_ == 8) {
         return f_101772_;
      } else {
         return i >= 0 && i < this.f_101780_.m_5919_().size() ? MoreObjects.firstNonNull(this.f_101780_.m_5919_().get(i), f_101771_) : f_101771_;
      }
   }

   public List<SpectatorMenuItem> m_101786_() {
      List<SpectatorMenuItem> list = Lists.newArrayList();

      for(int i = 0; i <= 8; ++i) {
         list.add(this.m_101787_(i));
      }

      return list;
   }

   public SpectatorMenuItem m_101796_() {
      return this.m_101787_(this.f_101781_);
   }

   public SpectatorMenuCategory m_101799_() {
      return this.f_101780_;
   }

   public void m_101797_(int p_101798_) {
      SpectatorMenuItem spectatormenuitem = this.m_101787_(p_101798_);
      if (spectatormenuitem != f_101771_) {
         if (this.f_101781_ == p_101798_ && spectatormenuitem.m_7304_()) {
            spectatormenuitem.m_7608_(this);
         } else {
            this.f_101781_ = p_101798_;
         }
      }

   }

   public void m_101800_() {
      this.f_101779_.m_7613_(this);
   }

   public int m_101801_() {
      return this.f_101781_;
   }

   public void m_101794_(SpectatorMenuCategory p_101795_) {
      this.f_101780_ = p_101795_;
      this.f_101781_ = -1;
      this.f_101782_ = 0;
   }

   public SpectatorPage m_101802_() {
      return new SpectatorPage(this.m_101786_(), this.f_101781_);
   }

   @OnlyIn(Dist.CLIENT)
   static class CloseSpectatorItem implements SpectatorMenuItem {
      public void m_7608_(SpectatorMenu p_101823_) {
         p_101823_.m_101800_();
      }

      public Component m_7869_() {
         return SpectatorMenu.f_101776_;
      }

      public void m_6252_(PoseStack p_101819_, float p_101820_, int p_101821_) {
         RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
         GuiComponent.m_93133_(p_101819_, 0, 0, 128.0F, 0.0F, 16, 16, 256, 256);
      }

      public boolean m_7304_() {
         return true;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ScrollMenuItem implements SpectatorMenuItem {
      private final int f_101826_;
      private final boolean f_101827_;

      public ScrollMenuItem(int p_101829_, boolean p_101830_) {
         this.f_101826_ = p_101829_;
         this.f_101827_ = p_101830_;
      }

      public void m_7608_(SpectatorMenu p_101836_) {
         p_101836_.f_101782_ += this.f_101826_;
      }

      public Component m_7869_() {
         return this.f_101826_ < 0 ? SpectatorMenu.f_101777_ : SpectatorMenu.f_101778_;
      }

      public void m_6252_(PoseStack p_101832_, float p_101833_, int p_101834_) {
         RenderSystem.m_157456_(0, SpectatorGui.f_94760_);
         if (this.f_101826_ < 0) {
            GuiComponent.m_93133_(p_101832_, 0, 0, 144.0F, 0.0F, 16, 16, 256, 256);
         } else {
            GuiComponent.m_93133_(p_101832_, 0, 0, 160.0F, 0.0F, 16, 16, 256, 256);
         }

      }

      public boolean m_7304_() {
         return this.f_101827_;
      }
   }
}