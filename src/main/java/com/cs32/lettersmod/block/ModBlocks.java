package com.cs32.lettersmod.block;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.block.custom.CollectionBoxBlock;
import com.cs32.lettersmod.block.custom.MailboxBlock;
import com.cs32.lettersmod.item.ModItemGroup;
import com.cs32.lettersmod.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS
      = DeferredRegister.create(ForgeRegistries.BLOCKS, LettersMod.MOD_ID);

  public static final RegistryObject<Block> MAILBOX = registerBlock("mailbox",
      () -> new MailboxBlock(AbstractBlock.Properties.create(Material.WOOD)));
  public static final RegistryObject<Block> COLLECTION_BOX = registerBlock("collection_box",
      () -> new CollectionBoxBlock(AbstractBlock.Properties.create(Material.WOOD)));


  private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn);
    return toReturn;
  }

  private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
    ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
        new Item.Properties().group(ModItemGroup.LETTERS_GROUP)));
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}