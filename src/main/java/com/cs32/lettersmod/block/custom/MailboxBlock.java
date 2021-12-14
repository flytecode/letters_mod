package com.cs32.lettersmod.block.custom;


import com.cs32.lettersmod.container.MailboxContainer;
import com.cs32.lettersmod.courier.MailCourier;
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

public class MailboxBlock extends Block {
  public MailboxBlock(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                           PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    if(!worldIn.isRemote()) {
      TileEntity tileEntity = worldIn.getTileEntity(pos);

      // TODO get the number of items in the inventory so far

      // call the getmail command to update the parcelList
      SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) worldIn);
      String resultString = MailCourier.getMail(saver, 1);
      System.out.println(resultString);

      // now the parcel list should have data
      ListNBT parcelList = (ListNBT) saver.data.get("parcelList");
      assert (parcelList != null);

      // create a new gson to deserialize the parcelstrings
      Gson gson = new Gson();

      // loop through and populate the mailbox
      for (INBT p : parcelList) {
        // cast it to a CompoundNBT, then get the parcelString and turn into ItemStack
        String parcelString = ((CompoundNBT) p).getString("parcelString");
        ItemStack parcel = gson.fromJson(parcelString, ItemStack.class);



        //tileentity.dosomefunctionthataddsparcelstring()
      }


//      if(!player.isCrouching()) {
//        if(tileEntity instanceof MailboxTile) {
//          INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
//
//          NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getPos());
//        } else {
//          throw new IllegalStateException("Our Container provider is missing!");
//        }
//      } else {
//        if(tileEntity instanceof MailboxTile) {
//          if(worldIn.isThundering()) {
//            EntityType.LIGHTNING_BOLT.spawn(((ServerWorld) worldIn), null, player,
//                pos, SpawnReason.TRIGGERED, true, true);
//
//            ((MailboxTile)tileEntity).lightningHasStruck();
//          }
//        }
//      }


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
      public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        IInventory mailboxInv = new Inventory(); //TODO works?
        return new MailboxContainer(i, worldIn, pos, mailboxInv, playerInventory, playerEntity);
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