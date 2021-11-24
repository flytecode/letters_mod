package net.minecraft.client.gui.screens.social;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerSocialManager {
   private final Minecraft f_100668_;
   private final Set<UUID> f_100669_ = Sets.newHashSet();
   private final SocialInteractionsService f_100670_;
   private final Map<String, UUID> f_100671_ = Maps.newHashMap();

   public PlayerSocialManager(Minecraft p_100673_, SocialInteractionsService p_100674_) {
      this.f_100668_ = p_100673_;
      this.f_100670_ = p_100674_;
   }

   public void m_100680_(UUID p_100681_) {
      this.f_100669_.add(p_100681_);
   }

   public void m_100682_(UUID p_100683_) {
      this.f_100669_.remove(p_100683_);
   }

   public boolean m_100684_(UUID p_100685_) {
      return this.m_100686_(p_100685_) || this.m_100688_(p_100685_);
   }

   public boolean m_100686_(UUID p_100687_) {
      return this.f_100669_.contains(p_100687_);
   }

   public boolean m_100688_(UUID p_100689_) {
      return this.f_100670_.isBlockedPlayer(p_100689_);
   }

   public Set<UUID> m_100675_() {
      return this.f_100669_;
   }

   public UUID m_100678_(String p_100679_) {
      return this.f_100671_.getOrDefault(p_100679_, Util.f_137441_);
   }

   public void m_100676_(PlayerInfo p_100677_) {
      GameProfile gameprofile = p_100677_.m_105312_();
      if (gameprofile.isComplete()) {
         this.f_100671_.put(gameprofile.getName(), gameprofile.getId());
      }

      Screen screen = this.f_100668_.f_91080_;
      if (screen instanceof SocialInteractionsScreen) {
         SocialInteractionsScreen socialinteractionsscreen = (SocialInteractionsScreen)screen;
         socialinteractionsscreen.m_100775_(p_100677_);
      }

   }

   public void m_100690_(UUID p_100691_) {
      Screen screen = this.f_100668_.f_91080_;
      if (screen instanceof SocialInteractionsScreen) {
         SocialInteractionsScreen socialinteractionsscreen = (SocialInteractionsScreen)screen;
         socialinteractionsscreen.m_100779_(p_100691_);
      }

   }
}