package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsNotificationsScreen extends RealmsScreen {
   private static final ResourceLocation f_88821_ = new ResourceLocation("realms", "textures/gui/realms/invite_icon.png");
   private static final ResourceLocation f_88822_ = new ResourceLocation("realms", "textures/gui/realms/trial_icon.png");
   private static final ResourceLocation f_88823_ = new ResourceLocation("realms", "textures/gui/realms/news_notification_mainscreen.png");
   private static final RealmsDataFetcher f_88824_ = new RealmsDataFetcher(Minecraft.m_91087_(), RealmsClient.m_87169_());
   private volatile int f_88825_;
   static boolean f_88826_;
   private static boolean f_88827_;
   static boolean f_88828_;
   private static boolean f_88829_;

   public RealmsNotificationsScreen() {
      super(NarratorChatListener.f_93310_);
   }

   public void m_7856_() {
      this.m_88850_();
      this.f_96541_.f_91068_.m_90926_(true);
   }

   public void m_96624_() {
      if ((!this.m_88848_() || !this.m_88849_() || !f_88828_) && !f_88824_.m_87817_()) {
         f_88824_.m_87856_();
      } else if (f_88828_ && this.m_88848_()) {
         f_88824_.m_87847_();
         if (f_88824_.m_87820_(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.f_88825_ = f_88824_.m_87851_();
         }

         if (f_88824_.m_87820_(RealmsDataFetcher.Task.TRIAL_AVAILABLE)) {
            f_88827_ = f_88824_.m_87852_();
         }

         if (f_88824_.m_87820_(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            f_88829_ = f_88824_.m_87854_();
         }

         f_88824_.m_87848_();
      }
   }

   private boolean m_88848_() {
      return this.f_96541_.f_91066_.f_92046_;
   }

   private boolean m_88849_() {
      return this.f_96541_.f_91080_ instanceof TitleScreen;
   }

   private void m_88850_() {
      if (!f_88826_) {
         f_88826_ = true;
         (new Thread("Realms Notification Availability checker #1") {
            public void run() {
               RealmsClient realmsclient = RealmsClient.m_87169_();

               try {
                  RealmsClient.CompatibleVersionResponse realmsclient$compatibleversionresponse = realmsclient.m_87259_();
                  if (realmsclient$compatibleversionresponse != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                     return;
                  }
               } catch (RealmsServiceException realmsserviceexception) {
                  if (realmsserviceexception.f_87773_ != 401) {
                     RealmsNotificationsScreen.f_88826_ = false;
                  }

                  return;
               }

               RealmsNotificationsScreen.f_88828_ = true;
            }
         }).start();
      }

   }

   public void m_6305_(PoseStack p_88837_, int p_88838_, int p_88839_, float p_88840_) {
      if (f_88828_) {
         this.m_88832_(p_88837_, p_88838_, p_88839_);
      }

      super.m_6305_(p_88837_, p_88838_, p_88839_, p_88840_);
   }

   private void m_88832_(PoseStack p_88833_, int p_88834_, int p_88835_) {
      int i = this.f_88825_;
      int j = 24;
      int k = this.f_96544_ / 4 + 48;
      int l = this.f_96543_ / 2 + 80;
      int i1 = k + 48 + 2;
      int j1 = 0;
      if (f_88829_) {
         RenderSystem.m_157456_(0, f_88823_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         p_88833_.m_85836_();
         p_88833_.m_85841_(0.4F, 0.4F, 0.4F);
         GuiComponent.m_93133_(p_88833_, (int)((double)(l + 2 - j1) * 2.5D), (int)((double)i1 * 2.5D), 0.0F, 0.0F, 40, 40, 40, 40);
         p_88833_.m_85849_();
         j1 += 14;
      }

      if (i != 0) {
         RenderSystem.m_157456_(0, f_88821_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.m_93133_(p_88833_, l - j1, i1 - 6, 0.0F, 0.0F, 15, 25, 31, 25);
         j1 += 16;
      }

      if (f_88827_) {
         RenderSystem.m_157456_(0, f_88822_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         int k1 = 0;
         if ((Util.m_137550_() / 800L & 1L) == 1L) {
            k1 = 8;
         }

         GuiComponent.m_93133_(p_88833_, l + 4 - j1, i1 + 4, 0.0F, (float)k1, 8, 8, 8, 16);
      }

   }

   public void m_7861_() {
      f_88824_.m_87856_();
   }
}