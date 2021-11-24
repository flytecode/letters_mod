package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ConnectScreen extends Screen {
   private static final AtomicInteger f_95682_ = new AtomicInteger(0);
   static final Logger f_95683_ = LogManager.getLogger();
   private static final long f_169261_ = 2000L;
   public static final Component f_169260_ = new TranslatableComponent("disconnect.genericReason", new TranslatableComponent("disconnect.unknownHost"));
   @Nullable
   volatile Connection f_95684_;
   volatile boolean f_95685_;
   final Screen f_95686_;
   private Component f_95687_ = new TranslatableComponent("connect.connecting");
   private long f_95688_ = -1L;

   private ConnectScreen(Screen p_169263_) {
      super(NarratorChatListener.f_93310_);
      this.f_95686_ = p_169263_;
   }

   public static void m_169267_(Screen p_169268_, Minecraft p_169269_, ServerAddress p_169270_, @Nullable ServerData p_169271_) {
      ConnectScreen connectscreen = new ConnectScreen(p_169268_);
      p_169269_.m_91399_();
      p_169269_.m_91158_(p_169271_);
      p_169269_.m_91152_(connectscreen);
      connectscreen.m_169264_(p_169269_, p_169270_);
   }

   private void m_169264_(final Minecraft p_169265_, final ServerAddress p_169266_) {
      f_95683_.info("Connecting to {}, {}", p_169266_.m_171863_(), p_169266_.m_171866_());
      Thread thread = new Thread("Server Connector #" + f_95682_.incrementAndGet()) {
         public void run() {
            InetSocketAddress inetsocketaddress = null;

            try {
               if (ConnectScreen.this.f_95685_) {
                  return;
               }

               Optional<InetSocketAddress> optional = ServerNameResolver.f_171881_.m_171890_(p_169266_).map(ResolvedServerAddress::m_142641_);
               if (ConnectScreen.this.f_95685_) {
                  return;
               }

               if (!optional.isPresent()) {
                  p_169265_.execute(() -> {
                     p_169265_.m_91152_(new DisconnectedScreen(ConnectScreen.this.f_95686_, CommonComponents.f_130661_, ConnectScreen.f_169260_));
                  });
                  return;
               }

               inetsocketaddress = optional.get();
               ConnectScreen.this.f_95684_ = Connection.m_178300_(inetsocketaddress, p_169265_.f_91066_.m_92175_());
               ConnectScreen.this.f_95684_.m_129505_(new ClientHandshakePacketListenerImpl(ConnectScreen.this.f_95684_, p_169265_, ConnectScreen.this.f_95686_, ConnectScreen.this::m_95717_));
               ConnectScreen.this.f_95684_.m_129512_(new ClientIntentionPacket(inetsocketaddress.getHostName(), inetsocketaddress.getPort(), ConnectionProtocol.LOGIN));
               ConnectScreen.this.f_95684_.m_129512_(new ServerboundHelloPacket(p_169265_.m_91094_().m_92548_()));
            } catch (Exception exception) {
               if (ConnectScreen.this.f_95685_) {
                  return;
               }

               ConnectScreen.f_95683_.error("Couldn't connect to server", (Throwable)exception);
               String s = inetsocketaddress == null ? exception.toString() : exception.toString().replaceAll(inetsocketaddress.getHostName() + ":" + inetsocketaddress.getPort(), "");
               p_169265_.execute(() -> {
                  p_169265_.m_91152_(new DisconnectedScreen(ConnectScreen.this.f_95686_, CommonComponents.f_130661_, new TranslatableComponent("disconnect.genericReason", s)));
               });
            }

         }
      };
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_95683_));
      thread.start();
   }

   private void m_95717_(Component p_95718_) {
      this.f_95687_ = p_95718_;
   }

   public void m_96624_() {
      if (this.f_95684_ != null) {
         if (this.f_95684_.m_129536_()) {
            this.f_95684_.m_129483_();
         } else {
            this.f_95684_.m_129541_();
         }
      }

   }

   public boolean m_6913_() {
      return false;
   }

   protected void m_7856_() {
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 12, 200, 20, CommonComponents.f_130656_, (p_95705_) -> {
         this.f_95685_ = true;
         if (this.f_95684_ != null) {
            this.f_95684_.m_129507_(new TranslatableComponent("connect.aborted"));
         }

         this.f_96541_.m_91152_(this.f_95686_);
      }));
   }

   public void m_6305_(PoseStack p_95700_, int p_95701_, int p_95702_, float p_95703_) {
      this.m_7333_(p_95700_);
      long i = Util.m_137550_();
      if (i - this.f_95688_ > 2000L) {
         this.f_95688_ = i;
         NarratorChatListener.f_93311_.m_168785_(new TranslatableComponent("narrator.joining"));
      }

      m_93215_(p_95700_, this.f_96547_, this.f_95687_, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 16777215);
      super.m_6305_(p_95700_, p_95701_, p_95702_, p_95703_);
   }
}