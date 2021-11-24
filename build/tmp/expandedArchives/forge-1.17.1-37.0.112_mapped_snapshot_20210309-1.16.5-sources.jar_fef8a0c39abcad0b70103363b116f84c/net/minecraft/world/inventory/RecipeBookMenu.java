package net.minecraft.world.inventory;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.crafting.Recipe;

public abstract class RecipeBookMenu<C extends Container> extends AbstractContainerMenu {
   public RecipeBookMenu(MenuType<?> p_40115_, int p_40116_) {
      super(p_40115_, p_40116_);
   }

   public void m_6951_(boolean p_40119_, Recipe<?> p_40120_, ServerPlayer p_40121_) {
      new ServerPlaceRecipe(this).m_135434_(p_40121_, p_40120_, p_40119_);
   }

   public abstract void m_5816_(StackedContents p_40117_);

   public abstract void m_6650_();

   public abstract boolean m_6032_(Recipe<? super C> p_40118_);

   public abstract int m_6636_();

   public abstract int m_6635_();

   public abstract int m_6656_();

   public abstract int m_6653_();

   public java.util.List<net.minecraft.client.RecipeBookCategories> getRecipeBookCategories() {
       return net.minecraft.client.RecipeBookCategories.m_92269_(this.m_5867_());
   }

   public abstract RecipeBookType m_5867_();

   public abstract boolean m_142157_(int p_150635_);
}
