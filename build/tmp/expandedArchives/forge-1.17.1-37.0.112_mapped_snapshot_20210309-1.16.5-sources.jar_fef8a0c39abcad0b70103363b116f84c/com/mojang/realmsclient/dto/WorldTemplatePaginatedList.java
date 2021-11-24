package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldTemplatePaginatedList extends ValueObject {
   private static final Logger f_87757_ = LogManager.getLogger();
   public List<WorldTemplate> f_87753_;
   public int f_87754_;
   public int f_87755_;
   public int f_87756_;

   public WorldTemplatePaginatedList() {
   }

   public WorldTemplatePaginatedList(int p_87761_) {
      this.f_87753_ = Collections.emptyList();
      this.f_87754_ = 0;
      this.f_87755_ = p_87761_;
      this.f_87756_ = -1;
   }

   public boolean m_167327_() {
      return this.f_87754_ * this.f_87755_ >= this.f_87756_ && this.f_87754_ > 0 && this.f_87756_ > 0 && this.f_87755_ > 0;
   }

   public static WorldTemplatePaginatedList m_87762_(String p_87763_) {
      WorldTemplatePaginatedList worldtemplatepaginatedlist = new WorldTemplatePaginatedList();
      worldtemplatepaginatedlist.f_87753_ = Lists.newArrayList();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87763_).getAsJsonObject();
         if (jsonobject.get("templates").isJsonArray()) {
            Iterator<JsonElement> iterator = jsonobject.get("templates").getAsJsonArray().iterator();

            while(iterator.hasNext()) {
               worldtemplatepaginatedlist.f_87753_.add(WorldTemplate.m_87738_(iterator.next().getAsJsonObject()));
            }
         }

         worldtemplatepaginatedlist.f_87754_ = JsonUtils.m_90153_("page", jsonobject, 0);
         worldtemplatepaginatedlist.f_87755_ = JsonUtils.m_90153_("size", jsonobject, 0);
         worldtemplatepaginatedlist.f_87756_ = JsonUtils.m_90153_("total", jsonobject, 0);
      } catch (Exception exception) {
         f_87757_.error("Could not parse WorldTemplatePaginatedList: {}", (Object)exception.getMessage());
      }

      return worldtemplatepaginatedlist;
   }
}