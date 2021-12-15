package com.cs32.lettersmod.block.custom;


import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.MailboxContainer;
import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.network.MailboxOpenedPacket;
import com.cs32.lettersmod.network.SendParcelPacket;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.cs32.lettersmod.tileentity.MailboxTile;
import com.cs32.lettersmod.tileentity.ModTileEntities;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.lang.reflect.MalformedParameterizedTypeException;

public class MailboxBlock extends Block {
  public MailboxBlock(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                           PlayerEntity player, Hand handIn,
                                           BlockRayTraceResult hit) {
    // tell the server to update the inventory
    LettersMod.network.sendToServer(new MailboxOpenedPacket(pos));

    // display the gui
    if (!worldIn.isRemote()) {
      TileEntity tileEntity = worldIn.getTileEntity(pos);

      if (tileEntity instanceof MailboxTile) {
        INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

        NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
      } else {
        throw new IllegalStateException("Our Container provider is missing!");
      }

    }

    return ActionResultType.SUCCESS;
  }

  private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
    return new INamedContainerProvider() {
      @Override
      public ITextComponent getDisplayName() {
        return new TranslationTextComponent("screen.lettersmod.mailbox");
      }

      @Nullable
      @Override
      public Container createMenu(int i, PlayerInventory playerInventory,
                                  PlayerEntity playerEntity) {
        return new MailboxContainer(i, worldIn, pos, playerInventory, playerEntity);
      }
    };
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return ModTileEntities.MAILBOX_TILE.get().create();
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }
}