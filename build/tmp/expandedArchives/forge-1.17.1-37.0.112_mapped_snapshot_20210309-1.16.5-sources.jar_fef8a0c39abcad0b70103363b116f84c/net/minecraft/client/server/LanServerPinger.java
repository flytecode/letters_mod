package net.minecraft.client.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class LanServerPinger extends Thread {
   private static final AtomicInteger f_120101_ = new AtomicInteger(0);
   private static final Logger f_120102_ = LogManager.getLogger();
   public static final String f_174974_ = "224.0.2.60";
   public static final int f_174975_ = 4445;
   private static final long f_174976_ = 1500L;
   private final String f_120103_;
   private final DatagramSocket f_120104_;
   private boolean f_120105_ = true;
   private final String f_120106_;

   public LanServerPinger(String p_120109_, String p_120110_) throws IOException {
      super("LanServerPinger #" + f_120101_.incrementAndGet());
      this.f_120103_ = p_120109_;
      this.f_120106_ = p_120110_;
      this.setDaemon(true);
      this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_120102_));
      this.f_120104_ = new DatagramSocket();
   }

   public void run() {
      String s = m_120113_(this.f_120103_, this.f_120106_);
      byte[] abyte = s.getBytes(StandardCharsets.UTF_8);

      while(!this.isInterrupted() && this.f_120105_) {
         try {
            InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
            this.f_120104_.send(datagrampacket);
         } catch (IOException ioexception) {
            f_120102_.warn("LanServerPinger: {}", (Object)ioexception.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException interruptedexception) {
         }
      }

   }

   public void interrupt() {
      super.interrupt();
      this.f_120105_ = false;
   }

   public static String m_120113_(String p_120114_, String p_120115_) {
      return "[MOTD]" + p_120114_ + "[/MOTD][AD]" + p_120115_ + "[/AD]";
   }

   public static String m_120111_(String p_120112_) {
      int i = p_120112_.indexOf("[MOTD]");
      if (i < 0) {
         return "missing no";
      } else {
         int j = p_120112_.indexOf("[/MOTD]", i + "[MOTD]".length());
         return j < i ? "missing no" : p_120112_.substring(i + "[MOTD]".length(), j);
      }
   }

   public static String m_120116_(String p_120117_) {
      int i = p_120117_.indexOf("[/MOTD]");
      if (i < 0) {
         return null;
      } else {
         int j = p_120117_.indexOf("[/MOTD]", i + "[/MOTD]".length());
         if (j >= 0) {
            return null;
         } else {
            int k = p_120117_.indexOf("[AD]", i + "[/MOTD]".length());
            if (k < 0) {
               return null;
            } else {
               int l = p_120117_.indexOf("[/AD]", k + "[AD]".length());
               return l < k ? null : p_120117_.substring(k + "[AD]".length(), l);
            }
         }
      }
   }
}