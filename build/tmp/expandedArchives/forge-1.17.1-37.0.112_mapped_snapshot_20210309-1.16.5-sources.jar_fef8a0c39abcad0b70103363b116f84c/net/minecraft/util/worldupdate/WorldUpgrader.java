package net.minecraft.util.worldupdate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldUpgrader {
   private static final Logger f_18797_ = LogManager.getLogger();
   private static final ThreadFactory f_18798_ = (new ThreadFactoryBuilder()).setDaemon(true).build();
   private final ImmutableSet<ResourceKey<Level>> f_18799_;
   private final boolean f_18800_;
   private final LevelStorageSource.LevelStorageAccess f_18801_;
   private final Thread f_18802_;
   private final DataFixer f_18803_;
   private volatile boolean f_18804_ = true;
   private volatile boolean f_18805_;
   private volatile float f_18806_;
   private volatile int f_18807_;
   private volatile int f_18808_;
   private volatile int f_18809_;
   private final Object2FloatMap<ResourceKey<Level>> f_18810_ = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap<>(Util.m_137583_()));
   private volatile Component f_18811_ = new TranslatableComponent("optimizeWorld.stage.counting");
   private static final Pattern f_18812_ = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final DimensionDataStorage f_18813_;

   public WorldUpgrader(LevelStorageSource.LevelStorageAccess p_18816_, DataFixer p_18817_, ImmutableSet<ResourceKey<Level>> p_18818_, boolean p_18819_) {
      this.f_18799_ = p_18818_;
      this.f_18800_ = p_18819_;
      this.f_18803_ = p_18817_;
      this.f_18801_ = p_18816_;
      this.f_18813_ = new DimensionDataStorage(new File(this.f_18801_.m_78299_(Level.f_46428_), "data"), p_18817_);
      this.f_18802_ = f_18798_.newThread(this::m_18838_);
      this.f_18802_.setUncaughtExceptionHandler((p_18825_, p_18826_) -> {
         f_18797_.error("Error upgrading world", p_18826_);
         this.f_18811_ = new TranslatableComponent("optimizeWorld.stage.failed");
         this.f_18805_ = true;
      });
      this.f_18802_.start();
   }

   public void m_18820_() {
      this.f_18804_ = false;

      try {
         this.f_18802_.join();
      } catch (InterruptedException interruptedexception) {
      }

   }

   private void m_18838_() {
      this.f_18807_ = 0;
      Builder<ResourceKey<Level>, ListIterator<ChunkPos>> builder = ImmutableMap.builder();

      for(ResourceKey<Level> resourcekey : this.f_18799_) {
         List<ChunkPos> list = this.m_18830_(resourcekey);
         builder.put(resourcekey, list.listIterator());
         this.f_18807_ += list.size();
      }

      if (this.f_18807_ == 0) {
         this.f_18805_ = true;
      } else {
         float f1 = (float)this.f_18807_;
         ImmutableMap<ResourceKey<Level>, ListIterator<ChunkPos>> immutablemap = builder.build();
         Builder<ResourceKey<Level>, ChunkStorage> builder1 = ImmutableMap.builder();

         for(ResourceKey<Level> resourcekey1 : this.f_18799_) {
            File file1 = this.f_18801_.m_78299_(resourcekey1);
            builder1.put(resourcekey1, new ChunkStorage(new File(file1, "region"), this.f_18803_, true));
         }

         ImmutableMap<ResourceKey<Level>, ChunkStorage> immutablemap1 = builder1.build();
         long i = Util.m_137550_();
         this.f_18811_ = new TranslatableComponent("optimizeWorld.stage.upgrading");

         while(this.f_18804_) {
            boolean flag = false;
            float f = 0.0F;

            for(ResourceKey<Level> resourcekey2 : this.f_18799_) {
               ListIterator<ChunkPos> listiterator = immutablemap.get(resourcekey2);
               ChunkStorage chunkstorage = immutablemap1.get(resourcekey2);
               if (listiterator.hasNext()) {
                  ChunkPos chunkpos = listiterator.next();
                  boolean flag1 = false;

                  try {
                     CompoundTag compoundtag = chunkstorage.m_63512_(chunkpos);
                     if (compoundtag != null) {
                        int j = ChunkStorage.m_63505_(compoundtag);
                        CompoundTag compoundtag1 = chunkstorage.m_63507_(resourcekey2, () -> {
                           return this.f_18813_;
                        }, compoundtag);
                        CompoundTag compoundtag2 = compoundtag1.m_128469_("Level");
                        ChunkPos chunkpos1 = new ChunkPos(compoundtag2.m_128451_("xPos"), compoundtag2.m_128451_("zPos"));
                        if (!chunkpos1.equals(chunkpos)) {
                           f_18797_.warn("Chunk {} has invalid position {}", chunkpos, chunkpos1);
                        }

                        boolean flag2 = j < SharedConstants.m_136187_().getWorldVersion();
                        if (this.f_18800_) {
                           flag2 = flag2 || compoundtag2.m_128441_("Heightmaps");
                           compoundtag2.m_128473_("Heightmaps");
                           flag2 = flag2 || compoundtag2.m_128441_("isLightOn");
                           compoundtag2.m_128473_("isLightOn");
                        }

                        if (flag2) {
                           chunkstorage.m_63502_(chunkpos, compoundtag1);
                           flag1 = true;
                        }
                     }
                  } catch (ReportedException reportedexception) {
                     Throwable throwable = reportedexception.getCause();
                     if (!(throwable instanceof IOException)) {
                        throw reportedexception;
                     }

                     f_18797_.error("Error upgrading chunk {}", chunkpos, throwable);
                  } catch (IOException ioexception1) {
                     f_18797_.error("Error upgrading chunk {}", chunkpos, ioexception1);
                  }

                  if (flag1) {
                     ++this.f_18808_;
                  } else {
                     ++this.f_18809_;
                  }

                  flag = true;
               }

               float f2 = (float)listiterator.nextIndex() / f1;
               this.f_18810_.put(resourcekey2, f2);
               f += f2;
            }

            this.f_18806_ = f;
            if (!flag) {
               this.f_18804_ = false;
            }
         }

         this.f_18811_ = new TranslatableComponent("optimizeWorld.stage.finished");

         for(ChunkStorage chunkstorage1 : immutablemap1.values()) {
            try {
               chunkstorage1.close();
            } catch (IOException ioexception) {
               f_18797_.error("Error upgrading chunk", (Throwable)ioexception);
            }
         }

         this.f_18813_.m_78151_();
         i = Util.m_137550_() - i;
         f_18797_.info("World optimizaton finished after {} ms", (long)i);
         this.f_18805_ = true;
      }
   }

   private List<ChunkPos> m_18830_(ResourceKey<Level> p_18831_) {
      File file1 = this.f_18801_.m_78299_(p_18831_);
      File file2 = new File(file1, "region");
      File[] afile = file2.listFiles((p_18822_, p_18823_) -> {
         return p_18823_.endsWith(".mca");
      });
      if (afile == null) {
         return ImmutableList.of();
      } else {
         List<ChunkPos> list = Lists.newArrayList();

         for(File file3 : afile) {
            Matcher matcher = f_18812_.matcher(file3.getName());
            if (matcher.matches()) {
               int i = Integer.parseInt(matcher.group(1)) << 5;
               int j = Integer.parseInt(matcher.group(2)) << 5;

               try {
                  RegionFile regionfile = new RegionFile(file3, file2, true);

                  try {
                     for(int k = 0; k < 32; ++k) {
                        for(int l = 0; l < 32; ++l) {
                           ChunkPos chunkpos = new ChunkPos(k + i, l + j);
                           if (regionfile.m_63673_(chunkpos)) {
                              list.add(chunkpos);
                           }
                        }
                     }
                  } catch (Throwable throwable1) {
                     try {
                        regionfile.close();
                     } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                     }

                     throw throwable1;
                  }

                  regionfile.close();
               } catch (Throwable throwable2) {
               }
            }
         }

         return list;
      }
   }

   public boolean m_18829_() {
      return this.f_18805_;
   }

   public ImmutableSet<ResourceKey<Level>> m_18832_() {
      return this.f_18799_;
   }

   public float m_18827_(ResourceKey<Level> p_18828_) {
      return this.f_18810_.getFloat(p_18828_);
   }

   public float m_18833_() {
      return this.f_18806_;
   }

   public int m_18834_() {
      return this.f_18807_;
   }

   public int m_18835_() {
      return this.f_18808_;
   }

   public int m_18836_() {
      return this.f_18809_;
   }

   public Component m_18837_() {
      return this.f_18811_;
   }
}