package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class FeaturePoolElement extends StructurePoolElement {
   public static final Codec<FeaturePoolElement> f_68882_ = RecordCodecBuilder.create((p_68893_) -> {
      return p_68893_.group(ConfiguredFeature.f_65374_.fieldOf("feature").forGetter((p_161605_) -> {
         return p_161605_.f_68883_;
      }), m_69229_()).apply(p_68893_, FeaturePoolElement::new);
   });
   private final Supplier<ConfiguredFeature<?, ?>> f_68883_;
   private final CompoundTag f_68884_;

   protected FeaturePoolElement(Supplier<ConfiguredFeature<?, ?>> p_68887_, StructureTemplatePool.Projection p_68888_) {
      super(p_68888_);
      this.f_68883_ = p_68887_;
      this.f_68884_ = this.m_68917_();
   }

   private CompoundTag m_68917_() {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("name", "minecraft:bottom");
      compoundtag.m_128359_("final_state", "minecraft:air");
      compoundtag.m_128359_("pool", "minecraft:empty");
      compoundtag.m_128359_("target", "minecraft:empty");
      compoundtag.m_128359_("joint", JigsawBlockEntity.JointType.ROLLABLE.m_7912_());
      return compoundtag;
   }

   public Vec3i m_141911_(StructureManager p_161607_, Rotation p_161608_) {
      return Vec3i.f_123288_;
   }

   public List<StructureTemplate.StructureBlockInfo> m_6439_(StructureManager p_68913_, BlockPos p_68914_, Rotation p_68915_, Random p_68916_) {
      List<StructureTemplate.StructureBlockInfo> list = Lists.newArrayList();
      list.add(new StructureTemplate.StructureBlockInfo(p_68914_, Blocks.f_50678_.m_49966_().m_61124_(JigsawBlock.f_54222_, FrontAndTop.m_122622_(Direction.DOWN, Direction.SOUTH)), this.f_68884_));
      return list;
   }

   public BoundingBox m_6867_(StructureManager p_68909_, BlockPos p_68910_, Rotation p_68911_) {
      Vec3i vec3i = this.m_141911_(p_68909_, p_68911_);
      return new BoundingBox(p_68910_.m_123341_(), p_68910_.m_123342_(), p_68910_.m_123343_(), p_68910_.m_123341_() + vec3i.m_123341_(), p_68910_.m_123342_() + vec3i.m_123342_(), p_68910_.m_123343_() + vec3i.m_123343_());
   }

   public boolean m_6791_(StructureManager p_68895_, WorldGenLevel p_68896_, StructureFeatureManager p_68897_, ChunkGenerator p_68898_, BlockPos p_68899_, BlockPos p_68900_, Rotation p_68901_, BoundingBox p_68902_, Random p_68903_, boolean p_68904_) {
      return this.f_68883_.get().m_65385_(p_68896_, p_68898_, p_68903_, p_68899_);
   }

   public StructurePoolElementType<?> m_6379_() {
      return StructurePoolElementType.f_69235_;
   }

   public String toString() {
      return "Feature[" + Registry.f_122839_.m_7981_(this.f_68883_.get().m_65394_()) + "]";
   }
}