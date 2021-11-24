package net.minecraft.world.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Blocks;

public class EnchantmentMenu extends AbstractContainerMenu {
   private final Container f_39449_ = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         EnchantmentMenu.this.m_6199_(this);
      }
   };
   private final ContainerLevelAccess f_39450_;
   private final Random f_39451_ = new Random();
   private final DataSlot f_39452_ = DataSlot.m_39401_();
   public final int[] f_39446_ = new int[3];
   public final int[] f_39447_ = new int[]{-1, -1, -1};
   public final int[] f_39448_ = new int[]{-1, -1, -1};

   public EnchantmentMenu(int p_39454_, Inventory p_39455_) {
      this(p_39454_, p_39455_, ContainerLevelAccess.f_39287_);
   }

   public EnchantmentMenu(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) {
      super(MenuType.f_39969_, p_39457_);
      this.f_39450_ = p_39459_;
      this.m_38897_(new Slot(this.f_39449_, 0, 15, 47) {
         public boolean m_5857_(ItemStack p_39508_) {
            return true;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.m_38897_(new Slot(this.f_39449_, 1, 35, 47) {
         public boolean m_5857_(ItemStack p_39517_) {
            return p_39517_.m_150922_(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39458_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39458_, k, 8 + k * 18, 142));
      }

      this.m_38895_(DataSlot.m_39406_(this.f_39446_, 0));
      this.m_38895_(DataSlot.m_39406_(this.f_39446_, 1));
      this.m_38895_(DataSlot.m_39406_(this.f_39446_, 2));
      this.m_38895_(this.f_39452_).m_6422_(p_39458_.f_35978_.m_36322_());
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 0));
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 1));
      this.m_38895_(DataSlot.m_39406_(this.f_39447_, 2));
      this.m_38895_(DataSlot.m_39406_(this.f_39448_, 0));
      this.m_38895_(DataSlot.m_39406_(this.f_39448_, 1));
      this.m_38895_(DataSlot.m_39406_(this.f_39448_, 2));
   }

   private float getPower(net.minecraft.world.level.Level world, net.minecraft.core.BlockPos pos) {
      return world.m_8055_(pos).getEnchantPowerBonus(world, pos);
   }

   public void m_6199_(Container p_39461_) {
      if (p_39461_ == this.f_39449_) {
         ItemStack itemstack = p_39461_.m_8020_(0);
         if (!itemstack.m_41619_() && itemstack.m_41792_()) {
            this.f_39450_.m_39292_((p_39485_, p_39486_) -> {
               int power = 0;

               for(int k = -1; k <= 1; ++k) {
                  for(int l = -1; l <= 1; ++l) {
                     if ((k != 0 || l != 0) && p_39485_.m_46859_(p_39486_.m_142082_(l, 0, k)) && p_39485_.m_46859_(p_39486_.m_142082_(l, 1, k))) {
                        power += getPower(p_39485_, p_39486_.m_142082_(l * 2, 0, k * 2));
                        power += getPower(p_39485_, p_39486_.m_142082_(l * 2, 1, k * 2));

                        if (l != 0 && k != 0) {
                           power += getPower(p_39485_, p_39486_.m_142082_(l * 2, 0, k));
                           power += getPower(p_39485_, p_39486_.m_142082_(l * 2, 1, k));
                           power += getPower(p_39485_, p_39486_.m_142082_(l, 0, k * 2));
                           power += getPower(p_39485_, p_39486_.m_142082_(l, 1, k * 2));
                        }
                     }
                  }
               }

               this.f_39451_.setSeed((long)this.f_39452_.m_6501_());

               for(int i1 = 0; i1 < 3; ++i1) {
                  this.f_39446_[i1] = EnchantmentHelper.m_44872_(this.f_39451_, i1, (int)power, itemstack);
                  this.f_39447_[i1] = -1;
                  this.f_39448_[i1] = -1;
                  if (this.f_39446_[i1] < i1 + 1) {
                     this.f_39446_[i1] = 0;
                  }
                  this.f_39446_[i1] = net.minecraftforge.event.ForgeEventFactory.onEnchantmentLevelSet(p_39485_, p_39486_, i1, (int)power, itemstack, f_39446_[i1]);
               }

               for(int j1 = 0; j1 < 3; ++j1) {
                  if (this.f_39446_[j1] > 0) {
                     List<EnchantmentInstance> list = this.m_39471_(itemstack, j1, this.f_39446_[j1]);
                     if (list != null && !list.isEmpty()) {
                        EnchantmentInstance enchantmentinstance = list.get(this.f_39451_.nextInt(list.size()));
                        this.f_39447_[j1] = Registry.f_122825_.m_7447_(enchantmentinstance.f_44947_);
                        this.f_39448_[j1] = enchantmentinstance.f_44948_;
                     }
                  }
               }

               this.m_38946_();
            });
         } else {
            for(int i = 0; i < 3; ++i) {
               this.f_39446_[i] = 0;
               this.f_39447_[i] = -1;
               this.f_39448_[i] = -1;
            }
         }
      }

   }

   public boolean m_6366_(Player p_39465_, int p_39466_) {
      ItemStack itemstack = this.f_39449_.m_8020_(0);
      ItemStack itemstack1 = this.f_39449_.m_8020_(1);
      int i = p_39466_ + 1;
      if ((itemstack1.m_41619_() || itemstack1.m_41613_() < i) && !p_39465_.m_150110_().f_35937_) {
         return false;
      } else if (this.f_39446_[p_39466_] <= 0 || itemstack.m_41619_() || (p_39465_.f_36078_ < i || p_39465_.f_36078_ < this.f_39446_[p_39466_]) && !p_39465_.m_150110_().f_35937_) {
         return false;
      } else {
         this.f_39450_.m_39292_((p_39481_, p_39482_) -> {
            ItemStack itemstack2 = itemstack;
            List<EnchantmentInstance> list = this.m_39471_(itemstack, p_39466_, this.f_39446_[p_39466_]);
            if (!list.isEmpty()) {
               p_39465_.m_7408_(itemstack, i);
               boolean flag = itemstack.m_150930_(Items.f_42517_);
               if (flag) {
                  itemstack2 = new ItemStack(Items.f_42690_);
                  CompoundTag compoundtag = itemstack.m_41783_();
                  if (compoundtag != null) {
                     itemstack2.m_41751_(compoundtag.m_6426_());
                  }

                  this.f_39449_.m_6836_(0, itemstack2);
               }

               for(int j = 0; j < list.size(); ++j) {
                  EnchantmentInstance enchantmentinstance = list.get(j);
                  if (flag) {
                     EnchantedBookItem.m_41153_(itemstack2, enchantmentinstance);
                  } else {
                     itemstack2.m_41663_(enchantmentinstance.f_44947_, enchantmentinstance.f_44948_);
                  }
               }

               if (!p_39465_.m_150110_().f_35937_) {
                  itemstack1.m_41774_(i);
                  if (itemstack1.m_41619_()) {
                     this.f_39449_.m_6836_(1, ItemStack.f_41583_);
                  }
               }

               p_39465_.m_36220_(Stats.f_12964_);
               if (p_39465_ instanceof ServerPlayer) {
                  CriteriaTriggers.f_10575_.m_27668_((ServerPlayer)p_39465_, itemstack2, i);
               }

               this.f_39449_.m_6596_();
               this.f_39452_.m_6422_(p_39465_.m_36322_());
               this.m_6199_(this.f_39449_);
               p_39481_.m_5594_((Player)null, p_39482_, SoundEvents.f_11887_, SoundSource.BLOCKS, 1.0F, p_39481_.f_46441_.nextFloat() * 0.1F + 0.9F);
            }

         });
         return true;
      }
   }

   private List<EnchantmentInstance> m_39471_(ItemStack p_39472_, int p_39473_, int p_39474_) {
      this.f_39451_.setSeed((long)(this.f_39452_.m_6501_() + p_39473_));
      List<EnchantmentInstance> list = EnchantmentHelper.m_44909_(this.f_39451_, p_39472_, p_39474_, false);
      if (p_39472_.m_150930_(Items.f_42517_) && list.size() > 1) {
         list.remove(this.f_39451_.nextInt(list.size()));
      }

      return list;
   }

   public int m_39492_() {
      ItemStack itemstack = this.f_39449_.m_8020_(1);
      return itemstack.m_41619_() ? 0 : itemstack.m_41613_();
   }

   public int m_39493_() {
      return this.f_39452_.m_6501_();
   }

   public void m_6877_(Player p_39488_) {
      super.m_6877_(p_39488_);
      this.f_39450_.m_39292_((p_39469_, p_39470_) -> {
         this.m_150411_(p_39488_, this.f_39449_);
      });
   }

   public boolean m_6875_(Player p_39463_) {
      return m_38889_(this.f_39450_, p_39463_, Blocks.f_50201_);
   }

   public ItemStack m_7648_(Player p_39490_, int p_39491_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39491_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39491_ == 0) {
            if (!this.m_38903_(itemstack1, 2, 38, true)) {
               return ItemStack.f_41583_;
            }
         } else if (p_39491_ == 1) {
            if (!this.m_38903_(itemstack1, 2, 38, true)) {
               return ItemStack.f_41583_;
            }
         } else if (itemstack1.m_150922_(net.minecraftforge.common.Tags.Items.ENCHANTING_FUELS)) {
            if (!this.m_38903_(itemstack1, 1, 2, true)) {
               return ItemStack.f_41583_;
            }
         } else {
            if (this.f_38839_.get(0).m_6657_() || !this.f_38839_.get(0).m_5857_(itemstack1)) {
               return ItemStack.f_41583_;
            }

            ItemStack itemstack2 = itemstack1.m_41777_();
            itemstack2.m_41764_(1);
            itemstack1.m_41774_(1);
            this.f_38839_.get(0).m_5852_(itemstack2);
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_39490_, itemstack1);
      }

      return itemstack;
   }
}
