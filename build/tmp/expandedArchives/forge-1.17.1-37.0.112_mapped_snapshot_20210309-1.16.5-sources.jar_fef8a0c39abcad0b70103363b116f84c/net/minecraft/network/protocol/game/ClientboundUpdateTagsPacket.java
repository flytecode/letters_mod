package net.minecraft.network.protocol.game;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagCollection;

public class ClientboundUpdateTagsPacket implements Packet<ClientGamePacketListener> {
   private final Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> f_133649_;

   public ClientboundUpdateTagsPacket(Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> p_179473_) {
      this.f_133649_ = p_179473_;
   }

   public ClientboundUpdateTagsPacket(FriendlyByteBuf p_179475_) {
      this.f_133649_ = p_179475_.m_178368_((p_179484_) -> {
         return ResourceKey.m_135788_(p_179484_.m_130281_());
      }, TagCollection.NetworkPayload::m_144430_);
   }

   public void m_5779_(FriendlyByteBuf p_133661_) {
      p_133661_.m_178355_(this.f_133649_, (p_179480_, p_179481_) -> {
         p_179480_.m_130085_(p_179481_.m_135782_());
      }, (p_179477_, p_179478_) -> {
         p_179478_.m_144428_(p_179477_);
      });
   }

   public void m_5797_(ClientGamePacketListener p_133658_) {
      p_133658_.m_5859_(this);
   }

   public Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> m_179482_() {
      return this.f_133649_;
   }
}