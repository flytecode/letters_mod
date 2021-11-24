package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetNameFunction extends LootItemConditionalFunction {
   private static final Logger f_81122_ = LogManager.getLogger();
   final Component f_81123_;
   @Nullable
   final LootContext.EntityTarget f_81124_;

   SetNameFunction(LootItemCondition[] p_81127_, @Nullable Component p_81128_, @Nullable LootContext.EntityTarget p_81129_) {
      super(p_81127_);
      this.f_81123_ = p_81128_;
      this.f_81124_ = p_81129_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80744_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_81124_ != null ? ImmutableSet.of(this.f_81124_.m_79003_()) : ImmutableSet.of();
   }

   public static UnaryOperator<Component> m_81139_(LootContext p_81140_, @Nullable LootContext.EntityTarget p_81141_) {
      if (p_81141_ != null) {
         Entity entity = p_81140_.m_78953_(p_81141_.m_79003_());
         if (entity != null) {
            CommandSourceStack commandsourcestack = entity.m_20203_().m_81325_(2);
            return (p_81147_) -> {
               try {
                  return ComponentUtils.m_130731_(commandsourcestack, p_81147_, entity, 0);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  f_81122_.warn("Failed to resolve text component", (Throwable)commandsyntaxexception);
                  return p_81147_;
               }
            };
         }
      }

      return (p_81152_) -> {
         return p_81152_;
      };
   }

   public ItemStack m_7372_(ItemStack p_81137_, LootContext p_81138_) {
      if (this.f_81123_ != null) {
         p_81137_.m_41714_(m_81139_(p_81138_, this.f_81124_).apply(this.f_81123_));
      }

      return p_81137_;
   }

   public static LootItemConditionalFunction.Builder<?> m_165457_(Component p_165458_) {
      return m_80683_((p_165468_) -> {
         return new SetNameFunction(p_165468_, p_165458_, (LootContext.EntityTarget)null);
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_165459_(Component p_165460_, LootContext.EntityTarget p_165461_) {
      return m_80683_((p_165465_) -> {
         return new SetNameFunction(p_165465_, p_165460_, p_165461_);
      });
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetNameFunction> {
      public void m_6170_(JsonObject p_81163_, SetNameFunction p_81164_, JsonSerializationContext p_81165_) {
         super.m_6170_(p_81163_, p_81164_, p_81165_);
         if (p_81164_.f_81123_ != null) {
            p_81163_.add("name", Component.Serializer.m_130716_(p_81164_.f_81123_));
         }

         if (p_81164_.f_81124_ != null) {
            p_81163_.add("entity", p_81165_.serialize(p_81164_.f_81124_));
         }

      }

      public SetNameFunction m_6821_(JsonObject p_81155_, JsonDeserializationContext p_81156_, LootItemCondition[] p_81157_) {
         Component component = Component.Serializer.m_130691_(p_81155_.get("name"));
         LootContext.EntityTarget lootcontext$entitytarget = GsonHelper.m_13845_(p_81155_, "entity", (LootContext.EntityTarget)null, p_81156_, LootContext.EntityTarget.class);
         return new SetNameFunction(p_81157_, component, lootcontext$entitytarget);
      }
   }
}