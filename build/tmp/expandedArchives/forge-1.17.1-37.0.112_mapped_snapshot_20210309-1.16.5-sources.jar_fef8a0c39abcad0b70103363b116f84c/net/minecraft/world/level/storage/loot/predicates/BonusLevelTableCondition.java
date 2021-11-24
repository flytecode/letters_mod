package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BonusLevelTableCondition implements LootItemCondition {
   final Enchantment f_81507_;
   final float[] f_81508_;

   BonusLevelTableCondition(Enchantment p_81510_, float[] p_81511_) {
      this.f_81507_ = p_81510_;
      this.f_81508_ = p_81511_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81820_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81463_);
   }

   public boolean test(LootContext p_81521_) {
      ItemStack itemstack = p_81521_.m_78953_(LootContextParams.f_81463_);
      int i = itemstack != null ? EnchantmentHelper.m_44843_(this.f_81507_, itemstack) : 0;
      float f = this.f_81508_[Math.min(i, this.f_81508_.length - 1)];
      return p_81521_.m_78933_().nextFloat() < f;
   }

   public static LootItemCondition.Builder m_81517_(Enchantment p_81518_, float... p_81519_) {
      return () -> {
         return new BonusLevelTableCondition(p_81518_, p_81519_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BonusLevelTableCondition> {
      public void m_6170_(JsonObject p_81537_, BonusLevelTableCondition p_81538_, JsonSerializationContext p_81539_) {
         p_81537_.addProperty("enchantment", Registry.f_122825_.m_7981_(p_81538_.f_81507_).toString());
         p_81537_.add("chances", p_81539_.serialize(p_81538_.f_81508_));
      }

      public BonusLevelTableCondition m_7561_(JsonObject p_81547_, JsonDeserializationContext p_81548_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_81547_, "enchantment"));
         Enchantment enchantment = Registry.f_122825_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonParseException("Invalid enchantment id: " + resourcelocation);
         });
         float[] afloat = GsonHelper.m_13836_(p_81547_, "chances", p_81548_, float[].class);
         return new BonusLevelTableCondition(enchantment, afloat);
      }
   }
}