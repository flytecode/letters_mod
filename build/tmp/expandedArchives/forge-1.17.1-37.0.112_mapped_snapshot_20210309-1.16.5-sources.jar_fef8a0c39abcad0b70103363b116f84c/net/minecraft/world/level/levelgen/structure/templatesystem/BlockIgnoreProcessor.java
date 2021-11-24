package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockIgnoreProcessor extends StructureProcessor {
   public static final Codec<BlockIgnoreProcessor> f_74045_ = BlockState.f_61039_.xmap(BlockBehaviour.BlockStateBase::m_60734_, Block::m_49966_).listOf().fieldOf("blocks").xmap(BlockIgnoreProcessor::new, (p_74062_) -> {
      return p_74062_.f_74049_;
   }).codec();
   public static final BlockIgnoreProcessor f_74046_ = new BlockIgnoreProcessor(ImmutableList.of(Blocks.f_50677_));
   public static final BlockIgnoreProcessor f_74047_ = new BlockIgnoreProcessor(ImmutableList.of(Blocks.f_50016_));
   public static final BlockIgnoreProcessor f_74048_ = new BlockIgnoreProcessor(ImmutableList.of(Blocks.f_50016_, Blocks.f_50677_));
   private final ImmutableList<Block> f_74049_;

   public BlockIgnoreProcessor(List<Block> p_74052_) {
      this.f_74049_ = ImmutableList.copyOf(p_74052_);
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74055_, BlockPos p_74056_, BlockPos p_74057_, StructureTemplate.StructureBlockInfo p_74058_, StructureTemplate.StructureBlockInfo p_74059_, StructurePlaceSettings p_74060_) {
      return this.f_74049_.contains(p_74059_.f_74676_.m_60734_()) ? null : p_74059_;
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74456_;
   }
}