package net.minecraft.server.packs;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FolderPackResources extends AbstractPackResources {
   private static final Logger f_10252_ = LogManager.getLogger();
   private static final boolean f_10253_ = Util.m_137581_() == Util.OS.WINDOWS;
   private static final CharMatcher f_10254_ = CharMatcher.is('\\');

   public FolderPackResources(File p_10257_) {
      super(p_10257_);
   }

   public static boolean m_10273_(File p_10274_, String p_10275_) throws IOException {
      String s = p_10274_.getCanonicalPath();
      if (f_10253_) {
         s = f_10254_.replaceFrom(s, '/');
      }

      return s.endsWith(p_10275_);
   }

   protected InputStream m_5541_(String p_10277_) throws IOException {
      File file1 = this.m_10281_(p_10277_);
      if (file1 == null) {
         throw new ResourcePackFileNotFoundException(this.f_10203_, p_10277_);
      } else {
         return new FileInputStream(file1);
      }
   }

   protected boolean m_6105_(String p_10279_) {
      return this.m_10281_(p_10279_) != null;
   }

   @Nullable
   private File m_10281_(String p_10282_) {
      try {
         File file1 = new File(this.f_10203_, p_10282_);
         if (file1.isFile() && m_10273_(file1, p_10282_)) {
            return file1;
         }
      } catch (IOException ioexception) {
      }

      return null;
   }

   public Set<String> m_5698_(PackType p_10259_) {
      Set<String> set = Sets.newHashSet();
      File file1 = new File(this.f_10203_, p_10259_.m_10305_());
      File[] afile = file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
      if (afile != null) {
         for(File file2 : afile) {
            String s = m_10217_(file1, file2);
            if (s.equals(s.toLowerCase(Locale.ROOT))) {
               set.add(s.substring(0, s.length() - 1));
            } else {
               this.m_10230_(s);
            }
         }
      }

      return set;
   }

   public void close() {
   }

   public Collection<ResourceLocation> m_7466_(PackType p_10261_, String p_10262_, String p_10263_, int p_10264_, Predicate<String> p_10265_) {
      File file1 = new File(this.f_10203_, p_10261_.m_10305_());
      List<ResourceLocation> list = Lists.newArrayList();
      this.m_10266_(new File(new File(file1, p_10262_), p_10263_), p_10264_, p_10262_, list, p_10263_ + "/", p_10265_);
      return list;
   }

   private void m_10266_(File p_10267_, int p_10268_, String p_10269_, List<ResourceLocation> p_10270_, String p_10271_, Predicate<String> p_10272_) {
      File[] afile = p_10267_.listFiles();
      if (afile != null) {
         for(File file1 : afile) {
            if (file1.isDirectory()) {
               if (p_10268_ > 0) {
                  this.m_10266_(file1, p_10268_ - 1, p_10269_, p_10270_, p_10271_ + file1.getName() + "/", p_10272_);
               }
            } else if (!file1.getName().endsWith(".mcmeta") && p_10272_.test(file1.getName())) {
               try {
                  p_10270_.add(new ResourceLocation(p_10269_, p_10271_ + file1.getName()));
               } catch (ResourceLocationException resourcelocationexception) {
                  f_10252_.error(resourcelocationexception.getMessage());
               }
            }
         }
      }

   }
}