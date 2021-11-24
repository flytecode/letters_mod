package net.minecraft.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableProvider implements DataProvider {
   private static final Logger f_124431_ = LogManager.getLogger();
   private static final Gson f_124432_ = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator f_124433_;
   private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> f_124434_ = ImmutableList.of(Pair.of(FishingLoot::new, LootContextParamSets.f_81414_), Pair.of(ChestLoot::new, LootContextParamSets.f_81411_), Pair.of(EntityLoot::new, LootContextParamSets.f_81415_), Pair.of(BlockLoot::new, LootContextParamSets.f_81421_), Pair.of(PiglinBarterLoot::new, LootContextParamSets.f_81417_), Pair.of(GiftLoot::new, LootContextParamSets.f_81416_));

   public LootTableProvider(DataGenerator p_124437_) {
      this.f_124433_ = p_124437_;
   }

   public void m_6865_(HashCache p_124444_) {
      Path path = this.f_124433_.m_123916_();
      Map<ResourceLocation, LootTable> map = Maps.newHashMap();
      this.getTables().forEach((p_124458_) -> {
         p_124458_.getFirst().get().accept((p_176077_, p_176078_) -> {
            if (map.put(p_176077_, p_176078_.m_79165_(p_124458_.getSecond()).m_79167_()) != null) {
               throw new IllegalStateException("Duplicate loot table " + p_176077_);
            }
         });
      });
      ValidationContext validationcontext = new ValidationContext(LootContextParamSets.f_81420_, (p_124465_) -> {
         return null;
      }, map::get);

      validate(map, validationcontext);

      Multimap<String, String> multimap = validationcontext.m_79352_();
      if (!multimap.isEmpty()) {
         multimap.forEach((p_124446_, p_124447_) -> {
            f_124431_.warn("Found validation problem in {}: {}", p_124446_, p_124447_);
         });
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         map.forEach((p_124451_, p_124452_) -> {
            Path path1 = m_124453_(path, p_124451_);

            try {
               DataProvider.m_123920_(f_124432_, p_124444_, LootTables.m_79200_(p_124452_), path1);
            } catch (IOException ioexception) {
               f_124431_.error("Couldn't save loot table {}", path1, ioexception);
            }

         });
      }
   }

   protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
      return f_124434_;
   }

   protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
      for(ResourceLocation resourcelocation : Sets.difference(BuiltInLootTables.m_78766_(), map.keySet())) {
         validationtracker.m_79357_("Missing built-in table: " + resourcelocation);
      }

      map.forEach((p_218436_2_, p_218436_3_) -> {
         LootTables.m_79202_(validationtracker, p_218436_2_, p_218436_3_);
      });
   }

   private static Path m_124453_(Path p_124454_, ResourceLocation p_124455_) {
      return p_124454_.resolve("data/" + p_124455_.m_135827_() + "/loot_tables/" + p_124455_.m_135815_() + ".json");
   }

   public String m_6055_() {
      return "LootTables";
   }
}
