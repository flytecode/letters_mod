package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SimpleBlockConfiguration implements FeatureConfiguration {
   public static final Codec<SimpleBlockConfiguration> f_68068_ = RecordCodecBuilder.create((p_68082_) -> {
      return p_68082_.group(BlockStateProvider.f_68747_.fieldOf("to_place").forGetter((p_161168_) -> {
         return p_161168_.f_68069_;
      }), BlockState.f_61039_.listOf().fieldOf("place_on").orElse(ImmutableList.of()).forGetter((p_161166_) -> {
         return p_161166_.f_68070_;
      }), BlockState.f_61039_.listOf().fieldOf("place_in").orElse(ImmutableList.of()).forGetter((p_161164_) -> {
         return p_161164_.f_68071_;
      }), BlockState.f_61039_.listOf().fieldOf("place_under").orElse(ImmutableList.of()).forGetter((p_161162_) -> {
         return p_161162_.f_68072_;
      })).apply(p_68082_, SimpleBlockConfiguration::new);
   });
   public final BlockStateProvider f_68069_;
   public final List<BlockState> f_68070_;
   public final List<BlockState> f_68071_;
   public final List<BlockState> f_68072_;

   public SimpleBlockConfiguration(BlockStateProvider p_161157_, List<BlockState> p_161158_, List<BlockState> p_161159_, List<BlockState> p_161160_) {
      this.f_68069_ = p_161157_;
      this.f_68070_ = p_161158_;
      this.f_68071_ = p_161159_;
      this.f_68072_ = p_161160_;
   }

   public SimpleBlockConfiguration(BlockStateProvider p_161155_) {
      this(p_161155_, ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
   }
}