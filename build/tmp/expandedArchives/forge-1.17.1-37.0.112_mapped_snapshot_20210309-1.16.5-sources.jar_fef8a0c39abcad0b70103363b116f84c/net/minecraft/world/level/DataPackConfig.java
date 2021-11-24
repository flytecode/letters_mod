package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class DataPackConfig {
   public static final DataPackConfig f_45842_ = new DataPackConfig(ImmutableList.of("vanilla"), ImmutableList.of());
   public static final Codec<DataPackConfig> f_45843_ = RecordCodecBuilder.create((p_45854_) -> {
      return p_45854_.group(Codec.STRING.listOf().fieldOf("Enabled").forGetter((p_151457_) -> {
         return p_151457_.f_45844_;
      }), Codec.STRING.listOf().fieldOf("Disabled").forGetter((p_151455_) -> {
         return p_151455_.f_45845_;
      })).apply(p_45854_, DataPackConfig::new);
   });
   private final List<String> f_45844_;
   private final List<String> f_45845_;

   public DataPackConfig(List<String> p_45848_, List<String> p_45849_) {
      this.f_45844_ = com.google.common.collect.Lists.newArrayList(p_45848_);
      this.f_45845_ = ImmutableList.copyOf(p_45849_);
   }

   public List<String> m_45850_() {
      return this.f_45844_;
   }

   public List<String> m_45855_() {
      return this.f_45845_;
   }

   public void addModPacks(List<String> modPacks) {
      f_45844_.addAll(modPacks.stream().filter(p->!f_45844_.contains(p)).collect(java.util.stream.Collectors.toList()));
   }
}
