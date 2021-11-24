package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class OceanRuinFeature extends StructureFeature<OceanRuinConfiguration> {
   public OceanRuinFeature(Codec<OceanRuinConfiguration> p_72474_) {
      super(p_72474_);
   }

   public StructureFeature.StructureStartFactory<OceanRuinConfiguration> m_6258_() {
      return OceanRuinFeature.OceanRuinStart::new;
   }

   public static class OceanRuinStart extends StructureStart<OceanRuinConfiguration> {
      public OceanRuinStart(StructureFeature<OceanRuinConfiguration> p_163056_, ChunkPos p_163057_, int p_163058_, long p_163059_) {
         super(p_163056_, p_163057_, p_163058_, p_163059_);
      }

      public void m_142743_(RegistryAccess p_163069_, ChunkGenerator p_163070_, StructureManager p_163071_, ChunkPos p_163072_, Biome p_163073_, OceanRuinConfiguration p_163074_, LevelHeightAccessor p_163075_) {
         BlockPos blockpos = new BlockPos(p_163072_.m_45604_(), 90, p_163072_.m_45605_());
         Rotation rotation = Rotation.m_55956_(this.f_73564_);
         OceanRuinPieces.m_163078_(p_163071_, blockpos, rotation, this, this.f_73564_, p_163074_);
      }
   }

   public static enum Type implements StringRepresentable {
      WARM("warm"),
      COLD("cold");

      public static final Codec<OceanRuinFeature.Type> f_72501_ = StringRepresentable.m_14350_(OceanRuinFeature.Type::values, OceanRuinFeature.Type::m_72513_);
      private static final Map<String, OceanRuinFeature.Type> f_72502_ = Arrays.stream(values()).collect(Collectors.toMap(OceanRuinFeature.Type::m_72515_, (p_72512_) -> {
         return p_72512_;
      }));
      private final String f_72503_;

      private Type(String p_72509_) {
         this.f_72503_ = p_72509_;
      }

      public String m_72515_() {
         return this.f_72503_;
      }

      @Nullable
      public static OceanRuinFeature.Type m_72513_(String p_72514_) {
         return f_72502_.get(p_72514_);
      }

      public String m_7912_() {
         return this.f_72503_;
      }
   }
}