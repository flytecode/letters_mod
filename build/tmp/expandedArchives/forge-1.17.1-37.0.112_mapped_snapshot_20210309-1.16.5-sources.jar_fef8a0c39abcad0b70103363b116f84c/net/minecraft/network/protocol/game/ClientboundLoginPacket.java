package net.minecraft.network.protocol.game;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientboundLoginPacket implements Packet<ClientGamePacketListener> {
   private static final int f_178958_ = 8;
   private final int f_132360_;
   private final long f_132361_;
   private final boolean f_132362_;
   private final GameType f_132363_;
   @Nullable
   private final GameType f_132364_;
   private final Set<ResourceKey<Level>> f_132365_;
   private final RegistryAccess.RegistryHolder f_132366_;
   private final DimensionType f_132367_;
   private final ResourceKey<Level> f_132368_;
   private final int f_132369_;
   private final int f_132370_;
   private final boolean f_132371_;
   private final boolean f_132372_;
   private final boolean f_132373_;
   private final boolean f_132374_;

   public ClientboundLoginPacket(int p_132377_, GameType p_132378_, @Nullable GameType p_132379_, long p_132380_, boolean p_132381_, Set<ResourceKey<Level>> p_132382_, RegistryAccess.RegistryHolder p_132383_, DimensionType p_132384_, ResourceKey<Level> p_132385_, int p_132386_, int p_132387_, boolean p_132388_, boolean p_132389_, boolean p_132390_, boolean p_132391_) {
      this.f_132360_ = p_132377_;
      this.f_132365_ = p_132382_;
      this.f_132366_ = p_132383_;
      this.f_132367_ = p_132384_;
      this.f_132368_ = p_132385_;
      this.f_132361_ = p_132380_;
      this.f_132363_ = p_132378_;
      this.f_132364_ = p_132379_;
      this.f_132369_ = p_132386_;
      this.f_132362_ = p_132381_;
      this.f_132370_ = p_132387_;
      this.f_132371_ = p_132388_;
      this.f_132372_ = p_132389_;
      this.f_132373_ = p_132390_;
      this.f_132374_ = p_132391_;
   }

   public ClientboundLoginPacket(FriendlyByteBuf p_178960_) {
      this.f_132360_ = p_178960_.readInt();
      this.f_132362_ = p_178960_.readBoolean();
      this.f_132363_ = GameType.m_46393_(p_178960_.readByte());
      this.f_132364_ = GameType.m_151497_(p_178960_.readByte());
      this.f_132365_ = p_178960_.m_178371_(Sets::newHashSetWithExpectedSize, (p_178965_) -> {
         return ResourceKey.m_135785_(Registry.f_122819_, p_178965_.m_130281_());
      });
      this.f_132366_ = p_178960_.m_130057_(RegistryAccess.RegistryHolder.f_123112_);
      this.f_132367_ = p_178960_.m_130057_(DimensionType.f_63853_).get();
      this.f_132368_ = ResourceKey.m_135785_(Registry.f_122819_, p_178960_.m_130281_());
      this.f_132361_ = p_178960_.readLong();
      this.f_132369_ = p_178960_.m_130242_();
      this.f_132370_ = p_178960_.m_130242_();
      this.f_132371_ = p_178960_.readBoolean();
      this.f_132372_ = p_178960_.readBoolean();
      this.f_132373_ = p_178960_.readBoolean();
      this.f_132374_ = p_178960_.readBoolean();
   }

   public void m_5779_(FriendlyByteBuf p_132400_) {
      p_132400_.writeInt(this.f_132360_);
      p_132400_.writeBoolean(this.f_132362_);
      p_132400_.writeByte(this.f_132363_.m_46392_());
      p_132400_.writeByte(GameType.m_151495_(this.f_132364_));
      p_132400_.m_178352_(this.f_132365_, (p_178962_, p_178963_) -> {
         p_178962_.m_130085_(p_178963_.m_135782_());
      });
      p_132400_.m_130059_(RegistryAccess.RegistryHolder.f_123112_, this.f_132366_);
      p_132400_.m_130059_(DimensionType.f_63853_, () -> {
         return this.f_132367_;
      });
      p_132400_.m_130085_(this.f_132368_.m_135782_());
      p_132400_.writeLong(this.f_132361_);
      p_132400_.m_130130_(this.f_132369_);
      p_132400_.m_130130_(this.f_132370_);
      p_132400_.writeBoolean(this.f_132371_);
      p_132400_.writeBoolean(this.f_132372_);
      p_132400_.writeBoolean(this.f_132373_);
      p_132400_.writeBoolean(this.f_132374_);
   }

   public void m_5797_(ClientGamePacketListener p_132397_) {
      p_132397_.m_5998_(this);
   }

   public int m_132398_() {
      return this.f_132360_;
   }

   public long m_132401_() {
      return this.f_132361_;
   }

   public boolean m_132402_() {
      return this.f_132362_;
   }

   public GameType m_132403_() {
      return this.f_132363_;
   }

   @Nullable
   public GameType m_132404_() {
      return this.f_132364_;
   }

   public Set<ResourceKey<Level>> m_132405_() {
      return this.f_132365_;
   }

   public RegistryAccess m_132406_() {
      return this.f_132366_;
   }

   public DimensionType m_132407_() {
      return this.f_132367_;
   }

   public ResourceKey<Level> m_132408_() {
      return this.f_132368_;
   }

   public int m_178966_() {
      return this.f_132369_;
   }

   public int m_132409_() {
      return this.f_132370_;
   }

   public boolean m_132410_() {
      return this.f_132371_;
   }

   public boolean m_132411_() {
      return this.f_132372_;
   }

   public boolean m_132412_() {
      return this.f_132373_;
   }

   public boolean m_132413_() {
      return this.f_132374_;
   }
}