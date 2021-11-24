package net.minecraft.server.packs;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPackResources implements PackResources {
   private static final Logger f_10204_ = LogManager.getLogger();
   public final File f_10203_;

   public AbstractPackResources(File p_10207_) {
      this.f_10203_ = p_10207_;
   }

   private static String m_10226_(PackType p_10227_, ResourceLocation p_10228_) {
      return String.format("%s/%s/%s", p_10227_.m_10305_(), p_10228_.m_135827_(), p_10228_.m_135815_());
   }

   protected static String m_10217_(File p_10218_, File p_10219_) {
      return p_10218_.toURI().relativize(p_10219_.toURI()).getPath();
   }

   public InputStream m_8031_(PackType p_10210_, ResourceLocation p_10211_) throws IOException {
      return this.m_5541_(m_10226_(p_10210_, p_10211_));
   }

   public boolean m_7211_(PackType p_10222_, ResourceLocation p_10223_) {
      return this.m_6105_(m_10226_(p_10222_, p_10223_));
   }

   protected abstract InputStream m_5541_(String p_10220_) throws IOException;

   public InputStream m_5542_(String p_10225_) throws IOException {
      if (!p_10225_.contains("/") && !p_10225_.contains("\\")) {
         return this.m_5541_(p_10225_);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   protected abstract boolean m_6105_(String p_10229_);

   protected void m_10230_(String p_10231_) {
      f_10204_.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", p_10231_, this.f_10203_);
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> p_10213_) throws IOException {
      InputStream inputstream = this.m_5541_("pack.mcmeta");

      Object object;
      try {
         object = m_10214_(p_10213_, inputstream);
      } catch (Throwable throwable1) {
         if (inputstream != null) {
            try {
               inputstream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (inputstream != null) {
         inputstream.close();
      }

      return (T)object;
   }

   @Nullable
   public static <T> T m_10214_(MetadataSectionSerializer<T> p_10215_, InputStream p_10216_) {
      JsonObject jsonobject;
      try {
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(p_10216_, StandardCharsets.UTF_8));

         try {
            jsonobject = GsonHelper.m_13859_(bufferedreader);
         } catch (Throwable throwable1) {
            try {
               bufferedreader.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         bufferedreader.close();
      } catch (JsonParseException | IOException ioexception) {
         f_10204_.error("Couldn't load {} metadata", p_10215_.m_7991_(), ioexception);
         return (T)null;
      }

      if (!jsonobject.has(p_10215_.m_7991_())) {
         return (T)null;
      } else {
         try {
            return p_10215_.m_6322_(GsonHelper.m_13930_(jsonobject, p_10215_.m_7991_()));
         } catch (JsonParseException jsonparseexception) {
            f_10204_.error("Couldn't load {} metadata", p_10215_.m_7991_(), jsonparseexception);
            return (T)null;
         }
      }
   }

   public String m_8017_() {
      return this.f_10203_.getName();
   }

   @Override
   public String toString()
   {
      return String.format("%s: %s", getClass().getName(), f_10203_.getPath());
   }
}
