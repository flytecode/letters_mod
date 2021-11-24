package net.minecraft.client.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LanServerDetection {
   static final AtomicInteger f_120081_ = new AtomicInteger(0);
   static final Logger f_120082_ = LogManager.getLogger();

   @OnlyIn(Dist.CLIENT)
   public static class LanServerDetector extends Thread {
      private final LanServerDetection.LanServerList f_120086_;
      private final InetAddress f_120087_;
      private final MulticastSocket f_120088_;

      public LanServerDetector(LanServerDetection.LanServerList p_120090_) throws IOException {
         super("LanServerDetector #" + LanServerDetection.f_120081_.incrementAndGet());
         this.f_120086_ = p_120090_;
         this.setDaemon(true);
         this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LanServerDetection.f_120082_));
         this.f_120088_ = new MulticastSocket(4445);
         this.f_120087_ = InetAddress.getByName("224.0.2.60");
         this.f_120088_.setSoTimeout(5000);
         this.f_120088_.joinGroup(this.f_120087_);
      }

      public void run() {
         byte[] abyte = new byte[1024];

         while(!this.isInterrupted()) {
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);

            try {
               this.f_120088_.receive(datagrampacket);
            } catch (SocketTimeoutException sockettimeoutexception) {
               continue;
            } catch (IOException ioexception1) {
               LanServerDetection.f_120082_.error("Couldn't ping server", (Throwable)ioexception1);
               break;
            }

            String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength(), StandardCharsets.UTF_8);
            LanServerDetection.f_120082_.debug("{}: {}", datagrampacket.getAddress(), s);
            this.f_120086_.m_120096_(s, datagrampacket.getAddress());
         }

         try {
            this.f_120088_.leaveGroup(this.f_120087_);
         } catch (IOException ioexception) {
         }

         this.f_120088_.close();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class LanServerList {
      private final List<LanServer> f_120092_ = Lists.newArrayList();
      private boolean f_120093_;

      public synchronized boolean m_120095_() {
         return this.f_120093_;
      }

      public synchronized void m_120099_() {
         this.f_120093_ = false;
      }

      public synchronized List<LanServer> m_120100_() {
         return Collections.unmodifiableList(this.f_120092_);
      }

      public synchronized void m_120096_(String p_120097_, InetAddress p_120098_) {
         String s = LanServerPinger.m_120111_(p_120097_);
         String s1 = LanServerPinger.m_120116_(p_120097_);
         if (s1 != null) {
            s1 = p_120098_.getHostAddress() + ":" + s1;
            boolean flag = false;

            for(LanServer lanserver : this.f_120092_) {
               if (lanserver.m_120079_().equals(s1)) {
                  lanserver.m_120080_();
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               this.f_120092_.add(new LanServer(s, s1));
               this.f_120093_ = true;
            }

         }
      }
   }
}