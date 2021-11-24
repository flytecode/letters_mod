package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;

public class BlockAgeProcessor extends StructureProcessor {
   public static final Codec<BlockAgeProcessor> f_74009_ = Codec.FLOAT.fieldOf("mossiness").xmap(BlockAgeProcessor::new, (p_74023_) -> {
      return p_74023_.f_74010_;
   }).codec();
   private static final float f_163720_ = 0.5F;
   private static final float f_163721_ = 0.5F;
   private static final float f_163722_ = 0.15F;
   private static final BlockState[] f_163723_ = new BlockState[]{Blocks.f_50404_.m_49966_(), Blocks.f_50411_.m_49966_()};
   private final float f_74010_;

   public BlockAgeProcessor(float p_74013_) {
      this.f_74010_ = p_74013_;
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74016_, BlockPos p_74017_, BlockPos p_74018_, StructureTemplate.StructureBlockInfo p_74019_, StructureTemplate.StructureBlockInfo p_74020_, StructurePlaceSettings p_74021_) {
      Random random = p_74021_.m_74399_(p_74020_.f_74675_);
      BlockState blockstate = p_74020_.f_74676_;
      BlockPos blockpos = p_74020_.f_74675_;
      BlockState blockstate1 = null;
      if (!blockstate.m_60713_(Blocks.f_50222_) && !blockstate.m_60713_(Blocks.f_50069_) && !blockstate.m_60713_(Blocks.f_50225_)) {
         if (blockstate.m_60620_(BlockTags.f_13030_)) {
            blockstate1 = this.m_74029_(random, p_74020_.f_74676_);
         } else if (blockstate.m_60620_(BlockTags.f_13031_)) {
            blockstate1 = this.m_74039_(random);
         } else if (blockstate.m_60620_(BlockTags.f_13032_)) {
            blockstate1 = this.m_74041_(random);
         } else if (blockstate.m_60713_(Blocks.f_50080_)) {
            blockstate1 = this.m_74043_(random);
         }
      } else {
         blockstate1 = this.m_74024_(random);
      }

      return blockstate1 != null ? new StructureTemplate.StructureBlockInfo(blockpos, blockstate1, p_74020_.f_74677_) : p_74020_;
   }

   @Nullable
   private BlockState m_74024_(Random p_74025_) {
      if (p_74025_.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] ablockstate = new BlockState[]{Blocks.f_50224_.m_49966_(), m_74026_(p_74025_, Blocks.f_50194_)};
         BlockState[] ablockstate1 = new BlockState[]{Blocks.f_50223_.m_49966_(), m_74026_(p_74025_, Blocks.f_50631_)};
         return this.m_74035_(p_74025_, ablockstate, ablockstate1);
      }
   }

   @Nullable
   private BlockState m_74029_(Random p_74030_, BlockState p_74031_) {
      Direction direction = p_74031_.m_61143_(StairBlock.f_56841_);
      Half half = p_74031_.m_61143_(StairBlock.f_56842_);
      if (p_74030_.nextFloat() >= 0.5F) {
         return null;
      } else {
         BlockState[] ablockstate = new BlockState[]{Blocks.f_50631_.m_49966_().m_61124_(StairBlock.f_56841_, direction).m_61124_(StairBlock.f_56842_, half), Blocks.f_50645_.m_49966_()};
         return this.m_74035_(p_74030_, f_163723_, ablockstate);
      }
   }

   @Nullable
   private BlockState m_74039_(Random p_74040_) {
      return p_74040_.nextFloat() < this.f_74010_ ? Blocks.f_50645_.m_49966_() : null;
   }

   @Nullable
   private BlockState m_74041_(Random p_74042_) {
      return p_74042_.nextFloat() < this.f_74010_ ? Blocks.f_50607_.m_49966_() : null;
   }

   @Nullable
   private BlockState m_74043_(Random p_74044_) {
      return p_74044_.nextFloat() < 0.15F ? Blocks.f_50723_.m_49966_() : null;
   }

   private static BlockState m_74026_(Random p_74027_, Block p_74028_) {
      return p_74028_.m_49966_().m_61124_(StairBlock.f_56841_, Direction.Plane.HORIZONTAL.m_122560_(p_74027_)).m_61124_(StairBlock.f_56842_, Half.values()[p_74027_.nextInt(Half.values().length)]);
   }

   private BlockState m_74035_(Random p_74036_, BlockState[] p_74037_, BlockState[] p_74038_) {
      return p_74036_.nextFloat() < this.f_74010_ ? m_74032_(p_74036_, p_74038_) : m_74032_(p_74036_, p_74037_);
   }

   private static BlockState m_74032_(Random p_74033_, BlockState[] p_74034_) {
      return p_74034_[p_74033_.nextInt(p_74034_.length)];
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74462_;
   }
}