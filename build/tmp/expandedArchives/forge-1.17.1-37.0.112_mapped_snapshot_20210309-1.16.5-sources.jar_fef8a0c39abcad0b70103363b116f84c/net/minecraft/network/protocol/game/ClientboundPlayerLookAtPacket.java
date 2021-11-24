package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClientboundPlayerLookAtPacket implements Packet<ClientGamePacketListener> {
   private final double f_132768_;
   private final double f_132769_;
   private final double f_132770_;
   private final int f_132771_;
   private final EntityAnchorArgument.Anchor f_132772_;
   private final EntityAnchorArgument.Anchor f_132773_;
   private final boolean f_132774_;

   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor p_132777_, double p_132778_, double p_132779_, double p_132780_) {
      this.f_132772_ = p_132777_;
      this.f_132768_ = p_132778_;
      this.f_132769_ = p_132779_;
      this.f_132770_ = p_132780_;
      this.f_132771_ = 0;
      this.f_132774_ = false;
      this.f_132773_ = null;
   }

   public ClientboundPlayerLookAtPacket(EntityAnchorArgument.Anchor p_132782_, Entity p_132783_, EntityAnchorArgument.Anchor p_132784_) {
      this.f_132772_ = p_132782_;
      this.f_132771_ = p_132783_.m_142049_();
      this.f_132773_ = p_132784_;
      Vec3 vec3 = p_132784_.m_90377_(p_132783_);
      this.f_132768_ = vec3.f_82479_;
      this.f_132769_ = vec3.f_82480_;
      this.f_132770_ = vec3.f_82481_;
      this.f_132774_ = true;
   }

   public ClientboundPlayerLookAtPacket(FriendlyByteBuf p_179146_) {
      this.f_132772_ = p_179146_.m_130066_(EntityAnchorArgument.Anchor.class);
      this.f_132768_ = p_179146_.readDouble();
      this.f_132769_ = p_179146_.readDouble();
      this.f_132770_ = p_179146_.readDouble();
      this.f_132774_ = p_179146_.readBoolean();
      if (this.f_132774_) {
         this.f_132771_ = p_179146_.m_130242_();
         this.f_132773_ = p_179146_.m_130066_(EntityAnchorArgument.Anchor.class);
      } else {
         this.f_132771_ = 0;
         this.f_132773_ = null;
      }

   }

   public void m_5779_(FriendlyByteBuf p_132795_) {
      p_132795_.m_130068_(this.f_132772_);
      p_132795_.writeDouble(this.f_132768_);
      p_132795_.writeDouble(this.f_132769_);
      p_132795_.writeDouble(this.f_132770_);
      p_132795_.writeBoolean(this.f_132774_);
      if (this.f_132774_) {
         p_132795_.m_130130_(this.f_132771_);
         p_132795_.m_130068_(this.f_132773_);
      }

   }

   public void m_5797_(ClientGamePacketListener p_132792_) {
      p_132792_.m_7244_(this);
   }

   public EntityAnchorArgument.Anchor m_132793_() {
      return this.f_132772_;
   }

   @Nullable
   public Vec3 m_132785_(Level p_132786_) {
      if (this.f_132774_) {
         Entity entity = p_132786_.m_6815_(this.f_132771_);
         return entity == null ? new Vec3(this.f_132768_, this.f_132769_, this.f_132770_) : this.f_132773_.m_90377_(entity);
      } else {
         return new Vec3(this.f_132768_, this.f_132769_, this.f_132770_);
      }
   }
}