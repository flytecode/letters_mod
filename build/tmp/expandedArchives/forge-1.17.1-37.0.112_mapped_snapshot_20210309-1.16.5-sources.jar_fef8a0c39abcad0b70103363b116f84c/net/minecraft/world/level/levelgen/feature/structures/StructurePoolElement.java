package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public abstract class StructurePoolElement {
   public static final Codec<StructurePoolElement> f_69153_ = Registry.f_122892_.dispatch("element_type", StructurePoolElement::m_6379_, StructurePoolElementType::m_69244_);
   @Nullable
   private volatile StructureTemplatePool.Projection f_69152_;

   protected static <E extends StructurePoolElement> RecordCodecBuilder<E, StructureTemplatePool.Projection> m_69229_() {
      return StructureTemplatePool.Projection.f_69281_.fieldOf("projection").forGetter(StructurePoolElement::m_69230_);
   }

   protected StructurePoolElement(StructureTemplatePool.Projection p_69156_) {
      this.f_69152_ = p_69156_;
   }

   public abstract Vec3i m_141911_(StructureManager p_161669_, Rotation p_161670_);

   public abstract List<StructureTemplate.StructureBlockInfo> m_6439_(StructureManager p_69186_, BlockPos p_69187_, Rotation p_69188_, Random p_69189_);

   public abstract BoundingBox m_6867_(StructureManager p_69183_, BlockPos p_69184_, Rotation p_69185_);

   public abstract boolean m_6791_(StructureManager p_69173_, WorldGenLevel p_69174_, StructureFeatureManager p_69175_, ChunkGenerator p_69176_, BlockPos p_69177_, BlockPos p_69178_, Rotation p_69179_, BoundingBox p_69180_, Random p_69181_, boolean p_69182_);

   public abstract StructurePoolElementType<?> m_6379_();

   public void m_69157_(LevelAccessor p_69158_, StructureTemplate.StructureBlockInfo p_69159_, BlockPos p_69160_, Rotation p_69161_, Random p_69162_, BoundingBox p_69163_) {
   }

   public StructurePoolElement m_5533_(StructureTemplatePool.Projection p_69169_) {
      this.f_69152_ = p_69169_;
      return this;
   }

   public StructureTemplatePool.Projection m_69230_() {
      StructureTemplatePool.Projection structuretemplatepool$projection = this.f_69152_;
      if (structuretemplatepool$projection == null) {
         throw new IllegalStateException();
      } else {
         return structuretemplatepool$projection;
      }
   }

   public int m_69231_() {
      return 1;
   }

   public static Function<StructureTemplatePool.Projection, EmptyPoolElement> m_69232_() {
      return (p_69213_) -> {
         return EmptyPoolElement.f_68856_;
      };
   }

   public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> m_69192_(String p_69193_) {
      return (p_69220_) -> {
         return new LegacySinglePoolElement(Either.left(new ResourceLocation(p_69193_)), () -> {
            return ProcessorLists.f_127198_;
         }, p_69220_);
      };
   }

   public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> m_69197_(String p_69198_, StructureProcessorList p_69199_) {
      return (p_69227_) -> {
         return new LegacySinglePoolElement(Either.left(new ResourceLocation(p_69198_)), () -> {
            return p_69199_;
         }, p_69227_);
      };
   }

   public static Function<StructureTemplatePool.Projection, SinglePoolElement> m_69216_(String p_69217_) {
      return (p_69196_) -> {
         return new SinglePoolElement(Either.left(new ResourceLocation(p_69217_)), () -> {
            return ProcessorLists.f_127198_;
         }, p_69196_);
      };
   }

   public static Function<StructureTemplatePool.Projection, SinglePoolElement> m_69221_(String p_69222_, StructureProcessorList p_69223_) {
      return (p_69203_) -> {
         return new SinglePoolElement(Either.left(new ResourceLocation(p_69222_)), () -> {
            return p_69223_;
         }, p_69203_);
      };
   }

   public static Function<StructureTemplatePool.Projection, FeaturePoolElement> m_69164_(ConfiguredFeature<?, ?> p_69165_) {
      return (p_69168_) -> {
         return new FeaturePoolElement(() -> {
            return p_69165_;
         }, p_69168_);
      };
   }

   public static Function<StructureTemplatePool.Projection, ListPoolElement> m_69204_(List<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>> p_69205_) {
      return (p_69208_) -> {
         return new ListPoolElement(p_69205_.stream().map((p_161668_) -> {
            return p_161668_.apply(p_69208_);
         }).collect(Collectors.toList()), p_69208_);
      };
   }
}