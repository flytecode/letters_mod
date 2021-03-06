package com.cs32.lettersmod.block.custom;


import com.cs32.lettersmod.container.CollectionBoxContainer;
import com.cs32.lettersmod.courier.MailCourier;
import com.cs32.lettersmod.saveddata.SavedDataClass;
import com.cs32.lettersmod.tileentity.CollectionBoxTile;
import com.cs32.lettersmod.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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

public class CollectionBoxBlock extends Block {
  public CollectionBoxBlock(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                           PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
    if(!worldIn.isRemote()) {
      TileEntity tileEntity = worldIn.getTileEntity(pos);

      //TODO this is the type of place where you would call the MailCourier methods, need to do in interface
      SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) worldIn);
      String resultString = MailCourier.getMail(saver, 1);

      if(!player.isCrouching()) {
        if(tileEntity instanceof CollectionBoxTile) {
          INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

          NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getPos());
        } else {
          throw new IllegalStateException("Our Container provider is missing!");
        }
      } else {
        if(tileEntity instanceof CollectionBoxTile) {
          if(worldIn.isThundering()) {
            EntityType.LIGHTNING_BOLT.spawn(((ServerWorld) worldIn), null, player,
                pos, SpawnReason.TRIGGERED, true, true);

//            ((CollectionBoxTile)tileEntity).lightningHasStruck();
          }
        }
      }
    }
    return ActionResultType.SUCCESS;
  }

  private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
    return new INamedContainerProvider() {
      @Override
      public ITextComponent getDisplayName() {
        return new TranslationTextComponent("screen.lettersmod.collection_box");
      }

      @Nullable
      @Override
      public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CollectionBoxContainer(i, worldIn, pos, playerInventory, playerEntity);
      }
    };
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return ModTileEntities.COLLECTION_BOX_TILE.get().create();
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }
}