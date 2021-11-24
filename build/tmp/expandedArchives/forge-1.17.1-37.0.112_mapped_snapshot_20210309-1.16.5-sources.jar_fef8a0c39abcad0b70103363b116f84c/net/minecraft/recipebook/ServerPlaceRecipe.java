package net.minecraft.recipebook;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlaceRecipe<C extends Container> implements PlaceRecipe<Integer> {
   protected static final Logger f_135425_ = LogManager.getLogger();
   protected final StackedContents f_135426_ = new StackedContents();
   protected Inventory f_135427_;
   protected RecipeBookMenu<C> f_135428_;

   public ServerPlaceRecipe(RecipeBookMenu<C> p_135431_) {
      this.f_135428_ = p_135431_;
   }

   public void m_135434_(ServerPlayer p_135435_, @Nullable Recipe<C> p_135436_, boolean p_135437_) {
      if (p_135436_ != null && p_135435_.m_8952_().m_12709_(p_135436_)) {
         this.f_135427_ = p_135435_.m_150109_();
         if (this.m_135453_() || p_135435_.m_7500_()) {
            this.f_135426_.m_36453_();
            p_135435_.m_150109_().m_36010_(this.f_135426_);
            this.f_135428_.m_5816_(this.f_135426_);
            if (this.f_135426_.m_36475_(p_135436_, (IntList)null)) {
               this.m_6024_(p_135436_, p_135437_);
            } else {
               this.m_179844_(true);
               p_135435_.f_8906_.m_141995_(new ClientboundPlaceGhostRecipePacket(p_135435_.f_36096_.f_38840_, p_135436_));
            }

            p_135435_.m_150109_().m_6596_();
         }
      }
   }

   protected void m_179844_(boolean p_179845_) {
      for(int i = 0; i < this.f_135428_.m_6653_(); ++i) {
         if (this.f_135428_.m_142157_(i)) {
            ItemStack itemstack = this.f_135428_.m_38853_(i).m_7993_().m_41777_();
            this.f_135427_.m_150076_(itemstack, false);
            this.f_135428_.m_38853_(i).m_5852_(itemstack);
         }
      }

      this.f_135428_.m_6650_();
   }

   protected void m_6024_(Recipe<C> p_135441_, boolean p_135442_) {
      boolean flag = this.f_135428_.m_6032_(p_135441_);
      int i = this.f_135426_.m_36493_(p_135441_, (IntList)null);
      if (flag) {
         for(int j = 0; j < this.f_135428_.m_6656_() * this.f_135428_.m_6635_() + 1; ++j) {
            if (j != this.f_135428_.m_6636_()) {
               ItemStack itemstack = this.f_135428_.m_38853_(j).m_7993_();
               if (!itemstack.m_41619_() && Math.min(i, itemstack.m_41741_()) < itemstack.m_41613_() + 1) {
                  return;
               }
            }
         }
      }

      int j1 = this.m_135449_(p_135442_, i, flag);
      IntList intlist = new IntArrayList();
      if (this.f_135426_.m_36478_(p_135441_, intlist, j1)) {
         int k = j1;

         for(int l : intlist) {
            int i1 = StackedContents.m_36454_(l).m_41741_();
            if (i1 < k) {
               k = i1;
            }
         }

         if (this.f_135426_.m_36478_(p_135441_, intlist, k)) {
            this.m_179844_(false);
            this.m_135408_(this.f_135428_.m_6635_(), this.f_135428_.m_6656_(), this.f_135428_.m_6636_(), p_135441_, intlist.iterator(), k);
         }
      }

   }

   public void m_5817_(Iterator<Integer> p_135444_, int p_135445_, int p_135446_, int p_135447_, int p_135448_) {
      Slot slot = this.f_135428_.m_38853_(p_135445_);
      ItemStack itemstack = StackedContents.m_36454_(p_135444_.next());
      if (!itemstack.m_41619_()) {
         for(int i = 0; i < p_135446_; ++i) {
            this.m_135438_(slot, itemstack);
         }
      }

   }

   protected int m_135449_(boolean p_135450_, int p_135451_, boolean p_135452_) {
      int i = 1;
      if (p_135450_) {
         i = p_135451_;
      } else if (p_135452_) {
         i = 64;

         for(int j = 0; j < this.f_135428_.m_6635_() * this.f_135428_.m_6656_() + 1; ++j) {
            if (j != this.f_135428_.m_6636_()) {
               ItemStack itemstack = this.f_135428_.m_38853_(j).m_7993_();
               if (!itemstack.m_41619_() && i > itemstack.m_41613_()) {
                  i = itemstack.m_41613_();
               }
            }
         }

         if (i < 64) {
            ++i;
         }
      }

      return i;
   }

   protected void m_135438_(Slot p_135439_, ItemStack p_135440_) {
      int i = this.f_135427_.m_36043_(p_135440_);
      if (i != -1) {
         ItemStack itemstack = this.f_135427_.m_8020_(i).m_41777_();
         if (!itemstack.m_41619_()) {
            if (itemstack.m_41613_() > 1) {
               this.f_135427_.m_7407_(i, 1);
            } else {
               this.f_135427_.m_8016_(i);
            }

            itemstack.m_41764_(1);
            if (p_135439_.m_7993_().m_41619_()) {
               p_135439_.m_5852_(itemstack);
            } else {
               p_135439_.m_7993_().m_41769_(1);
            }

         }
      }
   }

   private boolean m_135453_() {
      List<ItemStack> list = Lists.newArrayList();
      int i = this.m_135454_();

      for(int j = 0; j < this.f_135428_.m_6635_() * this.f_135428_.m_6656_() + 1; ++j) {
         if (j != this.f_135428_.m_6636_()) {
            ItemStack itemstack = this.f_135428_.m_38853_(j).m_7993_().m_41777_();
            if (!itemstack.m_41619_()) {
               int k = this.f_135427_.m_36050_(itemstack);
               if (k == -1 && list.size() <= i) {
                  for(ItemStack itemstack1 : list) {
                     if (itemstack1.m_41656_(itemstack) && itemstack1.m_41613_() != itemstack1.m_41741_() && itemstack1.m_41613_() + itemstack.m_41613_() <= itemstack1.m_41741_()) {
                        itemstack1.m_41769_(itemstack.m_41613_());
                        itemstack.m_41764_(0);
                        break;
                     }
                  }

                  if (!itemstack.m_41619_()) {
                     if (list.size() >= i) {
                        return false;
                     }

                     list.add(itemstack);
                  }
               } else if (k == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int m_135454_() {
      int i = 0;

      for(ItemStack itemstack : this.f_135427_.f_35974_) {
         if (itemstack.m_41619_()) {
            ++i;
         }
      }

      return i;
   }
}