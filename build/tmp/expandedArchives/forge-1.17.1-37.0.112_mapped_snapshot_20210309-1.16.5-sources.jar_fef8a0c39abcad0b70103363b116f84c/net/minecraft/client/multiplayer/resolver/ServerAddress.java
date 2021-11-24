package net.minecraft.client.multiplayer.resolver;

import com.google.common.net.HostAndPort;
import java.net.IDN;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public final class ServerAddress {
   private static final Logger f_171854_ = LogManager.getLogger();
   private final HostAndPort f_171855_;
   private static final ServerAddress f_171856_ = new ServerAddress(HostAndPort.fromParts("server.invalid", 25565));

   public ServerAddress(String p_171861_, int p_171862_) {
      this(HostAndPort.fromParts(p_171861_, p_171862_));
   }

   private ServerAddress(HostAndPort p_171859_) {
      this.f_171855_ = p_171859_;
   }

   public String m_171863_() {
      try {
         return IDN.toASCII(this.f_171855_.getHost());
      } catch (IllegalArgumentException illegalargumentexception) {
         return "";
      }
   }

   public int m_171866_() {
      return this.f_171855_.getPort();
   }

   public static ServerAddress m_171864_(String p_171865_) {
      if (p_171865_ == null) {
         return f_171856_;
      } else {
         try {
            HostAndPort hostandport = HostAndPort.fromString(p_171865_).withDefaultPort(25565);
            return hostandport.getHost().isEmpty() ? f_171856_ : new ServerAddress(hostandport);
         } catch (IllegalArgumentException illegalargumentexception) {
            f_171854_.info("Failed to parse URL {}", p_171865_, illegalargumentexception);
            return f_171856_;
         }
      }
   }

   public static boolean m_171867_(String p_171868_) {
      try {
         HostAndPort hostandport = HostAndPort.fromString(p_171868_);
         String s = hostandport.getHost();
         if (!s.isEmpty()) {
            IDN.toASCII(s);
            return true;
         }
      } catch (IllegalArgumentException illegalargumentexception) {
      }

      return false;
   }

   static int m_171869_(String p_171870_) {
      try {
         return Integer.parseInt(p_171870_.trim());
      } catch (Exception exception) {
         return 25565;
      }
   }

   public boolean equals(Object p_171872_) {
      if (this == p_171872_) {
         return true;
      } else {
         return p_171872_ instanceof ServerAddress ? this.f_171855_.equals(((ServerAddress)p_171872_).f_171855_) : false;
      }
   }

   public int hashCode() {
      return this.f_171855_.hashCode();
   }
}