package net.minecraft.data.worldgen.biome;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class VanillaBiomes {
   private static int m_127332_(float p_127333_) {
      float f = p_127333_ / 3.0F;
      f = Mth.m_14036_(f, -1.0F, 1.0F);
      return Mth.m_14169_(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
   }

   public static Biome m_127351_(float p_127352_, float p_127353_, float p_127354_, boolean p_127355_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 8, 4, 4));
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20452_, 8, 2, 4));
      if (p_127355_) {
         BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      } else {
         BiomeDefaultFeatures.m_176859_(mobspawnsettings$builder);
         BiomeDefaultFeatures.m_126781_(mobspawnsettings$builder, 100, 25, 100);
      }

      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127284_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126826_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126828_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, p_127355_ ? Features.f_126998_ : Features.f_126999_);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126718_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126832_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.TAIGA).m_47593_(p_127352_).m_47607_(p_127353_).m_47609_(p_127354_).m_47611_(0.8F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(p_127354_)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127393_(float p_127394_, float p_127395_, boolean p_127396_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126706_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127396_) {
         BiomeDefaultFeatures.m_126846_(biomegenerationsettings$builder);
      } else {
         BiomeDefaultFeatures.m_126842_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126708_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.FOREST).m_47593_(p_127394_).m_47607_(p_127395_).m_47609_(0.6F).m_47611_(0.6F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.6F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127331_() {
      return m_127382_(0.1F, 0.2F, 40, 2, 3);
   }

   public static Biome m_127441_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126808_(mobspawnsettings$builder);
      return m_127369_(0.1F, 0.2F, 0.8F, false, true, false, mobspawnsettings$builder);
   }

   public static Biome m_127451_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126808_(mobspawnsettings$builder);
      return m_127369_(0.2F, 0.4F, 0.8F, false, true, true, mobspawnsettings$builder);
   }

   public static Biome m_127461_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126808_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20508_, 10, 1, 1)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20505_, 2, 1, 1));
      return m_127369_(0.2F, 0.4F, 0.9F, false, false, true, mobspawnsettings$builder);
   }

   public static Biome m_127468_() {
      return m_127382_(0.45F, 0.3F, 10, 1, 1);
   }

   public static Biome m_127471_() {
      return m_127377_(0.1F, 0.2F, 40, 2);
   }

   public static Biome m_127472_() {
      return m_127377_(0.45F, 0.3F, 10, 1);
   }

   private static Biome m_127382_(float p_127383_, float p_127384_, int p_127385_, int p_127386_, int p_127387_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126808_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20508_, p_127385_, 1, p_127386_)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20505_, 2, 1, p_127387_)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20507_, 1, 1, 2));
      mobspawnsettings$builder.m_48367_();
      return m_127369_(p_127383_, p_127384_, 0.9F, false, false, false, mobspawnsettings$builder);
   }

   private static Biome m_127377_(float p_127378_, float p_127379_, int p_127380_, int p_127381_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126808_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20508_, p_127380_, 1, p_127381_)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20507_, 80, 1, 2)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20505_, 2, 1, 1));
      return m_127369_(p_127378_, p_127379_, 0.9F, true, false, false, mobspawnsettings$builder);
   }

   private static Biome m_127369_(float p_127370_, float p_127371_, float p_127372_, boolean p_127373_, boolean p_127374_, boolean p_127375_, MobSpawnSettings.Builder p_127376_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      if (!p_127374_ && !p_127375_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127243_);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127234_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127373_) {
         BiomeDefaultFeatures.m_126836_(biomegenerationsettings$builder);
      } else {
         if (!p_127374_ && !p_127375_) {
            BiomeDefaultFeatures.m_126834_(biomegenerationsettings$builder);
         }

         if (p_127374_) {
            BiomeDefaultFeatures.m_126690_(biomegenerationsettings$builder);
         } else {
            BiomeDefaultFeatures.m_126688_(biomegenerationsettings$builder);
         }
      }

      BiomeDefaultFeatures.m_126722_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126696_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126749_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.JUNGLE).m_47593_(p_127370_).m_47607_(p_127371_).m_47609_(0.95F).m_47611_(p_127372_).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.95F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(p_127376_.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127388_(float p_127389_, float p_127390_, ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> p_127391_, boolean p_127392_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20466_, 5, 4, 6));
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147035_, 10, 4, 6));
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127391_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127236_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127392_) {
         BiomeDefaultFeatures.m_126686_(biomegenerationsettings$builder);
      } else {
         BiomeDefaultFeatures.m_126684_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126818_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126820_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.EXTREME_HILLS).m_47593_(p_127389_).m_47607_(p_127390_).m_47609_(0.2F).m_47611_(0.3F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.2F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127407_(float p_127408_, float p_127409_, boolean p_127410_, boolean p_127411_, boolean p_127412_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126800_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127279_);
      if (p_127410_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127259_);
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127239_);
      }

      if (p_127411_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127244_);
      }

      if (p_127412_) {
         BiomeDefaultFeatures.m_126757_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127264_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126802_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126716_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126751_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126755_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.DESERT).m_47593_(p_127408_).m_47607_(p_127409_).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(2.0F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127439_(boolean p_127440_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126792_(mobspawnsettings$builder);
      if (!p_127440_) {
         mobspawnsettings$builder.m_48367_();
      }

      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      if (!p_127440_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127258_).m_47849_(StructureFeatures.f_127239_);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126728_(biomegenerationsettings$builder);
      if (p_127440_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126930_);
      }

      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126714_(biomegenerationsettings$builder);
      if (p_127440_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126906_);
      }

      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      if (p_127440_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126931_);
      } else {
         BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.PLAINS).m_47593_(0.125F).m_47607_(0.05F).m_47609_(0.8F).m_47611_(0.4F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.8F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   private static Biome m_127420_(BiomeGenerationSettings.Builder p_127421_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126812_(mobspawnsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.THEEND).m_47593_(0.1F).m_47607_(0.2F).m_47609_(0.5F).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(10518688).m_48040_(0).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(p_127421_.m_47831_()).m_47592_();
   }

   public static Biome m_127473_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127280_);
      return m_127420_(biomegenerationsettings$builder);
   }

   public static Biome m_127474_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127280_).m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126887_);
      return m_127420_(biomegenerationsettings$builder);
   }

   public static Biome m_127475_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127280_).m_47849_(StructureFeatures.f_127255_);
      return m_127420_(biomegenerationsettings$builder);
   }

   public static Biome m_127476_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127280_).m_47849_(StructureFeatures.f_127255_).m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126940_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127030_);
      return m_127420_(biomegenerationsettings$builder);
   }

   public static Biome m_127477_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127280_).m_47842_(GenerationStep.Decoration.RAW_GENERATION, Features.f_127032_);
      return m_127420_(biomegenerationsettings$builder);
   }

   public static Biome m_127334_(float p_127335_, float p_127336_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126804_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127289_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126712_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.MUSHROOM).m_47593_(p_127335_).m_47607_(p_127336_).m_47609_(0.9F).m_47611_(1.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.9F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   private static Biome m_127362_(float p_127363_, float p_127364_, float p_127365_, boolean p_127366_, boolean p_127367_, MobSpawnSettings.Builder p_127368_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127367_ ? SurfaceBuilders.f_127293_ : SurfaceBuilders.f_127285_);
      if (!p_127366_ && !p_127367_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127260_).m_47849_(StructureFeatures.f_127239_);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(p_127366_ ? StructureFeatures.f_127236_ : StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      if (!p_127367_) {
         BiomeDefaultFeatures.m_126698_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127367_) {
         BiomeDefaultFeatures.m_126682_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126700_(biomegenerationsettings$builder);
      } else {
         BiomeDefaultFeatures.m_126680_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126722_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126702_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.SAVANNA).m_47593_(p_127363_).m_47607_(p_127364_).m_47609_(p_127365_).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(p_127365_)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(p_127368_.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127356_(float p_127357_, float p_127358_, float p_127359_, boolean p_127360_, boolean p_127361_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = m_127489_();
      return m_127362_(p_127357_, p_127358_, p_127359_, p_127360_, p_127361_, mobspawnsettings$builder);
   }

   private static MobSpawnSettings.Builder m_127489_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20457_, 1, 2, 6)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20560_, 1, 1, 1));
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      return mobspawnsettings$builder;
   }

   public static Biome m_127478_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = m_127489_();
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20466_, 8, 4, 4));
      return m_127362_(1.5F, 0.025F, 1.0F, true, false, mobspawnsettings$builder);
   }

   private static Biome m_127428_(ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> p_127429_, float p_127430_, float p_127431_, boolean p_127432_, boolean p_127433_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127429_);
      BiomeDefaultFeatures.m_126732_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(p_127432_ ? StructureFeatures.f_127236_ : StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126816_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127433_) {
         BiomeDefaultFeatures.m_126692_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126704_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126747_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.MESA).m_47593_(p_127430_).m_47607_(p_127431_).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(2.0F)).m_48043_(10387789).m_48045_(9470285).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127445_(float p_127446_, float p_127447_, boolean p_127448_) {
      return m_127428_(SurfaceBuilders.f_127276_, p_127446_, p_127447_, p_127448_, false);
   }

   public static Biome m_127442_(float p_127443_, float p_127444_) {
      return m_127428_(SurfaceBuilders.f_127298_, p_127443_, p_127444_, true, true);
   }

   public static Biome m_127479_() {
      return m_127428_(SurfaceBuilders.f_127281_, 0.1F, 0.2F, true, false);
   }

   private static Biome m_127422_(MobSpawnSettings.Builder p_127423_, int p_127424_, int p_127425_, boolean p_127426_, BiomeGenerationSettings.Builder p_127427_) {
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.OCEAN).m_47593_(p_127426_ ? -1.8F : -1.0F).m_47607_(0.1F).m_47609_(0.5F).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(p_127424_).m_48037_(p_127425_).m_48019_(12638463).m_48040_(m_127332_(0.5F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(p_127423_.m_48381_()).m_47601_(p_127427_.m_47831_()).m_47592_();
   }

   private static BiomeGenerationSettings.Builder m_127434_(ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> p_127435_, boolean p_127436_, boolean p_127437_, boolean p_127438_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127435_);
      ConfiguredStructureFeature<?, ?> configuredstructurefeature = p_127437_ ? StructureFeatures.f_127252_ : StructureFeatures.f_127251_;
      if (p_127438_) {
         if (p_127436_) {
            biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127250_);
         }

         BiomeDefaultFeatures.m_126786_(biomegenerationsettings$builder);
         biomegenerationsettings$builder.m_47849_(configuredstructurefeature);
      } else {
         biomegenerationsettings$builder.m_47849_(configuredstructurefeature);
         if (p_127436_) {
            biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127250_);
         }

         BiomeDefaultFeatures.m_126786_(biomegenerationsettings$builder);
      }

      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127237_);
      BiomeDefaultFeatures.m_126794_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176854_(biomegenerationsettings$builder, true);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126840_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      return biomegenerationsettings$builder;
   }

   public static Biome m_127449_(boolean p_127450_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126740_(mobspawnsettings$builder, 3, 4, 15);
      mobspawnsettings$builder.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 15, 1, 5));
      boolean flag = !p_127450_;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = m_127434_(SurfaceBuilders.f_127285_, p_127450_, false, flag);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, p_127450_ ? Features.f_127047_ : Features.f_127046_);
      BiomeDefaultFeatures.m_126761_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126759_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return m_127422_(mobspawnsettings$builder, 4020182, 329011, p_127450_, biomegenerationsettings$builder);
   }

   public static Biome m_127459_(boolean p_127460_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126740_(mobspawnsettings$builder, 1, 4, 10);
      mobspawnsettings$builder.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 1, 1, 2));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = m_127434_(SurfaceBuilders.f_127285_, p_127460_, false, true);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, p_127460_ ? Features.f_127050_ : Features.f_127048_);
      BiomeDefaultFeatures.m_126761_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126759_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return m_127422_(mobspawnsettings$builder, 4159204, 329011, p_127460_, biomegenerationsettings$builder);
   }

   public static Biome m_127466_(boolean p_127467_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      if (p_127467_) {
         BiomeDefaultFeatures.m_126740_(mobspawnsettings$builder, 8, 4, 8);
      } else {
         BiomeDefaultFeatures.m_126740_(mobspawnsettings$builder, 10, 2, 15);
      }

      mobspawnsettings$builder.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20516_, 5, 1, 3)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8)).m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 2, 1, 2));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = m_127434_(SurfaceBuilders.f_127292_, p_127467_, true, false);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, p_127467_ ? Features.f_126861_ : Features.f_127052_);
      if (p_127467_) {
         BiomeDefaultFeatures.m_126761_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126763_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return m_127422_(mobspawnsettings$builder, 4566514, 267827, p_127467_, biomegenerationsettings$builder);
   }

   public static Biome m_127480_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20516_, 15, 1, 3));
      BiomeDefaultFeatures.m_126736_(mobspawnsettings$builder, 10, 4);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = m_127434_(SurfaceBuilders.f_127283_, false, true, false).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127021_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127052_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126862_);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return m_127422_(mobspawnsettings$builder, 4445678, 270131, false, biomegenerationsettings$builder);
   }

   public static Biome m_127481_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126736_(mobspawnsettings$builder, 5, 1);
      mobspawnsettings$builder.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = m_127434_(SurfaceBuilders.f_127283_, true, true, false).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126861_);
      BiomeDefaultFeatures.m_126761_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return m_127422_(mobspawnsettings$builder, 4445678, 270131, true, biomegenerationsettings$builder);
   }

   public static Biome m_127469_(boolean p_127470_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, 1, 1, 4)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 15, 1, 5)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20514_, 1, 1, 2));
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
      float f = p_127470_ ? 0.5F : 0.0F;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127282_);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127251_);
      if (p_127470_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127250_);
      }

      BiomeDefaultFeatures.m_126786_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127237_);
      BiomeDefaultFeatures.m_126794_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126767_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126769_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176854_(biomegenerationsettings$builder, true);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126840_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(p_127470_ ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW).m_47595_(Biome.BiomeCategory.OCEAN).m_47593_(p_127470_ ? -1.8F : -1.0F).m_47607_(0.1F).m_47609_(f).m_47599_(Biome.TemperatureModifier.FROZEN).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(3750089).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(f)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   private static Biome m_127397_(float p_127398_, float p_127399_, boolean p_127400_, MobSpawnSettings.Builder p_127401_) {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      if (p_127400_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127017_);
      } else {
         BiomeDefaultFeatures.m_126706_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      if (p_127400_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127022_);
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127013_);
         BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      } else {
         BiomeDefaultFeatures.m_126844_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
         BiomeDefaultFeatures.m_126708_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.FOREST).m_47593_(p_127398_).m_47607_(p_127399_).m_47609_(0.7F).m_47611_(0.8F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.7F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(p_127401_.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   private static MobSpawnSettings.Builder m_127490_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      return mobspawnsettings$builder;
   }

   public static Biome m_127452_(float p_127453_, float p_127454_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = m_127490_().m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 5, 4, 4)).m_48367_();
      return m_127397_(p_127453_, p_127454_, false, mobspawnsettings$builder);
   }

   public static Biome m_127482_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = m_127490_().m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
      return m_127397_(0.1F, 0.4F, true, mobspawnsettings$builder);
   }

   public static Biome m_127413_(float p_127414_, float p_127415_, boolean p_127416_, boolean p_127417_, boolean p_127418_, boolean p_127419_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 8, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20452_, 8, 2, 4));
      if (!p_127416_ && !p_127417_) {
         mobspawnsettings$builder.m_48367_();
      }

      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      float f = p_127416_ ? -0.5F : 0.25F;
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      if (p_127418_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127262_);
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127239_);
      }

      if (p_127419_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127245_);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(p_127417_ ? StructureFeatures.f_127236_ : StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126828_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126838_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126726_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      if (p_127416_) {
         BiomeDefaultFeatures.m_126830_(biomegenerationsettings$builder);
      } else {
         BiomeDefaultFeatures.m_126832_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(p_127416_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.TAIGA).m_47593_(p_127414_).m_47607_(p_127415_).m_47609_(f).m_47611_(p_127416_ ? 0.4F : 0.8F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(p_127416_ ? 4020182 : 4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(f)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127455_(float p_127456_, float p_127457_, boolean p_127458_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127242_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, p_127458_ ? Features.f_127020_ : Features.f_127019_);
      BiomeDefaultFeatures.m_126706_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126708_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.FOREST).m_47593_(p_127456_).m_47607_(p_127457_).m_47609_(0.7F).m_47611_(0.8F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.7F)).m_48031_(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127462_(float p_127463_, float p_127464_, boolean p_127465_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126734_(mobspawnsettings$builder);
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20526_, 1, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127296_);
      if (!p_127465_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127248_);
      }

      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127240_);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127235_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      if (!p_127465_) {
         BiomeDefaultFeatures.m_126757_(biomegenerationsettings$builder);
      }

      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126824_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126710_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126753_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      if (p_127465_) {
         BiomeDefaultFeatures.m_126757_(biomegenerationsettings$builder);
      } else {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127051_);
      }

      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.SWAMP).m_47593_(p_127463_).m_47607_(p_127464_).m_47609_(0.8F).m_47611_(0.9F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(6388580).m_48037_(2302743).m_48019_(12638463).m_48040_(m_127332_(0.8F)).m_48043_(6975545).m_48031_(BiomeSpecialEffects.GrassColorModifier.SWAMP).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127402_(float p_127403_, float p_127404_, boolean p_127405_, boolean p_127406_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = (new MobSpawnSettings.Builder()).m_48368_(0.07F);
      BiomeDefaultFeatures.m_126796_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127405_ ? SurfaceBuilders.f_127287_ : SurfaceBuilders.f_127285_);
      if (!p_127405_ && !p_127406_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127261_).m_47849_(StructureFeatures.f_127245_);
      }

      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      if (!p_127405_ && !p_127406_) {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127239_);
      }

      biomegenerationsettings$builder.m_47849_(p_127406_ ? StructureFeatures.f_127236_ : StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      if (p_127405_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126863_);
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126864_);
      }

      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126694_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.SNOW).m_47595_(Biome.BiomeCategory.ICY).m_47593_(p_127403_).m_47607_(p_127404_).m_47609_(0.0F).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.0F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127345_(float p_127346_, float p_127347_, float p_127348_, int p_127349_, boolean p_127350_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, 2, 1, 4)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 5, 1, 5));
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      mobspawnsettings$builder.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, p_127350_ ? 1 : 100, 1, 1));
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127240_);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126840_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      if (!p_127350_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127049_);
      }

      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(p_127350_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.RIVER).m_47593_(p_127346_).m_47607_(p_127347_).m_47609_(p_127348_).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(p_127349_).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(p_127348_)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127337_(float p_127338_, float p_127339_, float p_127340_, float p_127341_, int p_127342_, boolean p_127343_, boolean p_127344_) {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      if (!p_127344_ && !p_127343_) {
         mobspawnsettings$builder.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20490_, 5, 2, 5));
      }

      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(p_127344_ ? SurfaceBuilders.f_127295_ : SurfaceBuilders.f_127279_);
      if (p_127344_) {
         BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      } else {
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127240_);
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127256_);
         biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127247_);
      }

      biomegenerationsettings$builder.m_47849_(p_127344_ ? StructureFeatures.f_127236_ : StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126720_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126724_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(p_127343_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN).m_47595_(p_127344_ ? Biome.BiomeCategory.NONE : Biome.BiomeCategory.BEACH).m_47593_(p_127338_).m_47607_(p_127339_).m_47609_(p_127340_).m_47611_(p_127341_).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(p_127342_).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(p_127340_)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127483_() {
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127291_);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Features.f_126882_);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NONE).m_47593_(0.1F).m_47607_(0.2F).m_47609_(0.5F).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.5F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(MobSpawnSettings.f_48326_).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127484_() {
      MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 50, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 100, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 2, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20511_, 15, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127290_).m_47849_(StructureFeatures.f_127238_).m_47849_(StructureFeatures.f_127253_).m_47849_(StructureFeatures.f_127257_).m_47839_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126914_);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126918_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126925_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126926_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127038_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127039_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126907_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126908_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126970_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126916_);
      BiomeDefaultFeatures.m_126773_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NETHER).m_47593_(0.1F).m_47607_(0.2F).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(3344392).m_48040_(m_127332_(2.0F)).m_48023_(SoundEvents.f_12113_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12166_, 6000, 8, 2.0D)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12060_, 0.0111D)).m_48021_(Musics.m_11653_(SoundEvents.f_12155_)).m_48018_()).m_47605_(mobspawnsettings).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127485_() {
      double d0 = 0.7D;
      double d1 = 0.15D;
      MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20524_, 20, 5, 5)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 50, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48370_(EntityType.f_20524_, 0.7D, 0.15D).m_48370_(EntityType.f_20453_, 0.7D, 0.15D).m_48370_(EntityType.f_20566_, 0.7D, 0.15D).m_48370_(EntityType.f_20482_, 0.7D, 0.15D).m_48381_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127294_).m_47849_(StructureFeatures.f_127253_).m_47849_(StructureFeatures.f_127254_).m_47849_(StructureFeatures.f_127238_).m_47849_(StructureFeatures.f_127257_).m_47839_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126914_).m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_127045_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126918_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127038_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127039_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126929_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126925_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126926_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126970_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126916_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126971_);
      BiomeDefaultFeatures.m_126773_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NETHER).m_47593_(0.1F).m_47607_(0.2F).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(1787717).m_48040_(m_127332_(2.0F)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123783_, 0.00625F)).m_48023_(SoundEvents.f_12272_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12325_, 6000, 8, 2.0D)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12219_, 0.0111D)).m_48021_(Musics.m_11653_(SoundEvents.f_12156_)).m_48018_()).m_47605_(mobspawnsettings).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127486_() {
      MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 40, 1, 1)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 100, 2, 5)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127277_).m_47849_(StructureFeatures.f_127238_).m_47839_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_47849_(StructureFeatures.f_127253_).m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_127033_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126886_).m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_127034_).m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_127035_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127036_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127037_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126915_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126925_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126926_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127038_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127039_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126907_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126908_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126970_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126917_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126972_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126973_);
      BiomeDefaultFeatures.m_126775_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NETHER).m_47593_(0.1F).m_47607_(0.2F).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(4341314).m_48019_(6840176).m_48040_(m_127332_(2.0F)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123790_, 0.118093334F)).m_48023_(SoundEvents.f_11795_).m_48027_(new AmbientMoodSettings(SoundEvents.f_11848_, 6000, 8, 2.0D)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_11742_, 0.0111D)).m_48021_(Musics.m_11653_(SoundEvents.f_12154_)).m_48018_()).m_47605_(mobspawnsettings).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127487_() {
      MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 1, 2, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20456_, 9, 3, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20511_, 5, 3, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127278_).m_47849_(StructureFeatures.f_127238_).m_47839_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_47849_(StructureFeatures.f_127253_).m_47849_(StructureFeatures.f_127257_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126914_);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126918_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126925_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127038_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127039_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126970_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126916_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127044_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126942_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127040_);
      BiomeDefaultFeatures.m_126773_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NETHER).m_47593_(0.1F).m_47607_(0.2F).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(3343107).m_48040_(m_127332_(2.0F)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123784_, 0.025F)).m_48023_(SoundEvents.f_11954_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12007_, 6000, 8, 2.0D)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_11901_, 0.0111D)).m_48021_(Musics.m_11653_(SoundEvents.f_12157_)).m_48018_()).m_47605_(mobspawnsettings).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_127488_() {
      MobSpawnSettings mobspawnsettings = (new MobSpawnSettings.Builder()).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48370_(EntityType.f_20566_, 1.0D, 0.12D).m_48381_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127297_).m_47849_(StructureFeatures.f_127253_).m_47849_(StructureFeatures.f_127257_).m_47849_(StructureFeatures.f_127238_).m_47839_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126914_);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126918_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126925_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126926_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127038_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_127039_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126970_).m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126916_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126944_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127041_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127042_).m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127043_);
      BiomeDefaultFeatures.m_126773_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.NONE).m_47595_(Biome.BiomeCategory.NETHER).m_47593_(0.1F).m_47607_(0.2F).m_47609_(2.0F).m_47611_(0.0F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(1705242).m_48040_(m_127332_(2.0F)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123785_, 0.01428F)).m_48023_(SoundEvents.f_12431_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12484_, 6000, 8, 2.0D)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12378_, 0.0111D)).m_48021_(Musics.m_11653_(SoundEvents.f_12158_)).m_48018_()).m_47605_(mobspawnsettings).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_177034_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126728_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176852_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176850_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.UNDERGROUND).m_47593_(0.1F).m_47607_(0.2F).m_47609_(0.5F).m_47611_(0.5F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.5F)).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }

   public static Biome m_177035_() {
      MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.m_126788_(mobspawnsettings$builder);
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47851_(SurfaceBuilders.f_127285_);
      BiomeDefaultFeatures.m_126777_(biomegenerationsettings$builder);
      biomegenerationsettings$builder.m_47849_(StructureFeatures.f_127263_);
      BiomeDefaultFeatures.m_126790_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126798_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176857_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126806_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126728_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126810_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126814_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126822_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126714_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126730_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126745_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126765_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_126771_(biomegenerationsettings$builder);
      BiomeDefaultFeatures.m_176863_(biomegenerationsettings$builder);
      return (new Biome.BiomeBuilder()).m_47597_(Biome.Precipitation.RAIN).m_47595_(Biome.BiomeCategory.UNDERGROUND).m_47593_(0.125F).m_47607_(0.05F).m_47609_(0.8F).m_47611_(0.4F).m_47603_((new BiomeSpecialEffects.Builder()).m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(m_127332_(0.8F)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_(mobspawnsettings$builder.m_48381_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47592_();
   }
}