package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class Inventory implements Container, Nameable {
   public static final int f_150064_ = 5;
   public static final int f_150065_ = 36;
   private static final int f_150070_ = 9;
   public static final int f_150066_ = 40;
   public static final int f_150067_ = -1;
   public static final int[] f_150068_ = new int[]{0, 1, 2, 3};
   public static final int[] f_150069_ = new int[]{3};
   public final NonNullList<ItemStack> f_35974_ = NonNullList.m_122780_(36, ItemStack.f_41583_);
   public final NonNullList<ItemStack> f_35975_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
   public final NonNullList<ItemStack> f_35976_ = NonNullList.m_122780_(1, ItemStack.f_41583_);
   private final List<NonNullList<ItemStack>> f_35979_ = ImmutableList.of(this.f_35974_, this.f_35975_, this.f_35976_);
   public int f_35977_;
   public final Player f_35978_;
   private int f_35981_;

   public Inventory(Player p_35983_) {
      this.f_35978_ = p_35983_;
   }

   public ItemStack m_36056_() {
      return m_36045_(this.f_35977_) ? this.f_35974_.get(this.f_35977_) : ItemStack.f_41583_;
   }

   public static int m_36059_() {
      return 9;
   }

   private boolean m_36014_(ItemStack p_36015_, ItemStack p_36016_) {
      return !p_36015_.m_41619_() && ItemStack.m_150942_(p_36015_, p_36016_) && p_36015_.m_41753_() && p_36015_.m_41613_() < p_36015_.m_41741_() && p_36015_.m_41613_() < this.m_6893_();
   }

   public int m_36062_() {
      for(int i = 0; i < this.f_35974_.size(); ++i) {
         if (this.f_35974_.get(i).m_41619_()) {
            return i;
         }
      }

      return -1;
   }

   public void m_36012_(ItemStack p_36013_) {
      int i = this.m_36030_(p_36013_);
      if (m_36045_(i)) {
         this.f_35977_ = i;
      } else {
         if (i == -1) {
            this.f_35977_ = this.m_36065_();
            if (!this.f_35974_.get(this.f_35977_).m_41619_()) {
               int j = this.m_36062_();
               if (j != -1) {
                  this.f_35974_.set(j, this.f_35974_.get(this.f_35977_));
               }
            }

            this.f_35974_.set(this.f_35977_, p_36013_);
         } else {
            this.m_36038_(i);
         }

      }
   }

   public void m_36038_(int p_36039_) {
      this.f_35977_ = this.m_36065_();
      ItemStack itemstack = this.f_35974_.get(this.f_35977_);
      this.f_35974_.set(this.f_35977_, this.f_35974_.get(p_36039_));
      this.f_35974_.set(p_36039_, itemstack);
   }

   public static boolean m_36045_(int p_36046_) {
      return p_36046_ >= 0 && p_36046_ < 9;
   }

   public int m_36030_(ItemStack p_36031_) {
      for(int i = 0; i < this.f_35974_.size(); ++i) {
         if (!this.f_35974_.get(i).m_41619_() && ItemStack.m_150942_(p_36031_, this.f_35974_.get(i))) {
            return i;
         }
      }

      return -1;
   }

   public int m_36043_(ItemStack p_36044_) {
      for(int i = 0; i < this.f_35974_.size(); ++i) {
         ItemStack itemstack = this.f_35974_.get(i);
         if (!this.f_35974_.get(i).m_41619_() && ItemStack.m_150942_(p_36044_, this.f_35974_.get(i)) && !this.f_35974_.get(i).m_41768_() && !itemstack.m_41793_() && !itemstack.m_41788_()) {
            return i;
         }
      }

      return -1;
   }

   public int m_36065_() {
      for(int i = 0; i < 9; ++i) {
         int j = (this.f_35977_ + i) % 9;
         if (this.f_35974_.get(j).m_41619_()) {
            return j;
         }
      }

      for(int k = 0; k < 9; ++k) {
         int l = (this.f_35977_ + k) % 9;
         if (!this.f_35974_.get(l).m_41793_()) {
            return l;
         }
      }

      return this.f_35977_;
   }

   public void m_35988_(double p_35989_) {
      if (p_35989_ > 0.0D) {
         p_35989_ = 1.0D;
      }

      if (p_35989_ < 0.0D) {
         p_35989_ = -1.0D;
      }

      for(this.f_35977_ = (int)((double)this.f_35977_ - p_35989_); this.f_35977_ < 0; this.f_35977_ += 9) {
      }

      while(this.f_35977_ >= 9) {
         this.f_35977_ -= 9;
      }

   }

   public int m_36022_(Predicate<ItemStack> p_36023_, int p_36024_, Container p_36025_) {
      int i = 0;
      boolean flag = p_36024_ == 0;
      i = i + ContainerHelper.m_18956_(this, p_36023_, p_36024_ - i, flag);
      i = i + ContainerHelper.m_18956_(p_36025_, p_36023_, p_36024_ - i, flag);
      ItemStack itemstack = this.f_35978_.f_36096_.m_142621_();
      i = i + ContainerHelper.m_18961_(itemstack, p_36023_, p_36024_ - i, flag);
      if (itemstack.m_41619_()) {
         this.f_35978_.f_36096_.m_142503_(ItemStack.f_41583_);
      }

      return i;
   }

   private int m_36066_(ItemStack p_36067_) {
      int i = this.m_36050_(p_36067_);
      if (i == -1) {
         i = this.m_36062_();
      }

      return i == -1 ? p_36067_.m_41613_() : this.m_36047_(i, p_36067_);
   }

   private int m_36047_(int p_36048_, ItemStack p_36049_) {
      Item item = p_36049_.m_41720_();
      int i = p_36049_.m_41613_();
      ItemStack itemstack = this.m_8020_(p_36048_);
      if (itemstack.m_41619_()) {
         itemstack = p_36049_.m_41777_(); // Forge: Replace Item clone above to preserve item capabilities when picking the item up.
         itemstack.m_41764_(0);
         if (p_36049_.m_41782_()) {
            itemstack.m_41751_(p_36049_.m_41783_().m_6426_());
         }

         this.m_6836_(p_36048_, itemstack);
      }

      int j = i;
      if (i > itemstack.m_41741_() - itemstack.m_41613_()) {
         j = itemstack.m_41741_() - itemstack.m_41613_();
      }

      if (j > this.m_6893_() - itemstack.m_41613_()) {
         j = this.m_6893_() - itemstack.m_41613_();
      }

      if (j == 0) {
         return i;
      } else {
         i = i - j;
         itemstack.m_41769_(j);
         itemstack.m_41754_(5);
         return i;
      }
   }

   public int m_36050_(ItemStack p_36051_) {
      if (this.m_36014_(this.m_8020_(this.f_35977_), p_36051_)) {
         return this.f_35977_;
      } else if (this.m_36014_(this.m_8020_(40), p_36051_)) {
         return 40;
      } else {
         for(int i = 0; i < this.f_35974_.size(); ++i) {
            if (this.m_36014_(this.f_35974_.get(i), p_36051_)) {
               return i;
            }
         }

         return -1;
      }
   }

   public void m_36068_() {
      for(NonNullList<ItemStack> nonnulllist : this.f_35979_) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (!nonnulllist.get(i).m_41619_()) {
               nonnulllist.get(i).m_41666_(this.f_35978_.f_19853_, this.f_35978_, i, this.f_35977_ == i);
            }
         }
      }
      f_35975_.forEach(e -> e.onArmorTick(f_35978_.f_19853_, f_35978_));
   }

   public boolean m_36054_(ItemStack p_36055_) {
      return this.m_36040_(-1, p_36055_);
   }

   public boolean m_36040_(int p_36041_, ItemStack p_36042_) {
      if (p_36042_.m_41619_()) {
         return false;
      } else {
         try {
            if (p_36042_.m_41768_()) {
               if (p_36041_ == -1) {
                  p_36041_ = this.m_36062_();
               }

               if (p_36041_ >= 0) {
                  this.f_35974_.set(p_36041_, p_36042_.m_41777_());
                  this.f_35974_.get(p_36041_).m_41754_(5);
                  p_36042_.m_41764_(0);
                  return true;
               } else if (this.f_35978_.m_150110_().f_35937_) {
                  p_36042_.m_41764_(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int i;
               do {
                  i = p_36042_.m_41613_();
                  if (p_36041_ == -1) {
                     p_36042_.m_41764_(this.m_36066_(p_36042_));
                  } else {
                     p_36042_.m_41764_(this.m_36047_(p_36041_, p_36042_));
                  }
               } while(!p_36042_.m_41619_() && p_36042_.m_41613_() < i);

               if (p_36042_.m_41613_() == i && this.f_35978_.m_150110_().f_35937_) {
                  p_36042_.m_41764_(0);
                  return true;
               } else {
                  return p_36042_.m_41613_() < i;
               }
            }
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Adding item to inventory");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Item being added");
            crashreportcategory.m_128165_("Registry Name", () -> String.valueOf(p_36042_.m_41720_().getRegistryName()));
            crashreportcategory.m_128165_("Item Class", () -> p_36042_.m_41720_().getClass().getName());
            crashreportcategory.m_128159_("Item ID", Item.m_41393_(p_36042_.m_41720_()));
            crashreportcategory.m_128159_("Item data", p_36042_.m_41773_());
            crashreportcategory.m_128165_("Item name", () -> {
               return p_36042_.m_41786_().getString();
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   public void m_150079_(ItemStack p_150080_) {
      this.m_150076_(p_150080_, true);
   }

   public void m_150076_(ItemStack p_150077_, boolean p_150078_) {
      while(true) {
         if (!p_150077_.m_41619_()) {
            int i = this.m_36050_(p_150077_);
            if (i == -1) {
               i = this.m_36062_();
            }

            if (i != -1) {
               int j = p_150077_.m_41741_() - this.m_8020_(i).m_41613_();
               if (this.m_36040_(i, p_150077_.m_41620_(j)) && p_150078_ && this.f_35978_ instanceof ServerPlayer) {
                  ((ServerPlayer)this.f_35978_).f_8906_.m_141995_(new ClientboundContainerSetSlotPacket(-2, 0, i, this.m_8020_(i)));
               }
               continue;
            }

            this.f_35978_.m_36176_(p_150077_, false);
         }

         return;
      }
   }

   public ItemStack m_7407_(int p_35993_, int p_35994_) {
      List<ItemStack> list = null;

      for(NonNullList<ItemStack> nonnulllist : this.f_35979_) {
         if (p_35993_ < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         p_35993_ -= nonnulllist.size();
      }

      return list != null && !list.get(p_35993_).m_41619_() ? ContainerHelper.m_18969_(list, p_35993_, p_35994_) : ItemStack.f_41583_;
   }

   public void m_36057_(ItemStack p_36058_) {
      for(NonNullList<ItemStack> nonnulllist : this.f_35979_) {
         for(int i = 0; i < nonnulllist.size(); ++i) {
            if (nonnulllist.get(i) == p_36058_) {
               nonnulllist.set(i, ItemStack.f_41583_);
               break;
            }
         }
      }

   }

   public ItemStack m_8016_(int p_36029_) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList<ItemStack> nonnulllist1 : this.f_35979_) {
         if (p_36029_ < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         p_36029_ -= nonnulllist1.size();
      }

      if (nonnulllist != null && !nonnulllist.get(p_36029_).m_41619_()) {
         ItemStack itemstack = nonnulllist.get(p_36029_);
         nonnulllist.set(p_36029_, ItemStack.f_41583_);
         return itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public void m_6836_(int p_35999_, ItemStack p_36000_) {
      NonNullList<ItemStack> nonnulllist = null;

      for(NonNullList<ItemStack> nonnulllist1 : this.f_35979_) {
         if (p_35999_ < nonnulllist1.size()) {
            nonnulllist = nonnulllist1;
            break;
         }

         p_35999_ -= nonnulllist1.size();
      }

      if (nonnulllist != null) {
         nonnulllist.set(p_35999_, p_36000_);
      }

   }

   public float m_36020_(BlockState p_36021_) {
      return this.f_35974_.get(this.f_35977_).m_41691_(p_36021_);
   }

   public ListTag m_36026_(ListTag p_36027_) {
      for(int i = 0; i < this.f_35974_.size(); ++i) {
         if (!this.f_35974_.get(i).m_41619_()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.m_128344_("Slot", (byte)i);
            this.f_35974_.get(i).m_41739_(compoundtag);
            p_36027_.add(compoundtag);
         }
      }

      for(int j = 0; j < this.f_35975_.size(); ++j) {
         if (!this.f_35975_.get(j).m_41619_()) {
            CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.m_128344_("Slot", (byte)(j + 100));
            this.f_35975_.get(j).m_41739_(compoundtag1);
            p_36027_.add(compoundtag1);
         }
      }

      for(int k = 0; k < this.f_35976_.size(); ++k) {
         if (!this.f_35976_.get(k).m_41619_()) {
            CompoundTag compoundtag2 = new CompoundTag();
            compoundtag2.m_128344_("Slot", (byte)(k + 150));
            this.f_35976_.get(k).m_41739_(compoundtag2);
            p_36027_.add(compoundtag2);
         }
      }

      return p_36027_;
   }

   public void m_36035_(ListTag p_36036_) {
      this.f_35974_.clear();
      this.f_35975_.clear();
      this.f_35976_.clear();

      for(int i = 0; i < p_36036_.size(); ++i) {
         CompoundTag compoundtag = p_36036_.m_128728_(i);
         int j = compoundtag.m_128445_("Slot") & 255;
         ItemStack itemstack = ItemStack.m_41712_(compoundtag);
         if (!itemstack.m_41619_()) {
            if (j >= 0 && j < this.f_35974_.size()) {
               this.f_35974_.set(j, itemstack);
            } else if (j >= 100 && j < this.f_35975_.size() + 100) {
               this.f_35975_.set(j - 100, itemstack);
            } else if (j >= 150 && j < this.f_35976_.size() + 150) {
               this.f_35976_.set(j - 150, itemstack);
            }
         }
      }

   }

   public int m_6643_() {
      return this.f_35974_.size() + this.f_35975_.size() + this.f_35976_.size();
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_35974_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      for(ItemStack itemstack1 : this.f_35975_) {
         if (!itemstack1.m_41619_()) {
            return false;
         }
      }

      for(ItemStack itemstack2 : this.f_35976_) {
         if (!itemstack2.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_35991_) {
      List<ItemStack> list = null;

      for(NonNullList<ItemStack> nonnulllist : this.f_35979_) {
         if (p_35991_ < nonnulllist.size()) {
            list = nonnulllist;
            break;
         }

         p_35991_ -= nonnulllist.size();
      }

      return list == null ? ItemStack.f_41583_ : list.get(p_35991_);
   }

   public Component m_7755_() {
      return new TranslatableComponent("container.inventory");
   }

   public ItemStack m_36052_(int p_36053_) {
      return this.f_35975_.get(p_36053_);
   }

   public void m_150072_(DamageSource p_150073_, float p_150074_, int[] p_150075_) {
      if (!(p_150074_ <= 0.0F)) {
         p_150074_ = p_150074_ / 4.0F;
         if (p_150074_ < 1.0F) {
            p_150074_ = 1.0F;
         }

         for(int i : p_150075_) {
            ItemStack itemstack = this.f_35975_.get(i);
            if ((!p_150073_.m_19384_() || !itemstack.m_41720_().m_41475_()) && itemstack.m_41720_() instanceof ArmorItem) {
               itemstack.m_41622_((int)p_150074_, this.f_35978_, (p_35997_) -> {
                  p_35997_.m_21166_(EquipmentSlot.m_20744_(EquipmentSlot.Type.ARMOR, i));
               });
            }
         }

      }
   }

   public void m_36071_() {
      for(List<ItemStack> list : this.f_35979_) {
         for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            if (!itemstack.m_41619_()) {
               this.f_35978_.m_7197_(itemstack, true, false);
               list.set(i, ItemStack.f_41583_);
            }
         }
      }

   }

   public void m_6596_() {
      ++this.f_35981_;
   }

   public int m_36072_() {
      return this.f_35981_;
   }

   public boolean m_6542_(Player p_36009_) {
      if (this.f_35978_.m_146910_()) {
         return false;
      } else {
         return !(p_36009_.m_20280_(this.f_35978_) > 64.0D);
      }
   }

   public boolean m_36063_(ItemStack p_36064_) {
      for(List<ItemStack> list : this.f_35979_) {
         for(ItemStack itemstack : list) {
            if (!itemstack.m_41619_() && itemstack.m_41656_(p_36064_)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_36001_(Tag<Item> p_36002_) {
      for(List<ItemStack> list : this.f_35979_) {
         for(ItemStack itemstack : list) {
            if (!itemstack.m_41619_() && itemstack.m_150922_(p_36002_)) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_36006_(Inventory p_36007_) {
      for(int i = 0; i < this.m_6643_(); ++i) {
         this.m_6836_(i, p_36007_.m_8020_(i));
      }

      this.f_35977_ = p_36007_.f_35977_;
   }

   public void m_6211_() {
      for(List<ItemStack> list : this.f_35979_) {
         list.clear();
      }

   }

   public void m_36010_(StackedContents p_36011_) {
      for(ItemStack itemstack : this.f_35974_) {
         p_36011_.m_36466_(itemstack);
      }

   }

   public ItemStack m_182403_(boolean p_182404_) {
      ItemStack itemstack = this.m_36056_();
      return itemstack.m_41619_() ? ItemStack.f_41583_ : this.m_7407_(this.f_35977_, p_182404_ ? itemstack.m_41613_() : 1);
   }
}
