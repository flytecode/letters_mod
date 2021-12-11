package com.cs32.lettersmod.item.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Envelope extends Item {
  public Envelope(Item.Properties properties) {
    super(properties);
  }

  /**
   * Called when an ItemStack with NBT data is read to potentially that ItemStack's NBT data
   *
   * @param nbt
   */
  @Override
  public boolean updateItemStackNBT(CompoundNBT nbt) {
    return super.updateItemStackNBT(nbt);
  }

  /**
   * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
   * {@link #onItemUse}.
   *
   * @param worldIn
   * @param playerIn
   * @param handIn
   */
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn,
                                                  Hand handIn) {
    ItemStack stack = playerIn.getItemInHand(handIn);

    return ActionResult.resultSuccess();
  }


}