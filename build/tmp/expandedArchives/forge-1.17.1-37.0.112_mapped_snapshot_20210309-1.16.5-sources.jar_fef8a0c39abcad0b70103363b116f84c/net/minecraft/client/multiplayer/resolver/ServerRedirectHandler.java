package net.minecraft.client.multiplayer.resolver;

import java.util.Hashtable;
import java.util.Optional;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface ServerRedirectHandler {
   Logger f_171892_ = LogManager.getLogger();
   ServerRedirectHandler f_171893_ = (p_171897_) -> {
      return Optional.empty();
   };

   Optional<ServerAddress> m_171901_(ServerAddress p_171902_);

   static ServerRedirectHandler m_171895_() {
      DirContext dircontext;
      try {
         String s = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable<String, String> hashtable = new Hashtable<>();
         hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         hashtable.put("java.naming.provider.url", "dns:");
         hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
         dircontext = new InitialDirContext(hashtable);
      } catch (Throwable throwable) {
         f_171892_.error("Failed to initialize SRV redirect resolved, some servers might not work", throwable);
         return f_171893_;
      }

      return (p_171900_) -> {
         if (p_171900_.m_171866_() == 25565) {
            try {
               Attributes attributes = dircontext.getAttributes("_minecraft._tcp." + p_171900_.m_171863_(), new String[]{"SRV"});
               Attribute attribute = attributes.get("srv");
               if (attribute != null) {
                  String[] astring = attribute.get().toString().split(" ", 4);
                  return Optional.of(new ServerAddress(astring[3], ServerAddress.m_171869_(astring[2])));
               }
            } catch (Throwable throwable1) {
            }
         }

         return Optional.empty();
      };
   }
}