package net.minecraft.world.level.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BrewingStandBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int f_155280_ = 3;
   private static final int f_155281_ = 4;
   private static final int[] f_58972_ = new int[]{3};
   private static final int[] f_58973_ = new int[]{0, 1, 2, 3};
   private static final int[] f_58974_ = new int[]{0, 1, 2, 4};
   public static final int f_155276_ = 20;
   public static final int f_155277_ = 0;
   public static final int f_155278_ = 1;
   public static final int f_155279_ = 2;
   private NonNullList<ItemStack> f_58975_ = NonNullList.m_122780_(5, ItemStack.f_41583_);
   int f_58976_;
   private boolean[] f_58977_;
   private Item f_58978_;
   int f_58979_;
   protected final ContainerData f_58971_ = new ContainerData() {
      public int m_6413_(int p_59038_) {
         switch(p_59038_) {
         case 0:
            return BrewingStandBlockEntity.this.f_58976_;
         case 1:
            return BrewingStandBlockEntity.this.f_58979_;
         default:
            return 0;
         }
      }

      public void m_8050_(int p_59040_, int p_59041_) {
         switch(p_59040_) {
         case 0:
            BrewingStandBlockEntity.this.f_58976_ = p_59041_;
            break;
         case 1:
            BrewingStandBlockEntity.this.f_58979_ = p_59041_;
         }

      }

      public int m_6499_() {
         return 2;
      }
   };

   public BrewingStandBlockEntity(BlockPos p_155283_, BlockState p_155284_) {
      super(BlockEntityType.f_58927_, p_155283_, p_155284_);
   }

   protected Component m_6820_() {
      return new TranslatableComponent("container.brewing");
   }

   public int m_6643_() {
      return this.f_58975_.size();
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_58975_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public static void m_155285_(Level p_155286_, BlockPos p_155287_, BlockState p_155288_, BrewingStandBlockEntity p_155289_) {
      ItemStack itemstack = p_155289_.f_58975_.get(4);
      if (p_155289_.f_58979_ <= 0 && itemstack.m_150930_(Items.f_42593_)) {
         p_155289_.f_58979_ = 20;
         itemstack.m_41774_(1);
         m_155232_(p_155286_, p_155287_, p_155288_);
      }

      boolean flag = m_155294_(p_155289_.f_58975_);
      boolean flag1 = p_155289_.f_58976_ > 0;
      ItemStack itemstack1 = p_155289_.f_58975_.get(3);
      if (flag1) {
         --p_155289_.f_58976_;
         boolean flag2 = p_155289_.f_58976_ == 0;
         if (flag2 && flag) {
            m_155290_(p_155286_, p_155287_, p_155289_.f_58975_);
            m_155232_(p_155286_, p_155287_, p_155288_);
         } else if (!flag || !itemstack1.m_150930_(p_155289_.f_58978_)) {
            p_155289_.f_58976_ = 0;
            m_155232_(p_155286_, p_155287_, p_155288_);
         }
      } else if (flag && p_155289_.f_58979_ > 0) {
         --p_155289_.f_58979_;
         p_155289_.f_58976_ = 400;
         p_155289_.f_58978_ = itemstack1.m_41720_();
         m_155232_(p_155286_, p_155287_, p_155288_);
      }

      boolean[] aboolean = p_155289_.m_59029_();
      if (!Arrays.equals(aboolean, p_155289_.f_58977_)) {
         p_155289_.f_58977_ = aboolean;
         BlockState blockstate = p_155288_;
         if (!(p_155288_.m_60734_() instanceof BrewingStandBlock)) {
            return;
         }

         for(int i = 0; i < BrewingStandBlock.f_50905_.length; ++i) {
            blockstate = blockstate.m_61124_(BrewingStandBlock.f_50905_[i], Boolean.valueOf(aboolean[i]));
         }

         p_155286_.m_7731_(p_155287_, blockstate, 2);
      }

   }

   private boolean[] m_59029_() {
      boolean[] aboolean = new boolean[3];

      for(int i = 0; i < 3; ++i) {
         if (!this.f_58975_.get(i).m_41619_()) {
            aboolean[i] = true;
         }
      }

      return aboolean;
   }

   private static boolean m_155294_(NonNullList<ItemStack> p_155295_) {
      ItemStack itemstack = p_155295_.get(3);
      if (!itemstack.m_41619_()) return net.minecraftforge.common.brewing.BrewingRecipeRegistry.canBrew(p_155295_, itemstack, f_58974_); // divert to VanillaBrewingRegistry
      if (itemstack.m_41619_()) {
         return false;
      } else if (!PotionBrewing.m_43506_(itemstack)) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            ItemStack itemstack1 = p_155295_.get(i);
            if (!itemstack1.m_41619_() && PotionBrewing.m_43508_(itemstack1, itemstack)) {
               return true;
            }
         }

         return false;
      }
   }

   private static void m_155290_(Level p_155291_, BlockPos p_155292_, NonNullList<ItemStack> p_155293_) {
      if (net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBrew(p_155293_)) return;
      ItemStack itemstack = p_155293_.get(3);

      net.minecraftforge.common.brewing.BrewingRecipeRegistry.brewPotions(p_155293_, itemstack, f_58974_);
      net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(p_155293_);
      if (itemstack.hasContainerItem()) {
         ItemStack itemstack1 = itemstack.getContainerItem();
         itemstack.m_41774_(1);
         if (itemstack.m_41619_()) {
            itemstack = itemstack1;
         } else {
            Containers.m_18992_(p_155291_, (double)p_155292_.m_123341_(), (double)p_155292_.m_123342_(), (double)p_155292_.m_123343_(), itemstack1);
         }
      }
      else itemstack.m_41774_(1);

      p_155293_.set(3, itemstack);
      p_155291_.m_46796_(1035, p_155292_, 0);
   }

   public void m_142466_(CompoundTag p_155297_) {
      super.m_142466_(p_155297_);
      this.f_58975_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      ContainerHelper.m_18980_(p_155297_, this.f_58975_);
      this.f_58976_ = p_155297_.m_128448_("BrewTime");
      this.f_58979_ = p_155297_.m_128445_("Fuel");
   }

   public CompoundTag m_6945_(CompoundTag p_59012_) {
      super.m_6945_(p_59012_);
      p_59012_.m_128376_("BrewTime", (short)this.f_58976_);
      ContainerHelper.m_18973_(p_59012_, this.f_58975_);
      p_59012_.m_128344_("Fuel", (byte)this.f_58979_);
      return p_59012_;
   }

   public ItemStack m_8020_(int p_58985_) {
      return p_58985_ >= 0 && p_58985_ < this.f_58975_.size() ? this.f_58975_.get(p_58985_) : ItemStack.f_41583_;
   }

   public ItemStack m_7407_(int p_58987_, int p_58988_) {
      return ContainerHelper.m_18969_(this.f_58975_, p_58987_, p_58988_);
   }

   public ItemStack m_8016_(int p_59015_) {
      return ContainerHelper.m_18966_(this.f_58975_, p_59015_);
   }

   public void m_6836_(int p_58993_, ItemStack p_58994_) {
      if (p_58993_ >= 0 && p_58993_ < this.f_58975_.size()) {
         this.f_58975_.set(p_58993_, p_58994_);
      }

   }

   public boolean m_6542_(Player p_59000_) {
      if (this.f_58857_.m_7702_(this.f_58858_) != this) {
         return false;
      } else {
         return !(p_59000_.m_20275_((double)this.f_58858_.m_123341_() + 0.5D, (double)this.f_58858_.m_123342_() + 0.5D, (double)this.f_58858_.m_123343_() + 0.5D) > 64.0D);
      }
   }

   public boolean m_7013_(int p_59017_, ItemStack p_59018_) {
      if (p_59017_ == 3) {
         return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(p_59018_);
      } else if (p_59017_ == 4) {
         return p_59018_.m_150930_(Items.f_42593_);
      } else {
            return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(p_59018_) && this.m_8020_(p_59017_).m_41619_();
      }
   }

   public int[] m_7071_(Direction p_59010_) {
      if (p_59010_ == Direction.UP) {
         return f_58972_;
      } else {
         return p_59010_ == Direction.DOWN ? f_58973_ : f_58974_;
      }
   }

   public boolean m_7155_(int p_58996_, ItemStack p_58997_, @Nullable Direction p_58998_) {
      return this.m_7013_(p_58996_, p_58997_);
   }

   public boolean m_7157_(int p_59020_, ItemStack p_59021_, Direction p_59022_) {
      return p_59020_ == 3 ? p_59021_.m_150930_(Items.f_42590_) : true;
   }

   public void m_6211_() {
      this.f_58975_.clear();
   }

   protected AbstractContainerMenu m_6555_(int p_58990_, Inventory p_58991_) {
      return new BrewingStandMenu(p_58990_, p_58991_, this, this.f_58971_);
   }

   net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
           net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
      if (!this.f_58859_ && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == Direction.UP)
            return handlers[0].cast();
         else if (facing == Direction.DOWN)
            return handlers[1].cast();
         else
            return handlers[2].cast();
      }
      return super.getCapability(capability, facing);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      for (int x = 0; x < handlers.length; x++)
        handlers[x].invalidate();
   }

   @Override
   public void reviveCaps() {
      super.reviveCaps();
      this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
   }
}
