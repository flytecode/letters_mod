package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldTemplate extends ValueObject {
   private static final Logger f_87735_ = LogManager.getLogger();
   public String f_87726_ = "";
   public String f_87727_ = "";
   public String f_87728_ = "";
   public String f_87729_ = "";
   public String f_87730_ = "";
   @Nullable
   public String f_87731_;
   public String f_87732_ = "";
   public String f_87733_ = "";
   public WorldTemplate.WorldTemplateType f_87734_ = WorldTemplate.WorldTemplateType.WORLD_TEMPLATE;

   public static WorldTemplate m_87738_(JsonObject p_87739_) {
      WorldTemplate worldtemplate = new WorldTemplate();

      try {
         worldtemplate.f_87726_ = JsonUtils.m_90161_("id", p_87739_, "");
         worldtemplate.f_87727_ = JsonUtils.m_90161_("name", p_87739_, "");
         worldtemplate.f_87728_ = JsonUtils.m_90161_("version", p_87739_, "");
         worldtemplate.f_87729_ = JsonUtils.m_90161_("author", p_87739_, "");
         worldtemplate.f_87730_ = JsonUtils.m_90161_("link", p_87739_, "");
         worldtemplate.f_87731_ = JsonUtils.m_90161_("image", p_87739_, (String)null);
         worldtemplate.f_87732_ = JsonUtils.m_90161_("trailer", p_87739_, "");
         worldtemplate.f_87733_ = JsonUtils.m_90161_("recommendedPlayers", p_87739_, "");
         worldtemplate.f_87734_ = WorldTemplate.WorldTemplateType.valueOf(JsonUtils.m_90161_("type", p_87739_, WorldTemplate.WorldTemplateType.WORLD_TEMPLATE.name()));
      } catch (Exception exception) {
         f_87735_.error("Could not parse WorldTemplate: {}", (Object)exception.getMessage());
      }

      return worldtemplate;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum WorldTemplateType {
      WORLD_TEMPLATE,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;
   }
}