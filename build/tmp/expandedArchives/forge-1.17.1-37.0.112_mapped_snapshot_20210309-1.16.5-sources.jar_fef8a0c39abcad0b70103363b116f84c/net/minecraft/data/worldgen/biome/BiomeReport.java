package net.minecraft.data.worldgen.biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeReport implements DataProvider {
   private static final Logger f_127307_ = LogManager.getLogger();
   private static final Gson f_127308_ = (new GsonBuilder()).setPrettyPrinting().create();
   private final DataGenerator f_127309_;

   public BiomeReport(DataGenerator p_127312_) {
      this.f_127309_ = p_127312_;
   }

   public void m_6865_(HashCache p_127317_) {
      Path path = this.f_127309_.m_123916_();

      for(Entry<ResourceKey<Biome>, Biome> entry : BuiltinRegistries.f_123865_.m_6579_()) {
         Path path1 = m_127318_(path, entry.getKey().m_135782_());
         Biome biome = entry.getValue();
         Function<Supplier<Biome>, DataResult<JsonElement>> function = JsonOps.INSTANCE.withEncoder(Biome.f_47431_);

         try {
            Optional<JsonElement> optional = function.apply(() -> {
               return biome;
            }).result();
            if (optional.isPresent()) {
               DataProvider.m_123920_(f_127308_, p_127317_, optional.get(), path1);
            } else {
               f_127307_.error("Couldn't serialize biome {}", (Object)path1);
            }
         } catch (IOException ioexception) {
            f_127307_.error("Couldn't save biome {}", path1, ioexception);
         }
      }

   }

   private static Path m_127318_(Path p_127319_, ResourceLocation p_127320_) {
      return p_127319_.resolve("reports/biomes/" + p_127320_.m_135815_() + ".json");
   }

   public String m_6055_() {
      return "Biomes";
   }
}