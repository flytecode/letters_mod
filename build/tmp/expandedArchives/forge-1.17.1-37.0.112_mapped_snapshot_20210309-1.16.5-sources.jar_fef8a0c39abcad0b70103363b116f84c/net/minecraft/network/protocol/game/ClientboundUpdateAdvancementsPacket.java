package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

public class ClientboundUpdateAdvancementsPacket implements Packet<ClientGamePacketListener> {
   private final boolean f_133554_;
   private final Map<ResourceLocation, Advancement.Builder> f_133555_;
   private final Set<ResourceLocation> f_133556_;
   private final Map<ResourceLocation, AdvancementProgress> f_133557_;

   public ClientboundUpdateAdvancementsPacket(boolean p_133560_, Collection<Advancement> p_133561_, Set<ResourceLocation> p_133562_, Map<ResourceLocation, AdvancementProgress> p_133563_) {
      this.f_133554_ = p_133560_;
      Builder<ResourceLocation, Advancement.Builder> builder = ImmutableMap.builder();

      for(Advancement advancement : p_133561_) {
         builder.put(advancement.m_138327_(), advancement.m_138313_());
      }

      this.f_133555_ = builder.build();
      this.f_133556_ = ImmutableSet.copyOf(p_133562_);
      this.f_133557_ = ImmutableMap.copyOf(p_133563_);
   }

   public ClientboundUpdateAdvancementsPacket(FriendlyByteBuf p_179439_) {
      this.f_133554_ = p_179439_.readBoolean();
      this.f_133555_ = p_179439_.m_178368_(FriendlyByteBuf::m_130281_, Advancement.Builder::m_138401_);
      this.f_133556_ = p_179439_.m_178371_(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::m_130281_);
      this.f_133557_ = p_179439_.m_178368_(FriendlyByteBuf::m_130281_, AdvancementProgress::m_8211_);
   }

   public void m_5779_(FriendlyByteBuf p_133572_) {
      p_133572_.writeBoolean(this.f_133554_);
      p_133572_.m_178355_(this.f_133555_, FriendlyByteBuf::m_130085_, (p_179441_, p_179442_) -> {
         p_179442_.m_138394_(p_179441_);
      });
      p_133572_.m_178352_(this.f_133556_, FriendlyByteBuf::m_130085_);
      p_133572_.m_178355_(this.f_133557_, FriendlyByteBuf::m_130085_, (p_179444_, p_179445_) -> {
         p_179445_.m_8204_(p_179444_);
      });
   }

   public void m_5797_(ClientGamePacketListener p_133569_) {
      p_133569_.m_5498_(this);
   }

   public Map<ResourceLocation, Advancement.Builder> m_133570_() {
      return this.f_133555_;
   }

   public Set<ResourceLocation> m_133573_() {
      return this.f_133556_;
   }

   public Map<ResourceLocation, AdvancementProgress> m_133574_() {
      return this.f_133557_;
   }

   public boolean m_133575_() {
      return this.f_133554_;
   }
}