package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class BackupList extends ValueObject {
   private static final Logger f_87406_ = LogManager.getLogger();
   public List<Backup> f_87405_;

   public static BackupList m_87409_(String p_87410_) {
      JsonParser jsonparser = new JsonParser();
      BackupList backuplist = new BackupList();
      backuplist.f_87405_ = Lists.newArrayList();

      try {
         JsonElement jsonelement = jsonparser.parse(p_87410_).getAsJsonObject().get("backups");
         if (jsonelement.isJsonArray()) {
            Iterator<JsonElement> iterator = jsonelement.getAsJsonArray().iterator();

            while(iterator.hasNext()) {
               backuplist.f_87405_.add(Backup.m_87399_(iterator.next()));
            }
         }
      } catch (Exception exception) {
         f_87406_.error("Could not parse BackupList: {}", (Object)exception.getMessage());
      }

      return backuplist;
   }
}