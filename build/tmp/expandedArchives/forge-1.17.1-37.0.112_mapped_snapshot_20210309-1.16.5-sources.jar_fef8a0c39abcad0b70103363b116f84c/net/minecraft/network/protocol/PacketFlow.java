package net.minecraft.network.protocol;

public enum PacketFlow {
   SERVERBOUND,
   CLIENTBOUND;

   public PacketFlow m_178539_() {
      return this == CLIENTBOUND ? SERVERBOUND : CLIENTBOUND;
   }
}