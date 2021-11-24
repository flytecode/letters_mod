package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public abstract class AbstractFurnaceMenu extends RecipeBookMenu<Container> {
   public static final int f_150453_ = 0;
   public static final int f_150454_ = 1;
   public static final int f_150455_ = 2;
   public static final int f_150456_ = 3;
   public static final int f_150457_ = 4;
   private static final int f_150458_ = 3;
   private static final int f_150459_ = 30;
   private static final int f_150460_ = 30;
   private static final int f_150461_ = 39;
   private final Container f_38955_;
   private final ContainerData f_38956_;
   protected final Level f_38954_;
   private final RecipeType<? extends AbstractCookingRecipe> f_38957_;
   private final RecipeBookType f_38958_;

   protected AbstractFurnaceMenu(MenuType<?> p_38960_, RecipeType<? extends AbstractCookingRecipe> p_38961_, RecipeBookType p_38962_, int p_38963_, Inventory p_38964_) {
      this(p_38960_, p_38961_, p_38962_, p_38963_, p_38964_, new SimpleContainer(3), new SimpleContainerData(4));
   }

   protected AbstractFurnaceMenu(MenuType<?> p_38966_, RecipeType<? extends AbstractCookingRecipe> p_38967_, RecipeBookType p_38968_, int p_38969_, Inventory p_38970_, Container p_38971_, ContainerData p_38972_) {
      super(p_38966_, p_38969_);
      this.f_38957_ = p_38967_;
      this.f_38958_ = p_38968_;
      m_38869_(p_38971_, 3);
      m_38886_(p_38972_, 4);
      this.f_38955_ = p_38971_;
      this.f_38956_ = p_38972_;
      this.f_38954_ = p_38970_.f_35978_.f_19853_;
      this.m_38897_(new Slot(p_38971_, 0, 56, 17));
      this.m_38897_(new FurnaceFuelSlot(this, p_38971_, 1, 56, 53));
      this.m_38897_(new FurnaceResultSlot(p_38970_.f_35978_, p_38971_, 2, 116, 35));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_38970_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_38970_, k, 8 + k * 18, 142));
      }

      this.m_38884_(p_38972_);
   }

   public void m_5816_(StackedContents p_38976_) {
      if (this.f_38955_ instanceof StackedContentsCompatible) {
         ((StackedContentsCompatible)this.f_38955_).m_5809_(p_38976_);
      }

   }

   public void m_6650_() {
      this.m_38853_(0).m_5852_(ItemStack.f_41583_);
      this.m_38853_(2).m_5852_(ItemStack.f_41583_);
   }

   public boolean m_6032_(Recipe<? super Container> p_38980_) {
      return p_38980_.m_5818_(this.f_38955_, this.f_38954_);
   }

   public int m_6636_() {
      return 2;
   }

   public int m_6635_() {
      return 1;
   }

   public int m_6656_() {
      return 1;
   }

   public int m_6653_() {
      return 3;
   }

   public boolean m_6875_(Player p_38974_) {
      return this.f_38955_.m_6542_(p_38974_);
   }

   public ItemStack m_7648_(Player p_38986_, int p_38987_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_38987_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_38987_ == 2) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_38987_ != 1 && p_38987_ != 0) {
            if (this.m_38977_(itemstack1)) {
               if (!this.m_38903_(itemstack1, 0, 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (this.m_38988_(itemstack1)) {
               if (!this.m_38903_(itemstack1, 1, 2, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_38987_ >= 3 && p_38987_ < 30) {
               if (!this.m_38903_(itemstack1, 30, 39, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_38987_ >= 30 && p_38987_ < 39 && !this.m_38903_(itemstack1, 3, 30, false)) {
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

         slot.m_142406_(p_38986_, itemstack1);
      }

      return itemstack;
   }

   protected boolean m_38977_(ItemStack p_38978_) {
      return this.f_38954_.m_7465_().m_44015_((RecipeType<AbstractCookingRecipe>)this.f_38957_, new SimpleContainer(p_38978_), this.f_38954_).isPresent();
   }

   protected boolean m_38988_(ItemStack p_38989_) {
      return net.minecraftforge.common.ForgeHooks.getBurnTime(p_38989_, this.f_38957_) > 0;
   }

   public int m_38995_() {
      int i = this.f_38956_.m_6413_(2);
      int j = this.f_38956_.m_6413_(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   public int m_38996_() {
      int i = this.f_38956_.m_6413_(1);
      if (i == 0) {
         i = 200;
      }

      return this.f_38956_.m_6413_(0) * 13 / i;
   }

   public boolean m_38997_() {
      return this.f_38956_.m_6413_(0) > 0;
   }

   public RecipeBookType m_5867_() {
      return this.f_38958_;
   }

   public boolean m_142157_(int p_150463_) {
      return p_150463_ != 1;
   }
}
