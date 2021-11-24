package net.minecraft.world.level.timers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbacks<C> {
   private static final Logger f_82227_ = LogManager.getLogger();
   public static final TimerCallbacks<MinecraftServer> f_82226_ = (new TimerCallbacks<MinecraftServer>()).m_82232_(new FunctionCallback.Serializer()).m_82232_(new FunctionTagCallback.Serializer());
   private final Map<ResourceLocation, TimerCallback.Serializer<C, ?>> f_82228_ = Maps.newHashMap();
   private final Map<Class<?>, TimerCallback.Serializer<C, ?>> f_82229_ = Maps.newHashMap();

   public TimerCallbacks<C> m_82232_(TimerCallback.Serializer<C, ?> p_82233_) {
      this.f_82228_.put(p_82233_.m_82221_(), p_82233_);
      this.f_82229_.put(p_82233_.m_82224_(), p_82233_);
      return this;
   }

   private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> m_82236_(Class<?> p_82237_) {
      return (TimerCallback.Serializer<C, T>)this.f_82229_.get(p_82237_);
   }

   public <T extends TimerCallback<C>> CompoundTag m_82234_(T p_82235_) {
      TimerCallback.Serializer<C, T> serializer = this.m_82236_(p_82235_.getClass());
      CompoundTag compoundtag = new CompoundTag();
      serializer.m_6585_(compoundtag, p_82235_);
      compoundtag.m_128359_("Type", serializer.m_82221_().toString());
      return compoundtag;
   }

   @Nullable
   public TimerCallback<C> m_82238_(CompoundTag p_82239_) {
      ResourceLocation resourcelocation = ResourceLocation.m_135820_(p_82239_.m_128461_("Type"));
      TimerCallback.Serializer<C, ?> serializer = this.f_82228_.get(resourcelocation);
      if (serializer == null) {
         f_82227_.error("Failed to deserialize timer callback: {}", (Object)p_82239_);
         return null;
      } else {
         try {
            return serializer.m_6006_(p_82239_);
         } catch (Exception exception) {
            f_82227_.error("Failed to deserialize timer callback: {}", p_82239_, exception);
            return null;
         }
      }
   }
}