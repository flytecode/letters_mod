package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SystemToast implements Toast {
   private static final long f_169077_ = 5000L;
   private static final int f_169078_ = 200;
   private final SystemToast.SystemToastIds f_94820_;
   private Component f_94821_;
   private List<FormattedCharSequence> f_94822_;
   private long f_94823_;
   private boolean f_94824_;
   private final int f_94825_;

   public SystemToast(SystemToast.SystemToastIds p_94832_, Component p_94833_, @Nullable Component p_94834_) {
      this(p_94832_, p_94833_, m_94860_(p_94834_), 160);
   }

   public static SystemToast m_94847_(Minecraft p_94848_, SystemToast.SystemToastIds p_94849_, Component p_94850_, Component p_94851_) {
      Font font = p_94848_.f_91062_;
      List<FormattedCharSequence> list = font.m_92923_(p_94851_, 200);
      int i = Math.max(200, list.stream().mapToInt(font::m_92724_).max().orElse(200));
      return new SystemToast(p_94849_, p_94850_, list, i + 30);
   }

   private SystemToast(SystemToast.SystemToastIds p_94827_, Component p_94828_, List<FormattedCharSequence> p_94829_, int p_94830_) {
      this.f_94820_ = p_94827_;
      this.f_94821_ = p_94828_;
      this.f_94822_ = p_94829_;
      this.f_94825_ = p_94830_;
   }

   private static ImmutableList<FormattedCharSequence> m_94860_(@Nullable Component p_94861_) {
      return p_94861_ == null ? ImmutableList.of() : ImmutableList.of(p_94861_.m_7532_());
   }

   public int m_7828_() {
      return this.f_94825_;
   }

   public Toast.Visibility m_7172_(PoseStack p_94844_, ToastComponent p_94845_, long p_94846_) {
      if (this.f_94824_) {
         this.f_94823_ = p_94846_;
         this.f_94824_ = false;
      }

      RenderSystem.m_157456_(0, f_94893_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      int i = this.m_7828_();
      int j = 12;
      if (i == 160 && this.f_94822_.size() <= 1) {
         p_94845_.m_93228_(p_94844_, 0, 0, 0, 64, i, this.m_94899_());
      } else {
         int k = this.m_94899_() + Math.max(0, this.f_94822_.size() - 1) * 12;
         int l = 28;
         int i1 = Math.min(4, k - 28);
         this.m_94836_(p_94844_, p_94845_, i, 0, 0, 28);

         for(int j1 = 28; j1 < k - i1; j1 += 10) {
            this.m_94836_(p_94844_, p_94845_, i, 16, j1, Math.min(16, k - j1 - i1));
         }

         this.m_94836_(p_94844_, p_94845_, i, 32 - i1, k - i1, i1);
      }

      if (this.f_94822_ == null) {
         p_94845_.m_94929_().f_91062_.m_92889_(p_94844_, this.f_94821_, 18.0F, 12.0F, -256);
      } else {
         p_94845_.m_94929_().f_91062_.m_92889_(p_94844_, this.f_94821_, 18.0F, 7.0F, -256);

         for(int k1 = 0; k1 < this.f_94822_.size(); ++k1) {
            p_94845_.m_94929_().f_91062_.m_92877_(p_94844_, this.f_94822_.get(k1), 18.0F, (float)(18 + k1 * 12), -1);
         }
      }

      return p_94846_ - this.f_94823_ < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
   }

   private void m_94836_(PoseStack p_94837_, ToastComponent p_94838_, int p_94839_, int p_94840_, int p_94841_, int p_94842_) {
      int i = p_94840_ == 0 ? 20 : 5;
      int j = Math.min(60, p_94839_ - i);
      p_94838_.m_93228_(p_94837_, 0, p_94841_, 0, 64 + p_94840_, i, p_94842_);

      for(int k = i; k < p_94839_ - j; k += 64) {
         p_94838_.m_93228_(p_94837_, k, p_94841_, 32, 64 + p_94840_, Math.min(64, p_94839_ - k - j), p_94842_);
      }

      p_94838_.m_93228_(p_94837_, p_94839_ - j, p_94841_, 160 - j, 64 + p_94840_, j, p_94842_);
   }

   public void m_94862_(Component p_94863_, @Nullable Component p_94864_) {
      this.f_94821_ = p_94863_;
      this.f_94822_ = m_94860_(p_94864_);
      this.f_94824_ = true;
   }

   public SystemToast.SystemToastIds m_7283_() {
      return this.f_94820_;
   }

   public static void m_94855_(ToastComponent p_94856_, SystemToast.SystemToastIds p_94857_, Component p_94858_, @Nullable Component p_94859_) {
      p_94856_.m_94922_(new SystemToast(p_94857_, p_94858_, p_94859_));
   }

   public static void m_94869_(ToastComponent p_94870_, SystemToast.SystemToastIds p_94871_, Component p_94872_, @Nullable Component p_94873_) {
      SystemToast systemtoast = p_94870_.m_94926_(SystemToast.class, p_94871_);
      if (systemtoast == null) {
         m_94855_(p_94870_, p_94871_, p_94872_, p_94873_);
      } else {
         systemtoast.m_94862_(p_94872_, p_94873_);
      }

   }

   public static void m_94852_(Minecraft p_94853_, String p_94854_) {
      m_94855_(p_94853_.m_91300_(), SystemToast.SystemToastIds.WORLD_ACCESS_FAILURE, new TranslatableComponent("selectWorld.access_failure"), new TextComponent(p_94854_));
   }

   public static void m_94866_(Minecraft p_94867_, String p_94868_) {
      m_94855_(p_94867_.m_91300_(), SystemToast.SystemToastIds.WORLD_ACCESS_FAILURE, new TranslatableComponent("selectWorld.delete_failure"), new TextComponent(p_94868_));
   }

   public static void m_94875_(Minecraft p_94876_, String p_94877_) {
      m_94855_(p_94876_.m_91300_(), SystemToast.SystemToastIds.PACK_COPY_FAILURE, new TranslatableComponent("pack.copyFailure"), new TextComponent(p_94877_));
   }

   @OnlyIn(Dist.CLIENT)
   public static enum SystemToastIds {
      TUTORIAL_HINT,
      NARRATOR_TOGGLE,
      WORLD_BACKUP,
      WORLD_GEN_SETTINGS_TRANSFER,
      PACK_LOAD_FAILURE,
      WORLD_ACCESS_FAILURE,
      PACK_COPY_FAILURE;
   }
}