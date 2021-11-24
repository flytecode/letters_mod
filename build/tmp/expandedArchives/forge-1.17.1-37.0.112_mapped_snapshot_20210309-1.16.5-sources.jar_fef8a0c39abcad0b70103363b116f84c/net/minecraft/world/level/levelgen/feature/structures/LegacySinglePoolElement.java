package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class LegacySinglePoolElement extends SinglePoolElement {
   public static final Codec<LegacySinglePoolElement> f_69043_ = RecordCodecBuilder.create((p_69055_) -> {
      return p_69055_.group(m_69149_(), m_69146_(), m_69229_()).apply(p_69055_, LegacySinglePoolElement::new);
   });

   protected LegacySinglePoolElement(Either<ResourceLocation, StructureTemplate> p_69046_, Supplier<StructureProcessorList> p_69047_, StructureTemplatePool.Projection p_69048_) {
      super(p_69046_, p_69047_, p_69048_);
   }

   protected StructurePlaceSettings m_8122_(Rotation p_69051_, BoundingBox p_69052_, boolean p_69053_) {
      StructurePlaceSettings structureplacesettings = super.m_8122_(p_69051_, p_69052_, p_69053_);
      structureplacesettings.m_74397_(BlockIgnoreProcessor.f_74046_);
      structureplacesettings.m_74383_(BlockIgnoreProcessor.f_74048_);
      return structureplacesettings;
   }

   public StructurePoolElementType<?> m_6379_() {
      return StructurePoolElementType.f_69237_;
   }

   public String toString() {
      return "LegacySingle[" + this.f_69098_ + "]";
   }
}