package net.minecraft.client.sounds;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.Listener;
import com.mojang.math.Vector3f;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@OnlyIn(Dist.CLIENT)
public class SoundEngine {
   private static final Marker f_120214_ = MarkerManager.getMarker("SOUNDS");
   private static final Logger f_120215_ = LogManager.getLogger();
   private static final float f_174983_ = 0.5F;
   private static final float f_174984_ = 2.0F;
   private static final float f_174985_ = 0.0F;
   private static final float f_174986_ = 1.0F;
   private static final int f_174987_ = 20;
   private static final Set<ResourceLocation> f_120216_ = Sets.newHashSet();
   public static final String f_174982_ = "FOR THE DEBUG!";
   public final SoundManager f_120217_;
   private final Options f_120218_;
   private boolean f_120219_;
   private final Library f_120220_ = new Library();
   private final Listener f_120221_ = this.f_120220_.m_83700_();
   private final SoundBufferLibrary f_120222_;
   private final SoundEngineExecutor f_120223_ = new SoundEngineExecutor();
   private final ChannelAccess f_120224_ = new ChannelAccess(this.f_120220_, this.f_120223_);
   private int f_120225_;
   private final Map<SoundInstance, ChannelAccess.ChannelHandle> f_120226_ = Maps.newHashMap();
   private final Multimap<SoundSource, SoundInstance> f_120227_ = HashMultimap.create();
   private final List<TickableSoundInstance> f_120228_ = Lists.newArrayList();
   private final Map<SoundInstance, Integer> f_120229_ = Maps.newHashMap();
   private final Map<SoundInstance, Integer> f_120230_ = Maps.newHashMap();
   private final List<SoundEventListener> f_120231_ = Lists.newArrayList();
   private final List<TickableSoundInstance> f_120232_ = Lists.newArrayList();
   private final List<Sound> f_120233_ = Lists.newArrayList();

   public SoundEngine(SoundManager p_120236_, Options p_120237_, ResourceManager p_120238_) {
      this.f_120217_ = p_120236_;
      this.f_120218_ = p_120237_;
      this.f_120222_ = new SoundBufferLibrary(p_120238_);
      net.minecraftforge.fml.ModLoader.get().postEvent(new net.minecraftforge.client.event.sound.SoundLoadEvent(this));
   }

