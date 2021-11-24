package net.minecraft.world.inventory;

import java.util.Optional;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class CraftingMenu extends RecipeBookMenu<CraftingContainer> {
   public static final int f_150539_ = 0;
   private static final int f_150540_ = 1;
   private static final int f_150541_ = 10;
   private static final int f_150542_ = 10;
   private static final int f_150543_ = 37;
   private static final int f_150544_ = 37;
   private static final int f_150545_ = 46;
   private final CraftingContainer f_39348_ = new CraftingContainer(this, 3, 3);
   private final ResultContainer f_39349_ = new ResultContainer();
   private final ContainerLevelAccess f_39350_;
   private final Player f_39351_;

   public CraftingMenu(int p_39353_, Inventory p_39354_) {
      this(p_39353_, p_39354_, ContainerLevelAccess.f_39287_);
   }

   public CraftingMenu(int p_39356_, Inventory p_39357_, ContainerLevelAccess p_39358_) {
      super(MenuType.f_39968_, p_39356_);
      this.f_39350_ = p_39358_;
      this.f_39351_ = p_39357_.f_35978_;
      this.m_38897_(new ResultSlot(p_39357_.f_35978_, this.f_39348_, this.f_39349_, 0, 124, 35));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 3; ++j) {
            this.m_38897_(new Slot(this.f_39348_, j + i * 3, 30 + j * 18, 17 + i * 18));
         }
      }

      for(int k = 0; k < 3; ++k) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.m_38897_(new Slot(p_39357_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for(int l = 0; l < 9; ++l) {
         this.m_38897_(new Slot(p_39357_, l, 8 + l * 18, 142));
      }

   }

   protected static void m_150546_(AbstractContainerMenu p_150547_, Level p_150548_, Player p_150549_, CraftingContainer p_150550_, ResultContainer p_150551_) {
      if (!p_150548_.f_46443_) {
         ServerPlayer serverplayer = (ServerPlayer)p_150549_;
         ItemStack itemstack = ItemStack.f_41583_;
         Optional<CraftingRecipe> optional = p_150548_.m_142572_().m_129894_().m_44015_(RecipeType.f_44107_, p_150550_, p_150548_);
         if (optional.isPresent()) {
            CraftingRecipe craftingrecipe = optional.get();
            if (p_150551_.m_40135_(p_150548_, serverplayer, craftingrecipe)) {
               itemstack = craftingrecipe.m_5874_(p_150550_);
            }
         }

         p_150551_.m_6836_(0, itemstack);
         p_150547_.m_150404_(0, itemstack);
         serverplayer.f_8906_.m_141995_(new ClientboundContainerSetSlotPacket(p_150547_.f_38840_, p_150547_.m_182425_(), 0, itemstack));
      }
   }

   public void m_6199_(Container p_39366_) {
      this.f_39350_.m_39292_((p_39386_, p_39387_) -> {
         m_150546_(this, p_39386_, this.f_39351_, this.f_39348_, this.f_39349_);
      });
   }

   public void m_5816_(StackedContents p_39374_) {
      this.f_39348_.m_5809_(p_39374_);
   }

   public void m_6650_() {
      this.f_39348_.m_6211_();
      this.f_39349_.m_6211_();
   }

   public boolean m_6032_(Recipe<? super CraftingContainer> p_39384_) {
      return p_39384_.m_5818_(this.f_39348_, this.f_39351_.f_19853_);
   }

   public void m_6877_(Player p_39389_) {
      super.m_6877_(p_39389_);
      this.f_39350_.m_39292_((p_39371_, p_39372_) -> {
         this.m_150411_(p_39389_, this.f_39348_);
      });
   }

   public boolean m_6875_(Player p_39368_) {
      return m_38889_(this.f_39350_, p_39368_, Blocks.f_50091_);
   }

   public ItemStack m_7648_(Player p_39391_, int p_39392_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39392_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39392_ == 0) {
            this.f_39350_.m_39292_((p_39378_, p_39379_) -> {
               itemstack1.m_41720_().m_7836_(itemstack1, p_39378_, p_39391_);
            });
            if (!this.m_38903_(itemstack1, 10, 46, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39392_ >= 10 && p_39392_ < 46) {
            if (!this.m_38903_(itemstack1, 1, 10, false)) {
               if (p_39392_ < 37) {
                  if (!this.m_38903_(itemstack1, 37, 46, false)) {
                     return ItemStack.f_41583_;
                  }
               } else if (!this.m_38903_(itemstack1, 10, 37, false)) {
                  return ItemStack.f_41583_;
               }
            }
         } else if (!this.m_38903_(itemstack1, 10, 46, false)) {
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

         slot.m_142406_(p_39391_, itemstack1);
         if (p_39392_ == 0) {
            p_39391_.m_36176_(itemstack1, false);
         }
      }

      return itemstack;
   }

   public boolean m_5882_(ItemStack p_39381_, Slot p_39382_) {
      return p_39382_.f_40218_ != this.f_39349_ && super.m_5882_(p_39381_, p_39382_);
   }

   public int m_6636_() {
      return 0;
   }

   public int m_6635_() {
      return this.f_39348_.m_39347_();
   }

   public int m_6656_() {
      return this.f_39348_.m_39346_();
   }

   public int m_6653_() {
      return 10;
   }

   public RecipeBookType m_5867_() {
      return RecipeBookType.CRAFTING;
   }

   public boolean m_142157_(int p_150553_) {
      return p_150553_ != this.m_6636_();
   }
}