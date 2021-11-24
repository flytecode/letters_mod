package net.minecraft.world.level.levelgen;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.BitStorage;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Heightmap {
   private static final Logger f_158363_ = LogManager.getLogger();
   static final Predicate<BlockState> f_64230_ = (p_64263_) -> {
      return !p_64263_.m_60795_();
   };
   static final Predicate<BlockState> f_64231_ = (p_64255_) -> {
      return p_64255_.m_60767_().m_76334_();
   };
   private final BitStorage f_64232_;
   private final Predicate<BlockState> f_64233_;
   private final ChunkAccess f_64234_;

   public Heightmap(ChunkAccess p_64237_, Heightmap.Types p_64238_) {
      this.f_64233_ = p_64238_.m_64299_();
      this.f_64234_ = p_64237_;
      int i = Mth.m_14163_(p_64237_.m_141928_() + 1);
      this.f_64232_ = new BitStorage(i, 256);
   }

   public static void m_64256_(ChunkAccess p_64257_, Set<Heightmap.Types> p_64258_) {
      int i = p_64258_.size();
      ObjectList<Heightmap> objectlist = new ObjectArrayList<>(i);
      ObjectListIterator<Heightmap> objectlistiterator = objectlist.iterator();
      int j = p_64257_.m_62098_() + 16;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int k = 0; k < 16; ++k) {
         for(int l = 0; l < 16; ++l) {
            for(Heightmap.Types heightmap$types : p_64258_) {
               objectlist.add(p_64257_.m_6005_(heightmap$types));
            }

            for(int i1 = j - 1; i1 >= p_64257_.m_141937_(); --i1) {
               blockpos$mutableblockpos.m_122178_(k, i1, l);
               BlockState blockstate = p_64257_.m_8055_(blockpos$mutableblockpos);
               if (!blockstate.m_60713_(Blocks.f_50016_)) {
                  while(objectlistiterator.hasNext()) {
                     Heightmap heightmap = objectlistiterator.next();
                     if (heightmap.f_64233_.test(blockstate)) {
                        heightmap.m_64245_(k, l, i1 + 1);
                        objectlistiterator.remove();
                     }
                  }

                  if (objectlist.isEmpty()) {
                     break;
                  }

                  objectlistiterator.back(i);
               }
            }
         }
      }

   }

   public boolean m_64249_(int p_64250_, int p_64251_, int p_64252_, BlockState p_64253_) {
      int i = this.m_64242_(p_64250_, p_64252_);
      if (p_64251_ <= i - 2) {
         return false;
      } else {
         if (this.f_64233_.test(p_64253_)) {
            if (p_64251_ >= i) {
               this.m_64245_(p_64250_, p_64252_, p_64251_ + 1);
               return true;
            }
         } else if (i - 1 == p_64251_) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int j = p_64251_ - 1; j >= this.f_64234_.m_141937_(); --j) {
               blockpos$mutableblockpos.m_122178_(p_64250_, j, p_64252_);
               if (this.f_64233_.test(this.f_64234_.m_8055_(blockpos$mutableblockpos))) {
                  this.m_64245_(p_64250_, p_64252_, j + 1);
                  return true;
               }
            }

            this.m_64245_(p_64250_, p_64252_, this.f_64234_.m_141937_());
            return true;
         }

         return false;
      }
   }

   public int m_64242_(int p_64243_, int p_64244_) {
      return this.m_64240_(m_64265_(p_64243_, p_64244_));
   }

   public int m_158368_(int p_158369_, int p_158370_) {
      return this.m_64240_(m_64265_(p_158369_, p_158370_)) - 1;
   }

   private int m_64240_(int p_64241_) {
      return this.f_64232_.m_13514_(p_64241_) + this.f_64234_.m_141937_();
   }

   private void m_64245_(int p_64246_, int p_64247_, int p_64248_) {
      this.f_64232_.m_13524_(m_64265_(p_64246_, p_64247_), p_64248_ - this.f_64234_.m_141937_());
   }

   public void m_158364_(ChunkAccess p_158365_, Heightmap.Types p_158366_, long[] p_158367_) {
      long[] along = this.f_64232_.m_13513_();
      if (along.length == p_158367_.length) {
         System.arraycopy(p_158367_, 0, along, 0, p_158367_.length);
      } else {
         f_158363_.warn("Ignoring heightmap data for chunk " + p_158365_.m_7697_() + ", size does not match; expected: " + along.length + ", got: " + p_158367_.length);
         m_64256_(p_158365_, EnumSet.of(p_158366_));
      }
   }

   public long[] m_64239_() {
      return this.f_64232_.m_13513_();
   }

   private static int m_64265_(int p_64266_, int p_64267_) {
      return p_64266_ + p_64267_ * 16;
   }

   public static enum Types implements StringRepresentable {
      WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Usage.WORLDGEN, Heightmap.f_64230_),
      WORLD_SURFACE("WORLD_SURFACE", Heightmap.Usage.CLIENT, Heightmap.f_64230_),
      OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Usage.WORLDGEN, Heightmap.f_64231_),
      OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Usage.LIVE_WORLD, Heightmap.f_64231_),
      MOTION_BLOCKING("MOTION_BLOCKING", Heightmap.Usage.CLIENT, (p_64296_) -> {
         return p_64296_.m_60767_().m_76334_() || !p_64296_.m_60819_().m_76178_();
      }),
      MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", Heightmap.Usage.LIVE_WORLD, (p_64289_) -> {
         return (p_64289_.m_60767_().m_76334_() || !p_64289_.m_60819_().m_76178_()) && !(p_64289_.m_60734_() instanceof LeavesBlock);
      });

      public static final Codec<Heightmap.Types> f_64274_ = StringRepresentable.m_14350_(Heightmap.Types::values, Heightmap.Types::m_64290_);
      private final String f_64275_;
      private final Heightmap.Usage f_64276_;
      private final Predicate<BlockState> f_64277_;
      private static final Map<String, Heightmap.Types> f_64278_ = Util.m_137469_(Maps.newHashMap(), (p_64293_) -> {
         for(Heightmap.Types heightmap$types : values()) {
            p_64293_.put(heightmap$types.f_64275_, heightmap$types);
         }

      });

      private Types(String p_64284_, Heightmap.Usage p_64285_, Predicate<BlockState> p_64286_) {
         this.f_64275_ = p_64284_;
         this.f_64276_ = p_64285_;
         this.f_64277_ = p_64286_;
      }

      public String m_64294_() {
         return this.f_64275_;
      }

      public boolean m_64297_() {
         return this.f_64276_ == Heightmap.Usage.CLIENT;
      }

      public boolean m_64298_() {
         return this.f_64276_ != Heightmap.Usage.WORLDGEN;
      }

      @Nullable
      public static Heightmap.Types m_64290_(String p_64291_) {
         return f_64278_.get(p_64291_);
      }

      public Predicate<BlockState> m_64299_() {
         return this.f_64277_;
      }

      public String m_7912_() {
         return this.f_64275_;
      }
   }

   public static enum Usage {
      WORLDGEN,
      LIVE_WORLD,
      CLIENT;
   }
}