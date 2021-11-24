package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class StaticTagHelper<T> {
   private final ResourceKey<? extends Registry<T>> f_144326_;
   private final String f_144327_;
   private TagCollection<T> f_13227_ = TagCollection.m_13410_();
   private final List<StaticTagHelper.Wrapper<T>> f_13228_ = Lists.newArrayList();
   @Nullable private static java.util.Map<ResourceLocation, List<StaticTagHelper.Wrapper<?>>> toAdd = com.google.common.collect.Maps.newHashMap();

   public StaticTagHelper(ResourceKey<? extends Registry<T>> p_144329_, String p_144330_) {
      this.f_144326_ = p_144329_;
      this.f_144327_ = p_144330_;
   }

   public Tag.Named<T> m_13244_(String p_13245_) {
      return add(new StaticTagHelper.Wrapper<>(new ResourceLocation(p_13245_)));
   }

   public net.minecraftforge.common.Tags.IOptionalNamedTag<T> createOptional(ResourceLocation key, @Nullable Set<java.util.function.Supplier<T>> defaults) {
      return add(new StaticTagHelper.OptionalNamedTag<>(key, defaults));
   }

   /** Call via ForgeTagHandler#makeWrapperTag to avoid any exceptions due to calling this after it is safe to call {@link #bind(String)} */
   public static <T> Tag.Named<T> createDelayedTag(ResourceLocation tagRegistry, ResourceLocation name) {
      return delayedAdd(tagRegistry, new StaticTagHelper.Wrapper<>(name));
   }

   /** Call via ForgeTagHandler#createOptionalTag to avoid any exceptions due to calling this after it is safe to call {@link #createOptional(ResourceLocation, Set)} */
   public static <T> net.minecraftforge.common.Tags.IOptionalNamedTag<T> createDelayedOptional(ResourceLocation tagRegistry, ResourceLocation key, @Nullable Set<java.util.function.Supplier<T>> defaults) {
      return delayedAdd(tagRegistry, new StaticTagHelper.OptionalNamedTag<>(key, defaults));
   }

   private static synchronized <T, R extends StaticTagHelper.Wrapper<T>> R delayedAdd(ResourceLocation tagRegistry, R tag) {
      if (toAdd == null) throw new RuntimeException("Creating delayed tags or optional tags, is only supported before custom tag types have been added.");
      toAdd.computeIfAbsent(tagRegistry, registry -> Lists.newArrayList()).add(tag);
      return tag;
   }

   public static void performDelayedAdd() {
      if (toAdd != null) {
         for (java.util.Map.Entry<ResourceLocation, List<StaticTagHelper.Wrapper<?>>> entry : toAdd.entrySet()) {
            StaticTagHelper<?> tagRegistry = StaticTags.get(entry.getKey());
            if (tagRegistry == null) throw new RuntimeException("A mod attempted to add a delayed tag for a registry that doesn't have custom tag support.");
            for (StaticTagHelper.Wrapper<?> tag : entry.getValue()) {
               tagRegistry.add((StaticTagHelper.Wrapper) tag);
            }
         }
         toAdd = null;
      }
   }

   private <R extends StaticTagHelper.Wrapper<T>> R add(R namedtag) {
      namedtag.m_13260_(f_13227_::m_13404_);
      this.f_13228_.add(namedtag);
      return namedtag;
   }

   public TagCollection<T> reinjectOptionalTags(TagCollection<T> tagCollection) {
      java.util.Map<ResourceLocation, Tag<T>> currentTags = tagCollection.m_5643_();
      java.util.Map<ResourceLocation, Tag<T>> missingOptionals = this.f_13228_.stream().filter(e -> e instanceof OptionalNamedTag && !currentTags.containsKey(e.m_6979_())).collect(Collectors.toMap(Wrapper::m_6979_, namedTag -> {
         OptionalNamedTag<T> optionalNamedTag = (OptionalNamedTag<T>) namedTag;
         optionalNamedTag.defaulted = true;
         return optionalNamedTag.resolveDefaulted();
      }));
      if (!missingOptionals.isEmpty()) {
         missingOptionals.putAll(currentTags);
         return TagCollection.m_13396_(missingOptionals);
      }
      return tagCollection;
   }

   public void m_13232_() {
      this.f_13227_ = TagCollection.m_13410_();
      Tag<T> tag = SetTag.m_13216_();
      this.f_13228_.forEach((p_13235_) -> {
         p_13235_.m_13260_((p_144335_) -> {
            return tag;
         });
      });
   }

   public void m_13242_(TagContainer p_13243_) {
      TagCollection<T> tagcollection = p_13243_.m_144452_(this.f_144326_);
      this.f_13227_ = tagcollection;
      this.f_13228_.forEach((p_13241_) -> {
         p_13241_.m_13260_(tagcollection::m_13404_);
      });
   }

   public TagCollection<T> m_13246_() {
      return this.f_13227_;
   }

   public Set<ResourceLocation> m_13247_(TagContainer p_13248_) {
      TagCollection<T> tagcollection = p_13248_.m_144452_(this.f_144326_);
      Set<ResourceLocation> set = this.f_13228_.stream().filter(e -> !(e instanceof OptionalNamedTag)).map(StaticTagHelper.Wrapper::m_6979_).collect(Collectors.toSet());
      ImmutableSet<ResourceLocation> immutableset = ImmutableSet.copyOf(tagcollection.m_13406_());
      return Sets.difference(set, immutableset);
   }

   public ResourceKey<? extends Registry<T>> m_144338_() {
      return this.f_144326_;
   }

   public String m_144339_() {
      return this.f_144327_;
   }

   protected void m_144336_(TagContainer.Builder p_144337_) {
      p_144337_.m_144486_(this.f_144326_, TagCollection.m_13396_(this.f_13228_.stream().distinct().collect(Collectors.toMap(Tag.Named::m_6979_, (p_144332_) -> {
         return p_144332_;
      }))));
   }

   static class Wrapper<T> implements Tag.Named<T> {
      @Nullable
      protected Tag<T> f_13251_;
      protected final ResourceLocation f_13250_;

      Wrapper(ResourceLocation p_13253_) {
         this.f_13250_ = p_13253_;
      }

      public ResourceLocation m_6979_() {
         return this.f_13250_;
      }

      private Tag<T> m_13263_() {
         if (this.f_13251_ == null) {
            throw new IllegalStateException("Tag " + this.f_13250_ + " used before it was bound");
         } else {
            return this.f_13251_;
         }
      }

      void m_13260_(Function<ResourceLocation, Tag<T>> p_13261_) {
         this.f_13251_ = p_13261_.apply(this.f_13250_);
      }

      public boolean m_8110_(T p_13259_) {
         return this.m_13263_().m_8110_(p_13259_);
      }

      public List<T> m_6497_() {
         return this.m_13263_().m_6497_();
      }

      @Override
      public String toString() {
          return "NamedTag[" + m_6979_() + ']';
      }
      @Override public boolean equals(Object o) { return (o == this) || (o instanceof Tag.Named && java.util.Objects.equals(this.m_6979_(), ((Tag.Named<T>)o).m_6979_())); }
      @Override public int hashCode() { return m_6979_().hashCode(); }
   }

   private static class OptionalNamedTag<T> extends Wrapper<T> implements net.minecraftforge.common.Tags.IOptionalNamedTag<T> {
      @Nullable
      private final Set<java.util.function.Supplier<T>> defaults;
      private boolean defaulted = false;

      private OptionalNamedTag(ResourceLocation name, @Nullable Set<java.util.function.Supplier<T>> defaults) {
         super(name);
         this.defaults = defaults;
      }

      @Override
      public boolean isDefaulted() {
         return defaulted;
      }

      SetTag<T> resolveDefaulted() {
         if (defaults == null || defaults.isEmpty()) {
            return SetTag.m_13216_();
         }
         return SetTag.m_13222_(ImmutableSet.copyOf(defaults.stream().map(java.util.function.Supplier::get).collect(Collectors.toSet())));
      }

      @Override
      public String toString() {
          return "OptionalNamedTag[" + m_6979_() + ']';
      }
   }
}
