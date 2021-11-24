package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
import net.minecraft.util.datafix.PackedBitStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPalettedStorageFix extends DataFix {
   private static final int f_145212_ = 128;
   private static final int f_145213_ = 64;
   private static final int f_145214_ = 32;
   private static final int f_145215_ = 16;
   private static final int f_145216_ = 8;
   private static final int f_145217_ = 4;
   private static final int f_145218_ = 2;
   private static final int f_145219_ = 1;
   static final Logger f_15035_ = LogManager.getLogger();
   static final BitSet f_15036_ = new BitSet(256);
   static final BitSet f_15037_ = new BitSet(256);
   static final Dynamic<?> f_15038_ = BlockStateData.m_14956_("{Name:'minecraft:pumpkin'}");
   static final Dynamic<?> f_15039_ = BlockStateData.m_14956_("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   static final Dynamic<?> f_15040_ = BlockStateData.m_14956_("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   static final Dynamic<?> f_15041_ = BlockStateData.m_14956_("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   static final Dynamic<?> f_15042_ = BlockStateData.m_14956_("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   static final Dynamic<?> f_15043_ = BlockStateData.m_14956_("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   static final Dynamic<?> f_15044_ = BlockStateData.m_14956_("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   static final Dynamic<?> f_15045_ = BlockStateData.m_14956_("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   static final Dynamic<?> f_15046_ = BlockStateData.m_14956_("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   static final Dynamic<?> f_15047_ = BlockStateData.m_14956_("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   static final Map<String, Dynamic<?>> f_15048_ = DataFixUtils.make(Maps.newHashMap(), (p_15111_) -> {
      p_15111_.put("minecraft:air0", BlockStateData.m_14956_("{Name:'minecraft:flower_pot'}"));
      p_15111_.put("minecraft:red_flower0", BlockStateData.m_14956_("{Name:'minecraft:potted_poppy'}"));
      p_15111_.put("minecraft:red_flower1", BlockStateData.m_14956_("{Name:'minecraft:potted_blue_orchid'}"));
      p_15111_.put("minecraft:red_flower2", BlockStateData.m_14956_("{Name:'minecraft:potted_allium'}"));
      p_15111_.put("minecraft:red_flower3", BlockStateData.m_14956_("{Name:'minecraft:potted_azure_bluet'}"));
      p_15111_.put("minecraft:red_flower4", BlockStateData.m_14956_("{Name:'minecraft:potted_red_tulip'}"));
      p_15111_.put("minecraft:red_flower5", BlockStateData.m_14956_("{Name:'minecraft:potted_orange_tulip'}"));
      p_15111_.put("minecraft:red_flower6", BlockStateData.m_14956_("{Name:'minecraft:potted_white_tulip'}"));
      p_15111_.put("minecraft:red_flower7", BlockStateData.m_14956_("{Name:'minecraft:potted_pink_tulip'}"));
      p_15111_.put("minecraft:red_flower8", BlockStateData.m_14956_("{Name:'minecraft:potted_oxeye_daisy'}"));
      p_15111_.put("minecraft:yellow_flower0", BlockStateData.m_14956_("{Name:'minecraft:potted_dandelion'}"));
      p_15111_.put("minecraft:sapling0", BlockStateData.m_14956_("{Name:'minecraft:potted_oak_sapling'}"));
      p_15111_.put("minecraft:sapling1", BlockStateData.m_14956_("{Name:'minecraft:potted_spruce_sapling'}"));
      p_15111_.put("minecraft:sapling2", BlockStateData.m_14956_("{Name:'minecraft:potted_birch_sapling'}"));
      p_15111_.put("minecraft:sapling3", BlockStateData.m_14956_("{Name:'minecraft:potted_jungle_sapling'}"));
      p_15111_.put("minecraft:sapling4", BlockStateData.m_14956_("{Name:'minecraft:potted_acacia_sapling'}"));
      p_15111_.put("minecraft:sapling5", BlockStateData.m_14956_("{Name:'minecraft:potted_dark_oak_sapling'}"));
      p_15111_.put("minecraft:red_mushroom0", BlockStateData.m_14956_("{Name:'minecraft:potted_red_mushroom'}"));
      p_15111_.put("minecraft:brown_mushroom0", BlockStateData.m_14956_("{Name:'minecraft:potted_brown_mushroom'}"));
      p_15111_.put("minecraft:deadbush0", BlockStateData.m_14956_("{Name:'minecraft:potted_dead_bush'}"));
      p_15111_.put("minecraft:tallgrass2", BlockStateData.m_14956_("{Name:'minecraft:potted_fern'}"));
      p_15111_.put("minecraft:cactus0", BlockStateData.m_14952_(2240));
   });
   static final Map<String, Dynamic<?>> f_15049_ = DataFixUtils.make(Maps.newHashMap(), (p_15108_) -> {
      m_15077_(p_15108_, 0, "skeleton", "skull");
      m_15077_(p_15108_, 1, "wither_skeleton", "skull");
      m_15077_(p_15108_, 2, "zombie", "head");
      m_15077_(p_15108_, 3, "player", "head");
      m_15077_(p_15108_, 4, "creeper", "head");
      m_15077_(p_15108_, 5, "dragon", "head");
   });
   static final Map<String, Dynamic<?>> f_15050_ = DataFixUtils.make(Maps.newHashMap(), (p_15105_) -> {
      m_15082_(p_15105_, "oak_door", 1024);
      m_15082_(p_15105_, "iron_door", 1136);
      m_15082_(p_15105_, "spruce_door", 3088);
      m_15082_(p_15105_, "birch_door", 3104);
      m_15082_(p_15105_, "jungle_door", 3120);
      m_15082_(p_15105_, "acacia_door", 3136);
      m_15082_(p_15105_, "dark_oak_door", 3152);
   });
   static final Map<String, Dynamic<?>> f_15051_ = DataFixUtils.make(Maps.newHashMap(), (p_15102_) -> {
      for(int i = 0; i < 26; ++i) {
         p_15102_.put("true" + i, BlockStateData.m_14956_("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + i + "'}}"));
         p_15102_.put("false" + i, BlockStateData.m_14956_("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + i + "'}}"));
      }

   });
   private static final Int2ObjectMap<String> f_15052_ = DataFixUtils.make(new Int2ObjectOpenHashMap<>(), (p_15070_) -> {
      p_15070_.put(0, "white");
      p_15070_.put(1, "orange");
      p_15070_.put(2, "magenta");
      p_15070_.put(3, "light_blue");
      p_15070_.put(4, "yellow");
      p_15070_.put(5, "lime");
      p_15070_.put(6, "pink");
      p_15070_.put(7, "gray");
      p_15070_.put(8, "light_gray");
      p_15070_.put(9, "cyan");
      p_15070_.put(10, "purple");
      p_15070_.put(11, "blue");
      p_15070_.put(12, "brown");
      p_15070_.put(13, "green");
      p_15070_.put(14, "red");
      p_15070_.put(15, "black");
   });
   static final Map<String, Dynamic<?>> f_15053_ = DataFixUtils.make(Maps.newHashMap(), (p_15095_) -> {
      for(Entry<String> entry : f_15052_.int2ObjectEntrySet()) {
         if (!Objects.equals(entry.getValue(), "red")) {
            m_15073_(p_15095_, entry.getIntKey(), entry.getValue());
         }
      }

   });
   static final Map<String, Dynamic<?>> f_15054_ = DataFixUtils.make(Maps.newHashMap(), (p_15072_) -> {
      for(Entry<String> entry : f_15052_.int2ObjectEntrySet()) {
         if (!Objects.equals(entry.getValue(), "white")) {
            m_15096_(p_15072_, 15 - entry.getIntKey(), entry.getValue());
         }
      }

   });
   static final Dynamic<?> f_15055_ = BlockStateData.m_14952_(0);
   private static final int f_145211_ = 4096;

   public ChunkPalettedStorageFix(Schema p_15058_, boolean p_15059_) {
      super(p_15058_, p_15059_);
   }

   private static void m_15077_(Map<String, Dynamic<?>> p_15078_, int p_15079_, String p_15080_, String p_15081_) {
      p_15078_.put(p_15079_ + "north", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'north'}}"));
      p_15078_.put(p_15079_ + "east", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'east'}}"));
      p_15078_.put(p_15079_ + "south", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'south'}}"));
      p_15078_.put(p_15079_ + "west", BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_wall_" + p_15081_ + "',Properties:{facing:'west'}}"));

      for(int i = 0; i < 16; ++i) {
         p_15078_.put("" + p_15079_ + i, BlockStateData.m_14956_("{Name:'minecraft:" + p_15080_ + "_" + p_15081_ + "',Properties:{rotation:'" + i + "'}}"));
      }

   }

   private static void m_15082_(Map<String, Dynamic<?>> p_15083_, String p_15084_, int p_15085_) {
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 4));
      p_15083_.put("minecraft:" + p_15084_ + "eastlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperleftfalsefalse", BlockStateData.m_14952_(p_15085_ + 8));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperleftfalsetrue", BlockStateData.m_14952_(p_15085_ + 10));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 9));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperrightfalsetrue", BlockStateData.m_14952_(p_15085_ + 11));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "eastupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 3));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 7));
      p_15083_.put("minecraft:" + p_15084_ + "northlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "northupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 1));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 5));
      p_15083_.put("minecraft:" + p_15084_ + "southlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "southupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerrightfalsefalse", BlockStateData.m_14952_(p_15085_ + 2));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerrighttruefalse", BlockStateData.m_14952_(p_15085_ + 6));
      p_15083_.put("minecraft:" + p_15084_ + "westlowerrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperleftfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperleftfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperlefttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperlefttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperrightfalsefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperrightfalsetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperrighttruefalse", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}"));
      p_15083_.put("minecraft:" + p_15084_ + "westupperrighttruetrue", BlockStateData.m_14956_("{Name:'minecraft:" + p_15084_ + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}"));
   }

   private static void m_15073_(Map<String, Dynamic<?>> p_15074_, int p_15075_, String p_15076_) {
      p_15074_.put("southfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
      p_15074_.put("westfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
      p_15074_.put("northfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
      p_15074_.put("eastfalsefoot" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
      p_15074_.put("southfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
      p_15074_.put("westfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
      p_15074_.put("northfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
      p_15074_.put("eastfalsehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
      p_15074_.put("southtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
      p_15074_.put("westtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
      p_15074_.put("northtruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
      p_15074_.put("easttruehead" + p_15075_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15076_ + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
   }

   private static void m_15096_(Map<String, Dynamic<?>> p_15097_, int p_15098_, String p_15099_) {
      for(int i = 0; i < 16; ++i) {
         p_15097_.put(i + "_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_banner',Properties:{rotation:'" + i + "'}}"));
      }

      p_15097_.put("north_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'north'}}"));
      p_15097_.put("south_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'south'}}"));
      p_15097_.put("west_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'west'}}"));
      p_15097_.put("east_" + p_15098_, BlockStateData.m_14956_("{Name:'minecraft:" + p_15099_ + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String m_15064_(Dynamic<?> p_15065_) {
      return p_15065_.get("Name").asString("");
   }

   public static String m_15066_(Dynamic<?> p_15067_, String p_15068_) {
      return p_15067_.get("Properties").get(p_15068_).asString("");
   }

   public static int m_15061_(CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> p_15062_, Dynamic<?> p_15063_) {
      int i = p_15062_.m_7447_(p_15063_);
      if (i == -1) {
         i = p_15062_.m_13569_(p_15063_);
      }

      return i;
   }

   private Dynamic<?> m_15092_(Dynamic<?> p_15093_) {
      Optional<? extends Dynamic<?>> optional = p_15093_.get("Level").result();
      return optional.isPresent() && optional.get().get("Sections").asStreamOpt().result().isPresent() ? p_15093_.set("Level", (new ChunkPalettedStorageFix.UpgradeChunk(optional.get())).m_15223_()) : p_15093_;
   }

   public TypeRewriteRule makeRule() {
      Type<?> type = this.getInputSchema().getType(References.f_16773_);
      Type<?> type1 = this.getOutputSchema().getType(References.f_16773_);
      return this.writeFixAndRead("ChunkPalettedStorageFix", type, type1, this::m_15092_);
   }

   public static int m_15086_(boolean p_15087_, boolean p_15088_, boolean p_15089_, boolean p_15090_) {
      int i = 0;
      if (p_15089_) {
         if (p_15088_) {
            i |= 2;
         } else if (p_15087_) {
            i |= 128;
         } else {
            i |= 1;
         }
      } else if (p_15090_) {
         if (p_15087_) {
            i |= 32;
         } else if (p_15088_) {
            i |= 8;
         } else {
            i |= 16;
         }
      } else if (p_15088_) {
         i |= 4;
      } else if (p_15087_) {
         i |= 64;
      }

      return i;
   }

   static {
      f_15037_.set(2);
      f_15037_.set(3);
      f_15037_.set(110);
      f_15037_.set(140);
      f_15037_.set(144);
      f_15037_.set(25);
      f_15037_.set(86);
      f_15037_.set(26);
      f_15037_.set(176);
      f_15037_.set(177);
      f_15037_.set(175);
      f_15037_.set(64);
      f_15037_.set(71);
      f_15037_.set(193);
      f_15037_.set(194);
      f_15037_.set(195);
      f_15037_.set(196);
      f_15037_.set(197);
      f_15036_.set(54);
      f_15036_.set(146);
      f_15036_.set(25);
      f_15036_.set(26);
      f_15036_.set(51);
      f_15036_.set(53);
      f_15036_.set(67);
      f_15036_.set(108);
      f_15036_.set(109);
      f_15036_.set(114);
      f_15036_.set(128);
      f_15036_.set(134);
      f_15036_.set(135);
      f_15036_.set(136);
      f_15036_.set(156);
      f_15036_.set(163);
      f_15036_.set(164);
      f_15036_.set(180);
      f_15036_.set(203);
      f_15036_.set(55);
      f_15036_.set(85);
      f_15036_.set(113);
      f_15036_.set(188);
      f_15036_.set(189);
      f_15036_.set(190);
      f_15036_.set(191);
      f_15036_.set(192);
      f_15036_.set(93);
      f_15036_.set(94);
      f_15036_.set(101);
      f_15036_.set(102);
      f_15036_.set(160);
      f_15036_.set(106);
      f_15036_.set(107);
      f_15036_.set(183);
      f_15036_.set(184);
      f_15036_.set(185);
      f_15036_.set(186);
      f_15036_.set(187);
      f_15036_.set(132);
      f_15036_.set(139);
      f_15036_.set(199);
   }

   static class DataLayer {
      private static final int f_145220_ = 2048;
      private static final int f_145221_ = 4;
      private final byte[] f_15129_;

      public DataLayer() {
         this.f_15129_ = new byte[2048];
      }

      public DataLayer(byte[] p_15132_) {
         this.f_15129_ = p_15132_;
         if (p_15132_.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + p_15132_.length);
         }
      }

      public int m_15135_(int p_15136_, int p_15137_, int p_15138_) {
         int i = this.m_15139_(p_15137_ << 8 | p_15138_ << 4 | p_15136_);
         return this.m_15133_(p_15137_ << 8 | p_15138_ << 4 | p_15136_) ? this.f_15129_[i] & 15 : this.f_15129_[i] >> 4 & 15;
      }

      private boolean m_15133_(int p_15134_) {
         return (p_15134_ & 1) == 0;
      }

      private int m_15139_(int p_15140_) {
         return p_15140_ >> 1;
      }
   }

   public static enum Direction {
      DOWN(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.Y),
      UP(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.Y),
      NORTH(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.Z),
      SOUTH(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.Z),
      WEST(ChunkPalettedStorageFix.Direction.AxisDirection.NEGATIVE, ChunkPalettedStorageFix.Direction.Axis.X),
      EAST(ChunkPalettedStorageFix.Direction.AxisDirection.POSITIVE, ChunkPalettedStorageFix.Direction.Axis.X);

      private final ChunkPalettedStorageFix.Direction.Axis f_15147_;
      private final ChunkPalettedStorageFix.Direction.AxisDirection f_15148_;

      private Direction(ChunkPalettedStorageFix.Direction.AxisDirection p_15154_, ChunkPalettedStorageFix.Direction.Axis p_15155_) {
         this.f_15147_ = p_15155_;
         this.f_15148_ = p_15154_;
      }

      public ChunkPalettedStorageFix.Direction.AxisDirection m_15156_() {
         return this.f_15148_;
      }

      public ChunkPalettedStorageFix.Direction.Axis m_15157_() {
         return this.f_15147_;
      }

      public static enum Axis {
         X,
         Y,
         Z;
      }

      public static enum AxisDirection {
         POSITIVE(1),
         NEGATIVE(-1);

         private final int f_15174_;

         private AxisDirection(int p_15180_) {
            this.f_15174_ = p_15180_;
         }

         public int m_15181_() {
            return this.f_15174_;
         }
      }
   }

   static class Section {
      private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> f_15186_ = new CrudeIncrementalIntIdentityHashBiMap<>(32);
      private final List<Dynamic<?>> f_15187_;
      private final Dynamic<?> f_15188_;
      private final boolean f_15189_;
      final Int2ObjectMap<IntList> f_15190_ = new Int2ObjectLinkedOpenHashMap<>();
      final IntList f_15191_ = new IntArrayList();
      public final int f_15185_;
      private final Set<Dynamic<?>> f_15192_ = Sets.newIdentityHashSet();
      private final int[] f_15193_ = new int[4096];

      public Section(Dynamic<?> p_15195_) {
         this.f_15187_ = Lists.newArrayList();
         this.f_15188_ = p_15195_;
         this.f_15185_ = p_15195_.get("Y").asInt(0);
         this.f_15189_ = p_15195_.get("Blocks").result().isPresent();
      }

      public Dynamic<?> m_15197_(int p_15198_) {
         if (p_15198_ >= 0 && p_15198_ <= 4095) {
            Dynamic<?> dynamic = this.f_15186_.m_7942_(this.f_15193_[p_15198_]);
            return dynamic == null ? ChunkPalettedStorageFix.f_15055_ : dynamic;
         } else {
            return ChunkPalettedStorageFix.f_15055_;
         }
      }

      public void m_15202_(int p_15203_, Dynamic<?> p_15204_) {
         if (this.f_15192_.add(p_15204_)) {
            this.f_15187_.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.m_15064_(p_15204_)) ? ChunkPalettedStorageFix.f_15055_ : p_15204_);
         }

         this.f_15193_[p_15203_] = ChunkPalettedStorageFix.m_15061_(this.f_15186_, p_15204_);
      }

      public int m_15209_(int p_15210_) {
         if (!this.f_15189_) {
            return p_15210_;
         } else {
            ByteBuffer bytebuffer = this.f_15188_.get("Blocks").asByteBufferOpt().result().get();
            ChunkPalettedStorageFix.DataLayer chunkpalettedstoragefix$datalayer = this.f_15188_.get("Data").asByteBufferOpt().map((p_15214_) -> {
               return new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray(p_15214_));
            }).result().orElseGet(ChunkPalettedStorageFix.DataLayer::new);
            ChunkPalettedStorageFix.DataLayer chunkpalettedstoragefix$datalayer1 = this.f_15188_.get("Add").asByteBufferOpt().map((p_15208_) -> {
               return new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray(p_15208_));
            }).result().orElseGet(ChunkPalettedStorageFix.DataLayer::new);
            this.f_15192_.add(ChunkPalettedStorageFix.f_15055_);
            ChunkPalettedStorageFix.m_15061_(this.f_15186_, ChunkPalettedStorageFix.f_15055_);
            this.f_15187_.add(ChunkPalettedStorageFix.f_15055_);

            for(int i = 0; i < 4096; ++i) {
               int j = i & 15;
               int k = i >> 8 & 15;
               int l = i >> 4 & 15;
               int i1 = chunkpalettedstoragefix$datalayer1.m_15135_(j, k, l) << 12 | (bytebuffer.get(i) & 255) << 4 | chunkpalettedstoragefix$datalayer.m_15135_(j, k, l);
               if (ChunkPalettedStorageFix.f_15037_.get(i1 >> 4)) {
                  this.m_15199_(i1 >> 4, i);
               }

               if (ChunkPalettedStorageFix.f_15036_.get(i1 >> 4)) {
                  int j1 = ChunkPalettedStorageFix.m_15086_(j == 0, j == 15, l == 0, l == 15);
                  if (j1 == 0) {
                     this.f_15191_.add(i);
                  } else {
                     p_15210_ |= j1;
                  }
               }

               this.m_15202_(i, BlockStateData.m_14952_(i1));
            }

            return p_15210_;
         }
      }

      private void m_15199_(int p_15200_, int p_15201_) {
         IntList intlist = this.f_15190_.get(p_15200_);
         if (intlist == null) {
            intlist = new IntArrayList();
            this.f_15190_.put(p_15200_, intlist);
         }

         intlist.add(p_15201_);
      }

      public Dynamic<?> m_15196_() {
         Dynamic<?> dynamic = this.f_15188_;
         if (!this.f_15189_) {
            return dynamic;
         } else {
            dynamic = dynamic.set("Palette", dynamic.createList(this.f_15187_.stream()));
            int i = Math.max(4, DataFixUtils.ceillog2(this.f_15192_.size()));
            PackedBitStorage packedbitstorage = new PackedBitStorage(i, 4096);

            for(int j = 0; j < this.f_15193_.length; ++j) {
               packedbitstorage.m_14564_(j, this.f_15193_[j]);
            }

            dynamic = dynamic.set("BlockStates", dynamic.createLongList(Arrays.stream(packedbitstorage.m_14561_())));
            dynamic = dynamic.remove("Blocks");
            dynamic = dynamic.remove("Data");
            return dynamic.remove("Add");
         }
      }
   }

   static final class UpgradeChunk {
      private int f_15215_;
      private final ChunkPalettedStorageFix.Section[] f_15216_ = new ChunkPalettedStorageFix.Section[16];
      private final Dynamic<?> f_15217_;
      private final int f_15218_;
      private final int f_15219_;
      private final Int2ObjectMap<Dynamic<?>> f_15220_ = new Int2ObjectLinkedOpenHashMap<>(16);

      public UpgradeChunk(Dynamic<?> p_15222_) {
         this.f_15217_ = p_15222_;
         this.f_15218_ = p_15222_.get("xPos").asInt(0) << 4;
         this.f_15219_ = p_15222_.get("zPos").asInt(0) << 4;
         p_15222_.get("TileEntities").asStreamOpt().result().ifPresent((p_15241_) -> {
            p_15241_.forEach((p_145228_) -> {
               int l3 = p_145228_.get("x").asInt(0) - this.f_15218_ & 15;
               int i4 = p_145228_.get("y").asInt(0);
               int j4 = p_145228_.get("z").asInt(0) - this.f_15219_ & 15;
               int k4 = i4 << 8 | j4 << 4 | l3;
               if (this.f_15220_.put(k4, p_145228_) != null) {
                  ChunkPalettedStorageFix.f_15035_.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.f_15218_, this.f_15219_, l3, i4, j4);
               }

            });
         });
         boolean flag = p_15222_.get("convertedFromAlphaFormat").asBoolean(false);
         p_15222_.get("Sections").asStreamOpt().result().ifPresent((p_15235_) -> {
            p_15235_.forEach((p_145226_) -> {
               ChunkPalettedStorageFix.Section chunkpalettedstoragefix$section1 = new ChunkPalettedStorageFix.Section(p_145226_);
               this.f_15215_ = chunkpalettedstoragefix$section1.m_15209_(this.f_15215_);
               this.f_15216_[chunkpalettedstoragefix$section1.f_15185_] = chunkpalettedstoragefix$section1;
            });
         });

         for(ChunkPalettedStorageFix.Section chunkpalettedstoragefix$section : this.f_15216_) {
            if (chunkpalettedstoragefix$section != null) {
               for(java.util.Map.Entry<Integer, IntList> entry : chunkpalettedstoragefix$section.f_15190_.entrySet()) {
                  int i = chunkpalettedstoragefix$section.f_15185_ << 12;
                  switch(entry.getKey()) {
                  case 2:
                     for(int i3 : entry.getValue()) {
                        i3 = i3 | i;
                        Dynamic<?> dynamic11 = this.m_15224_(i3);
                        if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.m_15064_(dynamic11))) {
                           String s12 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(m_15226_(i3, ChunkPalettedStorageFix.Direction.UP)));
                           if ("minecraft:snow".equals(s12) || "minecraft:snow_layer".equals(s12)) {
                              this.m_15229_(i3, ChunkPalettedStorageFix.f_15040_);
                           }
                        }
                     }
                     break;
                  case 3:
                     for(int l2 : entry.getValue()) {
                        l2 = l2 | i;
                        Dynamic<?> dynamic10 = this.m_15224_(l2);
                        if ("minecraft:podzol".equals(ChunkPalettedStorageFix.m_15064_(dynamic10))) {
                           String s11 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(m_15226_(l2, ChunkPalettedStorageFix.Direction.UP)));
                           if ("minecraft:snow".equals(s11) || "minecraft:snow_layer".equals(s11)) {
                              this.m_15229_(l2, ChunkPalettedStorageFix.f_15039_);
                           }
                        }
                     }
                     break;
                  case 25:
                     for(int k2 : entry.getValue()) {
                        k2 = k2 | i;
                        Dynamic<?> dynamic9 = this.m_15242_(k2);
                        if (dynamic9 != null) {
                           String s10 = Boolean.toString(dynamic9.get("powered").asBoolean(false)) + (byte)Math.min(Math.max(dynamic9.get("note").asInt(0), 0), 24);
                           this.m_15229_(k2, ChunkPalettedStorageFix.f_15051_.getOrDefault(s10, ChunkPalettedStorageFix.f_15051_.get("false0")));
                        }
                     }
                     break;
                  case 26:
                     for(int j2 : entry.getValue()) {
                        j2 = j2 | i;
                        Dynamic<?> dynamic8 = this.m_15236_(j2);
                        Dynamic<?> dynamic14 = this.m_15224_(j2);
                        if (dynamic8 != null) {
                           int k3 = dynamic8.get("color").asInt(0);
                           if (k3 != 14 && k3 >= 0 && k3 < 16) {
                              String s16 = ChunkPalettedStorageFix.m_15066_(dynamic14, "facing") + ChunkPalettedStorageFix.m_15066_(dynamic14, "occupied") + ChunkPalettedStorageFix.m_15066_(dynamic14, "part") + k3;
                              if (ChunkPalettedStorageFix.f_15053_.containsKey(s16)) {
                                 this.m_15229_(j2, ChunkPalettedStorageFix.f_15053_.get(s16));
                              }
                           }
                        }
                     }
                     break;
                  case 64:
                  case 71:
                  case 193:
                  case 194:
                  case 195:
                  case 196:
                  case 197:
                     for(int i2 : entry.getValue()) {
                        i2 = i2 | i;
                        Dynamic<?> dynamic7 = this.m_15224_(i2);
                        if (ChunkPalettedStorageFix.m_15064_(dynamic7).endsWith("_door")) {
                           Dynamic<?> dynamic13 = this.m_15224_(i2);
                           if ("lower".equals(ChunkPalettedStorageFix.m_15066_(dynamic13, "half"))) {
                              int j3 = m_15226_(i2, ChunkPalettedStorageFix.Direction.UP);
                              Dynamic<?> dynamic15 = this.m_15224_(j3);
                              String s1 = ChunkPalettedStorageFix.m_15064_(dynamic13);
                              if (s1.equals(ChunkPalettedStorageFix.m_15064_(dynamic15))) {
                                 String s2 = ChunkPalettedStorageFix.m_15066_(dynamic13, "facing");
                                 String s3 = ChunkPalettedStorageFix.m_15066_(dynamic13, "open");
                                 String s4 = flag ? "left" : ChunkPalettedStorageFix.m_15066_(dynamic15, "hinge");
                                 String s5 = flag ? "false" : ChunkPalettedStorageFix.m_15066_(dynamic15, "powered");
                                 this.m_15229_(i2, ChunkPalettedStorageFix.f_15050_.get(s1 + s2 + "lower" + s4 + s3 + s5));
                                 this.m_15229_(j3, ChunkPalettedStorageFix.f_15050_.get(s1 + s2 + "upper" + s4 + s3 + s5));
                              }
                           }
                        }
                     }
                     break;
                  case 86:
                     for(int l1 : entry.getValue()) {
                        l1 = l1 | i;
                        Dynamic<?> dynamic6 = this.m_15224_(l1);
                        if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.m_15064_(dynamic6))) {
                           String s9 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(m_15226_(l1, ChunkPalettedStorageFix.Direction.DOWN)));
                           if ("minecraft:grass_block".equals(s9) || "minecraft:dirt".equals(s9)) {
                              this.m_15229_(l1, ChunkPalettedStorageFix.f_15038_);
                           }
                        }
                     }
                     break;
                  case 110:
                     for(int k1 : entry.getValue()) {
                        k1 = k1 | i;
                        Dynamic<?> dynamic5 = this.m_15224_(k1);
                        if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.m_15064_(dynamic5))) {
                           String s8 = ChunkPalettedStorageFix.m_15064_(this.m_15224_(m_15226_(k1, ChunkPalettedStorageFix.Direction.UP)));
                           if ("minecraft:snow".equals(s8) || "minecraft:snow_layer".equals(s8)) {
                              this.m_15229_(k1, ChunkPalettedStorageFix.f_15041_);
                           }
                        }
                     }
                     break;
                  case 140:
                     for(int j1 : entry.getValue()) {
                        j1 = j1 | i;
                        Dynamic<?> dynamic4 = this.m_15242_(j1);
                        if (dynamic4 != null) {
                           String s7 = dynamic4.get("Item").asString("") + dynamic4.get("Data").asInt(0);
                           this.m_15229_(j1, ChunkPalettedStorageFix.f_15048_.getOrDefault(s7, ChunkPalettedStorageFix.f_15048_.get("minecraft:air0")));
                        }
                     }
                     break;
                  case 144:
                     for(int i1 : entry.getValue()) {
                        i1 = i1 | i;
                        Dynamic<?> dynamic3 = this.m_15236_(i1);
                        if (dynamic3 != null) {
                           String s6 = String.valueOf(dynamic3.get("SkullType").asInt(0));
                           String s14 = ChunkPalettedStorageFix.m_15066_(this.m_15224_(i1), "facing");
                           String s15;
                           if (!"up".equals(s14) && !"down".equals(s14)) {
                              s15 = s6 + s14;
                           } else {
                              s15 = s6 + String.valueOf(dynamic3.get("Rot").asInt(0));
                           }

                           dynamic3.remove("SkullType");
                           dynamic3.remove("facing");
                           dynamic3.remove("Rot");
                           this.m_15229_(i1, ChunkPalettedStorageFix.f_15049_.getOrDefault(s15, ChunkPalettedStorageFix.f_15049_.get("0north")));
                        }
                     }
                     break;
                  case 175:
                     for(int l : entry.getValue()) {
                        l = l | i;
                        Dynamic<?> dynamic2 = this.m_15224_(l);
                        if ("upper".equals(ChunkPalettedStorageFix.m_15066_(dynamic2, "half"))) {
                           Dynamic<?> dynamic12 = this.m_15224_(m_15226_(l, ChunkPalettedStorageFix.Direction.DOWN));
                           String s13 = ChunkPalettedStorageFix.m_15064_(dynamic12);
                           if ("minecraft:sunflower".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15042_);
                           } else if ("minecraft:lilac".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15043_);
                           } else if ("minecraft:tall_grass".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15044_);
                           } else if ("minecraft:large_fern".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15045_);
                           } else if ("minecraft:rose_bush".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15046_);
                           } else if ("minecraft:peony".equals(s13)) {
                              this.m_15229_(l, ChunkPalettedStorageFix.f_15047_);
                           }
                        }
                     }
                     break;
                  case 176:
                  case 177:
                     for(int j : entry.getValue()) {
                        j = j | i;
                        Dynamic<?> dynamic = this.m_15236_(j);
                        Dynamic<?> dynamic1 = this.m_15224_(j);
                        if (dynamic != null) {
                           int k = dynamic.get("Base").asInt(0);
                           if (k != 15 && k >= 0 && k < 16) {
                              String s = ChunkPalettedStorageFix.m_15066_(dynamic1, entry.getKey() == 176 ? "rotation" : "facing") + "_" + k;
                              if (ChunkPalettedStorageFix.f_15054_.containsKey(s)) {
                                 this.m_15229_(j, ChunkPalettedStorageFix.f_15054_.get(s));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

      }

      @Nullable
      private Dynamic<?> m_15236_(int p_15237_) {
         return this.f_15220_.get(p_15237_);
      }

      @Nullable
      private Dynamic<?> m_15242_(int p_15243_) {
         return this.f_15220_.remove(p_15243_);
      }

      public static int m_15226_(int p_15227_, ChunkPalettedStorageFix.Direction p_15228_) {
         switch(p_15228_.m_15157_()) {
         case X:
            int i = (p_15227_ & 15) + p_15228_.m_15156_().m_15181_();
            return i >= 0 && i <= 15 ? p_15227_ & -16 | i : -1;
         case Y:
            int j = (p_15227_ >> 8) + p_15228_.m_15156_().m_15181_();
            return j >= 0 && j <= 255 ? p_15227_ & 255 | j << 8 : -1;
         case Z:
            int k = (p_15227_ >> 4 & 15) + p_15228_.m_15156_().m_15181_();
            return k >= 0 && k <= 15 ? p_15227_ & -241 | k << 4 : -1;
         default:
            return -1;
         }
      }

      private void m_15229_(int p_15230_, Dynamic<?> p_15231_) {
         if (p_15230_ >= 0 && p_15230_ <= 65535) {
            ChunkPalettedStorageFix.Section chunkpalettedstoragefix$section = this.m_15244_(p_15230_);
            if (chunkpalettedstoragefix$section != null) {
               chunkpalettedstoragefix$section.m_15202_(p_15230_ & 4095, p_15231_);
            }
         }
      }

      @Nullable
      private ChunkPalettedStorageFix.Section m_15244_(int p_15245_) {
         int i = p_15245_ >> 12;
         return i < this.f_15216_.length ? this.f_15216_[i] : null;
      }

      public Dynamic<?> m_15224_(int p_15225_) {
         if (p_15225_ >= 0 && p_15225_ <= 65535) {
            ChunkPalettedStorageFix.Section chunkpalettedstoragefix$section = this.m_15244_(p_15225_);
            return chunkpalettedstoragefix$section == null ? ChunkPalettedStorageFix.f_15055_ : chunkpalettedstoragefix$section.m_15197_(p_15225_ & 4095);
         } else {
            return ChunkPalettedStorageFix.f_15055_;
         }
      }

      public Dynamic<?> m_15223_() {
         Dynamic<?> dynamic = this.f_15217_;
         if (this.f_15220_.isEmpty()) {
            dynamic = dynamic.remove("TileEntities");
         } else {
            dynamic = dynamic.set("TileEntities", dynamic.createList(this.f_15220_.values().stream()));
         }

         Dynamic<?> dynamic1 = dynamic.emptyMap();
         List<Dynamic<?>> list = Lists.newArrayList();

         for(ChunkPalettedStorageFix.Section chunkpalettedstoragefix$section : this.f_15216_) {
            if (chunkpalettedstoragefix$section != null) {
               list.add(chunkpalettedstoragefix$section.m_15196_());
               dynamic1 = dynamic1.set(String.valueOf(chunkpalettedstoragefix$section.f_15185_), dynamic1.createIntList(Arrays.stream(chunkpalettedstoragefix$section.f_15191_.toIntArray())));
            }
         }

         Dynamic<?> dynamic2 = dynamic.emptyMap();
         dynamic2 = dynamic2.set("Sides", dynamic2.createByte((byte)this.f_15215_));
         dynamic2 = dynamic2.set("Indices", dynamic1);
         return dynamic.set("UpgradeData", dynamic2).set("Sections", dynamic2.createList(list.stream()));
      }
   }
}