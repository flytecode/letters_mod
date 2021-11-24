package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum RecipeBookCategories {
   CRAFTING_SEARCH(new ItemStack(Items.f_42522_)),
   CRAFTING_BUILDING_BLOCKS(new ItemStack(Blocks.f_50076_)),
   CRAFTING_REDSTONE(new ItemStack(Items.f_42451_)),
   CRAFTING_EQUIPMENT(new ItemStack(Items.f_42386_), new ItemStack(Items.f_42430_)),
   CRAFTING_MISC(new ItemStack(Items.f_42448_), new ItemStack(Items.f_42410_)),
   FURNACE_SEARCH(new ItemStack(Items.f_42522_)),
   FURNACE_FOOD(new ItemStack(Items.f_42485_)),
   FURNACE_BLOCKS(new ItemStack(Blocks.f_50069_)),
   FURNACE_MISC(new ItemStack(Items.f_42448_), new ItemStack(Items.f_42616_)),
   BLAST_FURNACE_SEARCH(new ItemStack(Items.f_42522_)),
   BLAST_FURNACE_BLOCKS(new ItemStack(Blocks.f_50173_)),
   BLAST_FURNACE_MISC(new ItemStack(Items.f_42384_), new ItemStack(Items.f_42478_)),
   SMOKER_SEARCH(new ItemStack(Items.f_42522_)),
   SMOKER_FOOD(new ItemStack(Items.f_42485_)),
   STONECUTTER(new ItemStack(Items.f_42021_)),
   SMITHING(new ItemStack(Items.f_42481_)),
   CAMPFIRE(new ItemStack(Items.f_42485_)),
   UNKNOWN(new ItemStack(Items.f_42127_));

   public static final List<RecipeBookCategories> f_92256_ = ImmutableList.of(SMOKER_SEARCH, SMOKER_FOOD);
   public static final List<RecipeBookCategories> f_92257_ = ImmutableList.of(BLAST_FURNACE_SEARCH, BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC);
   public static final List<RecipeBookCategories> f_92258_ = ImmutableList.of(FURNACE_SEARCH, FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC);
   public static final List<RecipeBookCategories> f_92259_ = ImmutableList.of(CRAFTING_SEARCH, CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE);
   public static final Map<RecipeBookCategories, List<RecipeBookCategories>> f_92260_ = ImmutableMap.of(CRAFTING_SEARCH, ImmutableList.of(CRAFTING_EQUIPMENT, CRAFTING_BUILDING_BLOCKS, CRAFTING_MISC, CRAFTING_REDSTONE), FURNACE_SEARCH, ImmutableList.of(FURNACE_FOOD, FURNACE_BLOCKS, FURNACE_MISC), BLAST_FURNACE_SEARCH, ImmutableList.of(BLAST_FURNACE_BLOCKS, BLAST_FURNACE_MISC), SMOKER_SEARCH, ImmutableList.of(SMOKER_FOOD));
   private final List<ItemStack> f_92261_;

   private RecipeBookCategories(ItemStack... p_92267_) {
      this.f_92261_ = ImmutableList.copyOf(p_92267_);
   }

   public static List<RecipeBookCategories> m_92269_(RecipeBookType p_92270_) {
      switch(p_92270_) {
      case CRAFTING:
         return f_92259_;
      case FURNACE:
         return f_92258_;
      case BLAST_FURNACE:
         return f_92257_;
      case SMOKER:
         return f_92256_;
      default:
         return ImmutableList.of();
      }
   }

   public List<ItemStack> m_92268_() {
      return this.f_92261_;
   }
}