package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public class StructureSettings {
   public static final Codec<StructureSettings> f_64579_ = RecordCodecBuilder.create((p_64596_) -> {
      return p_64596_.group(StrongholdConfiguration.f_68148_.optionalFieldOf("stronghold").forGetter((p_158913_) -> {
         return Optional.ofNullable(p_158913_.f_64583_);
      }), Codec.simpleMap(Registry.f_122841_, StructureFeatureConfiguration.f_68162_, Registry.f_122841_).fieldOf("structures").forGetter((p_158911_) -> {
         return p_158911_.f_64582_;
      })).apply(p_64596_, StructureSettings::new);
   });
   public static final ImmutableMap<StructureFeature<?>, StructureFeatureConfiguration> f_64580_ = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().put(StructureFeature.f_67028_, new StructureFeatureConfiguration(32, 8, 10387312)).put(StructureFeature.f_67017_, new StructureFeatureConfiguration(32, 8, 14357617)).put(StructureFeature.f_67018_, new StructureFeatureConfiguration(32, 8, 14357618)).put(StructureFeature.f_67016_, new StructureFeatureConfiguration(32, 8, 14357619)).put(StructureFeature.f_67021_, new StructureFeatureConfiguration(32, 8, 14357620)).put(StructureFeature.f_67013_, new StructureFeatureConfiguration(32, 8, 165745296)).put(StructureFeature.f_67022_, new StructureFeatureConfiguration(1, 0, 0)).put(StructureFeature.f_67023_, new StructureFeatureConfiguration(32, 5, 10387313)).put(StructureFeature.f_67026_, new StructureFeatureConfiguration(20, 11, 10387313)).put(StructureFeature.f_67015_, new StructureFeatureConfiguration(80, 20, 10387319)).put(StructureFeature.f_67027_, new StructureFeatureConfiguration(1, 0, 0)).put(StructureFeature.f_67014_, new StructureFeatureConfiguration(1, 0, 0)).put(StructureFeature.f_67019_, new StructureFeatureConfiguration(40, 15, 34222645)).put(StructureFeature.f_67020_, new StructureFeatureConfiguration(24, 4, 165745295)).put(StructureFeature.f_67024_, new StructureFeatureConfiguration(20, 8, 14357621)).put(StructureFeature.f_67030_, new StructureFeatureConfiguration(27, 4, 30084232)).put(StructureFeature.f_67025_, new StructureFeatureConfiguration(27, 4, 30084232)).put(StructureFeature.f_67029_, new StructureFeatureConfiguration(2, 1, 14357921)).build();
   public static final StrongholdConfiguration f_64581_;
   private final Map<StructureFeature<?>, StructureFeatureConfiguration> f_64582_;
   @Nullable
   private final StrongholdConfiguration f_64583_;

   public StructureSettings(Optional<StrongholdConfiguration> p_64586_, Map<StructureFeature<?>, StructureFeatureConfiguration> p_64587_) {
      this.f_64583_ = p_64586_.orElse((StrongholdConfiguration)null);
      this.f_64582_ = p_64587_;
   }

   public StructureSettings(boolean p_64589_) {
      this.f_64582_ = Maps.newHashMap(f_64580_);
      this.f_64583_ = p_64589_ ? f_64581_ : null;
   }

   public Map<StructureFeature<?>, StructureFeatureConfiguration> m_64590_() {
      return this.f_64582_;
   }

   @Nullable
   public StructureFeatureConfiguration m_64593_(StructureFeature<?> p_64594_) {
      return this.f_64582_.get(p_64594_);
   }

   @Nullable
   public StrongholdConfiguration m_64597_() {
      return this.f_64583_;
   }

   static {
      for(StructureFeature<?> structurefeature : Registry.f_122841_) {
         if (!f_64580_.containsKey(structurefeature)) {
            throw new IllegalStateException("Structure feature without default settings: " + Registry.f_122841_.m_7981_(structurefeature));
         }
      }

      f_64581_ = new StrongholdConfiguration(32, 3, 128);
   }
}