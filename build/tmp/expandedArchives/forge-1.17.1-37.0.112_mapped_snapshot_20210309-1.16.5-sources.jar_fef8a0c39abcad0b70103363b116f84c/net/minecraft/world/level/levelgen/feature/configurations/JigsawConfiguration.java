package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;

public class JigsawConfiguration implements FeatureConfiguration {
   public static final Codec<JigsawConfiguration> f_67756_ = RecordCodecBuilder.create((p_67764_) -> {
      return p_67764_.group(StructureTemplatePool.f_69246_.fieldOf("start_pool").forGetter(JigsawConfiguration::m_67766_), Codec.intRange(0, 7).fieldOf("size").forGetter(JigsawConfiguration::m_67765_)).apply(p_67764_, JigsawConfiguration::new);
   });
   private final Supplier<StructureTemplatePool> f_67757_;
   private final int f_67758_;

   public JigsawConfiguration(Supplier<StructureTemplatePool> p_67761_, int p_67762_) {
      this.f_67757_ = p_67761_;
      this.f_67758_ = p_67762_;
   }

   public int m_67765_() {
      return this.f_67758_;
   }

   public Supplier<StructureTemplatePool> m_67766_() {
      return this.f_67757_;
   }
}