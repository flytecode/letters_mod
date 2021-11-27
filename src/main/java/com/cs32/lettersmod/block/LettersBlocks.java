package com.cs32.lettersmod.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LettersBlocks {

  private static final String MOD_ID = "lettersmod";
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }


}
