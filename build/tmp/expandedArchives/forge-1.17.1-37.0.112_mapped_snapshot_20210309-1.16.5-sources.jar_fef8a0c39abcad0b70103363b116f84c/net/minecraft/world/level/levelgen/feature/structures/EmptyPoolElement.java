package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class EmptyPoolElement extends StructurePoolElement {
   public static final Codec<EmptyPoolElement> f_68855_;
   public static final EmptyPoolElement f_68856_ = new EmptyPoolElement();

   private EmptyPoolElement() {
      super(StructureTemplatePool.Projection.TERRAIN_MATCHING);
   }

   public Vec3i m_141911_(StructureManager p_161602_, Rotation p_161603_) {
      return Vec3i.f_123288_;
   }

   public List<StructureTemplate.StructureBlockInfo> m_6439_(StructureManager p_68876_, BlockPos p_68877_, Rotation p_68878_, Random p_68879_) {
      return Collections.emptyList();
   }

   public BoundingBox m_6867_(StructureManager p_68872_, BlockPos p_68873_, Rotation p_68874_) {
      throw new IllegalStateException("Invalid call to EmtyPoolElement.getBoundingBox, filter me!");
   }

   public boolean m_6791_(StructureManager p_68861_, WorldGenLevel p_68862_, StructureFeatureManager p_68863_, ChunkGenerator p_68864_, BlockPos p_68865_, BlockPos p_68866_, Rotation p_68867_, BoundingBox p_68868_, Random p_68869_, boolean p_68870_) {
      return true;
   }

   public StructurePoolElementType<?> m_6379_() {
      return StructurePoolElementType.f_69236_;
   }

   public String toString() {
      return "Empty";
   }

   static {
      f_68855_ = Codec.unit(() -> {
         return f_68856_;
      });
   }
}