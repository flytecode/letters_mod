package com.cs32.lettersmod.tileentity;

import com.cs32.lettersmod.block.ModBlocks;
import com.cs32.lettersmod.container.CollectionBoxContainer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.cs32.lettersmod.LettersMod;

public class ModTileEntities {

  public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
      DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LettersMod.MOD_ID);

  public static RegistryObject<TileEntityType<MailboxTile>> MAILBOX_TILE =
      TILE_ENTITIES.register("mailbox_tile", () -> TileEntityType.Builder.create(
          MailboxTile::new, ModBlocks.MAILBOX.get()).build(null));

  public static RegistryObject<TileEntityType<CollectionBoxTile>> COLLECTION_BOX_TILE =
      TILE_ENTITIES.register("collection_box_tile", () -> TileEntityType.Builder.create(
          CollectionBoxTile::new, ModBlocks.COLLECTION_BOX.get()).build(null));

  public static void register(IEventBus eventBus) {
    TILE_ENTITIES.register(eventBus);
  }
}