package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.structure.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ShipwreckFeature extends StructureFeature<ShipwreckConfiguration> {
   public ShipwreckFeature(Codec<ShipwreckConfiguration> p_66782_) {
      super(p_66782_);
   }

   public StructureFeature.StructureStartFactory<ShipwreckConfiguration> m_6258_() {
      return ShipwreckFeature.FeatureStart::new;
   }

   public static class FeatureStart extends StructureStart<ShipwreckConfiguration> {
      public FeatureStart(StructureFeature<ShipwreckConfiguration> p_160320_, ChunkPos p_160321_, int p_160322_, long p_160323_) {
         super(p_160320_, p_160321_, p_160322_, p_160323_);
      }

      public void m_142743_(RegistryAccess p_160333_, ChunkGenerator p_160334_, StructureManager p_160335_, ChunkPos p_160336_, Biome p_160337_, ShipwreckConfiguration p_160338_, LevelHeightAccessor p_160339_) {
         Rotation rotation = Rotation.m_55956_(this.f_73564_);
         BlockPos blockpos = new BlockPos(p_160336_.m_45604_(), 90, p_160336_.m_45605_());
         ShipwreckPieces.m_163200_(p_160335_, blockpos, rotation, this, this.f_73564_, p_160338_);
      }
   }
}