package net.minecraft.client.multiplayer.resolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface ServerAddressResolver {
   Logger f_171874_ = LogManager.getLogger();
   ServerAddressResolver f_171875_ = (p_171878_) -> {
      try {
         InetAddress inetaddress = InetAddress.getByName(p_171878_.m_171863_());
         return Optional.of(ResolvedServerAddress.m_171845_(new InetSocketAddress(inetaddress, p_171878_.m_171866_())));
      } catch (UnknownHostException unknownhostexception) {
         f_171874_.debug("Couldn't resolve server {} address", p_171878_.m_171863_(), unknownhostexception);
         return Optional.empty();
      }
   };

   Optional<ResolvedServerAddress> m_171879_(ServerAddress p_171880_);
}