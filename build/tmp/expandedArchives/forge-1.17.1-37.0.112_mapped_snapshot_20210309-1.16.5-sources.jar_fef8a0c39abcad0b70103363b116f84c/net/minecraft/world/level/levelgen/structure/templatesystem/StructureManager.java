package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureManager {
   private static final Logger f_74325_ = LogManager.getLogger();
   private static final String f_163771_ = "structures";
   private static final String f_163772_ = ".nbt";
   private static final String f_163773_ = ".snbt";
   private final Map<ResourceLocation, Optional<StructureTemplate>> f_74326_ = Maps.newConcurrentMap();
   private final DataFixer f_74327_;
   private ResourceManager f_74328_;
   private final Path f_74329_;

   public StructureManager(ResourceManager p_74332_, LevelStorageSource.LevelStorageAccess p_74333_, DataFixer p_74334_) {
      this.f_74328_ = p_74332_;
      this.f_74327_ = p_74334_;
      this.f_74329_ = p_74333_.m_78283_(LevelResource.f_78179_).normalize();
   }

   public StructureTemplate m_74341_(ResourceLocation p_74342_) {
      Optional<StructureTemplate> optional = this.m_163774_(p_74342_);
      if (optional.isPresent()) {
         return optional.get();
      } else {
         StructureTemplate structuretemplate = new StructureTemplate();
         this.f_74326_.put(p_74342_, Optional.of(structuretemplate));
         return structuretemplate;
      }
   }

   public Optional<StructureTemplate> m_163774_(ResourceLocation p_163775_) {
      return this.f_74326_.computeIfAbsent(p_163775_, (p_163781_) -> {
         Optional<StructureTemplate> optional = this.m_163778_(p_163781_);
         return optional.isPresent() ? optional : this.m_163776_(p_163781_);
      });
   }

   public void m_74335_(ResourceManager p_74336_) {
      this.f_74328_ = p_74336_;
      this.f_74326_.clear();
   }

   private Optional<StructureTemplate> m_163776_(ResourceLocation p_163777_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_163777_.m_135827_(), "structures/" + p_163777_.m_135815_() + ".nbt");

      try {
         Resource resource = this.f_74328_.m_142591_(resourcelocation);

         Optional optional;
         try {
            optional = Optional.of(this.m_74337_(resource.m_6679_()));
         } catch (Throwable throwable1) {
            if (resource != null) {
               try {
                  resource.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (resource != null) {
            resource.close();
         }

         return optional;
      } catch (FileNotFoundException filenotfoundexception) {
         return Optional.empty();
      } catch (Throwable throwable2) {
         f_74325_.error("Couldn't load structure {}: {}", p_163777_, throwable2.toString());
         return Optional.empty();
      }
   }

   private Optional<StructureTemplate> m_163778_(ResourceLocation p_163779_) {
      if (!this.f_74329_.toFile().isDirectory()) {
         return Optional.empty();
      } else {
         Path path = this.m_74348_(p_163779_, ".nbt");

         try {
            InputStream inputstream = new FileInputStream(path.toFile());

            Optional optional;
            try {
               optional = Optional.of(this.m_74337_(inputstream));
            } catch (Throwable throwable1) {
               try {
                  inputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            inputstream.close();
            return optional;
         } catch (FileNotFoundException filenotfoundexception) {
            return Optional.empty();
         } catch (IOException ioexception) {
            f_74325_.error("Couldn't load structure from {}", path, ioexception);
            return Optional.empty();
         }
      }
   }

   private StructureTemplate m_74337_(InputStream p_74338_) throws IOException {
      CompoundTag compoundtag = NbtIo.m_128939_(p_74338_);
      return this.m_74339_(compoundtag);
   }

   public StructureTemplate m_74339_(CompoundTag p_74340_) {
      if (!p_74340_.m_128425_("DataVersion", 99)) {
         p_74340_.m_128405_("DataVersion", 500);
      }

      StructureTemplate structuretemplate = new StructureTemplate();
      structuretemplate.m_74638_(NbtUtils.m_129213_(this.f_74327_, DataFixTypes.STRUCTURE, p_74340_, p_74340_.m_128451_("DataVersion")));
      return structuretemplate;
   }

   public boolean m_74351_(ResourceLocation p_74352_) {
      Optional<StructureTemplate> optional = this.f_74326_.get(p_74352_);
      if (!optional.isPresent()) {
         return false;
      } else {
         StructureTemplate structuretemplate = optional.get();
         Path path = this.m_74348_(p_74352_, ".nbt");
         Path path1 = path.getParent();
         if (path1 == null) {
            return false;
         } else {
            try {
               Files.createDirectories(Files.exists(path1) ? path1.toRealPath() : path1);
            } catch (IOException ioexception) {
               f_74325_.error("Failed to create parent directory: {}", (Object)path1);
               return false;
            }

            CompoundTag compoundtag = structuretemplate.m_74618_(new CompoundTag());

            try {
               OutputStream outputstream = new FileOutputStream(path.toFile());

               try {
                  NbtIo.m_128947_(compoundtag, outputstream);
               } catch (Throwable throwable1) {
                  try {
                     outputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }

                  throw throwable1;
               }

               outputstream.close();
               return true;
            } catch (Throwable throwable2) {
               return false;
            }
         }
      }
   }

   public Path m_74343_(ResourceLocation p_74344_, String p_74345_) {
      try {
         Path path = this.f_74329_.resolve(p_74344_.m_135827_());
         Path path1 = path.resolve("structures");
         return FileUtil.m_133736_(path1, p_74344_.m_135815_(), p_74345_);
      } catch (InvalidPathException invalidpathexception) {
         throw new ResourceLocationException("Invalid resource path: " + p_74344_, invalidpathexception);
      }
   }

   private Path m_74348_(ResourceLocation p_74349_, String p_74350_) {
      if (p_74349_.m_135815_().contains("//")) {
         throw new ResourceLocationException("Invalid resource path: " + p_74349_);
      } else {
         Path path = this.m_74343_(p_74349_, p_74350_);
         if (path.startsWith(this.f_74329_) && FileUtil.m_133728_(path) && FileUtil.m_133734_(path)) {
            return path;
         } else {
            throw new ResourceLocationException("Invalid resource path: " + path);
         }
      }
   }

   public void m_74353_(ResourceLocation p_74354_) {
      this.f_74326_.remove(p_74354_);
   }
}