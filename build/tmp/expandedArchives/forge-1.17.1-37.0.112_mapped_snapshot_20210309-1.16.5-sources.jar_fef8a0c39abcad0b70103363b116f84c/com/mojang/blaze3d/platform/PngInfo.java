package com.mojang.blaze3d.platform;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class PngInfo {
   public final int f_85207_;
   public final int f_85208_;

   public PngInfo(String p_85210_, InputStream p_85211_) throws IOException {
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         PngInfo.StbReader pnginfo$stbreader = m_85212_(p_85211_);

         try {
            STBIReadCallback stbireadcallback = STBIReadCallback.create(pnginfo$stbreader::m_85223_);

            try {
               STBISkipCallback stbiskipcallback = STBISkipCallback.create(pnginfo$stbreader::m_85220_);

               try {
                  STBIEOFCallback stbieofcallback = STBIEOFCallback.create(pnginfo$stbreader::m_6816_);

                  try {
                     STBIIOCallbacks stbiiocallbacks = STBIIOCallbacks.mallocStack(memorystack);
                     stbiiocallbacks.read(stbireadcallback);
                     stbiiocallbacks.skip(stbiskipcallback);
                     stbiiocallbacks.eof(stbieofcallback);
                     IntBuffer intbuffer = memorystack.mallocInt(1);
                     IntBuffer intbuffer1 = memorystack.mallocInt(1);
                     IntBuffer intbuffer2 = memorystack.mallocInt(1);
                     if (!STBImage.stbi_info_from_callbacks(stbiiocallbacks, 0L, intbuffer, intbuffer1, intbuffer2)) {
                        throw new IOException("Could not read info from the PNG file " + p_85210_ + " " + STBImage.stbi_failure_reason());
                     }

                     this.f_85207_ = intbuffer.get(0);
                     this.f_85208_ = intbuffer1.get(0);
                  } catch (Throwable throwable5) {
                     if (stbieofcallback != null) {
                        try {
                           stbieofcallback.close();
                        } catch (Throwable throwable4) {
                           throwable5.addSuppressed(throwable4);
                        }
                     }

                     throw throwable5;
                  }

                  if (stbieofcallback != null) {
                     stbieofcallback.close();
                  }
               } catch (Throwable throwable6) {
                  if (stbiskipcallback != null) {
                     try {
                        stbiskipcallback.close();
                     } catch (Throwable throwable3) {
                        throwable6.addSuppressed(throwable3);
                     }
                  }

                  throw throwable6;
               }

               if (stbiskipcallback != null) {
                  stbiskipcallback.close();
               }
            } catch (Throwable throwable7) {
               if (stbireadcallback != null) {
                  try {
                     stbireadcallback.close();
                  } catch (Throwable throwable2) {
                     throwable7.addSuppressed(throwable2);
                  }
               }

               throw throwable7;
            }

            if (stbireadcallback != null) {
               stbireadcallback.close();
            }
         } catch (Throwable throwable8) {
            if (pnginfo$stbreader != null) {
               try {
                  pnginfo$stbreader.close();
               } catch (Throwable throwable1) {
                  throwable8.addSuppressed(throwable1);
               }
            }

            throw throwable8;
         }

         if (pnginfo$stbreader != null) {
            pnginfo$stbreader.close();
         }
      } catch (Throwable throwable9) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable throwable) {
               throwable9.addSuppressed(throwable);
            }
         }

         throw throwable9;
      }

      if (memorystack != null) {
         memorystack.close();
      }

   }

   private static PngInfo.StbReader m_85212_(InputStream p_85213_) {
      return (PngInfo.StbReader)(p_85213_ instanceof FileInputStream ? new PngInfo.StbReaderSeekableByteChannel(((FileInputStream)p_85213_).getChannel()) : new PngInfo.StbReaderBufferedChannel(Channels.newChannel(p_85213_)));
   }

   @OnlyIn(Dist.CLIENT)
   abstract static class StbReader implements AutoCloseable {
      protected boolean f_85214_;

      int m_85223_(long p_85224_, long p_85225_, int p_85226_) {
         try {
            return this.m_5835_(p_85225_, p_85226_);
         } catch (IOException ioexception) {
            this.f_85214_ = true;
            return 0;
         }
      }

      void m_85220_(long p_85221_, int p_85222_) {
         try {
            this.m_5666_(p_85222_);
         } catch (IOException ioexception) {
            this.f_85214_ = true;
         }

      }

      int m_6816_(long p_85219_) {
         return this.f_85214_ ? 1 : 0;
      }

      protected abstract int m_5835_(long p_85227_, int p_85228_) throws IOException;

      protected abstract void m_5666_(int p_85218_) throws IOException;

      public abstract void close() throws IOException;
   }

   @OnlyIn(Dist.CLIENT)
   static class StbReaderBufferedChannel extends PngInfo.StbReader {
      private static final int f_166443_ = 128;
      private final ReadableByteChannel f_85230_;
      private long f_85231_ = MemoryUtil.nmemAlloc(128L);
      private int f_85232_ = 128;
      private int f_85233_;
      private int f_85234_;

      StbReaderBufferedChannel(ReadableByteChannel p_85236_) {
         this.f_85230_ = p_85236_;
      }

      private void m_85242_(int p_85243_) throws IOException {
         ByteBuffer bytebuffer = MemoryUtil.memByteBuffer(this.f_85231_, this.f_85232_);
         if (p_85243_ + this.f_85234_ > this.f_85232_) {
            this.f_85232_ = p_85243_ + this.f_85234_;
            bytebuffer = MemoryUtil.memRealloc(bytebuffer, this.f_85232_);
            this.f_85231_ = MemoryUtil.memAddress(bytebuffer);
         }

         bytebuffer.position(this.f_85233_);

         while(p_85243_ + this.f_85234_ > this.f_85233_) {
            try {
               int i = this.f_85230_.read(bytebuffer);
               if (i == -1) {
                  break;
               }
            } finally {
               this.f_85233_ = bytebuffer.position();
            }
         }

      }

      public int m_5835_(long p_85245_, int p_85246_) throws IOException {
         this.m_85242_(p_85246_);
         if (p_85246_ + this.f_85234_ > this.f_85233_) {
            p_85246_ = this.f_85233_ - this.f_85234_;
         }

         MemoryUtil.memCopy(this.f_85231_ + (long)this.f_85234_, p_85245_, (long)p_85246_);
         this.f_85234_ += p_85246_;
         return p_85246_;
      }

      public void m_5666_(int p_85241_) throws IOException {
         if (p_85241_ > 0) {
            this.m_85242_(p_85241_);
            if (p_85241_ + this.f_85234_ > this.f_85233_) {
               throw new EOFException("Can't skip past the EOF.");
            }
         }

         if (this.f_85234_ + p_85241_ < 0) {
            throw new IOException("Can't seek before the beginning: " + (this.f_85234_ + p_85241_));
         } else {
            this.f_85234_ += p_85241_;
         }
      }

      public void close() throws IOException {
         MemoryUtil.nmemFree(this.f_85231_);
         this.f_85230_.close();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class StbReaderSeekableByteChannel extends PngInfo.StbReader {
      private final SeekableByteChannel f_85248_;

      StbReaderSeekableByteChannel(SeekableByteChannel p_85250_) {
         this.f_85248_ = p_85250_;
      }

      public int m_5835_(long p_85259_, int p_85260_) throws IOException {
         ByteBuffer bytebuffer = MemoryUtil.memByteBuffer(p_85259_, p_85260_);
         return this.f_85248_.read(bytebuffer);
      }

      public void m_5666_(int p_85255_) throws IOException {
         this.f_85248_.position(this.f_85248_.position() + (long)p_85255_);
      }

      public int m_6816_(long p_85257_) {
         return super.m_6816_(p_85257_) != 0 && this.f_85248_.isOpen() ? 1 : 0;
      }

      public void close() throws IOException {
         this.f_85248_.close();
      }
   }
}