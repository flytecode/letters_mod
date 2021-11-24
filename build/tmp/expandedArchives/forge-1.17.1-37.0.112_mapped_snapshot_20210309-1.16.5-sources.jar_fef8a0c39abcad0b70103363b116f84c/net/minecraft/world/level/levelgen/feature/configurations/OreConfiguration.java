package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class OreConfiguration implements FeatureConfiguration {
   public static final Codec<OreConfiguration> f_67837_ = RecordCodecBuilder.create((p_67849_) -> {
      return p_67849_.group(Codec.list(OreConfiguration.TargetBlockState.f_161031_).fieldOf("targets").forGetter((p_161027_) -> {
         return p_161027_.f_161005_;
      }), Codec.intRange(0, 64).fieldOf("size").forGetter((p_161025_) -> {
         return p_161025_.f_67839_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter((p_161020_) -> {
         return p_161020_.f_161006_;
      })).apply(p_67849_, OreConfiguration::new);
   });
   public final List<OreConfiguration.TargetBlockState> f_161005_;
   public final int f_67839_;
   public final float f_161006_;

   public OreConfiguration(List<OreConfiguration.TargetBlockState> p_161016_, int p_161017_, float p_161018_) {
      this.f_67839_ = p_161017_;
      this.f_161005_ = p_161016_;
      this.f_161006_ = p_161018_;
   }

   public OreConfiguration(List<OreConfiguration.TargetBlockState> p_161013_, int p_161014_) {
      this(p_161013_, p_161014_, 0.0F);
   }

   public OreConfiguration(RuleTest p_161008_, BlockState p_161009_, int p_161010_, float p_161011_) {
      this(ImmutableList.of(new OreConfiguration.TargetBlockState(p_161008_, p_161009_)), p_161010_, p_161011_);
   }

   public OreConfiguration(RuleTest p_67843_, BlockState p_67844_, int p_67845_) {
      this(ImmutableList.of(new OreConfiguration.TargetBlockState(p_67843_, p_67844_)), p_67845_, 0.0F);
   }

   public static OreConfiguration.TargetBlockState m_161021_(RuleTest p_161022_, BlockState p_161023_) {
      return new OreConfiguration.TargetBlockState(p_161022_, p_161023_);
   }

   public static final class Predicates {
      public static final RuleTest f_67854_ = new TagMatchTest(BlockTags.f_13061_);
      public static final RuleTest f_161028_ = new TagMatchTest(BlockTags.f_144266_);
      public static final RuleTest f_161029_ = new TagMatchTest(BlockTags.f_144267_);
      public static final RuleTest f_67855_ = new BlockMatchTest(Blocks.f_50134_);
      public static final RuleTest f_67856_ = new TagMatchTest(BlockTags.f_13062_);
   }

   public static class TargetBlockState {
      public static final Codec<OreConfiguration.TargetBlockState> f_161031_ = RecordCodecBuilder.create((p_161039_) -> {
         return p_161039_.group(RuleTest.f_74307_.fieldOf("target").forGetter((p_161043_) -> {
            return p_161043_.f_161032_;
         }), BlockState.f_61039_.fieldOf("state").forGetter((p_161041_) -> {
            return p_161041_.f_161033_;
         })).apply(p_161039_, OreConfiguration.TargetBlockState::new);
      });
      public final RuleTest f_161032_;
      public final BlockState f_161033_;

      TargetBlockState(RuleTest p_161036_, BlockState p_161037_) {
         this.f_161032_ = p_161036_;
         this.f_161033_ = p_161037_;
      }
   }
}