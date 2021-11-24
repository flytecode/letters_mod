package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;

public interface StructureProcessorType<P extends StructureProcessor> {
   StructureProcessorType<BlockIgnoreProcessor> f_74456_ = m_74476_("block_ignore", BlockIgnoreProcessor.f_74045_);
   StructureProcessorType<BlockRotProcessor> f_74457_ = m_74476_("block_rot", BlockRotProcessor.f_74074_);
   StructureProcessorType<GravityProcessor> f_74458_ = m_74476_("gravity", GravityProcessor.f_74100_);
   StructureProcessorType<JigsawReplacementProcessor> f_74459_ = m_74476_("jigsaw_replacement", JigsawReplacementProcessor.f_74121_);
   StructureProcessorType<RuleProcessor> f_74460_ = m_74476_("rule", RuleProcessor.f_74292_);
   StructureProcessorType<NopProcessor> f_74461_ = m_74476_("nop", NopProcessor.f_74174_);
   StructureProcessorType<BlockAgeProcessor> f_74462_ = m_74476_("block_age", BlockAgeProcessor.f_74009_);
   StructureProcessorType<BlackstoneReplaceProcessor> f_74463_ = m_74476_("blackstone_replace", BlackstoneReplaceProcessor.f_73993_);
   StructureProcessorType<LavaSubmergedBlockProcessor> f_74464_ = m_74476_("lava_submerged_block", LavaSubmergedBlockProcessor.f_74134_);
   StructureProcessorType<ProtectedBlockProcessor> f_163784_ = m_74476_("protected_blocks", ProtectedBlockProcessor.f_163749_);
   Codec<StructureProcessor> f_74465_ = Registry.f_122891_.dispatch("processor_type", StructureProcessor::m_6953_, StructureProcessorType::m_74481_);
   Codec<StructureProcessorList> f_74466_ = f_74465_.listOf().xmap(StructureProcessorList::new, StructureProcessorList::m_74425_);
   Codec<StructureProcessorList> f_74467_ = Codec.either(f_74466_.fieldOf("processors").codec(), f_74466_).xmap((p_74471_) -> {
      return p_74471_.map((p_163788_) -> {
         return p_163788_;
      }, (p_163786_) -> {
         return p_163786_;
      });
   }, Either::left);
   Codec<Supplier<StructureProcessorList>> f_74468_ = RegistryFileCodec.m_135589_(Registry.f_122883_, f_74467_);

   Codec<P> m_74481_();

   static <P extends StructureProcessor> StructureProcessorType<P> m_74476_(String p_74477_, Codec<P> p_74478_) {
      return Registry.m_122961_(Registry.f_122891_, p_74477_, () -> {
         return p_74478_;
      });
   }
}