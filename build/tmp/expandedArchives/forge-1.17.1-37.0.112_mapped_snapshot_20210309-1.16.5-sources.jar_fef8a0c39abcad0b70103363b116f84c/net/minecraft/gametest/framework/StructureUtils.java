package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUtils {
   private static final Logger f_177742_ = LogManager.getLogger();
   public static final String f_177741_ = "gameteststructures";
   public static String f_127833_ = "gameteststructures";
   private static final int f_177743_ = 4;

   public static Rotation m_127835_(int p_127836_) {
      switch(p_127836_) {
      case 0:
         return Rotation.NONE;
      case 1:
         return Rotation.CLOCKWISE_90;
      case 2:
         return Rotation.CLOCKWISE_180;
      case 3:
         return Rotation.COUNTERCLOCKWISE_90;
      default:
         throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + p_127836_);
      }
   }

   public static int m_177751_(Rotation p_177752_) {
      switch(p_177752_) {
      case NONE:
         return 0;
      case CLOCKWISE_90:
         return 1;
      case CLOCKWISE_180:
         return 2;
      case COUNTERCLOCKWISE_90:
         return 3;
      default:
         throw new IllegalArgumentException("Unknown rotation value, don't know how many steps it represents: " + p_177752_);
      }
   }

   public static void m_177770_(String[] p_177771_) throws IOException {
      Bootstrap.m_135870_();
      Files.walk(Paths.get(f_127833_)).filter((p_177775_) -> {
         return p_177775_.toString().endsWith(".snbt");
      }).forEach((p_177773_) -> {
         try {
            String s = new String(Files.readAllBytes(p_177773_), StandardCharsets.UTF_8);
            CompoundTag compoundtag = NbtUtils.m_178024_(s);
            CompoundTag compoundtag1 = StructureUpdater.m_176822_(p_177773_.toString(), compoundtag);
            NbtToSnbt.m_176812_(p_177773_, NbtUtils.m_178063_(compoundtag1));
         } catch (IOException | CommandSyntaxException commandsyntaxexception) {
            f_177742_.error("Something went wrong upgrading: {}", p_177773_, commandsyntaxexception);
         }

      });
   }

   public static AABB m_127847_(StructureBlockEntity p_127848_) {
      BlockPos blockpos = p_127848_.m_58899_();
      BlockPos blockpos1 = blockpos.m_141952_(p_127848_.m_155805_().m_142082_(-1, -1, -1));
      BlockPos blockpos2 = StructureTemplate.m_74593_(blockpos1, Mirror.NONE, p_127848_.m_59906_(), blockpos);
      return new AABB(blockpos, blockpos2);
   }

   public static BoundingBox m_127904_(StructureBlockEntity p_127905_) {
      BlockPos blockpos = p_127905_.m_58899_();
      BlockPos blockpos1 = blockpos.m_141952_(p_127905_.m_155805_().m_142082_(-1, -1, -1));
      BlockPos blockpos2 = StructureTemplate.m_74593_(blockpos1, Mirror.NONE, p_127905_.m_59906_(), blockpos);
      return BoundingBox.m_162375_(blockpos, blockpos2);
   }

   public static void m_127875_(BlockPos p_127876_, BlockPos p_127877_, Rotation p_127878_, ServerLevel p_127879_) {
      BlockPos blockpos = StructureTemplate.m_74593_(p_127876_.m_141952_(p_127877_), Mirror.NONE, p_127878_, p_127876_);
      p_127879_.m_46597_(blockpos, Blocks.f_50272_.m_49966_());
      CommandBlockEntity commandblockentity = (CommandBlockEntity)p_127879_.m_7702_(blockpos);
      commandblockentity.m_59141_().m_6590_("test runthis");
      BlockPos blockpos1 = StructureTemplate.m_74593_(blockpos.m_142082_(0, 0, -1), Mirror.NONE, p_127878_, blockpos);
      p_127879_.m_46597_(blockpos1, Blocks.f_50124_.m_49966_().m_60717_(p_127878_));
   }

   public static void m_177764_(String p_177765_, BlockPos p_177766_, Vec3i p_177767_, Rotation p_177768_, ServerLevel p_177769_) {
      BoundingBox boundingbox = m_177760_(p_177766_, p_177767_, p_177768_);
      m_127849_(boundingbox, p_177766_.m_123342_(), p_177769_);
      p_177769_.m_46597_(p_177766_, Blocks.f_50677_.m_49966_());
      StructureBlockEntity structureblockentity = (StructureBlockEntity)p_177769_.m_7702_(p_177766_);
      structureblockentity.m_59876_(false);
      structureblockentity.m_59874_(new ResourceLocation(p_177765_));
      structureblockentity.m_155797_(p_177767_);
      structureblockentity.m_59860_(StructureMode.SAVE);
      structureblockentity.m_59898_(true);
   }

   public static StructureBlockEntity m_127883_(String p_127884_, BlockPos p_127885_, Rotation p_127886_, int p_127887_, ServerLevel p_127888_, boolean p_127889_) {
      Vec3i vec3i = m_127880_(p_127884_, p_127888_).m_163801_();
      BoundingBox boundingbox = m_177760_(p_127885_, vec3i, p_127886_);
      BlockPos blockpos;
      if (p_127886_ == Rotation.NONE) {
         blockpos = p_127885_;
      } else if (p_127886_ == Rotation.CLOCKWISE_90) {
         blockpos = p_127885_.m_142082_(vec3i.m_123343_() - 1, 0, 0);
      } else if (p_127886_ == Rotation.CLOCKWISE_180) {
         blockpos = p_127885_.m_142082_(vec3i.m_123341_() - 1, 0, vec3i.m_123343_() - 1);
      } else {
         if (p_127886_ != Rotation.COUNTERCLOCKWISE_90) {
            throw new IllegalArgumentException("Invalid rotation: " + p_127886_);
         }

         blockpos = p_127885_.m_142082_(0, 0, vec3i.m_123341_() - 1);
      }

      m_127857_(p_127885_, p_127888_);
      m_127849_(boundingbox, p_127885_.m_123342_(), p_127888_);
      StructureBlockEntity structureblockentity = m_127890_(p_127884_, blockpos, p_127886_, p_127888_, p_127889_);
      p_127888_.m_6219_().m_47232_(boundingbox, true, false);
      p_127888_.m_8722_(boundingbox);
      return structureblockentity;
   }

   private static void m_127857_(BlockPos p_127858_, ServerLevel p_127859_) {
      ChunkPos chunkpos = new ChunkPos(p_127858_);

      for(int i = -1; i < 4; ++i) {
         for(int j = -1; j < 4; ++j) {
            int k = chunkpos.f_45578_ + i;
            int l = chunkpos.f_45579_ + j;
            p_127859_.m_8602_(k, l, true);
         }
      }

   }

   public static void m_127849_(BoundingBox p_127850_, int p_127851_, ServerLevel p_127852_) {
      BoundingBox boundingbox = new BoundingBox(p_127850_.m_162395_() - 2, p_127850_.m_162396_() - 3, p_127850_.m_162398_() - 3, p_127850_.m_162399_() + 3, p_127850_.m_162400_() + 20, p_127850_.m_162401_() + 3);
      BlockPos.m_121919_(boundingbox).forEach((p_177748_) -> {
         m_127841_(p_127851_, p_177748_, p_127852_);
      });
      p_127852_.m_6219_().m_47232_(boundingbox, true, false);
      p_127852_.m_8722_(boundingbox);
      AABB aabb = new AABB((double)boundingbox.m_162395_(), (double)boundingbox.m_162396_(), (double)boundingbox.m_162398_(), (double)boundingbox.m_162399_(), (double)boundingbox.m_162400_(), (double)boundingbox.m_162401_());
      List<Entity> list = p_127852_.m_6443_(Entity.class, aabb, (p_177750_) -> {
         return !(p_177750_ instanceof Player);
      });
      list.forEach(Entity::m_146870_);
   }

   public static BoundingBox m_177760_(BlockPos p_177761_, Vec3i p_177762_, Rotation p_177763_) {
      BlockPos blockpos = p_177761_.m_141952_(p_177762_).m_142082_(-1, -1, -1);
      BlockPos blockpos1 = StructureTemplate.m_74593_(blockpos, Mirror.NONE, p_177763_, p_177761_);
      BoundingBox boundingbox = BoundingBox.m_162375_(p_177761_, blockpos1);
      int i = Math.min(boundingbox.m_162395_(), boundingbox.m_162399_());
      int j = Math.min(boundingbox.m_162398_(), boundingbox.m_162401_());
      return boundingbox.m_162367_(p_177761_.m_123341_() - i, 0, p_177761_.m_123343_() - j);
   }

   public static Optional<BlockPos> m_127853_(BlockPos p_127854_, int p_127855_, ServerLevel p_127856_) {
      return m_127910_(p_127854_, p_127855_, p_127856_).stream().filter((p_177756_) -> {
         return m_127867_(p_177756_, p_127854_, p_127856_);
      }).findFirst();
   }

   @Nullable
   public static BlockPos m_127906_(BlockPos p_127907_, int p_127908_, ServerLevel p_127909_) {
      Comparator<BlockPos> comparator = Comparator.comparingInt((p_177759_) -> {
         return p_177759_.m_123333_(p_127907_);
      });
      Collection<BlockPos> collection = m_127910_(p_127907_, p_127908_, p_127909_);
      Optional<BlockPos> optional = collection.stream().min(comparator);
      return optional.orElse((BlockPos)null);
   }

   public static Collection<BlockPos> m_127910_(BlockPos p_127911_, int p_127912_, ServerLevel p_127913_) {
      Collection<BlockPos> collection = Lists.newArrayList();
      AABB aabb = new AABB(p_127911_);
      aabb = aabb.m_82400_((double)p_127912_);

      for(int i = (int)aabb.f_82288_; i <= (int)aabb.f_82291_; ++i) {
         for(int j = (int)aabb.f_82289_; j <= (int)aabb.f_82292_; ++j) {
            for(int k = (int)aabb.f_82290_; k <= (int)aabb.f_82293_; ++k) {
               BlockPos blockpos = new BlockPos(i, j, k);
               BlockState blockstate = p_127913_.m_8055_(blockpos);
               if (blockstate.m_60713_(Blocks.f_50677_)) {
                  collection.add(blockpos);
               }
            }
         }
      }

      return collection;
   }

   private static StructureTemplate m_127880_(String p_127881_, ServerLevel p_127882_) {
      StructureManager structuremanager = p_127882_.m_8875_();
      Optional<StructureTemplate> optional = structuremanager.m_163774_(new ResourceLocation(p_127881_));
      if (optional.isPresent()) {
         return optional.get();
      } else {
         String s = p_127881_ + ".snbt";
         Path path = Paths.get(f_127833_, s);
         CompoundTag compoundtag = m_127902_(path);
         if (compoundtag == null) {
            throw new RuntimeException("Could not find structure file " + path + ", and the structure is not available in the world structures either.");
         } else {
            return structuremanager.m_74339_(compoundtag);
         }
      }
   }

   private static StructureBlockEntity m_127890_(String p_127891_, BlockPos p_127892_, Rotation p_127893_, ServerLevel p_127894_, boolean p_127895_) {
      p_127894_.m_46597_(p_127892_, Blocks.f_50677_.m_49966_());
      StructureBlockEntity structureblockentity = (StructureBlockEntity)p_127894_.m_7702_(p_127892_);
      structureblockentity.m_59860_(StructureMode.LOAD);
      structureblockentity.m_59883_(p_127893_);
      structureblockentity.m_59876_(false);
      structureblockentity.m_59874_(new ResourceLocation(p_127891_));
      structureblockentity.m_59844_(p_127894_, p_127895_);
      if (structureblockentity.m_155805_() != Vec3i.f_123288_) {
         return structureblockentity;
      } else {
         StructureTemplate structuretemplate = m_127880_(p_127891_, p_127894_);
         structureblockentity.m_59847_(p_127894_, p_127895_, structuretemplate);
         if (structureblockentity.m_155805_() == Vec3i.f_123288_) {
            throw new RuntimeException("Failed to load structure " + p_127891_);
         } else {
            return structureblockentity;
         }
      }
   }

   @Nullable
   private static CompoundTag m_127902_(Path p_127903_) {
      try {
         BufferedReader bufferedreader = Files.newBufferedReader(p_127903_);
         String s = IOUtils.toString((Reader)bufferedreader);
         return NbtUtils.m_178024_(s);
      } catch (IOException ioexception) {
         return null;
      } catch (CommandSyntaxException commandsyntaxexception) {
         throw new RuntimeException("Error while trying to load structure " + p_127903_, commandsyntaxexception);
      }
   }

   private static void m_127841_(int p_127842_, BlockPos p_127843_, ServerLevel p_127844_) {
      BlockState blockstate = null;
      FlatLevelGeneratorSettings flatlevelgeneratorsettings = FlatLevelGeneratorSettings.m_70376_(p_127844_.m_5962_().m_175515_(Registry.f_122885_));
      if (flatlevelgeneratorsettings instanceof FlatLevelGeneratorSettings) {
         List<BlockState> list = flatlevelgeneratorsettings.m_161917_();
         int i = p_127843_.m_123342_() - p_127844_.m_141937_();
         if (p_127843_.m_123342_() < p_127842_ && i > 0 && i <= list.size()) {
            blockstate = list.get(i - 1);
         }
      } else if (p_127843_.m_123342_() == p_127842_ - 1) {
         blockstate = p_127844_.m_46857_(p_127843_).m_47536_().m_47824_().m_6743_();
      } else if (p_127843_.m_123342_() < p_127842_ - 1) {
         blockstate = p_127844_.m_46857_(p_127843_).m_47536_().m_47824_().m_6744_();
      }

      if (blockstate == null) {
         blockstate = Blocks.f_50016_.m_49966_();
      }

      BlockInput blockinput = new BlockInput(blockstate, Collections.emptySet(), (CompoundTag)null);
      blockinput.m_114670_(p_127844_, p_127843_, 2);
      p_127844_.m_6289_(p_127843_, blockstate.m_60734_());
   }

   private static boolean m_127867_(BlockPos p_127868_, BlockPos p_127869_, ServerLevel p_127870_) {
      StructureBlockEntity structureblockentity = (StructureBlockEntity)p_127870_.m_7702_(p_127868_);
      AABB aabb = m_127847_(structureblockentity).m_82400_(1.0D);
      return aabb.m_82390_(Vec3.m_82512_(p_127869_));
   }
}