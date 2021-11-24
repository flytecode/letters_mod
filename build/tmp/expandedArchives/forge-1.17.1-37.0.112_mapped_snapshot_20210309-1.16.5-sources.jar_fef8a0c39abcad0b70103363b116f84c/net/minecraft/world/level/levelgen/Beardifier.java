package net.minecraft.world.level.levelgen;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.JigsawJunction;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

public class Beardifier {
   public static final Beardifier f_158059_ = new Beardifier();
   public static final int f_158060_ = 12;
   private static final int f_158061_ = 24;
   private static final float[] f_158062_ = Util.m_137469_(new float[13824], (p_158082_) -> {
      for(int i = 0; i < 24; ++i) {
         for(int j = 0; j < 24; ++j) {
            for(int k = 0; k < 24; ++k) {
               p_158082_[i * 24 * 24 + j * 24 + k] = (float)m_158091_(j - 12, k - 12, i - 12);
            }
         }
      }

   });
   protected final ObjectList<StructurePiece> f_158063_;
   protected final ObjectList<JigsawJunction> f_158064_;
   protected final ObjectListIterator<StructurePiece> f_158065_;
   protected final ObjectListIterator<JigsawJunction> f_158066_;

   protected Beardifier(StructureFeatureManager p_158070_, ChunkAccess p_158071_) {
      ChunkPos chunkpos = p_158071_.m_7697_();
      int i = chunkpos.m_45604_();
      int j = chunkpos.m_45605_();
      this.f_158064_ = new ObjectArrayList<>(32);
      this.f_158063_ = new ObjectArrayList<>(10);

      for(StructureFeature<?> structurefeature : StructureFeature.f_67031_) {
         p_158070_.m_47289_(SectionPos.m_175562_(p_158071_), structurefeature).forEach((p_158080_) -> {
            for(StructurePiece structurepiece : p_158080_.m_73602_()) {
               if (structurepiece.m_73411_(chunkpos, 12)) {
                  if (structurepiece instanceof PoolElementStructurePiece) {
                     PoolElementStructurePiece poolelementstructurepiece = (PoolElementStructurePiece)structurepiece;
                     StructureTemplatePool.Projection structuretemplatepool$projection = poolelementstructurepiece.m_72645_().m_69230_();
                     if (structuretemplatepool$projection == StructureTemplatePool.Projection.RIGID) {
                        this.f_158063_.add(poolelementstructurepiece);
                     }

                     for(JigsawJunction jigsawjunction : poolelementstructurepiece.m_72648_()) {
                        int k = jigsawjunction.m_68930_();
                        int l = jigsawjunction.m_68936_();
                        if (k > i - 12 && l > j - 12 && k < i + 15 + 12 && l < j + 15 + 12) {
                           this.f_158064_.add(jigsawjunction);
                        }
                     }
                  } else {
                     this.f_158063_.add(structurepiece);
                  }
               }
            }

         });
      }

      this.f_158065_ = this.f_158063_.iterator();
      this.f_158066_ = this.f_158064_.iterator();
   }

   protected Beardifier() {
      this.f_158064_ = new ObjectArrayList<>();
      this.f_158063_ = new ObjectArrayList<>();
      this.f_158065_ = this.f_158063_.iterator();
      this.f_158066_ = this.f_158064_.iterator();
   }

   public double m_158072_(int p_158073_, int p_158074_, int p_158075_) {
      double d0 = 0.0D;

      while(this.f_158065_.hasNext()) {
         StructurePiece structurepiece = this.f_158065_.next();
         BoundingBox boundingbox = structurepiece.m_73547_();
         int i = Math.max(0, Math.max(boundingbox.m_162395_() - p_158073_, p_158073_ - boundingbox.m_162399_()));
         int j = p_158074_ - (boundingbox.m_162396_() + (structurepiece instanceof PoolElementStructurePiece ? ((PoolElementStructurePiece)structurepiece).m_72647_() : 0));
         int k = Math.max(0, Math.max(boundingbox.m_162398_() - p_158075_, p_158075_ - boundingbox.m_162401_()));
         NoiseEffect noiseeffect = structurepiece.m_142318_();
         if (noiseeffect == NoiseEffect.BURY) {
            d0 += m_158083_(i, j, k);
         } else if (noiseeffect == NoiseEffect.BEARD) {
            d0 += m_158087_(i, j, k) * 0.8D;
         }
      }

      this.f_158065_.back(this.f_158063_.size());

      while(this.f_158066_.hasNext()) {
         JigsawJunction jigsawjunction = this.f_158066_.next();
         int l = p_158073_ - jigsawjunction.m_68930_();
         int i1 = p_158074_ - jigsawjunction.m_68935_();
         int j1 = p_158075_ - jigsawjunction.m_68936_();
         d0 += m_158087_(l, i1, j1) * 0.4D;
      }

      this.f_158066_.back(this.f_158064_.size());
      return d0;
   }

   protected static double m_158083_(int p_158084_, int p_158085_, int p_158086_) {
      double d0 = Mth.m_144877_(p_158084_, (double)p_158085_ / 2.0D, p_158086_);
      return Mth.m_144851_(d0, 0.0D, 6.0D, 1.0D, 0.0D);
   }

   protected static double m_158087_(int p_158088_, int p_158089_, int p_158090_) {
      int i = p_158088_ + 12;
      int j = p_158089_ + 12;
      int k = p_158090_ + 12;
      if (i >= 0 && i < 24) {
         if (j >= 0 && j < 24) {
            return k >= 0 && k < 24 ? (double)f_158062_[k * 24 * 24 + i * 24 + j] : 0.0D;
         } else {
            return 0.0D;
         }
      } else {
         return 0.0D;
      }
   }

   private static double m_158091_(int p_158092_, int p_158093_, int p_158094_) {
      double d0 = (double)(p_158092_ * p_158092_ + p_158094_ * p_158094_);
      double d1 = (double)p_158093_ + 0.5D;
      double d2 = d1 * d1;
      double d3 = Math.pow(Math.E, -(d2 / 16.0D + d0 / 16.0D));
      double d4 = -d1 * Mth.m_14193_(d2 / 2.0D + d0 / 2.0D) / 2.0D;
      return d4 * d3;
   }
}