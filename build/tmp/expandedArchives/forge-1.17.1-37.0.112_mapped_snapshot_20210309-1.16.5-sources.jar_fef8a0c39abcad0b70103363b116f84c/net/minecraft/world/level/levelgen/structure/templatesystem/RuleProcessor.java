package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class RuleProcessor extends StructureProcessor {
   public static final Codec<RuleProcessor> f_74292_ = ProcessorRule.f_74215_.listOf().fieldOf("rules").xmap(RuleProcessor::new, (p_74306_) -> {
      return p_74306_.f_74293_;
   }).codec();
   private final ImmutableList<ProcessorRule> f_74293_;

   public RuleProcessor(List<? extends ProcessorRule> p_74296_) {
      this.f_74293_ = ImmutableList.copyOf(p_74296_);
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_74299_, BlockPos p_74300_, BlockPos p_74301_, StructureTemplate.StructureBlockInfo p_74302_, StructureTemplate.StructureBlockInfo p_74303_, StructurePlaceSettings p_74304_) {
      Random random = new Random(Mth.m_14057_(p_74303_.f_74675_));
      BlockState blockstate = p_74299_.m_8055_(p_74303_.f_74675_);

      for(ProcessorRule processorrule : this.f_74293_) {
         if (processorrule.m_74238_(p_74303_.f_74676_, blockstate, p_74302_.f_74675_, p_74303_.f_74675_, p_74301_, random)) {
            return new StructureTemplate.StructureBlockInfo(p_74303_.f_74675_, processorrule.m_74237_(), processorrule.m_74249_());
         }
      }

      return p_74303_;
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_74460_;
   }
}