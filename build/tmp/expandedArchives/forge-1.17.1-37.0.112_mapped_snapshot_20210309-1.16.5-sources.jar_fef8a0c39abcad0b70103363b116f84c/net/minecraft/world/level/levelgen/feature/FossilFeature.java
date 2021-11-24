package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.mutable.MutableInt;

public class FossilFeature extends Feature<FossilFeatureConfiguration> {
   public FossilFeature(Codec<FossilFeatureConfiguration> p_65851_) {
      super(p_65851_);
   }

   public boolean m_142674_(FeaturePlaceContext<FossilFeatureConfiguration> p_159789_) {
      Random random = p_159789_.m_159776_();
      WorldGenLevel worldgenlevel = p_159789_.m_159774_();
      BlockPos blockpos = p_159789_.m_159777_();
      Rotation rotation = Rotation.m_55956_(random);
      FossilFeatureConfiguration fossilfeatureconfiguration = p_159789_.m_159778_();
      int i = random.nextInt(fossilfeatureconfiguration.f_159797_.size());
      StructureManager structuremanager = worldgenlevel.m_6018_().m_142572_().m_129909_();
      StructureTemplate structuretemplate = structuremanager.m_74341_(fossilfeatureconfiguration.f_159797_.get(i));
      StructureTemplate structuretemplate1 = structuremanager.m_74341_(fossilfeatureconfiguration.f_159798_.get(i));
      ChunkPos chunkpos = new ChunkPos(blockpos);
      BoundingBox boundingbox = new BoundingBox(chunkpos.m_45604_(), worldgenlevel.m_141937_(), chunkpos.m_45605_(), chunkpos.m_45608_(), worldgenlevel.m_151558_(), chunkpos.m_45609_());
      StructurePlaceSettings structureplacesettings = (new StructurePlaceSettings()).m_74379_(rotation).m_74381_(boundingbox).m_74390_(random);
      Vec3i vec3i = structuretemplate.m_163808_(rotation);
      int j = random.nextInt(16 - vec3i.m_123341_());
      int k = random.nextInt(16 - vec3i.m_123343_());
      int l = worldgenlevel.m_151558_();

      for(int i1 = 0; i1 < vec3i.m_123341_(); ++i1) {
         for(int j1 = 0; j1 < vec3i.m_123343_(); ++j1) {
            l = Math.min(l, worldgenlevel.m_6924_(Heightmap.Types.OCEAN_FLOOR_WG, blockpos.m_123341_() + i1 + j, blockpos.m_123343_() + j1 + k));
         }
      }

      int k1 = Math.max(l - 15 - random.nextInt(10), worldgenlevel.m_141937_() + 10);
      BlockPos blockpos1 = structuretemplate.m_74583_(blockpos.m_142082_(j, 0, k).m_175288_(k1), Mirror.NONE, rotation);
      if (m_159781_(worldgenlevel, structuretemplate.m_74633_(structureplacesettings, blockpos1)) > fossilfeatureconfiguration.f_159801_) {
         return false;
      } else {
         structureplacesettings.m_74394_();
         fossilfeatureconfiguration.f_159799_.get().m_74425_().forEach((p_159795_) -> {
            structureplacesettings.m_74383_(p_159795_);
         });
         structuretemplate.m_74536_(worldgenlevel, blockpos1, blockpos1, structureplacesettings, random, 4);
         structureplacesettings.m_74394_();
         fossilfeatureconfiguration.f_159800_.get().m_74425_().forEach((p_159792_) -> {
            structureplacesettings.m_74383_(p_159792_);
         });
         structuretemplate1.m_74536_(worldgenlevel, blockpos1, blockpos1, structureplacesettings, random, 4);
         return true;
      }
   }

   private static int m_159781_(WorldGenLevel p_159782_, BoundingBox p_159783_) {
      MutableInt mutableint = new MutableInt(0);
      p_159783_.m_162380_((p_159787_) -> {
         BlockState blockstate = p_159782_.m_8055_(p_159787_);
         if (blockstate.m_60795_() || blockstate.m_60713_(Blocks.f_49991_) || blockstate.m_60713_(Blocks.f_49990_)) {
            mutableint.add(1);
         }

      });
      return mutableint.getValue();
   }
}