package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentPredicate {
   public static final EnchantmentPredicate f_30464_ = new EnchantmentPredicate();
   public static final EnchantmentPredicate[] f_30465_ = new EnchantmentPredicate[0];
   private final Enchantment f_30466_;
   private final MinMaxBounds.Ints f_30467_;

   public EnchantmentPredicate() {
      this.f_30466_ = null;
      this.f_30467_ = MinMaxBounds.Ints.f_55364_;
   }

   public EnchantmentPredicate(@Nullable Enchantment p_30471_, MinMaxBounds.Ints p_30472_) {
      this.f_30466_ = p_30471_;
      this.f_30467_ = p_30472_;
   }

   public boolean m_30476_(Map<Enchantment, Integer> p_30477_) {
      if (this.f_30466_ != null) {
         if (!p_30477_.containsKey(this.f_30466_)) {
            return false;
         }

         int i = p_30477_.get(this.f_30466_);
         if (this.f_30467_ != null && !this.f_30467_.m_55390_(i)) {
            return false;
         }
      } else if (this.f_30467_ != null) {
         for(Integer integer : p_30477_.values()) {
            if (this.f_30467_.m_55390_(integer)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement m_30473_() {
      if (this == f_30464_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_30466_ != null) {
            jsonobject.addProperty("enchantment", Registry.f_122825_.m_7981_(this.f_30466_).toString());
         }

         jsonobject.add("levels", this.f_30467_.m_55328_());
         return jsonobject;
      }
   }

   public static EnchantmentPredicate m_30474_(@Nullable JsonElement p_30475_) {
      if (p_30475_ != null && !p_30475_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_30475_, "enchantment");
         Enchantment enchantment = null;
         if (jsonobject.has("enchantment")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "enchantment"));
            enchantment = Registry.f_122825_.m_6612_(resourcelocation).orElseThrow(() -> {
               return new JsonSyntaxException("Unknown enchantment '" + resourcelocation + "'");
            });
         }

         MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(jsonobject.get("levels"));
         return new EnchantmentPredicate(enchantment, minmaxbounds$ints);
      } else {
         return f_30464_;
      }
   }

   public static EnchantmentPredicate[] m_30480_(@Nullable JsonElement p_30481_) {
      if (p_30481_ != null && !p_30481_.isJsonNull()) {
         JsonArray jsonarray = GsonHelper.m_13924_(p_30481_, "enchantments");
         EnchantmentPredicate[] aenchantmentpredicate = new EnchantmentPredicate[jsonarray.size()];

         for(int i = 0; i < aenchantmentpredicate.length; ++i) {
            aenchantmentpredicate[i] = m_30474_(jsonarray.get(i));
         }

         return aenchantmentpredicate;
      } else {
         return f_30465_;
      }
   }
}