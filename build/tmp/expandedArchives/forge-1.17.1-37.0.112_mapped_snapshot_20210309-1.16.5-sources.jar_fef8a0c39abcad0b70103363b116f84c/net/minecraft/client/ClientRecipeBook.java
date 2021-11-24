package net.minecraft.client;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.core.Registry;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientRecipeBook extends RecipeBook {
   private static final Logger f_90618_ = LogManager.getLogger();
   private Map<RecipeBookCategories, List<RecipeCollection>> f_90619_ = ImmutableMap.of();
   private List<RecipeCollection> f_90620_ = ImmutableList.of();

   public void m_90625_(Iterable<Recipe<?>> p_90626_) {
      Map<RecipeBookCategories, List<List<Recipe<?>>>> map = m_90642_(p_90626_);
      Map<RecipeBookCategories, List<RecipeCollection>> map1 = Maps.newHashMap();
      Builder<RecipeCollection> builder = ImmutableList.builder();
      map.forEach((p_90630_, p_90631_) -> {
         map1.put(p_90630_, p_90631_.stream().map(RecipeCollection::new).peek(builder::add).collect(ImmutableList.toImmutableList()));
      });
      RecipeBookCategories.f_92260_.forEach((p_90637_, p_90638_) -> {
         map1.put(p_90637_, p_90638_.stream().flatMap((p_167706_) -> {
            return map1.getOrDefault(p_167706_, ImmutableList.of()).stream();
         }).collect(ImmutableList.toImmutableList()));
      });
      this.f_90619_ = ImmutableMap.copyOf(map1);
      this.f_90620_ = builder.build();
   }

   private static Map<RecipeBookCategories, List<List<Recipe<?>>>> m_90642_(Iterable<Recipe<?>> p_90643_) {
      Map<RecipeBookCategories, List<List<Recipe<?>>>> map = Maps.newHashMap();
      Table<RecipeBookCategories, String, List<Recipe<?>>> table = HashBasedTable.create();

      for(Recipe<?> recipe : p_90643_) {
         if (!recipe.m_5598_() && !recipe.m_142505_()) {
            RecipeBookCategories recipebookcategories = m_90646_(recipe);
            String s = recipe.m_6076_();
            if (s.isEmpty()) {
               map.computeIfAbsent(recipebookcategories, (p_90645_) -> {
                  return Lists.newArrayList();
               }).add(ImmutableList.of(recipe));
            } else {
               List<Recipe<?>> list = table.get(recipebookcategories, s);
               if (list == null) {
                  list = Lists.newArrayList();
                  table.put(recipebookcategories, s, list);
                  map.computeIfAbsent(recipebookcategories, (p_90641_) -> {
                     return Lists.newArrayList();
                  }).add(list);
               }

               list.add(recipe);
            }
         }
      }

      return map;
   }

   private static RecipeBookCategories m_90646_(Recipe<?> p_90647_) {
      RecipeType<?> recipetype = p_90647_.m_6671_();
      if (recipetype == RecipeType.f_44107_) {
         ItemStack itemstack = p_90647_.m_8043_();
         CreativeModeTab creativemodetab = itemstack.m_41720_().m_41471_();
         if (creativemodetab == CreativeModeTab.f_40749_) {
            return RecipeBookCategories.CRAFTING_BUILDING_BLOCKS;
         } else if (creativemodetab != CreativeModeTab.f_40756_ && creativemodetab != CreativeModeTab.f_40757_) {
            return creativemodetab == CreativeModeTab.f_40751_ ? RecipeBookCategories.CRAFTING_REDSTONE : RecipeBookCategories.CRAFTING_MISC;
         } else {
            return RecipeBookCategories.CRAFTING_EQUIPMENT;
         }
      } else if (recipetype == RecipeType.f_44108_) {
         if (p_90647_.m_8043_().m_41720_().m_41472_()) {
            return RecipeBookCategories.FURNACE_FOOD;
         } else {
            return p_90647_.m_8043_().m_41720_() instanceof BlockItem ? RecipeBookCategories.FURNACE_BLOCKS : RecipeBookCategories.FURNACE_MISC;
         }
      } else if (recipetype == RecipeType.f_44109_) {
         return p_90647_.m_8043_().m_41720_() instanceof BlockItem ? RecipeBookCategories.BLAST_FURNACE_BLOCKS : RecipeBookCategories.BLAST_FURNACE_MISC;
      } else if (recipetype == RecipeType.f_44110_) {
         return RecipeBookCategories.SMOKER_FOOD;
      } else if (recipetype == RecipeType.f_44112_) {
         return RecipeBookCategories.STONECUTTER;
      } else if (recipetype == RecipeType.f_44111_) {
         return RecipeBookCategories.CAMPFIRE;
      } else if (recipetype == RecipeType.f_44113_) {
         return RecipeBookCategories.SMITHING;
      } else {
         f_90618_.warn("Unknown recipe category: {}/{}", () -> {
            return Registry.f_122864_.m_7981_(p_90647_.m_6671_());
         }, p_90647_::m_6423_);
         return RecipeBookCategories.UNKNOWN;
      }
   }

   public List<RecipeCollection> m_90639_() {
      return this.f_90620_;
   }

   public List<RecipeCollection> m_90623_(RecipeBookCategories p_90624_) {
      return this.f_90619_.getOrDefault(p_90624_, Collections.emptyList());
   }
}