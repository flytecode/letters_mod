package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LavaSubmergedBlockProcessor extends StructureProcessor {
   public static final Codec<LavaSubmergedBlockProcessor> f_74134_;
   public static final LavaSubmergedBlockProcessor f_74135_ = new LavaSubmergedBlockProcessor();

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74140_, BlockPos p_74141_, BlockPos p_74142_, StructureTemplate.StructureBlockInfo p_74143_, StructureTemplate.StructureBlockInfo p_74144_, StructurePlaceSettings p_74145_) {
      BlockPos blockpos = p_74144_.f_74675_;
      boolean flag = p_74140_.m_8055_(blockpos).m_60713_(Blocks.f_49991_);
      return flag && !Block.m_49916_(p_74144_.f_74676_.m_60808_(p_74140_, blockpos)) ? new StructureTemplate.StructureBlockInfo(blockpos, Blocks.f_49991_.m_49966_(), p_74144_.f_74677_) : p_74144_;
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74464_;
   }

   static {
      f_74134_ = Codec.unit(() -> {
         return f_74135_;
      });
   }
}