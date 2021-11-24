package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;

public class ClientboundAwardStatsPacket implements Packet<ClientGamePacketListener> {
   private final Object2IntMap<Stat<?>> f_131628_;

   public ClientboundAwardStatsPacket(Object2IntMap<Stat<?>> p_131631_) {
      this.f_131628_ = p_131631_;
   }

   public ClientboundAwardStatsPacket(FriendlyByteBuf p_178592_) {
      this.f_131628_ = p_178592_.m_178374_(Object2IntOpenHashMap::new, (p_178602_) -> {
         int i = p_178602_.m_130242_();
         int j = p_178602_.m_130242_();
         return m_178595_(Registry.f_122867_.m_7942_(i), j);
      }, FriendlyByteBuf::m_130242_);
   }

   private static <T> Stat<T> m_178595_(StatType<T> p_178596_, int p_178597_) {
      return p_178596_.m_12902_(p_178596_.m_12893_().m_7942_(p_178597_));
   }

   public void m_5797_(ClientGamePacketListener p_131642_) {
      p_131642_.m_7271_(this);
   }

   public void m_5779_(FriendlyByteBuf p_131645_) {
      p_131645_.m_178355_(this.f_131628_, (p_178599_, p_178600_) -> {
         p_178599_.m_130130_(Registry.f_122867_.m_7447_(p_178600_.m_12859_()));
         p_178599_.m_130130_(this.m_178593_(p_178600_));
      }, FriendlyByteBuf::m_130130_);
   }

   private <T> int m_178593_(Stat<T> p_178594_) {
      return p_178594_.m_12859_().m_12893_().m_7447_(p_178594_.m_12867_());
   }

   public Map<Stat<?>, Integer> m_131643_() {
      return this.f_131628_;
   }
}