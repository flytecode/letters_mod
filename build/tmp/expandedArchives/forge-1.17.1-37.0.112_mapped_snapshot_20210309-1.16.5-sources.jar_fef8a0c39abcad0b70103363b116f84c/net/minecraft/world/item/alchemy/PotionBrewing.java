package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.Ingredient;

public class PotionBrewing {
   public static final int f_151252_ = 20;
   private static final List<PotionBrewing.Mix<Potion>> f_43494_ = Lists.newArrayList();
   private static final List<PotionBrewing.Mix<Item>> f_43495_ = Lists.newArrayList();
   private static final List<Ingredient> f_43496_ = Lists.newArrayList();
   private static final Predicate<ItemStack> f_43497_ = (p_43528_) -> {
      for(Ingredient ingredient : f_43496_) {
         if (ingredient.test(p_43528_)) {
            return true;
         }
      }

      return false;
   };

   public static boolean m_43506_(ItemStack p_43507_) {
      return m_43517_(p_43507_) || m_43522_(p_43507_);
   }

   protected static boolean m_43517_(ItemStack p_43518_) {
      int i = 0;

      for(int j = f_43495_.size(); i < j; ++i) {
         if ((f_43495_.get(i)).f_43533_.test(p_43518_)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean m_43522_(ItemStack p_43523_) {
      int i = 0;

      for(int j = f_43494_.size(); i < j; ++i) {
         if ((f_43494_.get(i)).f_43533_.test(p_43523_)) {
            return true;
         }
      }

      return false;
   }

   public static boolean m_43511_(Potion p_43512_) {
      int i = 0;

      for(int j = f_43494_.size(); i < j; ++i) {
         if ((f_43494_.get(i)).f_43534_.get() == p_43512_) {
            return true;
         }
      }

      return false;
   }

   public static boolean m_43508_(ItemStack p_43509_, ItemStack p_43510_) {
      if (!f_43497_.test(p_43509_)) {
         return false;
      } else {
         return m_43519_(p_43509_, p_43510_) || m_43524_(p_43509_, p_43510_);
      }
   }

   protected static boolean m_43519_(ItemStack p_43520_, ItemStack p_43521_) {
      Item item = p_43520_.m_41720_();
      int i = 0;

      for(int j = f_43495_.size(); i < j; ++i) {
         PotionBrewing.Mix<Item> mix = f_43495_.get(i);
         if (mix.f_43532_.get() == item && mix.f_43533_.test(p_43521_)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean m_43524_(ItemStack p_43525_, ItemStack p_43526_) {
      Potion potion = PotionUtils.m_43579_(p_43525_);
      int i = 0;

      for(int j = f_43494_.size(); i < j; ++i) {
         PotionBrewing.Mix<Potion> mix = f_43494_.get(i);
         if (mix.f_43532_.get() == potion && mix.f_43533_.test(p_43526_)) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack m_43529_(ItemStack p_43530_, ItemStack p_43531_) {
      if (!p_43531_.m_41619_()) {
         Potion potion = PotionUtils.m_43579_(p_43531_);
         Item item = p_43531_.m_41720_();
         int i = 0;

         for(int j = f_43495_.size(); i < j; ++i) {
            PotionBrewing.Mix<Item> mix = f_43495_.get(i);
            if (mix.f_43532_.get() == item && mix.f_43533_.test(p_43530_)) {
               return PotionUtils.m_43549_(new ItemStack(mix.f_43534_.get()), potion);
            }
         }

         i = 0;

         for(int k = f_43494_.size(); i < k; ++i) {
            PotionBrewing.Mix<Potion> mix1 = f_43494_.get(i);
            if (mix1.f_43532_.get() == potion && mix1.f_43533_.test(p_43530_)) {
               return PotionUtils.m_43549_(new ItemStack(item), mix1.f_43534_.get());
            }
         }
      }

      return p_43531_;
   }

   public static void m_43499_() {
      m_43500_(Items.f_42589_);
      m_43500_(Items.f_42736_);
      m_43500_(Items.f_42739_);
      m_43502_(Items.f_42589_, Items.f_42403_, Items.f_42736_);
      m_43502_(Items.f_42736_, Items.f_42735_, Items.f_42739_);
      m_43513_(Potions.f_43599_, Items.f_42546_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42586_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42648_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42593_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42591_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42501_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42542_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42525_, Potions.f_43601_);
      m_43513_(Potions.f_43599_, Items.f_42451_, Potions.f_43600_);
      m_43513_(Potions.f_43599_, Items.f_42588_, Potions.f_43602_);
      m_43513_(Potions.f_43602_, Items.f_42677_, Potions.f_43603_);
      m_43513_(Potions.f_43603_, Items.f_42451_, Potions.f_43604_);
      m_43513_(Potions.f_43603_, Items.f_42592_, Potions.f_43605_);
      m_43513_(Potions.f_43604_, Items.f_42592_, Potions.f_43606_);
      m_43513_(Potions.f_43605_, Items.f_42451_, Potions.f_43606_);
      m_43513_(Potions.f_43602_, Items.f_42542_, Potions.f_43610_);
      m_43513_(Potions.f_43610_, Items.f_42451_, Potions.f_43611_);
      m_43513_(Potions.f_43602_, Items.f_42648_, Potions.f_43607_);
      m_43513_(Potions.f_43607_, Items.f_42451_, Potions.f_43608_);
      m_43513_(Potions.f_43607_, Items.f_42525_, Potions.f_43609_);
      m_43513_(Potions.f_43607_, Items.f_42592_, Potions.f_43615_);
      m_43513_(Potions.f_43608_, Items.f_42592_, Potions.f_43616_);
      m_43513_(Potions.f_43615_, Items.f_42451_, Potions.f_43616_);
      m_43513_(Potions.f_43615_, Items.f_42525_, Potions.f_43617_);
      m_43513_(Potions.f_43602_, Items.f_42354_, Potions.f_43618_);
      m_43513_(Potions.f_43618_, Items.f_42451_, Potions.f_43619_);
      m_43513_(Potions.f_43618_, Items.f_42525_, Potions.f_43620_);
      m_43513_(Potions.f_43612_, Items.f_42592_, Potions.f_43615_);
      m_43513_(Potions.f_43613_, Items.f_42592_, Potions.f_43616_);
      m_43513_(Potions.f_43602_, Items.f_42501_, Potions.f_43612_);
      m_43513_(Potions.f_43612_, Items.f_42451_, Potions.f_43613_);
      m_43513_(Potions.f_43612_, Items.f_42525_, Potions.f_43614_);
      m_43513_(Potions.f_43602_, Items.f_42529_, Potions.f_43621_);
      m_43513_(Potions.f_43621_, Items.f_42451_, Potions.f_43622_);
      m_43513_(Potions.f_43602_, Items.f_42546_, Potions.f_43623_);
      m_43513_(Potions.f_43623_, Items.f_42525_, Potions.f_43581_);
      m_43513_(Potions.f_43623_, Items.f_42592_, Potions.f_43582_);
      m_43513_(Potions.f_43581_, Items.f_42592_, Potions.f_43583_);
      m_43513_(Potions.f_43582_, Items.f_42525_, Potions.f_43583_);
      m_43513_(Potions.f_43584_, Items.f_42592_, Potions.f_43582_);
      m_43513_(Potions.f_43585_, Items.f_42592_, Potions.f_43582_);
      m_43513_(Potions.f_43586_, Items.f_42592_, Potions.f_43583_);
      m_43513_(Potions.f_43602_, Items.f_42591_, Potions.f_43584_);
      m_43513_(Potions.f_43584_, Items.f_42451_, Potions.f_43585_);
      m_43513_(Potions.f_43584_, Items.f_42525_, Potions.f_43586_);
      m_43513_(Potions.f_43602_, Items.f_42586_, Potions.f_43587_);
      m_43513_(Potions.f_43587_, Items.f_42451_, Potions.f_43588_);
      m_43513_(Potions.f_43587_, Items.f_42525_, Potions.f_43589_);
      m_43513_(Potions.f_43602_, Items.f_42593_, Potions.f_43590_);
      m_43513_(Potions.f_43590_, Items.f_42451_, Potions.f_43591_);
      m_43513_(Potions.f_43590_, Items.f_42525_, Potions.f_43592_);
      m_43513_(Potions.f_43599_, Items.f_42592_, Potions.f_43593_);
      m_43513_(Potions.f_43593_, Items.f_42451_, Potions.f_43594_);
      m_43513_(Potions.f_43602_, Items.f_42714_, Potions.f_43596_);
      m_43513_(Potions.f_43596_, Items.f_42451_, Potions.f_43597_);
   }

   private static void m_43502_(Item p_43503_, Item p_43504_, Item p_43505_) {
      if (!(p_43503_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43503_));
      } else if (!(p_43505_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43505_));
      } else {
         f_43495_.add(new PotionBrewing.Mix<>(p_43503_, Ingredient.m_43929_(p_43504_), p_43505_));
      }
   }

   private static void m_43500_(Item p_43501_) {
      if (!(p_43501_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.f_122827_.m_7981_(p_43501_));
      } else {
         f_43496_.add(Ingredient.m_43929_(p_43501_));
      }
   }

   private static void m_43513_(Potion p_43514_, Item p_43515_, Potion p_43516_) {
      f_43494_.add(new PotionBrewing.Mix<>(p_43514_, Ingredient.m_43929_(p_43515_), p_43516_));
   }

   public static class Mix<T extends net.minecraftforge.registries.ForgeRegistryEntry<T>> {
      public final net.minecraftforge.registries.IRegistryDelegate<T> f_43532_;
      public final Ingredient f_43533_;
      public final net.minecraftforge.registries.IRegistryDelegate<T> f_43534_;

      public Mix(T p_43536_, Ingredient p_43537_, T p_43538_) {
         this.f_43532_ = p_43536_.delegate;
         this.f_43533_ = p_43537_;
         this.f_43534_ = p_43538_.delegate;
      }
   }
}
