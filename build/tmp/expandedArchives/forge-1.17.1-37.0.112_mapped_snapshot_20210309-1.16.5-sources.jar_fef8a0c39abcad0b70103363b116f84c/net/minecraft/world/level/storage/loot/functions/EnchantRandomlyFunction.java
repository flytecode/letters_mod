package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomlyFunction extends LootItemConditionalFunction {
   private static final Logger f_80414_ = LogManager.getLogger();
   final List<Enchantment> f_80415_;

   EnchantRandomlyFunction(LootItemCondition[] p_80418_, Collection<Enchantment> p_80419_) {
      super(p_80418_);
      this.f_80415_ = ImmutableList.copyOf(p_80419_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80738_;
   }

   public ItemStack m_7372_(ItemStack p_80429_, LootContext p_80430_) {
      Random random = p_80430_.m_78933_();
      Enchantment enchantment;
      if (this.f_80415_.isEmpty()) {
         boolean flag = p_80429_.m_150930_(Items.f_42517_);
         List<Enchantment> list = Registry.f_122825_.m_123024_().filter(Enchantment::m_6592_).filter((p_80436_) -> {
            return flag || p_80436_.m_6081_(p_80429_);
         }).collect(Collectors.toList());
         if (list.isEmpty()) {
            f_80414_.warn("Couldn't find a compatible enchantment for {}", (Object)p_80429_);
            return p_80429_;
         }

         enchantment = list.get(random.nextInt(list.size()));
      } else {
         enchantment = this.f_80415_.get(random.nextInt(this.f_80415_.size()));
      }

      return m_80424_(p_80429_, enchantment, random);
   }

   private static ItemStack m_80424_(ItemStack p_80425_, Enchantment p_80426_, Random p_80427_) {
      int i = Mth.m_14072_(p_80427_, p_80426_.m_44702_(), p_80426_.m_6586_());
      if (p_80425_.m_150930_(Items.f_42517_)) {
         p_80425_ = new ItemStack(Items.f_42690_);
         EnchantedBookItem.m_41153_(p_80425_, new EnchantmentInstance(p_80426_, i));
      } else {
         p_80425_.m_41663_(p_80426_, i);
      }

      return p_80425_;
   }

   public static EnchantRandomlyFunction.Builder m_165191_() {
      return new EnchantRandomlyFunction.Builder();
   }

   public static LootItemConditionalFunction.Builder<?> m_80440_() {
      return m_80683_((p_80438_) -> {
         return new EnchantRandomlyFunction(p_80438_, ImmutableList.of());
      });
   }

   public static class Builder extends LootItemConditionalFunction.Builder<EnchantRandomlyFunction.Builder> {
      private final Set<Enchantment> f_80441_ = Sets.newHashSet();

      protected EnchantRandomlyFunction.Builder m_6477_() {
         return this;
      }

      public EnchantRandomlyFunction.Builder m_80444_(Enchantment p_80445_) {
         this.f_80441_.add(p_80445_);
         return this;
      }

      public LootItemFunction m_7453_() {
         return new EnchantRandomlyFunction(this.m_80699_(), this.f_80441_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<EnchantRandomlyFunction> {
      public void m_6170_(JsonObject p_80454_, EnchantRandomlyFunction p_80455_, JsonSerializationContext p_80456_) {
         super.m_6170_(p_80454_, p_80455_, p_80456_);
         if (!p_80455_.f_80415_.isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(Enchantment enchantment : p_80455_.f_80415_) {
               ResourceLocation resourcelocation = Registry.f_122825_.m_7981_(enchantment);
               if (resourcelocation == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + enchantment);
               }

               jsonarray.add(new JsonPrimitive(resourcelocation.toString()));
            }

            p_80454_.add("enchantments", jsonarray);
         }

      }

      public EnchantRandomlyFunction m_6821_(JsonObject p_80450_, JsonDeserializationContext p_80451_, LootItemCondition[] p_80452_) {
         List<Enchantment> list = Lists.newArrayList();
         if (p_80450_.has("enchantments")) {
            for(JsonElement jsonelement : GsonHelper.m_13933_(p_80450_, "enchantments")) {
               String s = GsonHelper.m_13805_(jsonelement, "enchantment");
               Enchantment enchantment = Registry.f_122825_.m_6612_(new ResourceLocation(s)).orElseThrow(() -> {
                  return new JsonSyntaxException("Unknown enchantment '" + s + "'");
               });
               list.add(enchantment);
            }
         }

         return new EnchantRandomlyFunction(p_80452_, list);
      }
   }
}