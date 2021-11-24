package net.minecraft.world.level.levelgen.feature.structures;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface StructurePoolElementType<P extends StructurePoolElement> {
   StructurePoolElementType<SinglePoolElement> f_69233_ = m_69241_("single_pool_element", SinglePoolElement.f_69097_);
   StructurePoolElementType<ListPoolElement> f_69234_ = m_69241_("list_pool_element", ListPoolElement.f_69057_);
   StructurePoolElementType<FeaturePoolElement> f_69235_ = m_69241_("feature_pool_element", FeaturePoolElement.f_68882_);
   StructurePoolElementType<EmptyPoolElement> f_69236_ = m_69241_("empty_pool_element", EmptyPoolElement.f_68855_);
   StructurePoolElementType<LegacySinglePoolElement> f_69237_ = m_69241_("legacy_single_pool_element", LegacySinglePoolElement.f_69043_);

   Codec<P> m_69244_();

   static <P extends StructurePoolElement> StructurePoolElementType<P> m_69241_(String p_69242_, Codec<P> p_69243_) {
      return Registry.m_122961_(Registry.f_122892_, p_69242_, () -> {
         return p_69243_;
      });
   }
}