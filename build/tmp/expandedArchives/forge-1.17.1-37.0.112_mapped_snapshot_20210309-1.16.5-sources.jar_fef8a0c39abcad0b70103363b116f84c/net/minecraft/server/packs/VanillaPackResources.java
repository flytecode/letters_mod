package net.minecraft.server.packs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanillaPackResources implements PackResources, ResourceProvider {
   public static Path f_10312_;
   private static final Logger f_10315_ = LogManager.getLogger();
   public static Class<?> f_10313_;
   private static final Map<PackType, Path> f_182296_ = Util.m_137537_(() -> {
      synchronized(VanillaPackResources.class) {
         Builder<PackType, Path> builder = ImmutableMap.builder();

         for(PackType packtype : PackType.values()) {
            String s = "/" + packtype.m_10305_() + "/.mcassetsroot";
            URL url = VanillaPackResources.class.getResource(s);
            if (url == null) {
               f_10315_.error("File {} does not exist in classpath", (Object)s);
            } else {
               try {
                  URI uri = url.toURI();
                  String s1 = uri.getScheme();
                  if (!"jar".equals(s1) && !"file".equals(s1)) {
                     f_10315_.warn("Assets URL '{}' uses unexpected schema", (Object)uri);
                  }

                  Path path = m_182297_(uri);
                  builder.put(packtype, path.getParent());
               } catch (Exception exception) {
                  f_10315_.error("Couldn't resolve path to vanilla assets", (Throwable)exception);
               }
            }
         }

         return builder.build();
      }
   });
   public final PackMetadataSection f_143759_;
   public final Set<String> f_10314_;

   private static Path m_182297_(URI p_182298_) throws IOException {
      try {
         return Paths.get(p_182298_);
      } catch (FileSystemNotFoundException filesystemnotfoundexception) {
      } catch (Throwable throwable) {
         f_10315_.warn("Unable to get path for: {}", p_182298_, throwable);
      }

      try {
         FileSystems.newFileSystem(p_182298_, Collections.emptyMap());
      } catch (FileSystemAlreadyExistsException filesystemalreadyexistsexception) {
      }

      return Paths.get(p_182298_);
   }

   public VanillaPackResources(PackMetadataSection p_143761_, String... p_143762_) {
      this.f_143759_ = p_143761_;
      this.f_10314_ = ImmutableSet.copyOf(p_143762_);
   }

   public InputStream m_5542_(String p_10358_) throws IOException {
      if (!p_10358_.contains("/") && !p_10358_.contains("\\")) {
         if (f_10312_ != null) {
            Path path = f_10312_.resolve(p_10358_);
            if (Files.exists(path)) {
               return Files.newInputStream(path);
            }
         }

         return this.m_5539_(p_10358_);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   public InputStream m_8031_(PackType p_10330_, ResourceLocation p_10331_) throws IOException {
      InputStream inputstream = this.m_8033_(p_10330_, p_10331_);
      if (inputstream != null) {
         return inputstream;
      } else {
         throw new FileNotFoundException(p_10331_.m_135815_());
      }
   }

   public Collection<ResourceLocation> m_7466_(PackType p_10324_, String p_10325_, String p_10326_, int p_10327_, Predicate<String> p_10328_) {
      Set<ResourceLocation> set = Sets.newHashSet();
      if (f_10312_ != null) {
         try {
            m_10342_(set, p_10327_, p_10325_, f_10312_.resolve(p_10324_.m_10305_()), p_10326_, p_10328_);
         } catch (IOException ioexception2) {
         }

         if (p_10324_ == PackType.CLIENT_RESOURCES) {
            Enumeration<URL> enumeration = null;

            try {
               enumeration = f_10313_.getClassLoader().getResources(p_10324_.m_10305_() + "/");
            } catch (IOException ioexception1) {
            }

            while(enumeration != null && enumeration.hasMoreElements()) {
               try {
                  URI uri = enumeration.nextElement().toURI();
                  if ("file".equals(uri.getScheme())) {
                     m_10342_(set, p_10327_, p_10325_, Paths.get(uri), p_10326_, p_10328_);
                  }
               } catch (IOException | URISyntaxException urisyntaxexception) {
               }
            }
         }
      }

      try {
         Path path = f_182296_.get(p_10324_);
         if (path != null) {
            m_10342_(set, p_10327_, p_10325_, path, p_10326_, p_10328_);
         } else {
            f_10315_.error("Can't access assets root for type: {}", (Object)p_10324_);
         }
      } catch (NoSuchFileException | FileNotFoundException filenotfoundexception) {
      } catch (IOException ioexception) {
         f_10315_.error("Couldn't get a list of all vanilla resources", (Throwable)ioexception);
      }

      return set;
   }

   private static void m_10342_(Collection<ResourceLocation> p_10343_, int p_10344_, String p_10345_, Path p_10346_, String p_10347_, Predicate<String> p_10348_) throws IOException {
      Path path = p_10346_.resolve(p_10345_);
      Stream<Path> stream = Files.walk(path.resolve(p_10347_), p_10344_);

      try {
         stream.filter((p_10353_) -> {
            return !p_10353_.endsWith(".mcmeta") && Files.isRegularFile(p_10353_) && p_10348_.test(p_10353_.getFileName().toString());
         }).map((p_10341_) -> {
            return new ResourceLocation(p_10345_, path.relativize(p_10341_).toString().replaceAll("\\\\", "/"));
         }).forEach(p_10343_::add);
      } catch (Throwable throwable1) {
         if (stream != null) {
            try {
               stream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (stream != null) {
         stream.close();
      }

   }

   @Nullable
   protected InputStream m_8033_(PackType p_10359_, ResourceLocation p_10360_) {
      String s = m_10362_(p_10359_, p_10360_);
      if (f_10312_ != null) {
         Path path = f_10312_.resolve(p_10359_.m_10305_() + "/" + p_10360_.m_135827_() + "/" + p_10360_.m_135815_());
         if (Files.exists(path)) {
            try {
               return Files.newInputStream(path);
            } catch (IOException ioexception1) {
            }
         }
      }

      try {
         URL url = VanillaPackResources.class.getResource(s);
         return m_10335_(s, url) ? getExtraInputStream(p_10359_, s) : null;
      } catch (IOException ioexception) {
         return VanillaPackResources.class.getResourceAsStream(s);
      }
   }

   private static String m_10362_(PackType p_10363_, ResourceLocation p_10364_) {
      return "/" + p_10363_.m_10305_() + "/" + p_10364_.m_135827_() + "/" + p_10364_.m_135815_();
   }

   private static boolean m_10335_(String p_10336_, @Nullable URL p_10337_) throws IOException {
      return p_10337_ != null && (p_10337_.getProtocol().equals("jar") || FolderPackResources.m_10273_(new File(p_10337_.getFile()), p_10336_));
   }

   @Nullable
   protected InputStream m_5539_(String p_10334_) {
      return getExtraInputStream(PackType.SERVER_DATA, "/" + p_10334_);
   }

   public boolean m_7211_(PackType p_10355_, ResourceLocation p_10356_) {
      String s = m_10362_(p_10355_, p_10356_);
      if (f_10312_ != null) {
         Path path = f_10312_.resolve(p_10355_.m_10305_() + "/" + p_10356_.m_135827_() + "/" + p_10356_.m_135815_());
         if (Files.exists(path)) {
            return true;
         }
      }

      try {
         URL url = VanillaPackResources.class.getResource(s);
         return m_10335_(s, url);
      } catch (IOException ioexception) {
         return false;
      }
   }

   public Set<String> m_5698_(PackType p_10322_) {
      return this.f_10314_;
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> p_10333_) throws IOException {
      try {
         InputStream inputstream = this.m_5542_("pack.mcmeta");

         Object object;
         label59: {
            try {
               if (inputstream != null) {
                  T t = AbstractPackResources.m_10214_(p_10333_, inputstream);
                  if (t != null) {
                     object = t;
                     break label59;
                  }
               }
            } catch (Throwable throwable1) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            return (T)(p_10333_ == PackMetadataSection.f_10366_ ? this.f_143759_ : null);
         }

         if (inputstream != null) {
            inputstream.close();
         }

         return (T)object;
      } catch (FileNotFoundException | RuntimeException runtimeexception) {
         return (T)(p_10333_ == PackMetadataSection.f_10366_ ? this.f_143759_ : null);
      }
   }

   public String m_8017_() {
      return "Default";
   }

   public void close() {
   }

   //Vanilla used to just grab from the classpath, this breaks dev environments, and Forge runtime
   //as forge ships vanilla assets in an 'extra' jar with no classes.
   //So find that extra jar using the .mcassetsroot marker.
   private InputStream getExtraInputStream(PackType type, String resource) {
      try {
         Path rootDir = f_182296_.get(type);
         if (rootDir != null)
            return Files.newInputStream(rootDir.resolve(resource));
         return VanillaPackResources.class.getResourceAsStream(resource);
      } catch (IOException e) {
         return VanillaPackResources.class.getResourceAsStream(resource);
      }
   }

   public Resource m_142591_(final ResourceLocation p_143764_) throws IOException {
      return new Resource() {
         @Nullable
         InputStream f_143765_;

         public void close() throws IOException {
            if (this.f_143765_ != null) {
               this.f_143765_.close();
            }

         }

         public ResourceLocation m_7843_() {
            return p_143764_;
         }

         public InputStream m_6679_() {
            try {
               this.f_143765_ = VanillaPackResources.this.m_8031_(PackType.CLIENT_RESOURCES, p_143764_);
            } catch (IOException ioexception) {
               throw new UncheckedIOException("Could not get client resource from vanilla pack", ioexception);
            }

            return this.f_143765_;
         }

         public boolean m_142564_() {
            return false;
         }

         @Nullable
         public <T> T m_5507_(MetadataSectionSerializer<T> p_143773_) {
            return (T)null;
         }

         public String m_7816_() {
            return p_143764_.toString();
         }
      };
   }
}
