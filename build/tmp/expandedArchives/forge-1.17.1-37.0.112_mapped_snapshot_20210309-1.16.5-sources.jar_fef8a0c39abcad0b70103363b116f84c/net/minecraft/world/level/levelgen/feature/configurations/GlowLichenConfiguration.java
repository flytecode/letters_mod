package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GlowLichenConfiguration implements FeatureConfiguration {
   public static final Codec<GlowLichenConfiguration> f_160869_ = RecordCodecBuilder.create((p_160891_) -> {
      return p_160891_.group(Codec.intRange(1, 64).fieldOf("search_range").orElse(10).forGetter((p_160903_) -> {
         return p_160903_.f_160870_;
      }), Codec.BOOL.fieldOf("can_place_on_floor").orElse(false).forGetter((p_160901_) -> {
         return p_160901_.f_160871_;
      }), Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(false).forGetter((p_160899_) -> {
         return p_160899_.f_160872_;
      }), Codec.BOOL.fieldOf("can_place_on_wall").orElse(false).forGetter((p_160897_) -> {
         return p_160897_.f_160873_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(0.5F).forGetter((p_160895_) -> {
         return p_160895_.f_160874_;
      }), BlockState.f_61039_.listOf().fieldOf("can_be_placed_on").forGetter((p_160893_) -> {
         return new ArrayList<>(p_160893_.f_160875_);
      })).apply(p_160891_, GlowLichenConfiguration::new);
   });
   public final int f_160870_;
   public final boolean f_160871_;
   public final boolean f_160872_;
   public final boolean f_160873_;
   public final float f_160874_;
   public final List<BlockState> f_160875_;
   public final List<Direction> f_160876_;

   public GlowLichenConfiguration(int p_160879_, boolean p_160880_, boolean p_160881_, boolean p_160882_, float p_160883_, List<BlockState> p_160884_) {
      this.f_160870_ = p_160879_;
      this.f_160871_ = p_160880_;
      this.f_160872_ = p_160881_;
      this.f_160873_ = p_160882_;
      this.f_160874_ = p_160883_;
      this.f_160875_ = p_160884_;
      List<Direction> list = Lists.newArrayList();
      if (p_160881_) {
         list.add(Direction.UP);
      }

      if (p_160880_) {
         list.add(Direction.DOWN);
      }

      if (p_160882_) {
         Direction.Plane.HORIZONTAL.forEach(list::add);
      }

      this.f_160876_ = Collections.unmodifiableList(list);
   }

   public boolean m_160885_(Block p_160886_) {
      return this.f_160875_.stream().anyMatch((p_160889_) -> {
         return p_160889_.m_60713_(p_160886_);
      });
   }
}