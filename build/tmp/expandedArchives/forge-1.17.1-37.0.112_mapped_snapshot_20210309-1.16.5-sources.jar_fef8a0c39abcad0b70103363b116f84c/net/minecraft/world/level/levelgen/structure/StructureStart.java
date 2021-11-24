package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureStart<C extends FeatureConfiguration> implements StructurePieceAccessor {
   private static final Logger f_163591_ = LogManager.getLogger();
   public static final String f_163590_ = "INVALID";
   public static final StructureStart<?> f_73561_ = new StructureStart<MineshaftConfiguration>((StructureFeature)null, new ChunkPos(0, 0), 0, 0L) {
      public void m_142743_(RegistryAccess p_163644_, ChunkGenerator p_163645_, StructureManager p_163646_, ChunkPos p_163647_, Biome p_163648_, MineshaftConfiguration p_163649_, LevelHeightAccessor p_163650_) {
      }

      public boolean m_73603_() {
         return false;
      }
   };
   private final StructureFeature<C> f_73565_;
   protected final List<StructurePiece> f_73562_ = Lists.newArrayList();
   private final ChunkPos f_163592_;
   private int f_73568_;
   protected final WorldgenRandom f_73564_;
   @Nullable
   private BoundingBox f_163593_;

   public StructureStart(StructureFeature<C> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
      this.f_73565_ = p_163595_;
      this.f_163592_ = p_163596_;
      this.f_73568_ = p_163597_;
      this.f_73564_ = new WorldgenRandom();
      this.f_73564_.m_64703_(p_163598_, p_163596_.f_45578_, p_163596_.f_45579_);
   }

   public abstract void m_142743_(RegistryAccess p_163615_, ChunkGenerator p_163616_, StructureManager p_163617_, ChunkPos p_163618_, Biome p_163619_, C p_163620_, LevelHeightAccessor p_163621_);

   public final BoundingBox m_73601_() {
      if (this.f_163593_ == null) {
         this.f_163593_ = this.m_142516_();
      }

      return this.f_163593_;
   }

   protected BoundingBox m_142516_() {
      synchronized(this.f_73562_) {
         return BoundingBox.m_162388_(this.f_73562_.stream().map(StructurePiece::m_73547_)::iterator).orElseThrow(() -> {
            return new IllegalStateException("Unable to calculate boundingbox without pieces");
         });
      }
   }

   public List<StructurePiece> m_73602_() {
      return this.f_73562_;
   }

   public void m_7129_(WorldGenLevel p_73584_, StructureFeatureManager p_73585_, ChunkGenerator p_73586_, Random p_73587_, BoundingBox p_73588_, ChunkPos p_73589_) {
      synchronized(this.f_73562_) {
         if (!this.f_73562_.isEmpty()) {
            BoundingBox boundingbox = (this.f_73562_.get(0)).f_73383_;
            BlockPos blockpos = boundingbox.m_162394_();
            BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), boundingbox.m_162396_(), blockpos.m_123343_());
            Iterator<StructurePiece> iterator = this.f_73562_.iterator();

            while(iterator.hasNext()) {
               StructurePiece structurepiece = iterator.next();
               if (structurepiece.m_73547_().m_71049_(p_73588_) && !structurepiece.m_7832_(p_73584_, p_73585_, p_73586_, p_73587_, p_73588_, p_73589_, blockpos1)) {
                  iterator.remove();
               }
            }

         }
      }
   }

   public CompoundTag m_163606_(ServerLevel p_163607_, ChunkPos p_163608_) {
      CompoundTag compoundtag = new CompoundTag();
      if (this.m_73603_()) {
         if (Registry.f_122841_.m_7981_(this.m_73610_()) == null) { // FORGE: This is just a more friendly error instead of the 'Null String' below
            throw new RuntimeException("StructureStart \"" + this.getClass().getName() + "\": \"" + this.m_73610_() + "\" missing ID Mapping, Modder see MapGenStructureIO");
         }
         compoundtag.m_128359_("id", Registry.f_122841_.m_7981_(this.m_73610_()).toString());
         compoundtag.m_128405_("ChunkX", p_163608_.f_45578_);
         compoundtag.m_128405_("ChunkZ", p_163608_.f_45579_);
         compoundtag.m_128405_("references", this.f_73568_);
         ListTag listtag = new ListTag();
         synchronized(this.f_73562_) {
            for(StructurePiece structurepiece : this.f_73562_) {
               listtag.add(structurepiece.m_163549_(p_163607_));
            }
         }

         compoundtag.m_128365_("Children", listtag);
         return compoundtag;
      } else {
         compoundtag.m_128359_("id", "INVALID");
         return compoundtag;
      }
   }

   protected void m_163601_(int p_163602_, int p_163603_, Random p_163604_, int p_163605_) {
      int i = p_163602_ - p_163605_;
      BoundingBox boundingbox = this.m_73601_();
      int j = boundingbox.m_71057_() + p_163603_ + 1;
      if (j < i) {
         j += p_163604_.nextInt(i - j);
      }

      int k = j - boundingbox.m_162400_();
      this.m_163599_(k);
   }

   protected void m_73597_(Random p_73598_, int p_73599_, int p_73600_) {
      BoundingBox boundingbox = this.m_73601_();
      int i = p_73600_ - p_73599_ + 1 - boundingbox.m_71057_();
      int j;
      if (i > 1) {
         j = p_73599_ + p_73598_.nextInt(i);
      } else {
         j = p_73599_;
      }

      int k = j - boundingbox.m_162396_();
      this.m_163599_(k);
   }

   protected void m_163599_(int p_163600_) {
      for(StructurePiece structurepiece : this.f_73562_) {
         structurepiece.m_6324_(0, p_163600_, 0);
      }

      this.m_163628_();
   }

   private void m_163628_() {
      this.f_163593_ = null;
   }

   public boolean m_73603_() {
      return !this.f_73562_.isEmpty();
   }

   public ChunkPos m_163625_() {
      return this.f_163592_;
   }

   public BlockPos m_7148_() {
      return new BlockPos(this.f_163592_.m_45604_(), 0, this.f_163592_.m_45605_());
   }

   public boolean m_73606_() {
      return this.f_73568_ < this.m_73609_();
   }

   public void m_73607_() {
      ++this.f_73568_;
   }

   public int m_73608_() {
      return this.f_73568_;
   }

   protected int m_73609_() {
      return 1;
   }

   public StructureFeature<?> m_73610_() {
      return this.f_73565_;
   }

   public void m_142679_(StructurePiece p_163612_) {
      this.f_73562_.add(p_163612_);
      this.m_163628_();
   }

   @Nullable
   public StructurePiece m_141921_(BoundingBox p_163610_) {
      return m_163622_(this.f_73562_, p_163610_);
   }

   public void m_163626_() {
      this.f_73562_.clear();
      this.m_163628_();
   }

   public boolean m_163627_() {
      return this.f_73562_.isEmpty();
   }

   @Nullable
   public static StructurePiece m_163622_(List<StructurePiece> p_163623_, BoundingBox p_163624_) {
      for(StructurePiece structurepiece : p_163623_) {
         if (structurepiece.m_73547_().m_71049_(p_163624_)) {
            return structurepiece;
         }
      }

      return null;
   }

   protected boolean m_163613_(BlockPos p_163614_) {
      for(StructurePiece structurepiece : this.f_73562_) {
         if (structurepiece.m_73547_().m_71051_(p_163614_)) {
            return true;
         }
      }

      return false;
   }
}
