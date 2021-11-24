package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class BlockRotProcessor extends StructureProcessor {
   public static final Codec<BlockRotProcessor> f_74074_ = Codec.FLOAT.fieldOf("integrity").orElse(1.0F).xmap(BlockRotProcessor::new, (p_74088_) -> {
      return p_74088_.f_74075_;
   }).codec();
   private final float f_74075_;

   public BlockRotProcessor(float p_74078_) {
      this.f_74075_ = p_74078_;
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74081_, BlockPos p_74082_, BlockPos p_74083_, StructureTemplate.StructureBlockInfo p_74084_, StructureTemplate.StructureBlockInfo p_74085_, StructurePlaceSettings p_74086_) {
      Random random = p_74086_.m_74399_(p_74085_.f_74675_);
      return !(this.f_74075_ >= 1.0F) && !(random.nextFloat() <= this.f_74075_) ? null : p_74085_;
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74457_;
   }
}