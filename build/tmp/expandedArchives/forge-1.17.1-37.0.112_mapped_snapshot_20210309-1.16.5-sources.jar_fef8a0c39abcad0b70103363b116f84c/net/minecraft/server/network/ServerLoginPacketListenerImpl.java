package net.minecraft.server.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLoginPacketListenerImpl implements ServerLoginPacketListener {
   private static final AtomicInteger f_10014_ = new AtomicInteger(0);
   static final Logger f_10015_ = LogManager.getLogger();
   private static final int f_143698_ = 600;
   private static final Random f_10016_ = new Random();
   private final byte[] f_10017_ = new byte[4];
   final MinecraftServer f_10018_;
   public final Connection f_10013_;
   ServerLoginPacketListenerImpl.State f_10019_ = ServerLoginPacketListenerImpl.State.HELLO;
   private int f_10020_;
   @Nullable
   GameProfile f_10021_;
   private final String f_10022_ = "";
   @Nullable
   private ServerPlayer f_10024_;

   public ServerLoginPacketListenerImpl(MinecraftServer p_10027_, Connection p_10028_) {
      this.f_10018_ = p_10027_;
      this.f_10013_ = p_10028_;
      f_10016_.nextBytes(this.f_10017_);
   }

   public void m_10050_() {
      if (this.f_10019_ == State.NEGOTIATING) {
         // We force the state into "NEGOTIATING" which is otherwise unused. Once we're completed we move the negotiation onto "READY_TO_ACCEPT"
         // Might want to promote player object creation to here as well..
         boolean negotiationComplete = net.minecraftforge.fmllegacy.network.NetworkHooks.tickNegotiation(this, this.f_10013_, this.f_10024_);
         if (negotiationComplete)
            this.f_10019_ = State.READY_TO_ACCEPT;
      } else if (this.f_10019_ == ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT) {
         this.m_10055_();
      } else if (this.f_10019_ == ServerLoginPacketListenerImpl.State.DELAY_ACCEPT) {
         ServerPlayer serverplayer = this.f_10018_.m_6846_().m_11259_(this.f_10021_.getId());
         if (serverplayer == null) {
            this.f_10019_ = ServerLoginPacketListenerImpl.State.READY_TO_ACCEPT;
            this.m_143699_(this.f_10024_);
            this.f_10024_ = null;
         }
      }

      if (this.f_10020_++ == 600) {
         this.m_10053_(new TranslatableComponent("multiplayer.disconnect.slow_login"));
      }

   }

   public Connection m_6198_() {
      return this.f_10013_;
   }

   public void m_10053_(Component p_10054_) {
      try {
         f_10015_.info("Disconnecting {}: {}", this.m_10056_(), p_10054_.getString());
         this.f_10013_.m_129512_(new ClientboundLoginDisconnectPacket(p_10054_));
         this.f_10013_.m_129507_(p_10054_);
      } catch (Exception exception) {
         f_10015_.error("Error whilst disconnecting player", (Throwable)exception);
      }

   }

   public void m_10055_() {
      if (!this.f_10021_.isComplete()) {
         this.f_10021_ = this.m_10038_(this.f_10021_);
      }

      Component component = this.f_10018_.m_6846_().m_6418_(this.f_10013_.m_129523_(), this.f_10021_);
      if (component != null) {
         this.m_10053_(component);
      } else {
         this.f_10019_ = ServerLoginPacketListenerImpl.State.ACCEPTED;
         if (this.f_10018_.m_6328_() >= 0 && !this.f_10013_.m_129531_()) {
            this.f_10013_.m_129514_(new ClientboundLoginCompressionPacket(this.f_10018_.m_6328_()), (p_10041_) -> {
               this.f_10013_.m_129484_(this.f_10018_.m_6328_(), true);
            });
         }

         this.f_10013_.m_129512_(new ClientboundGameProfilePacket(this.f_10021_));
         ServerPlayer serverplayer = this.f_10018_.m_6846_().m_11259_(this.f_10021_.getId());

         try {
            ServerPlayer serverplayer1 = this.f_10018_.m_6846_().m_11300_(this.f_10021_);
            if (serverplayer != null) {
               this.f_10019_ = ServerLoginPacketListenerImpl.State.DELAY_ACCEPT;
               this.f_10024_ = serverplayer1;
            } else {
               this.m_143699_(serverplayer1);
            }
         } catch (Exception exception) {
            Component component1 = new TranslatableComponent("multiplayer.disconnect.invalid_player_data");
            this.f_10013_.m_129512_(new ClientboundDisconnectPacket(component1));
            this.f_10013_.m_129507_(component1);
         }
      }

   }

   private void m_143699_(ServerPlayer p_143700_) {
      this.f_10018_.m_6846_().m_11261_(this.f_10013_, p_143700_);
   }

   public void m_7026_(Component p_10043_) {
      f_10015_.info("{} lost connection: {}", this.m_10056_(), p_10043_.getString());
   }

   public String m_10056_() {
      return this.f_10021_ != null ? this.f_10021_ + " (" + this.f_10013_.m_129523_() + ")" : String.valueOf((Object)this.f_10013_.m_129523_());
   }

   public void m_5990_(ServerboundHelloPacket p_10047_) {
      Validate.validState(this.f_10019_ == ServerLoginPacketListenerImpl.State.HELLO, "Unexpected hello packet");
      this.f_10021_ = p_10047_.m_134849_();
      if (this.f_10018_.m_129797_() && !this.f_10013_.m_129531_()) {
         this.f_10019_ = ServerLoginPacketListenerImpl.State.KEY;
         this.f_10013_.m_129512_(new ClientboundHelloPacket("", this.f_10018_.m_129790_().getPublic().getEncoded(), this.f_10017_));
      } else {
         this.f_10019_ = ServerLoginPacketListenerImpl.State.NEGOTIATING;
      }

   }

   public void m_8072_(ServerboundKeyPacket p_10049_) {
      Validate.validState(this.f_10019_ == ServerLoginPacketListenerImpl.State.KEY, "Unexpected key packet");
      PrivateKey privatekey = this.f_10018_.m_129790_().getPrivate();

      final String s;
      try {
         if (!Arrays.equals(this.f_10017_, p_10049_.m_134867_(privatekey))) {
            throw new IllegalStateException("Protocol error");
         }

         SecretKey secretkey = p_10049_.m_134859_(privatekey);
         Cipher cipher = Crypt.m_13583_(2, secretkey);
         Cipher cipher1 = Crypt.m_13583_(1, secretkey);
         s = (new BigInteger(Crypt.m_13590_("", this.f_10018_.m_129790_().getPublic(), secretkey))).toString(16);
         this.f_10019_ = ServerLoginPacketListenerImpl.State.AUTHENTICATING;
         this.f_10013_.m_129495_(cipher, cipher1);
      } catch (CryptException cryptexception) {
         throw new IllegalStateException("Protocol error", cryptexception);
      }

      Thread thread = new Thread(net.minecraftforge.fml.util.thread.SidedThreadGroups.SERVER, "User Authenticator #" + f_10014_.incrementAndGet()) {
         public void run() {
            GameProfile gameprofile = ServerLoginPacketListenerImpl.this.f_10021_;

            try {
               ServerLoginPacketListenerImpl.this.f_10021_ = ServerLoginPacketListenerImpl.this.f_10018_.m_129925_().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s, this.m_10064_());
               if (ServerLoginPacketListenerImpl.this.f_10021_ != null) {
                  ServerLoginPacketListenerImpl.f_10015_.info("UUID of player {} is {}", ServerLoginPacketListenerImpl.this.f_10021_.getName(), ServerLoginPacketListenerImpl.this.f_10021_.getId());
                  ServerLoginPacketListenerImpl.this.f_10019_ = ServerLoginPacketListenerImpl.State.NEGOTIATING;
               } else if (ServerLoginPacketListenerImpl.this.f_10018_.m_129792_()) {
                  ServerLoginPacketListenerImpl.f_10015_.warn("Failed to verify username but will let them in anyway!");
                  ServerLoginPacketListenerImpl.this.f_10021_ = ServerLoginPacketListenerImpl.this.m_10038_(gameprofile);
                  ServerLoginPacketListenerImpl.this.f_10019_ = ServerLoginPacketListenerImpl.State.NEGOTIATING;
               } else {
                  ServerLoginPacketListenerImpl.this.m_10053_(new TranslatableComponent("multiplayer.disconnect.unverified_username"));
                  ServerLoginPacketListenerImpl.f_10015_.error("Username '{}' tried to join with an invalid session", (Object)gameprofile.getName());
               }
            } catch (AuthenticationUnavailableException authenticationunavailableexception) {
               if (ServerLoginPacketListenerImpl.this.f_10018_.m_129792_()) {
                  ServerLoginPacketListenerImpl.f_10015_.warn("Authentication servers are down but will let them in anyway!");
                  ServerLoginPacketListenerImpl.this.f_10021_ = ServerLoginPacketListenerImpl.this.m_10038_(gameprofile);
                  ServerLoginPacketListenerImpl.this.f_10019_ = ServerLoginPacketListenerImpl.State.NEGOTIATING;
               } else {
                  ServerLoginPacketListenerImpl.this.m_10053_(new TranslatableComponent("multiplayer.disconnect.authservers_down"));
                  ServerLoginPacketListenerImpl.f_10015_.error("Couldn't verify username because servers are unavailable");
               }
            }

         }

         @Nullable
         private InetAddress m_10064_() {
            SocketAddress socketaddress = ServerLoginPacketListenerImpl.this.f_10013_.m_129523_();
            return ServerLoginPacketListenerImpl.this.f_10018_.m_129798_() && socketaddress instanceof InetSocketAddress ? ((InetSocketAddress)socketaddress).getAddress() : null;
         }
      };
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_10015_));
      thread.start();
   }

   public void m_7223_(ServerboundCustomQueryPacket p_10045_) {
      if (!net.minecraftforge.fmllegacy.network.NetworkHooks.onCustomPayload(p_10045_, this.f_10013_))
      this.m_10053_(new TranslatableComponent("multiplayer.disconnect.unexpected_query_response"));
   }

   protected GameProfile m_10038_(GameProfile p_10039_) {
      UUID uuid = Player.m_36283_(p_10039_.getName());
      return new GameProfile(uuid, p_10039_.getName());
   }

   static enum State {
      HELLO,
      KEY,
      AUTHENTICATING,
      NEGOTIATING,
      READY_TO_ACCEPT,
      DELAY_ACCEPT,
      ACCEPTED;
   }
}
