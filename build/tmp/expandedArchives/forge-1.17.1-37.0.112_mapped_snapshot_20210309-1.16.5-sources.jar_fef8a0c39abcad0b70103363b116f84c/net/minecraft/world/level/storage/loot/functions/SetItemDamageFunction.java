package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetItemDamageFunction extends LootItemConditionalFunction {
   private static final Logger f_81037_ = LogManager.getLogger();
   final NumberProvider f_81038_;
   final boolean f_165425_;

   SetItemDamageFunction(LootItemCondition[] p_165427_, NumberProvider p_165428_, boolean p_165429_) {
      super(p_165427_);
      this.f_81038_ = p_165428_;
      this.f_165425_ = p_165429_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80742_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_81038_.m_6231_();
   }

   public ItemStack m_7372_(ItemStack p_81048_, LootContext p_81049_) {
      if (p_81048_.m_41763_()) {
         int i = p_81048_.m_41776_();
         float f = this.f_165425_ ? 1.0F - (float)p_81048_.m_41773_() / (float)i : 0.0F;
         float f1 = 1.0F - Mth.m_14036_(this.f_81038_.m_142688_(p_81049_) + f, 0.0F, 1.0F);
         p_81048_.m_41721_(Mth.m_14143_(f1 * (float)i));
      } else {
         f_81037_.warn("Couldn't set damage of loot item {}", (Object)p_81048_);
      }

      return p_81048_;
   }

   public static LootItemConditionalFunction.Builder<?> m_165430_(NumberProvider p_165431_) {
      return m_80683_((p_165441_) -> {
         return new SetItemDamageFunction(p_165441_, p_165431_, false);
      });
   }

   public static LootItemConditionalFunction.Builder<?> m_165432_(NumberProvider p_165433_, boolean p_165434_) {
      return m_80683_((p_165438_) -> {
         return new SetItemDamageFunction(p_165438_, p_165433_, p_165434_);
      });
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetItemDamageFunction> {
      public void m_6170_(JsonObject p_81068_, SetItemDamageFunction p_81069_, JsonSerializationContext p_81070_) {
         super.m_6170_(p_81068_, p_81069_, p_81070_);
         p_81068_.add("damage", p_81070_.serialize(p_81069_.f_81038_));
         p_81068_.addProperty("add", p_81069_.f_165425_);
      }

      public SetItemDamageFunction m_6821_(JsonObject p_81060_, JsonDeserializationContext p_81061_, LootItemCondition[] p_81062_) {
         NumberProvider numberprovider = GsonHelper.m_13836_(p_81060_, "damage", p_81061_, NumberProvider.class);
         boolean flag = GsonHelper.m_13855_(p_81060_, "add", false);
         return new SetItemDamageFunction(p_81062_, numberprovider, flag);
      }
   }
}