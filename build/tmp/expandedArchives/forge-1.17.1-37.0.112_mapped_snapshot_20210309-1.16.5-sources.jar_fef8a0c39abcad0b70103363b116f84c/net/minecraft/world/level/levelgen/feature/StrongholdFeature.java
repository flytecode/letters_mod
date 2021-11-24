package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureStart;
import net.minecraft.world.level.levelgen.structure.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class StrongholdFeature extends StructureFeature<NoneFeatureConfiguration> {
   public StrongholdFeature(Codec<NoneFeatureConfiguration> p_66928_) {
      super(p_66928_);
   }

   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> m_6258_() {
      return StrongholdFeature.StrongholdStart::new;
   }

   protected boolean m_142290_(ChunkGenerator p_160416_, BiomeSource p_160417_, long p_160418_, WorldgenRandom p_160419_, ChunkPos p_160420_, Biome p_160421_, ChunkPos p_160422_, NoneFeatureConfiguration p_160423_, LevelHeightAccessor p_160424_) {
      return p_160416_.m_62172_(p_160420_);
   }

   public static class StrongholdStart extends NoiseAffectingStructureStart<NoneFeatureConfiguration> {
      private final long f_66950_;

      public StrongholdStart(StructureFeature<NoneFeatureConfiguration> p_160426_, ChunkPos p_160427_, int p_160428_, long p_160429_) {
         super(p_160426_, p_160427_, p_160428_, p_160429_);
         this.f_66950_ = p_160429_;
      }

      public void m_142743_(RegistryAccess p_160439_, ChunkGenerator p_160440_, StructureManager p_160441_, ChunkPos p_160442_, Biome p_160443_, NoneFeatureConfiguration p_160444_, LevelHeightAccessor p_160445_) {
         int i = 0;

         StrongholdPieces.StartPiece strongholdpieces$startpiece;
         do {
            this.m_163626_();
            this.f_73564_.m_64703_(this.f_66950_ + (long)(i++), p_160442_.f_45578_, p_160442_.f_45579_);
            StrongholdPieces.m_72857_();
            strongholdpieces$startpiece = new StrongholdPieces.StartPiece(this.f_73564_, p_160442_.m_151382_(2), p_160442_.m_151391_(2));
            this.m_142679_(strongholdpieces$startpiece);
            strongholdpieces$startpiece.m_142537_(strongholdpieces$startpiece, this, this.f_73564_);
            List<StructurePiece> list = strongholdpieces$startpiece.f_73235_;

            while(!list.isEmpty()) {
               int j = this.f_73564_.nextInt(list.size());
               StructurePiece structurepiece = list.remove(j);
               structurepiece.m_142537_(strongholdpieces$startpiece, this, this.f_73564_);
            }

            this.m_163601_(p_160440_.m_6337_(), p_160440_.m_142062_(), this.f_73564_, 10);
         } while(this.m_163627_() || strongholdpieces$startpiece.f_73234_ == null);

      }
   }
}