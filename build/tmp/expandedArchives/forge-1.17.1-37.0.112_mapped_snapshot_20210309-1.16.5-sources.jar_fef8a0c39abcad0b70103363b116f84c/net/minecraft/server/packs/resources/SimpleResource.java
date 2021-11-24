package net.minecraft.server.packs.resources;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements Resource {
   private final String f_10922_;
   private final ResourceLocation f_10923_;
   private final InputStream f_10924_;
   private final InputStream f_10925_;
   private boolean f_10926_;
   private JsonObject f_10927_;

   public SimpleResource(String p_10929_, ResourceLocation p_10930_, InputStream p_10931_, @Nullable InputStream p_10932_) {
      this.f_10922_ = p_10929_;
      this.f_10923_ = p_10930_;
      this.f_10924_ = p_10931_;
      this.f_10925_ = p_10932_;
   }

   public ResourceLocation m_7843_() {
      return this.f_10923_;
   }

   public InputStream m_6679_() {
      return this.f_10924_;
   }

   public boolean m_142564_() {
      return this.f_10925_ != null;
   }

   @Nullable
   public <T> T m_5507_(MetadataSectionSerializer<T> p_10935_) {
      if (!this.m_142564_()) {
         return (T)null;
      } else {
         if (this.f_10927_ == null && !this.f_10926_) {
            this.f_10926_ = true;
            BufferedReader bufferedreader = null;

            try {
               bufferedreader = new BufferedReader(new InputStreamReader(this.f_10925_, StandardCharsets.UTF_8));
               this.f_10927_ = GsonHelper.m_13859_(bufferedreader);
            } finally {
               IOUtils.closeQuietly((Reader)bufferedreader);
            }
         }

         if (this.f_10927_ == null) {
            return (T)null;
         } else {
            String s = p_10935_.m_7991_();
            return (T)(this.f_10927_.has(s) ? p_10935_.m_6322_(GsonHelper.m_13930_(this.f_10927_, s)) : null);
         }
      }
   }

   public String m_7816_() {
      return this.f_10922_;
   }

   public boolean equals(Object p_10941_) {
      if (this == p_10941_) {
         return true;
      } else if (!(p_10941_ instanceof SimpleResource)) {
         return false;
      } else {
         SimpleResource simpleresource = (SimpleResource)p_10941_;
         if (this.f_10923_ != null) {
            if (!this.f_10923_.equals(simpleresource.f_10923_)) {
               return false;
            }
         } else if (simpleresource.f_10923_ != null) {
            return false;
         }

         if (this.f_10922_ != null) {
            if (!this.f_10922_.equals(simpleresource.f_10922_)) {
               return false;
            }
         } else if (simpleresource.f_10922_ != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int i = this.f_10922_ != null ? this.f_10922_.hashCode() : 0;
      return 31 * i + (this.f_10923_ != null ? this.f_10923_.hashCode() : 0);
   }

   public void close() throws IOException {
      this.f_10924_.close();
      if (this.f_10925_ != null) {
         this.f_10925_.close();
      }

   }
}