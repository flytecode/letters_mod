package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.commands.arguments.OperationArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.commands.synchronization.brigadier.BrigadierArgumentSerializers;
import net.minecraft.gametest.framework.TestClassNameArgument;
import net.minecraft.gametest.framework.TestFunctionArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
   private static final Logger f_121582_ = LogManager.getLogger();
   private static final Map<Class<?>, ArgumentTypes.Entry<?>> f_121583_ = Maps.newHashMap();
   private static final Map<ResourceLocation, ArgumentTypes.Entry<?>> f_121584_ = Maps.newHashMap();

   public static <T extends ArgumentType<?>> void m_121601_(String p_121602_, Class<T> p_121603_, ArgumentSerializer<T> p_121604_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_121602_);
      if (f_121583_.containsKey(p_121603_)) {
         throw new IllegalArgumentException("Class " + p_121603_.getName() + " already has a serializer!");
      } else if (f_121584_.containsKey(resourcelocation)) {
         throw new IllegalArgumentException("'" + resourcelocation + "' is already a registered serializer!");
      } else {
         ArgumentTypes.Entry<T> entry = new ArgumentTypes.Entry<>(p_121603_, p_121604_, resourcelocation);
         f_121583_.put(p_121603_, entry);
         f_121584_.put(resourcelocation, entry);
      }
   }

   public static void m_121586_() {
      BrigadierArgumentSerializers.m_121685_();
      m_121601_("entity", EntityArgument.class, new EntityArgument.Serializer());
      m_121601_("game_profile", GameProfileArgument.class, new EmptyArgumentSerializer<>(GameProfileArgument::m_94584_));
      m_121601_("block_pos", BlockPosArgument.class, new EmptyArgumentSerializer<>(BlockPosArgument::m_118239_));
      m_121601_("column_pos", ColumnPosArgument.class, new EmptyArgumentSerializer<>(ColumnPosArgument::m_118989_));
      m_121601_("vec3", Vec3Argument.class, new EmptyArgumentSerializer<>(Vec3Argument::m_120841_));
      m_121601_("vec2", Vec2Argument.class, new EmptyArgumentSerializer<>(Vec2Argument::m_120822_));
      m_121601_("block_state", BlockStateArgument.class, new EmptyArgumentSerializer<>(BlockStateArgument::m_116120_));
      m_121601_("block_predicate", BlockPredicateArgument.class, new EmptyArgumentSerializer<>(BlockPredicateArgument::m_115570_));
      m_121601_("item_stack", ItemArgument.class, new EmptyArgumentSerializer<>(ItemArgument::m_120960_));
      m_121601_("item_predicate", ItemPredicateArgument.class, new EmptyArgumentSerializer<>(ItemPredicateArgument::m_121037_));
      m_121601_("color", ColorArgument.class, new EmptyArgumentSerializer<>(ColorArgument::m_85463_));
      m_121601_("component", ComponentArgument.class, new EmptyArgumentSerializer<>(ComponentArgument::m_87114_));
      m_121601_("message", MessageArgument.class, new EmptyArgumentSerializer<>(MessageArgument::m_96832_));
      m_121601_("nbt_compound_tag", CompoundTagArgument.class, new EmptyArgumentSerializer<>(CompoundTagArgument::m_87657_));
      m_121601_("nbt_tag", NbtTagArgument.class, new EmptyArgumentSerializer<>(NbtTagArgument::m_100659_));
      m_121601_("nbt_path", NbtPathArgument.class, new EmptyArgumentSerializer<>(NbtPathArgument::m_99487_));
      m_121601_("objective", ObjectiveArgument.class, new EmptyArgumentSerializer<>(ObjectiveArgument::m_101957_));
      m_121601_("objective_criteria", ObjectiveCriteriaArgument.class, new EmptyArgumentSerializer<>(ObjectiveCriteriaArgument::m_102555_));
      m_121601_("operation", OperationArgument.class, new EmptyArgumentSerializer<>(OperationArgument::m_103269_));
      m_121601_("particle", ParticleArgument.class, new EmptyArgumentSerializer<>(ParticleArgument::m_103931_));
      m_121601_("angle", AngleArgument.class, new EmptyArgumentSerializer<>(AngleArgument::m_83807_));
      m_121601_("rotation", RotationArgument.class, new EmptyArgumentSerializer<>(RotationArgument::m_120479_));
      m_121601_("scoreboard_slot", ScoreboardSlotArgument.class, new EmptyArgumentSerializer<>(ScoreboardSlotArgument::m_109196_));
      m_121601_("score_holder", ScoreHolderArgument.class, new ScoreHolderArgument.Serializer());
      m_121601_("swizzle", SwizzleArgument.class, new EmptyArgumentSerializer<>(SwizzleArgument::m_120807_));
      m_121601_("team", TeamArgument.class, new EmptyArgumentSerializer<>(TeamArgument::m_112088_));
      m_121601_("item_slot", SlotArgument.class, new EmptyArgumentSerializer<>(SlotArgument::m_111276_));
      m_121601_("resource_location", ResourceLocationArgument.class, new EmptyArgumentSerializer<>(ResourceLocationArgument::m_106984_));
      m_121601_("mob_effect", MobEffectArgument.class, new EmptyArgumentSerializer<>(MobEffectArgument::m_98426_));
      m_121601_("function", FunctionArgument.class, new EmptyArgumentSerializer<>(FunctionArgument::m_120907_));
      m_121601_("entity_anchor", EntityAnchorArgument.class, new EmptyArgumentSerializer<>(EntityAnchorArgument::m_90350_));
      m_121601_("int_range", RangeArgument.Ints.class, new EmptyArgumentSerializer<>(RangeArgument::m_105404_));
      m_121601_("float_range", RangeArgument.Floats.class, new EmptyArgumentSerializer<>(RangeArgument::m_105405_));
      m_121601_("item_enchantment", ItemEnchantmentArgument.class, new EmptyArgumentSerializer<>(ItemEnchantmentArgument::m_95260_));
      m_121601_("entity_summon", EntitySummonArgument.class, new EmptyArgumentSerializer<>(EntitySummonArgument::m_93335_));
      m_121601_("dimension", DimensionArgument.class, new EmptyArgumentSerializer<>(DimensionArgument::m_88805_));
      m_121601_("time", TimeArgument.class, new EmptyArgumentSerializer<>(TimeArgument::m_113037_));
      m_121601_("uuid", UuidArgument.class, new EmptyArgumentSerializer<>(UuidArgument::m_113850_));
      if (SharedConstants.f_136183_) {
         m_121601_("test_argument", TestFunctionArgument.class, new EmptyArgumentSerializer<>(TestFunctionArgument::m_128088_));
         m_121601_("test_class", TestClassNameArgument.class, new EmptyArgumentSerializer<>(TestClassNameArgument::m_127917_));
      }

   }

   @Nullable
   private static ArgumentTypes.Entry<?> m_121614_(ResourceLocation p_121615_) {
      return f_121584_.get(p_121615_);
   }

   @Nullable
   private static ArgumentTypes.Entry<?> m_121616_(ArgumentType<?> p_121617_) {
      return f_121583_.get(p_121617_.getClass());
   }

   public static <T extends ArgumentType<?>> void m_121611_(FriendlyByteBuf p_121612_, T p_121613_) {
      ArgumentTypes.Entry<T> entry = (ArgumentTypes.Entry<T>)m_121616_(p_121613_);
      if (entry == null) {
         f_121582_.error("Could not serialize {} ({}) - will not be sent to client!", p_121613_, p_121613_.getClass());
         p_121612_.m_130085_(new ResourceLocation(""));
      } else {
         p_121612_.m_130085_(entry.f_121620_);
         entry.f_121619_.m_6017_(p_121613_, p_121612_);
      }
   }

   @Nullable
   public static ArgumentType<?> m_121609_(FriendlyByteBuf p_121610_) {
      ResourceLocation resourcelocation = p_121610_.m_130281_();
      ArgumentTypes.Entry<?> entry = m_121614_(resourcelocation);
      if (entry == null) {
         f_121582_.error("Could not deserialize {}", (Object)resourcelocation);
         return null;
      } else {
         return entry.f_121619_.m_7813_(p_121610_);
      }
   }

   private static <T extends ArgumentType<?>> void m_121587_(JsonObject p_121588_, T p_121589_) {
      ArgumentTypes.Entry<T> entry = (ArgumentTypes.Entry<T>)m_121616_(p_121589_);
      if (entry == null) {
         f_121582_.error("Could not serialize argument {} ({})!", p_121589_, p_121589_.getClass());
         p_121588_.addProperty("type", "unknown");
      } else {
         p_121588_.addProperty("type", "argument");
         p_121588_.addProperty("parser", entry.f_121620_.toString());
         JsonObject jsonobject = new JsonObject();
         entry.f_121619_.m_6964_(p_121589_, jsonobject);
         if (jsonobject.size() > 0) {
            p_121588_.add("properties", jsonobject);
         }
      }

   }

   public static <S> JsonObject m_121590_(CommandDispatcher<S> p_121591_, CommandNode<S> p_121592_) {
      JsonObject jsonobject = new JsonObject();
      if (p_121592_ instanceof RootCommandNode) {
         jsonobject.addProperty("type", "root");
      } else if (p_121592_ instanceof LiteralCommandNode) {
         jsonobject.addProperty("type", "literal");
      } else if (p_121592_ instanceof ArgumentCommandNode) {
         m_121587_(jsonobject, ((ArgumentCommandNode)p_121592_).getType());
      } else {
         f_121582_.error("Could not serialize node {} ({})!", p_121592_, p_121592_.getClass());
         jsonobject.addProperty("type", "unknown");
      }

      JsonObject jsonobject1 = new JsonObject();

      for(CommandNode<S> commandnode : p_121592_.getChildren()) {
         jsonobject1.add(commandnode.getName(), m_121590_(p_121591_, commandnode));
      }

      if (jsonobject1.size() > 0) {
         jsonobject.add("children", jsonobject1);
      }

      if (p_121592_.getCommand() != null) {
         jsonobject.addProperty("executable", true);
      }

      if (p_121592_.getRedirect() != null) {
         Collection<String> collection = p_121591_.getPath(p_121592_.getRedirect());
         if (!collection.isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(String s : collection) {
               jsonarray.add(s);
            }

            jsonobject.add("redirect", jsonarray);
         }
      }

      return jsonobject;
   }

   public static boolean m_121593_(ArgumentType<?> p_121594_) {
      return m_121616_(p_121594_) != null;
   }

   public static <T> Set<ArgumentType<?>> m_121595_(CommandNode<T> p_121596_) {
      Set<CommandNode<T>> set = Sets.newIdentityHashSet();
      Set<ArgumentType<?>> set1 = Sets.newHashSet();
      m_121597_(p_121596_, set1, set);
      return set1;
   }

   private static <T> void m_121597_(CommandNode<T> p_121598_, Set<ArgumentType<?>> p_121599_, Set<CommandNode<T>> p_121600_) {
      if (p_121600_.add(p_121598_)) {
         if (p_121598_ instanceof ArgumentCommandNode) {
            p_121599_.add(((ArgumentCommandNode)p_121598_).getType());
         }

         p_121598_.getChildren().forEach((p_121608_) -> {
            m_121597_(p_121608_, p_121599_, p_121600_);
         });
         CommandNode<T> commandnode = p_121598_.getRedirect();
         if (commandnode != null) {
            m_121597_(commandnode, p_121599_, p_121600_);
         }

      }
   }

   static class Entry<T extends ArgumentType<?>> {
      public final Class<T> f_121618_;
      public final ArgumentSerializer<T> f_121619_;
      public final ResourceLocation f_121620_;

      Entry(Class<T> p_121622_, ArgumentSerializer<T> p_121623_, ResourceLocation p_121624_) {
         this.f_121618_ = p_121622_;
         this.f_121619_ = p_121623_;
         this.f_121620_ = p_121624_;
      }
   }
   @javax.annotation.Nullable public static ResourceLocation getId(ArgumentType<?> type) {
      Entry<?> entry = m_121616_(type);
      return entry == null ? null : entry.f_121620_;
   }
}
