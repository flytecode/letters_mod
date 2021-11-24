package net.minecraft.data.worldgen;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeDefaultFeatures {
   public static void m_126732_(BiomeGenerationSettings.Builder p_126733_) {
      p_126733_.m_47849_(StructureFeatures.f_127241_);
      p_126733_.m_47849_(StructureFeatures.f_127249_);
   }

   public static void m_126777_(BiomeGenerationSettings.Builder p_126778_) {
      p_126778_.m_47849_(StructureFeatures.f_127240_);
      p_126778_.m_47849_(StructureFeatures.f_127249_);
   }

   public static void m_126786_(BiomeGenerationSettings.Builder p_126787_) {
      p_126787_.m_47849_(StructureFeatures.f_127240_);
      p_126787_.m_47849_(StructureFeatures.f_127246_);
   }

   public static void m_126790_(BiomeGenerationSettings.Builder p_126791_) {
      p_126791_.m_47839_(GenerationStep.Carving.AIR, Carvers.f_126848_);
      p_126791_.m_47839_(GenerationStep.Carving.AIR, Carvers.f_126849_);
   }

   public static void m_126794_(BiomeGenerationSettings.Builder p_126795_) {
      p_126795_.m_47839_(GenerationStep.Carving.AIR, Carvers.f_126850_);
      p_126795_.m_47839_(GenerationStep.Carving.AIR, Carvers.f_126849_);
      p_126795_.m_47839_(GenerationStep.Carving.LIQUID, Carvers.f_126851_);
      p_126795_.m_47839_(GenerationStep.Carving.LIQUID, Carvers.f_126852_);
   }

   public static void m_126798_(BiomeGenerationSettings.Builder p_126799_) {
      p_126799_.m_47842_(GenerationStep.Decoration.LAKES, Features.f_126875_);
      p_126799_.m_47842_(GenerationStep.Decoration.LAKES, Features.f_126876_);
   }

   public static void m_126802_(BiomeGenerationSettings.Builder p_126803_) {
      p_126803_.m_47842_(GenerationStep.Decoration.LAKES, Features.f_126876_);
   }

   public static void m_126806_(BiomeGenerationSettings.Builder p_126807_) {
      p_126807_.m_47842_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, Features.f_126883_);
   }

   public static void m_126810_(BiomeGenerationSettings.Builder p_126811_) {
      m_176854_(p_126811_, false);
   }

   public static void m_176854_(BiomeGenerationSettings.Builder p_176855_, boolean p_176856_) {
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126978_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126979_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126980_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126981_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126982_);
      if (!p_176856_) {
         p_176855_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176928_);
      }

      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_176889_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_176891_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_176925_);
      p_176855_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_176926_);
   }

   public static void m_176863_(BiomeGenerationSettings.Builder p_176864_) {
      p_176864_.m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_176923_);
      p_176864_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_176922_);
      p_176864_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_176924_);
   }

   public static void m_126814_(BiomeGenerationSettings.Builder p_126815_) {
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126983_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126984_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126986_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126987_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126988_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126989_);
      p_126815_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_176919_);
   }

   public static void m_126816_(BiomeGenerationSettings.Builder p_126817_) {
      p_126817_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126985_);
   }

   public static void m_126818_(BiomeGenerationSettings.Builder p_126819_) {
      p_126819_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126991_);
   }

   public static void m_126820_(BiomeGenerationSettings.Builder p_126821_) {
      p_126821_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126990_);
   }

   public static void m_126822_(BiomeGenerationSettings.Builder p_126823_) {
      p_126823_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126879_);
      p_126823_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126877_);
      p_126823_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126878_);
   }

   public static void m_126824_(BiomeGenerationSettings.Builder p_126825_) {
      p_126825_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_126877_);
   }

   public static void m_126826_(BiomeGenerationSettings.Builder p_126827_) {
      p_126827_.m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_126865_);
   }

   public static void m_126828_(BiomeGenerationSettings.Builder p_126829_) {
      p_126829_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126899_);
   }

   public static void m_126830_(BiomeGenerationSettings.Builder p_126831_) {
      p_126831_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126895_);
   }

   public static void m_126832_(BiomeGenerationSettings.Builder p_126833_) {
      p_126833_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126894_);
   }

   public static void m_126834_(BiomeGenerationSettings.Builder p_126835_) {
      p_126835_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126872_);
   }

   public static void m_126836_(BiomeGenerationSettings.Builder p_126837_) {
      p_126837_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126873_);
      p_126837_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127001_);
   }

   public static void m_126838_(BiomeGenerationSettings.Builder p_126839_) {
      p_126839_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127023_);
   }

   public static void m_126840_(BiomeGenerationSettings.Builder p_126841_) {
      p_126841_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126994_);
   }

   public static void m_126842_(BiomeGenerationSettings.Builder p_126843_) {
      p_126843_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127027_);
   }

   public static void m_126844_(BiomeGenerationSettings.Builder p_126845_) {
      p_126845_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126995_);
   }

   public static void m_126846_(BiomeGenerationSettings.Builder p_126847_) {
      p_126847_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127026_);
   }

   public static void m_126680_(BiomeGenerationSettings.Builder p_126681_) {
      p_126681_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127025_);
   }

   public static void m_126682_(BiomeGenerationSettings.Builder p_126683_) {
      p_126683_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127024_);
   }

   public static void m_176850_(BiomeGenerationSettings.Builder p_176851_) {
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176943_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176933_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176941_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176937_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176930_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176944_);
      p_176851_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176945_);
   }

   public static void m_176852_(BiomeGenerationSettings.Builder p_176853_) {
      p_176853_.m_47842_(GenerationStep.Decoration.UNDERGROUND_ORES, Features.f_176921_);
   }

   public static void m_126684_(BiomeGenerationSettings.Builder p_126685_) {
      p_126685_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127029_);
   }

   public static void m_126686_(BiomeGenerationSettings.Builder p_126687_) {
      p_126687_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127028_);
   }

   public static void m_126688_(BiomeGenerationSettings.Builder p_126689_) {
      p_126689_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127000_);
   }

   public static void m_126690_(BiomeGenerationSettings.Builder p_126691_) {
      p_126691_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126997_);
   }

   public static void m_126692_(BiomeGenerationSettings.Builder p_126693_) {
      p_126693_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176949_);
   }

   public static void m_126694_(BiomeGenerationSettings.Builder p_126695_) {
      p_126695_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176950_);
   }

   public static void m_126696_(BiomeGenerationSettings.Builder p_126697_) {
      p_126697_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126889_);
   }

   public static void m_126698_(BiomeGenerationSettings.Builder p_126699_) {
      p_126699_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126898_);
   }

   public static void m_126700_(BiomeGenerationSettings.Builder p_126701_) {
      p_126701_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126938_);
   }

   public static void m_126702_(BiomeGenerationSettings.Builder p_126703_) {
      p_126703_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126937_);
   }

   public static void m_126704_(BiomeGenerationSettings.Builder p_126705_) {
      p_126705_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126936_);
      p_126705_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126892_);
   }

   public static void m_126706_(BiomeGenerationSettings.Builder p_126707_) {
      p_126707_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127018_);
   }

   public static void m_126708_(BiomeGenerationSettings.Builder p_126709_) {
      p_126709_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126935_);
   }

   public static void m_126710_(BiomeGenerationSettings.Builder p_126711_) {
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_176951_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127014_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126938_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126891_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126896_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126968_);
      p_126711_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126969_);
   }

   public static void m_126712_(BiomeGenerationSettings.Builder p_126713_) {
      p_126713_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127002_);
      p_126713_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126911_);
      p_126713_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126912_);
   }

   public static void m_126714_(BiomeGenerationSettings.Builder p_126715_) {
      p_126715_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126996_);
      p_126715_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127016_);
      p_126715_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126934_);
   }

   public static void m_126716_(BiomeGenerationSettings.Builder p_126717_) {
      p_126717_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126890_);
   }

   public static void m_126718_(BiomeGenerationSettings.Builder p_126719_) {
      p_126719_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126888_);
      p_126719_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126891_);
      p_126719_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126913_);
      p_126719_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126967_);
   }

   public static void m_126720_(BiomeGenerationSettings.Builder p_126721_) {
      p_126721_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127012_);
   }

   public static void m_126722_(BiomeGenerationSettings.Builder p_126723_) {
      p_126723_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_127011_);
   }

   public static void m_126724_(BiomeGenerationSettings.Builder p_126725_) {
      p_126725_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126936_);
   }

   public static void m_126726_(BiomeGenerationSettings.Builder p_126727_) {
      p_126727_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126939_);
      p_126727_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126911_);
      p_126727_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126912_);
   }

   public static void m_126728_(BiomeGenerationSettings.Builder p_126729_) {
      p_126729_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126897_);
   }

   public static void m_126730_(BiomeGenerationSettings.Builder p_126731_) {
      p_126731_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126909_);
      p_126731_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126910_);
   }

   public static void m_126745_(BiomeGenerationSettings.Builder p_126746_) {
      p_126746_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126906_);
      p_126746_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126931_);
   }

   public static void m_126747_(BiomeGenerationSettings.Builder p_126748_) {
      p_126748_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126905_);
      p_126748_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126931_);
      p_126748_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126902_);
   }

   public static void m_126749_(BiomeGenerationSettings.Builder p_126750_) {
      p_126750_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126893_);
      p_126750_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126874_);
   }

   public static void m_126751_(BiomeGenerationSettings.Builder p_126752_) {
      p_126752_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126904_);
      p_126752_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126931_);
      p_126752_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126901_);
   }

   public static void m_126753_(BiomeGenerationSettings.Builder p_126754_) {
      p_126754_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126903_);
      p_126754_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126931_);
   }

   public static void m_126755_(BiomeGenerationSettings.Builder p_126756_) {
      p_126756_.m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126884_);
   }

   public static void m_126757_(BiomeGenerationSettings.Builder p_126758_) {
      p_126758_.m_47842_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, Features.f_126885_);
   }

   public static void m_126759_(BiomeGenerationSettings.Builder p_126760_) {
      p_126760_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126869_);
   }

   public static void m_126761_(BiomeGenerationSettings.Builder p_126762_) {
      p_126762_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126866_);
   }

   public static void m_126763_(BiomeGenerationSettings.Builder p_126764_) {
      p_126764_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126870_);
   }

   public static void m_126765_(BiomeGenerationSettings.Builder p_126766_) {
      p_126766_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126919_);
      p_126766_.m_47842_(GenerationStep.Decoration.VEGETAL_DECORATION, Features.f_126914_);
   }

   public static void m_126767_(BiomeGenerationSettings.Builder p_126768_) {
      p_126768_.m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_126867_);
      p_126768_.m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_126868_);
   }

   public static void m_126769_(BiomeGenerationSettings.Builder p_126770_) {
      p_126770_.m_47842_(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.f_126871_);
   }

   public static void m_126771_(BiomeGenerationSettings.Builder p_126772_) {
      p_126772_.m_47842_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Features.f_126880_);
   }

   public static void m_126773_(BiomeGenerationSettings.Builder p_126774_) {
      p_126774_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126976_);
      p_126774_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126977_);
      p_126774_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126974_);
      p_126774_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126975_);
      m_126775_(p_126774_);
   }

   public static void m_126775_(BiomeGenerationSettings.Builder p_126776_) {
      p_126776_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126992_);
      p_126776_.m_47842_(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.f_126941_);
   }

   public static void m_176857_(BiomeGenerationSettings.Builder p_176858_) {
      p_176858_.m_47842_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.f_176946_);
   }

   public static void m_126734_(MobSpawnSettings.Builder p_126735_) {
      p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20520_, 12, 4, 4));
      p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20510_, 10, 4, 4));
      p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20555_, 10, 4, 4));
      p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20557_, 8, 4, 4));
   }

   public static void m_176859_(MobSpawnSettings.Builder p_176860_) {
      p_176860_.m_48376_(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20549_, 10, 8, 8));
      m_176861_(p_176860_);
   }

   public static void m_126788_(MobSpawnSettings.Builder p_126789_) {
      m_176859_(p_126789_);
      m_126781_(p_126789_, 95, 5, 100);
   }

   public static void m_176861_(MobSpawnSettings.Builder p_176862_) {
      p_176862_.m_48376_(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147034_, 10, 4, 6));
      p_176862_.m_48376_(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147039_, 10, 4, 6));
   }

   public static void m_126740_(MobSpawnSettings.Builder p_126741_, int p_126742_, int p_126743_, int p_126744_) {
      p_126741_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, p_126742_, 1, p_126743_));
      p_126741_.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20556_, p_126744_, 3, 6));
      m_126788_(p_126741_);
      p_126741_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
   }

   public static void m_126736_(MobSpawnSettings.Builder p_126737_, int p_126738_, int p_126739_) {
      p_126737_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, p_126738_, p_126739_, 4));
      p_126737_.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8));
      p_126737_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 2, 1, 2));
      m_126788_(p_126737_);
   }

   public static void m_126792_(MobSpawnSettings.Builder p_126793_) {
      m_126734_(p_126793_);
      p_126793_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20457_, 5, 2, 6));
      p_126793_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20560_, 1, 1, 3));
      m_126788_(p_126793_);
   }

   public static void m_126796_(MobSpawnSettings.Builder p_126797_) {
      p_126797_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 10, 2, 3));
      p_126797_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20514_, 1, 1, 2));
      m_176859_(p_126797_);
      m_126781_(p_126797_, 95, 5, 20);
      p_126797_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20481_, 80, 4, 4));
   }

   public static void m_126800_(MobSpawnSettings.Builder p_126801_) {
      p_126801_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
      m_176859_(p_126801_);
      m_126781_(p_126801_, 19, 1, 100);
      p_126801_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20458_, 80, 4, 4));
   }

   public static void m_126781_(MobSpawnSettings.Builder p_126782_, int p_126783_, int p_126784_, int p_126785_) {
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20479_, 100, 4, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20501_, p_126783_, 4, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20530_, p_126784_, 1, 1));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20524_, p_126785_, 4, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20558_, 100, 4, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20526_, 100, 4, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 10, 1, 4));
      p_126782_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20495_, 5, 1, 1));
   }

   public static void m_126804_(MobSpawnSettings.Builder p_126805_) {
      p_126805_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20504_, 8, 4, 8));
      m_176859_(p_126805_);
   }

   public static void m_126808_(MobSpawnSettings.Builder p_126809_) {
      m_126734_(p_126809_);
      p_126809_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20555_, 10, 4, 4));
      m_126788_(p_126809_);
   }

   public static void m_126812_(MobSpawnSettings.Builder p_126813_) {
      p_126813_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 10, 4, 4));
   }
}