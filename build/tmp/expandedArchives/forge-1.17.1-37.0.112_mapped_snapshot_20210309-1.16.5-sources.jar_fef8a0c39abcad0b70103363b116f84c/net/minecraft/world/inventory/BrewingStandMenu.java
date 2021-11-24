package net.minecraft.world.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;

public class BrewingStandMenu extends AbstractContainerMenu {
   private static final int f_150488_ = 0;
   private static final int f_150489_ = 2;
   private static final int f_150490_ = 3;
   private static final int f_150491_ = 4;
   private static final int f_150492_ = 5;
   private static final int f_150493_ = 2;
   private static final int f_150494_ = 5;
   private static final int f_150495_ = 32;
   private static final int f_150496_ = 32;
   private static final int f_150497_ = 41;
   private final Container f_39086_;
   private final ContainerData f_39087_;
   private final Slot f_39088_;

   public BrewingStandMenu(int p_39090_, Inventory p_39091_) {
      this(p_39090_, p_39091_, new SimpleContainer(5), new SimpleContainerData(2));
   }

   public BrewingStandMenu(int p_39093_, Inventory p_39094_, Container p_39095_, ContainerData p_39096_) {
      super(MenuType.f_39967_, p_39093_);
      m_38869_(p_39095_, 5);
      m_38886_(p_39096_, 2);
      this.f_39086_ = p_39095_;
      this.f_39087_ = p_39096_;
      this.m_38897_(new BrewingStandMenu.PotionSlot(p_39095_, 0, 56, 51));
      this.m_38897_(new BrewingStandMenu.PotionSlot(p_39095_, 1, 79, 58));
      this.m_38897_(new BrewingStandMenu.PotionSlot(p_39095_, 2, 102, 51));
      this.f_39088_ = this.m_38897_(new BrewingStandMenu.IngredientsSlot(p_39095_, 3, 79, 17));
      this.m_38897_(new BrewingStandMenu.FuelSlot(p_39095_, 4, 17, 17));
      this.m_38884_(p_39096_);

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39094_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39094_, k, 8 + k * 18, 142));
      }

   }

   public boolean m_6875_(Player p_39098_) {
      return this.f_39086_.m_6542_(p_39098_);
   }

   public ItemStack m_7648_(Player p_39100_, int p_39101_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39101_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if ((p_39101_ < 0 || p_39101_ > 2) && p_39101_ != 3 && p_39101_ != 4) {
            if (BrewingStandMenu.FuelSlot.m_39112_(itemstack)) {
               if (this.m_38903_(itemstack1, 4, 5, false) || this.f_39088_.m_5857_(itemstack1) && !this.m_38903_(itemstack1, 3, 4, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (this.f_39088_.m_5857_(itemstack1)) {
               if (!this.m_38903_(itemstack1, 3, 4, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (BrewingStandMenu.PotionSlot.m_39133_(itemstack) && itemstack.m_41613_() == 1) {
               if (!this.m_38903_(itemstack1, 0, 3, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_39101_ >= 5 && p_39101_ < 32) {
               if (!this.m_38903_(itemstack1, 32, 41, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_39101_ >= 32 && p_39101_ < 41) {
               if (!this.m_38903_(itemstack1, 5, 32, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, 5, 41, false)) {
               return ItemStack.f_41583_;
            }
         } else {
            if (!this.m_38903_(itemstack1, 5, 41, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_39100_, itemstack1);
      }

      return itemstack;
   }

   public int m_39102_() {
      return this.f_39087_.m_6413_(1);
   }

   public int m_39103_() {
      return this.f_39087_.m_6413_(0);
   }

   static class FuelSlot extends Slot {
      public FuelSlot(Container p_39105_, int p_39106_, int p_39107_, int p_39108_) {
         super(p_39105_, p_39106_, p_39107_, p_39108_);
      }

      public boolean m_5857_(ItemStack p_39111_) {
         return m_39112_(p_39111_);
      }

      public static boolean m_39112_(ItemStack p_39113_) {
         return p_39113_.m_150930_(Items.f_42593_);
      }

      public int m_6641_() {
         return 64;
      }
   }

   static class IngredientsSlot extends Slot {
      public IngredientsSlot(Container p_39115_, int p_39116_, int p_39117_, int p_39118_) {
         super(p_39115_, p_39116_, p_39117_, p_39118_);
      }

      public boolean m_5857_(ItemStack p_39121_) {
         return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(p_39121_);
      }

      public int m_6641_() {
         return 64;
      }
   }

   static class PotionSlot extends Slot {
      public PotionSlot(Container p_39123_, int p_39124_, int p_39125_, int p_39126_) {
         super(p_39123_, p_39124_, p_39125_, p_39126_);
      }

      public boolean m_5857_(ItemStack p_39132_) {
         return m_39133_(p_39132_);
      }

      public int m_6641_() {
         return 1;
      }

      public void m_142406_(Player p_150499_, ItemStack p_150500_) {
         Potion potion = PotionUtils.m_43579_(p_150500_);
         if (p_150499_ instanceof ServerPlayer) {
            net.minecraftforge.event.ForgeEventFactory.onPlayerBrewedPotion(p_150499_, p_150500_);
            CriteriaTriggers.f_10577_.m_19120_((ServerPlayer)p_150499_, potion);
         }

         super.m_142406_(p_150499_, p_150500_);
      }

      public static boolean m_39133_(ItemStack p_39134_) {
         return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(p_39134_);
      }
   }
}
