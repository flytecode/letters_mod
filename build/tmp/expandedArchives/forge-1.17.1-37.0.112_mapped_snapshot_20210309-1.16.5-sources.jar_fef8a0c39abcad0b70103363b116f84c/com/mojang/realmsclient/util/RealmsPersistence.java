package com.mojang.realmsclient.util;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.FileUtils;

@OnlyIn(Dist.CLIENT)
public class RealmsPersistence {
   private static final String f_167613_ = "realms_persistence.json";
   private static final GuardedSerializer f_90169_ = new GuardedSerializer();

   public RealmsPersistence.RealmsPersistenceData m_167615_() {
      return m_90171_();
   }

   public void m_167616_(RealmsPersistence.RealmsPersistenceData p_167617_) {
      m_90172_(p_167617_);
   }

   public static RealmsPersistence.RealmsPersistenceData m_90171_() {
      File file1 = m_90174_();

      try {
         String s = FileUtils.readFileToString(file1, StandardCharsets.UTF_8);
         RealmsPersistence.RealmsPersistenceData realmspersistence$realmspersistencedata = f_90169_.m_87415_(s, RealmsPersistence.RealmsPersistenceData.class);
         return realmspersistence$realmspersistencedata != null ? realmspersistence$realmspersistencedata : new RealmsPersistence.RealmsPersistenceData();
      } catch (IOException ioexception) {
         return new RealmsPersistence.RealmsPersistenceData();
      }
   }

   public static void m_90172_(RealmsPersistence.RealmsPersistenceData p_90173_) {
      File file1 = m_90174_();

      try {
         FileUtils.writeStringToFile(file1, f_90169_.m_87413_(p_90173_), StandardCharsets.UTF_8);
      } catch (IOException ioexception) {
      }

   }

   private static File m_90174_() {
      return new File(Minecraft.m_91087_().f_91069_, "realms_persistence.json");
   }

   @OnlyIn(Dist.CLIENT)
   public static class RealmsPersistenceData implements ReflectionBasedSerialization {
      @SerializedName("newsLink")
      public String f_90175_;
      @SerializedName("hasUnreadNews")
      public boolean f_90176_;
   }
}