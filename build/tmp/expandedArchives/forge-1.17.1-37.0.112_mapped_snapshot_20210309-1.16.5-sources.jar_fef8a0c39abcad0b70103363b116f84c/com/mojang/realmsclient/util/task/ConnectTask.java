package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsConnect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConnectTask extends LongRunningTask {
   private final RealmsConnect f_90305_;
   private final RealmsServer f_90306_;
   private final RealmsServerAddress f_90307_;

   public ConnectTask(Screen p_90309_, RealmsServer p_90310_, RealmsServerAddress p_90311_) {
      this.f_90306_ = p_90310_;
      this.f_90307_ = p_90311_;
      this.f_90305_ = new RealmsConnect(p_90309_);
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.connect.connecting"));
      this.f_90305_.m_175031_(this.f_90306_, ServerAddress.m_171864_(this.f_90307_.f_87565_));
   }

   public void m_5520_() {
      this.f_90305_.m_120694_();
      Minecraft.m_91087_().m_91100_().m_118586_();
   }

   public void m_5519_() {
      this.f_90305_.m_120704_();
   }
}