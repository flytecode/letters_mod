package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public interface Tag<T> {
   static <T> Codec<Tag<T>> m_13290_(Supplier<TagCollection<T>> p_13291_) {
      return ResourceLocation.f_135803_.flatXmap((p_13297_) -> {
         return Optional.ofNullable(p_13291_.get().m_13404_(p_13297_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_13297_);
         });
      }, (p_13294_) -> {
         return Optional.ofNullable(p_13291_.get().m_7473_(p_13294_)).map(DataResult::success).orElseGet(() -> {
            return DataResult.error("Unknown tag: " + p_13294_);
         });
      });
   }

   boolean m_8110_(T p_13287_);

   List<T> m_6497_();

   default T m_13288_(Random p_13289_) {
      List<T> list = this.m_6497_();
      return list.get(p_13289_.nextInt(list.size()));
   }

   static <T> Tag<T> m_13300_(Set<T> p_13301_) {
      return SetTag.m_13222_(p_13301_);
   }

   public static class Builder implements net.minecraftforge.common.extensions.IForgeRawTagBuilder {
      private final List<Tag.BuilderEntry> removeEntries = Lists.newArrayList(); // FORGE: internal field for tracking "remove" entries
      /** FORGE: Gets a view of this builder's "remove" entries (only used during datagen) **/
      public Stream<Tag.BuilderEntry> getRemoveEntries() { return this.removeEntries.stream(); }
      public Tag.Builder remove(final Tag.BuilderEntry proxy) { // internal forge method for adding remove entries
         this.removeEntries.add(proxy);
         return this;
      }
      private final List<Tag.BuilderEntry> f_13302_ = Lists.newArrayList();
      private boolean replace = false;

      public static Tag.Builder m_13304_() {
         return new Tag.Builder();
      }

      public Tag.Builder m_13305_(Tag.BuilderEntry p_13306_) {
         this.f_13302_.add(p_13306_);
         return this;
      }

      public Tag.Builder m_13307_(Tag.Entry p_13308_, String p_13309_) {
         return this.m_13305_(new Tag.BuilderEntry(p_13308_, p_13309_));
      }

      public Tag.Builder m_13327_(ResourceLocation p_13328_, String p_13329_) {
         return this.m_13307_(new Tag.ElementEntry(p_13328_), p_13329_);
      }

      public Tag.Builder m_144379_(ResourceLocation p_144380_, String p_144381_) {
         return this.m_13307_(new Tag.OptionalElementEntry(p_144380_), p_144381_);
      }

      public Tag.Builder m_13335_(ResourceLocation p_13336_, String p_13337_) {
         return this.m_13307_(new Tag.TagEntry(p_13336_), p_13337_);
      }

      public Tag.Builder m_144382_(ResourceLocation p_144383_, String p_144384_) {
         return this.m_13307_(new Tag.OptionalTagEntry(p_144383_), p_144384_);
      }

      public Tag.Builder replace(boolean value) {
         this.replace = value;
         return this;
      }

      public Tag.Builder replace() {
         return replace(true);
      }

      public <T> Either<Collection<Tag.BuilderEntry>, Tag<T>> m_144371_(Function<ResourceLocation, Tag<T>> p_144372_, Function<ResourceLocation, T> p_144373_) {
         ImmutableSet.Builder<T> builder = ImmutableSet.builder();
         List<Tag.BuilderEntry> list = Lists.newArrayList();

         for(Tag.BuilderEntry tag$builderentry : this.f_13302_) {
            if (!tag$builderentry.m_13347_().m_7657_(p_144372_, p_144373_, builder::add)) {
               list.add(tag$builderentry);
            }
         }

         return list.isEmpty() ? Either.right(Tag.m_13300_(builder.build())) : Either.left(list);
      }

      public Stream<Tag.BuilderEntry> m_13330_() {
         return this.f_13302_.stream();
      }

      public void m_144366_(Consumer<ResourceLocation> p_144367_) {
         this.f_13302_.forEach((p_144378_) -> {
            p_144378_.f_13338_.m_141929_(p_144367_);
         });
      }

      public void m_144374_(Consumer<ResourceLocation> p_144375_) {
         this.f_13302_.forEach((p_144370_) -> {
            p_144370_.f_13338_.m_141918_(p_144375_);
         });
      }

      public Tag.Builder m_13312_(JsonObject p_13313_, String p_13314_) {
         JsonArray jsonarray = GsonHelper.m_13933_(p_13313_, "values");
         List<Tag.Entry> list = Lists.newArrayList();

         for(JsonElement jsonelement : jsonarray) {
            list.add(m_13310_(jsonelement));
         }

         if (GsonHelper.m_13855_(p_13313_, "replace", false)) {
            this.f_13302_.clear();
         }

         net.minecraftforge.common.ForgeHooks.deserializeTagAdditions(list, p_13313_, f_13302_);
         list.forEach((p_13319_) -> {
            this.f_13302_.add(new Tag.BuilderEntry(p_13319_, p_13314_));
         });
         return this;
      }

      private static Tag.Entry m_13310_(JsonElement p_13311_) {
         String s;
         boolean flag;
         if (p_13311_.isJsonObject()) {
            JsonObject jsonobject = p_13311_.getAsJsonObject();
            s = GsonHelper.m_13906_(jsonobject, "id");
            flag = GsonHelper.m_13855_(jsonobject, "required", true);
         } else {
            s = GsonHelper.m_13805_(p_13311_, "id");
            flag = true;
         }

         if (s.startsWith("#")) {
            ResourceLocation resourcelocation1 = new ResourceLocation(s.substring(1));
            return (Tag.Entry)(flag ? new Tag.TagEntry(resourcelocation1) : new Tag.OptionalTagEntry(resourcelocation1));
         } else {
            ResourceLocation resourcelocation = new ResourceLocation(s);
            return (Tag.Entry)(flag ? new Tag.ElementEntry(resourcelocation) : new Tag.OptionalElementEntry(resourcelocation));
         }
      }

      public JsonObject m_13334_() {
         JsonObject jsonobject = new JsonObject();
         JsonArray jsonarray = new JsonArray();

         for(Tag.BuilderEntry tag$builderentry : this.f_13302_) {
            tag$builderentry.m_13347_().m_6383_(jsonarray);
         }

         jsonobject.addProperty("replace", replace);
         jsonobject.add("values", jsonarray);
         this.serializeTagAdditions(jsonobject);
         return jsonobject;
      }
   }

   public static class BuilderEntry {
      final Tag.Entry f_13338_;
      private final String f_13339_;

      public BuilderEntry(Tag.Entry p_13341_, String p_13342_) {
         this.f_13338_ = p_13341_;
         this.f_13339_ = p_13342_;
      }

      public Tag.Entry m_13347_() {
         return this.f_13338_;
      }

      public String m_144385_() {
         return this.f_13339_;
      }

      public String toString() {
         return this.f_13338_ + " (from " + this.f_13339_ + ")";
      }
   }

   public static class ElementEntry implements Tag.Entry {
      private final ResourceLocation f_13349_;

      public ElementEntry(ResourceLocation p_13351_) {
         this.f_13349_ = p_13351_;
      }

      public <T> boolean m_7657_(Function<ResourceLocation, Tag<T>> p_13355_, Function<ResourceLocation, T> p_13356_, Consumer<T> p_13357_) {
         T t = p_13356_.apply(this.f_13349_);
         if (t == null) {
            return false;
         } else {
            p_13357_.accept(t);
            return true;
         }
      }

      public void m_6383_(JsonArray p_13353_) {
         p_13353_.add(this.f_13349_.toString());
      }

      public boolean m_142746_(Predicate<ResourceLocation> p_144387_, Predicate<ResourceLocation> p_144388_) {
         return p_144387_.test(this.f_13349_);
      }

      public String toString() {
         return this.f_13349_.toString();
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof Tag.ElementEntry && java.util.Objects.equals(this.f_13349_, ((Tag.ElementEntry) o).f_13349_)); }
   }

   public interface Entry {
      <T> boolean m_7657_(Function<ResourceLocation, Tag<T>> p_13360_, Function<ResourceLocation, T> p_13361_, Consumer<T> p_13362_);

      void m_6383_(JsonArray p_13359_);

      default void m_141929_(Consumer<ResourceLocation> p_144389_) {
      }

      default void m_141918_(Consumer<ResourceLocation> p_144392_) {
      }

      boolean m_142746_(Predicate<ResourceLocation> p_144390_, Predicate<ResourceLocation> p_144391_);
   }

   public interface Named<T> extends Tag<T> {
      ResourceLocation m_6979_();
   }

   public static class OptionalElementEntry implements Tag.Entry {
      private final ResourceLocation f_13363_;

      public OptionalElementEntry(ResourceLocation p_13365_) {
         this.f_13363_ = p_13365_;
      }

      public <T> boolean m_7657_(Function<ResourceLocation, Tag<T>> p_13369_, Function<ResourceLocation, T> p_13370_, Consumer<T> p_13371_) {
         T t = p_13370_.apply(this.f_13363_);
         if (t != null) {
            p_13371_.accept(t);
         }

         return true;
      }

      public void m_6383_(JsonArray p_13367_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", this.f_13363_.toString());
         jsonobject.addProperty("required", false);
         p_13367_.add(jsonobject);
      }

      public boolean m_142746_(Predicate<ResourceLocation> p_144394_, Predicate<ResourceLocation> p_144395_) {
         return true;
      }

      public String toString() {
         return this.f_13363_ + "?";
      }
   }

   public static class OptionalTagEntry implements Tag.Entry {
      private final ResourceLocation f_13373_;

      public OptionalTagEntry(ResourceLocation p_13375_) {
         this.f_13373_ = p_13375_;
      }

      public <T> boolean m_7657_(Function<ResourceLocation, Tag<T>> p_13379_, Function<ResourceLocation, T> p_13380_, Consumer<T> p_13381_) {
         Tag<T> tag = p_13379_.apply(this.f_13373_);
         if (tag != null) {
            tag.m_6497_().forEach(p_13381_);
         }

         return true;
      }

      public void m_6383_(JsonArray p_13377_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("id", "#" + this.f_13373_);
         jsonobject.addProperty("required", false);
         p_13377_.add(jsonobject);
      }

      public String toString() {
         return "#" + this.f_13373_ + "?";
      }

      public void m_141918_(Consumer<ResourceLocation> p_144400_) {
         p_144400_.accept(this.f_13373_);
      }

      public boolean m_142746_(Predicate<ResourceLocation> p_144397_, Predicate<ResourceLocation> p_144398_) {
         return true;
      }
   }

   public static class TagEntry implements Tag.Entry {
      private final ResourceLocation f_13383_;

      public TagEntry(ResourceLocation p_13385_) {
         this.f_13383_ = p_13385_;
      }

      public <T> boolean m_7657_(Function<ResourceLocation, Tag<T>> p_13389_, Function<ResourceLocation, T> p_13390_, Consumer<T> p_13391_) {
         Tag<T> tag = p_13389_.apply(this.f_13383_);
         if (tag == null) {
            return false;
         } else {
            tag.m_6497_().forEach(p_13391_);
            return true;
         }
      }

      public void m_6383_(JsonArray p_13387_) {
         p_13387_.add("#" + this.f_13383_);
      }

      public String toString() {
         return "#" + this.f_13383_;
      }
      @Override public boolean equals(Object o) { return o == this || (o instanceof Tag.TagEntry && java.util.Objects.equals(this.f_13383_, ((Tag.TagEntry) o).f_13383_)); }
      public ResourceLocation getId() { return f_13383_; }

      public boolean m_142746_(Predicate<ResourceLocation> p_144404_, Predicate<ResourceLocation> p_144405_) {
         return p_144405_.test(this.f_13383_);
      }

      public void m_141929_(Consumer<ResourceLocation> p_144402_) {
         p_144402_.accept(this.f_13383_);
      }
   }
}
