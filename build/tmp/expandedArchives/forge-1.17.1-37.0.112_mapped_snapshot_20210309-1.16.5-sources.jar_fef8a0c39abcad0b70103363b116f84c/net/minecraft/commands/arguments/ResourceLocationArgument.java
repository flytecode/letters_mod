package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ResourceLocationArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> f_106977_ = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType f_106978_ = new DynamicCommandExceptionType((p_107010_) -> {
      return new TranslatableComponent("advancement.advancementNotFound", p_107010_);
   });
   private static final DynamicCommandExceptionType f_106979_ = new DynamicCommandExceptionType((p_107005_) -> {
      return new TranslatableComponent("recipe.notFound", p_107005_);
   });
   private static final DynamicCommandExceptionType f_106980_ = new DynamicCommandExceptionType((p_106998_) -> {
      return new TranslatableComponent("predicate.unknown", p_106998_);
   });
   private static final DynamicCommandExceptionType f_106981_ = new DynamicCommandExceptionType((p_106991_) -> {
      return new TranslatableComponent("attribute.unknown", p_106991_);
   });
   private static final DynamicCommandExceptionType f_171024_ = new DynamicCommandExceptionType((p_171026_) -> {
      return new TranslatableComponent("item_modifier.unknown", p_171026_);
   });

   public static ResourceLocationArgument m_106984_() {
      return new ResourceLocationArgument();
   }

   public static Advancement m_106987_(CommandContext<CommandSourceStack> p_106988_, String p_106989_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_106988_.getArgument(p_106989_, ResourceLocation.class);
      Advancement advancement = p_106988_.getSource().m_81377_().m_129889_().m_136041_(resourcelocation);
      if (advancement == null) {
         throw f_106978_.create(resourcelocation);
      } else {
         return advancement;
      }
   }

   public static Recipe<?> m_106994_(CommandContext<CommandSourceStack> p_106995_, String p_106996_) throws CommandSyntaxException {
      RecipeManager recipemanager = p_106995_.getSource().m_81377_().m_129894_();
      ResourceLocation resourcelocation = p_106995_.getArgument(p_106996_, ResourceLocation.class);
      return recipemanager.m_44043_(resourcelocation).orElseThrow(() -> {
         return f_106979_.create(resourcelocation);
      });
   }

   public static LootItemCondition m_107001_(CommandContext<CommandSourceStack> p_107002_, String p_107003_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_107002_.getArgument(p_107003_, ResourceLocation.class);
      PredicateManager predicatemanager = p_107002_.getSource().m_81377_().m_129899_();
      LootItemCondition lootitemcondition = predicatemanager.m_79252_(resourcelocation);
      if (lootitemcondition == null) {
         throw f_106980_.create(resourcelocation);
      } else {
         return lootitemcondition;
      }
   }

   public static LootItemFunction m_171031_(CommandContext<CommandSourceStack> p_171032_, String p_171033_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_171032_.getArgument(p_171033_, ResourceLocation.class);
      ItemModifierManager itemmodifiermanager = p_171032_.getSource().m_81377_().m_177926_();
      LootItemFunction lootitemfunction = itemmodifiermanager.m_165108_(resourcelocation);
      if (lootitemfunction == null) {
         throw f_171024_.create(resourcelocation);
      } else {
         return lootitemfunction;
      }
   }

   public static Attribute m_107006_(CommandContext<CommandSourceStack> p_107007_, String p_107008_) throws CommandSyntaxException {
      ResourceLocation resourcelocation = p_107007_.getArgument(p_107008_, ResourceLocation.class);
      return Registry.f_122866_.m_6612_(resourcelocation).orElseThrow(() -> {
         return f_106981_.create(resourcelocation);
      });
   }

   public static ResourceLocation m_107011_(CommandContext<CommandSourceStack> p_107012_, String p_107013_) {
      return p_107012_.getArgument(p_107013_, ResourceLocation.class);
   }

   public ResourceLocation parse(StringReader p_106986_) throws CommandSyntaxException {
      return ResourceLocation.m_135818_(p_106986_);
   }

   public Collection<String> getExamples() {
      return f_106977_;
   }
}