package com.cs32.lettersmod.container;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.item.custom.Envelope;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

  public static final ContainerType<Envelope.EnvelopeContainer> ENVELOPE = create("envelope", Envelope::getClientContainer);

  private static <T extends Container> ContainerType<T> create(String name, IContainerFactory<T> factory) {
    ContainerType<T> type = new ContainerType<T>(factory);
    type.setRegistryName(LettersMod.MOD_ID, name);
    System.out.println(type.getRegistryName());
    return type;
  }
}