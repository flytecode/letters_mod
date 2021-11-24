package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.RateKickingConnection;
import net.minecraft.network.Varint21FrameDecoder;
import net.minecraft.network.Varint21LengthFieldPrepender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LazyLoadedValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConnectionListener {
   private static final Logger f_9701_ = LogManager.getLogger();
   private static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("forge.readTimeout", "30"));
   public static final LazyLoadedValue<NioEventLoopGroup> f_9698_ = new LazyLoadedValue<>(() -> {
      return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).setThreadFactory(net.minecraftforge.fml.util.thread.SidedThreadGroups.SERVER).build());
   });
   public static final LazyLoadedValue<EpollEventLoopGroup> f_9699_ = new LazyLoadedValue<>(() -> {
      return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).setThreadFactory(net.minecraftforge.fml.util.thread.SidedThreadGroups.SERVER).build());
   });
   final MinecraftServer f_9702_;
   public volatile boolean f_9700_;
   private final List<ChannelFuture> f_9703_ = Collections.synchronizedList(Lists.newArrayList());
   final List<Connection> f_9704_ = Collections.synchronizedList(Lists.newArrayList());

   public ServerConnectionListener(MinecraftServer p_9707_) {
      this.f_9702_ = p_9707_;
      this.f_9700_ = true;
   }

   public void m_9711_(@Nullable InetAddress p_9712_, int p_9713_) throws IOException {
      if (p_9712_ instanceof java.net.Inet6Address) System.setProperty("java.net.preferIPv4Stack", "false");
      synchronized(this.f_9703_) {
         Class<? extends ServerSocketChannel> oclass;
         LazyLoadedValue<? extends EventLoopGroup> lazyloadedvalue;
         if (Epoll.isAvailable() && this.f_9702_.m_6994_()) {
            oclass = EpollServerSocketChannel.class;
            lazyloadedvalue = f_9699_;
            f_9701_.info("Using epoll channel type");
         } else {
            oclass = NioServerSocketChannel.class;
            lazyloadedvalue = f_9698_;
            f_9701_.info("Using default channel type");
         }

         this.f_9703_.add((new ServerBootstrap()).channel(oclass).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_9729_) {
               try {
                  p_9729_.config().setOption(ChannelOption.TCP_NODELAY, true);
               } catch (ChannelException channelexception) {
               }

               p_9729_.pipeline().addLast("timeout", new ReadTimeoutHandler(READ_TIMEOUT)).addLast("legacy_query", new LegacyQueryHandler(ServerConnectionListener.this)).addLast("splitter", new Varint21FrameDecoder()).addLast("decoder", new PacketDecoder(PacketFlow.SERVERBOUND)).addLast("prepender", new Varint21LengthFieldPrepender()).addLast("encoder", new PacketEncoder(PacketFlow.CLIENTBOUND));
               int i = ServerConnectionListener.this.f_9702_.m_7032_();
               Connection connection = (Connection)(i > 0 ? new RateKickingConnection(i) : new Connection(PacketFlow.SERVERBOUND));
               ServerConnectionListener.this.f_9704_.add(connection);
               p_9729_.pipeline().addLast("packet_handler", connection);
               connection.m_129505_(new ServerHandshakePacketListenerImpl(ServerConnectionListener.this.f_9702_, connection));
            }
         }).group(lazyloadedvalue.m_13971_()).localAddress(p_9712_, p_9713_).bind().syncUninterruptibly());
      }
   }

   public SocketAddress m_9708_() {
      ChannelFuture channelfuture;
      synchronized(this.f_9703_) {
         channelfuture = (new ServerBootstrap()).channel(LocalServerChannel.class).childHandler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel p_9734_) {
               Connection connection = new Connection(PacketFlow.SERVERBOUND);
               connection.m_129505_(new MemoryServerHandshakePacketListenerImpl(ServerConnectionListener.this.f_9702_, connection));
               ServerConnectionListener.this.f_9704_.add(connection);
               p_9734_.pipeline().addLast("packet_handler", connection);
            }
         }).group(f_9698_.m_13971_()).localAddress(LocalAddress.ANY).bind().syncUninterruptibly();
         this.f_9703_.add(channelfuture);
      }

      return channelfuture.channel().localAddress();
   }

   public void m_9718_() {
      this.f_9700_ = false;

      for(ChannelFuture channelfuture : this.f_9703_) {
         try {
            channelfuture.channel().close().sync();
         } catch (InterruptedException interruptedexception) {
            f_9701_.error("Interrupted whilst closing channel");
         }
      }

   }

   public void m_9721_() {
      synchronized(this.f_9704_) {
         Iterator<Connection> iterator = this.f_9704_.iterator();

         while(iterator.hasNext()) {
            Connection connection = iterator.next();
            if (!connection.m_129537_()) {
               if (connection.m_129536_()) {
                  try {
                     connection.m_129483_();
                  } catch (Exception exception) {
                     if (connection.m_129531_()) {
                        throw new ReportedException(CrashReport.m_127521_(exception, "Ticking memory connection"));
                     }

                     f_9701_.warn("Failed to handle packet for {}", connection.m_129523_(), exception);
                     Component component = new TextComponent("Internal server error");
                     connection.m_129514_(new ClientboundDisconnectPacket(component), (p_9717_) -> {
                        connection.m_129507_(component);
                     });
                     connection.m_129540_();
                  }
               } else {
                  iterator.remove();
                  connection.m_129541_();
               }
            }
         }

      }
   }

   public MinecraftServer m_9722_() {
      return this.f_9702_;
   }

   static class LatencySimulator extends ChannelInboundHandlerAdapter {
      private static final Timer f_143587_ = new HashedWheelTimer();
      private final int f_143588_;
      private final int f_143589_;
      private final List<ServerConnectionListener.LatencySimulator.DelayedMessage> f_143590_ = Lists.newArrayList();

      public LatencySimulator(int p_143593_, int p_143594_) {
         this.f_143588_ = p_143593_;
         this.f_143589_ = p_143594_;
      }

      public void channelRead(ChannelHandlerContext p_143601_, Object p_143602_) {
         this.m_143595_(p_143601_, p_143602_);
      }

      private void m_143595_(ChannelHandlerContext p_143596_, Object p_143597_) {
         int i = this.f_143588_ + (int)(Math.random() * (double)this.f_143589_);
         this.f_143590_.add(new ServerConnectionListener.LatencySimulator.DelayedMessage(p_143596_, p_143597_));
         f_143587_.newTimeout(this::m_143598_, (long)i, TimeUnit.MILLISECONDS);
      }

      private void m_143598_(Timeout p_143599_) {
         ServerConnectionListener.LatencySimulator.DelayedMessage serverconnectionlistener$latencysimulator$delayedmessage = this.f_143590_.remove(0);
         serverconnectionlistener$latencysimulator$delayedmessage.f_143603_.fireChannelRead(serverconnectionlistener$latencysimulator$delayedmessage.f_143604_);
      }

      static class DelayedMessage {
         public final ChannelHandlerContext f_143603_;
         public final Object f_143604_;

         public DelayedMessage(ChannelHandlerContext p_143606_, Object p_143607_) {
            this.f_143603_ = p_143606_;
            this.f_143604_ = p_143607_;
         }
      }
   }
}
