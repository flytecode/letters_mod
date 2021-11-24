package net.minecraft.client.player;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractClientPlayer extends Player {
   private static final String f_172517_ = "http://skins.minecraft.net/MinecraftSkins/%s.png";
   public static final int f_172510_ = 8;
   public static final int f_172511_ = 8;
   public static final int f_172518_ = 8;
   public static final int f_172519_ = 8;
   public static final int f_172520_ = 40;
   public static final int f_172512_ = 8;
   public static final int f_172513_ = 8;
   public static final int f_172514_ = 8;
   public static final int f_172515_ = 64;
   public static final int f_172516_ = 64;
   private PlayerInfo f_108546_;
   public float f_108542_;
   public float f_108543_;
   public float f_108544_;
   public final ClientLevel f_108545_;

   public AbstractClientPlayer(ClientLevel p_108548_, GameProfile p_108549_) {
      super(p_108548_, p_108548_.m_104822_(), p_108548_.m_104823_(), p_108549_);
      this.f_108545_ = p_108548_;
   }

   public boolean m_5833_() {
      PlayerInfo playerinfo = Minecraft.m_91087_().m_91403_().m_104949_(this.m_36316_().getId());
      return playerinfo != null && playerinfo.m_105325_() == GameType.SPECTATOR;
   }

   public boolean m_7500_() {
      PlayerInfo playerinfo = Minecraft.m_91087_().m_91403_().m_104949_(this.m_36316_().getId());
      return playerinfo != null && playerinfo.m_105325_() == GameType.CREATIVE;
   }

   public boolean m_108555_() {
      return this.m_108558_() != null;
   }

   @Nullable
   protected PlayerInfo m_108558_() {
      if (this.f_108546_ == null) {
         this.f_108546_ = Minecraft.m_91087_().m_91403_().m_104949_(this.m_142081_());
      }

      return this.f_108546_;
   }

   public boolean m_108559_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo != null && playerinfo.m_105335_();
   }

   public ResourceLocation m_108560_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? DefaultPlayerSkin.m_118627_(this.m_142081_()) : playerinfo.m_105337_();
   }

   @Nullable
   public ResourceLocation m_108561_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? null : playerinfo.m_105338_();
   }

   public boolean m_108562_() {
      return this.m_108558_() != null;
   }

   @Nullable
   public ResourceLocation m_108563_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? null : playerinfo.m_105339_();
   }

   public static void m_172521_(ResourceLocation p_172522_, String p_172523_) {
      TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
      AbstractTexture abstracttexture = texturemanager.m_174786_(p_172522_, MissingTextureAtlasSprite.m_118080_());
      if (abstracttexture == MissingTextureAtlasSprite.m_118080_()) {
         AbstractTexture httptexture = new HttpTexture((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtil.m_14406_(p_172523_)), DefaultPlayerSkin.m_118627_(m_36283_(p_172523_)), true, (Runnable)null);
         texturemanager.m_118495_(p_172522_, httptexture);
      }

   }

   public static ResourceLocation m_108556_(String p_108557_) {
      return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(StringUtil.m_14406_(p_108557_)));
   }

   public String m_108564_() {
      PlayerInfo playerinfo = this.m_108558_();
      return playerinfo == null ? DefaultPlayerSkin.m_118629_(this.m_142081_()) : playerinfo.m_105336_();
   }

   public float m_108565_() {
      float f = 1.0F;
      if (this.m_150110_().f_35935_) {
         f *= 1.1F;
      }

      f = (float)((double)f * ((this.m_21133_(Attributes.f_22279_) / (double)this.m_150110_().m_35947_() + 1.0D) / 2.0D));
      if (this.m_150110_().m_35947_() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
         f = 1.0F;
      }

      ItemStack itemstack = this.m_21211_();
      if (this.m_6117_()) {
         if (itemstack.m_150930_(Items.f_42411_)) {
            int i = this.m_21252_();
            float f1 = (float)i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 = f1 * f1;
            }

            f *= 1.0F - f1 * 0.15F;
         } else if (Minecraft.m_91087_().f_91066_.m_92176_().m_90612_() && this.m_150108_()) {
            return 0.1F;
         }
      }

      return net.minecraftforge.client.ForgeHooksClient.getOffsetFOV(this, f);
   }
}
