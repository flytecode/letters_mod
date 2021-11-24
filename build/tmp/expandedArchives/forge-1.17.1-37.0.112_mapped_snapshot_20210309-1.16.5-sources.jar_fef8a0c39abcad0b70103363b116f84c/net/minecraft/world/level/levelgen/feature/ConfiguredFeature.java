package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Decoratable;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfiguredFeature<FC extends FeatureConfiguration, F extends Feature<FC>> implements Decoratable<ConfiguredFeature<?, ?>> {
   public static final Codec<ConfiguredFeature<?, ?>> f_65373_ = Registry.f_122839_.dispatch((p_65391_) -> {
      return p_65391_.f_65377_;
   }, Feature::m_65787_);
   public static final Codec<Supplier<ConfiguredFeature<?, ?>>> f_65374_ = RegistryFileCodec.m_135589_(Registry.f_122881_, f_65373_);
   public static final Codec<List<Supplier<ConfiguredFeature<?, ?>>>> f_65375_ = RegistryFileCodec.m_135600_(Registry.f_122881_, f_65373_);
   public static final Logger f_65376_ = LogManager.getLogger();
   public final F f_65377_;
   public final FC f_65378_;

   public ConfiguredFeature(F p_65381_, FC p_65382_) {
      this.f_65377_ = p_65381_;
      this.f_65378_ = p_65382_;
   }

   public F m_65394_() {
      return this.f_65377_;
   }

   public FC m_65397_() {
      return this.f_65378_;
   }

   public ConfiguredFeature<?, ?> m_7679_(ConfiguredDecorator<?> p_65396_) {
      return Feature.f_65758_.m_65815_(new DecoratedFeatureConfiguration(() -> {
         return this;
      }, p_65396_));
   }

   public WeightedConfiguredFeature m_65383_(float p_65384_) {
      return new WeightedConfiguredFeature(this, p_65384_);
   }

   public boolean m_65385_(WorldGenLevel p_65386_, ChunkGenerator p_65387_, Random p_65388_, BlockPos p_65389_) {
      return this.f_65377_.m_142674_(new FeaturePlaceContext<>(p_65386_, p_65387_, p_65388_, p_65389_, this.f_65378_));
   }

   public Stream<ConfiguredFeature<?, ?>> m_65398_() {
      return Stream.concat(Stream.of(this), this.f_65378_.m_7817_());
   }

   public String toString() {
      return BuiltinRegistries.f_123861_.m_7854_(this).map(Objects::toString).orElseGet(() -> {
         return f_65373_.encodeStart(JsonOps.INSTANCE, this).toString();
      });
   }
}