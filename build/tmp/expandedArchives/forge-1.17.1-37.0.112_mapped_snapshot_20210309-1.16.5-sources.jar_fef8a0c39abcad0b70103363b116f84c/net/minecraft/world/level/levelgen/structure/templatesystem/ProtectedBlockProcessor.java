package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ProtectedBlockProcessor extends StructureProcessor {
   public final ResourceLocation f_163748_;
   public static final Codec<ProtectedBlockProcessor> f_163749_ = ResourceLocation.f_135803_.xmap(ProtectedBlockProcessor::new, (p_163762_) -> {
      return p_163762_.f_163748_;
   });

   public ProtectedBlockProcessor(ResourceLocation p_163752_) {
      this.f_163748_ = p_163752_;
   }

   @Nullable
   public StructureTemplate.StructureBlockInfo m_7382_(LevelReader p_163755_, BlockPos p_163756_, BlockPos p_163757_, StructureTemplate.StructureBlockInfo p_163758_, StructureTemplate.StructureBlockInfo p_163759_, StructurePlaceSettings p_163760_) {
      return Feature.m_159757_(this.f_163748_).test(p_163755_.m_8055_(p_163759_.f_74675_)) ? p_163759_ : null;
   }

   protected StructureProcessorType<?> m_6953_() {
      return StructureProcessorType.f_163784_;
   }
}