package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsBrokenWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsTermsScreen;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GetServerDetailsTask extends LongRunningTask {
   private final RealmsServer f_90327_;
   private final Screen f_90328_;
   private final RealmsMainScreen f_90329_;
   private final ReentrantLock f_90330_;

   public GetServerDetailsTask(RealmsMainScreen p_90332_, Screen p_90333_, RealmsServer p_90334_, ReentrantLock p_90335_) {
      this.f_90328_ = p_90333_;
      this.f_90329_ = p_90332_;
      this.f_90327_ = p_90334_;
      this.f_90330_ = p_90335_;
   }

   public void run() {
      this.m_90409_(new TranslatableComponent("mco.connect.connecting"));

      RealmsServerAddress realmsserveraddress;
      try {
         realmsserveraddress = this.m_167653_();
      } catch (CancellationException cancellationexception) {
         f_90394_.info("User aborted connecting to realms");
         return;
      } catch (RealmsServiceException realmsserviceexception) {
         switch(realmsserviceexception.f_87775_) {
         case 6002:
            m_90405_(new RealmsTermsScreen(this.f_90328_, this.f_90329_, this.f_90327_));
            return;
         case 6006:
            boolean flag1 = this.f_90327_.f_87479_.equals(Minecraft.m_91087_().m_91094_().m_92545_());
            m_90405_((Screen)(flag1 ? new RealmsBrokenWorldScreen(this.f_90328_, this.f_90329_, this.f_90327_.f_87473_, this.f_90327_.f_87485_ == RealmsServer.WorldType.MINIGAME) : new RealmsGenericErrorScreen(new TranslatableComponent("mco.brokenworld.nonowner.title"), new TranslatableComponent("mco.brokenworld.nonowner.error"), this.f_90328_)));
            return;
         default:
            this.m_87791_(realmsserviceexception.toString());
            f_90394_.error("Couldn't connect to world", (Throwable)realmsserviceexception);
            return;
         }
      } catch (TimeoutException timeoutexception) {
         this.m_5673_(new TranslatableComponent("mco.errorMessage.connectionFailure"));
         return;
      } catch (Exception exception) {
         f_90394_.error("Couldn't connect to world", (Throwable)exception);
         this.m_87791_(exception.getLocalizedMessage());
         return;
      }

      boolean flag = realmsserveraddress.f_87566_ != null && realmsserveraddress.f_87567_ != null;
      Screen screen = (Screen)(flag ? this.m_167639_(realmsserveraddress, this::m_167637_) : this.m_167637_(realmsserveraddress));
      m_90405_(screen);
   }

   private RealmsServerAddress m_167653_() throws RealmsServiceException, TimeoutException, CancellationException {
      RealmsClient realmsclient = RealmsClient.m_87169_();

      for(int i = 0; i < 40; ++i) {
         if (this.m_90411_()) {
            throw new CancellationException();
         }

         try {
            return realmsclient.m_87207_(this.f_90327_.f_87473_);
         } catch (RetryCallException retrycallexception) {
            m_167655_((long)retrycallexception.f_87787_);
         }
      }

      throw new TimeoutException();
   }

   public RealmsLongRunningMcoTaskScreen m_167637_(RealmsServerAddress p_167638_) {
      return new RealmsLongRunningMcoTaskScreen(this.f_90328_, new ConnectTask(this.f_90328_, this.f_90327_, p_167638_));
   }

   private RealmsLongConfirmationScreen m_167639_(RealmsServerAddress p_167640_, Function<RealmsServerAddress, Screen> p_167641_) {
      BooleanConsumer booleanconsumer = (p_167645_) -> {
         try {
            if (p_167645_) {
               this.m_167651_(p_167640_).thenRun(() -> {
                  m_90405_(p_167641_.apply(p_167640_));
               }).exceptionally((p_167647_) -> {
                  Minecraft.m_91087_().m_91100_().m_118586_();
                  f_90394_.error(p_167647_);
                  m_90405_(new RealmsGenericErrorScreen(new TextComponent("Failed to download resource pack!"), this.f_90328_));
                  return null;
               });
               return;
            }

            m_90405_(this.f_90328_);
         } finally {
            if (this.f_90330_.isHeldByCurrentThread()) {
               this.f_90330_.unlock();
            }

         }

      };
      return new RealmsLongConfirmationScreen(booleanconsumer, RealmsLongConfirmationScreen.Type.Info, new TranslatableComponent("mco.configure.world.resourcepack.question.line1"), new TranslatableComponent("mco.configure.world.resourcepack.question.line2"), true);
   }

   private CompletableFuture<?> m_167651_(RealmsServerAddress p_167652_) {
      try {
         return Minecraft.m_91087_().m_91100_().m_174813_(p_167652_.f_87566_, p_167652_.f_87567_, false);
      } catch (Exception exception) {
         CompletableFuture<Void> completablefuture = new CompletableFuture<>();
         completablefuture.completeExceptionally(exception);
         return completablefuture;
      }
   }
}