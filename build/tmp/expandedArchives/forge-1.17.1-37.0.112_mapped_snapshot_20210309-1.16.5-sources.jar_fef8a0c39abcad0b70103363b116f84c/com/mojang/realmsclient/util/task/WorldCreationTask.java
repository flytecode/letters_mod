package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WorldCreationTask extends LongRunningTask {
   private final String f_90463_;
   private final String f_90464_;
   private final long f_90465_;
   private final Screen f_90466_;

   public WorldCreationTask(long p_90468_, String p_90469_, String p_90470_, Screen p_90471_) {
      this.f_90465_ = p_90468_;
      this.f_90463_ = p_90469_;
      this.f_90464_ = p_90470_;
      this.f_90466_ = p_90471_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.create.world.wait"));
      RealmsClient realmsclient = RealmsClient.m_87169_();

      try {
         realmsclient.m_87191_(this.f_90465_, this.f_90463_, this.f_90464_);
         m_90405_(this.f_90466_);
      } catch (RealmsServiceException realmsserviceexception) {
         f_90394_.error("Couldn't create world");
         this.m_87791_(realmsserviceexception.toString());
      } catch (Exception exception) {
         f_90394_.error("Could not create world");
         this.m_87791_(exception.getLocalizedMessage());
      }

   }
}