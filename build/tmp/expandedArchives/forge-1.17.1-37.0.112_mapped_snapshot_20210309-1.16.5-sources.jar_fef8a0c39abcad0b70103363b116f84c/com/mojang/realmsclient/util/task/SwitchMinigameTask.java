package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SwitchMinigameTask extends LongRunningTask {
   private final long f_90447_;
   private final WorldTemplate f_90448_;
   private final RealmsConfigureWorldScreen f_90449_;

   public SwitchMinigameTask(long p_90451_, WorldTemplate p_90452_, RealmsConfigureWorldScreen p_90453_) {
      this.f_90447_ = p_90451_;
      this.f_90448_ = p_90452_;
      this.f_90449_ = p_90453_;
   }

   public void run() {
      RealmsClient realmsclient = RealmsClient.m_87169_();
      this.m_90409_(new TranslatableComponent("mco.minigame.world.starting.screen.title"));

      for(int i = 0; i < 25; ++i) {
         try {
            if (this.m_90411_()) {
               return;
            }

            if (realmsclient.m_87232_(this.f_90447_, this.f_90448_.f_87726_)) {
               m_90405_(this.f_90449_);
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

            f_90394_.error("Couldn't start mini game!");
            this.m_87791_(exception.toString());
         }
      }

   }
}