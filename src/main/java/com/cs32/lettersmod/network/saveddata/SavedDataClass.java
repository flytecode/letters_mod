package com.cs32.lettersmod.network.saveddata;

import java.util.function.Supplier;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

// class that retrieves and stores the current world address
// from forums https://forums.minecraftforge.net/topic/83420-solved-1152-saving-and-loading-data-per-world/
public class SavedDataClass extends WorldSavedData implements Supplier {
  public CompoundNBT data = new CompoundNBT();

  public SavedDataClass() {
    super(LettersMod.MOD_ID);
  }

  public SavedDataClass(String name) {
    super(name);
  }

  @Override
  public void read(CompoundNBT nbt) {
    data = nbt.getCompound("data");
  }

  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    nbt.put("data", data);
    return nbt;
  }

  public static SavedDataClass forWorld(ServerWorld world) {
    DimensionSavedDataManager storage = world.getSavedData();
    Supplier<SavedDataClass> sup = new SavedDataClass();
    SavedDataClass saver = (SavedDataClass) storage.getOrCreate(sup, LettersMod.MOD_ID);

    if (saver == null) {
      saver = new SavedDataClass();
      storage.set(saver);
    }
    return saver;
  }

  @Override
  public Object get() {
    return this;
  }
}