package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkRocketRecipe extends CustomRecipe {
   private static final Ingredient f_43837_ = Ingredient.m_43929_(Items.f_42516_);
   private static final Ingredient f_43838_ = Ingredient.m_43929_(Items.f_42403_);
   private static final Ingredient f_43839_ = Ingredient.m_43929_(Items.f_42689_);

   public FireworkRocketRecipe(ResourceLocation p_43842_) {
      super(p_43842_);
   }

   public boolean m_5818_(CraftingContainer p_43854_, Level p_43855_) {
      boolean flag = false;
      int i = 0;

      for(int j = 0; j < p_43854_.m_6643_(); ++j) {
         ItemStack itemstack = p_43854_.m_8020_(j);
         if (!itemstack.m_41619_()) {
            if (f_43837_.test(itemstack)) {
               if (flag) {
                  return false;
               }

               flag = true;
            } else if (f_43838_.test(itemstack)) {
               ++i;
               if (i > 3) {
                  return false;
               }
            } else if (!f_43839_.test(itemstack)) {
               return false;
            }
         }
      }

      return flag && i >= 1;
   }

   public ItemStack m_5874_(CraftingContainer p_43852_) {
      ItemStack itemstack = new ItemStack(Items.f_42688_, 3);
      CompoundTag compoundtag = itemstack.m_41698_("Fireworks");
      ListTag listtag = new ListTag();
      int i = 0;

      for(int j = 0; j < p_43852_.m_6643_(); ++j) {
         ItemStack itemstack1 = p_43852_.m_8020_(j);
         if (!itemstack1.m_41619_()) {
            if (f_43838_.test(itemstack1)) {
               ++i;
            } else if (f_43839_.test(itemstack1)) {
               CompoundTag compoundtag1 = itemstack1.m_41737_("Explosion");
               if (compoundtag1 != null) {
                  listtag.add(compoundtag1);
               }
            }
         }
      }

      compoundtag.m_128344_("Flight", (byte)i);
      if (!listtag.isEmpty()) {
         compoundtag.m_128365_("Explosions", listtag);
      }

      return itemstack;
   }

   public boolean m_8004_(int p_43844_, int p_43845_) {
      return p_43844_ * p_43845_ >= 2;
   }

   public ItemStack m_8043_() {
      return new ItemStack(Items.f_42688_);
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44082_;
   }
}