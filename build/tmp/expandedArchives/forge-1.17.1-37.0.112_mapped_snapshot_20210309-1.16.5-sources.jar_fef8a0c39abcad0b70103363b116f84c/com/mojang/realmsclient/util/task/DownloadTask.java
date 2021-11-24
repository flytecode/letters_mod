package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DownloadTask extends LongRunningTask {
   private final long f_90315_;
   private final int f_90316_;
   private final Screen f_90317_;
   private final String f_90318_;

   public DownloadTask(long p_90320_, int p_90321_, String p_90322_, Screen p_90323_) {
      this.f_90315_ = p_90320_;
      this.f_90316_ = p_90321_;
      this.f_90317_ = p_90323_;
      this.f_90318_ = p_90322_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.download.preparing"));
      RealmsClient realmsclient = RealmsClient.m_87169_();
      int i = 0;

      while(i < 25) {
         try {
            if (this.m_90411_()) {
               return;
            }

            WorldDownload worlddownload = realmsclient.m_87209_(this.f_90315_, this.f_90316_);
            m_167655_(1L);
            if (this.m_90411_()) {
               return;
            }

            m_90405_(new RealmsDownloadLatestWorldScreen(this.f_90317_, worlddownload, this.f_90318_, (p_90325_) -> {
            }));
            return;
         } catch (RetryCallException retrycallexception) {
            if (this.m_90411_()) {
               return;
            }

            m_167655_((long)retrycallexception.f_87787_);
            ++i;
         } catch (RealmsServiceException realmsserviceexception) {
            if (this.m_90411_()) {
               return;
            }

            f_90394_.error("Couldn't download world data");
            m_90405_(new RealmsGenericErrorScreen(realmsserviceexception, this.f_90317_));
            return;
         } catch (Exception exception) {
            if (this.m_90411_()) {
               return;
            }

            f_90394_.error("Couldn't download world data", (Throwable)exception);
            this.m_87791_(exception.getLocalizedMessage());
            return;
         }
      }

   }
}