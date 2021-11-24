package net.minecraft.resources;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryReadOps<T> extends DelegatingOps<T> {
   static final Logger f_135634_ = LogManager.getLogger();
   private static final String f_179859_ = ".json";
   private final RegistryReadOps.ResourceAccess f_135635_;
   public final RegistryAccess f_179860_;
   private final Map<ResourceKey<? extends Registry<?>>, RegistryReadOps.ReadCache<?>> f_135637_;
   private final RegistryReadOps<JsonElement> f_135638_;

   public static <T> RegistryReadOps<T> m_179866_(DynamicOps<T> p_179867_, ResourceManager p_179868_, RegistryAccess p_179869_) {
      return m_179870_(p_179867_, RegistryReadOps.ResourceAccess.m_135714_(p_179868_), p_179869_);
   }

   public static <T> RegistryReadOps<T> m_179870_(DynamicOps<T> p_179871_, RegistryReadOps.ResourceAccess p_179872_, RegistryAccess p_179873_) {
      RegistryReadOps<T> registryreadops = new RegistryReadOps<>(p_179871_, p_179872_, p_179873_, Maps.newIdentityHashMap());
      RegistryAccess.m_175500_(p_179873_, registryreadops);
      return registryreadops;
   }

   public static <T> RegistryReadOps<T> m_179882_(DynamicOps<T> p_179883_, ResourceManager p_179884_, RegistryAccess p_179885_) {
      return m_179886_(p_179883_, RegistryReadOps.ResourceAccess.m_135714_(p_179884_), p_179885_);
   }

   public static <T> RegistryReadOps<T> m_179886_(DynamicOps<T> p_179887_, RegistryReadOps.ResourceAccess p_179888_, RegistryAccess p_179889_) {
      return new RegistryReadOps<>(p_179887_, p_179888_, p_179889_, Maps.newIdentityHashMap());
   }

   private RegistryReadOps(DynamicOps<T> p_179862_, RegistryReadOps.ResourceAccess p_179863_, RegistryAccess p_179864_, IdentityHashMap<ResourceKey<? extends Registry<?>>, RegistryReadOps.ReadCache<?>> p_179865_) {
      super(p_179862_);
      this.f_135635_ = p_179863_;
      this.f_179860_ = p_179864_;
      this.f_135637_ = p_179865_;
      this.f_135638_ = p_179862_ == JsonOps.INSTANCE ? (RegistryReadOps<JsonElement>)this : new RegistryReadOps<>(JsonOps.INSTANCE, p_179863_, p_179864_, p_179865_);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> m_135677_(T p_135678_, ResourceKey<? extends Registry<E>> p_135679_, Codec<E> p_135680_, boolean p_135681_) {
      Optional<WritableRegistry<E>> optional = this.f_179860_.m_142664_(p_135679_);
      if (!optional.isPresent()) {
         return DataResult.error("Unknown registry: " + p_135679_);
      } else {
         WritableRegistry<E> writableregistry = optional.get();
         DataResult<Pair<ResourceLocation, T>> dataresult = ResourceLocation.f_135803_.decode(this.f_135465_, p_135678_);
         if (!dataresult.result().isPresent()) {
            return !p_135681_ ? DataResult.error("Inline definitions not allowed here") : p_135680_.decode(this, p_135678_).map((p_135647_) -> {
               return p_135647_.mapFirst((p_179881_) -> {
                  return () -> {
                     return p_179881_;
                  };
               });
            });
         } else {
            Pair<ResourceLocation, T> pair = dataresult.result().get();
            ResourceLocation resourcelocation = pair.getFirst();
            return this.m_135689_(p_135679_, writableregistry, p_135680_, resourcelocation).map((p_135650_) -> {
               return Pair.of(p_135650_, pair.getSecond());
            });
         }
      }
   }

   public <E> DataResult<MappedRegistry<E>> m_135662_(MappedRegistry<E> p_135663_, ResourceKey<? extends Registry<E>> p_135664_, Codec<E> p_135665_) {
      Collection<ResourceLocation> collection = this.f_135635_.m_7115_(p_135664_);
      DataResult<MappedRegistry<E>> dataresult = DataResult.success(p_135663_, Lifecycle.stable());
      String s = p_135664_.m_135782_().m_135815_() + "/";

      for(ResourceLocation resourcelocation : collection) {
         String s1 = resourcelocation.m_135815_();
         if (!s1.endsWith(".json")) {
            f_135634_.warn("Skipping resource {} since it is not a json file", (Object)resourcelocation);
         } else if (!s1.startsWith(s)) {
            f_135634_.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourcelocation);
         } else {
            String s2 = s1.substring(s.length(), s1.length() - ".json".length());
            ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.m_135827_(), s2);
            dataresult = dataresult.flatMap((p_135688_) -> {
               return this.m_135689_(p_135664_, p_135688_, p_135665_, resourcelocation1).map((p_179876_) -> {
                  return p_135688_;
               });
            });
         }
      }

      return dataresult.setPartial(p_135663_);
   }

   private <E> DataResult<Supplier<E>> m_135689_(ResourceKey<? extends Registry<E>> p_135690_, final WritableRegistry<E> p_135691_, Codec<E> p_135692_, ResourceLocation p_135693_) {
      final ResourceKey<E> resourcekey = ResourceKey.m_135785_(p_135690_, p_135693_);
      RegistryReadOps.ReadCache<E> readcache = this.m_135699_(p_135690_);
      DataResult<Supplier<E>> dataresult = readcache.f_135708_.get(resourcekey);
      if (dataresult != null) {
         return dataresult;
      } else {
         Supplier<E> supplier = Suppliers.memoize(() -> {
            E e = p_135691_.m_6246_(resourcekey);
            if (e == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + resourcekey);
            } else {
               return e;
            }
         });
         readcache.f_135708_.put(resourcekey, DataResult.success(supplier));
         Optional<DataResult<Pair<E, OptionalInt>>> optional = this.f_135635_.m_142701_(this.f_135638_, p_135690_, resourcekey, p_135692_);
         DataResult<Supplier<E>> dataresult1;
         if (!optional.isPresent()) {
            dataresult1 = DataResult.success(new Supplier<E>() {
               public E get() {
                  return p_135691_.m_6246_(resourcekey);
               }

               public String toString() {
                  return resourcekey.toString();
               }
            }, Lifecycle.stable());
         } else {
            DataResult<Pair<E, OptionalInt>> dataresult2 = optional.get();
            Optional<Pair<E, OptionalInt>> optional1 = dataresult2.result();
            if (optional1.isPresent()) {
               Pair<E, OptionalInt> pair = optional1.get();
               p_135691_.m_7794_(pair.getSecond(), resourcekey, pair.getFirst(), dataresult2.lifecycle());
            }

            dataresult1 = dataresult2.map((p_135674_) -> {
               return () -> {
                  return p_135691_.m_6246_(resourcekey);
               };
            });
         }

         readcache.f_135708_.put(resourcekey, dataresult1);
         return dataresult1;
      }
   }

   private <E> RegistryReadOps.ReadCache<E> m_135699_(ResourceKey<? extends Registry<E>> p_135700_) {
      return (RegistryReadOps.ReadCache<E>)this.f_135637_.computeIfAbsent(p_135700_, (p_135707_) -> {
         return new RegistryReadOps.ReadCache<E>();
      });
   }

   protected <E> DataResult<Registry<E>> m_135682_(ResourceKey<? extends Registry<E>> p_135683_) {
      return this.f_179860_.m_142664_(p_135683_).map((p_135667_) -> {
         return DataResult.<Registry<E>>success(p_135667_, p_135667_.m_7837_());
      }).orElseGet(() -> {
         return DataResult.error("Unknown registry: " + p_135683_);
      });
   }

   static final class ReadCache<E> {
      final Map<ResourceKey<E>, DataResult<Supplier<E>>> f_135708_ = Maps.newIdentityHashMap();
   }

   public interface ResourceAccess {
      Collection<ResourceLocation> m_7115_(ResourceKey<? extends Registry<?>> p_135720_);

      <E> Optional<DataResult<Pair<E, OptionalInt>>> m_142701_(DynamicOps<JsonElement> p_179892_, ResourceKey<? extends Registry<E>> p_179893_, ResourceKey<E> p_179894_, Decoder<E> p_179895_);

      static RegistryReadOps.ResourceAccess m_135714_(final ResourceManager p_135715_) {
         return new RegistryReadOps.ResourceAccess() {
            public Collection<ResourceLocation> m_7115_(ResourceKey<? extends Registry<?>> p_135734_) {
               return p_135715_.m_6540_(p_135734_.m_135782_().m_135815_(), (p_135732_) -> {
                  return p_135732_.endsWith(".json");
               });
            }

            public <E> Optional<DataResult<Pair<E, OptionalInt>>> m_142701_(DynamicOps<JsonElement> p_179897_, ResourceKey<? extends Registry<E>> p_179898_, ResourceKey<E> p_179899_, Decoder<E> p_179900_) {
               ResourceLocation resourcelocation = p_179899_.m_135782_();
               ResourceLocation resourcelocation1 = new ResourceLocation(resourcelocation.m_135827_(), p_179898_.m_135782_().m_135815_() + "/" + resourcelocation.m_135815_() + ".json");
               if (!p_135715_.m_7165_(resourcelocation1)) {
                  return Optional.empty();
               } else {
                  try {
                     Resource resource = p_135715_.m_142591_(resourcelocation1);

                     Optional optional;
                     try {
                        Reader reader = new InputStreamReader(resource.m_6679_(), StandardCharsets.UTF_8);

                        try {
                           JsonParser jsonparser = new JsonParser();
                           JsonElement jsonelement = jsonparser.parse(reader);
                           if (jsonelement != null) jsonelement.getAsJsonObject().addProperty("forge:registry_name", p_179899_.m_135782_().toString());
                           optional = Optional.of(p_179900_.parse(p_179897_, jsonelement).map((p_135730_) -> {
                              return Pair.of(p_135730_, OptionalInt.empty());
                           }));
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
                        if (resource != null) {
                           try {
                              resource.close();
                           } catch (Throwable throwable) {
                              throwable3.addSuppressed(throwable);
                           }
                        }

                        throw throwable3;
                     }

                     if (resource != null) {
                        resource.close();
                     }

                     return optional;
                  } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
                     return Optional.of(DataResult.error("Failed to parse " + resourcelocation1 + " file: " + ioexception.getMessage()));
                  }
               }
            }

            public String toString() {
               return "ResourceAccess[" + p_135715_ + "]";
            }
         };
      }

      public static final class MemoryMap implements RegistryReadOps.ResourceAccess {
         private final Map<ResourceKey<?>, JsonElement> f_135736_ = Maps.newIdentityHashMap();
         private final Object2IntMap<ResourceKey<?>> f_135737_ = new Object2IntOpenCustomHashMap<>(Util.m_137583_());
         private final Map<ResourceKey<?>, Lifecycle> f_135738_ = Maps.newIdentityHashMap();

         public <E> void m_135745_(RegistryAccess.RegistryHolder p_135746_, ResourceKey<E> p_135747_, Encoder<E> p_135748_, int p_135749_, E p_135750_, Lifecycle p_135751_) {
            DataResult<JsonElement> dataresult = p_135748_.encodeStart(RegistryWriteOps.m_135767_(JsonOps.INSTANCE, p_135746_), p_135750_);
            Optional<PartialResult<JsonElement>> optional = dataresult.error();
            if (optional.isPresent()) {
               RegistryReadOps.f_135634_.error("Error adding element: {}", (Object)optional.get().message());
            } else {
               this.f_135736_.put(p_135747_, dataresult.result().get());
               this.f_135737_.put(p_135747_, p_135749_);
               this.f_135738_.put(p_135747_, p_135751_);
            }
         }

         public Collection<ResourceLocation> m_7115_(ResourceKey<? extends Registry<?>> p_135753_) {
            return this.f_135736_.keySet().stream().filter((p_135762_) -> {
               return p_135762_.m_135783_(p_135753_);
            }).map((p_135759_) -> {
               return new ResourceLocation(p_135759_.m_135782_().m_135827_(), p_135753_.m_135782_().m_135815_() + "/" + p_135759_.m_135782_().m_135815_() + ".json");
            }).collect(Collectors.toList());
         }

         public <E> Optional<DataResult<Pair<E, OptionalInt>>> m_142701_(DynamicOps<JsonElement> p_179902_, ResourceKey<? extends Registry<E>> p_179903_, ResourceKey<E> p_179904_, Decoder<E> p_179905_) {
            JsonElement jsonelement = this.f_135736_.get(p_179904_);
            if (jsonelement != null) jsonelement.getAsJsonObject().addProperty("forge:registry_name", p_179904_.m_135782_().toString());
            return jsonelement == null ? Optional.of(DataResult.error("Unknown element: " + p_179904_)) : Optional.of(p_179905_.parse(p_179902_, jsonelement).setLifecycle(this.f_135738_.get(p_179904_)).map((p_135756_) -> {
               return Pair.of(p_135756_, OptionalInt.of(this.f_135737_.getInt(p_179904_)));
            }));
         }
      }
   }
}
