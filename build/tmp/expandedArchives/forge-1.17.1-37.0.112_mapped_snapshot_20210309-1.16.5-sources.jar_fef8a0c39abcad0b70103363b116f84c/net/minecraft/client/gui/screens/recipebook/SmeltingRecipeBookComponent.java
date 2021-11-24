package net.minecraft.client.gui.screens.recipebook;

import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmeltingRecipeBookComponent extends AbstractFurnaceRecipeBookComponent {
   private static final Component f_100519_ = new TranslatableComponent("gui.recipebook.toggleRecipes.smeltable");

   protected Component m_5815_() {
      return f_100519_;
   }

   protected Set<Item> m_7690_() {
      return AbstractFurnaceBlockEntity.m_58423_().keySet();
   }
}