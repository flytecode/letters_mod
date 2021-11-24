package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloseServerTask extends LongRunningTask {
   private final RealmsServer f_90299_;
   private final RealmsConfigureWorldScreen f_90300_;

   public CloseServerTask(RealmsServer p_90302_, RealmsConfigureWorldScreen p_90303_) {
      this.f_90299_ = p_90302_;
      this.f_90300_ = p_90303_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.configure.world.closing"));
      RealmsClient realmsclient = RealmsClient.m_87169_();

      for(int i = 0; i < 25; ++i) {
         if (this.m_90411_()) {
            return;
         }

         try {
            boolean flag = realmsclient.m_87242_(this.f_90299_.f_87473_);
            if (flag) {
               this.f_90300_.m_88413_();
               this.f_90299_.f_87477_ = RealmsServer.State.CLOSED;
               m_90405_(this.f_90300_);
               break;
            }
         } catch (RetryCallException retrycallexception) {
            if (this.m_90411_()) {
               return;
            }

            m_167655_((long)retrycallexception.f_87787_);
         } catch (Exception exception) {
            if (this.m_90411_()) {
               return;
            }

            f_90394_.error("Failed to close server", (Throwable)exception);
            this.m_87791_("Failed to close the server");
         }
      }

   }
}