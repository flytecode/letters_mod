package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.gui.ErrorCallback;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public abstract class LongRunningTask implements ErrorCallback, Runnable {
   protected static final int f_167654_ = 25;
   public static final Logger f_90394_ = LogManager.getLogger();
   protected RealmsLongRunningMcoTaskScreen f_90395_;

   protected static void m_167655_(long p_167656_) {
      try {
         Thread.sleep(p_167656_ * 1000L);
      } catch (InterruptedException interruptedexception) {
         Thread.currentThread().interrupt();
         f_90394_.error("", (Throwable)interruptedexception);
      }

   }

   public static void m_90405_(Screen p_90406_) {
      Minecraft minecraft = Minecraft.m_91087_();
      minecraft.execute(() -> {
         minecraft.m_91152_(p_90406_);
      });
   }

   public void m_90400_(RealmsLongRunningMcoTaskScreen p_90401_) {
      this.f_90395_ = p_90401_;
   }

   public void m_5673_(Component p_90408_) {
      this.f_90395_.m_5673_(p_90408_);
   }

   public void m_90409_(Component p_90410_) {
      this.f_90395_.m_88796_(p_90410_);
   }

   public boolean m_90411_() {
      return this.f_90395_.m_88779_();
   }

   public void m_5519_() {
   }

   public void m_90412_() {
   }

   public void m_5520_() {
   }
}