   public void m_120239_() {
      f_120216_.clear();

      for(SoundEvent soundevent : Registry.f_122821_) {
         ResourceLocation resourcelocation = soundevent.m_11660_();
         if (this.f_120217_.m_120384_(resourcelocation) == null) {
            f_120215_.warn("Missing sound for event: {}", (Object)Registry.f_122821_.m_7981_(soundevent));
            f_120216_.add(resourcelocation);
         }
      }

      this.m_120304_();
      this.m_120323_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.SoundLoadEvent(this));
   }

   private synchronized void m_120323_() {
      if (!this.f_120219_) {
         try {
            this.f_120220_.m_83694_();
            this.f_120221_.m_83745_();
            this.f_120221_.m_83737_(this.f_120218_.m_92147_(SoundSource.MASTER));
            this.f_120222_.m_120198_(this.f_120233_).thenRun(this.f_120233_::clear);
            this.f_120219_ = true;
            f_120215_.info(f_120214_, "Sound engine started");
         } catch (RuntimeException runtimeexception) {
            f_120215_.error(f_120214_, "Error starting SoundSystem. Turning off sounds & music", (Throwable)runtimeexception);
         }

      }
   }

   private float m_120258_(@Nullable SoundSource p_120259_) {
      return p_120259_ != null && p_120259_ != SoundSource.MASTER ? this.f_120218_.m_92147_(p_120259_) : 1.0F;
   }

   public void m_120260_(SoundSource p_120261_, float p_120262_) {
      if (this.f_120219_) {
         if (p_120261_ == SoundSource.MASTER) {
            this.f_120221_.m_83737_(p_120262_);
         } else {
            this.f_120226_.forEach((p_120280_, p_120281_) -> {
               float f = this.m_120327_(p_120280_);
               p_120281_.m_120154_((p_174990_) -> {
                  if (f <= 0.0F) {
                     p_174990_.m_83679_();
                  } else {
                     p_174990_.m_83666_(f);
                  }

               });
            });
         }
      }
   }

   public void m_120304_() {
      if (this.f_120219_) {
         this.m_120311_();
         this.f_120222_.m_120193_();
         this.f_120220_.m_83699_();
         this.f_120219_ = false;
      }

   }

   public void m_120274_(SoundInstance p_120275_) {
      if (this.f_120219_) {
         ChannelAccess.ChannelHandle channelaccess$channelhandle = this.f_120226_.get(p_120275_);
         if (channelaccess$channelhandle != null) {
            channelaccess$channelhandle.m_120154_(Channel::m_83679_);
         }
      }

   }

   public void m_120311_() {
      if (this.f_120219_) {
         this.f_120223_.m_120332_();
         this.f_120226_.values().forEach((p_120288_) -> {
            p_120288_.m_120154_(Channel::m_83679_);
         });
         this.f_120226_.clear();
         this.f_120224_.m_120139_();
         this.f_120229_.clear();
         this.f_120228_.clear();
         this.f_120227_.clear();
         this.f_120230_.clear();
         this.f_120232_.clear();
      }

   }

   public void m_120295_(SoundEventListener p_120296_) {
      this.f_120231_.add(p_120296_);
   }

   public void m_120307_(SoundEventListener p_120308_) {
      this.f_120231_.remove(p_120308_);
   }

   public void m_120302_(boolean p_120303_) {
      if (!p_120303_) {
         this.m_120326_();
      }

      this.f_120224_.m_120127_();
   }

   private void m_120326_() {
      ++this.f_120225_;
      this.f_120232_.stream().filter(SoundInstance::m_7767_).forEach(this::m_120312_);
      this.f_120232_.clear();

      for(TickableSoundInstance tickablesoundinstance : this.f_120228_) {
         if (!tickablesoundinstance.m_7767_()) {
            this.m_120274_(tickablesoundinstance);
         }

         tickablesoundinstance.m_7788_();
         if (tickablesoundinstance.m_7801_()) {
            this.m_120274_(tickablesoundinstance);
         } else {
            float f = this.m_120327_(tickablesoundinstance);
            float f1 = this.m_120324_(tickablesoundinstance);
            Vec3 vec3 = new Vec3(tickablesoundinstance.m_7772_(), tickablesoundinstance.m_7780_(), tickablesoundinstance.m_7778_());
            ChannelAccess.ChannelHandle channelaccess$channelhandle = this.f_120226_.get(tickablesoundinstance);
            if (channelaccess$channelhandle != null) {
               channelaccess$channelhandle.m_120154_((p_120244_) -> {
                  p_120244_.m_83666_(f);
                  p_120244_.m_83650_(f1);
                  p_120244_.m_83654_(vec3);
               });
            }
         }
      }

      Iterator<Entry<SoundInstance, ChannelAccess.ChannelHandle>> iterator = this.f_120226_.entrySet().iterator();

      while(iterator.hasNext()) {
         Entry<SoundInstance, ChannelAccess.ChannelHandle> entry = iterator.next();
         ChannelAccess.ChannelHandle channelaccess$channelhandle1 = entry.getValue();
         SoundInstance soundinstance = entry.getKey();
         float f2 = this.f_120218_.m_92147_(soundinstance.m_8070_());
         if (f2 <= 0.0F) {
            channelaccess$channelhandle1.m_120154_(Channel::m_83679_);
            iterator.remove();
         } else if (channelaccess$channelhandle1.m_120151_()) {
            int i = this.f_120230_.get(soundinstance);
            if (i <= this.f_120225_) {
               if (m_120318_(soundinstance)) {
                  this.f_120229_.put(soundinstance, this.f_120225_ + soundinstance.m_7766_());
               }

               iterator.remove();
               f_120215_.debug(f_120214_, "Removed channel {} because it's not playing anymore", (Object)channelaccess$channelhandle1);
               this.f_120230_.remove(soundinstance);

               try {
                  this.f_120227_.remove(soundinstance.m_8070_(), soundinstance);
               } catch (RuntimeException runtimeexception) {
               }

               if (soundinstance instanceof TickableSoundInstance) {
                  this.f_120228_.remove(soundinstance);
               }
            }
         }
      }

      Iterator<Entry<SoundInstance, Integer>> iterator1 = this.f_120229_.entrySet().iterator();

      while(iterator1.hasNext()) {
         Entry<SoundInstance, Integer> entry1 = iterator1.next();
         if (this.f_120225_ >= entry1.getValue()) {
            SoundInstance soundinstance1 = entry1.getKey();
            if (soundinstance1 instanceof TickableSoundInstance) {
               ((TickableSoundInstance)soundinstance1).m_7788_();
            }

            this.m_120312_(soundinstance1);
            iterator1.remove();
         }
      }

   }

   private static boolean m_120315_(SoundInstance p_120316_) {
      return p_120316_.m_7766_() > 0;
   }

   private static boolean m_120318_(SoundInstance p_120319_) {
      return p_120319_.m_7775_() && m_120315_(p_120319_);
   }

   private static boolean m_120321_(SoundInstance p_120322_) {
      return p_120322_.m_7775_() && !m_120315_(p_120322_);
   }

   public boolean m_120305_(SoundInstance p_120306_) {
      if (!this.f_120219_) {
         return false;
      } else {
         return this.f_120230_.containsKey(p_120306_) && this.f_120230_.get(p_120306_) <= this.f_120225_ ? true : this.f_120226_.containsKey(p_120306_);
      }
   }

   public void m_120312_(SoundInstance p_120313_) {
      if (this.f_120219_) {
         p_120313_ = net.minecraftforge.client.ForgeHooksClient.playSound(this, p_120313_);
         if (p_120313_ != null && p_120313_.m_7767_()) {
            WeighedSoundEvents weighedsoundevents = p_120313_.m_6775_(this.f_120217_);
            ResourceLocation resourcelocation = p_120313_.m_7904_();
            if (weighedsoundevents == null) {
               if (f_120216_.add(resourcelocation)) {
                  f_120215_.warn(f_120214_, "Unable to play unknown soundEvent: {}", (Object)resourcelocation);
               }

            } else {
               Sound sound = p_120313_.m_5891_();
               if (sound == SoundManager.f_120344_) {
                  if (f_120216_.add(resourcelocation)) {
                     f_120215_.warn(f_120214_, "Unable to play empty soundEvent: {}", (Object)resourcelocation);
                  }

               } else {
                  float f = p_120313_.m_7769_();
                  float f1 = Math.max(f, 1.0F) * (float)sound.m_119798_();
                  SoundSource soundsource = p_120313_.m_8070_();
                  float f2 = this.m_120327_(p_120313_);
                  float f3 = this.m_120324_(p_120313_);
                  SoundInstance.Attenuation soundinstance$attenuation = p_120313_.m_7438_();
                  boolean flag = p_120313_.m_7796_();
                  if (f2 == 0.0F && !p_120313_.m_7784_()) {
                     f_120215_.debug(f_120214_, "Skipped playing sound {}, volume was zero.", (Object)sound.m_119787_());
                  } else {
                     Vec3 vec3 = new Vec3(p_120313_.m_7772_(), p_120313_.m_7780_(), p_120313_.m_7778_());
                     if (!this.f_120231_.isEmpty()) {
                        boolean flag1 = flag || soundinstance$attenuation == SoundInstance.Attenuation.NONE || this.f_120221_.m_83736_().m_82557_(vec3) < (double)(f1 * f1);
                        if (flag1) {
                           for(SoundEventListener soundeventlistener : this.f_120231_) {
                              soundeventlistener.m_6985_(p_120313_, weighedsoundevents);
                           }
                        } else {
                           f_120215_.debug(f_120214_, "Did not notify listeners of soundEvent: {}, it is too far away to hear", (Object)resourcelocation);
                        }
                     }

                     if (this.f_120221_.m_83744_() <= 0.0F) {
                        f_120215_.debug(f_120214_, "Skipped playing soundEvent: {}, master volume was zero", (Object)resourcelocation);
                     } else {
                        boolean flag2 = m_120321_(p_120313_);
                        boolean flag3 = sound.m_119796_();
                        CompletableFuture<ChannelAccess.ChannelHandle> completablefuture = this.f_120224_.m_120128_(sound.m_119796_() ? Library.Pool.STREAMING : Library.Pool.STATIC);
                        ChannelAccess.ChannelHandle channelaccess$channelhandle = completablefuture.join();
                        if (channelaccess$channelhandle == null) {
                           f_120215_.warn("Failed to create new sound handle");
                        } else {
                           f_120215_.debug(f_120214_, "Playing sound {} for event {}", sound.m_119787_(), resourcelocation);
                           this.f_120230_.put(p_120313_, this.f_120225_ + 20);
                           this.f_120226_.put(p_120313_, channelaccess$channelhandle);
                           this.f_120227_.put(soundsource, p_120313_);
                           channelaccess$channelhandle.m_120154_((p_120254_) -> {
                              p_120254_.m_83650_(f3);
                              p_120254_.m_83666_(f2);
                              if (soundinstance$attenuation == SoundInstance.Attenuation.LINEAR) {
                                 p_120254_.m_83673_(f1);
                              } else {
                                 p_120254_.m_83681_();
                              }

                              p_120254_.m_83663_(flag2 && !flag3);
                              p_120254_.m_83654_(vec3);
                              p_120254_.m_83670_(flag);
                           });
                           final SoundInstance soundinstance = p_120313_;
                           if (!flag3) {
                              this.f_120222_.m_120202_(sound.m_119790_()).thenAccept((p_120291_) -> {
                                 channelaccess$channelhandle.m_120154_((p_174993_) -> {
                                    p_174993_.m_83656_(p_120291_);
                                    p_174993_.m_83672_();
                                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.PlaySoundSourceEvent(this, soundinstance, p_174993_));
                                 });
                              });
                           } else {
                              this.f_120222_.m_120204_(sound.m_119790_(), flag2).thenAccept((p_120294_) -> {
                                 channelaccess$channelhandle.m_120154_((p_174996_) -> {
                                    p_174996_.m_83658_(p_120294_);
                                    p_174996_.m_83672_();
                                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.sound.PlayStreamingSourceEvent(this, soundinstance, p_174996_));
                                 });
                              });
                           }

                           if (p_120313_ instanceof TickableSoundInstance) {
                              this.f_120228_.add((TickableSoundInstance)p_120313_);
                           }

                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void m_120282_(TickableSoundInstance p_120283_) {
      this.f_120232_.add(p_120283_);
   }

   public void m_120272_(Sound p_120273_) {
      this.f_120233_.add(p_120273_);
   }

   private float m_120324_(SoundInstance p_120325_) {
      return Mth.m_14036_(p_120325_.m_7783_(), 0.5F, 2.0F);
   }

   private float m_120327_(SoundInstance p_120328_) {
      return Mth.m_14036_(p_120328_.m_7769_() * this.m_120258_(p_120328_.m_8070_()), 0.0F, 1.0F);
   }

   public void m_120314_() {
      if (this.f_120219_) {
         this.f_120224_.m_120137_((p_120310_) -> {
            p_120310_.forEach(Channel::m_83677_);
         });
      }

   }

   public void m_120317_() {
      if (this.f_120219_) {
         this.f_120224_.m_120137_((p_120298_) -> {
            p_120298_.forEach(Channel::m_83678_);
         });
      }

   }

   public void m_120276_(SoundInstance p_120277_, int p_120278_) {
      this.f_120229_.put(p_120277_, this.f_120225_ + p_120278_);
   }

   public void m_120270_(Camera p_120271_) {
      if (this.f_120219_ && p_120271_.m_90593_()) {
         Vec3 vec3 = p_120271_.m_90583_();
         Vector3f vector3f = p_120271_.m_90596_();
         Vector3f vector3f1 = p_120271_.m_90597_();
         this.f_120223_.execute(() -> {
            this.f_120221_.m_83739_(vec3);
            this.f_120221_.m_83741_(vector3f, vector3f1);
         });
      }
   }

   public void m_120299_(@Nullable ResourceLocation p_120300_, @Nullable SoundSource p_120301_) {
      if (p_120301_ != null) {
         for(SoundInstance soundinstance : this.f_120227_.get(p_120301_)) {
            if (p_120300_ == null || soundinstance.m_7904_().equals(p_120300_)) {
               this.m_120274_(soundinstance);
            }
         }
      } else if (p_120300_ == null) {
         this.m_120311_();
      } else {
         for(SoundInstance soundinstance1 : this.f_120226_.keySet()) {
            if (soundinstance1.m_7904_().equals(p_120300_)) {
               this.m_120274_(soundinstance1);
            }
         }
      }

   }

   public String m_120320_() {
      return this.f_120220_.m_83701_();
   }
}
