package net.minecraft.world.level.newbiome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.function.LongFunction;
import net.minecraft.Util;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.LazyAreaContext;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

public class Layers implements LayerBiomes {
   protected static final int f_164618_ = 1;
   protected static final int f_164619_ = 2;
   protected static final int f_164620_ = 3;
   protected static final int f_164621_ = 4;
   protected static final int f_164622_ = 3840;
   protected static final int f_164623_ = 8;
   private static final Int2IntMap f_76719_ = Util.m_137469_(new Int2IntOpenHashMap(), (p_76741_) -> {
      m_76742_(p_76741_, Layers.Category.BEACH, 16);
      m_76742_(p_76741_, Layers.Category.BEACH, 26);
      m_76742_(p_76741_, Layers.Category.DESERT, 2);
      m_76742_(p_76741_, Layers.Category.DESERT, 17);
      m_76742_(p_76741_, Layers.Category.DESERT, 130);
      m_76742_(p_76741_, Layers.Category.EXTREME_HILLS, 131);
      m_76742_(p_76741_, Layers.Category.EXTREME_HILLS, 162);
      m_76742_(p_76741_, Layers.Category.EXTREME_HILLS, 20);
      m_76742_(p_76741_, Layers.Category.EXTREME_HILLS, 3);
      m_76742_(p_76741_, Layers.Category.EXTREME_HILLS, 34);
      m_76742_(p_76741_, Layers.Category.FOREST, 27);
      m_76742_(p_76741_, Layers.Category.FOREST, 28);
      m_76742_(p_76741_, Layers.Category.FOREST, 29);
      m_76742_(p_76741_, Layers.Category.FOREST, 157);
      m_76742_(p_76741_, Layers.Category.FOREST, 132);
      m_76742_(p_76741_, Layers.Category.FOREST, 4);
      m_76742_(p_76741_, Layers.Category.FOREST, 155);
      m_76742_(p_76741_, Layers.Category.FOREST, 156);
      m_76742_(p_76741_, Layers.Category.FOREST, 18);
      m_76742_(p_76741_, Layers.Category.ICY, 140);
      m_76742_(p_76741_, Layers.Category.ICY, 13);
      m_76742_(p_76741_, Layers.Category.ICY, 12);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 168);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 169);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 21);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 23);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 22);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 149);
      m_76742_(p_76741_, Layers.Category.JUNGLE, 151);
      m_76742_(p_76741_, Layers.Category.MESA, 37);
      m_76742_(p_76741_, Layers.Category.MESA, 165);
      m_76742_(p_76741_, Layers.Category.MESA, 167);
      m_76742_(p_76741_, Layers.Category.MESA, 166);
      m_76742_(p_76741_, Layers.Category.BADLANDS_PLATEAU, 39);
      m_76742_(p_76741_, Layers.Category.BADLANDS_PLATEAU, 38);
      m_76742_(p_76741_, Layers.Category.MUSHROOM, 14);
      m_76742_(p_76741_, Layers.Category.MUSHROOM, 15);
      m_76742_(p_76741_, Layers.Category.NONE, 25);
      m_76742_(p_76741_, Layers.Category.OCEAN, 46);
      m_76742_(p_76741_, Layers.Category.OCEAN, 49);
      m_76742_(p_76741_, Layers.Category.OCEAN, 50);
      m_76742_(p_76741_, Layers.Category.OCEAN, 48);
      m_76742_(p_76741_, Layers.Category.OCEAN, 24);
      m_76742_(p_76741_, Layers.Category.OCEAN, 47);
      m_76742_(p_76741_, Layers.Category.OCEAN, 10);
      m_76742_(p_76741_, Layers.Category.OCEAN, 45);
      m_76742_(p_76741_, Layers.Category.OCEAN, 0);
      m_76742_(p_76741_, Layers.Category.OCEAN, 44);
      m_76742_(p_76741_, Layers.Category.PLAINS, 1);
      m_76742_(p_76741_, Layers.Category.PLAINS, 129);
      m_76742_(p_76741_, Layers.Category.RIVER, 11);
      m_76742_(p_76741_, Layers.Category.RIVER, 7);
      m_76742_(p_76741_, Layers.Category.SAVANNA, 35);
      m_76742_(p_76741_, Layers.Category.SAVANNA, 36);
      m_76742_(p_76741_, Layers.Category.SAVANNA, 163);
      m_76742_(p_76741_, Layers.Category.SAVANNA, 164);
      m_76742_(p_76741_, Layers.Category.SWAMP, 6);
      m_76742_(p_76741_, Layers.Category.SWAMP, 134);
      m_76742_(p_76741_, Layers.Category.TAIGA, 160);
      m_76742_(p_76741_, Layers.Category.TAIGA, 161);
      m_76742_(p_76741_, Layers.Category.TAIGA, 32);
      m_76742_(p_76741_, Layers.Category.TAIGA, 33);
      m_76742_(p_76741_, Layers.Category.TAIGA, 30);
      m_76742_(p_76741_, Layers.Category.TAIGA, 31);
      m_76742_(p_76741_, Layers.Category.TAIGA, 158);
      m_76742_(p_76741_, Layers.Category.TAIGA, 5);
      m_76742_(p_76741_, Layers.Category.TAIGA, 19);
      m_76742_(p_76741_, Layers.Category.TAIGA, 133);
   });

   public static <T extends Area, C extends BigContext<T>> AreaFactory<T> m_76729_(long p_76730_, AreaTransformer1 p_76731_, AreaFactory<T> p_76732_, int p_76733_, LongFunction<C> p_76734_) {
      AreaFactory<T> areafactory = p_76732_;

      for(int i = 0; i < p_76733_; ++i) {
         areafactory = p_76731_.m_77002_(p_76734_.apply(p_76730_ + (long)i), areafactory);
      }

      return areafactory;
   }

   private static <T extends Area, C extends BigContext<T>> AreaFactory<T> m_76746_(boolean p_76747_, int p_76748_, int p_76749_, LongFunction<C> p_76750_) {
      AreaFactory<T> areafactory = IslandLayer.INSTANCE.m_76984_(p_76750_.apply(1L));
      areafactory = ZoomLayer.FUZZY.m_77002_(p_76750_.apply(2000L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(1L), areafactory);
      areafactory = ZoomLayer.NORMAL.m_77002_(p_76750_.apply(2001L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(2L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(50L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(70L), areafactory);
      areafactory = RemoveTooMuchOceanLayer.INSTANCE.m_77002_(p_76750_.apply(2L), areafactory);
      AreaFactory<T> areafactory1 = OceanLayer.INSTANCE.m_76984_(p_76750_.apply(2L));
      areafactory1 = m_76729_(2001L, ZoomLayer.NORMAL, areafactory1, 6, p_76750_);
      areafactory = AddSnowLayer.INSTANCE.m_77002_(p_76750_.apply(2L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(3L), areafactory);
      areafactory = AddEdgeLayer.CoolWarm.INSTANCE.m_77002_(p_76750_.apply(2L), areafactory);
      areafactory = AddEdgeLayer.HeatIce.INSTANCE.m_77002_(p_76750_.apply(2L), areafactory);
      areafactory = AddEdgeLayer.IntroduceSpecial.INSTANCE.m_77002_(p_76750_.apply(3L), areafactory);
      areafactory = ZoomLayer.NORMAL.m_77002_(p_76750_.apply(2002L), areafactory);
      areafactory = ZoomLayer.NORMAL.m_77002_(p_76750_.apply(2003L), areafactory);
      areafactory = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(4L), areafactory);
      areafactory = AddMushroomIslandLayer.INSTANCE.m_77002_(p_76750_.apply(5L), areafactory);
      areafactory = AddDeepOceanLayer.INSTANCE.m_77002_(p_76750_.apply(4L), areafactory);
      areafactory = m_76729_(1000L, ZoomLayer.NORMAL, areafactory, 0, p_76750_);
      AreaFactory<T> areafactory2 = m_76729_(1000L, ZoomLayer.NORMAL, areafactory, 0, p_76750_);
      areafactory2 = RiverInitLayer.INSTANCE.m_77002_(p_76750_.apply(100L), areafactory2);
      AreaFactory<T> areafactory3 = (new BiomeInitLayer(p_76747_)).m_77002_(p_76750_.apply(200L), areafactory);
      areafactory3 = RareBiomeLargeLayer.INSTANCE.m_77002_(p_76750_.apply(1001L), areafactory3);
      areafactory3 = m_76729_(1000L, ZoomLayer.NORMAL, areafactory3, 2, p_76750_);
      areafactory3 = BiomeEdgeLayer.INSTANCE.m_77002_(p_76750_.apply(1000L), areafactory3);
      AreaFactory<T> areafactory4 = m_76729_(1000L, ZoomLayer.NORMAL, areafactory2, 2, p_76750_);
      areafactory3 = RegionHillsLayer.INSTANCE.m_77020_(p_76750_.apply(1000L), areafactory3, areafactory4);
      areafactory2 = m_76729_(1000L, ZoomLayer.NORMAL, areafactory2, 2, p_76750_);
      areafactory2 = m_76729_(1000L, ZoomLayer.NORMAL, areafactory2, p_76749_, p_76750_);
      areafactory2 = RiverLayer.INSTANCE.m_77002_(p_76750_.apply(1L), areafactory2);
      areafactory2 = SmoothLayer.INSTANCE.m_77002_(p_76750_.apply(1000L), areafactory2);
      areafactory3 = RareBiomeSpotLayer.INSTANCE.m_77002_(p_76750_.apply(1001L), areafactory3);

      for(int i = 0; i < p_76748_; ++i) {
         areafactory3 = ZoomLayer.NORMAL.m_77002_(p_76750_.apply((long)(1000 + i)), areafactory3);
         if (i == 0) {
            areafactory3 = AddIslandLayer.INSTANCE.m_77002_(p_76750_.apply(3L), areafactory3);
         }

         if (i == 1 || p_76748_ == 1) {
            areafactory3 = ShoreLayer.INSTANCE.m_77002_(p_76750_.apply(1000L), areafactory3);
         }
      }

      areafactory3 = SmoothLayer.INSTANCE.m_77002_(p_76750_.apply(1000L), areafactory3);
      areafactory3 = RiverMixerLayer.INSTANCE.m_77020_(p_76750_.apply(100L), areafactory3, areafactory2);
      return OceanMixerLayer.INSTANCE.m_77020_(p_76750_.apply(100L), areafactory3, areafactory1);
   }

   public static Layer m_76735_(long p_76736_, boolean p_76737_, int p_76738_, int p_76739_) {
      int i = 25;
      AreaFactory<LazyArea> areafactory = m_76746_(p_76737_, p_76738_, p_76739_, (p_76728_) -> {
         return new LazyAreaContext(25, p_76736_, p_76728_);
      });
      return new Layer(areafactory);
   }

   public static boolean m_76723_(int p_76724_, int p_76725_) {
      if (p_76724_ == p_76725_) {
         return true;
      } else {
         return f_76719_.get(p_76724_) == f_76719_.get(p_76725_);
      }
   }

   private static void m_76742_(Int2IntOpenHashMap p_76743_, Layers.Category p_76744_, int p_76745_) {
      p_76743_.put(p_76745_, p_76744_.ordinal());
   }

   protected static boolean m_76721_(int p_76722_) {
      return p_76722_ == 44 || p_76722_ == 45 || p_76722_ == 0 || p_76722_ == 46 || p_76722_ == 10 || p_76722_ == 47 || p_76722_ == 48 || p_76722_ == 24 || p_76722_ == 49 || p_76722_ == 50;
   }

   protected static boolean m_76751_(int p_76752_) {
      return p_76752_ == 44 || p_76752_ == 45 || p_76752_ == 0 || p_76752_ == 46 || p_76752_ == 10;
   }

   static enum Category {
      NONE,
      TAIGA,
      EXTREME_HILLS,
      JUNGLE,
      MESA,
      BADLANDS_PLATEAU,
      PLAINS,
      SAVANNA,
      ICY,
      BEACH,
      FOREST,
      OCEAN,
      DESERT,
      RIVER,
      SWAMP,
      MUSHROOM;
   }
}