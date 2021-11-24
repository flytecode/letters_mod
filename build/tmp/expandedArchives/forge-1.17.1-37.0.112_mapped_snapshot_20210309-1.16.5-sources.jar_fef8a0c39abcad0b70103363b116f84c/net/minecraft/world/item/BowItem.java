package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class BowItem extends ProjectileWeaponItem implements Vanishable {
   public static final int f_150704_ = 20;
   public static final int f_150705_ = 15;

   public BowItem(Item.Properties p_40660_) {
      super(p_40660_);
   }

   public void m_5551_(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
      if (p_40669_ instanceof Player) {
         Player player = (Player)p_40669_;
         boolean flag = player.m_150110_().f_35937_ || EnchantmentHelper.m_44843_(Enchantments.f_44952_, p_40667_) > 0;
         ItemStack itemstack = player.m_6298_(p_40667_);

         int i = this.m_8105_(p_40667_) - p_40670_;
         i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.m_41619_() || flag);
         if (i < 0) return;

         if (!itemstack.m_41619_() || flag) {
            if (itemstack.m_41619_()) {
               itemstack = new ItemStack(Items.f_42412_);
            }

            float f = m_40661_(i);
            if (!((double)f < 0.1D)) {
               boolean flag1 = player.m_150110_().f_35937_ || (itemstack.m_41720_() instanceof ArrowItem && ((ArrowItem)itemstack.m_41720_()).isInfinite(itemstack, p_40667_, player));
               if (!p_40668_.f_46443_) {
                  ArrowItem arrowitem = (ArrowItem)(itemstack.m_41720_() instanceof ArrowItem ? itemstack.m_41720_() : Items.f_42412_);
                  AbstractArrow abstractarrow = arrowitem.m_6394_(p_40668_, itemstack, player);
                  abstractarrow = customArrow(abstractarrow);
                  abstractarrow.m_37251_(player, player.m_146909_(), player.m_146908_(), 0.0F, f * 3.0F, 1.0F);
                  if (f == 1.0F) {
                     abstractarrow.m_36762_(true);
                  }

                  int j = EnchantmentHelper.m_44843_(Enchantments.f_44988_, p_40667_);
                  if (j > 0) {
                     abstractarrow.m_36781_(abstractarrow.m_36789_() + (double)j * 0.5D + 0.5D);
                  }

                  int k = EnchantmentHelper.m_44843_(Enchantments.f_44989_, p_40667_);
                  if (k > 0) {
                     abstractarrow.m_36735_(k);
                  }

                  if (EnchantmentHelper.m_44843_(Enchantments.f_44990_, p_40667_) > 0) {
                     abstractarrow.m_20254_(100);
                  }

                  p_40667_.m_41622_(1, player, (p_40665_) -> {
                     p_40665_.m_21190_(player.m_7655_());
                  });
                  if (flag1 || player.m_150110_().f_35937_ && (itemstack.m_150930_(Items.f_42737_) || itemstack.m_150930_(Items.f_42738_))) {
                     abstractarrow.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
                  }

                  p_40668_.m_7967_(abstractarrow);
               }

               p_40668_.m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11687_, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.m_5822_().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
               if (!flag1 && !player.m_150110_().f_35937_) {
                  itemstack.m_41774_(1);
                  if (itemstack.m_41619_()) {
                     player.m_150109_().m_36057_(itemstack);
                  }
               }

               player.m_36246_(Stats.f_12982_.m_12902_(this));
            }
         }
      }
   }

   public static float m_40661_(int p_40662_) {
      float f = (float)p_40662_ / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   public int m_8105_(ItemStack p_40680_) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack p_40678_) {
      return UseAnim.BOW;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
      ItemStack itemstack = p_40673_.m_21120_(p_40674_);
      boolean flag = !p_40673_.m_6298_(itemstack).m_41619_();

      InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, flag);
      if (ret != null) return ret;

      if (!p_40673_.m_150110_().f_35937_ && !flag) {
         return InteractionResultHolder.m_19100_(itemstack);
      } else {
         p_40673_.m_6672_(p_40674_);
         return InteractionResultHolder.m_19096_(itemstack);
      }
   }

   public Predicate<ItemStack> m_6437_() {
      return f_43005_;
   }

   public AbstractArrow customArrow(AbstractArrow arrow) {
      return arrow;
   }

   public int m_6615_() {
      return 15;
   }
}
