package net.minecraft.data.recipes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeProvider implements DataProvider {
   private static final Logger f_125968_ = LogManager.getLogger();
   private static final Gson f_125969_ = (new GsonBuilder()).setPrettyPrinting().create();
   private static final ImmutableList<ItemLike> f_176505_ = ImmutableList.of(Items.f_41835_, Items.f_150963_);
   private static final ImmutableList<ItemLike> f_176506_ = ImmutableList.of(Items.f_41834_, Items.f_150964_, Items.f_151050_);
   private static final ImmutableList<ItemLike> f_176507_ = ImmutableList.of(Items.f_150965_, Items.f_150966_, Items.f_151051_);
   private static final ImmutableList<ItemLike> f_176508_ = ImmutableList.of(Items.f_41833_, Items.f_150967_, Items.f_41836_, Items.f_151053_);
   private static final ImmutableList<ItemLike> f_176509_ = ImmutableList.of(Items.f_42010_, Items.f_150994_);
   private static final ImmutableList<ItemLike> f_176510_ = ImmutableList.of(Items.f_41853_, Items.f_150993_);
   private static final ImmutableList<ItemLike> f_176511_ = ImmutableList.of(Items.f_41977_, Items.f_150968_);
   private static final ImmutableList<ItemLike> f_176512_ = ImmutableList.of(Items.f_42107_, Items.f_150969_);
   protected final DataGenerator f_125970_;
   private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> f_176513_ = ImmutableMap.<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>builder().put(BlockFamily.Variant.BUTTON, (p_176733_, p_176734_) -> {
      return m_176658_(p_176733_, Ingredient.m_43929_(p_176734_));
   }).put(BlockFamily.Variant.CHISELED, (p_176730_, p_176731_) -> {
      return m_176646_(p_176730_, Ingredient.m_43929_(p_176731_));
   }).put(BlockFamily.Variant.CUT, (p_176724_, p_176725_) -> {
      return m_176634_(p_176724_, Ingredient.m_43929_(p_176725_));
   }).put(BlockFamily.Variant.DOOR, (p_176714_, p_176715_) -> {
      return m_176670_(p_176714_, Ingredient.m_43929_(p_176715_));
   }).put(BlockFamily.Variant.FENCE, (p_176708_, p_176709_) -> {
      return m_176678_(p_176708_, Ingredient.m_43929_(p_176709_));
   }).put(BlockFamily.Variant.FENCE_GATE, (p_176698_, p_176699_) -> {
      return m_176684_(p_176698_, Ingredient.m_43929_(p_176699_));
   }).put(BlockFamily.Variant.SIGN, (p_176688_, p_176689_) -> {
      return m_176726_(p_176688_, Ingredient.m_43929_(p_176689_));
   }).put(BlockFamily.Variant.SLAB, (p_176682_, p_176683_) -> {
      return m_176704_(p_176682_, Ingredient.m_43929_(p_176683_));
   }).put(BlockFamily.Variant.STAIRS, (p_176674_, p_176675_) -> {
      return m_176710_(p_176674_, Ingredient.m_43929_(p_176675_));
   }).put(BlockFamily.Variant.PRESSURE_PLATE, (p_176662_, p_176663_) -> {
      return m_176694_(p_176662_, Ingredient.m_43929_(p_176663_));
   }).put(BlockFamily.Variant.POLISHED, (p_176650_, p_176651_) -> {
      return m_176604_(p_176650_, Ingredient.m_43929_(p_176651_));
   }).put(BlockFamily.Variant.TRAPDOOR, (p_176638_, p_176639_) -> {
      return m_176720_(p_176638_, Ingredient.m_43929_(p_176639_));
   }).put(BlockFamily.Variant.WALL, (p_176608_, p_176609_) -> {
      return m_176514_(p_176608_, Ingredient.m_43929_(p_176609_));
   }).build();

   public RecipeProvider(DataGenerator p_125973_) {
      this.f_125970_ = p_125973_;
   }

   public void m_6865_(HashCache p_125982_) {
      Path path = this.f_125970_.m_123916_();
      Set<ResourceLocation> set = Sets.newHashSet();
      m_176531_((p_125991_) -> {
         if (!set.add(p_125991_.m_6445_())) {
            throw new IllegalStateException("Duplicate recipe " + p_125991_.m_6445_());
         } else {
            m_125983_(p_125982_, p_125991_.m_125966_(), path.resolve("data/" + p_125991_.m_6445_().m_135827_() + "/recipes/" + p_125991_.m_6445_().m_135815_() + ".json"));
            JsonObject jsonobject = p_125991_.m_5860_();
            if (jsonobject != null) {
               m_126013_(p_125982_, jsonobject, path.resolve("data/" + p_125991_.m_6445_().m_135827_() + "/advancements/" + p_125991_.m_6448_().m_135815_() + ".json"));
            }

         }
      });
      if (this.getClass() == RecipeProvider.class) //Forge: Subclasses don't need this.
      m_126013_(p_125982_, Advancement.Builder.m_138353_().m_138386_("impossible", new ImpossibleTrigger.TriggerInstance()).m_138400_(), path.resolve("data/minecraft/advancements/recipes/root.json"));
   }

   private static void m_125983_(HashCache p_125984_, JsonObject p_125985_, Path p_125986_) {
      try {
         String s = f_125969_.toJson((JsonElement)p_125985_);
         String s1 = f_123918_.hashUnencodedChars(s).toString();
         if (!Objects.equals(p_125984_.m_123938_(p_125986_), s1) || !Files.exists(p_125986_)) {
            Files.createDirectories(p_125986_.getParent());
            BufferedWriter bufferedwriter = Files.newBufferedWriter(p_125986_);

            try {
               bufferedwriter.write(s);
            } catch (Throwable throwable1) {
               if (bufferedwriter != null) {
                  try {
                     bufferedwriter.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (bufferedwriter != null) {
               bufferedwriter.close();
            }
         }

         p_125984_.m_123940_(p_125986_, s1);
      } catch (IOException ioexception) {
         f_125968_.error("Couldn't save recipe {}", p_125986_, ioexception);
      }

   }

   protected void m_126013_(HashCache p_126014_, JsonObject p_126015_, Path p_126016_) {
      try {
         String s = f_125969_.toJson((JsonElement)p_126015_);
         String s1 = f_123918_.hashUnencodedChars(s).toString();
         if (!Objects.equals(p_126014_.m_123938_(p_126016_), s1) || !Files.exists(p_126016_)) {
            Files.createDirectories(p_126016_.getParent());
            BufferedWriter bufferedwriter = Files.newBufferedWriter(p_126016_);

            try {
               bufferedwriter.write(s);
            } catch (Throwable throwable1) {
               if (bufferedwriter != null) {
                  try {
                     bufferedwriter.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (bufferedwriter != null) {
               bufferedwriter.close();
            }
         }

         p_126014_.m_123940_(p_126016_, s1);
      } catch (IOException ioexception) {
         f_125968_.error("Couldn't save recipe advancement {}", p_126016_, ioexception);
      }

   }

   protected void m_176531_(Consumer<FinishedRecipe> p_176532_) {
      BlockFamilies.m_175934_().filter(BlockFamily::m_175956_).forEach((p_176624_) -> {
         m_176580_(p_176532_, p_176624_);
      });
      m_125998_(p_176532_, Blocks.f_50744_, ItemTags.f_13186_);
      m_126017_(p_176532_, Blocks.f_50742_, ItemTags.f_13185_);
      m_126017_(p_176532_, Blocks.f_50655_, ItemTags.f_13189_);
      m_125998_(p_176532_, Blocks.f_50745_, ItemTags.f_13183_);
      m_126017_(p_176532_, Blocks.f_50743_, ItemTags.f_13187_);
      m_126017_(p_176532_, Blocks.f_50705_, ItemTags.f_13184_);
      m_126017_(p_176532_, Blocks.f_50741_, ItemTags.f_13188_);
      m_126017_(p_176532_, Blocks.f_50656_, ItemTags.f_13190_);
      m_126002_(p_176532_, Blocks.f_50015_, Blocks.f_50003_);
      m_126002_(p_176532_, Blocks.f_50013_, Blocks.f_50001_);
      m_126002_(p_176532_, Blocks.f_50043_, Blocks.f_50004_);
      m_126002_(p_176532_, Blocks.f_50014_, Blocks.f_50002_);
      m_126002_(p_176532_, Blocks.f_50011_, Blocks.f_49999_);
      m_126002_(p_176532_, Blocks.f_50012_, Blocks.f_50000_);
      m_126002_(p_176532_, Blocks.f_50697_, Blocks.f_50695_);
      m_126002_(p_176532_, Blocks.f_50688_, Blocks.f_50686_);
      m_126002_(p_176532_, Blocks.f_50048_, Blocks.f_50008_);
      m_126002_(p_176532_, Blocks.f_50046_, Blocks.f_50006_);
      m_126002_(p_176532_, Blocks.f_50049_, Blocks.f_50009_);
      m_126002_(p_176532_, Blocks.f_50047_, Blocks.f_50007_);
      m_126002_(p_176532_, Blocks.f_50044_, Blocks.f_50010_);
      m_126002_(p_176532_, Blocks.f_50045_, Blocks.f_50005_);
      m_126002_(p_176532_, Blocks.f_50698_, Blocks.f_50696_);
      m_126002_(p_176532_, Blocks.f_50689_, Blocks.f_50687_);
      m_126021_(p_176532_, Items.f_42745_, Blocks.f_50744_);
      m_126021_(p_176532_, Items.f_42743_, Blocks.f_50742_);
      m_126021_(p_176532_, Items.f_42746_, Blocks.f_50745_);
      m_126021_(p_176532_, Items.f_42744_, Blocks.f_50743_);
      m_126021_(p_176532_, Items.f_42453_, Blocks.f_50705_);
      m_126021_(p_176532_, Items.f_42742_, Blocks.f_50741_);
      m_126061_(p_176532_, Blocks.f_50109_, Items.f_42498_);
      m_176716_(p_176532_, Blocks.f_50351_, Blocks.f_50109_);
      m_126069_(p_176532_, Blocks.f_50351_, Items.f_42498_);
      m_126073_(p_176532_, Items.f_42571_, Blocks.f_50109_);
      m_126077_(p_176532_, Items.f_42571_, Items.f_42498_);
      m_126081_(p_176532_, Items.f_42728_, Blocks.f_50109_);
      m_126061_(p_176532_, Blocks.f_50105_, Items.f_42494_);
      m_176716_(p_176532_, Blocks.f_50347_, Blocks.f_50105_);
      m_126069_(p_176532_, Blocks.f_50347_, Items.f_42494_);
      m_126073_(p_176532_, Items.f_42514_, Blocks.f_50105_);
      m_126077_(p_176532_, Items.f_42514_, Items.f_42494_);
      m_126081_(p_176532_, Items.f_42671_, Blocks.f_50105_);
      m_126061_(p_176532_, Blocks.f_50106_, Items.f_42495_);
      m_176716_(p_176532_, Blocks.f_50348_, Blocks.f_50106_);
      m_126069_(p_176532_, Blocks.f_50348_, Items.f_42495_);
      m_126073_(p_176532_, Items.f_42568_, Blocks.f_50106_);
      m_126077_(p_176532_, Items.f_42568_, Items.f_42495_);
      m_126081_(p_176532_, Items.f_42672_, Blocks.f_50106_);
      m_126061_(p_176532_, Blocks.f_50103_, Items.f_42492_);
      m_176716_(p_176532_, Blocks.f_50345_, Blocks.f_50103_);
      m_126069_(p_176532_, Blocks.f_50345_, Items.f_42492_);
      m_126073_(p_176532_, Items.f_42512_, Blocks.f_50103_);
      m_126077_(p_176532_, Items.f_42512_, Items.f_42492_);
      m_126081_(p_176532_, Items.f_42669_, Blocks.f_50103_);
      m_126061_(p_176532_, Blocks.f_50101_, Items.f_42490_);
      m_176716_(p_176532_, Blocks.f_50343_, Blocks.f_50101_);
      m_126069_(p_176532_, Blocks.f_50343_, Items.f_42490_);
      m_126073_(p_176532_, Items.f_42510_, Blocks.f_50101_);
      m_126077_(p_176532_, Items.f_42510_, Items.f_42490_);
      m_126081_(p_176532_, Items.f_42667_, Blocks.f_50101_);
      m_126061_(p_176532_, Blocks.f_50107_, Items.f_42496_);
      m_176716_(p_176532_, Blocks.f_50349_, Blocks.f_50107_);
      m_126069_(p_176532_, Blocks.f_50349_, Items.f_42496_);
      m_126073_(p_176532_, Items.f_42569_, Blocks.f_50107_);
      m_126077_(p_176532_, Items.f_42569_, Items.f_42496_);
      m_126081_(p_176532_, Items.f_42673_, Blocks.f_50107_);
      m_126061_(p_176532_, Blocks.f_50097_, Items.f_42538_);
      m_176716_(p_176532_, Blocks.f_50339_, Blocks.f_50097_);
      m_126069_(p_176532_, Blocks.f_50339_, Items.f_42538_);
      m_126073_(p_176532_, Items.f_42506_, Blocks.f_50097_);
      m_126077_(p_176532_, Items.f_42506_, Items.f_42538_);
      m_126081_(p_176532_, Items.f_42663_, Blocks.f_50097_);
      m_126061_(p_176532_, Blocks.f_50102_, Items.f_42491_);
      m_176716_(p_176532_, Blocks.f_50344_, Blocks.f_50102_);
      m_126069_(p_176532_, Blocks.f_50344_, Items.f_42491_);
      m_126073_(p_176532_, Items.f_42511_, Blocks.f_50102_);
      m_126077_(p_176532_, Items.f_42511_, Items.f_42491_);
      m_126081_(p_176532_, Items.f_42668_, Blocks.f_50102_);
      m_126061_(p_176532_, Blocks.f_50099_, Items.f_42540_);
      m_176716_(p_176532_, Blocks.f_50341_, Blocks.f_50099_);
      m_126069_(p_176532_, Blocks.f_50341_, Items.f_42540_);
      m_126073_(p_176532_, Items.f_42508_, Blocks.f_50099_);
      m_126077_(p_176532_, Items.f_42508_, Items.f_42540_);
      m_126081_(p_176532_, Items.f_42665_, Blocks.f_50099_);
      m_126061_(p_176532_, Blocks.f_50096_, Items.f_42537_);
      m_176716_(p_176532_, Blocks.f_50338_, Blocks.f_50096_);
      m_126069_(p_176532_, Blocks.f_50338_, Items.f_42537_);
      m_126073_(p_176532_, Items.f_42505_, Blocks.f_50096_);
      m_126077_(p_176532_, Items.f_42505_, Items.f_42537_);
      m_126081_(p_176532_, Items.f_42662_, Blocks.f_50096_);
      m_126061_(p_176532_, Blocks.f_50042_, Items.f_42536_);
      m_176716_(p_176532_, Blocks.f_50337_, Blocks.f_50042_);
      m_126069_(p_176532_, Blocks.f_50337_, Items.f_42536_);
      m_126073_(p_176532_, Items.f_42504_, Blocks.f_50042_);
      m_126077_(p_176532_, Items.f_42504_, Items.f_42536_);
      m_126081_(p_176532_, Items.f_42661_, Blocks.f_50042_);
      m_126061_(p_176532_, Blocks.f_50100_, Items.f_42489_);
      m_176716_(p_176532_, Blocks.f_50342_, Blocks.f_50100_);
      m_126069_(p_176532_, Blocks.f_50342_, Items.f_42489_);
      m_126073_(p_176532_, Items.f_42509_, Blocks.f_50100_);
      m_126077_(p_176532_, Items.f_42509_, Items.f_42489_);
      m_126081_(p_176532_, Items.f_42666_, Blocks.f_50100_);
      m_126061_(p_176532_, Blocks.f_50104_, Items.f_42493_);
      m_176716_(p_176532_, Blocks.f_50346_, Blocks.f_50104_);
      m_126069_(p_176532_, Blocks.f_50346_, Items.f_42493_);
      m_126073_(p_176532_, Items.f_42513_, Blocks.f_50104_);
      m_126077_(p_176532_, Items.f_42513_, Items.f_42493_);
      m_126081_(p_176532_, Items.f_42670_, Blocks.f_50104_);
      m_126061_(p_176532_, Blocks.f_50108_, Items.f_42497_);
      m_176716_(p_176532_, Blocks.f_50350_, Blocks.f_50108_);
      m_126069_(p_176532_, Blocks.f_50350_, Items.f_42497_);
      m_126073_(p_176532_, Items.f_42570_, Blocks.f_50108_);
      m_126077_(p_176532_, Items.f_42570_, Items.f_42497_);
      m_126081_(p_176532_, Items.f_42727_, Blocks.f_50108_);
      m_176716_(p_176532_, Blocks.f_50336_, Blocks.f_50041_);
      m_126073_(p_176532_, Items.f_42503_, Blocks.f_50041_);
      m_126081_(p_176532_, Items.f_42660_, Blocks.f_50041_);
      m_126061_(p_176532_, Blocks.f_50098_, Items.f_42539_);
      m_176716_(p_176532_, Blocks.f_50340_, Blocks.f_50098_);
      m_126069_(p_176532_, Blocks.f_50340_, Items.f_42539_);
      m_126073_(p_176532_, Items.f_42507_, Blocks.f_50098_);
      m_126077_(p_176532_, Items.f_42507_, Items.f_42539_);
      m_126081_(p_176532_, Items.f_42664_, Blocks.f_50098_);
      m_176716_(p_176532_, Blocks.f_152543_, Blocks.f_152544_);
      m_126085_(p_176532_, Blocks.f_50215_, Items.f_42498_);
      m_126089_(p_176532_, Blocks.f_50371_, Blocks.f_50215_);
      m_126093_(p_176532_, Blocks.f_50371_, Items.f_42498_);
      m_126085_(p_176532_, Blocks.f_50211_, Items.f_42494_);
      m_126089_(p_176532_, Blocks.f_50367_, Blocks.f_50211_);
      m_126093_(p_176532_, Blocks.f_50367_, Items.f_42494_);
      m_126085_(p_176532_, Blocks.f_50212_, Items.f_42495_);
      m_126089_(p_176532_, Blocks.f_50368_, Blocks.f_50212_);
      m_126093_(p_176532_, Blocks.f_50368_, Items.f_42495_);
      m_126085_(p_176532_, Blocks.f_50209_, Items.f_42492_);
      m_126089_(p_176532_, Blocks.f_50365_, Blocks.f_50209_);
      m_126093_(p_176532_, Blocks.f_50365_, Items.f_42492_);
      m_126085_(p_176532_, Blocks.f_50207_, Items.f_42490_);
      m_126089_(p_176532_, Blocks.f_50363_, Blocks.f_50207_);
      m_126093_(p_176532_, Blocks.f_50363_, Items.f_42490_);
      m_126085_(p_176532_, Blocks.f_50213_, Items.f_42496_);
      m_126089_(p_176532_, Blocks.f_50369_, Blocks.f_50213_);
      m_126093_(p_176532_, Blocks.f_50369_, Items.f_42496_);
      m_126085_(p_176532_, Blocks.f_50203_, Items.f_42538_);
      m_126089_(p_176532_, Blocks.f_50306_, Blocks.f_50203_);
      m_126093_(p_176532_, Blocks.f_50306_, Items.f_42538_);
      m_126085_(p_176532_, Blocks.f_50208_, Items.f_42491_);
      m_126089_(p_176532_, Blocks.f_50364_, Blocks.f_50208_);
      m_126093_(p_176532_, Blocks.f_50364_, Items.f_42491_);
      m_126085_(p_176532_, Blocks.f_50205_, Items.f_42540_);
      m_126089_(p_176532_, Blocks.f_50361_, Blocks.f_50205_);
      m_126093_(p_176532_, Blocks.f_50361_, Items.f_42540_);
      m_126085_(p_176532_, Blocks.f_50202_, Items.f_42537_);
      m_126089_(p_176532_, Blocks.f_50305_, Blocks.f_50202_);
      m_126093_(p_176532_, Blocks.f_50305_, Items.f_42537_);
      m_126085_(p_176532_, Blocks.f_50148_, Items.f_42536_);
      m_126089_(p_176532_, Blocks.f_50304_, Blocks.f_50148_);
      m_126093_(p_176532_, Blocks.f_50304_, Items.f_42536_);
      m_126085_(p_176532_, Blocks.f_50206_, Items.f_42489_);
      m_126089_(p_176532_, Blocks.f_50362_, Blocks.f_50206_);
      m_126093_(p_176532_, Blocks.f_50362_, Items.f_42489_);
      m_126085_(p_176532_, Blocks.f_50210_, Items.f_42493_);
      m_126089_(p_176532_, Blocks.f_50366_, Blocks.f_50210_);
      m_126093_(p_176532_, Blocks.f_50366_, Items.f_42493_);
      m_126085_(p_176532_, Blocks.f_50214_, Items.f_42497_);
      m_126089_(p_176532_, Blocks.f_50370_, Blocks.f_50214_);
      m_126093_(p_176532_, Blocks.f_50370_, Items.f_42497_);
      m_126085_(p_176532_, Blocks.f_50147_, Items.f_42535_);
      m_126089_(p_176532_, Blocks.f_50303_, Blocks.f_50147_);
      m_126093_(p_176532_, Blocks.f_50303_, Items.f_42535_);
      m_126085_(p_176532_, Blocks.f_50204_, Items.f_42539_);
      m_126089_(p_176532_, Blocks.f_50307_, Blocks.f_50204_);
      m_126093_(p_176532_, Blocks.f_50307_, Items.f_42539_);
      m_126097_(p_176532_, Blocks.f_50302_, Items.f_42498_);
      m_126097_(p_176532_, Blocks.f_50298_, Items.f_42494_);
      m_126097_(p_176532_, Blocks.f_50299_, Items.f_42495_);
      m_126097_(p_176532_, Blocks.f_50296_, Items.f_42492_);
      m_126097_(p_176532_, Blocks.f_50294_, Items.f_42490_);
      m_126097_(p_176532_, Blocks.f_50300_, Items.f_42496_);
      m_126097_(p_176532_, Blocks.f_50290_, Items.f_42538_);
      m_126097_(p_176532_, Blocks.f_50295_, Items.f_42491_);
      m_126097_(p_176532_, Blocks.f_50292_, Items.f_42540_);
      m_126097_(p_176532_, Blocks.f_50289_, Items.f_42537_);
      m_126097_(p_176532_, Blocks.f_50288_, Items.f_42536_);
      m_126097_(p_176532_, Blocks.f_50293_, Items.f_42489_);
      m_126097_(p_176532_, Blocks.f_50297_, Items.f_42493_);
      m_126097_(p_176532_, Blocks.f_50301_, Items.f_42497_);
      m_126097_(p_176532_, Blocks.f_50287_, Items.f_42535_);
      m_126097_(p_176532_, Blocks.f_50291_, Items.f_42539_);
      m_126101_(p_176532_, Blocks.f_50574_, Items.f_42498_);
      m_126101_(p_176532_, Blocks.f_50517_, Items.f_42494_);
      m_126101_(p_176532_, Blocks.f_50518_, Items.f_42495_);
      m_126101_(p_176532_, Blocks.f_50515_, Items.f_42492_);
      m_126101_(p_176532_, Blocks.f_50513_, Items.f_42490_);
      m_126101_(p_176532_, Blocks.f_50519_, Items.f_42496_);
      m_126101_(p_176532_, Blocks.f_50509_, Items.f_42538_);
      m_126101_(p_176532_, Blocks.f_50514_, Items.f_42491_);
      m_126101_(p_176532_, Blocks.f_50511_, Items.f_42540_);
      m_126101_(p_176532_, Blocks.f_50508_, Items.f_42537_);
      m_126101_(p_176532_, Blocks.f_50507_, Items.f_42536_);
      m_126101_(p_176532_, Blocks.f_50512_, Items.f_42489_);
      m_126101_(p_176532_, Blocks.f_50516_, Items.f_42493_);
      m_126101_(p_176532_, Blocks.f_50573_, Items.f_42497_);
      m_126101_(p_176532_, Blocks.f_50506_, Items.f_42535_);
      m_126101_(p_176532_, Blocks.f_50510_, Items.f_42539_);
      ShapedRecipeBuilder.m_126116_(Items.f_151065_).m_126127_('S', Items.f_42401_).m_126127_('H', Items.f_42784_).m_126130_("S").m_126130_("H").m_142284_("has_string", m_125977_(Items.f_42401_)).m_142284_("has_honeycomb", m_125977_(Items.f_42784_)).m_176498_(p_176532_);
      m_176542_(p_176532_, Blocks.f_152524_, Items.f_42498_);
      m_176542_(p_176532_, Blocks.f_152520_, Items.f_42494_);
      m_176542_(p_176532_, Blocks.f_152521_, Items.f_42495_);
      m_176542_(p_176532_, Blocks.f_152518_, Items.f_42492_);
      m_176542_(p_176532_, Blocks.f_152516_, Items.f_42490_);
      m_176542_(p_176532_, Blocks.f_152522_, Items.f_42496_);
      m_176542_(p_176532_, Blocks.f_152512_, Items.f_42538_);
      m_176542_(p_176532_, Blocks.f_152517_, Items.f_42491_);
      m_176542_(p_176532_, Blocks.f_152514_, Items.f_42540_);
      m_176542_(p_176532_, Blocks.f_152511_, Items.f_42537_);
      m_176542_(p_176532_, Blocks.f_152484_, Items.f_42536_);
      m_176542_(p_176532_, Blocks.f_152515_, Items.f_42489_);
      m_176542_(p_176532_, Blocks.f_152519_, Items.f_42493_);
      m_176542_(p_176532_, Blocks.f_152523_, Items.f_42497_);
      m_176542_(p_176532_, Blocks.f_152483_, Items.f_42535_);
      m_176542_(p_176532_, Blocks.f_152513_, Items.f_42539_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50285_, 6).m_126127_('#', Blocks.f_50174_).m_126127_('S', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("XSX").m_126130_("X#X").m_126130_("XSX").m_142284_("has_rail", m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Blocks.f_50334_, 2).m_126209_(Blocks.f_50228_).m_126209_(Blocks.f_50652_).m_142284_("has_stone", m_125977_(Blocks.f_50228_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50322_).m_126127_('I', Blocks.f_50075_).m_126127_('i', Items.f_42416_).m_126130_("III").m_126130_(" i ").m_126130_("iii").m_142284_("has_iron_block", m_125977_(Blocks.f_50075_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42650_).m_126127_('/', Items.f_42398_).m_126127_('_', Blocks.f_50405_).m_126130_("///").m_126130_(" / ").m_126130_("/_/").m_142284_("has_stone_slab", m_125977_(Blocks.f_50405_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42412_, 4).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42484_).m_126127_('Y', Items.f_42402_).m_126130_("X").m_126130_("#").m_126130_("Y").m_142284_("has_feather", m_125977_(Items.f_42402_)).m_142284_("has_flint", m_125977_(Items.f_42484_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50618_, 1).m_126121_('P', ItemTags.f_13168_).m_126121_('S', ItemTags.f_13175_).m_126130_("PSP").m_126130_("P P").m_126130_("PSP").m_142284_("has_planks", m_125975_(ItemTags.f_13168_)).m_142284_("has_wood_slab", m_125975_(ItemTags.f_13175_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50273_).m_126127_('S', Items.f_42686_).m_126127_('G', Blocks.f_50058_).m_126127_('O', Blocks.f_50080_).m_126130_("GGG").m_126130_("GSG").m_126130_("OOO").m_142284_("has_nether_star", m_125977_(Items.f_42686_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50718_).m_126121_('P', ItemTags.f_13168_).m_126127_('H', Items.f_42784_).m_126130_("PPP").m_126130_("HHH").m_126130_("PPP").m_142284_("has_honeycomb", m_125977_(Items.f_42784_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42734_).m_126209_(Items.f_42399_).m_126211_(Items.f_42732_, 6).m_142284_("has_beetroot", m_125977_(Items.f_42732_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42498_).m_126209_(Items.f_42532_).m_142409_("black_dye").m_142284_("has_ink_sac", m_125977_(Items.f_42532_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42498_, Blocks.f_50070_, "black_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42593_, 2).m_126209_(Items.f_42585_).m_142284_("has_blaze_rod", m_125977_(Items.f_42585_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42494_).m_126209_(Items.f_42534_).m_142409_("blue_dye").m_142284_("has_lapis_lazuli", m_125977_(Items.f_42534_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42494_, Blocks.f_50121_, "blue_dye");
      ShapedRecipeBuilder.m_126116_(Blocks.f_50568_).m_126127_('#', Blocks.f_50354_).m_126130_("###").m_126130_("###").m_126130_("###").m_142284_("has_packed_ice", m_125977_(Blocks.f_50354_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42499_, 3).m_126209_(Items.f_42500_).m_142409_("bonemeal").m_142284_("has_bone", m_125977_(Items.f_42500_)).m_176498_(p_176532_);
      m_176616_(p_176532_, Items.f_42499_, Items.f_42262_, "bone_meal_from_bone_block", "bonemeal");
      ShapelessRecipeBuilder.m_126189_(Items.f_42517_).m_126211_(Items.f_42516_, 3).m_126209_(Items.f_42454_).m_142284_("has_paper", m_125977_(Items.f_42516_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50078_).m_126121_('#', ItemTags.f_13168_).m_126127_('X', Items.f_42517_).m_126130_("###").m_126130_("XXX").m_126130_("###").m_142284_("has_book", m_125977_(Items.f_42517_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42411_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42401_).m_126130_(" #X").m_126130_("# X").m_126130_(" #X").m_142284_("has_string", m_125977_(Items.f_42401_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42399_, 4).m_126121_('#', ItemTags.f_13168_).m_126130_("# #").m_126130_(" # ").m_142284_("has_brown_mushroom", m_125977_(Blocks.f_50072_)).m_142284_("has_red_mushroom", m_125977_(Blocks.f_50073_)).m_142284_("has_mushroom_stew", m_125977_(Items.f_42400_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42406_).m_126127_('#', Items.f_42405_).m_126130_("###").m_142284_("has_wheat", m_125977_(Items.f_42405_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50255_).m_126127_('B', Items.f_42585_).m_126121_('#', ItemTags.f_13166_).m_126130_(" B ").m_126130_("###").m_142284_("has_blaze_rod", m_125977_(Items.f_42585_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50076_).m_126127_('#', Items.f_42460_).m_126130_("##").m_126130_("##").m_142284_("has_brick", m_125977_(Items.f_42460_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42495_).m_126209_(Items.f_42533_).m_142409_("brown_dye").m_142284_("has_cocoa_beans", m_125977_(Items.f_42533_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42446_).m_126127_('#', Items.f_42416_).m_126130_("# #").m_126130_(" # ").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50145_).m_126127_('A', Items.f_42455_).m_126127_('B', Items.f_42501_).m_126127_('C', Items.f_42405_).m_126127_('E', Items.f_42521_).m_126130_("AAA").m_126130_("BEB").m_126130_("CCC").m_142284_("has_egg", m_125977_(Items.f_42521_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50683_).m_126121_('L', ItemTags.f_13182_).m_126127_('S', Items.f_42398_).m_126121_('C', ItemTags.f_13160_).m_126130_(" S ").m_126130_("SCS").m_126130_("LLL").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_142284_("has_coal", m_125975_(ItemTags.f_13160_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42684_).m_126127_('#', Items.f_42523_).m_126127_('X', Items.f_42619_).m_126130_("# ").m_126130_(" X").m_142284_("has_carrot", m_125977_(Items.f_42619_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42685_).m_126127_('#', Items.f_42523_).m_126127_('X', Items.f_41955_).m_126130_("# ").m_126130_(" X").m_142284_("has_warped_fungus", m_125977_(Items.f_41955_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50256_).m_126127_('#', Items.f_42416_).m_126130_("# #").m_126130_("# #").m_126130_("###").m_142284_("has_water_bucket", m_125977_(Items.f_42447_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50715_).m_126121_('#', ItemTags.f_13175_).m_126130_("# #").m_126130_("# #").m_126130_("###").m_142284_("has_wood_slab", m_125975_(ItemTags.f_13175_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50087_).m_126121_('#', ItemTags.f_13168_).m_126130_("###").m_126130_("# #").m_126130_("###").m_142284_("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.m_55386_(10), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, new ItemPredicate[0])).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42519_).m_126127_('A', Blocks.f_50087_).m_126127_('B', Items.f_42449_).m_126130_("A").m_126130_("B").m_142284_("has_minecart", m_125977_(Items.f_42449_)).m_176498_(p_176532_);
      m_176646_(Blocks.f_50282_, Ingredient.m_43929_(Blocks.f_50413_)).m_142284_("has_chiseled_quartz_block", m_125977_(Blocks.f_50282_)).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_142284_("has_quartz_pillar", m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
      m_176646_(Blocks.f_50225_, Ingredient.m_43929_(Blocks.f_50411_)).m_142284_("has_tag", m_125975_(ItemTags.f_13169_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50129_).m_126127_('#', Items.f_42461_).m_126130_("##").m_126130_("##").m_142284_("has_clay_ball", m_125977_(Items.f_42461_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42524_).m_126127_('#', Items.f_42417_).m_126127_('X', Items.f_42451_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42413_, Items.f_42200_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50546_, 4).m_126127_('D', Blocks.f_50493_).m_126127_('G', Blocks.f_49994_).m_126130_("DG").m_126130_("GD").m_142284_("has_gravel", m_125977_(Blocks.f_49994_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50328_).m_126127_('#', Blocks.f_50174_).m_126127_('X', Items.f_42692_).m_126127_('I', Blocks.f_50069_).m_126130_(" # ").m_126130_("#X#").m_126130_("III").m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42522_).m_126127_('#', Items.f_42416_).m_126127_('X', Items.f_42451_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42572_, 8).m_126127_('#', Items.f_42405_).m_126127_('X', Items.f_42533_).m_126130_("#X#").m_142284_("has_cocoa", m_125977_(Items.f_42533_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50091_).m_126121_('#', ItemTags.f_13168_).m_126130_("##").m_126130_("##").m_142284_("has_planks", m_125975_(ItemTags.f_13168_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42717_).m_126127_('~', Items.f_42401_).m_126127_('#', Items.f_42398_).m_126127_('&', Items.f_42416_).m_126127_('$', Blocks.f_50266_).m_126130_("#&#").m_126130_("~$~").m_126130_(" # ").m_142284_("has_string", m_125977_(Items.f_42401_)).m_142284_("has_stick", m_125977_(Items.f_42398_)).m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_142284_("has_tripwire_hook", m_125977_(Blocks.f_50266_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50617_).m_126121_('#', ItemTags.f_13168_).m_126127_('@', Items.f_42401_).m_126130_("@@").m_126130_("##").m_142284_("has_string", m_125977_(Items.f_42401_)).m_176498_(p_176532_);
      m_176646_(Blocks.f_50395_, Ingredient.m_43929_(Blocks.f_50467_)).m_142284_("has_red_sandstone", m_125977_(Blocks.f_50394_)).m_142284_("has_chiseled_red_sandstone", m_125977_(Blocks.f_50395_)).m_142284_("has_cut_red_sandstone", m_125977_(Blocks.f_50396_)).m_176498_(p_176532_);
      m_176664_(p_176532_, Blocks.f_50063_, Blocks.f_50406_);
      m_176616_(p_176532_, Items.f_151052_, Items.f_151000_, m_176644_(Items.f_151052_), m_176632_(Items.f_151052_));
      ShapelessRecipeBuilder.m_126191_(Items.f_151052_, 9).m_126209_(Blocks.f_152571_).m_142409_(m_176632_(Items.f_151052_)).m_142284_(m_176602_(Blocks.f_152571_), m_125977_(Blocks.f_152571_)).m_176500_(p_176532_, m_176517_(Items.f_151052_, Blocks.f_152571_));
      m_176610_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42492_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42496_).m_142284_("has_green_dye", m_125977_(Items.f_42496_)).m_142284_("has_blue_dye", m_125977_(Items.f_42494_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50379_).m_126127_('S', Items.f_42695_).m_126127_('I', Items.f_42498_).m_126130_("SSS").m_126130_("SIS").m_126130_("SSS").m_142284_("has_prismarine_shard", m_125977_(Items.f_42695_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50329_).m_126127_('Q', Items.f_42692_).m_126127_('G', Blocks.f_50058_).m_126124_('W', Ingredient.m_43911_(ItemTags.f_13175_)).m_126130_("GGG").m_126130_("QQQ").m_126130_("WWW").m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_152589_, 4).m_126127_('S', Blocks.f_152555_).m_126130_("SS").m_126130_("SS").m_142284_("has_polished_deepslate", m_125977_(Blocks.f_152555_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_152559_, 4).m_126127_('S', Blocks.f_152589_).m_126130_("SS").m_126130_("SS").m_142284_("has_deepslate_bricks", m_125977_(Blocks.f_152589_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50031_, 6).m_126127_('R', Items.f_42451_).m_126127_('#', Blocks.f_50165_).m_126127_('X', Items.f_42416_).m_126130_("X X").m_126130_("X#X").m_126130_("XRX").m_142284_("has_rail", m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42391_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42415_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42415_, Items.f_41959_);
      ShapedRecipeBuilder.m_126116_(Items.f_42475_).m_126127_('X', Items.f_42415_).m_126130_("X X").m_126130_("X X").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42473_).m_126127_('X', Items.f_42415_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42472_).m_126127_('X', Items.f_42415_).m_126130_("XXX").m_126130_("X X").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42392_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42415_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42474_).m_126127_('X', Items.f_42415_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42390_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42415_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42389_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42415_).m_126130_("X").m_126130_("#").m_126130_("#").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42388_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42415_).m_126130_("X").m_126130_("X").m_126130_("#").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50228_, 2).m_126127_('Q', Items.f_42692_).m_126127_('C', Blocks.f_50652_).m_126130_("CQ").m_126130_("QC").m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50061_).m_126127_('R', Items.f_42451_).m_126127_('#', Blocks.f_50652_).m_126127_('X', Items.f_42411_).m_126130_("###").m_126130_("#X#").m_126130_("#R#").m_142284_("has_bow", m_125977_(Items.f_42411_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_152537_).m_126127_('#', Items.f_151087_).m_126130_("##").m_126130_("##").m_142409_("pointed_dripstone").m_142284_("has_pointed_dripstone", m_125977_(Items.f_151087_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50286_).m_126127_('R', Items.f_42451_).m_126127_('#', Blocks.f_50652_).m_126130_("###").m_126130_("# #").m_126130_("#R#").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42616_, Items.f_42110_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50201_).m_126127_('B', Items.f_42517_).m_126127_('#', Blocks.f_50080_).m_126127_('D', Items.f_42415_).m_126130_(" B ").m_126130_("D#D").m_126130_("###").m_142284_("has_obsidian", m_125977_(Blocks.f_50080_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50265_).m_126127_('#', Blocks.f_50080_).m_126127_('E', Items.f_42545_).m_126130_("###").m_126130_("#E#").m_126130_("###").m_142284_("has_ender_eye", m_125977_(Items.f_42545_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42545_).m_126209_(Items.f_42584_).m_126209_(Items.f_42593_).m_142284_("has_blaze_powder", m_125977_(Items.f_42593_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50443_, 4).m_126127_('#', Blocks.f_50259_).m_126130_("##").m_126130_("##").m_142284_("has_end_stone", m_125977_(Blocks.f_50259_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42729_).m_126127_('T', Items.f_42586_).m_126127_('E', Items.f_42545_).m_126127_('G', Blocks.f_50058_).m_126130_("GGG").m_126130_("GEG").m_126130_("GTG").m_142284_("has_ender_eye", m_125977_(Items.f_42545_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50489_, 4).m_126127_('#', Items.f_42731_).m_126127_('/', Items.f_42585_).m_126130_("/").m_126130_("#").m_142284_("has_chorus_fruit_popped", m_125977_(Items.f_42731_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42592_).m_126209_(Items.f_42591_).m_126209_(Blocks.f_50072_).m_126209_(Items.f_42501_).m_142284_("has_spider_eye", m_125977_(Items.f_42591_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42613_, 3).m_126209_(Items.f_42403_).m_126209_(Items.f_42593_).m_126184_(Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_142284_("has_blaze_powder", m_125977_(Items.f_42593_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42688_, 3).m_126209_(Items.f_42403_).m_126209_(Items.f_42516_).m_142284_("has_gunpowder", m_125977_(Items.f_42403_)).m_176500_(p_176532_, "firework_rocket_simple");
      ShapedRecipeBuilder.m_126116_(Items.f_42523_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42401_).m_126130_("  #").m_126130_(" #X").m_126130_("# X").m_142284_("has_string", m_125977_(Items.f_42401_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42409_).m_126209_(Items.f_42416_).m_126209_(Items.f_42484_).m_142284_("has_flint", m_125977_(Items.f_42484_)).m_142284_("has_obsidian", m_125977_(Blocks.f_50080_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50276_).m_126127_('#', Items.f_42460_).m_126130_("# #").m_126130_(" # ").m_142284_("has_brick", m_125977_(Items.f_42460_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50094_).m_126121_('#', ItemTags.f_13166_).m_126130_("###").m_126130_("# #").m_126130_("###").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13166_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42520_).m_126127_('A', Blocks.f_50094_).m_126127_('B', Items.f_42449_).m_126130_("A").m_126130_("B").m_142284_("has_minecart", m_125977_(Items.f_42449_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42590_, 3).m_126127_('#', Blocks.f_50058_).m_126130_("# #").m_126130_(" # ").m_142284_("has_glass", m_125977_(Blocks.f_50058_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50185_, 16).m_126127_('#', Blocks.f_50058_).m_126130_("###").m_126130_("###").m_142284_("has_glass", m_125977_(Blocks.f_50058_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50141_).m_126127_('#', Items.f_42525_).m_126130_("##").m_126130_("##").m_142284_("has_glowstone_dust", m_125977_(Items.f_42525_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_151063_).m_126209_(Items.f_42617_).m_126209_(Items.f_151056_).m_142284_("has_item_frame", m_125977_(Items.f_42617_)).m_142284_("has_glow_ink_sac", m_125977_(Items.f_151056_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42436_).m_126127_('#', Items.f_42417_).m_126127_('X', Items.f_42410_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42433_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42479_).m_126127_('X', Items.f_42417_).m_126130_("X X").m_126130_("X X").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42677_).m_126127_('#', Items.f_42587_).m_126127_('X', Items.f_42619_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_gold_nugget", m_125977_(Items.f_42587_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42477_).m_126127_('X', Items.f_42417_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42476_).m_126127_('X', Items.f_42417_).m_126130_("XXX").m_126130_("X X").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42434_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42478_).m_126127_('X', Items.f_42417_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42432_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50030_, 6).m_126127_('R', Items.f_42451_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("X X").m_126130_("X#X").m_126130_("XRX").m_142284_("has_rail", m_125977_(Blocks.f_50156_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42431_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("X").m_126130_("#").m_126130_("#").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42430_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42417_).m_126130_("X").m_126130_("X").m_126130_("#").m_142284_("has_gold_ingot", m_125977_(Items.f_42417_)).m_176498_(p_176532_);
      m_176616_(p_176532_, Items.f_42417_, Items.f_41912_, "gold_ingot_from_gold_block", "gold_ingot");
      m_176562_(p_176532_, Items.f_42587_, Items.f_42417_, "gold_ingot_from_nuggets", "gold_ingot");
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50122_).m_126209_(Blocks.f_50228_).m_126209_(Items.f_42692_).m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42490_, 2).m_126209_(Items.f_42498_).m_126209_(Items.f_42535_).m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_142284_("has_black_dye", m_125977_(Items.f_42498_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50335_).m_126127_('#', Items.f_42405_).m_126130_("###").m_126130_("###").m_126130_("###").m_142284_("has_wheat", m_125977_(Items.f_42405_)).m_176498_(p_176532_);
      m_176690_(p_176532_, Blocks.f_50327_, Items.f_42416_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42787_, 4).m_126209_(Items.f_42788_).m_126211_(Items.f_42590_, 4).m_142284_("has_honey_block", m_125977_(Blocks.f_50719_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50719_, 1).m_126127_('S', Items.f_42787_).m_126130_("SS").m_126130_("SS").m_142284_("has_honey_bottle", m_125977_(Items.f_42787_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50720_).m_126127_('H', Items.f_42784_).m_126130_("HH").m_126130_("HH").m_142284_("has_honeycomb", m_125977_(Items.f_42784_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50332_).m_126127_('C', Blocks.f_50087_).m_126127_('I', Items.f_42416_).m_126130_("I I").m_126130_("ICI").m_126130_(" I ").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42694_).m_126127_('A', Blocks.f_50332_).m_126127_('B', Items.f_42449_).m_126130_("A").m_126130_("B").m_142284_("has_minecart", m_125977_(Items.f_42449_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42386_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50183_, 16).m_126127_('#', Items.f_42416_).m_126130_("###").m_126130_("###").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42471_).m_126127_('X', Items.f_42416_).m_126130_("X X").m_126130_("X X").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42469_).m_126127_('X', Items.f_42416_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      m_176670_(Blocks.f_50166_, Ingredient.m_43929_(Items.f_42416_)).m_142284_(m_176602_(Items.f_42416_), m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42468_).m_126127_('X', Items.f_42416_).m_126130_("XXX").m_126130_("X X").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42387_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      m_176616_(p_176532_, Items.f_42416_, Items.f_41913_, "iron_ingot_from_iron_block", "iron_ingot");
      m_176562_(p_176532_, Items.f_42749_, Items.f_42416_, "iron_ingot_from_nuggets", "iron_ingot");
      ShapedRecipeBuilder.m_126116_(Items.f_42470_).m_126127_('X', Items.f_42416_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42385_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42384_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("X").m_126130_("#").m_126130_("#").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42383_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("X").m_126130_("X").m_126130_("#").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50376_).m_126127_('#', Items.f_42416_).m_126130_("##").m_126130_("##").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42617_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42454_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50131_).m_126121_('#', ItemTags.f_13168_).m_126127_('X', Items.f_42415_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_diamond", m_125977_(Items.f_42415_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50155_, 3).m_126127_('#', Items.f_42398_).m_126130_("# #").m_126130_("###").m_126130_("# #").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42534_, Items.f_41854_);
      ShapedRecipeBuilder.m_126118_(Items.f_42655_, 2).m_126127_('~', Items.f_42401_).m_126127_('O', Items.f_42518_).m_126130_("~~ ").m_126130_("~O ").m_126130_("  ~").m_142284_("has_slime_ball", m_125977_(Items.f_42518_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42454_).m_126127_('#', Items.f_42649_).m_126130_("##").m_126130_("##").m_142284_("has_rabbit_hide", m_125977_(Items.f_42649_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42463_).m_126127_('X', Items.f_42454_).m_126130_("X X").m_126130_("X X").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42408_).m_126127_('X', Items.f_42454_).m_126130_("X X").m_126130_("XXX").m_126130_("XXX").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42407_).m_126127_('X', Items.f_42454_).m_126130_("XXX").m_126130_("X X").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42462_).m_126127_('X', Items.f_42454_).m_126130_("XXX").m_126130_("X X").m_126130_("X X").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42654_).m_126127_('X', Items.f_42454_).m_126130_("X X").m_126130_("XXX").m_126130_("X X").m_142284_("has_leather", m_125977_(Items.f_42454_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50624_).m_126121_('S', ItemTags.f_13175_).m_126127_('B', Blocks.f_50078_).m_126130_("SSS").m_126130_(" B ").m_126130_(" S ").m_142284_("has_book", m_125977_(Items.f_42517_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50164_).m_126127_('#', Blocks.f_50652_).m_126127_('X', Items.f_42398_).m_126130_("X").m_126130_("#").m_142284_("has_cobblestone", m_125977_(Blocks.f_50652_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42538_, Blocks.f_50113_, "light_blue_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42538_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42535_).m_142409_("light_blue_dye").m_142284_("has_blue_dye", m_125977_(Items.f_42494_)).m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_176500_(p_176532_, "light_blue_dye_from_blue_white_dye");
      m_176551_(p_176532_, Items.f_42491_, Blocks.f_50115_, "light_gray_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42491_, 2).m_126209_(Items.f_42490_).m_126209_(Items.f_42535_).m_142409_("light_gray_dye").m_142284_("has_gray_dye", m_125977_(Items.f_42490_)).m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_176500_(p_176532_, "light_gray_dye_from_gray_white_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42491_, 3).m_126209_(Items.f_42498_).m_126211_(Items.f_42535_, 2).m_142409_("light_gray_dye").m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_142284_("has_black_dye", m_125977_(Items.f_42498_)).m_176500_(p_176532_, "light_gray_dye_from_black_white_dye");
      m_176551_(p_176532_, Items.f_42491_, Blocks.f_50120_, "light_gray_dye");
      m_176551_(p_176532_, Items.f_42491_, Blocks.f_50118_, "light_gray_dye");
      m_176690_(p_176532_, Blocks.f_50326_, Items.f_42417_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_152587_).m_126127_('#', Items.f_151052_).m_126130_("#").m_126130_("#").m_126130_("#").m_142284_("has_copper_ingot", m_125977_(Items.f_151052_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42540_, 2).m_126209_(Items.f_42496_).m_126209_(Items.f_42535_).m_142284_("has_green_dye", m_125977_(Items.f_42496_)).m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50144_).m_126127_('A', Blocks.f_50143_).m_126127_('B', Blocks.f_50081_).m_126130_("A").m_126130_("B").m_142284_("has_carved_pumpkin", m_125977_(Blocks.f_50143_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42537_, Blocks.f_50114_, "magenta_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 4).m_126209_(Items.f_42494_).m_126211_(Items.f_42497_, 2).m_126209_(Items.f_42535_).m_142409_("magenta_dye").m_142284_("has_blue_dye", m_125977_(Items.f_42494_)).m_142284_("has_rose_red", m_125977_(Items.f_42497_)).m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_176500_(p_176532_, "magenta_dye_from_blue_red_white_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 3).m_126209_(Items.f_42494_).m_126209_(Items.f_42497_).m_126209_(Items.f_42489_).m_142409_("magenta_dye").m_142284_("has_pink_dye", m_125977_(Items.f_42489_)).m_142284_("has_blue_dye", m_125977_(Items.f_42494_)).m_142284_("has_red_dye", m_125977_(Items.f_42497_)).m_176500_(p_176532_, "magenta_dye_from_blue_red_pink");
      m_176556_(p_176532_, Items.f_42537_, Blocks.f_50356_, "magenta_dye", 2);
      ShapelessRecipeBuilder.m_126191_(Items.f_42537_, 2).m_126209_(Items.f_42493_).m_126209_(Items.f_42489_).m_142409_("magenta_dye").m_142284_("has_pink_dye", m_125977_(Items.f_42489_)).m_142284_("has_purple_dye", m_125977_(Items.f_42493_)).m_176500_(p_176532_, "magenta_dye_from_purple_and_pink");
      ShapedRecipeBuilder.m_126116_(Blocks.f_50450_).m_126127_('#', Items.f_42542_).m_126130_("##").m_126130_("##").m_142284_("has_magma_cream", m_125977_(Items.f_42542_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42542_).m_126209_(Items.f_42593_).m_126209_(Items.f_42518_).m_142284_("has_blaze_powder", m_125977_(Items.f_42593_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42676_).m_126127_('#', Items.f_42516_).m_126127_('X', Items.f_42522_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_compass", m_125977_(Items.f_42522_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50186_).m_126127_('M', Items.f_42575_).m_126130_("MMM").m_126130_("MMM").m_126130_("MMM").m_142284_("has_melon", m_125977_(Items.f_42575_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42578_).m_126209_(Items.f_42575_).m_142284_("has_melon", m_125977_(Items.f_42575_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42449_).m_126127_('#', Items.f_42416_).m_126130_("# #").m_126130_("###").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50079_).m_126209_(Blocks.f_50652_).m_126209_(Blocks.f_50191_).m_142409_("mossy_cobblestone").m_142284_("has_vine", m_125977_(Blocks.f_50191_)).m_176500_(p_176532_, m_176517_(Blocks.f_50079_, Blocks.f_50191_));
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50223_).m_126209_(Blocks.f_50222_).m_126209_(Blocks.f_50191_).m_142409_("mossy_stone_bricks").m_142284_("has_vine", m_125977_(Blocks.f_50191_)).m_176500_(p_176532_, m_176517_(Blocks.f_50223_, Blocks.f_50191_));
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50079_).m_126209_(Blocks.f_50652_).m_126209_(Blocks.f_152544_).m_142409_("mossy_cobblestone").m_142284_("has_moss_block", m_125977_(Blocks.f_152544_)).m_176500_(p_176532_, m_176517_(Blocks.f_50079_, Blocks.f_152544_));
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50223_).m_126209_(Blocks.f_50222_).m_126209_(Blocks.f_152544_).m_142409_("mossy_stone_bricks").m_142284_("has_moss_block", m_125977_(Blocks.f_152544_)).m_176500_(p_176532_, m_176517_(Blocks.f_50223_, Blocks.f_152544_));
      ShapelessRecipeBuilder.m_126189_(Items.f_42400_).m_126209_(Blocks.f_50072_).m_126209_(Blocks.f_50073_).m_126209_(Items.f_42399_).m_142284_("has_mushroom_stew", m_125977_(Items.f_42400_)).m_142284_("has_bowl", m_125977_(Items.f_42399_)).m_142284_("has_brown_mushroom", m_125977_(Blocks.f_50072_)).m_142284_("has_red_mushroom", m_125977_(Blocks.f_50073_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50197_).m_126127_('N', Items.f_42691_).m_126130_("NN").m_126130_("NN").m_142284_("has_netherbrick", m_125977_(Items.f_42691_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50451_).m_126127_('#', Items.f_42588_).m_126130_("###").m_126130_("###").m_126130_("###").m_142284_("has_nether_wart", m_125977_(Items.f_42588_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50065_).m_126121_('#', ItemTags.f_13168_).m_126127_('X', Items.f_42451_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50455_).m_126127_('Q', Items.f_42692_).m_126127_('R', Items.f_42451_).m_126127_('#', Blocks.f_50652_).m_126130_("###").m_126130_("RRQ").m_126130_("###").m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42536_, Blocks.f_50117_, "orange_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42536_, 2).m_126209_(Items.f_42497_).m_126209_(Items.f_42539_).m_142409_("orange_dye").m_142284_("has_red_dye", m_125977_(Items.f_42497_)).m_142284_("has_yellow_dye", m_125977_(Items.f_42539_)).m_176500_(p_176532_, "orange_dye_from_red_yellow");
      ShapedRecipeBuilder.m_126116_(Items.f_42487_).m_126127_('#', Items.f_42398_).m_126124_('X', Ingredient.m_43911_(ItemTags.f_13167_)).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_wool", m_125975_(ItemTags.f_13167_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42516_, 3).m_126127_('#', Blocks.f_50130_).m_126130_("###").m_142284_("has_reeds", m_125977_(Blocks.f_50130_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50283_, 2).m_126127_('#', Blocks.f_50333_).m_126130_("#").m_126130_("#").m_142284_("has_chiseled_quartz_block", m_125977_(Blocks.f_50282_)).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_142284_("has_quartz_pillar", m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50354_).m_126211_(Blocks.f_50126_, 9).m_142284_("has_ice", m_125977_(Blocks.f_50126_)).m_176498_(p_176532_);
      m_176556_(p_176532_, Items.f_42489_, Blocks.f_50358_, "pink_dye", 2);
      m_176551_(p_176532_, Items.f_42489_, Blocks.f_50119_, "pink_dye");
      ShapelessRecipeBuilder.m_126191_(Items.f_42489_, 2).m_126209_(Items.f_42497_).m_126209_(Items.f_42535_).m_142409_("pink_dye").m_142284_("has_white_dye", m_125977_(Items.f_42535_)).m_142284_("has_red_dye", m_125977_(Items.f_42497_)).m_176500_(p_176532_, "pink_dye_from_red_white_dye");
      ShapedRecipeBuilder.m_126116_(Blocks.f_50039_).m_126127_('R', Items.f_42451_).m_126127_('#', Blocks.f_50652_).m_126121_('T', ItemTags.f_13168_).m_126127_('X', Items.f_42416_).m_126130_("TTT").m_126130_("#X#").m_126130_("#R#").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      m_176640_(p_176532_, Blocks.f_50138_, Blocks.f_50137_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50377_).m_126127_('S', Items.f_42695_).m_126130_("SS").m_126130_("SS").m_142284_("has_prismarine_shard", m_125977_(Items.f_42695_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50378_).m_126127_('S', Items.f_42695_).m_126130_("SSS").m_126130_("SSS").m_126130_("SSS").m_142284_("has_prismarine_shard", m_125977_(Items.f_42695_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42687_).m_126209_(Blocks.f_50133_).m_126209_(Items.f_42501_).m_126209_(Items.f_42521_).m_142284_("has_carved_pumpkin", m_125977_(Blocks.f_50143_)).m_142284_("has_pumpkin", m_125977_(Blocks.f_50133_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42577_, 4).m_126209_(Blocks.f_50133_).m_142284_("has_pumpkin", m_125977_(Blocks.f_50133_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42493_, 2).m_126209_(Items.f_42494_).m_126209_(Items.f_42497_).m_142284_("has_blue_dye", m_125977_(Items.f_42494_)).m_142284_("has_red_dye", m_125977_(Items.f_42497_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50456_).m_126127_('#', Blocks.f_50087_).m_126127_('-', Items.f_42748_).m_126130_("-").m_126130_("#").m_126130_("-").m_142284_("has_shulker_shell", m_125977_(Items.f_42748_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50492_, 4).m_126127_('F', Items.f_42731_).m_126130_("FF").m_126130_("FF").m_142284_("has_chorus_fruit_popped", m_125977_(Items.f_42731_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50441_).m_126127_('#', Blocks.f_50469_).m_126130_("#").m_126130_("#").m_142284_("has_purpur_block", m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
      m_176704_(Blocks.f_50469_, Ingredient.m_43929_(Blocks.f_50492_, Blocks.f_50441_)).m_142284_("has_purpur_block", m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
      m_176710_(Blocks.f_50442_, Ingredient.m_43929_(Blocks.f_50492_, Blocks.f_50441_)).m_142284_("has_purpur_block", m_125977_(Blocks.f_50492_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50333_).m_126127_('#', Items.f_42692_).m_126130_("##").m_126130_("##").m_142284_("has_quartz", m_125977_(Items.f_42692_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50714_, 4).m_126127_('#', Blocks.f_50333_).m_126130_("##").m_126130_("##").m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_176498_(p_176532_);
      m_176704_(Blocks.f_50413_, Ingredient.m_43929_(Blocks.f_50282_, Blocks.f_50333_, Blocks.f_50283_)).m_142284_("has_chiseled_quartz_block", m_125977_(Blocks.f_50282_)).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_142284_("has_quartz_pillar", m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
      m_176710_(Blocks.f_50284_, Ingredient.m_43929_(Blocks.f_50282_, Blocks.f_50333_, Blocks.f_50283_)).m_142284_("has_chiseled_quartz_block", m_125977_(Blocks.f_50282_)).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_142284_("has_quartz_pillar", m_125977_(Blocks.f_50283_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42699_).m_126209_(Items.f_42674_).m_126209_(Items.f_42698_).m_126209_(Items.f_42399_).m_126209_(Items.f_42619_).m_126209_(Blocks.f_50072_).m_142409_("rabbit_stew").m_142284_("has_cooked_rabbit", m_125977_(Items.f_42698_)).m_176500_(p_176532_, m_176517_(Items.f_42699_, Items.f_41952_));
      ShapelessRecipeBuilder.m_126189_(Items.f_42699_).m_126209_(Items.f_42674_).m_126209_(Items.f_42698_).m_126209_(Items.f_42399_).m_126209_(Items.f_42619_).m_126209_(Blocks.f_50073_).m_142409_("rabbit_stew").m_142284_("has_cooked_rabbit", m_125977_(Items.f_42698_)).m_176500_(p_176532_, m_176517_(Items.f_42699_, Items.f_41953_));
      ShapedRecipeBuilder.m_126118_(Blocks.f_50156_, 16).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42416_).m_126130_("X X").m_126130_("X#X").m_126130_("X X").m_142284_("has_minecart", m_125977_(Items.f_42449_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42451_, Items.f_42153_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50261_).m_126127_('R', Items.f_42451_).m_126127_('G', Blocks.f_50141_).m_126130_(" R ").m_126130_("RGR").m_126130_(" R ").m_142284_("has_glowstone", m_125977_(Blocks.f_50141_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50174_).m_126127_('#', Items.f_42398_).m_126127_('X', Items.f_42451_).m_126130_("X").m_126130_("#").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42497_, Items.f_42732_, "red_dye");
      m_176551_(p_176532_, Items.f_42497_, Blocks.f_50112_, "red_dye");
      m_176556_(p_176532_, Items.f_42497_, Blocks.f_50357_, "red_dye", 2);
      ShapelessRecipeBuilder.m_126189_(Items.f_42497_).m_126209_(Blocks.f_50116_).m_142409_("red_dye").m_142284_("has_red_flower", m_125977_(Blocks.f_50116_)).m_176500_(p_176532_, "red_dye_from_tulip");
      ShapedRecipeBuilder.m_126116_(Blocks.f_50452_).m_126127_('W', Items.f_42588_).m_126127_('N', Items.f_42691_).m_126130_("NW").m_126130_("WN").m_142284_("has_nether_wart", m_125977_(Items.f_42588_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50394_).m_126127_('#', Blocks.f_49993_).m_126130_("##").m_126130_("##").m_142284_("has_sand", m_125977_(Blocks.f_49993_)).m_176498_(p_176532_);
      m_176704_(Blocks.f_50467_, Ingredient.m_43929_(Blocks.f_50394_, Blocks.f_50395_)).m_142284_("has_red_sandstone", m_125977_(Blocks.f_50394_)).m_142284_("has_chiseled_red_sandstone", m_125977_(Blocks.f_50395_)).m_176498_(p_176532_);
      m_176710_(Blocks.f_50397_, Ingredient.m_43929_(Blocks.f_50394_, Blocks.f_50395_, Blocks.f_50396_)).m_142284_("has_red_sandstone", m_125977_(Blocks.f_50394_)).m_142284_("has_chiseled_red_sandstone", m_125977_(Blocks.f_50395_)).m_142284_("has_cut_red_sandstone", m_125977_(Blocks.f_50396_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50146_).m_126127_('#', Blocks.f_50174_).m_126127_('X', Items.f_42451_).m_126127_('I', Blocks.f_50069_).m_126130_("#X#").m_126130_("III").m_142284_("has_redstone_torch", m_125977_(Blocks.f_50174_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50062_).m_126127_('#', Blocks.f_49992_).m_126130_("##").m_126130_("##").m_142284_("has_sand", m_125977_(Blocks.f_49992_)).m_176498_(p_176532_);
      m_176704_(Blocks.f_50406_, Ingredient.m_43929_(Blocks.f_50062_, Blocks.f_50063_)).m_142284_("has_sandstone", m_125977_(Blocks.f_50062_)).m_142284_("has_chiseled_sandstone", m_125977_(Blocks.f_50063_)).m_176498_(p_176532_);
      m_176710_(Blocks.f_50263_, Ingredient.m_43929_(Blocks.f_50062_, Blocks.f_50063_, Blocks.f_50064_)).m_142284_("has_sandstone", m_125977_(Blocks.f_50062_)).m_142284_("has_chiseled_sandstone", m_125977_(Blocks.f_50063_)).m_142284_("has_cut_sandstone", m_125977_(Blocks.f_50064_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50386_).m_126127_('S', Items.f_42695_).m_126127_('C', Items.f_42696_).m_126130_("SCS").m_126130_("CCC").m_126130_("SCS").m_142284_("has_prismarine_crystals", m_125977_(Items.f_42696_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42574_).m_126127_('#', Items.f_42416_).m_126130_(" #").m_126130_("# ").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42740_).m_126121_('W', ItemTags.f_13168_).m_126127_('o', Items.f_42416_).m_126130_("WoW").m_126130_("WWW").m_126130_(" W ").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      m_176743_(p_176532_, Items.f_42518_, Items.f_42204_);
      m_176652_(p_176532_, Blocks.f_50396_, Blocks.f_50394_);
      m_176652_(p_176532_, Blocks.f_50064_, Blocks.f_50062_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50127_).m_126127_('#', Items.f_42452_).m_126130_("##").m_126130_("##").m_142284_("has_snowball", m_125977_(Items.f_42452_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50125_, 6).m_126127_('#', Blocks.f_50127_).m_126130_("###").m_142284_("has_snowball", m_125977_(Items.f_42452_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50684_).m_126121_('L', ItemTags.f_13182_).m_126127_('S', Items.f_42398_).m_126121_('#', ItemTags.f_13154_).m_126130_(" S ").m_126130_("S#S").m_126130_("LLL").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_142284_("has_soul_sand", m_125975_(ItemTags.f_13154_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42546_).m_126127_('#', Items.f_42587_).m_126127_('X', Items.f_42575_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_melon", m_125977_(Items.f_42575_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42737_, 2).m_126127_('#', Items.f_42525_).m_126127_('X', Items.f_42412_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_142284_("has_glowstone_dust", m_125977_(Items.f_42525_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_151059_).m_126127_('#', Items.f_151049_).m_126127_('X', Items.f_151052_).m_126130_(" # ").m_126130_(" X ").m_126130_(" X ").m_142284_("has_amethyst_shard", m_125977_(Items.f_151049_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42398_, 4).m_126121_('#', ItemTags.f_13168_).m_126130_("#").m_126130_("#").m_142409_("sticks").m_142284_("has_planks", m_125975_(ItemTags.f_13168_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Items.f_42398_, 1).m_126127_('#', Blocks.f_50571_).m_126130_("#").m_126130_("#").m_142409_("sticks").m_142284_("has_bamboo", m_125977_(Blocks.f_50571_)).m_176500_(p_176532_, "stick_from_bamboo_item");
      ShapedRecipeBuilder.m_126116_(Blocks.f_50032_).m_126127_('P', Blocks.f_50039_).m_126127_('S', Items.f_42518_).m_126130_("S").m_126130_("P").m_142284_("has_slime_ball", m_125977_(Items.f_42518_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50222_, 4).m_126127_('#', Blocks.f_50069_).m_126130_("##").m_126130_("##").m_142284_("has_stone", m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42428_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13165_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13165_)).m_176498_(p_176532_);
      m_176704_(Blocks.f_50411_, Ingredient.m_43929_(Blocks.f_50222_)).m_142284_("has_stone_bricks", m_125975_(ItemTags.f_13169_)).m_176498_(p_176532_);
      m_176710_(Blocks.f_50194_, Ingredient.m_43929_(Blocks.f_50222_)).m_142284_("has_stone_bricks", m_125975_(ItemTags.f_13169_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42429_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13165_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13165_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42427_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13165_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13165_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42426_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13165_).m_126130_("X").m_126130_("#").m_126130_("#").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13165_)).m_176498_(p_176532_);
      m_176700_(p_176532_, Blocks.f_50405_, Blocks.f_50470_);
      ShapedRecipeBuilder.m_126116_(Items.f_42425_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13165_).m_126130_("X").m_126130_("X").m_126130_("#").m_142284_("has_cobblestone", m_125975_(ItemTags.f_13165_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50041_).m_126127_('#', Items.f_42401_).m_126130_("##").m_126130_("##").m_142284_("has_string", m_125977_(Items.f_42401_)).m_176500_(p_176532_, m_176517_(Blocks.f_50041_, Items.f_42401_));
      m_176551_(p_176532_, Items.f_42501_, Blocks.f_50130_, "sugar");
      ShapelessRecipeBuilder.m_126191_(Items.f_42501_, 3).m_126209_(Items.f_42787_).m_142409_("sugar").m_142284_("has_honey_bottle", m_125977_(Items.f_42787_)).m_176500_(p_176532_, m_176517_(Items.f_42501_, Items.f_42787_));
      ShapedRecipeBuilder.m_126116_(Blocks.f_50716_).m_126127_('H', Items.f_42129_).m_126127_('R', Items.f_42451_).m_126130_(" R ").m_126130_("RHR").m_126130_(" R ").m_142284_("has_redstone", m_125977_(Items.f_42451_)).m_142284_("has_hay_block", m_125977_(Blocks.f_50335_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50077_).m_126124_('#', Ingredient.m_43929_(Blocks.f_49992_, Blocks.f_49993_)).m_126127_('X', Items.f_42403_).m_126130_("X#X").m_126130_("#X#").m_126130_("X#X").m_142284_("has_gunpowder", m_125977_(Items.f_42403_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42693_).m_126127_('A', Blocks.f_50077_).m_126127_('B', Items.f_42449_).m_126130_("A").m_126130_("B").m_142284_("has_minecart", m_125977_(Items.f_42449_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50081_, 4).m_126127_('#', Items.f_42398_).m_126124_('X', Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_126130_("X").m_126130_("#").m_142284_("has_stone_pickaxe", m_125977_(Items.f_42427_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50139_, 4).m_126124_('X', Ingredient.m_43929_(Items.f_42413_, Items.f_42414_)).m_126127_('#', Items.f_42398_).m_126121_('S', ItemTags.f_13154_).m_126130_("X").m_126130_("#").m_126130_("S").m_142284_("has_soul_sand", m_125975_(ItemTags.f_13154_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50681_).m_126127_('#', Items.f_42000_).m_126127_('X', Items.f_42749_).m_126130_("XXX").m_126130_("X#X").m_126130_("XXX").m_142284_("has_iron_nugget", m_125977_(Items.f_42749_)).m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50682_).m_126127_('#', Items.f_42053_).m_126127_('X', Items.f_42749_).m_126130_("XXX").m_126130_("X#X").m_126130_("XXX").m_142284_("has_soul_torch", m_125977_(Items.f_42053_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Blocks.f_50325_).m_126209_(Blocks.f_50087_).m_126209_(Blocks.f_50266_).m_142284_("has_tripwire_hook", m_125977_(Blocks.f_50266_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50266_, 2).m_126121_('#', ItemTags.f_13168_).m_126127_('S', Items.f_42398_).m_126127_('I', Items.f_42416_).m_126130_("I").m_126130_("S").m_126130_("#").m_142284_("has_string", m_125977_(Items.f_42401_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42354_).m_126127_('X', Items.f_42355_).m_126130_("XXX").m_126130_("X X").m_142284_("has_scute", m_125977_(Items.f_42355_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126191_(Items.f_42405_, 9).m_126209_(Blocks.f_50335_).m_142284_("has_hay_block", m_125977_(Blocks.f_50335_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42535_).m_126209_(Items.f_42499_).m_142409_("white_dye").m_142284_("has_bone_meal", m_125977_(Items.f_42499_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42535_, Blocks.f_50071_, "white_dye");
      ShapedRecipeBuilder.m_126116_(Items.f_42423_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13168_).m_126130_("XX").m_126130_("X#").m_126130_(" #").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42424_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13168_).m_126130_("XX").m_126130_(" #").m_126130_(" #").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42422_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13168_).m_126130_("XXX").m_126130_(" # ").m_126130_(" # ").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42421_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13168_).m_126130_("X").m_126130_("#").m_126130_("#").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Items.f_42420_).m_126127_('#', Items.f_42398_).m_126121_('X', ItemTags.f_13168_).m_126130_("X").m_126130_("X").m_126130_("#").m_142284_("has_stick", m_125977_(Items.f_42398_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42614_).m_126209_(Items.f_42517_).m_126209_(Items.f_42532_).m_126209_(Items.f_42402_).m_142284_("has_book", m_125977_(Items.f_42517_)).m_176498_(p_176532_);
      m_176551_(p_176532_, Items.f_42539_, Blocks.f_50111_, "yellow_dye");
      m_176556_(p_176532_, Items.f_42539_, Blocks.f_50355_, "yellow_dye", 2);
      m_176743_(p_176532_, Items.f_42576_, Items.f_42515_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50569_).m_126127_('#', Items.f_42715_).m_126127_('X', Items.f_42716_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142284_("has_nautilus_core", m_125977_(Items.f_42716_)).m_142284_("has_nautilus_shell", m_125977_(Items.f_42715_)).m_176498_(p_176532_);
      m_176612_(p_176532_, Blocks.f_50606_, Blocks.f_50394_);
      m_176612_(p_176532_, Blocks.f_50609_, Blocks.f_50222_);
      m_176612_(p_176532_, Blocks.f_50613_, Blocks.f_50062_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42721_).m_126209_(Items.f_42516_).m_126209_(Items.f_42682_).m_142284_("has_creeper_head", m_125977_(Items.f_42682_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42722_).m_126209_(Items.f_42516_).m_126209_(Items.f_42679_).m_142284_("has_wither_skeleton_skull", m_125977_(Items.f_42679_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42720_).m_126209_(Items.f_42516_).m_126209_(Blocks.f_50120_).m_142284_("has_oxeye_daisy", m_125977_(Blocks.f_50120_)).m_176498_(p_176532_);
      ShapelessRecipeBuilder.m_126189_(Items.f_42723_).m_126209_(Items.f_42516_).m_126209_(Items.f_42437_).m_142284_("has_enchanted_golden_apple", m_125977_(Items.f_42437_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_50616_, 6).m_126127_('~', Items.f_42401_).m_126127_('I', Blocks.f_50571_).m_126130_("I~I").m_126130_("I I").m_126130_("I I").m_142284_("has_bamboo", m_125977_(Blocks.f_50571_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50623_).m_126127_('I', Items.f_42398_).m_126127_('-', Blocks.f_50404_).m_126121_('#', ItemTags.f_13168_).m_126130_("I-I").m_126130_("# #").m_142284_("has_stone_slab", m_125977_(Blocks.f_50404_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50620_).m_126127_('#', Blocks.f_50470_).m_126127_('X', Blocks.f_50094_).m_126127_('I', Items.f_42416_).m_126130_("III").m_126130_("IXI").m_126130_("###").m_142284_("has_smooth_stone", m_125977_(Blocks.f_50470_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50619_).m_126121_('#', ItemTags.f_13182_).m_126127_('X', Blocks.f_50094_).m_126130_(" # ").m_126130_("#X#").m_126130_(" # ").m_142284_("has_furnace", m_125977_(Blocks.f_50094_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50621_).m_126121_('#', ItemTags.f_13168_).m_126127_('@', Items.f_42516_).m_126130_("@@").m_126130_("##").m_126130_("##").m_142284_("has_paper", m_125977_(Items.f_42516_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50625_).m_126121_('#', ItemTags.f_13168_).m_126127_('@', Items.f_42416_).m_126130_("@@").m_126130_("##").m_126130_("##").m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50622_).m_126121_('#', ItemTags.f_13168_).m_126127_('@', Items.f_42484_).m_126130_("@@").m_126130_("##").m_126130_("##").m_142284_("has_flint", m_125977_(Items.f_42484_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50679_).m_126127_('I', Items.f_42416_).m_126127_('#', Blocks.f_50069_).m_126130_(" I ").m_126130_("###").m_142284_("has_stone", m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50729_).m_126127_('S', Items.f_42021_).m_126127_('#', Items.f_42418_).m_126130_("SSS").m_126130_("S#S").m_126130_("SSS").m_142284_("has_netherite_ingot", m_125977_(Items.f_42418_)).m_176498_(p_176532_);
      m_176616_(p_176532_, Items.f_42418_, Items.f_42791_, "netherite_ingot_from_netherite_block", "netherite_ingot");
      ShapelessRecipeBuilder.m_126189_(Items.f_42418_).m_126211_(Items.f_42419_, 4).m_126211_(Items.f_42417_, 4).m_142409_("netherite_ingot").m_142284_("has_netherite_scrap", m_125977_(Items.f_42419_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50724_).m_126127_('O', Blocks.f_50723_).m_126127_('G', Blocks.f_50141_).m_126130_("OOO").m_126130_("GGG").m_126130_("OOO").m_142284_("has_obsidian", m_125977_(Blocks.f_50723_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_50184_).m_126127_('I', Items.f_42416_).m_126127_('N', Items.f_42749_).m_126130_("N").m_126130_("I").m_126130_("N").m_142284_("has_iron_nugget", m_125977_(Items.f_42749_)).m_142284_("has_iron_ingot", m_125977_(Items.f_42416_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126118_(Blocks.f_152498_, 2).m_126127_('G', Blocks.f_50058_).m_126127_('S', Items.f_151049_).m_126130_(" S ").m_126130_("SGS").m_126130_(" S ").m_142284_("has_amethyst_shard", m_125977_(Items.f_151049_)).m_176498_(p_176532_);
      ShapedRecipeBuilder.m_126116_(Blocks.f_152490_).m_126127_('S', Items.f_151049_).m_126130_("SS").m_126130_("SS").m_142284_("has_amethyst_shard", m_125977_(Items.f_151049_)).m_176498_(p_176532_);
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44078_).m_126359_(p_176532_, "armor_dye");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44086_).m_126359_(p_176532_, "banner_duplicate");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44079_).m_126359_(p_176532_, "book_cloning");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44082_).m_126359_(p_176532_, "firework_rocket");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44083_).m_126359_(p_176532_, "firework_star");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44084_).m_126359_(p_176532_, "firework_star_fade");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44080_).m_126359_(p_176532_, "map_cloning");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44081_).m_126359_(p_176532_, "map_extending");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44090_).m_126359_(p_176532_, "repair_item");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44087_).m_126359_(p_176532_, "shield_decoration");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44088_).m_126359_(p_176532_, "shulker_box_coloring");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44085_).m_126359_(p_176532_, "tipped_arrow");
      SpecialRecipeBuilder.m_126357_(RecipeSerializer.f_44089_).m_126359_(p_176532_, "suspicious_stew");
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42620_), Items.f_42674_, 0.35F, 200).m_142284_("has_potato", m_125977_(Items.f_42620_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42461_), Items.f_42460_, 0.3F, 200).m_142284_("has_clay_ball", m_125977_(Items.f_42461_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43911_(ItemTags.f_13181_), Items.f_42414_, 0.15F, 200).m_142284_("has_log", m_125975_(ItemTags.f_13181_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42730_), Items.f_42731_, 0.1F, 200).m_142284_("has_chorus_fruit", m_125977_(Items.f_42730_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42579_), Items.f_42580_, 0.35F, 200).m_142284_("has_beef", m_125977_(Items.f_42579_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42581_), Items.f_42582_, 0.35F, 200).m_142284_("has_chicken", m_125977_(Items.f_42581_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42526_), Items.f_42530_, 0.35F, 200).m_142284_("has_cod", m_125977_(Items.f_42526_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50575_), Items.f_42576_, 0.1F, 200).m_142284_("has_kelp", m_125977_(Blocks.f_50575_)).m_176500_(p_176532_, m_176656_(Items.f_42576_));
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42527_), Items.f_42531_, 0.35F, 200).m_142284_("has_salmon", m_125977_(Items.f_42527_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42658_), Items.f_42659_, 0.35F, 200).m_142284_("has_mutton", m_125977_(Items.f_42658_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42485_), Items.f_42486_, 0.35F, 200).m_142284_("has_porkchop", m_125977_(Items.f_42485_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42697_), Items.f_42698_, 0.35F, 200).m_142284_("has_rabbit", m_125977_(Items.f_42697_)).m_176498_(p_176532_);
      m_176591_(p_176532_, f_176505_, Items.f_42413_, 0.1F, 200, "coal");
      m_176591_(p_176532_, f_176506_, Items.f_42416_, 0.7F, 200, "iron_ingot");
      m_176591_(p_176532_, f_176507_, Items.f_151052_, 0.7F, 200, "copper_ingot");
      m_176591_(p_176532_, f_176508_, Items.f_42417_, 1.0F, 200, "gold_ingot");
      m_176591_(p_176532_, f_176509_, Items.f_42415_, 1.0F, 200, "diamond");
      m_176591_(p_176532_, f_176510_, Items.f_42534_, 0.2F, 200, "lapis_lazuli");
      m_176591_(p_176532_, f_176511_, Items.f_42451_, 0.7F, 200, "redstone");
      m_176591_(p_176532_, f_176512_, Items.f_42616_, 1.0F, 200, "emerald");
      m_176743_(p_176532_, Items.f_151050_, Items.f_150995_);
      m_176743_(p_176532_, Items.f_151051_, Items.f_150996_);
      m_176743_(p_176532_, Items.f_151053_, Items.f_150997_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43911_(ItemTags.f_13137_), Blocks.f_50058_.m_5456_(), 0.1F, 200).m_142284_("has_sand", m_125975_(ItemTags.f_13137_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50567_), Items.f_42540_, 0.1F, 200).m_142284_("has_sea_pickle", m_125977_(Blocks.f_50567_)).m_176500_(p_176532_, m_176656_(Items.f_42540_));
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50128_.m_5456_()), Items.f_42496_, 1.0F, 200).m_142284_("has_cactus", m_125977_(Blocks.f_50128_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42432_, Items.f_42431_, Items.f_42433_, Items.f_42434_, Items.f_42430_, Items.f_42476_, Items.f_42477_, Items.f_42478_, Items.f_42479_, Items.f_42652_), Items.f_42587_, 0.1F, 200).m_142284_("has_golden_pickaxe", m_125977_(Items.f_42432_)).m_142284_("has_golden_shovel", m_125977_(Items.f_42431_)).m_142284_("has_golden_axe", m_125977_(Items.f_42433_)).m_142284_("has_golden_hoe", m_125977_(Items.f_42434_)).m_142284_("has_golden_sword", m_125977_(Items.f_42430_)).m_142284_("has_golden_helmet", m_125977_(Items.f_42476_)).m_142284_("has_golden_chestplate", m_125977_(Items.f_42477_)).m_142284_("has_golden_leggings", m_125977_(Items.f_42478_)).m_142284_("has_golden_boots", m_125977_(Items.f_42479_)).m_142284_("has_golden_horse_armor", m_125977_(Items.f_42652_)).m_176500_(p_176532_, m_176656_(Items.f_42587_));
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Items.f_42385_, Items.f_42384_, Items.f_42386_, Items.f_42387_, Items.f_42383_, Items.f_42468_, Items.f_42469_, Items.f_42470_, Items.f_42471_, Items.f_42651_, Items.f_42464_, Items.f_42465_, Items.f_42466_, Items.f_42467_), Items.f_42749_, 0.1F, 200).m_142284_("has_iron_pickaxe", m_125977_(Items.f_42385_)).m_142284_("has_iron_shovel", m_125977_(Items.f_42384_)).m_142284_("has_iron_axe", m_125977_(Items.f_42386_)).m_142284_("has_iron_hoe", m_125977_(Items.f_42387_)).m_142284_("has_iron_sword", m_125977_(Items.f_42383_)).m_142284_("has_iron_helmet", m_125977_(Items.f_42468_)).m_142284_("has_iron_chestplate", m_125977_(Items.f_42469_)).m_142284_("has_iron_leggings", m_125977_(Items.f_42470_)).m_142284_("has_iron_boots", m_125977_(Items.f_42471_)).m_142284_("has_iron_horse_armor", m_125977_(Items.f_42651_)).m_142284_("has_chainmail_helmet", m_125977_(Items.f_42464_)).m_142284_("has_chainmail_chestplate", m_125977_(Items.f_42465_)).m_142284_("has_chainmail_leggings", m_125977_(Items.f_42466_)).m_142284_("has_chainmail_boots", m_125977_(Items.f_42467_)).m_176500_(p_176532_, m_176656_(Items.f_42749_));
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50129_), Blocks.f_50352_.m_5456_(), 0.35F, 200).m_142284_("has_clay_block", m_125977_(Blocks.f_50129_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50134_), Items.f_42691_, 0.1F, 200).m_142284_("has_netherrack", m_125977_(Blocks.f_50134_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50331_), Items.f_42692_, 0.2F, 200).m_142284_("has_nether_quartz_ore", m_125977_(Blocks.f_50331_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50057_), Blocks.f_50056_.m_5456_(), 0.15F, 200).m_142284_("has_wet_sponge", m_125977_(Blocks.f_50057_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50652_), Blocks.f_50069_.m_5456_(), 0.1F, 200).m_142284_("has_cobblestone", m_125977_(Blocks.f_50652_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50470_.m_5456_(), 0.1F, 200).m_142284_("has_stone", m_125977_(Blocks.f_50069_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50062_), Blocks.f_50471_.m_5456_(), 0.1F, 200).m_142284_("has_sandstone", m_125977_(Blocks.f_50062_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50394_), Blocks.f_50473_.m_5456_(), 0.1F, 200).m_142284_("has_red_sandstone", m_125977_(Blocks.f_50394_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50333_), Blocks.f_50472_.m_5456_(), 0.1F, 200).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50222_), Blocks.f_50224_.m_5456_(), 0.1F, 200).m_142284_("has_stone_bricks", m_125977_(Blocks.f_50222_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50302_), Blocks.f_50541_.m_5456_(), 0.1F, 200).m_142284_("has_black_terracotta", m_125977_(Blocks.f_50302_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50298_), Blocks.f_50537_.m_5456_(), 0.1F, 200).m_142284_("has_blue_terracotta", m_125977_(Blocks.f_50298_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50299_), Blocks.f_50538_.m_5456_(), 0.1F, 200).m_142284_("has_brown_terracotta", m_125977_(Blocks.f_50299_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50296_), Blocks.f_50535_.m_5456_(), 0.1F, 200).m_142284_("has_cyan_terracotta", m_125977_(Blocks.f_50296_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50294_), Blocks.f_50533_.m_5456_(), 0.1F, 200).m_142284_("has_gray_terracotta", m_125977_(Blocks.f_50294_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50300_), Blocks.f_50539_.m_5456_(), 0.1F, 200).m_142284_("has_green_terracotta", m_125977_(Blocks.f_50300_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50290_), Blocks.f_50529_.m_5456_(), 0.1F, 200).m_142284_("has_light_blue_terracotta", m_125977_(Blocks.f_50290_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50295_), Blocks.f_50534_.m_5456_(), 0.1F, 200).m_142284_("has_light_gray_terracotta", m_125977_(Blocks.f_50295_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50292_), Blocks.f_50531_.m_5456_(), 0.1F, 200).m_142284_("has_lime_terracotta", m_125977_(Blocks.f_50292_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50289_), Blocks.f_50528_.m_5456_(), 0.1F, 200).m_142284_("has_magenta_terracotta", m_125977_(Blocks.f_50289_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50288_), Blocks.f_50527_.m_5456_(), 0.1F, 200).m_142284_("has_orange_terracotta", m_125977_(Blocks.f_50288_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50293_), Blocks.f_50532_.m_5456_(), 0.1F, 200).m_142284_("has_pink_terracotta", m_125977_(Blocks.f_50293_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50297_), Blocks.f_50536_.m_5456_(), 0.1F, 200).m_142284_("has_purple_terracotta", m_125977_(Blocks.f_50297_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50301_), Blocks.f_50540_.m_5456_(), 0.1F, 200).m_142284_("has_red_terracotta", m_125977_(Blocks.f_50301_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50287_), Blocks.f_50526_.m_5456_(), 0.1F, 200).m_142284_("has_white_terracotta", m_125977_(Blocks.f_50287_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50291_), Blocks.f_50530_.m_5456_(), 0.1F, 200).m_142284_("has_yellow_terracotta", m_125977_(Blocks.f_50291_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50722_), Items.f_42419_, 2.0F, 200).m_142284_("has_ancient_debris", m_125977_(Blocks.f_50722_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_50137_), Blocks.f_152597_, 0.1F, 200).m_142284_("has_basalt", m_125977_(Blocks.f_50137_)).m_176498_(p_176532_);
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(Blocks.f_152551_), Blocks.f_152550_, 0.1F, 200).m_142284_("has_cobbled_deepslate", m_125977_(Blocks.f_152551_)).m_176498_(p_176532_);
      m_176625_(p_176532_, f_176505_, Items.f_42413_, 0.1F, 100, "coal");
      m_176625_(p_176532_, f_176506_, Items.f_42416_, 0.7F, 100, "iron_ingot");
      m_176625_(p_176532_, f_176507_, Items.f_151052_, 0.7F, 100, "copper_ingot");
      m_176625_(p_176532_, f_176508_, Items.f_42417_, 1.0F, 100, "gold_ingot");
      m_176625_(p_176532_, f_176509_, Items.f_42415_, 1.0F, 100, "diamond");
      m_176625_(p_176532_, f_176510_, Items.f_42534_, 0.2F, 100, "lapis_lazuli");
      m_176625_(p_176532_, f_176511_, Items.f_42451_, 0.7F, 100, "redstone");
      m_176625_(p_176532_, f_176512_, Items.f_42616_, 1.0F, 100, "emerald");
      SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Blocks.f_50331_), Items.f_42692_, 0.2F, 100).m_142284_("has_nether_quartz_ore", m_125977_(Blocks.f_50331_)).m_176500_(p_176532_, m_176668_(Items.f_42692_));
      SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Items.f_42432_, Items.f_42431_, Items.f_42433_, Items.f_42434_, Items.f_42430_, Items.f_42476_, Items.f_42477_, Items.f_42478_, Items.f_42479_, Items.f_42652_), Items.f_42587_, 0.1F, 100).m_142284_("has_golden_pickaxe", m_125977_(Items.f_42432_)).m_142284_("has_golden_shovel", m_125977_(Items.f_42431_)).m_142284_("has_golden_axe", m_125977_(Items.f_42433_)).m_142284_("has_golden_hoe", m_125977_(Items.f_42434_)).m_142284_("has_golden_sword", m_125977_(Items.f_42430_)).m_142284_("has_golden_helmet", m_125977_(Items.f_42476_)).m_142284_("has_golden_chestplate", m_125977_(Items.f_42477_)).m_142284_("has_golden_leggings", m_125977_(Items.f_42478_)).m_142284_("has_golden_boots", m_125977_(Items.f_42479_)).m_142284_("has_golden_horse_armor", m_125977_(Items.f_42652_)).m_176500_(p_176532_, m_176668_(Items.f_42587_));
      SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Items.f_42385_, Items.f_42384_, Items.f_42386_, Items.f_42387_, Items.f_42383_, Items.f_42468_, Items.f_42469_, Items.f_42470_, Items.f_42471_, Items.f_42651_, Items.f_42464_, Items.f_42465_, Items.f_42466_, Items.f_42467_), Items.f_42749_, 0.1F, 100).m_142284_("has_iron_pickaxe", m_125977_(Items.f_42385_)).m_142284_("has_iron_shovel", m_125977_(Items.f_42384_)).m_142284_("has_iron_axe", m_125977_(Items.f_42386_)).m_142284_("has_iron_hoe", m_125977_(Items.f_42387_)).m_142284_("has_iron_sword", m_125977_(Items.f_42383_)).m_142284_("has_iron_helmet", m_125977_(Items.f_42468_)).m_142284_("has_iron_chestplate", m_125977_(Items.f_42469_)).m_142284_("has_iron_leggings", m_125977_(Items.f_42470_)).m_142284_("has_iron_boots", m_125977_(Items.f_42471_)).m_142284_("has_iron_horse_armor", m_125977_(Items.f_42651_)).m_142284_("has_chainmail_helmet", m_125977_(Items.f_42464_)).m_142284_("has_chainmail_chestplate", m_125977_(Items.f_42465_)).m_142284_("has_chainmail_leggings", m_125977_(Items.f_42466_)).m_142284_("has_chainmail_boots", m_125977_(Items.f_42467_)).m_176500_(p_176532_, m_176668_(Items.f_42749_));
      SimpleCookingRecipeBuilder.m_126267_(Ingredient.m_43929_(Blocks.f_50722_), Items.f_42419_, 2.0F, 100).m_142284_("has_ancient_debris", m_125977_(Blocks.f_50722_)).m_176500_(p_176532_, m_176668_(Items.f_42419_));
      m_126006_(p_176532_, "smoking", RecipeSerializer.f_44093_, 100);
      m_126006_(p_176532_, "campfire_cooking", RecipeSerializer.f_44094_, 600);
      m_176546_(p_176532_, Blocks.f_50404_, Blocks.f_50069_, 2);
      m_176735_(p_176532_, Blocks.f_50635_, Blocks.f_50069_);
      m_176735_(p_176532_, Blocks.f_50222_, Blocks.f_50069_);
      m_176546_(p_176532_, Blocks.f_50411_, Blocks.f_50069_, 2);
      m_176735_(p_176532_, Blocks.f_50194_, Blocks.f_50069_);
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50225_).m_142284_("has_stone", m_125977_(Blocks.f_50069_)).m_176500_(p_176532_, "chiseled_stone_bricks_stone_from_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50069_), Blocks.f_50609_).m_142284_("has_stone", m_125977_(Blocks.f_50069_)).m_176500_(p_176532_, "stone_brick_walls_from_stone_stonecutting");
      m_176735_(p_176532_, Blocks.f_50064_, Blocks.f_50062_);
      m_176546_(p_176532_, Blocks.f_50406_, Blocks.f_50062_, 2);
      m_176546_(p_176532_, Blocks.f_50407_, Blocks.f_50062_, 2);
      m_176546_(p_176532_, Blocks.f_50407_, Blocks.f_50064_, 2);
      m_176735_(p_176532_, Blocks.f_50263_, Blocks.f_50062_);
      m_176735_(p_176532_, Blocks.f_50613_, Blocks.f_50062_);
      m_176735_(p_176532_, Blocks.f_50063_, Blocks.f_50062_);
      m_176735_(p_176532_, Blocks.f_50396_, Blocks.f_50394_);
      m_176546_(p_176532_, Blocks.f_50467_, Blocks.f_50394_, 2);
      m_176546_(p_176532_, Blocks.f_50468_, Blocks.f_50394_, 2);
      m_176546_(p_176532_, Blocks.f_50468_, Blocks.f_50396_, 2);
      m_176735_(p_176532_, Blocks.f_50397_, Blocks.f_50394_);
      m_176735_(p_176532_, Blocks.f_50606_, Blocks.f_50394_);
      m_176735_(p_176532_, Blocks.f_50395_, Blocks.f_50394_);
      SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50333_), Blocks.f_50413_, 2).m_142284_("has_quartz_block", m_125977_(Blocks.f_50333_)).m_176500_(p_176532_, "quartz_slab_from_stonecutting");
      m_176735_(p_176532_, Blocks.f_50284_, Blocks.f_50333_);
      m_176735_(p_176532_, Blocks.f_50283_, Blocks.f_50333_);
      m_176735_(p_176532_, Blocks.f_50282_, Blocks.f_50333_);
      m_176735_(p_176532_, Blocks.f_50714_, Blocks.f_50333_);
      m_176735_(p_176532_, Blocks.f_50157_, Blocks.f_50652_);
      m_176546_(p_176532_, Blocks.f_50409_, Blocks.f_50652_, 2);
      m_176735_(p_176532_, Blocks.f_50274_, Blocks.f_50652_);
      m_176546_(p_176532_, Blocks.f_50411_, Blocks.f_50222_, 2);
      m_176735_(p_176532_, Blocks.f_50194_, Blocks.f_50222_);
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50222_), Blocks.f_50609_).m_142284_("has_stone_bricks", m_125977_(Blocks.f_50222_)).m_176500_(p_176532_, "stone_brick_wall_from_stone_bricks_stonecutting");
      m_176735_(p_176532_, Blocks.f_50225_, Blocks.f_50222_);
      m_176546_(p_176532_, Blocks.f_50410_, Blocks.f_50076_, 2);
      m_176735_(p_176532_, Blocks.f_50193_, Blocks.f_50076_);
      m_176735_(p_176532_, Blocks.f_50604_, Blocks.f_50076_);
      m_176546_(p_176532_, Blocks.f_50412_, Blocks.f_50197_, 2);
      m_176735_(p_176532_, Blocks.f_50199_, Blocks.f_50197_);
      m_176735_(p_176532_, Blocks.f_50610_, Blocks.f_50197_);
      m_176735_(p_176532_, Blocks.f_50712_, Blocks.f_50197_);
      m_176546_(p_176532_, Blocks.f_50601_, Blocks.f_50452_, 2);
      m_176735_(p_176532_, Blocks.f_50640_, Blocks.f_50452_);
      m_176735_(p_176532_, Blocks.f_50612_, Blocks.f_50452_);
      m_176546_(p_176532_, Blocks.f_50469_, Blocks.f_50492_, 2);
      m_176735_(p_176532_, Blocks.f_50442_, Blocks.f_50492_);
      m_176735_(p_176532_, Blocks.f_50441_, Blocks.f_50492_);
      m_176546_(p_176532_, Blocks.f_50383_, Blocks.f_50377_, 2);
      m_176735_(p_176532_, Blocks.f_50380_, Blocks.f_50377_);
      m_176735_(p_176532_, Blocks.f_50605_, Blocks.f_50377_);
      SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50378_), Blocks.f_50384_, 2).m_142284_("has_prismarine_brick", m_125977_(Blocks.f_50378_)).m_176500_(p_176532_, "prismarine_brick_slab_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50378_), Blocks.f_50381_).m_142284_("has_prismarine_brick", m_125977_(Blocks.f_50378_)).m_176500_(p_176532_, "prismarine_brick_stairs_from_prismarine_stonecutting");
      m_176546_(p_176532_, Blocks.f_50385_, Blocks.f_50379_, 2);
      m_176735_(p_176532_, Blocks.f_50382_, Blocks.f_50379_);
      m_176546_(p_176532_, Blocks.f_50600_, Blocks.f_50334_, 2);
      m_176735_(p_176532_, Blocks.f_50639_, Blocks.f_50334_);
      m_176735_(p_176532_, Blocks.f_50611_, Blocks.f_50334_);
      m_176735_(p_176532_, Blocks.f_50387_, Blocks.f_50334_);
      m_176546_(p_176532_, Blocks.f_50602_, Blocks.f_50334_, 2);
      m_176735_(p_176532_, Blocks.f_50641_, Blocks.f_50334_);
      m_176546_(p_176532_, Blocks.f_50602_, Blocks.f_50387_, 2);
      m_176735_(p_176532_, Blocks.f_50641_, Blocks.f_50387_);
      m_176735_(p_176532_, Blocks.f_50138_, Blocks.f_50137_);
      m_176546_(p_176532_, Blocks.f_50651_, Blocks.f_50122_, 2);
      m_176735_(p_176532_, Blocks.f_50638_, Blocks.f_50122_);
      m_176735_(p_176532_, Blocks.f_50608_, Blocks.f_50122_);
      m_176735_(p_176532_, Blocks.f_50175_, Blocks.f_50122_);
      m_176546_(p_176532_, Blocks.f_50643_, Blocks.f_50122_, 2);
      m_176735_(p_176532_, Blocks.f_50629_, Blocks.f_50122_);
      m_176546_(p_176532_, Blocks.f_50643_, Blocks.f_50175_, 2);
      m_176735_(p_176532_, Blocks.f_50629_, Blocks.f_50175_);
      m_176546_(p_176532_, Blocks.f_50603_, Blocks.f_50228_, 2);
      m_176735_(p_176532_, Blocks.f_50642_, Blocks.f_50228_);
      m_176735_(p_176532_, Blocks.f_50615_, Blocks.f_50228_);
      m_176735_(p_176532_, Blocks.f_50281_, Blocks.f_50228_);
      m_176546_(p_176532_, Blocks.f_50646_, Blocks.f_50228_, 2);
      m_176735_(p_176532_, Blocks.f_50632_, Blocks.f_50228_);
      m_176546_(p_176532_, Blocks.f_50646_, Blocks.f_50281_, 2);
      m_176735_(p_176532_, Blocks.f_50632_, Blocks.f_50281_);
      SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50645_, 2).m_142284_("has_mossy_stone_bricks", m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50631_).m_142284_("has_mossy_stone_bricks", m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50223_), Blocks.f_50607_).m_142284_("has_mossy_stone_bricks", m_125977_(Blocks.f_50223_)).m_176500_(p_176532_, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
      m_176546_(p_176532_, Blocks.f_50647_, Blocks.f_50079_, 2);
      m_176735_(p_176532_, Blocks.f_50633_, Blocks.f_50079_);
      m_176735_(p_176532_, Blocks.f_50275_, Blocks.f_50079_);
      m_176546_(p_176532_, Blocks.f_50649_, Blocks.f_50471_, 2);
      m_176735_(p_176532_, Blocks.f_50636_, Blocks.f_50471_);
      m_176546_(p_176532_, Blocks.f_50644_, Blocks.f_50473_, 2);
      m_176735_(p_176532_, Blocks.f_50630_, Blocks.f_50473_);
      m_176546_(p_176532_, Blocks.f_50650_, Blocks.f_50472_, 2);
      m_176735_(p_176532_, Blocks.f_50637_, Blocks.f_50472_);
      SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50648_, 2).m_142284_("has_end_stone_brick", m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50634_).m_142284_("has_end_stone_brick", m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.m_126313_(Ingredient.m_43929_(Blocks.f_50443_), Blocks.f_50614_).m_142284_("has_end_stone_brick", m_125977_(Blocks.f_50443_)).m_176500_(p_176532_, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
      m_176735_(p_176532_, Blocks.f_50443_, Blocks.f_50259_);
      m_176546_(p_176532_, Blocks.f_50648_, Blocks.f_50259_, 2);
      m_176735_(p_176532_, Blocks.f_50634_, Blocks.f_50259_);
      m_176735_(p_176532_, Blocks.f_50614_, Blocks.f_50259_);
      m_176546_(p_176532_, Blocks.f_50405_, Blocks.f_50470_, 2);
      m_176546_(p_176532_, Blocks.f_50733_, Blocks.f_50730_, 2);
      m_176735_(p_176532_, Blocks.f_50731_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50732_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50734_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50711_, Blocks.f_50730_);
      m_176546_(p_176532_, Blocks.f_50708_, Blocks.f_50730_, 2);
      m_176735_(p_176532_, Blocks.f_50707_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50737_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50735_, Blocks.f_50730_);
      m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50730_, 2);
      m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50730_);
      m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50730_);
      m_176546_(p_176532_, Blocks.f_50708_, Blocks.f_50734_, 2);
      m_176735_(p_176532_, Blocks.f_50707_, Blocks.f_50734_);
      m_176735_(p_176532_, Blocks.f_50735_, Blocks.f_50734_);
      m_176735_(p_176532_, Blocks.f_50711_, Blocks.f_50734_);
      m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50734_, 2);
      m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50734_);
      m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50734_);
      m_176735_(p_176532_, Blocks.f_50737_, Blocks.f_50734_);
      m_176546_(p_176532_, Blocks.f_50738_, Blocks.f_50735_, 2);
      m_176735_(p_176532_, Blocks.f_50739_, Blocks.f_50735_);
      m_176735_(p_176532_, Blocks.f_50740_, Blocks.f_50735_);
      m_176546_(p_176532_, Blocks.f_152570_, Blocks.f_152510_, 2);
      m_176735_(p_176532_, Blocks.f_152566_, Blocks.f_152510_);
      m_176546_(p_176532_, Blocks.f_152569_, Blocks.f_152509_, 2);
      m_176735_(p_176532_, Blocks.f_152565_, Blocks.f_152509_);
      m_176546_(p_176532_, Blocks.f_152568_, Blocks.f_152508_, 2);
      m_176735_(p_176532_, Blocks.f_152564_, Blocks.f_152508_);
      m_176546_(p_176532_, Blocks.f_152567_, Blocks.f_152507_, 2);
      m_176735_(p_176532_, Blocks.f_152563_, Blocks.f_152507_);
      m_176546_(p_176532_, Blocks.f_152586_, Blocks.f_152578_, 2);
      m_176735_(p_176532_, Blocks.f_152582_, Blocks.f_152578_);
      m_176546_(p_176532_, Blocks.f_152585_, Blocks.f_152577_, 2);
      m_176735_(p_176532_, Blocks.f_152581_, Blocks.f_152577_);
      m_176546_(p_176532_, Blocks.f_152584_, Blocks.f_152576_, 2);
      m_176735_(p_176532_, Blocks.f_152580_, Blocks.f_152576_);
      m_176546_(p_176532_, Blocks.f_152583_, Blocks.f_152575_, 2);
      m_176735_(p_176532_, Blocks.f_152579_, Blocks.f_152575_);
      m_176735_(p_176532_, Blocks.f_152510_, Blocks.f_152504_);
      m_176735_(p_176532_, Blocks.f_152566_, Blocks.f_152504_);
      m_176546_(p_176532_, Blocks.f_152570_, Blocks.f_152504_, 2);
      m_176735_(p_176532_, Blocks.f_152509_, Blocks.f_152503_);
      m_176735_(p_176532_, Blocks.f_152565_, Blocks.f_152503_);
      m_176546_(p_176532_, Blocks.f_152569_, Blocks.f_152503_, 2);
      m_176735_(p_176532_, Blocks.f_152508_, Blocks.f_152502_);
      m_176735_(p_176532_, Blocks.f_152564_, Blocks.f_152502_);
      m_176546_(p_176532_, Blocks.f_152568_, Blocks.f_152502_, 2);
      m_176735_(p_176532_, Blocks.f_152507_, Blocks.f_152501_);
      m_176735_(p_176532_, Blocks.f_152563_, Blocks.f_152501_);
      m_176546_(p_176532_, Blocks.f_152567_, Blocks.f_152501_, 2);
      m_176735_(p_176532_, Blocks.f_152578_, Blocks.f_152571_);
      m_176735_(p_176532_, Blocks.f_152582_, Blocks.f_152571_);
      m_176546_(p_176532_, Blocks.f_152586_, Blocks.f_152571_, 2);
      m_176735_(p_176532_, Blocks.f_152577_, Blocks.f_152573_);
      m_176735_(p_176532_, Blocks.f_152581_, Blocks.f_152573_);
      m_176546_(p_176532_, Blocks.f_152585_, Blocks.f_152573_, 2);
      m_176735_(p_176532_, Blocks.f_152576_, Blocks.f_152572_);
      m_176735_(p_176532_, Blocks.f_152580_, Blocks.f_152572_);
      m_176546_(p_176532_, Blocks.f_152584_, Blocks.f_152572_, 2);
      m_176735_(p_176532_, Blocks.f_152575_, Blocks.f_152574_);
      m_176735_(p_176532_, Blocks.f_152579_, Blocks.f_152574_);
      m_176546_(p_176532_, Blocks.f_152583_, Blocks.f_152574_, 2);
      m_176546_(p_176532_, Blocks.f_152553_, Blocks.f_152551_, 2);
      m_176735_(p_176532_, Blocks.f_152552_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152554_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152593_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152555_, Blocks.f_152551_);
      m_176546_(p_176532_, Blocks.f_152557_, Blocks.f_152551_, 2);
      m_176735_(p_176532_, Blocks.f_152556_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152558_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152589_, Blocks.f_152551_);
      m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152551_, 2);
      m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152551_);
      m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152551_, 2);
      m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152551_);
      m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152551_);
      m_176546_(p_176532_, Blocks.f_152557_, Blocks.f_152555_, 2);
      m_176735_(p_176532_, Blocks.f_152556_, Blocks.f_152555_);
      m_176735_(p_176532_, Blocks.f_152558_, Blocks.f_152555_);
      m_176735_(p_176532_, Blocks.f_152589_, Blocks.f_152555_);
      m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152555_, 2);
      m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152555_);
      m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152555_);
      m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152555_);
      m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152555_, 2);
      m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152555_);
      m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152555_);
      m_176546_(p_176532_, Blocks.f_152591_, Blocks.f_152589_, 2);
      m_176735_(p_176532_, Blocks.f_152590_, Blocks.f_152589_);
      m_176735_(p_176532_, Blocks.f_152592_, Blocks.f_152589_);
      m_176735_(p_176532_, Blocks.f_152559_, Blocks.f_152589_);
      m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152589_, 2);
      m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152589_);
      m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152589_);
      m_176546_(p_176532_, Blocks.f_152561_, Blocks.f_152559_, 2);
      m_176735_(p_176532_, Blocks.f_152560_, Blocks.f_152559_);
      m_176735_(p_176532_, Blocks.f_152562_, Blocks.f_152559_);
      m_125994_(p_176532_, Items.f_42473_, Items.f_42481_);
      m_125994_(p_176532_, Items.f_42474_, Items.f_42482_);
      m_125994_(p_176532_, Items.f_42472_, Items.f_42480_);
      m_125994_(p_176532_, Items.f_42475_, Items.f_42483_);
      m_125994_(p_176532_, Items.f_42388_, Items.f_42393_);
      m_125994_(p_176532_, Items.f_42391_, Items.f_42396_);
      m_125994_(p_176532_, Items.f_42390_, Items.f_42395_);
      m_125994_(p_176532_, Items.f_42392_, Items.f_42397_);
      m_125994_(p_176532_, Items.f_42389_, Items.f_42394_);
   }

   private static void m_176551_(Consumer<FinishedRecipe> p_176552_, ItemLike p_176553_, ItemLike p_176554_, @Nullable String p_176555_) {
      m_176556_(p_176552_, p_176553_, p_176554_, p_176555_, 1);
   }

   private static void m_176556_(Consumer<FinishedRecipe> p_176557_, ItemLike p_176558_, ItemLike p_176559_, @Nullable String p_176560_, int p_176561_) {
      ShapelessRecipeBuilder.m_126191_(p_176558_, p_176561_).m_126209_(p_176559_).m_142409_(p_176560_).m_142284_(m_176602_(p_176559_), m_125977_(p_176559_)).m_176500_(p_176557_, m_176517_(p_176558_, p_176559_));
   }

   private static void m_176591_(Consumer<FinishedRecipe> p_176592_, List<ItemLike> p_176593_, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
      m_176533_(p_176592_, RecipeSerializer.f_44091_, p_176593_, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
   }

   private static void m_176625_(Consumer<FinishedRecipe> p_176626_, List<ItemLike> p_176627_, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
      m_176533_(p_176626_, RecipeSerializer.f_44092_, p_176627_, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
   }

   private static void m_176533_(Consumer<FinishedRecipe> p_176534_, SimpleCookingSerializer<?> p_176535_, List<ItemLike> p_176536_, ItemLike p_176537_, float p_176538_, int p_176539_, String p_176540_, String p_176541_) {
      for(ItemLike itemlike : p_176536_) {
         SimpleCookingRecipeBuilder.m_126248_(Ingredient.m_43929_(itemlike), p_176537_, p_176538_, p_176539_, p_176535_).m_142409_(p_176540_).m_142284_(m_176602_(itemlike), m_125977_(itemlike)).m_176500_(p_176534_, m_176632_(p_176537_) + p_176541_ + "_" + m_176632_(itemlike));
      }

   }

   private static void m_125994_(Consumer<FinishedRecipe> p_125995_, Item p_125996_, Item p_125997_) {
      UpgradeRecipeBuilder.m_126385_(Ingredient.m_43929_(p_125996_), Ingredient.m_43929_(Items.f_42418_), p_125997_).m_126389_("has_netherite_ingot", m_125977_(Items.f_42418_)).m_126392_(p_125995_, m_176632_(p_125997_) + "_smithing");
   }

   private static void m_125998_(Consumer<FinishedRecipe> p_125999_, ItemLike p_126000_, Tag<Item> p_126001_) {
      ShapelessRecipeBuilder.m_126191_(p_126000_, 4).m_126182_(p_126001_).m_142409_("planks").m_142284_("has_log", m_125975_(p_126001_)).m_176498_(p_125999_);
   }

   private static void m_126017_(Consumer<FinishedRecipe> p_126018_, ItemLike p_126019_, Tag<Item> p_126020_) {
      ShapelessRecipeBuilder.m_126191_(p_126019_, 4).m_126182_(p_126020_).m_142409_("planks").m_142284_("has_logs", m_125975_(p_126020_)).m_176498_(p_126018_);
   }

   private static void m_126002_(Consumer<FinishedRecipe> p_126003_, ItemLike p_126004_, ItemLike p_126005_) {
      ShapedRecipeBuilder.m_126118_(p_126004_, 3).m_126127_('#', p_126005_).m_126130_("##").m_126130_("##").m_142409_("bark").m_142284_("has_log", m_125977_(p_126005_)).m_176498_(p_126003_);
   }

   private static void m_126021_(Consumer<FinishedRecipe> p_126022_, ItemLike p_126023_, ItemLike p_126024_) {
      ShapedRecipeBuilder.m_126116_(p_126023_).m_126127_('#', p_126024_).m_126130_("# #").m_126130_("###").m_142409_("boat").m_142284_("in_water", m_125979_(Blocks.f_49990_)).m_176498_(p_126022_);
   }

   private static RecipeBuilder m_176658_(ItemLike p_176659_, Ingredient p_176660_) {
      return ShapelessRecipeBuilder.m_126189_(p_176659_).m_126184_(p_176660_);
   }

   private static RecipeBuilder m_176670_(ItemLike p_176671_, Ingredient p_176672_) {
      return ShapedRecipeBuilder.m_126118_(p_176671_, 3).m_126124_('#', p_176672_).m_126130_("##").m_126130_("##").m_126130_("##");
   }

   private static RecipeBuilder m_176678_(ItemLike p_176679_, Ingredient p_176680_) {
      int i = p_176679_ == Blocks.f_50198_ ? 6 : 3;
      Item item = p_176679_ == Blocks.f_50198_ ? Items.f_42691_ : Items.f_42398_;
      return ShapedRecipeBuilder.m_126118_(p_176679_, i).m_126124_('W', p_176680_).m_126127_('#', item).m_126130_("W#W").m_126130_("W#W");
   }

   private static RecipeBuilder m_176684_(ItemLike p_176685_, Ingredient p_176686_) {
      return ShapedRecipeBuilder.m_126116_(p_176685_).m_126127_('#', Items.f_42398_).m_126124_('W', p_176686_).m_126130_("#W#").m_126130_("#W#");
   }

   private static void m_176690_(Consumer<FinishedRecipe> p_176691_, ItemLike p_176692_, ItemLike p_176693_) {
      m_176694_(p_176692_, Ingredient.m_43929_(p_176693_)).m_142284_(m_176602_(p_176693_), m_125977_(p_176693_)).m_176498_(p_176691_);
   }

   private static RecipeBuilder m_176694_(ItemLike p_176695_, Ingredient p_176696_) {
      return ShapedRecipeBuilder.m_126116_(p_176695_).m_126124_('#', p_176696_).m_126130_("##");
   }

   private static void m_176700_(Consumer<FinishedRecipe> p_176701_, ItemLike p_176702_, ItemLike p_176703_) {
      m_176704_(p_176702_, Ingredient.m_43929_(p_176703_)).m_142284_(m_176602_(p_176703_), m_125977_(p_176703_)).m_176498_(p_176701_);
   }

   private static RecipeBuilder m_176704_(ItemLike p_176705_, Ingredient p_176706_) {
      return ShapedRecipeBuilder.m_126118_(p_176705_, 6).m_126124_('#', p_176706_).m_126130_("###");
   }

   private static RecipeBuilder m_176710_(ItemLike p_176711_, Ingredient p_176712_) {
      return ShapedRecipeBuilder.m_126118_(p_176711_, 4).m_126124_('#', p_176712_).m_126130_("#  ").m_126130_("## ").m_126130_("###");
   }

   private static RecipeBuilder m_176720_(ItemLike p_176721_, Ingredient p_176722_) {
      return ShapedRecipeBuilder.m_126118_(p_176721_, 2).m_126124_('#', p_176722_).m_126130_("###").m_126130_("###");
   }

   private static RecipeBuilder m_176726_(ItemLike p_176727_, Ingredient p_176728_) {
      return ShapedRecipeBuilder.m_126118_(p_176727_, 3).m_142409_("sign").m_126124_('#', p_176728_).m_126127_('X', Items.f_42398_).m_126130_("###").m_126130_("###").m_126130_(" X ");
   }

   private static void m_126061_(Consumer<FinishedRecipe> p_126062_, ItemLike p_126063_, ItemLike p_126064_) {
      ShapelessRecipeBuilder.m_126189_(p_126063_).m_126209_(p_126064_).m_126209_(Blocks.f_50041_).m_142409_("wool").m_142284_("has_white_wool", m_125977_(Blocks.f_50041_)).m_176498_(p_126062_);
   }

   private static void m_176716_(Consumer<FinishedRecipe> p_176717_, ItemLike p_176718_, ItemLike p_176719_) {
      ShapedRecipeBuilder.m_126118_(p_176718_, 3).m_126127_('#', p_176719_).m_126130_("##").m_142409_("carpet").m_142284_(m_176602_(p_176719_), m_125977_(p_176719_)).m_176498_(p_176717_);
   }

   private static void m_126069_(Consumer<FinishedRecipe> p_126070_, ItemLike p_126071_, ItemLike p_126072_) {
      ShapedRecipeBuilder.m_126118_(p_126071_, 8).m_126127_('#', Blocks.f_50336_).m_126127_('$', p_126072_).m_126130_("###").m_126130_("#$#").m_126130_("###").m_142409_("carpet").m_142284_("has_white_carpet", m_125977_(Blocks.f_50336_)).m_142284_(m_176602_(p_126072_), m_125977_(p_126072_)).m_176500_(p_126070_, m_176517_(p_126071_, Blocks.f_50336_));
   }

   private static void m_126073_(Consumer<FinishedRecipe> p_126074_, ItemLike p_126075_, ItemLike p_126076_) {
      ShapedRecipeBuilder.m_126116_(p_126075_).m_126127_('#', p_126076_).m_126121_('X', ItemTags.f_13168_).m_126130_("###").m_126130_("XXX").m_142409_("bed").m_142284_(m_176602_(p_126076_), m_125977_(p_126076_)).m_176498_(p_126074_);
   }

   private static void m_126077_(Consumer<FinishedRecipe> p_126078_, ItemLike p_126079_, ItemLike p_126080_) {
      ShapelessRecipeBuilder.m_126189_(p_126079_).m_126209_(Items.f_42503_).m_126209_(p_126080_).m_142409_("dyed_bed").m_142284_("has_bed", m_125977_(Items.f_42503_)).m_176500_(p_126078_, m_176517_(p_126079_, Items.f_42503_));
   }

   private static void m_126081_(Consumer<FinishedRecipe> p_126082_, ItemLike p_126083_, ItemLike p_126084_) {
      ShapedRecipeBuilder.m_126116_(p_126083_).m_126127_('#', p_126084_).m_126127_('|', Items.f_42398_).m_126130_("###").m_126130_("###").m_126130_(" | ").m_142409_("banner").m_142284_(m_176602_(p_126084_), m_125977_(p_126084_)).m_176498_(p_126082_);
   }

   private static void m_126085_(Consumer<FinishedRecipe> p_126086_, ItemLike p_126087_, ItemLike p_126088_) {
      ShapedRecipeBuilder.m_126118_(p_126087_, 8).m_126127_('#', Blocks.f_50058_).m_126127_('X', p_126088_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142409_("stained_glass").m_142284_("has_glass", m_125977_(Blocks.f_50058_)).m_176498_(p_126086_);
   }

   private static void m_126089_(Consumer<FinishedRecipe> p_126090_, ItemLike p_126091_, ItemLike p_126092_) {
      ShapedRecipeBuilder.m_126118_(p_126091_, 16).m_126127_('#', p_126092_).m_126130_("###").m_126130_("###").m_142409_("stained_glass_pane").m_142284_("has_glass", m_125977_(p_126092_)).m_176498_(p_126090_);
   }

   private static void m_126093_(Consumer<FinishedRecipe> p_126094_, ItemLike p_126095_, ItemLike p_126096_) {
      ShapedRecipeBuilder.m_126118_(p_126095_, 8).m_126127_('#', Blocks.f_50185_).m_126127_('$', p_126096_).m_126130_("###").m_126130_("#$#").m_126130_("###").m_142409_("stained_glass_pane").m_142284_("has_glass_pane", m_125977_(Blocks.f_50185_)).m_142284_(m_176602_(p_126096_), m_125977_(p_126096_)).m_176500_(p_126094_, m_176517_(p_126095_, Blocks.f_50185_));
   }

   private static void m_126097_(Consumer<FinishedRecipe> p_126098_, ItemLike p_126099_, ItemLike p_126100_) {
      ShapedRecipeBuilder.m_126118_(p_126099_, 8).m_126127_('#', Blocks.f_50352_).m_126127_('X', p_126100_).m_126130_("###").m_126130_("#X#").m_126130_("###").m_142409_("stained_terracotta").m_142284_("has_terracotta", m_125977_(Blocks.f_50352_)).m_176498_(p_126098_);
   }

   private static void m_126101_(Consumer<FinishedRecipe> p_126102_, ItemLike p_126103_, ItemLike p_126104_) {
      ShapelessRecipeBuilder.m_126191_(p_126103_, 8).m_126209_(p_126104_).m_126211_(Blocks.f_49992_, 4).m_126211_(Blocks.f_49994_, 4).m_142409_("concrete_powder").m_142284_("has_sand", m_125977_(Blocks.f_49992_)).m_142284_("has_gravel", m_125977_(Blocks.f_49994_)).m_176498_(p_126102_);
   }

   public static void m_176542_(Consumer<FinishedRecipe> p_176543_, ItemLike p_176544_, ItemLike p_176545_) {
      ShapelessRecipeBuilder.m_126189_(p_176544_).m_126209_(Blocks.f_152482_).m_126209_(p_176545_).m_142409_("dyed_candle").m_142284_(m_176602_(p_176545_), m_125977_(p_176545_)).m_176498_(p_176543_);
   }

   public static void m_176612_(Consumer<FinishedRecipe> p_176613_, ItemLike p_176614_, ItemLike p_176615_) {
      m_176514_(p_176614_, Ingredient.m_43929_(p_176615_)).m_142284_(m_176602_(p_176615_), m_125977_(p_176615_)).m_176498_(p_176613_);
   }

   public static RecipeBuilder m_176514_(ItemLike p_176515_, Ingredient p_176516_) {
      return ShapedRecipeBuilder.m_126118_(p_176515_, 6).m_126124_('#', p_176516_).m_126130_("###").m_126130_("###");
   }

   public static void m_176640_(Consumer<FinishedRecipe> p_176641_, ItemLike p_176642_, ItemLike p_176643_) {
      m_176604_(p_176642_, Ingredient.m_43929_(p_176643_)).m_142284_(m_176602_(p_176643_), m_125977_(p_176643_)).m_176498_(p_176641_);
   }

   public static RecipeBuilder m_176604_(ItemLike p_176605_, Ingredient p_176606_) {
      return ShapedRecipeBuilder.m_126118_(p_176605_, 4).m_126124_('S', p_176606_).m_126130_("SS").m_126130_("SS");
   }

   public static void m_176652_(Consumer<FinishedRecipe> p_176653_, ItemLike p_176654_, ItemLike p_176655_) {
      m_176634_(p_176654_, Ingredient.m_43929_(p_176655_)).m_142284_(m_176602_(p_176655_), m_125977_(p_176655_)).m_176498_(p_176653_);
   }

   public static ShapedRecipeBuilder m_176634_(ItemLike p_176635_, Ingredient p_176636_) {
      return ShapedRecipeBuilder.m_126118_(p_176635_, 4).m_126124_('#', p_176636_).m_126130_("##").m_126130_("##");
   }

   public static void m_176664_(Consumer<FinishedRecipe> p_176665_, ItemLike p_176666_, ItemLike p_176667_) {
      m_176646_(p_176666_, Ingredient.m_43929_(p_176667_)).m_142284_(m_176602_(p_176667_), m_125977_(p_176667_)).m_176498_(p_176665_);
   }

   public static ShapedRecipeBuilder m_176646_(ItemLike p_176647_, Ingredient p_176648_) {
      return ShapedRecipeBuilder.m_126116_(p_176647_).m_126124_('#', p_176648_).m_126130_("#").m_126130_("#");
   }

   private static void m_176735_(Consumer<FinishedRecipe> p_176736_, ItemLike p_176737_, ItemLike p_176738_) {
      m_176546_(p_176736_, p_176737_, p_176738_, 1);
   }

   private static void m_176546_(Consumer<FinishedRecipe> p_176547_, ItemLike p_176548_, ItemLike p_176549_, int p_176550_) {
      SingleItemRecipeBuilder.m_126316_(Ingredient.m_43929_(p_176549_), p_176548_, p_176550_).m_142284_(m_176602_(p_176549_), m_125977_(p_176549_)).m_176500_(p_176547_, m_176517_(p_176548_, p_176549_) + "_stonecutting");
   }

   private static void m_176739_(Consumer<FinishedRecipe> p_176740_, ItemLike p_176741_, ItemLike p_176742_) {
      SimpleCookingRecipeBuilder.m_126272_(Ingredient.m_43929_(p_176742_), p_176741_, 0.1F, 200).m_142284_(m_176602_(p_176742_), m_125977_(p_176742_)).m_176498_(p_176740_);
   }

   private static void m_176743_(Consumer<FinishedRecipe> p_176744_, ItemLike p_176745_, ItemLike p_176746_) {
      m_176568_(p_176744_, p_176745_, p_176746_, m_176644_(p_176746_), (String)null, m_176644_(p_176745_), (String)null);
   }

   private static void m_176562_(Consumer<FinishedRecipe> p_176563_, ItemLike p_176564_, ItemLike p_176565_, String p_176566_, String p_176567_) {
      m_176568_(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, m_176644_(p_176564_), (String)null);
   }

   private static void m_176616_(Consumer<FinishedRecipe> p_176617_, ItemLike p_176618_, ItemLike p_176619_, String p_176620_, String p_176621_) {
      m_176568_(p_176617_, p_176618_, p_176619_, m_176644_(p_176619_), (String)null, p_176620_, p_176621_);
   }

   private static void m_176568_(Consumer<FinishedRecipe> p_176569_, ItemLike p_176570_, ItemLike p_176571_, String p_176572_, @Nullable String p_176573_, String p_176574_, @Nullable String p_176575_) {
      ShapelessRecipeBuilder.m_126191_(p_176570_, 9).m_126209_(p_176571_).m_142409_(p_176575_).m_142284_(m_176602_(p_176571_), m_125977_(p_176571_)).m_142700_(p_176569_, new ResourceLocation(p_176574_));
      ShapedRecipeBuilder.m_126116_(p_176571_).m_126127_('#', p_176570_).m_126130_("###").m_126130_("###").m_126130_("###").m_142409_(p_176573_).m_142284_(m_176602_(p_176570_), m_125977_(p_176570_)).m_142700_(p_176569_, new ResourceLocation(p_176572_));
   }

   private static void m_126006_(Consumer<FinishedRecipe> p_126007_, String p_126008_, SimpleCookingSerializer<?> p_126009_, int p_126010_) {
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42579_, Items.f_42580_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42581_, Items.f_42582_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42526_, Items.f_42530_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_41910_, Items.f_42576_, 0.1F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42527_, Items.f_42531_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42658_, Items.f_42659_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42485_, Items.f_42486_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42620_, Items.f_42674_, 0.35F);
      m_176583_(p_126007_, p_126008_, p_126009_, p_126010_, Items.f_42697_, Items.f_42698_, 0.35F);
   }

   private static void m_176583_(Consumer<FinishedRecipe> p_176584_, String p_176585_, SimpleCookingSerializer<?> p_176586_, int p_176587_, ItemLike p_176588_, ItemLike p_176589_, float p_176590_) {
      SimpleCookingRecipeBuilder.m_126248_(Ingredient.m_43929_(p_176588_), p_176589_, p_176590_, p_176587_, p_176586_).m_142284_(m_176602_(p_176588_), m_125977_(p_176588_)).m_176500_(p_176584_, m_176632_(p_176589_) + "_from_" + p_176585_);
   }

   private static void m_176610_(Consumer<FinishedRecipe> p_176611_) {
      HoneycombItem.f_150863_.get().forEach((p_176578_, p_176579_) -> {
         ShapelessRecipeBuilder.m_126189_(p_176579_).m_126209_(p_176578_).m_126209_(Items.f_42784_).m_142409_(m_176632_(p_176579_)).m_142284_(m_176602_(p_176578_), m_125977_(p_176578_)).m_176500_(p_176611_, m_176517_(p_176579_, Items.f_42784_));
      });
   }

   private static void m_176580_(Consumer<FinishedRecipe> p_176581_, BlockFamily p_176582_) {
      p_176582_.m_175954_().forEach((p_176529_, p_176530_) -> {
         BiFunction<ItemLike, ItemLike, RecipeBuilder> bifunction = f_176513_.get(p_176529_);
         ItemLike itemlike = m_176523_(p_176582_, p_176529_);
         if (bifunction != null) {
            RecipeBuilder recipebuilder = bifunction.apply(p_176530_, itemlike);
            p_176582_.m_175957_().ifPresent((p_176601_) -> {
               recipebuilder.m_142409_(p_176601_ + (p_176529_ == BlockFamily.Variant.CUT ? "" : "_" + p_176529_.m_176020_()));
            });
            recipebuilder.m_142284_(p_176582_.m_175958_().orElseGet(() -> {
               return m_176602_(itemlike);
            }), m_125977_(itemlike));
            recipebuilder.m_176498_(p_176581_);
         }

         if (p_176529_ == BlockFamily.Variant.CRACKED) {
            m_176739_(p_176581_, p_176530_, itemlike);
         }

      });
   }

   private static Block m_176523_(BlockFamily p_176524_, BlockFamily.Variant p_176525_) {
      if (p_176525_ == BlockFamily.Variant.CHISELED) {
         if (!p_176524_.m_175954_().containsKey(BlockFamily.Variant.SLAB)) {
            throw new IllegalStateException("Slab is not defined for the family.");
         } else {
            return p_176524_.m_175952_(BlockFamily.Variant.SLAB);
         }
      } else {
         return p_176524_.m_175951_();
      }
   }

   protected static EnterBlockTrigger.TriggerInstance m_125979_(Block p_125980_) {
      return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_125980_, StatePropertiesPredicate.f_67658_);
   }

   private static InventoryChangeTrigger.TriggerInstance m_176520_(MinMaxBounds.Ints p_176521_, ItemLike p_176522_) {
      return m_126011_(ItemPredicate.Builder.m_45068_().m_151445_(p_176522_).m_151443_(p_176521_).m_45077_());
   }

   protected static InventoryChangeTrigger.TriggerInstance m_125977_(ItemLike p_125978_) {
      return m_126011_(ItemPredicate.Builder.m_45068_().m_151445_(p_125978_).m_45077_());
   }

   protected static InventoryChangeTrigger.TriggerInstance m_125975_(Tag<Item> p_125976_) {
      return m_126011_(ItemPredicate.Builder.m_45068_().m_45069_(p_125976_).m_45077_());
   }

   protected static InventoryChangeTrigger.TriggerInstance m_126011_(ItemPredicate... p_126012_) {
      return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, p_126012_);
   }

   private static String m_176602_(ItemLike p_176603_) {
      return "has_" + m_176632_(p_176603_);
   }

   private static String m_176632_(ItemLike p_176633_) {
      return Registry.f_122827_.m_7981_(p_176633_.m_5456_()).m_135815_();
   }

   private static String m_176644_(ItemLike p_176645_) {
      return m_176632_(p_176645_);
   }

   private static String m_176517_(ItemLike p_176518_, ItemLike p_176519_) {
      return m_176632_(p_176518_) + "_from_" + m_176632_(p_176519_);
   }

   private static String m_176656_(ItemLike p_176657_) {
      return m_176632_(p_176657_) + "_from_smelting";
   }

   private static String m_176668_(ItemLike p_176669_) {
      return m_176632_(p_176669_) + "_from_blasting";
   }

   public String m_6055_() {
      return "Recipes";
   }
}
