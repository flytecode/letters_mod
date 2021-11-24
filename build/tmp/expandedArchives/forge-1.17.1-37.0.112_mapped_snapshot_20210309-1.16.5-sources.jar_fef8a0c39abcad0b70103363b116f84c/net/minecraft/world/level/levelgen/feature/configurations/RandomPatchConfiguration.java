package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class RandomPatchConfiguration implements FeatureConfiguration {
   public static final Codec<RandomPatchConfiguration> f_67902_ = RecordCodecBuilder.create((p_67955_) -> {
      return p_67955_.group(BlockStateProvider.f_68747_.fieldOf("state_provider").forGetter((p_161075_) -> {
         return p_161075_.f_67903_;
      }), BlockPlacer.f_67480_.fieldOf("block_placer").forGetter((p_161073_) -> {
         return p_161073_.f_67904_;
      }), BlockState.f_61039_.listOf().fieldOf("whitelist").forGetter((p_161071_) -> {
         return p_161071_.f_67905_.stream().map(Block::m_49966_).collect(Collectors.toList());
      }), BlockState.f_61039_.listOf().fieldOf("blacklist").forGetter((p_161069_) -> {
         return ImmutableList.copyOf(p_161069_.f_67906_);
      }), ExtraCodecs.f_144629_.fieldOf("tries").orElse(128).forGetter((p_161067_) -> {
         return p_161067_.f_67907_;
      }), ExtraCodecs.f_144628_.fieldOf("xspread").orElse(7).forGetter((p_161065_) -> {
         return p_161065_.f_67908_;
      }), ExtraCodecs.f_144628_.fieldOf("yspread").orElse(3).forGetter((p_161063_) -> {
         return p_161063_.f_67909_;
      }), ExtraCodecs.f_144628_.fieldOf("zspread").orElse(7).forGetter((p_161061_) -> {
         return p_161061_.f_67910_;
      }), Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter((p_161059_) -> {
         return p_161059_.f_67911_;
      }), Codec.BOOL.fieldOf("project").orElse(true).forGetter((p_161057_) -> {
         return p_161057_.f_67912_;
      }), Codec.BOOL.fieldOf("need_water").orElse(false).forGetter((p_161055_) -> {
         return p_161055_.f_67913_;
      })).apply(p_67955_, RandomPatchConfiguration::new);
   });
   public final BlockStateProvider f_67903_;
   public final BlockPlacer f_67904_;
   public final Set<Block> f_67905_;
   public final Set<BlockState> f_67906_;
   public final int f_67907_;
   public final int f_67908_;
   public final int f_67909_;
   public final int f_67910_;
   public final boolean f_67911_;
   public final boolean f_67912_;
   public final boolean f_67913_;

   private RandomPatchConfiguration(BlockStateProvider p_67916_, BlockPlacer p_67917_, List<BlockState> p_67918_, List<BlockState> p_67919_, int p_67920_, int p_67921_, int p_67922_, int p_67923_, boolean p_67924_, boolean p_67925_, boolean p_67926_) {
      this(p_67916_, p_67917_, p_67918_.stream().map(BlockBehaviour.BlockStateBase::m_60734_).collect(Collectors.toSet()), ImmutableSet.copyOf(p_67919_), p_67920_, p_67921_, p_67922_, p_67923_, p_67924_, p_67925_, p_67926_);
   }

   RandomPatchConfiguration(BlockStateProvider p_67928_, BlockPlacer p_67929_, Set<Block> p_67930_, Set<BlockState> p_67931_, int p_67932_, int p_67933_, int p_67934_, int p_67935_, boolean p_67936_, boolean p_67937_, boolean p_67938_) {
      this.f_67903_ = p_67928_;
      this.f_67904_ = p_67929_;
      this.f_67905_ = p_67930_;
      this.f_67906_ = p_67931_;
      this.f_67907_ = p_67932_;
      this.f_67908_ = p_67933_;
      this.f_67909_ = p_67934_;
      this.f_67910_ = p_67935_;
      this.f_67911_ = p_67936_;
      this.f_67912_ = p_67937_;
      this.f_67913_ = p_67938_;
   }

   public static class GrassConfigurationBuilder {
      private final BlockStateProvider f_67976_;
      private final BlockPlacer f_67977_;
      private Set<Block> f_67978_ = ImmutableSet.of();
      private Set<BlockState> f_67979_ = ImmutableSet.of();
      private int f_67980_ = 64;
      private int f_67981_ = 7;
      private int f_67982_ = 3;
      private int f_67983_ = 7;
      private boolean f_67984_;
      private boolean f_67985_ = true;
      private boolean f_67986_;

      public GrassConfigurationBuilder(BlockStateProvider p_67988_, BlockPlacer p_67989_) {
         this.f_67976_ = p_67988_;
         this.f_67977_ = p_67989_;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67993_(Set<Block> p_67994_) {
         this.f_67978_ = p_67994_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67998_(Set<BlockState> p_67999_) {
         this.f_67979_ = p_67999_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67991_(int p_67992_) {
         this.f_67980_ = p_67992_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67996_(int p_67997_) {
         this.f_67981_ = p_67997_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_68001_(int p_68002_) {
         this.f_67982_ = p_68002_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_68004_(int p_68005_) {
         this.f_67983_ = p_68005_;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67990_() {
         this.f_67984_ = true;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_67995_() {
         this.f_67985_ = false;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder m_68000_() {
         this.f_67986_ = true;
         return this;
      }

      public RandomPatchConfiguration m_68003_() {
         return new RandomPatchConfiguration(this.f_67976_, this.f_67977_, this.f_67978_, this.f_67979_, this.f_67980_, this.f_67981_, this.f_67982_, this.f_67983_, this.f_67984_, this.f_67985_, this.f_67986_);
      }
   }
}