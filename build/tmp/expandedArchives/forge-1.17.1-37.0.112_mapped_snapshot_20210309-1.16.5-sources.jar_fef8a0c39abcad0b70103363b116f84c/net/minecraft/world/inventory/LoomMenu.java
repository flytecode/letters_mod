package net.minecraft.world.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;

public class LoomMenu extends AbstractContainerMenu {
   private static final int f_150612_ = 4;
   private static final int f_150613_ = 31;
   private static final int f_150614_ = 31;
   private static final int f_150615_ = 40;
   private final ContainerLevelAccess f_39845_;
   final DataSlot f_39846_ = DataSlot.m_39401_();
   Runnable f_39847_ = () -> {
   };
   final Slot f_39848_;
   final Slot f_39849_;
   private final Slot f_39850_;
   private final Slot f_39851_;
   long f_39852_;
   private final Container f_39853_ = new SimpleContainer(3) {
      public void m_6596_() {
         super.m_6596_();
         LoomMenu.this.m_6199_(this);
         LoomMenu.this.f_39847_.run();
      }
   };
   private final Container f_39854_ = new SimpleContainer(1) {
      public void m_6596_() {
         super.m_6596_();
         LoomMenu.this.f_39847_.run();
      }
   };

   public LoomMenu(int p_39856_, Inventory p_39857_) {
      this(p_39856_, p_39857_, ContainerLevelAccess.f_39287_);
   }

