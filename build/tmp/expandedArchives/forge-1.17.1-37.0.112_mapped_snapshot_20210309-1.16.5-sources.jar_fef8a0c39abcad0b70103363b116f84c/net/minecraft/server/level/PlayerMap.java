package net.minecraft.server.level;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;

public final class PlayerMap {
   private final Object2BooleanMap<ServerPlayer> f_8241_ = new Object2BooleanOpenHashMap<>();

   public Stream<ServerPlayer> m_8243_(long p_8244_) {
      return this.f_8241_.keySet().stream();
   }

   public void m_8252_(long p_8253_, ServerPlayer p_8254_, boolean p_8255_) {
      this.f_8241_.put(p_8254_, p_8255_);
   }

   public void m_8249_(long p_8250_, ServerPlayer p_8251_) {
      this.f_8241_.removeBoolean(p_8251_);
   }

   public void m_8256_(ServerPlayer p_8257_) {
      this.f_8241_.replace(p_8257_, true);
   }

   public void m_8258_(ServerPlayer p_8259_) {
      this.f_8241_.replace(p_8259_, false);
   }

   public boolean m_8260_(ServerPlayer p_8261_) {
      return this.f_8241_.getOrDefault(p_8261_, true);
   }

   public boolean m_8262_(ServerPlayer p_8263_) {
      return this.f_8241_.getBoolean(p_8263_);
   }

   public void m_8245_(long p_8246_, long p_8247_, ServerPlayer p_8248_) {
   }
}