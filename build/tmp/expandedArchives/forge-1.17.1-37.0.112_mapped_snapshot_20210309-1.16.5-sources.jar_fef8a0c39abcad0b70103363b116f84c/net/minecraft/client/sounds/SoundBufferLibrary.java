package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.audio.OggAudioStream;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.Util;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundBufferLibrary {
   private final ResourceManager f_120189_;
   private final Map<ResourceLocation, CompletableFuture<SoundBuffer>> f_120190_ = Maps.newHashMap();

   public SoundBufferLibrary(ResourceManager p_120192_) {
      this.f_120189_ = p_120192_;
   }

   public CompletableFuture<SoundBuffer> m_120202_(ResourceLocation p_120203_) {
      return this.f_120190_.computeIfAbsent(p_120203_, (p_120208_) -> {
         return CompletableFuture.supplyAsync(() -> {
            try {
               Resource resource = this.f_120189_.m_142591_(p_120208_);

               SoundBuffer soundbuffer;
               try {
                  InputStream inputstream = resource.m_6679_();

                  try {
                     OggAudioStream oggaudiostream = new OggAudioStream(inputstream);

                     try {
                        ByteBuffer bytebuffer = oggaudiostream.m_83764_();
                        soundbuffer = new SoundBuffer(bytebuffer, oggaudiostream.m_6206_());
                     } catch (Throwable throwable3) {
                        try {
                           oggaudiostream.close();
                        } catch (Throwable throwable2) {
                           throwable3.addSuppressed(throwable2);
                        }

                        throw throwable3;
                     }

                     oggaudiostream.close();
                  } catch (Throwable throwable4) {
                     if (inputstream != null) {
                        try {
                           inputstream.close();
                        } catch (Throwable throwable1) {
                           throwable4.addSuppressed(throwable1);
                        }
                     }

                     throw throwable4;
                  }

                  if (inputstream != null) {
                     inputstream.close();
                  }
               } catch (Throwable throwable5) {
                  if (resource != null) {
                     try {
                        resource.close();
                     } catch (Throwable throwable) {
                        throwable5.addSuppressed(throwable);
                     }
                  }

                  throw throwable5;
               }

               if (resource != null) {
                  resource.close();
               }

               return soundbuffer;
            } catch (IOException ioexception) {
               throw new CompletionException(ioexception);
            }
         }, Util.m_137578_());
      });
   }

   public CompletableFuture<AudioStream> m_120204_(ResourceLocation p_120205_, boolean p_120206_) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Resource resource = this.f_120189_.m_142591_(p_120205_);
            InputStream inputstream = resource.m_6679_();
            return (AudioStream)(p_120206_ ? new LoopingAudioStream(OggAudioStream::new, inputstream) : new OggAudioStream(inputstream));
         } catch (IOException ioexception) {
            throw new CompletionException(ioexception);
         }
      }, Util.m_137578_());
   }

   public void m_120193_() {
      this.f_120190_.values().forEach((p_120201_) -> {
         p_120201_.thenAccept(SoundBuffer::m_83801_);
      });
      this.f_120190_.clear();
   }

   public CompletableFuture<?> m_120198_(Collection<Sound> p_120199_) {
      return CompletableFuture.allOf(p_120199_.stream().map((p_120197_) -> {
         return this.m_120202_(p_120197_.m_119790_());
      }).toArray((p_120195_) -> {
         return new CompletableFuture[p_120195_];
      }));
   }
}