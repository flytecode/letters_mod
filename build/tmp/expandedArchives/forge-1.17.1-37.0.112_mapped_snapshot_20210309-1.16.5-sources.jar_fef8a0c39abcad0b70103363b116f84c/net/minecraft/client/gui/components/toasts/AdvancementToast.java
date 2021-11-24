package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancementToast implements Toast {
   private final Advancement f_94795_;
   private boolean f_94796_;

   public AdvancementToast(Advancement p_94798_) {
      this.f_94795_ = p_94798_;
   }

   public Toast.Visibility m_7172_(PoseStack p_94800_, ToastComponent p_94801_, long p_94802_) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, f_94893_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      DisplayInfo displayinfo = this.f_94795_.m_138320_();
      p_94801_.m_93228_(p_94800_, 0, 0, 0, 0, this.m_7828_(), this.m_94899_());
      if (displayinfo != null) {
         List<FormattedCharSequence> list = p_94801_.m_94929_().f_91062_.m_92923_(displayinfo.m_14977_(), 125);
         int i = displayinfo.m_14992_() == FrameType.CHALLENGE ? 16746751 : 16776960;
         if (list.size() == 1) {
            p_94801_.m_94929_().f_91062_.m_92889_(p_94800_, displayinfo.m_14992_().m_15553_(), 30.0F, 7.0F, i | -16777216);
            p_94801_.m_94929_().f_91062_.m_92877_(p_94800_, list.get(0), 30.0F, 18.0F, -1);
         } else {
            int j = 1500;
            float f = 300.0F;
            if (p_94802_ < 1500L) {
               int k = Mth.m_14143_(Mth.m_14036_((float)(1500L - p_94802_) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               p_94801_.m_94929_().f_91062_.m_92889_(p_94800_, displayinfo.m_14992_().m_15553_(), 30.0F, 11.0F, i | k);
            } else {
               int i1 = Mth.m_14143_(Mth.m_14036_((float)(p_94802_ - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int l = this.m_94899_() / 2 - list.size() * 9 / 2;

               for(FormattedCharSequence formattedcharsequence : list) {
                  p_94801_.m_94929_().f_91062_.m_92877_(p_94800_, formattedcharsequence, 30.0F, (float)l, 16777215 | i1);
                  l += 9;
               }
            }
         }

         if (!this.f_94796_ && p_94802_ > 0L) {
            this.f_94796_ = true;
            if (displayinfo.m_14992_() == FrameType.CHALLENGE) {
               p_94801_.m_94929_().m_91106_().m_120367_(SimpleSoundInstance.m_119755_(SoundEvents.f_12496_, 1.0F, 1.0F));
            }
         }

         p_94801_.m_94929_().m_91291_().m_115218_(displayinfo.m_14990_(), 8, 8);
         return p_94802_ >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
      } else {
         return Toast.Visibility.HIDE;
      }
   }
}