package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class InventoryMenu extends RecipeBookMenu<CraftingContainer> {
   public static final int f_150579_ = 0;
   public static final int f_150580_ = 0;
   public static final int f_150581_ = 1;
   public static final int f_150582_ = 5;
   public static final int f_150583_ = 5;
   public static final int f_150584_ = 9;
   public static final int f_150585_ = 9;
   public static final int f_150586_ = 36;
   public static final int f_150587_ = 36;
   public static final int f_150588_ = 45;
   public static final int f_150589_ = 45;
   public static final ResourceLocation f_39692_ = new ResourceLocation("textures/atlas/blocks.png");
   public static final ResourceLocation f_39693_ = new ResourceLocation("item/empty_armor_slot_helmet");
   public static final ResourceLocation f_39694_ = new ResourceLocation("item/empty_armor_slot_chestplate");
   public static final ResourceLocation f_39695_ = new ResourceLocation("item/empty_armor_slot_leggings");
   public static final ResourceLocation f_39696_ = new ResourceLocation("item/empty_armor_slot_boots");
   public static final ResourceLocation f_39697_ = new ResourceLocation("item/empty_armor_slot_shield");
   static final ResourceLocation[] f_39699_ = new ResourceLocation[]{f_39696_, f_39695_, f_39694_, f_39693_};
   private static final EquipmentSlot[] f_39700_ = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   private final CraftingContainer f_39701_ = new CraftingContainer(this, 2, 2);
   private final ResultContainer f_39702_ = new ResultContainer();
   public final boolean f_39698_;
   private final Player f_39703_;

   public InventoryMenu(Inventory p_39706_, boolean p_39707_, Player p_39708_) {
      super((MenuType<?>)null, 0);
      this.f_39698_ = p_39707_;
      this.f_39703_ = p_39708_;
      this.m_38897_(new ResultSlot(p_39706_.f_35978_, this.f_39701_, this.f_39702_, 0, 154, 28));

      for(int i = 0; i < 2; ++i) {
         for(int j = 0; j < 2; ++j) {
            this.m_38897_(new Slot(this.f_39701_, j + i * 2, 98 + j * 18, 18 + i * 18));
         }
      }

      for(int k = 0; k < 4; ++k) {
         final EquipmentSlot equipmentslot = f_39700_[k];
         this.m_38897_(new Slot(p_39706_, 39 - k, 8, 8 + k * 18) {
            public int m_6641_() {
               return 1;
            }

            public boolean m_5857_(ItemStack p_39746_) {
               return p_39746_.canEquip(equipmentslot, f_39703_);
            }

            public boolean m_8010_(Player p_39744_) {
               ItemStack itemstack = this.m_7993_();
               return !itemstack.m_41619_() && !p_39744_.m_7500_() && EnchantmentHelper.m_44920_(itemstack) ? false : super.m_8010_(p_39744_);
            }

            public Pair<ResourceLocation, ResourceLocation> m_7543_() {
               return Pair.of(InventoryMenu.f_39692_, InventoryMenu.f_39699_[equipmentslot.m_20749_()]);
            }
         });
      }

      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot(p_39706_, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.m_38897_(new Slot(p_39706_, i1, 8 + i1 * 18, 142));
      }

      this.m_38897_(new Slot(p_39706_, 40, 77, 62) {
         public Pair<ResourceLocation, ResourceLocation> m_7543_() {
            return Pair.of(InventoryMenu.f_39692_, InventoryMenu.f_39697_);
         }
      });
   }

   public static boolean m_150592_(int p_150593_) {
      return p_150593_ >= 36 && p_150593_ < 45 || p_150593_ == 45;
   }

   public void m_5816_(StackedContents p_39714_) {
      this.f_39701_.m_5809_(p_39714_);
   }

   public void m_6650_() {
      this.f_39702_.m_6211_();
      this.f_39701_.m_6211_();
   }

   public boolean m_6032_(Recipe<? super CraftingContainer> p_39719_) {
      return p_39719_.m_5818_(this.f_39701_, this.f_39703_.f_19853_);
   }

   public void m_6199_(Container p_39710_) {
      CraftingMenu.m_150546_(this, this.f_39703_.f_19853_, this.f_39703_, this.f_39701_, this.f_39702_);
   }

   public void m_6877_(Player p_39721_) {
      super.m_6877_(p_39721_);
      this.f_39702_.m_6211_();
      if (!p_39721_.f_19853_.f_46443_) {
         this.m_150411_(p_39721_, this.f_39701_);
      }
   }

   public boolean m_6875_(Player p_39712_) {
      return true;
   }

   public ItemStack m_7648_(Player p_39723_, int p_39724_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39724_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         EquipmentSlot equipmentslot = Mob.m_147233_(itemstack);
         if (p_39724_ == 0) {
            if (!this.m_38903_(itemstack1, 9, 45, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39724_ >= 1 && p_39724_ < 5) {
            if (!this.m_38903_(itemstack1, 9, 45, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_39724_ >= 5 && p_39724_ < 9) {
            if (!this.m_38903_(itemstack1, 9, 45, false)) {
               return ItemStack.f_41583_;
            }
         } else if (equipmentslot.m_20743_() == EquipmentSlot.Type.ARMOR && !this.f_38839_.get(8 - equipmentslot.m_20749_()).m_6657_()) {
            int i = 8 - equipmentslot.m_20749_();
            if (!this.m_38903_(itemstack1, i, i + 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.f_38839_.get(45).m_6657_()) {
            if (!this.m_38903_(itemstack1, 45, 46, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_39724_ >= 9 && p_39724_ < 36) {
            if (!this.m_38903_(itemstack1, 36, 45, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_39724_ >= 36 && p_39724_ < 45) {
            if (!this.m_38903_(itemstack1, 9, 36, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 9, 45, false)) {
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

         slot.m_142406_(p_39723_, itemstack1);
         if (p_39724_ == 0) {
            p_39723_.m_36176_(itemstack1, false);
         }
      }

      return itemstack;
   }

   public boolean m_5882_(ItemStack p_39716_, Slot p_39717_) {
      return p_39717_.f_40218_ != this.f_39702_ && super.m_5882_(p_39716_, p_39717_);
   }

   public int m_6636_() {
      return 0;
   }

   public int m_6635_() {
      return this.f_39701_.m_39347_();
   }

   public int m_6656_() {
      return this.f_39701_.m_39346_();
   }

   public int m_6653_() {
      return 5;
   }

   public CraftingContainer m_39730_() {
      return this.f_39701_;
   }

   public RecipeBookType m_5867_() {
      return RecipeBookType.CRAFTING;
   }

   public boolean m_142157_(int p_150591_) {
      return p_150591_ != this.m_6636_();
   }
}
