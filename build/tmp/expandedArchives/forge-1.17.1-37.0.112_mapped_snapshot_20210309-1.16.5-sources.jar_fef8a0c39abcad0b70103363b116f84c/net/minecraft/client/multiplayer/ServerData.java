package net.minecraft.client.multiplayer;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerData {
   public String f_105362_;
   public String f_105363_;
   public Component f_105364_;
   public Component f_105365_;
   public long f_105366_;
   public int f_105367_ = SharedConstants.m_136187_().getProtocolVersion();
   public Component f_105368_ = new TextComponent(SharedConstants.m_136187_().getName());
   public boolean f_105369_;
   public List<Component> f_105370_ = Collections.emptyList();
   private ServerData.ServerPackStatus f_105371_ = ServerData.ServerPackStatus.PROMPT;
   @Nullable
   private String f_105372_;
   private boolean f_105373_;
   public net.minecraftforge.fmlclient.ExtendedServerListData forgeData = null;

   public ServerData(String p_105375_, String p_105376_, boolean p_105377_) {
      this.f_105362_ = p_105375_;
      this.f_105363_ = p_105376_;
      this.f_105373_ = p_105377_;
   }

   public CompoundTag m_105378_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("name", this.f_105362_);
      compoundtag.m_128359_("ip", this.f_105363_);
      if (this.f_105372_ != null) {
         compoundtag.m_128359_("icon", this.f_105372_);
      }

      if (this.f_105371_ == ServerData.ServerPackStatus.ENABLED) {
         compoundtag.m_128379_("acceptTextures", true);
      } else if (this.f_105371_ == ServerData.ServerPackStatus.DISABLED) {
         compoundtag.m_128379_("acceptTextures", false);
      }

      return compoundtag;
   }

   public ServerData.ServerPackStatus m_105387_() {
      return this.f_105371_;
   }

   public void m_105379_(ServerData.ServerPackStatus p_105380_) {
      this.f_105371_ = p_105380_;
   }

   public static ServerData m_105385_(CompoundTag p_105386_) {
      ServerData serverdata = new ServerData(p_105386_.m_128461_("name"), p_105386_.m_128461_("ip"), false);
      if (p_105386_.m_128425_("icon", 8)) {
         serverdata.m_105383_(p_105386_.m_128461_("icon"));
      }

      if (p_105386_.m_128425_("acceptTextures", 1)) {
         if (p_105386_.m_128471_("acceptTextures")) {
            serverdata.m_105379_(ServerData.ServerPackStatus.ENABLED);
         } else {
            serverdata.m_105379_(ServerData.ServerPackStatus.DISABLED);
         }
      } else {
         serverdata.m_105379_(ServerData.ServerPackStatus.PROMPT);
      }

      return serverdata;
   }

   @Nullable
   public String m_105388_() {
      return this.f_105372_;
   }

   public void m_105383_(@Nullable String p_105384_) {
      this.f_105372_ = p_105384_;
   }

   public boolean m_105389_() {
      return this.f_105373_;
   }

   public void m_105381_(ServerData p_105382_) {
      this.f_105363_ = p_105382_.f_105363_;
      this.f_105362_ = p_105382_.f_105362_;
      this.m_105379_(p_105382_.m_105387_());
      this.f_105372_ = p_105382_.f_105372_;
      this.f_105373_ = p_105382_.f_105373_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum ServerPackStatus {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final Component f_105393_;

      private ServerPackStatus(String p_105399_) {
         this.f_105393_ = new TranslatableComponent("addServer.resourcePack." + p_105399_);
      }

      public Component m_105400_() {
         return this.f_105393_;
      }
   }
}
