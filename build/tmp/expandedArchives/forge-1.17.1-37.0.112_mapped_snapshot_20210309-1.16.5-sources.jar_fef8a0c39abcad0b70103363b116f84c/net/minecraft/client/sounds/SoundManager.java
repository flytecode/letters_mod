package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.resources.sounds.SoundEventRegistrationSerializer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SoundManager extends SimplePreparableReloadListener<SoundManager.Preparations> {
   public static final Sound f_120344_ = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16);
   static final Logger f_120345_ = LogManager.getLogger();
   private static final String f_174997_ = "sounds.json";
   private static final Gson f_120346_ = (new GsonBuilder()).registerTypeHierarchyAdapter(Component.class, new Component.Serializer()).registerTypeAdapter(SoundEventRegistration.class, new SoundEventRegistrationSerializer()).create();
   private static final TypeToken<Map<String, SoundEventRegistration>> f_120347_ = new TypeToken<Map<String, SoundEventRegistration>>() {
   };
   private final Map<ResourceLocation, WeighedSoundEvents> f_120348_ = Maps.newHashMap();
   private final SoundEngine f_120349_;

   public SoundManager(ResourceManager p_120352_, Options p_120353_) {
      this.f_120349_ = new SoundEngine(this, p_120353_, p_120352_);
   }

   protected SoundManager.Preparations m_5944_(ResourceManager p_120356_, ProfilerFiller p_120357_) {
      SoundManager.Preparations soundmanager$preparations = new SoundManager.Preparations();
      p_120357_.m_7242_();

      for(String s : p_120356_.m_7187_()) {
         p_120357_.m_6180_(s);

         try {
            for(Resource resource : p_120356_.m_7396_(new ResourceLocation(s, "sounds.json"))) {
               p_120357_.m_6180_(resource.m_7816_());

               try {
                  InputStream inputstream = resource.m_6679_();

                  try {
                     Reader reader = new InputStreamReader(inputstream, StandardCharsets.UTF_8);

                     try {
                        p_120357_.m_6180_("parse");
                        Map<String, SoundEventRegistration> map = GsonHelper.m_13767_(f_120346_, reader, f_120347_);
                        p_120357_.m_6182_("register");

                        for(Entry<String, SoundEventRegistration> entry : map.entrySet()) {
                           soundmanager$preparations.m_120425_(new ResourceLocation(s, entry.getKey()), entry.getValue(), p_120356_);
                        }

                        p_120357_.m_7238_();
                     } catch (Throwable throwable2) {
                        try {
                           reader.close();
                        } catch (Throwable throwable1) {
                           throwable2.addSuppressed(throwable1);
                        }

                        throw throwable2;
                     }

                     reader.close();
                  } catch (Throwable throwable3) {
                     if (inputstream != null) {
                        try {
                           inputstream.close();
                        } catch (Throwable throwable) {
                           throwable3.addSuppressed(throwable);
                        }
                     }

                     throw throwable3;
                  }

                  if (inputstream != null) {
                     inputstream.close();
                  }
               } catch (RuntimeException runtimeexception) {
                  f_120345_.warn("Invalid {} in resourcepack: '{}'", "sounds.json", resource.m_7816_(), runtimeexception);
               }

               p_120357_.m_7238_();
            }
         } catch (IOException ioexception) {
         }

         p_120357_.m_7238_();
      }

      p_120357_.m_7241_();
      return soundmanager$preparations;
   }

   protected void m_5787_(SoundManager.Preparations p_120377_, ResourceManager p_120378_, ProfilerFiller p_120379_) {
      p_120377_.m_120422_(this.f_120348_, this.f_120349_);
      if (SharedConstants.f_136183_) {
         for(ResourceLocation resourcelocation : this.f_120348_.keySet()) {
            WeighedSoundEvents weighedsoundevents = this.f_120348_.get(resourcelocation);
            if (weighedsoundevents.m_120453_() instanceof TranslatableComponent) {
               String s = ((TranslatableComponent)weighedsoundevents.m_120453_()).m_131328_();
               if (!I18n.m_118936_(s) && Registry.f_122821_.m_7804_(resourcelocation)) {
                  f_120345_.error("Missing subtitle {} for sound event: {}", s, resourcelocation);
               }
            }
         }
      }

      if (f_120345_.isDebugEnabled()) {
         for(ResourceLocation resourcelocation1 : this.f_120348_.keySet()) {
            if (!Registry.f_122821_.m_7804_(resourcelocation1)) {
               f_120345_.debug("Not having sound event for: {}", (Object)resourcelocation1);
            }
         }
      }

      this.f_120349_.m_120239_();
   }

   static boolean m_120395_(Sound p_120396_, ResourceLocation p_120397_, ResourceManager p_120398_) {
      ResourceLocation resourcelocation = p_120396_.m_119790_();
      if (!p_120398_.m_7165_(resourcelocation)) {
         f_120345_.warn("File {} does not exist, cannot add it to event {}", resourcelocation, p_120397_);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   public WeighedSoundEvents m_120384_(ResourceLocation p_120385_) {
      return this.f_120348_.get(p_120385_);
   }

   public Collection<ResourceLocation> m_120354_() {
      return this.f_120348_.keySet();
   }

   public void m_120372_(TickableSoundInstance p_120373_) {
      this.f_120349_.m_120282_(p_120373_);
   }

   public void m_120367_(SoundInstance p_120368_) {
      this.f_120349_.m_120312_(p_120368_);
   }

   public void m_120369_(SoundInstance p_120370_, int p_120371_) {
      this.f_120349_.m_120276_(p_120370_, p_120371_);
   }

   public void m_120361_(Camera p_120362_) {
      this.f_120349_.m_120270_(p_120362_);
   }

   public void m_120391_() {
      this.f_120349_.m_120314_();
   }

   public void m_120405_() {
      this.f_120349_.m_120311_();
   }

   public void m_120406_() {
      this.f_120349_.m_120304_();
   }

   public void m_120389_(boolean p_120390_) {
      this.f_120349_.m_120302_(p_120390_);
   }

   public void m_120407_() {
      this.f_120349_.m_120317_();
   }

   public void m_120358_(SoundSource p_120359_, float p_120360_) {
      if (p_120359_ == SoundSource.MASTER && p_120360_ <= 0.0F) {
         this.m_120405_();
      }

      this.f_120349_.m_120260_(p_120359_, p_120360_);
   }

   public void m_120399_(SoundInstance p_120400_) {
      this.f_120349_.m_120274_(p_120400_);
   }

   public boolean m_120403_(SoundInstance p_120404_) {
      return this.f_120349_.m_120305_(p_120404_);
   }

   public void m_120374_(SoundEventListener p_120375_) {
      this.f_120349_.m_120295_(p_120375_);
   }

   public void m_120401_(SoundEventListener p_120402_) {
      this.f_120349_.m_120307_(p_120402_);
   }

   public void m_120386_(@Nullable ResourceLocation p_120387_, @Nullable SoundSource p_120388_) {
      this.f_120349_.m_120299_(p_120387_, p_120388_);
   }

   public String m_120408_() {
      return this.f_120349_.m_120320_();
   }

   @OnlyIn(Dist.CLIENT)
   protected static class Preparations {
      final Map<ResourceLocation, WeighedSoundEvents> f_120413_ = Maps.newHashMap();

      void m_120425_(ResourceLocation p_120426_, SoundEventRegistration p_120427_, ResourceManager p_120428_) {
         WeighedSoundEvents weighedsoundevents = this.f_120413_.get(p_120426_);
         boolean flag = weighedsoundevents == null;
         if (flag || p_120427_.m_119823_()) {
            if (!flag) {
               SoundManager.f_120345_.debug("Replaced sound event location {}", (Object)p_120426_);
            }

            weighedsoundevents = new WeighedSoundEvents(p_120426_, p_120427_.m_119824_());
            this.f_120413_.put(p_120426_, weighedsoundevents);
         }

         for(final Sound sound : p_120427_.m_119822_()) {
            final ResourceLocation resourcelocation = sound.m_119787_();
            Weighted<Sound> weighted;
            switch(sound.m_119795_()) {
            case FILE:
               if (!SoundManager.m_120395_(sound, p_120426_, p_120428_)) {
                  continue;
               }

               weighted = sound;
               break;
            case SOUND_EVENT:
               weighted = new Weighted<Sound>() {
                  public int m_7789_() {
                     WeighedSoundEvents weighedsoundevents1 = Preparations.this.f_120413_.get(resourcelocation);
                     return weighedsoundevents1 == null ? 0 : weighedsoundevents1.m_7789_();
                  }

                  public Sound m_6776_() {
                     WeighedSoundEvents weighedsoundevents1 = Preparations.this.f_120413_.get(resourcelocation);
                     if (weighedsoundevents1 == null) {
                        return SoundManager.f_120344_;
                     } else {
                        Sound sound1 = weighedsoundevents1.m_6776_();
                        return new Sound(sound1.m_119787_().toString(), sound1.m_119791_() * sound.m_119791_(), sound1.m_119792_() * sound.m_119792_(), sound.m_7789_(), Sound.Type.FILE, sound1.m_119796_() || sound.m_119796_(), sound1.m_119797_(), sound1.m_119798_());
                     }
                  }

                  public void m_8054_(SoundEngine p_120438_) {
                     WeighedSoundEvents weighedsoundevents1 = Preparations.this.f_120413_.get(resourcelocation);
                     if (weighedsoundevents1 != null) {
                        weighedsoundevents1.m_8054_(p_120438_);
                     }
                  }
               };
               break;
            default:
               throw new IllegalStateException("Unknown SoundEventRegistration type: " + sound.m_119795_());
            }

            weighedsoundevents.m_120451_(weighted);
         }

      }

      public void m_120422_(Map<ResourceLocation, WeighedSoundEvents> p_120423_, SoundEngine p_120424_) {
         p_120423_.clear();

         for(Entry<ResourceLocation, WeighedSoundEvents> entry : this.f_120413_.entrySet()) {
            p_120423_.put(entry.getKey(), entry.getValue());
            entry.getValue().m_8054_(p_120424_);
         }

      }
   }
}