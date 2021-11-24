package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ResettingWorldTask extends LongRunningTask {
   private final long f_90427_;
   private final Component f_90428_;
   private final Runnable f_90429_;

   public ResettingWorldTask(long p_167676_, Component p_167677_, Runnable p_167678_) {
      this.f_90427_ = p_167676_;
      this.f_90428_ = p_167677_;
      this.f_90429_ = p_167678_;
   }

   protected abstract void m_142381_(RealmsClient p_167679_, long p_167680_) throws RealmsServiceException;

   public void run() {
      RealmsClient realmsclient = RealmsClient.m_87169_();
      this.m_90409_(this.f_90428_);
      int i = 0;

      while(i < 25) {
         try {
            if (this.m_90411_()) {
               return;
            }

            this.m_142381_(realmsclient, this.f_90427_);
            if (this.m_90411_()) {
               return;
            }

            this.f_90429_.run();
            return;
         } catch (RetryCallException retrycallexception) {
            if (this.m_90411_()) {
               return;
            }

            m_167655_((long)retrycallexception.f_87787_);
            ++i;
         } catch (Exception exception) {
            if (this.m_90411_()) {
               return;
            }

            f_90394_.error("Couldn't reset world");
            this.m_87791_(exception.toString());
            return;
         }
      }

   }
}