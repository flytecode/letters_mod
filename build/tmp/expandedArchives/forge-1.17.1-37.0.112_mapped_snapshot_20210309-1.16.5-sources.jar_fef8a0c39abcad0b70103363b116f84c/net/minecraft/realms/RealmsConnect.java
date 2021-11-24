package net.minecraft.realms;

import com.mojang.realmsclient.dto.RealmsServer;
import java.net.InetSocketAddress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsConnect {
   static final Logger f_120687_ = LogManager.getLogger();
   final Screen f_120688_;
   volatile boolean f_120689_;
   Connection f_120690_;

   public RealmsConnect(Screen p_120693_) {
      this.f_120688_ = p_120693_;
   }

   public void m_175031_(final RealmsServer p_175032_, ServerAddress p_175033_) {
      final Minecraft minecraft = Minecraft.m_91087_();
      minecraft.m_91372_(true);
      NarratorChatListener.f_93311_.m_168785_(new TranslatableComponent("mco.connect.success"));
      final String s = p_175033_.m_171863_();
      final int i = p_175033_.m_171866_();
      (new Thread("Realms-connect-task") {
         public void run() {
            InetSocketAddress inetsocketaddress = null;

            try {
               inetsocketaddress = new InetSocketAddress(s, i);
               if (RealmsConnect.this.f_120689_) {
                  return;
               }

               RealmsConnect.this.f_120690_ = Connection.m_178300_(inetsocketaddress, minecraft.f_91066_.m_92175_());
               if (RealmsConnect.this.f_120689_) {
                  return;
               }

               RealmsConnect.this.f_120690_.m_129505_(new ClientHandshakePacketListenerImpl(RealmsConnect.this.f_120690_, minecraft, RealmsConnect.this.f_120688_, (p_120726_) -> {
               }));
               if (RealmsConnect.this.f_120689_) {
                  return;
               }

               RealmsConnect.this.f_120690_.m_129512_(new ClientIntentionPacket(s, i, ConnectionProtocol.LOGIN));
               if (RealmsConnect.this.f_120689_) {
                  return;
               }

               RealmsConnect.this.f_120690_.m_129512_(new ServerboundHelloPacket(minecraft.m_91094_().m_92548_()));
               minecraft.m_91158_(p_175032_.m_87522_(s));
            } catch (Exception exception) {
               minecraft.m_91100_().m_118586_();
               if (RealmsConnect.this.f_120689_) {
                  return;
               }

               RealmsConnect.f_120687_.error("Couldn't connect to world", (Throwable)exception);
               String s1 = exception.toString();
               if (inetsocketaddress != null) {
                  String s2 = inetsocketaddress + ":" + i;
                  s1 = s1.replaceAll(s2, "");
               }

               DisconnectedRealmsScreen disconnectedrealmsscreen = new DisconnectedRealmsScreen(RealmsConnect.this.f_120688_, CommonComponents.f_130661_, new TranslatableComponent("disconnect.genericReason", s1));
               minecraft.execute(() -> {
                  minecraft.m_91152_(disconnectedrealmsscreen);
               });
            }

         }
      }).start();
   }

   public void m_120694_() {
      this.f_120689_ = true;
      if (this.f_120690_ != null && this.f_120690_.m_129536_()) {
         this.f_120690_.m_129507_(new TranslatableComponent("disconnect.genericReason"));
         this.f_120690_.m_129541_();
      }

   }

   public void m_120704_() {
      if (this.f_120690_ != null) {
         if (this.f_120690_.m_129536_()) {
            this.f_120690_.m_129483_();
         } else {
            this.f_120690_.m_129541_();
         }
      }

   }
}