package net.minecraft.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LockIconButton extends Button {
   private boolean f_94297_;

   public LockIconButton(int p_94299_, int p_94300_, Button.OnPress p_94301_) {
      super(p_94299_, p_94300_, 20, 20, new TranslatableComponent("narrator.button.difficulty_lock"), p_94301_);
   }

   protected MutableComponent m_5646_() {
      return CommonComponents.m_178398_(super.m_5646_(), this.m_94302_() ? new TranslatableComponent("narrator.button.difficulty_lock.locked") : new TranslatableComponent("narrator.button.difficulty_lock.unlocked"));
   }

   public boolean m_94302_() {
      return this.f_94297_;
   }

   public void m_94309_(boolean p_94310_) {
      this.f_94297_ = p_94310_;
   }

   public void m_6303_(PoseStack p_94304_, int p_94305_, int p_94306_, float p_94307_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, Button.f_93617_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      LockIconButton.Icon lockiconbutton$icon;
      if (!this.f_93623_) {
         lockiconbutton$icon = this.f_94297_ ? LockIconButton.Icon.LOCKED_DISABLED : LockIconButton.Icon.UNLOCKED_DISABLED;
      } else if (this.m_5702_()) {
         lockiconbutton$icon = this.f_94297_ ? LockIconButton.Icon.LOCKED_HOVER : LockIconButton.Icon.UNLOCKED_HOVER;
      } else {
         lockiconbutton$icon = this.f_94297_ ? LockIconButton.Icon.LOCKED : LockIconButton.Icon.UNLOCKED;
      }

      this.m_93228_(p_94304_, this.f_93620_, this.f_93621_, lockiconbutton$icon.m_94326_(), lockiconbutton$icon.m_94327_(), this.f_93618_, this.f_93619_);
   }

   @OnlyIn(Dist.CLIENT)
   static enum Icon {
      LOCKED(0, 146),
      LOCKED_HOVER(0, 166),
      LOCKED_DISABLED(0, 186),
      UNLOCKED(20, 146),
      UNLOCKED_HOVER(20, 166),
      UNLOCKED_DISABLED(20, 186);

      private final int f_94317_;
      private final int f_94318_;

      private Icon(int p_94324_, int p_94325_) {
         this.f_94317_ = p_94324_;
         this.f_94318_ = p_94325_;
      }

      public int m_94326_() {
         return this.f_94317_;
      }

      public int m_94327_() {
         return this.f_94318_;
      }
   }
}