package com.cs32.lettersmod.block;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.item.LetterModItemGroup;
import com.cs32.lettersmod.item.LettersItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.function.Supplier;

/**
 * This class is for making custom block for Mailbox
 */

public class LettersBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, LettersMod.MOD_ID);

    // should not be easily broken
    public static final RegistryObject<Block> MAILBOX = registerBlock("mailbox",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(3)
                    .harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(5f)));


    /**
     * Method that allows us to register our block item
     * @param name - name of block
     * @param block - type
     * @param <T> - param
     * @return - registers
     */
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        LettersItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(LetterModItemGroup.LETTER_MOD_GROUP)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}











