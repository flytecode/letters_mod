package net.minecraft.tags;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagContainer {
   static final Logger f_144432_ = LogManager.getLogger();
   public static final TagContainer f_13420_ = new TagContainer(ImmutableMap.of());
   public final Map<ResourceKey<? extends Registry<?>>, TagCollection<?>> f_144433_;

   public TagContainer(Map<ResourceKey<? extends Registry<?>>, TagCollection<?>> p_144435_) {
      this.f_144433_ = p_144435_;
   }

   @Nullable
   private <T> TagCollection<T> m_144471_(ResourceKey<? extends Registry<T>> p_144472_) {
      return (TagCollection<T>)this.f_144433_.get(p_144472_);
   }

   public <T> TagCollection<T> m_144452_(ResourceKey<? extends Registry<T>> p_144453_) {
      return (TagCollection<T>)this.f_144433_.getOrDefault(p_144453_, TagCollection.<T>m_13410_());
   }

   public <T, E extends Exception> Tag<T> m_144458_(ResourceKey<? extends Registry<T>> p_144459_, ResourceLocation p_144460_, Function<ResourceLocation, E> p_144461_) throws E {
      TagCollection<T> tagcollection = this.m_144471_(p_144459_);
      if (tagcollection == null) {
         throw p_144461_.apply(p_144460_);
      } else {
         Tag<T> tag = tagcollection.m_13404_(p_144460_);
         if (tag == null) {
            throw p_144461_.apply(p_144460_);
         } else {
            return tag;
         }
      }
   }

   public <T, E extends Exception> ResourceLocation m_144454_(ResourceKey<? extends Registry<T>> p_144455_, Tag<T> p_144456_, Supplier<E> p_144457_) throws E {
      TagCollection<T> tagcollection = this.m_144471_(p_144455_);
      if (tagcollection == null) {
         throw p_144457_.get();
      } else {
         ResourceLocation resourcelocation = tagcollection.m_7473_(p_144456_);
         if (resourcelocation == null) {
            throw p_144457_.get();
         } else {
            return resourcelocation;
         }
      }
   }

   public void m_144436_(TagContainer.CollectionConsumer p_144437_) {
      this.f_144433_.forEach((p_144464_, p_144465_) -> {
         m_144438_(p_144437_, p_144464_, p_144465_);
      });
   }

   private static <T> void m_144438_(TagContainer.CollectionConsumer p_144439_, ResourceKey<? extends Registry<?>> p_144440_, TagCollection<?> p_144441_) {
      p_144439_.m_142574_((ResourceKey<? extends Registry<T>>)p_144440_, (TagCollection<T>)p_144441_);
   }

   public void m_13431_() {
      StaticTags.m_13269_(this);
      Blocks.m_50758_();
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.TagsUpdatedEvent(this));
   }

   public Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> m_144442_(final RegistryAccess p_144443_) {
      final Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> map = Maps.newHashMap();
      this.m_144436_(new TagContainer.CollectionConsumer() {
         public <T> void m_142574_(ResourceKey<? extends Registry<T>> p_144481_, TagCollection<T> p_144482_) {
            Optional<? extends Registry<T>> optional = p_144443_.m_6632_(p_144481_);
            optional = net.minecraftforge.common.ForgeTagHandler.getWrapperRegistry(p_144481_, optional);
            if (optional.isPresent()) {
               map.put(p_144481_, p_144482_.m_144411_(optional.get()));
            } else {
               TagContainer.f_144432_.error("Unknown registry {}", (Object)p_144481_);
            }

         }
      });
      return map;
   }

   public static TagContainer m_144449_(RegistryAccess p_144450_, Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> p_144451_) {
      TagContainer.Builder tagcontainer$builder = new TagContainer.Builder();
      p_144451_.forEach((p_144469_, p_144470_) -> {
         m_144444_(p_144450_, tagcontainer$builder, p_144469_, p_144470_);
      });
      return tagcontainer$builder.m_144485_();
   }

   private static <T> void m_144444_(RegistryAccess p_144445_, TagContainer.Builder p_144446_, ResourceKey<? extends Registry<? extends T>> p_144447_, TagCollection.NetworkPayload p_144448_) {
      Optional<? extends Registry<? extends T>> optional = p_144445_.m_6632_(p_144447_);
      if (optional.isPresent()) {
         p_144446_.m_144486_(p_144447_, TagCollection.m_144408_(p_144448_, optional.get()));
      } else {
         f_144432_.error("Unknown registry {}", (Object)p_144447_);
      }

   }

   public static class Builder {
      private final ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, TagCollection<?>> f_144483_ = ImmutableMap.builder();

      public <T> TagContainer.Builder m_144486_(ResourceKey<? extends Registry<? extends T>> p_144487_, TagCollection<T> p_144488_) {
         this.f_144483_.put(p_144487_, p_144488_);
         return this;
      }

      public TagContainer m_144485_() {
         return new TagContainer(this.f_144483_.build());
      }
   }

   @FunctionalInterface
   interface CollectionConsumer {
      <T> void m_142574_(ResourceKey<? extends Registry<T>> p_144489_, TagCollection<T> p_144490_);
   }
}
