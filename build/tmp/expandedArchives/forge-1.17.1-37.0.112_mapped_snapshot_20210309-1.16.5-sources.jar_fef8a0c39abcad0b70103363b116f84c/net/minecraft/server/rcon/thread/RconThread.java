package net.minecraft.server.rcon.thread;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconThread extends GenericThread {
   private static final Logger f_11601_ = LogManager.getLogger();
   private final ServerSocket f_11602_;
   private final String f_11603_;
   private final List<RconClient> f_11604_ = Lists.newArrayList();
   private final ServerInterface f_11605_;

   private RconThread(ServerInterface p_11608_, ServerSocket p_11609_, String p_11610_) {
      super("RCON Listener");
      this.f_11605_ = p_11608_;
      this.f_11602_ = p_11609_;
      this.f_11603_ = p_11610_;
   }

   private void m_11618_() {
      this.f_11604_.removeIf((p_11612_) -> {
         return !p_11612_.m_11523_();
      });
   }

   public void run() {
      try {
         while(this.f_11515_) {
            try {
               Socket socket = this.f_11602_.accept();
               RconClient rconclient = new RconClient(this.f_11605_, this.f_11603_, socket);
               rconclient.m_7528_();
               this.f_11604_.add(rconclient);
               this.m_11618_();
            } catch (SocketTimeoutException sockettimeoutexception) {
               this.m_11618_();
            } catch (IOException ioexception) {
               if (this.f_11515_) {
                  f_11601_.info("IO exception: ", (Throwable)ioexception);
               }
            }
         }
      } finally {
         this.m_11613_(this.f_11602_);
      }

   }

   @Nullable
   public static RconThread m_11615_(ServerInterface p_11616_) {
      DedicatedServerProperties dedicatedserverproperties = p_11616_.m_7913_();
      String s = p_11616_.m_6866_();
      if (s.isEmpty()) {
         s = "0.0.0.0";
      }

      int i = dedicatedserverproperties.f_139748_;
      if (0 < i && 65535 >= i) {
         String s1 = dedicatedserverproperties.f_139749_;
         if (s1.isEmpty()) {
            f_11601_.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
         } else {
            try {
               ServerSocket serversocket = new ServerSocket(i, 0, InetAddress.getByName(s));
               serversocket.setSoTimeout(500);
               RconThread rconthread = new RconThread(p_11616_, serversocket, s1);
               if (!rconthread.m_7528_()) {
                  return null;
               } else {
                  f_11601_.info("RCON running on {}:{}", s, i);
                  return rconthread;
               }
            } catch (IOException ioexception) {
               f_11601_.warn("Unable to initialise RCON on {}:{}", s, i, ioexception);
               return null;
            }
         }
      } else {
         f_11601_.warn("Invalid rcon port {} found in server.properties, rcon disabled!", (int)i);
         return null;
      }
   }

   public void m_7530_() {
      this.f_11515_ = false;
      this.m_11613_(this.f_11602_);
      super.m_7530_();

      for(RconClient rconclient : this.f_11604_) {
         if (rconclient.m_11523_()) {
            rconclient.m_7530_();
         }
      }

      this.f_11604_.clear();
   }

   private void m_11613_(ServerSocket p_11614_) {
      f_11601_.debug("closeSocket: {}", (Object)p_11614_);

      try {
         p_11614_.close();
      } catch (IOException ioexception) {
         f_11601_.warn("Failed to close socket", (Throwable)ioexception);
      }

   }
}