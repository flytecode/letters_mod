package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetNbtFunction extends LootItemConditionalFunction {
   final CompoundTag f_81174_;

   SetNbtFunction(LootItemCondition[] p_81176_, CompoundTag p_81177_) {
      super(p_81176_);
      this.f_81174_ = p_81177_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80739_;
   }

   public ItemStack m_7372_(ItemStack p_81183_, LootContext p_81184_) {
      p_81183_.m_41784_().m_128391_(this.f_81174_);
      return p_81183_;
   }

   public static LootItemConditionalFunction.Builder<?> m_81187_(CompoundTag p_81188_) {
      return m_80683_((p_81191_) -> {
         return new SetNbtFunction(p_81191_, p_81188_);
      });
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetNbtFunction> {
      public void m_6170_(JsonObject p_81203_, SetNbtFunction p_81204_, JsonSerializationContext p_81205_) {
         super.m_6170_(p_81203_, p_81204_, p_81205_);
         p_81203_.addProperty("tag", p_81204_.f_81174_.toString());
      }

      public SetNbtFunction m_6821_(JsonObject p_81195_, JsonDeserializationContext p_81196_, LootItemCondition[] p_81197_) {
         try {
            CompoundTag compoundtag = TagParser.m_129359_(GsonHelper.m_13906_(p_81195_, "tag"));
            return new SetNbtFunction(p_81197_, compoundtag);
         } catch (CommandSyntaxException commandsyntaxexception) {
            throw new JsonSyntaxException(commandsyntaxexception.getMessage());
         }
      }
   }
}