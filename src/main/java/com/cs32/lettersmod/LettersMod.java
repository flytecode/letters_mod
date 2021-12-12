package com.cs32.lettersmod;

import com.cs32.lettersmod.block.ModBlocks;
import com.cs32.lettersmod.container.screen.EnvelopeScreen;
import com.cs32.lettersmod.network.command.RegisterCommandEvent;
import com.cs32.lettersmod.container.ModContainers;
import com.cs32.lettersmod.item.ModItems;
import com.cs32.lettersmod.network.saveddata.SavedDataClass;
//import com.cs32.lettersmod.screen.MailboxScreen;
import com.cs32.lettersmod.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.stream.Collectors;


@Mod.EventBusSubscriber(modid = LettersMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
// The value here should match an entry in the META-INF/mods.toml file
@Mod(LettersMod.MOD_ID)
public class LettersMod {
  // define mod id for project
  public static final String MOD_ID = "lettersmod";

  public static ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModItems.ENVELOPE_CLOSED);
    }
  };

  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger();

  public LettersMod() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.initSpec());
  }

  @SubscribeEvent
  public static void loadComplete(final FMLLoadCompleteEvent event) {
  }
//
//  @SubscribeEvent
//  public static void clientSetup(final FMLClientSetupEvent event) {
//    ScreenManager.registerFactory(ModContainers.ENVELOPE, EnvelopeScreen::new);
//    LOGGER.debug("Screens Registered");
//  }

  @SubscribeEvent
  public static void registerItems(final RegistryEvent.Register<Item> event) {
    event.getRegistry().registerAll(
        ModItems.STAMP,
        ModItems.ENVELOPE_OPEN,
        ModItems.ENVELOPE_CLOSED);
  }

  private void setup(final FMLCommonSetupEvent event) {
    // some preinit code
    LOGGER.info("HELLO FROM PREINIT");
    LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
  }

  private void enqueueIMC(final InterModEnqueueEvent event) {
    // some example code to dispatch IMC to another mod
    InterModComms.sendTo(MOD_ID, "helloworld", () -> {
      LOGGER.info("Hello world from the MDK");
      return "Hello world";
    });
  }

  private void processIMC(final InterModProcessEvent event) {
    // some example code to receive and process InterModComms from other mods
    LOGGER.info("Got IMC {}", event.getIMCStream().
        map(m -> m.getMessageSupplier().get()).
        collect(Collectors.toList()));
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(FMLServerStartingEvent event) {
    // do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
  @SubscribeEvent
  public void onClientSetupEvent(final FMLClientSetupEvent event) {
    LOGGER.debug("Hellooo from the Client Setup!");
  }

  // from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
  @SubscribeEvent
  public void onWorldLoaded(WorldEvent.Load event) {
    if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
      SavedDataClass saver = SavedDataClass.forWorld((ServerWorld) event.getWorld());

      if (saver.data.contains("MyData")) {
        LOGGER.debug("Found my data: " + saver.data.get("MyData"));
        //Do whatever you want to do with the data // TODO how to use this?
      }
    }
  }

  // from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
  @SubscribeEvent
  public void onWorldSaved(WorldEvent.Save event) {
//    if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld) {
//      // code to retrieve world data and store in saver class
//      SavedDataClass addressSaver = new SavedDataClass("worldAddress");
//      SavedDataClass saver = addressSaver.forWorld((ServerWorld) event.getWorld());
//
//      // set up the address we want to save
//      Address newAddress = new Address("24601");
//
//      // write into container
//      CompoundNBT addressData = new CompoundNBT();
//      newAddress.writeToNBT(addressData);
//
//      // save the container of data and mark dirty
//      saver.data = addressData;
//      saver.markDirty();
//      LOGGER.debug("Put my address data in!");
//    }
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      // register a new block here
      LOGGER.info("HELLO from Register Block");
    }

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
      MinecraftForge.EVENT_BUS.register(RegisterCommandEvent.class);
    }
  }
  public static class Configuration {
    public static ForgeConfigSpec SPEC = null;
    protected static Configuration INSTANCE = null;

    public static Configuration get() {
      return INSTANCE;
    }

    public static ForgeConfigSpec initSpec() {
      final Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);
      SPEC = specPair.getRight();
      INSTANCE = specPair.getLeft();
      return specPair.getRight();
    }

    public final ForgeConfigSpec.BooleanValue LOCK_BOXES;
    public final ForgeConfigSpec.BooleanValue PROTECT_BOX_DESTROY;

    protected Configuration(ForgeConfigSpec.Builder builder) {
      LOCK_BOXES = builder.comment("Block snailboxes from being opened by non-owners").define("lock_boxes", true);
      PROTECT_BOX_DESTROY = builder.comment("Protect snailboxes from being destroyed by non-owners").define("protect_box_destroy", true);
    }
  }
}
