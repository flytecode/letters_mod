package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;

public class GravityProcessor extends StructureProcessor {
   public static final Codec<GravityProcessor> f_74100_ = RecordCodecBuilder.create((p_74116_) -> {
      return p_74116_.group(Heightmap.Types.f_64274_.fieldOf("heightmap").orElse(Heightmap.Types.WORLD_SURFACE_WG).forGetter((p_163729_) -> {
         return p_163729_.f_74101_;
      }), Codec.INT.fieldOf("offset").orElse(0).forGetter((p_163727_) -> {
         return p_163727_.f_74102_;
      })).apply(p_74116_, GravityProcessor::new);
   });
   private final Heightmap.Types f_74101_;
   private final int f_74102_;

   public GravityProcessor(Heightmap.Types p_74105_, int p_74106_) {
      this.f_74101_ = p_74105_;
      this.f_74102_ = p_74106_;
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74109_, BlockPos p_74110_, BlockPos p_74111_, StructureTemplate.StructureBlockInfo p_74112_, StructureTemplate.StructureBlockInfo p_74113_, StructurePlaceSettings p_74114_) {
      Heightmap.Types heightmap$types;
      if (p_74109_ instanceof ServerLevel) {
         if (this.f_74101_ == Heightmap.Types.WORLD_SURFACE_WG) {
            heightmap$types = Heightmap.Types.WORLD_SURFACE;
         } else if (this.f_74101_ == Heightmap.Types.OCEAN_FLOOR_WG) {
            heightmap$types = Heightmap.Types.OCEAN_FLOOR;
         } else {
            heightmap$types = this.f_74101_;
         }
      } else {
         heightmap$types = this.f_74101_;
      }

      int i = p_74109_.m_6924_(heightmap$types, p_74113_.f_74675_.m_123341_(), p_74113_.f_74675_.m_123343_()) + this.f_74102_;
      int j = p_74112_.f_74675_.m_123342_();
      return new StructureTemplate.StructureBlockInfo(new BlockPos(p_74113_.f_74675_.m_123341_(), i + j, p_74113_.f_74675_.m_123343_()), p_74113_.f_74676_, p_74113_.f_74677_);
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74458_;
   }
}