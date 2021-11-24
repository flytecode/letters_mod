package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenServerTask extends LongRunningTask {
   private final RealmsServer f_90413_;
   private final Screen f_90414_;
   private final boolean f_90415_;
   private final RealmsMainScreen f_90416_;
   private final Minecraft f_181342_;

   public OpenServerTask(RealmsServer p_181344_, Screen p_181345_, RealmsMainScreen p_181346_, boolean p_181347_, Minecraft p_181348_) {
      this.f_90413_ = p_181344_;
      this.f_90414_ = p_181345_;
      this.f_90415_ = p_181347_;
      this.f_90416_ = p_181346_;
      this.f_181342_ = p_181348_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.configure.world.opening"));
      RealmsClient realmsclient = RealmsClient.m_87169_();

      for(int i = 0; i < 25; ++i) {
         if (this.m_90411_()) {
            return;
         }

         try {
            boolean flag = realmsclient.m_87236_(this.f_90413_.f_87473_);
            if (flag) {
               this.f_181342_.execute(() -> {
                  if (this.f_90414_ instanceof RealmsConfigureWorldScreen) {
                     ((RealmsConfigureWorldScreen)this.f_90414_).m_88413_();
                  }

                  this.f_90413_.f_87477_ = RealmsServer.State.OPEN;
                  if (this.f_90415_) {
                     this.f_90416_.m_86515_(this.f_90413_, this.f_90414_);
                  } else {
                     this.f_181342_.m_91152_(this.f_90414_);
                  }

               });
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

            f_90394_.error("Failed to open server", (Throwable)exception);
            this.m_87791_("Failed to open the server");
         }
      }

   }
}