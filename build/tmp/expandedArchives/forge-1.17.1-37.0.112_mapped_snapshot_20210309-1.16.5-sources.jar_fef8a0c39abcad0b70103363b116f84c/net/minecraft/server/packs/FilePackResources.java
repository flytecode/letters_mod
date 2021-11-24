package net.minecraft.server.packs;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class FilePackResources extends AbstractPackResources {
   public static final Splitter f_10232_ = Splitter.on('/').omitEmptyStrings().limit(3);
   private ZipFile f_10233_;

   public FilePackResources(File p_10236_) {
      super(p_10236_);
   }

   private ZipFile m_10247_() throws IOException {
      if (this.f_10233_ == null) {
         this.f_10233_ = new ZipFile(this.f_10203_);
      }

      return this.f_10233_;
   }

   protected InputStream m_5541_(String p_10246_) throws IOException {
      ZipFile zipfile = this.m_10247_();
      ZipEntry zipentry = zipfile.getEntry(p_10246_);
      if (zipentry == null) {
         throw new ResourcePackFileNotFoundException(this.f_10203_, p_10246_);
      } else {
         return zipfile.getInputStream(zipentry);
      }
   }

   public boolean m_6105_(String p_10249_) {
      try {
         return this.m_10247_().getEntry(p_10249_) != null;
      } catch (IOException ioexception) {
         return false;
      }
   }

   public Set<String> m_5698_(PackType p_10238_) {
      ZipFile zipfile;
      try {
         zipfile = this.m_10247_();
      } catch (IOException ioexception) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
      Set<String> set = Sets.newHashSet();

      while(enumeration.hasMoreElements()) {
         ZipEntry zipentry = enumeration.nextElement();
         String s = zipentry.getName();
         if (s.startsWith(p_10238_.m_10305_() + "/")) {
            List<String> list = Lists.newArrayList(f_10232_.split(s));
            if (list.size() > 1) {
               String s1 = list.get(1);
               if (s1.equals(s1.toLowerCase(Locale.ROOT))) {
                  set.add(s1);
               } else {
                  this.m_10230_(s1);
               }
            }
         }
      }

      return set;
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   public void close() {
      if (this.f_10233_ != null) {
         IOUtils.closeQuietly((Closeable)this.f_10233_);
         this.f_10233_ = null;
      }

   }

   public Collection<ResourceLocation> m_7466_(PackType p_10240_, String p_10241_, String p_10242_, int p_10243_, Predicate<String> p_10244_) {
      ZipFile zipfile;
      try {
         zipfile = this.m_10247_();
      } catch (IOException ioexception) {
         return Collections.emptySet();
      }

      Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
      List<ResourceLocation> list = Lists.newArrayList();
      String s = p_10240_.m_10305_() + "/" + p_10241_ + "/";
      String s1 = s + p_10242_ + "/";

      while(enumeration.hasMoreElements()) {
         ZipEntry zipentry = enumeration.nextElement();
         if (!zipentry.isDirectory()) {
            String s2 = zipentry.getName();
            if (!s2.endsWith(".mcmeta") && s2.startsWith(s1)) {
               String s3 = s2.substring(s.length());
               String[] astring = s3.split("/");
               if (astring.length >= p_10243_ + 1 && p_10244_.test(astring[astring.length - 1])) {
                  list.add(new ResourceLocation(p_10241_, s3));
               }
            }
         }
      }

      return list;
   }
}