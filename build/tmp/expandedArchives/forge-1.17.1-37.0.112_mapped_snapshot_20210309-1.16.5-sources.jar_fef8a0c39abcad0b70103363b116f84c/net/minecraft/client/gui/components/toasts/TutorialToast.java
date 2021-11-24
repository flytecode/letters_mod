package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TutorialToast implements Toast {
   public static final int f_169083_ = 154;
   public static final int f_169084_ = 1;
   public static final int f_169085_ = 3;
   public static final int f_169086_ = 28;
   private final TutorialToast.Icons f_94949_;
   private final Component f_94950_;
   private final Component f_94951_;
   private Toast.Visibility f_94952_ = Toast.Visibility.SHOW;
   private long f_94953_;
   private float f_94954_;
   private float f_94955_;
   private final boolean f_94956_;

   public TutorialToast(TutorialToast.Icons p_94958_, Component p_94959_, @Nullable Component p_94960_, boolean p_94961_) {
      this.f_94949_ = p_94958_;
      this.f_94950_ = p_94959_;
      this.f_94951_ = p_94960_;
      this.f_94956_ = p_94961_;
   }

   public Toast.Visibility m_7172_(PoseStack p_94965_, ToastComponent p_94966_, long p_94967_) {
      RenderSystem.m_157456_(0, f_94893_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      p_94966_.m_93228_(p_94965_, 0, 0, 0, 96, this.m_7828_(), this.m_94899_());
      this.f_94949_.m_94984_(p_94965_, p_94966_, 6, 6);
      if (this.f_94951_ == null) {
         p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94950_, 30.0F, 12.0F, -11534256);
      } else {
         p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94950_, 30.0F, 7.0F, -11534256);
         p_94966_.m_94929_().f_91062_.m_92889_(p_94965_, this.f_94951_, 30.0F, 18.0F, -16777216);
      }

      if (this.f_94956_) {
         GuiComponent.m_93172_(p_94965_, 3, 28, 157, 29, -1);
         float f = Mth.m_144920_(this.f_94954_, this.f_94955_, (float)(p_94967_ - this.f_94953_) / 100.0F);
         int i;
         if (this.f_94955_ >= this.f_94954_) {
            i = -16755456;
         } else {
            i = -11206656;
         }

         GuiComponent.m_93172_(p_94965_, 3, 28, (int)(3.0F + 154.0F * f), 29, i);
         this.f_94954_ = f;
         this.f_94953_ = p_94967_;
      }

      return this.f_94952_;
   }

   public void m_94968_() {
      this.f_94952_ = Toast.Visibility.HIDE;
   }

   public void m_94962_(float p_94963_) {
      this.f_94955_ = p_94963_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Icons {
      MOVEMENT_KEYS(0, 0),
      MOUSE(1, 0),
      TREE(2, 0),
      RECIPE_BOOK(0, 1),
      WOODEN_PLANKS(1, 1),
      SOCIAL_INTERACTIONS(2, 1),
      RIGHT_CLICK(3, 1);

      private final int f_94975_;
      private final int f_94976_;

      private Icons(int p_94982_, int p_94983_) {
         this.f_94975_ = p_94982_;
         this.f_94976_ = p_94983_;
      }

      public void m_94984_(PoseStack p_94985_, GuiComponent p_94986_, int p_94987_, int p_94988_) {
         RenderSystem.m_69478_();
         p_94986_.m_93228_(p_94985_, p_94987_, p_94988_, 176 + this.f_94975_ * 20, this.f_94976_ * 20, 20, 20);
         RenderSystem.m_69478_();
      }
   }
}