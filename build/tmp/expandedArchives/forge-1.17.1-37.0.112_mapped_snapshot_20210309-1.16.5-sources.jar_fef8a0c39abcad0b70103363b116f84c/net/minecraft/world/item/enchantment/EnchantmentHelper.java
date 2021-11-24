package net.minecraft.world.item.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
   private static final String f_182430_ = "id";
   private static final String f_182431_ = "lvl";

   public static CompoundTag m_182443_(@Nullable ResourceLocation p_182444_, int p_182445_) {
      CompoundTag compoundtag = new CompoundTag();
      compoundtag.m_128359_("id", String.valueOf((Object)p_182444_));
      compoundtag.m_128376_("lvl", (short)p_182445_);
      return compoundtag;
   }

   public static void m_182440_(CompoundTag p_182441_, int p_182442_) {
      p_182441_.m_128376_("lvl", (short)p_182442_);
   }

   public static int m_182438_(CompoundTag p_182439_) {
      return Mth.m_14045_(p_182439_.m_128451_("lvl"), 0, 255);
   }

   @Nullable
   public static ResourceLocation m_182446_(CompoundTag p_182447_) {
      return ResourceLocation.m_135820_(p_182447_.m_128461_("id"));
   }

   @Nullable
   public static ResourceLocation m_182432_(Enchantment p_182433_) {
      return Registry.f_122825_.m_7981_(p_182433_);
   }

   public static int m_44843_(Enchantment p_44844_, ItemStack p_44845_) {
      if (p_44845_.m_41619_()) {
         return 0;
      } else {
         ResourceLocation resourcelocation = m_182432_(p_44844_);
         ListTag listtag = p_44845_.m_41785_();

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            ResourceLocation resourcelocation1 = m_182446_(compoundtag);
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
               return m_182438_(compoundtag);
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> m_44831_(ItemStack p_44832_) {
      ListTag listtag = p_44832_.m_150930_(Items.f_42690_) ? EnchantedBookItem.m_41163_(p_44832_) : p_44832_.m_41785_();
      return m_44882_(listtag);
   }

   public static Map<Enchantment, Integer> m_44882_(ListTag p_44883_) {
      Map<Enchantment, Integer> map = Maps.newLinkedHashMap();

      for(int i = 0; i < p_44883_.size(); ++i) {
         CompoundTag compoundtag = p_44883_.m_128728_(i);
         Registry.f_122825_.m_6612_(m_182446_(compoundtag)).ifPresent((p_44871_) -> {
            map.put(p_44871_, m_182438_(compoundtag));
         });
      }

      return map;
   }

   public static void m_44865_(Map<Enchantment, Integer> p_44866_, ItemStack p_44867_) {
      ListTag listtag = new ListTag();

      for(Entry<Enchantment, Integer> entry : p_44866_.entrySet()) {
         Enchantment enchantment = entry.getKey();
         if (enchantment != null) {
            int i = entry.getValue();
            listtag.add(m_182443_(m_182432_(enchantment), i));
            if (p_44867_.m_150930_(Items.f_42690_)) {
               EnchantedBookItem.m_41153_(p_44867_, new EnchantmentInstance(enchantment, i));
            }
         }
      }

      if (listtag.isEmpty()) {
         p_44867_.m_41749_("Enchantments");
      } else if (!p_44867_.m_150930_(Items.f_42690_)) {
         p_44867_.m_41700_("Enchantments", listtag);
      }

   }

   private static void m_44850_(EnchantmentHelper.EnchantmentVisitor p_44851_, ItemStack p_44852_) {
      if (!p_44852_.m_41619_()) {
         ListTag listtag = p_44852_.m_41785_();

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            Registry.f_122825_.m_6612_(m_182446_(compoundtag)).ifPresent((p_182437_) -> {
               p_44851_.m_44944_(p_182437_, m_182438_(compoundtag));
            });
         }

      }
   }

   private static void m_44853_(EnchantmentHelper.EnchantmentVisitor p_44854_, Iterable<ItemStack> p_44855_) {
      for(ItemStack itemstack : p_44855_) {
         m_44850_(p_44854_, itemstack);
      }

   }

   public static int m_44856_(Iterable<ItemStack> p_44857_, DamageSource p_44858_) {
      MutableInt mutableint = new MutableInt();
      m_44853_((p_44892_, p_44893_) -> {
         mutableint.add(p_44892_.m_7205_(p_44893_, p_44858_));
      }, p_44857_);
      return mutableint.intValue();
   }

   public static float m_44833_(ItemStack p_44834_, MobType p_44835_) {
      MutableFloat mutablefloat = new MutableFloat();
      m_44850_((p_44887_, p_44888_) -> {
         mutablefloat.add(p_44887_.m_7335_(p_44888_, p_44835_));
      }, p_44834_);
      return mutablefloat.floatValue();
   }

   public static float m_44821_(LivingEntity p_44822_) {
      int i = m_44836_(Enchantments.f_44983_, p_44822_);
      return i > 0 ? SweepingEdgeEnchantment.m_45193_(i) : 0.0F;
   }

   public static void m_44823_(LivingEntity p_44824_, Entity p_44825_) {
      EnchantmentHelper.EnchantmentVisitor enchantmenthelper$enchantmentvisitor = (p_44902_, p_44903_) -> {
         p_44902_.m_7675_(p_44824_, p_44825_, p_44903_);
      };
      if (p_44824_ != null) {
         m_44853_(enchantmenthelper$enchantmentvisitor, p_44824_.m_20158_());
      }

      if (p_44825_ instanceof Player) {
         m_44850_(enchantmenthelper$enchantmentvisitor, p_44824_.m_21205_());
      }

   }

   public static void m_44896_(LivingEntity p_44897_, Entity p_44898_) {
      EnchantmentHelper.EnchantmentVisitor enchantmenthelper$enchantmentvisitor = (p_44829_, p_44830_) -> {
         p_44829_.m_7677_(p_44897_, p_44898_, p_44830_);
      };
      if (p_44897_ != null) {
         m_44853_(enchantmenthelper$enchantmentvisitor, p_44897_.m_20158_());
      }

      if (p_44897_ instanceof Player) {
         m_44850_(enchantmenthelper$enchantmentvisitor, p_44897_.m_21205_());
      }

   }

   public static int m_44836_(Enchantment p_44837_, LivingEntity p_44838_) {
      Iterable<ItemStack> iterable = p_44837_.m_44684_(p_44838_).values();
      if (iterable == null) {
         return 0;
      } else {
         int i = 0;

         for(ItemStack itemstack : iterable) {
            int j = m_44843_(p_44837_, itemstack);
            if (j > i) {
               i = j;
            }
         }

         return i;
      }
   }

   public static int m_44894_(LivingEntity p_44895_) {
      return m_44836_(Enchantments.f_44980_, p_44895_);
   }

   public static int m_44914_(LivingEntity p_44915_) {
      return m_44836_(Enchantments.f_44981_, p_44915_);
   }

   public static int m_44918_(LivingEntity p_44919_) {
      return m_44836_(Enchantments.f_44970_, p_44919_);
   }

   public static int m_44922_(LivingEntity p_44923_) {
      return m_44836_(Enchantments.f_44973_, p_44923_);
   }

   public static int m_44926_(LivingEntity p_44927_) {
      return m_44836_(Enchantments.f_44984_, p_44927_);
   }

   public static int m_44904_(ItemStack p_44905_) {
      return m_44843_(Enchantments.f_44953_, p_44905_);
   }

   public static int m_44916_(ItemStack p_44917_) {
      return m_44843_(Enchantments.f_44954_, p_44917_);
   }

   public static int m_44930_(LivingEntity p_44931_) {
      return m_44836_(Enchantments.f_44982_, p_44931_);
   }

   public static boolean m_44934_(LivingEntity p_44935_) {
      return m_44836_(Enchantments.f_44971_, p_44935_) > 0;
   }

   public static boolean m_44938_(LivingEntity p_44939_) {
      return m_44836_(Enchantments.f_44974_, p_44939_) > 0;
   }

   public static boolean m_44942_(LivingEntity p_44943_) {
      return m_44836_(Enchantments.f_44976_, p_44943_) > 0;
   }

   public static boolean m_44920_(ItemStack p_44921_) {
      return m_44843_(Enchantments.f_44975_, p_44921_) > 0;
   }

   public static boolean m_44924_(ItemStack p_44925_) {
      return m_44843_(Enchantments.f_44963_, p_44925_) > 0;
   }

   public static int m_44928_(ItemStack p_44929_) {
      return m_44843_(Enchantments.f_44955_, p_44929_);
   }

   public static int m_44932_(ItemStack p_44933_) {
      return m_44843_(Enchantments.f_44957_, p_44933_);
   }

   public static boolean m_44936_(ItemStack p_44937_) {
      return m_44843_(Enchantments.f_44958_, p_44937_) > 0;
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> m_44906_(Enchantment p_44907_, LivingEntity p_44908_) {
      return m_44839_(p_44907_, p_44908_, (p_44941_) -> {
         return true;
      });
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> m_44839_(Enchantment p_44840_, LivingEntity p_44841_, Predicate<ItemStack> p_44842_) {
      Map<EquipmentSlot, ItemStack> map = p_44840_.m_44684_(p_44841_);
      if (map.isEmpty()) {
         return null;
      } else {
         List<Entry<EquipmentSlot, ItemStack>> list = Lists.newArrayList();

         for(Entry<EquipmentSlot, ItemStack> entry : map.entrySet()) {
            ItemStack itemstack = entry.getValue();
            if (!itemstack.m_41619_() && m_44843_(p_44840_, itemstack) > 0 && p_44842_.test(itemstack)) {
               list.add(entry);
            }
         }

         return list.isEmpty() ? null : list.get(p_44841_.m_21187_().nextInt(list.size()));
      }
   }

   public static int m_44872_(Random p_44873_, int p_44874_, int p_44875_, ItemStack p_44876_) {
      Item item = p_44876_.m_41720_();
      int i = p_44876_.getItemEnchantability();
      if (i <= 0) {
         return 0;
      } else {
         if (p_44875_ > 15) {
            p_44875_ = 15;
         }

         int j = p_44873_.nextInt(8) + 1 + (p_44875_ >> 1) + p_44873_.nextInt(p_44875_ + 1);
         if (p_44874_ == 0) {
            return Math.max(j / 3, 1);
         } else {
            return p_44874_ == 1 ? j * 2 / 3 + 1 : Math.max(j, p_44875_ * 2);
         }
      }
   }

   public static ItemStack m_44877_(Random p_44878_, ItemStack p_44879_, int p_44880_, boolean p_44881_) {
      List<EnchantmentInstance> list = m_44909_(p_44878_, p_44879_, p_44880_, p_44881_);
      boolean flag = p_44879_.m_150930_(Items.f_42517_);
      if (flag) {
         p_44879_ = new ItemStack(Items.f_42690_);
      }

      for(EnchantmentInstance enchantmentinstance : list) {
         if (flag) {
            EnchantedBookItem.m_41153_(p_44879_, enchantmentinstance);
         } else {
            p_44879_.m_41663_(enchantmentinstance.f_44947_, enchantmentinstance.f_44948_);
         }
      }

      return p_44879_;
   }

   public static List<EnchantmentInstance> m_44909_(Random p_44910_, ItemStack p_44911_, int p_44912_, boolean p_44913_) {
      List<EnchantmentInstance> list = Lists.newArrayList();
      Item item = p_44911_.m_41720_();
      int i = p_44911_.getItemEnchantability();
      if (i <= 0) {
         return list;
      } else {
         p_44912_ = p_44912_ + 1 + p_44910_.nextInt(i / 4 + 1) + p_44910_.nextInt(i / 4 + 1);
         float f = (p_44910_.nextFloat() + p_44910_.nextFloat() - 1.0F) * 0.15F;
         p_44912_ = Mth.m_14045_(Math.round((float)p_44912_ + (float)p_44912_ * f), 1, Integer.MAX_VALUE);
         List<EnchantmentInstance> list1 = m_44817_(p_44912_, p_44911_, p_44913_);
         if (!list1.isEmpty()) {
            WeightedRandom.m_146317_(p_44910_, list1).ifPresent(list::add);

            while(p_44910_.nextInt(50) <= p_44912_) {
               if (!list.isEmpty()) {
                  m_44862_(list1, Util.m_137509_(list));
               }

               if (list1.isEmpty()) {
                  break;
               }

               WeightedRandom.m_146317_(p_44910_, list1).ifPresent(list::add);
               p_44912_ /= 2;
            }
         }

         return list;
      }
   }

   public static void m_44862_(List<EnchantmentInstance> p_44863_, EnchantmentInstance p_44864_) {
      Iterator<EnchantmentInstance> iterator = p_44863_.iterator();

      while(iterator.hasNext()) {
         if (!p_44864_.f_44947_.m_44695_((iterator.next()).f_44947_)) {
            iterator.remove();
         }
      }

   }

   public static boolean m_44859_(Collection<Enchantment> p_44860_, Enchantment p_44861_) {
      for(Enchantment enchantment : p_44860_) {
         if (!enchantment.m_44695_(p_44861_)) {
            return false;
         }
      }

      return true;
   }

   public static List<EnchantmentInstance> m_44817_(int p_44818_, ItemStack p_44819_, boolean p_44820_) {
      List<EnchantmentInstance> list = Lists.newArrayList();
      Item item = p_44819_.m_41720_();
      boolean flag = p_44819_.m_150930_(Items.f_42517_);

      for(Enchantment enchantment : Registry.f_122825_) {
         if ((!enchantment.m_6591_() || p_44820_) && enchantment.m_6592_() && (enchantment.canApplyAtEnchantingTable(p_44819_) || (flag && enchantment.isAllowedOnBooks()))) {
            for(int i = enchantment.m_6586_(); i > enchantment.m_44702_() - 1; --i) {
               if (p_44818_ >= enchantment.m_6183_(i) && p_44818_ <= enchantment.m_6175_(i)) {
                  list.add(new EnchantmentInstance(enchantment, i));
                  break;
               }
            }
         }
      }

      return list;
   }

   @FunctionalInterface
   interface EnchantmentVisitor {
      void m_44944_(Enchantment p_44945_, int p_44946_);
   }
}
