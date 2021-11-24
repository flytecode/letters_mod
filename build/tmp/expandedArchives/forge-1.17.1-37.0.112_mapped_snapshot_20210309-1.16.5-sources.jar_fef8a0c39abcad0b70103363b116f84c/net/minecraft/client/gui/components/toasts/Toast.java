package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface Toast {
   ResourceLocation f_94893_ = new ResourceLocation("textures/gui/toasts.png");
   Object f_94894_ = new Object();

   Toast.Visibility m_7172_(PoseStack p_94896_, ToastComponent p_94897_, long p_94898_);

   default Object m_7283_() {
      return f_94894_;
   }

   default int m_7828_() {
      return 160;
   }

   default int m_94899_() {
      return 32;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Visibility {
      SHOW(SoundEvents.f_12497_),
      HIDE(SoundEvents.f_12498_);

      private final SoundEvent f_94902_;

      private Visibility(SoundEvent p_94908_) {
         this.f_94902_ = p_94908_;
      }

      public void m_94909_(SoundManager p_94910_) {
         p_94910_.m_120367_(SimpleSoundInstance.m_119755_(this.f_94902_, 1.0F, 1.0F));
      }
   }
}