package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class BonusChestFeature extends Feature<NoneFeatureConfiguration> {
   public BonusChestFeature(Codec<NoneFeatureConfiguration> p_65299_) {
      super(p_65299_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159477_) {
      Random random = p_159477_.m_159776_();
      WorldGenLevel worldgenlevel = p_159477_.m_159774_();
      ChunkPos chunkpos = new ChunkPos(p_159477_.m_159777_());
      List<Integer> list = IntStream.rangeClosed(chunkpos.m_45604_(), chunkpos.m_45608_()).boxed().collect(Collectors.toList());
      Collections.shuffle(list, random);
      List<Integer> list1 = IntStream.rangeClosed(chunkpos.m_45605_(), chunkpos.m_45609_()).boxed().collect(Collectors.toList());
      Collections.shuffle(list1, random);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Integer integer : list) {
         for(Integer integer1 : list1) {
            blockpos$mutableblockpos.m_122178_(integer, 0, integer1);
            BlockPos blockpos = worldgenlevel.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos);
            if (worldgenlevel.m_46859_(blockpos) || worldgenlevel.m_8055_(blockpos).m_60812_(worldgenlevel, blockpos).m_83281_()) {
               worldgenlevel.m_7731_(blockpos, Blocks.f_50087_.m_49966_(), 2);
               RandomizableContainerBlockEntity.m_59620_(worldgenlevel, random, blockpos, BuiltInLootTables.f_78740_);
               BlockState blockstate = Blocks.f_50081_.m_49966_();

               for(Direction direction : Direction.Plane.HORIZONTAL) {
                  BlockPos blockpos1 = blockpos.m_142300_(direction);
                  if (blockstate.m_60710_(worldgenlevel, blockpos1)) {
                     worldgenlevel.m_7731_(blockpos1, blockstate, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}