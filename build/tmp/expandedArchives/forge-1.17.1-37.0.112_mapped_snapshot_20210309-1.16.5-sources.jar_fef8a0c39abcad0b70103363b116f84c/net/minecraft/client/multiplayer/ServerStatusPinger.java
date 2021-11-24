package net.minecraft.client.multiplayer;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.status.ClientStatusPacketListener;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ServerStatusPinger {
   static final Splitter f_105448_ = Splitter.on('\u0000').limit(6);
   static final Logger f_105449_ = LogManager.getLogger();
   private static final Component f_171810_ = (new TranslatableComponent("multiplayer.status.cannot_connect")).m_130940_(ChatFormatting.DARK_RED);
   private final List<Connection> f_105450_ = Collections.synchronizedList(Lists.newArrayList());

   public void m_105459_(final ServerData p_105460_, final Runnable p_105461_) throws UnknownHostException {
      ServerAddress serveraddress = ServerAddress.m_171864_(p_105460_.f_105363_);
      Optional<InetSocketAddress> optional = ServerNameResolver.f_171881_.m_171890_(serveraddress).map(ResolvedServerAddress::m_142641_);
      if (!optional.isPresent()) {
         this.m_171814_(ConnectScreen.f_169260_, p_105460_);
      } else {
         final InetSocketAddress inetsocketaddress = optional.get();
         final Connection connection = Connection.m_178300_(inetsocketaddress, false);
         this.f_105450_.add(connection);
         p_105460_.f_105365_ = new TranslatableComponent("multiplayer.status.pinging");
         p_105460_.f_105366_ = -1L;
         p_105460_.f_105370_ = null;
         connection.m_129505_(new ClientStatusPacketListener() {
            private boolean f_105475_;
            private boolean f_105476_;
            private long f_105477_;

            public void m_6440_(ClientboundStatusResponsePacket p_105489_) {
               if (this.f_105476_) {
                  connection.m_129507_(new TranslatableComponent("multiplayer.status.unrequested"));
               } else {
                  this.f_105476_ = true;
                  ServerStatus serverstatus = p_105489_.m_134897_();
                  if (serverstatus.m_134905_() != null) {
                     p_105460_.f_105365_ = serverstatus.m_134905_();
                  } else {
                     p_105460_.f_105365_ = TextComponent.f_131282_;
                  }

                  if (serverstatus.m_134915_() != null) {
                     p_105460_.f_105368_ = new TextComponent(serverstatus.m_134915_().m_134967_());
                     p_105460_.f_105367_ = serverstatus.m_134915_().m_134968_();
                  } else {
                     p_105460_.f_105368_ = new TranslatableComponent("multiplayer.status.old");
                     p_105460_.f_105367_ = 0;
                  }

                  if (serverstatus.m_134914_() != null) {
                     p_105460_.f_105364_ = ServerStatusPinger.m_105466_(serverstatus.m_134914_().m_134926_(), serverstatus.m_134914_().m_134923_());
                     List<Component> list = Lists.newArrayList();
                     if (ArrayUtils.isNotEmpty(serverstatus.m_134914_().m_134927_())) {
                        for(GameProfile gameprofile : serverstatus.m_134914_().m_134927_()) {
                           list.add(new TextComponent(gameprofile.getName()));
                        }

                        if (serverstatus.m_134914_().m_134927_().length < serverstatus.m_134914_().m_134926_()) {
                           list.add(new TranslatableComponent("multiplayer.status.and_more", serverstatus.m_134914_().m_134926_() - serverstatus.m_134914_().m_134927_().length));
                        }

                        p_105460_.f_105370_ = list;
                     }
                  } else {
                     p_105460_.f_105364_ = (new TranslatableComponent("multiplayer.status.unknown")).m_130940_(ChatFormatting.DARK_GRAY);
                  }

                  String s = null;
                  if (serverstatus.m_134916_() != null) {
                     String s1 = serverstatus.m_134916_();
                     if (s1.startsWith("data:image/png;base64,")) {
                        s = s1.substring("data:image/png;base64,".length());
                     } else {
                        ServerStatusPinger.f_105449_.error("Invalid server icon (unknown format)");
                     }
                  }

                  if (!Objects.equals(s, p_105460_.m_105388_())) {
                     p_105460_.m_105383_(s);
                     p_105461_.run();
                  }

                  net.minecraftforge.client.ForgeHooksClient.processForgeListPingData(serverstatus, p_105460_);
                  this.f_105477_ = Util.m_137550_();
                  connection.m_129512_(new ServerboundPingRequestPacket(this.f_105477_));
                  this.f_105475_ = true;
               }
            }

            public void m_7017_(ClientboundPongResponsePacket p_105487_) {
               long i = this.f_105477_;
               long j = Util.m_137550_();
               p_105460_.f_105366_ = j - i;
               connection.m_129507_(new TranslatableComponent("multiplayer.status.finished"));
            }

            public void m_7026_(Component p_105485_) {
               if (!this.f_105475_) {
                  ServerStatusPinger.this.m_171814_(p_105485_, p_105460_);
                  ServerStatusPinger.this.m_171811_(inetsocketaddress, p_105460_);
               }

            }

            public Connection m_6198_() {
               return connection;
            }
         });

         try {
            connection.m_129512_(new ClientIntentionPacket(serveraddress.m_171863_(), serveraddress.m_171866_(), ConnectionProtocol.STATUS));
            connection.m_129512_(new ServerboundStatusRequestPacket());
         } catch (Throwable throwable) {
            f_105449_.error(throwable);
         }

      }
   }

   void m_171814_(Component p_171815_, ServerData p_171816_) {
      f_105449_.error("Can't ping {}: {}", p_171816_.f_105363_, p_171815_.getString());
      p_171816_.f_105365_ = f_171810_;
      p_171816_.f_105364_ = TextComponent.f_131282_;
   }

   void m_171811_(final InetSocketAddress p_171812_, final ServerData p_171813_) {
      (new Bootstrap()).group(Connection.f_129462_.m_13971_()).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel p_105498_) {
            try {
               p_105498_.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException channelexception) {
            }

            p_105498_.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
               public void channelActive(ChannelHandlerContext p_105506_) throws Exception {
                  super.channelActive(p_105506_);
                  ByteBuf bytebuf = Unpooled.buffer();

                  try {
                     bytebuf.writeByte(254);
                     bytebuf.writeByte(1);
                     bytebuf.writeByte(250);
                     char[] achar = "MC|PingHost".toCharArray();
                     bytebuf.writeShort(achar.length);

                     for(char c0 : achar) {
                        bytebuf.writeChar(c0);
                     }

                     bytebuf.writeShort(7 + 2 * p_171812_.getHostName().length());
                     bytebuf.writeByte(127);
                     achar = p_171812_.getHostName().toCharArray();
                     bytebuf.writeShort(achar.length);

                     for(char c1 : achar) {
                        bytebuf.writeChar(c1);
                     }

                     bytebuf.writeInt(p_171812_.getPort());
                     p_105506_.channel().writeAndFlush(bytebuf).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                  } finally {
                     bytebuf.release();
                  }

               }

               protected void channelRead0(ChannelHandlerContext p_105503_, ByteBuf p_105504_) {
                  short short1 = p_105504_.readUnsignedByte();
                  if (short1 == 255) {
                     String s = new String(p_105504_.readBytes(p_105504_.readShort() * 2).array(), StandardCharsets.UTF_16BE);
                     String[] astring = Iterables.toArray(ServerStatusPinger.f_105448_.split(s), String.class);
                     if ("\u00a71".equals(astring[0])) {
                        int i = Mth.m_14059_(astring[1], 0);
                        String s1 = astring[2];
                        String s2 = astring[3];
                        int j = Mth.m_14059_(astring[4], -1);
                        int k = Mth.m_14059_(astring[5], -1);
                        p_171813_.f_105367_ = -1;
                        p_171813_.f_105368_ = new TextComponent(s1);
                        p_171813_.f_105365_ = new TextComponent(s2);
                        p_171813_.f_105364_ = ServerStatusPinger.m_105466_(j, k);
                     }
                  }

                  p_105503_.close();
               }

               public void exceptionCaught(ChannelHandlerContext p_105511_, Throwable p_105512_) {
                  p_105511_.close();
               }
            });
         }
      }).channel(NioSocketChannel.class).connect(p_171812_.getAddress(), p_171812_.getPort());
   }

   static Component m_105466_(int p_105467_, int p_105468_) {
      return (new TextComponent(Integer.toString(p_105467_))).m_7220_((new TextComponent("/")).m_130940_(ChatFormatting.DARK_GRAY)).m_130946_(Integer.toString(p_105468_)).m_130940_(ChatFormatting.GRAY);
   }

   public void m_105453_() {
      synchronized(this.f_105450_) {
         Iterator<Connection> iterator = this.f_105450_.iterator();

         while(iterator.hasNext()) {
            Connection connection = iterator.next();
            if (connection.m_129536_()) {
               connection.m_129483_();
            } else {
               iterator.remove();
               connection.m_129541_();
            }
         }

      }
   }

   public void m_105465_() {
      synchronized(this.f_105450_) {
         Iterator<Connection> iterator = this.f_105450_.iterator();

         while(iterator.hasNext()) {
            Connection connection = iterator.next();
            if (connection.m_129536_()) {
               iterator.remove();
               connection.m_129507_(new TranslatableComponent("multiplayer.status.cancelled"));
            }
         }

      }
   }
}
