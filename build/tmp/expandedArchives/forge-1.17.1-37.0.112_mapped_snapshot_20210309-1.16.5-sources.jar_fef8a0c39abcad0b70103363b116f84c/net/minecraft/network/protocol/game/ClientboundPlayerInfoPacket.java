package net.minecraft.network.protocol.game;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class ClientboundPlayerInfoPacket implements Packet<ClientGamePacketListener> {
   private final ClientboundPlayerInfoPacket.Action f_132717_;
   private final List<ClientboundPlayerInfoPacket.PlayerUpdate> f_132718_;

   public ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action p_132724_, ServerPlayer... p_132725_) {
      this.f_132717_ = p_132724_;
      this.f_132718_ = Lists.newArrayListWithCapacity(p_132725_.length);

      for(ServerPlayer serverplayer : p_132725_) {
         this.f_132718_.add(new ClientboundPlayerInfoPacket.PlayerUpdate(serverplayer.m_36316_(), serverplayer.f_8943_, serverplayer.f_8941_.m_9290_(), serverplayer.m_8957_()));
      }

   }

   public ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action p_179083_, Collection<ServerPlayer> p_179084_) {
      this.f_132717_ = p_179083_;
      this.f_132718_ = Lists.newArrayListWithCapacity(p_179084_.size());

      for(ServerPlayer serverplayer : p_179084_) {
         this.f_132718_.add(new ClientboundPlayerInfoPacket.PlayerUpdate(serverplayer.m_36316_(), serverplayer.f_8943_, serverplayer.f_8941_.m_9290_(), serverplayer.m_8957_()));
      }

   }

   public ClientboundPlayerInfoPacket(FriendlyByteBuf p_179081_) {
      this.f_132717_ = p_179081_.m_130066_(ClientboundPlayerInfoPacket.Action.class);
      this.f_132718_ = p_179081_.m_178366_(this.f_132717_::m_142553_);
   }

   public void m_5779_(FriendlyByteBuf p_132734_) {
      p_132734_.m_130068_(this.f_132717_);
      p_132734_.m_178352_(this.f_132718_, this.f_132717_::m_142214_);
   }

   public void m_5797_(ClientGamePacketListener p_132731_) {
      p_132731_.m_7039_(this);
   }

   public List<ClientboundPlayerInfoPacket.PlayerUpdate> m_132732_() {
      return this.f_132718_;
   }

   public ClientboundPlayerInfoPacket.Action m_132735_() {
      return this.f_132717_;
   }

   @Nullable
   static Component m_179088_(FriendlyByteBuf p_179089_) {
      return p_179089_.readBoolean() ? p_179089_.m_130238_() : null;
   }

   static void m_179085_(FriendlyByteBuf p_179086_, @Nullable Component p_179087_) {
      if (p_179087_ == null) {
         p_179086_.writeBoolean(false);
      } else {
         p_179086_.writeBoolean(true);
         p_179086_.m_130083_(p_179087_);
      }

   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.f_132717_).add("entries", this.f_132718_).toString();
   }

   public static enum Action {
      ADD_PLAYER {
         protected ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179101_) {
            GameProfile gameprofile = new GameProfile(p_179101_.m_130259_(), p_179101_.m_130136_(16));
            PropertyMap propertymap = gameprofile.getProperties();
            p_179101_.m_178364_((p_179099_) -> {
               String s = p_179099_.m_130277_();
               String s1 = p_179099_.m_130277_();
               if (p_179099_.readBoolean()) {
                  String s2 = p_179099_.m_130277_();
                  propertymap.put(s, new Property(s, s1, s2));
               } else {
                  propertymap.put(s, new Property(s, s1));
               }

            });
            GameType gametype = GameType.m_46393_(p_179101_.m_130242_());
            int i = p_179101_.m_130242_();
            Component component = ClientboundPlayerInfoPacket.m_179088_(p_179101_);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(gameprofile, i, gametype, component);
         }

         protected void m_142214_(FriendlyByteBuf p_179106_, ClientboundPlayerInfoPacket.PlayerUpdate p_179107_) {
            p_179106_.m_130077_(p_179107_.m_132763_().getId());
            p_179106_.m_130070_(p_179107_.m_132763_().getName());
            p_179106_.m_178352_(p_179107_.m_132763_().getProperties().values(), (p_179103_, p_179104_) -> {
               p_179103_.m_130070_(p_179104_.getName());
               p_179103_.m_130070_(p_179104_.getValue());
               if (p_179104_.hasSignature()) {
                  p_179103_.writeBoolean(true);
                  p_179103_.m_130070_(p_179104_.getSignature());
               } else {
                  p_179103_.writeBoolean(false);
               }

            });
            p_179106_.m_130130_(p_179107_.m_132765_().m_46392_());
            p_179106_.m_130130_(p_179107_.m_132764_());
            ClientboundPlayerInfoPacket.m_179085_(p_179106_, p_179107_.m_132766_());
         }
      },
      UPDATE_GAME_MODE {
         protected ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179112_) {
            GameProfile gameprofile = new GameProfile(p_179112_.m_130259_(), (String)null);
            GameType gametype = GameType.m_46393_(p_179112_.m_130242_());
            return new ClientboundPlayerInfoPacket.PlayerUpdate(gameprofile, 0, gametype, (Component)null);
         }

         protected void m_142214_(FriendlyByteBuf p_179114_, ClientboundPlayerInfoPacket.PlayerUpdate p_179115_) {
            p_179114_.m_130077_(p_179115_.m_132763_().getId());
            p_179114_.m_130130_(p_179115_.m_132765_().m_46392_());
         }
      },
      UPDATE_LATENCY {
         protected ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179120_) {
            GameProfile gameprofile = new GameProfile(p_179120_.m_130259_(), (String)null);
            int i = p_179120_.m_130242_();
            return new ClientboundPlayerInfoPacket.PlayerUpdate(gameprofile, i, (GameType)null, (Component)null);
         }

         protected void m_142214_(FriendlyByteBuf p_179122_, ClientboundPlayerInfoPacket.PlayerUpdate p_179123_) {
            p_179122_.m_130077_(p_179123_.m_132763_().getId());
            p_179122_.m_130130_(p_179123_.m_132764_());
         }
      },
      UPDATE_DISPLAY_NAME {
         protected ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179128_) {
            GameProfile gameprofile = new GameProfile(p_179128_.m_130259_(), (String)null);
            Component component = ClientboundPlayerInfoPacket.m_179088_(p_179128_);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(gameprofile, 0, (GameType)null, component);
         }

         protected void m_142214_(FriendlyByteBuf p_179130_, ClientboundPlayerInfoPacket.PlayerUpdate p_179131_) {
            p_179130_.m_130077_(p_179131_.m_132763_().getId());
            ClientboundPlayerInfoPacket.m_179085_(p_179130_, p_179131_.m_132766_());
         }
      },
      REMOVE_PLAYER {
         protected ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179136_) {
            GameProfile gameprofile = new GameProfile(p_179136_.m_130259_(), (String)null);
            return new ClientboundPlayerInfoPacket.PlayerUpdate(gameprofile, 0, (GameType)null, (Component)null);
         }

         protected void m_142214_(FriendlyByteBuf p_179138_, ClientboundPlayerInfoPacket.PlayerUpdate p_179139_) {
            p_179138_.m_130077_(p_179139_.m_132763_().getId());
         }
      };

      protected abstract ClientboundPlayerInfoPacket.PlayerUpdate m_142553_(FriendlyByteBuf p_179091_);

      protected abstract void m_142214_(FriendlyByteBuf p_179092_, ClientboundPlayerInfoPacket.PlayerUpdate p_179093_);
   }

   public static class PlayerUpdate {
      private final int f_132753_;
      private final GameType f_132754_;
      private final GameProfile f_132755_;
      @Nullable
      private final Component f_132756_;

      public PlayerUpdate(GameProfile p_179141_, int p_179142_, @Nullable GameType p_179143_, @Nullable Component p_179144_) {
         this.f_132755_ = p_179141_;
         this.f_132753_ = p_179142_;
         this.f_132754_ = p_179143_;
         this.f_132756_ = p_179144_;
      }

      public GameProfile m_132763_() {
         return this.f_132755_;
      }

      public int m_132764_() {
         return this.f_132753_;
      }

      public GameType m_132765_() {
         return this.f_132754_;
      }

      @Nullable
      public Component m_132766_() {
         return this.f_132756_;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("latency", this.f_132753_).add("gameMode", this.f_132754_).add("profile", this.f_132755_).add("displayName", this.f_132756_ == null ? null : Component.Serializer.m_130703_(this.f_132756_)).toString();
      }
   }
}