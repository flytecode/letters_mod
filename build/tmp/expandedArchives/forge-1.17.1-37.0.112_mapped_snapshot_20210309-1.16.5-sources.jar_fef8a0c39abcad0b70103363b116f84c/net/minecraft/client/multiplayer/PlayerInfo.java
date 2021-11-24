package net.minecraft.client.multiplayer;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerInfo {
   private final GameProfile f_105298_;
   private final Map<Type, ResourceLocation> f_105299_ = Maps.newEnumMap(Type.class);
   private GameType f_105300_;
   private int f_105301_;
   private boolean f_105302_;
   @Nullable
   private String f_105303_;
   @Nullable
   private Component f_105304_;
   private int f_105305_;
   private int f_105306_;
   private long f_105307_;
   private long f_105308_;
   private long f_105309_;

   public PlayerInfo(ClientboundPlayerInfoPacket.PlayerUpdate p_105311_) {
      this.f_105298_ = p_105311_.m_132763_();
      this.f_105300_ = p_105311_.m_132765_();
      this.f_105301_ = p_105311_.m_132764_();
      this.f_105304_ = p_105311_.m_132766_();
   }

   public GameProfile m_105312_() {
      return this.f_105298_;
   }

   @Nullable
   public GameType m_105325_() {
      return this.f_105300_;
   }

   protected void m_105317_(GameType p_105318_) {
      net.minecraftforge.client.ForgeHooksClient.onClientChangeGameMode(this, this.f_105300_, p_105318_);
      this.f_105300_ = p_105318_;
   }

   public int m_105330_() {
      return this.f_105301_;
   }

   protected void m_105313_(int p_105314_) {
      this.f_105301_ = p_105314_;
   }

   public boolean m_171808_() {
      return this.m_105338_() != null;
   }

   public boolean m_105335_() {
      return this.m_105337_() != null;
   }

   public String m_105336_() {
      return this.f_105303_ == null ? DefaultPlayerSkin.m_118629_(this.f_105298_.getId()) : this.f_105303_;
   }

   public ResourceLocation m_105337_() {
      this.m_105341_();
      return MoreObjects.firstNonNull(this.f_105299_.get(Type.SKIN), DefaultPlayerSkin.m_118627_(this.f_105298_.getId()));
   }

   @Nullable
   public ResourceLocation m_105338_() {
      this.m_105341_();
      return this.f_105299_.get(Type.CAPE);
   }

   @Nullable
   public ResourceLocation m_105339_() {
      this.m_105341_();
      return this.f_105299_.get(Type.ELYTRA);
   }

   @Nullable
   public PlayerTeam m_105340_() {
      return Minecraft.m_91087_().f_91073_.m_6188_().m_83500_(this.m_105312_().getName());
   }

   protected void m_105341_() {
      synchronized(this) {
         if (!this.f_105302_) {
            this.f_105302_ = true;
            Minecraft.m_91087_().m_91109_().m_118817_(this.f_105298_, (p_105320_, p_105321_, p_105322_) -> {
               this.f_105299_.put(p_105320_, p_105321_);
               if (p_105320_ == Type.SKIN) {
                  this.f_105303_ = p_105322_.getMetadata("model");
                  if (this.f_105303_ == null) {
                     this.f_105303_ = "default";
                  }
               }

            }, true);
         }

      }
   }

   public void m_105323_(@Nullable Component p_105324_) {
      this.f_105304_ = p_105324_;
   }

   @Nullable
   public Component m_105342_() {
      return this.f_105304_;
   }

   public int m_105343_() {
      return this.f_105305_;
   }

   public void m_105326_(int p_105327_) {
      this.f_105305_ = p_105327_;
   }

   public int m_105344_() {
      return this.f_105306_;
   }

   public void m_105331_(int p_105332_) {
      this.f_105306_ = p_105332_;
   }

   public long m_105345_() {
      return this.f_105307_;
   }

   public void m_105315_(long p_105316_) {
      this.f_105307_ = p_105316_;
   }

   public long m_105346_() {
      return this.f_105308_;
   }

   public void m_105328_(long p_105329_) {
      this.f_105308_ = p_105329_;
   }

   public long m_105347_() {
      return this.f_105309_;
   }

   public void m_105333_(long p_105334_) {
      this.f_105309_ = p_105334_;
   }
}
