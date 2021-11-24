package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToastComponent extends GuiComponent {
   private static final int f_169081_ = 5;
   final Minecraft f_94914_;
   private final ToastComponent.ToastInstance<?>[] f_94915_ = new ToastComponent.ToastInstance[5];
   private final Deque<Toast> f_94916_ = Queues.newArrayDeque();

   public ToastComponent(Minecraft p_94918_) {
      this.f_94914_ = p_94918_;
   }

   public void m_94920_(PoseStack p_94921_) {
      if (!this.f_94914_.f_91066_.f_92062_) {
         for(int i = 0; i < this.f_94915_.length; ++i) {
            ToastComponent.ToastInstance<?> toastinstance = this.f_94915_[i];
            if (toastinstance != null && toastinstance.m_94943_(this.f_94914_.m_91268_().m_85445_(), i, p_94921_)) {
               this.f_94915_[i] = null;
            }

            if (this.f_94915_[i] == null && !this.f_94916_.isEmpty()) {
               this.f_94915_[i] = new ToastComponent.ToastInstance<>(this.f_94916_.removeFirst());
            }
         }

      }
   }

   @Nullable
   public <T extends Toast> T m_94926_(Class<? extends T> p_94927_, Object p_94928_) {
      for(ToastComponent.ToastInstance<?> toastinstance : this.f_94915_) {
         if (toastinstance != null && p_94927_.isAssignableFrom(toastinstance.m_94942_().getClass()) && toastinstance.m_94942_().m_7283_().equals(p_94928_)) {
            return (T)toastinstance.m_94942_();
         }
      }

      for(Toast toast : this.f_94916_) {
         if (p_94927_.isAssignableFrom(toast.getClass()) && toast.m_7283_().equals(p_94928_)) {
            return (T)toast;
         }
      }

      return (T)null;
   }

   public void m_94919_() {
      Arrays.fill(this.f_94915_, (Object)null);
      this.f_94916_.clear();
   }

   public void m_94922_(Toast p_94923_) {
      this.f_94916_.add(p_94923_);
   }

   public Minecraft m_94929_() {
      return this.f_94914_;
   }

   @OnlyIn(Dist.CLIENT)
   class ToastInstance<T extends Toast> {
      private static final long f_169082_ = 600L;
      private final T f_94931_;
      private long f_94932_ = -1L;
      private long f_94933_ = -1L;
      private Toast.Visibility f_94934_ = Toast.Visibility.SHOW;

      ToastInstance(T p_94937_) {
         this.f_94931_ = p_94937_;
      }

      public T m_94942_() {
         return this.f_94931_;
      }

      private float m_94947_(long p_94948_) {
         float f = Mth.m_14036_((float)(p_94948_ - this.f_94932_) / 600.0F, 0.0F, 1.0F);
         f = f * f;
         return this.f_94934_ == Toast.Visibility.HIDE ? 1.0F - f : f;
      }

      public boolean m_94943_(int p_94944_, int p_94945_, PoseStack p_94946_) {
         long i = Util.m_137550_();
         if (this.f_94932_ == -1L) {
            this.f_94932_ = i;
            this.f_94934_.m_94909_(ToastComponent.this.f_94914_.m_91106_());
         }

         if (this.f_94934_ == Toast.Visibility.SHOW && i - this.f_94932_ <= 600L) {
            this.f_94933_ = i;
         }

         PoseStack posestack = RenderSystem.m_157191_();
         posestack.m_85836_();
         posestack.m_85837_((double)((float)p_94944_ - (float)this.f_94931_.m_7828_() * this.m_94947_(i)), (double)(p_94945_ * this.f_94931_.m_94899_()), (double)(800 + p_94945_));
         RenderSystem.m_157182_();
         Toast.Visibility toast$visibility = this.f_94931_.m_7172_(p_94946_, ToastComponent.this, i - this.f_94933_);
         posestack.m_85849_();
         RenderSystem.m_157182_();
         if (toast$visibility != this.f_94934_) {
            this.f_94932_ = i - (long)((int)((1.0F - this.m_94947_(i)) * 600.0F));
            this.f_94934_ = toast$visibility;
            this.f_94934_.m_94909_(ToastComponent.this.f_94914_.m_91106_());
         }

         return this.f_94934_ == Toast.Visibility.HIDE && i - this.f_94932_ > 600L;
      }
   }
}