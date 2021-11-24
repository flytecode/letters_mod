package net.minecraft.server.rcon.thread;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.NetworkDataOutputStream;
import net.minecraft.server.rcon.PktUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryThreadGs4 extends GenericThread {
   private static final Logger f_11524_ = LogManager.getLogger();
   private static final String f_144024_ = "SMP";
   private static final String f_144025_ = "MINECRAFT";
   private static final long f_144026_ = 30000L;
   private static final long f_144027_ = 5000L;
   private long f_11525_;
   private final int f_11526_;
   private final int f_11527_;
   private final int f_11528_;
   private final String f_11529_;
   private final String f_11530_;
   private DatagramSocket f_11531_;
   private final byte[] f_11532_ = new byte[1460];
   private String f_11533_;
   private String f_11534_;
   private final Map<SocketAddress, QueryThreadGs4.RequestChallenge> f_11535_;
   private final NetworkDataOutputStream f_11536_;
   private long f_11537_;
   private final ServerInterface f_11538_;

   private QueryThreadGs4(ServerInterface p_11541_, int p_11542_) {
      super("Query Listener");
      this.f_11538_ = p_11541_;
      this.f_11526_ = p_11542_;
      this.f_11534_ = p_11541_.m_6866_();
      this.f_11527_ = p_11541_.m_7448_();
      this.f_11529_ = p_11541_.m_6995_();
      this.f_11528_ = p_11541_.m_7418_();
      this.f_11530_ = p_11541_.m_7123_();
      this.f_11537_ = 0L;
      this.f_11533_ = "0.0.0.0";
      if (!this.f_11534_.isEmpty() && !this.f_11533_.equals(this.f_11534_)) {
         this.f_11533_ = this.f_11534_;
      } else {
         this.f_11534_ = "0.0.0.0";

         try {
            InetAddress inetaddress = InetAddress.getLocalHost();
            this.f_11533_ = inetaddress.getHostAddress();
         } catch (UnknownHostException unknownhostexception) {
            f_11524_.warn("Unable to determine local host IP, please set server-ip in server.properties", (Throwable)unknownhostexception);
         }
      }

      this.f_11536_ = new NetworkDataOutputStream(1460);
      this.f_11535_ = Maps.newHashMap();
   }

   @Nullable
   public static QueryThreadGs4 m_11553_(ServerInterface p_11554_) {
      int i = p_11554_.m_7913_().f_139746_;
      if (0 < i && 65535 >= i) {
         QueryThreadGs4 querythreadgs4 = new QueryThreadGs4(p_11554_, i);
         return !querythreadgs4.m_7528_() ? null : querythreadgs4;
      } else {
         f_11524_.warn("Invalid query port {} found in server.properties (queries disabled)", (int)i);
         return null;
      }
   }

   private void m_11555_(byte[] p_11556_, DatagramPacket p_11557_) throws IOException {
      this.f_11531_.send(new DatagramPacket(p_11556_, p_11556_.length, p_11557_.getSocketAddress()));
   }

   private boolean m_11549_(DatagramPacket p_11550_) throws IOException {
      byte[] abyte = p_11550_.getData();
      int i = p_11550_.getLength();
      SocketAddress socketaddress = p_11550_.getSocketAddress();
      f_11524_.debug("Packet len {} [{}]", i, socketaddress);
      if (3 <= i && -2 == abyte[0] && -3 == abyte[1]) {
         f_11524_.debug("Packet '{}' [{}]", PktUtils.m_11483_(abyte[2]), socketaddress);
         switch(abyte[2]) {
         case 0:
            if (!this.m_11560_(p_11550_)) {
               f_11524_.debug("Invalid challenge [{}]", (Object)socketaddress);
               return false;
            } else if (15 == i) {
               this.m_11555_(this.m_11558_(p_11550_), p_11550_);
               f_11524_.debug("Rules [{}]", (Object)socketaddress);
            } else {
               NetworkDataOutputStream networkdataoutputstream = new NetworkDataOutputStream(1460);
               networkdataoutputstream.m_11472_(0);
               networkdataoutputstream.m_11478_(this.m_11551_(p_11550_.getSocketAddress()));
               networkdataoutputstream.m_11474_(this.f_11529_);
               networkdataoutputstream.m_11474_("SMP");
               networkdataoutputstream.m_11474_(this.f_11530_);
               networkdataoutputstream.m_11474_(Integer.toString(this.f_11538_.m_7416_()));
               networkdataoutputstream.m_11474_(Integer.toString(this.f_11528_));
               networkdataoutputstream.m_11476_((short)this.f_11527_);
               networkdataoutputstream.m_11474_(this.f_11533_);
               this.m_11555_(networkdataoutputstream.m_11471_(), p_11550_);
               f_11524_.debug("Status [{}]", (Object)socketaddress);
            }
         default:
            return true;
         case 9:
            this.m_11563_(p_11550_);
            f_11524_.debug("Challenge [{}]", (Object)socketaddress);
            return true;
         }
      } else {
         f_11524_.debug("Invalid packet [{}]", (Object)socketaddress);
         return false;
      }
   }

   private byte[] m_11558_(DatagramPacket p_11559_) throws IOException {
      long i = Util.m_137550_();
      if (i < this.f_11537_ + 5000L) {
         byte[] abyte = this.f_11536_.m_11471_();
         byte[] abyte1 = this.m_11551_(p_11559_.getSocketAddress());
         abyte[1] = abyte1[0];
         abyte[2] = abyte1[1];
         abyte[3] = abyte1[2];
         abyte[4] = abyte1[3];
         return abyte;
      } else {
         this.f_11537_ = i;
         this.f_11536_.m_11480_();
         this.f_11536_.m_11472_(0);
         this.f_11536_.m_11478_(this.m_11551_(p_11559_.getSocketAddress()));
         this.f_11536_.m_11474_("splitnum");
         this.f_11536_.m_11472_(128);
         this.f_11536_.m_11472_(0);
         this.f_11536_.m_11474_("hostname");
         this.f_11536_.m_11474_(this.f_11529_);
         this.f_11536_.m_11474_("gametype");
         this.f_11536_.m_11474_("SMP");
         this.f_11536_.m_11474_("game_id");
         this.f_11536_.m_11474_("MINECRAFT");
         this.f_11536_.m_11474_("version");
         this.f_11536_.m_11474_(this.f_11538_.m_7630_());
         this.f_11536_.m_11474_("plugins");
         this.f_11536_.m_11474_(this.f_11538_.m_7138_());
         this.f_11536_.m_11474_("map");
         this.f_11536_.m_11474_(this.f_11530_);
         this.f_11536_.m_11474_("numplayers");
         this.f_11536_.m_11474_("" + this.f_11538_.m_7416_());
         this.f_11536_.m_11474_("maxplayers");
         this.f_11536_.m_11474_("" + this.f_11528_);
         this.f_11536_.m_11474_("hostport");
         this.f_11536_.m_11474_("" + this.f_11527_);
         this.f_11536_.m_11474_("hostip");
         this.f_11536_.m_11474_(this.f_11533_);
         this.f_11536_.m_11472_(0);
         this.f_11536_.m_11472_(1);
         this.f_11536_.m_11474_("player_");
         this.f_11536_.m_11472_(0);
         String[] astring = this.f_11538_.m_7641_();

         for(String s : astring) {
            this.f_11536_.m_11474_(s);
         }

         this.f_11536_.m_11472_(0);
         return this.f_11536_.m_11471_();
      }
   }

   private byte[] m_11551_(SocketAddress p_11552_) {
      return this.f_11535_.get(p_11552_).m_11578_();
   }

   private Boolean m_11560_(DatagramPacket p_11561_) {
      SocketAddress socketaddress = p_11561_.getSocketAddress();
      if (!this.f_11535_.containsKey(socketaddress)) {
         return false;
      } else {
         byte[] abyte = p_11561_.getData();
         return this.f_11535_.get(socketaddress).m_11574_() == PktUtils.m_11496_(abyte, 7, p_11561_.getLength());
      }
   }

   private void m_11563_(DatagramPacket p_11564_) throws IOException {
      QueryThreadGs4.RequestChallenge querythreadgs4$requestchallenge = new QueryThreadGs4.RequestChallenge(p_11564_);
      this.f_11535_.put(p_11564_.getSocketAddress(), querythreadgs4$requestchallenge);
      this.m_11555_(querythreadgs4$requestchallenge.m_11577_(), p_11564_);
   }

   private void m_11562_() {
      if (this.f_11515_) {
         long i = Util.m_137550_();
         if (i >= this.f_11525_ + 30000L) {
            this.f_11525_ = i;
            this.f_11535_.values().removeIf((p_11546_) -> {
               return p_11546_.m_11575_(i);
            });
         }
      }
   }

   public void run() {
      f_11524_.info("Query running on {}:{}", this.f_11534_, this.f_11526_);
      this.f_11525_ = Util.m_137550_();
      DatagramPacket datagrampacket = new DatagramPacket(this.f_11532_, this.f_11532_.length);

      try {
         while(this.f_11515_) {
            try {
               this.f_11531_.receive(datagrampacket);
               this.m_11562_();
               this.m_11549_(datagrampacket);
            } catch (SocketTimeoutException sockettimeoutexception) {
               this.m_11562_();
            } catch (PortUnreachableException portunreachableexception) {
            } catch (IOException ioexception) {
               this.m_11547_(ioexception);
            }
         }
      } finally {
         f_11524_.debug("closeSocket: {}:{}", this.f_11534_, this.f_11526_);
         this.f_11531_.close();
      }

   }

   public boolean m_7528_() {
      if (this.f_11515_) {
         return true;
      } else {
         return !this.m_11565_() ? false : super.m_7528_();
      }
   }

   private void m_11547_(Exception p_11548_) {
      if (this.f_11515_) {
         f_11524_.warn("Unexpected exception", (Throwable)p_11548_);
         if (!this.m_11565_()) {
            f_11524_.error("Failed to recover from exception, shutting down!");
            this.f_11515_ = false;
         }

      }
   }

   private boolean m_11565_() {
      try {
         this.f_11531_ = new DatagramSocket(this.f_11526_, InetAddress.getByName(this.f_11534_));
         this.f_11531_.setSoTimeout(500);
         return true;
      } catch (Exception exception) {
         f_11524_.warn("Unable to initialise query system on {}:{}", this.f_11534_, this.f_11526_, exception);
         return false;
      }
   }

   static class RequestChallenge {
      private final long f_11567_ = (new Date()).getTime();
      private final int f_11568_;
      private final byte[] f_11569_;
      private final byte[] f_11570_;
      private final String f_11571_;

      public RequestChallenge(DatagramPacket p_11573_) {
         byte[] abyte = p_11573_.getData();
         this.f_11569_ = new byte[4];
         this.f_11569_[0] = abyte[3];
         this.f_11569_[1] = abyte[4];
         this.f_11569_[2] = abyte[5];
         this.f_11569_[3] = abyte[6];
         this.f_11571_ = new String(this.f_11569_, StandardCharsets.UTF_8);
         this.f_11568_ = (new Random()).nextInt(16777216);
         this.f_11570_ = String.format("\t%s%d\u0000", this.f_11571_, this.f_11568_).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean m_11575_(long p_11576_) {
         return this.f_11567_ < p_11576_;
      }

      public int m_11574_() {
         return this.f_11568_;
      }

      public byte[] m_11577_() {
         return this.f_11570_;
      }

      public byte[] m_11578_() {
         return this.f_11569_;
      }

      public String m_144028_() {
         return this.f_11571_;
      }
   }
}