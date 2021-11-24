package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.HttpUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientHandshakePacketListenerImpl implements ClientLoginPacketListener {
   private static final Logger f_104518_ = LogManager.getLogger();
   private final Minecraft f_104519_;
   @Nullable
   private final Screen f_104520_;
   private final Consumer<Component> f_104521_;
   private final Connection f_104522_;
   private GameProfile f_104523_;

   public ClientHandshakePacketListenerImpl(Connection p_104526_, Minecraft p_104527_, @Nullable Screen p_104528_, Consumer<Component> p_104529_) {
      this.f_104522_ = p_104526_;
      this.f_104519_ = p_104527_;
      this.f_104520_ = p_104528_;
      this.f_104521_ = p_104529_;
   }

   public void m_7318_(ClientboundHelloPacket p_104549_) {
      Cipher cipher;
      Cipher cipher1;
      String s;
      ServerboundKeyPacket serverboundkeypacket;
      try {
         SecretKey secretkey = Crypt.m_13578_();
         PublicKey publickey = p_104549_.m_134794_();
         s = (new BigInteger(Crypt.m_13590_(p_104549_.m_134791_(), publickey, secretkey))).toString(16);
         cipher = Crypt.m_13583_(2, secretkey);
         cipher1 = Crypt.m_13583_(1, secretkey);
         serverboundkeypacket = new ServerboundKeyPacket(secretkey, publickey, p_104549_.m_134795_());
      } catch (CryptException cryptexception) {
         throw new IllegalStateException("Protocol error", cryptexception);
      }

      this.f_104521_.accept(new TranslatableComponent("connect.authorizing"));
      HttpUtil.f_13936_.submit(() -> {
         Component component = this.m_104531_(s);
         if (component != null) {
            if (this.f_104519_.m_91089_() == null || !this.f_104519_.m_91089_().m_105389_()) {
               this.f_104522_.m_129507_(component);
               return;
            }

            f_104518_.warn(component.getString());
         }

         this.f_104521_.accept(new TranslatableComponent("connect.encrypting"));
         this.f_104522_.m_129514_(serverboundkeypacket, (p_171627_) -> {
            this.f_104522_.m_129495_(cipher, cipher1);
         });
      });
   }

   @Nullable
   private Component m_104531_(String p_104532_) {
      try {
         this.m_104554_().joinServer(this.f_104519_.m_91094_().m_92548_(), this.f_104519_.m_91094_().m_92547_(), p_104532_);
         return null;
      } catch (AuthenticationUnavailableException authenticationunavailableexception) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.serversUnavailable"));
      } catch (InvalidCredentialsException invalidcredentialsexception) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.invalidSession"));
      } catch (InsufficientPrivilegesException insufficientprivilegesexception) {
         return new TranslatableComponent("disconnect.loginFailedInfo", new TranslatableComponent("disconnect.loginFailedInfo.insufficientPrivileges"));
      } catch (AuthenticationException authenticationexception) {
         return new TranslatableComponent("disconnect.loginFailedInfo", authenticationexception.getMessage());
      }
   }

   private MinecraftSessionService m_104554_() {
      return this.f_104519_.m_91108_();
   }

   public void m_7056_(ClientboundGameProfilePacket p_104547_) {
      this.f_104521_.accept(new TranslatableComponent("connect.joining"));
      this.f_104523_ = p_104547_.m_134774_();
      this.f_104522_.m_129498_(ConnectionProtocol.PLAY);
      net.minecraftforge.fmllegacy.network.NetworkHooks.handleClientLoginSuccess(this.f_104522_);
      this.f_104522_.m_129505_(new ClientPacketListener(this.f_104519_, this.f_104520_, this.f_104522_, this.f_104523_));
   }

   public void m_7026_(Component p_104543_) {
      if (this.f_104520_ != null && this.f_104520_ instanceof RealmsScreen) {
         this.f_104519_.m_91152_(new DisconnectedRealmsScreen(this.f_104520_, CommonComponents.f_130661_, p_104543_));
      } else {
         this.f_104519_.m_91152_(new DisconnectedScreen(this.f_104520_, CommonComponents.f_130661_, p_104543_));
      }

   }

   public Connection m_6198_() {
      return this.f_104522_;
   }

   public void m_5800_(ClientboundLoginDisconnectPacket p_104553_) {
      this.f_104522_.m_129507_(p_104553_.m_134819_());
   }

   public void m_5693_(ClientboundLoginCompressionPacket p_104551_) {
      if (!this.f_104522_.m_129531_()) {
         this.f_104522_.m_129484_(p_104551_.m_134806_(), false);
      }

   }

   public void m_7254_(ClientboundCustomQueryPacket p_104545_) {
      if (net.minecraftforge.fmllegacy.network.NetworkHooks.onCustomPayload(p_104545_, this.f_104522_)) return;
      this.f_104521_.accept(new TranslatableComponent("connect.negotiating"));
      this.f_104522_.m_129512_(new ServerboundCustomQueryPacket(p_104545_.m_134755_(), (FriendlyByteBuf)null));
   }
}
