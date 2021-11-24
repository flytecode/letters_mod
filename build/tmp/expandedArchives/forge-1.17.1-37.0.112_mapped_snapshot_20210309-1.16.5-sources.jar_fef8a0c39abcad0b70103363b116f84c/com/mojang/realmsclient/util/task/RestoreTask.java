package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RestoreTask extends LongRunningTask {
   private final Backup f_90439_;
   private final long f_90440_;
   private final RealmsConfigureWorldScreen f_90441_;

   public RestoreTask(Backup p_90443_, long p_90444_, RealmsConfigureWorldScreen p_90445_) {
      this.f_90439_ = p_90443_;
      this.f_90440_ = p_90444_;
      this.f_90441_ = p_90445_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.backup.restoring"));
      RealmsClient realmsclient = RealmsClient.m_87169_();
      int i = 0;

      while(i < 25) {
         try {
            if (this.m_90411_()) {
               return;
            }

            realmsclient.m_87224_(this.f_90440_, this.f_90439_.f_87389_);
            m_167655_(1L);
            if (this.m_90411_()) {
               return;
            }

            m_90405_(this.f_90441_.m_88486_());
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

            f_90394_.error("Couldn't restore backup", (Throwable)realmsserviceexception);
            m_90405_(new RealmsGenericErrorScreen(realmsserviceexception, this.f_90441_));
            return;
         } catch (Exception exception) {
            if (this.m_90411_()) {
               return;
            }

            f_90394_.error("Couldn't restore backup", (Throwable)exception);
            this.m_87791_(exception.getLocalizedMessage());
            return;
         }
      }

   }
}