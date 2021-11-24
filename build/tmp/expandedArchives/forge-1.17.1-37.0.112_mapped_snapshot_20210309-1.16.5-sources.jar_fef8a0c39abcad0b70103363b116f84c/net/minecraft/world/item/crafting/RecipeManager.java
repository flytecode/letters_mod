package net.minecraft.world.item.crafting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeManager extends SimpleJsonResourceReloadListener {
   private static final Gson f_44005_ = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private static final Logger f_44006_ = LogManager.getLogger();
   private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> f_44007_ = ImmutableMap.of();
   private boolean f_44008_;

   public RecipeManager() {
      super(f_44005_, "recipes");
   }

   protected void m_5787_(Map<ResourceLocation, JsonElement> p_44037_, ResourceManager p_44038_, ProfilerFiller p_44039_) {
      this.f_44008_ = false;
      Map<RecipeType<?>, Builder<ResourceLocation, Recipe<?>>> map = Maps.newHashMap();

      for(Entry<ResourceLocation, JsonElement> entry : p_44037_.entrySet()) {
         ResourceLocation resourcelocation = entry.getKey();
         if (resourcelocation.m_135815_().startsWith("_")) continue; //Forge: filter anything beginning with "_" as it's used for metadata.

         try {
            if (entry.getValue().isJsonObject() && !net.minecraftforge.common.crafting.CraftingHelper.processConditions(entry.getValue().getAsJsonObject(), "conditions")) {
               f_44006_.debug("Skipping loading recipe {} as it's conditions were not met", resourcelocation);
               continue;
            }
            Recipe<?> recipe = m_44045_(resourcelocation, GsonHelper.m_13918_(entry.getValue(), "top element"));
            if (recipe == null) {
               f_44006_.info("Skipping loading recipe {} as it's serializer returned null", resourcelocation);
               continue;
            }
            map.computeIfAbsent(recipe.m_6671_(), (p_44075_) -> {
               return ImmutableMap.builder();
            }).put(resourcelocation, recipe);
         } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
            f_44006_.error("Parsing error loading recipe {}", resourcelocation, jsonparseexception);
         }
      }

      this.f_44007_ = map.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_44033_) -> {
         return p_44033_.getValue().build();
      }));
      f_44006_.info("Loaded {} recipes", (int)map.size());
   }

   public boolean m_151269_() {
      return this.f_44008_;
   }

   public <C extends Container, T extends Recipe<C>> Optional<T> m_44015_(RecipeType<T> p_44016_, C p_44017_, Level p_44018_) {
      return this.m_44054_(p_44016_).values().stream().flatMap((p_44064_) -> {
         return Util.m_137519_(p_44016_.m_44115_(p_44064_, p_44018_, p_44017_));
      }).findFirst();
   }

   public <C extends Container, T extends Recipe<C>> List<T> m_44013_(RecipeType<T> p_44014_) {
      return this.m_44054_(p_44014_).values().stream().map((p_44053_) -> {
         return (T)p_44053_;
      }).collect(Collectors.toList());
   }

   public <C extends Container, T extends Recipe<C>> List<T> m_44056_(RecipeType<T> p_44057_, C p_44058_, Level p_44059_) {
      return this.m_44054_(p_44057_).values().stream().flatMap((p_44023_) -> {
         return Util.m_137519_(p_44057_.m_44115_(p_44023_, p_44059_, p_44058_));
      }).sorted(Comparator.comparing((p_44012_) -> {
         return p_44012_.m_8043_().m_41778_();
      })).collect(Collectors.toList());
   }

   private <C extends Container, T extends Recipe<C>> Map<ResourceLocation, Recipe<C>> m_44054_(RecipeType<T> p_44055_) {
      return (Map<ResourceLocation, Recipe<C>>)(Map<ResourceLocation, T>)this.f_44007_.getOrDefault(p_44055_, Collections.emptyMap());
   }

   public <C extends Container, T extends Recipe<C>> NonNullList<ItemStack> m_44069_(RecipeType<T> p_44070_, C p_44071_, Level p_44072_) {
      Optional<T> optional = this.m_44015_(p_44070_, p_44071_, p_44072_);
      if (optional.isPresent()) {
         return optional.get().m_7457_(p_44071_);
      } else {
         NonNullList<ItemStack> nonnulllist = NonNullList.m_122780_(p_44071_.m_6643_(), ItemStack.f_41583_);

         for(int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, p_44071_.m_8020_(i));
         }

         return nonnulllist;
      }
   }

   public Optional<? extends Recipe<?>> m_44043_(ResourceLocation p_44044_) {
      return this.f_44007_.values().stream().map((p_44050_) -> {
         return p_44050_.get(p_44044_);
      }).filter(Objects::nonNull).findFirst();
   }

   public Collection<Recipe<?>> m_44051_() {
      return this.f_44007_.values().stream().flatMap((p_44066_) -> {
         return p_44066_.values().stream();
      }).collect(Collectors.toSet());
   }

   public Stream<ResourceLocation> m_44073_() {
      return this.f_44007_.values().stream().flatMap((p_44035_) -> {
         return p_44035_.keySet().stream();
      });
   }

   public static Recipe<?> m_44045_(ResourceLocation p_44046_, JsonObject p_44047_) {
      String s = GsonHelper.m_13906_(p_44047_, "type");
      return Registry.f_122865_.m_6612_(new ResourceLocation(s)).orElseThrow(() -> {
         return new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
      }).m_6729_(p_44046_, p_44047_);
   }

   public void m_44024_(Iterable<Recipe<?>> p_44025_) {
      this.f_44008_ = false;
      Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> map = Maps.newHashMap();
      p_44025_.forEach((p_44042_) -> {
         Map<ResourceLocation, Recipe<?>> map1 = map.computeIfAbsent(p_44042_.m_6671_(), (p_151271_) -> {
            return Maps.newHashMap();
         });
         Recipe<?> recipe = map1.put(p_44042_.m_6423_(), p_44042_);
         if (recipe != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + p_44042_.m_6423_());
         }
      });
      this.f_44007_ = ImmutableMap.copyOf(map);
   }
}
