package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class SinglePoolElement extends StructurePoolElement {
   private static final Codec<Either<ResourceLocation, StructureTemplate>> f_69096_ = Codec.of(SinglePoolElement::m_69113_, ResourceLocation.f_135803_.map(Either::left));
   public static final Codec<SinglePoolElement> f_69097_ = RecordCodecBuilder.create((p_69118_) -> {
      return p_69118_.group(m_69149_(), m_69146_(), m_69229_()).apply(p_69118_, SinglePoolElement::new);
   });
   protected final Either<ResourceLocation, StructureTemplate> f_69098_;
   protected final Supplier<StructureProcessorList> f_69099_;

   private static <T> DataResult<T> m_69113_(Either<ResourceLocation, StructureTemplate> p_69114_, DynamicOps<T> p_69115_, T p_69116_) {
      Optional<ResourceLocation> optional = p_69114_.left();
      return !optional.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : ResourceLocation.f_135803_.encode(optional.get(), p_69115_, p_69116_);
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Supplier<StructureProcessorList>> m_69146_() {
      return StructureProcessorType.f_74468_.fieldOf("processors").forGetter((p_69148_) -> {
         return p_69148_.f_69099_;
      });
   }

   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<ResourceLocation, StructureTemplate>> m_69149_() {
      return f_69096_.fieldOf("location").forGetter((p_69112_) -> {
         return p_69112_.f_69098_;
      });
   }

   protected SinglePoolElement(Either<ResourceLocation, StructureTemplate> p_69102_, Supplier<StructureProcessorList> p_69103_, StructureTemplatePool.Projection p_69104_) {
      super(p_69104_);
      this.f_69098_ = p_69102_;
      this.f_69099_ = p_69103_;
   }

   public SinglePoolElement(StructureTemplate p_69106_) {
      this(Either.right(p_69106_), () -> {
         return ProcessorLists.f_127198_;
      }, StructureTemplatePool.Projection.RIGID);
   }

   public Vec3i m_141911_(StructureManager p_161664_, Rotation p_161665_) {
      StructureTemplate structuretemplate = this.m_69119_(p_161664_);
      return structuretemplate.m_163808_(p_161665_);
   }

   private StructureTemplate m_69119_(StructureManager p_69120_) {
      return this.f_69098_.map(p_69120_::m_74341_, Function.identity());
   }

   public List<StructureTemplate.StructureBlockInfo> m_69141_(StructureManager p_69142_, BlockPos p_69143_, Rotation p_69144_, boolean p_69145_) {
      StructureTemplate structuretemplate = this.m_69119_(p_69142_);
      List<StructureTemplate.StructureBlockInfo> list = structuretemplate.m_74607_(p_69143_, (new StructurePlaceSettings()).m_74379_(p_69144_), Blocks.f_50677_, p_69145_);
      List<StructureTemplate.StructureBlockInfo> list1 = Lists.newArrayList();

      for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : list) {
         if (structuretemplate$structureblockinfo.f_74677_ != null) {
            StructureMode structuremode = StructureMode.valueOf(structuretemplate$structureblockinfo.f_74677_.m_128461_("mode"));
            if (structuremode == StructureMode.DATA) {
               list1.add(structuretemplate$structureblockinfo);
            }
         }
      }

      return list1;
   }

   public List<StructureTemplate.StructureBlockInfo> m_6439_(StructureManager p_69137_, BlockPos p_69138_, Rotation p_69139_, Random p_69140_) {
      StructureTemplate structuretemplate = this.m_69119_(p_69137_);
      List<StructureTemplate.StructureBlockInfo> list = structuretemplate.m_74607_(p_69138_, (new StructurePlaceSettings()).m_74379_(p_69139_), Blocks.f_50678_, true);
      Collections.shuffle(list, p_69140_);
      return list;
   }

   public BoundingBox m_6867_(StructureManager p_69133_, BlockPos p_69134_, Rotation p_69135_) {
      StructureTemplate structuretemplate = this.m_69119_(p_69133_);
      return structuretemplate.m_74633_((new StructurePlaceSettings()).m_74379_(p_69135_), p_69134_);
   }

   public boolean m_6791_(StructureManager p_69122_, WorldGenLevel p_69123_, StructureFeatureManager p_69124_, ChunkGenerator p_69125_, BlockPos p_69126_, BlockPos p_69127_, Rotation p_69128_, BoundingBox p_69129_, Random p_69130_, boolean p_69131_) {
      StructureTemplate structuretemplate = this.m_69119_(p_69122_);
      StructurePlaceSettings structureplacesettings = this.m_8122_(p_69128_, p_69129_, p_69131_);
      if (!structuretemplate.m_74536_(p_69123_, p_69126_, p_69127_, structureplacesettings, p_69130_, 18)) {
         return false;
      } else {
         for(StructureTemplate.StructureBlockInfo structuretemplate$structureblockinfo : StructureTemplate.processBlockInfos(p_69123_, p_69126_, p_69127_, structureplacesettings, this.m_69141_(p_69122_, p_69126_, p_69128_, false), structuretemplate)) {
            this.m_69157_(p_69123_, structuretemplate$structureblockinfo, p_69126_, p_69128_, p_69130_, p_69129_);
         }

         return true;
      }
   }

   protected StructurePlaceSettings m_8122_(Rotation p_69108_, BoundingBox p_69109_, boolean p_69110_) {
      StructurePlaceSettings structureplacesettings = new StructurePlaceSettings();
      structureplacesettings.m_74381_(p_69109_);
      structureplacesettings.m_74379_(p_69108_);
      structureplacesettings.m_74402_(true);
      structureplacesettings.m_74392_(false);
      structureplacesettings.m_74383_(BlockIgnoreProcessor.f_74046_);
      structureplacesettings.m_74405_(true);
      if (!p_69110_) {
         structureplacesettings.m_74383_(JigsawReplacementProcessor.f_74122_);
      }

      this.f_69099_.get().m_74425_().forEach(structureplacesettings::m_74383_);
      this.m_69230_().m_69298_().forEach(structureplacesettings::m_74383_);
      return structureplacesettings;
   }

   public StructurePoolElementType<?> m_6379_() {
      return StructurePoolElementType.f_69233_;
   }

   public String toString() {
      return "Single[" + this.f_69098_ + "]";
   }
}
