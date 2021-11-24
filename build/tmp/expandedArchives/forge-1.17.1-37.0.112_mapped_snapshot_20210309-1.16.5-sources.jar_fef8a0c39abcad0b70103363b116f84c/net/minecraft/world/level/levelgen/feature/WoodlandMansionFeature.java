package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class WoodlandMansionFeature extends StructureFeature<NoneFeatureConfiguration> {
   public WoodlandMansionFeature(Codec<NoneFeatureConfiguration> p_67427_) {
      super(p_67427_);
   }

   protected boolean m_5910_() {
      return false;
   }

   protected boolean m_142290_(ChunkGenerator p_160677_, BiomeSource p_160678_, long p_160679_, WorldgenRandom p_160680_, ChunkPos p_160681_, Biome p_160682_, ChunkPos p_160683_, NoneFeatureConfiguration p_160684_, LevelHeightAccessor p_160685_) {
      for(Biome biome : p_160678_.m_7901_(p_160681_.m_151382_(9), p_160677_.m_6337_(), p_160681_.m_151391_(9), 32)) {
         if (!biome.m_47536_().m_47808_(this)) {
            return false;
         }
      }

      return true;
   }

   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> m_6258_() {
      return WoodlandMansionFeature.WoodlandMansionStart::new;
   }

   public static class WoodlandMansionStart extends StructureStart<NoneFeatureConfiguration> {
      public WoodlandMansionStart(StructureFeature<NoneFeatureConfiguration> p_160687_, ChunkPos p_160688_, int p_160689_, long p_160690_) {
         super(p_160687_, p_160688_, p_160689_, p_160690_);
      }

      public void m_142743_(RegistryAccess p_160700_, ChunkGenerator p_160701_, StructureManager p_160702_, ChunkPos p_160703_, Biome p_160704_, NoneFeatureConfiguration p_160705_, LevelHeightAccessor p_160706_) {
         Rotation rotation = Rotation.m_55956_(this.f_73564_);
         int i = 5;
         int j = 5;
         if (rotation == Rotation.CLOCKWISE_90) {
            i = -5;
         } else if (rotation == Rotation.CLOCKWISE_180) {
            i = -5;
            j = -5;
         } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            j = -5;
         }

         int k = p_160703_.m_151382_(7);
         int l = p_160703_.m_151391_(7);
         int i1 = p_160701_.m_156179_(k, l, Heightmap.Types.WORLD_SURFACE_WG, p_160706_);
         int j1 = p_160701_.m_156179_(k, l + j, Heightmap.Types.WORLD_SURFACE_WG, p_160706_);
         int k1 = p_160701_.m_156179_(k + i, l, Heightmap.Types.WORLD_SURFACE_WG, p_160706_);
         int l1 = p_160701_.m_156179_(k + i, l + j, Heightmap.Types.WORLD_SURFACE_WG, p_160706_);
         int i2 = Math.min(Math.min(i1, j1), Math.min(k1, l1));
         if (i2 >= 60) {
            BlockPos blockpos = new BlockPos(p_160703_.m_151382_(8), i2 + 1, p_160703_.m_151391_(8));
            List<WoodlandMansionPieces.WoodlandMansionPiece> list = Lists.newLinkedList();
            WoodlandMansionPieces.m_73691_(p_160702_, blockpos, rotation, list, this.f_73564_);
            list.forEach(this::m_142679_);
         }
      }

      public void m_7129_(WorldGenLevel p_67458_, StructureFeatureManager p_67459_, ChunkGenerator p_67460_, Random p_67461_, BoundingBox p_67462_, ChunkPos p_67463_) {
         super.m_7129_(p_67458_, p_67459_, p_67460_, p_67461_, p_67462_, p_67463_);
         BoundingBox boundingbox = this.m_73601_();
         int i = boundingbox.m_162396_();

         for(int j = p_67462_.m_162395_(); j <= p_67462_.m_162399_(); ++j) {
            for(int k = p_67462_.m_162398_(); k <= p_67462_.m_162401_(); ++k) {
               BlockPos blockpos = new BlockPos(j, i, k);
               if (!p_67458_.m_46859_(blockpos) && boundingbox.m_71051_(blockpos) && this.m_163613_(blockpos)) {
                  for(int l = i - 1; l > 1; --l) {
                     BlockPos blockpos1 = new BlockPos(j, l, k);
                     if (!p_67458_.m_46859_(blockpos1) && !p_67458_.m_8055_(blockpos1).m_60767_().m_76332_()) {
                        break;
                     }

                     p_67458_.m_7731_(blockpos1, Blocks.f_50652_.m_49966_(), 2);
                  }
               }
            }
         }

      }
   }
}