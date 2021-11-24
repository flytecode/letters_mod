package net.minecraft.client.gui.screens.recipebook;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.Set;
import net.minecraft.stats.RecipeBook;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RecipeCollection {
   private final List<Recipe<?>> f_100491_;
   private final boolean f_100492_;
   private final Set<Recipe<?>> f_100493_ = Sets.newHashSet();
   private final Set<Recipe<?>> f_100494_ = Sets.newHashSet();
   private final Set<Recipe<?>> f_100495_ = Sets.newHashSet();

   public RecipeCollection(List<Recipe<?>> p_100497_) {
      this.f_100491_ = ImmutableList.copyOf(p_100497_);
      if (p_100497_.size() <= 1) {
         this.f_100492_ = true;
      } else {
         this.f_100492_ = m_100508_(p_100497_);
      }

   }

   private static boolean m_100508_(List<Recipe<?>> p_100509_) {
      int i = p_100509_.size();
      ItemStack itemstack = p_100509_.get(0).m_8043_();

      for(int j = 1; j < i; ++j) {
         ItemStack itemstack1 = p_100509_.get(j).m_8043_();
         if (!ItemStack.m_41746_(itemstack, itemstack1) || !ItemStack.m_41658_(itemstack, itemstack1)) {
            return false;
         }
      }

      return true;
   }

   public boolean m_100498_() {
      return !this.f_100495_.isEmpty();
   }

   public void m_100499_(RecipeBook p_100500_) {
      for(Recipe<?> recipe : this.f_100491_) {
         if (p_100500_.m_12709_(recipe)) {
            this.f_100495_.add(recipe);
         }
      }

   }

   public void m_100501_(StackedContents p_100502_, int p_100503_, int p_100504_, RecipeBook p_100505_) {
      for(Recipe<?> recipe : this.f_100491_) {
         boolean flag = recipe.m_8004_(p_100503_, p_100504_) && p_100505_.m_12709_(recipe);
         if (flag) {
            this.f_100494_.add(recipe);
         } else {
            this.f_100494_.remove(recipe);
         }

         if (flag && p_100502_.m_36475_(recipe, (IntList)null)) {
            this.f_100493_.add(recipe);
         } else {
            this.f_100493_.remove(recipe);
         }
      }

   }

   public boolean m_100506_(Recipe<?> p_100507_) {
      return this.f_100493_.contains(p_100507_);
   }

   public boolean m_100512_() {
      return !this.f_100493_.isEmpty();
   }

   public boolean m_100515_() {
      return !this.f_100494_.isEmpty();
   }

   public List<Recipe<?>> m_100516_() {
      return this.f_100491_;
   }

   public List<Recipe<?>> m_100510_(boolean p_100511_) {
      List<Recipe<?>> list = Lists.newArrayList();
      Set<Recipe<?>> set = p_100511_ ? this.f_100493_ : this.f_100494_;

      for(Recipe<?> recipe : this.f_100491_) {
         if (set.contains(recipe)) {
            list.add(recipe);
         }
      }

      return list;
   }

   public List<Recipe<?>> m_100513_(boolean p_100514_) {
      List<Recipe<?>> list = Lists.newArrayList();

      for(Recipe<?> recipe : this.f_100491_) {
         if (this.f_100494_.contains(recipe) && this.f_100493_.contains(recipe) == p_100514_) {
            list.add(recipe);
         }
      }

      return list;
   }

   public boolean m_100517_() {
      return this.f_100492_;
   }
}