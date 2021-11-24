package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ClientboundUpdateAttributesPacket implements Packet<ClientGamePacketListener> {
   private final int f_133576_;
   private final List<ClientboundUpdateAttributesPacket.AttributeSnapshot> f_133577_;

   public ClientboundUpdateAttributesPacket(int p_133580_, Collection<AttributeInstance> p_133581_) {
      this.f_133576_ = p_133580_;
      this.f_133577_ = Lists.newArrayList();

      for(AttributeInstance attributeinstance : p_133581_) {
         this.f_133577_.add(new ClientboundUpdateAttributesPacket.AttributeSnapshot(attributeinstance.m_22099_(), attributeinstance.m_22115_(), attributeinstance.m_22122_()));
      }

   }

   public ClientboundUpdateAttributesPacket(FriendlyByteBuf p_179447_) {
      this.f_133576_ = p_179447_.m_130242_();
      this.f_133577_ = p_179447_.m_178366_((p_179455_) -> {
         ResourceLocation resourcelocation = p_179455_.m_130281_();
         Attribute attribute = Registry.f_122866_.m_7745_(resourcelocation);
         double d0 = p_179455_.readDouble();
         List<AttributeModifier> list = p_179455_.m_178366_((p_179457_) -> {
            return new AttributeModifier(p_179457_.m_130259_(), "Unknown synced attribute modifier", p_179457_.readDouble(), AttributeModifier.Operation.m_22236_(p_179457_.readByte()));
         });
         return new ClientboundUpdateAttributesPacket.AttributeSnapshot(attribute, d0, list);
      });
   }

   public void m_5779_(FriendlyByteBuf p_133590_) {
      p_133590_.m_130130_(this.f_133576_);
      p_133590_.m_178352_(this.f_133577_, (p_179452_, p_179453_) -> {
         p_179452_.m_130085_(Registry.f_122866_.m_7981_(p_179453_.m_133601_()));
         p_179452_.writeDouble(p_179453_.m_133602_());
         p_179452_.m_178352_(p_179453_.m_133603_(), (p_179449_, p_179450_) -> {
            p_179449_.m_130077_(p_179450_.m_22209_());
            p_179449_.writeDouble(p_179450_.m_22218_());
            p_179449_.writeByte(p_179450_.m_22217_().m_22235_());
         });
      });
   }

   public void m_5797_(ClientGamePacketListener p_133587_) {
      p_133587_.m_7710_(this);
   }

   public int m_133588_() {
      return this.f_133576_;
   }

   public List<ClientboundUpdateAttributesPacket.AttributeSnapshot> m_133591_() {
      return this.f_133577_;
   }

   public static class AttributeSnapshot {
      private final Attribute f_133593_;
      private final double f_133594_;
      private final Collection<AttributeModifier> f_133595_;

      public AttributeSnapshot(Attribute p_179459_, double p_179460_, Collection<AttributeModifier> p_179461_) {
         this.f_133593_ = p_179459_;
         this.f_133594_ = p_179460_;
         this.f_133595_ = p_179461_;
      }

      public Attribute m_133601_() {
         return this.f_133593_;
      }

      public double m_133602_() {
         return this.f_133594_;
      }

      public Collection<AttributeModifier> m_133603_() {
         return this.f_133595_;
      }
   }
}