package com.cs32.lettersmod.container;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

  public static DeferredRegister<ContainerType<?>> CONTAINERS
      = DeferredRegister.create(ForgeRegistries.CONTAINERS, LettersMod.MOD_ID);

  public static final RegistryObject<ContainerType<MailboxContainer>> MAILBOX_CONTAINER
      = CONTAINERS.register("mailbox_container",
      () -> IForgeContainerType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new MailboxContainer(windowId, world, pos, inv, inv.player);
      })));

  public static final RegistryObject<ContainerType<CollectionBoxContainer>> COLLECTION_BOX_CONTAINER
      = CONTAINERS.register("collection_box_container",
      () -> IForgeContainerType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();

        return new CollectionBoxContainer(windowId, inv, IWorldPosCallable.of(world, pos));
//        return new CollectionBoxContainer(windowId, world, pos, inv, inv.player);
      })));


  public static void register(IEventBus eventBus) {
    CONTAINERS.register(eventBus);
  }
}