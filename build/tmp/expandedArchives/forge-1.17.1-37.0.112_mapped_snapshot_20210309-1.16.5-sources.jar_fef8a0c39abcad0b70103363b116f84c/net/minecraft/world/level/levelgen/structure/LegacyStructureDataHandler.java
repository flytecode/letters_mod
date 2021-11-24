package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class LegacyStructureDataHandler {
   private static final Map<String, String> f_71299_ = Util.m_137469_(Maps.newHashMap(), (p_71337_) -> {
      p_71337_.put("Village", "Village");
      p_71337_.put("Mineshaft", "Mineshaft");
      p_71337_.put("Mansion", "Mansion");
      p_71337_.put("Igloo", "Temple");
      p_71337_.put("Desert_Pyramid", "Temple");
      p_71337_.put("Jungle_Pyramid", "Temple");
      p_71337_.put("Swamp_Hut", "Temple");
      p_71337_.put("Stronghold", "Stronghold");
      p_71337_.put("Monument", "Monument");
      p_71337_.put("Fortress", "Fortress");
      p_71337_.put("EndCity", "EndCity");
   });
   private static final Map<String, String> f_71300_ = Util.m_137469_(Maps.newHashMap(), (p_71325_) -> {
      p_71325_.put("Iglu", "Igloo");
      p_71325_.put("TeDP", "Desert_Pyramid");
      p_71325_.put("TeJP", "Jungle_Pyramid");
      p_71325_.put("TeSH", "Swamp_Hut");
   });
   private final boolean f_71301_;
   private final Map<String, Long2ObjectMap<CompoundTag>> f_71302_ = Maps.newHashMap();
   private final Map<String, StructureFeatureIndexSavedData> f_71303_ = Maps.newHashMap();
   private final List<String> f_71304_;
   private final List<String> f_71305_;

   public LegacyStructureDataHandler(@Nullable DimensionDataStorage p_71308_, List<String> p_71309_, List<String> p_71310_) {
      this.f_71304_ = p_71309_;
      this.f_71305_ = p_71310_;
      this.m_71320_(p_71308_);
      boolean flag = false;

      for(String s : this.f_71305_) {
         flag |= this.f_71302_.get(s) != null;
      }

      this.f_71301_ = flag;
   }

   public void m_71318_(long p_71319_) {
      for(String s : this.f_71304_) {
         StructureFeatureIndexSavedData structurefeatureindexsaveddata = this.f_71303_.get(s);
         if (structurefeatureindexsaveddata != null && structurefeatureindexsaveddata.m_73373_(p_71319_)) {
            structurefeatureindexsaveddata.m_73375_(p_71319_);
            structurefeatureindexsaveddata.m_77762_();
         }
      }

   }

   public CompoundTag m_71326_(CompoundTag p_71327_) {
      CompoundTag compoundtag = p_71327_.m_128469_("Level");
      ChunkPos chunkpos = new ChunkPos(compoundtag.m_128451_("xPos"), compoundtag.m_128451_("zPos"));
      if (this.m_71311_(chunkpos.f_45578_, chunkpos.f_45579_)) {
         p_71327_ = this.m_71328_(p_71327_, chunkpos);
      }

      CompoundTag compoundtag1 = compoundtag.m_128469_("Structures");
      CompoundTag compoundtag2 = compoundtag1.m_128469_("References");

      for(String s : this.f_71305_) {
         StructureFeature<?> structurefeature = StructureFeature.f_67012_.get(s.toLowerCase(Locale.ROOT));
         if (!compoundtag2.m_128425_(s, 12) && structurefeature != null) {
            int i = 8;
            LongList longlist = new LongArrayList();

            for(int j = chunkpos.f_45578_ - 8; j <= chunkpos.f_45578_ + 8; ++j) {
               for(int k = chunkpos.f_45579_ - 8; k <= chunkpos.f_45579_ + 8; ++k) {
                  if (this.m_71314_(j, k, s)) {
                     longlist.add(ChunkPos.m_45589_(j, k));
                  }
               }
            }

            compoundtag2.m_128428_(s, longlist);
         }
      }

      compoundtag1.m_128365_("References", compoundtag2);
      compoundtag.m_128365_("Structures", compoundtag1);
      p_71327_.m_128365_("Level", compoundtag);
      return p_71327_;
   }

   private boolean m_71314_(int p_71315_, int p_71316_, String p_71317_) {
      if (!this.f_71301_) {
         return false;
      } else {
         return this.f_71302_.get(p_71317_) != null && this.f_71303_.get(f_71299_.get(p_71317_)).m_73369_(ChunkPos.m_45589_(p_71315_, p_71316_));
      }
   }

   private boolean m_71311_(int p_71312_, int p_71313_) {
      if (!this.f_71301_) {
         return false;
      } else {
         for(String s : this.f_71305_) {
            if (this.f_71302_.get(s) != null && this.f_71303_.get(f_71299_.get(s)).m_73373_(ChunkPos.m_45589_(p_71312_, p_71313_))) {
               return true;
            }
         }

         return false;
      }
   }

   private CompoundTag m_71328_(CompoundTag p_71329_, ChunkPos p_71330_) {
      CompoundTag compoundtag = p_71329_.m_128469_("Level");
      CompoundTag compoundtag1 = compoundtag.m_128469_("Structures");
      CompoundTag compoundtag2 = compoundtag1.m_128469_("Starts");

      for(String s : this.f_71305_) {
         Long2ObjectMap<CompoundTag> long2objectmap = this.f_71302_.get(s);
         if (long2objectmap != null) {
            long i = p_71330_.m_45588_();
            if (this.f_71303_.get(f_71299_.get(s)).m_73373_(i)) {
               CompoundTag compoundtag3 = long2objectmap.get(i);
               if (compoundtag3 != null) {
                  compoundtag2.m_128365_(s, compoundtag3);
               }
            }
         }
      }

      compoundtag1.m_128365_("Starts", compoundtag2);
      compoundtag.m_128365_("Structures", compoundtag1);
      p_71329_.m_128365_("Level", compoundtag);
      return p_71329_;
   }

   private void m_71320_(@Nullable DimensionDataStorage p_71321_) {
      if (p_71321_ != null) {
         for(String s : this.f_71304_) {
            CompoundTag compoundtag = new CompoundTag();

            try {
               compoundtag = p_71321_.m_78158_(s, 1493).m_128469_("data").m_128469_("Features");
               if (compoundtag.m_128456_()) {
                  continue;
               }
            } catch (IOException ioexception) {
            }

            for(String s1 : compoundtag.m_128431_()) {
               CompoundTag compoundtag1 = compoundtag.m_128469_(s1);
               long i = ChunkPos.m_45589_(compoundtag1.m_128451_("ChunkX"), compoundtag1.m_128451_("ChunkZ"));
               ListTag listtag = compoundtag1.m_128437_("Children", 10);
               if (!listtag.isEmpty()) {
                  String s3 = listtag.m_128728_(0).m_128461_("id");
                  String s4 = f_71300_.get(s3);
                  if (s4 != null) {
                     compoundtag1.m_128359_("id", s4);
                  }
               }

               String s6 = compoundtag1.m_128461_("id");
               this.f_71302_.computeIfAbsent(s6, (p_71335_) -> {
                  return new Long2ObjectOpenHashMap();
               }).put(i, compoundtag1);
            }

            String s5 = s + "_index";
            StructureFeatureIndexSavedData structurefeatureindexsaveddata = p_71321_.m_164861_(StructureFeatureIndexSavedData::m_163534_, StructureFeatureIndexSavedData::new, s5);
            if (!structurefeatureindexsaveddata.m_73364_().isEmpty()) {
               this.f_71303_.put(s, structurefeatureindexsaveddata);
            } else {
               StructureFeatureIndexSavedData structurefeatureindexsaveddata1 = new StructureFeatureIndexSavedData();
               this.f_71303_.put(s, structurefeatureindexsaveddata1);

               for(String s2 : compoundtag.m_128431_()) {
                  CompoundTag compoundtag2 = compoundtag.m_128469_(s2);
                  structurefeatureindexsaveddata1.m_73365_(ChunkPos.m_45589_(compoundtag2.m_128451_("ChunkX"), compoundtag2.m_128451_("ChunkZ")));
               }

               structurefeatureindexsaveddata1.m_77762_();
            }
         }

      }
   }

   public static LegacyStructureDataHandler m_71331_(ResourceKey<Level> p_71332_, @Nullable DimensionDataStorage p_71333_) {
      if (p_71332_ == Level.f_46428_) {
         return new LegacyStructureDataHandler(p_71333_, ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"), ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument"));
      } else if (p_71332_ == Level.f_46429_) {
         List<String> list1 = ImmutableList.of("Fortress");
         return new LegacyStructureDataHandler(p_71333_, list1, list1);
      } else if (p_71332_ == Level.f_46430_) {
         List<String> list = ImmutableList.of("EndCity");
         return new LegacyStructureDataHandler(p_71333_, list, list);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", p_71332_));
      }
   }
}