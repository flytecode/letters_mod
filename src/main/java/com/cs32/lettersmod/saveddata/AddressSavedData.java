package com.cs32.lettersmod.saveddata;

import java.util.function.Supplier;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

// class that retrieves and stores the current world address
// from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
public class AddressSavedData extends WorldSavedData implements Supplier {
  public CompoundNBT data = new CompoundNBT();

  public AddressSavedData() {
    super(LettersMod.MOD_ID);
  }

  public AddressSavedData(String name) {
    super(name);
  }

  @Override
  public void read(CompoundNBT nbt) {
    data = nbt.getCompound("worldAddress");
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    nbt.put("worldAddress", data);
    return nbt;
  }

  public static AddressSavedData forWorld(ServerWorld world) {
    DimensionSavedDataManager storage = world.getSavedData();
    Supplier<AddressSavedData> sup = new AddressSavedData();
    AddressSavedData saver = (AddressSavedData) storage.getOrCreate(sup, LettersMod.MOD_ID);

    if (saver == null) {
      saver = new AddressSavedData();
      storage.set(saver);
    }
    return saver;
  }

  @Override
  public Object get() {
    return this;
  }
}