package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class HugeFungusConfiguration implements FeatureConfiguration {
   public static final Codec<HugeFungusConfiguration> f_65892_ = RecordCodecBuilder.create((p_65912_) -> {
      return p_65912_.group(BlockState.f_61039_.fieldOf("valid_base_block").forGetter((p_159875_) -> {
         return p_159875_.f_65897_;
      }), BlockState.f_61039_.fieldOf("stem_state").forGetter((p_159873_) -> {
         return p_159873_.f_65898_;
      }), BlockState.f_61039_.fieldOf("hat_state").forGetter((p_159871_) -> {
         return p_159871_.f_65899_;
      }), BlockState.f_61039_.fieldOf("decor_state").forGetter((p_159869_) -> {
         return p_159869_.f_65900_;
      }), Codec.BOOL.fieldOf("planted").orElse(false).forGetter((p_159867_) -> {
         return p_159867_.f_65901_;
      })).apply(p_65912_, HugeFungusConfiguration::new);
   });
   public static final HugeFungusConfiguration f_65893_ = new HugeFungusConfiguration(Blocks.f_50699_.m_49966_(), Blocks.f_50695_.m_49966_(), Blocks.f_50451_.m_49966_(), Blocks.f_50701_.m_49966_(), true);
   public static final HugeFungusConfiguration f_65894_;
   public static final HugeFungusConfiguration f_65895_ = new HugeFungusConfiguration(Blocks.f_50690_.m_49966_(), Blocks.f_50686_.m_49966_(), Blocks.f_50692_.m_49966_(), Blocks.f_50701_.m_49966_(), true);
   public static final HugeFungusConfiguration f_65896_;
   public final BlockState f_65897_;
   public final BlockState f_65898_;
   public final BlockState f_65899_;
   public final BlockState f_65900_;
   public final boolean f_65901_;

   public HugeFungusConfiguration(BlockState p_65904_, BlockState p_65905_, BlockState p_65906_, BlockState p_65907_, boolean p_65908_) {
      this.f_65897_ = p_65904_;
      this.f_65898_ = p_65905_;
      this.f_65899_ = p_65906_;
      this.f_65900_ = p_65907_;
      this.f_65901_ = p_65908_;
   }

   static {
      f_65894_ = new HugeFungusConfiguration(f_65893_.f_65897_, f_65893_.f_65898_, f_65893_.f_65899_, f_65893_.f_65900_, false);
      f_65896_ = new HugeFungusConfiguration(f_65895_.f_65897_, f_65895_.f_65898_, f_65895_.f_65899_, f_65895_.f_65900_, false);
   }
}