   public LoomMenu(int p_39859_, Inventory p_39860_, final ContainerLevelAccess p_39861_) {
      super(MenuType.f_39974_, p_39859_);
      this.f_39845_ = p_39861_;
      this.f_39848_ = this.m_38897_(new Slot(this.f_39853_, 0, 13, 26) {
         public boolean m_5857_(ItemStack p_39918_) {
            return p_39918_.m_41720_() instanceof BannerItem;
         }
      });
      this.f_39849_ = this.m_38897_(new Slot(this.f_39853_, 1, 33, 26) {
         public boolean m_5857_(ItemStack p_39927_) {
            return p_39927_.m_41720_() instanceof DyeItem;
         }
      });
      this.f_39850_ = this.m_38897_(new Slot(this.f_39853_, 2, 23, 45) {
         public boolean m_5857_(ItemStack p_39936_) {
            return p_39936_.m_41720_() instanceof BannerPatternItem;
         }
      });
      this.f_39851_ = this.m_38897_(new Slot(this.f_39854_, 0, 143, 58) {
         public boolean m_5857_(ItemStack p_39950_) {
            return false;
         }

         public void m_142406_(Player p_150617_, ItemStack p_150618_) {
            LoomMenu.this.f_39848_.m_6201_(1);
            LoomMenu.this.f_39849_.m_6201_(1);
            if (!LoomMenu.this.f_39848_.m_6657_() || !LoomMenu.this.f_39849_.m_6657_()) {
               LoomMenu.this.f_39846_.m_6422_(0);
            }

            p_39861_.m_39292_((p_39952_, p_39953_) -> {
               long l = p_39952_.m_46467_();
               if (LoomMenu.this.f_39852_ != l) {
                  p_39952_.m_5594_((Player)null, p_39953_, SoundEvents.f_12492_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  LoomMenu.this.f_39852_ = l;
               }

            });
            super.m_142406_(p_150617_, p_150618_);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39860_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39860_, k, 8 + k * 18, 142));
      }

      this.m_38895_(this.f_39846_);
   }

   public int m_39891_() {
      return this.f_39846_.m_6501_();
   }

   public boolean m_6875_(Player p_39865_) {
      return m_38889_(this.f_39845_, p_39865_, Blocks.f_50617_);
   }

   public boolean m_6366_(Player p_39867_, int p_39868_) {
      if (p_39868_ > 0 && p_39868_ <= BannerPattern.f_58528_) {
         this.f_39846_.m_6422_(p_39868_);
         this.m_39898_();
         return true;
      } else {
         return false;
      }
   }

   public void m_6199_(Container p_39863_) {
      ItemStack itemstack = this.f_39848_.m_7993_();
      ItemStack itemstack1 = this.f_39849_.m_7993_();
      ItemStack itemstack2 = this.f_39850_.m_7993_();
      ItemStack itemstack3 = this.f_39851_.m_7993_();
      if (itemstack3.m_41619_() || !itemstack.m_41619_() && !itemstack1.m_41619_() && this.f_39846_.m_6501_() > 0 && (this.f_39846_.m_6501_() < BannerPattern.f_58526_ - BannerPattern.f_58527_ || !itemstack2.m_41619_())) {
         if (!itemstack2.m_41619_() && itemstack2.m_41720_() instanceof BannerPatternItem) {
            CompoundTag compoundtag = itemstack.m_41698_("BlockEntityTag");
            boolean flag = compoundtag.m_128425_("Patterns", 9) && !itemstack.m_41619_() && compoundtag.m_128437_("Patterns", 10).size() >= 6;
            if (flag) {
               this.f_39846_.m_6422_(0);
            } else {
               this.f_39846_.m_6422_(((BannerPatternItem)itemstack2.m_41720_()).m_40555_().ordinal());
            }
         }
      } else {
         this.f_39851_.m_5852_(ItemStack.f_41583_);
         this.f_39846_.m_6422_(0);
      }

      this.m_39898_();
      this.m_38946_();
   }

   public void m_39878_(Runnable p_39879_) {
      this.f_39847_ = p_39879_;
   }

   public ItemStack m_7648_(Player p_39883_, int p_39884_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39884_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39884_ == this.f_39851_.f_40219_) {
            if (!this.m_38903_(itemstack1, 4, 40, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39884_ != this.f_39849_.f_40219_ && p_39884_ != this.f_39848_.f_40219_ && p_39884_ != this.f_39850_.f_40219_) {
            if (itemstack1.m_41720_() instanceof BannerItem) {
               if (!this.m_38903_(itemstack1, this.f_39848_.f_40219_, this.f_39848_.f_40219_ + 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (itemstack1.m_41720_() instanceof DyeItem) {
               if (!this.m_38903_(itemstack1, this.f_39849_.f_40219_, this.f_39849_.f_40219_ + 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (itemstack1.m_41720_() instanceof BannerPatternItem) {
               if (!this.m_38903_(itemstack1, this.f_39850_.f_40219_, this.f_39850_.f_40219_ + 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_39884_ >= 4 && p_39884_ < 31) {
               if (!this.m_38903_(itemstack1, 31, 40, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_39884_ >= 31 && p_39884_ < 40 && !this.m_38903_(itemstack1, 4, 31, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 4, 40, false)) {
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

         slot.m_142406_(p_39883_, itemstack1);
      }

      return itemstack;
   }

   public void m_6877_(Player p_39881_) {
      super.m_6877_(p_39881_);
      this.f_39845_.m_39292_((p_39871_, p_39872_) -> {
         this.m_150411_(p_39881_, this.f_39853_);
      });
   }

   private void m_39898_() {
      if (this.f_39846_.m_6501_() > 0) {
         ItemStack itemstack = this.f_39848_.m_7993_();
         ItemStack itemstack1 = this.f_39849_.m_7993_();
         ItemStack itemstack2 = ItemStack.f_41583_;
         if (!itemstack.m_41619_() && !itemstack1.m_41619_()) {
            itemstack2 = itemstack.m_41777_();
            itemstack2.m_41764_(1);
            BannerPattern bannerpattern = BannerPattern.values()[this.f_39846_.m_6501_()];
            DyeColor dyecolor = ((DyeItem)itemstack1.m_41720_()).m_41089_();
            CompoundTag compoundtag = itemstack2.m_41698_("BlockEntityTag");
            ListTag listtag;
            if (compoundtag.m_128425_("Patterns", 9)) {
               listtag = compoundtag.m_128437_("Patterns", 10);
            } else {
               listtag = new ListTag();
               compoundtag.m_128365_("Patterns", listtag);
            }

            CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.m_128359_("Pattern", bannerpattern.m_58579_());
            compoundtag1.m_128405_("Color", dyecolor.m_41060_());
            listtag.add(compoundtag1);
         }

         if (!ItemStack.m_41728_(itemstack2, this.f_39851_.m_7993_())) {
            this.f_39851_.m_5852_(itemstack2);
         }
      }

   }

   public Slot m_39894_() {
      return this.f_39848_;
   }

   public Slot m_39895_() {
      return this.f_39849_;
   }

   public Slot m_39896_() {
      return this.f_39850_;
   }

   public Slot m_39897_() {
      return this.f_39851_;
   }
}