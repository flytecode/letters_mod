package net.minecraft.data.structures;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NbtToSnbt implements DataProvider {
   private static final Logger f_126421_ = LogManager.getLogger();
   private final DataGenerator f_126422_;

   public NbtToSnbt(DataGenerator p_126425_) {
      this.f_126422_ = p_126425_;
   }

   public void m_6865_(HashCache p_126428_) throws IOException {
      Path path = this.f_126422_.m_123916_();

      for(Path path1 : this.f_126422_.m_123913_()) {
         Files.walk(path1).filter((p_126430_) -> {
            return p_126430_.toString().endsWith(".nbt");
         }).forEach((p_126441_) -> {
            m_126431_(p_126441_, this.m_126435_(path1, p_126441_), path);
         });
      }

   }

   public String m_6055_() {
      return "NBT to SNBT";
   }

   private String m_126435_(Path p_126436_, Path p_126437_) {
      String s = p_126436_.relativize(p_126437_).toString().replaceAll("\\\\", "/");
      return s.substring(0, s.length() - ".nbt".length());
   }

   @Nullable
   public static Path m_126431_(Path p_126432_, String p_126433_, Path p_126434_) {
      try {
         m_176812_(p_126434_.resolve(p_126433_ + ".snbt"), NbtUtils.m_178063_(NbtIo.m_128939_(Files.newInputStream(p_126432_))));
         f_126421_.info("Converted {} from NBT to SNBT", (Object)p_126433_);
         return p_126434_.resolve(p_126433_ + ".snbt");
      } catch (IOException ioexception) {
         f_126421_.error("Couldn't convert {} from NBT to SNBT at {}", p_126433_, p_126432_, ioexception);
         return null;
      }
   }

   public static void m_176812_(Path p_176813_, String p_176814_) throws IOException {
      Files.createDirectories(p_176813_.getParent());
      BufferedWriter bufferedwriter = Files.newBufferedWriter(p_176813_);

      try {
         bufferedwriter.write(p_176814_);
         bufferedwriter.write(10);
      } catch (Throwable throwable1) {
         if (bufferedwriter != null) {
            try {
               bufferedwriter.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (bufferedwriter != null) {
         bufferedwriter.close();
      }

   }
}