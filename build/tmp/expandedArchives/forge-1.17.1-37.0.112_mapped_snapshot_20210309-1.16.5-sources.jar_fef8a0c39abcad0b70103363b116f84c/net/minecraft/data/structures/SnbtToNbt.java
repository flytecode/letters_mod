package net.minecraft.data.structures;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnbtToNbt implements DataProvider {
   @Nullable
   private static final Path f_176815_ = null;
   private static final Logger f_126443_ = LogManager.getLogger();
   private final DataGenerator f_126444_;
   private final List<SnbtToNbt.Filter> f_126445_ = Lists.newArrayList();

   public SnbtToNbt(DataGenerator p_126448_) {
      this.f_126444_ = p_126448_;
   }

   public SnbtToNbt m_126475_(SnbtToNbt.Filter p_126476_) {
      this.f_126445_.add(p_126476_);
      return this;
   }

   private CompoundTag m_126460_(String p_126461_, CompoundTag p_126462_) {
      CompoundTag compoundtag = p_126462_;

      for(SnbtToNbt.Filter snbttonbt$filter : this.f_126445_) {
         compoundtag = snbttonbt$filter.m_6392_(p_126461_, compoundtag);
      }

      return compoundtag;
   }

   public void m_6865_(HashCache p_126451_) throws IOException {
      Path path = this.f_126444_.m_123916_();
      List<CompletableFuture<SnbtToNbt.TaskResult>> list = Lists.newArrayList();

      for(Path path1 : this.f_126444_.m_123913_()) {
         Files.walk(path1).filter((p_126464_) -> {
            return p_126464_.toString().endsWith(".snbt");
         }).forEach((p_126474_) -> {
            list.add(CompletableFuture.supplyAsync(() -> {
               return this.m_126465_(p_126474_, this.m_126468_(path1, p_126474_));
            }, Util.m_137578_()));
         });
      }

      boolean flag = false;

      for(CompletableFuture<SnbtToNbt.TaskResult> completablefuture : list) {
         try {
            this.m_126456_(p_126451_, completablefuture.get(), path);
         } catch (Exception exception) {
            f_126443_.error("Failed to process structure", (Throwable)exception);
            flag = true;
         }
      }

      if (flag) {
         throw new IllegalStateException("Failed to convert all structures, aborting");
      }
   }

   public String m_6055_() {
      return "SNBT -> NBT";
   }

   private String m_126468_(Path p_126469_, Path p_126470_) {
      String s = p_126469_.relativize(p_126470_).toString().replaceAll("\\\\", "/");
      return s.substring(0, s.length() - ".snbt".length());
   }

   private SnbtToNbt.TaskResult m_126465_(Path p_126466_, String p_126467_) {
      try {
         BufferedReader bufferedreader = Files.newBufferedReader(p_126466_);

         SnbtToNbt.TaskResult snbttonbt$taskresult;
         try {
            String s = IOUtils.toString((Reader)bufferedreader);
            CompoundTag compoundtag = this.m_126460_(p_126467_, NbtUtils.m_178024_(s));
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            NbtIo.m_128947_(compoundtag, bytearrayoutputstream);
            byte[] abyte = bytearrayoutputstream.toByteArray();
            String s1 = f_123918_.hashBytes(abyte).toString();
            String s2;
            if (f_176815_ != null) {
               s2 = NbtUtils.m_178063_(compoundtag);
            } else {
               s2 = null;
            }

            snbttonbt$taskresult = new SnbtToNbt.TaskResult(p_126467_, abyte, s2, s1);
         } catch (Throwable throwable1) {
            if (bufferedreader != null) {
               try {
                  bufferedreader.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (bufferedreader != null) {
            bufferedreader.close();
         }

         return snbttonbt$taskresult;
      } catch (Throwable throwable2) {
         throw new SnbtToNbt.StructureConversionException(p_126466_, throwable2);
      }
   }

   private void m_126456_(HashCache p_126457_, SnbtToNbt.TaskResult p_126458_, Path p_126459_) {
      if (p_126458_.f_126484_ != null) {
         Path path = f_176815_.resolve(p_126458_.f_126482_ + ".snbt");

         try {
            NbtToSnbt.m_176812_(path, p_126458_.f_126484_);
         } catch (IOException ioexception) {
            f_126443_.error("Couldn't write structure SNBT {} at {}", p_126458_.f_126482_, path, ioexception);
         }
      }

      Path path1 = p_126459_.resolve(p_126458_.f_126482_ + ".nbt");

      try {
         if (!Objects.equals(p_126457_.m_123938_(path1), p_126458_.f_126485_) || !Files.exists(path1)) {
            Files.createDirectories(path1.getParent());
            OutputStream outputstream = Files.newOutputStream(path1);

            try {
               outputstream.write(p_126458_.f_126483_);
            } catch (Throwable throwable1) {
               if (outputstream != null) {
                  try {
                     outputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (outputstream != null) {
               outputstream.close();
            }
         }

         p_126457_.m_123940_(path1, p_126458_.f_126485_);
      } catch (IOException ioexception1) {
         f_126443_.error("Couldn't write structure {} at {}", p_126458_.f_126482_, path1, ioexception1);
      }

   }

   @FunctionalInterface
   public interface Filter {
      CompoundTag m_6392_(String p_126480_, CompoundTag p_126481_);
   }

   static class StructureConversionException extends RuntimeException {
      public StructureConversionException(Path p_176820_, Throwable p_176821_) {
         super(p_176820_.toAbsolutePath().toString(), p_176821_);
      }
   }

   static class TaskResult {
      final String f_126482_;
      final byte[] f_126483_;
      @Nullable
      final String f_126484_;
      final String f_126485_;

      public TaskResult(String p_126487_, byte[] p_126488_, @Nullable String p_126489_, String p_126490_) {
         this.f_126482_ = p_126487_;
         this.f_126483_ = p_126488_;
         this.f_126484_ = p_126489_;
         this.f_126485_ = p_126490_;
      }
   }
}