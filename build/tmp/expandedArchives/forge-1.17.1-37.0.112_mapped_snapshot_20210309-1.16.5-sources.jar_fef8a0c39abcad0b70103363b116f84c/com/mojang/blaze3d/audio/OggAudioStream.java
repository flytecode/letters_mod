package com.mojang.blaze3d.audio;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisAlloc;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class OggAudioStream implements AudioStream {
   private static final int f_166130_ = 8192;
   private long f_83746_;
   private final AudioFormat f_83747_;
   private final InputStream f_83748_;
   private ByteBuffer f_83749_ = MemoryUtil.memAlloc(8192);

   public OggAudioStream(InputStream p_83751_) throws IOException {
      this.f_83748_ = p_83751_;
      this.f_83749_.limit(0);
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         IntBuffer intbuffer = memorystack.mallocInt(1);
         IntBuffer intbuffer1 = memorystack.mallocInt(1);

         while(this.f_83746_ == 0L) {
            if (!this.m_83765_()) {
               throw new IOException("Failed to find Ogg header");
            }

            int i = this.f_83749_.position();
            this.f_83749_.position(0);
            this.f_83746_ = STBVorbis.stb_vorbis_open_pushdata(this.f_83749_, intbuffer, intbuffer1, (STBVorbisAlloc)null);
            this.f_83749_.position(i);
            int j = intbuffer1.get(0);
            if (j == 1) {
               this.m_83767_();
            } else if (j != 0) {
               throw new IOException("Failed to read Ogg file " + j);
            }
         }

         this.f_83749_.position(this.f_83749_.position() + intbuffer.get(0));
         STBVorbisInfo stbvorbisinfo = STBVorbisInfo.mallocStack(memorystack);
         STBVorbis.stb_vorbis_get_info(this.f_83746_, stbvorbisinfo);
         this.f_83747_ = new AudioFormat((float)stbvorbisinfo.sample_rate(), 16, stbvorbisinfo.channels(), true, false);
      } catch (Throwable throwable1) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (memorystack != null) {
         memorystack.close();
      }

   }

   private boolean m_83765_() throws IOException {
      int i = this.f_83749_.limit();
      int j = this.f_83749_.capacity() - i;
      if (j == 0) {
         return true;
      } else {
         byte[] abyte = new byte[j];
         int k = this.f_83748_.read(abyte);
         if (k == -1) {
            return false;
         } else {
            int l = this.f_83749_.position();
            this.f_83749_.limit(i + k);
            this.f_83749_.position(i);
            this.f_83749_.put(abyte, 0, k);
            this.f_83749_.position(l);
            return true;
         }
      }
   }

   private void m_83767_() {
      boolean flag = this.f_83749_.position() == 0;
      boolean flag1 = this.f_83749_.position() == this.f_83749_.limit();
      if (flag1 && !flag) {
         this.f_83749_.position(0);
         this.f_83749_.limit(0);
      } else {
         ByteBuffer bytebuffer = MemoryUtil.memAlloc(flag ? 2 * this.f_83749_.capacity() : this.f_83749_.capacity());
         bytebuffer.put(this.f_83749_);
         MemoryUtil.memFree(this.f_83749_);
         bytebuffer.flip();
         this.f_83749_ = bytebuffer;
      }

   }

   private boolean m_83755_(OggAudioStream.OutputConcat p_83756_) throws IOException {
      if (this.f_83746_ == 0L) {
         return false;
      } else {
         MemoryStack memorystack = MemoryStack.stackPush();

         boolean flag1;
         label79: {
            boolean flag;
            label80: {
               try {
                  PointerBuffer pointerbuffer = memorystack.mallocPointer(1);
                  IntBuffer intbuffer = memorystack.mallocInt(1);
                  IntBuffer intbuffer1 = memorystack.mallocInt(1);

                  while(true) {
                     int l = STBVorbis.stb_vorbis_decode_frame_pushdata(this.f_83746_, this.f_83749_, intbuffer, pointerbuffer, intbuffer1);
                     this.f_83749_.position(this.f_83749_.position() + l);
                     int i = STBVorbis.stb_vorbis_get_error(this.f_83746_);
                     if (i == 1) {
                        this.m_83767_();
                        if (!this.m_83765_()) {
                           flag1 = false;
                           break label79;
                        }
                     } else {
                        if (i != 0) {
                           throw new IOException("Failed to read Ogg file " + i);
                        }

                        int j = intbuffer1.get(0);
                        if (j != 0) {
                           int k = intbuffer.get(0);
                           PointerBuffer pointerbuffer1 = pointerbuffer.getPointerBuffer(k);
                           if (k == 1) {
                              this.m_83757_(pointerbuffer1.getFloatBuffer(0, j), p_83756_);
                              flag = true;
                              break label80;
                           }

                           if (k != 2) {
                              throw new IllegalStateException("Invalid number of channels: " + k);
                           }

                           this.m_83760_(pointerbuffer1.getFloatBuffer(0, j), pointerbuffer1.getFloatBuffer(1, j), p_83756_);
                           flag = true;
                           break;
                        }
                     }
                  }
               } catch (Throwable throwable1) {
                  if (memorystack != null) {
                     try {
                        memorystack.close();
                     } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                     }
                  }

                  throw throwable1;
               }

               if (memorystack != null) {
                  memorystack.close();
               }

               return flag;
            }

            if (memorystack != null) {
               memorystack.close();
            }

            return flag;
         }

         if (memorystack != null) {
            memorystack.close();
         }

         return flag1;
      }
   }

   private void m_83757_(FloatBuffer p_83758_, OggAudioStream.OutputConcat p_83759_) {
      while(p_83758_.hasRemaining()) {
         p_83759_.m_83775_(p_83758_.get());
      }

   }

   private void m_83760_(FloatBuffer p_83761_, FloatBuffer p_83762_, OggAudioStream.OutputConcat p_83763_) {
      while(p_83761_.hasRemaining() && p_83762_.hasRemaining()) {
         p_83763_.m_83775_(p_83761_.get());
         p_83763_.m_83775_(p_83762_.get());
      }

   }

   public void close() throws IOException {
      if (this.f_83746_ != 0L) {
         STBVorbis.stb_vorbis_close(this.f_83746_);
         this.f_83746_ = 0L;
      }

      MemoryUtil.memFree(this.f_83749_);
      this.f_83748_.close();
   }

   public AudioFormat m_6206_() {
      return this.f_83747_;
   }

   public ByteBuffer m_7118_(int p_83754_) throws IOException {
      OggAudioStream.OutputConcat oggaudiostream$outputconcat = new OggAudioStream.OutputConcat(p_83754_ + 8192);

      while(this.m_83755_(oggaudiostream$outputconcat) && oggaudiostream$outputconcat.f_83770_ < p_83754_) {
      }

      return oggaudiostream$outputconcat.m_83774_();
   }

   public ByteBuffer m_83764_() throws IOException {
      OggAudioStream.OutputConcat oggaudiostream$outputconcat = new OggAudioStream.OutputConcat(16384);

      while(this.m_83755_(oggaudiostream$outputconcat)) {
      }

      return oggaudiostream$outputconcat.m_83774_();
   }

   @OnlyIn(Dist.CLIENT)
   static class OutputConcat {
      private final List<ByteBuffer> f_83768_ = Lists.newArrayList();
      private final int f_83769_;
      int f_83770_;
      private ByteBuffer f_83771_;

      public OutputConcat(int p_83773_) {
         this.f_83769_ = p_83773_ + 1 & -2;
         this.m_83779_();
      }

      private void m_83779_() {
         this.f_83771_ = BufferUtils.createByteBuffer(this.f_83769_);
      }

      public void m_83775_(float p_83776_) {
         if (this.f_83771_.remaining() == 0) {
            this.f_83771_.flip();
            this.f_83768_.add(this.f_83771_);
            this.m_83779_();
         }

         int i = Mth.m_14045_((int)(p_83776_ * 32767.5F - 0.5F), -32768, 32767);
         this.f_83771_.putShort((short)i);
         this.f_83770_ += 2;
      }

      public ByteBuffer m_83774_() {
         this.f_83771_.flip();
         if (this.f_83768_.isEmpty()) {
            return this.f_83771_;
         } else {
            ByteBuffer bytebuffer = BufferUtils.createByteBuffer(this.f_83770_);
            this.f_83768_.forEach(bytebuffer::put);
            bytebuffer.put(this.f_83771_);
            bytebuffer.flip();
            return bytebuffer;
         }
      }
   }
}