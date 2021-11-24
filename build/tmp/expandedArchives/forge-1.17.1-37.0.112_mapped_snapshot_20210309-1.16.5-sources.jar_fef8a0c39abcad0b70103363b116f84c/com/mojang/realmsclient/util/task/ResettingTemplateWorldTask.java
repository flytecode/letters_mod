package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResettingTemplateWorldTask extends ResettingWorldTask {
   private final WorldTemplate f_167666_;

   public ResettingTemplateWorldTask(WorldTemplate p_167668_, long p_167669_, Component p_167670_, Runnable p_167671_) {
      super(p_167669_, p_167670_, p_167671_);
      this.f_167666_ = p_167668_;
   }

   protected void m_142381_(RealmsClient p_167673_, long p_167674_) throws RealmsServiceException {
      p_167673_.m_87250_(p_167674_, this.f_167666_.f_87726_);
   }
}