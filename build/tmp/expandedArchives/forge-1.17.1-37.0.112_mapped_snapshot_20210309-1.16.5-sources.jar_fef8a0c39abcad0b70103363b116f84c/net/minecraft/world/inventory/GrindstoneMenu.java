package net.minecraft.world.inventory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class GrindstoneMenu extends AbstractContainerMenu {
   public static final int f_150565_ = 35;
   public static final int f_150566_ = 0;
   public static final int f_150567_ = 1;
   public static final int f_150568_ = 2;
   private static final int f_150569_ = 3;
   private static final int f_150570_ = 30;
   private static final int f_150571_ = 30;
   private static final int f_150572_ = 39;
   private final Container f_39559_ = new ResultContainer();
   final Container f_39560_ = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         GrindstoneMenu.this.m_6199_(this);
      }
   };
   private final ContainerLevelAccess f_39561_;

   public GrindstoneMenu(int p_39563_, Inventory p_39564_) {
      this(p_39563_, p_39564_, ContainerLevelAccess.f_39287_);
   }

   public GrindstoneMenu(int p_39566_, Inventory p_39567_, final ContainerLevelAccess p_39568_) {
      super(MenuType.f_39971_, p_39566_);
      this.f_39561_ = p_39568_;
      this.m_38897_(new Slot(this.f_39560_, 0, 49, 19) {
         public boolean m_5857_(ItemStack p_39607_) {
            return p_39607_.m_41763_() || p_39607_.m_150930_(Items.f_42690_) || p_39607_.m_41793_();
         }
      });
      this.m_38897_(new Slot(this.f_39560_, 1, 49, 40) {
         public boolean m_5857_(ItemStack p_39616_) {
            return p_39616_.m_41763_() || p_39616_.m_150930_(Items.f_42690_) || p_39616_.m_41793_();
         }
      });
      this.m_38897_(new Slot(this.f_39559_, 2, 129, 34) {
         public boolean m_5857_(ItemStack p_39630_) {
            return false;
         }

         public void m_142406_(Player p_150574_, ItemStack p_150575_) {
            p_39568_.m_39292_((p_39634_, p_39635_) -> {
               if (p_39634_ instanceof ServerLevel) {
                  ExperienceOrb.m_147082_((ServerLevel)p_39634_, Vec3.m_82512_(p_39635_), this.m_39631_(p_39634_));
               }

               p_39634_.m_46796_(1042, p_39635_, 0);
            });
            GrindstoneMenu.this.f_39560_.m_6836_(0, ItemStack.f_41583_);
            GrindstoneMenu.this.f_39560_.m_6836_(1, ItemStack.f_41583_);
         }

         private int m_39631_(Level p_39632_) {
            int l = 0;
            l = l + this.m_39636_(GrindstoneMenu.this.f_39560_.m_8020_(0));
            l = l + this.m_39636_(GrindstoneMenu.this.f_39560_.m_8020_(1));
            if (l > 0) {
               int i1 = (int)Math.ceil((double)l / 2.0D);
               return i1 + p_39632_.f_46441_.nextInt(i1);
            } else {
               return 0;
            }
         }

         private int m_39636_(ItemStack p_39637_) {
            int l = 0;
            Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(p_39637_);

            for(Entry<Enchantment, Integer> entry : map.entrySet()) {
               Enchantment enchantment = entry.getKey();
               Integer integer = entry.getValue();
               if (!enchantment.m_6589_()) {
                  l += enchantment.m_6183_(integer);
               }
            }

            return l;
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39567_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39567_, k, 8 + k * 18, 142));
      }

   }

   public void m_6199_(Container p_39570_) {
      super.m_6199_(p_39570_);
      if (p_39570_ == this.f_39560_) {
         this.m_39593_();
      }

   }

   private void m_39593_() {
      ItemStack itemstack = this.f_39560_.m_8020_(0);
      ItemStack itemstack1 = this.f_39560_.m_8020_(1);
      boolean flag = !itemstack.m_41619_() || !itemstack1.m_41619_();
      boolean flag1 = !itemstack.m_41619_() && !itemstack1.m_41619_();
      if (!flag) {
         this.f_39559_.m_6836_(0, ItemStack.f_41583_);
      } else {
         boolean flag2 = !itemstack.m_41619_() && !itemstack.m_150930_(Items.f_42690_) && !itemstack.m_41793_() || !itemstack1.m_41619_() && !itemstack1.m_150930_(Items.f_42690_) && !itemstack1.m_41793_();
         if (itemstack.m_41613_() > 1 || itemstack1.m_41613_() > 1 || !flag1 && flag2) {
            this.f_39559_.m_6836_(0, ItemStack.f_41583_);
            this.m_38946_();
            return;
         }

         int j = 1;
         int i;
         ItemStack itemstack2;
         if (flag1) {
            if (!itemstack.m_150930_(itemstack1.m_41720_())) {
               this.f_39559_.m_6836_(0, ItemStack.f_41583_);
               this.m_38946_();
               return;
            }

            Item item = itemstack.m_41720_();
            int k = itemstack.m_41776_() - itemstack.m_41773_();
            int l = itemstack.m_41776_() - itemstack1.m_41773_();
            int i1 = k + l + itemstack.m_41776_() * 5 / 100;
            i = Math.max(itemstack.m_41776_() - i1, 0);
            itemstack2 = this.m_39590_(itemstack, itemstack1);
            if (!itemstack2.isRepairable()) i = itemstack.m_41773_();
            if (!itemstack2.m_41763_() || !itemstack2.isRepairable()) {
               if (!ItemStack.m_41728_(itemstack, itemstack1)) {
                  this.f_39559_.m_6836_(0, ItemStack.f_41583_);
                  this.m_38946_();
                  return;
               }

               j = 2;
            }
         } else {
            boolean flag3 = !itemstack.m_41619_();
            i = flag3 ? itemstack.m_41773_() : itemstack1.m_41773_();
            itemstack2 = flag3 ? itemstack : itemstack1;
         }

         this.f_39559_.m_6836_(0, this.m_39579_(itemstack2, i, j));
      }

      this.m_38946_();
   }

   private ItemStack m_39590_(ItemStack p_39591_, ItemStack p_39592_) {
      ItemStack itemstack = p_39591_.m_41777_();
      Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(p_39592_);

      for(Entry<Enchantment, Integer> entry : map.entrySet()) {
         Enchantment enchantment = entry.getKey();
         if (!enchantment.m_6589_() || EnchantmentHelper.m_44843_(enchantment, itemstack) == 0) {
            itemstack.m_41663_(enchantment, entry.getValue());
         }
      }

      return itemstack;
   }

   private ItemStack m_39579_(ItemStack p_39580_, int p_39581_, int p_39582_) {
      ItemStack itemstack = p_39580_.m_41777_();
      itemstack.m_41749_("Enchantments");
      itemstack.m_41749_("StoredEnchantments");
      if (p_39581_ > 0) {
         itemstack.m_41721_(p_39581_);
      } else {
         itemstack.m_41749_("Damage");
      }

      itemstack.m_41764_(p_39582_);
      Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(p_39580_).entrySet().stream().filter((p_39584_) -> {
         return p_39584_.getKey().m_6589_();
      }).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentHelper.m_44865_(map, itemstack);
      itemstack.m_41742_(0);
      if (itemstack.m_150930_(Items.f_42690_) && map.size() == 0) {
         itemstack = new ItemStack(Items.f_42517_);
         if (p_39580_.m_41788_()) {
            itemstack.m_41714_(p_39580_.m_41786_());
         }
      }

      for(int i = 0; i < map.size(); ++i) {
         itemstack.m_41742_(AnvilMenu.m_39025_(itemstack.m_41610_()));
      }

      return itemstack;
   }

   public void m_6877_(Player p_39586_) {
      super.m_6877_(p_39586_);
      this.f_39561_.m_39292_((p_39575_, p_39576_) -> {
         this.m_150411_(p_39586_, this.f_39560_);
      });
   }

   public boolean m_6875_(Player p_39572_) {
      return m_38889_(this.f_39561_, p_39572_, Blocks.f_50623_);
   }

   public ItemStack m_7648_(Player p_39588_, int p_39589_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39589_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         ItemStack itemstack2 = this.f_39560_.m_8020_(0);
         ItemStack itemstack3 = this.f_39560_.m_8020_(1);
         if (p_39589_ == 2) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39589_ != 0 && p_39589_ != 1) {
            if (!itemstack2.m_41619_() && !itemstack3.m_41619_()) {
               if (p_39589_ >= 3 && p_39589_ < 30) {
                  if (!this.m_38903_(itemstack1, 30, 39, false)) {
                     return ItemStack.f_41583_;
                  }
               } else if (p_39589_ >= 30 && p_39589_ < 39 && !this.m_38903_(itemstack1, 3, 30, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, 0, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 3, 39, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_39588_, itemstack1);
      }

      return itemstack;
   }
}
