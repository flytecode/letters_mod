package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ChunkTickList;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.ProtoTickList;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkSerializer {
   private static final Logger f_63449_ = LogManager.getLogger();
   public static final String f_156511_ = "UpgradeData";

   public static ProtoChunk m_63457_(ServerLevel p_63458_, StructureManager p_63459_, PoiManager p_63460_, ChunkPos p_63461_, CompoundTag p_63462_) {
      ChunkGenerator chunkgenerator = p_63458_.m_7726_().m_8481_();
      BiomeSource biomesource = chunkgenerator.m_62218_();
      CompoundTag compoundtag = p_63462_.m_128469_("Level");
      ChunkPos chunkpos = new ChunkPos(compoundtag.m_128451_("xPos"), compoundtag.m_128451_("zPos"));
      if (!Objects.equals(p_63461_, chunkpos)) {
         f_63449_.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", p_63461_, p_63461_, chunkpos);
      }

      ChunkBiomeContainer chunkbiomecontainer = new ChunkBiomeContainer(p_63458_.m_5962_().m_175515_(Registry.f_122885_), p_63458_, p_63461_, biomesource, compoundtag.m_128425_("Biomes", 11) ? compoundtag.m_128465_("Biomes") : null);
      UpgradeData upgradedata = compoundtag.m_128425_("UpgradeData", 10) ? new UpgradeData(compoundtag.m_128469_("UpgradeData"), p_63458_) : UpgradeData.f_63320_;
      ProtoTickList<Block> prototicklist = new ProtoTickList<>((p_63475_) -> {
         return p_63475_ == null || p_63475_.m_49966_().m_60795_();
      }, p_63461_, compoundtag.m_128437_("ToBeTicked", 9), p_63458_);
      ProtoTickList<Fluid> prototicklist1 = new ProtoTickList<>((p_63484_) -> {
         return p_63484_ == null || p_63484_ == Fluids.f_76191_;
      }, p_63461_, compoundtag.m_128437_("LiquidsToBeTicked", 9), p_63458_);
      boolean flag = compoundtag.m_128471_("isLightOn");
      ListTag listtag = compoundtag.m_128437_("Sections", 10);
      int i = p_63458_.m_151559_();
      LevelChunkSection[] alevelchunksection = new LevelChunkSection[i];
      boolean flag1 = p_63458_.m_6042_().m_63935_();
      ChunkSource chunksource = p_63458_.m_7726_();
      LevelLightEngine levellightengine = chunksource.m_7827_();
      if (flag) {
         levellightengine.m_6462_(p_63461_, true);
      }

      for(int j = 0; j < listtag.size(); ++j) {
         CompoundTag compoundtag1 = listtag.m_128728_(j);
         int k = compoundtag1.m_128445_("Y");
         if (compoundtag1.m_128425_("Palette", 9) && compoundtag1.m_128425_("BlockStates", 12)) {
            LevelChunkSection levelchunksection = new LevelChunkSection(k);
            levelchunksection.m_63019_().m_63115_(compoundtag1.m_128437_("Palette", 10), compoundtag1.m_128467_("BlockStates"));
            levelchunksection.m_63018_();
            if (!levelchunksection.m_63013_()) {
               alevelchunksection[p_63458_.m_151566_(k)] = levelchunksection;
            }

            p_63460_.m_27047_(p_63461_, levelchunksection);
         }

         if (flag) {
            if (compoundtag1.m_128425_("BlockLight", 7)) {
               levellightengine.m_5687_(LightLayer.BLOCK, SectionPos.m_123196_(p_63461_, k), new DataLayer(compoundtag1.m_128463_("BlockLight")), true);
            }

            if (flag1 && compoundtag1.m_128425_("SkyLight", 7)) {
               levellightengine.m_5687_(LightLayer.SKY, SectionPos.m_123196_(p_63461_, k), new DataLayer(compoundtag1.m_128463_("SkyLight")), true);
            }
         }
      }

      long k1 = compoundtag.m_128454_("InhabitedTime");
      ChunkStatus.ChunkType chunkstatus$chunktype = m_63485_(p_63462_);
      ChunkAccess chunkaccess;
      if (chunkstatus$chunktype == ChunkStatus.ChunkType.LEVELCHUNK) {
         TickList<Block> ticklist;
         if (compoundtag.m_128425_("TileTicks", 9)) {
            ticklist = ChunkTickList.m_45656_(compoundtag.m_128437_("TileTicks", 10), Registry.f_122824_::m_7981_, Registry.f_122824_::m_7745_);
         } else {
            ticklist = prototicklist;
         }

         TickList<Fluid> ticklist1;
         if (compoundtag.m_128425_("LiquidTicks", 9)) {
            ticklist1 = ChunkTickList.m_45656_(compoundtag.m_128437_("LiquidTicks", 10), Registry.f_122822_::m_7981_, Registry.f_122822_::m_7745_);
         } else {
            ticklist1 = prototicklist1;
         }

         chunkaccess = new LevelChunk(p_63458_.m_6018_(), p_63461_, chunkbiomecontainer, upgradedata, ticklist, ticklist1, k1, alevelchunksection, (p_156533_) -> {
            m_156522_(p_63458_, compoundtag, p_156533_);
         });
         if (compoundtag.m_128441_("ForgeCaps")) ((LevelChunk)chunkaccess).readCapsFromNBT(compoundtag.m_128469_("ForgeCaps"));
      } else {
         ProtoChunk protochunk = new ProtoChunk(p_63461_, upgradedata, alevelchunksection, prototicklist, prototicklist1, p_63458_);
         protochunk.m_7329_(chunkbiomecontainer);
         chunkaccess = protochunk;
         protochunk.m_6141_(k1);
         protochunk.m_7150_(ChunkStatus.m_62397_(compoundtag.m_128461_("Status")));
         if (protochunk.m_6415_().m_62427_(ChunkStatus.f_62322_)) {
            protochunk.m_63209_(levellightengine);
         }

         if (!flag && protochunk.m_6415_().m_62427_(ChunkStatus.f_62323_)) {
            for(BlockPos blockpos : BlockPos.m_121976_(p_63461_.m_45604_(), p_63458_.m_141937_(), p_63461_.m_45605_(), p_63461_.m_45608_(), p_63458_.m_151558_() - 1, p_63461_.m_45609_())) {
               if (chunkaccess.m_8055_(blockpos).getLightEmission(chunkaccess, blockpos) != 0) {
                  protochunk.m_63277_(blockpos);
               }
            }
         }
      }

      chunkaccess.m_8094_(flag);
      CompoundTag compoundtag3 = compoundtag.m_128469_("Heightmaps");
      EnumSet<Heightmap.Types> enumset = EnumSet.noneOf(Heightmap.Types.class);

      for(Heightmap.Types heightmap$types : chunkaccess.m_6415_().m_62500_()) {
         String s = heightmap$types.m_64294_();
         if (compoundtag3.m_128425_(s, 12)) {
            chunkaccess.m_6511_(heightmap$types, compoundtag3.m_128467_(s));
         } else {
            enumset.add(heightmap$types);
         }
      }

      Heightmap.m_64256_(chunkaccess, enumset);
      CompoundTag compoundtag4 = compoundtag.m_128469_("Structures");
      chunkaccess.m_8040_(m_156518_(p_63458_, compoundtag4, p_63458_.m_7328_()));
      net.minecraftforge.common.ForgeHooks.fixNullStructureReferences(chunkaccess, m_63471_(p_63461_, compoundtag4));
      if (compoundtag.m_128471_("shouldSave")) {
         chunkaccess.m_8092_(true);
      }

      ListTag listtag3 = compoundtag.m_128437_("PostProcessing", 9);

      for(int l1 = 0; l1 < listtag3.size(); ++l1) {
         ListTag listtag1 = listtag3.m_128744_(l1);

         for(int l = 0; l < listtag1.size(); ++l) {
            chunkaccess.m_6561_(listtag1.m_128757_(l), l1);
         }
      }

      if (chunkstatus$chunktype == ChunkStatus.ChunkType.LEVELCHUNK) {
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkDataEvent.Load(chunkaccess, p_63462_, chunkstatus$chunktype));
         return new ImposterProtoChunk((LevelChunk)chunkaccess);
      } else {
         ProtoChunk protochunk1 = (ProtoChunk)chunkaccess;
         ListTag listtag4 = compoundtag.m_128437_("Entities", 10);

         for(int i2 = 0; i2 < listtag4.size(); ++i2) {
            protochunk1.m_63242_(listtag4.m_128728_(i2));
         }

         ListTag listtag5 = compoundtag.m_128437_("TileEntities", 10);

         for(int i1 = 0; i1 < listtag5.size(); ++i1) {
            CompoundTag compoundtag2 = listtag5.m_128728_(i1);
            chunkaccess.m_5604_(compoundtag2);
         }

         ListTag listtag6 = compoundtag.m_128437_("Lights", 9);

         for(int j2 = 0; j2 < listtag6.size(); ++j2) {
            ListTag listtag2 = listtag6.m_128744_(j2);

            for(int j1 = 0; j1 < listtag2.size(); ++j1) {
               protochunk1.m_63244_(listtag2.m_128757_(j1), j2);
            }
         }

         CompoundTag compoundtag5 = compoundtag.m_128469_("CarvingMasks");

         for(String s1 : compoundtag5.m_128431_()) {
            GenerationStep.Carving generationstep$carving = GenerationStep.Carving.valueOf(s1);
            protochunk1.m_63189_(generationstep$carving, BitSet.valueOf(compoundtag5.m_128463_(s1)));
         }

         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.ChunkDataEvent.Load(chunkaccess, p_63462_, chunkstatus$chunktype));
         return protochunk1;
      }
   }

   public static CompoundTag m_63454_(ServerLevel p_63455_, ChunkAccess p_63456_) {
      ChunkPos chunkpos = p_63456_.m_7697_();
      CompoundTag compoundtag = new CompoundTag();
      CompoundTag compoundtag1 = new CompoundTag();
      compoundtag.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());
      compoundtag.m_128365_("Level", compoundtag1);
      compoundtag1.m_128405_("xPos", chunkpos.f_45578_);
      compoundtag1.m_128405_("zPos", chunkpos.f_45579_);
      compoundtag1.m_128356_("LastUpdate", p_63455_.m_46467_());
      compoundtag1.m_128356_("InhabitedTime", p_63456_.m_6319_());
      compoundtag1.m_128359_("Status", p_63456_.m_6415_().m_62467_());
      UpgradeData upgradedata = p_63456_.m_7387_();
      if (!upgradedata.m_63331_()) {
         compoundtag1.m_128365_("UpgradeData", upgradedata.m_63346_());
      }

      LevelChunkSection[] alevelchunksection = p_63456_.m_7103_();
      ListTag listtag = new ListTag();
      LevelLightEngine levellightengine = p_63455_.m_7726_().m_7827_();
      boolean flag = p_63456_.m_6332_();

      for(int i = levellightengine.m_164447_(); i < levellightengine.m_164448_(); ++i) {
         int j = i;
         LevelChunkSection levelchunksection = Arrays.stream(alevelchunksection).filter((p_63453_) -> {
            return p_63453_ != null && SectionPos.m_123171_(p_63453_.m_63017_()) == j;
         }).findFirst().orElse(LevelChunk.f_62770_);
         DataLayer datalayer = levellightengine.m_75814_(LightLayer.BLOCK).m_8079_(SectionPos.m_123196_(chunkpos, j));
         DataLayer datalayer1 = levellightengine.m_75814_(LightLayer.SKY).m_8079_(SectionPos.m_123196_(chunkpos, j));
         if (levelchunksection != LevelChunk.f_62770_ || datalayer != null || datalayer1 != null) {
            CompoundTag compoundtag2 = new CompoundTag();
            compoundtag2.m_128344_("Y", (byte)(j & 255));
            if (levelchunksection != LevelChunk.f_62770_) {
               levelchunksection.m_63019_().m_63111_(compoundtag2, "Palette", "BlockStates");
            }

            if (datalayer != null && !datalayer.m_62575_()) {
               compoundtag2.m_128382_("BlockLight", datalayer.m_7877_());
            }

            if (datalayer1 != null && !datalayer1.m_62575_()) {
               compoundtag2.m_128382_("SkyLight", datalayer1.m_7877_());
            }

            listtag.add(compoundtag2);
         }
      }

      compoundtag1.m_128365_("Sections", listtag);
      if (flag) {
         compoundtag1.m_128379_("isLightOn", true);
      }

      ChunkBiomeContainer chunkbiomecontainer = p_63456_.m_6221_();
      if (chunkbiomecontainer != null) {
         compoundtag1.m_128385_("Biomes", chunkbiomecontainer.m_62131_());
      }

      ListTag listtag1 = new ListTag();

      for(BlockPos blockpos : p_63456_.m_5928_()) {
         CompoundTag compoundtag3 = p_63456_.m_8051_(blockpos);
         if (compoundtag3 != null) {
            listtag1.add(compoundtag3);
         }
      }

      compoundtag1.m_128365_("TileEntities", listtag1);
      if (p_63456_.m_6415_().m_62494_() == ChunkStatus.ChunkType.PROTOCHUNK) {
         ProtoChunk protochunk = (ProtoChunk)p_63456_;
         ListTag listtag2 = new ListTag();
         listtag2.addAll(protochunk.m_63293_());
         compoundtag1.m_128365_("Entities", listtag2);
         compoundtag1.m_128365_("Lights", m_63490_(protochunk.m_63291_()));
         CompoundTag compoundtag4 = new CompoundTag();

         for(GenerationStep.Carving generationstep$carving : GenerationStep.Carving.values()) {
            BitSet bitset = protochunk.m_6548_(generationstep$carving);
            if (bitset != null) {
               compoundtag4.m_128382_(generationstep$carving.toString(), bitset.toByteArray());
            }
         }

         compoundtag1.m_128365_("CarvingMasks", compoundtag4);
      }
      else {
          LevelChunk levelChunk = (LevelChunk) p_63456_;
          try {
              final CompoundTag capTag = levelChunk.writeCapsToNBT();
              if (capTag != null) compoundtag1.m_128365_("ForgeCaps", capTag);
          } catch (Exception exception) {
              LogManager.getLogger().error("A capability provider has thrown an exception trying to write state. It will not persist. Report this to the mod author", exception);
          }
      }

      TickList<Block> ticklist = p_63456_.m_5782_();
      if (ticklist instanceof ProtoTickList) {
         compoundtag1.m_128365_("ToBeTicked", ((ProtoTickList)ticklist).m_63316_());
      } else if (ticklist instanceof ChunkTickList) {
         compoundtag1.m_128365_("TileTicks", ((ChunkTickList)ticklist).m_45660_());
      } else {
         compoundtag1.m_128365_("TileTicks", p_63455_.m_6219_().m_47221_(chunkpos));
      }

      TickList<Fluid> ticklist1 = p_63456_.m_5783_();
      if (ticklist1 instanceof ProtoTickList) {
         compoundtag1.m_128365_("LiquidsToBeTicked", ((ProtoTickList)ticklist1).m_63316_());
      } else if (ticklist1 instanceof ChunkTickList) {
         compoundtag1.m_128365_("LiquidTicks", ((ChunkTickList)ticklist1).m_45660_());
      } else {
         compoundtag1.m_128365_("LiquidTicks", p_63455_.m_6217_().m_47221_(chunkpos));
      }

      compoundtag1.m_128365_("PostProcessing", m_63490_(p_63456_.m_6720_()));
      CompoundTag compoundtag5 = new CompoundTag();

      for(Entry<Heightmap.Types, Heightmap> entry : p_63456_.m_6890_()) {
         if (p_63456_.m_6415_().m_62500_().contains(entry.getKey())) {
            compoundtag5.m_128365_(entry.getKey().m_64294_(), new LongArrayTag(entry.getValue().m_64239_()));
         }
      }

      compoundtag1.m_128365_("Heightmaps", compoundtag5);
      compoundtag1.m_128365_("Structures", m_156513_(p_63455_, chunkpos, p_63456_.m_6633_(), p_63456_.m_7049_()));
      return compoundtag;
   }

   public static ChunkStatus.ChunkType m_63485_(@Nullable CompoundTag p_63486_) {
      if (p_63486_ != null) {
         ChunkStatus chunkstatus = ChunkStatus.m_62397_(p_63486_.m_128469_("Level").m_128461_("Status"));
         if (chunkstatus != null) {
            return chunkstatus.m_62494_();
         }
      }

      return ChunkStatus.ChunkType.PROTOCHUNK;
   }

   private static void m_156522_(ServerLevel p_156523_, CompoundTag p_156524_, LevelChunk p_156525_) {
      if (p_156524_.m_128425_("Entities", 9)) {
         ListTag listtag = p_156524_.m_128437_("Entities", 10);
         if (!listtag.isEmpty()) {
            p_156523_.m_143311_(EntityType.m_147045_(listtag, p_156523_));
         }
      }

      ListTag listtag1 = p_156524_.m_128437_("TileEntities", 10);

      for(int i = 0; i < listtag1.size(); ++i) {
         CompoundTag compoundtag = listtag1.m_128728_(i);
         boolean flag = compoundtag.m_128471_("keepPacked");
         if (flag) {
            p_156525_.m_5604_(compoundtag);
         } else {
            BlockPos blockpos = new BlockPos(compoundtag.m_128451_("x"), compoundtag.m_128451_("y"), compoundtag.m_128451_("z"));
            BlockEntity blockentity = BlockEntity.m_155241_(blockpos, p_156525_.m_8055_(blockpos), compoundtag);
            if (blockentity != null) {
               p_156525_.m_142169_(blockentity);
            }
         }
      }

   }

   private static CompoundTag m_156513_(ServerLevel p_156514_, ChunkPos p_156515_, Map<StructureFeature<?>, StructureStart<?>> p_156516_, Map<StructureFeature<?>, LongSet> p_156517_) {
      CompoundTag compoundtag = new CompoundTag();
      CompoundTag compoundtag1 = new CompoundTag();

      for(Entry<StructureFeature<?>, StructureStart<?>> entry : p_156516_.entrySet()) {
         compoundtag1.m_128365_(entry.getKey().m_67098_(), entry.getValue().m_163606_(p_156514_, p_156515_));
      }

      compoundtag.m_128365_("Starts", compoundtag1);
      CompoundTag compoundtag2 = new CompoundTag();

      for(Entry<StructureFeature<?>, LongSet> entry1 : p_156517_.entrySet()) {
         compoundtag2.m_128365_(entry1.getKey().m_67098_(), new LongArrayTag(entry1.getValue()));
      }

      compoundtag.m_128365_("References", compoundtag2);
      return compoundtag;
   }

   private static Map<StructureFeature<?>, StructureStart<?>> m_156518_(ServerLevel p_156519_, CompoundTag p_156520_, long p_156521_) {
      Map<StructureFeature<?>, StructureStart<?>> map = Maps.newHashMap();
      CompoundTag compoundtag = p_156520_.m_128469_("Starts");

      for(String s : compoundtag.m_128431_()) {
         String s1 = s.toLowerCase(Locale.ROOT);
         StructureFeature<?> structurefeature = StructureFeature.f_67012_.get(s1);
         if (structurefeature == null) {
            f_63449_.error("Unknown structure start: {}", (Object)s1);
         } else {
            StructureStart<?> structurestart = StructureFeature.m_160447_(p_156519_, compoundtag.m_128469_(s), p_156521_);
            if (structurestart != null) {
               map.put(structurefeature, structurestart);
            }
         }
      }

      return map;
   }

   private static Map<StructureFeature<?>, LongSet> m_63471_(ChunkPos p_63472_, CompoundTag p_63473_) {
      Map<StructureFeature<?>, LongSet> map = Maps.newHashMap();
      CompoundTag compoundtag = p_63473_.m_128469_("References");

      for(String s : compoundtag.m_128431_()) {
         String s1 = s.toLowerCase(Locale.ROOT);
         StructureFeature<?> structurefeature = StructureFeature.f_67012_.get(s1);
         if (structurefeature == null) {
            f_63449_.warn("Found reference to unknown structure '{}' in chunk {}, discarding", s1, p_63472_);
         } else {
            map.put(structurefeature, new LongOpenHashSet(Arrays.stream(compoundtag.m_128467_(s)).filter((p_156529_) -> {
               ChunkPos chunkpos = new ChunkPos(p_156529_);
               if (chunkpos.m_45594_(p_63472_) > 8) {
                  f_63449_.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", s1, chunkpos, p_63472_);
                  return false;
               } else {
                  return true;
               }
            }).toArray()));
         }
      }

      return map;
   }

   public static ListTag m_63490_(ShortList[] p_63491_) {
      ListTag listtag = new ListTag();

      for(ShortList shortlist : p_63491_) {
         ListTag listtag1 = new ListTag();
         if (shortlist != null) {
            for(Short oshort : shortlist) {
               listtag1.add(ShortTag.m_129258_(oshort));
            }
         }

         listtag.add(listtag1);
      }

      return listtag;
   }
}
