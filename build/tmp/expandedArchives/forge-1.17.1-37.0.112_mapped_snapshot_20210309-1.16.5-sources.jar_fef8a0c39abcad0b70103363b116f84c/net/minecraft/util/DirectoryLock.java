package net.minecraft.util;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DirectoryLock implements AutoCloseable {
   public static final String f_144627_ = "session.lock";
   private final FileChannel f_13632_;
   private final FileLock f_13633_;
   private static final ByteBuffer f_13634_;

   public static DirectoryLock m_13640_(Path p_13641_) throws IOException {
      Path path = p_13641_.resolve("session.lock");
      if (!Files.isDirectory(p_13641_)) {
         Files.createDirectories(p_13641_);
      }

      FileChannel filechannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         filechannel.write(f_13634_.duplicate());
         filechannel.force(true);
         FileLock filelock = filechannel.tryLock();
         if (filelock == null) {
            throw DirectoryLock.LockException.m_13648_(path);
         } else {
            return new DirectoryLock(filechannel, filelock);
         }
      } catch (IOException ioexception1) {
         try {
            filechannel.close();
         } catch (IOException ioexception) {
            ioexception1.addSuppressed(ioexception);
         }

         throw ioexception1;
      }
   }

   private DirectoryLock(FileChannel p_13637_, FileLock p_13638_) {
      this.f_13632_ = p_13637_;
      this.f_13633_ = p_13638_;
   }

   public void close() throws IOException {
      try {
         if (this.f_13633_.isValid()) {
            this.f_13633_.release();
         }
      } finally {
         if (this.f_13632_.isOpen()) {
            this.f_13632_.close();
         }

      }

   }

   public boolean m_13639_() {
      return this.f_13633_.isValid();
   }

   public static boolean m_13642_(Path p_13643_) throws IOException {
      Path path = p_13643_.resolve("session.lock");

      try {
         FileChannel filechannel = FileChannel.open(path, StandardOpenOption.WRITE);

         boolean flag;
         try {
            FileLock filelock = filechannel.tryLock();

            try {
               flag = filelock == null;
            } catch (Throwable throwable2) {
               if (filelock != null) {
                  try {
                     filelock.close();
                  } catch (Throwable throwable1) {
                     throwable2.addSuppressed(throwable1);
                  }
               }

               throw throwable2;
            }

            if (filelock != null) {
               filelock.close();
            }
         } catch (Throwable throwable3) {
            if (filechannel != null) {
               try {
                  filechannel.close();
               } catch (Throwable throwable) {
                  throwable3.addSuppressed(throwable);
               }
            }

            throw throwable3;
         }

         if (filechannel != null) {
            filechannel.close();
         }

         return flag;
      } catch (AccessDeniedException accessdeniedexception) {
         return true;
      } catch (NoSuchFileException nosuchfileexception) {
         return false;
      }
   }

   static {
      byte[] abyte = "\u2603".getBytes(Charsets.UTF_8);
      f_13634_ = ByteBuffer.allocateDirect(abyte.length);
      f_13634_.put(abyte);
      f_13634_.flip();
   }

   public static class LockException extends IOException {
      private LockException(Path p_13646_, String p_13647_) {
         super(p_13646_.toAbsolutePath() + ": " + p_13647_);
      }

      public static DirectoryLock.LockException m_13648_(Path p_13649_) {
         return new DirectoryLock.LockException(p_13649_, "already locked (possibly by other Minecraft instance?)");
      }
   }
}