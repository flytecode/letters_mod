package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSpawnSettings {
   public static final Logger f_48325_ = LogManager.getLogger();
   private static final float f_151797_ = 0.1F;
   public static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_151796_ = WeightedRandomList.m_146332_();
   public static final MobSpawnSettings f_48326_ = new MobSpawnSettings(0.1F, Stream.of(MobCategory.values()).collect(ImmutableMap.toImmutableMap((p_48359_) -> {
      return p_48359_;
   }, (p_151803_) -> {
      return f_151796_;
   })), ImmutableMap.of(), false);
   public static final MapCodec<MobSpawnSettings> f_48327_ = RecordCodecBuilder.mapCodec((p_48352_) -> {
      return p_48352_.group(Codec.floatRange(0.0F, 0.9999999F).optionalFieldOf("creature_spawn_probability", 0.1F).forGetter((p_151807_) -> {
         return p_151807_.f_48328_;
      }), Codec.simpleMap(MobCategory.f_21584_, WeightedRandomList.m_146333_(MobSpawnSettings.SpawnerData.f_48403_).promotePartial(Util.m_137489_("Spawn data: ", f_48325_::error)), StringRepresentable.m_14357_(MobCategory.values())).fieldOf("spawners").forGetter((p_151805_) -> {
         return p_151805_.f_48329_;
      }), Codec.simpleMap(Registry.f_122826_, MobSpawnSettings.MobSpawnCost.f_48384_, Registry.f_122826_).fieldOf("spawn_costs").forGetter((p_151801_) -> {
         return p_151801_.f_48330_;
      }), Codec.BOOL.fieldOf("player_spawn_friendly").orElse(false).forGetter(MobSpawnSettings::m_48353_)).apply(p_48352_, MobSpawnSettings::new);
   });
   private final float f_48328_;
   private final Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> f_48329_;
   private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> f_48330_;
   private final boolean f_48331_;
   private final java.util.Set<MobCategory> typesView;
   private final java.util.Set<EntityType<?>> costView;

   MobSpawnSettings(float p_48334_, Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> p_48335_, Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> p_48336_, boolean p_48337_) {
      this.f_48328_ = p_48334_;
      this.f_48329_ = ImmutableMap.copyOf(p_48335_);
      this.f_48330_ = ImmutableMap.copyOf(p_48336_);
      this.f_48331_ = p_48337_;
      this.typesView = java.util.Collections.unmodifiableSet(this.f_48329_.keySet());
      this.costView = java.util.Collections.unmodifiableSet(this.f_48330_.keySet());
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_151798_(MobCategory p_151799_) {
      return this.f_48329_.getOrDefault(p_151799_, f_151796_);
   }

   public java.util.Set<MobCategory> getSpawnerTypes() {
       return this.typesView;
   }

   @Nullable
   public MobSpawnSettings.MobSpawnCost m_48345_(EntityType<?> p_48346_) {
      return this.f_48330_.get(p_48346_);
   }

   public java.util.Set<EntityType<?>> getEntityTypes() {
       return this.costView;
   }

   public float m_48344_() {
      return this.f_48328_;
   }

   public boolean m_48353_() {
      return this.f_48331_;
   }

   public static class Builder {
      protected final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> f_48362_ = Stream.of(MobCategory.values()).collect(ImmutableMap.toImmutableMap((p_48383_) -> {
         return p_48383_;
      }, (p_48375_) -> {
         return Lists.newArrayList();
      }));
      protected final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> f_48363_ = Maps.newLinkedHashMap();
      protected float f_48364_ = 0.1F;
      protected boolean f_48365_;

      public MobSpawnSettings.Builder m_48376_(MobCategory p_48377_, MobSpawnSettings.SpawnerData p_48378_) {
         this.f_48362_.get(p_48377_).add(p_48378_);
         return this;
      }

      public MobSpawnSettings.Builder m_48370_(EntityType<?> p_48371_, double p_48372_, double p_48373_) {
         this.f_48363_.put(p_48371_, new MobSpawnSettings.MobSpawnCost(p_48373_, p_48372_));
         return this;
      }

      public MobSpawnSettings.Builder m_48368_(float p_48369_) {
         this.f_48364_ = p_48369_;
         return this;
      }

      public MobSpawnSettings.Builder m_48367_() {
         this.f_48365_ = true;
         return this;
      }

      public MobSpawnSettings m_48381_() {
         return new MobSpawnSettings(this.f_48364_, this.f_48362_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_151809_) -> {
            return WeightedRandomList.m_146328_(p_151809_.getValue());
         })), ImmutableMap.copyOf(this.f_48363_), this.f_48365_);
      }
   }

   public static class MobSpawnCost {
      public static final Codec<MobSpawnSettings.MobSpawnCost> f_48384_ = RecordCodecBuilder.create((p_48399_) -> {
         return p_48399_.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter((p_151813_) -> {
            return p_151813_.f_48385_;
         }), Codec.DOUBLE.fieldOf("charge").forGetter((p_151811_) -> {
            return p_151811_.f_48386_;
         })).apply(p_48399_, MobSpawnSettings.MobSpawnCost::new);
      });
      private final double f_48385_;
      private final double f_48386_;

      MobSpawnCost(double p_48389_, double p_48390_) {
         this.f_48385_ = p_48389_;
         this.f_48386_ = p_48390_;
      }

      public double m_48395_() {
         return this.f_48385_;
      }

      public double m_48400_() {
         return this.f_48386_;
      }
   }

   public static class SpawnerData extends WeightedEntry.IntrusiveBase {
      public static final Codec<MobSpawnSettings.SpawnerData> f_48403_ = RecordCodecBuilder.create((p_151822_) -> {
         return p_151822_.group(Registry.f_122826_.fieldOf("type").forGetter((p_151826_) -> {
            return p_151826_.f_48404_;
         }), Weight.f_146274_.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::m_142631_), Codec.INT.fieldOf("minCount").forGetter((p_151824_) -> {
            return p_151824_.f_48405_;
         }), Codec.INT.fieldOf("maxCount").forGetter((p_151820_) -> {
            return p_151820_.f_48406_;
         })).apply(p_151822_, MobSpawnSettings.SpawnerData::new);
      });
      public final EntityType<?> f_48404_;
      public final int f_48405_;
      public final int f_48406_;

      public SpawnerData(EntityType<?> p_48409_, int p_48410_, int p_48411_, int p_48412_) {
         this(p_48409_, Weight.m_146282_(p_48410_), p_48411_, p_48412_);
      }

      public SpawnerData(EntityType<?> p_151815_, Weight p_151816_, int p_151817_, int p_151818_) {
         super(p_151816_);
         this.f_48404_ = p_151815_.m_20674_() == MobCategory.MISC ? EntityType.f_20510_ : p_151815_;
         this.f_48405_ = p_151817_;
         this.f_48406_ = p_151818_;
      }

      public String toString() {
         return EntityType.m_20613_(this.f_48404_) + "*(" + this.f_48405_ + "-" + this.f_48406_ + "):" + this.m_142631_();
      }
   }
}
