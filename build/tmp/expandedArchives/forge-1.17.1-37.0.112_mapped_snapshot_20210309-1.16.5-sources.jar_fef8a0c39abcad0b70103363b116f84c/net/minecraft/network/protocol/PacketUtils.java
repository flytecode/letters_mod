package net.minecraft.network.protocol;

import net.minecraft.network.PacketListener;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.thread.BlockableEventLoop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketUtils {
   private static final Logger f_131354_ = LogManager.getLogger();

   public static <T extends PacketListener> void m_131359_(Packet<T> p_131360_, T p_131361_, ServerLevel p_131362_) throws RunningOnDifferentThreadException {
      m_131363_(p_131360_, p_131361_, p_131362_.m_142572_());
   }

   public static <T extends PacketListener> void m_131363_(Packet<T> p_131364_, T p_131365_, BlockableEventLoop<?> p_131366_) throws RunningOnDifferentThreadException {
      if (!p_131366_.m_18695_()) {
         p_131366_.execute(() -> {
            if (p_131365_.m_6198_().m_129536_()) {
               p_131364_.m_5797_(p_131365_);
            } else {
               f_131354_.debug("Ignoring packet due to disconnection: {}", (Object)p_131364_);
            }

         });
         throw RunningOnDifferentThreadException.f_136017_;
      }
   }
}