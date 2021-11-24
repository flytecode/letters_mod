package net.minecraft.client.gui.screens.recipebook;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractFurnaceRecipeBookComponent extends RecipeBookComponent {
   @Nullable
   private Ingredient f_100113_;

   protected void m_5674_() {
      this.f_100270_.m_94624_(152, 182, 28, 18, f_100268_);
   }

   public void m_6904_(@Nullable Slot p_100120_) {
      super.m_6904_(p_100120_);
      if (p_100120_ != null && p_100120_.f_40219_ < this.f_100271_.m_6653_()) {
         this.f_100269_.m_100140_();
      }

   }

   public void m_7173_(Recipe<?> p_100122_, List<Slot> p_100123_) {
      ItemStack itemstack = p_100122_.m_8043_();
      this.f_100269_.m_100147_(p_100122_);
      this.f_100269_.m_100143_(Ingredient.m_43927_(itemstack), (p_100123_.get(2)).f_40220_, (p_100123_.get(2)).f_40221_);
      NonNullList<Ingredient> nonnulllist = p_100122_.m_7527_();
      Slot slot = p_100123_.get(1);
      if (slot.m_7993_().m_41619_()) {
         if (this.f_100113_ == null) {
            this.f_100113_ = Ingredient.m_43921_(this.m_7690_().stream().map(ItemStack::new));
         }

         this.f_100269_.m_100143_(this.f_100113_, slot.f_40220_, slot.f_40221_);
      }

      Iterator<Ingredient> iterator = nonnulllist.iterator();

      for(int i = 0; i < 2; ++i) {
         if (!iterator.hasNext()) {
            return;
         }

         Ingredient ingredient = iterator.next();
         if (!ingredient.m_43947_()) {
            Slot slot1 = p_100123_.get(i);
            this.f_100269_.m_100143_(ingredient, slot1.f_40220_, slot1.f_40221_);
         }
      }

   }

   protected abstract Set<Item> m_7690_();
}