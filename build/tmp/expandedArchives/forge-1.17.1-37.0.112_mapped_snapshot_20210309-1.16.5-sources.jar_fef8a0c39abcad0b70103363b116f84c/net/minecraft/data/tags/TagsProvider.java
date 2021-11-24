package net.minecraft.data.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TagsProvider<T> implements DataProvider {
   private static final Logger f_126541_ = LogManager.getLogger();
   private static final Gson f_126542_ = (new GsonBuilder()).setPrettyPrinting().create();
   protected final DataGenerator f_126539_;
   protected final Registry<T> f_126540_;
   protected final Map<ResourceLocation, Tag.Builder> f_126543_ = Maps.newLinkedHashMap();
   protected final String modId;
   protected final String folder;
   protected final net.minecraftforge.common.data.ExistingFileHelper existingFileHelper;
   private final net.minecraftforge.common.data.ExistingFileHelper.IResourceType resourceType;

   /**@deprecated Forge: Use the ModID version**/ @Deprecated
   protected TagsProvider(DataGenerator p_126546_, Registry<T> p_126547_) {
      this(p_126546_, p_126547_, "vanilla", null);
   }
   protected TagsProvider(DataGenerator p_126546_, Registry<T> p_126547_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
      this(p_126546_, p_126547_, modId, existingFileHelper, null);
   }
   protected TagsProvider(DataGenerator p_126546_, Registry<T> p_126547_, String modId, @javax.annotation.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper, @javax.annotation.Nullable String folder) {
      this.f_126539_ = p_126546_;
      this.f_126540_ = p_126547_;
      this.modId = modId;
      this.existingFileHelper = existingFileHelper;
      if (folder == null) {
         //noinspection unchecked,rawtypes
         folder = ((Registry) Registry.f_122897_).m_7981_(f_126540_).m_135815_() + "s";
      }
      this.folder = folder;
      this.resourceType = new net.minecraftforge.common.data.ExistingFileHelper.ResourceType(net.minecraft.server.packs.PackType.SERVER_DATA, ".json", "tags/" + this.folder);
   }

   protected abstract void m_6577_();

   public void m_6865_(HashCache p_126554_) {
      this.f_126543_.clear();
      this.m_6577_();
      this.f_126543_.forEach((p_176835_, p_176836_) -> {
         List<Tag.BuilderEntry> list = p_176836_.m_13330_().filter((p_176832_) -> {
            return !p_176832_.m_13347_().m_142746_(this.f_126540_::m_7804_, this.f_126543_::containsKey);
         }).filter(this::missing).collect(Collectors.toList()); // Forge: Add validation via existing resources
         if (!list.isEmpty()) {
            throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", p_176835_, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
         } else {
            JsonObject jsonobject = p_176836_.m_13334_();
            Path path = this.m_6648_(p_176835_);
            if (path == null) return; // Forge: Allow running this data provider without writing it. Recipe provider needs valid tags.

            try {
               String s = f_126542_.toJson((JsonElement)jsonobject);
               String s1 = f_123918_.hashUnencodedChars(s).toString();
               if (!Objects.equals(p_126554_.m_123938_(path), s1) || !Files.exists(path)) {
                  Files.createDirectories(path.getParent());
                  BufferedWriter bufferedwriter = Files.newBufferedWriter(path);

                  try {
                     bufferedwriter.write(s);
                  } catch (Throwable throwable1) {
                     if (bufferedwriter != null) {
                        try {
                           bufferedwriter.close();
                        } catch (Throwable throwable) {
                           throwable1.addSuppressed(throwable);
                        }
                     }

                     throw throwable1;
                  }

                  if (bufferedwriter != null) {
                     bufferedwriter.close();
                  }
               }

               p_126554_.m_123940_(path, s1);
            } catch (IOException ioexception) {
               f_126541_.error("Couldn't save tags to {}", path, ioexception);
            }

         }
      });
   }

   private boolean missing(Tag.BuilderEntry reference) {
      Tag.Entry entry = reference.m_13347_();
      // We only care about non-optional tag entries, this is the only type that can reference a resource and needs validation
      // Optional tags should not be validated
      if (entry instanceof Tag.TagEntry) {
         return existingFileHelper == null || !existingFileHelper.exists(((Tag.TagEntry)entry).getId(), resourceType);
      }
      return false;
   }

   protected abstract Path m_6648_(ResourceLocation p_126561_);

   protected TagsProvider.TagAppender<T> m_126548_(Tag.Named<T> p_126549_) {
      Tag.Builder tag$builder = this.m_126562_(p_126549_);
      return new TagsProvider.TagAppender<>(tag$builder, this.f_126540_, modId);
   }

   protected Tag.Builder m_126562_(Tag.Named<T> p_126563_) {
      return this.f_126543_.computeIfAbsent(p_126563_.m_6979_(), (p_176838_) -> {
         existingFileHelper.trackGenerated(p_176838_, resourceType);
         return new Tag.Builder();
      });
   }

   public static class TagAppender<T> implements net.minecraftforge.common.extensions.IForgeTagAppender<T> {
      private final Tag.Builder f_126568_;
      public final Registry<T> f_126569_;
      private final String f_126570_;

      TagAppender(Tag.Builder p_126572_, Registry<T> p_126573_, String p_126574_) {
         this.f_126568_ = p_126572_;
         this.f_126569_ = p_126573_;
         this.f_126570_ = p_126574_;
      }

      public TagsProvider.TagAppender<T> m_126582_(T p_126583_) {
         this.f_126568_.m_13327_(this.f_126569_.m_7981_(p_126583_), this.f_126570_);
         return this;
      }

      public TagsProvider.TagAppender<T> addOptional(ResourceLocation p_176840_) {
         this.f_126568_.m_144379_(p_176840_, this.f_126570_);
         return this;
      }

      public TagsProvider.TagAppender<T> m_126580_(Tag.Named<T> p_126581_) {
         this.f_126568_.m_13335_(p_126581_.m_6979_(), this.f_126570_);
         return this;
      }

      public TagsProvider.TagAppender<T> addOptionalTag(ResourceLocation p_176842_) {
         this.f_126568_.m_144382_(p_176842_, this.f_126570_);
         return this;
      }

      @SafeVarargs
      public final TagsProvider.TagAppender<T> m_126584_(T... p_126585_) {
         Stream.<T>of(p_126585_).map(this.f_126569_::m_7981_).forEach((p_126587_) -> {
            this.f_126568_.m_13327_(p_126587_, this.f_126570_);
         });
         return this;
      }

      public TagsProvider.TagAppender<T> add(Tag.Entry tag) {
          f_126568_.m_13307_(tag, f_126570_);
          return this;
      }

      public Tag.Builder getInternalBuilder() {
          return f_126568_;
      }

      public String getModID() {
          return f_126570_;
      }
   }
}
