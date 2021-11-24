package net.minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

public class FileUtil {
   private static final Pattern f_133725_ = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
   private static final int f_179920_ = 255;
   private static final Pattern f_133726_ = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);

   public static String m_133730_(Path p_133731_, String p_133732_, String p_133733_) throws IOException {
      for(char c0 : SharedConstants.f_136184_) {
         p_133732_ = p_133732_.replace(c0, '_');
      }

      p_133732_ = p_133732_.replaceAll("[./\"]", "_");
      if (f_133726_.matcher(p_133732_).matches()) {
         p_133732_ = "_" + p_133732_ + "_";
      }

      Matcher matcher = f_133725_.matcher(p_133732_);
      int j = 0;
      if (matcher.matches()) {
         p_133732_ = matcher.group("name");
         j = Integer.parseInt(matcher.group("count"));
      }

      if (p_133732_.length() > 255 - p_133733_.length()) {
         p_133732_ = p_133732_.substring(0, 255 - p_133733_.length());
      }

      while(true) {
         String s = p_133732_;
         if (j != 0) {
            String s1 = " (" + j + ")";
            int i = 255 - s1.length();
            if (p_133732_.length() > i) {
               s = p_133732_.substring(0, i);
            }

            s = s + s1;
         }

         s = s + p_133733_;
         Path path = p_133731_.resolve(s);

         try {
            Path path1 = Files.createDirectory(path);
            Files.deleteIfExists(path1);
            return p_133731_.relativize(path1).toString();
         } catch (FileAlreadyExistsException filealreadyexistsexception) {
            ++j;
         }
      }
   }

   public static boolean m_133728_(Path p_133729_) {
      Path path = p_133729_.normalize();
      return path.equals(p_133729_);
   }

   public static boolean m_133734_(Path p_133735_) {
      for(Path path : p_133735_) {
         if (f_133726_.matcher(path.toString()).matches()) {
            return false;
         }
      }

      return true;
   }

   public static Path m_133736_(Path p_133737_, String p_133738_, String p_133739_) {
      String s = p_133738_ + p_133739_;
      Path path = Paths.get(s);
      if (path.endsWith(p_133739_)) {
         throw new InvalidPathException(s, "empty resource name");
      } else {
         return p_133737_.resolve(path);
      }
   }

   public static String m_179922_(String p_179923_) {
      return FilenameUtils.getFullPath(p_179923_).replace(File.separator, "/");
   }

   public static String m_179924_(String p_179925_) {
      return FilenameUtils.normalize(p_179925_).replace(File.separator, "/");
   }
}