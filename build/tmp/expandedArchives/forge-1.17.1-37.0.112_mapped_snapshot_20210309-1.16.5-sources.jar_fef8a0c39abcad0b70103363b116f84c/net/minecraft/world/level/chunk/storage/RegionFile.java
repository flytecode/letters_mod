package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegionFile implements AutoCloseable {
   private static final Logger f_63619_ = LogManager.getLogger();
   private static final int f_156605_ = 4096;
   @VisibleForTesting
   protected static final int f_156604_ = 1024;
   private static final int f_156606_ = 5;
   private static final int f_156607_ = 0;
   private static final ByteBuffer f_63620_ = ByteBuffer.allocateDirect(1);
   private static final String f_156608_ = ".mcc";
   private static final int f_156609_ = 128;
   private static final int f_156610_ = 256;
   private static final int f_156611_ = 0;
   private final FileChannel f_63621_;
   private final Path f_63622_;
   final RegionFileVersion f_63623_;
   private final ByteBuffer f_63624_ = ByteBuffer.allocateDirect(8192);
   private final IntBuffer f_63625_;
   private final IntBuffer f_63626_;
   @VisibleForTesting
   protected final RegionBitmap f_63618_ = new RegionBitmap();

   public RegionFile(File p_63629_, File p_63630_, boolean p_63631_) throws IOException {
      this(p_63629_.toPath(), p_63630_.toPath(), RegionFileVersion.f_63744_, p_63631_);
   }

   public RegionFile(Path p_63633_, Path p_63634_, RegionFileVersion p_63635_, boolean p_63636_) throws IOException {
      this.f_63623_ = p_63635_;
      if (!Files.isDirectory(p_63634_)) {
         throw new IllegalArgumentException("Expected directory, got " + p_63634_.toAbsolutePath());
      } else {
         this.f_63622_ = p_63634_;
         this.f_63625_ = this.f_63624_.asIntBuffer();
         this.f_63625_.limit(1024);
         this.f_63624_.position(4096);
         this.f_63626_ = this.f_63624_.asIntBuffer();
         if (p_63636_) {
            this.f_63621_ = FileChannel.open(p_63633_, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
         } else {
            this.f_63621_ = FileChannel.open(p_63633_, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
         }

         this.f_63618_.m_63612_(0, 2);
         this.f_63624_.position(0);
         int i = this.f_63621_.read(this.f_63624_, 0L);
         if (i != -1) {
            if (i != 8192) {
               f_63619_.warn("Region file {} has truncated header: {}", p_63633_, i);
            }

            long j = Files.size(p_63633_);

            for(int k = 0; k < 1024; ++k) {
               int l = this.f_63625_.get(k);
               if (l != 0) {
                  int i1 = m_63671_(l);
                  int j1 = m_63640_(l);
                  if (i1 < 2) {
                     f_63619_.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", p_63633_, k, i1);
                     this.f_63625_.put(k, 0);
                  } else if (j1 == 0) {
                     f_63619_.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", p_63633_, k);
                     this.f_63625_.put(k, 0);
                  } else if ((long)i1 * 4096L > j) {
                     f_63619_.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", p_63633_, k, i1);
                     this.f_63625_.put(k, 0);
                  } else {
                     this.f_63618_.m_63612_(i1, j1);
                  }
               }
            }
         }

      }
   }

   private Path m_63684_(ChunkPos p_63685_) {
      String s = "c." + p_63685_.f_45578_ + "." + p_63685_.f_45579_ + ".mcc";
      return this.f_63622_.resolve(s);
   }

   @Nullable
   public synchronized DataInputStream m_63645_(ChunkPos p_63646_) throws IOException {
      int i = this.m_63686_(p_63646_);
      if (i == 0) {
         return null;
      } else {
         int j = m_63671_(i);
         int k = m_63640_(i);
         int l = k * 4096;
         ByteBuffer bytebuffer = ByteBuffer.allocate(l);
         this.f_63621_.read(bytebuffer, (long)(j * 4096));
         bytebuffer.flip();
         if (bytebuffer.remaining() < 5) {
            f_63619_.error("Chunk {} header is truncated: expected {} but read {}", p_63646_, l, bytebuffer.remaining());
            return null;
         } else {
            int i1 = bytebuffer.getInt();
            byte b0 = bytebuffer.get();
            if (i1 == 0) {
               f_63619_.warn("Chunk {} is allocated, but stream is missing", (Object)p_63646_);
               return null;
            } else {
               int j1 = i1 - 1;
               if (m_63638_(b0)) {
                  if (j1 != 0) {
                     f_63619_.warn("Chunk has both internal and external streams");
                  }

                  return this.m_63647_(p_63646_, m_63669_(b0));
               } else if (j1 > bytebuffer.remaining()) {
                  f_63619_.error("Chunk {} stream is truncated: expected {} but read {}", p_63646_, j1, bytebuffer.remaining());
                  return null;
               } else if (j1 < 0) {
                  f_63619_.error("Declared size {} of chunk {} is negative", i1, p_63646_);
                  return null;
               } else {
                  return this.m_63650_(p_63646_, b0, m_63659_(bytebuffer, j1));
               }
            }
         }
      }
   }

   private static int m_156612_() {
      return (int)(Util.m_137574_() / 1000L);
   }

   private static boolean m_63638_(byte p_63639_) {
      return (p_63639_ & 128) != 0;
   }

   private static byte m_63669_(byte p_63670_) {
      return (byte)(p_63670_ & -129);
   }

   @Nullable
   private DataInputStream m_63650_(ChunkPos p_63651_, byte p_63652_, InputStream p_63653_) throws IOException {
      RegionFileVersion regionfileversion = RegionFileVersion.m_63756_(p_63652_);
      if (regionfileversion == null) {
         f_63619_.error("Chunk {} has invalid chunk stream version {}", p_63651_, p_63652_);
         return null;
      } else {
         return new DataInputStream(new BufferedInputStream(regionfileversion.m_63760_(p_63653_)));
      }
   }

   @Nullable
   private DataInputStream m_63647_(ChunkPos p_63648_, byte p_63649_) throws IOException {
      Path path = this.m_63684_(p_63648_);
      if (!Files.isRegularFile(path)) {
         f_63619_.error("External chunk path {} is not file", (Object)path);
         return null;
      } else {
         return this.m_63650_(p_63648_, p_63649_, Files.newInputStream(path));
      }
   }

   private static ByteArrayInputStream m_63659_(ByteBuffer p_63660_, int p_63661_) {
      return new ByteArrayInputStream(p_63660_.array(), p_63660_.position(), p_63661_);
   }

   private int m_63642_(int p_63643_, int p_63644_) {
      return p_63643_ << 8 | p_63644_;
   }

   private static int m_63640_(int p_63641_) {
      return p_63641_ & 255;
   }

   private static int m_63671_(int p_63672_) {
      return p_63672_ >> 8 & 16777215;
   }

   private static int m_63676_(int p_63677_) {
      return (p_63677_ + 4096 - 1) / 4096;
   }

   public boolean m_63673_(ChunkPos p_63674_) {
      int i = this.m_63686_(p_63674_);
      if (i == 0) {
         return false;
      } else {
         int j = m_63671_(i);
         int k = m_63640_(i);
         ByteBuffer bytebuffer = ByteBuffer.allocate(5);

         try {
            this.f_63621_.read(bytebuffer, (long)(j * 4096));
            bytebuffer.flip();
            if (bytebuffer.remaining() != 5) {
               return false;
            } else {
               int l = bytebuffer.getInt();
               byte b0 = bytebuffer.get();
               if (m_63638_(b0)) {
                  if (!RegionFileVersion.m_63764_(m_63669_(b0))) {
                     return false;
                  }

                  if (!Files.isRegularFile(this.m_63684_(p_63674_))) {
                     return false;
                  }
               } else {
                  if (!RegionFileVersion.m_63764_(b0)) {
                     return false;
                  }

                  if (l == 0) {
                     return false;
                  }

                  int i1 = l - 1;
                  if (i1 < 0 || i1 > 4096 * k) {
                     return false;
                  }
               }

               return true;
            }
         } catch (IOException ioexception) {
            return false;
         }
      }
   }

   public DataOutputStream m_63678_(ChunkPos p_63679_) throws IOException {
      return new DataOutputStream(new BufferedOutputStream(this.f_63623_.m_63762_(new RegionFile.ChunkBuffer(p_63679_))));
   }

   public void m_63637_() throws IOException {
      this.f_63621_.force(true);
   }

   public void m_156613_(ChunkPos p_156614_) throws IOException {
      int i = m_63688_(p_156614_);
      int j = this.f_63625_.get(i);
      if (j != 0) {
         this.f_63625_.put(i, 0);
         this.f_63626_.put(i, m_156612_());
         this.m_63675_();
         Files.deleteIfExists(this.m_63684_(p_156614_));
         this.f_63618_.m_63615_(m_63671_(j), m_63640_(j));
      }
   }

   protected synchronized void m_63654_(ChunkPos p_63655_, ByteBuffer p_63656_) throws IOException {
      int i = m_63688_(p_63655_);
      int j = this.f_63625_.get(i);
      int k = m_63671_(j);
      int l = m_63640_(j);
      int i1 = p_63656_.remaining();
      int j1 = m_63676_(i1);
      int k1;
      RegionFile.CommitOp regionfile$commitop;
      if (j1 >= 256) {
         Path path = this.m_63684_(p_63655_);
         f_63619_.warn("Saving oversized chunk {} ({} bytes} to external file {}", p_63655_, i1, path);
         j1 = 1;
         k1 = this.f_63618_.m_63610_(j1);
         regionfile$commitop = this.m_63662_(path, p_63656_);
         ByteBuffer bytebuffer = this.m_63668_();
         this.f_63621_.write(bytebuffer, (long)(k1 * 4096));
      } else {
         k1 = this.f_63618_.m_63610_(j1);
         regionfile$commitop = () -> {
            Files.deleteIfExists(this.m_63684_(p_63655_));
         };
         this.f_63621_.write(p_63656_, (long)(k1 * 4096));
      }

      this.f_63625_.put(i, this.m_63642_(k1, j1));
      this.f_63626_.put(i, m_156612_());
      this.m_63675_();
      regionfile$commitop.m_63698_();
      if (k != 0) {
         this.f_63618_.m_63615_(k, l);
      }

   }

   private ByteBuffer m_63668_() {
      ByteBuffer bytebuffer = ByteBuffer.allocate(5);
      bytebuffer.putInt(1);
      bytebuffer.put((byte)(this.f_63623_.m_63755_() | 128));
      bytebuffer.flip();
      return bytebuffer;
   }

   private RegionFile.CommitOp m_63662_(Path p_63663_, ByteBuffer p_63664_) throws IOException {
      Path path = Files.createTempFile(this.f_63622_, "tmp", (String)null);
      FileChannel filechannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         p_63664_.position(5);
         filechannel.write(p_63664_);
      } catch (Throwable throwable1) {
         if (filechannel != null) {
            try {
               filechannel.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (filechannel != null) {
         filechannel.close();
      }

      return () -> {
         Files.move(path, p_63663_, StandardCopyOption.REPLACE_EXISTING);
      };
   }

   private void m_63675_() throws IOException {
      this.f_63624_.position(0);
      this.f_63621_.write(this.f_63624_, 0L);
   }

   private int m_63686_(ChunkPos p_63687_) {
      return this.f_63625_.get(m_63688_(p_63687_));
   }

   public boolean m_63682_(ChunkPos p_63683_) {
      return this.m_63686_(p_63683_) != 0;
   }

   private static int m_63688_(ChunkPos p_63689_) {
      return p_63689_.m_45613_() + p_63689_.m_45614_() * 32;
   }

   public void close() throws IOException {
      try {
         this.m_63681_();
      } finally {
         try {
            this.f_63621_.force(true);
         } finally {
            this.f_63621_.close();
         }
      }

   }

   private void m_63681_() throws IOException {
      int i = (int)this.f_63621_.size();
      int j = m_63676_(i) * 4096;
      if (i != j) {
         ByteBuffer bytebuffer = f_63620_.duplicate();
         bytebuffer.position(0);
         this.f_63621_.write(bytebuffer, (long)(j - 1));
      }

   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final ChunkPos f_63693_;

      public ChunkBuffer(ChunkPos p_63696_) {
         super(8096);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(RegionFile.this.f_63623_.m_63755_());
         this.f_63693_ = p_63696_;
      }

      public void close() throws IOException {
         ByteBuffer bytebuffer = ByteBuffer.wrap(this.buf, 0, this.count);
         bytebuffer.putInt(0, this.count - 5 + 1);
         RegionFile.this.m_63654_(this.f_63693_, bytebuffer);
      }
   }

   interface CommitOp {
      void m_63698_() throws IOException;
   }
}