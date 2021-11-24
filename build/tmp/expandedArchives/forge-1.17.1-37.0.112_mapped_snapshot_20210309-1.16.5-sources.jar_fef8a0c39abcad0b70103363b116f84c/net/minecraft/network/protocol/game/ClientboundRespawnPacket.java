package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientboundRespawnPacket implements Packet<ClientGamePacketListener> {
   private final DimensionType f_132928_;
   private final ResourceKey<Level> f_132929_;
   private final long f_132930_;
   private final GameType f_132931_;
   @Nullable
   private final GameType f_132932_;
   private final boolean f_132933_;
   private final boolean f_132934_;
   private final boolean f_132935_;

   public ClientboundRespawnPacket(DimensionType p_132938_, ResourceKey<Level> p_132939_, long p_132940_, GameType p_132941_, @Nullable GameType p_132942_, boolean p_132943_, boolean p_132944_, boolean p_132945_) {
      this.f_132928_ = p_132938_;
      this.f_132929_ = p_132939_;
      this.f_132930_ = p_132940_;
      this.f_132931_ = p_132941_;
      this.f_132932_ = p_132942_;
      this.f_132933_ = p_132943_;
      this.f_132934_ = p_132944_;
      this.f_132935_ = p_132945_;
   }

   public ClientboundRespawnPacket(FriendlyByteBuf p_179191_) {
      this.f_132928_ = p_179191_.m_130057_(DimensionType.f_63853_).get();
      this.f_132929_ = ResourceKey.m_135785_(Registry.f_122819_, p_179191_.m_130281_());
      this.f_132930_ = p_179191_.readLong();
      this.f_132931_ = GameType.m_46393_(p_179191_.readUnsignedByte());
      this.f_132932_ = GameType.m_151497_(p_179191_.readByte());
      this.f_132933_ = p_179191_.readBoolean();
      this.f_132934_ = p_179191_.readBoolean();
      this.f_132935_ = p_179191_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_132954_) {
      p_132954_.m_130059_(DimensionType.f_63853_, () -> {
         return this.f_132928_;
      });
      p_132954_.m_130085_(this.f_132929_.m_135782_());
      p_132954_.writeLong(this.f_132930_);
      p_132954_.writeByte(this.f_132931_.m_46392_());
      p_132954_.writeByte(GameType.m_151495_(this.f_132932_));
      p_132954_.writeBoolean(this.f_132933_);
      p_132954_.writeBoolean(this.f_132934_);
      p_132954_.writeBoolean(this.f_132935_);
   }

   public void m_5797_(ClientGamePacketListener p_132951_) {
      p_132951_.m_7992_(this);
   }

   public DimensionType m_132952_() {
      return this.f_132928_;
   }

   public ResourceKey<Level> m_132955_() {
      return this.f_132929_;
   }

   public long m_132956_() {
      return this.f_132930_;
   }

   public GameType m_132957_() {
      return this.f_132931_;
   }

   @Nullable
   public GameType m_132958_() {
      return this.f_132932_;
   }

   public boolean m_132959_() {
      return this.f_132933_;
   }

   public boolean m_132960_() {
      return this.f_132934_;
   }

   public boolean m_132961_() {
      return this.f_132935_;
   }
}