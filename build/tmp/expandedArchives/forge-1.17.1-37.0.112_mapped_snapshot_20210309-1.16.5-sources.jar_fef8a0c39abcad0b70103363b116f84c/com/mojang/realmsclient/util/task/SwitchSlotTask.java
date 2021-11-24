package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RetryCallException;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SwitchSlotTask extends LongRunningTask {
   private final long f_90455_;
   private final int f_90456_;
   private final Runnable f_90457_;

   public SwitchSlotTask(long p_90459_, int p_90460_, Runnable p_90461_) {
      this.f_90455_ = p_90459_;
      this.f_90456_ = p_90460_;
      this.f_90457_ = p_90461_;
   }

   public void run() {
      RealmsClient realmsclient = RealmsClient.m_87169_();
      this.m_90409_(new TranslatableComponent("mco.minigame.world.slot.screen.title"));

      for(int i = 0; i < 25; ++i) {
         try {
            if (this.m_90411_()) {
               return;
            }

            if (realmsclient.m_87176_(this.f_90455_, this.f_90456_)) {
               this.f_90457_.run();
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

            f_90394_.error("Couldn't switch world!");
            this.m_87791_(exception.toString());
         }
      }

   }
}