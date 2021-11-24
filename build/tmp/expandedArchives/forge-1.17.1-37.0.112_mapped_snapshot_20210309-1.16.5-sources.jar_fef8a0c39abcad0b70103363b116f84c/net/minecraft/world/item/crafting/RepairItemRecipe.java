package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class RepairItemRecipe extends CustomRecipe {
   public RepairItemRecipe(ResourceLocation p_44126_) {
      super(p_44126_);
   }

   public boolean m_5818_(CraftingContainer p_44138_, Level p_44139_) {
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < p_44138_.m_6643_(); ++i) {
         ItemStack itemstack = p_44138_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            list.add(itemstack);
            if (list.size() > 1) {
               ItemStack itemstack1 = list.get(0);
               if (itemstack.m_41720_() != itemstack1.m_41720_() || itemstack1.m_41613_() != 1 || itemstack.m_41613_() != 1 || !itemstack1.isRepairable()) {
                  return false;
               }
            }
         }
      }

      return list.size() == 2;
   }

   public ItemStack m_5874_(CraftingContainer p_44136_) {
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < p_44136_.m_6643_(); ++i) {
         ItemStack itemstack = p_44136_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            list.add(itemstack);
            if (list.size() > 1) {
               ItemStack itemstack1 = list.get(0);
               if (itemstack.m_41720_() != itemstack1.m_41720_() || itemstack1.m_41613_() != 1 || itemstack.m_41613_() != 1 || !itemstack1.isRepairable()) {
                  return ItemStack.f_41583_;
               }
            }
         }
      }

      if (list.size() == 2) {
         ItemStack itemstack3 = list.get(0);
         ItemStack itemstack4 = list.get(1);
         if (itemstack3.m_41720_() == itemstack4.m_41720_() && itemstack3.m_41613_() == 1 && itemstack4.m_41613_() == 1 && itemstack3.isRepairable()) {
            Item item = itemstack3.m_41720_();
            int j = itemstack3.m_41776_() - itemstack3.m_41773_();
            int k = itemstack3.m_41776_() - itemstack4.m_41773_();
            int l = j + k + itemstack3.m_41776_() * 5 / 100;
            int i1 = itemstack3.m_41776_() - l;
            if (i1 < 0) {
               i1 = 0;
            }

            ItemStack itemstack2 = new ItemStack(itemstack3.m_41720_());
            itemstack2.m_41721_(i1);
            Map<Enchantment, Integer> map = Maps.newHashMap();
            Map<Enchantment, Integer> map1 = EnchantmentHelper.m_44831_(itemstack3);
            Map<Enchantment, Integer> map2 = EnchantmentHelper.m_44831_(itemstack4);
            Registry.f_122825_.m_123024_().filter(Enchantment::m_6589_).forEach((p_44144_) -> {
               int j1 = Math.max(map1.getOrDefault(p_44144_, 0), map2.getOrDefault(p_44144_, 0));
               if (j1 > 0) {
                  map.put(p_44144_, j1);
               }

            });
            if (!map.isEmpty()) {
               EnchantmentHelper.m_44865_(map, itemstack2);
            }

            return itemstack2;
         }
      }

      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int p_44128_, int p_44129_) {
      return p_44128_ * p_44129_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44090_;
   }
}
