package net.minecraft.world.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.Level;

public class BundleItem extends Item {
   private static final String f_150721_ = "Items";
   public static final int f_150720_ = 64;
   private static final int f_150722_ = 4;
   private static final int f_150723_ = Mth.m_14159_(0.4F, 0.4F, 1.0F);

   public BundleItem(Item.Properties p_150726_) {
      super(p_150726_);
   }

   public static float m_150766_(ItemStack p_150767_) {
      return (float)m_150778_(p_150767_) / 64.0F;
   }

   public boolean m_142207_(ItemStack p_150733_, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
      if (p_150735_ != ClickAction.SECONDARY) {
         return false;
      } else {
         ItemStack itemstack = p_150734_.m_7993_();
         if (itemstack.m_41619_()) {
            m_150780_(p_150733_).ifPresent((p_150740_) -> {
               m_150763_(p_150733_, p_150734_.m_150659_(p_150740_));
            });
         } else if (itemstack.m_41720_().m_142095_()) {
            int i = (64 - m_150778_(p_150733_)) / m_150776_(itemstack);
            m_150763_(p_150733_, p_150734_.m_150647_(itemstack.m_41613_(), i, p_150736_));
         }

         return true;
      }
   }

   public boolean m_142305_(ItemStack p_150742_, ItemStack p_150743_, Slot p_150744_, ClickAction p_150745_, Player p_150746_, SlotAccess p_150747_) {
      if (p_150745_ == ClickAction.SECONDARY && p_150744_.m_150651_(p_150746_)) {
         if (p_150743_.m_41619_()) {
            m_150780_(p_150742_).ifPresent(p_150747_::m_142104_);
         } else {
            p_150743_.m_41774_(m_150763_(p_150742_, p_150743_));
         }

         return true;
      } else {
         return false;
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_150760_, Player p_150761_, InteractionHand p_150762_) {
      ItemStack itemstack = p_150761_.m_21120_(p_150762_);
      if (m_150729_(itemstack, p_150761_)) {
         p_150761_.m_36246_(Stats.f_12982_.m_12902_(this));
         return InteractionResultHolder.m_19092_(itemstack, p_150760_.m_5776_());
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
      }
   }

   public boolean m_142522_(ItemStack p_150769_) {
      return m_150778_(p_150769_) > 0;
   }

   public int m_142158_(ItemStack p_150771_) {
      return Math.min(1 + 12 * m_150778_(p_150771_) / 64, 13);
   }

   public int m_142159_(ItemStack p_150773_) {
      return f_150723_;
   }

   private static int m_150763_(ItemStack p_150764_, ItemStack p_150765_) {
      if (!p_150765_.m_41619_() && p_150765_.m_41720_().m_142095_()) {
         CompoundTag compoundtag = p_150764_.m_41784_();
         if (!compoundtag.m_128441_("Items")) {
            compoundtag.m_128365_("Items", new ListTag());
         }

         int i = m_150778_(p_150764_);
         int j = m_150776_(p_150765_);
         int k = Math.min(p_150765_.m_41613_(), (64 - i) / j);
         if (k == 0) {
            return 0;
         } else {
            ListTag listtag = compoundtag.m_128437_("Items", 10);
            Optional<CompoundTag> optional = m_150756_(p_150765_, listtag);
            if (optional.isPresent()) {
               CompoundTag compoundtag1 = optional.get();
               ItemStack itemstack = ItemStack.m_41712_(compoundtag1);
               itemstack.m_41769_(k);
               itemstack.m_41739_(compoundtag1);
               listtag.remove(compoundtag1);
               listtag.add(0, (Tag)compoundtag1);
            } else {
               ItemStack itemstack1 = p_150765_.m_41777_();
               itemstack1.m_41764_(k);
               CompoundTag compoundtag2 = new CompoundTag();
               itemstack1.m_41739_(compoundtag2);
               listtag.add(0, (Tag)compoundtag2);
            }

            return k;
         }
      } else {
         return 0;
      }
   }

   private static Optional<CompoundTag> m_150756_(ItemStack p_150757_, ListTag p_150758_) {
      return p_150757_.m_150930_(Items.f_151058_) ? Optional.empty() : p_150758_.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((p_150755_) -> {
         return ItemStack.m_150942_(ItemStack.m_41712_(p_150755_), p_150757_);
      }).findFirst();
   }

   private static int m_150776_(ItemStack p_150777_) {
      if (p_150777_.m_150930_(Items.f_151058_)) {
         return 4 + m_150778_(p_150777_);
      } else {
         if ((p_150777_.m_150930_(Items.f_42786_) || p_150777_.m_150930_(Items.f_42785_)) && p_150777_.m_41782_()) {
            CompoundTag compoundtag = p_150777_.m_41737_("BlockEntityTag");
            if (compoundtag != null && !compoundtag.m_128437_("Bees", 10).isEmpty()) {
               return 64;
            }
         }

         return 64 / p_150777_.m_41741_();
      }
   }

   private static int m_150778_(ItemStack p_150779_) {
      return m_150782_(p_150779_).mapToInt((p_150785_) -> {
         return m_150776_(p_150785_) * p_150785_.m_41613_();
      }).sum();
   }

   private static Optional<ItemStack> m_150780_(ItemStack p_150781_) {
      CompoundTag compoundtag = p_150781_.m_41784_();
      if (!compoundtag.m_128441_("Items")) {
         return Optional.empty();
      } else {
         ListTag listtag = compoundtag.m_128437_("Items", 10);
         if (listtag.isEmpty()) {
            return Optional.empty();
         } else {
            int i = 0;
            CompoundTag compoundtag1 = listtag.m_128728_(0);
            ItemStack itemstack = ItemStack.m_41712_(compoundtag1);
            listtag.remove(0);
            if (listtag.isEmpty()) {
               p_150781_.m_41749_("Items");
            }

            return Optional.of(itemstack);
         }
      }
   }

   private static boolean m_150729_(ItemStack p_150730_, Player p_150731_) {
      CompoundTag compoundtag = p_150730_.m_41784_();
      if (!compoundtag.m_128441_("Items")) {
         return false;
      } else {
         if (p_150731_ instanceof ServerPlayer) {
            ListTag listtag = compoundtag.m_128437_("Items", 10);

            for(int i = 0; i < listtag.size(); ++i) {
               CompoundTag compoundtag1 = listtag.m_128728_(i);
               ItemStack itemstack = ItemStack.m_41712_(compoundtag1);
               p_150731_.m_36176_(itemstack, true);
            }
         }

         p_150730_.m_41749_("Items");
         return true;
      }
   }

   private static Stream<ItemStack> m_150782_(ItemStack p_150783_) {
      CompoundTag compoundtag = p_150783_.m_41783_();
      if (compoundtag == null) {
         return Stream.empty();
      } else {
         ListTag listtag = compoundtag.m_128437_("Items", 10);
         return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::m_41712_);
      }
   }

   public Optional<TooltipComponent> m_142422_(ItemStack p_150775_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122779_();
      m_150782_(p_150775_).forEach(nonnulllist::add);
      return Optional.of(new BundleTooltip(nonnulllist, m_150778_(p_150775_)));
   }

   public void m_7373_(ItemStack p_150749_, Level p_150750_, List<Component> p_150751_, TooltipFlag p_150752_) {
      p_150751_.add((new TranslatableComponent("item.minecraft.bundle.fullness", m_150778_(p_150749_), 64)).m_130940_(ChatFormatting.GRAY));
   }

   public void m_142023_(ItemEntity p_150728_) {
      ItemUtils.m_150952_(p_150728_, m_150782_(p_150728_.m_32055_()));
   }
}