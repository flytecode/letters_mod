package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkStarRecipe extends CustomRecipe {
   private static final Ingredient f_43876_ = Ingredient.m_43929_(Items.f_42613_, Items.f_42402_, Items.f_42587_, Items.f_42678_, Items.f_42679_, Items.f_42682_, Items.f_42680_, Items.f_42683_, Items.f_42681_);
   private static final Ingredient f_43877_ = Ingredient.m_43929_(Items.f_42415_);
   private static final Ingredient f_43878_ = Ingredient.m_43929_(Items.f_42525_);
   private static final Map<Item, FireworkRocketItem.Shape> f_43879_ = Util.m_137469_(Maps.newHashMap(), (p_43898_) -> {
      p_43898_.put(Items.f_42613_, FireworkRocketItem.Shape.LARGE_BALL);
      p_43898_.put(Items.f_42402_, FireworkRocketItem.Shape.BURST);
      p_43898_.put(Items.f_42587_, FireworkRocketItem.Shape.STAR);
      p_43898_.put(Items.f_42678_, FireworkRocketItem.Shape.CREEPER);
      p_43898_.put(Items.f_42679_, FireworkRocketItem.Shape.CREEPER);
      p_43898_.put(Items.f_42682_, FireworkRocketItem.Shape.CREEPER);
      p_43898_.put(Items.f_42680_, FireworkRocketItem.Shape.CREEPER);
      p_43898_.put(Items.f_42683_, FireworkRocketItem.Shape.CREEPER);
      p_43898_.put(Items.f_42681_, FireworkRocketItem.Shape.CREEPER);
   });
   private static final Ingredient f_43880_ = Ingredient.m_43929_(Items.f_42403_);

   public FireworkStarRecipe(ResourceLocation p_43883_) {
      super(p_43883_);
   }

   public boolean m_5818_(CraftingContainer p_43895_, Level p_43896_) {
      boolean flag = false;
      boolean flag1 = false;
      boolean flag2 = false;
      boolean flag3 = false;
      boolean flag4 = false;

      for(int i = 0; i < p_43895_.m_6643_(); ++i) {
         ItemStack itemstack = p_43895_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            if (f_43876_.test(itemstack)) {
               if (flag2) {
                  return false;
               }

               flag2 = true;
            } else if (f_43878_.test(itemstack)) {
               if (flag4) {
                  return false;
               }

               flag4 = true;
            } else if (f_43877_.test(itemstack)) {
               if (flag3) {
                  return false;
               }

               flag3 = true;
            } else if (f_43880_.test(itemstack)) {
               if (flag) {
                  return false;
               }

               flag = true;
            } else {
               if (!(itemstack.m_41720_() instanceof DyeItem)) {
                  return false;
               }

               flag1 = true;
            }
         }
      }

      return flag && flag1;
   }

   public ItemStack m_5874_(CraftingContainer p_43893_) {
      ItemStack itemstack = new ItemStack(Items.f_42689_);
      CompoundTag compoundtag = itemstack.m_41698_("Explosion");
      FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.SMALL_BALL;
      List<Integer> list = Lists.newArrayList();

      for(int i = 0; i < p_43893_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_43893_.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            if (f_43876_.test(itemstack1)) {
               fireworkrocketitem$shape = f_43879_.get(itemstack1.m_41720_());
            } else if (f_43878_.test(itemstack1)) {
               compoundtag.m_128379_("Flicker", true);
            } else if (f_43877_.test(itemstack1)) {
               compoundtag.m_128379_("Trail", true);
            } else if (itemstack1.m_41720_() instanceof DyeItem) {
               list.add(((DyeItem)itemstack1.m_41720_()).m_41089_().m_41070_());
            }
         }
      }

      compoundtag.m_128408_("Colors", list);
      compoundtag.m_128344_("Type", (byte)fireworkrocketitem$shape.m_41236_());
      return itemstack;
   }

   public boolean m_8004_(int p_43885_, int p_43886_) {
      return p_43885_ * p_43886_ >= 2;
   }

   public ItemStack m_8043_() {
      return new ItemStack(Items.f_42689_);
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44083_;
   }
}